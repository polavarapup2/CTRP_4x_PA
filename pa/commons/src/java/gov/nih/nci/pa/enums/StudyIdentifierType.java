package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Editable trial identifier.
 * 
 * @author Denis G. Krylov
 */
public enum StudyIdentifierType implements CodedEnum<String> {

    /** Lead Organization. */
    LEAD_ORG_ID("Lead Organization Trial ID", true, true),
    /** NCT. */
    CTGOV("ClinicalTrials.gov Identifier", false, true),
    /** CTEP. */
    CTEP("CTEP Identifier", false, true),
    /** DCP. */
    DCP("DCP Identifier", false, true),
    /** CCR. */
    CCR("CCR Identifier", false, true),
    /** Duplicate NCI ID. */
    DUPLICATE_NCI("Duplicate NCI Identifier"),
    /** Obsolete NCT ID. */
    OBSOLETE_CTGOV("Obsolete ClinicalTrials.gov Identifier"),
    /** Other Identifier. */
    OTHER("Other Identifier");

    private String code;
    private boolean required;
    private boolean studySiteBased;

    /**
     * 
     * @param code
     * @param required
     *            required
     */
    private StudyIdentifierType(String code, boolean required,
            boolean studySiteBased) {
        this.code = code;
        this.required = required;
        this.studySiteBased = studySiteBased;
        register(this);
    }

    /**
     * 
     * @param code
     */
    private StudyIdentifierType(String code) {
        this(code, false, false);
    }

    /**
     * @return code code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return String DisplayName
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * 
     * @return String name
     */
    public String getName() {
        return name();
    }

    /**
     * Gets by code.
     * 
     * @param code
     *            the code
     * @return the enum with the given code
     */
    public static StudyIdentifierType getByCode(String code) {
        return getByClassAndCode(StudyIdentifierType.class, code);
    }

    /**
     * Gets the display names.
     * 
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        StudyIdentifierType[] l = StudyIdentifierType.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

    /**
     * @return required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @return the studySiteBased
     */
    public boolean isStudySiteBased() {
        return studySiteBased;
    }
}
