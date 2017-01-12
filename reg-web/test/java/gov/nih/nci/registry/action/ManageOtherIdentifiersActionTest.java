package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Kalpana Guthikonda
 *
 */
public class ManageOtherIdentifiersActionTest extends AbstractRegWebTest{
    private ManageOtherIdentifiersAction action;
    
    @Test
    public void testAddOtherIdentifier() {
        action = new ManageOtherIdentifiersAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("otherIdentifier", "test123");
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_otherIdentifiers", action.addOtherIdentifier());
        List<Ii> sessionList = new ArrayList<Ii>();
        sessionList.add(IiConverter.convertToIi("testing"));
        sess.setAttribute("secondaryIdentifiersList", sessionList);
        ServletActionContext.setRequest(request);
        assertEquals("display_otherIdentifiers", action.addOtherIdentifier());
    }
    
    @Test
    public void testDeleteOtherIdentifier() {
        action = new ManageOtherIdentifiersAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        action.setUuid(1);
        List<Ii> sessionList = new ArrayList<Ii>();
        sessionList.add(IiConverter.convertToIi("testing"));
        sess.setAttribute("secondaryIdentifiersList", sessionList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_otherIdentifiers", action.deleteOtherIdentifier());
    }
    
    @Test
    public void testDeleteOtherIdentifierUpdate() {
        action = new ManageOtherIdentifiersAction();
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        action.setUuid(1);
        List<Ii> sessionList = new ArrayList<Ii>();
        sessionList.add(IiConverter.convertToIi("testing"));
        sess.setAttribute("secondaryIdentifiersList", sessionList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        assertEquals("display_otherIdentifiers_update", action.deleteOtherIdentifierUpdate());
    }
    
    @Test
    public void testShowWaitDialog() {
        action = new ManageOtherIdentifiersAction();
        assertEquals("show_ok_create", action.showWaitDialog());
    }
}
