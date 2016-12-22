/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;


public class ResultsReportingDocumentActionTest extends AbstractPaActionTest{

    ResultsReportingDocumentAction reportingDocumentAction;
    TrialDocumentWebDTO trialDocumentWebDTO;
    StudyProtocolQueryDTO spDTO; 
    
    @Before
    public void setUp() throws PAException {
        reportingDocumentAction = new ResultsReportingDocumentAction();
        trialDocumentWebDTO = new TrialDocumentWebDTO();
        trialDocumentWebDTO.setTypeCode("");
        trialDocumentWebDTO.setFileName("");
        reportingDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
                
      
     
        
        MockCSMUserService mockCSMUserService = new MockCSMUserService();
        CSMUserService.setInstance(mockCSMUserService);
        
       
        PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
        reportingDocumentAction.setPaServiceUtil(paServiceUtils);
        reportingDocumentAction.setServletRequest(getRequest());
        DocumentDTO dto = new DocumentDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
      
        
        
       
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#query()}.
     * @throws PAException 
     */
    @Test
    public void testQuery() throws PAException {
       
    
       
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        DocumentServiceLocal docService = mock(DocumentServiceLocal.class);
        RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
    
      
        List<DocumentDTO> isoList = new ArrayList<DocumentDTO>();
        DocumentDTO dto = new DocumentDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.AFTER_RESULTS));
        dto.setFileName(StConverter.convertToSt("FileName"));
        dto.setDateLastCreated(TsConverter.convertToTs(new Date()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        dto.setUserLastUpdated(StConverter.convertToSt("User"));
        isoList.add(dto);
       
        when(docService.getReportsDocumentsByStudyProtocol(IiConverter.convertToStudyProtocolIi(1L))).thenReturn(isoList);
        when(paRegSvcLoc.getDocumentService()).thenReturn(docService);
        when(paRegSvcLoc.getRegistryUserService()).thenReturn(registryUserServiceLocal);
        
      
        reportingDocumentAction.setStudyProtocolId(1L);
        
        
        
        String result = reportingDocumentAction.query();
        assertEquals("success",result);
        assertEquals(null, ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
        reportingDocumentAction.setStudyProtocolId(null);
        
        result = reportingDocumentAction.query();
        assertEquals("success",result);
        
        
        
        
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#create()}.
     * @throws Exception 
     */
    @Test
    public void testCreate() throws Exception {
       String result = reportingDocumentAction.create();
       assertEquals("errorDocument",result);
       assertTrue(reportingDocumentAction.hasFieldErrors());
       
       Map<String, List<String>> errors = reportingDocumentAction.getFieldErrors();
       assertTrue(errors.keySet().size() > 0);
       assertTrue(errors.keySet().contains("trialDocumentWebDTO.typeCode"));
       
       
       trialDocumentWebDTO.setTypeCode("After Results");
       reportingDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
       reportingDocumentAction.setUploadFileName("FileName");
       reportingDocumentAction.clearFieldErrors();
       reportingDocumentAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
       result = reportingDocumentAction.create();
       assertEquals("success",result);
       assertTrue(ServletActionContext.getRequest().getAttribute(
               Constants.SUCCESS_MESSAGE)!=null);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#saveFile()}.
     */
    @Test
    public void testSaveFile() {
        
        String result = reportingDocumentAction.saveFile();
        assertEquals("errorDocument",result);

    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#edit()}.
     */
    @Test
    public void testEdit() {
        String result = reportingDocumentAction.edit();
        assertEquals("input",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#update()}.
     * @throws Exception 
     */
    @Test
    public void testUpdate() throws Exception {
        String result = reportingDocumentAction.update();
        assertEquals("errorDocument",result);
        assertTrue(reportingDocumentAction.hasFieldErrors());
        Map<String, List<String>> errors = reportingDocumentAction.getFieldErrors();
        assertTrue(errors.keySet().size() > 0);
        assertTrue(errors.keySet().contains("trialDocumentWebDTO.typeCode"));
      
        
        trialDocumentWebDTO.setTypeCode("After Results");
        reportingDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
        reportingDocumentAction.setUploadFileName("FileName");
        reportingDocumentAction.clearFieldErrors();
        reportingDocumentAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
        result = reportingDocumentAction.update();
        assertEquals("success",result);
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#delete()}.
     */
    @Test
    public void testDelete() {
        
     
        
        reportingDocumentAction.clearErrorsAndMessages();
        
        reportingDocumentAction.setObjectsToDelete(new String[] {"1"});
        String result = reportingDocumentAction.delete();
        assertEquals("success",result);
        
      
      
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testReviewCtro() {
        
       
        trialDocumentWebDTO.setCtroUserId(1L);
        trialDocumentWebDTO.setCtroUserReviewDateTime(new Date());
        reportingDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
      
        String result = reportingDocumentAction.reviewCtro();
        assertEquals("success",result);
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testReviewCcct() {
        
       
        trialDocumentWebDTO.setCcctUserId(1L);
        trialDocumentWebDTO.setCtroUserReviewDateTime(new Date());
        reportingDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
      
        String result = reportingDocumentAction.reviewCcct();
        assertEquals("success",result);
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testInput() {
        String result = reportingDocumentAction.input();
        assertEquals("input",result);
    }

}
