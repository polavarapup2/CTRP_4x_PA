/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialIndideActionTest extends AbstractPaActionTest {

	TrialIndideAction trialIndideAction;

	@Before
	public void setUp() throws PAException {
		trialIndideAction =  new TrialIndideAction();
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));

	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#displayJs()}.
	 */
	@Test
	public void testDisplayJs() {
		assertEquals("success", trialIndideAction.displayJs());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#query()}.
	 */
	@Test
	public void testQuery() {
		 assertEquals("query",trialIndideAction.query());
		 assertEquals("No IND/IDE records exist on the trial", getRequest().getAttribute("successMessage"));
		 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
		 assertEquals("query",trialIndideAction.query());
		 assertNotNull(trialIndideAction.getStudyIndideList());
		 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
	     assertEquals("query",trialIndideAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#create()}.
	 */
	@Test
	public void testCreate() {
         assertEquals("add",trialIndideAction.create());
         StudyIndldeWebDTO studyIndldeWebDTO = getStudyIndIdeDTO();
         studyIndldeWebDTO.setHolderType(HolderTypeCode.NIH.getCode());
         trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
         assertEquals("add",trialIndideAction.create());
         studyIndldeWebDTO.setNihInstHolder("nihInstHolder");
         studyIndldeWebDTO.setIndldeNumber("exception");
         trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
         assertEquals("add",trialIndideAction.create());
         assertNotNull(getRequest().getAttribute("failureMessage"));
         studyIndldeWebDTO.setIndldeNumber("indldeNumber");
         trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
         assertEquals("query",trialIndideAction.create());
         studyIndldeWebDTO = getStudyIndIdeDTO();
         studyIndldeWebDTO.setExemptIndicator(Boolean.TRUE);
         trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
         assertEquals("query",trialIndideAction.create());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#update()}.
	 */
	@Test
	public void testUpdate() {
		assertEquals("edit",trialIndideAction.update());
        StudyIndldeWebDTO studyIndldeWebDTO = getStudyIndIdeDTO();
        studyIndldeWebDTO.setIndldeNumber("!233");
        studyIndldeWebDTO.setExpandedAccessIndicator("true");
        studyIndldeWebDTO.setExpandedAccessStatus("");
        trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
        assertEquals("edit",trialIndideAction.update());
        studyIndldeWebDTO.setExpandedAccessIndicator("expandedAccessIndicator");
        studyIndldeWebDTO.setHolderType(HolderTypeCode.NCI.getCode());
        trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
        assertEquals("edit",trialIndideAction.update());

        studyIndldeWebDTO.setNciDivProgHolder("nciDivProgHolder");
        studyIndldeWebDTO.setIndldeNumber("exception");
        trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
        assertEquals("edit",trialIndideAction.update());
        assertNotNull(getRequest().getAttribute("failureMessage"));

        trialIndideAction.setStudyIndldeWebDTO(getStudyIndIdeDTO());
        trialIndideAction.setCbValue(1L);
        assertEquals("query",trialIndideAction.update());
        studyIndldeWebDTO = getStudyIndIdeDTO();
        studyIndldeWebDTO.setExpandedAccessIndicator("expandedAccessIndicator");
        studyIndldeWebDTO.setExemptIndicator(Boolean.FALSE);
        trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
        trialIndideAction.setCbValue(1L);
        assertEquals("query",trialIndideAction.update());
	}

    /**
     * @return
     */
    private StudyIndldeWebDTO getStudyIndIdeDTO() {
        StudyIndldeWebDTO studyIndldeWebDTO = new StudyIndldeWebDTO();
        studyIndldeWebDTO.setExpandedAccessIndicator("Yes");
        studyIndldeWebDTO.setExpandedAccessStatus("Available");
        studyIndldeWebDTO.setGrantor(GrantorCode.CDRH.getCode());
        studyIndldeWebDTO.setHolderType(HolderTypeCode.INDUSTRY.getCode());
        studyIndldeWebDTO.setIndldeNumber("indldeNumber");
        studyIndldeWebDTO.setIndldeType(IndldeTypeCode.IND.getCode());
        return studyIndldeWebDTO;
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#delete()}.
	 */
    @Test
    public void testDelete() {
        trialIndideAction.setObjectsToDelete(new String[0]);
        assertEquals("query",trialIndideAction.delete());
        assertNotNull(getRequest().getAttribute("failureMessage"));
        getRequest().removeAttribute("failureMessage");
        getRequest().removeAttribute("successMessage");
        
        trialIndideAction.setObjectsToDelete(new String[] {"1"});
        assertEquals("query",trialIndideAction.delete());
        assertNull(getRequest().getAttribute("failureMessage"));
        assertNotNull(getRequest().getAttribute("successMessage"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#edit()}.
     */
    @Test
    public void testEdit() {
        trialIndideAction.setCbValue(1L);
        assertEquals("edit",trialIndideAction.edit());
        trialIndideAction.setCbValue(3L);
        assertEquals("edit",trialIndideAction.edit());
        assertNotNull(getRequest().getAttribute("failureMessage"));
    }
    @Test
    public void testProperties() {
        assertNull(trialIndideAction.getCbValue());
        trialIndideAction.setCbValue(1L);
        assertNotNull(trialIndideAction.getCbValue());
        assertNull(trialIndideAction.getPage());
        trialIndideAction.setPage("page");
        assertNotNull(trialIndideAction.getPage());
        assertNotNull(trialIndideAction.getStudyIndldeWebDTO());
        trialIndideAction.setStudyIndldeWebDTO(null);
        assertNull(trialIndideAction.getStudyIndldeWebDTO());
        assertNull(trialIndideAction.getStudyIndideList());
        trialIndideAction.setStudyIndideList(new ArrayList<StudyIndldeWebDTO>());
        assertNotNull(trialIndideAction.getStudyIndideList());

    }
}
