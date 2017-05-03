package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.CTGovImportMergeHelper;
import gov.nih.nci.pa.util.Constants;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class ImportCtGovActionTest extends AbstractPaActionTest {
     private ImportCtGovAction action;
     private CTGovSyncServiceLocal ctGovSyncService;
     private StudyProtocolService studyProtocolService;
     private ProtocolQueryServiceLocal protocolQueryService;
     private RegistryUserServiceLocal registryUserService;
     
     /**
      * Initialization method.
      * @throws PAException if an error occurs
      */
     @Before
     public void setUp() throws PAException {
         action = new ImportCtGovAction();
         ctGovSyncService = mock(CTGovSyncServiceLocal.class);
         studyProtocolService = mock(StudyProtocolService.class);
         protocolQueryService = mock(ProtocolQueryServiceLocal.class);
         registryUserService = mock(RegistryUserServiceLocal.class);
         action.setCtGovSyncService(ctGovSyncService);
         action.setStudyProtocolService(studyProtocolService);
         action.setProtocolQueryService(protocolQueryService);
         action.setRegistryUserService(registryUserService);
     }
     
     @Test
     public void testExecute() {
         assertEquals("success", action.execute());
     }
     
     @Test    
     public void testQueryError() {
         assertEquals("error", action.query());  
         assertTrue(action.getActionErrors().size() > 0);
         assertTrue(action.getActionErrors().contains
                ("Please provide an ClinicalTrials.gov Identifier value"));
     }
     
     @Test    
     public void testQuery() {
         action.setNctID("NCT12345");
         assertEquals("success", action.query());  
     }
     
     @Test    
     public void testStudyExistsQuery() throws PAException {
         action.setNctID("NCT12345");
         ClinicalStudy study = new ClinicalStudy();
         CTGovStudyAdapter studyAdapter = new CTGovStudyAdapter(study);
         when(ctGovSyncService.getAdaptedCtGovStudyByNctId(action.getEscapedNctID())).thenReturn(studyAdapter);
         List<StudyProtocolDTO> studyProtocolDtos = new ArrayList<StudyProtocolDTO>();
         StudyProtocolDTO dto = new StudyProtocolDTO();
         studyProtocolDtos.add(dto);
         when(studyProtocolService.getStudyProtocolsByNctId(action.getNctID())).thenReturn(studyProtocolDtos);
         assertEquals("success", action.query());  
     }
     
     @Test
     public void testNewTrialImport() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(false);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);
         TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
         dto.setPaTrialID("1");
         dto.setNciTrialID("NCI-2017-1234");
         
         when(helper.insertNctId(any(String.class), any(String.class))).thenReturn(dto);
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.SUCCESS_MESSAGE);
         String expected = "importctgov.import.new.success";
         assertTrue(StringUtils.equals(msg, expected));
     }
     
     @Test
     public void testNoNewTrialImport() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(false);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);
         
         when(helper.insertNctId(any(String.class), any(String.class))).thenReturn(null);
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.FAILURE_MESSAGE);
         assertTrue(StringUtils.isNotBlank(msg));
     }
     
     @Test
     public void testUpdateTrialImport() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(true);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);
         TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
         dto.setPaTrialID("1");
         dto.setNciTrialID("NCI-2017-1234");
         List<TrialRegistrationConfirmationDTO> list = new ArrayList<TrialRegistrationConfirmationDTO>();
         list.add(dto);
         
         when(helper.updateNctId(any(String.class), any(String.class))).thenReturn(list);
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.SUCCESS_MESSAGE);
         String expected = "importctgov.import.update.success";
         assertTrue(StringUtils.equals(msg, expected));
     }
     
     @Test
     public void testUpdateMultipleTrialImport() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(true);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);
         List<TrialRegistrationConfirmationDTO> list = new ArrayList<TrialRegistrationConfirmationDTO>();
         TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
         dto.setPaTrialID("1");
         dto.setNciTrialID("NCI-2017-1234");
         TrialRegistrationConfirmationDTO dto1 = new TrialRegistrationConfirmationDTO();
         dto1.setPaTrialID("2");
         dto1.setNciTrialID("NCI-2017-2234");
         list.add(dto);
         list.add(dto1);
         
         when(helper.updateNctId(any(String.class), any(String.class))).thenReturn(list);
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.SUCCESS_MESSAGE);
         String expected = "importctgov.import.update.success";
         assertTrue(StringUtils.equals(msg, expected));
     }
     
     @Test
     public void testNoUpdateTrialImport() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(true);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);

         when(helper.updateNctId(any(String.class), any(String.class))).thenReturn(null);
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.FAILURE_MESSAGE);
         assertTrue(StringUtils.isNotBlank(msg));
     }
     
     @Test
     public void testImportTrialException() throws PAException {
         action.setNctIdToImport("NCT12345678");
         action.setStudyExists(true);
         CTGovImportMergeHelper helper = mock(CTGovImportMergeHelper.class);
         action.setHelper(helper);
         TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
         dto.setPaTrialID("1");
         dto.setNciTrialID("NCI-2017-1234");
         List<TrialRegistrationConfirmationDTO> list = new ArrayList<TrialRegistrationConfirmationDTO>();
         list.add(dto);
         
         when(helper.updateNctId(any(String.class), any(String.class))).thenThrow(new PAException("Error in call to import trial."));
         action.importTrial();
         String msg = (String) getRequest().getAttribute(Constants.FAILURE_MESSAGE);
         assertTrue(StringUtils.isNotBlank(msg));
     }
}
