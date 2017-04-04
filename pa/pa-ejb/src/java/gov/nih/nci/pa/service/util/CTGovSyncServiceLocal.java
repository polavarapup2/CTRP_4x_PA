package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
@SuppressWarnings("PMD.ExcessiveParameterList")
public interface CTGovSyncServiceLocal {

    /**
     * Searches for a study using CT.Gov Public API and, if found, returns it in
     * a "raw" format: JAXB classes.
     * 
     * @throws PAException
     *             PAException
     * @param nctID
     *            nctID
     * @return ClinicalStudy
     */
    ClinicalStudy getCtGovStudyByNctId(String nctID) throws PAException;

    /**
     * Searches for a study using CT.Gov Public API and, if found, returns an
     * adapter of it: {@link CTGovStudyAdapter}, mostly for purposes of easier
     * access to the underlying study elements.
     * 
     * @throws PAException
     *             PAException
     * @param nctID
     *            nctID
     * @return ClinicalStudy
     */
    CTGovStudyAdapter getAdaptedCtGovStudyByNctId(String nctID)
            throws PAException;

    /**
     * Imports a trial from CT.Gov and returns an NCI ID of the corresponding
     * trial in CTRP.
     * 
     * @param nctID
     *            nctID
     * @return NCI ID
     * @throws PAException
     *             PAException
     */
    String importTrial(String nctID) throws PAException;

    /**
     * @param searchCriteria search criteria 
     * @return list of log entries which match the specified attributes.
     * @throws PAException PAException
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    List<CTGovImportLog> getLogEntries(CTGovImportLogSearchCriteria searchCriteria)
            throws PAException;
    // CHECKSTYLE:ON
    // CHECKSTYLE:OFF
    /**
     * 
     * @param trialNciId trialNciId
     * @param nctIdStr nctIdStr
     * @param title title
     * @param action action
     * @param status status
     * @param user user
     * @param needsReview needsReview
     * @param adminChanged adminChanged
     * @param scientificChanged scientificChanged
     * @param recent recent
     * @throws PAException PAException
     */
    void createImportLogEntry(
            String trialNciId, // NOPMD
            String nctIdStr, // NOPMD
            String title, String action, String status, String user,
            boolean needsReview, boolean adminChanged,
            boolean scientificChanged, StudyInboxDTO recent) throws PAException;
    // CHECKSTYLE:ON
    
    /**
     * 
     * @param fieldKey fieldKey
     * @return String
     * @throws PAException PAException
     */
    String getFieldLabel(String fieldKey) throws PAException;
    /**
     * 
     * @param spId spId 
     * @param fieldsOfInterestChanged fieldsOfInterestChanged
     * @param ctgovLastUpdateDate ctgovLastUpdateDate
     * @throws PAException PAException
     */
    void closeStudyInboxAndAcceptTrialIfNeeded(Ii spId,
            boolean fieldsOfInterestChanged, String ctgovLastUpdateDate)
            throws PAException;
}
