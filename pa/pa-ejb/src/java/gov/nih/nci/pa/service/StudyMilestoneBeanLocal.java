/**
 *
 */
package gov.nih.nci.pa.service;

import static gov.nih.nci.pa.enums.StudyInboxTypeCode.VALIDATION;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyMilestoneConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyMilestoneSortCriterion;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceCachingDecorator;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyMilestoneBeanLocal
    extends AbstractCurrentStudyIsoService<StudyMilestoneDTO, StudyMilestone, StudyMilestoneConverter>
    implements StudyMilestoneServicelocal {
    
     private static final String ENTIRE_TRIAL = "ENTIRE_TRIAL";

    private static final Logger LOG = Logger.getLogger(StudyMilestoneBeanLocal.class);
    
    /** TRIAL_SUMMARY_REPORT stop search milestones. **/
    private static final Set<MilestoneCode> TSS_STOP_SEARCH = EnumSet.complementOf(EnumSet
        .of(MilestoneCode.TRIAL_SUMMARY_REPORT, MilestoneCode.TRIAL_SUMMARY_FEEDBACK,
            MilestoneCode.INITIAL_ABSTRACTION_VERIFY, MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION,
            MilestoneCode.LATE_REJECTION_DATE));

    /** TRIAL_SUMMARY_FEEDBACK stop search milestones. **/
    private static final Set<MilestoneCode> TSF_STOP_SEARCH = EnumSet.of(MilestoneCode.SUBMISSION_ACCEPTED,
                                                                         MilestoneCode.TRIAL_SUMMARY_FEEDBACK);
    
    /** LATE_REJECTION_DATE stop search milestones. **/
    private static final Set<MilestoneCode> LRD_STOP_SEARCH = EnumSet.noneOf(MilestoneCode.class);
    
    private static final String TSR = "TSR_";
    private static final String EXTENSION_RTF = ".rtf";

    @EJB
    private AbstractionCompletionServiceLocal abstractionCompletionService;
    
    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService;
    
    @EJB
    private MailManagerServiceLocal mailManagerService;
    
    @EJB
    private StudyInboxServiceLocal studyInboxService;

    @EJB
    private StudyOnholdServiceLocal studyOnholdService;
    
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    
    @EJB
    private TSRReportGeneratorServiceLocal tsrReportGeneratorService;   
    
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    
    @EJB 
    private DocumentServiceLocal documentService;

    @EJB
//    @IgnoreDependency
    private TrialRegistrationServiceLocal trialRegistrationService;
    
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    /** For testing purposes only. Set to false to bypass abstraction validations. */
    private boolean validateAbstractions = true;
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryOrderClause() {
        return " order by alias.milestoneDate,alias.id";
    }

    /**
     * @param dto dto
     * @return created dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
        //PO-2961:The order of the below calls is significant.
        //The emails must be sent after creation has happened. - aevansel, 12/21/2010.
        StudyMilestoneDTO workDto = businessRules(dto);
        StudyMilestoneDTO resultDto = super.create(workDto);
        createDocumentWorkflowStatuses(resultDto);
        updateRecordVerificationDates(resultDto);
        resetProcessingPriority(resultDto);
        createReadyForTSRMilestone(resultDto);
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
        if (BlConverter.convertToBoolean(sp.getProprietaryTrialIndicator())) {
            createSentTSRMilestone(resultDto);
        }
        // Send TSR e-mail for the appropriate milestone
        attachTSRToTrialDocs(workDto);
        
        if (!BlConverter.convertToBoolean(sp.getProprietaryTrialIndicator())) {
             sendTSREmail(workDto);
        }
        rejectAmendmentAndSendLateRejectionEmail(workDto);
        return resultDto;
    }

    private void resetProcessingPriority(StudyMilestoneDTO dto) {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter
                .convertCdToString(dto.getMilestoneCode()));
        if (newCode == MilestoneCode.TRIAL_SUMMARY_REPORT) {
            Ii spIi = dto.getStudyProtocolIdentifier();
            Session session = PaHibernateUtil.getCurrentSession();
            session.createQuery(
                    "update " + StudyProtocol.class.getSimpleName()
                            + " set processingPriority=2 where id="
                            + IiConverter.convertToLong(spIi)).executeUpdate();
        }
    }

    /**
     * @param ii index of milestone
     * @throws PAException exception
     */
    @Override
    public void delete(Ii ii) throws PAException {
        throw new PAException("The delete() method in the StudyMilestoneService has been disabled.");
    }

    /**
     * @param dto dto
     * @return updated dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO update(StudyMilestoneDTO dto) throws PAException {
        throw new PAException("The update() method in the StudyMilestoneService has been disabled.");
    }

    private DocumentWorkflowStatusCode getLatestOffholdStatus(Ii studyProtocolIi) throws PAException {
        DocumentWorkflowStatusDTO dw = documentWorkflowStatusService.getLatestOffholdStatus(studyProtocolIi);
        return (dw == null) ? null : DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dw
            .getStatusCode()));
    }
    
    private DocumentWorkflowStatusCode getPreviousStatus(Ii studyProtocolIi) throws PAException {
        DocumentWorkflowStatusDTO dw = documentWorkflowStatusService.getPreviousStatus(studyProtocolIi);
        return (dw == null) ? null : DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dw
            .getStatusCode()));
    }
    

    private StudyMilestoneDTO businessRules(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        Ii spIi = dto.getStudyProtocolIdentifier();
        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(spIi);
        checkRequiredDataRules(dto);
        checkDateRules(dto, existingDtoList);
        checkLateRejectionRules(dto);
        checkOnHoldRules(dto, newCode);
        checkInboxRules(dto, newCode);
        checkUniquenessRules(newCode, existingDtoList);
        checkMilestoneSpecificRules(newCode, existingDtoList);
        checkDocumentWorkflowStatusRules(dto, newCode);
        checkAbstractionsRules(dto, newCode);
        checkTerminationRules(newCode, existingDtoList, spIi);
        return dto;
    }

    private void checkRequiredDataRules(StudyMilestoneDTO dto) throws PAException {
        if (ISOUtil.isCdNull(dto.getMilestoneCode())) {
            throw new PAException("Milestone code is required.");
        }
    }

    private void checkDateRules(StudyMilestoneDTO dto, List<StudyMilestoneDTO> existingDtoList) throws PAException {
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getMilestoneDate());
        if (newDate == null) {
            throw new PAException("Milestone date is required.");
        }
        if (newDate.after(new Timestamp(new Date().getTime()))) {
            throw new PAException("Milestone dates may not be in the future.");
        }
        if (!existingDtoList.isEmpty()) {
            StudyMilestoneDTO last = existingDtoList.get(existingDtoList.size() - 1);
            Timestamp lastDate = TsConverter.convertToTimestamp(last.getMilestoneDate());
            if (lastDate.after(newDate)) {
                String msg = "A milestone cannot predate existing milestones. The prior milestone date is {0}.";
                String lastMileStoneDate = PAUtil.normalizeDateStringWithTime(lastDate.toString());
                throw new PAException(MessageFormat.format(msg, lastMileStoneDate));
            }
        }
    }

    private void checkLateRejectionRules(StudyMilestoneDTO dto)
            throws PAException {
        if (MilestoneCode.LATE_REJECTION_DATE.getCode().equalsIgnoreCase(
                dto.getMilestoneCode().getCode())
                && ISOUtil.isStNull(dto.getCommentText())) {

            throw new PAException("Milestone Comment is required.");
        }
    }

    private void checkOnHoldRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        if (!newCode.isAllowedIfOnhold()
                && BlConverter.convertToBool(studyOnholdService.isOnhold(dto.getStudyProtocolIdentifier()))) {
            String msg = "The milestone \"{0}\" cannot be recorded if there is an active on-hold record.";
            throw new PAException(MessageFormat.format(msg, newCode.getCode()));
        }
    }

    private void checkInboxRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        if (!newCode.isAllowedIfInBox()) {
            List<StudyInboxDTO> listInboxDTO = studyInboxService.getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyInboxDTO inboxDto : listInboxDTO) {
                String strCloseDate = IvlConverter.convertTs().convertHighToString(inboxDto.getInboxDateRange());
                if (StringUtils.isEmpty(strCloseDate)) {
                    String msg;
                    final StudyInboxTypeCode codeEnum = CdConverter
                            .convertCdToEnum(StudyInboxTypeCode.class,
                                    inboxDto.getTypeCode());
                    switch (codeEnum != null ? codeEnum : VALIDATION) {
                    case UPDATE:
                        msg = "There are update(s) pending acknowledgement in the Trial History Section and hence "
                                + "\"{0}\" cannot be completed at this stage";
                        break;
                    case VALIDATION:
                        msg = "The milestone \"{0}\" cannot be recorded if there is an active Inbox record.";
                        break;
                    default:
                        msg = "The milestone \"{0}\" cannot be recorded if there is an active Inbox record or "
                                + "update waiting for aknowledgement.";
                        break;
                    
                    }
                    throw new PAException(MessageFormat.format(msg, newCode.getCode()));
                }
            }
        }
    }

    private void checkUniquenessRules(MilestoneCode newCode, List<StudyMilestoneDTO> existingDtoList)
            throws PAException {
        if (newCode.isUnique()) {
            for (StudyMilestoneDTO edto : existingDtoList) {
                if (newCode.getCode().equals(edto.getMilestoneCode().getCode())) {
                    String msg = "The milestone \"{0}\" must be unique.  It was previously recorded on {1}.";
                    String lastMileStoneDate = PAUtil.normalizeDateString(TsConverter
                        .convertToTimestamp(edto.getMilestoneDate()).toString());
                    throw new PAException(MessageFormat.format(msg, newCode.getCode(), lastMileStoneDate));
                }
            }
        }
    }
    
    private void checkTerminationRules(MilestoneCode newCode,
            List<StudyMilestoneDTO> existingDtoList, Ii studyProtocolIi)
            throws PAException {
        DocumentWorkflowStatusCode docStatus = getLatestOffholdStatus(studyProtocolIi);
        if (newCode != MilestoneCode.SUBMISSION_REACTIVATED) {
            List<MilestoneCode> codes = getExistingMilestones(existingDtoList);
            if (DocumentWorkflowStatusCode.SUBMISSION_TERMINATED == docStatus
                    && codes.contains(MilestoneCode.SUBMISSION_TERMINATED)) {
                throw new PAException(
                        "'Submission Terminated Date' milestone can only be followed by "
                                + "'Submission Reactivated Date' milestone.");
            }
        } else if (DocumentWorkflowStatusCode.SUBMISSION_TERMINATED != docStatus) {
            throw new PAException(
                    "'Submission Reactivated Date' milestone can only be added when the submission is terminated.");
        }

    }

    private void checkDocumentWorkflowStatusRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
        DocumentWorkflowStatusCode dwStatus = getLatestOffholdStatus(dto.getStudyProtocolIdentifier());
        if (newCode != MilestoneCode.SUBMISSION_RECEIVED && newCode != MilestoneCode.SUBMISSION_ACCEPTED
                && newCode != MilestoneCode.SUBMISSION_REJECTED && !newCode.isValidDwfStatus(dwStatus)) {
            StringBuilder errMsg = new StringBuilder("The processing status must be ");
            int iSize = newCode.getValidDwfStatuses().size();
            for (int x = 0; x < iSize; x++) {
                errMsg.append("'" + newCode.getValidDwfStatuses().get(x).getCode() + "'");
                if ((iSize == 2) && (x == 0)) {
                    errMsg.append(" or ");
                }
                if ((iSize > 2) && (x < iSize - 2)) {
                    errMsg.append(", ");
                }
                if ((iSize > 2) && (x == iSize - 2)) {
                    errMsg.append(", or ");
                }
            }
            errMsg.append(" when entering the milestone '");
            errMsg.append(newCode.getCode());
            errMsg.append("'.  The current processing status is ");
            errMsg.append(((dwStatus == null) ? "null." : "'" + dwStatus.getCode() + "'."));
            throw new PAException(errMsg.toString());
        }
    }

    private void checkAbstractionsRules(StudyMilestoneDTO dto, MilestoneCode newCode) throws PAException {
      if (dto != null) {
        if (validateAbstractions && newCode.isValidationTrigger()) {
            if (abstractionCompletionService == null) {
                throw new PAException("Error injecting reference to AbstractionCompletionService.");
            }
            List<AbstractionCompletionDTO> errorList = abstractionCompletionService.validateAbstractionCompletion(dto
                .getStudyProtocolIdentifier());
            if (!errorList.isEmpty() && hasAnyAbstractionErrors(errorList)) {
                String msg = "The milestone \"{0}\" can only be recorded if the abstraction is valid.  There is a"
                        + " problem with the current abstraction.  Select 'Abstraction Validation' under 'Completion'"
                        + " menu to view details.";
                throw new PAException(MessageFormat.format(msg, newCode.getCode()));
            }
        }
      }
    }

    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        for (AbstractionCompletionDTO absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                return true;
            }
        }
        return false;
    }

    private void checkReadyForTSRMilestone(List<MilestoneCode> milestones) throws PAException {
            if (!canCreateReadyForTSRMilestone(milestones, null)) {
                String msg = "\"{0}\" can not be created at this stage.";
                throw new PAException(MessageFormat.format(msg, MilestoneCode.READY_FOR_TSR.getCode()));
        }
    }

    private void checkMilestoneSpecificRules(MilestoneCode newCode, List<StudyMilestoneDTO> existingDtoList)
            throws PAException {
        List<MilestoneCode> milestones = getExistingMilestones(existingDtoList);
        switch (newCode) {
        case ADMINISTRATIVE_PROCESSING_START_DATE:
        case ADMINISTRATIVE_PROCESSING_COMPLETED_DATE:
        case ADMINISTRATIVE_READY_FOR_QC:
        case ADMINISTRATIVE_QC_START:
        case ADMINISTRATIVE_QC_COMPLETE:
        case SCIENTIFIC_PROCESSING_START_DATE:
        case SCIENTIFIC_PROCESSING_COMPLETED_DATE:
        case SCIENTIFIC_READY_FOR_QC:
        case SCIENTIFIC_QC_START:
        case SCIENTIFIC_QC_COMPLETE:
            checkProcessAndQC(milestones, newCode);
            break;
        case TRIAL_SUMMARY_REPORT:
            checkPreRequisite(milestones, newCode, TSS_STOP_SEARCH, MilestoneCode.READY_FOR_TSR);
            break;
        case TRIAL_SUMMARY_FEEDBACK:
            checkPreRequisite(milestones, newCode, TSF_STOP_SEARCH, MilestoneCode.TRIAL_SUMMARY_REPORT);
            break;
        case LATE_REJECTION_DATE:
            checkPreRequisite(milestones, newCode, LRD_STOP_SEARCH, MilestoneCode.SUBMISSION_ACCEPTED);
            break;
        case READY_FOR_TSR:
            checkReadyForTSRMilestone(milestones);
            break;

        default:
            break;
        }
    }

    /**
     * Check the milestones in the processing and QC branches.
     * @param milestones The list of all existing milestones
     * @param milestone The new milestone to check. It must be one of the administrative or scientific processing or QC
     *        milestones.
     * @throws PAException if the given milestone is not acceptable in the current state.
     */
    private void checkProcessAndQC(List<MilestoneCode> milestones, MilestoneCode milestone) throws PAException {
        List<MilestoneCode> mainSequence = null;
        List<MilestoneCode> altSequence = null;
        
        if (MilestoneCode.ADMIN_SEQ.contains(milestone)) {
            mainSequence = MilestoneCode.ADMIN_SEQ;
            altSequence = MilestoneCode.SCIENTIFIC_SEQ;
        } else {
            mainSequence = MilestoneCode.SCIENTIFIC_SEQ;
            altSequence = MilestoneCode.ADMIN_SEQ;
        }
        int milestoneIndex = mainSequence.indexOf(milestone);
        List<MilestoneCode> predecessors = new ArrayList<MilestoneCode>();
        if (milestoneIndex > 0) {
            predecessors.add(mainSequence.get(milestoneIndex - 1));
        } else {
            predecessors.add(MilestoneCode.SUBMISSION_ACCEPTED);            
            predecessors.add(MilestoneCode.TRIAL_SUMMARY_FEEDBACK);
        }
        for (int i = milestones.size() - 1; i >= 0; i--) {
            MilestoneCode current = milestones.get(i);
            if (predecessors.contains(current)) {
                return;
            }
            if (altSequence.contains(current)) {
                continue;
            }
            int currentIndex = mainSequence.indexOf(current);
            if (currentIndex >= milestoneIndex) {
                String msg = "\"{0}\" already reached.";
                throw new PAException(MessageFormat.format(msg, milestone.getCode()));
            }
            String msg = "\"{0}\" can not be reached at this stage.";
            throw new PAException(MessageFormat.format(msg, milestone.getCode()));
        }
        String msg = "\"{0}\" can not be reached at this stage.";
        throw new PAException(MessageFormat.format(msg, milestone.getCode()));
    }

    /**
     * @param milestones
     */
    private void removeSubmissionTerminationAndReactivation(
            List<MilestoneCode> milestones) {
        // get rid of SUBMISSION_TERMINATED->SUBMISSION_REACTIVATED milestones.
        // As per PO-2899, they can be inserted anywhere within the chain and
        // mess up
        // the logic.
        while (milestones.indexOf(MilestoneCode.SUBMISSION_TERMINATED) >= 0
                && milestones.indexOf(MilestoneCode.SUBMISSION_TERMINATED) == milestones
                        .indexOf(MilestoneCode.SUBMISSION_REACTIVATED) - 1) {
            milestones.remove(milestones
                    .indexOf(MilestoneCode.SUBMISSION_TERMINATED));
            milestones.remove(milestones
                    .indexOf(MilestoneCode.SUBMISSION_REACTIVATED));
        }
    }

    private void checkPreRequisite(List<MilestoneCode> milestones, MilestoneCode milestone,
            Set<MilestoneCode> stopSearchMilestones, MilestoneCode preRequisite) throws PAException {        
        for (int i = milestones.size() - 1; i >= 0; i--) {
            MilestoneCode current = milestones.get(i);
            if (current.equals(preRequisite)) {
                return;
            }
            if (stopSearchMilestones.contains(current)) {
                break;
            }
        }
        String msg = "\"{0}\" is a prerequisite to \"{1}\".";
        throw new PAException(MessageFormat.format(msg, preRequisite.getCode(), milestone.getCode()));
    }

    private List<MilestoneCode> getExistingMilestones(List<StudyMilestoneDTO> existingDTOs) {
        List<MilestoneCode> existingMilestones = new ArrayList<MilestoneCode>();
        if (existingDTOs != null) {
            for (StudyMilestoneDTO edto : existingDTOs) {
                existingMilestones.add(MilestoneCode.getByCode(edto.getMilestoneCode().getCode()));
            }
        }
        removeSubmissionTerminationAndReactivation(existingMilestones);
        return existingMilestones;
    }

    private void createDocumentWorkflowStatuses(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        DocumentWorkflowStatusCode dwStatus = getLatestOffholdStatus(dto.getStudyProtocolIdentifier());
        DocumentWorkflowStatusCode prevDwStatus = getPreviousStatus(dto.getStudyProtocolIdentifier());
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());

        if (newCode == MilestoneCode.SUBMISSION_RECEIVED && sp.getSubmissionNumber().getValue().intValue() == 1) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.SUBMITTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_RECEIVED && sp.getSubmissionNumber().getValue().intValue() > 1) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_ACCEPTED
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ACCEPTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ACCEPTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_REJECTED
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_TERMINATED
                && canTransition(dwStatus, DocumentWorkflowStatusCode.SUBMISSION_TERMINATED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED , dto);
        }
        if (newCode == MilestoneCode.SUBMISSION_REACTIVATED
                && prevDwStatus != null
                && canTransition(dwStatus, prevDwStatus)) {
            createDocumentWorkflowStatus(
                    prevDwStatus, dto);
        }
        if (newCode == MilestoneCode.READY_FOR_TSR && dwStatus != null
                && DocumentWorkflowStatusCode.ACCEPTED == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTED)) {
            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTED, dto);
        }

        if (newCode == MilestoneCode.INITIAL_ABSTRACTION_VERIFY
                && (dwStatus != null)
                && (DocumentWorkflowStatusCode.ABSTRACTED == dwStatus
                        || DocumentWorkflowStatusCode.VERIFICATION_PENDING == dwStatus)) {

            if (milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto.getStudyProtocolIdentifier())) {
                if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)) {
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE , dto);
                }
            } else {
                if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE)) {
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE , dto);
                }
            }
        }

        if (newCode == MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)
                && milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto.getStudyProtocolIdentifier())) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE, dto);
        }


        if (newCode == MilestoneCode.TRIAL_SUMMARY_REPORT
                && (dwStatus != null)
                && DocumentWorkflowStatusCode.ABSTRACTED == dwStatus
                && canTransition(dwStatus, DocumentWorkflowStatusCode.VERIFICATION_PENDING)) {

            createDocumentWorkflowStatus(DocumentWorkflowStatusCode.VERIFICATION_PENDING, dto);
        }
        if (newCode == MilestoneCode.LATE_REJECTION_DATE
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {
            if (sp.getSubmissionNumber().getValue().intValue() == 1) {
                createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
            }            
        }
    }

    private void createDocumentWorkflowStatus(DocumentWorkflowStatusCode dwf, StudyMilestoneDTO dto) 
            throws PAException {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(dwf));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(TsConverter.convertToTimestamp(dto
                                                                            .getMilestoneDate()), null));
        dwfDto.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
        if (dto.determineCommentText() != null) {
            dwfDto.setCommentText(dto.determineCommentText());
        }
        documentWorkflowStatusService.create(dwfDto);
    }

    private void updateRecordVerificationDates(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        if (newCode == MilestoneCode.READY_FOR_TSR) {
            DocumentWorkflowStatusCode dwStatus = getLatestOffholdStatus(dto.getStudyProtocolIdentifier());
            if ((dwStatus != null) && DocumentWorkflowStatusCode.ACCEPTED == dwStatus) {
                updateRecordVerificationDate(dto);
            }
        }
        if (newCode == MilestoneCode.INITIAL_ABSTRACTION_VERIFY
                || newCode == MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION) {
            updateRecordVerificationDate(dto);
        }
    }

    private void updateRecordVerificationDate(StudyMilestoneDTO dto) throws PAException {
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(dto.getStudyProtocolIdentifier());
        sp.setRecordVerificationDate(dto.getMilestoneDate());
        studyProtocolService.updateStudyProtocol(sp);
    }

    /**
     * Test if the given milestone exists for the given stdy protocol. 
     * @param milestoneCode The milestone to search for
     * @param spIi The study protocol Ii
     * @return true i the given milestone exist in the given study protocol.
     * @throws PAException in error
     */
    boolean milestoneExists(MilestoneCode milestoneCode, Ii spIi) throws PAException {
        List<StudyMilestoneDTO> smList = getByStudyProtocol(spIi);
        for (StudyMilestoneDTO sm : smList) {
            MilestoneCode tempCode = MilestoneCode.getByCode(CdConverter.convertCdToString(sm.getMilestoneCode()));
            if (tempCode.equals(milestoneCode)) {
                return true;
            }
        }
        return false;
    }

    private void createReadyForTSRMilestone(StudyMilestoneDTO dto) throws PAException {
        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        List<MilestoneCode> mileStones = getExistingMilestones(existingDtoList);
        if (canCreateReadyForTSRMilestone(mileStones, dto)) {
            StudyMilestoneDTO readyForTSR = new StudyMilestoneDTO();
            readyForTSR.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.READY_FOR_TSR));
            readyForTSR.setMilestoneDate(TsConverter.convertToTs(new Date()));
            readyForTSR.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
            create(readyForTSR);
        }
    }
    
    private void createSentTSRMilestone(StudyMilestoneDTO dto) throws PAException {
        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        List<MilestoneCode> mileStones = getExistingMilestones(existingDtoList);
        if (canCreateTSRSentMilestone(mileStones)) {
            StudyMilestoneDTO sentTSR = new StudyMilestoneDTO();
            sentTSR.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_REPORT));
            sentTSR.setMilestoneDate(TsConverter.convertToTs(new Date()));
            sentTSR.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
            create(sentTSR);
        }
    }

    private boolean canCreateReadyForTSRMilestone(List<MilestoneCode> mileStones, StudyMilestoneDTO dto) {
        boolean admin = false;
        boolean scientific = false;
        for (int i = mileStones.size() - 1; i >= 0; i--) {
            switch (mileStones.get(i)) {
            case ADMINISTRATIVE_QC_COMPLETE:
                admin = true;
                break;
            case SCIENTIFIC_QC_COMPLETE:
                scientific = true;
                break;
            case ADMINISTRATIVE_PROCESSING_START_DATE:
            case ADMINISTRATIVE_PROCESSING_COMPLETED_DATE:
            case ADMINISTRATIVE_READY_FOR_QC:
            case ADMINISTRATIVE_QC_START:
            case SCIENTIFIC_PROCESSING_START_DATE:
            case SCIENTIFIC_PROCESSING_COMPLETED_DATE:
            case SCIENTIFIC_READY_FOR_QC:
            case SCIENTIFIC_QC_START:
                 break;
            default:
                return false;
            }
            if (admin && scientific) {
                try {
                   checkAbstractionsRules(dto, MilestoneCode.READY_FOR_TSR);
                } catch (PAException e) {
                   LOG.error(e.getMessage());
                   dto.setErrorMessage(StConverter.convertToSt(e.getMessage()));
                   return false;
               } 
                return true;
              }
        }
        return false;
    }

    private boolean canCreateTSRSentMilestone(List<MilestoneCode> mileStones) {
        boolean admin = false;
        boolean scientific = false;        
        for (int i = mileStones.size() - 1; i >= 0; i--) {
            switch (mileStones.get(i)) {
            case ADMINISTRATIVE_QC_COMPLETE:
                admin = true;
                break;
            case SCIENTIFIC_QC_COMPLETE:
                scientific = true;
                break;
            case ADMINISTRATIVE_PROCESSING_START_DATE:
            case ADMINISTRATIVE_PROCESSING_COMPLETED_DATE:
            case ADMINISTRATIVE_READY_FOR_QC:
            case ADMINISTRATIVE_QC_START:
            case SCIENTIFIC_PROCESSING_START_DATE:
            case SCIENTIFIC_PROCESSING_COMPLETED_DATE:
            case SCIENTIFIC_READY_FOR_QC:
            case SCIENTIFIC_QC_START:
            case READY_FOR_TSR:
                 break;
            default:
                return false;
            }
            if (admin && scientific) {
                return true;
            }
        }
        return false;
    }

    private void sendTSREmail(StudyMilestoneDTO workDto) throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(
                CdConverter.convertCdToString(workDto.getMilestoneCode()));
        if ((MilestoneCode.TRIAL_SUMMARY_REPORT.equals(milestoneCode))) {
            try {
                mailManagerService.sendTSREmail(workDto.getStudyProtocolIdentifier());
            } catch (PAException e) {
                throw new PAException(workDto.getMilestoneCode().getCode() + "' could not "
                        + "be recorded as sending the TSR report to the submitter  failed.", e);
            }
        }
    }
    
    /**
     * Automatically attach TSRs to trial docs whenever a "TSR Sent Date" milestone is recorded.
     * @param studyMilestoneDTO
     * @throws PAException
     * @see https://tracker.nci.nih.gov/browse/PO-2106
     */
    void attachTSRToTrialDocs(StudyMilestoneDTO studyMilestoneDTO)
            throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(CdConverter
                .convertCdToString(studyMilestoneDTO.getMilestoneCode()));
        if ((MilestoneCode.TRIAL_SUMMARY_REPORT.equals(milestoneCode))) {
            try {
                final Ii studyID = studyMilestoneDTO.getStudyProtocolIdentifier();
                StudyProtocolQueryDTO spDTO = protocolQueryService
                        .getTrialSummaryByStudyProtocolId(IiConverter
                                .convertToLong(studyID));
                if (spDTO != null) {
                    String amendNum = spDTO.getAmendmentNumber();
                    String discriminator = "_"
                            + (StringUtils.isBlank(amendNum) ? "O"
                                    : ("A" + amendNum));
                    final String nciID = spDTO.getNciIdentifier();
                    String filename = TSR + nciID + "_"
                            + DateFormatUtils.format(new Date(), PAConstants.TSR_DATE_FORMAT)
                            + discriminator + EXTENSION_RTF;
                    ByteArrayOutputStream tsrStream = getTsrReportGeneratorService()
                            .generateRtfTsrReport(studyID);
                    attachTSRToTrialDocs(studyMilestoneDTO, filename, tsrStream);
                }
            } catch (Exception e) {                
                throw new PAException("Unable to add TSR to the trial documents.", e);
            }
        }
    }
    

    /**
     * Automatically attach TSRs to trial docs whenever a "TSR Sent Date" milestone is recorded.
     * @param studyMilestoneDTO
     * @param filename
     * @param tsrStream
     * @throws PAException 
     */
    void attachTSRToTrialDocs(StudyMilestoneDTO studyMilestoneDTO,
            String filename, ByteArrayOutputStream tsrStream) throws PAException {
        DocumentDTO docDto = new DocumentDTO();
        docDto.setStudyProtocolIdentifier(studyMilestoneDTO.getStudyProtocolIdentifier());
        docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
        docDto.setText(EdConverter.convertToEd(tsrStream.toByteArray()));
        docDto.setFileName(StConverter.convertToSt(filename));
        documentService.create(docDto);        
    }

    private boolean canTransition(DocumentWorkflowStatusCode dwStatus, DocumentWorkflowStatusCode newCode)
    throws PAException {
        boolean canTransition = false;
        if (dwStatus.canTransitionTo(newCode)) {
            canTransition = true;
        } else {
            throw new PAException("Invalid DocumentWorkflow status transition from '" + dwStatus.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        return canTransition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyMilestoneDTO> search(StudyMilestoneDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyMilestoneDTO should not be null.");
        }
        StudyMilestone criteria = Converters.get(StudyMilestoneConverter.class).convertFromDtoToDomain(dto);

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyMilestone> params = new PageSortParams<StudyMilestone>(maxLimit,
                pagingParams.getOffset(), StudyMilestoneSortCriterion.STUDY_MILESTONE_ID, false);
        List<StudyMilestone> studyMilestoneList =
            search(new AnnotatedBeanSearchCriteria<StudyMilestone>(criteria), params);

        if (studyMilestoneList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(studyMilestoneList);
    }
    
    @Override
    public void deleteMilestoneByCodeAndStudy(MilestoneCode status, Ii spIi)
            throws PAException {
        Long spID = IiConverter.convertToLong(spIi);
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        Query query = session.createQuery("delete from StudyMilestone where "
               + "studyProtocol.id =:spID and milestoneCode =:status");
        query.setParameter("spID", spID);
        query.setParameter("status", status);
        query.executeUpdate();
    }
    @Override
    public void updateMilestoneCodeCommentWithDateAndUser(StudyMilestoneDTO dto, 
               String reason, String submitterFullName) throws PAException {
       if (!ISOUtil.isStNull(dto.getCommentText())) {
       dto.setCommentText(StConverter.convertToSt(StringUtils.defaultString(StConverter
            .convertToString(dto.getCommentText())) + " " + TsConverter
            .convertToTimestamp(dto.getMilestoneDate()) + " \n\n" + reason 
              + " " + new Timestamp(System.currentTimeMillis()).toString() + " " + submitterFullName));
       } else {
           dto.setCommentText(StConverter.convertToSt(reason 
                 + " " + new Timestamp(System.currentTimeMillis()).toString() + " " + submitterFullName));
       }
       super.update(dto);
    }

    private void rejectAmendmentAndSendLateRejectionEmail(
            StudyMilestoneDTO workDto) throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(CdConverter
                .convertCdToString(workDto.getMilestoneCode()));
        if ((MilestoneCode.LATE_REJECTION_DATE.equals(milestoneCode))) {
            boolean rejectEntireTrial = ENTIRE_TRIAL.equals(CdConverter
                    .convertCdToString(workDto.getLateRejectBehavior()));
            if (rejectEntireTrial) {
                rejectTrialBasedOnLateRejectionMilestone(workDto);
            } else {
                rejectOriginalSubmissionOrLatestAmendment(workDto,
                        milestoneCode);
            }
        }
    }

    /**
     * @param workDto
     * @param milestoneCode
     * @throws HibernateException
     * @throws PAException
     */
    private void rejectOriginalSubmissionOrLatestAmendment(
            StudyMilestoneDTO workDto, MilestoneCode milestoneCode)
            throws PAException {
        try {
            StudyProtocolDTO sp = studyProtocolService
                    .getStudyProtocol(workDto
                            .getStudyProtocolIdentifier());
            // For the case where the milestone is late rejection date
            // and trial is amended, we should follow
            // the same workflow as trial amendment rejection.
            if (sp.getSubmissionNumber().getValue().intValue() > 1) {
                StudyProtocolQueryDTO spDTO = protocolQueryService
                        .getTrialSummaryByStudyProtocolId(IiConverter
                                .convertToLong(sp.getIdentifier()));
                PaHibernateUtil.getCurrentSession().flush();
                trialRegistrationService
                        .reject(sp.getIdentifier(),
                                workDto.getCommentText(),
                                workDto.getRejectionReasonCode(),
                                milestoneCode);
                String comment = workDto.determineCommentText() == null ? "Unknown Reason"
                        : workDto.determineCommentText().getValue();
                mailManagerService.sendAmendRejectEmail(spDTO, comment);
            } else {
                mailManagerService.sendRejectionEmail(workDto
                        .getStudyProtocolIdentifier());
            }
        } catch (PAException e) {
            throw new PAException(workDto.getMilestoneCode().getCode()
                    + "' could not " + "be recorded.", e);
        }
    }
    

    private void rejectTrialBasedOnLateRejectionMilestone(
            StudyMilestoneDTO workDto) throws PAException {
        createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , workDto);
        mailManagerService.sendRejectionEmail(workDto
                .getStudyProtocolIdentifier());
        
    }

    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceLocal abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }

    /**
     * @param documentWorkflowStatusService the documentWorkflowStatusService to set
     */
    public void setDocumentWorkflowStatusService(DocumentWorkflowStatusServiceLocal documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param studyInboxService the studyInboxService to set
     */
    public void setStudyInboxService(StudyInboxServiceLocal studyInboxService) {
        this.studyInboxService = studyInboxService;
    }

    /**
     * @param studyOnholdService the studyOnholdService to set
     */
    public void setStudyOnholdService(StudyOnholdServiceLocal studyOnholdService) {
        this.studyOnholdService = studyOnholdService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param validateAbstractions the validateAbstractions to set
     */
    public void setValidateAbstractions(boolean validateAbstractions) {
        this.validateAbstractions = validateAbstractions;
    }

    /**
     * @return the tsrReportGeneratorService
     */
    public TSRReportGeneratorServiceLocal getTsrReportGeneratorService() {
        return new TSRReportGeneratorServiceCachingDecorator(
                tsrReportGeneratorService);
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(
            TSRReportGeneratorServiceLocal tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }

    /**
     * @return the protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return the documentService
     */
    public DocumentServiceLocal getDocumentService() {
        return documentService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }
    
    /**
     * @param trialRegistrationService the trialRegistrationService to set
     */
    public void setTrialRegistrationService(
            TrialRegistrationServiceLocal trialRegistrationService) {
        this.trialRegistrationService = trialRegistrationService;
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }
    /**
     * 
     * @return the current paServiceUtils. 
     */
    public PAServiceUtils getPaServiceUtils() {
        return this.paServiceUtils;
    }

    /**
     * 
     * @return the trial registration service
     */
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return this.trialRegistrationService;
    }
    
}
