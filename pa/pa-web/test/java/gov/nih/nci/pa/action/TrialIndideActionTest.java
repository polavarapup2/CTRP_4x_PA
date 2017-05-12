/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalTrialIndIdeDTO;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAJsonUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RestClient;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TrialInfoHelperUtil;
import gov.nih.nci.pa.util.TrialInfoMergeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialIndideActionTest extends AbstractPaActionTest {

	TrialIndideAction trialIndideAction;
	private AdditionalTrialIndIdeDTO additionalTrialIndIdeDTO = new AdditionalTrialIndIdeDTO();
	private List<AdditionalTrialIndIdeDTO> list = new ArrayList<AdditionalTrialIndIdeDTO>();
    private RestClient client = mock(RestClient.class);
    private TrialInfoMergeHelper helper = new TrialInfoMergeHelper();
    private TrialInfoHelperUtil helperUtil = new TrialInfoHelperUtil();
    private StudyIndldeServiceLocal studyIndldeServiceLocal = mock(StudyIndldeServiceLocal.class);
    private StudyProtocolServiceLocal protocolService = mock(StudyProtocolServiceLocal.class);
    
	@Before
	public void setUp() throws PAException, IOException {
		trialIndideAction =  new TrialIndideAction();
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        String url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
 
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyIndldeService()).thenReturn(studyIndldeServiceLocal);
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(IiConverter.convertToIi(1L));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(false));
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(protocolService);
        when(protocolService.getStudyProtocol(any(Ii.class))).thenReturn(spDTO);
        additionalTrialIndIdeDTO.setStudyProtocolId("1");
        additionalTrialIndIdeDTO.setTrialIndIdeId("123");
        additionalTrialIndIdeDTO.setExpandedAccessIndicator("Yes");
        additionalTrialIndIdeDTO.setExpandedAccessNctId("NCT12345678");
        list.add(additionalTrialIndIdeDTO);
        String responseStr = PAJsonUtil.marshallJSON(list);
        when(client.sendHTTPRequest(url + "?study_protocol_id=1&trial_ide_ind_id=123", "GET", null)).thenReturn(responseStr);
        when(client.sendHTTPRequest(url, "POST", PAJsonUtil.marshallJSON(additionalTrialIndIdeDTO))).thenReturn("");
        when(client.sendHTTPRequest(url + "/1", "PUT",  PAJsonUtil.marshallJSON(additionalTrialIndIdeDTO))).thenReturn("");
        helperUtil.setClient(client);
        helper.setTrialInfoHelperUtil(helperUtil);
        trialIndideAction.setHelper(helper);
        
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
	 * @throws PAException 
	 */
	@Test
	public void testQuery() throws PAException {
		 assertEquals("query",trialIndideAction.query());
		 assertEquals("No IND/IDE records exist on the trial", getRequest().getAttribute("successMessage"));
		 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
         List<StudyIndldeDTO> isoList = new ArrayList<StudyIndldeDTO>();
         StudyIndldeDTO dto = new StudyIndldeDTO();
         dto.setIdentifier(IiConverter.convertToIi(123L)); 
         dto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CBER));
         dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.INDUSTRY));
         dto.setIndldeNumber(StConverter.convertToSt("123"));
         dto.setExpandedAccessIndicator(BlConverter.convertYesNoStringToBl("Yes"));
         dto.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IDE));
         isoList.add(dto);
         when(studyIndldeServiceLocal.getByStudyProtocol(any(Ii.class))).thenReturn(isoList);
		 
		 assertEquals("query",trialIndideAction.query());
		 assertNotNull(trialIndideAction.getStudyIndideList());
		 getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
	     assertEquals("query",trialIndideAction.query());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#create()}.
	 * @throws PAException  PAException
	 * @throws IOException  IOException
	 */
	@Test
	public void testCreate() throws PAException, IOException {
         assertEquals("add",trialIndideAction.create());
         StudyIndldeWebDTO studyIndldeWebDTO = getStudyIndIdeDTO();
         studyIndldeWebDTO.setStudyProtocolIi("1");
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
         StudyIndldeDTO dto = new StudyIndldeDTO();
         dto.setIdentifier(IiConverter.convertToIi(123L));
         when(studyIndldeServiceLocal.create(any(StudyIndldeDTO.class))).thenReturn(dto);
         assertEquals("query",trialIndideAction.create());
         studyIndldeWebDTO = getStudyIndIdeDTO();
         trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
         assertEquals("query",trialIndideAction.create());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#update()}.
	 * @throws PAException 
	 */
	@Test
	public void testUpdate() throws PAException {
		assertEquals("edit",trialIndideAction.update());
        StudyIndldeWebDTO studyIndldeWebDTO = getStudyIndIdeDTO();
        studyIndldeWebDTO.setIndldeNumber("!233");
        studyIndldeWebDTO.setExpandedAccessIndicator("true");
        studyIndldeWebDTO.setExpandedAccessNctId("NCT12345678");
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
        
        studyIndldeWebDTO = getStudyIndIdeDTO();
        studyIndldeWebDTO.setId("123");
        studyIndldeWebDTO.setMsId("1");
        trialIndideAction.setStudyIndldeWebDTO(studyIndldeWebDTO);
        trialIndideAction.setCbValue(1L);
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIdentifier(IiConverter.convertToIi(123L));
        when(studyIndldeServiceLocal.update(any(StudyIndldeDTO.class))).thenReturn(dto);
        when(studyIndldeServiceLocal.get(any(Ii.class))).thenReturn(dto);
        assertEquals("query",trialIndideAction.update());
        studyIndldeWebDTO = getStudyIndIdeDTO();
        studyIndldeWebDTO.setExpandedAccessIndicator("expandedAccessIndicator");
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
        studyIndldeWebDTO.setExpandedAccessNctId("NCT12345678");
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
     * @throws PAException 
     */
    @Test
    public void testEdit() throws PAException {
        trialIndideAction.setCbValue(1L);
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIdentifier(IiConverter.convertToIi(123L)); 
        dto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CBER));
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.INDUSTRY));
        dto.setIndldeNumber(StConverter.convertToSt("123"));
        dto.setExpandedAccessIndicator(BlConverter.convertYesNoStringToBl("Yes"));
        dto.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IDE));  
        when(studyIndldeServiceLocal.get(any(Ii.class))).thenReturn(dto);
        assertEquals("edit",trialIndideAction.edit());
    }
    

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialIndideAction#edit()}.
     * @throws PAException 
     */
    @Test
    public void testEditFailure() throws PAException {
        trialIndideAction.setCbValue(3L);
        assertEquals("edit",trialIndideAction.edit());
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
