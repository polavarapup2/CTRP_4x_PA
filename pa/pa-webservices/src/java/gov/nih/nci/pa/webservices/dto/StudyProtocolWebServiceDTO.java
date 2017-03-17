package gov.nih.nci.pa.webservices.dto;

import java.util.List;

/**
 * 
 * @author Reshma
 *
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class StudyProtocolWebServiceDTO {
    private String identifier;
    private String acronym;
    private String accrualReportingMethodCode;
    private boolean expandedAccessIndicator;
    private boolean reviewBoardApprovalRequiredIndicator;
    private String publicDescription;
    private String publicTitle;
    private String recordVerificationDate;
    private String scientificDescription;
    private String keywordText;
    private boolean acceptHealthyVolunteersIndicator;
    private boolean ctroOverride;
    private String statusCode;
    private String statusDate;
    private String amendmentNumber;
    private String amendmentDate;
    private String amendmentReasonCode;
    private Integer submissionNumber;
    private List<Integer> targetAccrualNumber;
    private Integer finalAccrualNumber;
    private List<String> secondaryPurposes;
    private List<String> processingStatuses;
    private String secondaryPurposeOtherText;
    private String comments;
    private Integer processingPriority;
    private Long assignedUser;
    private boolean nciGrant;
    private String studySource;
    private Long submitingOgranization;
    private String ctroOverideFlagComments;
    private String expectedAbstractionCompletionDate;
    private String expectedAbstractionCompletionComments;
    //Results reporting dates
    private String pcdSentToPIODate;
    private String pcdConfirmedDate;
    private String desgneeNotifiedDate;
    private String reportingInProcessDate;
    private String threeMonthReminderDate;
    private String fiveMonthReminderDate;
    private String sevenMonthEscalationtoPIODate;
    private String resultsSentToPIODate;
    private String resultsApprovedByPIODate;
    private String prsReleaseDate;
    private String qaCommentsReturnedDate;
    private String trialPublishedDate;
    private boolean delayedPostingIndicatorChanged;
    private boolean useStandardLanguage;
    private boolean dateEnteredInPrs;
    private boolean designeeAccessRevoked;
    private String designeeAccessRevokedDate;
    //Results reporting dates
    private boolean changesInCtrpCtGov;
    private String changesInCtrpCtGovDate;
    private boolean sendToCtGovUpdated; 
    private boolean ctgovXmlRequiredIndicator;
    private boolean dataMonitoringCommitteeAppointedIndicator;
    private boolean delayedpostingIndicator;
    private boolean fdaRegulatedIndicator;
    private boolean proprietaryTrialIndicator;
    private boolean section801Indicator;
    private String phaseCode;
    private String phaseAdditionalQualifierCode;
    private String officialTitle;
    private String primaryPurposeCode;
    private String primaryPurposeAdditionalQualifierCode;
    private String primaryPurposeOtherText;   
    private String programCodeText;
    private String studyProtocolType;
    private String userLastCreated;

    private String startDate;
    private String dateLastCreated;
    private String primaryCompletionDate;
    private String completionDate;
    private String startDateTypeCode;
    private String primaryCompletionDateTypeCode;
    private String completionDateTypeCode;
    private String consortiaTrialCategoryCode;
    private String accrualDiseaseCodeSystem;
    /**
     * 
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * 
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * 
     * @return acronym
     */
    public String getAcronym() {
        return acronym;
    }
    /**
     * 
     * @param acronym the acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    /**
     * 
     * @return accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * 
     * @param accrualReportingMethodCode the accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }
    /**
     * 
     * @return expandedAccessIndicator
     */
    public boolean isExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }
    /**
     * 
     * @param expandedAccessIndicator the expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }
    /**
     * 
     * @return reviewBoardApprovalRequiredIndicator
     */
    public boolean isReviewBoardApprovalRequiredIndicator() {
        return reviewBoardApprovalRequiredIndicator;
    }
    /**
     * 
     * @param reviewBoardApprovalRequiredIndicator the reviewBoardApprovalRequiredIndicator
     */
    public void setReviewBoardApprovalRequiredIndicator(
            boolean reviewBoardApprovalRequiredIndicator) {
        this.reviewBoardApprovalRequiredIndicator = reviewBoardApprovalRequiredIndicator;
    }
    /**
     * 
     * @return publicDescription
     */
    public String getPublicDescription() {
        return publicDescription;
    }
    /**
     * 
     * @param publicDescription the publicDescription
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
    /**
     * 
     * @return publicTitle
     */
    public String getPublicTitle() {
        return publicTitle;
    }
    /**
     * 
     * @param publicTitle the publicTitle
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }
    /**
     * 
     * @return recordVerificationDate
     */
    public String getRecordVerificationDate() {
        return recordVerificationDate;
    }
    /**
     * 
     * @param recordVerificationDate the recordVerificationDate
     */
    public void setRecordVerificationDate(String recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }
    /**
     * 
     * @return scientificDescription
     */
    public String getScientificDescription() {
        return scientificDescription;
    }
    /**
     * 
     * @param scientificDescription the scientificDescription
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }
    /**
     * 
     * @return keywordText
     */
    public String getKeywordText() {
        return keywordText;
    }
    /**
     * 
     * @param keywordText the keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    /**
     * 
     * @return acceptHealthyVolunteersIndicator
     */
    public boolean isAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }
    /**
     * 
     * @param acceptHealthyVolunteersIndicator the acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(
            boolean acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }
    /**
     * 
     * @return ctroOverride
     */
    public boolean isCtroOverride() {
        return ctroOverride;
    }
    /**
     * 
     * @param ctroOverride the ctroOverride
     */
    public void setCtroOverride(boolean ctroOverride) {
        this.ctroOverride = ctroOverride;
    }
    /**
     * 
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode the statusCodec
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * 
     * @param statusDate the statusDate
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
    /**
     *  
     * @return amendmentNumber
     */
    public String getAmendmentNumber() {
        return amendmentNumber;
    }
    /**
     * 
     * @param amendmentNumber the amendmentNumber
     */
    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }
    /**
     * 
     * @return amendmentDate
     */
    public String getAmendmentDate() {
        return amendmentDate;
    }
    /**
     * 
     * @param amendmentDate the amendmentDate
     */
    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }
    /**
     * 
     * @return amendmentReasonCode
     */
    public String getAmendmentReasonCode() {
        return amendmentReasonCode;
    }
    /**
     * 
     * @param amendmentReasonCode the amendmentReasonCode
     */
    public void setAmendmentReasonCode(String amendmentReasonCode) {
        this.amendmentReasonCode = amendmentReasonCode;
    }
    /**
     * 
     * @return submissionNumber
     */
    public Integer getSubmissionNumber() {
        return submissionNumber;
    }
    /**
     * 
     * @param submissionNumber the submissionNumber
     */
    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }
    /**
     * 
     * @return targetAccrualNumber
     */
    public List<Integer> getTargetAccrualNumber() {
        return targetAccrualNumber;
    }
    /**
     * 
     * @param targetAccrualNumber the targetAccrualNumber
     */
    public void setTargetAccrualNumber(List<Integer> targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }
    /**
     * 
     * @return finalAccrualNumber
     */
    public Integer getFinalAccrualNumber() {
        return finalAccrualNumber;
    }
    /**
     * 
     * @param finalAccrualNumber the finalAccrualNumber
     */
    public void setFinalAccrualNumber(Integer finalAccrualNumber) {
        this.finalAccrualNumber = finalAccrualNumber;
    }
    /**
     * 
     * @return secondaryPurposes
     */
    public List<String> getSecondaryPurposes() {
        return secondaryPurposes;
    }
    /**
     * 
     * @param secondaryPurposes the secondaryPurposes
     */
    public void setSecondaryPurposes(List<String> secondaryPurposes) {
        this.secondaryPurposes = secondaryPurposes;
    }
    /**
     * 
     * @return processingStatuses
     */
    public List<String> getProcessingStatuses() {
        return processingStatuses;
    }
    /**
     * 
     * @param processingStatuses the processingStatuses
     */
    public void setProcessingStatuses(List<String> processingStatuses) {
        this.processingStatuses = processingStatuses;
    }
    /**
     * 
     * @return secondaryPurposeOtherText
     */
    public String getSecondaryPurposeOtherText() {
        return secondaryPurposeOtherText;
    }
    /**
     * 
     * @param secondaryPurposeOtherText the secondaryPurposeOtherText
     */
    public void setSecondaryPurposeOtherText(String secondaryPurposeOtherText) {
        this.secondaryPurposeOtherText = secondaryPurposeOtherText;
    }
    /**
     * 
     * @return comments
     */
    public String getComments() {
        return comments;
    }
    /**
     * 
     * @param comments the comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * 
     * @return processingPriority
     */
    public Integer getProcessingPriority() {
        return processingPriority;
    }
    /**
     * 
     * @param processingPriority the processingPriority
     */
    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }
    /**
     * 
     * @return assignedUser
     */
    public Long getAssignedUser() {
        return assignedUser;
    }
    /**
     * 
     * @param assignedUser the assignedUser
     */
    public void setAssignedUser(Long assignedUser) {
        this.assignedUser = assignedUser;
    }
    /**
     * 
     * @return nciGrant
     */
    public boolean isNciGrant() {
        return nciGrant;
    }
    /**
     * 
     * @param nciGrant the nciGrant
     */
    public void setNciGrant(boolean nciGrant) {
        this.nciGrant = nciGrant;
    }
    /**
     * 
     * @return studySource
     */
    public String getStudySource() {
        return studySource;
    }
    /**
     * 
     * @param studySource the studySource
     */
    public void setStudySource(String studySource) {
        this.studySource = studySource;
    }
    /**
     * 
     * @return submitingOgranization
     */
    public Long getSubmitingOgranization() {
        return submitingOgranization;
    }
    /**
     * 
     * @param submitingOgranization the submitingOgranization
     */
    public void setSubmitingOgranization(Long submitingOgranization) {
        this.submitingOgranization = submitingOgranization;
    }
    /**
     * 
     * @return ctroOverideFlagComments
     */
    public String getCtroOverideFlagComments() {
        return ctroOverideFlagComments;
    }
    /**
     * 
     * @param ctroOverideFlagComments the ctroOverideFlagComments
     */
    public void setCtroOverideFlagComments(String ctroOverideFlagComments) {
        this.ctroOverideFlagComments = ctroOverideFlagComments;
    }
    /**
     * 
     * @return expectedAbstractionCompletionDate
     */
    public String getExpectedAbstractionCompletionDate() {
        return expectedAbstractionCompletionDate;
    }
    /**
     * 
     * @param expectedAbstractionCompletionDate the expectedAbstractionCompletionDate
     */
    public void setExpectedAbstractionCompletionDate(
            String expectedAbstractionCompletionDate) {
        this.expectedAbstractionCompletionDate = expectedAbstractionCompletionDate;
    }
    
    /**
     * 
     * @return expectedAbstractionCompletionComments
     */
    public String getExpectedAbstractionCompletionComments() {
        return expectedAbstractionCompletionComments;
    }
    /**
     * 
     * @param expectedAbstractionCompletionComments the expectedAbstractionCompletionComments
     */
    public void setExpectedAbstractionCompletionComments(
            String expectedAbstractionCompletionComments) {
        this.expectedAbstractionCompletionComments = expectedAbstractionCompletionComments;
    }
    /**
     * 
     * @return pcdSentToPIODate
     */
    public String getPcdSentToPIODate() {
        return pcdSentToPIODate;
    }
    /**
     * 
     * @param pcdSentToPIODate the pcdSentToPIODate
     */
    public void setPcdSentToPIODate(String pcdSentToPIODate) {
        this.pcdSentToPIODate = pcdSentToPIODate;
    }
    /**
     * 
     * @return pcdConfirmedDate
     */
    public String getPcdConfirmedDate() {
        return pcdConfirmedDate;
    }
    /**
     * 
     * @param pcdConfirmedDate the pcdConfirmedDate
     */
    public void setPcdConfirmedDate(String pcdConfirmedDate) {
        this.pcdConfirmedDate = pcdConfirmedDate;
    }
    /**
     * 
     * @return desgneeNotifiedDate
     */
    public String getDesgneeNotifiedDate() {
        return desgneeNotifiedDate;
    }
    /**
     * 
     * @param desgneeNotifiedDate the desgneeNotifiedDate
     */
    public void setDesgneeNotifiedDate(String desgneeNotifiedDate) {
        this.desgneeNotifiedDate = desgneeNotifiedDate;
    }
    /**
     * 
     * @return reportingInProcessDate
     */
    public String getReportingInProcessDate() {
        return reportingInProcessDate;
    }
    /**
     * 
     * @param reportingInProcessDate the reportingInProcessDate
     */
    public void setReportingInProcessDate(String reportingInProcessDate) {
        this.reportingInProcessDate = reportingInProcessDate;
    }
    /**
     * 
     * @return threeMonthReminderDate
     */
    public String getThreeMonthReminderDate() {
        return threeMonthReminderDate;
    }
    /**
     * 
     * @param threeMonthReminderDate the threeMonthReminderDate
     */
    public void setThreeMonthReminderDate(String threeMonthReminderDate) {
        this.threeMonthReminderDate = threeMonthReminderDate;
    }
    /**
     * 
     * @return fiveMonthReminderDate 
     */
    public String getFiveMonthReminderDate() {
        return fiveMonthReminderDate;
    }
    /**
     * 
     * @param fiveMonthReminderDate the fiveMonthReminderDate
     */
    public void setFiveMonthReminderDate(String fiveMonthReminderDate) {
        this.fiveMonthReminderDate = fiveMonthReminderDate;
    }
    /**
     * 
     * @return sevenMonthEscalationtoPIODate
     */
    public String getSevenMonthEscalationtoPIODate() {
        return sevenMonthEscalationtoPIODate;
    }
    /**
     * 
     * @param sevenMonthEscalationtoPIODate the sevenMonthEscalationtoPIODate
     */
    public void setSevenMonthEscalationtoPIODate(
            String sevenMonthEscalationtoPIODate) {
        this.sevenMonthEscalationtoPIODate = sevenMonthEscalationtoPIODate;
    }
    /**
     * 
     * @return resultsSentToPIODate
     */
    public String getResultsSentToPIODate() {
        return resultsSentToPIODate;
    }
    /**
     * 
     * @param resultsSentToPIODate the resultsSentToPIODate
     */
    public void setResultsSentToPIODate(String resultsSentToPIODate) {
        this.resultsSentToPIODate = resultsSentToPIODate;
    }
    /**
     * 
     * @return resultsApprovedByPIODate
     */
    public String getResultsApprovedByPIODate() {
        return resultsApprovedByPIODate;
    }
    /**
     * 
     * @param resultsApprovedByPIODate the resultsApprovedByPIODate
     */
    public void setResultsApprovedByPIODate(String resultsApprovedByPIODate) {
        this.resultsApprovedByPIODate = resultsApprovedByPIODate;
    }
    /**
     * 
     * @return prsReleaseDate
     */
    public String getPrsReleaseDate() {
        return prsReleaseDate;
    }
    /**
     * 
     * @param prsReleaseDate the prsReleaseDate
     */
    public void setPrsReleaseDate(String prsReleaseDate) {
        this.prsReleaseDate = prsReleaseDate;
    }
    /**
     * 
     * @return qaCommentsReturnedDate
     */
    public String getQaCommentsReturnedDate() {
        return qaCommentsReturnedDate;
    }
    /**
     * 
     * @param qaCommentsReturnedDate the qaCommentsReturnedDate
     */
    public void setQaCommentsReturnedDate(String qaCommentsReturnedDate) {
        this.qaCommentsReturnedDate = qaCommentsReturnedDate;
    }
    /**
     * 
     * @return trialPublishedDate
     */
    public String getTrialPublishedDate() {
        return trialPublishedDate;
    }
    /**
     * 
     * @param trialPublishedDate the trialPublishedDate
     */
    public void setTrialPublishedDate(String trialPublishedDate) {
        this.trialPublishedDate = trialPublishedDate;
    }
    /**
     * 
     * @return delayedPostingIndicatorChanged
     */ 
    public boolean isDelayedPostingIndicatorChanged() {
        return delayedPostingIndicatorChanged;
    }
    /**
     * 
     * @param delayedPostingIndicatorChanged the delayedPostingIndicatorChanged
     */
    public void setDelayedPostingIndicatorChanged(
            boolean delayedPostingIndicatorChanged) {
        this.delayedPostingIndicatorChanged = delayedPostingIndicatorChanged;
    }
    /**
     * 
     * @return useStandardLanguage 
     */
    public boolean isUseStandardLanguage() {
        return useStandardLanguage;
    }
    /**
     * 
     * @param useStandardLanguage the useStandardLanguage
     */
    public void setUseStandardLanguage(boolean useStandardLanguage) {
        this.useStandardLanguage = useStandardLanguage;
    }
    /**
     * 
     * @return dateEnteredInPrs
     */
    public boolean isDateEnteredInPrs() {
        return dateEnteredInPrs;
    }
    /**
     * 
     * @param dateEnteredInPrs the dateEnteredInPrs
     */
    public void setDateEnteredInPrs(boolean dateEnteredInPrs) {
        this.dateEnteredInPrs = dateEnteredInPrs;
    }
    /**
     * 
     * @return designeeAccessRevoked
     */
    public boolean isDesigneeAccessRevoked() {
        return designeeAccessRevoked;
    }
    /**
     * 
     * @param designeeAccessRevoked the designeeAccessRevoked
     */
    public void setDesigneeAccessRevoked(boolean designeeAccessRevoked) {
        this.designeeAccessRevoked = designeeAccessRevoked;
    }
    /**
     * 
     * @return designeeAccessRevokedDate
     */
    public String getDesigneeAccessRevokedDate() {
        return designeeAccessRevokedDate;
    }
    /**
     * 
     * @param designeeAccessRevokedDate the designeeAccessRevokedDate
     */
    public void setDesigneeAccessRevokedDate(String designeeAccessRevokedDate) {
        this.designeeAccessRevokedDate = designeeAccessRevokedDate;
    }
    /**
     * 
     * @return changesInCtrpCtGov
     */
    public boolean isChangesInCtrpCtGov() {
        return changesInCtrpCtGov;
    }
    /**
     * 
     * @param changesInCtrpCtGov the changesInCtrpCtGov
     */
    public void setChangesInCtrpCtGov(boolean changesInCtrpCtGov) {
        this.changesInCtrpCtGov = changesInCtrpCtGov;
    }
    /**
     * 
     * @return changesInCtrpCtGovDate
     */
    public String getChangesInCtrpCtGovDate() {
        return changesInCtrpCtGovDate;
    }
    /**
     * 
     * @param changesInCtrpCtGovDate the changesInCtrpCtGovDate
     */
    public void setChangesInCtrpCtGovDate(String changesInCtrpCtGovDate) {
        this.changesInCtrpCtGovDate = changesInCtrpCtGovDate;
    }
    /**
     * 
     * @return sendToCtGovUpdated
     */
    public boolean isSendToCtGovUpdated() {
        return sendToCtGovUpdated;
    }
    /**
     * 
     * @param sendToCtGovUpdated the sendToCtGovUpdated
     */
    public void setSendToCtGovUpdated(boolean sendToCtGovUpdated) {
        this.sendToCtGovUpdated = sendToCtGovUpdated;
    }
    /**
     * 
     * @return ctgovXmlRequiredIndicator
     */
    public boolean isCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }
    /**
     * 
     * @param ctgovXmlRequiredIndicator the ctgovXmlRequiredIndicator
     */
    public void setCtgovXmlRequiredIndicator(boolean ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }
    /**
     * 
     * @return dataMonitoringCommitteeAppointedIndicator
     */
    public boolean isDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * 
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            boolean dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }
    /**
     * 
     * @return delayedpostingIndicator
     */
    public boolean isDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }
    /**
     * 
     * @param delayedpostingIndicator the delayedpostingIndicator
     */
    public void setDelayedpostingIndicator(boolean delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }
    /**
     * 
     * @return fdaRegulatedIndicator
     */
    public boolean isFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }
    /**
     * 
     * @param fdaRegulatedIndicator the fdaRegulatedIndicator
     */
    public void setFdaRegulatedIndicator(boolean fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }
    /**
     * 
     * @return proprietaryTrialIndicator
     */
    public boolean isProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }
    /**
     * 
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator
     */
    public void setProprietaryTrialIndicator(boolean proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }
    /**
     * 
     * @return section801Indicator
     */
    public boolean isSection801Indicator() {
        return section801Indicator;
    }
    /**
     * 
     * @param section801Indicator the section801Indicator
     */
    public void setSection801Indicator(boolean section801Indicator) {
        this.section801Indicator = section801Indicator;
    }
    /**
     * 
     * @return phaseCode 
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * 
     * @param phaseCode the phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * 
     * @return phaseAdditionalQualifierCode
     */
    public String getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }
    /**
     * 
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode
     */
    public void setPhaseAdditionalQualifierCode(String phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }
    /**
     * 
     * @return officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * 
     * @param officialTitle the officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * 
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * 
     * @param primaryPurposeCode the primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * 
     * @return primaryPurposeAdditionalQualifierCode
     */
    public String getPrimaryPurposeAdditionalQualifierCode() {
        return primaryPurposeAdditionalQualifierCode;
    }
    /**
     * 
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode
     */
    public void setPrimaryPurposeAdditionalQualifierCode(
            String primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }
    /**
     * 
     * @return primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * 
     * @param primaryPurposeOtherText the primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * 
     * @return programCodeText
     */
    public String getProgramCodeText() {
        return programCodeText;
    }
    /**
     * 
     * @param programCodeText the programCodeText
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }
    /**
     * 
     * @return studyProtocolType
     */
    public String getStudyProtocolType() {
        return studyProtocolType;
    }
    /**
     * 
     * @param studyProtocolType the studyProtocolType
     */
    public void setStudyProtocolType(String studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }
    /**
     * 
     * @return userLastCreated
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * 
     * @param userLastCreated the userLastCreated
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }
    /**
     * 
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * 
     * @param startDate the startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * 
     * @return dateLastCreated
     */
    public String getDateLastCreated() {
        return dateLastCreated;
    }
    /**
     * 
     * @param dateLastCreated the dateLastCreated
     */
    public void setDateLastCreated(String dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }
    /**
     * 
     * @return primaryCompletionDate 
     */
    public String getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }
    /**
     * 
     * @param primaryCompletionDate the primaryCompletionDate
     */
    public void setPrimaryCompletionDate(String primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }
    /**
     * 
     * @return completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }
    /**
     * 
     * @param completionDate the completionDate
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }
    /**
     * 
     * @return startDateTypeCode
     */
    public String getStartDateTypeCode() {
        return startDateTypeCode;
    }
    /**
     * 
     * @param startDateTypeCode the startDateTypeCode
     */
    public void setStartDateTypeCode(String startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }
    /**
     * 
     * @return primaryCompletionDateTypeCode
     */
    public String getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }
    /**
     * 
     * @param primaryCompletionDateTypeCode the primaryCompletionDateTypeCode
     */
    public void setPrimaryCompletionDateTypeCode(
            String primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }
    /**
     * 
     * @return completionDateTypeCode
     */
    public String getCompletionDateTypeCode() {
        return completionDateTypeCode;
    }
    /**
     * 
     * @param completionDateTypeCode the completionDateTypeCode
     */
    public void setCompletionDateTypeCode(String completionDateTypeCode) {
        this.completionDateTypeCode = completionDateTypeCode;
    }
    /**
     * 
     * @return consortiaTrialCategoryCode
     */
    public String getConsortiaTrialCategoryCode() {
        return consortiaTrialCategoryCode;
    }
    /**
     * 
     * @param consortiaTrialCategoryCode the consortiaTrialCategoryCode
     */
    public void setConsortiaTrialCategoryCode(String consortiaTrialCategoryCode) {
        this.consortiaTrialCategoryCode = consortiaTrialCategoryCode;
    }
    /**
     * 
     * @return accrualDiseaseCodeSystem
     */
    public String getAccrualDiseaseCodeSystem() {
        return accrualDiseaseCodeSystem;
    }
    /**
     * 
     * @param accrualDiseaseCodeSystem the accrualDiseaseCodeSystem
     */
    public void setAccrualDiseaseCodeSystem(String accrualDiseaseCodeSystem) {
        this.accrualDiseaseCodeSystem = accrualDiseaseCodeSystem;
    }

}
