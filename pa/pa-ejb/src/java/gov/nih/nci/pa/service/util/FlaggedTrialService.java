package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.noniso.dto.StudyProtocolFlagDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * @author Denis G. Krylov
 */
public interface FlaggedTrialService {

    /**
     * @return ActiveFlaggedTrials
     */
    List<StudyProtocolFlagDTO> getActiveFlaggedTrials();

    /**
     * @param id
     *            id
     * @param byCode
     *            StudyFlagReasonCode
     * @param comments
     *            comments
     * @throws PAException
     *             PAException
     */
    void updateFlaggedTrial(Long id, StudyFlagReasonCode byCode, String comments)
            throws PAException;

    /**
     * @param nciID
     *            nciID
     * @param code
     *            code
     * @param comments
     *            comments
     * @throws PAException
     *             PAException
     */
    void addFlaggedTrial(String nciID, StudyFlagReasonCode code, String comments)
            throws PAException;

    /**
     * @param flaggedTrialID
     *            flaggedTrialID
     * @param comment
     *            comment
     * @throws PAException
     *             PAException
     */
    void delete(Long flaggedTrialID, String comment) throws PAException;

    /**
     * @return List<StudyProtocolFlagDTO>
     * @throws PAException PAException
     */
    List<StudyProtocolFlagDTO> getDeletedFlaggedTrials() throws PAException;
    
    /**
     * @param study StudyProtocolDTO
     * @param code StudyFlagReasonCode
     * @return isFlagged
     * @throws PAException  PAException
     */
    boolean isFlagged(StudyProtocolDTO study, StudyFlagReasonCode code) throws PAException;

}
