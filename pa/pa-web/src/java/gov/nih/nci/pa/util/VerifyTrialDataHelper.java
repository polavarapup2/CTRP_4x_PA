package gov.nih.nci.pa.util;


import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class VerifyTrialDataHelper {
    
    private RegistryUserServiceLocal registryUserService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private LookUpTableServiceRemote lookUpTableService;
    private MailManagerServiceLocal mailManagerService;
    
    /** The n value for setting the verification due date. */
    private static final String N = "N_value";
    /** The n1 value for calculating the verification due date. */
    private static final int N1 = 30;
    /** The n2 value for calculating the verification due date */
    private static final int N2 = 15;
    /** The n3 value for calculating the verification due date*/
    private static final int N3 = 7;
    
   
    /**
     * 
     * @throws PAException PAException
     */ 
    public void getOpenTrials() throws PAException {
        registryUserService = PaRegistry.getRegistryUserService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        lookUpTableService = PaRegistry.getLookUpTableService();
        mailManagerService = PaRegistry.getMailManagerService();
        StudyProtocolQueryCriteria queryCriteria = getCriteria();
        List<StudyProtocolQueryDTO> records = protocolQueryService.getStudyProtocolByCriteria(queryCriteria);
        List<StudyProtocolQueryDTO> recordList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO record : records) {
            RegistryUser userInfo = getUser(record);
            if (userInfo.getAffiliateOrg() != null 
                        && (!userInfo.getAffiliateOrg().equalsIgnoreCase(PAConstants.NCI_ORG_NAME) 
                        && !userInfo.getAffiliateOrg()
                        .equalsIgnoreCase(PAConstants.DCP_ORG_NAME))) {
                    recordList.add(record);
            }
        }
        List<StudyProtocolQueryDTO> finalRecordList = new ArrayList<StudyProtocolQueryDTO>();
        // Calculating the verificationDueDate for each Trial. The value of N is determined based on 3 conditions. 
        // Presently we are using only one as the other two are yet to be clarified by CTRO Team. 
        Date verificationDueDate = new Date();
        for (StudyProtocolQueryDTO record : recordList) {
            if (record.getRecordVerificationDate() != null) {
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
    
    // group the records based on the trial owners, site admins and not CTRO STAFF
    private Map<RegistryUser, List<StudyProtocolQueryDTO>> groupUsers(List<StudyProtocolQueryDTO> records) 
        throws PAException {   
        Map<RegistryUser, List<StudyProtocolQueryDTO>> map = new HashMap<RegistryUser, List<StudyProtocolQueryDTO>>();
        for (StudyProtocolQueryDTO record : records) {
            Long spId = record.getStudyProtocolId();
            Set<RegistryUser> trialOwners = registryUserService.getAllTrialOwners(spId);
            for (RegistryUser user : trialOwners) { 
                if ((record.getLastCreated() != null && user.getCsmUser() != null 
                        && user.getAffiliatedOrgUserType() != null)
                        && (user.getCsmUser().getLoginName().equals(record.getLastCreated().getUserLastCreated())
                        || user.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)) 
                        && !user.getCsmUser().getLoginName().equals("ctro_staff")) {
                        getDto(record, user, map); 
                }                    
            }
        }
     return map;
    }
    
    
    private RegistryUser getUser(StudyProtocolQueryDTO record) throws PAException {
        String user = record.getLastCreated().getUserLastCreated();
        return registryUserService.getUser(user);
    }
    
    private Map<RegistryUser, List<StudyProtocolQueryDTO>> getNearingDueDate(
            Map<RegistryUser, List<StudyProtocolQueryDTO>> map) throws PAException {
        Map<RegistryUser, List<StudyProtocolQueryDTO>> internalMap 
        = new HashMap<RegistryUser, List<StudyProtocolQueryDTO>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        for (Entry<RegistryUser, List<StudyProtocolQueryDTO>> entry : map.entrySet()) {
            RegistryUser user = new RegistryUser(); 
            user = entry.getKey();
            List<StudyProtocolQueryDTO> list = entry.getValue();
            for (StudyProtocolQueryDTO dto : list) {
                Date verifyDate1 = getDueDate(N1, dto.getVerificationDueDate()); 
                String formatDate1 = sdf.format(verifyDate1);
                Date verifyDate2 = getDueDate(N2, dto.getVerificationDueDate());
                String formatDate2 = sdf.format(verifyDate2);
                if (formatDate1.equals(sdf.format(new Date()))) {
                    internalMap = getDto(dto, user, internalMap);
                } else if (formatDate2.equals(sdf.format(new Date()))) {
                    internalMap = getDto(dto, user, internalMap);
                } 
            }
        }
        return internalMap;
    }
    
    private List<StudyProtocolQueryDTO> getCTRONearingDueDate(
            List<StudyProtocolQueryDTO> dtoList) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        for (StudyProtocolQueryDTO dto : dtoList) {
            Date verifyDate1 = getDueDate(N3, dto.getVerificationDueDate()); 
            String formatDate1 = sdf.format(verifyDate1);
            if (formatDate1.equals(sdf.format(new Date()))) {
                list.add(dto);
            }
        }
        return list;
    }
        
    private Map<RegistryUser, List<StudyProtocolQueryDTO>>getDto(
            StudyProtocolQueryDTO dto, 
            RegistryUser user, 
            Map<RegistryUser, List<StudyProtocolQueryDTO>> map) {
        List<StudyProtocolQueryDTO> internalDTO = new ArrayList<StudyProtocolQueryDTO>();
        if (map.containsKey(user)) {
            internalDTO = map.get(user);
            if (internalDTO != null) {
                internalDTO.add(dto);
            }
            
        } else {
            internalDTO.add(dto);
        }
        map.put(user, internalDTO);
        
        return map;
    }
   
    private Date getDueDate(int n, Date oldDate) {
        Calendar cal = Calendar.getInstance();  
        cal.setTime(oldDate);  
        cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) - n));
        return cal.getTime();
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
