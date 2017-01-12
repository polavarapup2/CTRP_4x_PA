/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class DuplicateSubjectLinePreprocessorTest {

    protected CdusBatchFilePreProcessor processor;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        processor = new DuplicateSubjectLinePreprocessor();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessor#preprocess(java.io.File)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public void testEmptyFile() throws IOException {
        File file = createTempFile();
        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());

    }

    @Test
    public void testJunkFile() throws IOException {
        File file = createTempFile();

        byte[] data = new byte[(int) FileUtils.ONE_MB];
        new Random().nextBytes(data);
        FileUtils.writeByteArrayToFile(file, data);

        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());

    }

    @Test
    public void testMalformedCdusRemainsUnchanged() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS,NCI-2014-00416,,,,,,,,,",
                                "PATIENTS,NcI-2014-00416,A0009,",
                                "PATIENTS,nCI-2014-00416,a0009,33908,,195306,Female,Not Hispanic or Latino,,20110930,,z1081,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,B0001,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1081,,,,,,,,,,10029462,,",
                                "PATIENTT_RACES,NCI-2014-00416,00009,White",
                                "PATIENT_RACES,NCI-2014-00416,00009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());
    }

    @Test
    public void testOnlyNonEmptySubjectInfoProcessed() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "COLLECTIONS,NCI-2014-00416,,,,,,,,,",
                                "PATIENTS,,A0009,77058,,193106,Female,Not Hispanic or Latino,,20110513,,Z1081,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,A0009,33908,,195306,Female,Not Hispanic or Latino,,20110930,,,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,A0009,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1081,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-00416,00009,White",
                                "PATIENT_RACES,NCI-2014-00416,00009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDupeRemoval() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-00416,,,,,,,,,",
                                "PATIENTS,NcI-2014-00416,A0009,77058,,193106,Female,Not Hispanic or Latino,,20110513,, Z1081 ,,,,,,,,,,10029462,,",
                                "\uFEFFPATIENTS, nCI-2014-00416, a0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,z1081,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,B0001,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1081,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,C0001,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1082,,,,,,,,,,10029462,,",
                                "PATIENTS,NCI-2014-00416,C0001,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1082,,,,,,,,,,10029462,,",
                                "", "PATIENT_RACES,NCI-2014-00416,00009,White",
                                "PATIENT_RACES,NCI-2014-00416,00009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        assertFalse(file.equals(result.getPreprocessedFile()));
        assertFalse(result.getValidationErrors().isEmpty());

        File filtered = result.getPreprocessedFile();
        filtered.deleteOnExit();

        List<String> lines = FileUtils.readLines(filtered, "UTF-8");
        assertEquals("\uFEFFCOLLECTIONS,NCI-2014-00416,,,,,,,,,", lines.get(0));
        assertEquals("", lines.get(1));
        assertEquals("", lines.get(2));
        assertEquals(
                "PATIENTS,NCI-2014-00416,B0001,33908,,195306,Female,Not Hispanic or Latino,,20110930,,Z1081,,,,,,,,,,10029462,,",
                lines.get(3));
        assertEquals("", lines.get(4));
        assertEquals("", lines.get(5));
        assertEquals("", lines.get(6));
        assertEquals("PATIENT_RACES,NCI-2014-00416,00009,White", lines.get(7));
        assertEquals("PATIENT_RACES,NCI-2014-00416,00009,Black", lines.get(8));

        assertEquals(1, result.getValidationErrors().size());
        ValidationError error = result.getValidationErrors().get(0);
        assertEquals(
                "The following lines contain duplicate subject data and were not processed into the system. "
                        + "Please remove the duplicate lines and resubmit the file:",
                error.getErrorMessage());
        assertEquals("Line 2, Site ID: Z1081, Subject ID: A0009", error
                .getErrorDetails().get(0));
        assertEquals("Line 3, Site ID: Z1081, Subject ID: A0009", error
                .getErrorDetails().get(1));
        assertEquals("Line 5, Site ID: Z1082, Subject ID: C0001", error
                .getErrorDetails().get(2));
        assertEquals("Line 6, Site ID: Z1082, Subject ID: C0001", error
                .getErrorDetails().get(3));

    }

    @Test
    public void testLargeShuffledListWithManyDupes() throws IOException {
        File file = createTempFile();

        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                lines.add(String
                        .format("PATIENTS,NCI-2014-00416,10%s,77058,,193106,Female,Not Hispanic or Latino,,20110513,,MI%s,,,,,,,,,,10029462,,",
                                i, i));
            }
        }
        Collections.shuffle(lines);
        lines.add(0, "COLLECTIONS,NCI-2014-00416,,,,,,,,,");
        FileUtils.writeLines(file, "UTF-8", lines);

        PreprocessingResult result = processor.preprocess(file);
        assertFalse(file.equals(result.getPreprocessedFile()));
        assertFalse(result.getValidationErrors().isEmpty());

        File filtered = result.getPreprocessedFile();
        filtered.deleteOnExit();

        lines = FileUtils.readLines(filtered, "UTF-8");
        assertEquals("COLLECTIONS,NCI-2014-00416,,,,,,,,,", lines.get(0));
        for (int i = 0; i < 1000; i++) {
            assertEquals("", lines.get(i + 1));
        }

        assertEquals(1, result.getValidationErrors().size());
        assertEquals(1000, result.getValidationErrors().get(0)
                .getErrorDetails().size());

    }

    /**
     * @return
     * @throws IOException
     */
    private File createTempFile() throws IOException {
        File file = File.createTempFile(UUID.randomUUID().toString(), "txt");
        file.deleteOnExit();
        return file;
    }

}
