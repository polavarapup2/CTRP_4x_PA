package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.EnPn;

import org.junit.Test;

public class EnPnConverterTest {

	@Test
	public void testConvertToEnPn() {
		EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
		assertEquals("Testing last name", "lastName",enpn.getPart().get(0).getValue());
		assertEquals("Testing first name", "firstName",enpn.getPart().get(1).getValue());
	}
	
	@Test
    public void testConvertToLastCommaFirst() {
        EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
        String str = EnPnConverter.convertToLastCommaFirstName(enpn);
        assertEquals("lastName, firstName middleName", str);
    }

}
