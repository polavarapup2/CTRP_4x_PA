package gov.nih.nci.pa.service.util;

import java.util.List;

import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;


/**
 * @author Kalpana Guthikonda
 */
@Local
public interface PendingPatientAccrualsServiceLocal {
 
    /**
     * Reads the patientstage and process them.
     * @throws PAException exception
     */
    void readAndProcess() throws PAException;
    
    /**
     * @param identifier NCI/CTEP or DCP id
     * @return all patientStage records.
     * @throws PAException exception
     */
    List<PatientStage> getAllPatientsStage(String identifier) throws PAException;
    
    /**
     * Deletes the patient stage record.
     * @param psToDelete patientStageId
     * @throws PAException exception
     */
    void deletePatientStage(List<Long> psToDelete) throws PAException;
}
