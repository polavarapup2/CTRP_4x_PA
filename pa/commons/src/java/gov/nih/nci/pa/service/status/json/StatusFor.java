package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StatusFor {

    /**
     * Indicates status rules are for TRIAL/SITE status transition
     */
    private TransitionFor type;
    /**
     * 
     */
    private Map<String, Status> statuses = new LinkedHashMap<String, Status>();
    
    /**
     * @return the type
     */
    public TransitionFor getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(TransitionFor type) {
        this.type = type;
    }
    /**
     * @return the statuses
     */
    public Map<String, Status> getStatuses() {
        return statuses;
    }
    /**
     * @param statuses the statuses to set
     */
    public void setStatuses(Map<String, Status> statuses) {
        this.statuses = statuses;
    }
}
