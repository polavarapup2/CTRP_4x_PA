package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author dkrylov
 * 
 */
public enum TweetStatusCode implements CodedEnum<String> {
    /*** PENDING. */
    PENDING("Pending"),
    /*** SENT. */
    SENT("Sent"),
    /*** In Active. **/
    CANCELED("Canceled"),
    /*** FAILED. */
    FAILED("Failed");

    private String code;

    /**
     * Constructor for StatusCode.
     * 
     * @param code
     */
    private TweetStatusCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return str
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code
     *            code
     * @return StatusCode
     */
    public static TweetStatusCode getByCode(String code) {
        return getByClassAndCode(TweetStatusCode.class, code);
    }

    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * 
     * @return String[] display names for Abstracted Status Code
     */
    public static String[] getDisplayNames() {
        TweetStatusCode[] absStatusCodes = TweetStatusCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }

}
