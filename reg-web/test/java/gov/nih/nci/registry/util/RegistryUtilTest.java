/**
 *
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.registry.action.AbstractRegWebTest;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class RegistryUtilTest extends AbstractRegWebTest {
    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(RegistryUtil.isValidPhoneNumber("phoneNumber"));
        assertFalse(RegistryUtil.isValidPhoneNumber("phone!*Number"));
        assertFalse(RegistryUtil.isValidPhoneNumber(null));
        assertFalse(RegistryUtil.isValidPhoneNumber("min"));
    }

    @Test
    public void testGenerateMail() {
        RegistryUtil.generateMail(Constants.PROCESSED, "firstName", "1", "0", "1", "fileName.doc", "", null, null);
        RegistryUtil.generateMail(Constants.ERROR_PROCESSING, "firstName", "0", "1", "1", "", "error", null, null);
        List<String> createList = new ArrayList<String>();
        createList.add("NCI-2015-0001");
        List<String> amendList = new ArrayList<String>();
        RegistryUtil.generateMail(Constants.PROCESSED, "firstName", "1", "0", "1", "fileName.doc", "", createList, amendList);
    }
    @Test
    public void testRemoveExceptionFromErrMsg() {
        String errMsg = "gov.nih.nci.pa.service.PAException: Duplicates grants are not allowed.";
        assertEquals(RegistryUtil.removeExceptionFromErrMsg(errMsg),"Duplicates grants are not allowed.");
        assertEquals(RegistryUtil.removeExceptionFromErrMsg("Duplicates grants are not allowed."),
             "Duplicates grants are not allowed.");
        assertEquals(RegistryUtil.removeExceptionFromErrMsg("gov.nih.nci.pa.service.PAException: " +
            "gov.nih.nci.pa.service.PAException: Validation Exception Please enter valid value for " +
            "Summary 4 Sponsor Category."),"Please enter valid value for Summary 4 Sponsor Category.");
    }
    
    @Test
    public void testIsDateValid() {
        assertFalse(RegistryUtil.isDateValid("01012011"));
        assertTrue(RegistryUtil.isDateValid("01/01/2011"));
        assertTrue(RegistryUtil.isDateValid("01-01-2011"));
        assertTrue(RegistryUtil.isDateValid("2011-12-30"));
        assertTrue(RegistryUtil.isDateValid("2012/12/31"));
        assertFalse(RegistryUtil.isDateValid("01-01-"));
        assertFalse(RegistryUtil.isDateValid("01"));
        assertFalse(RegistryUtil.isDateValid("abababcd"));
        assertTrue(RegistryUtil.isDateValid("2011/12/31"));
        assertFalse(RegistryUtil.isDateValid("2011/31/12"));
        assertFalse(RegistryUtil.isDateValid("20113112"));
        assertFalse(RegistryUtil.isDateValid("12312012"));
        assertTrue(RegistryUtil.isDateValid("12-31-2012"));
        assertTrue(RegistryUtil.isDateValid("12/31/2012"));
    }
    
    @Test
    public void testSendMail() {
       Map<String, String> warningMap = new HashMap<String, String>();
       warningMap.put("NCI-2015-0001", "CreateWarning");
       warningMap.put("NCI-2015-0002", "AmendWarning");
       RegistryUtil.sendEmail(Constants.PROCESSED, "firstName", "1", "0", "1", "fileName.doc", "", warningMap);
    }
}
