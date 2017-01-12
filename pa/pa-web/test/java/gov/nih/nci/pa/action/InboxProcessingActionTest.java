package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

public class InboxProcessingActionTest extends AbstractPaActionTest {
	InboxProcessingAction inboxAction;
	@Before
	public void setUp() throws PAException {
	 inboxAction	= new InboxProcessingAction();
	 MockHttpServletRequest request = getRequest();
     MockHttpSession session = getSession();
     getSession().setAttribute(Constants.IS_ABSTRACTOR, Boolean.TRUE);
     request.setSession(session);
     ServletActionContext.setRequest(getRequest());

	}
    @Test
    public void testExecute() throws PAException {
        assertEquals("success", inboxAction.execute());
        assertEquals(1,inboxAction.getPendingAdminUsers().size());
        getSession().setAttribute(Constants.IS_ABSTRACTOR, null);
        getRequest().setSession(getSession());
        getRequest().setUserInRole(Constants.ABSTRACTOR, true);
        ServletActionContext.setRequest(getRequest());
        assertEquals("criteriaProtected", inboxAction.execute());
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = getSession();
        session.setAttribute(Constants.IS_ABSTRACTOR, null);
        request.setSession(session);
        request.setUserInRole(Constants.REPORT_VIEWER, true);
        ServletActionContext.setRequest(request);
        assertEquals("criteriaReport", inboxAction.view());
    }

    @Test
    public void testShowCriteria(){
        try {
            inboxAction.showCriteria();
            fail();
        } catch (PAException e) {
            assertEquals("User configured improperly.  Use UPT to assign user to a valid group "
                    + "for this application.", e.getMessage());
        }
    }

    @Test
    public void testView() throws PAException {
        inboxAction.setStudyProtocolId(1L);
        assertEquals("view", inboxAction.view());
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(Constants.IS_ABSTRACTOR, null);
        request.setSession(session);
        request.setUserInRole(Constants.ABSTRACTOR, true);
        ServletActionContext.setRequest(request);
        assertEquals("criteriaProtected", inboxAction.view());
        inboxAction.setStudyProtocolId(2L);
        assertEquals("view", inboxAction.view());
        inboxAction.setStudyProtocolId(3L);
        assertEquals("error", inboxAction.view());
        assertNotNull(inboxAction.getActionErrors());

    }

	@Test
	public void testprocessUserRole() {
	    getRequest().setupAddParameter("action", "accept");
        getRequest().setupAddParameter("id","1");
        assertEquals("success",inboxAction.processUserRole());
        assertNotNull(getRequest().getAttribute("successMessage"));

        getRequest().setupAddParameter("action", "reject");
        getRequest().setupAddParameter("id","1");
        assertEquals("success",inboxAction.processUserRole());
        assertNotNull(getRequest().getAttribute("successMessage"));

        getRequest().setupAddParameter("action", "reject");
        getRequest().setupAddParameter("id","1");
        getRequest().setupAddParameter("rejectReason","sometest");
        assertEquals("success",inboxAction.processUserRole());
        assertNotNull(getRequest().getAttribute("successMessage"));
   }
    @Test
    public void testProperties() {
        assertNull(inboxAction.getServletResponse());
        inboxAction.setServletResponse(getResponse());
        assertNotNull(inboxAction.getServletResponse());
        assertNotNull(inboxAction.getCriteria());
        inboxAction.setCriteria(null);
        assertNull(inboxAction.getCriteria());
        assertNull(inboxAction.getStudyProtocolId());
        inboxAction.setStudyProtocolId(1L);
        assertNotNull(inboxAction.getStudyProtocolId());
        assertNull(inboxAction.getPendingAdminUsers());
        inboxAction.setPendingAdminUsers(new ArrayList<RegistryUser>());
        assertNotNull(inboxAction.getPendingAdminUsers());
    }
    @Test
    public void testViewPendingUserAdmin(){
        getRequest().setupAddParameter("id", "");
        assertEquals("success",inboxAction.viewPendingUserAdmin());
        getRequest().setupAddParameter("id", "1");
        assertEquals("viewpendingUserAdmin",inboxAction.viewPendingUserAdmin());
        getRequest().setupAddParameter("id", "4");
        assertEquals("viewpendingUserAdmin",inboxAction.viewPendingUserAdmin());
    }

}
