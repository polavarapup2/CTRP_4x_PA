/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.RegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingService.Method;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceBean;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.SessionContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Validator class for checking the input of the various trial registration methods.
 *
 * @author Michael Visee
 */
@SuppressWarnings({"PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.ExcessiveParameterList",
        "PMD.CyclomaticComplexity" })
public class TrialRegistrationValidator {

    private static final int STUDY_PROTOCOL_ID_LENGTH = 30;
    private static final String SUMMARY_4_ORGANIZATION = "Data Table 4 Organization";
    /**
     * FDA Regulated Intervention Indicator must be Yes since it has Trial IND/IDE records.
     */
    public static final String FDA_REGULATED_INTERVENTION_INDICATOR_ERR_MSG = 
            "FDA Regulated Intervention Indicator must be Yes since it has Trial IND/IDE records.";
    private static final String AMENDMENT = "Amendment";
    private static final String CREATION = "Create";
    private static final String REJECTION = "Reject";
    private static final String UPDATE = "Update";
    /**
     * Validation Exception.
     */
    public static final String VALIDATION_EXCEPTION = "Validation Exception ";

    /**
     * The set of DocumentWorkflowStatusCode that cause an error for amendment.
     */
    static final Set<DocumentWorkflowStatusCode> ERROR_DWFS_FOR_AMEND = EnumSet.complementOf(EnumSet
        .of(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
            DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE));
    /**
     * The error message for wrong DocumentWorkflowStatusCode in amendment.
     */
    static final String ERROR_MESSAGE_DWFS_FOR_AMEND = "Only Trials with processing status Abstraction Verified "
            + "Response or Abstraction Verified No Response can be Amended.\n";

    /**
     * The set of DocumentWorkflowStatusCode that cause an error for amendment.
     */
    static final Set<DocumentWorkflowStatusCode> ERROR_DWFS_FOR_REJECT = EnumSet.complementOf(EnumSet
        .of(DocumentWorkflowStatusCode.SUBMITTED, DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED, 
                DocumentWorkflowStatusCode.ACCEPTED, DocumentWorkflowStatusCode.ABSTRACTED, 
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE, 
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE, 
                DocumentWorkflowStatusCode.VERIFICATION_PENDING));
    /**
     * The error message for wrong DocumentWorkflowStatusCode in amendment.
     */
    static final String ERROR_MESSAGE_DWFS_FOR_REJECT = "Only Trials with SUBMITTED or ACCEPTED can be Rejected.\n";

    /**
     * The set of DocumentWorkflowStatusCode that cause an error for update.
     */
    static final Set<DocumentWorkflowStatusCode> ERROR_DWFS_FOR_UPDATE = EnumSet
        .of(DocumentWorkflowStatusCode.SUBMITTED, DocumentWorkflowStatusCode.REJECTED);

    /**
     * The error message for wrong DocumentWorkflowStatusCode in update.
     */
    static final String ERROR_MESSAGE_DWFS_FOR_UPDATE = "Only Trials with processing status Accepted or "
            + "Abstracted or Abstraction Verified No Response or Abstraction Verified No Response can be Updated.\n";

    private CSMUserUtil csmUserUtil;
    private DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService;
    private LookUpTableServiceRemote lookUpTableServiceRemote;
    private PAServiceUtils paServiceUtils;
    private RegistryUserServiceLocal registryUserServiceLocal;
    private RegulatoryInformationServiceLocal regulatoryInfoBean;
    private StudyInboxServiceLocal studyInboxServiceLocal;
    private StudyIndldeServiceLocal studyIndldeService;
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    private StudyProtocolServiceLocal studyProtocolService;    
    private StudyResourcingServiceLocal studyResourcingService;
    private RegulatoryAuthorityServiceLocal regulatoryAuthorityService;
    private AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService;

    private final boolean isGridSubmission;

    /**
     * Constructor.
     * @param ctx the ejb session context
     */
    public TrialRegistrationValidator(SessionContext ctx) {
        isGridSubmission = PAUtil.isGridCall(ctx);
    }

