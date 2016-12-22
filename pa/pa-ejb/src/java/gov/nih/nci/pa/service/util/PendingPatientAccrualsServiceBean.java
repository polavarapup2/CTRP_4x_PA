package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author Kalpana Guthikonda
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity", "PMD.NPathComplexity", 
    "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength" })
public class PendingPatientAccrualsServiceBean implements PendingPatientAccrualsServiceLocal {

    private static final Logger LOG = Logger.getLogger(PendingPatientAccrualsServiceBean.class);
    private static final String IDENTIFIER = "identifier";
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final int RESULTS_LEN = 1000;
    private boolean useTestSeq = false;
    
    @EJB
    private ParticipatingOrgServiceLocal partOrgSrv;
    @EJB 
    private LookUpTableServiceRemote lookUpTableService;
    @EJB 
    private MailManagerServiceLocal mailManagerService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessSrv;
    @EJB
    private StudyProtocolServiceLocal studyProtocolSrv;
    
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void readAndProcess() throws PAException {
        long startTime = System.currentTimeMillis();
        LOG.info("Started reading PendingPatientAccrualsServiceBean");
        Session session = PaHibernateUtil.getCurrentSession();
        String studiesQuery = "select distinct studyProtocolIdentifier from PatientStage";
        Query queryObject = session.createQuery(studiesQuery);
        List<Long> spIds = queryObject.list();
        
        RegistryUser registryUser = null;
        Map<RegistryUser, List<String[]>> notFoundSites = new HashMap<RegistryUser, List<String[]>>();
        for (Long spId : spIds) {
            String hql = "from PatientStage where studyProtocolIdentifier = " + spId;
            List<ParticipatingOrgDTO> orgList = partOrgSrv.getTreatingSites(spId);
            Map<String, Long> listOfPoStudySiteIds = new HashMap<String, Long>();
            Map<String, String> listOfCtepIds = new HashMap<String, String>();
            List<Long> poIds = new ArrayList<Long>();
            for (ParticipatingOrgDTO iso : orgList) {
                listOfPoStudySiteIds.put(iso.getPoId(), iso.getStudySiteId());
            }
            for (Map.Entry<String, Long> entry : listOfPoStudySiteIds.entrySet()) {
                poIds.add(IiConverter.convertToLong(IiConverter.convertToPoOrganizationIi(
                        entry.getKey())));
            }
            
            if (CollectionUtils.isNotEmpty(poIds)) {
                List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                        .getIdentifiedOrganizationEntityService()
                        .getCorrelationsByPlayerIdsWithoutLimit(poIds.toArray(
                                new Long[poIds.size()])); // NOPMD
                for (IdentifiedOrganizationDTO idOrgDTO : identifiedOrgs) {
                if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(idOrgDTO.getAssignedId().getRoot())) {
                        listOfCtepIds.put(idOrgDTO.getAssignedId().getExtension(), 
                            idOrgDTO.getPlayerIdentifier().getExtension());
                    }
                }
            }
            queryObject = session.createQuery(hql);
            List<PatientStage> results = queryObject.list();
            String nciId = "";
            HashSet<String> failureReords = new HashSet<String>();
            for (PatientStage ps : results) {
                nciId = ps.getStudyIdentifier();
                User user = ps.getUserLastCreated();                
                registryUser = getRegistryUser(session, user);
                Ii studySiteOrgIi = null;
                boolean processedRecord = false;
                String registeringInstitutionID = ps.getStudySite();
                if (listOfPoStudySiteIds.containsKey(registeringInstitutionID)) {
                    studySiteOrgIi = IiConverter.convertToIi(registeringInstitutionID);
                } else if (listOfCtepIds.containsKey(registeringInstitutionID)) {
                    studySiteOrgIi = IiConverter.convertToIi(listOfCtepIds.get(registeringInstitutionID));
                } else {
                    failureReords.add(registeringInstitutionID);
                }
                if (studySiteOrgIi != null) {
                    Long studySite  = listOfPoStudySiteIds.get(studySiteOrgIi.getExtension());
                    
                    createAccrualAccess(registryUser, studySite);
                    
                    if (ps.getAccrualCount() == null) {
                        Long[] ids = getSubjectAndPatientId(spId, studySite, ps.getAssignedIdentifier());
                        if (ids == null) {
                            create(ps, spId, user.getUserId(), studySite);
                            processedRecord = true;
                        } else {
                            update(ps, user.getUserId(), ids, studySite);
                            processedRecord = true;
                        }
                    } else {
                        doUpdateToSubjectAccrual(studySite, ps.getAccrualCount(), user);
                        processedRecord = true;
                    }
                    if (processedRecord) {
                        session.createSQLQuery("delete from patient_stage where identifier = " 
                                + ps.getId()).executeUpdate();
                    }
                }

            }
            if (CollectionUtils.isNotEmpty(failureReords)) {
                String[] missingSites = {nciId, StringUtils.join(failureReords, ',')};
                if (!notFoundSites.containsKey(registryUser)) {
                    List<String[]> trialMissingSites = new ArrayList<String[]>();
                    trialMissingSites.add(missingSites);
                    notFoundSites.put(registryUser, trialMissingSites);
                } else {
                    notFoundSites.get(registryUser).add(missingSites);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(notFoundSites.keySet())) {
            String mailSubject = lookUpTableService.getPropertyValue("accrualjob.email.subject");
            String mailBody = lookUpTableService.getPropertyValue("accrualjob.email.body");
            mailBody = mailBody.replace("${CurrentDate}", getFormatedCurrentDate());
            for (RegistryUser rec : notFoundSites.keySet()) {
                StringBuffer innerTable = new StringBuffer();
                for (String[] trialSites : notFoundSites.get(rec)) {
                    innerTable.append("<tr><td>" + trialSites[0] 
                            + "</td><td>" + trialSites[1] + "</td></tr>");
                }
                String regUserName = rec.getFirstName() + " " + rec.getLastName();
                String body = mailBody;
                body = body.replace("${SubmitterName}", regUserName);
                body = body.replace("${tableRows}", innerTable.toString()); 
                String mailto = rec.getEmailAddress();
                mailManagerService.sendMailWithHtmlBody(mailto, mailSubject, body);
            }
        }
        LOG.info("Time to read and process patient stage data: " 
                + (System.currentTimeMillis() - startTime) / RESULTS_LEN + " seconds on " + getFormatedCurrentDate());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientStage> getAllPatientsStage(String identifier) throws PAException {
        LOG.info("Started getting PendingPatients");
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("from PatientStage");
        if (StringUtils.isNotEmpty(identifier)) {
            StudyProtocolDTO foundStudy = null;
            Ii protocolIi = IiConverter.convertToAssignedIdentifierIi(identifier);
            if (StringUtils.startsWith(identifier, "NCI")) {
                foundStudy = studyProtocolSrv.loadStudyProtocol(protocolIi);
            }
            if (foundStudy == null) {
                protocolIi.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
                foundStudy = studyProtocolSrv.loadStudyProtocol(protocolIi);
                protocolIi.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
                foundStudy = foundStudy != null ? foundStudy : studyProtocolSrv.loadStudyProtocol(protocolIi);
            }
            if (foundStudy != null) {
                Ii ii = DSetConverter.convertToIi(foundStudy.getSecondaryIdentifiers());
                hql = hql.append(" where studyIdentifier = '").append(ii.getExtension()).append("'");
            } else {
                hql = hql.append(" where studyIdentifier = '")
                    .append(StringEscapeUtils.escapeSql(identifier)).append("'");
            }
        }
        hql = hql.append(" order by studyProtocolIdentifier");
        List<PatientStage> psList = session.createQuery(hql.toString()).list();
        if (StringUtils.isNotEmpty(identifier)) {
            Map<Long, StudyIdentifiers> idsList = new HashMap<Long, StudyIdentifiers>();
            Map<String, String> orgList = new HashMap<String, String>();
            for (PatientStage ps : psList) {
                if (!orgList.containsKey(ps.getStudySite())) {
                    OrganizationDTO org = getOrganizationIi(ps.getStudySite());
                    ps.setOrgName((String) (org != null ? org.getName().getPart().get(0).getValue() : ""));
                    orgList.put(ps.getStudySite(), 
                            (String) (org != null ? org.getName().getPart().get(0).getValue() : ""));
                } else {
                    ps.setOrgName(orgList.get(ps.getStudySite()));
                }
                if (!idsList.containsKey(ps.getStudyProtocolIdentifier())) {
                    String ctepId = paServiceUtils.getCtepOrDcpId(ps.getStudyProtocolIdentifier(), 
                            PAConstants.CTEP_IDENTIFIER_TYPE);
                    if (StringUtils.isNotEmpty(ctepId)) {
                        ps.setCtepId(ctepId);
                    }
                    String dcpId = paServiceUtils.getCtepOrDcpId(ps.getStudyProtocolIdentifier(),
                            PAConstants.DCP_IDENTIFIER_TYPE);
                    if (StringUtils.isNotEmpty(dcpId)) {
                        ps.setDcpId(dcpId);
                    }
                    StudyIdentifiers ids = new StudyIdentifiers();
                    ids.setCtepId(ctepId);
                    ids.setDcpId(dcpId);
                    ids.setSpId(ps.getStudyProtocolIdentifier());

                    idsList.put(ps.getStudyProtocolIdentifier(), ids);
                } else {
                    StudyIdentifiers ids = idsList.get(ps.getStudyProtocolIdentifier());
                    ps.setCtepId(ids.getCtepId());
                    ps.setDcpId(ids.getDcpId());
                }
            }
        }
        LOG.info("Ended getting PendingPatients");
        return psList;
    }
    
     /**
     * @author Kalpana Guthikonda
     */
    class StudyIdentifiers {
        
        private Long spId;
        private String ctepId;
        private String dcpId;
        
        public Long getSpId() {
            return spId;
        }
        public String getCtepId() {
            return ctepId;
        }
        public String getDcpId() {
            return dcpId;
        }
        public void setSpId(Long spId) {
            this.spId = spId;
        }
        public void setCtepId(String ctepId) {
            this.ctepId = ctepId;
        }
        public void setDcpId(String dcpId) {
            this.dcpId = dcpId;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePatientStage(List<Long> psToDelete) throws PAException {
        Map<RegistryUser, List<String[]>> deleteSitesEmail = new HashMap<RegistryUser, List<String[]>>();
        Session session = PaHibernateUtil.getCurrentSession();
        for (Long id : psToDelete) {
            PatientStage ps  = (PatientStage) session.createCriteria(PatientStage.class)
                    .add(Restrictions.eq("id", id)).uniqueResult();
            String[] deletingSites = {ps.getStudyIdentifier(), ps.getStudySite(), ps.getFileName()};
            RegistryUser registryUser = getRegistryUser(session, ps.getUserLastCreated());
            if (!deleteSitesEmail.containsKey(registryUser)) {
                List<String[]> trialDeletingSites = new ArrayList<String[]>();
                trialDeletingSites.add(deletingSites);
                deleteSitesEmail.put(registryUser, trialDeletingSites);
            } else {
                deleteSitesEmail.get(registryUser).add(deletingSites);
            }
            session.delete(ps);
        }
        String mailSubject = lookUpTableService.getPropertyValue("deletePendingAccruals.email.subject");
        String mailBody = lookUpTableService.getPropertyValue("deletePendingAccruals.email.body");
        mailBody = mailBody.replace("${CurrentDate}", getFormatedCurrentDate());
        for (RegistryUser rec : deleteSitesEmail.keySet()) {
            StringBuffer innerTable = new StringBuffer();
            for (String[] trialSites : deleteSitesEmail.get(rec)) {
                    innerTable.append("<tr><td>" + trialSites[0] 
                            + "</td><td>" + trialSites[1] + "</td><td>" + trialSites[2] + "</td></tr>");
            }
            String regUserName = rec.getFirstName() + " " + rec.getLastName();
            String body = mailBody;
            body = body.replace("${SubmitterName}", regUserName);
            body = body.replace("${tableRows}", innerTable.toString()); 
            String mailto = rec.getEmailAddress();
            mailManagerService.sendMailWithHtmlBody(mailto, mailSubject, body);
        }
    }

    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        return DateFormatUtils.format(new Date(), DATE_PATTERN);
    }
    
    private RegistryUser getRegistryUser(Session session, User user) {
        Query queryObject = session.createQuery("from RegistryUser where csmUser = :csmuser");
        queryObject.setParameter("csmuser", user);
        return (RegistryUser) queryObject.uniqueResult();
    }
    
    private OrganizationDTO getOrganizationIi(String orgIdentifier) throws PAException {
        Ii ctepOrgIi = null;
        OrganizationDTO org = null;
        IdentifiedOrganizationDTO identifiedOrg = new IdentifiedOrganizationDTO();
        identifiedOrg.setAssignedId(IiConverter.convertToIdentifiedOrgEntityIi(orgIdentifier));
        List<IdentifiedOrganizationDTO> results = 
                PoRegistry.getIdentifiedOrganizationEntityService().search(identifiedOrg);
        if (CollectionUtils.isNotEmpty(results)) {
            ctepOrgIi = results.get(0).getPlayerIdentifier();
        } 
        try {
            if (NumberUtils.isNumber(orgIdentifier) && ctepOrgIi == null || ctepOrgIi != null) {
                org = PoRegistry.getOrganizationEntityService().getOrganization(
                    ctepOrgIi != null ? ctepOrgIi : IiConverter.convertToPoOrganizationIi(orgIdentifier));
            }
        } catch (NullifiedEntityException e) {
            LOG.error("The organization with the identifier " + orgIdentifier 
                    +  " that is attempting to be loaded is nullified.");
        } 
        return org;
    }
    
    private Long[] getSubjectAndPatientId(Long studyProtocolId, Long studySite, String assignedIdentifier)
            throws PAException {
        Long[] ids = null;
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            String sql = "select identifier, patient_identifier "
                    + "from study_subject where study_protocol_identifier = :studyProtocolId "
                    + "and study_site_identifier = :studySite and assigned_identifier = :assignedIdentifier "
                    + ("and status_code = '" + FunctionalRoleStatusCode.ACTIVE.getName() + "'");
            Query query = session.createSQLQuery(sql);
            query.setLong("studyProtocolId", studyProtocolId);
            query.setLong("studySite", studySite);
            query.setString("assignedIdentifier", assignedIdentifier);
            List<Object[]> list = query.list();
            for (Object[] row : list) {
                Long ssubjid = Long.valueOf(row[0].toString());
                Long patientid = Long.valueOf(row[1].toString());
                ids = new Long[] {ssubjid, patientid};
            }
        } catch (Exception e) {
            throw new PAException("Exception in getSsPatientKeys().", e);
        }
        return ids;
    }    


    private void createAccrualAccess(RegistryUser ru, Long ssId) throws PAException {
        studySiteAccrualAccessSrv.createStudySiteAccrualAccess(ru.getId(), ssId, AccrualAccessSourceCode.ACC_GENERATED);
    }

    private Long[] create(PatientStage ps, Long spId, Long userId, Long studySite) throws PAException {
        Long newId = updatePatientTable(ps, userId, null);
        updateStudySubjectTable(ps, userId, null, spId, newId, studySite);

        Session session = PaHibernateUtil.getCurrentSession();
        String sql = "INSERT INTO performed_activity(identifier, study_protocol_identifier, performed_activity_type," 
                + "date_last_created, date_last_updated, study_subject_identifier, registration_date, " 
                + "user_last_created_id) "
                + "VALUES (:psmId, :spId, 'PerformedSubjectMilestone', now(), now(), :ssId, :regDate, :userId)";
        SQLQuery qry = session.createSQLQuery(sql);
        qry.setLong("psmId", newId);
        qry.setLong("spId", spId);
        qry.setLong("ssId", newId);
        qry.setTimestamp("regDate", ps.getRegistrationDate());
        qry.setLong("userId", userId);
        qry.executeUpdate();
        return new Long[]{newId, newId};
    }

    private Long[] update(PatientStage ps, Long userId, Long[] ids, Long studySite) throws PAException {
        Long ssId = ids[0];
        if (ssId == null) {
            throw new PAException("Cannot update a subject accrual without an identifier set. Please use create().");
        }
        updateStudySubjectTable(ps, userId, ids, null, null, studySite);
        updatePatientTable(ps, userId, ids);
        String sql = "UPDATE performed_activity SET registration_date=:registrationDate, "
                   + "user_last_updated_id=:userId, date_last_updated=now() "
                   + "WHERE study_subject_identifier=:ssId ";
        SQLQuery qry = PaHibernateUtil.getCurrentSession().createSQLQuery(sql);
        qry.setTimestamp("registrationDate", ps.getRegistrationDate());
        qry.setLong("userId", userId);
        qry.setLong("ssId", ssId);
        qry.executeUpdate();
        return ids;
    }

    private Long updatePatientTable(PatientStage ps, Long userId, Long[] ids) throws PAException {  
        String sql = "";
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery queryObject = null;
        Long result;
        Country criteriaCountry = new Country();
        criteriaCountry.setAlpha2(ps.getCountryCode());
        List<Country> countryList = lookUpTableService.searchCountry(criteriaCountry);
        if (ids != null) {
            result = ids[1];
            sql = "UPDATE patient SET race_code=:race_code, sex_code=:sex_code, ethnic_code=:ethnic_code, "
                    + "birth_date=:birth_date, status_code='ACTIVE', date_last_updated=now(), "
                    + "country_identifier=:country_identifier, zip=:zip , user_last_updated_id=:user_id "
                    + "WHERE identifier= :identifier";
        } else {
            result = getNextId(session);
            sql = "INSERT INTO patient(identifier, race_code, sex_code, ethnic_code, birth_date, status_code, " 
                    + "date_last_created, date_last_updated, country_identifier, zip, user_last_created_id) " 
                    + "VALUES (:identifier, :race_code, :sex_code, :ethnic_code, :birth_date,'ACTIVE', "
                    + "now(), now(), :country_identifier, :zip, :user_id)";
        }
        queryObject = session.createSQLQuery(sql);
        queryObject.setParameter(IDENTIFIER, result);
        queryObject.setParameter("race_code", ps.getRaceCode());
        queryObject.setParameter("sex_code", ps.getSexCode());
        queryObject.setParameter("ethnic_code", ps.getEthnicCode());
        queryObject.setParameter("birth_date", ps.getBirthDate(), Hibernate.TIMESTAMP);
        queryObject.setParameter("country_identifier", countryList.get(0).getId());
        queryObject.setParameter("zip", ps.getZip());
        queryObject.setParameter("user_id", userId);
        queryObject.executeUpdate();
        return result;
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

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private Long updateStudySubjectTable(PatientStage ps, Long userId, Long[] ids, Long spId, 
            Long newPatientId, Long studySite) throws PAException {
        String sql = "";
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery queryObject = null;
        Long result;
        if (ids != null) {
            result = ids[0];
            sql = "UPDATE study_subject SET study_site_identifier= :study_site_identifier, " 
                + "payment_method_code=:pmCode, status_code= :status_code, date_last_updated=now(), "
                + "assigned_identifier= :assigned_identifier, user_last_updated_id= :user_id, " 
                + "disease_identifier=" + ps.getDiseaseCode() + ", " 
                + "site_disease_identifier=" + ps.getSiteDiseaseCode() + ", "
                + "registration_group_id= :registration_group_id, submission_type= :submission_type "
                + "WHERE identifier= :identifier";

            queryObject = session.createSQLQuery(sql);
        } else {
            result = newPatientId;
            sql = "INSERT INTO study_subject(identifier, patient_identifier, study_protocol_identifier, " 
                    + "study_site_identifier, disease_identifier, payment_method_code, status_code," 
                    + "date_last_created, date_last_updated, assigned_identifier, submission_type," 
                    + "user_last_created_id, registration_group_id, site_disease_identifier) "
                    + "VALUES (:identifier, :patient_identifier, :study_protocol_identifier, "
                    + ":study_site_identifier, " + ps.getDiseaseCode() + ", :pmCode, :status_code, "
                    + "now(), now(), :assigned_identifier, :submission_type," 
                    + ":user_id,  :registration_group_id, " + ps.getSiteDiseaseCode() + ")";
            queryObject = session.createSQLQuery(sql);
            queryObject.setParameter("patient_identifier", newPatientId);
            queryObject.setParameter("study_protocol_identifier", spId);
        }
        queryObject.setParameter(IDENTIFIER, result);
        queryObject.setParameter("registration_group_id", ps.getRegistrationGroupId());
        queryObject.setParameter("submission_type", AccrualSubmissionTypeCode.BATCH.getName());
        queryObject.setParameter("study_site_identifier", studySite);
        queryObject.setParameter("status_code", FunctionalRoleStatusCode.ACTIVE.getName());
        queryObject.setParameter("assigned_identifier", ps.getAssignedIdentifier());
        queryObject.setParameter("user_id", userId);
        queryObject.setParameter("pmCode", ps.getPaymentMethodCode());
        queryObject.executeUpdate();
        return result;
    }

    private void doUpdateToSubjectAccrual(Long studySiteIi, Integer count, User user)
            throws PAException {
        StudySiteSubjectAccrualCount ssAccCount = (StudySiteSubjectAccrualCount) PaHibernateUtil.getCurrentSession()
        .createCriteria(StudySiteSubjectAccrualCount.class)
        .add(Restrictions.eq("studySite.id", studySiteIi)).uniqueResult();
        if (ssAccCount == null) {
            StudySiteDTO ssDto = studySiteService.get(IiConverter.convertToIi(studySiteIi));
            StudySite ss = new StudySiteConverter().convertFromDtoToDomain(ssDto);
            ssAccCount = new StudySiteSubjectAccrualCount();
            ssAccCount.setStudySite(ss);
            ssAccCount.setStudyProtocol(ss.getStudyProtocol());
            ssAccCount.setDateLastCreated(new Date());
            ssAccCount.setUserLastCreated(user);
        } else {
            if (ObjectUtils.equals(ssAccCount.getAccrualCount(), count)) {
                return;
            }
        }
        ssAccCount.setAccrualCount(count);
        ssAccCount.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        ssAccCount.setDateLastUpdated(new Date());
        ssAccCount.setUserLastUpdated(user);
        PaHibernateUtil.getCurrentSession().saveOrUpdate(ssAccCount);
    }

    /**
     * @param partOrgSrv the partOrgSrv to set
     */
    public void setPartOrgSrv(ParticipatingOrgServiceLocal partOrgSrv) {
        this.partOrgSrv = partOrgSrv;
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }
    
    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param studySiteAccrualAccessSrv the studySiteAccrualAccessSrv to set
     */
    public void setStudySiteAccrualAccessSrv(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessSrv) {
        this.studySiteAccrualAccessSrv = studySiteAccrualAccessSrv;
    }

    /**
     * @param studyProtocolSrv the studyProtocolSrv to set
     */
    public void setStudyProtocolSrv(StudyProtocolServiceLocal studyProtocolSrv) {
        this.studyProtocolSrv = studyProtocolSrv;
    }

    /**
     * @param useTestSeq the useTestSeq to set
     */
    public void setUseTestSeq(boolean useTestSeq) {
        this.useTestSeq = useTestSeq;
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

}
