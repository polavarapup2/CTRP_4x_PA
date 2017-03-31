package gov.nih.nci.pa.webservices.dto;

import gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.ctrp.importtrials.dto.NonInterventionalStudyProtocolDTO;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 *
 * @author Reshma
 *
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NonInterventionalStudyProtocolDTO.class),
        @JsonSubTypes.Type(value = InterventionalStudyProtocolDTO.class)

})
public class StudyProtocolWebServiceDTO {

    private String studyProtocolId;
    private String nciId;
    private String acronym;
    private String publicDescription;
    private String publicTitle;
    private String scientificDescription;
    private String keywordText;
    private String officialTitle;
    private String startDateTypeCode;
    private String primaryCompletionDateTypeCode;
    private String completionDateTypeCode;
    private String startDate;
    private String primaryCompletionDate;
    private String completionDate;
    private Integer targetAccrualNumber;
    private Boolean expandedAccessIndicator;
    private String phaseCode;
    private String recordVerificationDate;
    private Boolean acceptHealthyVolunteersIndicator;
    private Boolean dataMonitoringCommitteeAppointedIndicator;
    private String primaryPurposeCode;
    private String primaryPurposeAdditionalQualifierCode;
    private String primaryPurposeOtherText;
    private String userLastCreated;
    private String studySource;
    private List<String> secondaryIdentifiers;

    /**
     * const
     */
    public StudyProtocolWebServiceDTO() {
        super();
        // TODO
    }

    // CHECKSTYLE:OFF
    /**
     * 
     * @param studyProtocolId
     *            studyProtocolId
     * @param nciId
     *            nciId
     * @param acronym
     *            acronym
     * @param publicDescription
     *            publicDescription
     * @param publicTitle
     *            publicTitle
     * @param scientificDescription
     *            scientificDescription
     * @param keywordText
     *            keywordText
     * @param officialTitle
     *            officialTitle
     * @param startDateTypeCode
     *            startDateTypeCode
     * @param primaryCompletionDateTypeCode
     *            primaryCompletionDateTypeCode
     * @param completionDateTypeCode
     *            completionDateTypeCode
     * @param startDate
     *            startDate
     * @param primaryCompletionDate
     *            primaryCompletionDate
     * @param completionDate
     *            completionDate
     * @param targetAccrualNumber
     *            targetAccrualNumber
     * @param expandedAccessIndicator
     *            expandedAccessIndicator
     * @param phaseCode
     *            phaseCode
     * @param recordVerificationDate
     *            recordVerificationDate
     * @param acceptHealthyVolunteersIndicator
     *            acceptHealthyVolunteersIndicator
     * @param dataMonitoringCommitteeAppointedIndicator
     *            dataMonitoringCommitteeAppointedIndicator
     * @param primaryPurposeCode
     *            primaryPurposeCode
     * @param primaryPurposeAdditionalQualifierCode
     *            primaryPurposeAdditionalQualifierCode
     * @param primaryPurposeOtherText
     *            primaryPurposeOtherText
     * @param userLastCreated
     *            userLastCreated
     * @param studySource
     *            studySource
     * @param secondaryIdentifiers
     *            secondaryIdentifiers
     */
    public StudyProtocolWebServiceDTO(// NOPMD
            String studyProtocolId,
            String nciId,// NOPMD
            String acronym, String publicDescription, String publicTitle,
            String scientificDescription, String keywordText,
            String officialTitle, String startDateTypeCode,
            String primaryCompletionDateTypeCode,
            String completionDateTypeCode, String startDate,
            String primaryCompletionDate, String completionDate,
            Integer targetAccrualNumber, boolean expandedAccessIndicator,
            String phaseCode, String recordVerificationDate,
            Boolean acceptHealthyVolunteersIndicator,
            Boolean dataMonitoringCommitteeAppointedIndicator,
            String primaryPurposeCode,
            String primaryPurposeAdditionalQualifierCode,
            String primaryPurposeOtherText, String userLastCreated,
            String studySource, List<String> secondaryIdentifiers) {
        super();
        this.studyProtocolId = studyProtocolId;
        this.nciId = nciId;
        this.acronym = acronym;
        this.publicDescription = publicDescription;
        this.publicTitle = publicTitle;
        this.scientificDescription = scientificDescription;
        this.keywordText = keywordText;
        this.officialTitle = officialTitle;
        this.startDateTypeCode = startDateTypeCode;
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
        this.completionDateTypeCode = completionDateTypeCode;
        this.startDate = startDate;
        this.primaryCompletionDate = primaryCompletionDate;
        this.completionDate = completionDate;
        this.targetAccrualNumber = targetAccrualNumber;
        this.expandedAccessIndicator = expandedAccessIndicator;
        this.phaseCode = phaseCode;
        this.recordVerificationDate = recordVerificationDate;
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
        this.primaryPurposeCode = primaryPurposeCode;
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
        this.primaryPurposeOtherText = primaryPurposeOtherText;
        this.userLastCreated = userLastCreated;
        this.studySource = studySource;
        this.secondaryIdentifiers = secondaryIdentifiers;
    }

