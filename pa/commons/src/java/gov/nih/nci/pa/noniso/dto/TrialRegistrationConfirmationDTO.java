package gov.nih.nci.pa.noniso.dto;
/**
 * 
 * @author Reshma
 *
 */
public class TrialRegistrationConfirmationDTO {
    private String nciID;
    private String spID;
    /**
     * const
     */
    public TrialRegistrationConfirmationDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param nciID nciID
     * @param spID spID
     */
    public TrialRegistrationConfirmationDTO(String nciID, String spID) {
        super();
        this.nciID = nciID;
        this.spID = spID;
    }
    /**
     * 
     * @return nciID
     */
    public String getNciID() {
        return nciID;
    }
    /**
     * 
     * @param nciID nciID
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
    }
    /**
     * 
     * @return spID
     */
    public String getSpID() {
        return spID;
    }
    /**
     * 
     * @param spID spID
     */
    public void setSpID(String spID) {
        this.spID = spID;
    }

    
}
