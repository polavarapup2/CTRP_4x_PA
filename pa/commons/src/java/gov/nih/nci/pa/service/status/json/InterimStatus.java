package gov.nih.nci.pa.service.status.json;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class InterimStatus {

    /**
     * Name of the interim status
     */
    private String name;
    
    /**
     * Order of the interim status
     */
    private int order;
    /**
     * Error type (if this interim status is missing)
     */
    private ErrorType errorType;
    /**
     * Error message template (if this interim status is missing)
     */
    private String errorMsgTemplate;
    
    
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
     * @return the order
     */
    public int getOrder() {
        return order;
    }
    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
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
