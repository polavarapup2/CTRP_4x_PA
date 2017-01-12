/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RejectionReasonCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
* @author Naveen AMiruddin
*
*/
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.TooManyFields" })
public class TrialValidationAction extends AbstractGeneralTrialDesignAction implements Preparable {

    private static final long serialVersionUID = -6587531774808791496L;

    private static final String EDIT = "edit";
    private static final String UNDEFINED = "undefined";
    private static final String REJECT_OPERATION = "Reject";
    private static final int MAXIMUM_CHAR = 200;

    private LookUpTableServiceRemote lookUpTableService;
    private MailManagerServiceLocal mailManagerService;
    private OrganizationalContactCorrelationServiceRemote organizationalContactCorrelationService;
    private OrganizationEntityServiceRemote organizationEntityService;
    private PersonEntityServiceRemote personEntityService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private TrialRegistrationServiceLocal trialRegistrationService;
    private RegistryUserService registryUserService;
    private FamilyProgramCodeService familyProgramCodeService;

    private CorrelationUtilsRemote correlationUtils = new CorrelationUtils();
   
    private OrganizationDTO selectedLeadOrg;
    private List<PaPersonDTO> persons = new ArrayList<PaPersonDTO>();
    private List<Country> countryList = new ArrayList<Country>();
    private List<ProgramCodeDTO> programCodeList = new ArrayList<ProgramCodeDTO>();
    private long studyProtocolIdentifier;

    private List<Long> programCodeIds;
    private boolean cancerTrial;

    private boolean pgLoadComplete = false;


    private static final Logger LOG = Logger.getLogger(TrialValidationAction.class);

