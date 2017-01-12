/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.util.Date;

/**
 * @author dkrylov
 * 
 */
public final class CTGovTrialWorkflowHistoryDTO {

    private String action;
    private String userCreated;
    private Date dateCreated;
    private String importStatus;
    private String ackPending;
    private String ackPerformed;

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the userCreated
     */
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * @param userCreated
     *            the userCreated to set
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated
     *            the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the importStatus
     */
    public String getImportStatus() {
        return importStatus;
    }

    /**
     * @param importStatus
     *            the importStatus to set
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * @return the ackPending
     */
    public String getAckPending() {
        return ackPending;
    }

    /**
     * @param ackPending
     *            the ackPending to set
     */
    public void setAckPending(String ackPending) {
        this.ackPending = ackPending;
    }

    /**
     * @return the ackPerformed
     */
    public String getAckPerformed() {
        return ackPerformed;
    }

    /**
     * @param ackPerformed
     *            the ackPerformed to set
     */
    public void setAckPerformed(String ackPerformed) {
        this.ackPerformed = ackPerformed;
    }

}
