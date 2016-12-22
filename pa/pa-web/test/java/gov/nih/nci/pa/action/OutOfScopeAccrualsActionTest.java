package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AccrualUtilityService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.service.util.MockLookUpTableServiceBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * 
 * @author Denis G. Krylov
 * 
 */

public class OutOfScopeAccrualsActionTest extends AbstractPaActionTest {

    OutOfScopeAccrualsAction action;

    Map<Long, AccrualOutOfScopeTrialDTO> store;

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    @Before
    public void setUp() throws PAException {
        action = new OutOfScopeAccrualsAction();
        action.prepare();
        action.setServletRequest(ServletActionContext.getRequest());

        setUpMocks();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setUpMocks() throws PAException {
        action.setLookUpTableService(new MockLookUpTableServiceBean());
        setUpAccrualUtilityMock();
    }

    /**
     * @throws PAException
     */
    private void setUpAccrualUtilityMock() throws PAException {
        final AccrualOutOfScopeTrialDTO dto = new AccrualOutOfScopeTrialDTO();
        dto.setAction("Rejected");
        dto.setCtepID("CTEP01");
        dto.setFailureReason("Not found");
        dto.setId(1L);
        dto.setSubmissionDate(new Date());
        dto.setUserLoginName("ctrpsubstractor");

        store = new TreeMap<Long, AccrualOutOfScopeTrialDTO>();
        store.put(dto.getId(), dto);

        AccrualUtilityService accrualUtilityService = mock(AccrualUtilityService.class);
        when(accrualUtilityService.getAllOutOfScopeTrials()).thenReturn(
                new ArrayList(store.values()));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                AccrualOutOfScopeTrialDTO dto = (AccrualOutOfScopeTrialDTO) invocation
                        .getArguments()[0];
                store.put(dto.getId(), dto);
                return null;
            }
        }).when(accrualUtilityService).update(
                any(AccrualOutOfScopeTrialDTO.class));

        action.setAccrualUtilityService(accrualUtilityService);
    }

    @Test
    public void testExecute() throws PAException {
        String fwd = action.execute();
        assertEquals("success", fwd);
        assertEquals(1, action.getRecords().size());

        AccrualOutOfScopeTrialDTO dto = action.getRecords().get(0);
        assertEquals(new Long(1), dto.getId());
    }

    @Test
    public void testSave() throws PAException {
        MockHttpServletRequest request = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        request.setupAddParameter("ctroAction_1", "Out of Scope");

        String fwd = action.save();
        assertEquals("success", fwd);
        assertEquals("Your changes have been saved",
                request.getAttribute(Constants.SUCCESS_MESSAGE));

        AccrualOutOfScopeTrialDTO dto = store.get(1L);
        assertEquals("Out of Scope", dto.getAction());
    }

    @Test
    public void testGetCtroActions() throws PAException {
        assertEquals(
                Arrays.asList(new String[] { "Rejected", "Out of Scope" }),
                action.getCtroActions());
    }
}
