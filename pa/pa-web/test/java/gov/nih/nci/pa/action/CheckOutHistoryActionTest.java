package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

public class CheckOutHistoryActionTest extends AbstractPaActionTest {
    private CheckOutHistoryAction action;

    @Before
    public void prepare() throws Exception {
        action = new CheckOutHistoryAction();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
    }

    @Test
    public void executeTest() throws Exception {
        assertEquals("success", action.execute());
        assertNotNull(action.getCheckOutList());
    }
}
