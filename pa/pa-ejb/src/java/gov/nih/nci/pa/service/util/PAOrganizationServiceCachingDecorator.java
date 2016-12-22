/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;

import java.util.List;

/**
 * Decorates {@link PAOrganizationServiceRemote} and adds caching capability.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class PAOrganizationServiceCachingDecorator implements
        PAOrganizationServiceRemote {

    private final PAOrganizationServiceRemote serviceRemote;

    /**
     * @param serviceRemote
     *            PAOrganizationServiceRemote
     */
    public PAOrganizationServiceCachingDecorator(
            PAOrganizationServiceRemote serviceRemote) {
        this.serviceRemote = serviceRemote;
    }

    /**
     * @param organizationType
     *            organizationType
     * @param organizationTerm
     *            organizationTerm
     * @return List<PaOrganizationDTO>
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#
     * getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol
     *      (java.lang.String, java.lang.String)
     */
    public List<PaOrganizationDTO> getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(
            final String organizationType, final String organizationTerm)
            throws PAException {
        return serviceRemote
                .getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(
                        organizationType, organizationTerm);
    }
    
    /**
     * @param organizationType
     *            organizationType
     * @return List<PaOrganizationDTO>
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationsAssociatedWithStudyProtocol
     *      (java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<PaOrganizationDTO> getOrganizationsAssociatedWithStudyProtocol(
            final String organizationType) throws PAException {
        return (List<PaOrganizationDTO>) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getCriteriaCollectionsCache(),
                "getOrganizationsAssociatedWithStudyProtocol_" + organizationType,
                new Closure() {
                    public Object execute() throws PAException {
                        return serviceRemote
                                .getOrganizationsAssociatedWithStudyProtocol(organizationType);
                    }
                });
    }

    /**
     * @param organization organization
     * @return Organization
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationByIndetifers
     * (gov.nih.nci.pa.domain.Organization)
     */
    public Organization getOrganizationByIndetifers(Organization organization)
            throws PAException {
        return serviceRemote.getOrganizationByIndetifers(organization);
    }

    /**
     * @param names names
     * @return  List<Long>
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationIdsByNames(java.util.List)
     */
    public List<Long> getOrganizationIdsByNames(List<String> names)
            throws PAException {
        return serviceRemote.getOrganizationIdsByNames(names);
    }

    @Override
    public List<Organization> getOrganizationsWithUserAffiliations()
            throws PAException {
        return serviceRemote.getOrganizationsWithUserAffiliations();
    }

}
