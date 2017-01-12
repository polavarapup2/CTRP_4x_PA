/**
 *
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 */
public class StudyProtocolStageDTO extends AbstractStudyProtocolDTO {

    private static final long serialVersionUID = 7235772554482606139L;
    private St localProtocolIdentifier;
    private St nctIdentifier;
    private St trialType;

    private Ii leadOrganizationIdentifier;
    private Ii piIdentifier;
    private Ii sponsorIdentifier;

    private St responsiblePartyType;
    private St responsibleTitle;
    private Ii responsibleIdentifier;
    private Ii responsibleAffilId;
    private Ii responsibleGenericContactIdentifier;
    private St contactPhone;
    private St contactEmail;

    private List<Ii> summaryFourOrgIdentifiers = new ArrayList<Ii>();
    private Cd summaryFourFundingCategoryCode;
    private Cd trialStatusCode;
    private Ts trialStatusDate;
    private St statusReason;

    private Bl nciDesignatedCancerCenterIndicator;
    private Ii oversightAuthorityCountryId;
    private Ii oversightAuthorityOrgId;
    private Ii submitterOrganizationIdentifier;
    private Ii siteProtocolIdentifier;
    private Ii sitePiIdentifier;
    private Int siteTargetAccrual;
    private Cd siteSummaryFourFundingTypeCode;
    private St siteProgramCodeText;
    private Cd siteRecruitmentStatus;
    private Ts siteRecruitmentStatusDate;
    private Ts opendedForAccrualDate;
    private Ts closedForAccrualDate;
    private Bl piInitiatedIndicator;
    private Bl siteNciDesignatedCancerCenterIndicator;
    //TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolDTO
    //once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
    private List<Ii> secondaryIdentifierList = new ArrayList<Ii>();
    
    private Cd studyModelCode;
    private St studyModelOtherText;
    private Cd timePerspectiveCode;
    private St timePerspectiveOtherText;    
    private Cd studySubtypeCode;
    private St secondaryPurposes;
    private St secondaryPurposeOtherText;
    private Bl nciGrant;
    
    private String statusHistory;

