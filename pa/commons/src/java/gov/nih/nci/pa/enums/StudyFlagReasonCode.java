package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * This class contains an enumeration for study flag reasons.
 * 
 * @author dkrylov
 */
public enum StudyFlagReasonCode implements CodedEnum<String> {

    /**
     * DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES
     */
    DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES(
            "Do not enforce unique Subject ID across sites"),
    /**
     * Do not submit tweets
     */
    DO_NOT_SUBMIT_TWEETS("Do not submit tweets"),
    /**
     * 
     * DO_NOT_SEND_TO_CLINICALTRIALS_GOV
     */
    DO_NOT_SEND_TO_CLINICALTRIALS_GOV("Do not send to ClinicalTrials.gov"),
    /**
     * 
     * DO_NOT_PROCESS_CDUS_FILES
     */
    DO_NOT_PROCESS_CDUS_FILES("Do not process CDUS accrual files");

    private String code;

    /**
     * Constructor
     * 
     * @param name
     *            the name of the code
     */
    private StudyFlagReasonCode(String name) {
        code = name;
        register(this);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * Returns the name of the study source
     * 
     * @return String name the name
     */
    public String getName() {
        return name();
    }

    /**
     * It returns the enum by the given code
     * 
     * @param code
     *            code the code to look up
     * @return the Code for the study source
     */
    public static StudyFlagReasonCode getByCode(String code) {
        return getByClassAndCode(StudyFlagReasonCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        StudyFlagReasonCode[] l = StudyFlagReasonCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

}
