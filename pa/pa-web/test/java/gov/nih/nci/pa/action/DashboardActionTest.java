/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 * 
 */
public class DashboardActionTest extends AbstractPaActionTest {

    @Override
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void testNoDashboardAccess() throws PAException {
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("nonAbstractorLanding", action.execute());

    }

    @Test
    public void testSuAbstractorRedirectToSearchScreen() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("suAbstractorLanding", action.execute());
    }

    @Test
    public void testAdminScientificAbstractorSearch() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("abstractorLanding", action.execute());
        final List trials = (List) getRequest().getSession().getAttribute(
                "workload");
        assertNotNull(trials);
        assertEquals(3, trials.size());
        assertNull(getRequest().getAttribute("toggleResultsTab"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBizDays() throws PAException, NoSuchFieldException,
            SecurityException, IllegalArgumentException,
            IllegalAccessException, IOException {
        DashboardAction action = getAction();
        action.setDateFrom("06/08/2015");
        action.setDateTo("06/09/2015");
        final StreamResult result = (StreamResult) action.bizDays();
        final Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        assertEquals("1", IOUtils.toString((InputStream) field.get(result)));
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Test
    public void testUpdateExpectedAbstractionCompletionDate()
            throws PAException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {

        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        StudyProtocolDTO dto = action.getStudyProtocolService()
                .getStudyProtocol(IiConverter.convertToIi(1L));
        action.setNewCompletionDate("01/01/2015");
        action.setNewCompletionDateComments("Comments");

        final StreamResult result = (StreamResult) action
                .updateExpectedAbstractionCompletionDate();
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Test(expected = PAException.class)
    public void testUpdateExpectedAbstractionCompletionDateOnlyForSuperAbstractors()
            throws PAException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, IOException {

        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        StudyProtocolDTO dto = action.getStudyProtocolService()
                .getStudyProtocol(IiConverter.convertToIi(1L));
        action.setNewCompletionDate("01/01/2015");
        action.setNewCompletionDateComments("Comments");

        action.updateExpectedAbstractionCompletionDate();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmissionTypeFilter() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        action.setSubmissionTypeFilter(Arrays.asList("Abbreviated", "Complete",
                "Amendment"));
        assertEquals("abstractorLanding", action.filter());
        List<StudyProtocolQueryDTO> trials = (List) getRequest().getSession()
                .getAttribute("workload");
        assertEquals(3, trials.size());

        action = getAction();
        action.setSubmissionTypeFilter(Arrays.asList("Abbreviated"));
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(1, trials.size());
        assertTrue(trials.get(0).isProprietaryTrial());

        action = getAction();
        action.setSubmissionTypeFilter(Arrays.asList("Amendment"));
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());
        assertFalse(trials.get(0).isProprietaryTrial());
        assertTrue(trials.get(0).getAmendmentDate() != null);

        action = getAction();
        action.setSubmissionTypeFilter(Arrays.asList("Complete"));
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(0, trials.size());

        action = getAction();
        action.setSubmissionTypeFilter(new ArrayList<String>());
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

        action = getAction();
        action.setSubmissionTypeFilter(Arrays.asList("Abbreviated"));
        assertEquals("abstractorLanding", action.execute());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());
        assertTrue(action.getSubmissionTypeFilter().isEmpty());

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testDateRangeFilter() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/03/2015");
        action.setDateTo("06/05/2015");
        action.setChoice("limit");
        assertEquals("abstractorLanding", action.filter());
        List<StudyProtocolQueryDTO> trials = (List) getRequest().getSession()
                .getAttribute("workload");
        assertEquals(1, trials.size());
        assertEquals(PAUtil.dateStringToTimestamp("06/04/2015"), trials.get(0)
                .getActiveHoldDate());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("06/01/2015");
        action.setChoice("limit");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(1, trials.size());
        assertEquals(PAUtil.dateStringToTimestamp("06/01/2015"), trials.get(0)
                .getActiveHoldDate());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("06/04/2015");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("05/30/2015");
        action.setDateTo("05/30/2015");
        action.setChoice("limit");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(0, trials.size());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

        action = getAction();
        action.setDateFrom("05/30/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

        action = getAction();
        action.setDateTo("06/04/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

        // ensure execute resets any active filter.
        action = getAction();
        action.setDateFrom("05/30/2015");
        action.setDateTo("06/04/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.execute());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());
        assertNull(action.getDateFilterField());
        assertNull(action.getDateTo());
        assertNull(action.getDateFrom());

        // ensure invalid date format does not error out, assumes null date
        // instead
        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("56/01/2010");
        assertEquals("abstractorLanding", action.filter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(3, trials.size());

    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testNullDateFilter() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setChoice("nullDate");
        assertEquals("abstractorLanding", action.filter());
        List<StudyProtocolQueryDTO> trials = (List) getRequest().getSession()
                .getAttribute("workload");
        assertEquals(1, trials.size());
        assertEquals(null, trials.get(0)
                .getActiveHoldDate());

       

    }


    @Test
    public void testSearchByDistribution() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();

        action.setDistr(">10");
        assertEquals("suAbstractorLanding", action.searchByDistribution());
        List trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(1, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));

        action = getAction();
        action.setDistr("1-3");
        assertEquals("suAbstractorLanding", action.searchByDistribution());
        trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(1, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));

        action = getAction();
        action.setDistr("1-20");
        assertEquals("suAbstractorLanding", action.searchByDistribution());
        trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));

        action = getAction();
        action.setDistr("<16");
        assertEquals("suAbstractorLanding", action.searchByDistribution());
        trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));

        action = getAction();
        action.setDistr("0-1");
        assertEquals("suAbstractorLanding", action.searchByDistribution());
        trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(0, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));
    }


    @Test
    public void testSearchByCountType() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();

        action.setCountRangeFrom("05/25/2013");
        action.setCountRangeTo("07/25/2013");

        //When I do not pass coutForDay and countType prameters
        assertEquals("suAbstractorLanding", action.searchByCountType());
        List trials = (List) getRequest().getSession().getAttribute("dashboardSearchResults");

        //Then result should not be filtered
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));

        //When I set the day I am interested.
         action.setCountForDay("05/25/2013");
         action.setCountType("submittedCnt");
         assertEquals("suAbstractorLanding", action.searchByCountType());
         trials = (List) getRequest().getSession().getAttribute("dashboardSearchResults");

         //Then result should see the result filtered
         assertNotNull(trials);
         assertEquals(1, trials.size());

        //Also when I ask for the different count type on a different date that have data
        action.setCountForDay("06/25/2013");
        action.setCountType("expectedCnt");
        assertEquals("suAbstractorLanding", action.searchByCountType());
        trials = (List) getRequest().getSession().getAttribute("dashboardSearchResults");

        //Then result should see the result filtered appropriately
        assertNotNull(trials);
        assertEquals(1, trials.size());

        //When I set the day I am interested, which is not present in workload
        action.setCountForDay("05/24/2013");
        action.setCountType("expectedCnt");
        assertEquals("suAbstractorLanding", action.searchByCountType());
        trials = (List) getRequest().getSession().getAttribute("dashboardSearchResults");

        //Then result should see the result filtered
        assertNotNull(trials);
        assertEquals(0, trials.size());


        //And when I ask for a different count type that do not exist for the day
        action.setCountForDay("05/24/2013");
        action.setCountType("expectedCnt");
        assertEquals("suAbstractorLanding", action.searchByCountType());
        trials = (List) getRequest().getSession().getAttribute("dashboardSearchResults");

        //Then result should see the result filtered appropriately
        assertNotNull(trials);
        assertEquals(0, trials.size());


    }


    @Test
    public void testSearch() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        loadCriteriaIntoAction(action);
        assertEquals("suAbstractorLanding", action.search());
        final List trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));
    }

    @Test
    public void testTimelineValidation() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        loadCriteriaIntoAction(action);
        action.setSubmittedOnOrAfter("01/02/2012");
        action.setSubmittedOnOrBefore("01/01/2012");
        assertEquals("suAbstractorLanding", action.search());
        final List trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNull(trials);
        assertEquals(
                "Submission timeline dates are inconsistent and will never produce results. "
                        + "Please correct",
                getRequest().getAttribute(Constants.FAILURE_MESSAGE));
    }

    @Test
    public void testView() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        PAServiceUtils serviceUtils = mock(PAServiceUtils.class);
        when(serviceUtils.getStudyIdentifier(any(Ii.class), any(String.class)))
                .thenReturn("NCTID");
        action.setStudyProtocolId(2L);
        action.setServiceUtils(serviceUtils);

        assertEquals("suAbstractorLanding", action.view());
        assertEquals(true, getRequest().getAttribute("toggleDetailsTab"));
        assertEquals("NCTID",
                getRequest().getSession().getAttribute("nctIdentifier"));
        assertTrue(action.getCheckoutCommands().contains("adminCheckIn"));
        assertTrue(action.getCheckoutCommands().contains("scientificCheckOut"));
    }

    private void loadCriteriaIntoAction(DashboardAction action) {
        action.setAdminAbstraction(true);
        action.setAdminQC(true);
        action.setAssignedTo(1L);
        action.setCheckedOutBy(1L);
        action.setCtepDcpCategory("true");
        action.setMilestone("SUBMISSION_ACCEPTED");
        action.setMilestoneType("last");
        action.setNciSponsored("true");
        action.setOnHoldReason(Arrays.asList("Invalid Grant"));
        action.setOnHoldStatus(Arrays.asList("onhold"));
        action.setProcessingComments("comments");
        action.setProcessingPriority(Arrays.asList("1"));
        action.setProcessingStatus(Arrays.asList("Accepted"));
        action.setReadyForTSR(true);
        action.setScientificAbstraction(true);
        action.setScientificQC(true);
        action.setSubmissionType(Arrays.asList("Original"));
        action.setSubmittedBy("ctrp");
        action.setSubmittedOnOrAfter("01/01/2012");
        action.setSubmittedOnOrBefore("01/01/2012");
        action.setSubmittedUnaccepted(true);
        action.setSubmittingOrgId("1");
    }

    /**
     * @throws PAException
     * 
     */
    private DashboardAction getAction() throws PAException {
        DashboardAction action = new DashboardAction();
        action.setServletRequest(getRequest());
        action.prepare();
        action.setProtocolQueryService(getProtocolQueryMock());
        action.setStudyProtocolService(getStudyProtocolService());
        return action;
    }

    private StudyProtocolService getStudyProtocolService() throws PAException {
        final StudyProtocolService mock = mock(StudyProtocolService.class);
        InterventionalStudyProtocolDTO dto = new InterventionalStudyProtocolDTO();
        when(mock.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        return mock;
    }

    /**
     * @return
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    private ProtocolQueryServiceLocal getProtocolQueryMock() throws PAException {
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
        dto1.setStudyProtocolId(1L);
        dto1.getAdminCheckout().setCheckoutBy("suAbstractor");
        dto1.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("05/20/2013"));
        dto1.setActiveHoldDate(PAUtil.dateStringToTimestamp("06/04/2015"));
        dto1.setProprietaryTrial(true);
        setField(dto1, "bizDaysSinceSubmitted", 2);

        StudyProtocolQueryDTO dto2 = new StudyProtocolQueryDTO();
        dto2.setStudyProtocolId(2L);
        dto2.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("05/25/2013"));
        dto2.setOverriddenExpectedAbstractionCompletionDate(PAUtil.dateStringToDate("06/25/2013"));
        dto2.setActiveHoldDate(PAUtil.dateStringToTimestamp("06/01/2015"));
        dto2.setAmendmentDate(PAUtil.dateStringToTimestamp("06/01/2015"));
        setField(dto2, "bizDaysSinceSubmitted", 15);
        
        
        StudyProtocolQueryDTO dto3 = new StudyProtocolQueryDTO();
        dto3.setStudyProtocolId(2L);
        dto3.getLastCreated().setDateLastCreated(PAUtil.dateStringToDate("05/25/2013"));
        dto3.setOverriddenExpectedAbstractionCompletionDate(PAUtil.dateStringToDate("06/25/2013"));
        dto3.setAmendmentDate(PAUtil.dateStringToTimestamp("06/01/2015"));
        setField(dto3, "bizDaysSinceSubmitted", 15);

        when(mock.getWorkload()).thenReturn(
                new ArrayList(Arrays.asList(dto1, dto2,dto3)));
        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2,dto3)));
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2,dto3)));
        when(mock.getTrialSummaryByStudyProtocolId(any(Long.class)))
                .thenReturn(dto1);
        return mock;
    }

    private void setField(StudyProtocolQueryDTO dto, String field, Integer n) {
        final Field f = ReflectionUtils.findField(StudyProtocolQueryDTO.class,
                field);
        ReflectionUtils.makeAccessible(f);
        ReflectionUtils.setField(f, dto, n);
    }
}