package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
/**
 * Test class for the PopUpDisAction.
 * 
 * @author Michael Visee
 */
public class PopUpDisActionTest extends AbstractPaActionTest {
	private PopUpDisAction action;
	
	@Before
    public void setUp() {
		action = new PopUpDisAction();
	}
    /**
     * Test the displayList method with no search criteria.
     */
    @Test
    public void testDisplayListNoSearch() {
        String result = action.displayList();
        assertEquals("displayList", result);
        String msg = (String) ServletActionContext.getRequest().getAttribute(Constants.FAILURE_MESSAGE);
        assertEquals("Wrong error message", "Please enter at least one search criteria", msg);
        List<DiseaseWebDTO> disWebList = action.getDisWebList();
        assertNotNull(disWebList);
        assertTrue(disWebList.isEmpty());
    }
    
    /**
     * Test the displayList method in case of success.
     * @throws PAException if an error occurs
     */
    @Test
    public void testDisplayList() throws PAException {
		action.prepare();
		PopUpDisAction action = mock(PopUpDisAction.class);
        doCallRealMethod().when(action).setSearchName("abcd");
        doCallRealMethod().when(action).displayList();
        doCallRealMethod().when(action).getStudyProtocolIi();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        Map<Ii, StudyDiseaseDTO> existingDiseaseIis = new HashMap<Ii, StudyDiseaseDTO>();
        when(action.getExistingDiseases(spIi)).thenReturn(existingDiseaseIis);
        List<PDQDiseaseDTO> pdqDiseaseDTOs = new ArrayList<PDQDiseaseDTO>();
        when(action.searchDiseases()).thenReturn(pdqDiseaseDTOs);
        action.setSearchName("abcd");
        String result = action.displayList();
        verify(action).getExistingDiseases(spIi);
        verify(action).searchDiseases();
        verify(action).getDiseaseWebList(existingDiseaseIis, pdqDiseaseDTOs);
        verify(action).loadParentPreferredNames();
        assertEquals("displayList", result);
    }
    
    /**
     * Test the displayList method in case of backend exception.
     * @throws PAException if an error occurs
     */
    @Test
    public void testDisplayListException() throws PAException {
    	PopUpDisAction action = mock(PopUpDisAction.class);
        doCallRealMethod().when(action).setSearchName("abcd");
        doCallRealMethod().when(action).displayList();
        doCallRealMethod().when(action).getStudyProtocolIi();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        when(action.getExistingDiseases(spIi)).thenThrow(new PAException("PAException"));
        action.setSearchName("abcd");
        String result = action.displayList();
        verify(action).getExistingDiseases(spIi);
        String msg = (String) ServletActionContext.getRequest().getAttribute(Constants.FAILURE_MESSAGE);
        assertEquals("Wrong error message", "PAException", msg);
        assertEquals("displayList", result);
    }
    
    /**
     * Test the getStudyProtocolIi method.
     */
    @Test
    public void testGetStudyProtocolIi() {
        Ii result = action.getStudyProtocolIi();
        assertEquals("Wrong strudy Protocol Ii", IiConverter.convertToStudyProtocolIi(1L), result);
    }
    
