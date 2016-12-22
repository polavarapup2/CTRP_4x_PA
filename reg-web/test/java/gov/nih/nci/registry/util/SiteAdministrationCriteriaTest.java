package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SiteAdministrationCriteriaTest {

    @Test
    public void testSiteAdministrationCriteria() {
        SiteAdministrationCriteria sac = new SiteAdministrationCriteria();
        sac.setEmailAddress("test@example.com");
        sac.setFirstName("John");
        sac.setLastName("Doe");
        assertEquals("test@example.com", sac.getEmailAddress());
        assertEquals("John", sac.getFirstName());
        assertEquals("Doe", sac.getLastName());
    }
}
