package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CsmHelperTest {

    private static final String USERNAME = "myUser";

    @Test
    public void testConst() {
        CsmHelper helper = new CsmHelper("myUser");
        assertEquals(USERNAME, helper.getUsername());

        try {
            helper.getFirstName();
        } catch (Throwable t) {

        }
        try {
            helper.getLastName();
        } catch (Throwable t) {

        }
    }

}