    private TrialHelper trialHelper = new TrialHelper();
    private static final String DISPLAY_SUMMARY4FUNDING_SPONSOR = "display_summary4funding_sponsor";
  

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        lookUpTableService = PaRegistry.getLookUpTableService();
        mailManagerService = PaRegistry.getMailManagerService();
        organizationalContactCorrelationService = PoRegistry.getOrganizationalContactCorrelationService();
        organizationEntityService = PoRegistry.getOrganizationEntityService();
        personEntityService = PoRegistry.getPersonEntityService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        trialRegistrationService = PaRegistry.getTrialRegistrationService();
        registryUserService = PaRegistry.getRegistryUserService();
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        if (gtdDTO != null) {
            gtdDTO.setPrimaryPurposeAdditionalQualifierCode(PAUtil.lookupPrimaryPurposeAdditionalQualifierCode(gtdDTO
                .getPrimaryPurposeCode()));
        }
    }

    /**
     * @return res
     */
    public String query() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
            TrialHelper helper = new TrialHelper();
            gtdDTO = helper.getTrialDTO(studyProtocolIi, "validation");
            loadProgramCodes();
            syncProgramCodes();
            session.setAttribute(Constants.OTHER_IDENTIFIERS_LIST, gtdDTO.getOtherIdentifiers());
            session.setAttribute("nctIdentifier", gtdDTO.getNctIdentifier());
            session.setAttribute("nciIdentifier", gtdDTO.getAssignedIdentifier());
            session.setAttribute("summary4Sponsors", gtdDTO.getSummaryFourOrgIdentifiers());
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        populateOtherIdentifiers();
        return EDIT;
    }

    /**
     *
     * @return String
     * @throws PAException  PAException
     */
    public String update() throws PAException {
        final HttpServletRequest req = ServletActionContext.getRequest();
        loadProgramCodes();
        enforceBusinessRules("");
        if (hasFieldErrors()) {
            req.setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Update failed; please see errors below");
            return EDIT;
        }
        try {
            save(null);
        } catch (Exception e) {
            LOG.error(e, e);
            req.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        populateOtherIdentifiers();
        return EDIT;
    }

    /**
     *
     * @return String
     * @throws PAException PAException
     */
    @SuppressWarnings("deprecation")
    public String accept() throws PAException {
        final HttpServletRequest req = ServletActionContext.getRequest();
        loadProgramCodes();
        enforceBusinessRules("");
        // check if submission number is greater than 1 then it is amend
        if (gtdDTO.getSubmissionNumber() > 1 && StringUtils.isEmpty(gtdDTO.getAmendmentReasonCode())) {
            addFieldError("gtdDTO.amendmentReasonCode", "Amendment Reason Code is Required.");
        }
        if (hasFieldErrors()) {
            req.setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Update failed; please see errors below");
            return EDIT;
        }
        try {
            save("accept");
            // send mail only if the trial is Amended
            if (gtdDTO.getSubmissionNumber() > 1) {
                // send mail
                mailManagerService.sendAmendAcceptEmail(IiConverter.convertToIi(gtdDTO.getStudyProtocolId()));
            } else {
                mailManagerService.sendAcceptEmail(IiConverter.convertToIi(gtdDTO.getStudyProtocolId()));
            }
        } catch (Exception e) {
            req.setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        populateOtherIdentifiers();
        return EDIT;
    }

    /**
     *
     * @return String
     * @throws PAException PAException
     */
    public String reject() throws PAException {
        final HttpServletRequest req = ServletActionContext.getRequest();
        loadProgramCodes();
        enforceBusinessRules(REJECT_OPERATION);
        if (hasFieldErrors()) {
            req.setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Reject failed; please see errors below");
            return EDIT;
        }
        try {
            save(null);
        } catch (Exception e) {
            req.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        req.getSession().setAttribute("submissionNumber", gtdDTO.getSubmissionNumber());
        return "rejectReason";
    }

    /**
     * @return String
     */
    public String rejectReason() {        
        validateRejectionData();
        if (hasFieldErrors()) {
            return "rejectReason";
        }
        final HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String submissionNo = "submissionNumber";
        try {
            Integer intSubNo = (Integer) session.getAttribute(submissionNo);
            Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
            // if trial is amend then hard delete
            if (intSubNo > 1) {
                StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                        IiConverter.convertToLong(studyProtocolIi));
                trialRegistrationService.reject(studyProtocolIi, StConverter.convertToSt(gtdDTO.getCommentText()),
                                                CdConverter.convertToCd(RejectionReasonCode.getByCode(gtdDTO
                                                    .getRejectionReasonCode())), null);
                // send mail                                
                String rejectionMessage = StringUtils.isNotBlank(gtdDTO.getCommentText()) ? gtdDTO
                        .getRejectionReasonCode() + " - " + gtdDTO.getCommentText() : gtdDTO.getRejectionReasonCode();
                mailManagerService.sendAmendRejectEmail(spDTO, rejectionMessage);
                session.removeAttribute(submissionNo);
                session.removeAttribute(Constants.TRIAL_SUMMARY);
                session.removeAttribute(Constants.STUDY_PROTOCOL_II);
                session.setAttribute(Constants.SUCCESS_MESSAGE, "Amendment has been rejected");
                studyProtocolIdentifier = IiConverter.convertToLong(studyProtocolIi);
                return "protocol_view";
            }
            createMilestones(MilestoneCode.SUBMISSION_REJECTED);
            mailManagerService.sendRejectionEmail(studyProtocolIi);
            refreshStudyProtocol(studyProtocolIi);
        } catch (Exception e) {
            request.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        query();
        request.setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Rejected");
        session.removeAttribute(submissionNo);
        populateOtherIdentifiers();
        return EDIT;
    }
    
    /**
     * Refresh the study protocol in the Session.
     * @param studyProtocolIi The study protocol Ii
     * @throws PAException exception
     * @return The studyProtocolQueryDTO
     */
    StudyProtocolQueryDTO refreshStudyProtocol(Ii studyProtocolIi) throws PAException {
        Long spIi = IiConverter.convertToLong(studyProtocolIi);
        StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(spIi);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
        return studyProtocolQueryDTO;
    }

    private void validateRejectionData() {
        if (StringUtils.isEmpty(gtdDTO.getRejectionReasonCode())) {
            addFieldError("gtdDTO.getRejectionReasonCode", getText("Rejection Reason Code must be selected"));    
        }        
    }

    /**
     * Will load the program codes from database
     *
     * @throws PAException - up on error
     */
    protected void loadProgramCodes() throws PAException {
        //do not reload in current request processing flow (as save internally calls query)
        if (pgLoadComplete) {
            return;
        }

        HashMap<Long, ProgramCodeDTO> programCodeIndex = new HashMap<Long, ProgramCodeDTO>();

        //find the families of the lead organization
        List<OrgFamilyDTO> ofList = FamilyHelper.getByOrgId(Long.parseLong(gtdDTO.getLeadOrganizationIdentifier()));
        for (OrgFamilyDTO of : ofList) {
            FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(of.getId());
            if (familyDTO == null) {
                continue;
            }
            for (ProgramCodeDTO pgc : familyDTO.getProgramCodes()) {
                if (pgc.isActive()) {
                    programCodeIndex.put(pgc.getId(), pgc);
                }
            }
        }

        //also add the program codes accumulated on the study from other sites
        for (ProgramCodeDTO pgc : gtdDTO.getProgramCodes()) {
            programCodeIndex.put(pgc.getId(), pgc);
        }

        setCancerTrial(!programCodeIndex.isEmpty());
        programCodeList.addAll(programCodeIndex.values());
        Collections.sort(programCodeList, new Comparator<ProgramCodeDTO>() {
            @Override
            public int compare(ProgramCodeDTO o1, ProgramCodeDTO o2) {
                return o1.getProgramCode().compareTo(o2.getProgramCode());
            }
        });


        pgLoadComplete = true;
    }

    /**
     * Will sync the selected program code ids
     */
    protected void syncProgramCodes() {
        programCodeIds = new ArrayList<Long>();
        for (ProgramCodeDTO pgc : gtdDTO.getProgramCodes()) {
            programCodeIds.add(pgc.getId());
        }
    }

    /**
     * Will bind the program codes
     */
    protected void bindProgramCodes() {
        gtdDTO.getProgramCodes().clear();
        if (CollectionUtils.isEmpty(programCodeIds)) {
            return;
        }

        for (Long pgcId : programCodeIds) {
            for (ProgramCodeDTO pgc : programCodeList) {
                if (pgc.getId().equals(pgcId)) {
                    gtdDTO.getProgramCodes().add(pgc);
                }
            }
        }
    }

    /**
     * Creates the milestones.
     *
     * @param msc the msc
     * @throws PAException e
     */
    private void createMilestones(MilestoneCode msc) throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        paServiceUtil.createMilestone(studyProtocolIi, msc, StConverter.convertToSt(gtdDTO.getCommentText()),
                                      CdConverter.convertToCd(RejectionReasonCode.getByCode(gtdDTO
                                          .getRejectionReasonCode())));

        StudyProtocolQueryDTO studyProtocolQueryDTO =
                protocolQueryService.getTrialSummaryByStudyProtocolId(Long.valueOf(studyProtocolIi.getExtension()));

        // put an entry in the session and store StudyProtocolQueryDTO
        session.setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
    }

    private void save(String operation) throws PAException, NullifiedEntityException, NullifiedRoleException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);       
        bindProgramCodes();
        trialHelper.saveTrial(studyProtocolIi, gtdDTO, "Validation");
        if (StringUtils.equalsIgnoreCase(operation, "accept")) {
            createMilestones(MilestoneCode.SUBMISSION_ACCEPTED);
            if (trialHelper.shouldRssOwnTrial(studyProtocolIi)) {
                final Long rssUserId = getRssUserID();
                if (rssUserId == null) {
                    ServletActionContext.getRequest().setAttribute(
                            Constants.FAILURE_MESSAGE,
                            "Unable to find ctep-rss user and assign as owner");
                } else {
                    registryUserService.assignOwnership(rssUserId,
                            Long.valueOf(studyProtocolIi.getExtension()));
                }
            }
        }
        // put an entry in the session and store StudyProtocolQueryDTO
        if (StringUtils.equalsIgnoreCase(operation, "accept")) {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Accepted");
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        }
        StudyProtocolQueryDTO studyProtocolQueryDTO = refreshStudyProtocol(studyProtocolIi);
        studyProtocolQueryDTO.setOfficialTitle(StringUtils.abbreviate(studyProtocolQueryDTO.getOfficialTitle(),
                                                                      PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
        if (studyProtocolQueryDTO.getPiId() != null) {
            Person piPersonInfo =
                    correlationUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(studyProtocolQueryDTO
                            .getPiId()));
            session.setAttribute(Constants.PI_PO_ID, piPersonInfo.getIdentifier());
        }
                
        query();
    }

    private void addErrors(String fieldValue, String fieldName, String errMsg) {
        if (StringUtils.isEmpty(fieldValue)) {
            addFieldError(fieldName, getText(errMsg));
        }
    }

    private void enforceBusinessRules(String operation) {        
        addErrors(gtdDTO.getOfficialTitle(), "gtdDTO.OfficialTitle", "OfficialTitle must be Entered");
        if (StringUtils.equalsIgnoreCase(REJECT_OPERATION, operation)) {
            if (!BooleanUtils.toBoolean(gtdDTO.getProprietarytrialindicator())) {
                validatePhasePurpose();
            }
        } else {
            validatePhasePurpose();
        }
        
        if ((isNotProprietary() && gtdDTO.isCtGovXmlRequired())
                || (!isNotProprietary() && StringUtils.isNotEmpty(gtdDTO
                        .getSponsorIdentifier()))) {
            validateCtGovXmlRequiredFields();
        }
        
        validateResponsibleParty();        
       
        
    }
    

    private void validatePhasePurpose() {
        addErrors(gtdDTO.getPrimaryPurposeCode(), "gtdDTO.primaryPurposeCode", "error.primary");
        addErrors(gtdDTO.getPhaseCode(), "gtdDTO.phaseCode", "error.phase");
        if (PAUtil.isPrimaryPurposeOtherCodeReq(gtdDTO.getPrimaryPurposeCode(),
                                                gtdDTO.getPrimaryPurposeAdditionalQualifierCode())) {
            addFieldError("gtdDTO.primaryPurposeAdditionalQualifierCode",
                          getText("Primary Purpose Additional Code must be entered"));
        }
        if (PAUtil.isPrimaryPurposeOtherTextReq(gtdDTO.getPrimaryPurposeCode(),
                                                gtdDTO.getPrimaryPurposeAdditionalQualifierCode(),
                                                gtdDTO.getPrimaryPurposeOtherText())) {
            addFieldError("gtdDTO.primaryPurposeOtherText",
                          getText("If Primary Purpose is \"Other\", description must be entered"));
        }
        if (StringUtils.length(gtdDTO.getPrimaryPurposeOtherText()) > MAXIMUM_CHAR) {
            addFieldError("gtdDTO.primaryPurposeOtherText", getText("error.spType.other.maximumChar"));
        }
    }

    /**
     *
     * @return result
     */
    public String displayLeadOrganization() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return "display_org";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedLeadOrg = organizationEntityService.search(criteria, limit).get(0);
            gtdDTO.setLeadOrganizationName(selectedLeadOrg.getName().getPart().get(0).getValue());
            gtdDTO.setLeadOrganizationIdentifier(selectedLeadOrg.getIdentifier().getExtension());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "display_lead_org";
    }

    /**
     *
     * @return result
     */
    public String displayLeadPrincipalInvestigator() {
        PersonDTO selectedLeadPrincipalInvestigator;
        String persId = ServletActionContext.getRequest().getParameter("persId");
        if (UNDEFINED.equalsIgnoreCase(persId)) {
            return "display_lead_prinicipal_inv";
        }
        try {
            selectedLeadPrincipalInvestigator =
                    personEntityService.getPerson(EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setPiIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PaPersonDTO personDTO =
                    PADomainUtils.convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setPiName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "display_lead_prinicipal_inv";
    }

    /**
     *
     * @return result
     */
    public String displaySelectedSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO selectedSponsor = null;
        OrganizationDTO criteria = new OrganizationDTO();
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return "display_selected_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedSponsor = organizationEntityService.search(criteria, limit).get(0);
            gtdDTO.setSponsorIdentifier(selectedSponsor.getIdentifier().getExtension());
            gtdDTO.setSponsorName(selectedSponsor.getName().getPart().get(0).getValue());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "display_selected_sponsor";
    }

    /**
     *
     * @return result
     */
    @SuppressWarnings("unchecked")
    public String displaySummary4FundingSponsor() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = (List<SummaryFourSponsorsWebDTO>) 
                session.getAttribute("summary4Sponsors");
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        OrganizationDTO selectedSummary4Sponsor = null;
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return DISPLAY_SUMMARY4FUNDING_SPONSOR;
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedSummary4Sponsor = organizationEntityService.search(criteria, limit).get(0);
            SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
            summarySp.setRowId(UUID.randomUUID().toString());
            summarySp.setOrgId(selectedSummary4Sponsor.getIdentifier().getExtension());
            summarySp.setOrgName(selectedSummary4Sponsor.getName().getPart().get(0).getValue());
            if (summary4SponsorsList == null) {
                summary4SponsorsList = new ArrayList<SummaryFourSponsorsWebDTO>();
            } 
            if (summary4SponsorsList.contains(summarySp)) {
                addFieldError("summary4FundingSponsor", 
                                              "Selected Sponsor already exists for this trial");
            } else if (!summary4SponsorsList.contains(summarySp)) {
                summary4SponsorsList.add(summarySp);
            } 
            gtdDTO.getSummaryFourOrgIdentifiers().addAll(summary4SponsorsList);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return DISPLAY_SUMMARY4FUNDING_SPONSOR;
    }
    
    /**
     * @return string
     */
    @SuppressWarnings("unchecked")
    public String deleteSummaryFourOrg() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = (List<SummaryFourSponsorsWebDTO>) 
                session.getAttribute("summary4Sponsors");
        String uuId = ServletActionContext.getRequest().getParameter("uuid");
        for (int i = summary4SponsorsList.size() - 1; i >= 0; i--) {
            SummaryFourSponsorsWebDTO webDto = summary4SponsorsList.get(i);
            if (webDto.getRowId().equals(uuId)) {
                summary4SponsorsList.remove(i);
            }
        }
        gtdDTO.getSummaryFourOrgIdentifiers().addAll(summary4SponsorsList);
        return DISPLAY_SUMMARY4FUNDING_SPONSOR;
    }

    /**
     *
     * @return result
     */
    public String displayCentralContact() {
        PersonDTO centralContact = null;
        String persId = ServletActionContext.getRequest().getParameter("persId");
        try {
            centralContact = personEntityService.getPerson(EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(centralContact);
            gtdDTO.setCentralContactIdentifier(centralContact.getIdentifier().getExtension());
            gtdDTO.setCentralContactName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "central_contact";
    }

    /**
     *
     * @return res
     */
    public String getOrganizationContacts() {
        String orgContactIdentifier = ServletActionContext.getRequest().getParameter("orgContactIdentifier");
        Ii contactIi = IiConverter.convertToPoOrganizationIi(orgContactIdentifier);
        OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
        contactDTO.setScoperIdentifier(contactIi);
        try {
            getCountriesList();
            List<OrganizationalContactDTO> list = organizationalContactCorrelationService.search(contactDTO);
            for (OrganizationalContactDTO organizationalContactDTO : list) {
                try {
                    PersonDTO resultDTO = personEntityService.getPerson(organizationalContactDTO.getPlayerIdentifier());
                    persons.add(PADomainUtils.convertToPaPersonDTO(resultDTO));
                } catch (NullifiedEntityException e) {
                    ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
                    LOG.error("Exception occured while getting organization contact : " + e);
                    return "display_org_contacts";
                }
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            LOG.error("Exception occured while getting organization contact : " + e);
        }
        return "display_org_contacts";
    }

    /**
     *
     * @return res
     */
    public String createOrganizationContacts() {
        String persId = null;
        try {
            // String orgId = ServletActionContext.getRequest().getParameter("orgId");
            persId = ServletActionContext.getRequest().getParameter("persId");
            PersonDTO selectedLeadPrincipalInvestigator =
                    personEntityService.getPerson(EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setResponsiblePersonIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setResponsiblePersonName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (NullifiedEntityException e) {
            LOG.error("got Nullified exception from PO for person Id " + persId);
        }
        return "display_responsible_contact";
    }

    @SuppressWarnings("unchecked")
    private void getCountriesList() throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        countryList = (List<Country>) session.getAttribute("countrylist");
        if (countryList == null) {
            countryList = lookUpTableService.getCountries();
            session.setAttribute("countrylist", countryList);
        }
    }

    private void populateOtherIdentifiers() {
        ServletActionContext.getRequest().getSession()
            .setAttribute(Constants.OTHER_IDENTIFIERS_LIST, gtdDTO.getOtherIdentifiers());
    }

    /**
     *
     * @return gtdDTO
     */
    public GeneralTrialDesignWebDTO getGtdDTO() {
        return gtdDTO;
    }

    /**
     *
     * @param gtdDTO gtdDTO
     */
    public void setGtdDTO(GeneralTrialDesignWebDTO gtdDTO) {
        this.gtdDTO = gtdDTO;
    }

    /**
     *
     * @return selectedLeadOrg
     */
    public OrganizationDTO getSelectedLeadOrg() {
        return selectedLeadOrg;
    }

    /**
     *
     * @param selectedLeadOrg selectedLeadOrg
     */
    public void setSelectedLeadOrg(OrganizationDTO selectedLeadOrg) {
        this.selectedLeadOrg = selectedLeadOrg;
    }

    /**
     * @return the persons
     */
    public List<PaPersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PaPersonDTO> persons) {
        this.persons = persons;
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
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
     * @param organizationalContactCorrelationService the organizationalContactCorrelationService to set
     */
    public void setOrganizationalContactCorrelationService(
            OrganizationalContactCorrelationServiceRemote organizationalContactCorrelationService) {
        this.organizationalContactCorrelationService = organizationalContactCorrelationService;
    }

    /**
     * @param organizationEntityService the organizationEntityService to set
     */
    public void setOrganizationEntityService(OrganizationEntityServiceRemote organizationEntityService) {
        this.organizationEntityService = organizationEntityService;
    }

    /**
     * @param personEntityService the personEntityService to set
     */
    public void setPersonEntityService(PersonEntityServiceRemote personEntityService) {
        this.personEntityService = personEntityService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param trialRegistrationService the trialRegistrationService to set
     */
    public void setTrialRegistrationService(TrialRegistrationServiceLocal trialRegistrationService) {
        this.trialRegistrationService = trialRegistrationService;
    }

    /**
     * Will set the family programcode service
     * @param familyProgramCodeService  the service class
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

    private Long getRssUserID() {
        try {
            RegistryUser regUser = registryUserService
                    .getUser(lookUpTableService
                            .getPropertyValue("cteprss.user"));
            return regUser != null ? regUser.getId() : null;
        } catch (PAException e) {
            LOG.error(e, e);
            return null;
        }

    }

    /**
     * Injection setter for TrialHelper.
     *
     * @param helper the helper to set.
     */
    public void setTrialHelper(TrialHelper helper) {
        this.trialHelper = helper;
    }
    
    /**
     * @return studyProtocolIi
     */
    public long getStudyProtocolIdentifier() {        
        return studyProtocolIdentifier;
    }
    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserService registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * The ids of the program codes that were selected
     *
     * @return list having program code ids
     */
    public List<Long> getProgramCodeIds() {
        return programCodeIds;
    }

    /**
     * Sets the ids of the program codes
     *
     * @param programCodeIds - list of program code ids
     */
    public void setProgramCodeIds(List<Long> programCodeIds) {
        this.programCodeIds = programCodeIds;
    }

    /**
     * Will return the valid program codes
     *
     * @return - valid program codes
     */
    public List<ProgramCodeDTO> getProgramCodeList() {
        return programCodeList;
    }

    /**
     * Will set the valid program codes
     * @param programCodeList - valid program codes
     */
    public void setProgramCodeList(List<ProgramCodeDTO> programCodeList) {
        this.programCodeList = programCodeList;
    }

    /**
     * Sets if cancerTrial or not
     *
     * @return - true if cancer trial
     */
    public boolean isCancerTrial() {
        return cancerTrial;
    }

    /**
     * Sets cancer trial
     *
     * @param cancerTrial - true if cancer trial
     */
    public void setCancerTrial(boolean cancerTrial) {
        this.cancerTrial = cancerTrial;
    }
}
