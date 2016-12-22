/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Real;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstHolderCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class TrialFundingActionTest.
 *
 * @author asharma
 */
public class TrialFundingActionTest extends AbstractPaActionTest {

	/** The trial funding action. */
	TrialFundingAction trialFundingAction;
	Ii id = IiConverter.convertToIi(1L);

	/**
	 * Sets the up.
	 *
	 * @throws PAException the PA exception
	 */
	@Before
	public void setUp() throws PAException {
		trialFundingAction =  new TrialFundingAction();
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);

	}


	/**
	 * Test display js.
	 */
	@Test
	public void testDisplayJs() {
		assertEquals("success", trialFundingAction.displayJs());
	}


	/**
	 * Test query.
	 * @throws PAException 
	 */
    @Test
    public void testQuery() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        StudyResourcingServiceLocal service = mock(StudyResourcingServiceLocal.class);
        StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
        when(PaRegistry.getStudyResourcingService()).thenReturn(service);
        when(PaRegistry.getStudyProtocolService()).thenReturn(studyProtocolService);
        List<StudyResourcingDTO> isoList = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = new StudyResourcingDTO();
        dto.setIdentifier(id);
        dto.setActiveIndicator(BlConverter.convertToBl(true));
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("B09"));
        dto.setNihInstitutionCode(CdConverter.convertToCd(NihInstHolderCode.CSR));
        dto.setNciDivisionProgramCode(CdConverter.convertToCd(NciDivisionProgramCode.CCR));
        dto.setSerialNumber(StConverter.convertToSt("12345"));
        dto.setFundingPercent(new Real());
        dto.setInactiveCommentText(StConverter.convertToSt("Comment"));
        dto.setLastUpdatedDate(TsConverter.convertToTs(new Date()));
        dto.setUserLastUpdated("user");
        isoList.add(dto);
        StudyProtocolDTO sp = new StudyProtocolDTO();
        sp.setIdentifier(id);
        when(studyProtocolService.getStudyProtocol(id)).thenReturn(sp);
        when(service.getStudyResourcingByStudyProtocol(id)).thenReturn(isoList);
        assertEquals("query", trialFundingAction.query());
        assertTrue(trialFundingAction.getTrialFundingList().size() > 0);
        
        dto.setActiveIndicator(BlConverter.convertToBl(false));
        assertEquals("query", trialFundingAction.query());
        assertTrue(trialFundingAction.getTrialFundingDeleteList().size() > 0);
	}

	/**
	 * Test create.
	 */
	@Test
	public void testCreate() {
	    assertEquals(trialFundingAction.create(), "error");
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
	    dto.setFundingMechanismCode("B09");
	    dto.setNihInstitutionCode("AA");
	    dto.setNciDivisionProgramCode("CCR");
	    dto.setSerialNumber("00001");
	    dto.setFundingPercent("33.3");

	    trialFundingAction.setTrialFundingWebDTO(dto);
	    assertEquals(trialFundingAction.create(), "query");
	    assertTrue(trialFundingAction.getTrialFundingWebDTO()!= null);
	}

	@Test
	public void testFieldErrors() {
        TrialFundingWebDTO dto = new TrialFundingWebDTO();
        trialFundingAction.setTrialFundingWebDTO(dto);
        assertEquals(trialFundingAction.create(), "error");
        Map<String, List<String>> fes = trialFundingAction.getFieldErrors();
        assertEquals("error.trialFunding.funding.mechanism", fes.get("trialFundingWebDTO.fundingMechanismCode").get(0));
        assertEquals("error.trialFunding.institution.code", fes.get("trialFundingWebDTO.nihInstitutionCode").get(0));
        assertEquals("error.studyProtocol.monitorCode", fes.get("trialFundingWebDTO.nciDivisionProgramCode").get(0));
        assertEquals("error.trialFunding.serial.number", fes.get("trialFundingWebDTO.serialNumber").get(0));
        assertNull(fes.get("trialFundingWebDTO.fundingPercent"));
        trialFundingAction.clearErrorsAndMessages();
        dto.setSerialNumber("abc");
        dto.setFundingPercent("abc");
        assertEquals(trialFundingAction.create(), "error");
        fes = trialFundingAction.getFieldErrors();
        assertEquals("error.numeric", fes.get("trialFundingWebDTO.serialNumber").get(0));
        assertEquals("error.trialFunding.fundingPercent", fes.get("trialFundingWebDTO.fundingPercent").get(0));
        trialFundingAction.clearErrorsAndMessages();
        dto.setFundingPercent("101");
        assertEquals(trialFundingAction.create(), "error");
        fes = trialFundingAction.getFieldErrors();
        assertEquals("error.trialFunding.fundingPercent", fes.get("trialFundingWebDTO.fundingPercent").get(0));
	}

	/**
	 * Test update.
	 */
	@Test
	public void testUpdate() {
	    assertEquals("error",trialFundingAction.update());
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
        dto.setFundingMechanismCode("B09");
        dto.setNihInstitutionCode("AA");
        dto.setNciDivisionProgramCode("CCR");
        dto.setSerialNumber("00001");
        dto.setFundingPercent("33.3");

        trialFundingAction.setTrialFundingWebDTO(dto);
	    trialFundingAction.setCbValue(1L);
	    assertEquals(trialFundingAction.update(), "query");
	}


	/**
	 * Test delete.
	 */
	@Test
	public void testDelete() {
	    assertEquals("query",trialFundingAction.delete());
	    assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.FAILURE_MESSAGE)!=null);	    
	    trialFundingAction.clearErrorsAndMessages();

	    TrialFundingWebDTO dto = new TrialFundingWebDTO();
        dto.setInactiveCommentText("Deleting Funding");

        trialFundingAction.setTrialFundingWebDTO(dto);
        trialFundingAction.setObjectsToDelete(new String[] {"1"});
        assertEquals(trialFundingAction.delete(), "query");
	}

	@Test
	public void testUpdateGrant() throws Exception {
	    assertEquals(Action.NONE, trialFundingAction.updateNciGrant());
	}

	/**
	 * Test edit.
	 */
	@Test
	public void testEdit() {
		trialFundingAction.setCbValue(1L);
		assertEquals("success",trialFundingAction.edit());
	}

}
