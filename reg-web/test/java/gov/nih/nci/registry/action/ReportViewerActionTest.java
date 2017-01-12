/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.service.MockRestClientNCITServer;
import gov.nih.nci.registry.test.util.MockPoServiceLocatorReport;
import gov.nih.nci.registry.util.ReportViewerCriteria;

/**
 * @author vpoluri
 *
 */
public class ReportViewerActionTest extends AbstractRegWebTest {
    private ReportViewerAction action;
    MockRestClientNCITServer mockRestClientNCITServer = new MockRestClientNCITServer();

    @Before
    public void setup() throws Exception {
        CSMUserService.getInstance();
        CSMUserService.setInstance(new MockCSMUserService());
        action = new ReportViewerAction();
        action.prepare();
        setupProperties();
        mockRestClientNCITServer.startServer(20101);
    }

    private void setupProperties() throws PAException {
        action.setLookUpTableService(new MockLookUpTableService());
        MockRegistryUserService regUserSvc = new MockRegistryUserService();

        action.setRegistryUserService(regUserSvc);
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocatorReport());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearch() throws PAException {
        // action = new ReportViewerAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession sess = new MockHttpSession();
        request.setRemoteUser("FirstName");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
        List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("regUsersList");
        assertNotNull(lst);
        assertEquals(2, lst.size());

        List<RegistryUser> rlst = (List<RegistryUser>) sess.getAttribute("reportList");
        assertNotNull(rlst);
        assertEquals(2, rlst.size());
        
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSave() throws PAException {
        // action = new ReportViewerAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession sess = new MockHttpSession();
        request.setRemoteUser("FirstName");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
        List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("regUsersList");
        assertNotNull(lst);
        assertEquals(2, lst.size());

        List<String> rlst = (List<String>) sess.getAttribute("reportList");
        assertNotNull(rlst);
        assertEquals(2, rlst.size());

        try {
            
            RegistryUser member = lst.get(0);
            for (RegistryUser registryUser : lst) {
                if(registryUser.getFirstName().equals("FirstName")){
                    member = registryUser;
                    break;
                }
            }

            Long UPDATED_USERID = member.getId();
            member.setEnableReports(true);
            lst.set(0, member);
            request.setRemoteUser("FirstName");
            request.setupAddParameter("permittedReports", new String[]{"DT4~" + member.getId()});
            sess.setAttribute("regUsersList", lst);
            ServletActionContext.setRequest(request);

            action.save();

            request.setupAddParameter("permittedReports", new String[]{"DT4~" + member.getId(),"DT3~" + member.getId()});
            sess.setAttribute("regUsersList", lst);
            ServletActionContext.setRequest(request);
            
            action.save();
            
            request.setupAddParameter("permittedReports", "");
            sess.setAttribute("regUsersList", lst);
            ServletActionContext.setRequest(request);
            
            action.save();
            
            request.setupAddParameter("permittedReports", new String[]{"DT3~" + member.getId()});
            sess.setAttribute("regUsersList", lst);
            ServletActionContext.setRequest(request);
            
            action.save();
            
            assertEquals("viewResults", action.search());
            List<RegistryUser> updatedLst = (List<RegistryUser>) sess.getAttribute("regUsersList");
            assertNotNull(updatedLst);

            for (RegistryUser registryUser : updatedLst) {
                if (registryUser.getId() == UPDATED_USERID) {
                    assert(registryUser.getEnableReports());
                }
            }

        } catch (PAException e) {

        }
    }

    @Test
    public void testView() throws PAException {
        testSearch();
        assertEquals("viewResults", action.view());
        assertNotNull(action.getRegistryUsers());
        assertEquals(2, action.getRegistryUsers().size());
    }

    @Test
    public void testActionProperties() {
        // action = new ReportViewerAction();
        action.setCriteria(new ReportViewerCriteria());
        assertNotNull(action.getCriteria());
        assertNotNull(action.getAffiliatedOrgAdmins());
        action.setAffiliatedOrgAdmins(null);
        assertNull(action.getAffiliatedOrgAdmins());
        action.setRegistryUsers(new ArrayList<RegistryUser>());
        assertNotNull(action.getCriteria());
    }

    @After
    public void tearDown() throws Exception {
        mockRestClientNCITServer.stopServer();
    }
}
