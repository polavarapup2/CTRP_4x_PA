package gov.nih.nci.pa.dto;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccrualSubmissionAccessWebDTOTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testHashCode() {
        AccrualSubmissionAccessWebDTO dto1 = new AccrualSubmissionAccessWebDTO();
        AccrualSubmissionAccessWebDTO dto2 = new AccrualSubmissionAccessWebDTO();
        dto1.setTrialId(100L);
        dto2.setTrialId(100L);
        dto1.setTrialNciId("NCIID");
        dto2.setTrialNciId("NCIID");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public final void testEqualsObject() {
        AccrualSubmissionAccessWebDTO dto1 = new AccrualSubmissionAccessWebDTO();
        AccrualSubmissionAccessWebDTO dto2 = new AccrualSubmissionAccessWebDTO();
        dto1.setTrialId(100L);
        dto2.setTrialId(100L);
        dto1.setTrialNciId("NCIID");
        dto2.setTrialNciId("NCIID");
        assertEquals(dto1, dto2);
    }

}
