package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Kalpana Guthikonda
 *
 */
public class ManageTrialOwnershipActionTest extends AbstractRegWebTest {
    private ManageTrialOwnershipAction action;

    @Test
    public void testSearch() throws PAException {
        action = new MockTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());

        // confirm that not proprietary trials have been filtered out
        List<SelectedStudyProtocol> list = action.getStudyProtocols();
        for (SelectedStudyProtocol sp : list) {
            assertFalse(sp.getStudyProtocol().getProprietaryTrialIndicator());
        }
    }

    @Test
    public void testView() throws PAException {
        action = new MockTrialOwnershipAction();
        assertEquals("viewResults", action.view());
    }

    @Test
	public void testSetRegUser() throws PAException {
        action = new MockTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("regUserId", "3");
		request.setupAddParameter("isOwner", "true");
		request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.search();
        request.getSession().setAttribute("regUsersList", action.getRegistryUsers());
        action.setRegUser();
    }



    @Test
    public void testAssignOwnershipException() throws PAException {
        action = new MockTrialOwnershipAction();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.assignOwnership();
        } catch(PAException e) {
            //expected
        }
    }

    @Test
    public void testUnAssignOwnershipException() throws PAException {
        action = new MockTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        request.getSession().setAttribute("regUsersList", new ArrayList<SelectedRegistryUser>());
        request.getSession().setAttribute("studyProtocolsList", new ArrayList<SelectedStudyProtocol>());
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        try {
            action.unassignOwnership();
        } catch(PAException e) {
            //expected
        }
    }
    
    @Test
    public void testUpdateEmailPref() throws PAException {
        action = new MockTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.setTrialId(1l);
        action.setRegUserId(3L);
        try {
            action.updateEmailPref();
        } catch(PAException e) {
            //expected
        }
    }
    
    @Test
    public void testUpdateEmailPrefAll() throws PAException {
        action = new MockTrialOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        action.setSiteName("testSite");
        request.getSession().setAttribute("trialOwnershipInfo", new ArrayList<DisplayTrialOwnershipInformation>());
        try {
            action.updateEmailPref();
        } catch(PAException e) {
            //expected
        }
    }
    
    private class MockTrialOwnershipAction extends ManageTrialOwnershipAction {
        @Override
        protected List<Long> getAllRelatedOrgs(final Long siteId) throws PAException {
            ArrayList<Long> lst = new ArrayList<Long>();
            lst.add(siteId);
            return lst;
        }
    }
}
