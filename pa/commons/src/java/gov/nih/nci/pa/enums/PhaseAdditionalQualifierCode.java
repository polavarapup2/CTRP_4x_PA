/**
 *
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vrushali
 *
 */
public enum PhaseAdditionalQualifierCode implements CodedEnum<String> {
    /*** Pilot. */
    PILOT("Pilot", "Yes");

    private String code;
    private String displayName;

    private PhaseAdditionalQualifierCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
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
        return this.displayName;
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
    public static PhaseAdditionalQualifierCode getByCode(String code) {
        return getByClassAndCode(PhaseAdditionalQualifierCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static Map<String, String> getDisplayNames() {
        Map<String, String> result = new HashMap<String, String>();
        for (PhaseAdditionalQualifierCode value : PhaseAdditionalQualifierCode.values()) {
            result.put(value.getCode(), value.getDisplayName());
        }
        return result;
    }

}
