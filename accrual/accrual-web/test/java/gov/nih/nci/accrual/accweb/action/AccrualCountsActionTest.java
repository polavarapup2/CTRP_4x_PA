package gov.nih.nci.accrual.accweb.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class AccrualCountsActionTest extends AbstractAccrualActionTest {

    private AccrualCountsAction action;

    @Before
    public void initAction() throws PAException, URISyntaxException {
        action = new AccrualCountsAction();
        action.prepare();
    }

    @Override
    @Test
    public void executeTest() {
        assertEquals(Action.SUCCESS, action.execute());
    }
}
