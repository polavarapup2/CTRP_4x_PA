package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

public class ResultsReportingCoverSheetActionTest  extends TrialAbstractActionTest {
  
    
    @Before
    public void setUp() throws PAException {
        
         super.setUp();
        
    }
    
    @Test
    public void testQuery() throws PAException {
        
        beforeQuery();
        String result = reportingCoverSheetAction.query();
        
        assertEquals("success",result);
        assert(reportingCoverSheetAction.getStudyRecordChangeList().size() ==0);
        
     
    }
    
   
    
    @Test
    public void testAddRecordChange() throws PAException, IOException {
      
        String result= reportingCoverSheetAction.addOrEditRecordChange();
        assertEquals(null,result);
        
    }
    
    @Test
    public void testEditRecordChange() throws PAException, IOException {
       
        reportingCoverSheetAction.setId(1L);
        String result= reportingCoverSheetAction.addOrEditRecordChange();
        assertEquals(null,result);
        
    }
    
   
    
    @Test
    public void testSuccessfulAddRecordChange() throws PAException, IOException {
        reportingCoverSheetAction.successfulAddRecordChange();
        
        assertEquals("Record Change has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testDelete() throws PAException {
        String[] objectsToDelete = new String[1];
        objectsToDelete[0] ="1";
        reportingCoverSheetAction.setObjectsToDelete(objectsToDelete);
        String result = reportingCoverSheetAction.delete();
        assertEquals("success",result);
        assertEquals(Constants.MULTI_DELETE_MESSAGE, ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSaveFinalChanges() throws PAException {
      
        List<String> designeeSelectedList = new ArrayList<String>();
        designeeSelectedList.add("1");
       reportingCoverSheetAction.setDesigneeAccessRevoked(false);
       reportingCoverSheetAction.setDesigneeAccessRevokedDate("09/03/2015");
       reportingCoverSheetAction.setChangesInCtrpCtGov(false);
       reportingCoverSheetAction.setChangesInCtrpCtGovDate("09/03/2015");
      String result =  reportingCoverSheetAction.saveFinalChanges();
      assertEquals("success",result);
      
    }
    
    @Test
    public void testCoverSheetEmail() throws Exception {
        reportingCoverSheetAction.prepare();
        reportingCoverSheetAction.sendConverSheetEmail();
    }
}
