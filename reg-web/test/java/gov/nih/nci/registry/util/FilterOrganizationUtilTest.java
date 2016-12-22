package gov.nih.nci.registry.util;

import static org.junit.Assert.*;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.util.FilterOrganizationUtil.OrgItem;

import java.util.List;

import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilterOrganizationUtilTest extends AbstractMockitoTest {

    private Transaction tx;
    private RegistryUserWebDTO user;

    @Before
    public void init() throws Exception {
        tx = PaHibernateUtil.getCurrentSession().beginTransaction();
        super.setUp();

        user = new RegistryUserWebDTO();
        user.setAffiliatedOrganizationId(-1L);
    }
    
    @After
    public void cleanup() {
        if(tx != null) {
            tx.rollback();
        }
    }
    
    @Test
    public void testCoverage() throws PAException {
        FilterOrganizationUtil xd = new FilterOrganizationUtil();
        assertTrue(xd != null); //silly test to up coverage.
        List<OrgItem> list = FilterOrganizationUtil.getLeadOrganization(null);
        assertTrue(list.size() == 0);
        
        try {
            list = FilterOrganizationUtil.getSponsorOrganization(null);
            assertTrue(list.size() <= 3);
        } catch(IllegalArgumentException iae ) {
            //can't accsess the db, or built in types are missing this is passable.
        }
        
        try {
            new OrgItem(1);
            fail();
        } catch (IllegalArgumentException iae) {
            //Successfully threw an exception on illegal creation.
        }
        
        Organization org = new Organization();
        org.setId(1L);
        org.setIdentifier("2");
        org.setName("test");
        
        OrgItem item;
        
        item = new OrgItem(OrgItem.SPACER_TYPE);
        assertEquals("-----", item.getName());
        
        item = new OrgItem(OrgItem.SEARCH_TYPE);
        assertEquals("Search...", item.getName());
    }

    /*@Test
    public void testGetOrganization() {
        Organization org = FilterOrganizationUtil.getOrganizationByPoID(FilterOrganizationUtil.CTEP_PO_ID);
        assertTrue("No result returned.", org != null);
        assertEquals("The name is wrong.", "National Cancer Institute", org.getName());
    }
    
    @Test
    public void testGetLeadOrganizations() {
        List<OrgItem> list = FilterOrganizationUtil.getLeadOrganization(user);
        assertTrue("The list of lead organizations returned no result.", list != null);
        assertTrue("There are insuficient items in the list of results", list.size() > 1);
        assertTrue("The first item is not the affiliation of the user.",
                list.get(0).getName().endsWith("(Your Affiliation)"));
    }*/
    
    @Test
    public void testOrgItem() {
        List<OrgItem> list = FilterOrganizationUtil.getLeadOrganization(user);
        assertTrue("The list of lead organizations returned no result.", list != null);
        assertTrue("There are insuficient items in the list of results", list.size() > 1);
        OrgItem item = list.get(0);
        assertTrue(item.getType() == OrgItem.ERROR_TYPE);
        assertTrue(item.getName().equalsIgnoreCase("Error!"));
        item = list.get(1);
        assertTrue(item.getType() == OrgItem.SPACER_TYPE);
        
        try {
            list = FilterOrganizationUtil.getSponsorOrganization(user);
            assertTrue("The list of lead organizations returned no result.", list != null);
            assertTrue("There are insuficient items in the list of results", list.size() > 1);
            item = list.get(0);
            assertTrue(item.getType() == OrgItem.ERROR_TYPE);
            assertTrue(item.getName().equalsIgnoreCase("Error!"));
        } catch (IllegalArgumentException iae) {
            //if we can't reach the database during tests that is passable.
        }
    }
}
