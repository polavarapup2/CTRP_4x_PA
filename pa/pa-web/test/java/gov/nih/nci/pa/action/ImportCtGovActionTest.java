package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class ImportCtGovActionTest {
     private ImportCtGovAction action;
     private CTGovSyncServiceLocal ctGovSyncService;
     private StudyProtocolService studyProtocolService;
     private ProtocolQueryServiceLocal protocolQueryService;
     
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
         action.setCtGovSyncService(ctGovSyncService);
         action.setStudyProtocolService(studyProtocolService);
         action.setProtocolQueryService(protocolQueryService);
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
}
