/**
 * 
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class LastCreatedDTOTest {

    /**
     * Test method for
     * {@link gov.nih.nci.pa.dto.LastCreatedDTO#getDateLastCreatedPlusTenBiz()}.
     * 
     * @throws ParseException
     *             ParseException
     */
    @Test
    public final void testGetDateLastCreatedPlusTenBiz() throws ParseException {
        LastCreatedDTO dto = new LastCreatedDTO();
        dto.setDateLastCreated(date("06/01/2015"));
        assertTrue(DateUtils.isSameDay(date("06/15/2015"),
                dto.getDateLastCreatedPlusTenBiz()));

        dto.setDateLastCreated(date("05/18/2015"));
        assertTrue(DateUtils.isSameDay(date("06/02/2015"),
                dto.getDateLastCreatedPlusTenBiz()));

    }

    private Date date(final String date) throws ParseException {
        return DateUtils.parseDate(date, new String[] { "MM/dd/yyyy" });
    }

}
