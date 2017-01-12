package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since Jul 13, 2012
 */
public enum AccrualSubmissionTypeCode implements CodedEnum<String> {

    /** Unknown. */
    UNKNOWN("Unknown"),
    /** User Interface. */
    UI("Web Application"),
    /** Batch Upload. */
    BATCH("Batch"),
    /** Batch Submission Service. */
    SERVICE("Service"),
    /** Manage Subject Accruals Service. */
    SERVICE_MSA("Service-MSA");

    private final String code;

    private AccrualSubmissionTypeCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @param code code
     * @return CheckOutType with the given code
     */
    public static AccrualSubmissionTypeCode getByCode(String code) {
        return getByClassAndCode(AccrualSubmissionTypeCode.class, code);
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
     * Gets the name of the enum.
     * @return the name of the enum
     */
    public String getName() {
        return name();
    }
}
