package gov.nih.nci.pa.decorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.service.PAException;

public class OutcomeMeasureTableDecoratorTest extends AbstractPaActionTest  {
	
	OutcomeMeasureTableDecorator dec;
	
	@Before
	public void setUp() throws PAException {
		dec = new OutcomeMeasureTableDecorator();
	}

	@Test
	public void testAddRowId() {
		assertEquals("undefined", dec.addRowId());
		
	}
}