    /**
     * Validates the input for a trial update.
     * @param studyProtocolDTO The study protocol
     * @param overallStatusDTO The overall status
     * @param statusHistory 
     * @param studyResourcingDTOs The list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studySiteAccrualStatusDTOs The participating sites
     * @throws PAException If any validation error happens
     */
    public void validateUpdate(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyOverallStatusDTO> statusHistory,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs)
            throws PAException {
        Ii spIi = studyProtocolDTO.getIdentifier();
        StringBuilder errorMsg = new StringBuilder();
        validateUser(studyProtocolDTO, UPDATE, true, errorMsg);
        validateStatusAndDates(studyProtocolDTO, overallStatusDTO,
                statusHistory, StringUtils.isNotBlank(getPaServiceUtils()
                        .getCtepOrDcpId(
                                IiConverter.convertToLong(studyProtocolDTO
                                        .getIdentifier()),
                                PAConstants.DCP_IDENTIFIER_TYPE)), errorMsg);
        validateNihGrants(studyProtocolDTO, null, studyResourcingDTOs, errorMsg);
        validateDWFS(spIi, ERROR_DWFS_FOR_UPDATE, ERROR_MESSAGE_DWFS_FOR_UPDATE, errorMsg);
        validateExistingStatus(spIi, errorMsg);
        validateDocuments(documentDTOs, errorMsg);
        validateParticipatingSites(studyProtocolDTO, studySiteAccrualStatusDTOs, errorMsg);
        validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    /**
     * Validates the user executing the operation.
     * @param studyProtocolDTO The study protocol
     * @param operation The operation
     * @param checkTrialAccess true if the trial access must be checked
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If an error occurs
     */
    void validateUser(StudyProtocolDTO studyProtocolDTO, String operation, boolean checkTrialAccess,
            StringBuilder errorMsg) throws PAException {
        if (!ISOUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
            String loginName = studyProtocolDTO.getUserLastCreated().getValue();
            User user = csmUserUtil.getCSMUser(loginName);
            if (user == null) {
                errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
            } else {
                if (checkTrialAccess) {
                    Long spId = Long.parseLong(studyProtocolDTO.getIdentifier().getExtension());
                    if (!registryUserServiceLocal.hasTrialAccess(loginName, spId)) {
                        errorMsg.append(operation);
                        errorMsg.append(" to the trial can only be submitted by either an owner of the trial"
                                + " or a lead organization admin.\n");
                    }
                }
            }
        } else {
            errorMsg.append("Submitter is required.");
        }
    }

    /**
     * Validates the status and dates.
     *
     * This method checks the mandatory fileds for dates and overal status and validates the overall status object.
     *
     * @param studyProtocolDTO The study protocol
     * @param overallStatusDTO The overall status
     * @param statusHistory 
     * @param dcpIdentifierDTO 
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException 
     */
    void validateStatusAndDates(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyOverallStatusDTO> statusHistory,
            boolean isDcpTrial, StringBuilder errorMsg) {
        boolean datesValid = validateStudyProtocolDates(studyProtocolDTO,
                isDcpTrial, errorMsg);
        boolean statusFieldsValid = validateOverallStatusFields(
                overallStatusDTO, statusHistory, errorMsg);
        if (datesValid && statusFieldsValid) {
            validateOverallStatus(studyProtocolDTO, overallStatusDTO, errorMsg);
        }
    }

    /**
     * Validates the study protocol dates.
     *
     * This method validates the mandatory date and date type fields of the study protocol.
     *
     * @param studyProtocolDTO The study protocol
     * @param dcpIdentifierDTO 
     * @param errorMsg The StringBuilder collecting error messages
     * @return true if the dates are valid
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    boolean validateStudyProtocolDates(StudyProtocolDTO studyProtocolDTO,
            boolean isDcpTrial, StringBuilder errorMsg) {
        boolean valid = true;
        if (ISOUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())) {
            errorMsg.append("Trial Start Date Type cannot be null. ");
            valid = false;
        } else
        if (ActualAnticipatedTypeCode.getByCode(studyProtocolDTO
                .getStartDateTypeCode().getCode()) == null) {
            errorMsg.append("Trial Start Date Type is invalid: "
                    + "valid values are Actual, Anticipated (case-sensitive). ");
            valid = false;
        }      
        //don't validate primary completion date if it is non interventional trial 
        //and CTGovXmlRequired is false.
        if (PAUtil.isPrimaryCompletionDateRequired(studyProtocolDTO)) {
            if (ISOUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())) {
                errorMsg.append("Primary Completion Date Type cannot be null. ");
                valid = false;
            } else
            if (ActualAnticipatedTypeCode.getByCode(studyProtocolDTO
                    .getPrimaryCompletionDateTypeCode().getCode()) == null) {
                errorMsg.append("Primary Completion Date Type is invalid: "
                        + "valid values are Actual, Anticipated (case-sensitive). ");
                valid = false;
            }
            Ts pcDate = studyProtocolDTO.getPrimaryCompletionDate();
            if ((pcDate == null || (ISOUtil.isTsNull(pcDate) && pcDate
                    .getNullFlavor() != NullFlavor.UNK))
                    && !(ActualAnticipatedTypeCode.NA == CdConverter
                            .convertCdToEnum(ActualAnticipatedTypeCode.class,
                                    studyProtocolDTO
                                            .getPrimaryCompletionDateTypeCode()) && isDcpTrial)) {
                errorMsg.append("Primary Completion Date cannot be null. ");
                valid = false;
            }
        }         
        
        if (ActualAnticipatedTypeCode.NA == CdConverter.convertCdToEnum(
                ActualAnticipatedTypeCode.class,
                studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                && !isDcpTrial) {
            errorMsg.append("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'. ");
            valid = false;
        }
        if (ActualAnticipatedTypeCode.NA == CdConverter.convertCdToEnum(
                ActualAnticipatedTypeCode.class,
                studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                && !ISOUtil.isTsNull(studyProtocolDTO
                        .getPrimaryCompletionDate())) {
            errorMsg.append("When the Primary Completion Date Type is set to 'N/A', "
                    + "the Primary Completion Date must be null. ");
            valid = false;
        }
        
        
        if (ISOUtil.isTsNull(studyProtocolDTO.getStartDate())) {
            errorMsg.append("Trial Start Date cannot be null. ");
            valid = false;
        }
        
        return valid;
    }

    /**
     * Validates the overall status fields.
     *
     * This method validates the mandatory code and date fields of the overall status.
     *
     * @param overallStatusDTO The overall status
     * @param statusHistory 
     * @param errorMsg The StringBuilder collecting error messages
     * @return true if the overall status fields are valid
     */
    boolean validateOverallStatusFields(StudyOverallStatusDTO overallStatusDTO,
            List<StudyOverallStatusDTO> statusHistory, StringBuilder errorMsg) {
        boolean valid = true;
        if (overallStatusDTO != null) {
            if (ISOUtil.isCdNull(overallStatusDTO.getStatusCode())) {
                errorMsg.append("Current Trial Status cannot be null. ");
                valid = false;
            }
            if (ISOUtil.isTsNull(overallStatusDTO.getStatusDate())) {
                errorMsg.append("Current Trial Status Date cannot be null. ");
                valid = false;
            }
        } else if (CollectionUtils.isEmpty(statusHistory)) {
            errorMsg.append("Overall Status cannot be null. ");
            valid = false;
        }
        return valid;
    }

    /**
     * Validates the overall status.
     * 
     * @param studyProtocolDTO
     *            The study protocol
     * @param overallStatusDTO
     *            The overall status
     * @param statusHistory
     * @param errorMsg
     *            The StringBuilder collecting error messages
     * @throws PAException
     */
    void validateOverallStatus(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, StringBuilder errorMsg) {
        if (overallStatusDTO != null) {
            studyOverallStatusService.validate(overallStatusDTO,
                    studyProtocolDTO, errorMsg);
        }
    }

    /**
     * Validates the nih grants.
     *
     * @param studyProtocolDTO The study protocol
     * @param studyResourcingDTOs The list of nih grants
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateNihGrants(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrgDTO, 
            List<StudyResourcingDTO> studyResourcingDTOs, StringBuilder errorMsg) {
        if (isGridSubmission) { // checks already made for UI and BATCH
            String spId = ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())
                    ? null : studyProtocolDTO.getIdentifier().getExtension();
            Boolean nciFunded = BlConverter.convertToBoolean(studyProtocolDTO.getNciGrant());
            Long leadOrgPoId;
            try {
                leadOrgPoId = IiConverter.convertToLong(leadOrgDTO.getIdentifier());
            } catch (Exception e) {
                leadOrgPoId = null;
            }
            try {
                studyResourcingService.validate(Method.SERVICE, nciFunded, spId, leadOrgPoId, studyResourcingDTOs);
            } catch (PAException e) {
                errorMsg.append(e.getMessage());
            }
        }
        try {
            paServiceUtils.enforceNoDuplicateGrants(studyResourcingDTOs);
        } catch (PAException e) {
            errorMsg.append(e.getMessage());
        }
    }

    /**
     * Validates the current document workflow status.
     *
     * This method validates that the current document workflow status allows update to the trial.
     * @param spIi The study protocol Ii
     * @param errorStatuses The statuses that cause an error
     * @param error The error message
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If an error occurs in accessing the current document workflow status.
     */
    void validateDWFS(Ii spIi, Set<DocumentWorkflowStatusCode> errorStatuses, String error, StringBuilder errorMsg)
            throws PAException {
        DocumentWorkflowStatusDTO dwfsDTO = documentWorkFlowStatusService.getCurrentByStudyProtocol(spIi);
        DocumentWorkflowStatusCode dwfs = DocumentWorkflowStatusCode.getByCode(dwfsDTO.getStatusCode().getCode());
        if (errorStatuses.contains(dwfs)) {
            errorMsg.append(error);
        }
    }

    /**
     * Validates the current status.
     *
     * This method validates that the current status allows update to the trial.
     * @param spIi The study protocol Ii
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If an error occurs in accessing the current status.
     */
    void validateExistingStatus(Ii spIi, StringBuilder errorMsg) throws PAException {
        StudyOverallStatusDTO statusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        StudyStatusCode status = StudyStatusCode.getByCode(statusDTO.getStatusCode().getCode());
        Set<StudyStatusCode> errorStatuses = EnumSet.of(StudyStatusCode.ADMINISTRATIVELY_COMPLETE,
                                                        StudyStatusCode.WITHDRAWN, StudyStatusCode.COMPLETE);
        if (errorStatuses.contains(status)) {
            errorMsg.append("Update to a Trial with Current Trial Status as Disapproved or"
                    + " Withdrawn or Complete or Administratively Complete is not allowed.\n");
        }
    }

    /**
     * Validates that provided documents exist.
     *
     * @param documentDTOs List of documents IRB and Participating doc
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateDocuments(List<DocumentDTO> documentDTOs, StringBuilder errorMsg) {
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO docDto : documentDTOs) {
                if (!ISOUtil.isIiNull(docDto.getIdentifier())
                        && !paServiceUtils.isIiExistInPA(IiConverter.convertToDocumentIi(Long.valueOf(docDto
                            .getIdentifier().getExtension())))) {
                    errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exist.");
                }
            }
        }
    }

    /**
     * Validates the participating sites.
     * @param studyProtocolDTO The study protocol
     * @param studySiteAccrualStatusDTOs The participating sites
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException if an error accurs accessing the recruitment status.
     */
    void validateParticipatingSites(StudyProtocolDTO studyProtocolDTO,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, StringBuilder errorMsg) throws PAException {
        if (CollectionUtils.isNotEmpty(studySiteAccrualStatusDTOs)) {
            StudyOverallStatusDTO recruitmentStatusDto = studyOverallStatusService
                .getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
            try {
                paServiceUtils.enforceRecruitmentStatus(studyProtocolDTO, studySiteAccrualStatusDTOs,
                                                        recruitmentStatusDto);
            } catch (PAException e) {
                errorMsg.append(e.getMessage());
            }
        }
    }

    /**
     * Validates the SummaryFour information.
     * @param studyProtocolDTO The study protocol
     * @param organizationDTO organizationDTO
     * @param studyResourcingDTO The studyResourcingDTO
     * @throws PAException on error
     */
    public void validateSummary4SponsorAndCategory(StudyProtocolDTO studyProtocolDTO, OrganizationDTO organizationDTO,
            StudyResourcingDTO studyResourcingDTO) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        if (organizationDTO == null) {
            errorMsg.append("Summary Four Organization cannot be null, ");
        }
        validateSummary4Resourcing(studyProtocolDTO, studyResourcingDTO, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
    }

    /**
     * Validates the SummaryFour information.
     * @param studyProtocolDTO The study protocol
     * @param studyResourcingDTO The studyResourcingDTO
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateSummary4Resourcing(StudyProtocolDTO studyProtocolDTO, StudyResourcingDTO studyResourcingDTO,
            StringBuilder errorMsg) {
        if (studyResourcingDTO == null) {
            errorMsg.append("Summary Four Study Resourcing cannot be null, ");
        } else {
            String category = CdConverter.convertCdToString(studyResourcingDTO.getTypeCode());
            if (StringUtils.isEmpty(category)) {
                errorMsg.append("Summary Four Sponsor Category cannot be null, ");
            } else {
                validateSummary4Category(studyProtocolDTO, category, errorMsg);
            }
        }
    }

    /**
     * validates the summary4 category.
     * @param studyProtocolDTO The study protocol
     * @param category The category to validate
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateSummary4Category(StudyProtocolDTO studyProtocolDTO, String category, StringBuilder errorMsg) {
        SummaryFourFundingCategoryCode categoryCode = SummaryFourFundingCategoryCode.getByCode(category);
        boolean proprietary = BlConverter.convertToBool(studyProtocolDTO.getProprietaryTrialIndicator());
        boolean industrial = StringUtils
            .equalsIgnoreCase(category, SummaryFourFundingCategoryCode.INDUSTRIAL.getCode());
        if ((categoryCode == null) || (proprietary && !industrial) || (!proprietary && industrial)) {
            errorMsg.append("Please enter valid value for Data Table 4 Sponsor Category.");
        }
    }

    /**
     * Validates the input for a trial amendment.
     * @param studyProtocolDTO The study protocol
     * @param overallStatusDTO The overall status
     * @param leadOrganizationDTO The lead organization
     * @param sponsorOrganizationDTO The sponsor organization    
     * @param summary4OrganizationDTO The summary4 organization
     * @param summary4StudyResourcingDTO The Data Table 4 category code
     * @param principalInvestigatorDTO The principal investigator
     * @param studyRegAuthDTO The regulatory authority
     * @param studyResourcingDTOs The list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studyIndldeDTOs The list of study Ind/ides
     * @param nctIdentifierDTO The NCT identifier
     * @param dcpIdentifierDTO dcpIdentifierDTO
     * @param leadOrganizationSiteIdentifierDTO lead org site identifier 
     * @throws PAException If any validation error happens
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    public void validateAmendment(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyOverallStatusDTO> statusHistory,
            OrganizationDTO leadOrganizationDTO,
            OrganizationDTO sponsorOrganizationDTO,          
            List<OrganizationDTO> summary4OrganizationDTO, StudyResourcingDTO summary4StudyResourcingDTO,
            PersonDTO principalInvestigatorDTO, ResponsiblePartyDTO partyDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            StudySiteDTO nctIdentifierDTO, StudySiteDTO dcpIdentifierDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO) throws PAException {
        
        validateLeadOrgTrialIdLength(leadOrganizationSiteIdentifierDTO);
        validateAmendment(studyProtocolDTO, overallStatusDTO, 
                statusHistory, leadOrganizationDTO, 
                sponsorOrganizationDTO, summary4OrganizationDTO, 
                summary4StudyResourcingDTO, principalInvestigatorDTO, 
                partyDTO, studyRegAuthDTO, studyResourcingDTOs, 
                documentDTOs, studyIndldeDTOs, 
                nctIdentifierDTO, dcpIdentifierDTO);
        
    }
    /**
     * Validates the input for a trial amendment.
     * @param studyProtocolDTO The study protocol
     * @param overallStatusDTO The overall status
     * @param leadOrganizationDTO The lead organization
     * @param sponsorOrganizationDTO The sponsor organization    
     * @param summary4OrganizationDTO The summary4 organization
     * @param summary4StudyResourcingDTO The Data Table 4 category code
     * @param principalInvestigatorDTO The principal investigator
     * @param studyRegAuthDTO The regulatory authority
     * @param studyResourcingDTOs The list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studyIndldeDTOs The list of study Ind/ides
     * @param nctIdentifierDTO The NCT identifier
     * @param dcpIdentifierDTO 
     * @throws PAException If any validation error happens
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    public void validateAmendment(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyOverallStatusDTO> statusHistory,
            OrganizationDTO leadOrganizationDTO,
            OrganizationDTO sponsorOrganizationDTO,          
            List<OrganizationDTO> summary4OrganizationDTO, StudyResourcingDTO summary4StudyResourcingDTO,
            PersonDTO principalInvestigatorDTO, ResponsiblePartyDTO partyDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            StudySiteDTO nctIdentifierDTO, StudySiteDTO dcpIdentifierDTO) throws PAException {
        // CHECKSTYLE:ON
        Ii spIi = studyProtocolDTO.getIdentifier();
        StringBuilder errorMsg = new StringBuilder();
        validateUser(studyProtocolDTO, AMENDMENT, true, errorMsg);
        validateStatusAndDates(studyProtocolDTO, overallStatusDTO,
                statusHistory, dcpIdentifierDTO != null, errorMsg);
        validateNihGrants(studyProtocolDTO, leadOrganizationDTO, studyResourcingDTOs, errorMsg);
        validateIndlde(studyProtocolDTO, studyIndldeDTOs, errorMsg);
        validateDWFS(spIi, ERROR_DWFS_FOR_AMEND, ERROR_MESSAGE_DWFS_FOR_AMEND, errorMsg);
        validateExistingStatus(spIi, errorMsg);
        validateOtherIdentifiers(studyProtocolDTO, errorMsg);
        validateMandatoryDocuments(documentDTOs, errorMsg);
        validateAmendmentDocuments(documentDTOs, errorMsg);
        validatePOObjects(studyProtocolDTO, leadOrganizationDTO,
                sponsorOrganizationDTO, summary4OrganizationDTO,
                principalInvestigatorDTO, partyDTO.getInvestigator(),
                partyDTO.getAffiliation(), errorMsg);
        validateAmendmentInfo(studyProtocolDTO, errorMsg);
        validateResponsibleParty(studyProtocolDTO, partyDTO, errorMsg);
        validateRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO, studyIndldeDTOs, errorMsg, AMENDMENT);
        validateSummary4Resourcing(studyProtocolDTO, summary4StudyResourcingDTO, errorMsg);
        validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        if (nctIdentifierDTO != null && !ISOUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
            String nctValidationResultString = paServiceUtils.validateNCTIdentifier(
                   nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue(), studyProtocolDTO
                   .getIdentifier());
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                errorMsg.append(nctValidationResultString);
            }       
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    /**
     * validates the Ind/ide.
     * @param studyProtocolDTO The study protocol
     * @param studyIndldeDTOs The list of study Ind/ides
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateIndlde(StudyProtocolDTO studyProtocolDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            StringBuilder errorMsg) {
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            for (StudyIndldeDTO indIdeDto : studyIndldeDTOs) {
                indIdeDto.setStudyProtocolIdentifier(studyProtocolDTO
                        .getIdentifier());
                errorMsg.append(studyIndldeService
                        .validateWithoutRollback(indIdeDto));
            }
            try {
                for (String msg : paServiceUtils.enforceNoDuplicateIndIde(
                        studyIndldeDTOs, studyProtocolDTO)) {
                    if (errorMsg.indexOf(StringUtils.strip(msg)) == -1) {
                        errorMsg.append(msg);
                    }
                }
            } catch (PAException e) {
                errorMsg.append(e.getMessage());
            }
        }
    }

    /**
     * Validate the other identifiers for an amendment.
     * @param studyProtocolDTO The study protocol
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If any error happens
     */
    void validateOtherIdentifiers(StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) throws PAException {
        StudyProtocolDTO saved = studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier());
        Set<Ii> savedIdentifiers = saved.getSecondaryIdentifiers().getItem();
        Set<Ii> newIdentifiers = (studyProtocolDTO.getSecondaryIdentifiers() == null) ? new HashSet<Ii>()
                : studyProtocolDTO.getSecondaryIdentifiers().getItem();
        if (!CollectionUtils.isSubCollection(savedIdentifiers, newIdentifiers)) {
            errorMsg.append("Other identifiers cannot be modified or deleted as part of an amendment.");
        }
    }

