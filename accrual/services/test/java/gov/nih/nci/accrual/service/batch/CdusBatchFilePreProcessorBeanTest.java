/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class CdusBatchFilePreProcessorBeanTest extends
        DuplicateSubjectLinePreprocessorTest {

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.processor = new CdusBatchFilePreProcessorBean();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testEmptyFile()
     */
    @Override
    @Test
    public void testEmptyFile() throws IOException {
        // TODO Auto-generated method stub
        super.testEmptyFile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testJunkFile()
     */
    @Override
    @Test
    public void testJunkFile() throws IOException {
        // TODO Auto-generated method stub
        super.testJunkFile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testMalformedCdusRemainsUnchanged()
     */
    @Override
    @Test
    public void testMalformedCdusRemainsUnchanged() throws IOException {
        // TODO Auto-generated method stub
        super.testMalformedCdusRemainsUnchanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testOnlyNonEmptySubjectInfoProcessed()
     */
    @Override
    @Test
    public void testOnlyNonEmptySubjectInfoProcessed() throws IOException {
        // TODO Auto-generated method stub
        super.testOnlyNonEmptySubjectInfoProcessed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testDupeRemoval()
     */
    @Override
    @Test
    public void testDupeRemoval() throws IOException {
        // TODO Auto-generated method stub
        super.testDupeRemoval();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessorTest
     * #testLargeShuffledListWithManyDupes()
     */
    @Override
    @Test
    public void testLargeShuffledListWithManyDupes() throws IOException {
        // TODO Auto-generated method stub
        super.testLargeShuffledListWithManyDupes();
    }

}
