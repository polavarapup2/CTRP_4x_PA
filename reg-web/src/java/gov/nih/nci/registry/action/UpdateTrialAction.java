package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.enums.CodedEnumHelper;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.AccessTrackingMap;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialSessionUtil;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.CreateIfNull;
import com.opensymphony.xwork2.util.Element;

/**
 * The Class UpdateTrialAction.
 *
 * @author Vrushali
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.TooManyFields" })
public class UpdateTrialAction extends ManageFileAction implements Preparable {

    private static final long serialVersionUID = -1295113563440080699L;

    private static final Logger LOG = Logger.getLogger(UpdateTrialAction.class);
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    
    private static final String STATUS_CHANGE_ERR_MSG = "You are attempting to change this status from: "
            + "<br>Old Status: %1s <br>Old Status Date: %2s <br><strong>Error</strong>: %3s";

    private StudyProtocolServiceLocal studyProtocolService;
    private StudyResourcingServiceLocal studyResourcingService;
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    private StudySiteServiceLocal studySiteService;
    private TrialRegistrationServiceLocal trialRegistrationService;
    private StatusTransitionService statusTransitionService;
    private final TrialUtil trialUtil = new TrialUtil();

    private TrialDTO trialDTO = new TrialDTO();
    private String trialAction;
    private String studyProtocolId;

    // for update
    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> collaborators = new ArrayList<PaOrganizationDTO>();

    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingAddDtos = new ArrayList<TrialFundingWebDTO>();

    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingDtos = new ArrayList<TrialFundingWebDTO>();

    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.registry.dto.TrialIndIdeDTO.class)
    private List<TrialIndIdeDTO> indIdeAddDtos = new ArrayList<TrialIndIdeDTO>();

    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.registry.dto.TrialIndIdeDTO.class)
    private List<TrialIndIdeDTO> indIdeUpdateDtos = new ArrayList<TrialIndIdeDTO>();

    @CreateIfNull(value = true)
    @Element(value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> participatingSitesList = new ArrayList<PaOrganizationDTO>();
    
    private List<TrialDocumentWebDTO> existingDocuments = new ArrayList<TrialDocumentWebDTO>();

    private String programcodenciselectedvalue;
    private String programcodenihselectedvalue;
    private PaOrganizationDTO paOrganizationDTO;
    private TrialFundingWebDTO trialFundingDTO;
    private TrialIndIdeDTO trialIndIdeDTO;
    private int indIdeUpdateDtosLen = 0;

    private String currentUser;
    
    //  PO-6093 raised an interesting issue. Before an update, trial goes through formal validation process
    // (see TrialValidator().validateTrial(trialDTO) below). This validation process sometimes (not often)
    // can produce keyed field errors that are inapplicable to the Trial Update operation in Registry, i.e.
    // those field errors will not be picked up by <s:fielderror> tags in updateTrial.jsp simply because such
    // fields are unavailable for update. As a result, the page comes back to the user saying "there are field
    // error below, please review", but as the user scrolls the page, she sees no field errors. A good solution
    // would be to summarize such field errors, which are inapplicable to the actual update, at the top of the page.
    // However, we would need to track which field errors ARE consumed by the JSP and which are 'swallowed'.
    // To avoid duplication of field keys throughout, this special Map will remember all keys that have been
    // requested and then we can find out which field errors have not been consumed by individual <s:fielderror>
    // tags.
    private final AccessTrackingMap<String, List<String>> fieldErrors = new AccessTrackingMap<String, List<String>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare()  {
        super.prepare();
        currentUser = UsernameHolder.getUser();        
        studyProtocolService = PaRegistry.getStudyProtocolService();
        studyResourcingService = PaRegistry.getStudyResourcingService();
        studySiteAccrualStatusService = PaRegistry.getStudySiteAccrualStatusService();
        studySiteService = PaRegistry.getStudySiteService();
        trialRegistrationService = PaRegistry.getTrialRegistrationService();
        statusTransitionService = PaRegistry.getStatusTransitionService();
        AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService = 
                PaRegistry.getAccrualDiseaseTerminologyService();
        setAccrualDiseaseTerminologyList(accrualDiseaseTerminologyService.getValidCodeSystems());
        if (studyProtocolId != null) {
            setAccrualDiseaseTerminologyEditable(accrualDiseaseTerminologyService.canChangeCodeSystem(
                    Long.valueOf(studyProtocolId)));
        } else {
            if (trialDTO != null && trialDTO.getStudyProtocolId() != null) {
                setAccrualDiseaseTerminologyEditable(accrualDiseaseTerminologyService.canChangeCodeSystem(
                        Long.valueOf(trialDTO.getStudyProtocolId())));
            }
        }
        if (trialDTO != null) {
            trialDTO.setPrimaryPurposeAdditionalQualifierCode(PAUtil
                    .lookupPrimaryPurposeAdditionalQualifierCode(trialDTO.getPrimaryPurposeCode()));
        }
        setFieldErrors(this.fieldErrors);
    }
    
    @Override
    public Map<String, List<String>> getFieldErrors() {        
        return this.fieldErrors;
    }

    /**
     * View the trial.
     *
     * @return res
     */
    public String view() {
        clearSession();
        try {
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolId));
            trialUtil.getTrialDTOFromDb(studyProtocolIi, trialDTO);
            synchActionWithDTO();
            TrialSessionUtil.addSessionAttributesForUpdate(trialDTO);
            setInitialStatusHistory(trialDTO.getStatusHistory());
            setIndIdeUpdateDtosLen(trialDTO.getIndIdeUpdateDtos().size());
            ServletActionContext.getRequest().getSession().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
            
            existingDocuments = trialUtil.getTrialDocuments(trialDTO);
            
            setPageFrom("updateTrial");
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 
     */
    private void clearSession() {
        TrialSessionUtil.removeSessionAttributes();
        clearGrantsAndIndsFromSession();
        setInitialStatusHistory(new ArrayList<StatusDto>());
    }

    private void synchActionWithDTO() {
        if (CollectionUtils.isNotEmpty(trialDTO.getCollaborators())) {
            setCollaborators(trialDTO.getCollaborators());
        }
        if (CollectionUtils.isNotEmpty(trialDTO.getParticipatingSites())) {
            setParticipatingSitesList(trialDTO.getParticipatingSites());
        }
        syncIndIdesToDTO();
        syncFundingToDTO();       
    }

    private void syncIndIdesToDTO() {
        if (CollectionUtils.isNotEmpty(trialDTO.getIndIdeUpdateDtos())) {
            setIndIdeUpdateDtos(trialDTO.getIndIdeUpdateDtos());
        }
        if (CollectionUtils.isNotEmpty(trialDTO.getIndIdeAddDtos())) {
            setIndIdeAddDtos(trialDTO.getIndIdeAddDtos());
        }
    }

    private void syncFundingToDTO() {
        if (CollectionUtils.isNotEmpty(trialDTO.getFundingDtos())) {
            setFundingDtos(trialDTO.getFundingDtos());
        }
        if (CollectionUtils.isNotEmpty(trialDTO.getFundingAddDtos())) {
            setFundingAddDtos(trialDTO.getFundingAddDtos());
        }
    }

    private void synchDTOWithAction() {
        trialDTO.setCollaborators(getCollaborators());
        trialDTO.setParticipatingSites(getParticipatingSitesList());
        trialDTO.setIndIdeUpdateDtos(getIndIdeUpdateDtos());
        trialDTO.setFundingDtos(getFundingDtos());
    }

    /**
     * Clears the session variables and redirect to search.
     *
     * @return s
     */
    public String cancel() {
        clearSession();
        return "redirect_to_search";
    }

    /**
     * Review update.
     *
     * @return s
     */
    @SuppressWarnings("unchecked")
    public String reviewUpdate() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        try {
            trialDTO.setStatusHistory(getStatusHistoryFromSession());
            
            

            // add the IndIde,FundingList
            List<TrialIndIdeDTO> indAddList = (List<TrialIndIdeDTO>) session.getAttribute(Constants.INDIDE_ADD_LIST);
            if (indAddList != null) {
                trialDTO.setIndIdeAddDtos(indAddList);
                setIndIdeAddDtos(indAddList);
            }
            List<TrialFundingWebDTO> grantAddList = (List<TrialFundingWebDTO>) session
                .getAttribute(Constants.GRANT_ADD_LIST);
            if (grantAddList != null) {
                trialDTO.setFundingAddDtos(grantAddList);
                setFundingAddDtos(grantAddList);
            }
            
            List<Ii> otherIdsList = (List<Ii>) session.getAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
            if (otherIdsList != null) {
                trialDTO.setSecondaryIdentifierAddList(otherIdsList);
            }
            synchDTOWithAction();
            
            String failureMessage = validateTrial();
            trialDTO.setDocDtos(getTrialDocuments());
            if (failureMessage != null) {
                ServletActionContext.getRequest().setAttribute("failureMessage", failureMessage);

                TrialSessionUtil.addSessionAttributes(trialDTO);
                //trialUtil.populateRegulatoryList(trialDTO);
                trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
                synchActionWithDTO();
                this.fieldErrors.clearTrackedKeys();
                return ERROR;
            }
            if (trialDTO.isXmlRequired()) {
                trialUtil.setOversgtInfo(trialDTO);
            }
            if (hasActionErrors()) {
                TrialSessionUtil.addSessionAttributes(trialDTO);
                synchActionWithDTO();
                //trialUtil.populateRegulatoryList(trialDTO);
                trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
                return ERROR;
            }
            
        } catch (IOException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            //trialUtil.populateRegulatoryList(trialDTO);
            trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            //trialUtil.populateRegulatoryList(trialDTO);
            trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
            return ERROR;
        }        
        TrialSessionUtil.removeSessionAttributes();
        session.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
        return "review";
    }

    /**
     * Validates the trial.
     * @return a failure message if necessary
     * @throws PAException If an error occurs
     * @throws IOException If an error occurs
     */
    String validateTrial() throws PAException, IOException {
        clearErrorsAndMessages();
        String failureMessage = null;
        final String message = "This trial cannot be updated at this time. "
                + "Please contact us at ncictro@mail.nih.gov for further assistance regarding this trial. "
                + "Please include the NCI Trial Identifier in your email.";
        if (!validateSummaryFourInfo()) {
            failureMessage = message;
        } else {
            enforceBusinessRules();
            if (hasFieldErrors()) {
                failureMessage = "The form has errors and could not be submitted, please check the "
                        + "fields highlighted below as well as any general trial errors, if displayed.";              
            }
        }
        return failureMessage;
    }

   
    
    /**
     * See above comment.
     * 
     * @return List<String>
     */
    public Collection<String> getFlattenedRemainingFieldErrors() {
        Collection<String> list = new LinkedHashSet<String>();
        for (List<String> fieldErrorList : ((AccessTrackingMap<String, List<String>>) getFieldErrors())
                .getValuesWhoseKeysNeverAccessed()) {
            list.addAll(fieldErrorList);
        }
        return list;
    }

    /**
     * Edit the trial.
     *
     * @return s
     * @throws PAException PAException
     */
    public String edit() throws PAException {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        setDocumentsInSession(trialDTO);
        synchActionWithDTO();
        //trialUtil.populateRegulatoryList(trialDTO);
        trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
        existingDocuments = trialUtil.getTrialDocuments(trialDTO);
        TrialSessionUtil.addSessionAttributes(trialDTO);
        return "edit";
    }

    /**
     * Update the trial.
     *
     * @return s
     */
    public String update() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        if (trialDTO == null) {
            synchActionWithDTO();
            return ERROR;
        }
        TrialUtil util = new TrialUtil();
        Ii updateId = null;
        try {
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier()));
            // get the studyProtocol DTO
            StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
            util.addSecondaryIdentifiers(spDTO, trialDTO);
            util.updateStudyProtcolDTO(spDTO, trialDTO);
            spDTO.setUserLastCreated(StConverter.convertToSt(currentUser));
            
            final List<StudyOverallStatusDTO> statusHistory = new ArrayList<StudyOverallStatusDTO>();
            statusHistory.addAll(util.convertStatusHistory(trialDTO));
            statusHistory
                    .addAll(util
                            .convertStatusHistory(getDeletedStatusHistoryFromSession()));
            
                  
            List<DocumentDTO> documentDTOs = util.convertToISODocument(trialDTO.getDocDtos(), studyProtocolIi);

            // indide updates and adds
            List<StudyIndldeDTO> studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
            // updated
            if (CollectionUtils.isNotEmpty(trialDTO.getIndIdeUpdateDtos())) {
                studyIndldeDTOList = util.convertISOINDIDEList(trialDTO.getIndIdeUpdateDtos(), studyProtocolIi);
            }
            // newly added
            if (CollectionUtils.isNotEmpty(trialDTO.getIndIdeAddDtos())) {
                List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeAddDtos(),
                                                                                 studyProtocolIi);
                studyIndldeDTOList.addAll(studyIndldeDTOs);
            }
            // funding updates and adds
            List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
            if (trialDTO.getFundingDtos() != null && trialDTO.getFundingDtos().size() > 0) {
                for (TrialFundingWebDTO webdto : trialDTO.getFundingDtos()) {
                    studyResourcingDTOs.add(convertToStudyResourcingDTO(webdto, studyProtocolIi));
                }
            }
            // newly added
            if (trialDTO.getFundingAddDtos() != null && trialDTO.getFundingAddDtos().size() > 0) {
                List<StudyResourcingDTO> studyResourcingAddDTOs = util.convertISOGrantsList(
                        trialDTO.getFundingAddDtos(), studyProtocolIi);
                studyResourcingDTOs.addAll(studyResourcingAddDTOs);
            }

            // ps update- send the participating sites list
            List<StudySiteAccrualStatusDTO> pssDTOList = getParticipatingSitesForUpdate(trialDTO
                    .getParticipatingSites());

            // PO-9498 - list of studysite dtos Retain Program code values from the database
            List<StudySiteDTO> prgCdUpdatedList = getStudySiteToUpdate(trialDTO.getParticipatingSites());

            updateId = studyProtocolIi;

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));  
          
            //this should never be populated as per new implementation
            spDTO.setProgramCodeText(null);
            
            List<ProgramCodeDTO> oldProgramCodesList = new ArrayList<ProgramCodeDTO>();
            oldProgramCodesList = spDTO.getProgramCodes();
            
            util.assignProgramCodes(trialDTO , spDTO);
            //check user is actually removed program codes
            if (trialDTO.getProgramCodesList() != null 
                && trialDTO.getProgramCodesList().size() == 0) {
                spDTO.setProgramCodes(new ArrayList<ProgramCodeDTO>());
            }
            
            //retain program codes from different family as it is
            util.assignAdditionalProgramCodes(oldProgramCodesList, spDTO);
            
            
          
            // call the service to invoke the update method
            trialRegistrationService.update(spDTO, statusHistory, studyIdentifierDTOs, null, 
                    studyResourcingDTOs, documentDTOs, null, null, null, null, null, 
                    null, null, pssDTOList, prgCdUpdatedList, BlConverter.convertToBl(Boolean.FALSE));
            
            TrialSessionUtil.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", updateId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", updateId);
            setInitialStatusHistory(new ArrayList<StatusDto>());
        } catch (PAException e) {
            if (!RegistryUtil.setFailureMessage(e)) {
                addActionError("Error occurred. Please try again.");
            }
            LOG.error("Exception occured while updating trial", e);
            TrialSessionUtil.addSessionAttributes(trialDTO);
            //trialUtil.populateRegulatoryList(trialDTO);
            trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
            synchActionWithDTO();
            ServletActionContext.getRequest().getSession().removeAttribute("secondaryIdentifiersList");
            trialDTO.setSecondaryIdentifierAddList(null);
            trialUtil.removeAssignedIdentifierFromSecondaryIds(trialDTO);
            setDocumentsInSession(trialDTO);
            return ERROR;
        }
        setTrialAction("update");
        clearGrantsAndIndsFromSession();
        return "redirect_to_search";
    }

    /**
     * 
     */
    private void clearGrantsAndIndsFromSession() {
        ServletActionContext.getRequest().getSession().removeAttribute("grantAddList");
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeAddList");
    }

    /**
     * validate the submit trial form elements.
     *
     * @throws PAException the PA exception
     * @throws IOException on document error
     */
    void enforceBusinessRules() throws PAException, IOException {
        trialDTO.setFundingAddDtos(getFundingAddDtos());
        trialDTO.setFundingDtos(getFundingDtos());       
        addErrors(new TrialValidator().validateTrial(trialDTO));
        validateStatusAndDate();
        validateCollaborators();
        validateParticipatingSite();
        validateGrantsInfo();
        validateIndIdeInfo();
        validateDocuments();
    }

    /**
     * @throws PAException the PA exception
     */
    protected void validateStatusAndDate() throws PAException {
        if (doStatusDatesRequireBusinessRuleValidation()) {
            findStatusDateBusinessRulesErrors();
        }
    }

    private boolean doStatusDatesRequireBusinessRuleValidation() throws PAException {
        return StringUtils.isNotEmpty(trialDTO.getStatusCode()) && RegistryUtil.isValidDate(trialDTO.getStatusDate())
                && RegistryUtil.isValidDate(trialDTO.getPrimaryCompletionDate())
                && RegistryUtil.isValidDate(trialDTO.getStartDate())
                && new TrialValidator().isTrialStatusOrDateChanged(trialDTO);
    }

    private void findStatusDateBusinessRulesErrors() throws PAException {
        Collection<String> errDate = new TrialValidator().enforceBusinessRulesForDates(trialDTO);
        if (!errDate.isEmpty()) {
            for (String msg : errDate) {
                addActionError(msg);
            }
        }
    }

    private void validateIndIdeInfo() {
        int ind = 0;
        if (CollectionUtils.isNotEmpty(getIndIdeUpdateDtos())) {
            for (TrialIndIdeDTO indide : getIndIdeUpdateDtos()) {
                addFieldErrorIfEmptyValue(indide.getGrantor(), "updindideGrantor" + ind, "Grantor should not be null");
                addFieldErrorIfEmptyValue(indide.getNumber(), "updindideNumber" + ind,
                        "IND/IDE Number should not be null");
                validateIndIndHolderType(ind, indide);
                validateIndIneExpandedAccessValue(ind, indide);
                ind++;
            }
        }
    }

    private void validateIndIndHolderType(int ind, TrialIndIdeDTO indide) {
        addFieldErrorIfEmptyValue(indide.getHolderType(), "updindideHolderType" + ind,
                "Ind/IDE Holder Type should not be null");
        if (StringUtils.isNotEmpty(indide.getHolderType())) {
            validateIndIneHolderTypeValues(ind, indide);
        }
    }

    private void validateIndIneExpandedAccessValue(int ind, TrialIndIdeDTO indide) {
        if (StringUtils.isNotEmpty(indide.getExpandedAccess())
                && indide.getExpandedAccess().equalsIgnoreCase("yes")) {
            addFieldErrorIfEmptyValue(indide.getExpandedAccessType(), "updindideExpandedStatus" + ind,
                    "Expanded Access Status should not be null");
        }
    }

    private void validateIndIneHolderTypeValues(int ind, TrialIndIdeDTO indide) {
        if (indide.getHolderType().equalsIgnoreCase("NIH")) {
            addFieldErrorIfEmptyValue(indide.getNihInstHolder(), "updindideNihInstHolder" + ind,
                    "NIH Institute holder should not be null");
        }
        if (indide.getHolderType().equalsIgnoreCase("NCI")) {
            addFieldErrorIfEmptyValue(indide.getNciDivProgHolder(), "updindideNciDivPrgHolder" + ind,
                    "NCI Division Program holder should not be null");
        }
    }

    private void validateGrantsInfo() {
        int ind = 0;
        if (CollectionUtils.isNotEmpty(getFundingDtos())) {
            for (TrialFundingWebDTO fm : getFundingDtos()) {
                addFieldErrorIfEmptyValue(fm.getFundingMechanismCode(), "updfundingMechanismCode" + ind,
                        "Funding Mechanism Code should not be null");
                addFieldErrorIfEmptyValue(fm.getNciDivisionProgramCode(), "updnciDivisionProgramCode" + ind,
                        "NCI Division Code should not be null");
                addFieldErrorIfEmptyValue(fm.getNihInstitutionCode(), "updnihInstitutionCode" + ind,
                        "NIH Institution Code  should not be null");
                addFieldErrorIfEmptyValue(fm.getSerialNumber(), "updserialNumber" + ind,
                        "Serial Number should not be null");
                ind++;
            }
        }
    }

    private void addFieldErrorIfEmptyValue(String value, String errorField, String msg) {
        if (StringUtils.isEmpty(value)) {
            addFieldError(errorField, msg);
        }
    }

    @SuppressWarnings("unchecked")
    private void validateParticipatingSite() throws PAException {
        List<PaOrganizationDTO> sessionPartList = (List<PaOrganizationDTO>) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_SITES_LIST);
        List<PaOrganizationDTO> currPartList = getParticipatingSitesList();
        if (CollectionUtils.isEmpty(currPartList)
                || CollectionUtils.isEmpty(sessionPartList)) {
            return;
        }
        for (int i = 0; i < currPartList.size(); i++)  {
            PaOrganizationDTO currps = currPartList.get(i);
            PaOrganizationDTO prevps = sessionPartList.get(i);
            
            addFieldErrorIfEmptyValue(currps.getRecruitmentStatus(), "participatingsite.recStatus" + i,
                    "Recruitment Status should not be null");
            addFieldErrorIfEmptyValue(currps.getRecruitmentStatusDate(), "participatingsite.recStatusDate" + i,
                    "Recruitment Status date should not be null");
            if (StringUtils.isEmpty(currps.getRecruitmentStatus()) 
                    || StringUtils.isEmpty(currps.getRecruitmentStatusDate())) {
                continue;
            }
            
            if (StringUtils.equals(currps.getRecruitmentStatus(), prevps.getRecruitmentStatus())
                    && StringUtils.equals(currps.getRecruitmentStatusDate(), prevps.getRecruitmentStatusDate())) {
                continue;
            }
            
            Date prevDt, currDt = null;
            
            try {
                prevDt = SDF.parse(prevps.getRecruitmentStatusDate());
                currDt = SDF.parse(currps.getRecruitmentStatusDate());
            } catch (ParseException e) {
                addFieldError("participatingsite.recStatusDate" + i, 
                        "Error parsing the participating site recruitment status dates, " + e.getMessage());
                continue;
            }
            
            if (currDt.before(prevDt)) {
                String errMsg = String.format(STATUS_CHANGE_ERR_MSG, 
                        new Object[] {prevps .getRecruitmentStatus(), prevps.getRecruitmentStatusDate(),
                        " New status date must be greater or equal to the most recent status date"});
                addFieldError("participatingsite.recStatus" + i, errMsg);
                continue;
            } else {
                List<StatusDto> statusDtos = statusTransitionService.validateStatusTransition(
                        AppName.REGISTRATION, TrialType.COMPLETE, TransitionFor.SITE_STATUS, 
                        CodedEnumHelper.getByClassAndCode(RecruitmentStatusCode.class, 
                                prevps.getRecruitmentStatus()).name(),
                        prevDt, 
                        CodedEnumHelper.getByClassAndCode(RecruitmentStatusCode.class, 
                                currps.getRecruitmentStatus()).name(),
                        currDt);
                if (statusDtos.get(0).hasErrorOfType(ErrorType.ERROR)) {
                    
                    String errMsg = String.format(STATUS_CHANGE_ERR_MSG, 
                            new Object[] {prevps .getRecruitmentStatus(), prevps.getRecruitmentStatusDate(), 
                            statusDtos.get(0).getConsolidatedErrorMessage()});
                    addFieldError("participatingsite.recStatus" + i, errMsg);
                }
            }
        }
    }

    private void validateCollaborators() {
        int ind = 0;
        if (CollectionUtils.isNotEmpty(getCollaborators())) {
            for (PaOrganizationDTO coll : getCollaborators()) {
                addFieldErrorIfEmptyValue(coll.getFunctionalRole(), "collaborator.functionalCode" + ind,
                        "Functional role should not be null");
                ind++;
            }
        }
    }

    /**
     * Convert to study resourcing dto.
     *
     * @param trialFundingWebDTO the trial funding web dto
     * @param studyProtocolIi the study protocol ii
     *
     * @return the study resourcing dto
     */
    private StudyResourcingDTO convertToStudyResourcingDTO(TrialFundingWebDTO trialFundingWebDTO, Ii studyProtocolIi)
            throws PAException {
        StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();
        studyResoureDTO = studyResourcingService.getStudyResourcingById(
                IiConverter.convertToIi(Long.parseLong(trialFundingWebDTO.getId())));
        studyResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(trialFundingWebDTO
                .getFundingMechanismCode()));
        studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(NciDivisionProgramCode
                .getByCode(trialFundingWebDTO.getNciDivisionProgramCode())));
        studyResoureDTO
                .setNihInstitutionCode(CdConverter.convertStringToCd(trialFundingWebDTO.getNihInstitutionCode()));
        studyResoureDTO.setSerialNumber(StConverter.convertToSt(trialFundingWebDTO.getSerialNumber()));
        studyResoureDTO.setFundingPercent(RealConverter.convertToReal(trialFundingWebDTO.getFundingPercent()));
        return studyResoureDTO;
    }

    /**
     * Gets the participating sites for update.
     *
     * @param ps the ps
     *
     * @return the participating sites for update
     *
     * @throws PAException the PA exception
     */
    private List<StudySiteAccrualStatusDTO> getParticipatingSitesForUpdate(List<PaOrganizationDTO> ps)
            throws PAException {
        List<StudySiteAccrualStatusDTO> ssaDTO = new ArrayList<StudySiteAccrualStatusDTO>();
        for (PaOrganizationDTO dto : ps) {
            StudySiteAccrualStatusDTO ssasOld = studySiteAccrualStatusService
                    .getCurrentStudySiteAccrualStatusByStudySite(IiConverter.convertToIi(dto.getId()));
            StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
            ssas.setStudySiteIi(ssasOld.getStudySiteIi());
            ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto.getRecruitmentStatus())));
            ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto.getRecruitmentStatusDate())));
            ssaDTO.add(ssas);
        }
        return ssaDTO;
    }

    /**
     * get the StudtsiteDtos
     * @param ps List PaOrganizationDTO
     * @return the ssDTO
     * @throws PAException the PA exception
     */
    protected List<StudySiteDTO> getStudySiteToUpdate(List<PaOrganizationDTO> ps) throws PAException {
        List<StudySiteDTO> ssDTO = new ArrayList<StudySiteDTO>();
        for (PaOrganizationDTO dto : ps) {
            StudySiteDTO sp = studySiteService.get(IiConverter.convertToIi(dto.getId()));
            ssDTO.add(sp);
        }
        return ssDTO;

    }

    /**
     * Validates the summary four info.
     * @return true if the summary four info is valid
     */
    boolean validateSummaryFourInfo() {
        if (StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())) {
            return false;
        }
        if (CollectionUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifiers())) {
            return false;
        }
        return true;
    }

    /**
     * Gets the trial dto.
     *
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * Sets the trial dto.
     *
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

    /**
     * Gets the trial action.
     *
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

    /**
     * Sets the trial action.
     *
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * Gets the study protocol id.
     *
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * Sets the study protocol id.
     *
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * Gets the collaborators.
     *
     * @return the collaborators
     */
    public List<PaOrganizationDTO> getCollaborators() {
        return collaborators;
    }

    /**
     * Sets the collaborators.
     *
     * @param collaborators the collaborators to set
     */
    public void setCollaborators(List<PaOrganizationDTO> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Gets the funding add dtos.
     *
     * @return the fundingAddDtos
     */
    public List<TrialFundingWebDTO> getFundingAddDtos() {
        return fundingAddDtos;
    }

    /**
     * Sets the funding add dtos.
     *
     * @param fundingAddDtos the fundingAddDtos to set
     */
    public void setFundingAddDtos(List<TrialFundingWebDTO> fundingAddDtos) {
        this.fundingAddDtos = fundingAddDtos;
    }

    /**
     * Gets the funding dtos.
     *
     * @return the fundingDtos
     */
    public List<TrialFundingWebDTO> getFundingDtos() {
        return fundingDtos;
    }

    /**
     * Sets the funding dtos.
     *
     * @param fundingDtos the fundingDtos to set
     */
    public void setFundingDtos(List<TrialFundingWebDTO> fundingDtos) {
        this.fundingDtos = fundingDtos;
    }

    /**
     * Gets the ind ide add dtos.
     *
     * @return the indIdeAddDtos
     */
    public List<TrialIndIdeDTO> getIndIdeAddDtos() {
        return indIdeAddDtos;
    }

    /**
     * Sets the ind ide add dtos.
     *
     * @param indIdeAddDtos the indIdeAddDtos to set
     */
    public void setIndIdeAddDtos(List<TrialIndIdeDTO> indIdeAddDtos) {
        this.indIdeAddDtos = indIdeAddDtos;
    }

    /**
     * Gets the ind ide update dtos.
     *
     * @return the indIdeUpdateDtos
     */
    public List<TrialIndIdeDTO> getIndIdeUpdateDtos() {
        return indIdeUpdateDtos;
    }

    /**
     * Sets the ind ide update dtos.
     *
     * @param indIdeUpdateDtos the indIdeUpdateDtos to set
     */
    public void setIndIdeUpdateDtos(List<TrialIndIdeDTO> indIdeUpdateDtos) {
        this.indIdeUpdateDtos = indIdeUpdateDtos;
    }

    /**
     * Gets the participating sites.
     *
     * @return the participatingSites
     */
    public List<PaOrganizationDTO> getParticipatingSitesList() {
        return participatingSitesList;
    }

    /**
     * Sets the participating sites.
     *
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSitesList(List<PaOrganizationDTO> participatingSites) {
        this.participatingSitesList = participatingSites;
    }

    /**
     * Gets the programcodenciselectedvalue.
     *
     * @return the programcodenciselectedvalue
     */
    public String getProgramcodenciselectedvalue() {
        return programcodenciselectedvalue;
    }

    /**
     * Sets the programcodenciselectedvalue.
     *
     * @param programcodenciselectedvalue the programcodenciselectedvalue to set
     */
    public void setProgramcodenciselectedvalue(String programcodenciselectedvalue) {
        this.programcodenciselectedvalue = programcodenciselectedvalue;
    }

    /**
     * Gets the programcodenihselectedvalue.
     *
     * @return the programcodenihselectedvalue
     */
    public String getProgramcodenihselectedvalue() {
        return programcodenihselectedvalue;
    }

    /**
     * Sets the programcodenihselectedvalue.
     *
     * @param programcodenihselectedvalue the programcodenihselectedvalue to set
     */
    public void setProgramcodenihselectedvalue(String programcodenihselectedvalue) {
        this.programcodenihselectedvalue = programcodenihselectedvalue;
    }

    /**
     * Gets the pa organization dto.
     *
     * @return the paOrganizationDTO
     */
    public PaOrganizationDTO getPaOrganizationDTO() {
        return paOrganizationDTO;
    }

    /**
     * Sets the pa organization dto.
     *
     * @param paOrganizationDTO the paOrganizationDTO to set
     */
    public void setPaOrganizationDTO(PaOrganizationDTO paOrganizationDTO) {
        this.paOrganizationDTO = paOrganizationDTO;
    }

    /**
     * Gets the trial funding dto.
     *
     * @return the trialFundingDTO
     */
    public TrialFundingWebDTO getTrialFundingDTO() {
        return trialFundingDTO;
    }

    /**
     * Sets the trial funding dto.
     *
     * @param trialFundingDTO the trialFundingDTO to set
     */
    public void setTrialFundingDTO(TrialFundingWebDTO trialFundingDTO) {
        this.trialFundingDTO = trialFundingDTO;
    }

    /**
     * Gets the trial ind ide dto.
     *
     * @return the trialIndIdeDTO
     */
    public TrialIndIdeDTO getTrialIndIdeDTO() {
        return trialIndIdeDTO;
    }

    /**
     * Sets the trial ind ide dto.
     *
     * @param trialIndIdeDTO the trialIndIdeDTO to set
     */
    public void setTrialIndIdeDTO(TrialIndIdeDTO trialIndIdeDTO) {
        this.trialIndIdeDTO = trialIndIdeDTO;
    }

    /**
     * @return the indIdeUpdateDtosLen
     */
    public int getIndIdeUpdateDtosLen() {
        return indIdeUpdateDtosLen;
    }

    /**
     * @param indIdeUpdateDtosLen the indIdeUpdateDtosLen to set
     */
    public void setIndIdeUpdateDtosLen(int indIdeUpdateDtosLen) {
        this.indIdeUpdateDtosLen = indIdeUpdateDtosLen;
    }

    
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param trialRegistrationService the trialRegistrationService to set
     */
    public void setTrialRegistrationService(TrialRegistrationServiceLocal trialRegistrationService) {
        this.trialRegistrationService = trialRegistrationService;
    }

    /**
     * @return the existingDocuments
     */
    public List<TrialDocumentWebDTO> getExistingDocuments() {
        return existingDocuments;
    }

    @Override
    public final TrialType getTrialTypeHandledByThisClass() {
        return TrialType.COMPLETE;
    }
    
    @Override
    public boolean isOpenSitesWarningRequired() {     
        return true;
    }
}
