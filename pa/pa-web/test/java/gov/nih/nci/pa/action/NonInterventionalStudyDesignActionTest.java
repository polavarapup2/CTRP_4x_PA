/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.OSDesignDetailsWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class NonInterventionalStudyDesignActionTest extends AbstractPaActionTest {
	
	NonInterventionalStudyDesignAction observationalStudy;
	
	@Before 
	public void setUp() throws PAException {
	  observationalStudy =  new NonInterventionalStudyDesignAction();	
	  getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
	 
	}
	

	/**
	 * Test method for {@link gov.nih.nci.pa.action.NonInterventionalStudyDesignAction#detailsQuery()}.
	 */
	@Test
	public void testDetailsQuery() {
		assertEquals("details", observationalStudy.detailsQuery());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.NonInterventionalStudyDesignAction#update()}.
	 * @throws PAException 
	 */
	@Test
	public void testUpdate() throws PAException {
		OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setBiospecimenDescription("Test");
		webDTO.setBiospecimenRetentionCode("Retained");
		webDTO.setMinimumTargetAccrualNumber("1");
		webDTO.setNumberOfGroups("1");
		webDTO.setStudyModelCode("Cohort");
		webDTO.setStudyModelOtherText("text");
		webDTO.setTimePerspectiveCode("Prospective");
		webDTO.setTimePerspectiveOtherText("timeText");
		observationalStudy.setWebDTO(webDTO);
		assertEquals("details", observationalStudy.updateDesign());
	}
    @Test
    public void testUpdateErr() throws PAException {
    	OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setBiospecimenDescription("Test");
		webDTO.setBiospecimenRetentionCode("Retained");
		webDTO.setMinimumTargetAccrualNumber("-1");
		observationalStudy.setWebDTO(webDTO);
		assertEquals("details", observationalStudy.updateDesign());

    }
    
    @Test
    public void testUpdate2() throws PAException {
		OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setBiospecimenDescription("Test");
		webDTO.setBiospecimenRetentionCode("Retained");
		webDTO.setMinimumTargetAccrualNumber("1");
		webDTO.setNumberOfGroups("1");
		webDTO.setStudyModelCode("Cohort");
		webDTO.setTimePerspectiveCode("Prospective");
		webDTO.setPhaseCode("phaseCode");
		webDTO.setStudySubtypeCode("studySubtypeCode");
		webDTO.setPrimaryPurposeCode("primaryPurposeCode");
		webDTO.setStudyModelCode("studyModelCode");
		webDTO.setFinalAccrualNumber("1");
		observationalStudy.setWebDTO(webDTO);
		observationalStudy.prepare();
		observationalStudy.getWebDTO();
		assertEquals("details", observationalStudy.updateDesign());
    }
    
    @Test
    public void testUpdate3() throws PAException {
		OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setBiospecimenDescription("Test");
		webDTO.setBiospecimenRetentionCode("Retained");
		webDTO.setNumberOfGroups("-1");
		webDTO.setStudyModelCode("Other");
		webDTO.setTimePerspectiveCode("Other");
		webDTO.setPhaseCode("Other");
		webDTO.setStudySubtypeCode("Other");
		webDTO.setPrimaryPurposeCode("Other");
		webDTO.setPrimaryPurposeAdditionalQualifierCode("Other");
		webDTO.setStudyModelCode("Other");
		webDTO.setFinalAccrualNumber("1");
		observationalStudy.setWebDTO(webDTO);
		assertEquals("details", observationalStudy.updateDesign());
    }
    
    @Test
	public void testchangeStudyType() {
    	OSDesignDetailsWebDTO webDTO = new OSDesignDetailsWebDTO();
		webDTO.setStudyType(PAConstants.INTERVENTIONAL);
		observationalStudy.setWebDTO(webDTO);
		assertEquals("isdesign", observationalStudy.changeStudyType());
    }
    

}
