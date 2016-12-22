package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ii;
/**
 * 
 * @author Reshma Koganti
 * 
 */
public class PlannedMarkerSyncWithCaDSRDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private St name;
    private St meaning;
    private St description;
    private Ii caDSRId;
    private Cd statusCode;
    private St ntTermIdentifier;
    private St pvName;
    /**
     * 
     * @return name
     */
    public St getName() {
        return name;
    }
    /**
     * 
     * @param name name
     */
    public void setName(St name) {
        this.name = name;
    }
    /**
     * 
     * @return meaning
     */
    public St getMeaning() {
        return meaning;
    }
    /**
     * 
     * @param meaning meaning
     */
    public void setMeaning(St meaning) {
        this.meaning = meaning;
    }
    /**
     * 
     * @return description
     */
    public St getDescription() {
        return description;
    }
    /**
     * 
     * @param description description
     */
    public void setDescription(St description) {
        this.description = description;
    }
    /**
     * 
     * @return caDSRId
     */
    public Ii getCaDSRId() {
        return caDSRId;
    }
    /**
     * 
     * @param caDSRId caDSRId
     */
    public void setCaDSRId(Ii caDSRId) {
        this.caDSRId = caDSRId;
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
    /**
     * @return the ntTermIdentifier
     */
    public St getNtTermIdentifier() {
        return ntTermIdentifier;
    }
    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(St ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }
    /**
     * 
     * @return pvName pvName
     */
    public St getPvName() {
        return pvName;
    }
    /**
     * 
     * @param pvName pvName
     */
    public void setPvName(St pvName) {
        this.pvName = pvName;
    }
    
    
}
