/**
 *
 */
package gov.nih.nci.pa.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.dto.CaDSRWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.service.PAException;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PADisplayTagDecoratorTest extends AbstractPaActionTest {

	PADisplayTagDecorator tab;

	@Before
	public void setUp() throws PAException {
		tab = new PADisplayTagDecorator();

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.decorator.PADisplayTagDecorator#getAction()}.
	 */
	@Test
	public void testGetAction() {
		StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
		dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
		dto.setUpdatedDate(new Date());
		tab.initRow(dto, 1, 1);
		assertEquals("Validate", tab.getAction());
		dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.REJECTED);
		tab.initRow(dto, 1, 1);
		assertEquals("", tab.getAction());
		dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
		tab.initRow(dto, 1, 1);
		assertEquals("Abstract", tab.getAction());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.decorator.PADisplayTagDecorator#getViewTSR()}.
	 */
	@Test
	public void testGetViewTSR() {
		StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
		dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
		dto.setUpdatedDate(new Date());
		tab.initRow(dto, 1, 1);
		assertEquals("", tab.getViewTSR());
		dto.setViewTSR(true);
		tab.initRow(dto, 1, 1);
		assertNotNull(tab.getViewTSR());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.decorator.PADisplayTagDecorator#getRemove()}.
	 */
	@Test
	public void testGetRemove() {
		StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
		dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
		dto.setUpdatedDate(new Date());
		tab.initRow(dto, 1, 1);
		assertEquals("Remove", tab.getRemove());
		dto.setUpdatedDate(null);
		tab.initRow(dto, 1, 1);
		assertEquals("", tab.getRemove());
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.decorator.PADisplayTagDecorator#getViewPublicId()}.
	 */
	@Test
	public void testGetViewPublicId() {
		CaDSRWebDTO de = new CaDSRWebDTO();
		de.setPublicId(1L);
		de.setVersion("1");
		tab.initRow(de, 1, 1);
		assertNotNull(tab.getViewPublicId());
	}
	
    @Test
    public void getTrialCategory() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        tab.initRow(dto, 1, 1);
        assertEquals("Abbreviated", tab.getTrialCategory());

        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(false);
        tab.initRow(dto, 1, 1);
        assertEquals("Complete", tab.getTrialCategory());
    }

}
