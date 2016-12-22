package gov.nih.nci.pa.service.status;

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

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Assert;
import org.junit.Test;

public class StatusTransitionConfigTest {
    
    @Test
    public void loadStatusRulesTest() throws JsonGenerationException, JsonMappingException, IOException {
        
        String statusRulesStr = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("statusvalidations.json"));
        
        StatusTransitionsConfig cfg = new StatusTransitionsConfig();
        StatusRules rules = null;
        try {
            rules = cfg.loadStatusRules(statusRulesStr);
        } catch (Exception e) {
            Assert.fail("Error loading status transition rules, " + e.getMessage());
        }
        
        Assert.assertNotNull(rules);
    }
    
    @Test
    public void loadSampleStatusRulesTest() throws JsonGenerationException, JsonMappingException, IOException {
        StatusRules tmpRules = new StatusRules();
        App paApp = createApp(AppName.PA);
        tmpRules.getStatusRulesbyApp().put(AppName.PA, paApp);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        
        String statusRulesStr = mapper.writeValueAsString(tmpRules);
        Assert.assertTrue(StringUtils.isNotEmpty(statusRulesStr));
        System.out.println(statusRulesStr);
        StatusTransitionsConfig cfg = new StatusTransitionsConfig();
        StatusRules rules = null;
        try {
            rules = cfg.loadStatusRules(statusRulesStr);
        } catch (Exception e) {
            Assert.fail("Error loading status transition rules, " + e.getMessage());
        }
        
        Assert.assertNotNull(rules);
    }

    private App createApp(AppName appName) {
        App paApp = new App();
        paApp.setAppName(appName);
        Trial paAbbTrial = createTrial(TrialType.ABBREVIATED);
        paApp.getStatusRules().put(TrialType.ABBREVIATED, paAbbTrial);
        return paApp;
    }

    private Trial createTrial(TrialType trialType) {
        Trial trial = new Trial();
        trial.setTrialType(trialType);
        StatusFor trialStatus = createStatusFor(TransitionFor.TRIAL_STATUS); 
        trial.getStatusForList().put(TransitionFor.TRIAL_STATUS, trialStatus);
        return trial;
    }

    private StatusFor createStatusFor(TransitionFor transitionFor) {
        StatusFor statusFor = new StatusFor();
        statusFor.setType(transitionFor);
        
        Status zeroStatus = createStatus("STATUSZERO", 0, "Status Zero");
        NextStatus zeroToApprvd = createNextStatus("APPROVED", true, null, null); 
        SameDateValidation zeroToApprvdSDV = createSameDateValidation(true, null, null);
        zeroToApprvd.setSameDateValidation(zeroToApprvdSDV);
        
        NextStatus zeroToActv = createNextStatus("ACTIVE", true, null, null); 
        SameDateValidation zeroToActvSDV = createSameDateValidation(true, null, null);
        zeroToActv.setSameDateValidation(zeroToActvSDV);
        InterimStatus zeroToActvIS1 = createInterimStatus(1, "IN_REVIEW", ErrorType.WARNING, "Interim status [APPROVED] is missing");
        InterimStatus zeroToActvIS2 = createInterimStatus(2, "APPROVED", ErrorType.WARNING, "Interim status [APPROVED] is missing");
        zeroToActv.getInterimStatuses().put("IN_REVIEW", zeroToActvIS1);
        zeroToActv.getInterimStatuses().put("APPROVED", zeroToActvIS2);
        
        zeroStatus.getTransitions().put("APPROVED", zeroToApprvd);
        zeroStatus.getTransitions().put("ACTIVE", zeroToActv);
        statusFor.getStatuses().put("STATUSZERO", zeroStatus);
        return statusFor;
    }
    
    private InterimStatus createInterimStatus(int order, String name, ErrorType errorType, String errMsgTemp) {
        InterimStatus is = new InterimStatus();
        is.setName(name);
        is.setOrder(order);
        is.setErrorType(errorType);
        is.setErrorMsgTemplate(errMsgTemp);
        return is;
    }
    
    private SameDateValidation createSameDateValidation(boolean allowed,
            ErrorType errorType, String errMsgTemp) {
        SameDateValidation sdv = new SameDateValidation();
        sdv.setCanOccurOnSameDateAsPreviousState(allowed);
        sdv.setErrorType(errorType);
        sdv.setErrorMsgTemplate(errMsgTemp);
        return sdv;
    }

    private Status createStatus(String statusCode, int order, String displayName) {
        Status status = new Status();
        status.setCode(statusCode);
        status.setOrder(order);
        status.setDisplayName(displayName);
        return status;
    }
    
    private NextStatus createNextStatus(String statusNm, boolean validTransition, ErrorType errorType, String errorMsgTemp) {
        NextStatus status = new NextStatus();
        status.setName(statusNm);
        status.setValidTransition(validTransition);
        status.setErrorType(errorType);
        status.setErrorMsgTemplate(errorMsgTemp);
        return status;
    }
}
