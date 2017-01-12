/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.StudyIdentifierType;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public class StudyIdentifierDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final StudyIdentifierType type;
    private String value;

    /**
     * @param type
     *            type
     * @param value
     *            value
     */
    public StudyIdentifierDTO(StudyIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return the type
     */
    public StudyIdentifierType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
