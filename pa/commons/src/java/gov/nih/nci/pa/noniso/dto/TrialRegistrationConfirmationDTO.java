package gov.nih.nci.pa.noniso.dto;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
