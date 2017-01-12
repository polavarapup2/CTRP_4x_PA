/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionServiceBeanLocal;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mockrunner.mock.web.MockHttpServletResponse;

/**
 * @author dkrylov
 * 
 */
public class StatusHistoryManagementActionTest extends AbstractRegWebTest {

    private StatusHistoryManagementActionImpl action;

    @Before
    public void setup() throws Exception {

        final TrialDTO trialDTO = new TrialDTO();
        trialDTO.setIdentifier("123");
        ServletActionContext.getRequest().getSession()
                .setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);

        initAction();

    }

    /**
     * @throws IOException
     * @throws PAException
     * @throws ParseException
     * 
     */
    private void initAction() throws PAException, IOException, ParseException {
        LookUpTableServiceRemote lookUp = mock(LookUpTableServiceRemote.class);
        when(lookUp.getPropertyValueFromCache(eq("status.rules"))).thenReturn(
                IOUtils.toString(getClass().getClassLoader()
                        .getResourceAsStream("statusvalidations.json")));

        StatusTransitionServiceBeanLocal transitionBean = new StatusTransitionServiceBeanLocal();
        transitionBean.setLookUpTableServiceRemote(lookUp);

        ParticipatingOrgServiceLocal siteBean = mock(ParticipatingOrgServiceLocal.class);

        ParticipatingOrgDTO site = new ParticipatingOrgDTO();
        site.setPoId("1");
        site.setName("DCP");
        site.setRecruitmentStatusDate(new Timestamp(DateUtils.parseDate(
                "01/01/2015", new String[] { "MM/dd/yyyy" }).getTime()));
        site.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE);

        when(siteBean.getTreatingSites(eq(123L))).thenReturn(
                Arrays.asList(site));

        action = new StatusHistoryManagementActionImpl();
        action.setServletRequest(ServletActionContext.getRequest());
        action.setServletResponse(ServletActionContext.getResponse());
        action.setStatusTransitionService(transitionBean);
        action.setParticipatingOrgService(siteBean);
    }

    @Test
    public void addStatus() throws JSONException, ParseException, IOException,
            NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        addStatusAndVerify();

    }

    @Test
    public void clearStatusHistory() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, PAException {
        addStatusAndVerify();

        initAction();
        StreamResult result = action.clearStatusHistory();
        assertEmptyJson(result);

        Collection<StatusDto> hist = action.getStatusHistoryFromSession();
        assertEquals(0, hist.size());

    }

    @Test
    public void editStatusSortsHistory() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, PAException {
        addStatusAndVerify();
        initAction();

        action.setStatusCode("Closed to Accrual and Intervention");
        action.setStatusDate("01/03/2015");
        action.setReason(StringUtils.repeat("Stopped.", 1000));
        assertEmptyJson(action.addStatus());

        List<StatusDto> hist = new ArrayList<StatusDto>(
                action.getStatusHistoryFromSession());
        assertEquals(2, hist.size());

        StatusDto s1 = hist.get(0);
        StatusDto s2 = hist.get(1);
        assertEquals("CLOSED_TO_ACCRUAL", s1.getStatusCode());
        assertEquals("CLOSED_TO_ACCRUAL_AND_INTERVENTION", s2.getStatusCode());

        // Edit should re-order by status date.
        initAction();
        action.setUuid(s2.getUuid());
        action.setStatusCode("Active");
        action.setStatusDate("01/01/2015");
        assertEmptyJson(action.editStatus());

        hist = new ArrayList<StatusDto>(action.getStatusHistoryFromSession());
        assertEquals(2, hist.size());

        s1 = hist.get(0);
        s2 = hist.get(1);
        assertEquals("ACTIVE", s1.getStatusCode());
        assertEquals("CLOSED_TO_ACCRUAL", s2.getStatusCode());

    }

    @Test
    public void editStatus() throws JSONException, ParseException, IOException,
            NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException, PAException {
        addStatusAndVerify();

        initAction();
        Collection<StatusDto> hist = action.getStatusHistoryFromSession();
        StatusDto stat = hist.iterator().next();

        action.setUuid(stat.getUuid());
        action.setStatusCode("Active");
        action.setStatusDate("01/02/2016");
        action.setReason("Resumed.");
        action.setComment("No Comment.");

        StreamResult result = action.editStatus();
        assertEmptyJson(result);

        hist = action.getStatusHistoryFromSession();
        assertEquals(1, hist.size());
        StatusDto editedStat = hist.iterator().next();

        assertTrue(editedStat == stat);
        assertEquals(StudyStatusCode.ACTIVE.name(), stat.getStatusCode());
        assertEquals(DateUtils.parseDate("01/02/2016",
                new String[] { "MM/dd/yyyy" }), stat.getStatusDate());
        assertEquals("Resumed.", stat.getReason());
        assertEquals("No Comment.", stat.getComments());
        assertFalse(stat.isDeleted());

    }

    @Test
    public void deleteStatus() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, PAException {
        addStatusAndVerify();

        initAction();
        Collection<StatusDto> hist = action.getStatusHistoryFromSession();
        StatusDto stat = hist.iterator().next();

        action.setUuid(stat.getUuid());
        action.setComment("Deleted.");

        StreamResult result = action.deleteStatus();
        assertEmptyJson(result);

        assertEquals(0, action.getStatusHistoryFromSession().size());
        assertEquals(1, action.getDeletedStatusHistoryFromSession().size());
        assertEquals(stat, action.getDeletedStatusHistoryFromSession()
                .iterator().next());

        assertEquals(StudyStatusCode.CLOSED_TO_ACCRUAL.name(),
                stat.getStatusCode());
        assertEquals(DateUtils.parseDate("01/02/2015",
                new String[] { "MM/dd/yyyy" }), stat.getStatusDate());
        assertEquals(StringUtils.repeat("Stopped.", 1000).substring(0, 1000),
                stat.getReason());
        assertEquals("Deleted.", stat.getComments());
        assertTrue(stat.isDeleted());

    }

    @Test
    public void handleExceptionDuringAjax() throws JSONException,
            ParseException, IOException, NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        action.setStatusCode("Closed to Accrual And Everything Else"); // BAD
                                                                       // status
                                                                       // code.

        StreamResult result = action.addStatus();
        assertNull(result);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST,
                ((MockHttpServletResponse) ServletActionContext.getResponse())
                        .getErrorCode());
        assertTrue(((MockHttpServletResponse) ServletActionContext
                .getResponse()).containsHeader("msg"));

    }

    @Test
    public void getStatusHistoryNoValidation() throws JSONException,
            ParseException, IOException, NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, JsonSyntaxException, PAException {
        action.setStatusCode("Closed to Accrual");
        action.setStatusDate("01/02/2015");
        action.setReason("Stopped.");
        action.setComment("Comment.");

        assertEmptyJson(action.addStatus());
        initAction();

        action.setStatusCode("Approved");
        action.setStatusDate("01/01/2015");
        action.setComment("Approved.");

        assertEmptyJson(action.addStatus());
        initAction();

        final Map json = getJsonMap(action.getStatusHistory());
        List statusList = (List) json.get("data");
        assertEquals(2, statusList.size());
        Map status1 = (Map) statusList.get(0);
        Map status2 = (Map) statusList.get(1);

        assertEquals("Approved", status1.get("statusCode"));
        assertEquals("01/01/2015", status1.get("statusDate"));
        assertEquals("Approved.", status1.get("comments"));
        assertEquals("", status1.get("validationErrors"));
        assertFalse(StringUtils.isBlank((String) status1.get("DT_RowId")));

        assertEquals("Closed to Accrual", status2.get("statusCode"));
        assertEquals("01/02/2015", status2.get("statusDate"));
        assertEquals("Comment.", status2.get("comments"));
        assertEquals("", status2.get("validationErrors"));
        assertFalse(StringUtils.isBlank((String) status2.get("DT_RowId")));

        System.out.println(json);
    }

    @Test
    public void getStatusHistoryWithValidation() throws JSONException,
            ParseException, IOException, NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, JsonSyntaxException, PAException {
        action.setStatusCode("Closed to Accrual and Intervention");
        action.setStatusDate("01/02/2015");
        action.setReason("Stopped.");
        action.setComment("Comment.");

        assertEmptyJson(action.addStatus());
        initAction();

        action.setStatusCode("Active");
        action.setStatusDate("01/01/2015");
        action.setComment("Approved.");

        assertEmptyJson(action.addStatus());
        initAction();

        action.setRunValidations(true);
        final Map json = getJsonMap(action.getStatusHistory());
        List statusList = (List) json.get("data");
        assertEquals(2, statusList.size());
        Map status1 = (Map) statusList.get(0);
        Map status2 = (Map) statusList.get(1);

        assertEquals(
                "<div class='warning'>Interim status [IN REVIEW] is missing</div><div class='warning'>Interim status [APPROVED] is missing</div>",
                status1.get("validationErrors"));
        assertEquals(
                "<div class='warning'>Interim status [CLOSED TO ACCRUAL] is missing</div>",
                status2.get("validationErrors"));

        System.out.println(json);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void getValidationSummary() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException,
            JsonSyntaxException, PAException {
        action.setStatusCode("Closed to Accrual and Intervention");
        action.setStatusDate("02/02/2015");
        action.setReason("Stopped.");
        action.setComment("Comment.");

        assertEmptyJson(action.addStatus());
        initAction();

        action.setStatusCode("Active");
        action.setStatusDate("01/01/2015");
        action.setComment("Approved.");

        assertEmptyJson(action.addStatus());
        initAction();

        Map json = getJsonMap(action.getValidationSummary());
        System.out.println(json);
        assertFalse((Boolean) json.get("errors"));
        assertTrue((Boolean) json.get("warnings"));

        initAction();
        action.setStatusCode("Closed to Accrual");
        action.setStatusDate("02/01/2015");
        action.setReason("Stopped.");

        assertEmptyJson(action.addStatus());
        initAction();

        json = getJsonMap(action.getValidationSummary());
        System.out.println(json);
        assertFalse((Boolean) json.get("errors"));
        assertTrue((Boolean) json.get("warnings"));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void mustDisplayOpenSitesWarning() throws JSONException,
            ParseException, IOException, NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, JsonSyntaxException, PAException {
        action.setStatusCode("Active");
        action.setStatusDate("01/01/2015");

        assertEmptyJson(action.addStatus());
        initAction();

        Map json = getJsonMap(action.mustDisplayOpenSitesWarning());
        assertFalse((Boolean) json.get("answer"));

        action.setStatusCode("Closed to Accrual");
        action.setStatusDate("02/02/2015");

        assertEmptyJson(action.addStatus());
        initAction();

        json = getJsonMap(action.mustDisplayOpenSitesWarning());
        assertTrue((Boolean) json.get("answer"));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void getOpenSites() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException,
            JsonSyntaxException, PAException {

        Map json = getJsonMap(action.getOpenSites());
        List list = (List) json.get("data");
        assertEquals(1, list.size());

        Map site = (Map) list.get(0);
        assertEquals("1", site.get("poID"));
        assertEquals("DCP", site.get("name"));
        assertEquals("Active", site.get("statusCode"));
        assertEquals("01/01/2015", site.get("statusDate"));

    }

    /**
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws JsonSyntaxException
     */
    private void addStatusAndVerify() throws JSONException, ParseException,
            IOException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException,
            JsonSyntaxException {
        action.setStatusCode("Closed to Accrual");
        action.setStatusDate("01/02/2015");
        action.setReason(StringUtils.repeat("Stopped.", 1000));
        action.setComment(StringUtils.repeat("Comment.", 1000));

        StreamResult result = action.addStatus();
        assertEmptyJson(result);

        Collection<StatusDto> hist = action.getStatusHistoryFromSession();
        assertEquals(1, hist.size());
        StatusDto stat = hist.iterator().next();

        assertEquals(StudyStatusCode.CLOSED_TO_ACCRUAL.name(),
                stat.getStatusCode());
        assertEquals(DateUtils.parseDate("01/02/2015",
                new String[] { "MM/dd/yyyy" }), stat.getStatusDate());
        assertEquals(StringUtils.repeat("Stopped.", 1000).substring(0, 1000),
                stat.getReason());
        assertEquals(StringUtils.repeat("Comment.", 1000).substring(0, 1000),
                stat.getComments());
        assertFalse(stat.isDeleted());
    }

    /**
     * @param result
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws JsonSyntaxException
     */
    private void assertEmptyJson(StreamResult result)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException,
            JsonSyntaxException {
        final Map fromJson = getJsonMap(result);
        assertTrue(fromJson.isEmpty());
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

    public final class StatusHistoryManagementActionImpl extends
            StatusHistoryManagementAction {

        /**
         * 
         */
        private static final long serialVersionUID = -9197184766503038167L;

        @Override
        protected boolean requiresReasonText(CodedEnum<String> statEnum) {
            return true;
        }

        @Override
        protected CodedEnum<String> getStatusEnumByCode(String code) {
            return StudyStatusCode.getByCode(code);
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected final Class getStatusEnumClass() {
            return StudyStatusCode.class;
        }

        @Override
        public boolean isOpenSitesWarningRequired() {
            return true;
        }

        @Override
        protected TransitionFor getStatusTypeHandledByThisClass() {
            return TransitionFor.TRIAL_STATUS;
        }

        @Override
        protected TrialType getTrialTypeHandledByThisClass() {
            return TrialType.COMPLETE;
        }

        @Override
        public Collection<StatusDto> getStatusHistoryFromSession() {
            return super.getStatusHistoryFromSession();
        }

    }

}
