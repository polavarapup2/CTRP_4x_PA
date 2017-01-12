/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * @author Denis G. Krylov
 * 
 */
public interface AccrualUtilityService {

    /**
     * Returns ALL.
     * 
     * @return List<AccrualOutOfScopeTrialDTO>
     * @throws PAException
     *             PAException
     */
    List<AccrualOutOfScopeTrialDTO> getAllOutOfScopeTrials() throws PAException;

    /**
     * @param dto
     *            AccrualOutOfScopeTrialDTO
     * @throws PAException
     *             PAException
     */
    void update(AccrualOutOfScopeTrialDTO dto) throws PAException;

    /**
     * @param dto
     *            AccrualOutOfScopeTrialDTO
     * @throws PAException
     *             PAException
     */
    void create(AccrualOutOfScopeTrialDTO dto) throws PAException;

    /**
     * @param dto
     *            AccrualOutOfScopeTrialDTO
     * @throws PAException
     *             PAException
     */
    void delete(AccrualOutOfScopeTrialDTO dto) throws PAException;

    /**
     * @param ctepID
     *            ctepID
     * @return AccrualOutOfScopeTrialDTO
     * @throws PAException
     *             PAException
     */
    AccrualOutOfScopeTrialDTO getByCtepID(String ctepID) throws PAException;

}
