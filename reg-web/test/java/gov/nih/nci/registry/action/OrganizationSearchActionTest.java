/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.*;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.test.util.MockPoServiceLocator;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class OrganizationSearchActionTest extends AbstractRegWebTest {

    /**
     * @throws java.lang.Exception
     */    
    @Before
    public void setUp() {
        List<Country> countryList = new ArrayList<Country>();
        Country usa = new Country();
        usa.setName("United States");
        usa.setAlpha2("US");
        usa.setAlpha3("USA");
        countryList.add(usa);
        ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);

        PaRegistry.getInstance().setServiceLocator(new RegistrationMockServiceLocator());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
       
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#execute()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testExecute() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        assertEquals("success", action.execute());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#showDetailspopup()}
     * .
     * @throws PAException 
     * @throws NullifiedRoleException 
     * @throws NullifiedEntityException 
     * @throws TooManyResultsException 
     */
    @Test
    public final void testShowDetailspopup() throws NullifiedEntityException, NullifiedRoleException, PAException, TooManyResultsException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        action.setOrgID("4648");
        String fwd = action.showDetailspopup();
        assertEquals("details", fwd);
        assertNotNull(action.getOrganization());
        assertEquals("OrgName", action.getOrganization().getName());
        assertEquals("4648", action.getOrganization().getId());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#query()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testQueryEmptyCriteria() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        String fwd = action.query();
        assertEquals("error", fwd);
        assertTrue(ServletActionContext.getRequest()
                .getAttribute("failureMessage").toString()
                .contains("At least one criterion must be provided"));
    }
    
    @Test
    public final void testQuery() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        action.getCriteria().setIdentifier("1");
        String fwd = action.query();
        assertEquals("success", fwd);
        assertEquals(1, action.getResults().size());
        assertEquals("4648", action.getResults().get(0).getId());
      
    }
    

}
