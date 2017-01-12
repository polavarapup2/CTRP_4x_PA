package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class Status {
    
    /**
     * Order of the status
     */
    private int order;
    /**
     * Status code 
     */
    private String code;
    /**
     * Status displayName
     */
    private String displayName;
    
    /**
     * List of transitions allowed from this status
     */
    private Map<String, NextStatus> transitions = new LinkedHashMap<String, NextStatus>();

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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the transitions
     */
    public Map<String, NextStatus> getTransitions() {
        return transitions;
    }

    /**
     * @param transitions the transitions to set
     */
    public void setTransitions(Map<String, NextStatus> transitions) {
        this.transitions = transitions;
    }

    
}
