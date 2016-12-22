package gov.nih.nci.accrual.dto.util;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

/**
 * @author Kalpana
 *
 */
public class AccrualCountsDtoTest {

    @Test
    public void testGetters() {
    	AccrualCountsDto dto = new AccrualCountsDto();

        dto.setTrialCount(3L);
        assertNotNull(dto.getTrialCount());
        dto.setDate(new Timestamp(new Date().getTime()));
        assertNotNull(dto.getDate());
    }

}
