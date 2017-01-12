package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Batch(number = 1)
public class DashboardMenuTest extends AbstractPaSeleniumTest {
     
    @SuppressWarnings("deprecation")
    public void testMenu() {
        loginAsResultsAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertTrue(selenium.isElementPresent("link=Results Reporting"));
        assertFalse(selenium.isElementPresent("link=Abstractor"));
        
        logoutUser();
        loginAsSuperAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Super Abstractor"));
        
        logoutUser();
        loginAsAdminAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Admin Abstractor"));
        
        logoutUser();
        loginAsScientificAbstractor();
        assertTrue(selenium.isElementPresent("link=Dashboards"));
        assertFalse(selenium.isElementPresent("link=Results Reporting"));
        assertTrue(selenium.isElementPresent("link=Scientific Abstractor"));
    }
    
    
}
