package gov.nih.nci.pa.decorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.service.PAException;

public class InterventionTableDecoratorTest extends AbstractPaActionTest  {
	
	InterventionTableDecorator dec;
	
	@Before
	public void setUp() throws PAException {
		dec = new InterventionTableDecorator();
	}

	@Test
	public void testAddRowId() {
		assertEquals("undefined", dec.addRowId());
		
	}
}
