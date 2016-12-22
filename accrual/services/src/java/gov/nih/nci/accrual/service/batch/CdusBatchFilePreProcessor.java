/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import java.io.File;
import java.io.IOException;

/**
 * @author dkrylov
 * 
 */
public interface CdusBatchFilePreProcessor {
    /**
     * Pre-process the given CDUS file.
     * 
     * @param cdusFile
     *            File
     * @return PreprocessingResult PreprocessingResult
     * @throws IOException
     *             IOException
     */
    PreprocessingResult preprocess(File cdusFile) throws IOException;
}
