package gov.nih.nci.accrual.service.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.service.SubjectAccrualBeanLocal;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity" })
public class SubmissionHistoryBean implements SubmissionHistoryService {
    private static final String YES = "Yes";
    private static final String NO = "No";
    
    @EJB
    private SearchTrialService searchTrialSvc;
    
    private static final int DATE_COL = 3;
    private static final int TYPE_COL = 4;
    private static final int AI_COL = 5;
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final String BATCH_HQL = "from AccrualCollections ac "
            + "join fetch ac.batchFile bf "
            + "join fetch bf.userLastCreated us "
            + "where (ac.nciNumber in (:nciNumbers) "
            + "       or us.userId = :userId) ";
    private static final String GUI_COMPLETE_SQL = 
            "select identifier, study_protocol_identifier,  "
            + "     case when date_last_updated is null then user_last_created_id else user_last_updated_id end, "
            + "     case when date_last_updated is null then date_last_created else date_last_updated end, "
            + "     submission_type, assigned_identifier "
            + "from study_subject "
            + "where submission_type in ('" 
            + AccrualSubmissionTypeCode.UI.getName() + "','" + AccrualSubmissionTypeCode.SERVICE_MSA.getName() + "') "
            + "and status_code = '" + FunctionalRoleStatusCode.ACTIVE.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";
    private static final String GUI_ABBREVIATED_SQL =
            "select identifier, study_protocol_identifier, user_last_updated_id, date_last_updated "
            + "from study_site_subject_accrual_count "
            + "where submission_type = '" + AccrualSubmissionTypeCode.UI.getName() + "' "
            + "and study_protocol_identifier in (:studyProtocolIdentifiers) ";    
    private static final String AFFILIATEDORG_TREATING_SITE_TRIALS_SQL = 
            "select sp.identifier from study_protocol sp"
            + " join study_site ss on (ss.study_protocol_identifier = sp.identifier)"
            + " join healthcare_facility hcf on (ss.healthcare_facility_identifier = hcf.identifier)"
            + " join organization org on (hcf.organization_identifier = org.identifier)"
            + " where ss.functional_code = 'TREATING_SITE' and sp.status_code = 'ACTIVE'"
            + " and org.assigned_identifier IN (:affiliatedOrgId)"; 
    private static final String AFFILIATEDORG_LEAD_ORG_TRIALS_SQL = 
            "select sp.identifier from study_protocol sp"
            + " join study_site ss on (ss.study_protocol_identifier = sp.identifier)"
            + " join research_organization ro on (ss.research_organization_identifier = ro.identifier)"
            + " join organization org on (ro.organization_identifier = org.identifier)"
            + " where ss.functional_code = 'LEAD_ORGANIZATION' and sp.status_code = 'ACTIVE'"
            + " and org.assigned_identifier IN (:affiliatedOrgId)";

    /** Used to store data on submitters. Avoid redundant db queries. */
    private class UserData {
        private final String name;
        private final Long organization;
        public UserData(String name, Long organization) {
            this.name = name;
            this.organization = organization;
        }
    }

    /** Used to store data on trial. Avoid redundant db queries. */
    @SuppressFBWarnings
    private class TrialData implements Serializable {
        private static final long serialVersionUID = -8133288058658604054L;
        private final boolean industrial;
        private final boolean affiliatedWithLead;
        private final boolean affiliatedWithSite;
        private final Set<Long> siteAccess;
        public TrialData(boolean industrial, boolean affiliatedWithLead, boolean affiliatedWithSite, 
                Set<Long> siteAccess) {
            this.industrial = industrial;
            this.affiliatedWithLead = affiliatedWithLead;
            this.affiliatedWithSite = affiliatedWithSite;
            this.siteAccess = siteAccess;
        }
    }

