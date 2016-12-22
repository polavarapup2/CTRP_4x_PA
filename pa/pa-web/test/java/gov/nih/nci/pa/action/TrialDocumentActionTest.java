/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialDocumentActionTest extends AbstractPaActionTest{

    TrialDocumentAction trialDocumentAction;
    TrialDocumentWebDTO trialDocumentWebDTO;
    StudyProtocolQueryDTO spDTO; 
    
    @Before
    public void setUp() throws PAException {
        trialDocumentAction = new TrialDocumentAction();
        trialDocumentWebDTO = new TrialDocumentWebDTO();
        trialDocumentWebDTO.setTypeCode("");
        trialDocumentWebDTO.setFileName("");
        trialDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
                
        spDTO = new StudyProtocolQueryDTO();
        spDTO.setStudyProtocolType("NonInterventionalStudyProtocol");
        spDTO.setNciIdentifier("nci");
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.TRIAL_SUMMARY, spDTO);
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#query()}.
     * @throws PAException 
     */
    @Test
    public void testQuery() throws PAException {
        String result = trialDocumentAction.query();
        assertEquals("success",result);
        assertEquals("error.trialDocument.noRecords", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        DocumentServiceLocal docService = mock(DocumentServiceLocal.class);
        when(PaRegistry.getDocumentService()).thenReturn(docService);
        List<DocumentDTO> isoList = new ArrayList<DocumentDTO>();
        DocumentDTO dto = new DocumentDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        dto.setFileName(StConverter.convertToSt("FileName"));
        dto.setDateLastCreated(TsConverter.convertToTs(new Date()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        dto.setUserLastUpdated(StConverter.convertToSt("User"));
        isoList.add(dto);
        when(docService.getDocumentsAndAllTSRByStudyProtocol(IiConverter.convertToIi(1L))).thenReturn(isoList);
        result = trialDocumentAction.query();
        assertEquals("error",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#create()}.
     * @throws Exception 
     */
    @Test
    public void testCreate() throws Exception {
       String result = trialDocumentAction.create();
       assertEquals("input",result);
       assertTrue(trialDocumentAction.hasFieldErrors());
       
       trialDocumentWebDTO.setTypeCode("TypeCode");
       trialDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
       trialDocumentAction.setUploadFileName("FileName");
       trialDocumentAction.clearFieldErrors();
       trialDocumentAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
       result = trialDocumentAction.create();
       assertEquals("success",result);
       assertTrue(ServletActionContext.getRequest().getAttribute(
               Constants.SUCCESS_MESSAGE)!=null);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#saveFile()}.
     */
    @Test
    public void testSaveFile() {
        
        String result = trialDocumentAction.saveFile();
        assertEquals("error",result);

    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#edit()}.
     */
    @Test
    public void testEdit() {
        String result = trialDocumentAction.edit();
        assertEquals("input",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#update()}.
     * @throws Exception 
     */
    @Test
    public void testUpdate() throws Exception {
        String result = trialDocumentAction.update();
        assertEquals("input",result);
        assertTrue(trialDocumentAction.hasFieldErrors());
        
        trialDocumentWebDTO.setTypeCode("TypeCode");
        trialDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
        trialDocumentAction.setUploadFileName("FileName");
        trialDocumentAction.clearFieldErrors();
        trialDocumentAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
        result = trialDocumentAction.update();
        assertEquals("success",result);
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#delete()}.
     */
    @Test
    public void testDelete() {
        String result = trialDocumentAction.delete();
        assertEquals("success",result);
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.FAILURE_MESSAGE)!=null);
        
        trialDocumentAction.clearErrorsAndMessages();
        
        trialDocumentAction.setObjectsToDelete(new String[] {"1"});
        result = trialDocumentAction.delete();
        assertEquals("delete",result);
        
        trialDocumentAction.clearErrorsAndMessages();
        
        trialDocumentAction.setObjectsToDelete(new String[] {"1"});
        trialDocumentAction.getTrialDocumentWebDTO().setInactiveCommentText("test");
        result = trialDocumentAction.delete();
        assertEquals("success",result);        
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    @Test
    public void testInput() {
        String result = trialDocumentAction.input();
        assertEquals("input",result);
    }

}
