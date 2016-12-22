package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.StatusRules;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;

import java.util.Date;
import java.util.List;

/**
 * @author vinodh 
 * Copyright NCI 2008. All rights reserved. This code may not be
 * used without the express written permission of the copyright holder,
 * NCI.
 */
public interface StatusTransitionService {
    
    /**
     * Returns the instance of StatusRules to be used in the validation
     * @return Instance of StatusRules
     * @throws PAException - Any error
     */
     StatusRules getStatusRules() throws PAException;
    
    /**
     * Validates a single status transition as per the rules in
     * StatusTransitionConfig
     * 
     * @param appName
     *            - AppName enum
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param fromStatus
     *            - from status string
     * @param fromStatusDt
     *            - from status date
     * @param toStatus
     *            - to status string
     * @param toStatusDt
     *            - to status date
     * @return validation results as list of statuses
     * @throws PAException  - Any error
     */
    List<StatusDto> validateStatusTransition(AppName appName, TrialType trialType,
            TransitionFor transitionFor, String fromStatus, Date fromStatusDt, String toStatus, 
            Date toStatusDt) throws PAException;

    /**
     * Validates the list of status history as per the rules in
     * StatusTransitionConfig
     * 
     * @param appName
     *            - AppName enum
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param statusList
     *            - list of status history
     * @return validation results as list of statuses
     * @throws PAException  - Any error
     */
    List<StatusDto> validateStatusHistory(AppName appName, TrialType trialType,
            TransitionFor transitionFor, List<StatusDto> statusList) throws PAException;

}