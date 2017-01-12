package gov.nih.nci.registry.action;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyProgramCodeServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;


public class ProgramCodesActionTest extends AbstractRegWebTest {
    private ProtocolQueryServiceLocal protocolQueryService;

    private FamilyDTO familyDTO;

	@Test
	public void handleExceptionTest() throws Exception {
		ProgramCodesAction action = getAction();
		action.setServletRequest(ServletActionContext.getRequest());
	    action.setServletResponse(ServletActionContext.getResponse());
	    action.prepare();
	    action.setRegistryUserService(getRegistryServiceMock());	
		action.execute();		
	}
	
	@Test
	public void testAjaxException() throws Exception {
		ProgramCodesAction action = new ProgramCodesAction();
        action.setServletRequest(ServletActionContext.getRequest());
        action.setServletResponse(ServletActionContext.getResponse());
        action.prepare();
        action.setFamilyProgramCodeService(getProgramCodeServiceMock());        
        when(action.getFamilyProgramCodeService().update(any(FamilyDTO.class))).thenThrow(new RuntimeException());        
    }
    
    @Test
    public void testCreateProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        action.setPoId(12345L);
        assertTrue(action.createProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.createProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testUpdateProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        action.setServletRequest(mockRequest);
        action.setServletResponse(mockResponse);
        
        action.setPoId(12345L);
        when(mockRequest.getParameter("currentProgramCode")).thenReturn("PG1");
        when(mockRequest.getParameter("currentProgramCodeId")).thenReturn("1");
        when(mockRequest.getParameter("updatedProgramCode")).thenReturn("PG1-updated");
        when(mockRequest.getParameter("updatedProgramName")).thenReturn("PG1 Name-updated");
        
        assertTrue(action.updateProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.updateProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testDeleteProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        action.setServletRequest(mockRequest);
        
        action.setPoId(12345L);
        when(mockRequest.getParameter("programCodeIdSelectedForDeletion")).thenReturn("1");
        
        assertTrue(action.deleteProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.deleteProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    
    @Test
    public void testIsProgramCodeAssignedToATrial() throws Exception {        
        ProgramCodesAction action = getAction();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        action.setServletRequest(mockRequest);
        
        when(mockRequest.getParameter("programCodeIdSelectedForDeletion")).thenReturn("1");
        
        assertTrue(action.isProgramCodeAssignedToATrial() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.isProgramCodeAssignedToATrial();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[false]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testFetchProgramCodesForFamily() throws Exception {        
        ProgramCodesAction action = getAction();
        action.setPoId(12345L);
        assertTrue(action.fetchProgramCodesForFamily() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.fetchProgramCodesForFamily();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[{\"isActive\":true,\"programCodeId\":" +
                  "1,\"programCode\":\"PG1\",\"programName\":\"Program Name1\"}," +
                  "{\"isActive\":true,\"programCodeId\":2,\"programCode\":\"PG2\"," +
                  "\"programName\":\"Program Name2\"}]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testGetStudyProtocolsAssociatedToAProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        action.setServletRequest(mockRequest);
        
        action.setPoId(12345L);
        when(mockRequest.getParameter("programCodeIdSelectedForDeletion")).thenReturn("1");
        
        assertTrue(action.getStudyProtocolsAssociatedToAProgramCode() instanceof StreamResult);     
        
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          StreamResult sr = action.getStudyProtocolsAssociatedToAProgramCode();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }
    
    @Test
    public void testInactivateProgramCode() throws Exception {        
        ProgramCodesAction action = getAction();
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        action.setProtocolQueryService(protocolQueryService);


        when(action.getProtocolQueryService()
                .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class),
                        eq(SKIP_LAST_UPDATER_INFO), eq(SKIP_ALTERNATE_TITLES), eq(SKIP_OTHER_IDENTIFIERS)))
                .thenReturn(createTrials());


        when(action.getFamilyProgramCodeService().isProgramCodeAssociatedWithATrial(any(ProgramCodeDTO.class))).thenReturn(true);

        action.setPoId(12345L);
        action.setProgramCodeIdSelectedForDeletion(1L);

        assertTrue(action.inactivateProgramCode() instanceof StreamResult);

        verify(action.getStudyProtocolService(), times(1)).unassignProgramCodesFromTrials(Arrays.asList(1L),
                Arrays.asList(new ProgramCodeDTO(1L, "PG1")));


        when(action.getFamilyProgramCodeService().isProgramCodeAssociatedWithATrial(any(ProgramCodeDTO.class))).thenReturn(false);

        action.setProgramCodeIdSelectedForDeletion(2L);
        assertTrue(action.inactivateProgramCode() instanceof StreamResult);

        verify(action.getStudyProtocolService(), times(1)).unassignProgramCodesFromTrials(Arrays.asList(1L),
                Arrays.asList(new ProgramCodeDTO(2L, "PG2")));

        verify(action.getFamilyProgramCodeService(), times(1)).inactivateProgramCode(any(ProgramCodeDTO.class));
        verify(action.getFamilyProgramCodeService(), times(1)).deleteProgramCode(any(FamilyDTO.class), any(ProgramCodeDTO.class));


    }
	
	private ProgramCodesAction getAction() throws Exception {
		ProgramCodesAction action = new ProgramCodesAction();
        action.setServletRequest(ServletActionContext.getRequest());
        action.setServletResponse(ServletActionContext.getResponse());
        action.prepare();
        action.setFamilyProgramCodeService(getProgramCodeServiceMock());
        return action;
    }
	
	private FamilyProgramCodeServiceLocal getProgramCodeServiceMock() {
		final FamilyProgramCodeServiceLocal mock = mock(FamilyProgramCodeServiceLocal.class);
		

		createAndAddProgramCodes();
		
		when(mock.getFamilyDTOByPoId(any(Long.class)))
                .thenReturn(familyDTO);
                
		when(mock.update(any(FamilyDTO.class)))
                .thenReturn(familyDTO);
		
		return mock;
	}
	
	private RegistryUserServiceLocal getRegistryServiceMock() throws PAException {
		
		final RegistryUserServiceLocal mock = mock(RegistryUserServiceLocal.class);		
		
		RegistryUser registryUser = mock(RegistryUser.class);
		
	    when(registryUser.getAffiliatedOrganizationId()).thenReturn(null);
	    
	    when(mock.getUser(any(String.class))).thenReturn(registryUser);
		
		return mock;
    }
    
     private void createAndAddProgramCodes() {
        this.familyDTO = new FamilyDTO(Long.valueOf(12345L), Long.valueOf("123456"), new Date(), 12);

        ProgramCodeDTO pg1 = new ProgramCodeDTO();
        pg1.setId(1L);
        pg1.setProgramName("Program Name1");
        pg1.setProgramCode("PG1");
        pg1.setActive(true);
        familyDTO.getProgramCodes().add(pg1);

        ProgramCodeDTO pg2 = new ProgramCodeDTO();
        pg2.setId(2L);
        pg2.setProgramName("Program Name2");
        pg2.setProgramCode("PG2");
        pg2.setActive(true);
        familyDTO.getProgramCodes().add(pg2);
     }
    private List<StudyProtocolQueryDTO> createTrials() {
        List<StudyProtocolQueryDTO> trials = new ArrayList<StudyProtocolQueryDTO>();

        StudyProtocolQueryDTO trial = new StudyProtocolQueryDTO();
        trial.setStudyProtocolId(1L);
        trial.setNciIdentifier("1");
        trial.setNctIdentifier("2");
        trial.setOfficialTitle("title");
        trial.setPiFullName("Joel Joseph");
        ProgramCodeDTO pg1 = new ProgramCodeDTO();
        pg1.setProgramName("3");
        pg1.setId(3L);
        ProgramCodeDTO pg2 = new ProgramCodeDTO();
        pg2.setProgramName("4");
        pg2.setId(4L);
        trial.setProgramCodes(Arrays.asList(pg1, pg2));
        trial.setStudyStatusCode(StudyStatusCode.ACTIVE);
        trials.add(trial);

        return trials;
    }

}
