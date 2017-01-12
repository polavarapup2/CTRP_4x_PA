/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class BatchHelperTest {
    private BatchHelper helper;
    @Test
    public void testProperty() {
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName","", null);
        assertNotNull(helper.getTrialDataFileName());
        assertNotNull(helper.getUploadLoc());
        assertNotNull(helper.getUnzipLoc());
        assertNotNull(helper.getUserName());
        helper.setTrialDataFileName(null);
        helper.setUploadLoc(null);
        helper.setUnzipLoc(null);
        helper.setUserName(null);
        assertNull(helper.getTrialDataFileName());
        assertNull(helper.getUploadLoc());
        assertNull(helper.getUnzipLoc());
        assertNull(helper.getUserName());
    }
    @Test
    public void testProcessExcel() {
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName","", null);
        try {
            helper.processExcel("fileName");
        } catch (Exception e) {
         fail();
        }
        try {
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName","ctep", null);
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
        File f = new File(fileUrl.toURI());
        assertNotNull(helper.processExcel(f.getAbsolutePath()));
        helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName","DCP", null);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
        f = new File(fileUrl.toURI());
        assertNotNull(helper.processExcel(f.getAbsolutePath()));
        } catch (Exception e) {

        }
    }

    @Test
    public void testRun() {
        helper = new BatchHelper("uploadLoc", "dataFileName", "unzipLoc", "userName", "", null);
        try {
            helper.run();
        } catch (Exception e) {

        }
    }
    
    
    @Test 
    public void getWarningTypeWithNCIValuesTest() {
    	helper = new BatchHelper("uploadLoc","dataFileName","unzipLoc","userName","", null);
    	String warning = "CreateWarning:NCI-2015-00385 CreateWarning:NCI-2015-00388";
    	Map<String, String> map =helper.getWarningTypeWithNCIValues(warning);
    	map.size();
    	
    	warning = "CreateWarning";
    	 map =helper.getWarningTypeWithNCIValues(warning);
    	map.size();
    }

}
