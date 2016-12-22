package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;

import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Local
public interface TrialDataVerificationServiceLocal extends StudyPaService<TrialVerificationDataDTO> {
    /**
     * 
     * @param studyProtocolId studyProtocolId
     * @return list of TrialVerificationDataDTO
     * @throws PAException PAException
     */ 
    List<TrialVerificationDataDTO> getDataByStudyProtocolId(Long studyProtocolId) throws PAException;
}
