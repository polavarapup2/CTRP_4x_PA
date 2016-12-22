/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.domain.Country;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpPersonActionTest extends AbstractPaActionTest {

    PopUpPersonAction popUpAction;

    @Override
    @Before
    public void setUp(){
        popUpAction = new PopUpPersonAction();
        List<Country> countryList = new ArrayList <Country>();
        Country usa = new Country();
        usa.setName("United States");
        usa.setAlpha2("US");
        usa.setAlpha3("USA");
        countryList.add(usa);
        getSession().setAttribute("countrylist",countryList);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpPersonAction#lookupcontactpersons()}.
     */
    @Test
    public void testLookupcontactpersons() throws Exception {
        popUpAction.setEmail("test@test.org");
        popUpAction.setTelephone("132412312");
        assertEquals("contactpersons", popUpAction.lookupcontactpersons());
        assertEquals(1, popUpAction.getCountryList().size());
        assertNull(popUpAction.getPersons());
        assertEquals("test@test.org", getRequest().getSession().getAttribute("emailEntered"));
        assertEquals("132412312", getRequest().getSession().getAttribute("telephoneEntered"));
        popUpAction.lookuppersons();
    }

    @Test
    public void testdisplaycontactPersonsList() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displaycontactPersonsList());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplaycontactPersonsListDisplayTag() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("contactpersons", popUpAction.displaycontactPersonsListDisplayTag());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
    @Test
    public void testdisplayPersonsList() {
    	assertEquals("success", popUpAction.displayPersonsList());
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("success", popUpAction.displayPersonsList());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
        popUpAction.setPoId("1");
        popUpAction.setCtepId("1");
        assertEquals("success", popUpAction.displayPersonsList());
        popUpAction.setPersons(null);
        assertNotNull(popUpAction.getCriteria());
    }
    @Test
    public void testdisplayPersonsListDisplayTag() {
        popUpAction.setFirstName("fname");
        popUpAction.setLastName("lname");
        popUpAction.setCountryName("USA");
        popUpAction.setCityName("dallas");
        popUpAction.setZipCode("75090");
        popUpAction.setStateName("TX");
        assertEquals("persons", popUpAction.displayPersonsListDisplayTag());
        assertNotNull(popUpAction.getPersons());
        assertEquals(0, popUpAction.getPersons().size());
    }
}