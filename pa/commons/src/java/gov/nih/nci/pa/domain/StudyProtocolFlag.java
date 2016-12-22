package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Study protocol flags.
 * 
 * @author Denis G. Krylov
 */
@Entity
@Cacheable(value = false)
@Table(name = "study_protocol_flags")
@org.hibernate.annotations.Table(appliesTo = "study_protocol_flags")
public class StudyProtocolFlag implements PersistentObject, Auditable {

    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private StudyProtocol studyProtocol;
    private StudyFlagReasonCode flagReason;
    private Date dateFlagged;
    private User flaggingUser;
    private String comments;
    private Boolean deleted;
    private Date dateDeleted;
    private User deletingUser;
    private String deleteComments;

    /**
     * set id.
     * 
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * 
     * @return the id
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    @Searchable
    public Long getId() {
        return this.id;
    }

    /**
     * @return the studyProtocolA
     */
    @ManyToOne
    @JoinColumn(name = "study_protocol_id")
    @NotNull
    @Searchable(nested = true)
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * @param studyProtocol
     *            the studyProtocol to set
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }

    /**
     * @return the flagReason
     */
    @Column(name = "flag_reason")
    @Enumerated(EnumType.STRING)
    @Searchable
    @NotNull
    public StudyFlagReasonCode getFlagReason() {
        return flagReason;
    }

    /**
     * @param flagReason
     *            the flagReason to set
     */
    public void setFlagReason(StudyFlagReasonCode flagReason) {
        this.flagReason = flagReason;
    }

    /**
     * @return the dateFlagged
     */
    @Column(name = "date_flagged")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getDateFlagged() {
        return dateFlagged;
    }

    /**
     * @param dateFlagged
     *            the dateFlagged to set
     */
    public void setDateFlagged(Date dateFlagged) {
        this.dateFlagged = dateFlagged;
    }

    /**
     * @return the user
     */
    @ManyToOne
    @JoinColumn(name = "flagging_user_id")
    @NotNull
    public User getFlaggingUser() {
        return flaggingUser;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setFlaggingUser(User user) {
        this.flaggingUser = user;
    }

    /**
     * @return the comments
     */
    @Column(name = "comments")
    @Searchable
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
     * @return the deleted
     */
    @Column(name = "deleted")
    @Searchable
    @NotNull
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the dateDeleted
     */
    @Column(name = "date_deleted")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateDeleted() {
        return dateDeleted;
    }

    /**
     * @param dateDeleted
     *            the dateDeleted to set
     */
    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    /**
     * @return the deletingUser
     */
    @ManyToOne
    @JoinColumn(name = "deleting_user_id")
    public User getDeletingUser() {
        return deletingUser;
    }

    /**
     * @param deletingUser
     *            the deletingUser to set
     */
    public void setDeletingUser(User deletingUser) {
        this.deletingUser = deletingUser;
    }

    /**
     * @return the deleteComments
     */
    @Column(name = "delete_comments")
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
     * @return DeletingUserName
     */
    @Transient
    public String getDeletingUserName() {
        return CsmUserUtil.getDisplayUsername(getDeletingUser());
    }

    /**
     * @return FlaggingUserName
     */
    @Transient
    public String getFlaggingUserName() {
        return CsmUserUtil.getDisplayUsername(getFlaggingUser());
    }

    /**
     * @return NciID
     */
    @Transient
    public String getNciID() {
        return getStudyProtocol().getNciID();
    }

}
