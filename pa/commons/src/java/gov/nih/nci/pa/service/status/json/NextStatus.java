package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class NextStatus {
    
    /**
     * Name of the transition to status
     */
    private String name;
    /**
     * flag indicating is this transition is allowed
     */
    private boolean validTransition;
    /**
     * Error type (if not valid transition)
     */
    private ErrorType errorType;
    /**
     * Error message template (if not valid transition)
     */
    private String errorMsgTemplate;
    
    /**
     * Provides validation rules for same day transition
     */
    private SameDateValidation sameDateValidation;
    /**
     * List of interim statuses that must be present between this transition
     */
    private Map<String, InterimStatus> interimStatuses = new LinkedHashMap<String, InterimStatus>();
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the validTransition
     */
    public boolean isValidTransition() {
        return validTransition;
    }
    /**
     * @param validTransition the validTransition to set
     */
    public void setValidTransition(boolean validTransition) {
        this.validTransition = validTransition;
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
    /**
     * @return the sameDateValidation
     */
    public SameDateValidation getSameDateValidation() {
        return sameDateValidation;
    }
    /**
     * @param sameDateValidation the sameDateValidation to set
     */
    public void setSameDateValidation(SameDateValidation sameDateValidation) {
        this.sameDateValidation = sameDateValidation;
    }
    /**
     * @return the interimStatuses
     */
    public Map<String, InterimStatus> getInterimStatuses() {
        return interimStatuses;
    }
    /**
     * @param interimStatuses the interimStatuses to set
     */
    public void setInterimStatuses(Map<String, InterimStatus> interimStatuses) {
        this.interimStatuses = interimStatuses;
    }

    
}
