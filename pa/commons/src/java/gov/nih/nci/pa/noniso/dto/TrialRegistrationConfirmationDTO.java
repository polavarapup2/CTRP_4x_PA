package gov.nih.nci.pa.noniso.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Reshma
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TrialRegistrationConfirmation")
public class TrialRegistrationConfirmationDTO {
    private String paTrialID;
    private String nciTrialID;
    /**
     * const
     */
    public TrialRegistrationConfirmationDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param paTrialID paTrialID
     * @param nciTrialID nciTrialID
     */
    public TrialRegistrationConfirmationDTO(String paTrialID, String nciTrialID) {
        super();
        this.paTrialID = paTrialID;
        this.nciTrialID = nciTrialID;
    }
    /**
     * 
     * @return paTrialID
     */
    public String getPaTrialID() {
        return paTrialID;
    }
    /**
     * 
     * @param paTrialID paTrialID
     */
    public void setPaTrialID(String paTrialID) {
        this.paTrialID = paTrialID;
    }
    /**
     * 
     * @return nciTrialID
     */
    public String getNciTrialID() {
        return nciTrialID;
    }
    /**
     * 
     * @param nciTrialID nciTrialID
     */
    public void setNciTrialID(String nciTrialID) {
        this.nciTrialID = nciTrialID;
    }
    
   
}
