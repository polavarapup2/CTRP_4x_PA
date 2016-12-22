package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class App {
    
    /**
     * App name for which the following tree rules apply
     */
    private AppName appName;
    /**
     * Status rules under this app as per trial type ABBREVIATED/COMPLETE
     */
    private Map<TrialType, Trial> statusRules = new LinkedHashMap<TrialType, Trial>();
    
    /**
     * @return the appName
     */
    public AppName getAppName() {
        return appName;
    }
    /**
     * @param appName the appName to set
     */
    public void setAppName(AppName appName) {
        this.appName = appName;
    }
    /**
     * @return the statusRules
     */
    public Map<TrialType, Trial> getStatusRules() {
        return statusRules;
    }
    /**
     * @param statusRules the statusRules to set
     */
    public void setStatusRules(Map<TrialType, Trial> statusRules) {
        this.statusRules = statusRules;
    }
    
}