    /**
     * validates that the mandatory documents are present.
     * @param documentDTOs The List of documents IRB and Participating doc
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateMandatoryDocuments(List<DocumentDTO> documentDTOs, StringBuilder errorMsg) {
        errorMsg.append(paServiceUtils.checkDocumentListForValidFileTypes(documentDTOs));
        if (!paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)) {
            errorMsg.append("Protocol Document is required.\n");
        }
        if (!paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT)) {
            errorMsg.append("IRB Approval Document is required.\n");
        }
    }

    /**
     * Validates that the documents required for amendment are present.
     * @param documentDTOs The List of documents IRB and Participating doc
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateAmendmentDocuments(List<DocumentDTO> documentDTOs, StringBuilder errorMsg) {
        if (!paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.CHANGE_MEMO_DOCUMENT) && !paServiceUtils
            .isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT)) {
            errorMsg.append("At least one is required: Change Memo Document or Protocol Highlighted Document.");
        }
    }

    /**
     * Validates the PO objects for amendment.
     * @param studyProtocolDTO The study protocol
     * @param leadOrganizationDTO The lead organization
     * @param sponsorOrganizationDTO The sponsor organization
     * @param summary4organizationDTO The summary4 organization
     * @param piPersonDTO The principal investigator
     * @param responsiblePartyContactIi The responsible party contact
     * @param errorMsg The StringBuilder collecting error messages
     */
    //CHECKSTYLE:OFF
    void validatePOObjects(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO,
            OrganizationDTO sponsorOrganizationDTO, List<OrganizationDTO> summary4organizationDTO, 
            PersonDTO piPersonDTO, PersonDTO responsiblePartyInvestigator, OrganizationDTO respPartyAffiliation, 
            StringBuilder errorMsg) {
        errorMsg.append(validatePoObject(leadOrganizationDTO, "Lead Organization", true));
        if (CollectionUtils.isNotEmpty(summary4organizationDTO)) {
            for (OrganizationDTO dto : summary4organizationDTO) {
                errorMsg.append(validatePoObject(dto, SUMMARY_4_ORGANIZATION, false));
            }        
        } else {
            errorMsg.append(validatePoObject(null, SUMMARY_4_ORGANIZATION, false));
        }
        errorMsg.append(validatePoObject(piPersonDTO, "Principal Investigator", true));
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            errorMsg.append(validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true));            
            if (responsiblePartyInvestigator != null) {
                errorMsg.append(validatePoObject(responsiblePartyInvestigator,
                        "Responsible Party Investigator", true));
            }
            if (respPartyAffiliation != null) {
                errorMsg.append(validatePoObject(respPartyAffiliation,
                        "Responsible Party Investigator Affiliation", true));
            }
        }
    }
    
    //CHECKSTYLE:ON

    /**
     * Validate objects in po.
     * @param poDTO The object to validate
     * @param fieldName The field name
     * @param iiRequired true if the Ii is required
     * @return an error message or null if everything is valid
     */
    public String validatePoObject(EntityDto poDTO, String fieldName, boolean iiRequired) {
        if (poDTO == null) {
            return MessageFormat.format("{0} cannot be null.\n", fieldName);
        }
        StringBuilder errorMsg = new StringBuilder();
        if (iiRequired && ISOUtil.isIiNull(poDTO.getIdentifier())) {
            String msg = "Error getting {0} from PO. Identifier is required.\n";
            errorMsg.append(MessageFormat.format(msg, fieldName));
        }
        if (!ISOUtil.isIiNull(poDTO.getIdentifier()) && !paServiceUtils.isIiExistInPO(poDTO.getIdentifier())) {
            String msg = "Error getting {0} from PO for id = {1}.\n";
            errorMsg.append(MessageFormat.format(msg, fieldName, poDTO.getIdentifier().getExtension()));
        }
        return errorMsg.toString();
    }

    /**
     * Validates the amendment information.
     * @param studyProtocolDTO The study protocol
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If any error happens
     */
    void validateAmendmentInfo(StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) throws PAException {
        check(ISOUtil.isTsNull(studyProtocolDTO.getAmendmentDate()), "Amendment Date is required.  ", errorMsg);
        if (!studyInboxServiceLocal.getOpenInboxEntries(studyProtocolDTO.getIdentifier()).isEmpty()) {
            String ctroAddress = lookUpTableServiceRemote.getPropertyValue("fromaddress");
            String msg = "A trial with unaccepted updates cannot be amended. Please contact the CTRO at {0} to "
                    + "have your trial''s updates accepted.";
            errorMsg.append(MessageFormat.format(msg, ctroAddress));
        }
    }

        
    /**
     * Validates regulatory info.
     *
     * @param studyProtocolDTO The study protocol dto
     * @param studyRegAuthDTO The study reg auth dto
     * @param studyIndldeDTOs The study indlde dtos
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException the PA exception
     */
    void validateRegulatoryInfo(StudyProtocolDTO studyProtocolDTO, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, StringBuilder errorMsg, String operation) throws PAException {
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            check(studyRegAuthDTO == null, "Regulatory Information fields must be Entered.\n", errorMsg);

            check(ISOUtil.isBlNull(studyProtocolDTO.getFdaRegulatedIndicator()),
                  "FDA Regulated Intervention Indicator is required field.\n", errorMsg);

            boolean fdaRegulated = BlConverter.convertToBool(studyProtocolDTO.getFdaRegulatedIndicator());
            check(fdaRegulated && ISOUtil.isBlNull(studyProtocolDTO.getSection801Indicator()),
                  "Section 801 is required if FDA Regulated indicator is true.\n", errorMsg);

            validateDelayedPostingInd(studyProtocolDTO, operation);

            validateRegAuthorityExistence(studyRegAuthDTO, errorMsg);
            
            if (containsNonExemptInds(studyIndldeDTOs)) {
                check(!fdaRegulated
                        && errorMsg
                                .indexOf(FDA_REGULATED_INTERVENTION_INDICATOR_ERR_MSG) == -1,
                        FDA_REGULATED_INTERVENTION_INDICATOR_ERR_MSG + "\n",
                        errorMsg);

                if (studyRegAuthDTO != null && !ISOUtil.isIiNull(studyRegAuthDTO.getRegulatoryAuthorityIdentifier())) {
                    Long sraId = Long.valueOf(studyRegAuthDTO.getRegulatoryAuthorityIdentifier().getExtension());
                    // doing this just to load the country since its lazy loaded.
                    boolean isUSA = regulatoryInfoBean.getRegulatoryAuthorityCountry(sraId).getAlpha3().equals("USA");
                    String regAuthName = regulatoryInfoBean.getCountryOrOrgName(sraId, "RegulatoryAuthority");
                    check(!(isUSA && regAuthName.equalsIgnoreCase("Food and Drug Administration")),
                          "For IND protocols, Oversight Authorities must include United States: "
                                  + "Food and Drug Administration.\n", errorMsg);

                }
            }
        }
    }
    /**
     * 
     * @param studyProtocolDTO The study protocol dto
     * @param operation The operation
     * @throws PAException The exception
     */
    void validateDelayedPostingInd(StudyProtocolDTO studyProtocolDTO, String operation) throws PAException {
         boolean section801 = BlConverter.convertToBool(studyProtocolDTO.getSection801Indicator());
         if (operation.equalsIgnoreCase(AMENDMENT)) {
             StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService()
                     .getStudyProtocol(studyProtocolDTO.getIdentifier());
             if (!spDTO.getDelayedpostingIndicator().equals(studyProtocolDTO.getDelayedpostingIndicator())) {
                studyProtocolDTO.setDelayedpostingIndicator(spDTO.getDelayedpostingIndicator());
                studyProtocolDTO.setDelayedPostingIndicatorChanged(BlConverter.convertToBl(true));
             }
         } else if (section801 && (ISOUtil.isBlNull(studyProtocolDTO.getDelayedpostingIndicator()) 
             || (BlConverter.convertToBool(studyProtocolDTO.getDelayedpostingIndicator())))) {
                 studyProtocolDTO.setDelayedpostingIndicator(BlConverter.convertToBl(false));
                 studyProtocolDTO.setDelayedPostingIndicatorChanged(BlConverter.convertToBl(true));
         }
         
//         check(section801 && ISOUtil.isBlNull(studyProtocolDTO.getDelayedpostingIndicator()),
//               "Delayed posting Indicator is required if Section 801 is true.So setting it to no\n", errorMsg);
    }
    /**
     * @param studyRegAuthDTO
     * @param errorMsg
     * @throws PAException
     */
    void validateRegAuthorityExistence(
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, StringBuilder errorMsg)
            throws PAException {
        if (studyRegAuthDTO != null
                && !ISOUtil.isIiNull(studyRegAuthDTO
                        .getRegulatoryAuthorityIdentifier())) {
            RegulatoryAuthorityDTO raDTO = regulatoryAuthorityService
                    .get(studyRegAuthDTO.getRegulatoryAuthorityIdentifier());
            check(raDTO == null,
                    "Regulatory Authority with the given identifier "
                            + studyRegAuthDTO
                                    .getRegulatoryAuthorityIdentifier()
                                    .getExtension() + " is not found.\n",
                    errorMsg);
        }
    }

    /**
     * Test if the given StudyIndleDTO list contains a non exempt one.
     * @param studyIndldeDTOs ind
     * @return true if least one non-exempt IND/IDE exists
     */
    boolean containsNonExemptInds(List<StudyIndldeDTO> studyIndldeDTOs) {
        boolean isNonExemptInds = false;
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            for (StudyIndldeDTO dto : studyIndldeDTOs) {
                if (BooleanUtils.isFalse(BlConverter.convertToBoolean(dto.getExemptIndicator()))) {
                    isNonExemptInds = true;
                    break;
                }
            }
        }
        return isNonExemptInds;
    }

    /**
     * Validates the input for a trial creation.
     * @param studyProtocolDTO The study protocol
     * @param overallStatusDTO The overall status
     * @param leadOrganizationDTO The lead organization
     * @param sponsorOrganizationDTO The sponsor organization    
     * @param summary4OrganizationDTO The summary4 organization
     * @param summary4StudyResourcingDTO The Data Table 4 category code
     * @param principalInvestigatorDTO The principal investigator
     * @param leadOrganizationSiteIdentifierDTO The lead organization site    
     * @param studyRegAuthDTO The regulatory authority
     * @param studyResourcingDTOs The list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studyIndldeDTOs The list of study Ind/ides
     * @param nctIdentifierDTO The NCT identifier
     * @param dcpIdentifierDTO 
     * @throws PAException If any validation error happens
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    public void validateCreation(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            OrganizationDTO leadOrganizationDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO,
            PersonDTO principalInvestigatorDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            List<StudyIndldeDTO> studyIndldeDTOs,
            StudySiteDTO nctIdentifierDTO, StudySiteDTO dcpIdentifierDTO)
            throws PAException {
        // CHECKSTYLE:ON
        validateStudyProtocol(studyProtocolDTO);
        StringBuilder errorMsg = new StringBuilder();
        validateMandatoryFields(studyProtocolDTO, leadOrganizationSiteIdentifierDTO, documentDTOs, errorMsg);
        validateUser(studyProtocolDTO, CREATION, false, errorMsg);
        validateStatusAndDates(studyProtocolDTO, overallStatusDTO, null,
                dcpIdentifierDTO != null, errorMsg);
        validateNihGrants(studyProtocolDTO, leadOrganizationDTO, studyResourcingDTOs, errorMsg);
        validateIndlde(studyProtocolDTO, studyIndldeDTOs, errorMsg);
        validateMandatoryDocuments(documentDTOs, errorMsg);
        validatePOObjects(studyProtocolDTO, leadOrganizationDTO,
                sponsorOrganizationDTO, summary4OrganizationDTO,
                principalInvestigatorDTO, partyDTO.getInvestigator(),
                partyDTO.getAffiliation(), errorMsg);        
        validateResponsibleParty(studyProtocolDTO, partyDTO, errorMsg);
        validateRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO, studyIndldeDTOs, errorMsg, CREATION);
        validateSummary4Resourcing(studyProtocolDTO, summary4StudyResourcingDTO, errorMsg);
        validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        validateLeadOrgTrialIdLength(leadOrganizationSiteIdentifierDTO);
        if (nctIdentifierDTO != null && !ISOUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
            String nctValidationResultString = paServiceUtils.validateNCTIdentifier(
                   nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue(), null);
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                errorMsg.append(nctValidationResultString);
            }       
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    private void validateResponsibleParty(StudyProtocolDTO studyProtocolDTO,
            ResponsiblePartyDTO partyDTO, StringBuilder errorMsg) {
        if (BlConverter.convertToBool(studyProtocolDTO
                .getCtgovXmlRequiredIndicator())) {
            if (partyDTO == null || partyDTO.getType() == null) {
                errorMsg.append("Responsible Party must be specified. ");
                return;
            }
            if (ResponsiblePartyType.SPONSOR.equals(partyDTO.getType())) {
                return;
            }
            if (partyDTO.getInvestigator() == null) {
                errorMsg.append("Responsible Party Investigator must be specified. ");
            }
            if (partyDTO.getAffiliation() == null) {
                errorMsg.append("Responsible Party Investigator Affiliation must be specified. ");
            }
        }
    }

    /**
     * Validates the presence of the study protocol and the ctgov flag.
     * @param studyProtocolDTO The study protocol
     * @throws PAException if the study protocol is not present
     */
    void validateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (studyProtocolDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol cannot be null.");
        }
        if (ISOUtil.isBlNull(studyProtocolDTO.getCtgovXmlRequiredIndicator())) {
            throw new PAException(
                    VALIDATION_EXCEPTION
                            + "Study Protocol ClinicalTrials.gov XML indicator cannot be null.");
        }
    }

    /**
     * Validates that the mandatory fields are present in the study protocol.
     * @param studyProtocolDTO The study protocol
     * @param leadOrganizationSiteIdentifierDTO The lead organization site
     * @param documentDTOs The List of documents IRB and Participating doc
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateMandatoryFields(StudyProtocolDTO studyProtocolDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<DocumentDTO> documentDTOs, StringBuilder errorMsg) {
        check(leadOrganizationSiteIdentifierDTO != null
                      && ISOUtil.isStNull(leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier()),
              "Local StudyProtocol Identifier cannot be null , ", errorMsg);
        check(documentDTOs == null, "Document DTO's cannot be null, ", errorMsg);
        check(ISOUtil.isStNull(studyProtocolDTO.getOfficialTitle()), "Official Title cannot be null", errorMsg);
        validatePhase(studyProtocolDTO, errorMsg);
    }

    /**
     * Validates the Phase code.
     * @param studyProtocolDTO The study protocol
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validatePhase(StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) {
        if (ISOUtil.isCdNull(studyProtocolDTO.getPhaseCode())) {
            errorMsg.append("Phase cannot be null , ");
        } else {
            check(PhaseCode.getByCode(CdConverter.convertCdToString(studyProtocolDTO.getPhaseCode())) == null,
                  "Please enter valid value for Phase Code.", errorMsg);
        }

    }
    

    /**
     * Validates the input for a trial amendment.
     * @param studyProtocolDTO The study protocol
     * @param milestoneCode MilestoneCode
     * @throws PAException If any validation error happens
     */
    public void validateRejection(StudyProtocolDTO studyProtocolDTO, MilestoneCode milestoneCode) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        validateUser(studyProtocolDTO, REJECTION, false, errorMsg);
        if (!MilestoneCode.LATE_REJECTION_DATE.equals(milestoneCode)) {
            validateDWFS(studyProtocolDTO.getIdentifier(),
                    ERROR_DWFS_FOR_REJECT, ERROR_MESSAGE_DWFS_FOR_REJECT,
                    errorMsg);
        }
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    /**
     * Validates the input for a proprietary trial creation.
     * @param studyProtocolDTO The study protocol
     * @param studySiteAccrualStatusDTO The study site accrual status
     * @param documentDTOs The List of documents
     * @param leadOrganizationDTO The lead organization
     * @param studySiteInvestigatorDTO The Study Site Investigator
     * @param leadOrganizationStudySiteDTO The Lead Organization Study Site
     * @param studySiteOrganizationDTO The Study Site Organization
     * @param studySiteDTO The study site
     * @param nctIdentifierDTO The NCT identifier
     * @param summary4OrganizationDTO The Data Table 4 Organization
     * @param summary4StudyResourcingDTO The summary4 resourcing
     * @throws PAException If a validation error occurs
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    public void validateProprietaryCreation(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO) throws PAException {
        // CHECKSTYLE:ON
        if (studyProtocolDTO == null) {
            throw new PAException(VALIDATION_EXCEPTION + "Study Protocol cannot be null.");
        }
        StringBuilder errorMsg = new StringBuilder();
        validateMandatoryFieldsForProprietary(studyProtocolDTO, studySiteAccrualStatusDTO,
                                              leadOrganizationStudySiteDTO, studySiteDTO, summary4StudyResourcingDTO,
                                              errorMsg);
        validateLeadOrgTrialIdLength(leadOrganizationStudySiteDTO);
        validateUser(studyProtocolDTO, CREATION, false, errorMsg);
        if (nctIdentifierDTO != null && !ISOUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
            String nctValidationResultString = paServiceUtils.validateNCTIdentifier(
                   nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue(), null);
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                errorMsg.append(nctValidationResultString);
            }       
        }
        validatePhasePurposeAndTemplateDocument(studyProtocolDTO, documentDTOs, nctIdentifierDTO, errorMsg);
        errorMsg.append(paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO));
        errorMsg.append(validatePoObject(leadOrganizationDTO, "Lead Organization", false));
        errorMsg.append(validatePoObject(studySiteOrganizationDTO, "Study Site Organization", false));
        if (CollectionUtils.isNotEmpty(summary4OrganizationDTO)) {
            for (OrganizationDTO dto : summary4OrganizationDTO) {
                errorMsg.append(validatePoObject(dto, SUMMARY_4_ORGANIZATION, false));
            }        
        } else {
            errorMsg.append(validatePoObject(null, SUMMARY_4_ORGANIZATION, false));
        }
        errorMsg.append(validatePoObject(studySiteInvestigatorDTO, "Study Site Investigator", false));
        validateSummary4Resourcing(studyProtocolDTO, summary4StudyResourcingDTO, errorMsg);
        validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
        }
    }

    /**
     * Validates the mandatory fields for proprietary trial creation.
     * @param studyProtocolDTO The study protocol
     * @param studySiteAccrualStatusDTO The study site accrual status
     * @param leadOrganizationStudySiteDTO The lead organization study site
     * @param studySiteDTO The study site
     * @param summary4StudyResourcingDTO The summary4 resourcing
     * @param errorMsg The StringBuilder collecting error messages
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    void validateMandatoryFieldsForProprietary(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, StudySiteDTO leadOrganizationStudySiteDTO,
            StudySiteDTO studySiteDTO, StudyResourcingDTO summary4StudyResourcingDTO, StringBuilder errorMsg) {
        // CHECKSTYLE:ON
        check(ISOUtil.isStNull(studyProtocolDTO.getOfficialTitle()), "Official Title cannot be null , ", errorMsg);
        check(studySiteAccrualStatusDTO == null, "Study Site Accrual Status cannot be null , ", errorMsg);
        validateLeadOrgForProprietary(leadOrganizationStudySiteDTO, errorMsg);
        if (studySiteDTO == null) {
            errorMsg.append("Study Site DTO cannot be null , ");
        } else {
            check(ISOUtil.isStNull(studySiteDTO.getLocalStudyProtocolIdentifier()),
                  "Submitting Organization Local Trial Identifier cannot be null, ", errorMsg);
        }
    }

    /**
     * @param leadOrganizationStudySiteDTO
     * @param errorMsg
     */
    void validateLeadOrgForProprietary(
            StudySiteDTO leadOrganizationStudySiteDTO, StringBuilder errorMsg) {
        if (leadOrganizationStudySiteDTO == null) {
            errorMsg.append("Lead Organization Study Site cannot be null , ");
        } else {
            check(ISOUtil.isStNull(leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier()),
                  "Lead Organization Trial Identifier cannot be null, ", errorMsg);

        }
    }
    
    /**
     * Validates length of study site name not greater than 30 
     * @param leadOrganizationStudySiteDTO
     * @param errorMsg
     * @throws PAException 
     */
    void validateLeadOrgTrialIdLength(StudySiteDTO leadOrganizationStudySiteDTO) throws PAException {
        if (leadOrganizationStudySiteDTO != null) {
        StringBuilder errorMsg = new StringBuilder();
        boolean isLeadOrgLocalStudyProtocolIdNotNull = ISOUtil
                .isStNull(leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier());
        
        if (!isLeadOrgLocalStudyProtocolIdNotNull) {
            check(leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier()
                    .getValue().length() > STUDY_PROTOCOL_ID_LENGTH,
                "Lead Organization Trial Identifier length is greater than 30 characters", errorMsg);
            if (errorMsg.length() > 0) {
                throw new PAException(VALIDATION_EXCEPTION + errorMsg);
            }
        }
        }
    }

    /**
     * Validates the phase, primary purpose and proprietary template document.
     * @param studyProtocolDTO The study protocol
     * @param documentDTOs The List of documents
     * @param nctIdentifierDTO The NCT identifier
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validatePhasePurposeAndTemplateDocument(StudyProtocolDTO studyProtocolDTO,
            List<DocumentDTO> documentDTOs, StudySiteDTO nctIdentifierDTO, StringBuilder errorMsg) {
        if (nctIdentifierDTO != null) {
            if (ISOUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
                validatePhaseAndPurpose(studyProtocolDTO, errorMsg);                
            }
        } else {
            validatePhaseAndPurpose(studyProtocolDTO, errorMsg);
        }
    }

    /**
     * Validates the phase and primary purpose.
     * @param studyProtocolDTO The study protocol
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validatePhaseAndPurpose(StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) {
        validatePhase(studyProtocolDTO, errorMsg);
        check(ISOUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode()), "Purpose cannot be null , ", errorMsg);
    }

    /**
     * Validates and defaults if necessary the Accrual Disease Coding.
     * @param studyProtocolDTO The study protocol
     * @param errorMsg The StringBuilder collecting error messages
     */
    void validateAccrualDiseaseCodeSystem(StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) {
        Long spId = IiConverter.convertToLong(studyProtocolDTO.getIdentifier());
        String currentCodeSystem = spId == null ? null : accrualDiseaseTerminologyService.getCodeSystem(spId);
        String newCodeSystem = StConverter.convertToString(studyProtocolDTO.getAccrualDiseaseCodeSystem());
        if (StringUtils.isNotBlank(newCodeSystem)) {
            if (accrualDiseaseTerminologyService.getValidCodeSystems().contains(newCodeSystem)) {
                if (currentCodeSystem != null && !currentCodeSystem.equals(newCodeSystem)
                        && !accrualDiseaseTerminologyService.canChangeCodeSystem(spId)) {
                    errorMsg.append("Accrual disease code system for this trial can't be changed.");
                }
            } else {
                errorMsg.append("Accrual disease code system " + newCodeSystem + " is invalid.");
            }
        } else {
            if (spId == null) {
                studyProtocolDTO.setAccrualDiseaseCodeSystem(
                        StConverter.convertToSt(AccrualDiseaseTerminologyServiceBean.DEFAULT_CODE_SYSTEM));
            } else {
                studyProtocolDTO.setAccrualDiseaseCodeSystem(
                        StConverter.convertToSt(accrualDiseaseTerminologyService.getCodeSystem(spId)));
            }
        }
    }

    /**
     * Check an error condition and concatenate the message in the message collector.
     * @param condition The condition to check
     * @param message The message
     * @param errorMsg The StringBuilder collecting error messages
     */
    void check(boolean condition, String message, StringBuilder errorMsg) {
        if (condition) {
            errorMsg.append(message);
        }
    }

    /**
     * @param csmUserUtil the csmUserUtil to set
     */
    public void setCsmUserUtil(CSMUserUtil csmUserUtil) {
        this.csmUserUtil = csmUserUtil;
    }

    /**
     * @param documentWorkFlowStatusService the documentWorkFlowStatusService to set
     */
    public void setDocumentWorkFlowStatusService(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
    }

    /**
     * @param lookUpTableServiceRemote the lookUpTableServiceRemote to set
     */
    public void setLookUpTableServiceRemote(LookUpTableServiceRemote lookUpTableServiceRemote) {
        this.lookUpTableServiceRemote = lookUpTableServiceRemote;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param registryUserServiceLocal the registryUserServiceLocal to set
     */
    public void setRegistryUserServiceLocal(RegistryUserServiceLocal registryUserServiceLocal) {
        this.registryUserServiceLocal = registryUserServiceLocal;
    }

    /**
     * @param regulatoryInfoBean the regulatoryInfoBean to set
     */
    public void setRegulatoryInfoBean(RegulatoryInformationServiceLocal regulatoryInfoBean) {
        this.regulatoryInfoBean = regulatoryInfoBean;
    }

    /**
     * @param studyInboxServiceLocal the studyInboxServiceLocal to set
     */
    public void setStudyInboxServiceLocal(StudyInboxServiceLocal studyInboxServiceLocal) {
        this.studyInboxServiceLocal = studyInboxServiceLocal;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
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
     * @param regulatoryAuthorityService RegulatoryAuthorityServiceLocal
     */
    public void setRegulatoryAuthorityService(
            RegulatoryAuthorityServiceLocal regulatoryAuthorityService) {
        this.regulatoryAuthorityService = regulatoryAuthorityService;
    }

    /**
     * @param accrualDiseaseTerminologyService AccrualDiseaseTerminologyServiceRemote
     */
    public void setAccrualDiseaseTerminologyService(
            AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService) {
        this.accrualDiseaseTerminologyService = accrualDiseaseTerminologyService;
    }

    // CHECKSTYLE:OFF
    /**
     * @param studyProtocolDTO
     *            studyProtocolDTO
     * @param nctID
     *            nctID
     * @param leadOrgDTO
     *            leadOrgDTO
     * @param leadOrgID
     *            leadOrgID
     * @param sponsorDTO
     *            sponsorDTO
     * @param investigatorDTO
     *            investigatorDTO
     * @param centralContactDTO
     *            centralContactDTO
     * @param overallStatusDTO
     *            overallStatusDTO
     * @param regAuthDTO
     *            regAuthDTO
     * @param arms
     *            arms
     * @param eligibility
     *            eligibility
     * @param outcomes
     *            outcomes
     * @param collaborators
     *            collaborators
     * @throws PAException
     */
    public void validateProprietaryCreation(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators) throws PAException {

        StringBuilder errorMsg = new StringBuilder();
        validateProprietaryCreateOrUpdate(studyProtocolDTO, nctID, leadOrgDTO,
                leadOrgID, regAuthDTO, errorMsg);
        validateLeadOrgTrialIdLength(leadOrgID);
        String nctValidationResultString = paServiceUtils
                .validateNCTIdentifier(nctID.getLocalStudyProtocolIdentifier()
                        .getValue(), null);
        if (StringUtils.isNotEmpty(nctValidationResultString)) {
            errorMsg.append(nctValidationResultString);
        }

        if (errorMsg.length() > 0) {
            throw new PAException(errorMsg.toString());
        }

    }
    
    /**
     * @param studyProtocolDTO
     *            studyProtocolDTO
     * @param nctID
     *            nctID
     * @param leadOrgDTO
     *            leadOrgDTO
     * @param leadOrgID
     *            leadOrgID
     * @param sponsorDTO
     *            sponsorDTO
     * @param investigatorDTO
     *            investigatorDTO
     * @param centralContactDTO
     *            centralContactDTO
     * @param overallStatusDTO
     *            overallStatusDTO
     * @param regAuthDTO
     *            regAuthDTO
     * @param arms
     *            arms
     * @param eligibility
     *            eligibility
     * @param outcomes
     *            outcomes
     * @param collaborators
     *            collaborators
     * @throws PAException
     */
    public void validateProprietaryUpdate(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators) throws PAException {

        StringBuilder errorMsg = new StringBuilder();
        validateLeadOrgTrialIdLength(leadOrgID);
        validatePhasePurposeAndTemplateDocument(studyProtocolDTO,
                new ArrayList<DocumentDTO>(), nctID, errorMsg);
        validateRegAuthorityExistence(regAuthDTO, errorMsg);
        validateLeadOrgForProprietary(leadOrgID, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAException(errorMsg.toString());
        }

    }

    /**
     * @param studyProtocolDTO
     * @param nctID
     * @param leadOrgDTO
     * @param leadOrgID
     * @param regAuthDTO
     * @param errorMsg
     * @throws PAException
     */
    private void validateProprietaryCreateOrUpdate(
            StudyProtocolDTO studyProtocolDTO, StudySiteDTO nctID,
            OrganizationDTO leadOrgDTO, StudySiteDTO leadOrgID,
            StudyRegulatoryAuthorityDTO regAuthDTO, StringBuilder errorMsg)
            throws PAException {
        if (leadOrgDTO == null) {
            errorMsg.append("Lead organization is required");
        }
        validatePhasePurposeAndTemplateDocument(studyProtocolDTO,
                new ArrayList<DocumentDTO>(), nctID, errorMsg);
        validateLeadOrgForProprietary(leadOrgID, errorMsg);
        validateRegAuthorityExistence(regAuthDTO, errorMsg);
        validateAccrualDiseaseCodeSystem(studyProtocolDTO, errorMsg);
    }

    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }
}
