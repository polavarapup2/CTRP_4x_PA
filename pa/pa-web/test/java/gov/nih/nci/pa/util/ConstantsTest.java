/**
 *
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class ConstantsTest {

    @Test
    public void testContants() {
        assertEquals("User Name is required field", Constants.USERNAME_REQ_ERROR);
        assertEquals("trialSummary", Constants.TRIAL_SUMMARY);
        assertEquals("studyProtocolIi", Constants.STUDY_PROTOCOL_II);
        assertEquals("successMessage", Constants.SUCCESS_MESSAGE);
        assertEquals("Password is required field", Constants.PASSWORD_REQ_ERROR);
        assertEquals("failureMessage", Constants.FAILURE_MESSAGE);
        assertEquals("Record Deleted", Constants.DELETE_MESSAGE);
        assertEquals("fundingMechanism", Constants.FUNDING_MECHANISM);
        assertEquals("nihInstitute", Constants.NIH_INSTITUTE);
    }

}
