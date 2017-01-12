/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class BatchUploadActionTest extends AbstractRegWebTest {
    private BatchUploadAction action;
    private static final String FILE_NAME = "batchUploadTest.xls";

    @Test
    public void testTrialDataProperty() {
        action = new BatchUploadAction();
        assertNull(action.getTrialData());
        action.setTrialData(new File(FILE_NAME));
        assertNotNull(action.getTrialData());
    }

    @Test
    public void testTrialDataFileNameProperty() {
        action = new BatchUploadAction();
        assertNull(action.getTrialDataFileName());
        action.setTrialDataFileName("trialDataFileName");
        assertNotNull(action.getTrialDataFileName());
    }

    @Test
    public void testTrialDataContentTypeProperty() {
        action = new BatchUploadAction();
        assertNull(action.getTrialDataContentType());
        action.setTrialDataContentType("trialDataContentType");
        assertNotNull(action.getTrialDataContentType());
    }

    @Test
    public void testDocZipProperty() {
        action = new BatchUploadAction();
        assertNull(action.getDocZip());
        action.setDocZip(new File(FILE_NAME));
        assertNotNull(action.getDocZip());
    }

    @Test
    public void testDocZipFileName() {
        action = new BatchUploadAction();
        assertNull(action.getDocZipFileName());
        action.setDocZipFileName("docZipFileName");
        assertNotNull(action.getDocZipFileName());
    }

    @Test
    public void testOrgName() {
        action = new BatchUploadAction();
        assertNull(action.getOrgName());
        action.setOrgName("orgName");
        assertNotNull(action.getOrgName());
    }

    @Test
    public void testServletResponseProperty() {
        action = new BatchUploadAction();
        assertNull(action.getServletResponse());
        action.setServletResponse(null);
        assertNull(action.getServletResponse());
    }

    @Test
    public void testPageProperty() {
        action = new BatchUploadAction();
        assertNull(action.getPage());
        action.setPage("page");
        assertNotNull(action.getPage());
    }

    @Test
    public void testExecute() {
        assertEquals("success", new BatchUploadAction().execute());
    }

    @Test
    public void testProcessWithValidationErr() {
        action = new BatchUploadAction();
        assertEquals("error", action.process());
    }

    @Test
    public void testProcessWithInValidDocType() throws URISyntaxException, PAException {
        action = new BatchUploadAction();
        action.setOrgName("orgName");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        File f = new File(fileUrl.toURI());

        action.setTrialData(f);
        action.setTrialDataFileName("test.txt");
        fileUrl = ClassLoader.getSystemClassLoader().getResource("test.txt");
        f = new File(fileUrl.toURI());
        action.setDocZip(f);
        action.setDocZipFileName("test.txt");
        assertEquals("error", action.process());
        assertTrue(action.getFieldErrors().containsKey("docZipFileName"));
        assertTrue(action.getFieldErrors().containsKey("trialDataFileName"));
    }

    @Test
    @Ignore
    public void testProcessWithNoValidationErr() throws URISyntaxException, PAException {
        action = new BatchUploadAction();
        action.setOrgName("orgName");
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        action.setTrialData(f);
        action.setTrialDataFileName(FILE_NAME);
        fileUrl = ClassLoader.getSystemClassLoader().getResource("7000DDoc.zip");
        f = new File(fileUrl.toURI());
        action.setDocZip(f);
        action.setDocZipFileName("7000DDoc.zip");
        assertEquals("batch_confirm", action.process());
        deleteCreatedFolder();
    }

}
