/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mockrunner.mock.web.MockHttpSession;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.MockCSMUserService;

import gov.nih.nci.pa.util.PaRegistry;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * Test Class.
 */
public class DisclaimerActionTest extends AbstractRegWebTest {

    private DisclaimerAction action;

    @Before
    public void initAction() {
        action = new DisclaimerAction();
        CSMUserService.getInstance();
        CSMUserService.setInstance(new MockCSMUserService());
        
        MockHttpServletRequest request  = (MockHttpServletRequest) ServletActionContext.getRequest();
        request.setRemoteUser("firstName");
    }

    @Test
    public void acceptTest() throws PAException{
        assertEquals("redirect_to", action.accept());
        assertTrue((Boolean)ServletActionContext.getRequest().getSession().
                getAttribute("disclaimerAccepted"));
     }
    @Test
    public void acceptWhileAccountMissingTest() throws PAException{

        //given that the user is not present in registry
        MockHttpServletRequest request  = (MockHttpServletRequest) ServletActionContext.getRequest();
        request.setRemoteUser("aNonRegUser");
        MockHttpSession session = (MockHttpSession) request.getSession();

        //with valid http session
        assertTrue(session.isValid());

        //accept then takes me to missing account and invlidates the session
        assertEquals("missing_account", action.accept());
        assertFalse(session.isValid());

     }
}
