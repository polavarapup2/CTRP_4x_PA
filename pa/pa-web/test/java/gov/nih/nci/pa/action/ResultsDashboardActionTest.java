/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test ResultsDashBoardAction
 * @author gunnikrishnan
 * 
 */
public class ResultsDashboardActionTest extends AbstractPaActionTest {

    @Override
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void testNoResultsDashboardAccess() throws PAException {
        ResultsDashboardAction action = getAction();
        assertEquals(null, action.execute());
    }

    @Test
    public void testResultsDashboardAccess() throws PAException {
        getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        assertEquals("resultAbstractorLanding", action.execute());
    }

    @Test
    public void testSearchNoFilters() throws PAException {
        getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        assertEquals("resultAbstractorLanding", action.search());
        final List trials = action.getResults();
        assertNotNull(trials);
        assertEquals(2, trials.size());
    }
    
    @Test
    public void testSearchWithFilters() throws PAException {
        getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        loadCriteriaIntoAction(action);
        assertEquals("resultAbstractorLanding", action.search());
        final List trials = action.getResults();
        assertNotNull(trials);
        assertEquals(2, trials.size());
    }

    @Test
    public void testSearchNoAccess() throws PAException {
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        assertEquals(null, action.search());
        final List trials = action.getResults();
        assertNull(trials);
    }
    
    @Test
    public void testAjaxChangeDateNoAccess() throws PAException {
        ResultsDashboardAction action = getAction();
        assertEquals(null, action.ajaxChangeDate());
    }
    
    @Test
    public void testAjaxChangeDate() throws PAException {
        getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        action.setDateAttr("pcdCompletionDate");
        action.setDateValue(new Date());
        assertEquals("ajaxResponse", action.ajaxChangeDate());
        assertNotNull(action.getAjaxResponseStream());
    }
    
    @Test
    public void testChartData() throws PAException {
        getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        assertEquals("resultAbstractorLanding", action.search());
        assertEquals(1,action.getNotStartedCnt());
        assertEquals(0,action.getInProcessCnt());
        assertEquals(1,action.getCompletedCnt());
        assertEquals(0,action.getIssuesCnt());
    }
    
    @Test
    public void testDesigneeNamesSorting() throws PAException {
    	getRequest().setUserInRole(Constants.RESULTS_ABSTRACTOR, true);
        ResultsDashboardAction action = getAction();
        List<String> designeeList = new ArrayList<String>();
        designeeList.add("ZDoe - John");
        designeeList.add("Test - User");
        designeeList.add("Doe - John");
        assertEquals("Doe - John, Test - User, ZDoe - John", action.sortResultsDashboardDesigneeNames(designeeList));        
        designeeList.add("ADoe - John");
        assertEquals("ADoe - John, Doe - John, Test - User, ZDoe - John", action.sortResultsDashboardDesigneeNames(designeeList));        
        designeeList.add("ADoe - AJohn");
        assertEquals("ADoe - AJohn, ADoe - John, Doe - John, Test - User, ZDoe - John", action.sortResultsDashboardDesigneeNames(designeeList));
    }
    
    private void loadCriteriaIntoAction(ResultsDashboardAction action) {
        action.setSection801IndicatorYes(true);
        action.setSection801IndicatorNo(true);
        action.setPcdFrom(new Date());
        action.setPcdTo(new Date());
        action.setPcdType("Anticipated");
        action.setTrialIdentifier("AnyTypeId");
    }

    private ResultsDashboardAction getAction() throws PAException {
        ResultsDashboardAction action = new ResultsDashboardAction();
        action.setServletRequest(getRequest());
        action.setServletResponse(getResponse());
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

   
    private ProtocolQueryServiceLocal getProtocolQueryMock() throws PAException {
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
        dto1.setLeadOrganizationName("Lead org");
        dto1.setStudyProtocolId(1L);
        dto1.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        
        StudyProtocolQueryDTO dto2 = new StudyProtocolQueryDTO();
        
        dto2.setStudyProtocolId(2L);
        dto2.setTrialPublishedDate(new Date());
        dto2.setLeadOrganizationName("Lead org");
        dto2.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2)));
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2)));
        when(mock.getTrialSummaryByStudyProtocolId(any(Long.class)))
                .thenReturn(dto1);
        return mock;
    }

}