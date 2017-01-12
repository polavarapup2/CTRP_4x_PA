/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class LogoutActionTest extends AbstractRegWebTest{
    private Logout logoutAction;
    @Test
    public void testLogOut(){
        logoutAction = new Logout();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("success",logoutAction.logout());
    }
    @Test
    public void testLogOutSessionNull(){
        logoutAction = new Logout();
        //HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        //request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("success",logoutAction.logout());
    }
}
