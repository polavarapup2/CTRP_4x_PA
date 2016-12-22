package gov.nih.nci.registry.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialDataVerificationServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialVerificationDataWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class TrialDataVerificationAction extends ActionSupport implements
        Preparable, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS_MSG = "success";
    private static final Set<DocumentWorkflowStatusCode> ABSTRACTED_CODES =
        EnumSet.of(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                   DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    private static final String VERIFICATIONTEXT = "Manual Verification Entered";
    private Long studyProtocolId;
    private String currentUser;
    private RegistryUserServiceLocal registryUserService;
    private StudyProtocolServiceLocal studyProtocolService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyInboxServiceLocal studyInboxService;
    private TrialDataVerificationServiceLocal trialDataVerificationService;
    private TrialUtil trialUtils = new TrialUtil();
    private List<TrialVerificationDataWebDTO> webDTOList = new ArrayList<TrialVerificationDataWebDTO>();
    private TrialVerificationDataDTO newlyCreatedDTO;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        currentUser = UsernameHolder.getUser();
        registryUserService = PaRegistry.getRegistryUserService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        studyInboxService = PaRegistry.getStudyInboxService();
        trialDataVerificationService = PaRegistry.getTrialDataVerificationService();
    }

    /**
     * load initial view.
     * 
     * @return String the action result.
     * @throws PAException
     *             the pa exception.
     * 
     */
    public String view() throws PAException {
        try {
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            StudyProtocolDTO protocolDTO = loadTrial(studyProtocolIi);   
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO); 
            List<StudyProtocolQueryDTO> list = 
                  protocolQueryService.getActiveInactiveStudyProtocolsById(studyProtocolId);
            for (StudyProtocolQueryDTO dto : list) {
                DocumentWorkflowStatusCode dwf = dto.getDocumentWorkflowStatusCode();
                if (ABSTRACTED_CODES.contains(dwf)) {
                    TrialVerificationDataWebDTO trialWebDTO = new TrialVerificationDataWebDTO();
                    trialWebDTO.setStudyProtocolId(dto.getStudyProtocolId().toString());
                    trialWebDTO.setVerificationMethod(dwf.getCode());
                    trialWebDTO.setUserLastUpdated("CTRO Staff");
                    if (dto.getDocumentWorkflowStatusDate() != null) {
                        trialWebDTO.setUpdatedDate(dto.getDocumentWorkflowStatusDate().toString());
                    }                
                    webDTOList.add(trialWebDTO);
                }
            }

            addUpdateSubmittedTrials(studyProtocolIi);
            List<TrialVerificationDataDTO> trialList = trialDataVerificationService
            .getDataByStudyProtocolId(studyProtocolId);
            for (TrialVerificationDataDTO dto : trialList) {
                TrialVerificationDataWebDTO trialWebDTO = new TrialVerificationDataWebDTO();
                trialWebDTO.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
                trialWebDTO.setVerificationMethod("Manual Verification Entered");
                trialWebDTO.setUserLastUpdated(getRegisterUserFullName(dto));
                trialWebDTO.setUpdatedDate(TsConverter.convertToTimestamp(dto.getDateLastUpdated()).toString());
                webDTOList.add(trialWebDTO);
            }
            if (CollectionUtils.isNotEmpty(webDTOList)) {
                Collections.sort(webDTOList, new Comparator<TrialVerificationDataWebDTO>() {
                    public int compare(TrialVerificationDataWebDTO o1, TrialVerificationDataWebDTO o2) {
                        return StringUtils.defaultString(o2.getUpdatedDate()).compareTo(
                                StringUtils.defaultString(o1.getUpdatedDate()));
                    }
                });              
            }
            return SUCCESS_MSG;
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } catch (NullifiedRoleException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    private String getRegisterUserFullName(TrialVerificationDataDTO dto) throws PAException {
        RegistryUser registryUser = registryUserService.getUser(StConverter.convertToString(dto.getUserLastUpdated()));
        return registryUser != null ? registryUser.getFullName() : " ";
    }

    private void addUpdateSubmittedTrials(Ii studyProtocolIi) throws PAException {
        Set<RegistryUser> trialOwners = registryUserService.getAllTrialOwners(studyProtocolId);
        List<StudyInboxDTO> updateList = studyInboxService.getAllTrialUpdates(studyProtocolIi);
        for (StudyInboxDTO studyInboxDTO : updateList) {
            for (RegistryUser user : trialOwners) {
                if (studyInboxDTO.getUserLastCreated() != null 
                  && user.getCsmUser() != null      
                  && user.getCsmUser().getLoginName()
                  .equals(studyInboxDTO.getUserLastCreated().getValue())) {
                    TrialVerificationDataWebDTO trialWebDTO = new TrialVerificationDataWebDTO();
                    trialWebDTO.setStudyProtocolId(IiConverter.convertToString(studyProtocolIi));
                    trialWebDTO.setVerificationMethod("Update Submitted");
                    trialWebDTO.setUserLastUpdated(user.getFullName());
                    trialWebDTO.setUpdatedDate(TsConverter.convertToTimestamp(
                            studyInboxDTO.getInboxDateRange().getLow()).toString());
                    webDTOList.add(trialWebDTO);
                }
            }
        }
    }
    private StudyProtocolDTO loadTrial(Ii studyProtocolIi)
            throws PAException, NullifiedRoleException {
        StudyProtocolDTO protocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (!ISOUtil.isBlNull(protocolDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(protocolDTO.getProprietaryTrialIndicator())) { 
            loadPropTrial(studyProtocolIi);
        } else {    
            loadNonPropTrial(studyProtocolIi);
        }
        return protocolDTO;
    }
    
    private void loadPropTrial(Ii studyProtocolIi) throws PAException, NullifiedRoleException {
        ProprietaryTrialDTO trialDTO = new ProprietaryTrialDTO();
        trialUtils.getProprietaryTrialDTOFromDb(studyProtocolIi, trialDTO);        
        ServletActionContext.getRequest().setAttribute("leadOrganizationName", trialDTO.getLeadOrganizationName());
        ServletActionContext.getRequest().setAttribute("leadOrgTrialIdentifier", trialDTO.getLeadOrgTrialIdentifier());
        ServletActionContext.getRequest().setAttribute("nctIdentifier", trialDTO.getNctIdentifier());
        ServletActionContext.getRequest().setAttribute("assignedIdentifier", trialDTO.getAssignedIdentifier());
    }
    
    private void loadNonPropTrial(Ii studyProtocolIi) throws PAException, NullifiedRoleException {
        TrialDTO trialDTO = new TrialDTO();
        trialUtils.getTrialDTOFromDb(studyProtocolIi, trialDTO);   
        final HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);   
    }
    /**
     * 
     * @return success
     * @throws PAException PAException
     */
    public String save() throws PAException {
        Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
        TrialVerificationDataDTO trialDTO = new TrialVerificationDataDTO();
        trialDTO.setDateLastUpdated(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        trialDTO.setVerificationMethod(StConverter.convertToSt(VERIFICATIONTEXT));
        trialDTO.setUserLastUpdated(StConverter.convertToSt(currentUser));
        trialDTO.setStudyProtocolIdentifier(studyProtocolIi);
        newlyCreatedDTO = trialDataVerificationService.create(trialDTO);
        studyProtocolService.updateRecordVerificationDate(studyProtocolId);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Record was saved successfully");
        return view();
    }
    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId
     *            studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    @Override
    public void setServletRequest(HttpServletRequest arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * @param registryUserService
     *            the registryUserService to set
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }
    
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
    
    
    /**
     * 
     * @param studyInboxService studyInboxService
     */
    public void setStudyInboxService(StudyInboxServiceLocal studyInboxService) {
        this.studyInboxService = studyInboxService;
    }

    /**
     * @return the trialUtils
     */
    public TrialUtil getTrialUtils() {
        return trialUtils;
    }

    /**
     * @param trialUtils the trialUtils to set
     */
    public void setTrialUtils(TrialUtil trialUtils) {
        this.trialUtils = trialUtils;
    }
    /**
     * 
     * @return webDTOList webDTOList
     */
    public List<TrialVerificationDataWebDTO> getWebDTOList() {
        return webDTOList;
    }
    /**
     * 
     * @param webDTOList webDTOList
     */
    public void setWebDTOList(List<TrialVerificationDataWebDTO> webDTOList) {
        this.webDTOList = webDTOList;
    }
    /**
     * 
     * @param trialDataVerificationService trialDataVerificationService
     */
    public void setTrialDataVerificationService(
            TrialDataVerificationServiceLocal trialDataVerificationService) {
        this.trialDataVerificationService = trialDataVerificationService;
    }
    /**
     * 
     * @return newlyCreatedDTO newlyCreatedDTO
     */
    public TrialVerificationDataDTO getNewlyCreatedDTO() {
        return newlyCreatedDTO;
    }
    /**
     * 
     * @param newlyCreatedDTO newlyCreatedDTO
     */
    public void setNewlyCreatedDTO(TrialVerificationDataDTO newlyCreatedDTO) {
        this.newlyCreatedDTO = newlyCreatedDTO;
    }

    /**
     * 
     * @param protocolQueryService protocolQueryService
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }
    
}
