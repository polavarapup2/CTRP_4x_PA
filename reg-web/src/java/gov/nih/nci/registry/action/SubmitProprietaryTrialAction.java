/**
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialSessionUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class SubmitProprietaryTrialAction extends AbstractBaseProprietaryTrialAction implements Preparable {
    private static final String INPUT_NCT = "input_nct";
    /**
     *
     */
    private static final long serialVersionUID = 1L;    
    private static final Logger LOG = Logger.getLogger(SubmitProprietaryTrialAction.class);
    private String selectedTrialType = "no";   
    private String sum4FundingCatCode;
    private final TrialUtil  util = new TrialUtil();
    private String currentUser;
    private String nctID;
    
    private CTGovSyncServiceLocal ctGovSyncService;
    private StudyProtocolServiceLocal studyProtocolService;

    /**
     * Default constructor.
     */
    public SubmitProprietaryTrialAction() {
        setTrialAction("submit");
    }
    
    /**
     * @return String st
     */
    public String selectTypeOfTrial() {
        return SUCCESS;
    }

    /**
     * 
     * @return st
     */
    @Override
    public String execute() {
        TrialSessionUtil.removeSessionAttributes();

        if (StringUtils.isEmpty(getSum4FundingCatCode())) {
            setTrialAction("");
            ServletActionContext.getRequest()
                .setAttribute("failureMessage",
                              "Trial Submission Category is required to continue onto registration.");
            return "redirect_to_search";
        }
        setTrialDTO(new ProprietaryTrialDTO());
        final ProprietaryTrialDTO trialDTO = getTrialDTO();
        trialDTO.setPropritaryTrialIndicator(CommonsConstant.YES);
        trialDTO.setTrialType("Interventional");
        trialDTO.setSummaryFourFundingCategoryCode(getSum4FundingCatCode());
        trialDTO.setAccrualDiseaseCodeSystem("SDC");
        setPageFrom("proprietaryTrial");
        return SUCCESS;
    }
    
    /**
     * @return input_nct
     */
    public String inputNct() {
        return INPUT_NCT;
    }
    
    /**
     * @return st
     */
    public String searchByNct() {
        try {
            validateNctIDAndAbilityToImport(getNctID());
            if (!hasActionErrors()) {
                return "redirect_to_nct_import";
            }
        } catch (PAException e) {
            LOG.error(e, e);
            addActionError(e.getMessage());
        }
        return INPUT_NCT;
    }
    
    /**
     * @param nctIdToValidate
     * @throws PAException
     */
    private void validateNctIDAndAbilityToImport(final String nctIdToValidate) throws PAException {
        if (StringUtils.isBlank(nctIdToValidate)) {
            addActionError("Please provide an ClinicalTrials.gov Identifier value.");
        } else if (!StringUtils.isAlphanumericSpace(nctIdToValidate)) {
            addActionError("Provided ClinicalTrials.gov Identifer is invalid.");
        } else if (ctGovSyncService
                .getAdaptedCtGovStudyByNctId(nctIdToValidate) == null) {
            addActionError("A study with the given identifier is not found in ClinicalTrials.gov.");
        } else if (!studyProtocolService.getStudyProtocolsByNctId(// NOPMD
                nctIdToValidate).isEmpty()) {
            addActionError("A study with the given identifier already exists in CTRP."
                + " To find this trial in CTRP, go to the Search Trials page.");
        }
    }

    /**
     * 
     * @return st
     */
    public String review() {
        clearErrorsAndMessages();
        enforceBusinessRules();
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (hasFieldErrors()) {
            request.setAttribute("failureMessage",
                                                           "The form has errors and could not be submitted, "
                                                                   + "please check the fields highlighted below");
            return ERROR;
        }
        if (hasActionErrors()) {
            return ERROR;
        }
        final ProprietaryTrialDTO trialDTO = getTrialDTO();
        trialDTO.setDocDtos(getTrialDocuments());
        
        final HttpSession session = request.getSession();
        session.removeAttribute(Constants.INDIDE_LIST);
        session.removeAttribute(Constants.GRANT_LIST);
        session
            .removeAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.PARTICIPATING_SITES.getShortName());
        session.removeAttribute(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getShortName());
        session.removeAttribute(DocumentTypeCode.OTHER.getShortName());
        session.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
        return "review";
    }

    @SuppressWarnings("unchecked")
    private void enforceBusinessRules() {        
        ClassValidator<ProprietaryTrialDTO> validator =
                new ClassValidator<ProprietaryTrialDTO>(ProprietaryTrialDTO.class);
        final ProprietaryTrialDTO trialDTO = getTrialDTO();
        if (StringUtils.length(trialDTO
                .getLeadOrgTrialIdentifier()) > PAAttributeMaxLen.LEN_30) {
            addActionError("Lead Organization Trial Identifier  cannot be more than 30 characters");
        }
        for (InvalidValue invalidValue : validator.getInvalidValues(trialDTO)) {
            
            if (StringUtils.isNotEmpty(trialDTO.getNctIdentifier())) {
                if (!invalidValue.getPropertyName().equalsIgnoreCase("phaseCode")
                        && !invalidValue.getPropertyName().equalsIgnoreCase("primaryPurposeCode")) {
                    // if the nct Number is present ignore the phase code and primary purpose codes
                    addFieldError("trialDTO." + invalidValue.getPropertyName(), getText(invalidValue.getMessage()
                        .trim()));
                }
            } else {
                addFieldError("trialDTO." + invalidValue.getPropertyName(), getText(invalidValue.getMessage().trim()));
            }
        }
        
        checkSummary4Funding();
        
        Map<String, String> errMap = new HashMap<String, String>();
        try {
            errMap = validateProtocolDoc();
            new TrialValidator().validateNonInterventionalTrialDTO(trialDTO, errMap);
            addErrors(errMap);
            validateOtherDocUpdate();
        } catch (IOException e) {
            addActionError("There was an unexpected problem uploading your documents.");
        }        
        
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(trialDTO);
        StudySiteDTO studySiteDTO = getSubmittingStudySiteDTO();
        String errMsg = paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
        if (StringUtils.isNotEmpty(errMsg)) {
            addActionError(errMsg);
        }

    }

    /**
     * 
     * @return st
     */
    public String edit() {
        setTrialDTO((ProprietaryTrialDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
        setDocumentsInSession();
        return "edit";
    }

    /**
     * 
     * @return s
     */
    public String create() {
        setTrialDTO(
                (ProprietaryTrialDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
        final ProprietaryTrialDTO trialDTO = getTrialDTO();
        if (trialDTO == null) {
            return ERROR;
        }
        try {
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            trialDTO.setStudySource(StudySourceCode.REGISTRY);
            StudyProtocolDTO studyProtocolDTO = util.convertToStudyProtocolDTO(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(currentUser));
            StudySiteAccrualStatusDTO siteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(trialDTO);

            OrganizationDTO leadOrganizationDTO = util.convertToLeadOrgDTO(trialDTO);
            List<OrganizationDTO> summary4organizationDTO = util.convertToSummary4OrgDTO(trialDTO);
            
            StudySiteDTO leadOrganizationTrialIdentifierDTO = util.convertToleadOrgSiteIdDTO(trialDTO);

            StudySiteDTO nctIdentifierDTO = util.convertToNCTStudySiteDTO(trialDTO, null);
            StudySiteDTO siteDTO = getSubmittingStudySiteDTO();
            StudyResourcingDTO studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);

            PersonDTO siteInvestigatorDTO = new PersonDTO();
            siteInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO.getSitePiIdentifier()));
            OrganizationDTO studySiteOrgDTO = new OrganizationDTO();
            studySiteOrgDTO.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO
                .getSiteOrganizationIdentifier()));

            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            clearDocumentIdentifiers(documentDTOs);
            
            Ii studyProtocolIi =
                    PaRegistry.getTrialRegistrationService()
                        .createAbbreviatedInterventionalStudyProtocol(studyProtocolDTO, siteAccrualStatusDTO,
                                                                      documentDTOs, leadOrganizationDTO,
                                                                      siteInvestigatorDTO,
                                                                      leadOrganizationTrialIdentifierDTO,
                                                                      studySiteOrgDTO, siteDTO, nctIdentifierDTO,
                                                                      summary4organizationDTO, studyResourcingDTO,
                                                                      BlConverter.convertToBl(Boolean.FALSE));
            StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            TrialSessionUtil.removeSessionAttributes();
            ServletActionContext.getRequest().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
            ServletActionContext.getRequest().setAttribute("protocolId",
                                                           PAUtil.getAssignedIdentifier(protocolDTO).getExtension());
            if (StringUtils.isNotEmpty(trialDTO.getStudyProtocolId())) {
                PaRegistry.getStudyProtocolStageService()
                    .delete(IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
            }

        } catch (PAException e) {
            setDocumentsInSession();
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        } catch (Exception e) {
            LOG.error("Error creating trial", e);
            setDocumentsInSession();
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        return "review";
    }

    /**
     * @return
     */
    private StudySiteDTO getSubmittingStudySiteDTO() {
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(getTrialDTO().getLocalSiteIdentifier()));
        siteDTO.setProgramCodeText(StConverter.convertToSt(getTrialDTO().getSiteProgramCodeText()));
        if (StringUtils.isNotEmpty(getTrialDTO().getDateOpenedforAccrual())
                && StringUtils.isNotEmpty(getTrialDTO().getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(getTrialDTO().getDateOpenedforAccrual(),
                                                                              getTrialDTO().getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(getTrialDTO().getDateOpenedforAccrual())
                && StringUtils.isEmpty(getTrialDTO().getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(
                    getTrialDTO().getDateOpenedforAccrual(), null));
        }
        return siteDTO;
    }

    /**
     * 
     * @return s
     */
    public String cancel() {
        TrialSessionUtil.removeSessionAttributes();
        return "redirect_to_search";
    }

    private void setDocumentsInSession() {
        setDocumentsInSession(getTrialDTO());
    }
    

    private StudySiteAccrualStatusDTO convertToStudySiteAccrualStatusDTO(ProprietaryTrialDTO trialDto) {
        StudySiteAccrualStatusDTO isoDto = new StudySiteAccrualStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(trialDto.getSiteStatusCode())));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getSiteStatusDate())));
        return isoDto;
    }

    /**
     * @return the selectedTrialType
     */
    public String getSelectedTrialType() {
        return selectedTrialType;
    }

    /**
     * @param selectedTrialType the selectedTrialType to set
     */
    public void setSelectedTrialType(String selectedTrialType) {
        this.selectedTrialType = selectedTrialType;
    }

    /**
     * 
     * @return str
     */
    public String partialSave() {
        try {            
            validateDocuments(); // this will make sure docs are in the session. PO-4914.            
            getTrialDTO().setDocDtos(getTrialDocuments());            
            setTrialDTO((ProprietaryTrialDTO) util.saveDraft(getTrialDTO()));
            final ProprietaryTrialDTO trialDTO = getTrialDTO();                      
            ServletActionContext.getRequest().setAttribute("protocolId", trialDTO.getStudyProtocolId());
            ServletActionContext.getRequest().setAttribute("partialSubmission", "submit");
            ServletActionContext.getRequest().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        } catch (Exception e) {
            LOG.error("Error saving draft", e);
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        return "review";
    }

    /**
     * 
     * @return str
     */
    public String complete() {
        TrialSessionUtil.removeSessionAttributes();
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        setTrialDTO(new ProprietaryTrialDTO());
        try {
            setTrialDTO((ProprietaryTrialDTO) util.getTrialDTOForPartiallySumbissionById(pId));
            final ProprietaryTrialDTO trialDTO = getTrialDTO();
            trialDTO.setAccrualDiseaseCodeSystem("SDC");
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute(Constants.INDIDE_LIST, getTrialDTO().getIndIdeDtos());
            session.setAttribute(Constants.GRANT_LIST, getTrialDTO().getFundingDtos());
            session.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
            setPageFrom("proprietaryTrial");
            setDocumentsInSession(getTrialDTO());
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    /**
     * @param sum4FundingCatCode the sum4FundingCatCode to set
     */
    public void setSum4FundingCatCode(String sum4FundingCatCode) {
        this.sum4FundingCatCode = sum4FundingCatCode;
    }

    /**
     * @return the sum4FundingCatCode
     */
    public String getSum4FundingCatCode() {
        return sum4FundingCatCode;
    }
    
    private void clearDocumentIdentifiers(List<DocumentDTO> documentDTOs) {
        for (DocumentDTO dto : documentDTOs) {
           dto.setIdentifier(null);
        }
    }

    @Override
    public void prepare() {
        super.prepare();
        currentUser = UsernameHolder.getUser();    
        ctGovSyncService = PaRegistry.getCTGovSyncService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
    }

    /**
     * @return the nctID
     */
    public String getNctID() {
        return nctID;
    }

    /**
     * @param nctID the nctID to set
     */
    public void setNctID(String nctID) {
        this.nctID = nctID != null ? nctID.trim() : null;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
}
