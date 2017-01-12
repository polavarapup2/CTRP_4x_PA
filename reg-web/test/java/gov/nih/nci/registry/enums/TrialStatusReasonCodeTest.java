package gov.nih.nci.registry.enums;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TrialStatusReasonCodeTest {

    @Test
    public void testGetDisplayNames() {
        String[] names = TrialStatusReasonCode.getDisplayNames();
        /* I am really unsure this is corrcect*/
        assertEquals(11, names.length);
        assertTrue(Arrays.asList(names).contains(
                TrialStatusReasonCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode()));
        
    }
    
    @Test
    public void testGetName() {
        assertEquals("TEMPORARILY_CLOSED_TO_ACCRUAL", TrialStatusReasonCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getName());
    }

}
