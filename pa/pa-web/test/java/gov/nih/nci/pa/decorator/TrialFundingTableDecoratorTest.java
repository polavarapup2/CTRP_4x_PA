package gov.nih.nci.pa.decorator;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.util.Date;

import org.displaytag.exception.DecoratorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 *
 */
public class TrialFundingTableDecoratorTest {
	private TrialFundingTableDecorator decorator;
    @Before
    public void setUp() throws Exception {
       decorator = new TrialFundingTableDecorator();
       TrialFundingWebDTO dto = new TrialFundingWebDTO();
       dto.setFundingPercent("10");
       decorator.initRow(dto, 1, 1);
    }

    
    @After
    public void tearDown() throws Exception {
    }


    @Test
    public final void testDecorate() throws DecoratorException {
       String result = decorator.getFundingPercent();
       assertEquals("10%", result);
    }
}