    /**
     * test the getExistingDiseases method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testGetExistingDiseases() throws PAException {
        StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        List<StudyDiseaseDTO> studyDiseaseDTOs = new ArrayList<StudyDiseaseDTO>();
        StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
        Ii diseaseIi = IiConverter.convertToIi(1L);
        studyDiseaseDTO.setDiseaseIdentifier(diseaseIi);
        studyDiseaseDTOs.add(studyDiseaseDTO);
        when(studyDiseaseService.getByStudyProtocol(spIi)).thenReturn(studyDiseaseDTOs);        
        action.setStudyDiseaseService(studyDiseaseService);
        Map<Ii, StudyDiseaseDTO> result = action.getExistingDiseases(spIi);
        verify(studyDiseaseService).getByStudyProtocol(spIi);
        assertNotNull("No result returned", result);
        assertEquals("Result has wrong size", 1, result.size());
        StudyDiseaseDTO resultDTO = result.get(diseaseIi);
        assertSame("Wrong result in the map", studyDiseaseDTO, resultDTO);
    }
    
    /**
     * test the searchDiseases method
     * @throws PAException if an error occurs
     */
    @Test
    public void testSearchDiseases() throws PAException {
        PDQDiseaseServiceLocal pdqDiseaseService = mock(PDQDiseaseServiceLocal.class);
        List<PDQDiseaseDTO> expectedResult = new ArrayList<PDQDiseaseDTO>();
        when(pdqDiseaseService.search(any(PDQDiseaseDTO.class))).thenReturn(expectedResult);        
        action.setPdqDiseaseService(pdqDiseaseService);
        action.setSearchName("abcd");
        action.setIncludeSynonym("true");
        action.setExactMatch("true");
        assertNotNull(action.getSearchName());
        assertNotNull(action.getIncludeSynonym());
        assertNotNull(action.getExactMatch());
        List<PDQDiseaseDTO> result = action.searchDiseases();
        ArgumentCaptor<PDQDiseaseDTO> argument = ArgumentCaptor.forClass(PDQDiseaseDTO.class);
        verify(pdqDiseaseService).search(argument.capture());
        PDQDiseaseDTO criteria = argument.getValue();
        assertEquals("Wrong name in criteria", "abcd", StConverter.convertToString(criteria.getPreferredName()));
        assertEquals("Wrong IncludeSynonym in criteria", "true", StConverter.convertToString(criteria.getIncludeSynonym()));
        assertEquals("Wrong ExactMatch in criteria", "true", StConverter.convertToString(criteria.getExactMatch()));
        assertSame("Wrong result returned", expectedResult, result);
    }
    /**
     * 
     */
    @Test
    public void testGetDiseaseWebList() {
        List<PDQDiseaseDTO> pdqDiseaseDTOs = new ArrayList<PDQDiseaseDTO>();
        for (long i = 1; i < 4; i++) {
            pdqDiseaseDTOs.add(createDiseaseDTO(i));
        }
        Map<Ii, StudyDiseaseDTO> existingDiseases = new HashMap<Ii, StudyDiseaseDTO>();
        StudyDiseaseDTO sd1 = new StudyDiseaseDTO();
        sd1.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        existingDiseases.put(pdqDiseaseDTOs.get(0).getIdentifier(), sd1);
        StudyDiseaseDTO sd2 = new StudyDiseaseDTO();
        sd2.setCtGovXmlIndicator(BlConverter.convertToBl(false));
        existingDiseases.put(pdqDiseaseDTOs.get(1).getIdentifier(), sd2);        
        List<DiseaseWebDTO> result = action.getDiseaseWebList(existingDiseases, pdqDiseaseDTOs);
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 3, result.size());
        assertDiseaseWebDTO(result.get(0), 1L, true, "Yes");
        assertDiseaseWebDTO(result.get(1), 2L, true, "No");
        assertDiseaseWebDTO(result.get(2), 3L, false, null);
    }
    
    @Test
    public void testGetDisease() throws Exception {
    	action.getDiseaseTree();
    	action.displayDiseaseWidget();
    	action.addDiseases();
    	action.setDiseaseId(1L);
    	action.getDiseaseId();
    }
    
    private PDQDiseaseDTO createDiseaseDTO(long id) {
        PDQDiseaseDTO dto = new PDQDiseaseDTO();
        dto.setIdentifier(IiConverter.convertToIi(id));
        dto.setPreferredName(StConverter.convertToSt("name " + id));
        dto.setDiseaseCode(StConverter.convertToSt("diseaseCode " + id));
        dto.setNtTermIdentifier(StConverter.convertToSt("conceptId " + id));
        dto.setDisplayName(StConverter.convertToSt("displayName " + id));
        return dto;
    }
    
    private void assertDiseaseWebDTO(DiseaseWebDTO dto, Long id, boolean selected, String ctGovXmlIndicator) {
        assertEquals("Wrong identifier in webDTO", id, Long.valueOf(dto.getDiseaseIdentifier()));
        assertEquals("Wrong name in webDTO", "name " + id, dto.getPreferredName());
        assertEquals("Wrong Code webDTO", "diseaseCode " + id, dto.getCode());
        assertEquals("Wrong ConceptId in webDTO", "conceptId " + id, dto.getConceptId());
        assertEquals("Wrong MenuDisplayName in webDTO", "displayName " + id, dto.getMenuDisplayName());
        assertEquals("Wrong selected flag in webDTO", selected, dto.isSelected());
        assertEquals("Wrong CtGovXmlIndicator in webDTO",ctGovXmlIndicator, dto.getCtGovXmlIndicator());
    }
    
    /**
     * Test the add method when no error happens in the backend.
     * @throws PAException if an error occurs
     */
    @Test
    public void testAddDiseases() throws PAException {
        
        StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);        
        action.setStudyDiseaseService(studyDiseaseService);

        action.setDiseaseIds("123,321,4321");
        assertNotNull(action.getDiseaseIds());
        String result = action.addDiseases();
        assertEquals("success", result);
        assertNotNull(action.getPdqDiseases());
        assertEquals(3, action.getPdqDiseases().size());
        assertTrue(action.getPdqDiseases().contains(new Long("123")));
        assertTrue(action.getPdqDiseases().contains(new Long("321")));
        assertTrue(action.getPdqDiseases().contains(new Long("4321")));
    }

    /**
     * Test the add method when no error happens in the backend.
     * @throws PAException if an error occurs
     */
    @Test
    public void testAddDiseasesError() throws PAException {
        
        StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
        when(studyDiseaseService.create(any(StudyDiseaseDTO.class))).thenAnswer(new Answer<StudyDiseaseDTO>() {
            public StudyDiseaseDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                StudyDiseaseDTO dto = (StudyDiseaseDTO) args[0];
                if (IiConverter.convertToLong(dto.getDiseaseIdentifier()) == 321L) {
                    throw new PAException("PAException");
                }
                return dto;
            }
        });        
        action.setStudyDiseaseService(studyDiseaseService);

        action.setDiseaseIds("123,321,4321");
        String result = action.addDiseases();
        assertEquals("success", result);
        verify(studyDiseaseService, times(3)).create(any(StudyDiseaseDTO.class));
    }

	/**
	 * Test the add method when no error happens in the backend.
	 * @throws PAException if an error occurs
	 */
	@Test
	public void testAddNoError() throws PAException {
	    StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
	    StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
	    PopUpDisAction action = mock(PopUpDisAction.class);
	    doCallRealMethod().when(action).setStudyDiseaseService(studyDiseaseService);
	    doCallRealMethod().when(action).addDisease();
	    when(action.getStudyDisease()).thenReturn(studyDiseaseDTO);
	    when(action.displayList()).thenReturn("displayList");
	    action.setStudyDiseaseService(studyDiseaseService);
	    String result = action.addDisease();
	    verify(action).getStudyDisease();
	    verify(action, never()).addActionError(anyString());
	    verify(studyDiseaseService).create(studyDiseaseDTO);
	    assertEquals("displayList", result);
	}
	
	/**
     * Test the add method when an error happens in the backend.
     * @throws PAException if an error occurs
     */
	@Test
    public void testAddError() throws PAException {
        StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
        StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
        when(studyDiseaseService.create(studyDiseaseDTO)).thenThrow(new PAException("PAException"));
        PopUpDisAction action = mock(PopUpDisAction.class);
        doCallRealMethod().when(action).setStudyDiseaseService(studyDiseaseService);
        doCallRealMethod().when(action).addDisease();
        when(action.getStudyDisease()).thenReturn(studyDiseaseDTO);
        when(action.displayList()).thenReturn("displayList");
        action.setStudyDiseaseService(studyDiseaseService);
        String result = action.addDisease();
        verify(action).getStudyDisease();
        verify(action).addActionError("PAException");
        verify(studyDiseaseService).create(studyDiseaseDTO);
        assertEquals("displayList", result);
    }
	
	/**
	 * test the getStudyDisease method.
	 */
	@Test
	public void testGetStudyDisease() {
	    PopUpDisAction action = mock(PopUpDisAction.class);
	    Ii spIi = IiConverter.convertToIi(1L);
	    doCallRealMethod().when(action).setDiseaseId(anyLong());
	    doCallRealMethod().when(action).setIncludeXml(anyBoolean());
	    doCallRealMethod().when(action).getStudyDisease();
	    when(action.getStudyProtocolIi()).thenReturn(spIi);
	    action.setDiseaseId(1L);
	    action.setIncludeXml(true);
	    StudyDiseaseDTO result = action.getStudyDisease();
	    assertNull("Wrong result identifier", result.getIdentifier());
	    assertEquals("Wrong disease identifier", IiConverter.convertToIi(1L), result.getDiseaseIdentifier());
	    assertEquals("Wrong study protocol identifier", spIi, result.getStudyProtocolIdentifier());
	    assertEquals("Wrong ctov xml indicator", BlConverter.convertToBl(true), result.getCtGovXmlIndicator());
	}
	
	/**
     * Test the remove method when no error happens in the backend.
     * @throws PAException if an error occurs
     */
	@Test
	public void testRemoveNoError() throws PAException {
	    StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
	    PopUpDisAction action = mock(PopUpDisAction.class);
	    doCallRealMethod().when(action).setDiseaseId(anyLong());
        doCallRealMethod().when(action).setStudyDiseaseService(studyDiseaseService);
        doCallRealMethod().when(action).remove();
        when(action.displayList()).thenReturn("displayList");
        action.setDiseaseId(1L);
        action.setStudyDiseaseService(studyDiseaseService);
        String result = action.remove();
        verify(studyDiseaseService).delete(IiConverter.convertToIi(1L));
        verify(action, never()).addActionError(anyString());
        assertEquals("displayList", result);
	}
	
	/**
     * Test the remove method when an error happens in the backend.
     * @throws PAException if an error occurs
     */
	@Test
    public void testRemoveError() throws PAException {
	    StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
	    Ii diseaseIi = IiConverter.convertToIi(1L);
	    doThrow(new PAException("PAException")).when(studyDiseaseService).delete(diseaseIi);
        PopUpDisAction action = mock(PopUpDisAction.class);
        doCallRealMethod().when(action).setDiseaseId(anyLong());
        doCallRealMethod().when(action).setStudyDiseaseService(studyDiseaseService);
        doCallRealMethod().when(action).remove();
        when(action.displayList()).thenReturn("displayList");
        action.setDiseaseId(1L);
        action.setStudyDiseaseService(studyDiseaseService);
        String result = action.remove();
        verify(studyDiseaseService).delete(diseaseIi);
        verify(action).addActionError("PAException");
        assertEquals("displayList", result);
    }
	
	/**
	 * Test the getDiseaseParents method when no error occurs in the backend
	 * @throws PAException if an error occurs
	 */
	@Test
	public void testGetDiseaseParents() throws PAException {	    
        action.setDisWebList(createDiseaseWebDtos());
        List<PDQDiseaseParentDTO> expectedResult = new ArrayList<PDQDiseaseParentDTO>();
        PDQDiseaseParentServiceRemote pdqDiseaseParentService = mock(PDQDiseaseParentServiceRemote.class);
        action.setPdqDiseaseParentService(pdqDiseaseParentService);
        when(pdqDiseaseParentService.getByChildDisease(any(Ii[].class))).thenReturn(expectedResult);
        List<PDQDiseaseParentDTO> result = action.getDiseaseParents();
        assertSame("Wrong result returned", expectedResult, result);
        ArgumentCaptor<Ii[]> captor = ArgumentCaptor.forClass(Ii[].class);
        verify(pdqDiseaseParentService).getByChildDisease(captor.capture());
        Ii[] argument = captor.getValue();
        assertNotNull("No argument passed to getByChildDisease", argument);
        assertEquals("Array has wrong length", 2, argument.length);
        assertEquals("Wrong id in first position", Long.valueOf(1L), IiConverter.convertToLong(argument[0]));
        assertEquals("Wrong id in second position", Long.valueOf(2L), IiConverter.convertToLong(argument[1]));
        
	}
	
	/**
     * Test the getDiseaseParents method when an error occurs in the backend
	 * @throws PAException if an error occurs
     */
    @Test
    public void testGetDiseaseParentsError() throws PAException {        
        List<DiseaseWebDTO> diseaseWebDtos = new ArrayList<DiseaseWebDTO>();
        action.setDisWebList(diseaseWebDtos);
        PDQDiseaseParentServiceRemote pdqDiseaseParentService = mock(PDQDiseaseParentServiceRemote.class);
        action.setPdqDiseaseParentService(pdqDiseaseParentService);
        when(pdqDiseaseParentService.getByChildDisease(any(Ii[].class))).thenThrow(new PAException("PAException"));
        List<PDQDiseaseParentDTO> result = action.getDiseaseParents();
        verify(pdqDiseaseParentService).getByChildDisease(any(Ii[].class));
        assertNotNull("No result returned", result);
        assertEquals("Result has wrong size", 0, result.size());
        String msg = (String) ServletActionContext.getRequest().getAttribute(Constants.FAILURE_MESSAGE);
        assertEquals("Wrong error message", "Exception thrown while getting disease parents.", msg);
    }
    
    /**
     * test the loadParentPreferredNames method.
     * Input:  - disease web dto list contains diseases 1 and 2.
     *         - getDiseaseParents() returns the following:
     *           parent of 1 is 3
     *           parent of 2 is 4
     *         - disease service correctly return the parents
     * Expected result: - First disease web dto will contain parent name "Disease 3 name"
     *                  - Second disease web dto will contain parent name "Disease 4 name"
     *                  - error message is in the request
     * @throws PAException if an error occurs
     */
    @Test
    public void testloadParentPreferredNames() throws PAException {
        PDQDiseaseServiceLocal pdqDiseaseService = mock(PDQDiseaseServiceLocal.class);
        PopUpDisAction action = mock(PopUpDisAction.class);
        doCallRealMethod().when(action).loadParentPreferredNames();
        doCallRealMethod().when(action).setPdqDiseaseService(pdqDiseaseService);
        action.setPdqDiseaseService(pdqDiseaseService);
        List<DiseaseWebDTO> diseaseWebDtos = createDiseaseWebDtos();
        doCallRealMethod().when(action).setDisWebList(diseaseWebDtos);
        action.setDisWebList(diseaseWebDtos);
        // Creates the parent list
        List<PDQDiseaseParentDTO> parents = new ArrayList<PDQDiseaseParentDTO>();
        PDQDiseaseParentDTO parent1 = new PDQDiseaseParentDTO();
        parent1.setIdentifier(IiConverter.convertToIi(1L));
        parent1.setParentDiseaseIdentifier(IiConverter.convertToIi(3L));
        parents.add(parent1);
        PDQDiseaseParentDTO parent2 = new PDQDiseaseParentDTO();
        parent2.setIdentifier(IiConverter.convertToIi(2L));
        parent2.setParentDiseaseIdentifier(IiConverter.convertToIi(4L));
        parents.add(parent2);
        when(action.getDiseaseParents()).thenReturn(parents);
        // setup the pdqDisease service
        PDQDiseaseDTO parent3DTO = new PDQDiseaseDTO();
        parent3DTO.setIdentifier(IiConverter.convertToIi(3L));
        parent3DTO.setPreferredName(StConverter.convertToSt("Disease 3 name"));
        when(pdqDiseaseService.get(IiConverter.convertToIi(3L))).thenReturn(parent3DTO);
        PDQDiseaseDTO parent4DTO = new PDQDiseaseDTO();
        parent4DTO.setIdentifier(IiConverter.convertToIi(4L));
        parent4DTO.setPreferredName(StConverter.convertToSt("Disease 4 name"));
        when(pdqDiseaseService.get(IiConverter.convertToIi(4L))).thenReturn(parent4DTO);
        action.loadParentPreferredNames();
        verify(action).getDiseaseParents();
        verify(pdqDiseaseService).get(IiConverter.convertToIi(3L));
        verify(pdqDiseaseService).get(IiConverter.convertToIi(4L));
        assertEquals("Wrong preferred name", "Disease 3 name", diseaseWebDtos.get(0).getParentPreferredName());
        assertEquals("Wrong preferred name", "Disease 4 name", diseaseWebDtos.get(1).getParentPreferredName());
    }
   
    private List<DiseaseWebDTO> createDiseaseWebDtos() {
        List<DiseaseWebDTO> diseaseWebDtos = new ArrayList<DiseaseWebDTO>();
        DiseaseWebDTO dis1 = new DiseaseWebDTO();
        dis1.setDiseaseIdentifier("1");
        diseaseWebDtos.add(dis1);
        DiseaseWebDTO dis2 = new DiseaseWebDTO();
        dis2.setDiseaseIdentifier("2");
        diseaseWebDtos.add(dis2);
        return diseaseWebDtos;
    }
    
}
