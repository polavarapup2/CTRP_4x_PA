package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;

import java.util.List;

/**
 *
 * @author Vrushali
 *
 */
public interface StudyProtocolStageService {

    /**
     *
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    StudyProtocolStageDTO get(Ii ii) throws PAException;
    /**
     * for creating a new .
     * @param ispDTO  for spStage
     * @param fundDTOs for funding
     * @param indDTOs for ind
     * @param docDTOs for document
     * @return ii ii
     * @throws PAException exception
     */
    Ii create(StudyProtocolStageDTO ispDTO, List<StudyFundingStageDTO> fundDTOs, List<StudyIndIdeStageDTO> indDTOs,
            List<DocumentDTO> docDTOs) throws PAException;
    /**
     *
     * @param dto criteria
     * @param pagingParams p
     * @return list of dto
     * @throws PAException on err
     * @throws TooManyResultsException on err
     */
    List<StudyProtocolStageDTO> search(StudyProtocolStageDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException;
    /**
     *
     * @param isoDTO  for spStage
     * @param fundDTOs for funding
     * @param indDTOs for ind
     * @param docDTOs for document
     * @return dto
     * @throws PAException e
     */
    StudyProtocolStageDTO update(StudyProtocolStageDTO isoDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs)
    throws PAException;
    /**
     *
     * @param ii ii to delete
     * @return
     * @throws PAException e
     */
    void delete(Ii ii) throws PAException;
    /**
     *
     * @param studyProtocolStageIi ii
     * @return list
     * @throws PAException on err
     */
    List<StudyFundingStageDTO> getGrantsByStudyProtocolStage(Ii studyProtocolStageIi)throws PAException;
    /**
     *
     * @param studyProtocolStageIi ii
     * @return list
     * @throws PAException on err
     */
    List <StudyIndIdeStageDTO> getIndIdesByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException;

    /**
     *
     * @param studyProtocolStageIi ii
     * @return list of documents
     * @throws PAException on err
     */
    List<DocumentDTO> getDocumentsByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException;
}
