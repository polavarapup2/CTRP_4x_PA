package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Kalpana Guthikonda
 * @since 12/24/2012
 */
public enum OutcomeMeasureTypeCode implements CodedEnum<String> {
    /**
     * Primary.
     */
    PRIMARY("Primary"),
    /**
     * Secondary.
     */
    SECONDARY("Secondary"), 
    /**
     * Other Pre-specified.
     */
    OTHER_PRE_SPECIFIED("Other Pre-specified");

    private String code;

    /**
     * 
     * @param code
     */
    private OutcomeMeasureTypeCode(String code) {
        this.code = code;
        register(this);
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
     * 
     * @param code code
     * @return OutcomeMeasureTypeCode
     */
    public static OutcomeMeasureTypeCode getByCode(String code) {
        return getByClassAndCode(OutcomeMeasureTypeCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        OutcomeMeasureTypeCode[] l = OutcomeMeasureTypeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

}
