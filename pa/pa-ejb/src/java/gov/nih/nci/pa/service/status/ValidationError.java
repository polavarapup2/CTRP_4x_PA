package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.status.json.ErrorType;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class ValidationError {
    
    private ErrorType errorType;
    private String errorMessage;
    
    /**
     * Returns error type
     * @return ErrorType enum
     */
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * Set error type
     * @param errorType - ErrorType enum
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
    
    /**
     * Returns error message String
     * @return error message String
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Set error message string
     * @param errorMessage - String
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
}
