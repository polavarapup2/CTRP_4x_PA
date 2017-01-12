/**
 * 
 */
package gov.nih.nci.pa.util;

import static org.apache.commons.lang.StringUtils.defaultString;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang.StringUtils.trim;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Records updates made to a trial during Update operation invoked by Registry
 * or Batch Protocol update. Recorded updates are then used to produce Trial
 * History/Updates tab content.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class TrialUpdatesRecorder {

    /**
     * Using a {@link ThreadLocal} to avoid having to pass an instance of
     * TrialUpdatesRecorder around.
     */
    private static ThreadLocal<LinkedHashSet<String>> holder = new ThreadLocal<LinkedHashSet<String>>();

    /**
     * 
     */
    public static final String DOC_UPDATED = "%s document was updated.";

    /**
     * 
     */
    public static final String SEPARATOR = "\r";

    /**
     * 
     */
    public static final String STATUS_DATES_UPDATED = "Trial Status and/or Trial Status Date were updated.";
    /**
     * 
     */
    public static final String RECRUITMENT_STATUS_DATE_UPDATED = "Participating site's Recruitment Status "
            + "and/or Status Date were updated.";
    /**
     * 
     */
    public static final String IND_IDE_UPDATED = "Ind Ide was updated.";
    /**
     * 
     */
    public static final String GRANT_INFORMATION_UPDATED = "Grant information was updated.";

    /**
     * 
     */
    public static final String IDENTIFIERS_ADDED = "Other Identifiers information was updated: "
            + "new identifier(s) added.";

    /**
     * 
     */
    public static final String START_DATE_CHANGED = "Trial Start Date was updated.";

    /**
     * 
     */
    public static final String PRIMARY_COMPLETION_DATE_CHANGED = "Trial Primary Completion Date was updated.";

    /**
     * 
     */
    public static final String COMPLETION_DATE_CHANGED = "Trial Completion Date was updated.";
    
    /**
     * 
     */
    public static final String START_DATE_TYPE_CHANGED = "Trial Start Date Type was updated.";

    /**
     * 
     */
    public static final String PRIMARY_COMPLETION_DATE_TYPE_CHANGED = "Trial Primary Completion Date Type was updated.";

    /**
     * 
     */
    public static final String COMPLETION_DATE_TYPE_CHANGED = "Trial Completion Date Type was updated.";


    /**
     * 
     */
    public static final String PARTICIPATING_SITES_UPDATED = "Participating site's Program Code was updated.";
    
    /** Accrual disease terminology system message. */
    public static final String ACCRUAL_DISEASE_TERMINOLOGY_UPDATED = "Accrual Disease Terminology was updated.";
    
    /**
     * program code changed message
     */
    public static final String PROGRAM_CODE_CHANGED = "Program codes updated";

    private TrialUpdatesRecorder() {
    }

    /**
     * Reset recordings.
     */
    public static void reset() {
        holder.set(new LinkedHashSet<String>());
    }

    /**
     * recordUpdate.
     * 
     * @param original
     *            original
     * @param updated
     *            updated
     * @param msg
     *            msg
     */
    @SuppressWarnings("rawtypes")
    public static void recordUpdate(Collection original, Collection updated,
            String msg) {
        int sizeBefore = original != null ? original.size() : 0;
        int sizeAfter = updated != null ? updated.size() : 0;
        if (sizeBefore != sizeAfter) {
            add(msg);
        }
    }

    private static void add(String msg) {
        if (holder.get() == null) {
            reset();
        }
        holder.get().add(msg);
    }

    /**
     * recordUpdate.
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @param msg
     *            msg
     */
    public static void recordUpdate(Ts date1, Ts date2, String msg) {
        Date d1 = TsConverter.convertToTimestamp(date1);
        Date d2 = TsConverter.convertToTimestamp(date2);
        if ((d1 == null && d2 != null) || (d1 != null && d2 == null)
                || (d1 != null && d2 != null && !DateUtils.isSameDay(d1, d2))) {
            add(msg);
        }
    }

    /**
     * @param st1 first iso string
     * @param st2 second iso string
     * @param msg message
     */
    public static void recordUpdate(St st1, St st2, String msg) {
        String str1 = StConverter.convertToString(st1);
        String str2 = StConverter.convertToString(st2);
        if (!StringUtils.equals(str1, str2)) {
            add(msg);
        }
    }
    
    /**
     * @param cd1 first iso code
     * @param cd2 second iso code
     * @param msg message
     */
    public static void recordUpdate(Cd cd1, Cd cd2, String msg) {
        String str1 = CdConverter.convertCdToString(cd1);
        String str2 = CdConverter.convertCdToString(cd2);
        if (!StringUtils.equals(str1, str2)) {
            add(msg);
        }
    }

    /**
     * @param studyResourcingDTOs
     *            studyResourcingDTOs
     * @param grantInformationUpdated
     *            grantInformationUpdated
     * @throws PAException
     *             PAException
     */
    public static void recordUpdate(
            List<StudyResourcingDTO> studyResourcingDTOs,
            String grantInformationUpdated) throws PAException {
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO grantDto : studyResourcingDTOs) {
                if (ISOUtil.isIiNull(grantDto.getIdentifier())) {
                    add(grantInformationUpdated);
                    return;
                }
                StudyResourcingDTO dto = PaRegistry.getStudyResourcingService()
                        .get(grantDto.getIdentifier());
                if (isGrantUpdated(dto, grantDto)) {
                    add(grantInformationUpdated);
                    return;
                }
            }
        }
    }

    /**
     * Test if a grant has been updated.
     * 
     * @param existing
     *            The existing grant
     * @param updated
     *            The possibly updated grant
     * @return true if the grant has been updated
     */
    private static boolean isGrantUpdated(StudyResourcingDTO existing,
            StudyResourcingDTO updated) {
        return !(existing.getFundingMechanismCode().getCode().equals(updated
                .getFundingMechanismCode().getCode()))
                || !(existing.getNihInstitutionCode().getCode().equals(updated
                        .getNihInstitutionCode().getCode()))
                || !(existing.getSerialNumber().getValue().equals(updated
                        .getSerialNumber().getValue()))
                || !(existing.getNciDivisionProgramCode().getCode()
                        .equals(updated.getNciDivisionProgramCode().getCode())
                || !(ObjectUtils.equals(RealConverter.convertToDouble(existing.getFundingPercent()) 
                        , RealConverter.convertToDouble(updated.getFundingPercent())))
                );
    }

    /**
     * @param msg
     *            msg
     */
    public static void recordUpdate(String msg) {
        add(msg);
    }

    /**
     * @param studySiteDTOs
     *            studySiteDTOs
     * @param msg
     *            msg
     * @throws PAException
     *             PAException
     */
    public static void recordParticipatingSiteUpdate(
            List<StudySiteDTO> studySiteDTOs, String msg) throws PAException {
        if (studySiteDTOs != null) {
            for (StudySiteDTO newDTO : studySiteDTOs) {
                StudySiteDTO existentDTO = PaRegistry.getStudySiteService()
                        .get(newDTO.getIdentifier());
                if (!defaultString(
                                StConverter.convertToString(newDTO
                                        .getProgramCodeText())).equals(
                                defaultString(StConverter
                                        .convertToString(existentDTO
                                                .getProgramCodeText())))) {
                    add(msg);
                }
            }
        }
    }

    /**
     * @param overallStatusDTO
     *            overallStatusDTO
     * @param msg
     *            msg
     * @throws PAException PAException
     */
    public static void recordUpdate(StudyOverallStatusDTO overallStatusDTO,
            String msg) throws PAException {
        if (PaRegistry.getStudyOverallStatusService()
                .isTrialStatusOrDateChanged(overallStatusDTO,
                        overallStatusDTO.getStudyProtocolIdentifier())) {
            add(msg);
        }
    }
    
    /**
     * @param existingNCT existingNCT
     * @param newNCT newNCT
     */
    public static void isNctUpdated(String existingNCT, String newNCT) {
        if (!StringUtils.equals(trim(defaultString(existingNCT)), // NOPMD
                trim(defaultString(newNCT)))) { // NOPMD
            add(isEmpty(existingNCT) ? "ClinicalTrials.gov Identifier"
                  + " was added." : "ClinicalTrials.gov Identifier was changed.");
        }
    }

    /**
     * All updates together as a single contatenated {@link String}.
     * 
     * @return All updates together as a single contatenated {@link String}.
     */
    public static String getRecordedUpdates() {
        return join(holder.get(), SEPARATOR);
    }
    
    /**
     * @param original original
     * @param updated updated
     * @param msg msg
     */
    @SuppressWarnings("rawtypes")
    public static void recordUpdate(List<ProgramCodeDTO> original, 
            List<ProgramCodeDTO> updated,
            String msg) {
        int sizeBefore = original != null ? original.size() : 0;
        int sizeAfter = updated != null ? updated.size() : 0;
        if (sizeBefore != sizeAfter) {
            add(msg);
        } else {
          if (original != null && updated != null) {
            //even if size is equal actual collection might be different
              String originalText = getProgramCodeText(original);
              String updatedText = getProgramCodeText(updated);
              if (!originalText.equals(updatedText)) {
                  add(msg);
              }
          } 
        }
    }
    
    private static String getProgramCodeText(List<ProgramCodeDTO> programCodesList) {
        StringBuffer result = new StringBuffer();
        
        Collections.sort(programCodesList, new Comparator<ProgramCodeDTO>() {
            @Override
            public int compare(ProgramCodeDTO o1, ProgramCodeDTO o2) {
                return o1.getProgramCode().compareTo(o2.getProgramCode());
            }
        });
        
        for (ProgramCodeDTO programCodeDTO:programCodesList) {
            result.append(programCodeDTO.getProgramCode());
        }
        return result.toString();
    }

}
