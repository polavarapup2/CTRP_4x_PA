/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Vrushali
 * 
 */
public enum StructuralRoleStatusCode implements CodedEnum<String> {
    /*** Active. */
    ACTIVE("Active"),
    /*** Pending. */
    PENDING("Pending"),
    /*** Suspended. */
    SUSPENDED("Suspended"),
    /** Cancelled. */
    CANCELLED("Cancelled"),
    /** Terminated. */
    TERMINATED("Terminated"),
    /*** Nullified. */
    NULLIFIED("Nullified");

    private String code;

    /**
     * Constructor for StatusCode.
     * @param code
     */
    private StructuralRoleStatusCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return String display name
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @return String DisplayName
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @param code code
     * @return StatusCode
     */
    public static StructuralRoleStatusCode getByCode(String code) {
        return getByClassAndCode(StructuralRoleStatusCode.class, code);
    }

    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * @return String[] display names for Abstracted Status Code
     */
    public static String[] getDisplayNames() {
        StructuralRoleStatusCode[] absStatusCodes = StructuralRoleStatusCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }

}
