package gov.nih.nci.pa.service.util;



import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;


/**
 * FamilyProgramCodeService
 * @author lalit
 */
public interface FamilyProgramCodeService {    
    
    
    /**
     * Returns the associated Family DTO for a given family po id
     * @param familyPoId family po id 
     * @return FamityDTO
     */
    FamilyDTO getFamilyDTOByPoId(Long familyPoId);
    
    /**
     * Updates a family DTO in db
     * @param familyDTO family dto
     * @return familydto family dto
     */
    FamilyDTO update(FamilyDTO familyDTO);
    
    /**
     * Inserts a new family DTO in db
     * @param familyDTO family dto object
     * @return familkyDTO family dto
     */
    FamilyDTO create(FamilyDTO familyDTO);
    
    /**
     * Creates and adds a new program code in db
     * @param familyDTO family dto
     * @param programCodeDTO program code DTO
     * @throws PAValidationException if program code already exists
     * @return ProgramCodeDTO
     */
    ProgramCodeDTO createProgramCode(FamilyDTO familyDTO, ProgramCodeDTO programCodeDTO) 
            throws PAValidationException;
    
    /**
     * Updates existing program code in family
     * @param familyDTO family dto
     * @param existingProgramCodeDTO existing program code DTO
     * @param newProgramCodeDTO new program code DTO
     * @throws PAValidationException if new program code DTO results in a duplicate
     */
    void updateProgramCode(FamilyDTO familyDTO, ProgramCodeDTO existingProgramCodeDTO, 
            ProgramCodeDTO newProgramCodeDTO) throws PAValidationException;
    
    /**
     * Deletes existing program code in family
     * @param familyDTO family DTO
     * @param programCodeDTO program code DTO being deleted
     * @throws PAValidationException program code doesn't exist 
     */
    void deleteProgramCode(FamilyDTO familyDTO, ProgramCodeDTO programCodeDTO) throws PAValidationException;
    
    /**
     * Checks if a program code is associated to any trial
     * @param programCodeDTO program code DTO object
     * @return true if program code is associated with a trial, false otherwise
     */
    
    Boolean isProgramCodeAssociatedWithATrial(ProgramCodeDTO programCodeDTO);

    /**
     * Will copy new families in PO into PA.
     * @throws PAException when there is an error
     */
    void populate() throws PAException;
    
    /**
     * Inactivates existing program code in family
     * @param programCodeDTO program code DTO being inactivated
     * @throws PAValidationException program code doesn't exist 
     */
    void inactivateProgramCode(ProgramCodeDTO programCodeDTO) throws PAValidationException;
}
