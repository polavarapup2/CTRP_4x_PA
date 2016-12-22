package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Nov 15, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class FamilyServiceBeanLocal implements FamilyServiceLocal {
    private static final Logger LOG = Logger.getLogger(FamilyServiceBeanLocal.class);
    private static final String ORG_IDS = "orgIds";
    private static final String UNCHECKED = "unchecked";

    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccess;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private DocumentWorkflowStatusServiceLocal dwsService;
    @EJB
    private ParticipatingOrgServiceLocal participatingOrgService;

    /**
     * Class used to run separate thread for processing batch submissions.
     */
    private class FamilyAccessProcessor implements Runnable {
        private final boolean assign;
        private final Set<Long> trialIds;
        private final RegistryUser user;
        private final RegistryUser creator;
        private final String comment;

        public FamilyAccessProcessor(boolean assign, Set<Long> trialIds, RegistryUser user, RegistryUser creator,
                String comment) throws PAException {
            lockUserFA(user, true);
            this.assign = assign;
            this.trialIds = trialIds;
            this.user = user;
            this.creator = creator;
            this.comment = comment;
        }

        @Override
        public void run() {
            try {
                if (assign) {
                    assignFamilyAccess(trialIds, user, creator, comment);
                } else {
                    studySiteAccess.unassignAllAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                            null, creator);
                }
                lockUserFA(user, false);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    private static Map<Long, Date> locks = new HashMap<Long, Date>();
    private static synchronized void lockUserFA(RegistryUser user, boolean lock) throws PAException {
        if (lock) {
            Date lockdt = locks.get(user.getId());
            if (lockdt != null && (new Date().getTime() - lockdt.getTime() < DateUtils.MILLIS_PER_HOUR)) {
                throw new PAException("Prior family accrual submitter request for " + user.getFirstName() + " " 
                        + user.getLastName() + " processing. Try again later.");
            }
            locks.put(user.getId(), new Date());
        } else {
            locks.remove(user.getId());
        }
    }

    static final String HQL_COMPLETE = "SELECT sp.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.researchOrganization ro "
            + "INNER JOIN ro.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sp.proprietaryTrialIndicator = false "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND org.identifier IN (:orgIds) ";

    static final String HQL_ABBR = "SELECT sp.id "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.healthCareFacility hf "
            + "INNER JOIN hf.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sp.proprietaryTrialIndicator = true "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND org.identifier IN (:orgIds) ";

    static final String HQL_ABBR_STATUS = "SELECT sp.id "
                    + "FROM StudyProtocol sp "
                    + "INNER JOIN sp.studyResourcings sr "
                    + "INNER JOIN sp.studySites ss  "
                    + "INNER JOIN ss.healthCareFacility hf "
                    + "INNER JOIN hf.organization org  "
                    + "INNER JOIN ss.studySiteAccrualStatuses ssas "
                    + "WHERE sp.statusCode = :statusCode "
                    + "  AND sp.proprietaryTrialIndicator = true "
                    + "  AND sr.summary4ReportedResourceIndicator = true "
                    + "  AND sr.typeCode != :excludeType "
                    + "  AND ss.functionalCode = :siteCode "
                    + "  AND org.identifier IN (:orgIds) "
                    + "  AND ssas.id = (select max(id) "
                    + "    FROM StudySiteAccrualStatus WHERE studySite.id = ss.id"
                    + "    and deleted=false"
                    + "    AND statusDate = (select max(statusDate) "
                    + "        FROM StudySiteAccrualStatus WHERE studySite.id =ss.id"
                    + "         and deleted=false)) "
                    + "  AND ssas.statusCode NOT IN (:excludeStatus) ";

 
    private static final String HQL_LEAD_ORG = "SELECT org.identifier "
            + "FROM StudyProtocol sp "
            + "INNER JOIN sp.studyResourcings sr "
            + "INNER JOIN sp.studySites ss  "
            + "INNER JOIN ss.researchOrganization ro "
            + "INNER JOIN ro.organization org  "
            + "WHERE sp.statusCode = :statusCode "
            + "  AND sr.summary4ReportedResourceIndicator = true "
            + "  AND sr.typeCode != :excludeType "
            + "  AND ss.functionalCode = :siteCode "
            + "  AND sp.id = :trialId ";

    private static final String HQL_SITE_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId IN (:orgIds) AND siteAccrualSubmitter = true) ";

    private static final String HQL_FAMILY_TRIAL_SUBMITTERS = "FROM RegistryUser "
            + "WHERE (affiliatedOrganizationId IN (:orgIds) AND familyAccrualSubmitter = true) ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignFamilyAccrualAccess(RegistryUser user, RegistryUser creator, String comment) throws PAException {
        if (user == null) {
            return;
        }
        List<Long> poOrgIds =  FamilyHelper.getAllRelatedOrgs(user.getAffiliatedOrganizationId());
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Set<Long> trialIds = getSiteAccrualTrials(poOrgIds);
            Thread batchThread = new Thread(new FamilyAccessProcessor(true, trialIds, user, creator, comment));
            batchThread.start();
        }
        user.setFamilyAccrualSubmitter(true);
        registryUserService.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Long> getSiteAccrualTrials(Long poOrgId) throws PAException {
        List<Long> poOrgList =  FamilyHelper.getAllRelatedOrgs(poOrgId);
        return getSiteAccrualTrials(poOrgList);
    }

    @SuppressWarnings(UNCHECKED)
    Set<Long> getSiteAccrualTrials(List<Long> poOrgIds) throws PAException {
        Set<Long> result = new HashSet<Long>();
        if (CollectionUtils.isNotEmpty(poOrgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query  query = session.createQuery(HQL_COMPLETE);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("siteCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
            query.setParameterList(ORG_IDS, convertPoOrgIdsToStrings(poOrgIds));
            List<Long> queryList = query.list();
            for (Long trialId : queryList) {
                result.add(trialId);
            }
            query = session.createQuery(HQL_ABBR_STATUS);
            query.setParameter("statusCode", ActStatusCode.ACTIVE);
            query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
            query.setParameter("siteCode", StudySiteFunctionalCode.TREATING_SITE);
            query.setParameterList(ORG_IDS, convertPoOrgIdsToStrings(poOrgIds));
           
            query.setParameterList("excludeStatus", RecruitmentStatusCode.NOT_ELIGIBLE_FOR_ACCRUAL_STATUSES);
            queryList = query.list();
            for (Long trialId : queryList) {
                result.add(trialId);
            }
        }
        return result;
    }

    void assignFamilyAccess(Set<Long> trialIds, RegistryUser user, RegistryUser creator, String comment)
            throws PAException {
        Set<Long> idsForAccess = new HashSet<Long>();
        for (Long trialId : trialIds) {
            if (isEligibleForAccrual(trialId)) {
                idsForAccess.add(trialId);
            }
        }
        studySiteAccess.assignTrialLevelAccrualAccessNoTransaction(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE,
                idsForAccess, comment, creator);
    }

    private boolean isEligibleForAccrual(Long trialId) throws PAException {
        boolean result = false;
        DocumentWorkflowStatusDTO dws = dwsService.getCurrentByStudyProtocol(
                IiConverter.convertToStudyProtocolIi(trialId));
        if (dws != null) {
            DocumentWorkflowStatusCode code = CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, 
                    dws.getStatusCode());
            result = code.isEligibleForAccrual();
        }
        return result;
    }

    static Set<String> convertPoOrgIdsToStrings(List<Long> poOrgIds) throws PAException {
        Set<String> orgIdsString = new HashSet<String>();
        for (Long id : poOrgIds) {
            orgIdsString.add(id.toString());
        }
        return orgIdsString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAllAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException {
        if (user == null) {
            return;
        }
        if (creator == null) {
            throw new PAException("Calling FamilyServiceBeanLocal.unassignFamilyAccrualAccess with creator == null.");
        }
        Thread batchThread = new Thread(new FamilyAccessProcessor(false, null, user, creator, null));
        batchThread.start();
        user.setFamilyAccrualSubmitter(false);
        user.setSiteAccrualSubmitter(false);
        registryUserService.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSiteAndFamilyPermissions(Long trialId) throws PAException {
        if (trialId == null) {
            return;
        }
        Map<RegistryUser, AccrualAccessSourceCode> users;
        if (StudySiteAccrualAccessServiceBean.isIndustrialTrial(trialId)) {
            Set<Long> partSites = getParticipatingSiteOrgIds(trialId);
            users = getUsersForAccess(partSites);
        } else {
            Set<Long> leadOrgPoId = getLeadOrgId(trialId);
            users = getUsersForAccess(leadOrgPoId);
        }
        for (Map.Entry<RegistryUser, AccrualAccessSourceCode> user : users.entrySet()) {
            studySiteAccess.assignTrialLevelAccrualAccess(user.getKey(), user.getValue(),
                    Arrays.asList(trialId), null, null);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private Set<Long> getLeadOrgId(Long trialId) {
        Set<Long> result = new HashSet<Long>();
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(HQL_LEAD_ORG);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        query.setParameter("excludeType", SummaryFourFundingCategoryCode.NATIONAL);
        query.setParameter("siteCode", StudySiteFunctionalCode.LEAD_ORGANIZATION);
        query.setParameter("trialId", trialId);
        List<String> queryList = query.list();
        if (CollectionUtils.isNotEmpty(queryList)) {
            result.add(Long.valueOf(queryList.get(0)));
        }
        return result;
    }

    private Set<Long> getParticipatingSiteOrgIds(Long trialId) throws PAException {
        Set<Long> result = new HashSet<Long>();
        List<ParticipatingOrgDTO> sites = participatingOrgService.getTreatingSites(trialId);
        for (ParticipatingOrgDTO site : sites) {
            result.add(Long.valueOf(site.getPoId()));
        }
        return result;
    }

    @SuppressWarnings(UNCHECKED)
    private Map<RegistryUser, AccrualAccessSourceCode> getUsersForAccess(Collection<Long> orgIds) throws PAException {
        Map<RegistryUser, AccrualAccessSourceCode> result = new HashMap<RegistryUser, AccrualAccessSourceCode>();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery(HQL_SITE_TRIAL_SUBMITTERS);
            query.setParameterList(ORG_IDS, orgIds);
            List<RegistryUser> queryList = query.list();
            for (RegistryUser user : queryList) {
                result.put(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
            }
            Collection<Long> relatedOrgs = FamilyHelper.getAllRelatedOrgs(orgIds);
            if (CollectionUtils.isNotEmpty(relatedOrgs)) {
                query = session.createQuery(HQL_FAMILY_TRIAL_SUBMITTERS);
                query.setParameterList(ORG_IDS, relatedOrgs);
                queryList = query.list();
                for (RegistryUser user : queryList) {
                    result.put(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE);
                }
            }
        }
        return result;
    }
    /**
     * @param studySiteAccess the studySiteAccess to set
     */
    public void setStudySiteAccess(StudySiteAccrualAccessServiceLocal studySiteAccess) {
        this.studySiteAccess = studySiteAccess;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param dwsService the dwsService to set
     */
    public void setDwsService(DocumentWorkflowStatusServiceLocal dwsService) {
        this.dwsService = dwsService;
    }

    /**
     * @param participatingOrgService the participatingOrgService to set
     */
    public void setParticipatingOrgService(ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }
}
