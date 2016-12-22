/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.convert.StudyOnholdConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.joda.time.DateTime;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class StudyOnholdBeanLocal extends AbstractStudyIsoService<StudyOnholdDTO, StudyOnhold, StudyOnholdConverter>
implements StudyOnholdServiceLocal {

  
    /** id for onholdReasonCode. */
    public static final int FN_REASON_CODE = 0;
    /** id for onholdDate.low. */
    public static final int FN_DATE_LOW = 1;
    /** id for onholdDate.high. */
    public static final int FN_DATE_HIGH = 2;
    
    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService;
    
    @EJB
    private ProtocolQueryServiceLocal protocolQueryServiceLocal;
    
    @EJB 
    private LookUpTableServiceRemote lookUpTableServiceRemote;
    
    @EJB 
    private MailManagerServiceLocal mailManagerSerivceLocal;
    
    @EJB
//    @IgnoreDependency
    private StudyMilestoneServicelocal studyMilestoneService;
    
    private static final Logger LOG = Logger.getLogger(StudyOnholdBeanLocal.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Bl isOnhold(Ii studyProtocolIi) throws PAException {
        Bl result = new Bl();
        List<StudyOnholdDTO> list = getByStudyProtocol(studyProtocolIi);
        for (StudyOnholdDTO dto : list) {
            if (IvlConverter.convertTs().convertHigh(dto.getOnholdDate()) == null) {
                result.setValue(true);
                return result;
            }
        }
        result.setValue(false);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnholdDTO create(StudyOnholdDTO dto) throws PAException {
        validationForCreation(dto);
        DocumentWorkflowStatusCode status = getDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        boolean statusOnHold = status == DocumentWorkflowStatusCode.ON_HOLD;
        boolean onHold = isOnhold(dto.getStudyProtocolIdentifier()).getValue();
        boolean newOnHold = IvlConverter.convertTs().convertHigh(dto.getOnholdDate()) == null;
        statusRulesForCreation(statusOnHold, onHold, newOnHold);
        if (newOnHold) {
            dto.setPreviousStatusCode(CdConverter.convertToCd(status));
        }
        String holdReasonCode = OnholdReasonCode.getByCode(
                CdConverter.convertCdToString(dto.getOnholdReasonCode())).getName();
        if (!holdReasonCode.equals(OnholdReasonCode.OTHER.getName())) {
            String holdReasonCategory = getReasonCategoryValue(holdReasonCode);
            dto.setOnHoldCategory(StConverter.convertToSt(holdReasonCategory));
        }
             
        StudyOnholdDTO result = super.create(dto);
        if (!onHold && newOnHold) {
            createDocumentWorkflowStatus(dto.getStudyProtocolIdentifier(), DocumentWorkflowStatusCode.ON_HOLD);
        }
        String[] reasonCodes = getOnHoldReminderReasons();
        OnholdReasonCode reason = OnholdReasonCode.getByCode(CdConverter.convertCdToString(dto.getOnholdReasonCode()));
        if (reason != null && ArrayUtils.contains(reasonCodes, reason.name())) {
            sendOnHoldEmail(dto);
        }
        return result;
    }

    private String[] getOnHoldReminderReasons() throws PAException {
        String[] codes = lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.reminder.reasons").trim()
                .split(",");
        return codes;
    }
    
    /**
     * Validates the input for creation.
     * @param dto The StudyOnholdDTO to check
     * @throws PAException in case of error
     */
    void validationForCreation(StudyOnholdDTO dto) throws PAException {
        if (dto == null) {
            throw new PAException("No StudyOnholdDTO provided.");
        }
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("Study Protocol is required.");
        }
        setTimeIfToday(dto);
        reasonRules(dto);
        dateRules(dto);
    }
    
    /**
     * Gets the processing status of the given study protocol.
     * @param spIi The study protocol Id
     * @return The processing status of the given study protocol.
     * @throws PAException in case of error
     */
    DocumentWorkflowStatusCode getDocumentWorkflowStatus(Ii spIi) throws PAException {
        DocumentWorkflowStatusDTO dws =
                documentWorkflowStatusService.getCurrentByStudyProtocol(spIi);
        return CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class, dws.getStatusCode());
    }

    /**
     * Check the processing status rules for creation.
     * @param statusOnHold true if the current status is onHold
     * @param onHold true if there is a current onhold record
     * @param newOnHold true if the new record is onHold
     * @throws PAException in case of error
     */
    void statusRulesForCreation(boolean statusOnHold, boolean onHold, boolean newOnHold) throws PAException {
        if (statusOnHold != onHold) {
            throw new PAException("Processing status and on-hold records are inconsistent."
                    + " Please contact your system administrator.");
        }
        if (onHold && newOnHold) {
            throw new PAException("Study protocol is already on-hold.");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnholdDTO update(StudyOnholdDTO dto) throws PAException {
        validationForUpdate(dto);
        StudyOnholdDTO wrkDto = get(dto.getIdentifier());
        if (wrkDto != null) {
            wrkDto.getOnholdDate().setHigh(dto.getOnholdDate().getHigh());
            wrkDto.setProcessingLog(dto.getProcessingLog());
            setTimeIfToday(wrkDto);
            dateRules(wrkDto);
            DocumentWorkflowStatusCode status = getDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
            boolean statusOnHold = status == DocumentWorkflowStatusCode.ON_HOLD;
            boolean onHold = isOnhold(dto.getStudyProtocolIdentifier()).getValue();
            boolean newOnHold = IvlConverter.convertTs().convertHigh(dto.getOnholdDate()) == null;
            statusRulesForUpdate(statusOnHold, onHold, newOnHold);
            StudyOnholdDTO result = super.update(wrkDto);
            if (onHold != newOnHold) {
                DocumentWorkflowStatusCode newStatus =
                        (onHold) ? CdConverter.convertCdToEnum(DocumentWorkflowStatusCode.class,
                                                               wrkDto.getPreviousStatusCode())
                                : DocumentWorkflowStatusCode.ON_HOLD;
                createDocumentWorkflowStatus(wrkDto.getStudyProtocolIdentifier(), newStatus);
            }
            return result;
        } else {
            throw new PAException("On Hold record does not exist.");
        }

    }
    
    /**
     * Validates the input for update.
     * @param dto The StudyOnholdDTO to check
     * @throws PAException in case of error
     */
    void validationForUpdate(StudyOnholdDTO dto) throws PAException {
        if (dto == null) {
            throw new PAException("No StudyOnholdDTO provided.");
        }
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Identifier is required.");
        }
    }
    
    /**
     * Check the processing status rules for update.
     * @param statusOnHold true if the current status is onHold
     * @param onHold true if there is a current onhold record
     * @param newOnHold true if the new record is onHold
     * @throws PAException in case of error
     */
    void statusRulesForUpdate(boolean statusOnHold, boolean onHold, boolean newOnHold) throws PAException {
        if (statusOnHold != onHold) {
            throw new PAException("Processing status and on-hold records are inconsistent."
                    + " Please contact your system administrator.");
        }
    }

    /**
     * Adjust the timestamps of the given dto.
     * @param dto The StudyOnholdDTO
     */
    void setTimeIfToday(StudyOnholdDTO dto) {
        Timestamp now = new Timestamp(new DateTime().getMillis());
        Timestamp tsLow = IvlConverter.convertTs().convertLow(dto.getOnholdDate());
        Timestamp tsHigh = IvlConverter.convertTs().convertHigh(dto.getOnholdDate());
        if (tsLow != null && tsLow.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsLow = now;
        }
        if (tsHigh != null && tsHigh.equals(PAUtil.dateStringToTimestamp(PAUtil.today()))) {
            tsHigh = now;
        }
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(tsLow, tsHigh));
    }

    /**
     * Check the reason field of the given dto.
     * @param dto The StudyOnholdDTO
     * @throws PAException if a validation error occurs.
     */
    void reasonRules(StudyOnholdDTO dto) throws PAException {
        if (ISOUtil.isCdNull(dto.getOnholdReasonCode())) {
            throw new PAFieldException(FN_REASON_CODE, "The On-hold reason code is a required field.");
        }
    }

    /**
     * Check the dates of the given dto.
     * @param dto The StudyOnholdDTO
     * @throws PAException if a validation error occurs.
     */
    void dateRules(StudyOnholdDTO dto) throws PAException {
        Timestamp low = IvlConverter.convertTs().convertLow(dto.getOnholdDate());
        if (low == null) {
            throw new PAFieldException(FN_DATE_LOW, "On-hold date is required.");
        }        
        if (ISOUtil.isIiNull(dto.getIdentifier())
                && !DateUtils.isSameDay(low, getTodaysDate())) {
            throw new PAFieldException(FN_DATE_LOW,
                    "On-hold date must be today's date.");
        }
        Timestamp now = new Timestamp(getTodaysDate().getTime());
        Timestamp high = IvlConverter.convertTs().convertHigh(dto.getOnholdDate());
        if (high != null) {
            if (now.before(high)) {
                throw new PAFieldException(FN_DATE_HIGH, "Off-hold dates must be only past or current dates.");
            }
            if (high.before(low)) {
                throw new PAFieldException(FN_DATE_HIGH,
                        "Off-hold date must be bigger than on-hold date for the same on-hold record.");
            }
        }

    }
    
    /**
     * Creates a new processing status.
     * @param spIi The study protocol Ii
     * @param dwf The new status
     * @throws PAException if an error occurs.
     */
    void createDocumentWorkflowStatus(Ii spIi, DocumentWorkflowStatusCode dwf) 
            throws PAException {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(dwf));
        dwfDto.setStudyProtocolIdentifier(spIi);
        documentWorkflowStatusService.create(dwfDto);
    }

    /**
     * @param documentWorkflowStatusService the documentWorkflowStatusService to set
     */
    public void setDocumentWorkflowStatusService(DocumentWorkflowStatusServiceLocal documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processOnHoldTrials() throws Exception {
        try {
            LOG.info("On-hold reminders task kicked off.");
            List<StudyProtocolQueryDTO> studyDTOs = findOnHoldTrials();
            LOG.info("We have " + studyDTOs.size()
                    + " on-hold trials to check.");
            for (StudyProtocolQueryDTO studyDTO : studyDTOs) {
                if (DocumentWorkflowStatusCode.ON_HOLD.equals(studyDTO.getDocumentWorkflowStatusCode())) {
                    processOnHoldTrial(studyDTO);
               }
            }
        } catch (Exception e) {
            LOG.error(e, e);
            throw e;
        }
    }

    private void processOnHoldTrial(StudyProtocolQueryDTO studyDTO) {
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            // domain object is more convenient to work with.
            StudyProtocol studyProtocol = (StudyProtocol) session.get(
                    StudyProtocol.class, studyDTO.getStudyProtocolId());
            processOnHoldTrial(studyProtocol);
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    private void processOnHoldTrial(StudyProtocol studyProtocol)
            throws PAException {
        
        String[] codes = getOnHoldReminderReasons();
        
        List<StudyOnhold> onholds = new ArrayList<StudyOnhold>(
                studyProtocol.getStudyOnholds());    
        // make sure we have freshest on-holds first just in case.
        arrangeHoldsByID(onholds);
        
        for (StudyOnhold onhold : onholds) {
            if (onhold.getOnholdDate() != null
                    && onhold.getOffholdDate() == null
                    && ArrayUtils.contains(codes, onhold.getOnholdReasonCode()
                            .name())) {
                sendReminderOrTerminateSubmission(studyProtocol, onhold);
                break;
            }
        }       
    }
    
    private void sendOnHoldEmail(StudyOnholdDTO onHoldDto) throws PAException {
        StudyOnhold studyOnHold = new StudyOnholdConverter()
                .convertFromDtoToDomain(onHoldDto);
        Date deadline = calculateDeadLineDate(studyOnHold);
        mailManagerSerivceLocal.sendOnHoldEmail(studyOnHold.getStudyProtocol()
                .getId(), studyOnHold, deadline);
    }
    
    /**
     * This method calculates the On Hold Deadline date.
     * @param studyOnHold StudyOnHold
     * @return Deadline date
     * @throws PAException Exception to be thrown
     */
    private Date calculateDeadLineDate(StudyOnhold studyOnHold)
            throws PAException {
        int deadlineDays = Integer.parseInt(lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.deadline"));
        // protection from invalid configuration
        if (deadlineDays <= 0) {
            throw new IllegalArgumentException(
                    "Invalid configuration: trial.onhold.deadline property");
        }
        return DateUtils.addDays(studyOnHold.getOnholdDate(), deadlineDays);
    }

    private void sendReminderOrTerminateSubmission(StudyProtocol studyProtocol,
            StudyOnhold recentHold) throws PAException {
        
        int reminderFreq = Integer.parseInt(lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.reminder.frequency"));
        Date startDate = null;
        try {
            startDate = DateUtils.parseDate(lookUpTableServiceRemote
                    .getPropertyValue("trial.onhold.startdate"),
                    new String[] {PAUtil.DATE_FORMAT });
        } catch (ParseException e) {
            throw new PAException(
                    "Invalid trial.onhold.startdate configuration property.", e);
        }
        // protection from invalid configuration
        if (reminderFreq <= 0) {
            throw new IllegalArgumentException(
                    "Invalid configuration: trial.onhold.reminder.frequency property");
        }
        
        Date holdDate = recentHold.getOnholdDate();
        if (DateUtils.isSameDay(holdDate, startDate)
                || holdDate.after(startDate)) {
            Date deadline = calculateDeadLineDate(recentHold);
            Date today = getTodaysDate();
            if (today.after(deadline)) {
                terminateSubmission(studyProtocol, recentHold);
            } else if (isReminderDue(holdDate, reminderFreq)) {
                List<String> emails = mailManagerSerivceLocal
                        .sendOnHoldReminder(studyProtocol.getId(), recentHold,
                                deadline);
                updateOnHoldRecordWithReminder(emails, recentHold, today);
            }
        } 
    }

    private void updateOnHoldRecordWithReminder(List<String> emails,
            StudyOnhold recentHold, Date date) throws PAException {
        String entryTemplate = lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.reminder.logentry");
        String emailsString = StringUtils.join(emails, ", ");
        String text = entryTemplate.replace("${date}",
                DateFormatUtils.format(date, PAUtil.DATE_FORMAT)).replace(
                "${emails}", emailsString);
        recentHold.setProcessingLog(StringUtils.left(
                ObjectUtils.defaultIfNull(recentHold.getProcessingLog(), "")
                        + text, CommonsConstant.LEN_4096));

        Session session = PaHibernateUtil.getCurrentSession();
        session.saveOrUpdate(recentHold);
        session.flush();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private boolean isReminderDue(final Date holdDate, int reminderFreq) {
        // protection from infinite loop
        Date today = getTodaysDate();
        if (today.before(holdDate)) {
            LOG.error("On-hold date is before today's date??");
            return false;
        }

        Date date = holdDate;
        int bizDayCounter = 0;
        while (date.before(today) || DateUtils.isSameDay(date, today)) {
            if (bizDayCounter != 0 && bizDayCounter % reminderFreq == 0 && DateUtils.isSameDay(date, today)) {
                return true;
            }
            // move date one calendar day forward
            date = DateUtils.addDays(date, 1);
            // only increment bizDayCounter if we hit a business day
            if (PAUtil.isBusinessDay(date)) {
                bizDayCounter++;
            }
        }
        return false;
    }

    /**
     * @return
     */
    Date getTodaysDate() {
        return new Date();
    }

    /**
     * Terminate submission.
     * @param studyProtocol StudyProtocol
     * @param recentHold StudyOnhold
     * @throws PAException PAException
     */    
    private void terminateSubmission(StudyProtocol studyProtocol,
            StudyOnhold recentHold) throws PAException {
        String entryTemplate = lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.termination.logentry");
        String milestoneComment = lookUpTableServiceRemote
                .getPropertyValue("trial.onhold.termination.comment");
        String text = entryTemplate.replace("${date}",
                DateFormatUtils.format(getTodaysDate(), PAUtil.DATE_FORMAT));

        // Close hold.
        StudyOnholdDTO onholdDTO = new StudyOnholdConverter()
            .convertFromDomainToDto(recentHold);        
        onholdDTO.setOnholdDate(IvlConverter.convertTs().convertToIvl(
                recentHold.getOnholdDate(),
                new Timestamp(getTodaysDate().getTime())));
        onholdDTO.setProcessingLog(StConverter.convertToSt(StringUtils.left(
                ObjectUtils.defaultIfNull(recentHold.getProcessingLog(), "")
                + text, CommonsConstant.LEN_4096)));
        this.update(onholdDTO);

        // Add submission terminated milestone.
        StudyMilestoneDTO milestoneDTO = new StudyMilestoneDTO();
        milestoneDTO.setCommentText(StConverter.convertToSt(milestoneComment));
        milestoneDTO.setCreationDate(TsConverter.convertToTs(getTodaysDate()));
        milestoneDTO.setCreator(StConverter.convertToSt("unspecifieduser"));
        milestoneDTO.setMilestoneCode(CdConverter
                .convertToCd(MilestoneCode.SUBMISSION_TERMINATED));
        milestoneDTO.setMilestoneDate(TsConverter.convertToTs(getTodaysDate()));
        milestoneDTO.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()));
        studyMilestoneService.create(milestoneDTO);

        mailManagerSerivceLocal.sendSubmissionTerminationEmail(studyProtocol
                .getId());

    }

    /**
     * @param onholds
     */
    private void arrangeHoldsByID(List<StudyOnhold> onholds) {
        Collections.sort(onholds, new Comparator<StudyOnhold>() {
            @Override
            public int compare(StudyOnhold o1, StudyOnhold o2) {
                return -(o1.getId().compareTo(o2.getId()));
            }
        });
    }

    /**
     * @return
     * @throws PAException
     */
    private List<StudyProtocolQueryDTO> findOnHoldTrials() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(Boolean.TRUE);
        criteria.setHoldStatus(PAConstants.ON_HOLD);
        List<StudyProtocolQueryDTO> studyDTOs = protocolQueryServiceLocal
                .getStudyProtocolByCriteria(criteria);
        return studyDTOs;
    }

    /**
     * @param protocolQueryServiceLocal the protocolQueryServiceLocal to set
     */
    public void setProtocolQueryServiceLocal(
            ProtocolQueryServiceLocal protocolQueryServiceLocal) {
        this.protocolQueryServiceLocal = protocolQueryServiceLocal;
    }

    /**
     * @param lookUpTableServiceRemote the lookUpTableServiceRemote to set
     */
    public void setLookUpTableServiceRemote(
            LookUpTableServiceRemote lookUpTableServiceRemote) {
        this.lookUpTableServiceRemote = lookUpTableServiceRemote;
    }

    /**
     * @param mailManagerSerivceLocal the mailManagerSerivceLocal to set
     */
    public void setMailManagerSerivceLocal(
            MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }

    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(
            StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }

    @Override
    public String getReasonCategoryValue(String key) throws PAException {
        String fieldKeyToLabelMap = lookUpTableServiceRemote
                .getPropertyValue("studyonhold.reason_category.mapping");
        return getFieldKeyMappingValue(key, fieldKeyToLabelMap);
    }
    /**
     * @param fieldKey
     * @param fieldKeyMap
     * @return
     */
    private String getFieldKeyMappingValue(String fieldKey,
            String fieldKeyMap) {
        Matcher m = getFieldKeyMappingMatcher(fieldKey, fieldKeyMap);
        if (m.find()) {
            return m.group(1).trim();
        } else {
            return fieldKey;
        }
    }
    /**
     * @param fieldKey
     * @param fieldKeyMap
     * @return
     */
    private Matcher getFieldKeyMappingMatcher(String fieldKey,
            String fieldKeyMap) {
        Pattern p = Pattern.compile("(?m)^\\Q" + fieldKey + "\\E=(.*)$");
        return p.matcher(fieldKeyMap);        
    }

}
