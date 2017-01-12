/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.noniso.dto.StudyProtocolFlagDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FlaggedTrialService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

/**
 * @author dkrylov
 * 
 */
public class ManageFlaggedTrialsActionTest extends AbstractPaActionTest {

    ManageFlaggedTrialsAction action;
    FlaggedTrialService flaggedTrialService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        action = new ManageFlaggedTrialsAction();
        action.setServletRequest(ServletActionContext.getRequest());
        action.setServletResponse(ServletActionContext.getResponse());

        flaggedTrialService = mock(FlaggedTrialService.class);

        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        when(serviceLocator.getFlaggedTrialService()).thenReturn(
                flaggedTrialService);
        PaRegistry.getInstance().setServiceLocator(serviceLocator);

        ServletActionContext.getRequest().getSession()
                .setAttribute(Constants.IS_SU_ABSTRACTOR, Boolean.TRUE);
        action.prepare();

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.ManageFlaggedTrialsAction#addOrEdit()}.
     * 
     * @throws IOException
     * @throws PAException
     */
    @Test
    public final void testAdd() throws IOException, PAException {
        action.setNciID(" NCI-2014-001 ");
        action.setReason(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES
                .getCode());
        final String comments = RandomStringUtils.random(5000);
        action.setComments(comments);
        action.addOrEdit();

        verify(flaggedTrialService)
                .addFlaggedTrial(
                        eq("NCI-2014-001"),
                        eq(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES),
                        eq(comments.substring(0, 4000)));

    }

    @Test
    public final void testEdit() throws IOException, PAException {
        action.setId(1L);
        action.setReason(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES
                .getCode());
        final String comments = RandomStringUtils.random(5000);
        action.setComments(comments);
        action.addOrEdit();

        verify(flaggedTrialService)
                .updateFlaggedTrial(
                        eq(1L),
                        eq(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES),
                        eq(comments.substring(0, 4000)));

    }

    @Test
    public final void testAddOrEditException() throws IOException, PAException {
        action.setNciID(" NCI-2014-001 ");
        action.setReason(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES
                .getCode());
        final String comments = RandomStringUtils.random(5000);
        action.setComments(comments);

        doThrow(new PAException("test"))
                .when(flaggedTrialService)
                .addFlaggedTrial(
                        eq("NCI-2014-001"),
                        eq(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES),
                        eq(comments.substring(0, 4000)));

        action.addOrEdit();

        MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext
                .getResponse();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST,
                response.getErrorCode());
        assertEquals("test", response.getHeader("msg"));

    }

    @Test
    public final void testPrepare() {
        ServletActionContext.getRequest().getSession()
                .removeAttribute(Constants.IS_SU_ABSTRACTOR);
        try {
            action.prepare();
        } catch (RuntimeException e) {
            MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext
                    .getResponse();
            assertEquals(HttpServletResponse.SC_FORBIDDEN,
                    response.getErrorCode());
            return;
        }
        Assert.fail();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.ManageFlaggedTrialsAction#execute()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testExecute() throws PAException {
        final StudyProtocolFlagDTO dto1 = new StudyProtocolFlagDTO();
        final StudyProtocolFlagDTO dto2 = new StudyProtocolFlagDTO();
        when(flaggedTrialService.getActiveFlaggedTrials()).thenReturn(
                new ArrayList(Arrays.asList(dto1)));
        when(flaggedTrialService.getDeletedFlaggedTrials()).thenReturn(
                new ArrayList(Arrays.asList(dto2)));

        action.execute();

        assertEquals(1, action.getFlaggedTrials().size());
        assertEquals(1, action.getDeletedFlaggedTrials().size());
        assertEquals(dto1, action.getFlaggedTrials().get(0));
        assertEquals(dto2, action.getDeletedFlaggedTrials().get(0));

        MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        req.setupAddParameter("d-234234-e", "1");

        action.execute();
        assertEquals(2, action.getFlaggedTrials().size());
        assertEquals(0, action.getDeletedFlaggedTrials().size());
        assertEquals(dto1, action.getFlaggedTrials().get(0));
        assertEquals(dto2, action.getFlaggedTrials().get(1));

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.ManageFlaggedTrialsAction#successfulAdd()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testSuccessfulAdd() throws PAException {
        action.successfulAdd();
        MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        assertEquals("Flagged trial has been added successfully",
                req.getAttribute(Constants.SUCCESS_MESSAGE));

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.ManageFlaggedTrialsAction#delete()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testDelete() throws PAException {
        action.setObjectsToDelete(new String[] { "1,2" });
        final String comments = RandomStringUtils.random(5000);
        action.setDeleteComments(comments);
        action.delete();

        MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext
                .getRequest();
        assertEquals(Constants.MULTI_DELETE_MESSAGE,
                req.getAttribute(Constants.SUCCESS_MESSAGE));

        verify(flaggedTrialService).delete(eq(1L),
                eq(comments.substring(0, 4000)));
        verify(flaggedTrialService).delete(eq(2L),
                eq(comments.substring(0, 4000)));
    }

}
