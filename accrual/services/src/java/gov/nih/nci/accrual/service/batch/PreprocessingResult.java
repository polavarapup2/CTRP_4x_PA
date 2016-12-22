/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public final class PreprocessingResult {

    private final File preprocessedFile;
    private final List<ValidationError> validationErrors;

    /**
     * @param preprocessedFile File
     * @param validationErrors List<ValidationError>
     */
    public PreprocessingResult(File preprocessedFile,
            List<ValidationError> validationErrors) {
        this.preprocessedFile = preprocessedFile;
        this.validationErrors = validationErrors;
    }

    /**
     * @return the preprocessedFile
     */
    public File getPreprocessedFile() {
        return preprocessedFile;
    }

    /**
     * @return the validationErrors
     */
    public List<ValidationError> getValidationErrors() {
        return Collections.unmodifiableList(validationErrors);
    }

}
