/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 *
 */
public class AccrualAccessAssignmentHistoryDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String date;
    
    private String assignee;
    
    private String trialNciId;
    
    private String action;
    
    private String comments;
    
    private String assigner;

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the trialNciId
     */
    public String getTrialNciId() {
        return trialNciId;
    }

    /**
     * @param trialNciId the trialNciId to set
     */
    public void setTrialNciId(String trialNciId) {
        this.trialNciId = trialNciId;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the assigner
     */
    public String getAssigner() {
        return assigner;
    }

    /**
     * @param assigner the assigner to set
     */
    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }
    
    

}
