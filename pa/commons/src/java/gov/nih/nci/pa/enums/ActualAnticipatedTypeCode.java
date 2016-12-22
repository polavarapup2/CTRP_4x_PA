package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration for ActualAnticipatedCode.
 * 
 * @author Naveen Amiruddin
 * @since 07/22/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public enum ActualAnticipatedTypeCode implements CodedEnum<String> {

    /*** Actual. */
    ACTUAL("Actual"),
    /*** Anticipated. */
    ANTICIPATED("Anticipated"),
    /*** N/A. */
    NA("N/A");

    private String code;

    /**
     * Constructor for ActualAnticipatedCode.
     * 
     * @param code
     */
    private ActualAnticipatedTypeCode(String code) {
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
     * @return String DisplayName
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
     * @return ActualAnticipatedCode
     */
    public static ActualAnticipatedTypeCode getByCode(String code) {
        return getByClassAndCode(ActualAnticipatedTypeCode.class, code);
    }

    /**
     * construct a array of display names for ActualAnticipatedCode Enum.
     * 
     * @return String[] display names for ActualAnticipatedCode
     */
    public static String[] getDisplayNames() {
        ActualAnticipatedTypeCode[] codes = ActualAnticipatedTypeCode.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }

}
