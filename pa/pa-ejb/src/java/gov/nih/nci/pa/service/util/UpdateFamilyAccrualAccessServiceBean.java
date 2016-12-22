package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Kalpana Guthikonda
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class UpdateFamilyAccrualAccessServiceBean implements UpdateFamilyAccrualAccessServiceLocal {

    private static final Logger LOG = Logger.getLogger(UpdateFamilyAccrualAccessServiceBean.class);
    private boolean useTestSeq = false;
    private static final String CTEP_DCP_TRIALID_SQRY = "SELECT DISTINCT sp.identifier FROM study_protocol sp" 
            + " WHERE sp.status_code = 'ACTIVE' AND  (exists (select study_protocol_identifier from rv_ctep_id"
            + " where study_protocol_identifier=sp.identifier and local_sp_indentifier is not null) or exists"
            + " (select study_protocol_identifier from rv_dcp_id where"
            + " study_protocol_identifier=sp.identifier and local_sp_indentifier is not null))";
    
    /** The Constant DWF_QRY. */
    public static final String DWF_QRY = "select study_protocol_identifier, status_code from rv_dwf_current "
            + "where study_protocol_identifier in (:spIds)";
    
    /** The Constant LEAD_ORG_QRY. */
    public static final String LEAD_ORG_QRY = "select study_protocol_identifier, assigned_identifier "
            + "from rv_lead_organization where study_protocol_identifier in (:spIds)";

    @EJB
    private FamilyServiceLocal familyService;
    @EJB
    private DataAccessServiceLocal dataAccessService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void updateFamilyAccrualAccess() throws PAException {
        try {
            LOG.info("Starting UpdateFamilyAccrualAccessServiceBean");
            UsernameHolder.setUser("pauser");
            Session session = PaHibernateUtil.getCurrentSession();
            List<Long> ctepDcpTrialIdsList = new ArrayList<Long>();        
            SQLQuery query = session.createSQLQuery(CTEP_DCP_TRIALID_SQRY);
            List<BigInteger> queryList = query.list();
            for (BigInteger obj : queryList) {
                Long studyProtocolId = obj.longValue();
                ctepDcpTrialIdsList.add(studyProtocolId);
            }
            String familyAccessUsersQuery = "from RegistryUser where familyAccrualSubmitter = 'true'";
            Query queryObject = session.createQuery(familyAccessUsersQuery);
            List<RegistryUser> rUsers = queryObject.list();
            Map<RegistryUser, Set<Long>> userTrials = new HashMap<RegistryUser, Set<Long>>();
             DAQuery qr = new DAQuery();
             qr.setSql(true);
             qr.setText(DWF_QRY);

            for (RegistryUser ru : rUsers) {
                Set<Long> siteAccrualTrials = familyService.getSiteAccrualTrials(ru.getAffiliatedOrganizationId());
                LOG.info("siteAccrualTrials size is : " + siteAccrualTrials.size() + " for: " + ru.getFullName());
                if (CollectionUtils.isNotEmpty(siteAccrualTrials)) {
                Map<Long, String> trialsWorkFlowStatus = new HashMap<Long, String>();
                qr.addParameter("spIds", siteAccrualTrials);
                List<Object[]> trialsDWFS = dataAccessService.findByQuery(qr);
                for (Object[] row : trialsDWFS) {
                    Long studyId = ((BigInteger) row[0]).longValue();
                    String status = row[1].toString();
                    trialsWorkFlowStatus.put(studyId, status);
                }
                Set<Long> trialsWithoutCTEPOrDCPId = new HashSet<Long>();
                for (Long trialId : siteAccrualTrials) {
                   if (!ctepDcpTrialIdsList.contains(trialId) && isEligibleForAccrual(trialId, trialsWorkFlowStatus)) {
                       trialsWithoutCTEPOrDCPId.add(trialId);
                   }
                }
                userTrials.put(ru, trialsWithoutCTEPOrDCPId);
                LOG.info("the trial size is: " + trialsWithoutCTEPOrDCPId.size() + " and uname:" + ru.getFullName());
                }
            }
            User pauser = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser());
            for (Map.Entry<RegistryUser, Set<Long>> user : userTrials.entrySet()) {
                Map<Long, Long> trialsLeadOrgIdsList = new HashMap<Long, Long>();
                qr.setText(LEAD_ORG_QRY);
                qr.addParameter("spIds", user.getValue());
                List<Object[]> list = dataAccessService.findByQuery(qr);
                for (Object[] row : list) {
                    trialsLeadOrgIdsList.put(((BigInteger) row[0]).longValue(), Long.valueOf(row[1].toString()));
                }
                List<Long> poOrgList =  FamilyHelper.getAllRelatedOrgs(user.getKey().getAffiliatedOrganizationId());
                for (Long trialID : user.getValue()) {
                    List<Long> studySiteIdList = new ArrayList<Long>();
                    if (trialsLeadOrgIdsList.containsKey(trialID) 
                                && (poOrgList.contains(trialsLeadOrgIdsList.get(trialID)))) {
                        String leadOrgtrial = "select ssas.study_site_identifier from study_site ss"
                        + " join study_site_accrual_status ssas on (ssas.study_site_identifier = ss.identifier)"
                        + " where ss.functional_code ='TREATING_SITE' and ss.study_protocol_identifier = " + trialID
                        + " and ssas.status_code <> 'IN_REVIEW' "
                        + " and ssas.identifier in (select identifier from study_site_accrual_status where "
                        + " study_site_identifier  = ss.identifier and deleted=false"
                        + " order by status_date desc, identifier desc limit 1)";
                        query = session.createSQLQuery(leadOrgtrial);
                    } else {
                        String treatingSitetrial = "select ssas.study_site_identifier from study_site ss"
                        + " join study_site_accrual_status ssas on (ssas.study_site_identifier = ss.identifier)"
                        + " join healthcare_facility hcf on (ss.healthcare_facility_identifier = hcf.identifier)"
                        + " join organization org  on (hcf.organization_identifier = org.identifier)"
                        + " where ss.functional_code ='TREATING_SITE' and ss.study_protocol_identifier = " + trialID
                        + " and org.assigned_identifier IN (:orgIds)"
                        + " and ssas.status_code <> 'IN_REVIEW' "
                        + " and ssas.identifier in (select identifier from study_site_accrual_status where "
                        + " study_site_identifier  = ss.identifier and deleted=false"
                        + " order by status_date desc, identifier desc limit 1)";
                        query = session.createSQLQuery(treatingSitetrial);
                        query.setParameterList("orgIds", FamilyServiceBeanLocal.convertPoOrgIdsToStrings(poOrgList));
                    }
                    List<BigInteger> ssList = query.list();
                    for (BigInteger obj : ssList) {
                        studySiteIdList.add(obj.longValue());
                    }
                    if (CollectionUtils.isNotEmpty(studySiteIdList)) {
                        String studySiteAccess = "select study_site_identifier, identifier, status_code from "
                                + "study_site_accrual_access where study_site_identifier in (:ssIds) and " 
                                + "registry_user_id = " + user.getKey().getId();
                        query = session.createSQLQuery(studySiteAccess);
                        query.setParameterList("ssIds", studySiteIdList);
                        list = query.list();
                        Map<Long, StudySiteAccess> studySiteAccessList = new HashMap<Long, StudySiteAccess>();
                        for (Object[] row : list) {
                            Long studySiteId = ((BigInteger) row[0]).longValue();
                            StudySiteAccess ssa = new StudySiteAccess();
                            if (row[1] instanceof Integer) {
                                ssa.setSsAccrualAccessId(((Integer) row[1]).longValue());
                            } else if (row[1] instanceof BigInteger) {
                                ssa.setSsAccrualAccessId(((BigInteger) row[1]).longValue());
                            }
                            ssa.setStatus(row[2].toString());
                            studySiteAccessList.put(studySiteId, ssa);
                        }
                        for (Long ssId : studySiteIdList) {
                            if (studySiteAccessList.containsKey(ssId) 
                                    && studySiteAccessList.get(ssId).getStatus().equals(
                                                ActiveInactiveCode.INACTIVE.getName())) {
                                query = session.createSQLQuery("update study_site_accrual_access set status_code = "
                                        + ":stsCd, user_last_updated_id=:userId, date_last_updated=now() "
                                        + "WHERE identifier=:ssaaId ");
                                query.setParameter("ssaaId", studySiteAccessList.get(ssId).getSsAccrualAccessId());
                                query.setParameter("stsCd", ActiveInactiveCode.ACTIVE.getName());
                                query.setParameter("userId", pauser.getUserId());
                                query.executeUpdate();
                            } else if (!studySiteAccessList.containsKey(ssId)) {
                                query = session.createSQLQuery("INSERT INTO study_accrual_access(identifier, "
                                        + "study_protocol_identifier, status_code, registry_user_id, action_code, "
                                        + " status_date_range_low, date_last_created, user_last_created_id, source) "
                                        + "VALUES (:identifier, :spId, :stsCd, :ruUserId, :actCode,"
                                        + "now(), now(), :userId, :src )");
                                query.setParameter("identifier", getNextId(session));
                                query.setParameter("spId", trialID);
                                query.setParameter("ruUserId", user.getKey().getId());
                                query.setParameter("src", AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE.getName());
                                query.setParameter("stsCd", ActiveInactiveCode.ACTIVE.getName());
                                query.setParameter("actCode", AssignmentActionCode.ASSIGNED.getName());
                                query.setParameter("userId", pauser.getUserId());
                                query.executeUpdate();

                                query = session.createSQLQuery("INSERT INTO study_site_accrual_access(identifier, "
                                        + "study_site_identifier, status_code, registry_user_id, status_date_range_low,"
                                        + "date_last_created, user_last_created_id, source) "
                                        + "VALUES (:identifier, :ssId, :stsCd, :ruUserId,"
                                        + "now(), now(), :userId, :src )");
                                query.setParameter("identifier", getNextId(session));
                                query.setParameter("ssId", ssId);
                                query.setParameter("ruUserId", user.getKey().getId());
                                query.setParameter("src", AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE.getName());
                                query.setParameter("stsCd", ActiveInactiveCode.ACTIVE.getName());
                                query.setParameter("userId", pauser.getUserId());
                                query.executeUpdate();
                            }
                        }
                    }
                }
            }
            LOG.info("Ending UpdateFamilyAccrualAccessServiceBean");
        } finally {
            UsernameHolder.setUser(null);
        }
    }
    
    /**
    * @author Kalpana Guthikonda
    */
    class StudySiteAccess {

        private Long ssAccrualAccessId;
        private String status;
        
        public Long getSsAccrualAccessId() {
            return ssAccrualAccessId;
        }
        public String getStatus() {
            return status;
        }
        public void setSsAccrualAccessId(Long ssAccrualAccessId) {
            this.ssAccrualAccessId = ssAccrualAccessId;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }
    
    private synchronized Long getNextId(Session session) {
        long seq = 0;
        if (useTestSeq) {
            Random rand = new Random();
            seq = rand.nextLong();
        } else {
            SQLQuery queryObject = session.createSQLQuery("select nextval('hibernate_sequence')");
            seq = Long.valueOf(queryObject.uniqueResult().toString());
        }
        return seq;
    }

    private boolean isEligibleForAccrual(Long trialId, Map<Long, String> trialsWorkFlowStatus) throws PAException {
        boolean result = false;
        DocumentWorkflowStatusCode code = DocumentWorkflowStatusCode.valueOf(trialsWorkFlowStatus.get(trialId));
        result = code.isEligibleForAccrual();
        return result;
    }

    /**
     * @param useTestSeq the useTestSeq to set
     */
    public void setUseTestSeq(boolean useTestSeq) {
        this.useTestSeq = useTestSeq;
    }

    /**
     * @param familyService the familyService to set
     */
    public void setFamilyService(FamilyServiceLocal familyService) {
        this.familyService = familyService;
    }
    
    /**
     * @param dataAccessService service to set (used for testing).
     */
    void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

}
