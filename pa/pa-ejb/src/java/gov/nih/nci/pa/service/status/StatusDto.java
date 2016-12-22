package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.status.json.ErrorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StatusDto implements Serializable, Comparable<StatusDto> {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7251022912127118526L;
    
    private String statusCode;
    private Date statusDate;
    private String reason;
    private boolean systemCreated;
    private boolean deleted;
    private Long id;
    
    private Date updatedOn;
    private String updatedBy;
    
    private Date createdOn;
    private String createdBy;
    
    private boolean editable;
    private boolean deletable;
    private boolean undoable;
    
    private String comments;
    
    private transient List<ValidationError> validationErrors = new ArrayList<ValidationError>();
    
    private String uuid = UUID.randomUUID().toString();
    private long tstamp = System.currentTimeMillis();
    
    /**
     * Returns status code
     * @return status code string
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set status code
     * @param statusCode String
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns status date
     * @return status date
     */
    public Date getStatusDate() {
        return statusDate;
    }
    
    /**
     * Set status date
     * @param statusDate - Date
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    
    /**
     *  Returns reason for transition
     * @return reason String
     */
    public String getReason() {
        return reason;
    }
    
    /**
     * Set reason for transition
     * @param reason String
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Returns flag to indicate system created
     * @return flag boolean
     */
    public boolean isSystemCreated() {
        return systemCreated;
    }

    /**
     * Set flag that indicates system created
     * @param systemCreated - boolean
     */
    public void setSystemCreated(boolean systemCreated) {
        this.systemCreated = systemCreated;
    }

    /**
     * Returns Status instance id
     * @return identifier
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set Status instance id
     * @param id - Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns flag that indicates status is editable or not
     * @return flag - boolean
     */
    public boolean isEditable() {
        return editable;
    }
    
    /**
     * Sets flag that indicates status is editable or not
     * @param editable - boolean
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    /**
     * Returns flag that indicates status is deletable or not
     * @return flag - boolean
     */
    public boolean isDeletable() {
        return deletable;
    }
    
    /**
     * Sets flag that indicates status is deletable or not
     * @param deletable - boolean
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
    
    /**
     * Returns flag that indicates status is undoable or not
     * @return undoable - boolean
     */
    public boolean isUndoable() {
        return undoable;
    }
    
    /**
     * Returns flag that indicates status is undoable or not
     * @param undoable - boolean
     */
    public void setUndoable(boolean undoable) {
        this.undoable = undoable;
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
     * @return the updatedOn
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * @param updatedOn the updatedOn to set
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns list of status transition validation errors or empty list
     * @return List<ValidationError>
     */
    public List<ValidationError> getValidationErrors() {
        if (validationErrors == null) {
            validationErrors = new ArrayList<ValidationError>();
        }
        return validationErrors;
    }
    
    /**
     * Sets list of status transition validation errors
     * @param validationErrors - List<ValidationError>
     */
    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    /**
     * Returns flag to indicate there are errors
     * @return boolean 
     */
    public boolean hasErrors() {
        return getValidationErrors().isEmpty();
    }
    
    /**
     * @return String ConsolidatedErrorMessage
     */
    public String getConsolidatedErrorMessage() {
        return consolidateValidationMessagesOfType(ErrorType.ERROR);
    }
    
    /**
     * @return String ConsolidatedErrorMessage
     */
    public String getConsolidatedWarningMessage() {
        return consolidateValidationMessagesOfType(ErrorType.WARNING);
    }

    private String consolidateValidationMessagesOfType(ErrorType type) {
        StringBuilder sb = new StringBuilder();
        for (ValidationError err : getValidationErrors()) {
            if (err.getErrorType() == type) {
                sb.append(err.getErrorMessage());
                sb.append(". ");
            }
        }
        return sb.toString();
    }

    /**
     * @param type
     *            ErrorType
     * @return boolean
     */
    public boolean hasErrorOfType(ErrorType type) {
        for (ValidationError err : getValidationErrors()) {
            if (err.getErrorType() == type) {
                return true;
            }
        }
        return false;
    }
   

    /**
     * @return the tstamp
     */
    public long getTstamp() {
        return tstamp;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @param tstamp the tstamp to set
     */
    public void setTstamp(long tstamp) {
        this.tstamp = tstamp;
    }

    @Override
    public int compareTo(StatusDto other) {
        int byDate = this.getStatusDate().compareTo(other.getStatusDate());
        if (byDate != 0) {
            return byDate;
        }
        // Statuses within the same date are compared by ID.
        if (this.getId() != null && other.getId() != null) {
            return this.getId().compareTo(other.getId());
        }

        // Status with a null ID (means, it was just added via the UI) comes
        // later in the list
        if (this.getId() == null && other.getId() != null) {
            return +1;
        }
        if (this.getId() != null && other.getId() == null) {
            return -1;
        }
        return (int) Math.signum(this.getTstamp() - other.getTstamp());
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

   
   
}
