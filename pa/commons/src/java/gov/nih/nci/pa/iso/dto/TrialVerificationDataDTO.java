/**
 * caBIG Open Source Software License
 */
package gov.nih.nci.pa.iso.dto;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialVerificationDataDTO extends StudyDTO {
    private static final long serialVersionUID = 1L;
    private St verificationMethod; 
    private Ts dateLastUpdated;
    private St userLastUpdated;
    /**
     * 
     * @return verificationMethod verificationMethod
     */
    public St getVerificationMethod() {
        return verificationMethod;
    }
    /**
     * 
     * @param verificationMethod verificationMethod
     */
    public void setVerificationMethod(St verificationMethod) {
        this.verificationMethod = verificationMethod;
    }
    /**
     * 
     * @return dateLastUpdated dateLastUpdated
     */
    public Ts getDateLastUpdated() {
        return dateLastUpdated;
    }
    /**
     * 
     * @param dateLastUpdated dateLastUpdated
     */
    public void setDateLastUpdated(Ts dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
    /**
     * 
     * @return userLastUpdated userLastUpdated
     */
    public St getUserLastUpdated() {
        return userLastUpdated;
    }
    /**
     * 
     * @param userLastUpdated userLastUpdated
     */
    public void setUserLastUpdated(St userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }
    
    
    
}
