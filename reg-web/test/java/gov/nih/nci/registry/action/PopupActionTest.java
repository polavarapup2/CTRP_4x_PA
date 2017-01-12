/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author Vrushali
 *
 */
public class PopupActionTest extends AbstractRegWebTest {
    private PopupAction popUpAction;
    @Test
    public void testLookuporgs(){
        popUpAction = new PopupAction();
        assertEquals("orgs",popUpAction.lookuporgs());
        assertNull(popUpAction.getOrgs());
    }
    @Test
    public void testLookuporgsCountryListInSession(){
        popUpAction = new PopupAction();
        List<Country> countryList = new ArrayList<Country>();
        Country country = new Country();
        country.setAlpha2("US");
        country.setAlpha3("USA");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("CA");
        country.setAlpha3("CAN");
        countryList.add(country);
        countryList.add(country);
        HttpSession sess = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        sess.setAttribute("countrylist",countryList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);

        assertEquals("orgs",popUpAction.lookuporgs());
        assertEquals(3, popUpAction.getCountryList().size());
    }

    @Test
    public void testLookuppersons(){
        popUpAction = new PopupAction();
        assertEquals("persons",popUpAction.lookuppersons());
        assertNull(popUpAction.getPersons());
    }
    @Test
    public void testDisplayOrgList(){
        popUpAction = new PopupAction();
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListWithCountryList() throws Exception{
        popUpAction = new PopupAction();
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.prepare();
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListNoRecords(){
        popUpAction = new PopupAction();
        popUpAction.setOrgName("abs");
        popUpAction.setCountryName("USA");
        assertEquals("success", popUpAction.displayOrgList());
    }
    @Test
    public void testDisplayOrgListException(){
        popUpAction = new PopupAction();
        popUpAction.setOrgStAddress("orgStAddress");
        assertEquals("success", popUpAction.displayOrgList());
    }

    @Test
    public void testDisplayOrgListWithAllEmptyParam(){
        popUpAction = new PopupAction();
        assertEquals("success", popUpAction.displayOrgList());
        assertTrue(popUpAction.getActionErrors().contains("Please enter at least one search criteria"));
    }

    @Test
    public void testDisplayOrgListDisplayTagException(){
        popUpAction = new PopupAction();
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
    }

    @Test
    public void testDisplayOrgListDisplayTag(){
        popUpAction = new PopupAction();
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
    }

    @Test
    public void testDisplayOrgListDisplayTagFamily(){
        popUpAction = new PopupAction();
        popUpAction.setFamilyName("FamilyName");
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
    }

    @Test
    public void testCountryListProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getCountryList());
        popUpAction.setCountryList(null);
        assertNull(popUpAction.getCountryList());
    }

