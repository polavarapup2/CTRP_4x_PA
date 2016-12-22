package gov.nih.nci.pa.service.status.json;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class SameDateValidation {
    
    /**
     * Flag for indicating this transition can occur on the same day or not
     */
    private boolean canOccurOnSameDateAsPreviousState;
    /**
     * Error type (if same day transition is not allowed)
     */
    private ErrorType errorType;
    /**
     * Error message template (if same day transition is not allowed)
     */
    private String errorMsgTemplate;
    
    /**
     * @return the canOccurOnSameDateAsPreviousState
     */
    public boolean isCanOccurOnSameDateAsPreviousState() {
        return canOccurOnSameDateAsPreviousState;
    }
    /**
     * @param canOccurOnSameDateAsPreviousState the canOccurOnSameDateAsPreviousState to set
     */
    public void setCanOccurOnSameDateAsPreviousState(
            boolean canOccurOnSameDateAsPreviousState) {
        this.canOccurOnSameDateAsPreviousState = canOccurOnSameDateAsPreviousState;
    }
    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }
    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
    /**
     * @return the errorMsgTemplate
     */
    public String getErrorMsgTemplate() {
        return errorMsgTemplate;
    }
    /**
     * @param errorMsgTemplate the errorMsgTemplate to set
     */
    public void setErrorMsgTemplate(String errorMsgTemplate) {
        this.errorMsgTemplate = errorMsgTemplate;
    }
    
    
}
