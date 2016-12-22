/**
 * 
 */
package gov.nih.nci.pa.noniso.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author dkrylov
 * 
 */
public class StudyProtocolFlagDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String nciID;
    private String reason;
    private String flaggedBy; 
    private String deletedBy;
    private Date flaggedOn;
    private Date deletedOn;
    private String comments;
    private String deleteComments;
    private boolean deleted;

    /**
     * @return the nciID
     */
    public String getNciID() {
        return nciID;
    }

    /**
     * @param nciID
     *            the nciID to set
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the flaggedBy
     */
    public String getFlaggedBy() {
        return flaggedBy;
    }

    /**
     * @param flaggedBy
     *            the flaggedBy to set
     */
    public void setFlaggedBy(String flaggedBy) {
        this.flaggedBy = flaggedBy;
    }

    /**
     * @return the deletedBy
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * @param deletedBy
     *            the deletedBy to set
     */
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    /**
     * @return the flaggedOn
     */
    public Date getFlaggedOn() {
        return flaggedOn;
    }

    /**
     * @param flaggedOn
     *            the flaggedOn to set
     */
    public void setFlaggedOn(Date flaggedOn) {
        this.flaggedOn = flaggedOn;
    }

    /**
     * @return the deletedOn
     */
    public Date getDeletedOn() {
        return deletedOn;
    }

    /**
     * @param deletedOn
     *            the deletedOn to set
     */
    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the deleteComments
     */
    public String getDeleteComments() {
        return deleteComments;
    }

    /**
     * @param deleteComments
     *            the deleteComments to set
     */
    public void setDeleteComments(String deleteComments) {
        this.deleteComments = deleteComments;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
}
