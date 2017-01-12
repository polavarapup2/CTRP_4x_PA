package gov.nih.nci.pa.noniso.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Denis G. Krylov
 * 
 */
public class AccrualOutOfScopeTrialDTO implements Serializable {
    private static final long serialVersionUID = 1238767890L;

    private Long id;
    private String ctepID;
    private String failureReason;
    private Date submissionDate;
    private String userLoginName;
    private String action;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the ctepID
     */
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
     * @return the userLoginName
     */
    public String getUserLoginName() {
        return userLoginName;
    }

    /**
     * @return the userLoginName
     */
    public String getUserLoginNameStripped() { 
        return StringUtils.defaultString(getUserLoginName()).replaceFirst(
                "^/.*?/CN=", StringUtils.EMPTY);
    }

    /**
     * @param userLoginName
     *            the userLoginName to set
     */
    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
