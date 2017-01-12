package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;
/**
 * @author ADas
 */
@Local
public interface CTGovSyncNightlyServiceLocal {

    /**
     * Runs nightly job to import industrial and consortia trials with NCT identifiers
     * from CT.Gov and update them in CTRP.
     * @throws PAException PAException
     */
    void updateIndustrialAndConsortiaTrials() throws PAException;
}