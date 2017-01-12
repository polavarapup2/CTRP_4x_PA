package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;

import org.junit.Test;

public class EnOnConverterTest {

    /**
     * Exercises convertToOrgIi() method.
     */
    @Test 
    public void testConvertToOrgIi() {
        Ii iso = EnOnConverter.convertToOrgIi(null);
        assertNotNull(iso);
        assertEquals(iso.getNullFlavor(), NullFlavor.NI);
        iso = EnOnConverter.convertToOrgIi(1L);
        assertNotNull(iso);
        assertEquals(iso.getExtension(), "1");
        assertEquals(iso.getDisplayable(), true);
        assertEquals(iso.getScope(), IdentifierScope.OBJ);
        assertEquals(iso.getIdentifierName(), "NCI organization entity identifier");
        assertEquals(iso.getRoot(), "UID.for.nci.entity.organization");
        assertEquals(iso.getReliability(), IdentifierReliability.ISS);
    }
    
    /**
     * Exercises convertToEnOn() method.
     */
	@Test
	public void testConvertToEnOn() {		
		EnOn iso = EnOnConverter.convertToEnOn("");
		assertNotNull(iso);
        assertEquals(iso.getNullFlavor(), NullFlavor.NI);
        iso = EnOnConverter.convertToEnOn("2");
        assertNotNull(iso);
        assertNotNull(iso.getPart());
        Enxp e = iso.getPart().get(0);
        assertNotNull(e);
        assertEquals(e.getValue(), "2");
	}
	
	/**
	 * Exercises convertEnOnToString() method.
	 */
	@Test
	public void testConvertEnOnToString() {
	    EnOn iso = new EnOn();	    
	    String value = EnOnConverter.convertEnOnToString(iso);
	    assertNull(value);
	    Enxp e = new Enxp(null);
	    iso.getPart().add(e);
	    value = EnOnConverter.convertEnOnToString(iso);
	    assertNull(value);	    
        e.setValue("2");
        value = EnOnConverter.convertEnOnToString(iso);
        assertNotNull(value);
        assertEquals(value, "2");
	}
}
