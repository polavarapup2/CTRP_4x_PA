/***
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialSessionUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;


/**
 *
 * @author Bala Nair
 * @author Harsha
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.InefficientStringBuffering",
    "PMD.CollapsibleIfStatements", "PMD.AvoidDeeplyNestedIfStmts" })
public class SubmitTrialAction extends AbstractBaseTrialAction implements Preparable {
    private static final long serialVersionUID = -7644860242308952142L;
    private static final Logger LOG = Logger.getLogger(SubmitTrialAction.class);
    private static final String REDIRECT_TO_SEARCH = "redirect_to_search";
    
    private RegulatoryInformationServiceLocal regulatoryInformationService;
    private StudyProtocolStageServiceLocal studyProtocolStageService;
    private TrialRegistrationServiceLocal trialRegistrationService;
    private final TrialUtil  trialUtil = new TrialUtil();
    
    private Long cbValue;
    private String page;
    private Long id;
    private String sum4FundingCatCode;
    private String currentUser;
    private String orgId;
    private InputStream ajaxResponseStream;
    private FamilyProgramCodeService familyProgramCodeService;
    private long familyId;
    
    
    /**
     * Default constructor.
     */
    public SubmitTrialAction() {
        setTrialAction("submit");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        currentUser = UsernameHolder.getUser();
        regulatoryInformationService = PaRegistry.getRegulatoryInformationService();
        studyProtocolStageService = PaRegistry.getStudyProtocolStageService();
        trialRegistrationService = PaRegistry.getTrialRegistrationService();
        AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService = 
                PaRegistry.getAccrualDiseaseTerminologyService();
        setAccrualDiseaseTerminologyEditable(true);
        setAccrualDiseaseTerminologyList(accrualDiseaseTerminologyService.getValidCodeSystems());
        if (getTrialDTO() != null) {
            getTrialDTO().setPrimaryPurposeAdditionalQualifierCode(PAUtil
                    .lookupPrimaryPurposeAdditionalQualifierCode(getTrialDTO().getPrimaryPurposeCode()));
        }
        
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
    }

    /**
     *
     * @return res
     */
    @Override
    public String execute() {
        if (StringUtils.isEmpty(sum4FundingCatCode)) {
            setTrialAction("");
            ServletActionContext.getRequest().setAttribute("failureMessage",
                                                           getText("error.register.summary4FundingSponsorType"));
            return REDIRECT_TO_SEARCH;
        }
        setTrialDTO(new TrialDTO());
        final TrialDTO trialDTO = getTrialDTO();
        trialDTO.setResponsiblePartyType("");
        trialDTO.setTrialType("Interventional");
        trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
        TrialSessionUtil.removeSessionAttributes();
        //trialUtil.populateRegulatoryList(trialDTO);
        // have to call the usa list. 
        trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
        trialDTO.setSummaryFourFundingCategoryCode(sum4FundingCatCode);
        setPageFrom("submitTrial");
        setInitialStatusHistory(new ArrayList<StatusDto>());
        return SUCCESS;
    }


    /**
     * create protocol.
     *
     * @return String
     */
    public String create() {        
        try {
            setTrialDTO((TrialDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
            final TrialDTO trialDTO = getTrialDTO();
            if (trialDTO == null) {
               return ERROR;
            }
            TrialUtil util = new TrialUtil();
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.YES);
            trialDTO.setStudySource(StudySourceCode.REGISTRY);
            
            StudyProtocolDTO studyProtocolDTO = util.convertToStudyProtocolDTO(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(currentUser));
           
            List<StudyOverallStatusDTO> statusHistory = util.convertStatusHistory(trialDTO);

            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            clearDocumentIdentifiers(documentDTOs);
            if (StringUtils.length(trialDTO.getLeadOrgTrialIdentifier()) > PAAttributeMaxLen.LEN_30) {
                addActionError("Lead Organization Trial Identifier  cannot be more than 30 characters");
                return ERROR;   
            }
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            OrganizationDTO sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            StudySiteDTO leadOrgSiteIdDTO = util.convertToleadOrgSiteIdDTO(trialDTO);

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));
           
            List<OrganizationDTO> summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
            
            ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTO();
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                partyDTO = util.convertToResponsiblePartyDTO(trialDTO);
            }
            
            
            
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos(), null);
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = util.getStudyRegAuth(null, trialDTO);
            
            //set program code text to null sometimes this is populated in case 
            //where user is saved trial as draft make sure this value is never saved
            studyProtocolDTO.setProgramCodeText(null);
            
            util.assignProgramCodes(trialDTO , studyProtocolDTO);

            Ii studyProtocolIi = trialRegistrationService
                    .createCompleteInterventionalStudyProtocol(
                            studyProtocolDTO, statusHistory,
                            studyIndldeDTOs, studyResourcingDTOs, documentDTOs,
                            leadOrgDTO, principalInvestigatorDTO,
                            sponsorOrgDTO, partyDTO, leadOrgSiteIdDTO,
                            studyIdentifierDTOs, summary4orgDTO,
                            summary4studyResourcingDTO, studyRegAuthDTO,
                            BlConverter.convertToBl(Boolean.FALSE));
             TrialSessionUtil.removeSessionAttributes();
             ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
             ServletActionContext.getRequest().getSession().setAttribute("protocolId", studyProtocolIi.getExtension());
             deleteSavedDraft();
        } catch (Exception e) {
            final TrialDTO trialDTO = getTrialDTO();
            TrialSessionUtil.addSessionAttributes(trialDTO);
            if (!RegistryUtil.setFailureMessage(e)) {
                addActionError("Error occurred. Please try again.");
            }
            LOG.error("Exception occured while submitting trial", e);
            // have to call the usa country list
            //trialUtil.populateRegulatoryList(trialDTO);
            trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
            setDocumentsInSession(trialDTO);
            return ERROR;
        }
        return REDIRECT_TO_SEARCH;
    }
   

    @SuppressWarnings("deprecation")
    private void deleteSavedDraft() throws PAException {
        if (StringUtils.isNotEmpty(getTrialDTO().getStudyProtocolId())) {
            studyProtocolStageService.delete(IiConverter.convertToIi(getTrialDTO().getStudyProtocolId()));
        }
    }

    private void clearDocumentIdentifiers(List<DocumentDTO> documentDTOs) {
        for (DocumentDTO dto : documentDTOs) {
           dto.setIdentifier(null);
        }
    }


    

    /**
     * validate the submit trial form elements.
     * @throws IOException on document errors
     */
    private void validateForm() throws IOException {
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = validator.validateTrial(getTrialDTO());
        addErrors(err);
        validateDocuments();
    }

    /**
     *
     * @return s
     */
    public String review() {
        final TrialDTO trialDTO = getTrialDTO();
        try {
            clearErrorsAndMessages();
            addFundingToTrialDto();
            trialDTO.setStatusHistory(getStatusHistoryFromSession());
            validateForm();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute("failureMessage", getText("error.fieldErrors"));
                TrialSessionUtil.addSessionAttributes(trialDTO);
                // have to call the usa country list
                //trialUtil.populateRegulatoryList(trialDTO);
                trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
                return ERROR;
            }
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            trialDTO.setDocDtos(getTrialDocuments());
            addIndIdesToTrialDto();
            addSecondaryIdsToTrialDto();
            String section801 = trialDTO.getSection801Indicator();
            if (section801 != null && section801.equalsIgnoreCase("YES") 
                 && StringUtils.isEmpty(trialDTO.getDelayedPostingIndicator())) {
                trialDTO.setDelayedPostingIndicator(CommonsConstant.NO);
            }
            trialUtil.setOversgtInfo(trialDTO);

        } catch (IOException e) {
            LOG.error(e);
            return ERROR;
        } catch (PAException e) {
            LOG.error(e);
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        TrialSessionUtil.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
        return "review";
    }
    
    /**
     * @return if Org belongs to any family
     * @throws Exception exception 
     */
    public String isOrgBelongToFamily() throws Exception {
        String result = "";
        List<OrgFamilyDTO> familyList = FamilyHelper.getByOrgId(new Long(orgId));
        if (familyList != null && !familyList.isEmpty()) {
            result = familyList.get(0).getId() + "";
        }
        ajaxResponseStream = new ByteArrayInputStream(result.getBytes());
        return "ajaxResponse";   
    }
    
    /**
     * Gets program codes for family 
     * @throws UnsupportedEncodingException Unsupported encoding exception
     * @return JSON String
     */
    
    public StreamResult fetchProgramCodesForFamily() throws UnsupportedEncodingException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        populateProgramCodes(arr);
        
        return new StreamResult(new ByteArrayInputStream(root.toString().getBytes()));
    }
    
    private void populateProgramCodes(JSONArray arr) {
        LOG.debug("populating program codes for [familyPOId : " + familyId + "]");
        FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(familyId);
        if (familyDTO != null) {
            
            List<ProgramCodeDTO> programCodesList = new ArrayList<ProgramCodeDTO>();
            programCodesList = familyDTO.getProgramCodesAsOrderedList();
             
            for (ProgramCodeDTO programCodeDTO : familyDTO.getProgramCodesAsOrderedList()) {
                JSONObject o = new JSONObject();
                if (programCodeDTO.isActive()) {
                o.put("id", programCodeDTO.getProgramCode());
                o.put("text", programCodeDTO.getProgramCode() + "-" + programCodeDTO.getProgramName());
                o.put("title", programCodeDTO.getProgramCode() + "-" + programCodeDTO.getProgramName());
                arr.put(o);
                }
             }
            
            if (!CollectionUtils.isEmpty(programCodesList)) {
                ServletActionContext.getRequest().
                getSession().removeAttribute(Constants.PROGRAM_CODES_LIST);
                ServletActionContext.getRequest().
                getSession().setAttribute(Constants.PROGRAM_CODES_LIST, programCodesList);
             
            }
                
                
            
        }

      }


    private void addSecondaryIdsToTrialDto() {
        List<Ii> otherIdsList = (List<Ii>) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
        if (otherIdsList != null) {
            getTrialDTO().setSecondaryIdentifierList(otherIdsList);
        }
    }


    private void addFundingToTrialDto() {
        List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.GRANT_LIST);
        if (grantList != null) {
            getTrialDTO().setFundingDtos(grantList);
        }
    }


    private void addIndIdesToTrialDto() {
        List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.INDIDE_LIST);
        if (indList != null) {
            getTrialDTO().setIndIdeDtos(indList);
        }
    }

    /**
     *
     * @return s
     */
    public String cancel() {
        TrialSessionUtil.removeSessionAttributes();
        setTrialAction("");
        setInitialStatusHistory(new ArrayList<StatusDto>());
        return REDIRECT_TO_SEARCH;
    }
    /**
     *
     * @return s
     */
    public String edit() {
        setTrialDTO((TrialDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE));
        TrialSessionUtil.addSessionAttributes(getTrialDTO());
        // have to call the usa country list
        //trialUtil.populateRegulatoryList(getTrialDTO());
        trialUtil.populateRegulatoryListStartWithUSA(getTrialDTO());
        setDocumentsInSession(getTrialDTO());
        return "edit";
    }

    /**
     * Gets the reg authorities list.
     * 
     * @return String success or failure
     */
    public String getTrialOversightAuthorityOrganizationNameList() {
        try {
            String countryId = ServletActionContext.getRequest().getParameter("countryid");
            if (getTrialDTO() == null) {
                setTrialDTO(new TrialDTO());
            }
            if (countryId != null && !("".equals(countryId))) {
                getTrialDTO().setRegIdAuthOrgList(regulatoryInformationService.getRegulatoryAuthorityNameId(Long
                    .valueOf(countryId)));
            } else {
                RegulatoryAuthOrgDTO defaultVal = new RegulatoryAuthOrgDTO();
                defaultVal.setName("-Select Country-");
                List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
                regIdAuthOrgList.add(defaultVal);
                getTrialDTO().setRegIdAuthOrgList(regIdAuthOrgList);
            }

        } catch (PAException e) {
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return success or fail
     */
    public String partialSave() {
        final TrialDTO trialDTO = getTrialDTO();
        try {
            trialDTO.setStatusHistory(getStatusHistoryFromSession());
            addSecondaryIdsToTrialDto();
            validateDocuments();          
            trialDTO.setDocDtos(getTrialDocuments());  
            
          //set program codes values in program codes text
            if (trialDTO.getProgramCodesList() != null 
                    && trialDTO.getProgramCodesList().size() > 0) {
                StringBuffer programCodesText = new StringBuffer();
                for (int i = 0; i < trialDTO.getProgramCodesList().size(); i++) {
                    if (i == 0) {
                        programCodesText.append(trialDTO.getProgramCodesList().get(i));
                    } else {
                        programCodesText.append(';');
                        programCodesText.append(trialDTO.getProgramCodesList().get(i));
                    }
                }
                trialDTO.setProgramCodeText(programCodesText.toString());
            }
            
            setTrialDTO((TrialDTO) trialUtil.saveDraft(trialDTO));
            ServletActionContext.getRequest().setAttribute("protocolId", trialDTO.getStudyProtocolId());
            ServletActionContext.getRequest().setAttribute("partialSubmission", "submit");
            ServletActionContext.getRequest().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
            
            
            
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            TrialSessionUtil.addSessionAttributes(trialDTO);
            // have to call the usa country list
            //trialUtil.populateRegulatoryList(trialDTO);
            trialUtil.populateRegulatoryListStartWithUSA(trialDTO);
            return ERROR;
        } catch (IOException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
        return "review";
    }
    
    /**
     *
     * @return s
     */
    public String completePartialSubmission() {
        TrialSessionUtil.removeSessionAttributes();
        setInitialStatusHistory(new ArrayList<StatusDto>());
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        setTrialDTO(new TrialDTO());
        try {
            setTrialDTO((TrialDTO) trialUtil.getTrialDTOForPartiallySumbissionById(pId));
            final TrialDTO trialDTO = getTrialDTO();
            setInitialStatusHistory(trialDTO.getStatusHistory());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    trialDTO.getIndIdeDtos());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    trialDTO.getFundingDtos());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.SECONDARY_IDENTIFIERS_LIST,
                    trialDTO.getSecondaryIdentifierList());
            ServletActionContext.getRequest().getSession().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
             
            
            setPageFrom("submitTrial");
            setDocumentsInSession(trialDTO);
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    /**
     * 
     * @return string
     */
    @SuppressWarnings("deprecation")
    public String deletePartialSubmission() {
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        try {
            studyProtocolStageService.delete(IiConverter.convertToIi(pId));
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
        }
        setTrialAction("deletePartialSubmission");
        return REDIRECT_TO_SEARCH;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
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

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceLocal regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param studyProtocolStageService the studyProtocolStageService to set
     */
    public void setStudyProtocolStageService(StudyProtocolStageServiceLocal studyProtocolStageService) {
        this.studyProtocolStageService = studyProtocolStageService;
    }

    /**
     * @param trialRegistrationService the trialRegistrationService to set
     */
    public void setTrialRegistrationService(TrialRegistrationServiceLocal trialRegistrationService) {
        this.trialRegistrationService = trialRegistrationService;
    }

    @Override
    public boolean isOpenSitesWarningRequired() {
        return false;
    }

    /**
     * @return orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }

    /**
     * @param ajaxResponseStream ajaxResponseStream
     */
    public void setAjaxResponseStream(InputStream ajaxResponseStream) {
        this.ajaxResponseStream = ajaxResponseStream;
    }

    /**
     * @return familyId
     */
    public long getFamilyId() {
        return familyId;
    }

    /**
     * @param familyId familyId
     */
    public void setFamilyId(long familyId) {
        this.familyId = familyId;
    }

    /**
     * @return familyProgramCodeService
     */
    public FamilyProgramCodeService getFamilyProgramCodeService() {
        return familyProgramCodeService;
    }

    /**
     * @param familyProgramCodeService familyProgramCodeService
     */
    public void setFamilyProgramCodeService(
            FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

}
