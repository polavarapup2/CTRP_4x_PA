/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public final class ValidationError {

    private final String errorMessage;
    private final List<String> errorDetails = new ArrayList<String>();

    /**
     * @param errorMessage errorMessage
     */
    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorDetails
     */
    public List<String> getErrorDetails() {
        return errorDetails;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
