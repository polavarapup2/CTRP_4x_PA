package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import java.util.Set;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Nov 15, 2012
 */
@Local
public interface FamilyServiceLocal {

    /**
     * Assign the user access to all family trials.
     * @param user the user
     * @param creator the creator
     * @param comment optional comment
     * @throws PAException exception
     */
    void assignFamilyAccrualAccess(RegistryUser user, RegistryUser creator, String comment) throws PAException;

    /**
     * Unassign all accrual access. Per business rule in PO-5257 this is not limited 
     * to family trials.
     * @param user the user
     * @param creator the creator
     * @throws PAException exception
     */
    void unassignAllAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException;

    /**
     * Update the site submitter and family submitter permissions for a given trial.
     * @param trialId the trial
     * @throws PAException exception
     */
    void updateSiteAndFamilyPermissions(Long trialId) throws PAException;

    /**
     * Get a list of all trials for accrual.
     * @param poOrgId the PO organization id of the site
     * @return collection of study protocol ids
     * @throws PAException exception
     */
    Set<Long> getSiteAccrualTrials(Long poOrgId) throws PAException;
}