    /** Key for trial data cache. */
    @SuppressFBWarnings
    private class TrialDataKey implements Serializable { // NOPMD
        private static final long serialVersionUID = -7287423193024383923L;
        private final Long regUserId;
        private final String nciNumber;
        public TrialDataKey(Long regUserId, String nciNumber) {
            this.regUserId = regUserId;
            this.nciNumber = nciNumber;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (nciNumber == null ? 0 : nciNumber.hashCode());
            result = prime * result + (regUserId == null ? 0 : regUserId.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) { // NOPMD
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof TrialDataKey)) {
                return false;
            }
            TrialDataKey other = (TrialDataKey) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (nciNumber == null) {
                if (other.nciNumber != null) {
                    return false;
                }
            } else if (!nciNumber.equals(other.nciNumber)) {
                return false;
            }
            if (regUserId == null) {
                if (other.regUserId != null) {
                    return false;
                }
            } else if (!regUserId.equals(other.regUserId)) {
                return false;
            }
            return true;
        }
        private SubmissionHistoryBean getOuterType() {
            return SubmissionHistoryBean.this;
        }
    }

    // Cache for trial data
    private static CacheManager cacheManager;
    private static final String TRIAL_DATA_CACHE_KEY = "SUBM_HISTORY_TRIAL_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 500;
    private static final long CACHE_TIME = 600;

    Cache getTrialDataCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(TRIAL_DATA_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                    CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(TRIAL_DATA_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(TRIAL_DATA_CACHE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HistoricalSubmissionDto> search(Timestamp from, Timestamp to, RegistryUser ru) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        if (ru == null) {
            return result;
        }
        Map<Long, String> trials = searchTrialSvc.getAuthorizedTrialMap(ru.getId());
        Map<Long, UserData> users = new HashMap<Long, UserData>();
        if (!trials.isEmpty()) {
            result.addAll(searchGuiCompleteSubmissions(from, to, trials, users));
            result.addAll(searchGuiAbbreviatedSubmissions(from, to, trials, users));
            result.addAll(searchBatchSubmissions(from, to, trials, users));
            Collections.sort(result);
        }
        return filterPriorSubmissionResults(result, ru, trials);
    }

    private List<HistoricalSubmissionDto> filterPriorSubmissionResults(List<HistoricalSubmissionDto> sList, 
            RegistryUser ru, Map<Long, String> trials) throws PAException {
        List<Long> familyOrgIds = FamilyHelper.getAllRelatedOrgs(ru.getAffiliatedOrganizationId());
        List<Long> leadOrgTrials = new ArrayList<Long>();
        List<Long> treatingSiteTrials = new ArrayList<Long>();
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session.createSQLQuery(AFFILIATEDORG_TREATING_SITE_TRIALS_SQL);
        query.setString("affiliatedOrgId", ru.getAffiliatedOrganizationId().toString());
        List<BigInteger> queryList = query.list();
        for (BigInteger obj : queryList) {
            Long studyProtocolId = obj.longValue();
            if (!treatingSiteTrials.contains(studyProtocolId)) {
                treatingSiteTrials.add(studyProtocolId);
            }
        }
        query = session.createSQLQuery(AFFILIATEDORG_LEAD_ORG_TRIALS_SQL);
        query.setString("affiliatedOrgId", ru.getAffiliatedOrganizationId().toString());
        queryList = query.list();
        for (BigInteger obj : queryList) {
            Long studyProtocolId = obj.longValue();
            if (!leadOrgTrials.contains(studyProtocolId)) {
                leadOrgTrials.add(studyProtocolId);
            }
        }
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        for (HistoricalSubmissionDto s : sList) {
            if (s.getRegistryUserId() == null) {
                continue;
            }

            // always show if user is submitter
            if (s.getRegistryUserId().equals(ru.getCsmUser().getUserId())) {
                result.add(s);
                continue;
            }

            // otherwise show only under certain conditions
            if (trials.containsValue(s.getNciNumber()) 
                    && po6304Show(s, ru, familyOrgIds, leadOrgTrials, treatingSiteTrials)) {
                result.add(s);
            }
        }
        return result;
    }

    private boolean po6304Show(HistoricalSubmissionDto s, RegistryUser ru, List<Long> familyOrgIds,
            List<Long> leadOrgTrials, List<Long> treatingSiteTrials) throws PAException {
        TrialData d = getTrialData(s.getNciNumber(), ru, leadOrgTrials, treatingSiteTrials);
        return !d.industrial && d.affiliatedWithLead
                || d.affiliatedWithSite  
                   && d.siteAccess.contains(s.getUserAffiliatedOrgId())
                   && (familyOrgIds.contains(s.getUserAffiliatedOrgId()) 
                       // below required for case where affiliated org is not part of a family
                       || ru.getAffiliatedOrganizationId().equals(s.getUserAffiliatedOrgId()));
    }

    private TrialData getTrialData(String nciId, RegistryUser user,
            List<Long> leadOrgTrials, List<Long> treatingSiteTrials) throws PAException {
        TrialDataKey key = new TrialDataKey(user.getId(), nciId);
        Element element = getTrialDataCache().get(key);
        if (element == null) {
            Long spId = 0L;
            boolean industrial = false;
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = null;
            // Since the rejected trials are not showing up when we call getStudyProtocol and it 
            // throws PAException so using below sql query to get the spid and trial indicator
            query = session.createQuery("select sp.id, sp.proprietaryTrialIndicator " 
                    + " from StudyProtocol as sp inner join sp.otherIdentifiers as oi "
                    + " where sp.statusCode ='ACTIVE' and oi.extension = :nciId"
                    + " and oi.root = '" + IiConverter.STUDY_PROTOCOL_ROOT + "' ");

            query.setParameter("nciId", nciId);
            List<Object[]> list = query.list();
            for (Object[] row : list) {
                spId = (Long) row[0];
                industrial = (Boolean) row[1];
            }
            boolean lead = leadOrgTrials.contains(spId) ? true : false;
            boolean site =  treatingSiteTrials.contains(spId) ? true : false;
            
            Set<Long> siteAccess = new HashSet<Long>();
            String hql = "select org.identifier "
                + "from StudyProtocol sp "
                + "join sp.studySites ss "
                + "join ss.studySiteAccrualAccess ssaa "
                + "join ss.healthCareFacility hcf "
                + "join hcf.organization org "
                + "where sp.id = :spId "
                + "  and ssaa.statusCode = 'ACTIVE' "
                + "  and ssaa.registryUser.id = :ruId";
            query = session.createQuery(hql);
            query.setParameter("spId", spId);
            query.setParameter("ruId", user.getId());
            List<String> queryList = query.list();
            for (String obj : queryList) {
                siteAccess.add(Long.valueOf(obj));
            }
            TrialData td = new TrialData(industrial, lead, site, siteAccess);
            element = new Element(key, td);
            getTrialDataCache().put(element);
        }
        return (TrialData) element.getValue();
    }

    private List<HistoricalSubmissionDto> searchBatchSubmissions(Timestamp from, Timestamp to, // NOPMD
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer(BATCH_HQL);
        hql.append(getHqlDateCriteria(from, to));
        Query query = session.createQuery(hql.toString());
        query.setParameterList("nciNumbers", trials.values());
        User user = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser());
        query.setLong("userId", user.getUserId());
        setDateProperties(query, from, to);
        List<Object> submList = query.list();
        for (Object subm : submList) {
            AccrualCollections ac = (AccrualCollections) subm;
            BatchFile bf = ac.getBatchFile();
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setBatchFileIdentifier(bf.getId());
            row.setDate(bf.getDateLastCreated() == null ? null : new Timestamp(bf.getDateLastCreated().getTime()));
            File file = new File(bf.getFileLocation());
            row.setFileName(AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()));
            row.setNciNumber(ac.getNciNumber());
            row.setResult(getBatchResult(ac));
            row.setSubmissionType(bf.getSubmissionTypeCode());
            row.setRegistryUserId(bf.getUserLastCreated() == null ? null : bf.getUserLastCreated().getUserId());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData != null ? userData.name
                    : StringUtils.EMPTY);
            row.setUserAffiliatedOrgId(userData != null ? userData.organization
                    : 0);
            result.add(row);
        }
        return result;
    }

    String getBatchResult(AccrualCollections ac) {
        String result = null;
        if (!ac.isPassedValidation()) {
            result = NO;
        }
        if (ac.getTotalImports() != null) {
            result = YES;
        }
        if (result == null) {
            Date td = new Date(new Date().getTime() 
                    - SubjectAccrualBeanLocal.BATCH_PROCESSING_THREAD_TIMEOUT_HOURS * ONE_HOUR);
            if (td.after(ac.getDateLastCreated())) {
                result = NO;
            }
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiCompleteSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer(GUI_COMPLETE_SQL);
        sql.append(getSqlDateCriteria(from, to));
        Query query = session.createSQLQuery(sql.toString());
        query.setParameterList("studyProtocolIdentifiers", trials.keySet());
        setDateProperties(query, from, to);
        List<Object[]> submList = query.list();
        for (Object[] subm : submList) {
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setDate((Timestamp) subm[DATE_COL]);
            Long trialId = ((BigInteger) subm[1]).longValue();
            row.setCompleteTrialId(trialId);
            row.setNciNumber(trials.get(trialId));
            row.setResult(YES);
            row.setSubmissionType(AccrualSubmissionTypeCode.valueOf((String) subm[TYPE_COL]));
            row.setRegistryUserId(subm[2] == null ? null : ((Number) subm[2]).longValue());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData.name);
            row.setUserAffiliatedOrgId(userData.organization);
            row.setAssignedIdentifier((String) subm[AI_COL]);
            row.setStudySubjectId(((Number) subm[0]).longValue());
            result.add(row);
        }
        return result;
    }

    private List<HistoricalSubmissionDto> searchGuiAbbreviatedSubmissions(Timestamp from, Timestamp to, 
            Map<Long, String> trials, Map<Long, UserData> users) throws PAException {
        List<HistoricalSubmissionDto> result = new ArrayList<HistoricalSubmissionDto>();
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer(GUI_ABBREVIATED_SQL);
        sql.append(getSqlDateCriteria(from, to));
        Query query = session.createSQLQuery(sql.toString());
        query.setParameterList("studyProtocolIdentifiers", trials.keySet());
        setDateProperties(query, from, to);
        List<Object[]> submList = query.list();
        for (Object[] subm : submList) {
            HistoricalSubmissionDto row = new HistoricalSubmissionDto();
            row.setDate((Timestamp) subm[DATE_COL]);
            Long trialId = ((BigInteger) subm[1]).longValue();
            row.setAbbreviatedTrialId(trialId);
            row.setNciNumber(trials.get(trialId));
            row.setResult(YES);
            row.setSubmissionType(AccrualSubmissionTypeCode.UI);
            row.setRegistryUserId(subm[2] == null ? null : ((Number) subm[2]).longValue());
            UserData userData = getRegistryUserData(users, row.getRegistryUserId());
            row.setUsername(userData.name);
            row.setUserAffiliatedOrgId(userData.organization);
            result.add(row);
        }
        return result;
    }

    private String getHqlDateCriteria(Timestamp from, Timestamp to) {
        StringBuffer result = new StringBuffer();
        if (from != null) {
            result.append("and bf.dateLastCreated >= :from ");
        }
        if (to != null) {
            result.append("and bf.dateLastCreated < :to ");
        }
        return result.toString();
    }

    private String getSqlDateCriteria(Timestamp from, Timestamp to) {
        StringBuffer result = new StringBuffer();
        if (from != null) {
            result.append("and date_last_updated >= :from ");
        }
        if (to != null) {
            result.append("and date_last_updated < :to ");
        }
        return result.toString();
    }

    private void setDateProperties(Query query, Timestamp from, Timestamp to) {
        if (from != null) {
            query.setParameter("from", from);
        }
        if (to != null) {
            Timestamp toParam = new Timestamp(to.getTime() + ONE_DAY);
            query.setParameter("to", toParam);
        }
    }

    private UserData getRegistryUserData(Map<Long, UserData> users, Long ruId) {
        if (ruId == null) {
            return new UserData(null, null);
        }
        if (users.get(ruId) ==  null) {
            Session session = PaHibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer(
                    "from RegistryUser ru join fetch ru.csmUser cu where cu.id = :identifier");
            Query query = session.createQuery(hql.toString());
            query.setParameter("identifier", ruId);
            List<Object> userList = query.list();
            if (CollectionUtils.isNotEmpty(userList)) {
                RegistryUser registryUser = (RegistryUser) userList.get(0);
                users.put(ruId, new UserData(AccrualUtil.getDisplayName(registryUser), 
                        registryUser.getAffiliatedOrganizationId()));
            }
        }
        return users.get(ruId);
    }

    /**
     * @param searchTrialSvc the searchTrialSvc to set
     */
    public void setSearchTrialSvc(SearchTrialService searchTrialSvc) {
        this.searchTrialSvc = searchTrialSvc;
    }
}
