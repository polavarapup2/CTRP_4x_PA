package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 */
@Remote
public interface AccrualDiseaseTerminologyServiceRemote {

    /**
     * Return the list of available accrual disease code systems.
     * @return the code system, null if code system not yet defined
     */
    List<String> getValidCodeSystems();

    /**
     * Return the disease code system (e.g. ICD9) for a given trial
     * @param studyProtocolId the StudyProtcol.id
     * @return the code system
     */
    String getCodeSystem(Long studyProtocolId);

    /**
     * @param studyProtocolId the study protocol
     * @param codeSystem the disease code system (e.g. ICD9)
     * @throws PAException an exception (e.g. can't change code system)
     */
    void updateCodeSystem(Long studyProtocolId, String codeSystem) throws PAException;

    /**
     * Determine if it is okay to change the disease code system for patients on a trial.
     * @param studyProtocolId the study protocol
     * @return if code system can be changed
     */
    Boolean canChangeCodeSystem(Long studyProtocolId);
    
    /**
     * Returns maps with spId and boolean value which determines if it is okay to 
     * change the disease code system for patients on a trial.
     * @param studyProtocolIds studyProtocolIds
     * @return Map 
     */
    Map<Long, Boolean> canChangeCodeSystemForSpIds(List<Long> studyProtocolIds);

}
