/**
 *
 */
package gov.nih.nci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class DateConverterTest {
    private final DateConverter  converter = new DateConverter();
    @Test
    public void testConvertFromString() {
        String[] values = {"2009/12/15"};
        converter.convertFromString(null, values , null);
        String[] invalidValues = {"date"};
        try {
        converter.convertFromString(null, invalidValues, null);
        fail();
        } catch (Exception e) {

        }
        String[] nullValues = null;
        assertNull(converter.convertFromString(null, nullValues , null));
    }
    @Test
    public void testConvertToString() {
        converter.convertToString(null, Calendar.getInstance().getTime());
        assertEquals("",converter.convertToString(null, "abcd"));
    }
}
