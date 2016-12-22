/**
 *
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author vrushali
 *
 */
public enum PrimaryPurposeAdditionalQualifierCode implements CodedEnum<String> {
    /** Epidemiologic. */
    EPIDEMIOLOGIC("Epidemiologic"),
    /** Outcome. */
    OUTCOME("Outcome"),
    /** Observational. */
    OBSERVATIONAL("Observational"),
    /** Ancillary. */
    ANCILLARY("Ancillary"),
    /** Correlative. */
    CORRELATIVE("Correlative"),
    /** Other. */
    OTHER("Other");

    private String code;

    private PrimaryPurposeAdditionalQualifierCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return name
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @param str str
     * @return Name
     */
    public String getNameByCode(String str) {
        return getByCode(str).name();
    }

    /**
     * 
     * @return String name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code Code
     * @return code
     */
    public static PrimaryPurposeAdditionalQualifierCode getByCode(String code) {
        return getByClassAndCode(PrimaryPurposeAdditionalQualifierCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        int i = 0;
        String[] result = new String[PrimaryPurposeAdditionalQualifierCode.values().length];
        for (PrimaryPurposeAdditionalQualifierCode value : PrimaryPurposeAdditionalQualifierCode.values()) {
            result[i++] = value.getCode();
        }
        return result;
    }

}
