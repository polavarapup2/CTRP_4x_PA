/**
 * 
 */
package gov.nih.nci.pa.action; // NOPMD

import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_QC_COMPLETE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_QC_START;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.READY_FOR_TSR;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_ACCEPTED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_RECEIVED;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;
import static gov.nih.nci.pa.util.Constants.IS_ADMIN_ABSTRACTOR;
import static gov.nih.nci.pa.util.Constants.IS_SCIENTIFIC_ABSTRACTOR;
import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.search.StudyProtocolOptions.MilestoneFilter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Denis G. Krylov
 * 
 */

@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods",
        "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class DashboardAction extends AbstractCheckInOutAction implements
        Preparable, ServletRequestAware {

    private static final String DASHBOARD_TITLE = "dashboardTitle";
    private static final String NCT_IDENTIFIER = "nctIdentifier";
    private static final String SUMMARY_DTO = "summaryDTO";
    private static final String QUERY_DTO = "queryDTO";
    private static final String NON_ABSTRACTOR_LANDING = "nonAbstractorLanding";
    private static final String SU_ABSTRACTOR_LANDING = "suAbstractorLanding";
    private static final String ABSTRACTOR_LANDING = "abstractorLanding";
    private static final String DASHBOARD_SEARCH_RESULTS = "dashboardSearchResults";
    private static final String WORKLOAD = "workload";
    private static final String COUNT_TYPE_SUBMITTED = "submittedCnt";
    private static final String COUNT_TYPE_PAST_TEN = "pastTenCnt";
    private static final String COUNT_TYPE_EXPECTED = "expectedCnt";
    private static final int POSITIVE_TEN = 10;
    private static final int NEGATIVE_TEN = -10;

    private static final Logger LOG = Logger.getLogger(DashboardAction.class);

    private static final String LAST = "last";
    private static final String ANY = "any";

    private static final long serialVersionUID = 8458441253215157815L;

    private static final Long ANYONE = -1L;
    private static final Long NOONE = -2L;
    private static final Long ME = -3L;

    private static final String TOGGLE_RESULTS_TAB = "toggleResultsTab";
    private static final String TOGGLE_DETAILS_TAB = "toggleDetailsTab";

    private HttpServletRequest request;

    private LookUpTableServiceRemote lookUpService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private InterventionServiceLocal interventionService;
    private PDQDiseaseServiceLocal pdqDiseaseService;
    private StudyProtocolService studyProtocolService;
    private StudyOnholdServiceLocal onholdService;
    private PAServiceUtils serviceUtils = new PAServiceUtils();

    // fields that capture search criteria
    private Long checkedOutBy;
    private Long assignee;
    private List<String> processingPriority = new ArrayList<String>();
    private String submittedOnOrAfter;
    private String submittedOnOrBefore;
    private String submittingOrgId;
    private String submittedBy;
    private List<String> submissionType = new ArrayList<String>();
    private String nciSponsored;
    private List<String> onHoldStatus = new ArrayList<String>();
    private String ctepDcpCategory;
    private List<String> onHoldReason = new ArrayList<String>();
    private String milestoneType;
    private String milestone;
    private List<String> processingStatus = new ArrayList<String>();
    private List<String> anatomicSites = new ArrayList<String>();
    private List<String> interventions = new ArrayList<String>();
    private List<String> diseases = new ArrayList<String>();
    private Boolean adminAbstraction;
    private Boolean adminQC;
    private Boolean scientificAbstraction;
    private Boolean scientificQC;
    private Boolean readyForTSR;
    private Boolean submittedUnaccepted;

    // Details tab updatable fields
    private Long assignedTo;
    private String newProcessingPriority;
    private String processingComments;

    // Range Filter
    private String dateFrom;
    private String dateTo;
    private String dateFilterField;

    // Submission type filter
    private List<String> submissionTypeFilter = new ArrayList<String>();

    // Distribution filter.
    private String distr;

    // Expected abstraction completion date.
    private String newCompletionDate;
    private String newCompletionDateComments;

    private List<String> checkoutCommands = new ArrayList<String>();
    
    //filer type choice in case of date
    private String choice;

    //trial counts by date filter
    private String countForDay;
    private String countType;
    private String countRangeFrom;
    private String countRangeTo;

    @Override
    public String execute() {
        clearSearchSessionAttributes();
        clearFilters();
        initializeCountByRangeDates();
        if (!canAccessDashboard()) {
            return NON_ABSTRACTOR_LANDING;
        }
        
        //set default search value as 
        //unrestricted if no radio is selected in case of date search
        if (choice == null) {
            setChoice("unrestricted");
        }
        return filter();
    }

    /**
     * @return String
     */
    public String filter() {
        try {
            prepareWorkload();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }

    private void clearFilters() {
        clearDateRangeFilter();
        clearSubmissionTypeFilter();
    }

    /**
     * 
     */
    private void clearSubmissionTypeFilter() {
        submissionTypeFilter = new ArrayList<>();
    }

    /**
     * 
     */
    private void clearDateRangeFilter() {
        dateFrom = null;
        dateTo = null;
        dateFilterField = null;
    }

    private void initializeCountByRangeDates() {
        Date from = PAUtil.addBusinessDays(new Date(), NEGATIVE_TEN);
        Date to = PAUtil.addBusinessDays(new Date(), POSITIVE_TEN);

        countRangeFrom = DateFormatUtils.format(from, PAUtil.DATE_FORMAT);
        countRangeTo = DateFormatUtils.format(to, PAUtil.DATE_FORMAT);
    }

    private void prepareWorkload() throws PAException {
        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getWorkload();
        applyDateRangeFilter(results);
        applySubmissionTypeFilter(results);
        request.getSession().setAttribute(WORKLOAD, results);

    }

    private void applySubmissionTypeFilter(List<StudyProtocolQueryDTO> results) {
        if (!CollectionUtils.isEmpty(getSubmissionTypeFilter())) {
            CollectionUtils.filter(results, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    StudyProtocolQueryDTO dto = (StudyProtocolQueryDTO) o;
                    return getSubmissionTypeFilter().contains(
                            dto.getSubmissionType());
                }
            });
        }
    }

    private void applyDistributionFilter(
            final List<StudyProtocolQueryDTO> results) {
        CollectionUtils.filter(results, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                StudyProtocolQueryDTO dto = (StudyProtocolQueryDTO) o;
                return PAUtil.isInRange(dto.getBizDaysSinceSubmitted(),
                        getDistr());
            }
        });
    }


    @SuppressWarnings({ "PMD.NPathComplexity" })
    private void applyCountTypeFilter(final List<StudyProtocolQueryDTO> results) {
        if (StringUtils.isEmpty(countForDay) || StringUtils.isEmpty(countType)) {
            return;
        }


        final Date rangeStart = StringUtils.equals(countForDay, "Total")
            ? PAUtil.dateStringToDateTime(countRangeFrom) : PAUtil.dateStringToDateTime(countForDay);
        final Date rangeEnd = StringUtils.equals(countForDay, "Total")
            ? PAUtil.dateStringToDateTime(countRangeTo) : DateUtils.addDays(rangeStart, 1);

        //adjust start and end based on business days

        CollectionUtils.filter(results, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                StudyProtocolQueryDTO dto = (StudyProtocolQueryDTO) o;

                if (StringUtils.equals(COUNT_TYPE_EXPECTED, countType)) {
                    Date date = dto.getExpectedAbstractionCompletionDate();
                    return date != null
                            && (DateUtils.isSameDay(date, rangeStart) || date.after(rangeStart))
                            && (date.before(rangeEnd));
                }

                if (StringUtils.equals(COUNT_TYPE_SUBMITTED, countType)) {
                    Date date = dto.getLastCreated().getDateLastCreated();
                    return date != null
                            && (DateUtils.isSameDay(date, rangeStart) || date.after(rangeStart))
                            && (date.before(rangeEnd));
                }

                if (StringUtils.equals(COUNT_TYPE_PAST_TEN, countType)) {
                    Date submittedOn = dto.getLastCreated().getDateLastCreated();
                    Date date = submittedOn != null ? PAUtil.addBusinessDays(submittedOn, POSITIVE_TEN) : null;
                    return date != null
                            && (DateUtils.isSameDay(date, rangeStart) || date.after(rangeStart))
                            && (date.before(rangeEnd));
                }

                return false;
            }
        });
    }

    private void applyDateRangeFilter(final List<StudyProtocolQueryDTO> results) {
        
        //filter based on type
        if (!StringUtils.isEmpty(choice)) {
            //it means clear all filters
           if (("unrestricted").equalsIgnoreCase(choice)) {
               clearDateRangeFilter();
               return;
           } else {
                final Date rangeStart = PAUtil.dateStringToDateTime(dateFrom);
                final Date rangeEnd = PAUtil.dateStringToDateTime(dateTo);
                CollectionUtils.filter(results, new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        try {
                            Date date = (Date) PropertyUtils.getProperty(o,
                                    dateFilterField);
                            //return records with date as null                            
                            if (("nullDate").equalsIgnoreCase(choice)) {
                                return date == null;
                            } else if (("limit").equalsIgnoreCase(choice)) {
                                //if range not specified return records with not null date
                                 if (rangeStart == null && rangeEnd == null) {
                                     return date != null;
                                 } else {
                                     //return existing filter as usual
                                     return date != null
                                             && (rangeStart == null
                                                     || DateUtils.isSameDay(date, rangeStart) || date
                                                         .after(rangeStart))
                                             && (rangeEnd == null
                                                     || DateUtils.isSameDay(date, rangeEnd) || date
                                                         .before(rangeEnd));
                                 }
                            }
                           
                        } catch (Exception e) {
                            LOG.error(e, e);
                        }
                        return false;
                    }
                });
            }  
        }  
    }

    private boolean canAccessDashboard() {
        return isInRole(IS_SU_ABSTRACTOR) || isInRole(IS_ADMIN_ABSTRACTOR)
                || isInRole(IS_SCIENTIFIC_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
    }

    /**
     * 
     */
    private void clearSearchSessionAttributes() {
        final HttpSession session = request.getSession();
        session.removeAttribute(DASHBOARD_SEARCH_RESULTS);
        session.removeAttribute(QUERY_DTO);
        session.removeAttribute(SUMMARY_DTO);
        session.removeAttribute(Constants.TRIAL_SUMMARY);
        session.removeAttribute(Constants.STUDY_PROTOCOL_II);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String searchByDistribution() throws PAException {
        clearSearchSessionAttributes();
        try {
            List<StudyProtocolQueryDTO> results = protocolQueryService
                    .getWorkload();
            applyDistributionFilter(results);
            storeResults(results);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }


    /**
     * @return String
     * @throws PAException PAException
     */
    public String searchByCountType() throws PAException {
        clearSearchSessionAttributes();
        try {
            List<StudyProtocolQueryDTO> results = protocolQueryService
                    .getWorkload();
            applyCountTypeFilter(results);
            storeResults(results);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }


    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String search() throws PAException {
        clearSearchSessionAttributes();
        if (!canAccessDashboard()) {
            return NON_ABSTRACTOR_LANDING;
        }

        try {
            StudyProtocolQueryCriteria criteria = buildCriteria();
            return search(criteria);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }

    /**
     * @param criteria
     * @return
     */
    private String search(StudyProtocolQueryCriteria... criteriaList) {
        try {
            List<StudyProtocolQueryDTO> results = new ArrayList<StudyProtocolQueryDTO>();
            for (StudyProtocolQueryCriteria criteria : criteriaList) {
                List<StudyProtocolQueryDTO> currentResults = protocolQueryService
                        .getStudyProtocolByCriteria(criteria,
                                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                                SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
                protocolQueryService.populateMilestoneHistory(currentResults);
                results.addAll(currentResults);
            }
            storeResults(results);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }

    /**
     * @param results
     */
    private void storeResults(List<StudyProtocolQueryDTO> results) {
        eliminateDupes(results);
        request.getSession().setAttribute(DASHBOARD_SEARCH_RESULTS, results);
        toggleResultsTab();
    }

    private void eliminateDupes(List<StudyProtocolQueryDTO> trials) {
        TreeSet<StudyProtocolQueryDTO> set = new TreeSet<StudyProtocolQueryDTO>(
                new Comparator<StudyProtocolQueryDTO>() {
                    @Override
                    public int compare(StudyProtocolQueryDTO dto1,
                            StudyProtocolQueryDTO dto2) {
                        return dto1.getStudyProtocolId().compareTo(
                                dto2.getStudyProtocolId());
                    }
                });
        set.addAll(trials);
        trials.clear();
        trials.addAll(set);
    }

    /**
     * @return landingPage
     */
    public String landingPage() {
        if (isInRole(IS_SU_ABSTRACTOR)) {
            return SU_ABSTRACTOR_LANDING;
        } else {
            return ABSTRACTOR_LANDING;
        }
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @Override
    public String view() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setStudyProtocolId(getStudyProtocolId());
        try {
            StudyProtocolQueryDTO queryDTO = protocolQueryService
                    .getStudyProtocolByCriteria(criteria).get(0);
            final HttpSession session = request.getSession();
            session.setAttribute(QUERY_DTO, queryDTO);

            StudyProtocolQueryDTO summaryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(getStudyProtocolId());
            session.setAttribute(SUMMARY_DTO, summaryDTO);
            session.setAttribute(
                    NCT_IDENTIFIER,
                    getServiceUtils()
                            .getStudyIdentifier(
                                    IiConverter
                                            .convertToStudyProtocolIi(getStudyProtocolId()),
                                    PAConstants.NCT_IDENTIFIER_TYPE));
            session.setAttribute(Constants.TRIAL_SUMMARY, summaryDTO);
            session.setAttribute(Constants.TRIAL_SUBMITTER_ORG_PO_ID, summaryDTO.getSubmitterOrgId());
            session.setAttribute(Constants.TRIAL_SUBMITTER_ORG, summaryDTO.getSubmitterOrgName());
            session.setAttribute(Constants.STUDY_PROTOCOL_II,
                    IiConverter.convertToStudyProtocolIi(getStudyProtocolId()));

            checkoutCommands = new ArrayList<>();
            ActionUtils.setCheckoutCommands(summaryDTO, checkoutCommands);
            ActionUtils
                    .runTrialStatusTransitionValidations(summaryDTO, session);

            toggleDetailsTab();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public String save() throws PAException {
        try {
            StudyProtocolDTO studyDTO = studyProtocolService
                    .getStudyProtocol(IiConverter
                            .convertToIi(getStudyProtocolId()));
            final Ii assignedUser = studyDTO.getAssignedUser();
            final Ii newAssignedUser = IiConverter.convertToIi(assignedTo);

            studyDTO.setComments(StConverter.convertToSt(processingComments));
            if (isInRole(IS_SU_ABSTRACTOR)) {
                studyDTO.setProcessingPriority(IntConverter
                        .convertToInt(newProcessingPriority));
                studyDTO.setAssignedUser(newAssignedUser);
            }
            studyProtocolService.updateStudyProtocol(studyDTO);

            // if assigned user's changed, check out the trial in that name.
            if (isInRole(IS_SU_ABSTRACTOR)
                    && ((ISOUtil.isIiNull(assignedUser) && !ISOUtil
                            .isIiNull(newAssignedUser))
                            || (!ISOUtil.isIiNull(assignedUser) && ISOUtil
                                    .isIiNull(newAssignedUser)) || (!ISOUtil
                            .isIiNull(assignedUser)
                            && !ISOUtil.isIiNull(newAssignedUser) && !assignedUser
                            .getExtension().equals(
                                    newAssignedUser.getExtension())))) {
                getStudyCheckoutService().handleTrialAssigneeChange(
                        getStudyProtocolId());
            }
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    getText("dashboard.save.success"));
            return view();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return landingPage();
    }

    /**
     * @return loopback
     */
    public String loopback() {
        toggleResultsTab();
        return landingPage();
    }

    /**
     * 
     */
    private void toggleResultsTab() {
        request.setAttribute(TOGGLE_RESULTS_TAB, true);
    }

    private void toggleDetailsTab() {
        request.setAttribute(TOGGLE_DETAILS_TAB, true);
    }

    private StudyProtocolQueryCriteria buildCriteria() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setDocumentWorkflowStatusCodes(processingStatus);
        criteria.setProcessingPriority(processingPriority);
        criteria.setCtepDcpCategory(ctepDcpCategory);
        criteria.setSummary4AnatomicSitesAsStrings(anatomicSites);
        criteria.setInterventionIdsAsStrings(interventions);
        criteria.setPdqDiseasesAsStrings(diseases);
        for (String code : submissionType) {
            criteria.getTrialSubmissionTypes().add(
                    SubmissionTypeCode.getByCode(code));
        }
        buildOnHoldReasonCriteria(criteria);
        criteria.setSubmitterAffiliateOrgId(submittingOrgId);
        criteria.setNciSponsored(StringUtils.isBlank(nciSponsored) ? null
                : "true".equals(nciSponsored));
        buildCheckOutCriteria(criteria);
        buildAssigneeCriteria(criteria);
        buildSubmissionTimelineCriteria(criteria);
        buildOnHoldCriteria(criteria);
        buildMilestoneCriteria(criteria);
        return criteria;
    }

    /**
     * @param criteria
     */
    private void buildOnHoldReasonCriteria(StudyProtocolQueryCriteria criteria) {
        for (String code : onHoldReason) {
            if (!StringUtils.defaultString(code).startsWith(
                    OnholdReasonCode.OTHER.getCode())) {
                final OnholdReasonCode byCode = OnholdReasonCode
                        .getByCode(code);
                if (byCode != null) {
                    criteria.getOnholdReasons().add(
                            OnholdReasonCode.getByCode(code));
                }
            } else {
                criteria.getOnholdReasons().add(OnholdReasonCode.OTHER);
                String cat = code.replaceAll(
                        OnholdReasonCode.OTHER.getCode() + "_", "").trim();
                if (StringUtils.isNotBlank(cat)) {
                    criteria.getOnholdOtherReasonCategories().add(cat);
                }
            }
        }
    }

    /**
     * @param criteria
     */
    @SuppressWarnings({ "PMD.NPathComplexity", "unchecked" })
    private void buildMilestoneCriteria(StudyProtocolQueryCriteria criteria) {

        if (ANY.equalsIgnoreCase(milestoneType)
                && StringUtils.isNotBlank(milestone)) {
            criteria.setCurrentOrPreviousMilestone(MilestoneCode
                    .getByCode(milestone));
        }
        if (LAST.equalsIgnoreCase(milestoneType)
                && StringUtils.isNotBlank(milestone)) {
            criteria.getStudyMilestone().add(milestone);
        }

        final List<MilestoneFilter> filter = criteria.getMilestoneFilters();
        if (Boolean.TRUE.equals(scientificAbstraction)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_ACCEPTED,
                    ADMINISTRATIVE_PROCESSING_START_DATE,
                    ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                    ADMINISTRATIVE_READY_FOR_QC, ADMINISTRATIVE_QC_START,
                    ADMINISTRATIVE_QC_COMPLETE), Arrays
                    .asList(SCIENTIFIC_PROCESSING_START_DATE)));
        }
        if (Boolean.TRUE.equals(adminAbstraction)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_ACCEPTED),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(adminQC)) {
            filter.add(new MilestoneFilter(Arrays
                    .asList(ADMINISTRATIVE_READY_FOR_QC), ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(readyForTSR)) {
            filter.add(new MilestoneFilter(Arrays.asList(READY_FOR_TSR),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(submittedUnaccepted)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_RECEIVED),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(scientificQC)) {
            filter.add(new MilestoneFilter(Arrays
                    .asList(SCIENTIFIC_READY_FOR_QC), ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(scientificAbstraction)
                || Boolean.TRUE.equals(adminAbstraction)
                || Boolean.TRUE.equals(adminQC)
                || Boolean.TRUE.equals(scientificQC)) {
            criteria.setExcludeRejectProtocol(true);
        }
    }

    /**
     * @param criteria
     */
    private void buildOnHoldCriteria(StudyProtocolQueryCriteria criteria) {
        if (onHoldStatus.contains(PAConstants.ON_HOLD)) {
            criteria.setHoldStatus(PAConstants.ON_HOLD);
        }
        if (onHoldStatus.contains(PAConstants.NOT_ON_HOLD)) {
            criteria.setHoldStatus(PAConstants.NOT_ON_HOLD);
        }
        if (onHoldStatus.contains(PAConstants.ON_HOLD_ANYTIME)) {
            criteria.setHoldRecordExists(true);
        }
    }

    /**
     * @param criteria
     * @throws PAException
     */
    private void buildSubmissionTimelineCriteria(
            StudyProtocolQueryCriteria criteria) throws PAException {
        final Date onOrAfter = PAUtil.dateStringToDateTime(submittedOnOrAfter);
        final Date onOrBefore = PAUtil.endOfDay(PAUtil
                .dateStringToDateTime(submittedOnOrBefore));
        criteria.setSubmittedOnOrAfter(onOrAfter);
        criteria.setSubmittedOnOrBefore(onOrBefore);

        if (onOrAfter != null && onOrBefore != null
                && onOrAfter.after(onOrBefore)) {
            throw new PAException(
                    "Submission timeline dates are inconsistent and will never produce results. "
                            + "Please correct");
        }
    }

    /**
     * @param criteria
     * @throws PAException
     */
    private void buildCheckOutCriteria(StudyProtocolQueryCriteria criteria)
            throws PAException {
        if (checkedOutBy != null) {
            if (ANYONE.equals(checkedOutBy)) {
                criteria.setCheckedOut(true);
            } else if (NOONE.equals(checkedOutBy)) {
                criteria.setCheckedOut(false);
            } else {
                criteria.setStudyLockedBy(true);
                criteria.setUserLastCreated(getUserIdentifier(checkedOutBy));
            }
        }
    }

    /**
     * @param criteria
     * @throws PAException
     */
    private void buildAssigneeCriteria(StudyProtocolQueryCriteria criteria)
            throws PAException {
        if (assignee != null) {
            if (ME.equals(assignee)) {
                criteria.setAssignedUserId(CSMUserService.getInstance()
                        .getCSMUser(UsernameHolder.getUser()).getUserId());
            } else {
                criteria.setAssignedUserId(assignee);
            }
        }
    }

    private String getUserIdentifier(long userId) throws PAException {
        return CSMUserService.getInstance().getCSMUserById(userId)
                .getLoginName();
    }

    /**
     * @param servletRequest
     *            the servletRequest to set
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
    }

    @Override
    public void prepare() {
        ActionUtils.setUserRolesInSession(request);
        determineProperPageTitle();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        interventionService = PaRegistry.getInterventionService();
        pdqDiseaseService = PaRegistry.getDiseaseService();
        onholdService = PaRegistry.getStudyOnholdService();
        lookUpService = PaRegistry.getLookUpTableService();
        setStudyCheckoutService(PaRegistry.getStudyCheckoutService());
    }

    private void determineProperPageTitle() {
        if (isInRole(IS_ADMIN_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.admin"));
        }
        if (isInRole(IS_SCIENTIFIC_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.scientific"));
        }
        if (isInRole(IS_SCIENTIFIC_ABSTRACTOR) && isInRole(IS_ADMIN_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.adminAndScientific"));
        }
        if (isInRole(IS_SU_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.super"));
        }
    }

    /**
     * @return Map<String, String>
     * @throws PAException
     *             PAException
     */
    public Map<Long, String> getCheckedOutByList() throws PAException {
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        map.put(ANYONE, getText("dashboard.anyone"));
        map.put(NOONE, getText("dashboard.noone"));
        map.putAll(CSMUserService.getInstance().getAbstractors());
        return map;
    }

    /**
     * @return Map<String, String>
     * @throws PAException
     *             PAException
     */
    public Map<Long, String> getAssigneeList() throws PAException {
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        map.put(ME, getText("dashboard.me"));
        map.putAll(CSMUserService.getInstance().getAbstractors());
        return map;
    }

    @Override
    public String adminCheckOut() throws PAException {
        super.adminCheckOut();
        return view();
    }

    @Override
    public String scientificCheckOut() throws PAException {
        super.scientificCheckOut();
        return view();
    }

    @Override
    public String adminAndScientificCheckOut() throws PAException {
        super.adminAndScientificCheckOut();
        if (!hasActionErrors()) {
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    getText("studyProtocol.trial.checkOut.adminAndScientific"));
        }
        return view();
    }

    @Override
    public String adminCheckIn() throws PAException {
        super.adminCheckIn();
        return view();
    }

    @Override
    public String scientificCheckIn() throws PAException {
        super.scientificCheckIn();
        return view();
    }

    @Override
    public String adminAndScientificCheckIn() throws PAException {
        super.adminAndScientificCheckIn();
        return view();
    }

    /**
     * @return List<InterventionWebDTO>
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public List<InterventionDTO> getInterventionsList() throws PAException {
        final List<InterventionDTO> list = new ArrayList<InterventionDTO>();
        if (getInterventions() != null) {
            for (String id : getInterventions()) {
                list.add(interventionService.get(IiConverter.convertToIi(id)));
            }
        }
        return list;
    }

    /**
     * @return List<DiseaseWebDTO>
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public List<DiseaseWebDTO> getDiseasesList() throws PAException {
        final List<DiseaseWebDTO> list = new ArrayList<DiseaseWebDTO>();
        if (getDiseases() != null) {
            for (String id : getDiseases()) {
                list.add(new DiseaseWebDTO(pdqDiseaseService.get(IiConverter
                        .convertToIi(id))));
            }
        }
        return list;
    }

    /**
     * Calculates the number of business days between {@link #dateFrom} and
     * {@link #dateTo}. Returns plain text number.
     * 
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     */
    public StreamResult bizDays() throws UnsupportedEncodingException {
        Integer days = PAUtil.getBusinessDaysBetween(
                PAUtil.dateStringToDateTime(getDateFrom()),
                PAUtil.dateStringToDateTime(getDateTo())) - 1;
        return new StreamResult(new ByteArrayInputStream(days.toString()
                .getBytes("UTF-8")));
    }

    /**
     * updateExpectedAbstractionCompletionDate.
     * 
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public StreamResult updateExpectedAbstractionCompletionDate()
            throws UnsupportedEncodingException, PAException {
        if (isInRole(IS_SU_ABSTRACTOR)) {
            StudyProtocolDTO spDTO = studyProtocolService
                    .getStudyProtocol(IiConverter
                            .convertToIi(getStudyProtocolId()));
            spDTO.setExpectedAbstractionCompletionDate(TsConverter
                    .convertToTs(PAUtil
                            .dateStringToDateTime(getNewCompletionDate())));
            spDTO.setExpectedAbstractionCompletionComments(StConverter
                    .convertToSt(getNewCompletionDateComments()));
            studyProtocolService.updateStudyProtocol(spDTO);
            return new StreamResult(new ByteArrayInputStream(
                    StringUtils.EMPTY.getBytes("UTF-8")));
        } else {
            throw new PAException("Not allowed");
        }

    }

    /**
     * @return the checkedOutBy
     */
    public Long getCheckedOutBy() {
        return checkedOutBy;
    }

    /**
     * @param checkedOutBy
     *            the checkedOutBy to set
     */
    public void setCheckedOutBy(Long checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    /**
     * @return the submittedOnOrAfter
     */
    public String getSubmittedOnOrAfter() {
        return submittedOnOrAfter;
    }

    /**
     * @param submittedOnOrAfter
     *            the submittedOnOrAfter to set
     */
    public void setSubmittedOnOrAfter(String submittedOnOrAfter) {
        this.submittedOnOrAfter = submittedOnOrAfter;
    }

    /**
     * @return the submittedOnOrBefore
     */
    public String getSubmittedOnOrBefore() {
        return submittedOnOrBefore;
    }

    /**
     * @param submittedOnOrBefore the submittedOnOrBefore to set
     */
    public void setSubmittedOnOrBefore(String submittedOnOrBefore) {
        this.submittedOnOrBefore = submittedOnOrBefore;
    }

    /**
     * The day for which the count type is requested
     *
     * @return - countForDay
     */
    public String getCountForDay() {
        return countForDay;
    }

    /**
     * Sets the day for which count type is requested.
     *
     * @param countForDay - count for day
     */
    public void setCountForDay(String countForDay) {
        this.countForDay = countForDay;
    }

    /**
     * The count type, ie. past-10day , submitted-on, exptected-on
     *
     * @return  countType
     */
    public String getCountType() {
        return countType;
    }

    /**
     * Sets the count type.
     *
     * @param countType  - count type
     */
    public void setCountType(String countType) {
        this.countType = countType;
    }

    /**
     * The range start date for counting
     *
     * @return  countRangeFrom
     */
    public String getCountRangeFrom() {
        return countRangeFrom;
    }

    /**
     * Sets the range start date for counting
     *
     * @param countRangeFrom - count range from
     */
    public void setCountRangeFrom(String countRangeFrom) {
        this.countRangeFrom = countRangeFrom;
    }

    /**
     * The range end date for counting
     *
     * @return  - countRangeTo
     */
    public String getCountRangeTo() {
        return countRangeTo;
    }

    /**
     * Sets the range end date for counting.
     *
     * @param countRangeTo - count range to
     */
    public void setCountRangeTo(String countRangeTo) {
        this.countRangeTo = countRangeTo;
    }

    /**
     * @return the submittingOrgId
     */
    public String getSubmittingOrgId() {
        return submittingOrgId;
    }

    /**
     * @param submittingOrgId
     *            the submittingOrgId to set
     */
    public void setSubmittingOrgId(String submittingOrgId) {
        this.submittingOrgId = submittingOrgId;
    }

    /**
     * @return the submissionType
     */
    public List<String> getSubmissionType() {
        return submissionType;
    }

    /**
     * @param submissionType
     *            the submissionType to set
     */
    public void setSubmissionType(List<String> submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * @return the nciSponsored
     */
    public String getNciSponsored() {
        return nciSponsored;
    }

    /**
     * @param nciSponsored
     *            the nciSponsored to set
     */
    public void setNciSponsored(String nciSponsored) {
        this.nciSponsored = nciSponsored;
    }

    /**
     * @return the onHoldStatus
     */
    public List<String> getOnHoldStatus() {
        return onHoldStatus;
    }

    /**
     * @param onHoldStatus
     *            the onHoldStatus to set
     */
    public void setOnHoldStatus(List<String> onHoldStatus) {
        this.onHoldStatus = onHoldStatus;
    }

    /**
     * @return the ctepDcpCategory
     */
    public String getCtepDcpCategory() {
        return ctepDcpCategory;
    }

    /**
     * @param ctepDcpCategory
     *            the ctepDcpCategory to set
     */
    public void setCtepDcpCategory(String ctepDcpCategory) {
        this.ctepDcpCategory = ctepDcpCategory;
    }

    /**
     * @return the onHoldReason
     */
    public List<String> getOnHoldReason() {
        return onHoldReason;
    }

    /**
     * @param onHoldReason
     *            the onHoldReason to set
     */
    public void setOnHoldReason(List<String> onHoldReason) {
        this.onHoldReason = onHoldReason;
    }

    /**
     * @return the milestoneType
     */
    public String getMilestoneType() {
        return milestoneType;
    }

    /**
     * @param milestoneType
     *            the milestoneType to set
     */
    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    /**
     * @return the milestone
     */
    public String getMilestone() {
        return milestone;
    }

    /**
     * @param milestone
     *            the milestone to set
     */
    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    /**
     * @return the processingStatus
     */
    public List<String> getProcessingStatus() {
        return processingStatus;
    }

    /**
     * @param processingStatus
     *            the processingStatus to set
     */
    public void setProcessingStatus(List<String> processingStatus) {
        this.processingStatus = processingStatus;
    }

    /**
     * @return the adminAbstraction
     */
    public Boolean getAdminAbstraction() {
        return adminAbstraction;
    }

    /**
     * @param adminAbstraction
     *            the adminAbstraction to set
     */
    public void setAdminAbstraction(Boolean adminAbstraction) {
        this.adminAbstraction = adminAbstraction;
    }

    /**
     * @return the adminQC
     */
    public Boolean getAdminQC() {
        return adminQC;
    }

    /**
     * @param adminQC
     *            the adminQC to set
     */
    public void setAdminQC(Boolean adminQC) {
        this.adminQC = adminQC;
    }

    /**
     * @return the scientificAbstraction
     */
    public Boolean getScientificAbstraction() {
        return scientificAbstraction;
    }

    /**
     * @param scientificAbstraction
     *            the scientificAbstraction to set
     */
    public void setScientificAbstraction(Boolean scientificAbstraction) {
        this.scientificAbstraction = scientificAbstraction;
    }

    /**
     * @return the scientificQC
     */
    public Boolean getScientificQC() {
        return scientificQC;
    }

    /**
     * @param scientificQC
     *            the scientificQC to set
     */
    public void setScientificQC(Boolean scientificQC) {
        this.scientificQC = scientificQC;
    }

    /**
     * @return the assignedTo
     */
    public Long getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param assignedTo
     *            the assignedTo to set
     */
    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * @return the newProcessingPriority
     */
    public String getNewProcessingPriority() {
        return newProcessingPriority;
    }

    /**
     * @param newProcessingPriority
     *            the newProcessingPriority to set
     */
    public void setNewProcessingPriority(String newProcessingPriority) {
        this.newProcessingPriority = newProcessingPriority;
    }

    /**
     * @return the processingComments
     */
    public String getProcessingComments() {
        return processingComments;
    }

    /**
     * @param processingComments
     *            the processingComments to set
     */
    public void setProcessingComments(String processingComments) {
        this.processingComments = processingComments;
    }

    /**
     * @return the checkoutCommands
     */
    public List<String> getCheckoutCommands() {
        return checkoutCommands;
    }

    /**
     * @param checkoutCommands
     *            the checkoutCommands to set
     */
    public void setCheckoutCommands(List<String> checkoutCommands) {
        this.checkoutCommands = checkoutCommands;
    }

    /**
     * @return the readyForTSR
     */
    public Boolean getReadyForTSR() {
        return readyForTSR;
    }

    /**
     * @param readyForTSR
     *            the readyForTSR to set
     */
    public void setReadyForTSR(Boolean readyForTSR) {
        this.readyForTSR = readyForTSR;
    }

    /**
     * @return the submittedUnaccepted
     */
    public Boolean getSubmittedUnaccepted() {
        return submittedUnaccepted;
    }

    /**
     * @param submittedUnaccepted
     *            the submittedUnaccepted to set
     */
    public void setSubmittedUnaccepted(Boolean submittedUnaccepted) {
        this.submittedUnaccepted = submittedUnaccepted;
    }

    /**
     * @return the submittedBy
     */
    public String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * @param submittedBy
     *            the submittedBy to set
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * @return the processingPriority
     */
    public List<String> getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority
     *            the processingPriority to set
     */
    public void setProcessingPriority(List<String> processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @return the serviceUtils
     */
    public PAServiceUtils getServiceUtils() {
        return serviceUtils;
    }

    /**
     * @param serviceUtils
     *            the serviceUtils to set
     */
    public void setServiceUtils(PAServiceUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }

    /**
     * @return the assignee
     */
    public Long getAssignee() {
        return assignee;
    }

    /**
     * @param assignee
     *            the assignee to set
     */
    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the anatomicSites
     */
    public List<String> getAnatomicSites() {
        return anatomicSites;
    }

    /**
     * @param anatomicSites
     *            the anatomicSites to set
     */
    public void setAnatomicSites(List<String> anatomicSites) {
        this.anatomicSites = anatomicSites;
    }

    /**
     * @return the interventions
     */
    public List<String> getInterventions() {
        return interventions;
    }

    /**
     * @param interventions
     *            the interventions to set
     */
    public void setInterventions(List<String> interventions) {
        this.interventions = interventions;
    }

    /**
     * @return the diseases
     */
    public List<String> getDiseases() {
        return diseases;
    }

    /**
     * @param diseases
     *            the diseases to set
     */
    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    /**
     * @return onHoldValuesMap
     */
    public final Map<String, String> getOnHoldValuesMap() {
        final Map<String, String> onHoldValuesMap = new LinkedHashMap<>();
        try {
            OnholdReasonCode[] keys = OnholdReasonCode.values();
            for (OnholdReasonCode key : keys) {
                if (key != OnholdReasonCode.OTHER) {
                    String value = onholdService.getReasonCategoryValue(key
                            .getName());
                    onHoldValuesMap.put(key.getCode(), key.getCode() + " ("
                            + value + ")");
                } else {
                    for (String cat : lookUpService.getPropertyValue(
                            "studyonhold.reason_category").split(",")) {
                        onHoldValuesMap.put(OnholdReasonCode.OTHER.getCode()
                                + "_" + cat, OnholdReasonCode.OTHER.getCode()
                                + " (" + cat + ")");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error in setting on hold values " + e.getMessage(), e);
        }
        return onHoldValuesMap;
    }

    /**
     * @return the dateFrom
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom
     *            the dateFrom to set
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo
     *            the dateTo to set
     */
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the dateFilterField
     */
    public String getDateFilterField() {
        return dateFilterField;
    }

    /**
     * @param dateFilterField
     *            the dateFilterField to set
     */
    public void setDateFilterField(String dateFilterField) {
        this.dateFilterField = dateFilterField;
    }

    /**
     * @return the submissionTypeFilter
     */
    public List<String> getSubmissionTypeFilter() {
        return submissionTypeFilter;
    }

    /**
     * @param submissionTypeFilter
     *            the submissionTypeFilter to set
     */
    public void setSubmissionTypeFilter(List<String> submissionTypeFilter) {
        this.submissionTypeFilter = submissionTypeFilter;
    }

    /**
     * @return the newCompletionDate
     */
    public String getNewCompletionDate() {
        return newCompletionDate;
    }

    /**
     * @param newCompletionDate
     *            the newCompletionDate to set
     */
    public void setNewCompletionDate(String newCompletionDate) {
        this.newCompletionDate = newCompletionDate;
    }

    /**
     * @return the newCompletionDateComments
     */
    public String getNewCompletionDateComments() {
        return newCompletionDateComments;
    }

    /**
     * @param newCompletionDateComments
     *            the newCompletionDateComments to set
     */
    public void setNewCompletionDateComments(String newCompletionDateComments) {
        this.newCompletionDateComments = newCompletionDateComments;
    }

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @return the distr
     */
    public String getDistr() {
        return distr;
    }

    /**
     * @param distr
     *            the distr to set
     */
    public void setDistr(String distr) {
        this.distr = distr;
    }

    /**
     * @return choice
     */
    public String getChoice() {
        return choice;
    }

    /**
     * @param choice choice
     */
    public void setChoice(String choice) {
        this.choice = choice;
    }

}
