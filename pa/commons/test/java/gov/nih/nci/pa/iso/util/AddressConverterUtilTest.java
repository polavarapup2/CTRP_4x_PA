/**
 *
 */
package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;

import java.util.Map;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class AddressConverterUtilTest {

    @Test
    public void testConvertAdToAddressString() {
        String expectedAddressString = "101 Renner rd, Richardson, TX, 75081 USA";
        Ad address = AddressConverterUtil
            .create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
        assertEquals("Create and convert to address test pass", expectedAddressString,
                     AddressConverterUtil.convertToAddress(address));
    }

    @Test
    public void testConvertNullAdToAddressString() {
        assertNull(AddressConverterUtil.convertToAddress(null));
    }

    @Test
    public void testConvertEmptyAdToAddressString() {
        Ad emptyAddress = new Ad();
        assertNull(AddressConverterUtil.convertToAddress(emptyAddress));
    }

    @Test
    public void testConvertAdToMap() {
        Ad address = AddressConverterUtil
            .create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
        Map<String, String> myMap = AddressConverterUtil.convertToAddressBo(address);
        assertEquals(myMap.get(AdxpAl.class.getName()), "101 Renner rd");
        assertEquals(myMap.get(AdxpCty.class.getName()), "Richardson");
        assertEquals(myMap.get(AdxpSta.class.getName()), "TX");
        assertEquals(myMap.get(AdxpZip.class.getName()), "75081");
        assertEquals(myMap.get(AdxpCnt.class.getName()), "USA");
    }

    @Test
    public void testConvertNullAdToMap() {
        assertNull(AddressConverterUtil.convertToAddressBo(null));
    }

    @Test
    public void testConvertEmptyAdToMap() {
        Ad emptyAddress = new Ad();
        assertNull(AddressConverterUtil.convertToAddressBo(emptyAddress));
    }
}
