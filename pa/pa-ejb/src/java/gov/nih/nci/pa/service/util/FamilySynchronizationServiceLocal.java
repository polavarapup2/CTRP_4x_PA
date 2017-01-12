package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Nov 29, 2012
 */
@Local
public interface FamilySynchronizationServiceLocal {

    /**
     * Update accrual permissions to reflect removal of an organization from a family.
     * @param familyOrgRelId the PO family organization relationship id
     * @throws PAException exception
     */
    void synchronizeFamilyOrganizationRelationship(Long familyOrgRelId) throws PAException;

}
