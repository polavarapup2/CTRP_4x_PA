package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.App;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.InterimStatus;
import gov.nih.nci.pa.service.status.json.NextStatus;
import gov.nih.nci.pa.service.status.json.SameDateValidation;
import gov.nih.nci.pa.service.status.json.Status;
import gov.nih.nci.pa.service.status.json.StatusFor;
import gov.nih.nci.pa.service.status.json.StatusRules;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.Trial;
import gov.nih.nci.pa.service.status.json.TrialType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author vinodh copyright NCI 2008. All rights reserved. This code may not be
 *         used without the express written permission of the copyright holder,
 *         NCI.
 */

public class BaseStatusTransitionService implements
        StatusTransitionService {

    private static final String STATUSZERO = "STATUSZERO";
    
    private StatusRules statusRules = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusRules getStatusRules() throws PAException {
        if (statusRules != null) {
            return statusRules;
        }
        StatusTransitionsConfig statusTransitionsConfig 
                        = new StatusTransitionsConfig();
        
        try {
            String statusRulesStr = IOUtils.toString(getClass()
                    .getClassLoader().getResourceAsStream("statusvalidations.json"));
            statusRules = statusTransitionsConfig
                    .loadStatusRules(statusRulesStr);
        } catch (Exception e) {
            throw new PAException(
                    "Error loading status rules from config", e);
        }

        return statusRules;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatusDto> validateStatusTransition(AppName appName,
            TrialType trialType, TransitionFor transitionFor,
            String fromStatus, Date fromStatusDt, String toStatus, Date toStatusDt)
            throws PAException { //NOPMD
        List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
        StatusDto statusDto = new StatusDto();
        statusDto.setStatusCode(toStatus);
        statusDto.setStatusDate(toStatusDt);
        statusDtoList.add(statusDto);
        
        String tmpFromStatus = fromStatus;
        if (StringUtils.isBlank(tmpFromStatus)) {
            tmpFromStatus = STATUSZERO;
        }
        // if the from status date is null, setting it to today's date
        Date tmpFromStatusDt = fromStatusDt;
        if (tmpFromStatusDt == null) {
            tmpFromStatusDt = statusDto.getStatusDate();
        }
        StatusRules tmpSR = getStatusRules();
        StatusFor statusFor = getStatusRulesFor(appName, trialType,
                transitionFor, tmpSR);
        validateTransition(appName, trialType, transitionFor, statusFor,
                tmpFromStatus, tmpFromStatusDt, statusDto);
        
        return statusDtoList;
    }

    /**
     * @param appName
     *            - AppName enum
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param statusFor
     *            - statusFor instance
     * @param fromStatus
     *            - from status string
     * @param fromStatusDt
     *            - from status date
     * @param statusDto
     *            - StatusDto object representing to status
     * @throws PAException  - Any error
     */
    private void validateTransition(AppName appName,
            TrialType trialType, TransitionFor transitionFor,
            StatusFor statusFor,
            String fromStatus, Date fromStatusDt,
            StatusDto statusDto)
            throws PAException {
        if (StringUtils.isBlank(statusDto.getStatusCode()) || statusDto.getStatusDate() == null) {
            createValidationError(statusDto, ErrorType.ERROR, 
                    "Next transition status and/or status date is missing");
        }
        
        if (fromStatusDt.compareTo(statusDto.getStatusDate()) > 0) {
            createValidationError(statusDto, ErrorType.ERROR, 
                    "Invalid transition, to status date is before from status date");
        }
        
        NextStatus nextStatus = getNextStatus(appName, trialType,
                transitionFor, fromStatus, statusDto.getStatusCode(), statusFor);

        validateNextStatus(nextStatus, statusDto, fromStatusDt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatusDto> validateStatusHistory(AppName appName,
            TrialType trialType, TransitionFor transitionFor,
            List<StatusDto> statusList) throws PAException {
        if (statusList == null || statusList.isEmpty()) {
            return statusList;
        }
        StatusRules tmpSR = getStatusRules();
        StatusFor statusFor = getStatusRulesFor(appName, trialType,
                transitionFor, tmpSR);
        String fromStatus = STATUSZERO;
        //setting statuszero date to first status date in the list
        Date fromStatusDt = statusList.get(0).getStatusDate();
        for (StatusDto statusDto : statusList) {
            validateTransition(appName, trialType, 
                    transitionFor, statusFor, fromStatus, fromStatusDt, statusDto);
            fromStatus = statusDto.getStatusCode();
            fromStatusDt = statusDto.getStatusDate();
        }
        return statusList;
    }

    private NextStatus getNextStatus(AppName appName, TrialType trialType,
            TransitionFor transitionFor, String fromStatus, String toStatus,
            StatusFor statusFor) throws PAException { //NOPMD
        Status status = statusFor.getStatuses().get(fromStatus);
        if (status == null) {
            throw new PAException(
                    String.format(
                            "Unable to find status rules for current status, %1s, "
                                    + "for %2s for the trial type, %3s, in the app, %4s",
                            new Object[] {fromStatus, transitionFor,
                                    trialType, appName}));
        }

        NextStatus nextStatus = status.getTransitions().get(toStatus);
        if (nextStatus == null) {
            throw new PAException(
                    String.format(
                            "Unable to find status rules for the transitions from status, %1s, "
                             + "to status, %2s, for %3s for the trial type, %4s, in the app, %5s",
                            new Object[] {fromStatus, toStatus, transitionFor,
                                    trialType, appName}));
        }
        return nextStatus;
    }

    private StatusFor getStatusRulesFor(AppName appName, TrialType trialType,
            TransitionFor transitionFor, StatusRules stsRules)
            throws PAException {
        if (appName == null || trialType == null || transitionFor == null) {
            throw new PAException(
                    "Unable to find the required attributes, app name, trial type "
                            + "and transition rules to find the correct status transition rules");
        }
        App app = stsRules.getStatusRulesbyApp().get(appName);
        if (app == null) {
            throw new PAException("Unable to find status rules for the app,"
                    + appName);
        }
        Trial trial = app.getStatusRules().get(trialType);
        if (trial == null) {
            throw new PAException(
                    String.format(
                            "Unable to find status rules for the trial type, %1s, in the app, %2s",
                            new Object[] {trialType, appName}));
        }
        StatusFor statusFor = trial.getStatusForList().get(transitionFor);
        if (statusFor == null) {
            throw new PAException(
                    String.format(
                            "Unable to find status rules for %1s for the trial type, %2s, in the app, %3s",
                            new Object[] {transitionFor, trialType, appName}));
        }
        return statusFor;
    }

    private boolean validateNextStatus(NextStatus nextStatus,
            StatusDto statusDto, Date fromStatusDt) {
        boolean isValid = true;
        if (nextStatus.isValidTransition()) {
            SameDateValidation sameDateValidation = nextStatus
                    .getSameDateValidation();
            if (!sameDateValidation.isCanOccurOnSameDateAsPreviousState()
                    && statusDto.getStatusDate().compareTo(fromStatusDt) == 0) {
                createValidationError(statusDto,
                        sameDateValidation.getErrorType(),
                        sameDateValidation.getErrorMsgTemplate());
                isValid = false;
            }

            Map<String, InterimStatus> interimStatusesMap = nextStatus
                    .getInterimStatuses();
            for (Map.Entry<String, InterimStatus> isEntry : interimStatusesMap
                    .entrySet()) {
                createValidationError(statusDto, isEntry.getValue()
                        .getErrorType(), isEntry.getValue()
                        .getErrorMsgTemplate());
                isValid = false;
            }
        } else {
            createValidationError(statusDto, nextStatus.getErrorType(),
                    nextStatus.getErrorMsgTemplate());
            isValid = false;
        }
        return isValid;
    }

    private void createValidationError(StatusDto statusDto,
            ErrorType errorType, String errorMsg) {
        ValidationError err = new ValidationError();
        err.setErrorType(errorType);
        err.setErrorMessage(errorMsg);
        statusDto.getValidationErrors().add(err);
    }

}
