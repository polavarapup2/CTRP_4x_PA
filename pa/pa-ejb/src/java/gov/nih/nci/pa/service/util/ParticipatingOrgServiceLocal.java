package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Jun 18, 2012
 */
@Local
public interface ParticipatingOrgServiceLocal {

    /**
     * Return a list of participating organizations for the given type. Return empty list if not found.
     * @param studyProtocolId the id of the StudyProtocol
     * @return populated list of organizations
     * @throws PAException exception
     */
    List<ParticipatingOrgDTO> getTreatingSites(Long studyProtocolId) throws PAException;
    
    /**
     * Gets a participating site.
     * @param studySiteId studySiteId
     * @return ParticipatingOrgDTO
     * @throws PAException PAException
     */
    ParticipatingOrgDTO getTreatingSite(Long studySiteId) throws PAException;
    
    
    /**
     * Takes a Trial ID and a list of Organizations. Returns a sub-list of the
     * passed Organizations that includes <b>only</b> those which are not yet a
     * participating site on the trial.
     * 
     * @param studyProtocolId
     *            studyProtocolId
     * @param orgsToCheck
     *            List<OrganizationDTO>
     * @return List<OrganizationDTO>
     * @throws PAException
     *             PAException
     */
    Collection<OrganizationDTO> getOrganizationsThatAreNotSiteYet(
            Long studyProtocolId, Collection<OrganizationDTO> orgsToCheck)
            throws PAException;
    
}
