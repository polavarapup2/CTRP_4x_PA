/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class OrganizationGenericContactActionTest  extends AbstractPaActionTest {

	OrganizationGenericContactAction orgGenericContact;
	
	@Before 
	public void setUp() throws PAException {
	  orgGenericContact =  new OrganizationGenericContactAction();	
	  orgGenericContact.prepare();
	  getRequest().setupAddParameter("orgGenericContactIdentifier", "1");
	  getRequest().setupAddParameter("type", "type");
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#lookupByTitle()}.
	 */
	@Test
	public void testLookupByTitle() {
		assertEquals("success",orgGenericContact.lookupByTitle());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#displayTitleList()}.
	 */
	@Test
	public void testDisplayTitleList() {
		orgGenericContact.displayTitleList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testDisplayTitleList2() {
		getRequest().setupAddParameter("orgGenericContactIdentifier", "");
		orgGenericContact.displayTitleList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testDisplayTitleList3() {
		getRequest().setupAddParameter("title", "title");
		String result = orgGenericContact.displayTitleList();
		assertEquals("success", result);
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.OrganizationGenericContactAction#create()}.
	 */
	@Test
	public void testCreate() {
		assertEquals("create_org_contact_response", orgGenericContact.create());
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testCreate2() {
		getRequest().setupAddParameter("orgGenericContactIdentifier", "");
		getRequest().setupAddParameter("title", "title");
		getRequest().setupAddParameter("email", "email");
		getRequest().setupAddParameter("phone", "1234567890");
		assertEquals("create_org_contact_response", orgGenericContact.create());
		assertNotNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testCreate3() {
		getRequest().setupAddParameter("orgGenericContactIdentifier", "1");
		getRequest().setupAddParameter("title", "title");
		getRequest().setupAddParameter("email", "email@test.com");
		getRequest().setupAddParameter("phone", "1234567890");
		PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
		assertEquals("create_org_contact_response", orgGenericContact.create());
		assertNull(getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testSetterGetter() {
		orgGenericContact.setOrgContactList(new ArrayList<PAOrganizationalContactDTO>());
		orgGenericContact.getOrgContactList();
		orgGenericContact.setTitle("title");
		orgGenericContact.getTitle();
		orgGenericContact.setType("type");
		orgGenericContact.getType();
		orgGenericContact.setOrgContactId("orgContactId");
		orgGenericContact.getOrgContactId();
		orgGenericContact.setOrgContactId("1");
	}

}
