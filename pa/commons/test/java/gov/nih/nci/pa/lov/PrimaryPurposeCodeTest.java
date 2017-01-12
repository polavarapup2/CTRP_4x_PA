/**
 * 
 */
package gov.nih.nci.pa.lov;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;

/**
 * @author Denis G. Krylov
 *
 */
public class PrimaryPurposeCodeTest extends AbstractHibernateTestCase {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }
    

    /**
     * Test method for {@link gov.nih.nci.pa.lov.PrimaryPurposeCode#getByCode(java.lang.String)}.
     */
    @Test
    public final void testGetByCode() {
        final PrimaryPurposeCode treatment = PrimaryPurposeCode.getByCode("Treatment");
        assertNotNull(treatment);
        assertEquals("Treatment", treatment.getCode());
        assertEquals("TREATMENT", treatment.getName());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.lov.PrimaryPurposeCode#getDisplayNames()}.
     */
    @Test
    public final void testGetDisplayNames() {
       assertEquals(8, PrimaryPurposeCode.getDisplayNames().length);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.lov.PrimaryPurposeCode#getDisplayNames(gov.nih.nci.pa.enums.StudyTypeCode)}.
     */
    @Test
    public final void testGetDisplayNamesStudyTypeCode() {
        final String[] displayNames = PrimaryPurposeCode.getDisplayNames(StudyTypeCode.NON_INTERVENTIONAL);
        assertEquals(2, displayNames.length);
        assertEquals("Basic Science", displayNames[0]);
        assertEquals("Other", displayNames[1]);
    }

}
