package gov.nih.nci.pa.action;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;
import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author dkrylov
 * 
 */
public class TrialCountsAction extends ActionSupport implements Preparable,
        ServletRequestAware {

    private static final String JSON_KEY_DAY = "day";
    private static final String JSON_KEY_BDAY = "bday";
    private static final String JSON_KEY_SUBMITTED_CNT = "submittedCnt";
    private static final String JSON_KEY_PAST_TEN_CNT = "pastTenCnt";
    private static final String JSON_KEY_EXPECTED_CNT = "expectedCnt";
    private static final String JSON_KEY_DT_ROW_ID = "DT_RowId";
    private static final String JSON_KEY_COUNT = "count";
    private static final String JSON_KEY_DATA = "data";
    private static final String JSON_VALUE_TOTAL = "Total";
    private static final int TEN = 10;

    private ProtocolQueryServiceLocal protocolQueryService;
    private LookUpTableServiceRemote lookUpService;
    private CSMUserUtil csmUserUtil;

    private static final long serialVersionUID = -8919884566276557104L;
    private static final String UTF_8 = "UTF-8";

    private HttpServletRequest request;
    private String fromDate;
    private String toDate;

    // CHECKSTYLE:OFF

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult abstractorsWork() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(JSON_KEY_DATA, arr);
        abstractorsWork(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void abstractorsWork(JSONArray arr) throws PAException,
            JSONException {
        final Map<Long, String> users = csmUserUtil.getAbstractors();
        for (long userID : users.keySet()) {
            String userName = users.get(userID);
            final String loginName = csmUserUtil.getCSMUserById(userID)
                    .getLoginName();
            List<String> groups = csmUserUtil.getUserGroups(loginName);
            userName += (" (" + determinePostFixBasedOnGroupMembership(groups) + ")");
            // counts
            int admin = 0;
            int scientific = 0;
            int adminScientific = 0;

            StudyProtocolQueryCriteria criteria = getCriteria();
            criteria.setStudyLockedBy(true);
            criteria.setUserLastCreated(loginName);

            for (StudyProtocolQueryDTO dto : protocolQueryService
                    .getStudyProtocolByCriteria(criteria)) {
                final String adminBy = dto.getAdminCheckout().getCheckoutBy();
                final String sciBy = dto.getScientificCheckout()
                        .getCheckoutBy();
                if (loginName.equals(adminBy) && loginName.equals(sciBy)) {
                    adminScientific++;
                } else if (loginName.equals(adminBy)) {
                    admin++;
                } else if (loginName.equals(sciBy)) {
                    scientific++;
                }
            }

            // Put counts into JSON
            if (admin + scientific + adminScientific > 0) {
                JSONObject data = new JSONObject();
                data.put("name", userName);
                data.put("admin", admin);
                data.put("scientific", scientific);
                data.put("admin_scientific", adminScientific);
                data.put("user_id", userID);
                arr.put(data);
            }
        }
    }

    private String determinePostFixBasedOnGroupMembership(
            final List<String> groups) {
        boolean admin = groups.contains(Constants.ADMIN_ABSTRACTOR);
        boolean scientific = groups.contains(Constants.SCIENTIFIC_ABSTRACTOR);
        boolean sup = groups.contains(Constants.SUABSTRACTOR);
        return sup ? "SU" : (admin && scientific ? "AS"
                : (admin && !scientific ? "AS" : "SC"));
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult trialDist() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(JSON_KEY_DATA, arr);
        trialDist(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void trialDist(JSONArray arr) throws PAException, JSONException {
        final List<String> ranges = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.trialdist").split(","));

        // TreeMap ensures holds are displayed in the same order as in property.
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return ranges.indexOf(s1) - ranges.indexOf(s2);
                    }
                });

        // All ranges must be in the map, even those with zero counts.
        for (String range : ranges) {
            countsMap.put(range, 0);
        }

        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getWorkload();
        for (final StudyProtocolQueryDTO dto : results) {
            int days = dto.getBizDaysSinceSubmitted();
            for (String range : ranges) {
                if (PAUtil.isInRange(days, range)) {
                    countsMap.put(range, countsMap.get(range) + 1);
                }
            }
        }

        for (String range : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("range", range);
            data.put(JSON_KEY_COUNT, (int) countsMap.get(range));
            data.put(JSON_KEY_DT_ROW_ID, range);
            arr.put(data);
        }

    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     * @throws PAException                  PAException
     * @throws JSONException                JSONException
     */
    public StreamResult countsByDate() throws UnsupportedEncodingException,
            PAException, JSONException {

        //grab the from-date and to-date parameters from request
        Date from = PAUtil.dateStringToDate(fromDate);
        Date to = PAUtil.dateStringToDate(toDate);

        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(JSON_KEY_DATA, arr);
        if (from != null || to != null) {
            countsByDate(from, to, arr);
        }

        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    @SuppressWarnings({ "PMD.NPathComplexity"})
    private void countsByDate(Date from, Date to, JSONArray arr) throws PAException, JSONException {

        List<StudyProtocolQueryDTO> results = protocolQueryService.getWorkload();

        //Loop and find date range of workload
        Date lowest = null;
        Date highest = null;

        //also find the counts at various levels
        LinkedHashMap<String, Integer> submittedIndex = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> tenDaysIndex = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> expectedOnIndex = new LinkedHashMap<String, Integer>();

        //Note :- when user clicks on "count", we need to show in the search results the filtered data.
        // for this, we will use the existing search functionality which allows filtering based on "submitted date"
        // so, for 'past 10 business day' & 'expected completion' we need to also capture the lowest submitted and

        for (StudyProtocolQueryDTO dto : results) {
            Date submittedOn = dto.getLastCreated().getDateLastCreated();

            //filter if from & to dates are provided
          //filter if from & to dates are provided
            // if (!((from != null && submittedOn.before(from)) || (to != null && submittedOn.after(to)))) {

                 //find the lowest and highest dates
                 if (lowest == null || lowest.after(submittedOn)) {
                     lowest = submittedOn;
                 }
                 if (highest == null || highest.before(submittedOn)) {
                     highest = submittedOn;
                 }


                 String strDate = DateFormatUtils.format(submittedOn, PAUtil.DATE_FORMAT);

                 //update submitted index
                 Integer nSubmitted = submittedIndex.get(strDate);
                 nSubmitted = nSubmitted != null ? nSubmitted + 1 : 1;
                 submittedIndex.put(strDate, nSubmitted);
                 
                 Date tenBusinessDay =dto.getCalculatedSubmissionPlusTenBizDate();
                 if ((tenBusinessDay.after(from) && tenBusinessDay.before(to))
                      || (DateUtils.isSameDay(tenBusinessDay, from)|| DateUtils.isSameDay(tenBusinessDay, to))  ) {
                    String strTenDate = DateFormatUtils.format(tenBusinessDay, PAUtil.DATE_FORMAT);
                     Integer n10Days = tenDaysIndex.get(strTenDate);
                     n10Days = n10Days != null ? n10Days + 1 : 1;
                     tenDaysIndex.put(strTenDate, n10Days);
                 }
                 
                 //update the expected index
                 Date expectedDate = dto.getExpectedAbstractionCompletionDate();
                 String strExpectedDate = DateFormatUtils.format(dto.getExpectedAbstractionCompletionDate(), PAUtil.DATE_FORMAT);
                 if ((expectedDate.after(from) && expectedDate.before(to))
                         || (DateUtils.isSameDay(expectedDate, from)|| DateUtils.isSameDay(expectedDate, to))  ) {
                     Integer nExpected = expectedOnIndex.get(strExpectedDate);
                     nExpected = nExpected != null ? nExpected + 1 : 1;
                     expectedOnIndex.put(strExpectedDate, nExpected);
                 }
                 

             }

        // }


        //return if highest or lowest is null
        if (highest == null || lowest == null) {
            return;
        }

        Date begin = from != null ? from : lowest;
        Date end = to != null ? to : highest;
        int i = 0;

        Integer totalTrialsSubmitted = 0;
        Integer totalTrialsPast10Days = 0;
        Integer totalTrialsExpected = 0;

        //for each day layout the respective counts
        while (begin.compareTo(end) <= 0) {
            boolean isBusinessDay = PAUtil.isBusinessDay(begin);
            String strDate = DateFormatUtils.format(begin, PAUtil.DATE_FORMAT);
            JSONObject data = new JSONObject();
            data.put(JSON_KEY_DAY, strDate);
            data.put(JSON_KEY_BDAY, isBusinessDay);
            data.put(JSON_KEY_SUBMITTED_CNT, 0);
            data.put(JSON_KEY_PAST_TEN_CNT, 0);
            data.put(JSON_KEY_EXPECTED_CNT, 0);
            data.put(JSON_KEY_DT_ROW_ID, i++);

            Integer nSubmitted = submittedIndex.get(strDate);
            if (nSubmitted != null) {
                data.put(JSON_KEY_SUBMITTED_CNT, nSubmitted);
                totalTrialsSubmitted += nSubmitted;
            }

            Integer nPast10Days = tenDaysIndex.get(strDate);
            if (nPast10Days != null) {
                data.put(JSON_KEY_PAST_TEN_CNT, nPast10Days);
                totalTrialsPast10Days += nPast10Days;
            }
            Integer nExpected = expectedOnIndex.get(strDate);
            if (nExpected != null) {
                data.put(JSON_KEY_EXPECTED_CNT, nExpected);
                totalTrialsExpected += nExpected;
            }

            arr.put(data);
            begin = DateUtils.addDays(begin, 1);
        }

        //add the totals row
        JSONObject data = new JSONObject();
        data.put(JSON_KEY_DAY, JSON_VALUE_TOTAL);
        data.put(JSON_KEY_SUBMITTED_CNT, totalTrialsSubmitted);
        data.put(JSON_KEY_PAST_TEN_CNT, totalTrialsPast10Days);
        data.put(JSON_KEY_EXPECTED_CNT, totalTrialsExpected);
        data.put(JSON_KEY_BDAY, false);

        data.put(JSON_KEY_DT_ROW_ID, i++);
        arr.put(data);

    }


    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult onHoldTrials() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(JSON_KEY_DATA, arr);
        onHoldTrials(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void onHoldTrials(JSONArray arr) throws PAException, JSONException {
        final List<String> holdCodes = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.onholds").split(","));

        // TreeMap ensures holds are displayed in the same order as in property.
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return holdCodes.indexOf(s1) - holdCodes.indexOf(s2);
                    }
                });

        // All on-hold reasons must be in the map, even those with zero counts.
        for (String code : holdCodes) {
            countsMap.put(code, 0);
        }

        final List<StudyProtocolQueryDTO> results = getTrialsForOnHoldCount();
        for (StudyProtocolQueryDTO dto : results) {
            String code = dto.getActiveHoldReason().getCode();
            String cat = dto.getActiveHoldReasonCategory();
            String key = OnholdReasonCode.OTHER.getCode()
                    .equalsIgnoreCase(code) ? code + " (" + cat + ")" : code;
            Integer count = countsMap.get(key);
            if (count != null) {
                count++;
                countsMap.put(key, count);
            }
        }

        int total = 0;
        for (String code : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("reason", code);
            data.put("reasonKey", code.replaceFirst("\\s+\\(", "_")
                    .replaceFirst("\\)", ""));
            data.put(JSON_KEY_COUNT, (int) countsMap.get(code));
            data.put(JSON_KEY_DT_ROW_ID, code);
            arr.put(data);
            total += countsMap.get(code);
        }
        JSONObject data = new JSONObject();
        data.put("reason", JSON_VALUE_TOTAL);
        data.put("reasonKey", JSON_VALUE_TOTAL);
        data.put(JSON_KEY_COUNT, total);
        data.put(JSON_KEY_DT_ROW_ID, "TotalHold");
        arr.put(data);
    }

    private List<StudyProtocolQueryDTO> getTrialsForOnHoldCount()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getCriteria();
        criteria.setHoldStatus(PAConstants.ON_HOLD);
        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getStudyProtocolByCriteria(criteria, SKIP_ALTERNATE_TITLES,
                        SKIP_LAST_UPDATER_INFO, SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
        return results;
    }

    /**
     * @return
     */
    private StudyProtocolQueryCriteria getCriteria() {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(true);
        criteria.setExcludeTerminatedTrials(true);
        return criteria;
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult milestonesInProgress()
            throws UnsupportedEncodingException, PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put(JSON_KEY_DATA, arr);
        milestonesInProgress(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void milestonesInProgress(JSONArray arr) throws PAException,
            JSONException {

        final List<String> milestoneCodes = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.milestones").split(","));
        final List<StudyProtocolQueryDTO> results = getTrialsForMilestoneCount();
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return milestoneCodes.indexOf(s1)
                                - milestoneCodes.indexOf(s2);
                    }
                });

        // All milestones must be in the map, even those with zero counts.
        for (String code : milestoneCodes) {
            countsMap.put(code, 0);
        }

        for (StudyProtocolQueryDTO dto : results) {
            String code = dto.getMilestones().getLastMilestone().getMilestone()
                    .getCode();
            Integer count = countsMap.get(code);
            if (count == null) {
                count = 0;
            }
            count++;
            countsMap.put(code, count);
        }

        int total = 0;
        for (String code : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("milestone", code);
            data.put(JSON_KEY_COUNT, (int) countsMap.get(code));
            data.put(JSON_KEY_DT_ROW_ID, code);
            arr.put(data);
            total += countsMap.get(code);
        }
        JSONObject data = new JSONObject();
        data.put("milestone", JSON_VALUE_TOTAL);
        data.put(JSON_KEY_COUNT, total);
        data.put("DT_RowId", "TotalMilestone");
        arr.put(data);
    }

    // CHECKSTYLE:ON
    /**
     * @return
     * @throws PAException
     */
    private List<StudyProtocolQueryDTO> getTrialsForMilestoneCount()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getCriteria();
        criteria.setStudyMilestone(Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.milestones").split(",")));
        criteria.setHoldStatus(PAConstants.NOT_ON_HOLD);
        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getStudyProtocolByCriteria(criteria, SKIP_ALTERNATE_TITLES,
                        SKIP_LAST_UPDATER_INFO, SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
        protocolQueryService.populateMilestoneHistory(results);
        return results;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
    }

    @Override
    public void prepare() {
        ActionUtils.setUserRolesInSession(request);
        if (!canSeeTrialCounts()) {
            throw new RuntimeException("Unautorized"); // NOPMD
        }
        protocolQueryService = PaRegistry.getProtocolQueryService();
        lookUpService = PaRegistry.getLookUpTableService();
        csmUserUtil = CSMUserService.getInstance();
    }

    private boolean canSeeTrialCounts() {
        return isInRole(IS_SU_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
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
     * @param lookUpService
     *            the lookUpService to set
     */
    public void setLookUpService(LookUpTableServiceRemote lookUpService) {
        this.lookUpService = lookUpService;
    }

    /**
     * @param csmUserUtil
     *            the csmUserUtil to set
     */
    public void setCsmUserUtil(CSMUserUtil csmUserUtil) {
        this.csmUserUtil = csmUserUtil;
    }

    /**
     * Returns the from date
     * @return - the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the fromDate
     * @param fromDate    Sets the fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Gets the toDate
     * @return   toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the toDate
     * @param toDate  - toDate
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
