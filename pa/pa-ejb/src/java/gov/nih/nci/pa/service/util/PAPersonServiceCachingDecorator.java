/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;

import java.util.List;

/**
 * Decorates {@link PAPersonServiceRemote} and adds caching capability.
 * 
 * @author Denis G. Krylov
 * 
 */
public class PAPersonServiceCachingDecorator implements
        PAPersonServiceRemote {

    private final PAPersonServiceRemote serviceRemote;

    /**
     * Constructor.
     * 
     * @param serviceRemote
     *            PAPersonServiceRemote
     */
    public PAPersonServiceCachingDecorator(
            PAPersonServiceRemote serviceRemote) {
        this.serviceRemote = serviceRemote;
    }

    /**
     * @return List<PaPersonDTO>
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.PAPersonServiceRemote#getAllPrincipalInvestigators()
     */
    @SuppressWarnings("unchecked")
    public List<PaPersonDTO> getAllPrincipalInvestigators() throws PAException {
        return (List<PaPersonDTO>) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getCriteriaCollectionsCache(),
                "getAllPrincipalInvestigators", new Closure() {                    
                    public Object execute() throws PAException {
                        return serviceRemote.getAllPrincipalInvestigators();
                    }
                });
    }
    
    /**
     * @return List<PaPersonDTO>
     * @param personTerm personTerm
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.PAPersonServiceRemote#getAllPrincipalInvestigatorsByName()
     */
    public List<PaPersonDTO> getAllPrincipalInvestigatorsByName(
            String personTerm) throws PAException {
        return serviceRemote.getAllPrincipalInvestigatorsByName(personTerm);
    }

}
