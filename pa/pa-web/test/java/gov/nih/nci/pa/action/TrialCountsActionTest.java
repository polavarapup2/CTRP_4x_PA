/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.pa.dto.LastCreatedDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.service.util.MockLookUpTableServiceBean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author asharma
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TrialCountsActionTest extends AbstractPaActionTest {

    @Override
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test(expected = RuntimeException.class)
    public void testNoAccess() throws PAException {
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.prepare();

    }

    @Test
    public void testAbstractorsWork() throws PAException, JSONException,
            JsonSyntaxException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.setProtocolQueryService(getProtocolQueryMockForAbstractorsWork());
        StreamResult result = action.abstractorsWork();
        Map json = getJsonMap(result);
        System.out.println(json);

        List list = (List) json.get("data");
        assertEquals(1, list.size());

        Map map = (Map) list.get(0);
        assertEquals(2.0, map.get("admin_scientific"));
        assertEquals(1.0, map.get("admin"));
        assertEquals(1.0, map.get("scientific"));
        assertEquals(1.0, map.get("user_id"));
        assertEquals("suAbstractor (SU)", map.get("name"));

    }

    @Test
    public void testTrialDist() throws PAException, JSONException,
            JsonSyntaxException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.setProtocolQueryService(getProtocolQueryMockForDistr());
        StreamResult result = action.trialDist();
        Map json = getJsonMap(result);
        System.out.println(json);

        List list = (List) json.get("data");
        // 10 codes plus total is 10.
        assertEquals(4, list.size());

        Map map = (Map) list.get(0);
        assertEquals("1-3", map.get("range"));
        assertEquals(2.0, map.get("count"));

        map = (Map) list.get(1);
        assertEquals("4-7", map.get("range"));
        assertEquals(1.0, map.get("count"));

        map = (Map) list.get(2);
        assertEquals("8-10", map.get("range"));
        assertEquals(1.0, map.get("count"));

        map = (Map) list.get(3);
        assertEquals(">10", map.get("range"));
        assertEquals(2.0, map.get("count"));
    }


    @Test
    public void testTrialCountsByDate() throws PAException, JSONException,
            JsonSyntaxException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.setFromDate("01/01/2015");
        action.setToDate("03/01/2015");
        action.setProtocolQueryService(getProtocolQueryMockForCountsByDate());
        StreamResult result = action.countsByDate();
        Map json = getJsonMap(result);

        List list = (List) json.get("data");
        assertEquals(61, list.size());

        Map map = (Map) list.get(0);
        assertEquals("01/01/2015", map.get("day"));
        assertEquals(0.0, map.get("pastTenCnt"));
        assertEquals(0.0, map.get("submittedCnt"));
        assertEquals(0.0, map.get("expectedCnt"));

        map = (Map) list.get(4);
        assertEquals("01/05/2015", map.get("day"));
        assertEquals(0.0, map.get("pastTenCnt"));
        assertEquals(2.0, map.get("submittedCnt"));
        assertEquals(0.0, map.get("expectedCnt"));

        Map totals = (Map) list.get(60);
        assertEquals("Total", totals.get("day"));
        assertEquals(7.0, totals.get("pastTenCnt"));
        assertEquals(7.0, totals.get("submittedCnt"));
        assertEquals(7.0, totals.get("expectedCnt"));


        action.setFromDate("01/01/2011");
        action.setToDate("03/01/2012");
        result = action.countsByDate();
        json = getJsonMap(result);

        list = (List) json.get("data");
        assertEquals(427, list.size());
    }


    @Test
    public void testOnHoldTrials() throws PAException, JSONException,
            JsonSyntaxException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.setProtocolQueryService(getProtocolQueryMockForHolds());
        StreamResult result = action.onHoldTrials();
        Map json = getJsonMap(result);
        System.out.println(json);

        List list = (List) json.get("data");
        // 10 codes plus total is 10.
        assertEquals(11, list.size());

        Map map = (Map) list.get(0);
        assertEquals("Submission Incomplete", map.get("reason"));
        assertEquals("Submission Incomplete", map.get("reasonKey"));
        assertEquals(1.0, map.get("count"));

        map = (Map) list.get(1);
        assertEquals("Submission Incomplete -- Missing Documents",
                map.get("reason"));
        assertEquals("Submission Incomplete -- Missing Documents",
                map.get("reasonKey"));
        assertEquals(2.0, map.get("count"));

        map = (Map) list.get(2);
        assertEquals("Invalid Grant", map.get("reason"));
        assertEquals("Invalid Grant", map.get("reasonKey"));
        assertEquals(3.0, map.get("count"));

        map = (Map) list.get(3);
        assertEquals("Pending CTRP Review", map.get("reason"));
        assertEquals("Pending CTRP Review", map.get("reasonKey"));
        assertEquals(4.0, map.get("count"));

        map = (Map) list.get(4);
        assertEquals("Pending Disease Curation", map.get("reason"));
        assertEquals("Pending Disease Curation", map.get("reasonKey"));
        assertEquals(5.0, map.get("count"));

        map = (Map) list.get(5);
        assertEquals("Pending Person Curation", map.get("reason"));
        assertEquals("Pending Person Curation", map.get("reasonKey"));
        assertEquals(6.0, map.get("count"));

        map = (Map) list.get(6);
        assertEquals("Pending Organization Curation", map.get("reason"));
        assertEquals("Pending Organization Curation", map.get("reasonKey"));
        assertEquals(7.0, map.get("count"));

        map = (Map) list.get(7);
        assertEquals("Pending Intervention Curation", map.get("reason"));
        assertEquals("Pending Intervention Curation", map.get("reasonKey"));
        assertEquals(8.0, map.get("count"));

        map = (Map) list.get(8);
        assertEquals("Other (CTRP)", map.get("reason"));
        assertEquals("Other_CTRP", map.get("reasonKey"));
        assertEquals(9.0, map.get("count"));

        map = (Map) list.get(9);
        assertEquals("Other (Submitter)", map.get("reason"));
        assertEquals("Other_Submitter", map.get("reasonKey"));
        assertEquals(10.0, map.get("count"));

        map = (Map) list.get(10);
        assertEquals("Total", map.get("reason"));
        assertEquals("Total", map.get("reasonKey"));
        assertEquals(55.0, map.get("count"));

    }

    @Test
    public void testMilestonesInProgress() throws PAException, JSONException,
            JsonSyntaxException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        TrialCountsAction action = getAction();
        action.setProtocolQueryService(getProtocolQueryMockForMilestones());
        StreamResult result = action.milestonesInProgress();

        Map json = getJsonMap(result);
        System.out.println(json);

        List list = (List) json.get("data");
        // 9 milestones plus total is 10.
        assertEquals(10, list.size());

        // Submission Received Date
        Map map = (Map) list.get(0);
        assertEquals("Submission Received Date", map.get("milestone"));
        assertEquals(1.0, map.get("count"));

        map = (Map) list.get(1);
        assertEquals("Submission Acceptance Date", map.get("milestone"));
        assertEquals(2.0, map.get("count"));

        map = (Map) list.get(2);
        assertEquals("Administrative Processing Start Date",
                map.get("milestone"));
        assertEquals(3.0, map.get("count"));

        map = (Map) list.get(3);
        assertEquals("Ready for Administrative QC Date", map.get("milestone"));
        assertEquals(4.0, map.get("count"));

        map = (Map) list.get(4);
        assertEquals("Administrative QC Start Date", map.get("milestone"));
        assertEquals(5.0, map.get("count"));

        map = (Map) list.get(5);
        assertEquals("Scientific Processing Start Date", map.get("milestone"));
        assertEquals(6.0, map.get("count"));

        map = (Map) list.get(6);
        assertEquals("Ready for Scientific QC Date", map.get("milestone"));
        assertEquals(7.0, map.get("count"));

        map = (Map) list.get(7);
        assertEquals("Scientific QC Start Date", map.get("milestone"));
        assertEquals(8.0, map.get("count"));

        map = (Map) list.get(8);
        assertEquals("Ready for Trial Summary Report Date",
                map.get("milestone"));
        assertEquals(9.0, map.get("count"));

        map = (Map) list.get(9);
        assertEquals("Total", map.get("milestone"));
        assertEquals(45.0, map.get("count"));

    }

    /**
     * @param result
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws JsonSyntaxException
     */
    private Map getJsonMap(StreamResult result) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, IOException, JsonSyntaxException {
        final Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        InputStream is = (InputStream) field.get(result);
        String json = IOUtils.toString(is);
        Gson gson = new Gson();
        final Map fromJson = (Map) gson.fromJson(json, Object.class);
        return fromJson;
    }

    /**
     * @throws PAException
     * 
     */
    private TrialCountsAction getAction() throws PAException {
        TrialCountsAction action = new TrialCountsAction();
        action.setServletRequest(getRequest());
        action.setLookUpService(new MockLookUpTableServiceBean());
        action.setCsmUserUtil(mockCSM());
        ActionUtils.setUserRolesInSession(getRequest());
        return action;
    }

    private CSMUserUtil mockCSM() throws PAException {
        CSMUserUtil mock = mock(CSMUserUtil.class);

        User user = new User();
        user.setUserId(1L);
        user.setLoginName("suAbstractor");

        Map<Long, String> userMap = new HashMap<>();
        userMap.put(1L, user.getLoginName());
        when(mock.getAbstractors()).thenReturn(userMap);
        when(mock.getCSMUserById(eq(Long.valueOf(1)))).thenReturn(user);
        when(mock.getUserGroups(eq(user.getLoginName()))).thenReturn(
                Arrays.asList(Constants.SUABSTRACTOR));
        return mock;
    }

    private ProtocolQueryServiceLocal getProtocolQueryMockForAbstractorsWork()
            throws PAException {
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        final List<StudyProtocolQueryDTO> dtos = new ArrayList<>();

        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(1L);
        dto.getAdminCheckout().setCheckoutBy("suAbstractor");
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(2L);
        dto.getScientificCheckout().setCheckoutBy("suAbstractor");
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(3L);
        dto.getAdminCheckout().setCheckoutBy("suAbstractor");
        dto.getScientificCheckout().setCheckoutBy("suAbstractor");
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(4L);
        dto.getAdminCheckout().setCheckoutBy("suAbstractor");
        dto.getScientificCheckout().setCheckoutBy("suAbstractor");
        dtos.add(dto);

        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(dtos);
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(dtos);
        return mock;
    }

    /**
     * @return
     * @throws PAException
     */
    private ProtocolQueryServiceLocal getProtocolQueryMockForDistr()
            throws PAException {

        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        final List<StudyProtocolQueryDTO> dtos = new ArrayList<>();

        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 1);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 3);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 5);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 9);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 11);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        setField(dto, "bizDaysSinceSubmitted", 12);
        dtos.add(dto);

        when(mock.getWorkload()).thenReturn(dtos);

        return mock;
    }


    /**
     * @return
     * @throws PAException
     */
    private ProtocolQueryServiceLocal getProtocolQueryMockForCountsByDate()
            throws PAException {

        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        final List<StudyProtocolQueryDTO> dtos = new ArrayList<>();

        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/05/2015"));
        dto.setOverriddenExpectedAbstractionCompletionDate(PAUtil.dateStringToDate("01/28/2015"));
        setField(dto, "bizDaysSinceSubmitted", 10);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/05/2015"));
        setField(dto, "bizDaysSinceSubmitted", 4);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/06/2015"));
        setField(dto, "bizDaysSinceSubmitted", 10);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/07/2015"));
        setField(dto, "bizDaysSinceSubmitted", 12);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/08/2015"));
        dto.setOverriddenExpectedAbstractionCompletionDate(PAUtil.dateStringToDate("01/30/2015"));
        setField(dto, "bizDaysSinceSubmitted", 2);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/09/2015"));
        setField(dto, "bizDaysSinceSubmitted", 14);
        dtos.add(dto);

        dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(new Random().nextLong());
        dto.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("01/30/2015"));
        setField(dto, "bizDaysSinceSubmitted", 2);
        dtos.add(dto);

        when(mock.getWorkload()).thenReturn(dtos);

        return mock;
    }

    private void setField(StudyProtocolQueryDTO dto, String field, Integer n) {
        final Field f = ReflectionUtils.findField(StudyProtocolQueryDTO.class,
                field);
        ReflectionUtils.makeAccessible(f);
        ReflectionUtils.setField(f, dto, n);
    }

    /**
     * @return
     * @throws PAException
     */
    private ProtocolQueryServiceLocal getProtocolQueryMockForHolds()
            throws PAException {

        final List<String> holdCodes = Arrays
                .asList(new MockLookUpTableServiceBean().getPropertyValue(
                        "dashboard.counts.onholds").split(","));
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        final List<StudyProtocolQueryDTO> dtos = new ArrayList<>();

        for (String code : holdCodes) {
            for (int i = 0; i <= holdCodes.indexOf(code); i++) {
                StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
                dto.setStudyProtocolId(new Random().nextLong());
                dto.setActiveHoldDate(new Date());
                final OnholdReasonCode enumCode = OnholdReasonCode
                        .getByCode(code.replaceAll("\\s+\\(.*", ""));
                dto.setActiveHoldReason(enumCode);
                if (enumCode == OnholdReasonCode.OTHER) {
                    String cat = code.replaceFirst("^.*?\\(", "").replaceFirst(
                            "\\)", "");
                    dto.setActiveHoldReasonCategory(cat);
                }
                dtos.add(dto);
            }
        }

        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(dtos);
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(dtos);

        return mock;
    }

    /**
     * @return
     * @throws PAException
     */
    private ProtocolQueryServiceLocal getProtocolQueryMockForMilestones()
            throws PAException {

        final List<String> milestoneCodes = Arrays
                .asList(new MockLookUpTableServiceBean().getPropertyValue(
                        "dashboard.counts.milestones").split(","));
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        final List<StudyProtocolQueryDTO> dtos = new ArrayList<>();

        for (String code : milestoneCodes) {
            for (int i = 0; i <= milestoneCodes.indexOf(code); i++) {
                StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
                dto1.setStudyProtocolId(new Random().nextLong());
                dto1.getAdminCheckout().setCheckoutBy("suAbstractor");
                dto1.getMilestones().getLastMilestone()
                        .setMilestone(MilestoneCode.getByCode(code));
                dto1.getMilestones()
                        .getLastMilestone()
                        .setMilestoneDate(
                                PAUtil.dateStringToTimestamp("06/01/2015"));
                dtos.add(dto1);
            }
        }

        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(dtos);
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(dtos);

        return mock;
    }
}