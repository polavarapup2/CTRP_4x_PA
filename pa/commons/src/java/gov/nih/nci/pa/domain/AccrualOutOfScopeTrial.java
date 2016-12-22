package gov.nih.nci.pa.domain;

import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * 
 * @author Denis G. Krylov
 * 
 */
@Entity
@Table(name = "ACCRUAL_OUT_OF_SCOPE_TRIAL")
public class AccrualOutOfScopeTrial implements PersistentObject {
    private static final long serialVersionUID = 2827128893597594641L;
    private Long id;
    private String ctepID;
    private String failureReason;
    private Date submissionDate;
    private User user;
    private String action;

    /**
     * 
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the ctepID
     */
    @Column(name = "CTEP_ID")
    public String getCtepID() {
        return ctepID;
    }

    /**
     * @param ctepID
     *            the ctepID to set
     */
    public void setCtepID(String ctepID) {
        this.ctepID = ctepID;
    }

    /**
     * @return the failureReason
     */
    @Column(name = "FAILURE_REASON")
    public String getFailureReason() {
        return failureReason;
    }

    /**
     * @param failureReason
     *            the failureReason to set
     */
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    /**
     * @return the submissionDate
     */
    @Column(name = "SUBMISSION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate
     *            the submissionDate to set
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * @return the user
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", updatable = false)
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the action
     */
    @Column(name = "CTRO_ACTION")
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

}
