package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;

/**
 * @author Denis G. Krylov
 *
 */
public class ManageSiteOwnershipActionTest extends AbstractRegWebTest {
    private ManageSiteOwnershipAction action;
    
   
    private ParticipatingSiteServiceLocal participatingSiteServiceLocal;
  

    @Before
    public void before() throws PAException {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));

        participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class))).thenReturn(
                studySiteDTO);
    }

    @Test
    public void testSearch() throws PAException {
        action = new MockSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
    }

    @Test
    public void testView() throws PAException {
        action = new ManageSiteOwnershipAction();
        assertEquals("viewResults", action.view());
    }

    @Test
	public void testSetRegUser() throws PAException {
        action = new MockSiteOwnershipAction();
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
        action = new MockSiteOwnershipAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser("RegUser");
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
        action = new MockSiteOwnershipAction();
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
    
    private class MockSiteOwnershipAction extends ManageSiteOwnershipAction {
        @Override
        protected List<Long> getAllRelatedOrgs(final Long siteId) throws PAException {
            ArrayList<Long> lst = new ArrayList<Long>();
            lst.add(siteId);
            return lst;
        }
    }

}