    // CHECKSTYLE:ON

    /**
     * 
     * @return studyProtocolId studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * 
     * @return nciId the nciId
     */
    public String getNciId() {
        return nciId;
    }

    /**
     * 
     * @param nciId
     *            nciId
     */
    public void setNciId(String nciId) {
        this.nciId = nciId;
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
     * @param acronym
     *            the acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
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
     * @param expandedAccessIndicator
     *            the expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(boolean expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
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
     * @param publicDescription
     *            the publicDescription
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
     * @param publicTitle
     *            the publicTitle
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
     * @param recordVerificationDate
     *            the recordVerificationDate
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
     * @param scientificDescription
     *            the scientificDescription
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
     * @param keywordText
     *            the keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    /**
     *
     * @return targetAccrualNumber
     */
    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     *
     * @param targetAccrualNumber
     *            the targetAccrualNumber
     */
    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
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
     * @param phaseCode
     *            the phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
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
     * @param officialTitle
     *            the officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
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
     * @param primaryPurposeOtherText
     *            the primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
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
     * @param userLastCreated
     *            the userLastCreated
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
     * @param startDate
     *            the startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
     * @param primaryCompletionDate
     *            the primaryCompletionDate
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
     * @param completionDate
     *            the completionDate
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
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
     * @param primaryPurposeCode
     *            the primaryPurposeCode
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
     * @param primaryPurposeAdditionalQualifierCode
     *            the primaryPurposeAdditionalQualifierCode
     */
    public void setPrimaryPurposeAdditionalQualifierCode(
            String primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
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
     * @param startDateTypeCode
     *            the startDateTypeCode
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
     * @param primaryCompletionDateTypeCode
     *            the primaryCompletionDateTypeCode
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
     * @param completionDateTypeCode
     *            the completionDateTypeCode
     */
    public void setCompletionDateTypeCode(String completionDateTypeCode) {
        this.completionDateTypeCode = completionDateTypeCode;
    }

    /**
     * 
     * @return acceptHealthyVolunteersIndicator
     */
    public Boolean isAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }

    /**
     * 
     * @param acceptHealthyVolunteersIndicator
     *            acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(
            Boolean acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }

    /**
     * 
     * @return dataMonitoringCommitteeAppointedIndicator
     */
    public Boolean isDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * 
     * @param dataMonitoringCommitteeAppointedIndicator
     *            dataMonitoringCommitteeAppointedIndicator
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            Boolean dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
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
     * @param studySource
     *            studySource
     */
    public void setStudySource(String studySource) {
        this.studySource = studySource;
    }

    /**
     * 
     * @return secondaryIdentifiers
     */
    public List<String> getSecondaryIdentifiers() {
        return secondaryIdentifiers;
    }

    /**
     * 
     * @param secondaryIdentifiers
     *            secondaryIdentifiers
     */
    public void setSecondaryIdentifiers(List<String> secondaryIdentifiers) {
        this.secondaryIdentifiers = secondaryIdentifiers;
    }

}