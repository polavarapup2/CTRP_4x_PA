/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import gov.nih.nci.accrual.util.AccrualUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public final class DuplicateSubjectLinePreprocessor implements
        CdusBatchFilePreProcessor {

    private static final int SITE_ID_IDX = 11;
    private static final int PATIENT_ID_IDX = 2;
    private static final int STUDY_ID_IDX = 1;
    /**
     * UTF byte order marker.
     */
    public static final String UTF8_BOM = "\uFEFF";

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.accrual.service.batch.CdusBatchFilePreProcessor#preprocess
     * (java.io.File)
     */

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
                        + "_duplicate_subjects_removed"
                        + FilenameUtils.getExtension(cdusFile.getName()));
        FileUtils.writeLines(processedFile, "UTF-8", lines);
        return new PreprocessingResult(processedFile, Arrays.asList(error));
    }

    private ValidationError removeDuplicateSubjectLines(
            final List<String> writeThroughLines) {
        List<Patient> allPatients = parsePatients(writeThroughLines);
        Collection<Patient> duplicates = squeezeOutDuplicates(allPatients);
        if (duplicates.isEmpty()) {
            return null;
        }
        ValidationError error = new ValidationError(
                "The following lines contain duplicate subject data and were not processed into the system. "
                        + "Please remove the duplicate lines and resubmit the file:");
        for (Patient dupe : duplicates) {
            writeThroughLines.set(dupe.lineNumber, StringUtils.EMPTY);
            error.getErrorDetails().add(
                    String.format("Line %s, Site ID: %s, Subject ID: %s",
                            dupe.lineNumber + 1, dupe.siteID, dupe.subjectID));
        }
        return error;
    }

    private Collection<Patient> squeezeOutDuplicates(
            final List<Patient> allPatients) {
        List<Patient> dupes = new ArrayList<Patient>();
        for (Patient p : allPatients) {
            for (Patient p2 : allPatients) {
                if (p != p2 && p.equals(p2)) {
                    dupes.add(p);
                }
            }
        }

        TreeSet<Patient> sorted = new TreeSet<Patient>(new Comparator<Patient>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                return o1.lineNumber.compareTo(o2.lineNumber);
            }
        });
        sorted.addAll(dupes);
        return sorted;
    }

    private List<Patient> parsePatients(final List<String> lines) {
        List<Patient> allPatients = new ArrayList<Patient>();
        int lineNumber = -1;
        for (String line : lines) {
            lineNumber++;
            if (line.startsWith(UTF8_BOM)) {
                line = line.substring(1);
            }
            if (StringUtils.isBlank(line)) {
                continue;
            }
            String[] parts = AccrualUtil.csvParseAndTrim(line);
            if (parts == null || parts.length == 0) {
                continue;
            }
            if (equalsIgnoreCase("PATIENTS", parts[0])) {
                String studyID = AccrualUtil.safeGet(parts, STUDY_ID_IDX)
                        .toUpperCase();
                String subjectID = AccrualUtil.safeGet(parts, PATIENT_ID_IDX)
                        .toUpperCase();
                String siteID = AccrualUtil.safeGet(parts, SITE_ID_IDX)
                        .toUpperCase();
                if (isNotBlank(studyID) && isNotBlank(subjectID)
                        && isNotBlank(siteID)) {
                    allPatients.add(new Patient(lineNumber, studyID, siteID,
                            subjectID));
                }
            }
        }
        return allPatients;
    }

    /**
     * @author dkrylov
     *
     */
    private final class Patient { // NOPMD
        private final Integer lineNumber;
        private final String studyID, siteID, subjectID;

        /**
         * @param lineNumber
         * @param studyID
         * @param siteID
         * @param subjectID
         */
        public Patient(Integer lineNumber, String studyID, String siteID,
                String subjectID) {
            this.lineNumber = lineNumber;
            this.studyID = studyID;
            this.siteID = siteID;
            this.subjectID = subjectID;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(studyID).append(siteID)
                    .append(subjectID).toHashCode();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) { // NOPMD
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            Patient rhs = (Patient) obj;
            return new EqualsBuilder().append(studyID, rhs.studyID)
                    .append(siteID, rhs.siteID)
                    .append(subjectID, rhs.subjectID).isEquals();
        }

    }

}