    @Test
    public void testPersonsProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getPersons());
        popUpAction.setPersons(null);
        assertNull(popUpAction.getPersons());
    }

    @Test
    public void testPersonDTOProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getPersonDTO());
        popUpAction.setPersonDTO(null);
        assertNull(popUpAction.getPersonDTO());
    }

    @Test
    public void testOrgsProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getOrgs());
        popUpAction.setOrgs(null);
        assertNull(popUpAction.getOrgs());
    }

    @Test
    public void testCreateOrgProperty(){
        popUpAction = new PopupAction();
        assertNotNull(popUpAction.getCreateOrg());
        popUpAction.setCreateOrg(null);
        assertNull(popUpAction.getCreateOrg());
    }

    @Test
    public void testCreateOrgWithEmpty() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountryName("aaa");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Organization is a required field"));
    }

    @Test
    public void testCreateOrg_BadURL() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setUrl("www.google.com");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Please provide a full URL that includes protocol " +
        		"and host, e.g. http://cancer.gov/"));
    }

    @Test
    public void testCreateOrg_BadPhones() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setPhoneNumber("5555555555");
        popUpAction.setFax("555-5555555x");
        popUpAction.setTty("+1-555-5555555");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Valid USA/Canada phone numbers must match ###-###-####x#*, " +
        		"e.g. 555-555-5555 or 555-555-5555x123"));
        assertTrue(popUpAction.getActionErrors().contains("Valid USA/Canada fax numbers must match ###-###-####x#*, " +
                "e.g. 555-555-5555 or 555-555-5555x123"));
        assertTrue(popUpAction.getActionErrors().contains("Valid USA/Canada TTY numbers must match ###-###-####x#*, " +
                "e.g. 555-555-5555 or 555-555-5555x123"));
    }

    private void populateValidUSAOrg() {
        popUpAction.setOrgName("Some Name");
        popUpAction.setOrgStAddress("1 Main St.");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("rock");
        popUpAction.setStateName("MD");
        popUpAction.setZipCode("20663");
        popUpAction.setEmail("org@org.com");
        popUpAction.setPhoneNumber("555-555-5555");
        popUpAction.setFax("555-555-5555");
        popUpAction.setTty("555-555-5555");
        popUpAction.setUrl("http://org@org.com");
    }

    @Test
    public void testCreateOrgWithEmptyStAddress() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setOrgStAddress(null);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Street address is a required field"));
    }

    @Test
    public void testCreateOrgWithEmptyCountry() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setCountryName(null);
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("Country is a required field"));
    }

    @Test
    public void testCreateOrgWithCountryAsUSAWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountryName("USA");
        popUpAction.setStateName("abs");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }

    @Test
    public void testCreateOrgWithCountryAsCANWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountryName("CAN");
        popUpAction.setStateName("abs");
        popUpAction.setEmail("email");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }

    @Test
    public void testCreateOrgWithCountryAsAUSWith4LetterState() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountryName("AUS");
        popUpAction.setStateName("ASDAD");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
    }

    @Test
    public void testCreateOrg_USA() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().isEmpty());
    }

    @Test
    public void testCreateOrg_CAN() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setCountryName("CAN");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().isEmpty());
    }

    @Test
    public void testCreateOrg_AUS2LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setCountryName("AUS");
        popUpAction.setStateName("MD");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().isEmpty());
    }

    @Test
    public void testCreateOrg_AUS3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setCountryName("AUS");
        popUpAction.setStateName("mdd");
        assertEquals("create_org_response", popUpAction.createOrganization());
        assertTrue(popUpAction.getActionErrors().isEmpty());
    }

    @Test
    public void testCreateOrg_UpcaseStateCode() throws PAException{
        popUpAction = new PopupAction();
        populateValidUSAOrg();
        popUpAction.setStateName("md");
        popUpAction.createOrganization();
        assertEquals("MD", popUpAction.getStateName());

        popUpAction.setStateName("md");
        popUpAction.setCountryName("AUS");
        popUpAction.createOrganization();
        assertEquals("md", popUpAction.getStateName());

        popUpAction.setStateName("md");
        popUpAction.setCountryName("CAN");
        popUpAction.createOrganization();
        assertEquals("MD", popUpAction.getStateName());
    }

    @Test
    public void testDisplayPersonsListException() throws PAException{
        popUpAction = new PopupAction();
        assertEquals("success", popUpAction.displayPersonsList());
    }

    @Test
    public void testDisplayPersonsListAllParamNull() throws PAException{
        popUpAction = new PopupAction();
        assertEquals("success", popUpAction.displayPersonsList());
        assertTrue(popUpAction.getActionErrors().contains("Please enter at least one search criteria"));
    }

    @Test
    public void testDisplayPersonsList() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("firstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCountry("USA");
        assertEquals("success", popUpAction.displayPersonsList());
     }

    @Test
    public void testDisplayPersonsListGivenPOID() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setPoId("100");
        assertEquals("success", popUpAction.displayPersonsList());
     }
    
    @Test
    public void testDisplayPersonsListGivenCTEPID() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setPoId("1");
        assertEquals("success", popUpAction.displayPersonsList());
     }

    

    @Test
    public void testDisplayPersonsListDisplayTag() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("firstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCountry("USA");
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }

    @Test
    public void testCreatePersonWithEmpty() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountry("aaa");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("First Name is a required field"));
    }

    @Test
    public void testCreatePersonWithCountryAsUSAWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("firstName");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("USA");
        popUpAction.setState("abs");
        popUpAction.setZip("MAS");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }

    @Test
    public void testCreatePersonWithCountryAsCANWith3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("First");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("email");
        popUpAction.setCountry("CAN");
        popUpAction.setState("abs");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2-letter State/Province Code required for USA/Canada"));
    }

    @Test
    public void testCreatePersonWithCountryAsAUSWith4LetterState() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("AUS");
        popUpAction.setState("ASDAD");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("2/3-letter State/Province Code required for Australia"));
    }

    @Test
    public void testCreatePersonInvalidEmail() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("not valid");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("Email address is invalid"));
    }

    @Test
    public void testCreatePersonInvalidUrl() throws PAException {
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("org@org.com");
        popUpAction.setUrl("org.com");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains(
                "Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/"));
    }

    @Test
    public void testCreatePersonInvalidPhones() throws PAException {
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("org@org.com");
        popUpAction.setCountry("GBR");
        popUpAction.setPhone("[]");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("The phone number is invalid"));

        popUpAction.clearErrors();
        popUpAction.setPhone(null);
        popUpAction.setFax("[]");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("The fax number is invalid"));

        popUpAction.clearErrors();
        popUpAction.setFax(null);
        popUpAction.setTty("[]");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains("The TTY number is invalid"));

        popUpAction.clearErrors();
        popUpAction.setCountry("USA");
        popUpAction.setTty(null);
        popUpAction.setPhone("111-111-1111ext123");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains(
                "Valid USA/Canada phone numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));

        popUpAction.clearErrors();
        popUpAction.setCountry("USA");
        popUpAction.setPhone(null);
        popUpAction.setFax("111-111-1111ext123");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains(
                "Valid USA/Canada fax numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));

        popUpAction.clearErrors();
        popUpAction.setCountry("USA");
        popUpAction.setFax(null);
        popUpAction.setTty("[]");
        assertEquals("create_pers_response", popUpAction.createPerson());
        assertTrue(popUpAction.getActionErrors().contains(
                "Valid USA/Canada TTY numbers must match ###-###-####x#*, e.g. 555-555-5555 or 555-555-5555x123"));
    }

    @Test
    public void testCreatePerson_USA() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setPreFix("preFix");
        popUpAction.setMidName("midName");
        popUpAction.setSuffix("suffix");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("USA");
        popUpAction.setCity("rock");
        popUpAction.setState("MD");
        popUpAction.setZip("20663");
        popUpAction.setEmail("org@org.com");
        popUpAction.setPhone("phone");
        popUpAction.setFax("fax");
        popUpAction.setTty("tty");
        popUpAction.setUrl("http://org@org.com");
        assertEquals("create_pers_response", popUpAction.createPerson());
    }

    @Test
    public void testCreatePer_CAN() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("CAN");
        popUpAction.setCity("rock");
        popUpAction.setState("MD");
        popUpAction.setZip("20663");
        popUpAction.setEmail("org@org.com");
        assertEquals("create_pers_response", popUpAction.createPerson());
    }

    @Test
    public void testCreatePerson_AUS2LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("AUS");
        popUpAction.setCity("rock");
        popUpAction.setState("MD");
        popUpAction.setZip("20663");
        popUpAction.setEmail("org@org.com");
        assertEquals("create_pers_response", popUpAction.createPerson());
    }

    @Test
    public void testCreatePerson_AUS3LetterStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("FirstName");
        popUpAction.setLastName("lastName");
        popUpAction.setStreetAddr("streetAddr");
        popUpAction.setCountry("AUS");
        popUpAction.setCity("rock");
        popUpAction.setState("MDA");
        popUpAction.setZip("20663");
        popUpAction.setEmail("org@org.com");
        assertEquals("create_pers_response", popUpAction.createPerson());
    }

    @Test
    public void testCreatePerson_UpcaseStateCode() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setCountry("USA");
        popUpAction.setState("md");
        popUpAction.createPerson();
        assertEquals("MD", popUpAction.getState());

        popUpAction.setCountry("AUS");
        popUpAction.setState("md");
        popUpAction.createPerson();
        assertEquals("md", popUpAction.getState());

        popUpAction.setCountry("CAN");
        popUpAction.setState("md");
        popUpAction.createPerson();
        assertEquals("MD", popUpAction.getState());
    }

    @Test
    public void testDisplayPersonsListDisplayTagWithCtepId() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("firstName");
        popUpAction.setLastName("lastName");
        popUpAction.setCountry("USA");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCtepId("1");
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }

    @Test
    public void testDisplayOrgListDisplayTagWithCtepId() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setOrgName("OrgName");
        popUpAction.setCountryName("USA");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCtepId("1");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
     }

    @Test
    public void testDisplayPersonsListDisplayTagWithCtepIdNoResult() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setFirstName("firstName");
        popUpAction.setLastName("lastName");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCountry("USA");
        popUpAction.setCtepId("2");
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
     }

    @Test
    public void testDisplayOrgListDisplayTagWithCtepIdNoResult() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setOrgName("OrgName");
        popUpAction.setEmail("em@mail.com");
        popUpAction.setCountryName("USA");
        popUpAction.setCtepId("2");
        assertEquals("orgs", popUpAction.displayOrgListDisplayTag());
     }

    @Test
    public void testDisplayOrgList2() throws PAException{
        popUpAction = new PopupAction();
        popUpAction.setPoId("4648");
        assertEquals("success", popUpAction.displayOrgList());
        assertEquals(1, popUpAction.getPaOrgs().size());
     }

    @Test
    public void testPrepare() throws Exception{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession sess =  new MockHttpSession();
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        popUpAction.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));

    }
    @Test
    public void testPrepareSessionWithValue() throws Exception{
        popUpAction = new PopupAction();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession sess =  new MockHttpSession();
        List<String> countryList = new ArrayList<String>();
        countryList.add("USA");
        sess.setAttribute("countrylist",countryList);
        request.setSession(sess);
        ServletActionContext.setRequest(request);
        popUpAction.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("countrylist"));

    }
    @Test
    public void testDisplayPasswordReset() {
        assertEquals("displayPasswordReset", new PopupAction().displayPasswordReset());
    }
    @Test
    public void testAddSummary4OrgPopup() throws Exception {
    	popUpAction = new PopupAction();
    	assertEquals("summaryFourOrg", popUpAction.addSummaryFourOrg());
    	MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession sess =  new MockHttpSession();
        request.setSession(sess);
    	request.setupAddParameter("orgId", "1");
    	assertEquals("summaryFourOrg", popUpAction.addSummaryFourOrg());
    	SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId("1");
        summarySp.setOrgId("1");
        summarySp.setOrgName("orgName");
    	BaseTrialDTO trialDTO = new BaseTrialDTO();
        trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);        
    	sess.setAttribute("trialDTO", trialDTO);
    	assertEquals("summaryFourOrg", popUpAction.addSummaryFourOrg());
    	request.setupAddParameter("uuid", "1");
    	assertEquals("summaryFourOrg", popUpAction.deleteSummaryFourOrg());
    }
}
