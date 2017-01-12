/**
 *
 */
package gov.nih.nci.registry.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Criteria  class for Registry Protocol search.
 * @author Bala Nair
 *
 */
public class SearchProtocolCriteria implements Serializable {
    private static final long serialVersionUID = -4134080705571406618L;
    private String identifierType;
    private String identifier;
    private String officialTitle;
    private String organizationId;
    private String participatingSiteId;
    private String leadAndParticipatingOrgId;
    private String organizationName;
    private String participatingSiteName;
    private String leadAndParticipatingOrgName;
    private String phaseCode;
    private String primaryPurposeCode;
    private String organizationType;
    private boolean myTrialsOnly;
    private String principalInvestigatorId;
    private String principalInvestigatorName;
    private String phaseAdditionalQualifierCode;
    private String trialCategory;
    private String holdStatus;
    
    /**
     * @return the identifierType
     */
    public String getIdentifierType() {
        return identifierType;
    }
    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * @return the organizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }
    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    /**
     * @param participatingSiteId the participatingSiteId to set
     */
    public void setParticipatingSiteId(String participatingSiteId) {
        this.participatingSiteId = participatingSiteId;
    }
    /**
     * @return the participatingSiteId
     */
    public String getParticipatingSiteId() {
        return participatingSiteId;
    }
    /**
     * @return the phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * @return the primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return the organizationType
     */
    public String getOrganizationType() {
        return organizationType;
    }
    /**
     * @param organizationType the organizationType to set
     */
    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }
    /**
     * @return the myTrialsOnly
     */
    public boolean isMyTrialsOnly() {
        return myTrialsOnly;
    }
    /**
     * @param myTrialsOnly the myTrialsOnly to set
     */
    public void setMyTrialsOnly(boolean myTrialsOnly) {
        this.myTrialsOnly = myTrialsOnly;
    }
    /**
     * @return the principalInvestigatorId
     */
    public String getPrincipalInvestigatorId() {
        return principalInvestigatorId;
    }
    /**
     * @param principalInvestigatorId the principalInvestigatorId to set
     */
    public void setPrincipalInvestigatorId(String principalInvestigatorId) {
        this.principalInvestigatorId = principalInvestigatorId;
    }
    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(String phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }
    /**
     * @return the phaseAdditionalQualifierCode
     */
    public String getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }
    
    /**
     * Gets the trial category.
     * 
     * @return trialCategory
     */
    public String getTrialCategory() {
        return trialCategory;
    }

    /**
     * Sets trial category.
     * 
     * @param trialCategory
     *            trialCategory
     */
    public void setTrialCategory(String trialCategory) {
        this.trialCategory = trialCategory;
    }

    /**
     * @return the leadAndParticipatingOrgId
     */
    public String getLeadAndParticipatingOrgId() {
        return leadAndParticipatingOrgId;
    }

    /**
     * @param leadAndParticipatingOrgId
     *            the leadAndParticipatingOrgId to set
     */
    public void setLeadAndParticipatingOrgId(String leadAndParticipatingOrgId) {
        this.leadAndParticipatingOrgId = leadAndParticipatingOrgId;
    }
    /**
     * @return the holdStatus
     */
    public String getHoldStatus() {
        return holdStatus;
    }
    /**
     * @param holdStatus the holdStatus to set
     */
    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus;
    }
    /**
     * @return isNctIdentifierProvided
     */
    public boolean isNctIdentifierProvided() {        
        return StringUtils.startsWithIgnoreCase(getIdentifier(), "NCT");
    }
    
    /**
     * principalInvestigatorName
     * 
     * @return principalInvestigatorName
     */
    public String getPrincipalInvestigatorName() {
        return principalInvestigatorName;
    }

    /**
     * 
     * @param principalInvestigatorName
     *            principalInvestigatorName to set
     */
    public void setPrincipalInvestigatorName(String principalInvestigatorName) {
        this.principalInvestigatorName = principalInvestigatorName;
    }

    /**
     * 
     * @return lead organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * set lead organizationName
     * 
     * @param organizationName
     *            organizationName
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * 
     * @return participatingSiteName
     */
    public String getParticipatingSiteName() {
        return participatingSiteName;
    }

    /**
     * set participatingSiteName
     * 
     * @param participatingSiteName
     *            participatingSiteName
     */
    public void setParticipatingSiteName(String participatingSiteName) {
        this.participatingSiteName = participatingSiteName;
    }

    /**
     * 
     * @return lead organization or participating site name
     */
    public String getLeadAndParticipatingOrgName() {
        return leadAndParticipatingOrgName;
    }

    /**
     * set lead organization or participating site name
     * 
     * @param leadAndParticipatingOrgName
     *            leadAndParticipatingOrgName
     */
    public void setLeadAndParticipatingOrgName(
            String leadAndParticipatingOrgName) {
        this.leadAndParticipatingOrgName = leadAndParticipatingOrgName;
    }
    
}
