package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DocumentTypeCodeTest {
    
    
    /**
     * test the getDisplayNames method.
     */
    @Test
    public void testReportsTypeExcluded() {
        String[] result = DocumentTypeCode.getDisplayNames();
        assertNotNull("No result returned", result);
        assertEquals(result.length ,8);
        
        List<String> namesList = new ArrayList<String>();
        namesList = Arrays.asList(result);
        
        
        assert(!namesList.contains("Before results"));
     
    }
    
    /**
     * test the getDisplayNames method.
     */
    @Test
    public void tesgetTypeCodesTest() {
        String[] result = DocumentTypeCode.getDocTypeCodes();
        assertNotNull("No result returned", result);
        assertEquals(result.length ,7);
        
      
     
    }
    
    /**
     * test the getTrialReportingDisplayNames method.
     */
    @Test
    public void testReportsTypeNotExcluded() {
        String[] result = DocumentTypeCode.getTrialReportingDisplayNames();
        assertNotNull("No result returned", result);
        assertEquals(result.length ,3);
        
        List<String> namesList = new ArrayList<String>();
        namesList = Arrays.asList(result);
        
        
        assert(namesList.contains("Before results"));
        
      
     
    }
    

}
