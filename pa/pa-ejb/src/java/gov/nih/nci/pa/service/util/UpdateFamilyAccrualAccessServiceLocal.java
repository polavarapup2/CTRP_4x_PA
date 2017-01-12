package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;

/**
 * @author Kalpana Guthikonda
 */
@Local
public interface UpdateFamilyAccrualAccessServiceLocal {
 
    /**
     * Update the accrual access for the trials.
     * @throws PAException exception
     */
    void updateFamilyAccrualAccess() throws PAException;

}
