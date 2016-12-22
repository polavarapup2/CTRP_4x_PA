package gov.nih.nci.pa.service.util;


import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class VerifyTrialDataNightlyServiceBeanLocal implements 
              VerifyTrialDataNightlyServiceLocal {
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private MailManagerServiceLocal mailManagerService;
    
    /** The n value for setting the verification due date. */
    private static final String N = "group1TrialsVerificationFrequency";
    /** The n1 value for calculating the verification due date. */
    private static final int N1 = 30;
    /** The n2 value for calculating the verification due date */
    private static final int N2 = 15;
    /** The n3 value for calculating the verification due date*/
    private static final int N3 = 7;
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(VerifyTrialDataNightlyServiceBeanLocal.class);
    static {
        LOG.setLevel(Level.INFO);
    }
   
    /**
     * gets the open trials 
     * @throws PAException PAException
     */ 
    @Override
    public void getOpenTrials() throws PAException {
        LOG.info("executing verify trial data job");
        StudyProtocolQueryCriteria queryCriteria = getCriteria();
        List<StudyProtocolQueryDTO> records = protocolQueryService.getStudyProtocolByCriteria(queryCriteria);
        List<StudyProtocolQueryDTO> recordList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO record : records) {
            RegistryUser user = getUser(record);
            if (user != null && user.getAffiliateOrg() != null 
                        && (!user.getAffiliateOrg().equalsIgnoreCase(PAConstants.NCI_ORG_NAME) 
                        && !user.getAffiliateOrg()
                        .equalsIgnoreCase(PAConstants.DCP_ORG_NAME))) {
                    recordList.add(record);
            }
        }
        List<StudyProtocolQueryDTO> finalRecordList = new ArrayList<StudyProtocolQueryDTO>();
        // Calculating the verificationDueDate for each Trial. The value of N is determined based on 3 conditions. 
        // Presently we are using only one as the other two are yet to be clarified by CTRO Team.
        for (StudyProtocolQueryDTO record : recordList) {
            if (record.getRecordVerificationDate() != null) {
                Date verificationDueDate = new Date();
                verificationDueDate = record.getRecordVerificationDate();
                Calendar cal = Calendar.getInstance();  
                cal.setTime(verificationDueDate);  
                cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + Integer
                        .parseInt(lookUpTableService.getPropertyValue(N))));  
                verificationDueDate = cal.getTime();  
                record.setVerificationDueDate(verificationDueDate);
                finalRecordList.add(record);
            }
        }
        // call group users + group the users
        Map<RegistryUser, List<StudyProtocolQueryDTO>> emailNCIUserList = groupUsers(finalRecordList);
        // process the verify due date for the trial owners 
        Map<RegistryUser, List<StudyProtocolQueryDTO>> finalemailList = getNearingDueDate(emailNCIUserList); 
        mailManagerService.sendVerifyDataEmail(finalemailList);
        // process the verify due date to notify CTRO
        List<StudyProtocolQueryDTO> ctroemailList = getCTRONearingDueDate(finalRecordList);
        mailManagerService.sendCTROVerifyDataEmail(ctroemailList);
        LOG.info("Success verify trial data job...........");
    }
    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        List<String> statusList = new ArrayList<String>();
        statusList.add(StudyStatusCode.IN_REVIEW.getCode());
        statusList.add(StudyStatusCode.ACTIVE.getCode());
        statusList.add(StudyStatusCode.APPROVED.getCode());
        statusList.add(StudyStatusCode.ENROLLING_BY_INVITATION.getCode());
        statusList.add(StudyStatusCode.CLOSED_TO_ACCRUAL.getCode());
        statusList.add(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode());
        statusList.add(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode());
        queryCriteria.setStudyStatusCodeList(statusList);
        queryCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        queryCriteria.setCtepDcpCategory("exclude");
        queryCriteria.setStudyProtocolType(StudyTypeCode.INTERVENTIONAL.getCode());
        List<String> documentWorkflowStatusCodes = new ArrayList<String>();
        documentWorkflowStatusCodes.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode());
        documentWorkflowStatusCodes.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode());
        queryCriteria.setDocumentWorkflowStatusCodes(documentWorkflowStatusCodes);
        return queryCriteria;
    }
    /**
     *  group the records based on the trial owners, site admins and not CTRO STAFF
     * @param records recordsf
     * @return map map
     * @throws PAException error
     */
    
    protected Map<RegistryUser, List<StudyProtocolQueryDTO>> groupUsers(List<StudyProtocolQueryDTO> records) 
        throws PAException {   
        Map<RegistryUser, List<StudyProtocolQueryDTO>> map = getRegUserToProtocolListMap();
        for (StudyProtocolQueryDTO record : records) {
            Long spId = record.getStudyProtocolId();
            Set<RegistryUser> trialOwners = registryUserService.getAllTrialOwners(spId);
            for (RegistryUser user : trialOwners) { 
                if ((record.getLastCreated() != null && user.getCsmUser() != null 
                        && user.getAffiliatedOrgUserType() != null)
                        && (user.getCsmUser().getLoginName().equals(record.getLastCreated().getUserLastCreated())
                        || user.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)) 
                        && !user.getCsmUser().getLoginName().equals("ctro_staff")) {
                    createUserToProtocolsMap(record, user, map); 
                }                    
            }
        }
     return map;
    }
    
    
    private RegistryUser getUser(StudyProtocolQueryDTO record) throws PAException {
        String user = record.getLastCreated().getUserLastCreated();
        return registryUserService.getUser(user);
    }
    /**
     * 
     * @param map map
     * @return map map
     * @throws PAException error
     */
    protected Map<RegistryUser, List<StudyProtocolQueryDTO>> getNearingDueDate(
            Map<RegistryUser, List<StudyProtocolQueryDTO>> map) throws PAException {
        Map<RegistryUser, List<StudyProtocolQueryDTO>> internalMap 
        = getRegUserToProtocolListMap();
        for (Entry<RegistryUser, List<StudyProtocolQueryDTO>> entry : map.entrySet()) {
            RegistryUser user = entry.getKey(); 
            List<StudyProtocolQueryDTO> list = entry.getValue();
            for (StudyProtocolQueryDTO dto : list) {
                Date verifyDate1 = getDueDate(N1, dto.getVerificationDueDate()); 
                Date verifyDate2 = getDueDate(N2, dto.getVerificationDueDate());
                if (org.apache.commons.lang.time.DateUtils.isSameDay(verifyDate1, new Date()) 
                     || 
                     org.apache.commons.lang.time.DateUtils.isSameDay(verifyDate2, new Date())) {
                    createUserToProtocolsMap(dto, user, internalMap);
                } 
            }
        }
        return internalMap;
    }
   
    /**
     * This method will ensure uniqueness of keys (RegistryUsers) in the map.
     * @return
     */
    private Map<RegistryUser, List<StudyProtocolQueryDTO>> getRegUserToProtocolListMap() {
        return new TreeMap<RegistryUser, List<StudyProtocolQueryDTO>>(
                new Comparator<RegistryUser>() {
                    @Override
                    public int compare(RegistryUser o1, RegistryUser o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
    }

    /**
     * 
     * @param dtoList dtoList
     * @return List list
     * @throws PAException error
     */
    protected List<StudyProtocolQueryDTO> getCTRONearingDueDate(
            List<StudyProtocolQueryDTO> dtoList) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO dto : dtoList) {
            Date verifyDate1 = getDueDate(N3, dto.getVerificationDueDate()); 
            if (org.apache.commons.lang.time.DateUtils.isSameDay(verifyDate1, new Date())) {
                list.add(dto);
            }
        }
        return list;
    }
        
    private void createUserToProtocolsMap(
            StudyProtocolQueryDTO dto, 
            RegistryUser user, 
            Map<RegistryUser, List<StudyProtocolQueryDTO>> map) {
        List<StudyProtocolQueryDTO> internalList = new ArrayList<StudyProtocolQueryDTO>();
        if (map.containsKey(user)) {
            internalList = map.get(user);
            if (internalList != null) {
                internalList.add(dto);
            }
            
        } else {
            internalList.add(dto);
        }
        map.put(user, internalList);
    }
   
    private Date getDueDate(int n, Date oldDate) {
         return org.apache.commons.lang.time.DateUtils.addDays(oldDate, -n);
    }
    /**
     * @param registryUserService
     *            the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }
    /**
     * 
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }


    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }
    /**
     * 
     * @param mailManagerService mailManagerService
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }
        

}
