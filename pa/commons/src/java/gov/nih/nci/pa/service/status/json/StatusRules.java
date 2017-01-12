package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StatusRules {
    
    /**
     * Status rules for the app, PA/REG etc
     */
    private Map<AppName, App> statusRulesbyApp = new LinkedHashMap<AppName, App>();

    /**
     * @return the statusRulesbyApp
     */
    public Map<AppName, App> getStatusRulesbyApp() {
        return statusRulesbyApp;
    }

    /**
     * @param statusRulesbyApp the statusRulesbyApp to set
     */
    public void setStatusRulesbyApp(Map<AppName, App> statusRulesbyApp) {
        this.statusRulesbyApp = statusRulesbyApp;
    }

    
}
