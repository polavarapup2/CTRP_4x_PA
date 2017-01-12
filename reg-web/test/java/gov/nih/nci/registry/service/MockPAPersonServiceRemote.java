/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;

/**
 * @author Denis G. Krylov
 *
 */
public class MockPAPersonServiceRemote implements PAPersonServiceRemote {

    
    public static List<PaPersonDTO> investigators = new ArrayList<PaPersonDTO>();
    
    /**
     * 
     */
    public MockPAPersonServiceRemote() {
       
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAPersonServiceRemote#getAllPrincipalInvestigators()
     */
    @Override
    public List<PaPersonDTO> getAllPrincipalInvestigators() throws PAException {
       
        return investigators;
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAPersonServiceRemote#getAllPrincipalInvestigatorsByName(String personTerm)
     */
    @Override
    public List<PaPersonDTO> getAllPrincipalInvestigatorsByName(String personTerm) throws PAException {
       
        return investigators;
    }

}
