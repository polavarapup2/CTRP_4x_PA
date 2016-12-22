package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Hugh Reinhart
 * @since May 28, 2013
 */
@RunWith(Parameterized.class)
public class AddressUtilTest {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String errorMsg;
    private Integer errorCount;

    @Parameters
    public static Collection<? extends Object> data() {
        Object[][] data = new Object[][]{
                {"1 Main St.", "Springfield", "OH", "12345", "USA", null, 0},
                {null, null, null, null, null, "Street address is a required field", 3},
                {null, "Springfield", "OH", "12345", "USA", "Street address is a required field", 1},
                {"1 Main St.", "", "OH", "12345", "USA", "City is a required field", 1},
                {"1 Main St.", "Springfield", null, "1a345", "CAN", "2-letter State/Province Code required for USA/Canada", 1},
                {"1 Main St.", "Springfield", "b", "1a345", "CAN", "2-letter State/Province Code required for USA/Canada", 1},
                {"1 Main St.", "Springfield", "bc", "1a345", "CAN", null, 0},
                {"1 Main St.", "Springfield", "bcc", "1a345", "CAN", "2-letter State/Province Code required for USA/Canada", 1},
                {"1 Main St.", "Springfield", null, "12345", "AUS", "2/3-letter State/Province Code required for Australia", 1},
                {"1 Main St.", "Springfield", "B", "12345", "AUS", "2/3-letter State/Province Code required for Australia", 1},
                {"1 Main St.", "Springfield", "BC", "12345", "AUS", null, 0},
                {"1 Main St.", "Springfield", "BCC", "12345", "AUS", null, 0},
                {"1 Main St.", "Springfield", "BCCC", "12345", "AUS", "2/3-letter State/Province Code required for Australia", 1},
                {"1 Main St.", "Springfield", "OH", null, "USA", "Zip is a required field for US and Canadian addresses.", 1},
                {"1 Main St.", "Springfield", "OH", null, "CAN", "Zip is a required field for US and Canadian addresses.", 1},
                {"1 Main St.", "Springfield", "OH", null, "AUS", "Zip is a required field for Australian addresses.", 1},
                {"1 Main St.", "Springfield", "OH", "12345", null, "Country is a required field", 1}
        };
        return Arrays.asList(data);
    }

    public AddressUtilTest(String streetAddress, String city, String state, String zip, String country,
            String errorMsg, Integer errorCount) {
        this.street = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.errorMsg = errorMsg;
        this.errorCount = errorCount;
    }

    @Test
    public void addressValidationsTest() {
        Collection<String> errors = AddressUtil.addressValidations(street, city, state, zip, country);
        if (errorCount > 0 && !errors.contains(errorMsg)) {
            fail(this + " should have generated: " + errorMsg);
        }
        assertEquals("Wrong total number of errors found for " + this, errorCount, new Integer(errors.size()));
    }

    @Test
    public void fixStateTest() {
        if ("USA".equals(country) || "CAN".equals(country)) {
            String result = AddressUtil.fixState(state, country);
            if (state == null) {
                assertNull(result);
            } else {
                assertTrue("Failed to upper case state code for " + this, result.equals(state.toUpperCase()));
            }
        }
        
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append(street);
        sb.append(", ");
        sb.append(city);
        sb.append(", ");
        sb.append(state);
        sb.append(", ");
        sb.append(zip);
        sb.append(", ");
        sb.append(country);
        sb.append("]");
        return sb.toString();
    }
}
