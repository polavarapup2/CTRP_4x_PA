package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PaOrganizationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

public class PopUpOrgActionTest extends AbstractPaActionTest {

    PopUpOrgAction popUpAction;

    @Override
    @Before
    public void setUp() {
        popUpAction = new PopUpOrgAction();
        List<Country> countryList = new ArrayList<Country>();
        Country usa = new Country();
        usa.setName("United States");
        usa.setAlpha2("US");
        usa.setAlpha3("USA");
        countryList.add(usa);
        getSession().setAttribute("countrylist", countryList);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpPersonAction#lookuporgs()}.
     */
    @Test
    public void testLookuporgs() {
        getSession().removeAttribute("countrylist");
        assertEquals("orgs", popUpAction.lookuporgs());        
        assertEquals(2, popUpAction.getCountryList().size());
        assertEquals("Zanzibar", popUpAction.getCountryNameUsingCode("ZZZ"));
        assertEquals("Cayman Islands", popUpAction.getCountryNameUsingCode("CAM"));
        assertNull(popUpAction.getCountryNameUsingCode("ABC"));
        assertTrue(CollectionUtils.isEmpty(popUpAction.getOrgs()));
    }
    
    @Test
	public void testDisplayLookUpListDisplayTag() {
		 assertEquals("success",popUpAction.displayOrgList());
		 assertEquals("Please enter at least one search criteria", getRequest().getAttribute("failureMessage"));
	}

    @Test
    public void testdisplayOrgList() {
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displayOrgList());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(1, popUpAction.getOrgs().size());
    }

    @Test
    public void testdisplayOrgListByCtepId() {
        popUpAction.setCtepId("CTEP ID");
        assertEquals("success", popUpAction.displayOrgList());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(1, popUpAction.getOrgs().size());
    }

    @Test
    public void testdisplayOrgListDisplayTag() {
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
        assertNotNull(popUpAction.getOrgs());
        assertEquals(1, popUpAction.getOrgs().size());
    }

    @Test
    public void testcreateOrganizationRequiredFieldValidation() {
        assertEquals("create_org_response", popUpAction.createOrganization());
        Collection<String> errors = popUpAction.getActionErrors();
        assertTrue(errors.contains("Organization is a required field"));
        assertTrue(errors.contains("Street address is a required field"));
        assertTrue(errors.contains("Country is a required field"));
        assertTrue(errors.contains("City is a required field"));
        assertFalse(errors.contains("Email address is invalid"));

        popUpAction.setOrgName("OrgName");
        popUpAction.setOrgStAddress("1 Main St.");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setEmail("bad email");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertFalse(errors.contains("Organization is a required field"));
        assertFalse(errors.contains("Street address is a required field"));
        assertFalse(errors.contains("Country is a required field"));
        assertFalse(errors.contains("City is a required field"));
        assertFalse(errors.contains("Zip is a required field"));
        assertFalse(errors.contains("Email is a required field"));
        assertTrue(errors.contains("Email address is invalid"));

        popUpAction.setEmail("xxx@xxx.xxx");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertFalse(errors.contains("Email address is invalid"));
    }

    @Test
    public void testcreateOrganizationUrlValidation() {
        assertEquals("create_org_response", popUpAction.createOrganization());
        Collection<String> errors = popUpAction.getActionErrors();
        assertFalse(errors
                .contains("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/"));

        popUpAction.clearErrorsAndMessages();
        popUpAction.setUrl("http://www.google.com");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertFalse(errors
                .contains("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/"));

        popUpAction.clearErrorsAndMessages();
        popUpAction.setUrl("www.google.com");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertTrue(errors
                .contains("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/"));
    }

    @Test
    public void testcreateOrganizationCountrySpecificValidation() {
        popUpAction.setCountryName("USA");
        popUpAction.setStateName("Maryland");
        popUpAction.setTelephone("123");
        popUpAction.setTty("123");
        popUpAction.setFax("123");
        assertEquals("create_org_response", popUpAction.createOrganization());
        Collection<String> errors = popUpAction.getActionErrors();
        assertTrue(errors.contains("2-letter State/Province Code required for USA/Canada"));
        assertTrue(errors
                .contains("Valid USA/Canada phone numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));
        assertTrue(errors
                .contains("Valid USA/Canada fax numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));
        assertTrue(errors
                .contains("Valid USA/Canada TTY numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));

        popUpAction.setStateName("md");
        popUpAction.setTelephone("123-456-7890");
        popUpAction.setTty("123-456-7890");
        popUpAction.setFax("123-456-7890");
        popUpAction.clearErrors();
        popUpAction.getTty();
        popUpAction.getFax();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertFalse(errors.contains("2-letter State/Province Code required for USA/Canada"));
        assertEquals("MD", popUpAction.getStateName());
        assertFalse(errors
                .contains("Valid USA/Canada phone numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));
        assertFalse(errors
                .contains("Valid USA/Canada fax numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));
        assertFalse(errors
                .contains("Valid USA/Canada TTY numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));

        popUpAction.setCountryName("AUS");
        popUpAction.setStateName(null);
        popUpAction.setFamilyName("familyName");
        popUpAction.setOrgs(new ArrayList<PaOrganizationDTO>());
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertTrue(errors.contains("2/3-letter State/Province Code required for Australia"));

        popUpAction.setStateName("NWT");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        errors = popUpAction.getActionErrors();
        assertFalse(errors.contains("2/3-letter State/Province Code required for Australia"));
    }

    @Test
    public void testCreateOrganization() {
        popUpAction.setOrgName("OrgName");
        popUpAction.setOrgStAddress("1 Main St.");
        popUpAction.setCountryName("MEX");
        popUpAction.setCityName("TJ");
        popUpAction.setZipCode("1134445");
        popUpAction.setEmail("yyy@yyy.yyy");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertFalse(popUpAction.hasActionErrors());

        popUpAction.setStateName("Baja California");
        popUpAction.setTelephone("123-456-7890");
        popUpAction.setTty("123-456-7890");
        popUpAction.setFax("123-456-7890");
        popUpAction.setUrl("http://www.google.com");
        popUpAction.clearErrors();
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertFalse(popUpAction.hasActionErrors());
    }
}
