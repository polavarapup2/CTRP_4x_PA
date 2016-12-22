/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.registry.util.SiteAdministrationCriteria;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;


/**
 * @author kkanchinadam
 *
 */
public class SiteAdministrationActionTest extends AbstractRegWebTest {
    private SiteAdministrationAction action;

    @Before
    public void setup() {
        CSMUserService.getInstance();
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearch() throws PAException {
        action = new SiteAdministrationAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession sess = new MockHttpSession();
        request.setRemoteUser("RegUser");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());
        List<RegistryUser> lst = (List<RegistryUser>) sess.getAttribute("regUsersList");
        assertNotNull(lst);
        assertEquals(3, lst.size());
        try {
            action.save();
        } catch (PAException e) {

        }
    }

    @Test
    public void testView() throws PAException {
        testSearch();
        assertEquals("viewResults", action.view());
        assertNotNull(action.getRegistryUsers());
        assertEquals(3, action.getRegistryUsers().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetUserOrgTypeAndSave() throws PAException {
        testSearch();
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession sess = new MockHttpSession();
        request.setRemoteUser("RegUser");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("viewResults", action.search());

        // promote a member to admin
        request.setupAddParameter("regUserId", "5");
        request.setupAddParameter("isAdmin", "true");
        action.setUserOrgType();
        List<RegistryUser> regUsers = (List<RegistryUser>) sess.getAttribute("regUsersList");
        for (RegistryUser regUser : regUsers) {
            if (regUser.getId() == 3L) {
                assertEquals(UserOrgType.ADMIN, regUser.getAffiliatedOrgUserType());
            }
            if (regUser.getId() == 4L) {
                assertEquals(UserOrgType.MEMBER, regUser.getAffiliatedOrgUserType());
            }
            if (regUser.getId() == 5L) {
                assertEquals(UserOrgType.ADMIN, regUser.getAffiliatedOrgUserType());
            }
        }

    }

    @Test
    public void testActionProperties() {
        action = new SiteAdministrationAction();
        action.setCriteria(new SiteAdministrationCriteria());
        assertNotNull(action.getCriteria());
        assertNotNull(action.getAffiliatedOrgAdmins());
        action.setAffiliatedOrgAdmins(null);
        assertNull(action.getAffiliatedOrgAdmins());
        action.setRegistryUsers(new ArrayList<RegistryUser>());
        assertNotNull(action.getCriteria());
    }

}
