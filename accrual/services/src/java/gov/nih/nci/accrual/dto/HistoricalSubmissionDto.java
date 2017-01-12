package gov.nih.nci.accrual.dto;

import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.collections.comparators.NullComparator;

/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 */
public class HistoricalSubmissionDto implements Comparable<HistoricalSubmissionDto>, Serializable {

    private static final long serialVersionUID = -3377821023341924359L;

    private Long batchFileIdentifier;
    private String nciNumber;
    private AccrualSubmissionTypeCode submissionType;
    private Timestamp date;
    private String username;
    private Long registryUserId;
    private Long userAffiliatedOrgId;
    private String result;
    private Long completeTrialId;
    private Long abbreviatedTrialId;
    private String fileName;
    private String assignedIdentifier;
    private Long studySubjectId;

    /**
     * @return the batchFileIdentifier
     */
    public Long getBatchFileIdentifier() {
        return batchFileIdentifier;
    }
    /**
     * @param batchFileIdentifier the batchFileIdentifier to set
     */
    public void setBatchFileIdentifier(Long batchFileIdentifier) {
        this.batchFileIdentifier = batchFileIdentifier;
    }
    /**
     * @return the nciNumber
     */
    public String getNciNumber() {
        return nciNumber;
    }
    /**
     * @param nciNumber the nciNumber to set
     */
    public void setNciNumber(String nciNumber) {
        this.nciNumber = nciNumber;
    }
    /**
     * @return the submissionType
     */
    public AccrualSubmissionTypeCode getSubmissionType() {
        return submissionType;
    }
    /**
     * @param submissionType the submissionType to set
     */
    public void setSubmissionType(AccrualSubmissionTypeCode submissionType) {
        this.submissionType = submissionType;
    }
    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the registryUserId
     */
    public Long getRegistryUserId() {
        return registryUserId;
    }
    /**
     * @param registryUserId the registryUserId to set
     */
    public void setRegistryUserId(Long registryUserId) {
        this.registryUserId = registryUserId;
    }
    /**
     * @return the userAffiliatedOrgId
     */
    public Long getUserAffiliatedOrgId() {
        return userAffiliatedOrgId;
    }
    /**
     * @param userAffiliatedOrgId the userAffiliatedOrgId to set
     */
    public void setUserAffiliatedOrgId(Long userAffiliatedOrgId) {
        this.userAffiliatedOrgId = userAffiliatedOrgId;
    }
    /**
     * @return the completeTrialId
     */
    public Long getCompleteTrialId() {
        return completeTrialId;
    }
    /**
     * @param completeTrialId the completeTrialId to set
     */
    public void setCompleteTrialId(Long completeTrialId) {
        this.completeTrialId = completeTrialId;
    }
    /**
     * @return the abbreviatedTrialId
     */
    public Long getAbbreviatedTrialId() {
        return abbreviatedTrialId;
    }
    /**
     * @param abbreviatedTrialId the abbreviatedTrialId to set
     */
    public void setAbbreviatedTrialId(Long abbreviatedTrialId) {
        this.abbreviatedTrialId = abbreviatedTrialId;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the studySubjectId
     */
    public Long getStudySubjectId() {
        return studySubjectId;
    }
    /**
     * @param studySubjectId the studySubjectId to set
     */
    public void setStudySubjectId(Long studySubjectId) {
        this.studySubjectId = studySubjectId;
    }
    @Override
    public int compareTo(HistoricalSubmissionDto that) {
        NullComparator nc = new NullComparator();
        int comparison = nc.compare(that.getDate(), getDate());
        if (comparison != 0) {
           return comparison;
        }
        return nc.compare(getNciNumber(), that.getNciNumber());
    }
}
