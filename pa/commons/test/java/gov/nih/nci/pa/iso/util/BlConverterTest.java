/**
 * 
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.util.CommonsConstant;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class BlConverterTest {

    /**
     * Test method for {@link gov.nih.nci.pa.iso.util.BlConverter#convertToBl(java.lang.Boolean)}.
     */
    @Test
    public void testConvertToBl() {
        Boolean bool = true;
        Bl bl = BlConverter.convertToBl(bool);
        assertEquals(BlConverter.convertToBoolean(bl), bool);
        assertEquals(BlConverter.convertToBool(bl), bool.booleanValue());
        assertEquals(BlConverter.convertToString(bl), bool.toString());
    }

    /**
     * Test the convertBlToYesNoString with a null input.
     */
    @Test
    public void testConvertBlToYesNoStringNull() {
        testConvertBlToYesNoString(null, null);
    }

    /**
     * Test the convertBlToYesNoString with a null value input.
     */
    @Test
    public void testConvertBlToYesNoStringNullValue() {
        Bl bl = new Bl();
        testConvertBlToYesNoString(bl, null);
    }

    /**
     * Test the convertBlToYesNoString with a nullified value input.
     */
    @Test
    public void testConvertBlToYesNoStringNullifiedValue() {
        Bl bl = new Bl();
        bl.setNullFlavor(NullFlavor.NI);
        testConvertBlToYesNoString(bl, null);
    }

    /**
     * Test the convertBlToYesNoString with a nullified value input.
     */
    @Test
    public void testConvertBlToYesNoStringTrue() {
        Bl bl = new Bl();
        bl.setValue(true);
        testConvertBlToYesNoString(bl, CommonsConstant.YES);
    }

    /**
     * Test the convertBlToYesNoString with a nullified value input.
     */
    @Test
    public void testConvertBlToYesNoStringFalse() {
        Bl bl = new Bl();
        bl.setValue(false);
        testConvertBlToYesNoString(bl, CommonsConstant.NO);
    }

    /**
     * Test the convertBlToYesNoString with the given input and output.
     * @param input The input
     * @param expectedResult The expected result
     */
    private void testConvertBlToYesNoString(Bl input, String expectedResult) {
        String result = BlConverter.convertBlToYesNoString(input);
        assertEquals("Wrong result returned from convertBlToYesNoString", expectedResult, result);
    }

    /**
     * Test the convertYesNoStringToBl with a YES value
     */
    @Test
    public void testConvertYesNoStringToBlYes() {
        testConvertYesNoStringToBl(CommonsConstant.YES, null, true);
    }

    /**
     * Test the convertYesNoStringToBl with a No value
     */
    @Test
    public void testConvertYesNoStringToBlNo() {
        testConvertYesNoStringToBl(CommonsConstant.NO, null, false);
    }

    /**
     * Test the convertYesNoStringToBl with a Null value
     */
    @Test
    public void testConvertYesNoStringToBlNull() {
        testConvertYesNoStringToBl(null, NullFlavor.NI, null);
    }

    /**
     * Test the convertYesNoStringToBl with an invalid value
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertYesNoStringToBlInvalid() {
        BlConverter.convertYesNoStringToBl("invalid");
    }

    /**
     * Test the convertYesNoStringToBl method with the given input and output.
     * @param input The input string
     * @param expectedNullFlavor The expected nullFlavor of the Bl
     * @param expectedValue The expected value of the Bl
     */
    private void testConvertYesNoStringToBl(String input, NullFlavor expectedNullFlavor, Boolean expectedValue) {
        Bl result = BlConverter.convertYesNoStringToBl(input);
        assertNotNull("No result returned", result);
        assertEquals("Wrong null flavor", expectedNullFlavor, result.getNullFlavor());
        assertEquals("Wrong value", expectedValue, result.getValue());
    }
}
