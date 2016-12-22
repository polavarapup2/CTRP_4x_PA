package gov.nih.nci.pa.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.service.PAException;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

public class CheckOutHistoryTagDecoratorTest extends AbstractPaActionTest {

    CheckOutHistoryTagDecorator td;

    @Override
    @Before
    public void setUp() throws PAException {
        td = new CheckOutHistoryTagDecorator();
    }

    @Test
    public void testCheckOutDate() {
        StudyCheckout row = new StudyCheckout();
        row.setCheckOutDate(new Timestamp(112, 0, 31, 13, 59, 30, 0));
        td.initRow(row, 1, 1);
        assertEquals("<!--2012-01-31 13:59:30.0-->01/31/2012 13:59", td.getCheckOutDate());
    }

    @Test
    public void testCheckOutType() {
        StudyCheckout row = new StudyCheckout();
        row.setCheckOutType(CheckOutType.SCIENTIFIC);
        td.initRow(row, 1, 1);
        assertEquals("Scientific", td.getCheckOutType());
    }

    @Test
    public void testUserIdentifier() {
        StudyCheckout row = new StudyCheckout();
        row.setUserIdentifier("/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=testuser");
        td.initRow(row, 1, 1);
        assertEquals("testuser", td.getUserIdentifier());
    }

    @Test
    public void testCheckInDate() {
        StudyCheckout row = new StudyCheckout();
        td.initRow(row, 1, 1);
        assertNull(td.getCheckInDate());
        row.setCheckInDate(new Timestamp(112, 0, 31, 13, 59, 30, 0));
        assertEquals("<!--2012-01-31 13:59:30.0-->01/31/2012 13:59", td.getCheckInDate());
    }

    @Test
    public void testCheckInUserIdentifier() {
        StudyCheckout row = new StudyCheckout();
        td.initRow(row, 1, 1);
        assertNull(td.getCheckInUserIdentifier());
        row.setCheckInUserIdentifier("/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=testuser");
        assertEquals("testuser", td.getCheckInUserIdentifier());
    }

}