    /**
     * @return the localProtocolIdentifier
     */
    public St getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }
    /**
     * @param localProtocolIdentifier the localProtocolIdentifier to set
     */
    public void setLocalProtocolIdentifier(St localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }
    /**
     * @return the nctIdentifier
     */
    public St getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(St nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }
    /**
     * @return the trialType
     */
    public St getTrialType() {
        return trialType;
    }
    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(St trialType) {
        this.trialType = trialType;
    }
    /**
     * @return the leadOrganizationIdentifier
     */
    public Ii getLeadOrganizationIdentifier() {
        return leadOrganizationIdentifier;
    }
    /**
     * @param leadOrganizationIdentifier the leadOrganizationIdentifier to set
     */
    public void setLeadOrganizationIdentifier(Ii leadOrganizationIdentifier) {
        this.leadOrganizationIdentifier = leadOrganizationIdentifier;
    }
    /**
     * @return the piIdentifier
     */
    public Ii getPiIdentifier() {
        return piIdentifier;
    }
    /**
     * @param piIdentifier the piIdentifier to set
     */
    public void setPiIdentifier(Ii piIdentifier) {
        this.piIdentifier = piIdentifier;
    }
    /**
     * @return the sponsorIdentifier
     */
    public Ii getSponsorIdentifier() {
        return sponsorIdentifier;
    }
    /**
     * @param sponsorIdentifier the sponsorIdentifier to set
     */
    public void setSponsorIdentifier(Ii sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }
    /**
     * @return the responsiblePartyType
     */
    public St getResponsiblePartyType() {
        return responsiblePartyType;
    }
    /**
     * @param responsiblePartyType the responsiblePartyType to set
     */
    public void setResponsiblePartyType(St responsiblePartyType) {
        this.responsiblePartyType = responsiblePartyType;
    }
    /**
     * @return the responsibleIdentifier
     */
    public Ii getResponsibleIdentifier() {
        return responsibleIdentifier;
    }
    /**
     * @param responsibleIdentifier the responsibleIdentifier to set
     */
    public void setResponsibleIdentifier(Ii responsibleIdentifier) {
        this.responsibleIdentifier = responsibleIdentifier;
    }
    /**
     * @return the contactPhone
     */
    public St getContactPhone() {
        return contactPhone;
    }
    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(St contactPhone) {
        this.contactPhone = contactPhone;
    }
    /**
     * @return the contactEmail
     */
    public St getContactEmail() {
        return contactEmail;
    }
    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(St contactEmail) {
        this.contactEmail = contactEmail;
    }
    /**
     * @return the summaryFourOrgIdentifiers
     */
    public List<Ii> getSummaryFourOrgIdentifiers() {
        return summaryFourOrgIdentifiers;
    }
    /**
     * @param summaryFourOrgIdentifiers the summaryFourOrgIdentifiers to set
     */
    public void setSummaryFourOrgIdentifiers(List<Ii> summaryFourOrgIdentifiers) {
        this.summaryFourOrgIdentifiers = summaryFourOrgIdentifiers;
    }
    /**
     * @return the summaryFourFundingCategoryCode
     */
    public Cd getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * @param summaryFourFundingCategoryCode the summaryFourFundingCategoryCode to set
     */
    public void setSummaryFourFundingCategoryCode(Cd summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * @return the statusCode
     */
    public Cd getTrialStatusCode() {
        return trialStatusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setTrialStatusCode(Cd statusCode) {
        this.trialStatusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public Ts getTrialStatusDate() {
        return trialStatusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setTrialStatusDate(Ts statusDate) {
        this.trialStatusDate = statusDate;
    }
    /**
     * @return the statusReason
     */
    public St getStatusReason() {
        return statusReason;
    }
    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(St statusReason) {
        this.statusReason = statusReason;
    }
    /**
     * @return the nciDesignatedCancerCenterIndicator
     */
    public Bl getNciDesignatedCancerCenterIndicator() {
        return nciDesignatedCancerCenterIndicator;
    }
    /**
     * @param nciDesignatedCancerCenterIndicator the nciDesignatedCancerCenterIndicator to set
     */
    public void setNciDesignatedCancerCenterIndicator(
            Bl nciDesignatedCancerCenterIndicator) {
        this.nciDesignatedCancerCenterIndicator = nciDesignatedCancerCenterIndicator;
    }
    /**
     * @return the oversightAuthorityCountryId
     */
    public Ii getOversightAuthorityCountryId() {
        return oversightAuthorityCountryId;
    }
    /**
     * @param oversightAuthorityCountryId the oversightAuthorityCountryId to set
     */
    public void setOversightAuthorityCountryId(Ii oversightAuthorityCountryId) {
        this.oversightAuthorityCountryId = oversightAuthorityCountryId;
    }
    /**
     * @return the oversightAuthorityOrgId
     */
    public Ii getOversightAuthorityOrgId() {
        return oversightAuthorityOrgId;
    }
    /**
     * @param oversightAuthorityOrgId the oversightAuthorityOrgId to set
     */
    public void setOversightAuthorityOrgId(Ii oversightAuthorityOrgId) {
        this.oversightAuthorityOrgId = oversightAuthorityOrgId;
    }
    /**
     * @return the submitterOrganizationIdentifier
     */
    public Ii getSubmitterOrganizationIdentifier() {
        return submitterOrganizationIdentifier;
    }
    /**
     * @param submitterOrganizationIdentifier the submitterOrganizationIdentifier to set
     */
    public void setSubmitterOrganizationIdentifier(
            Ii submitterOrganizationIdentifier) {
        this.submitterOrganizationIdentifier = submitterOrganizationIdentifier;
    }
    /**
     * @return the siteProtocolIdentifier
     */
    public Ii getSiteProtocolIdentifier() {
        return siteProtocolIdentifier;
    }
    /**
     * @param siteProtocolIdentifier the siteProtocolIdentifier to set
     */
    public void setSiteProtocolIdentifier(Ii siteProtocolIdentifier) {
        this.siteProtocolIdentifier = siteProtocolIdentifier;
    }
    /**
     * @return the sitePiIdentifier
     */
    public Ii getSitePiIdentifier() {
        return sitePiIdentifier;
    }
    /**
     * @param sitePiIdentifier the sitePiIdentifier to set
     */
    public void setSitePiIdentifier(Ii sitePiIdentifier) {
        this.sitePiIdentifier = sitePiIdentifier;
    }
    /**
     * @return the siteTargetAccrual
     */
    public Int getSiteTargetAccrual() {
        return siteTargetAccrual;
    }
    /**
     * @param siteTargetAccrual the siteTargetAccrual to set
     */
    public void setSiteTargetAccrual(Int siteTargetAccrual) {
        this.siteTargetAccrual = siteTargetAccrual;
    }
    /**
     * @return the siteSummaryFourFundingTypeCode
     */
    public Cd getSiteSummaryFourFundingTypeCode() {
        return siteSummaryFourFundingTypeCode;
    }
    /**
     * @param siteSummaryFourFundingTypeCode the siteSummaryFourFundingTypeCode to set
     */
    public void setSiteSummaryFourFundingTypeCode(Cd siteSummaryFourFundingTypeCode) {
        this.siteSummaryFourFundingTypeCode = siteSummaryFourFundingTypeCode;
    }
    /**
     * @return the siteProgramCodeText
     */
    public St getSiteProgramCodeText() {
        return siteProgramCodeText;
    }
    /**
     * @param siteProgramCodeText the siteProgramCodeText to set
     */
    public void setSiteProgramCodeText(St siteProgramCodeText) {
        this.siteProgramCodeText = siteProgramCodeText;
    }
    /**
     * @return the siteRecruitmentStatus
     */
    public Cd getSiteRecruitmentStatus() {
        return siteRecruitmentStatus;
    }
    /**
     * @param siteRecruitmentStatus the siteRecruitmentStatus to set
     */
    public void setSiteRecruitmentStatus(Cd siteRecruitmentStatus) {
        this.siteRecruitmentStatus = siteRecruitmentStatus;
    }
    /**
     * @return the siteRecruitmentStatusDate
     */
    public Ts getSiteRecruitmentStatusDate() {
        return siteRecruitmentStatusDate;
    }
    /**
     * @param siteRecruitmentStatusDate the siteRecruitmentStatusDate to set
     */
    public void setSiteRecruitmentStatusDate(Ts siteRecruitmentStatusDate) {
        this.siteRecruitmentStatusDate = siteRecruitmentStatusDate;
    }
    /**
     * @return the opendedForAccrualDate
     */
    public Ts getOpendedForAccrualDate() {
        return opendedForAccrualDate;
    }
    /**
     * @param opendedForAccrualDate the opendedForAccrualDate to set
     */
    public void setOpendedForAccrualDate(Ts opendedForAccrualDate) {
        this.opendedForAccrualDate = opendedForAccrualDate;
    }
    /**
     * @return the closedForAccrualDate
     */
    public Ts getClosedForAccrualDate() {
        return closedForAccrualDate;
    }
    /**
     * @param closedForAccrualDate the closedForAccrualDate to set
     */
    public void setClosedForAccrualDate(Ts closedForAccrualDate) {
        this.closedForAccrualDate = closedForAccrualDate;
    }
    /**
     * @return the piInitiatedIndicator
     */
    public Bl getPiInitiatedIndicator() {
        return piInitiatedIndicator;
    }
    /**
     * @param piInitiatedIndicator the piInitiatedIndicator to set
     */
    public void setPiInitiatedIndicator(Bl piInitiatedIndicator) {
        this.piInitiatedIndicator = piInitiatedIndicator;
    }
    /**
     * @return the siteNciDesignatedCancerCenterIndicator
     */
    public Bl getSiteNciDesignatedCancerCenterIndicator() {
        return siteNciDesignatedCancerCenterIndicator;
    }
    /**
     * @param siteNciDesignatedCancerCenterIndicator the siteNciDesignatedCancerCenterIndicator to set
     */
    public void setSiteNciDesignatedCancerCenterIndicator(
            Bl siteNciDesignatedCancerCenterIndicator) {
        this.siteNciDesignatedCancerCenterIndicator = siteNciDesignatedCancerCenterIndicator;
    }

    /**
     * @return the secondaryIdentifierList
     */
    public List<Ii> getSecondaryIdentifierList() {
        return secondaryIdentifierList;
    }

    /**
     * @param secondaryIdentifierList the secondaryIdentifierList to set
     */
    public void setSecondaryIdentifierList(List<Ii> secondaryIdentifierList) {
        this.secondaryIdentifierList = secondaryIdentifierList;
    }
    /**
     * @return the responsibleGenericContactIdentifier
     */
    public Ii getResponsibleGenericContactIdentifier() {
        return responsibleGenericContactIdentifier;
    }
    /**
     * @param responsibleGenericContactIdentifier the responsibleGenericContactIdentifier to set
     */
    public void setResponsibleGenericContactIdentifier(
            Ii responsibleGenericContactIdentifier) {
        this.responsibleGenericContactIdentifier = responsibleGenericContactIdentifier;
    }
    /**
     * @return the studyModelCode
     */
    public Cd getStudyModelCode() {
        return studyModelCode;
    }
    /**
     * @param studyModelCode the studyModelCode to set
     */
    public void setStudyModelCode(Cd studyModelCode) {
        this.studyModelCode = studyModelCode;
    }
    /**
     * @return the studyModelOtherText
     */
    public St getStudyModelOtherText() {
        return studyModelOtherText;
    }
    /**
     * @param studyModelOtherText the studyModelOtherText to set
     */
    public void setStudyModelOtherText(St studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }
    /**
     * @return the timePerspectiveCode
     */
    public Cd getTimePerspectiveCode() {
        return timePerspectiveCode;
    }
    /**
     * @param timePerspectiveCode the timePerspectiveCode to set
     */
    public void setTimePerspectiveCode(Cd timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
    }
    /**
     * @return the timePerspectiveOtherText
     */
    public St getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }
    /**
     * @param timePerspectiveOtherText the timePerspectiveOtherText to set
     */
    public void setTimePerspectiveOtherText(St timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }
    /**
     * @return the studySubtypeCode
     */
    public Cd getStudySubtypeCode() {
        return studySubtypeCode;
    }
    /**
     * @param studySubtypeCode the studySubtypeCode to set
     */
    public void setStudySubtypeCode(Cd studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }
    /**
     * @return the secondaryPurposes
     */
    public St getSecondaryPurposes() {
        return secondaryPurposes;
    }
    /**
     * @param secondaryPurposes the secondaryPurposes to set
     */
    public void setSecondaryPurposes(St secondaryPurposes) {
        this.secondaryPurposes = secondaryPurposes;
    }
    /**
     * @return the secondaryPurposeOtherText
     */
    public St getSecondaryPurposeOtherText() {
        return secondaryPurposeOtherText;
    }
    /**
     * @param secondaryPurposeOtherText the secondaryPurposeOtherText to set
     */
    public void setSecondaryPurposeOtherText(St secondaryPurposeOtherText) {
        this.secondaryPurposeOtherText = secondaryPurposeOtherText;
    }
    /**
     * @return the nciGrant
     */
    public Bl getNciGrant() {
        return nciGrant;
    }
    /**
     * @param nciGrant the nciGrant to set
     */
    public void setNciGrant(Bl nciGrant) {
        this.nciGrant = nciGrant;
    }
    /**
     * @return the responsibleTitle
     */
    public St getResponsibleTitle() {
        return responsibleTitle;
    }
    /**
     * @param responsibleTitle the responsibleTitle to set
     */
    public void setResponsibleTitle(St responsibleTitle) {
        this.responsibleTitle = responsibleTitle;
    }
    /**
     * @return the responsibleAffilId
     */
    public Ii getResponsibleAffilId() {
        return responsibleAffilId;
    }
    /**
     * @param responsibleAffilId the responsibleAffilId to set
     */
    public void setResponsibleAffilId(Ii responsibleAffilId) {
        this.responsibleAffilId = responsibleAffilId;
    }
    /**
     * @return the statusHistory
     */
    public String getStatusHistory() {
        return statusHistory;
    }
    /**
     * @param statusHistory the statusHistory to set
     */
    public void setStatusHistory(String statusHistory) {
        this.statusHistory = statusHistory;
    }

}
