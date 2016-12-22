package gov.nih.nci.pa.service.status.json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class Trial {

    /**
     * Trial type, ABBREVIATED/COMPLETE
     */
    private TrialType trialType;
    /**
     * Status rules for TRIAL/SITE status
     */
    private Map<TransitionFor, StatusFor> statusForList = new LinkedHashMap<TransitionFor, StatusFor>();
    
    /**
     * @return the trialType
     */
    public TrialType getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(TrialType trialType) {
        this.trialType = trialType;
    }
    /**
     * @return the statusForList
     */
    public Map<TransitionFor, StatusFor> getStatusForList() {
        return statusForList;
    }
    /**
     * @param statusForList the statusForList to set
     */
    public void setStatusForList(Map<TransitionFor, StatusFor> statusForList) {
        this.statusForList = statusForList;
    }

    

}
