/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * @author Vrushali
 *
 */
public class OrganizationGenericContactActionTest extends AbstractRegWebTest {
    private OrganizationGenericContactAction action = new OrganizationGenericContactAction();

    @Test
    public void testOrgContactListProperty() {
        assertNotNull(action.getOrgContactList());
        action.setOrgContactList(null);
        assertNull(action.getOrgContactList());
    }

    @Test
    public void testOrgContactIdProperty() {
        assertNull(action.getOrgGenericContactIdentifier());
        action.setOrgGenericContactIdentifier("orgContactId");
        assertNotNull(action.getOrgGenericContactIdentifier());
    }

    @Test
    public void testTitle() {
        assertNull(action.getTitle());
        action.setTitle("title");
        assertNotNull(action.getTitle());
    }

    @Test
    public void testLookupByTitle() {
        assertEquals("success", action.lookupByTitle());
    }

    @Test
    public void testDisplayTitleList() {
        assertEquals("success", action.displayTitleList());
        assertTrue(action.getActionErrors().contains("Please enter at least one search criteria"));
        //MockHttpServletRequest request = new MockHttpServletRequest();
        action.setOrgGenericContactIdentifier("");
        assertEquals("success", action.displayTitleList());
        assertTrue(action.getActionErrors().contains("Please select a Sponsor."));
        action = new OrganizationGenericContactAction();
        action.setOrgGenericContactIdentifier("1");
        action.setTitle("title");
        assertEquals("success", action.displayTitleList());
    }

    @Test
    public void testCreate() {
        assertEquals("create_org_contact_response", action.ajaxCreate());
        assertTrue(action.getActionErrors().contains("Sponsor is a required field"));
        action = new OrganizationGenericContactAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupAddParameter("orgGenericContactIdentifier", "1");
        request.setupAddParameter("email", "e@mail.com");
        request.setupAddParameter("phone", "phone");
        request.setupAddParameter("title", "title");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_contact_response", action.ajaxCreate());
        request = new MockHttpServletRequest();
        request.setupAddParameter("orgGenericContactIdentifier", "1");
        request.setupAddParameter("email", "e@mail.com");
        request.setupAddParameter("phone", "phone");
        request.setupAddParameter("title", "TestEntityValidationException");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_contact_response", action.ajaxCreate());
        request = new MockHttpServletRequest();
        request.setupAddParameter("orgGenericContactIdentifier", "1");
        request.setupAddParameter("email", "e@mail");
        request.setupAddParameter("phone", "phone");
        request.setupAddParameter("title", "title");
        ServletActionContext.setRequest(request);
        assertEquals("create_org_contact_response", action.ajaxCreate());

    }
}
