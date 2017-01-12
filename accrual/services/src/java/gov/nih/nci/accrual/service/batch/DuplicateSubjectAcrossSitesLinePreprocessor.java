/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import static gov.nih.nci.accrual.util.AccrualUtil.safeGet;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.trim;
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public final class DuplicateSubjectAcrossSitesLinePreprocessor implements
        CdusBatchFilePreProcessor {

    private static final int SITE_ID_IDX = 11;
    private static final int PATIENT_ID_IDX = 2;
    private static final int STUDY_ID_IDX = 1;
    private static final int GENDER_CODE_IDX = 6;
    private static final int DOB_IDX = 5;
    private static final int ETHNICITY_IDX = 7;
    private static final int RACE_CODE_IDX = 3;

    /**
     * UTF byte order marker.
     */
    public static final String UTF8_BOM = "\uFEFF";

    private static final Logger LOG = Logger
            .getLogger(DuplicateSubjectAcrossSitesLinePreprocessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.CdusBatchFilePreProcessor#preprocess
     * (java.io.File)
     */

    @SuppressWarnings("unchecked")
    @Override
    public PreprocessingResult preprocess(File cdusFile) throws IOException {
        final List<String> lines = FileUtils.readLines(cdusFile, "UTF-8");
        ValidationError error = removeDuplicateSubjectLines(lines);
        if (error == null) {
            return new PreprocessingResult(cdusFile,
                    new ArrayList<ValidationError>());
        }
        File processedFile = new File(cdusFile.getParent(),
                FilenameUtils.getBaseName(cdusFile.getName())
                        + "_duplicate_subjects_across_sites_removed"
                        + FilenameUtils.getExtension(cdusFile.getName()));
        FileUtils.writeLines(processedFile, "UTF-8", lines);
        return new PreprocessingResult(processedFile, Arrays.asList(error));
    }

    private ValidationError removeDuplicateSubjectLines(
            final List<String> writeThroughLines) {
        StudyProtocolDTO study = findStudyProtocol(writeThroughLines);
        if (study == null || isExcept(study)) {
            return null;
        }
        List<Patient> allPatients = parsePatients(writeThroughLines);
        List<PotentialDuplicate> duplicates = findDuplicates(study, allPatients);
        if (duplicates.isEmpty()) {
            return null;
        }

        ValidationError error = new ValidationError(
                "The following lines contain Patient IDs already in use by Patients registered on a different site for "
                        + "this study with matching Gender, Date of Birth, Race, and Ethnicity. "
                        + "Patients were not processed:");
        for (PotentialDuplicate dupe : duplicates) {
            writeThroughLines.set(dupe.patient.lineNumber, StringUtils.EMPTY);
            error.getErrorDetails().add(
                    String.format(
                            "Line %s: Patient ID %s is already registered at "
                                    + "%s, Site PO ID: %s, Site CTEP ID: %s",
                            dupe.patient.lineNumber + 1,
                            dupe.patient.subjectID, dupe.siteName,
                            dupe.sitePoId,
                            StringUtils.defaultString(dupe.siteCtepId, "N/A")));
        }
        return error;
    }

    /**
     * @param study
     * @return
     * @throws PAException
     */
    private boolean isExcept(StudyProtocolDTO study) {
        try {
            return PaServiceLocator
                    .getInstance()
                    .getFlaggedTrialService()
                    .isFlagged(
                            study,
                            StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES);
        } catch (PAException e) {
            LOG.error(e, e);
            return false;
        }
    }

    @SuppressWarnings({ "unchecked" })
    private List<PotentialDuplicate> findDuplicates(StudyProtocolDTO study,
            List<Patient> allPatients) {
        List<PotentialDuplicate> dupes = new ArrayList<PotentialDuplicate>();
        try {
            Collection<Long> siteIDs = CollectionUtils.collect(
                    PaServiceLocator
                            .getInstance()
                            .getParticipatingSiteServiceRemote()
                            .getParticipatingSitesByStudyProtocol(
                                    study.getIdentifier()), new Transformer() {
                        @Override
                        public Object transform(Object o) {
                            return IiConverter
                                    .convertToLong(((ParticipatingSiteDTO) o)
                                            .getIdentifier());
                        }
                    });
            for (Patient patient : allPatients) {
                try {
                    PotentialDuplicate dupe = findDuplicate(patient, siteIDs);
                    if (dupe != null) {
                        dupes.add(dupe);
                    }
                } catch (Exception e) {
                    LOG.error(e, e);
                }
            }
        } catch (Exception e) {
            LOG.error(e, e);
        }
        return dupes;
    }

    private PotentialDuplicate findDuplicate(Patient patient,
            Collection<Long> siteIDs) throws PAException,
            NullifiedEntityException, TooManyResultsException,
            NullifiedRoleException {
        if (patient.isRequiredDataProvided()) {
            SearchSSPCriteriaDto criteria = new SearchSSPCriteriaDto();
            criteria.setStudySiteIds(new ArrayList<Long>(siteIDs));
            criteria.setStudySubjectAssignedIdentifier(patient.subjectID);
            criteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.ACTIVE);
            criteria.setPatientBirthDate(patient.dob);
            List<StudySubject> list = AccrualServiceLocator.getInstance()
                    .getStudySubjectService().search(criteria);
            return findDuplicate(patient, list);
        }
        return null;
    }

    private PotentialDuplicate findDuplicate(final Patient patient,
            final List<StudySubject> list) throws NullifiedEntityException,
            TooManyResultsException, PAException, NullifiedRoleException {
        for (StudySubject ss : list) {
            if (equalsIgnoreCase(patient.subjectID,
                    trim(ss.getAssignedIdentifier()))
                    && ss.getPatient().getSexCode() != null
                    && CDUSPatientGenderCode.getByCode(patient.gender) != null
                    && ss.getPatient().getSexCode() == CDUSPatientGenderCode
                            .getByCode(patient.gender).getValue()
                    && ss.getPatient().getEthnicCode() != null
                    && CDUSPatientEthnicityCode.getByCode(patient.ethnicity) != null
                    && ss.getPatient().getEthnicCode() == CDUSPatientEthnicityCode
                            .getByCode(patient.ethnicity).getValue()
                    && isNotBlank(ss.getPatient().getRaceCode())
                    && !patient.getRaceCodes().isEmpty()
                    && CDUSPatientRaceCode.getCodesByCdusCodes(
                            patient.getRaceCodes()).equals(
                            CDUSPatientRaceCode.getCodesByEnumNames(ss
                                    .getPatient().getRaceCode().split(",")))
                    && !isSameSite(ss.getStudySite(), patient.siteID)) {
                OrganizationDTO otherSite = PoRegistry
                        .getOrganizationEntityService().getOrganization(
                                IiConverter.convertToPoOrganizationIi(ss
                                        .getStudySite().getHealthCareFacility()
                                        .getOrganization().getIdentifier()));
                PotentialDuplicate dupe = new PotentialDuplicate(patient,
                        EnOnConverter.convertEnOnToString(otherSite.getName()),
                        IiConverter.convertToString(otherSite.getIdentifier()),
                        AccrualUtil.findOrgCtepID(otherSite));
                return dupe;
            }
        }
        return null;
    }

    private boolean isSameSite(StudySite studySite, String siteID)
            throws NullifiedEntityException, TooManyResultsException,
            PAException {
        OrganizationDTO poOrganization = null;
        if (StringUtils.isNumeric(siteID)) {
            poOrganization = PoRegistry.getOrganizationEntityService()
                    .getOrganization(
                            IiConverter.convertToPoOrganizationIi(siteID));
        }
        if (poOrganization == null) {
            poOrganization = findOrgByCtepID(siteID);
        }
        return poOrganization != null
                && studySite.getHealthCareFacility() != null
                && studySite.getHealthCareFacility().getOrganization() != null
                && IiConverter.convertToLong(poOrganization.getIdentifier())
                        .equals(Long.valueOf(studySite.getHealthCareFacility()
                                .getOrganization().getIdentifier()));
    }

    private OrganizationDTO findOrgByCtepID(final String ctepID)
            throws TooManyResultsException, PAException {
        OrganizationSearchCriteriaDTO orgSearchCriteria = new OrganizationSearchCriteriaDTO();
        orgSearchCriteria.setCtepId(ctepID);
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> orgList = PoRegistry
                .getOrganizationEntityService()
                .search(orgSearchCriteria, limit);
        CollectionUtils.filter(orgList, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                OrganizationDTO org = (OrganizationDTO) o;
                String orgCtepID;
                try {
                    orgCtepID = AccrualUtil.findOrgCtepID(org);
                } catch (NullifiedRoleException e) {
                    throw new RuntimeException(e); // NOPMD
                }
                return StringUtils.equalsIgnoreCase(orgCtepID, ctepID);
            }
        });

        if (orgList.size() != 1) {
            return null;
        } else {
            return orgList.get(0);
        }
    }

    private StudyProtocolDTO findStudyProtocol(final List<String> lines) {
        for (String line : lines) {
            String[] parts = lineIntoParts(line);
            if (parts == null || parts.length == 0) {
                continue;
            }
            if (equalsIgnoreCase("COLLECTIONS",
                    parts[BatchFileIndex.LINE_IDENTIFIER_INDEX])) {
                return findStudyProtocol(AccrualUtil.safeGet(parts,
                        STUDY_ID_IDX).toUpperCase());
            }
        }
        return null;
    }

    private StudyProtocolDTO findStudyProtocol(String protocolId) {
        try {
            if (StringUtils.isNotBlank(protocolId)) {
                return AccrualUtil.findStudy(protocolId);
            }
        } catch (Exception e) {
            LOG.warn(e, e);
        }
        return null;
    }

    private String[] lineIntoParts(String line) { // NOPMD
        if (line.startsWith(UTF8_BOM)) {
            line = line.substring(1);
        }
        if (StringUtils.isBlank(line)) {
            return new String[0];
        }
        return AccrualUtil.csvParseAndTrim(line);
    }

    private List<Patient> parsePatients(final List<String> lines) {
        List<Patient> allPatients = new ArrayList<Patient>();
        int lineNumber = -1;
        for (String line : lines) {
            lineNumber++;
            String[] parts = lineIntoParts(line);
            if (parts == null || parts.length == 0) {
                continue;
            }
            if (equalsIgnoreCase("PATIENTS", parts[0])) {
                String subjectID = safeGet(parts, PATIENT_ID_IDX).toUpperCase();
                String siteID = safeGet(parts, SITE_ID_IDX).toUpperCase();
                String genderCode = safeGet(parts, GENDER_CODE_IDX);
                String dob = safeGet(parts, DOB_IDX);
                String ethnicity = safeGet(parts, ETHNICITY_IDX);
                allPatients.add(new Patient(lineNumber, siteID, subjectID,
                        genderCode, dob, ethnicity));

            }
        }
        fillPatientRaceCodes(lines, allPatients);
        return allPatients;
    }

    private void fillPatientRaceCodes(final List<String> lines,
            final List<Patient> allPatients) {
        for (String line : lines) {
            String[] parts = lineIntoParts(line);
            if (parts == null || parts.length == 0) {
                continue;
            }
            if (equalsIgnoreCase("PATIENT_RACES", parts[0])) {
                final String pRaceCode = safeGet(parts, RACE_CODE_IDX);
                final String subjectID = safeGet(parts, PATIENT_ID_IDX)
                        .toUpperCase();
                if (StringUtils.isNotBlank(subjectID)
                        && StringUtils.isNotBlank(pRaceCode)) {
                    CollectionUtils.forAllDo(allPatients, new Closure() {
                        @Override
                        public void execute(Object arg0) {
                            Patient p = (Patient) arg0;
                            if (StringUtils.equals(subjectID, p.subjectID)) { // NOPMD
                                p.getRaceCodes().add(pRaceCode);
                            }
                        }
                    });
                }
            }
        }

    }

    /**
     * @author dkrylov
     * 
     */
    private static final class Patient { // NOPMD
        private final Integer lineNumber;
        private final String siteID, subjectID, gender, dob, ethnicity;
        private final Set<String> raceCodes = new LinkedHashSet<String>();

        /**
         * @param lineNumber
         * @param siteID
         * @param subjectID
         * @param gender
         * @param dob
         * @param ethnicity
         */
        public Patient(Integer lineNumber, String siteID, String subjectID, // NOPMD
                String gender, String dob, String ethnicity) {
            this.lineNumber = lineNumber;
            this.siteID = siteID;
            this.subjectID = subjectID;
            this.gender = gender;
            this.dob = dob;
            this.ethnicity = ethnicity;
        }

        /**
         * @return the raceCodes
         */
        public Set<String> getRaceCodes() {
            return raceCodes;
        }

        /**
         * @return boolean
         */
        public boolean isRequiredDataProvided() {
            return isNotBlank(siteID) && isNotBlank(subjectID)
                    && isNotBlank(gender) && isNotBlank(dob)
                    && isNotBlank(ethnicity) && !raceCodes.isEmpty();
        }

    }

    /**
     * @author dkrylov
     * 
     */
    private static final class PotentialDuplicate {
        private final Patient patient;
        private final String siteName, sitePoId, siteCtepId;

        /**
         * @param patient
         * @param siteName
         * @param sitePoId
         * @param siteCtepId
         */
        public PotentialDuplicate(Patient patient, String siteName,
                String sitePoId, String siteCtepId) {
            this.patient = patient;
            this.siteName = siteName;
            this.sitePoId = sitePoId;
            this.siteCtepId = siteCtepId;
        }
    }

}
