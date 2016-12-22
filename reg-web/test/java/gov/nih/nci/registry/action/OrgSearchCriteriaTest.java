/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class OrgSearchCriteriaTest {
    private OrgSearchCriteria action;
    @Test
    public void testOrgNameProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgName());
        action.setOrgName("OrgName");
        assertNotNull(action.getOrgName());
    }
    @Test
    public void testNciOrgNumberProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getNciOrgNumber());
        action.setNciOrgNumber("NciOrgNumber");
        assertNotNull(action.getNciOrgNumber());
    }
    @Test
    public void testOrgCountryProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgCountry());
        action.setOrgCountry("OrgCountry");
        assertNotNull(action.getOrgCountry());
    }
    @Test
    public void testOrgCityProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgCity());
        action.setOrgCity("OrgCity");
        assertNotNull(action.getOrgCity());
    }
    @Test
    public void testOrgZipProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgZip());
        action.setOrgZip("OrgZip");
        assertNotNull(action.getOrgZip());
    }
    @Test
    public void testOrgEmailProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgEmail());
        action.setOrgEmail("OrgEmail");
        assertNotNull(action.getOrgEmail());
    }
    @Test
    public void testOrgTTYProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgTTY());
        action.setOrgTTY("OrgTTY");
        assertNotNull(action.getOrgTTY());
    }
    @Test
    public void testOrgFaxProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgFax());
        action.setOrgFax("OrgFax");
        assertNotNull(action.getOrgFax());
    }
    @Test
    public void testOrgPhoneProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgPhone());
        action.setOrgPhone("OrgPhone");
        assertNotNull(action.getOrgPhone());
    }
    @Test
    public void testOrgURLProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgURL());
        action.setOrgURL("OrgURL");
        assertNotNull(action.getOrgURL());
    }
    @Test
    public void testOrgStateProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgState());
        action.setOrgState("OrgState");
        assertNotNull(action.getOrgState());
    }
    @Test
    public void testOrgStreetAddressProperty(){
        action = new OrgSearchCriteria();
        assertNull(action.getOrgStreetAddress());
        action.setOrgStreetAddress("OrgStreetAddress");
        assertNotNull(action.getOrgStreetAddress());
    }
}
