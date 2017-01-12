package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Operation outcome code.
 * 
 * @author dkrylov
 */
public enum OpOutcomeCode implements CodedEnum<String> {

    /** SUCCESS */
    SUCCESS("SUCCESS"),
    /** FAILURE */
    FAILURE("FAILURE");

    private final String code;

    private OpOutcomeCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @param code
     *            code
     * @return CheckOutType with the given code
     */
    public static OpOutcomeCode getByCode(String code) {
        return getByClassAndCode(OpOutcomeCode.class, code);
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
     * 
     * @return the name of the enum
     */
    public String getName() {
        return name();
    }
}
