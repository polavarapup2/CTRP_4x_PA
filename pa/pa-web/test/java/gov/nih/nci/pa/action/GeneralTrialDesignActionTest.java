/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class GeneralTrialDesignActionTest extends AbstractPaActionTest {

	GeneralTrialDesignAction generalTrialDesignAction;

	@Before
	public void setUp() throws PAException {
	 generalTrialDesignAction	= new GeneralTrialDesignAction();
	 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.GeneralTrialDesignAction#query()}.
	 */
	@Test
	public void testQuery() {
		assertEquals("edit",generalTrialDesignAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.GeneralTrialDesignAction#update()}.
	 */
	@Test
	public void testUpdate() {
		assertEquals("edit",generalTrialDesignAction.update());
		GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setProprietarytrialindicator(Boolean.TRUE.toString());
        generalTrialDesignAction.setGtdDTO(gtdDTO);
        assertEquals("edit", generalTrialDesignAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        generalTrialDesignAction.setGtdDTO(gtdDTO);
        assertEquals("edit", generalTrialDesignAction.update());
        gtdDTO = new GeneralTrialDesignWebDTO();
        gtdDTO.setCtGovXmlRequired(true);
        gtdDTO.setPrimaryPurposeCode("Other");
        gtdDTO.setPrimaryPurposeAdditionalQualifierCode("Other");
        generalTrialDesignAction.setGtdDTO(gtdDTO);
        assertEquals("edit", generalTrialDesignAction.update());

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.GeneralTrialDesignAction#removeCentralContact()}.
	 */
	@Test
	public void testRemoveCentralContact() {
		assertEquals("edit",generalTrialDesignAction.removeCentralContact());
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.AbstractGeneralTrialDesignAction#validateResponsibleParty()}.
	 */
	@Test
	public void testValidateResponsibleParty() {
	    GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
	    gtdDTO.setResponsiblePartyType("");
	    generalTrialDesignAction.setGtdDTO(gtdDTO);
	    generalTrialDesignAction.validateResponsibleParty();
	    gtdDTO.setResponsiblePartyType("si");
	    generalTrialDesignAction.validateResponsibleParty();
	    gtdDTO.setResponsiblePartyType("pi");
	    generalTrialDesignAction.validateResponsibleParty();
	    gtdDTO.setResponsiblePartyType("sponsor");
	    generalTrialDesignAction.validateResponsibleParty();	    
	}		
}
