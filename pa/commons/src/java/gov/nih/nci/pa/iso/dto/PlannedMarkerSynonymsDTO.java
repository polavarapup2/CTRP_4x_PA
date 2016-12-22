package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class PlannedMarkerSynonymsDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private St alternativeName;
    private Ii permissibleValue;
    private Cd statusCode;
    /**
     * 
     * @return the alternativeName
     */
    public St getAlternativeName() {
        return alternativeName;
    }
    /**
     * 
     * @param alternativeName alternativeName
     */
    public void setAlternativeName(St alternativeName) {
        this.alternativeName = alternativeName;
    }
    /**
     * @return the permissibleValue
     */
    public Ii getPermissibleValue() {
        return permissibleValue;
    }
    /**
     * 
     * @param permissibleValue permissibleValue
     */
    public void setPermissibleValue(Ii permissibleValue) {
        this.permissibleValue = permissibleValue;
    }
    
    /**
     * 
     * @return statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
}
