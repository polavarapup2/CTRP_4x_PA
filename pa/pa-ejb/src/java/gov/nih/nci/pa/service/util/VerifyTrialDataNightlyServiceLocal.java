package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;
/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Local
public interface VerifyTrialDataNightlyServiceLocal {

    /**
     * gets the open trials 
     * @throws PAException PAException
     */ 
    void getOpenTrials() throws PAException;
}
