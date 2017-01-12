package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author dkrylov
 */
public enum ExternalSystemCode implements CodedEnum<String> {

    /**
     * TWITTER
     */
    TWITTER("Twitter"),
    /**
     * Go.USA.gov
     */
    GO_USA_GOV("Go.USA.gov"),
    /**
     * Mailbox
     */
    MAILBOX("Mailbox"),
    
    /**
     * Jasper
     */
    JASPER("Jasper");

    private final String code;

    private ExternalSystemCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @param code
     *            code
     * @return CheckOutType with the given code
     */
    public static ExternalSystemCode getByCode(String code) {
        return getByClassAndCode(ExternalSystemCode.class, code);
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
