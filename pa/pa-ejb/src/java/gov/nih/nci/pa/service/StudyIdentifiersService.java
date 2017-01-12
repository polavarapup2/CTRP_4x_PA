/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyIdentifierDTO;

import java.util.List;

/**
 * A service for centralized management of various study identifiers. Over time
 * it is intended to replace identifier management scode scattered across Action
 * classes and {@link gov.nih.nci.pa.service.util.PAServiceUtils}.
 * 
 * @author Denis G. Krylov
 * 
 */
public interface StudyIdentifiersService {

    /**
     * @param studyProtocolID
     *            studyProtocolID
     * @return list of study identifiers
     * @throws PAException
     *             PAException
     */
    List<StudyIdentifierDTO> getStudyIdentifiers(Ii studyProtocolID)
            throws PAException;

    /**
     * Deletes an existing identifier on a trial. Identifier must have the same
     * type and value as the one being passed as a parameter.
     * 
     * @param studyProtocolID
     *            studyProtocolID
     * @param dto
     *            StudyIdentifierDTO
     * @throws PAException
     *             PAException
     */
    void delete(Ii studyProtocolID, StudyIdentifierDTO dto) throws PAException;

    /**
     * Adds a given identifier to the study.
     * 
     * @param studyProtocolIi
     *            studyProtocolIi
     * @param studyIdentifierDTO
     *            studyIdentifierDTO
     * @throws PAException
     *             if the identifier is missing a value or type; if other
     *             business rules are violated.
     */
    void add(Ii studyProtocolIi, StudyIdentifierDTO studyIdentifierDTO)
            throws PAException;

    /**
     * Updates the existing identifier with a new value.
     * @param studyProtocolIi studyProtocolIi
     * @param dto dto
     * @param newValue newValue
     * @throws PAException if the identifier is missing a value; if other
     *             business rules are violated.
     */
    void update(Ii studyProtocolIi, StudyIdentifierDTO dto, String newValue)
            throws PAException;

}
