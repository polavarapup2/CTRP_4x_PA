package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
    
public class TrialViewActionTest extends TrialAbstractActionTest {

    @Before
    public void setUp() throws PAException {
        
         super.setUp();
        
    }
    
    @Test
    public void testQuery() throws PAException {
        
        beforeQuery();
        String result = trialViewAction.query();
        
        assertEquals("success",result);
        assert(trialViewAction.getStudyRecordChangeList().size() ==0);
        
     
    }

    
  
    
    @Test
    public void testAddRecordChange() throws PAException, IOException {
      
        String result= trialViewAction.addOrEditRecordChange();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyRecordChangeList());
        
    }
    
    @Test
    public void testEditRecordChange() throws PAException, IOException {
       
        trialViewAction.setId(1L);
        String result= trialViewAction.addOrEditRecordChange();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyRecordChangeList());
        
    }
    
    @Test
    public void testSuccessfulAdd() throws PAException, IOException {
        trialViewAction.successfulAdd();
        
        assertEquals("Data Discrepancy has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSuccessfulAddRecordChange() throws PAException, IOException {
        trialViewAction.successfulAddRecordChange();
        
        assertEquals("Record Change has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testDeleteDoc() throws PAException {
        String[] objectsToDelete = new String[1];
        objectsToDelete[0] ="1";
        trialViewAction.setObjectsToDelete(objectsToDelete);
        String result = trialViewAction.delete();
        assertEquals("success",result);
        assertEquals(Constants.MULTI_DELETE_MESSAGE, ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSaveFinalChanges() throws PAException {
      
        List<String> designeeSelectedList = new ArrayList<String>();
        designeeSelectedList.add("1");
        trialViewAction.setDesigneeSelectedList(designeeSelectedList);
        trialViewAction.setDesigneeAccessRevoked(false);
        trialViewAction.setDesigneeAccessRevokedDate("09/03/2015");
        trialViewAction.setChangesInCtrpCtGov(false);
        trialViewAction.setChangesInCtrpCtGovDate("09/03/2015");
      String result =  trialViewAction.saveFinalChanges();
      assertEquals("success",result);
      
    }
    
    @Test
    public void testCreate() throws Exception {
       String result = trialViewAction.create();
       assertEquals("errorDocument",result);
       assertTrue(trialViewAction.hasFieldErrors());
       
       Map<String, List<String>> errors = trialViewAction.getFieldErrors();
       assertTrue(errors.keySet().size() > 0);
       assertTrue(errors.keySet().contains("trialDocumentWebDTO.typeCode"));
       
       
       trialDocumentWebDTO.setTypeCode("Comparison");
       trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
       trialViewAction.setUploadFileName("FileName");
       trialViewAction.clearFieldErrors();
       trialViewAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
       result = trialViewAction.create();
       assertEquals("success",result);
       assertTrue(ServletActionContext.getRequest().getAttribute(
               Constants.SUCCESS_MESSAGE)!=null);
    }

    
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#update()}.
     * @throws Exception 
     */
    @Test
    public void testUpdate() throws Exception {
        String result = trialViewAction.update();
        assertEquals("errorDocument",result);
        assertTrue(trialViewAction.hasFieldErrors());
        Map<String, List<String>> errors = trialViewAction.getFieldErrors();
        assertTrue(errors.keySet().size() > 0);
        assertTrue(errors.keySet().contains("trialDocumentWebDTO.typeCode"));
      
        
        trialDocumentWebDTO.setTypeCode("After Results");
        trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
        trialViewAction.setUploadFileName("FileName");
        trialViewAction.clearFieldErrors();
        trialViewAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
        result = trialViewAction.update();
        assertEquals("success",result);
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
    }
    
    @Test
    public void testUpdateCompare() throws Exception {
        String result = trialViewAction.update();
        assertEquals("errorDocument",result);
        assertTrue(trialViewAction.hasFieldErrors());
        Map<String, List<String>> errors = trialViewAction.getFieldErrors();
        assertTrue(errors.keySet().size() > 0);
        assertTrue(errors.keySet().contains("trialDocumentWebDTO.typeCode"));
      
        
        trialDocumentWebDTO.setTypeCode("Comparison");
        trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
        trialViewAction.setUploadFileName("FileName");
        trialViewAction.clearFieldErrors();
        trialViewAction.setUpload(new File(this.getClass().getResource("/test.properties").toURI()));
        result = trialViewAction.update();
        assertEquals("success",result);
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#delete()}.
     * @throws PAException 
     */
    @Test
    public void testDelete() throws PAException {
        
     
        
        trialViewAction.clearErrorsAndMessages();
        
        trialViewAction.setObjectsToDelete(new String[] {"1"});
        String result = trialViewAction.delete();
        assertEquals("success",result);
        
      
      
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testReviewCtro() {
        
       
        trialDocumentWebDTO.setCtroUserId(1L);
        trialDocumentWebDTO.setCtroUserReviewDateTime(new Date());
        trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
      
        String result = trialViewAction.reviewCtro();
        assertEquals("success",result);
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testReviewCcct() {
        
       
        trialDocumentWebDTO.setCcctUserId(1L);
        trialDocumentWebDTO.setCtroUserReviewDateTime(new Date());
        trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
      
        String result = trialViewAction.reviewCcct();
        assertEquals("success",result);
        
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE)!=null);
        
    }
    
    @Test
    public void testCoverSheetEmail() throws PAException {
        trialViewAction.sendConverSheetEmail();
    }
    
    
    
}
