/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class OrganizationContactActionTest extends AbstractRegWebTest {
     private OrganizationContactAction action;
     @Test
     public void testCountryListProperty() throws Exception {
         action = new OrganizationContactAction();
         assertNotNull(action.getCountryList());
         action.setCountryList(null);
         assertNull(action.getCountryList());
     }
     @Test
     public void testPersonNameProperty(){
         action = new OrganizationContactAction();
         assertNull(action.getPersonName());
         action.setPersonName("PersonName");
         assertNotNull(action.getPersonName());
     }
     @Test
     public void testPersonsProperty(){
         action = new OrganizationContactAction();
         assertNotNull(action.getPersons());
         action.setPersons(null);
         assertNull(action.getPersons());
     }
     @Test
     public void testPrepareEmptySession() throws Exception{
         action = new OrganizationContactAction();
         MockHttpServletRequest request = new MockHttpServletRequest();
         MockHttpSession sess =  new MockHttpSession();
         request.setSession(sess);
         ServletActionContext.setRequest(request);
         action.prepare();
         assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));
     }
     @Test
     public void testPrepareValueInSession() throws Exception{
         action = new OrganizationContactAction();
         MockHttpServletRequest request = new MockHttpServletRequest();
         MockHttpSession sess =  new MockHttpSession();
         List<String> countryList = new ArrayList<String>();
         countryList.add("USA");
         sess.setAttribute("countrylist",countryList);
         request.setSession(sess);
         ServletActionContext.setRequest(request);
         action.prepare();
         assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));
     }
     @Test
     public void testGetOrganizationContactsEmptySession() throws Exception{
         action = new OrganizationContactAction();
         MockHttpServletRequest request = new MockHttpServletRequest();
         request.setupAddParameter("orgContactIdentifier", "undefined");
         ServletActionContext.setRequest(request);
         assertEquals("display_org_contacts",action.getOrganizationContacts());
     }
     @Test
     public void testGetOrganizationContactsSessionNoParam() throws Exception{
         action = new OrganizationContactAction();
         MockHttpServletRequest request = new MockHttpServletRequest();
         ServletActionContext.setRequest(request);
         assertEquals("display_org_contacts",action.getOrganizationContacts());
     }
     @Test
     public void testGetOrganizationContacts() throws Exception{
         action = new OrganizationContactAction();
         MockHttpServletRequest request = new MockHttpServletRequest();
         request.setupAddParameter("orgContactIdentifier", "1");
         ServletActionContext.setRequest(request);
         assertEquals("display_org_contacts",action.getOrganizationContacts());
     }
     @Test
     public void testGetOrganizationContactsException() throws Exception{
         action = new OrganizationContactAction();
         action.setOrgContactIdentifier("2");
         assertEquals("display_org_contacts",action.getOrganizationContacts());
         assertTrue(action.hasActionErrors());
     }
}
