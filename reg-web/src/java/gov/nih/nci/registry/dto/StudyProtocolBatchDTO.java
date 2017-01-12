/**
 *
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.iso21090.Ii;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;

/**
 * @author Vrushali
 *
 */
public class StudyProtocolBatchDTO implements Serializable { // NOPMD
    private static final long serialVersionUID = 8816656630380231435L;
    private String uniqueTrialId;
    private String localProtocolIdentifier;
    private String nctNumber;
    private String title;
    private String trialType;
    private String primaryPurpose;
    private String primaryPurposeAdditionalQualifierCode;
    private String primaryPurposeOtherText;
    private String phase;
    private String phaseAdditionalQualifierCode;
    //Sponsor
    private String sponsorPOId;
    private String sponsorOrgName;
    private String sponsorStreetAddress;
    private String sponsorCity;
    private String sponsorState;
    private String sponsorZip;
    private String sponsorCountry;
    private String sponsorEmail;
    private String sponsorPhone;
    private String sponsorTTY;
    private String sponsorFax;
    private String sponsorURL;

    private String responsibleParty;
    private String sponsorContactPOId;
    private String sponsorContactFName;
    private String sponsorContactMName;
    private String sponsorContactLName;
    private String sponsorContactStreetAddress;
    private String sponsorContactCity;
    private String sponsorContactState;
    private String sponsorContactZip;
    private String sponsorContactCountry;
    private String sponsorContactEmail;
    private String sponsorContactPhone;
    private String sponsorContactTTY;
    private String sponsorContactFax;
    private String sponsorContactUrl;
    //generic contact
    private String responsibleGenericContactName;
    private String sponsorContactType;

    //lead org
    private String leadOrgPOId;
    private String leadOrgName;
    private String leadOrgStreetAddress;
    private String leadOrgCity;
    private String leadOrgState;
    private String leadOrgZip;
    private String leadOrgCountry;
    private String leadOrgEmail;
    private String leadOrgPhone;
    private String leadOrgTTY;
    private String leadOrgFax;
    private String leadOrgUrl;
    private String leadOrgType;

    //PI
    private String piPOId;
    private String piFirstName;
    private String piMiddleName;
    private String piLastName;
    private String piStreetAddress;
    private String piCity;
    private String piState;
    private String piZip;
    private String piCountry;
    private String piEmail;
    private String piPhone;
    private String piTTY;
    private String piFax;
    private String piUrl;
    
    // Party Investigator
    private String partyInvestigatorPOId;
    private String partyInvestigatorFirstName;
    private String partyInvestigatorMiddleName;
    private String partyInvestigatorLastName;
    private String partyInvestigatorStreetAddress;
    private String partyInvestigatorCity;
    private String partyInvestigatorState;
    private String partyInvestigatorZip;
    private String partyInvestigatorCountry;
    private String partyInvestigatorEmail;
    private String partyInvestigatorPhone;
    private String partyInvestigatorTTY;
    private String partyInvestigatorFax;
    private String partyInvestigatorUrl;
    
    private String partyInvestigatorTitle;
    
    //  responsible party affiliation
    private String partyAffiliationPOId;
    private String partyAffiliationName;
    private String partyAffiliationStreetAddress;
    private String partyAffiliationCity;
    private String partyAffiliationState;
    private String partyAffiliationZip;
    private String partyAffiliationCountry;
    private String partyAffiliationEmail;
    private String partyAffiliationPhone;
    private String partyAffiliationTTY;
    private String partyAffiliationFax;
    private String partyAffiliationUrl;

    private String summ4FundingCat;
    private String summ4OrgPOId;
    private String summ4OrgName;
    private String summ4OrgStreetAddress;
    private String summ4City;
    private String summ4State;
    private String summ4Zip;
    private String summ4Country;
    private String summ4Email;
    private String summ4Phone;
    private String summ4TTY;
    private String summ4Fax;
    private String summ4Url;
    private String programCodeText;

    private String nihGrantFundingMechanism;
    private String nihGrantInstituteCode;
    private String nihGrantSrNumber;
    private String nihGrantNCIDivisionCode;
    private String nihGrantFundingPct;
    private String currentTrialStatus;
    private String reasonForStudyStopped;
    private String currentTrialStatusDate;
    private String studyStartDate;
    private String studyStartDateType;
    private String primaryCompletionDate;
    private String primaryCompletionDateType;
    private String completionDate;
    private String completionDateType;

    private String indType;
    private String indNumber;
    private String indGrantor;
    private String indHolderType;
    private String indNIHInstitution;
    private String indNCIDivision;
    private String indHasExpandedAccess;
    private String indExpandedAccessStatus;
    private String exemptIndicator;

    private String protcolDocumentFileName;
    private String irbApprovalDocumentFileName;
    private String participatinSiteDocumentFileName;
    private String informedConsentDocumentFileName;
    private String otherTrialRelDocumentFileName;

    private String submissionType;
    private String nciTrialIdentifier;
    private String amendmentNumber;
    private String amendmentDate;
    private String changeRequestDocFileName;
    private String protocolHighlightDocFileName;

    private String oversightAuthorityCountry;
    private String oversightOrgName;
    private String fdaRegulatoryInformationIndicator;
    private String section801Indicator;
    private String delayedPostingIndicator;
    private String dataMonitoringCommitteeAppointedIndicator;
    private static final int ORG_NAME_MAX_LENGTH = 160;
    private static final int PCTL = 200;
    private String ctepIdentifier;
    private String dcpIdentifier;
    private boolean ctGovXmlIndicator = true;
    private Boolean nciGrant;
    private List<Ii> otherTrialIdentifiers;

    /**
     * @return the otherTrialIdentifiers
     */
     public List<Ii> getOtherTrialIdentifiers() {
        return otherTrialIdentifiers;
     }

     /**
      * @param otherTrialIdentifiers the otherTrialIdentifiers to set
      */
     public void setOtherTrialIdentifiers(List<Ii> otherTrialIdentifiers) {
       this.otherTrialIdentifiers = otherTrialIdentifiers;
     }

    /**
     * . Default Constructor
     */
    public StudyProtocolBatchDTO() {
        super();
    }

    /**
     * @return the currentTrialStatus
     */
    @NotEmpty(message = "Current Trial Status is required.\n")
    public String getCurrentTrialStatus() {
        return currentTrialStatus;
    }

    /**
     * @param currentTrialStatus the currentTrialStatus to set
     */
    public void setCurrentTrialStatus(String currentTrialStatus) {
        this.currentTrialStatus = currentTrialStatus;
    }

    /**
     * @return the currentTrialStatusDate
     */
    @NotEmpty(message = "Current Trial Status Date is required.\n")
    public String getCurrentTrialStatusDate() {
        return currentTrialStatusDate;
    }

    /**
     * @param currentTrialStatusDate the currentTrialStatusDate to set
     */
    public void setCurrentTrialStatusDate(String currentTrialStatusDate) {
        this.currentTrialStatusDate = currentTrialStatusDate;
    }

    /**
     *
     * @return lead organization PO Id
     */
    public String getLeadOrgPOId() {
        return leadOrgPOId;
    }

    /**
     *
     * @param leadOrgPOId Org PO Id
     */
    public void setLeadOrgPOId(String leadOrgPOId) {
        this.leadOrgPOId = leadOrgPOId;
    }

    /**
     * @return the leadOrgCity
     */
    public String getLeadOrgCity() {
        return leadOrgCity;
    }

    /**
     * @param leadOrgCity the leadOrgCity to set
     */
    public void setLeadOrgCity(String leadOrgCity) {
        this.leadOrgCity = leadOrgCity;
    }

    /**
     * @return the leadOrgCountry
     */
    public String getLeadOrgCountry() {
        return leadOrgCountry;
    }

    /**
     * @param leadOrgCountry the leadOrgCountry to set
     */
    public void setLeadOrgCountry(String leadOrgCountry) {
        this.leadOrgCountry = leadOrgCountry;
    }

    /**
     * @return the leadOrgEmail
     */
    public String getLeadOrgEmail() {
        return leadOrgEmail;
    }

    /**
     * @param leadOrgEmail the leadOrgEmail to set
     */
    public void setLeadOrgEmail(String leadOrgEmail) {
        this.leadOrgEmail = leadOrgEmail;
    }

    /**
     * @return the leadOrgFax
     */
    public String getLeadOrgFax() {
        return leadOrgFax;
    }

    /**
     * @param leadOrgFax the leadOrgFax to set
     */
    public void setLeadOrgFax(String leadOrgFax) {
        this.leadOrgFax = leadOrgFax;
    }

    /**
     * @return the leadOrgName
     */
    @org.hibernate.validator.Length(message = "Lead Organization's Name must be 160 characters max.\n",
            max = ORG_NAME_MAX_LENGTH)
    public String getLeadOrgName() {
        return leadOrgName;
    }

    /**
     * @param leadOrgName the leadOrgName to set
     */
    public void setLeadOrgName(String leadOrgName) {
        this.leadOrgName = leadOrgName;
    }

    /**
     * @return the leadOrgPhone
     */
    public String getLeadOrgPhone() {
        return leadOrgPhone;
    }

    /**
     * @param leadOrgPhone the leadOrgPhone to set
     */
    public void setLeadOrgPhone(String leadOrgPhone) {
        this.leadOrgPhone = leadOrgPhone;
    }

    /**
     * @return the leadOrgState
     */
    public String getLeadOrgState() {
        return leadOrgState;
    }

    /**
     * @param leadOrgState the leadOrgState to set
     */
    public void setLeadOrgState(String leadOrgState) {
        this.leadOrgState = leadOrgState;
    }

    /**
     * @return the leadOrgStreetAddress
     */
    public String getLeadOrgStreetAddress() {
        return leadOrgStreetAddress;
    }

    /**
     * @param leadOrgStreetAddress the leadOrgStreetAddress to set
     */
    public void setLeadOrgStreetAddress(String leadOrgStreetAddress) {
        this.leadOrgStreetAddress = leadOrgStreetAddress;
    }

    /**
     * @return the leadOrgTTY
     */
    public String getLeadOrgTTY() {
        return leadOrgTTY;
    }

    /**
     * @param leadOrgTTY the leadOrgTTY to set
     */
    public void setLeadOrgTTY(String leadOrgTTY) {
        this.leadOrgTTY = leadOrgTTY;
    }

    /**
     * @return the leadOrgType
     */
    public String getLeadOrgType() {
        return leadOrgType;
    }

    /**
     * @param leadOrgType the leadOrgType to set
     */
    public void setLeadOrgType(String leadOrgType) {
        this.leadOrgType = leadOrgType;
    }

    /**
     * @return the leadOrgUrl
     */
    public String getLeadOrgUrl() {
        return leadOrgUrl;
    }

    /**
     * @param leadOrgUrl the leadOrgUrl to set
     */
    public void setLeadOrgUrl(String leadOrgUrl) {
        this.leadOrgUrl = leadOrgUrl;
    }

    /**
     * @return the leadOrgZip
     */
    public String getLeadOrgZip() {
        return leadOrgZip;
    }

    /**
     * @param leadOrgZip the leadOrgZip to set
     */
    public void setLeadOrgZip(String leadOrgZip) {
        this.leadOrgZip = leadOrgZip;
    }

    /**
     * @return the localProtocolIdentifier
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }

    /**
     * @param localProtocolIdentifier the localProtocolIdentifier to set
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }

    /**
     * @param nctNumber the nctNumber to set
     */
    public void setNctNumber(String nctNumber) {
        this.nctNumber = nctNumber;
    }

    /**
     * @return the nctNumber
     */
    public String getNctNumber() {
        return nctNumber;
    }

    /**
     * @return the nihGrantFundingMechanism
     */
    public String getNihGrantFundingMechanism() {
        return nihGrantFundingMechanism;
    }

    /**
     * @param nihGrantFundingMechanism the nihGrantFundingMechanism to set
     */
    public void setNihGrantFundingMechanism(String nihGrantFundingMechanism) {
        this.nihGrantFundingMechanism = nihGrantFundingMechanism;
    }

    /**
     * @return the nihGrantInstituteCode
     */
    public String getNihGrantInstituteCode() {
        return nihGrantInstituteCode;
    }

    /**
     * @param nihGrantInstituteCode the nihGrantInstituteCode to set
     */
    public void setNihGrantInstituteCode(String nihGrantInstituteCode) {
        this.nihGrantInstituteCode = nihGrantInstituteCode;
    }

    /**
     * @return the nihGrantNCIDivisionCode
     */
    public String getNihGrantNCIDivisionCode() {
        return nihGrantNCIDivisionCode;
    }

    /**
     * @param nihGrantNCIDivisionCode the nihGrantNCIDivisionCode to set
     */
    public void setNihGrantNCIDivisionCode(String nihGrantNCIDivisionCode) {
        this.nihGrantNCIDivisionCode = nihGrantNCIDivisionCode;
    }

    /**
     * @return the nihGrantSrNumber
     */
    @Pattern(message = "Serial Number must be numeric.\n", regex = "^[0-9; ]+$")
    public String getNihGrantSrNumber() {
        return nihGrantSrNumber;
    }

    /**
     * @param nihGrantSrNumber the nihGrantSrNumber to set
     */
    public void setNihGrantSrNumber(String nihGrantSrNumber) {
        this.nihGrantSrNumber = nihGrantSrNumber;
    }

    /**
     * @return the nihGrantFundingPct
     */
    @Pattern(message = "Grant funding percent must be numeric (exclude percent sign).\n", regex = "^([0-9;.]+)?$")
    public String getNihGrantFundingPct() {
        return nihGrantFundingPct;
    }

    /**
     * @param nihGrantFundingPct the nihGrantFundingPct to set
     */
    public void setNihGrantFundingPct(String nihGrantFundingPct) {
        this.nihGrantFundingPct = nihGrantFundingPct;
    }

    /**
     * @return the phase
     */
    @NotEmpty(message = "Trial Phase is required.\n")
    public String getPhase() {
        return phase;
    }

    /**
     * @param phase the phase to set
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /**
     * @return the phaseAdditionalQualifierCode
     */
    public String getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }

    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(String phaseAdditionalQualifierCode) {
         this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }

    /**
     * @return the piCity
     */
    public String getPiCity() {
        return piCity;
    }

    /**
     * @param piCity the piCity to set
     */
    public void setPiCity(String piCity) {
        this.piCity = piCity;
    }

    /**
     * @return the piCountry
     */
    public String getPiCountry() {
        return piCountry;
    }

    /**
     * @param piCountry the piCountry to set
     */
    public void setPiCountry(String piCountry) {
        this.piCountry = piCountry;
    }

    /**
     * @return the piEmail
     */
    public String getPiEmail() {
        return piEmail;
    }

    /**
     * @param piEmail the piEmail to set
     */
    public void setPiEmail(String piEmail) {
        this.piEmail = piEmail;
    }

    /**
     * @return the piFax
     */
    public String getPiFax() {
        return piFax;
    }

    /**
     * @param piFax the piFax to set
     */
    public void setPiFax(String piFax) {
        this.piFax = piFax;
    }

    /**
     * @return the piFirstName
     */
    public String getPiFirstName() {
        return piFirstName;
    }

    /**
     * @param piFirstName the piFirstName to set
     */
    public void setPiFirstName(String piFirstName) {
        this.piFirstName = piFirstName;
    }

    /**
     * @return the piLastName
     */
    public String getPiLastName() {
        return piLastName;
    }

    /**
     * @param piLastName the piLastName to set
     */
    public void setPiLastName(String piLastName) {
        this.piLastName = piLastName;
    }

    /**
     * @return the piMiddleName
     */
    public String getPiMiddleName() {
        return piMiddleName;
    }

    /**
     * @param piMiddleName the piMiddleName to set
     */
    public void setPiMiddleName(String piMiddleName) {
        this.piMiddleName = piMiddleName;
    }

    /**
     *
     * @return PI person ID
     */
    public String getPiPOId() {
        return piPOId;
    }

    /**
     *
     * @param piPOId PI person ID
     */
    public void setPiPOId(String piPOId) {
        this.piPOId = piPOId;
    }

    /**
     * @return the piPhone
     */
    public String getPiPhone() {
        return piPhone;
    }

    /**
     * @param piPhone the piPhone to set
     */
    public void setPiPhone(String piPhone) {
        this.piPhone = piPhone;
    }

    /**
     * @return the piState
     */
    public String getPiState() {
        return piState;
    }

    /**
     * @param piState the piState to set
     */
    public void setPiState(String piState) {
        this.piState = piState;
    }

    /**
     * @return the piStreetAddress
     */
    public String getPiStreetAddress() {
        return piStreetAddress;
    }

    /**
     * @param piStreetAddress the piStreetAddress to set
     */
    public void setPiStreetAddress(String piStreetAddress) {
        this.piStreetAddress = piStreetAddress;
    }

    /**
     * @return the piTTY
     */
    public String getPiTTY() {
        return piTTY;
    }

    /**
     * @param piTTY the piTTY to set
     */
    public void setPiTTY(String piTTY) {
        this.piTTY = piTTY;
    }

    /**
     * @return the piUrl
     */
    public String getPiUrl() {
        return piUrl;
    }

    /**
     * @param piUrl the piUrl to set
     */
    public void setPiUrl(String piUrl) {
        this.piUrl = piUrl;
    }

    /**
     * @return the piZip
     */
    public String getPiZip() {
        return piZip;
    }

    /**
     * @param piZip the piZip to set
     */
    public void setPiZip(String piZip) {
        this.piZip = piZip;
    }

    /**
     * @return the primaryCompletionDate
     */
    public String getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(String primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * @return the primaryCompletionDateType
     */
    public String getPrimaryCompletionDateType() {
        return primaryCompletionDateType;
    }

    /**
     * @param primaryCompletionDateType the primaryCompletionDateType to set
     */
    public void setPrimaryCompletionDateType(String primaryCompletionDateType) {
        this.primaryCompletionDateType = primaryCompletionDateType;
    }

    /**
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the completionDateType
     */
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }

    /**
     * @return the primaryPurpose
     */
    @NotEmpty(message = "Trial Purpose is required.\n")
    public String getPrimaryPurpose() {
        return primaryPurpose;
    }

    /**
     * @param primaryPurpose the primaryPurpose to set
     */
    public void setPrimaryPurpose(String primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
    }

    /**
     * @return the primaryPurposeAdditionalQualifierCode
     */
    public String getPrimaryPurposeAdditionalQualifierCode() {
         return primaryPurposeAdditionalQualifierCode;
    }

    /**
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode to set
     */
    public void setPrimaryPurposeAdditionalQualifierCode(String primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }

    /**
     * @return the responsibleParty
     */
    public String getResponsibleParty() {
        return responsibleParty;
    }

    /**
     *
     * @return sponsor contact po id
     */
    public String getSponsorContactPOId() {
        return sponsorContactPOId;
    }

    /**
     *
     * @param sponsorContactPOId sponsor contact po id
     */
    public void setSponsorContactPOId(String sponsorContactPOId) {
        this.sponsorContactPOId = sponsorContactPOId;
    }

    /**
     * @param responsibleParty the responsibleParty to set
     */
    public void setResponsibleParty(String responsibleParty) {
        this.responsibleParty = responsibleParty;
    }

    /**
     * @return the sponsorCity
     */
    public String getSponsorCity() {
        return sponsorCity;
    }

    /**
     * @param sponsorCity the sponsorCity to set
     */
    public void setSponsorCity(String sponsorCity) {
        this.sponsorCity = sponsorCity;
    }

    /**
     * @return the sponsorContactCity
     */
    public String getSponsorContactCity() {
        return sponsorContactCity;
    }

    /**
     * @param sponsorContactCity the sponsorContactCity to set
     */
    public void setSponsorContactCity(String sponsorContactCity) {
        this.sponsorContactCity = sponsorContactCity;
    }

    /**
     * @return the sponsorContactCountry
     */
    public String getSponsorContactCountry() {
        return sponsorContactCountry;
    }

    /**
     * @param sponsorContactCountry the sponsorContactCountry to set
     */
    public void setSponsorContactCountry(String sponsorContactCountry) {
        this.sponsorContactCountry = sponsorContactCountry;
    }


    /**
     * @return the sponsorContactEmail
     */
    public String getSponsorContactEmail() {
        return sponsorContactEmail;
    }

    /**
     * @param sponsorContactEmail the sponsorContactEmail to set
     */
    public void setSponsorContactEmail(String sponsorContactEmail) {
        this.sponsorContactEmail = sponsorContactEmail;
    }

    /**
     * @return the sponsorContactFax
     */
    public String getSponsorContactFax() {
        return sponsorContactFax;
    }

    /**
     * @param sponsorContactFax the sponsorContactFax to set
     */
    public void setSponsorContactFax(String sponsorContactFax) {
        this.sponsorContactFax = sponsorContactFax;
    }

    /**
     * @return the sponsorContactFName
     */
    public String getSponsorContactFName() {
        return sponsorContactFName;
    }

    /**
     * @param sponsorContactFName the sponsorContactFName to set
     */
    public void setSponsorContactFName(String sponsorContactFName) {
        this.sponsorContactFName = sponsorContactFName;
    }

    /**
     * @return the sponsorContactLName
     */
    public String getSponsorContactLName() {
        return sponsorContactLName;
    }

    /**
     * @param sponsorContactLName the sponsorContactLName to set
     */
    public void setSponsorContactLName(String sponsorContactLName) {
        this.sponsorContactLName = sponsorContactLName;
    }

    /**
     * @return the sponsorContactMName
     */
    public String getSponsorContactMName() {
        return sponsorContactMName;
    }

    /**
     * @param sponsorContactMName the sponsorContactMName to set
     */
    public void setSponsorContactMName(String sponsorContactMName) {
        this.sponsorContactMName = sponsorContactMName;
    }

    /**
     * @return the sponsorContactPhone
     */
    public String getSponsorContactPhone() {
        return sponsorContactPhone;
    }

    /**
     * @param sponsorContactPhone the sponsorContactPhone to set
     */
    public void setSponsorContactPhone(String sponsorContactPhone) {
        this.sponsorContactPhone = sponsorContactPhone;
    }

    /**
     * @return the sponsorContactState
     */
    public String getSponsorContactState() {
        return sponsorContactState;
    }

    /**
     * @param sponsorContactState the sponsorContactState to set
     */
    public void setSponsorContactState(String sponsorContactState) {
        this.sponsorContactState = sponsorContactState;
    }

    /**
     * @return the sponsorContactStreetAddress
     */
    public String getSponsorContactStreetAddress() {
        return sponsorContactStreetAddress;
    }

    /**
     * @param sponsorContactStreetAddress the sponsorContactStreetAddress to set
     */
    public void setSponsorContactStreetAddress(String sponsorContactStreetAddress) {
        this.sponsorContactStreetAddress = sponsorContactStreetAddress;
    }

    /**
     * @return the sponsorContactTTY
     */
    public String getSponsorContactTTY() {
        return sponsorContactTTY;
    }

    /**
     * @param sponsorContactTTY the sponsorContactTTY to set
     */
    public void setSponsorContactTTY(String sponsorContactTTY) {
        this.sponsorContactTTY = sponsorContactTTY;
    }

    /**
     * @return the sponsorContactUrl
     */
    public String getSponsorContactUrl() {
        return sponsorContactUrl;
    }

    /**
     * @param sponsorContactUrl the sponsorContactUrl to set
     */
    public void setSponsorContactUrl(String sponsorContactUrl) {
        this.sponsorContactUrl = sponsorContactUrl;
    }

    /**
     * @return the sponsorContactZip
     */
    public String getSponsorContactZip() {
        return sponsorContactZip;
    }

    /**
     * @param sponsorContactZip the sponsorContactZip to set
     */
    public void setSponsorContactZip(String sponsorContactZip) {
        this.sponsorContactZip = sponsorContactZip;
    }

    /**
     * @return the sponsorCountry
     */
    public String getSponsorCountry() {
        return sponsorCountry;
    }

    /**
     * @param sponsorCountry the sponsorCountry to set
     */
    public void setSponsorCountry(String sponsorCountry) {
        this.sponsorCountry = sponsorCountry;
    }

    /**
     *
     * @return sponsor PO ID
     */
    public String getSponsorPOId() {
        return sponsorPOId;
    }

    /**
     *
     * @param sponsorPOId sponsor PO ID
     */
    public void setSponsorPOId(String sponsorPOId) {
        this.sponsorPOId = sponsorPOId;
    }

    /**
     * @return the sponsorEmail
     */
    public String getSponsorEmail() {
        return sponsorEmail;
    }

    /**
     * @param sponsorEmail the sponsorEmail to set
     */
    public void setSponsorEmail(String sponsorEmail) {
        this.sponsorEmail = sponsorEmail;
    }

    /**
     * @return the sponsorFax
     */
    public String getSponsorFax() {
        return sponsorFax;
    }

    /**
     * @param sponsorFax the sponsorFax to set
     */
    public void setSponsorFax(String sponsorFax) {
        this.sponsorFax = sponsorFax;
    }

    /**
     * @return the sponsorOrgName
     */
    public String getSponsorOrgName() {
        return sponsorOrgName;
    }

    /**
     * @param sponsorOrgName the sponsorOrgName to set
     */
    public void setSponsorOrgName(String sponsorOrgName) {
        this.sponsorOrgName = sponsorOrgName;
    }

    /**
     * @return the sponsorPhone
     */
    public String getSponsorPhone() {
        return sponsorPhone;
    }

    /**
     * @param sponsorPhone the sponsorPhone to set
     */
    public void setSponsorPhone(String sponsorPhone) {
        this.sponsorPhone = sponsorPhone;
    }

    /**
     * @return the sponsorState
     */
    public String getSponsorState() {
        return sponsorState;
    }

    /**
     * @param sponsorState the sponsorState to set
     */
    public void setSponsorState(String sponsorState) {
        this.sponsorState = sponsorState;
    }

    /**
     * @return the sponsorStreetAddress
     */
    public String getSponsorStreetAddress() {
        return sponsorStreetAddress;
    }

    /**
     * @param sponsorStreetAddress the sponsorStreetAddress to set
     */
    public void setSponsorStreetAddress(String sponsorStreetAddress) {
        this.sponsorStreetAddress = sponsorStreetAddress;
    }

    /**
     * @return the sponsorTTY
     */
    public String getSponsorTTY() {
        return sponsorTTY;
    }

    /**
     * @param sponsorTTY the sponsorTTY to set
     */
    public void setSponsorTTY(String sponsorTTY) {
        this.sponsorTTY = sponsorTTY;
    }

    /**
     * @return the sponsorURL
     */
    public String getSponsorURL() {
        return sponsorURL;
    }

    /**
     * @param sponsorURL the sponsorURL to set
     */
    public void setSponsorURL(String sponsorURL) {
        this.sponsorURL = sponsorURL;
    }

    /**
     * @return the sponsorZip
     */
    public String getSponsorZip() {
        return sponsorZip;
    }

    /**
     * @param sponsorZip the sponsorZip to set
     */
    public void setSponsorZip(String sponsorZip) {
        this.sponsorZip = sponsorZip;
    }

    /**
     * @return the studyStartDate
     */
    @NotEmpty(message = "Study Start Date is required. \n")
    public String getStudyStartDate() {
        return studyStartDate;
    }

    /**
     * @param studyStartDate the studyStartDate to set
     */
    public void setStudyStartDate(String studyStartDate) {
        this.studyStartDate = studyStartDate;
    }

    /**
     * @return the studyStartDateType
     */
    @NotEmpty(message = "Study Start Date Type is required.\n")
    public String getStudyStartDateType() {
        return studyStartDateType;
    }

    /**
     * @param studyStartDateType the studyStartDateType to set
     */
    public void setStudyStartDateType(String studyStartDateType) {
        this.studyStartDateType = studyStartDateType;
    }

    /**
     * @return the summ4City
     */
    public String getSumm4City() {
        return summ4City;
    }

    /**
     * @param summ4City the summ4City to set
     */
    public void setSumm4City(String summ4City) {
        this.summ4City = summ4City;
    }

    /**
     * @return the summ4Country
     */
    public String getSumm4Country() {
        return summ4Country;
    }

    /**
     * @param summ4Country the summ4Country to set
     */
    public void setSumm4Country(String summ4Country) {
        this.summ4Country = summ4Country;
    }

    /**
     * @return the summ4Email
     */
    public String getSumm4Email() {
        return summ4Email;
    }

    /**
     * @param summ4Email the summ4Email to set
     */
    public void setSumm4Email(String summ4Email) {
        this.summ4Email = summ4Email;
    }

    /**
     * @return the summ4Fax
     */
    public String getSumm4Fax() {
        return summ4Fax;
    }

    /**
     * @param summ4Fax the summ4Fax to set
     */
    public void setSumm4Fax(String summ4Fax) {
        this.summ4Fax = summ4Fax;
    }

    /**
     * @return the summ4FundingCat
     */
    public String getSumm4FundingCat() {
        return summ4FundingCat;
    }

    /**
     * @param summ4FundingCat the summ4FundingCat to set
     */
    public void setSumm4FundingCat(String summ4FundingCat) {
        this.summ4FundingCat = summ4FundingCat;
    }

    /**
     *
     * @return summary 4 PO ID
     */
    public String getSumm4OrgPOId() {
        return summ4OrgPOId;
    }
    /**
     *
     * @param summ4OrgPOId summary 4 PO ID
     */
    public void setSumm4OrgPOId(String summ4OrgPOId) {
        this.summ4OrgPOId = summ4OrgPOId;
    }

    /**
     * @return the summ4OrgName
     */
    public String getSumm4OrgName() {
        return summ4OrgName;
    }

    /**
     * @param summ4OrgName the summ4OrgName to set
     */
    public void setSumm4OrgName(String summ4OrgName) {
        this.summ4OrgName = summ4OrgName;
    }

    /**
     * @return the summ4OrgStreetAddress
     */
    public String getSumm4OrgStreetAddress() {
        return summ4OrgStreetAddress;
    }

    /**
     * @param summ4OrgStreetAddress the summ4OrgStreetAddress to set
     */
    public void setSumm4OrgStreetAddress(String summ4OrgStreetAddress) {
        this.summ4OrgStreetAddress = summ4OrgStreetAddress;
    }

    /**
     * @return the summ4Phone
     */
    public String getSumm4Phone() {
        return summ4Phone;
    }

    /**
     * @param summ4Phone the summ4Phone to set
     */
    public void setSumm4Phone(String summ4Phone) {
        this.summ4Phone = summ4Phone;
    }

    /**
     * @return the summ4State
     */
    public String getSumm4State() {
        return summ4State;
    }

    /**
     * @param summ4State the summ4State to set
     */
    public void setSumm4State(String summ4State) {
        this.summ4State = summ4State;
    }

    /**
     * @return the summ4TTY
     */
    public String getSumm4TTY() {
        return summ4TTY;
    }

    /**
     * @param summ4TTY the summ4TTY to set
     */
    public void setSumm4TTY(String summ4TTY) {
        this.summ4TTY = summ4TTY;
    }

    /**
     * @return the summ4Url
     */
    public String getSumm4Url() {
        return summ4Url;
    }

    /**
     * @param summ4Url the summ4Url to set
     */
    public void setSumm4Url(String summ4Url) {
        this.summ4Url = summ4Url;
    }

    /**
     * @return the summ4Zip
     */
    public String getSumm4Zip() {
        return summ4Zip;
    }

    /**
     * @param summ4Zip the summ4Zip to set
     */
    public void setSumm4Zip(String summ4Zip) {
        this.summ4Zip = summ4Zip;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the trialType
     */
    @NotEmpty(message = "Trial Type is required.\n")
    public String getTrialType() {
        return trialType;
    }

    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    /**
     * @return the uniqueTrialId
     */
    public String getUniqueTrialId() {
        return uniqueTrialId;
    }

    /**
     * @param uniqueTrialId the uniqueTrialId to set
     */
    public void setUniqueTrialId(String uniqueTrialId) {
        this.uniqueTrialId = uniqueTrialId;
    }


    /**
     * @return the indExpandedAccessStatus
     */
    public String getIndExpandedAccessStatus() {
        return indExpandedAccessStatus;
    }

    /**
     * @param indExpandedAccessStatus the indExpandedAccessStatus to set
     */
    public void setIndExpandedAccessStatus(String indExpandedAccessStatus) {
        this.indExpandedAccessStatus = indExpandedAccessStatus;
    }

    /**
     * @return the indGrantor
     */
    public String getIndGrantor() {
        return indGrantor;
    }

    /**
     * @param indGrantor the indGrantor to set
     */
    public void setIndGrantor(String indGrantor) {
        this.indGrantor = indGrantor;
    }

    /**
     * @return the indHasExpandedAccess
     */
    public String getIndHasExpandedAccess() {
        return indHasExpandedAccess;
    }

    /**
     * @param indHasExpandedAccess the indHasExpandedAccess to set
     */
    public void setIndHasExpandedAccess(String indHasExpandedAccess) {
        this.indHasExpandedAccess = indHasExpandedAccess;
    }

    /**
     * @return the indHolderType
     */
    public String getIndHolderType() {
        return indHolderType;
    }

    /**
     * @param indHolderType the indHolderType to set
     */
    public void setIndHolderType(String indHolderType) {
        this.indHolderType = indHolderType;
    }

    /**
     * @return the indNCIDivision
     */
    public String getIndNCIDivision() {
        return indNCIDivision;
    }

    /**
     * @param indNCIDivision the indNCIDivision to set
     */
    public void setIndNCIDivision(String indNCIDivision) {
        this.indNCIDivision = indNCIDivision;
    }

    /**
     * @return the indNIHInstitution
     */
    public String getIndNIHInstitution() {
        return indNIHInstitution;
    }

    /**
     * @param indNIHInstitution the indNIHInstitution to set
     */
    public void setIndNIHInstitution(String indNIHInstitution) {
        this.indNIHInstitution = indNIHInstitution;
    }

    /**
     * @return the indNumber
     */
    public String getIndNumber() {
        return indNumber;
    }

    /**
     * @param indNumber the indNumber to set
     */
    public void setIndNumber(String indNumber) {
        this.indNumber = indNumber;
    }

    /**
     * @return the indType
     */
    public String getIndType() {
        return indType;
    }

    /**
     * @param indType the indType to set
     */
    public void setIndType(String indType) {
        this.indType = indType;
    }

    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }

    /**
     * @param informedConsentDocumentFileName the informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(
            String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
    }

    /**
     * @return the irbApprovalDocumentFileName
     */
    public String getIrbApprovalDocumentFileName() {
        return irbApprovalDocumentFileName;
    }

    /**
     * @param irbApprovalDocumentFileName the irbApprovalDocumentFileName to set
     */
    public void setIrbApprovalDocumentFileName(String irbApprovalDocumentFileName) {
        this.irbApprovalDocumentFileName = irbApprovalDocumentFileName;
    }

    /**
     * @return the otherTrialRelDocumentFileName
     */
    public String getOtherTrialRelDocumentFileName() {
        return otherTrialRelDocumentFileName;
    }

    /**
     * @param otherTrialRelDocumentFileName the otherTrialRelDocumentFileName to set
     */
    public void setOtherTrialRelDocumentFileName(
            String otherTrialRelDocumentFileName) {
        this.otherTrialRelDocumentFileName = otherTrialRelDocumentFileName;
    }

    /**
     * @return the participatinSiteDocumentFileName
     */
    public String getParticipatinSiteDocumentFileName() {
        return participatinSiteDocumentFileName;
    }

    /**
     * @param participatinSiteDocumentFileName the participatinSiteDocumentFileName to set
     */
    public void setParticipatinSiteDocumentFileName(
            String participatinSiteDocumentFileName) {
        this.participatinSiteDocumentFileName = participatinSiteDocumentFileName;
    }

    /**
     * @return the protcolDocumentFileName
     */
    public String getProtcolDocumentFileName() {
        return protcolDocumentFileName;
    }

    /**
     * @param protcolDocumentFileName the protcolDocumentFileName to set
     */
    public void setProtcolDocumentFileName(String protcolDocumentFileName) {
        this.protcolDocumentFileName = protcolDocumentFileName;
    }

   /**
     * @return the reasonForStudyStopped
     */
    public String getReasonForStudyStopped() {
        return reasonForStudyStopped;
    }

    /**
     * @param reasonForStudyStopped the reasonForStudyStopped to set
     */
    public void setReasonForStudyStopped(String reasonForStudyStopped) {
        this.reasonForStudyStopped = reasonForStudyStopped;
    }

    /**
     * @return the programCodeText
     */
    @org.hibernate.validator.Length(message = "Program Code must be 2000 characters max", max = PCTL)
    public String getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * @return the submissionType
     */
    @NotEmpty(message = "Submission Type is required.\n")
    @Length(max = 1, message = "Submission Type must be single characters max.\n")
    @Pattern(regex = "^[A|O|U]$", message = "Submission Type can be A or O or U.\n")
    public String getSubmissionType() {
        return submissionType;
    }

    /**
     * @param submissionType the submissionType to set
     */
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * @return the nciTrialIdentifier
     */
    @Pattern(regex = "^[N][C][I][-][0-9]{4}[-][0-9]{5}$",
            message = "NCI Trial Indentifier is not well formatted. It should follow the pattern NCI-2009-00001 \n.")
    public String getNciTrialIdentifier() {
        return nciTrialIdentifier;
    }

    /**
     * @param nciTrialIdentifier the nciTrialIdentifier to set
     */
    public void setNciTrialIdentifier(String nciTrialIdentifier) {
        this.nciTrialIdentifier = nciTrialIdentifier;
    }

    /**
     * @return the amendmentNumber
     */
    public String getAmendmentNumber() {
        return amendmentNumber;
    }

    /**
     * @param amendmentNumber the amendmentNumber to set
     */
    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    /**
     * @return the amendmentDate
     */
    public String getAmendmentDate() {
        return amendmentDate;
    }

    /**
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    /**
     * @return the changeRequestDocFileName
     */
    public String getChangeRequestDocFileName() {
        return changeRequestDocFileName;
    }

    /**
     * @param changeRequestDocFileName the changeRequestDocFileName to set
     */
    public void setChangeRequestDocFileName(String changeRequestDocFileName) {
        this.changeRequestDocFileName = changeRequestDocFileName;
    }

    /**
     * @return the protocolHighlightDocFileName
     */
    public String getProtocolHighlightDocFileName() {
        return protocolHighlightDocFileName;
    }

    /**
     * @param protocolHighlightDocFileName the protocolHighlightDocFileName to set
     */
    public void setProtocolHighlightDocFileName(String protocolHighlightDocFileName) {
        this.protocolHighlightDocFileName = protocolHighlightDocFileName;
    }

    /**
     * @return the responsibleGenericContactName
     */
    public String getResponsibleGenericContactName() {
        return responsibleGenericContactName;
    }

    /**
     * @param responsibleGenericContactName the responsibleGenericContactName to set
     */
    public void setResponsibleGenericContactName(
            String responsibleGenericContactName) {
        this.responsibleGenericContactName = responsibleGenericContactName;
    }

    /**
     * @return the sponsorContactType
     */
    @Pattern(regex = "^[P][e][r][s][o][n][a][l]|[p][e][r][s][o][n][a][l]|[G][e][n][e][r][i][c]|[g][e][n][e][r][i][c]$",
            message = "Submission Type can be Personal or Generic .\n")
    public String getSponsorContactType() {
        return sponsorContactType;
    }

    /**
     * @param sponsorContactType the sponsorContactType to set
     */
    public void setSponsorContactType(String sponsorContactType) {
        this.sponsorContactType = sponsorContactType;
    }

    /**
     * @return the oversightAuthorityCountry
     */
    public String getOversightAuthorityCountry() {
        return oversightAuthorityCountry;
    }

    /**
     * @param oversightAuthorityCountry the oversightAuthorityCountry to set
     */
    public void setOversightAuthorityCountry(String oversightAuthorityCountry) {
        this.oversightAuthorityCountry = oversightAuthorityCountry;
    }

    /**
     * @return the oversightOrgName
     */
     public String getOversightOrgName() {
        return oversightOrgName;
    }

    /**
     * @param oversightOrgName the oversightOrgName to set
     */
    public void setOversightOrgName(String oversightOrgName) {
        this.oversightOrgName = oversightOrgName;
    }

    /**
     * @return the fdaRegulatoryInformationIndicator
     */
    public String getFdaRegulatoryInformationIndicator() {
        return fdaRegulatoryInformationIndicator;
    }

    /**
     * @param fdaRegulatoryInformationIndicator the fdaRegulatoryInformationIndicator to set
     */
    public void setFdaRegulatoryInformationIndicator(
            String fdaRegulatoryInformationIndicator) {
        this.fdaRegulatoryInformationIndicator = fdaRegulatoryInformationIndicator;
    }

    /**
     * @return the section801Indicator
     */
    public String getSection801Indicator() {
        return section801Indicator;
    }

    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(String section801Indicator) {
        this.section801Indicator = section801Indicator;
    }

    /**
     * @return the delayedPostingIndicator
     */
    public String getDelayedPostingIndicator() {
        return delayedPostingIndicator;
    }

    /**
     * @param delayedPostingIndicator the delayedPostingIndicator to set
     */
    public void setDelayedPostingIndicator(String delayedPostingIndicator) {
        this.delayedPostingIndicator = delayedPostingIndicator;
    }

    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    public String getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(
            String dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param ctepIdentifier the ctepIdentifier to set
     */
    public void setCtepIdentifier(String ctepIdentifier) {
        this.ctepIdentifier = ctepIdentifier;
    }

    /**
     * @return the ctepIdentifier
     */
    public String getCtepIdentifier() {
        return ctepIdentifier;
    }

    /**
     * @param dcpIdentifier the dcpIdentifier to set
     */
    public void setDcpIdentifier(String dcpIdentifier) {
        this.dcpIdentifier = dcpIdentifier;
    }

    /**
     * @return the dcpIdentifier
     */
    public String getDcpIdentifier() {
        return dcpIdentifier;
    }

    /**
     * @return true, if is ct gov xml indicator
     */
    public boolean isCtGovXmlIndicator() {
      return ctGovXmlIndicator;
    }

    /**
     * @param ctGovXmlIndicator the new ct gov xml indicator
     */
     public void setCtGovXmlIndicator(boolean ctGovXmlIndicator) {
       this.ctGovXmlIndicator = ctGovXmlIndicator;
     }

     /**
      * @return the nciGrant
      */
     public Boolean getNciGrant() {
         return nciGrant;
     }

     /**
      * @param nciGrant the nciGrant to set
      */
     public void setNciGrant(Boolean nciGrant) {
         this.nciGrant = nciGrant;
     }

     /**
      * @param exemptIndicator the exemptIndicator to set
      */
     public void setExemptIndicator(String exemptIndicator) {
         this.exemptIndicator = exemptIndicator;
     }

     /**
      * @return the exemptIndicator
      */
     public String getExemptIndicator() {
         return exemptIndicator;
     }
     /**
      * @param primaryPurposeOtherText the primaryPurposeOtherText to set
      */
     public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
         this.primaryPurposeOtherText = primaryPurposeOtherText;
     }

     /**
      * @return the primaryPurposeOtherText
      */
     public String getPrimaryPurposeOtherText() {
         return primaryPurposeOtherText;
     }

    /**
     * @return the partyInvestigatorPOId
     */
    public String getPartyInvestigatorPOId() {
        return partyInvestigatorPOId;
    }

    /**
     * @param partyInvestigatorPOId the partyInvestigatorPOId to set
     */
    public void setPartyInvestigatorPOId(String partyInvestigatorPOId) {
        this.partyInvestigatorPOId = partyInvestigatorPOId;
    }

    /**
     * @return the partyInvestigatorFirstName
     */
    public String getPartyInvestigatorFirstName() {
        return partyInvestigatorFirstName;
    }

    /**
     * @param partyInvestigatorFirstName the partyInvestigatorFirstName to set
     */
    public void setPartyInvestigatorFirstName(String partyInvestigatorFirstName) {
        this.partyInvestigatorFirstName = partyInvestigatorFirstName;
    }

    /**
     * @return the partyInvestigatorMiddleName
     */
    public String getPartyInvestigatorMiddleName() {
        return partyInvestigatorMiddleName;
    }

    /**
     * @param partyInvestigatorMiddleName the partyInvestigatorMiddleName to set
     */
    public void setPartyInvestigatorMiddleName(String partyInvestigatorMiddleName) {
        this.partyInvestigatorMiddleName = partyInvestigatorMiddleName;
    }

    /**
     * @return the partyInvestigatorLastName
     */
    public String getPartyInvestigatorLastName() {
        return partyInvestigatorLastName;
    }

    /**
     * @param partyInvestigatorLastName the partyInvestigatorLastName to set
     */
    public void setPartyInvestigatorLastName(String partyInvestigatorLastName) {
        this.partyInvestigatorLastName = partyInvestigatorLastName;
    }

    /**
     * @return the partyInvestigatorStreetAddress
     */
    public String getPartyInvestigatorStreetAddress() {
        return partyInvestigatorStreetAddress;
    }

    /**
     * @param partyInvestigatorStreetAddress the partyInvestigatorStreetAddress to set
     */
    public void setPartyInvestigatorStreetAddress(
            String partyInvestigatorStreetAddress) {
        this.partyInvestigatorStreetAddress = partyInvestigatorStreetAddress;
    }

    /**
     * @return the partyInvestigatorCity
     */
    public String getPartyInvestigatorCity() {
        return partyInvestigatorCity;
    }

    /**
     * @param partyInvestigatorCity the partyInvestigatorCity to set
     */
    public void setPartyInvestigatorCity(String partyInvestigatorCity) {
        this.partyInvestigatorCity = partyInvestigatorCity;
    }

    /**
     * @return the partyInvestigatorState
     */
    public String getPartyInvestigatorState() {
        return partyInvestigatorState;
    }

    /**
     * @param partyInvestigatorState the partyInvestigatorState to set
     */
    public void setPartyInvestigatorState(String partyInvestigatorState) {
        this.partyInvestigatorState = partyInvestigatorState;
    }

    /**
     * @return the partyInvestigatorZip
     */
    public String getPartyInvestigatorZip() {
        return partyInvestigatorZip;
    }

    /**
     * @param partyInvestigatorZip the partyInvestigatorZip to set
     */
    public void setPartyInvestigatorZip(String partyInvestigatorZip) {
        this.partyInvestigatorZip = partyInvestigatorZip;
    }

    /**
     * @return the partyInvestigatorCountry
     */
    public String getPartyInvestigatorCountry() {
        return partyInvestigatorCountry;
    }

    /**
     * @param partyInvestigatorCountry the partyInvestigatorCountry to set
     */
    public void setPartyInvestigatorCountry(String partyInvestigatorCountry) {
        this.partyInvestigatorCountry = partyInvestigatorCountry;
    }

    /**
     * @return the partyInvestigatorEmail
     */
    public String getPartyInvestigatorEmail() {
        return partyInvestigatorEmail;
    }

    /**
     * @param partyInvestigatorEmail the partyInvestigatorEmail to set
     */
    public void setPartyInvestigatorEmail(String partyInvestigatorEmail) {
        this.partyInvestigatorEmail = partyInvestigatorEmail;
    }

    /**
     * @return the partyInvestigatorPhone
     */
    public String getPartyInvestigatorPhone() {
        return partyInvestigatorPhone;
    }

    /**
     * @param partyInvestigatorPhone the partyInvestigatorPhone to set
     */
    public void setPartyInvestigatorPhone(String partyInvestigatorPhone) {
        this.partyInvestigatorPhone = partyInvestigatorPhone;
    }

    /**
     * @return the partyInvestigatorTTY
     */
    public String getPartyInvestigatorTTY() {
        return partyInvestigatorTTY;
    }

    /**
     * @param partyInvestigatorTTY the partyInvestigatorTTY to set
     */
    public void setPartyInvestigatorTTY(String partyInvestigatorTTY) {
        this.partyInvestigatorTTY = partyInvestigatorTTY;
    }

    /**
     * @return the partyInvestigatorFax
     */
    public String getPartyInvestigatorFax() {
        return partyInvestigatorFax;
    }

    /**
     * @param partyInvestigatorFax the partyInvestigatorFax to set
     */
    public void setPartyInvestigatorFax(String partyInvestigatorFax) {
        this.partyInvestigatorFax = partyInvestigatorFax;
    }

    /**
     * @return the partyInvestigatorUrl
     */
    public String getPartyInvestigatorUrl() {
        return partyInvestigatorUrl;
    }

    /**
     * @param partyInvestigatorUrl the partyInvestigatorUrl to set
     */
    public void setPartyInvestigatorUrl(String partyInvestigatorUrl) {
        this.partyInvestigatorUrl = partyInvestigatorUrl;
    }

    /**
     * @return the partyInvestigatorTitle
     */
    public String getPartyInvestigatorTitle() {
        return partyInvestigatorTitle;
    }

    /**
     * @param partyInvestigatorTitle the partyInvestigatorTitle to set
     */
    public void setPartyInvestigatorTitle(String partyInvestigatorTitle) {
        this.partyInvestigatorTitle = partyInvestigatorTitle;
    }

    /**
     * @return the partyAffiliationPOId
     */
    public String getPartyAffiliationPOId() {
        return partyAffiliationPOId;
    }

    /**
     * @param partyAffiliationPOId the partyAffiliationPOId to set
     */
    public void setPartyAffiliationPOId(String partyAffiliationPOId) {
        this.partyAffiliationPOId = partyAffiliationPOId;
    }

    /**
     * @return the partyAffiliationName
     */
    public String getPartyAffiliationName() {
        return partyAffiliationName;
    }

    /**
     * @param partyAffiliationName the partyAffiliationName to set
     */
    public void setPartyAffiliationName(String partyAffiliationName) {
        this.partyAffiliationName = partyAffiliationName;
    }

    /**
     * @return the partyAffiliationStreetAddress
     */
    public String getPartyAffiliationStreetAddress() {
        return partyAffiliationStreetAddress;
    }

    /**
     * @param partyAffiliationStreetAddress the partyAffiliationStreetAddress to set
     */
    public void setPartyAffiliationStreetAddress(
            String partyAffiliationStreetAddress) {
        this.partyAffiliationStreetAddress = partyAffiliationStreetAddress;
    }

    /**
     * @return the partyAffiliationCity
     */
    public String getPartyAffiliationCity() {
        return partyAffiliationCity;
    }

    /**
     * @param partyAffiliationCity the partyAffiliationCity to set
     */
    public void setPartyAffiliationCity(String partyAffiliationCity) {
        this.partyAffiliationCity = partyAffiliationCity;
    }

    /**
     * @return the partyAffiliationState
     */
    public String getPartyAffiliationState() {
        return partyAffiliationState;
    }

    /**
     * @param partyAffiliationState the partyAffiliationState to set
     */
    public void setPartyAffiliationState(String partyAffiliationState) {
        this.partyAffiliationState = partyAffiliationState;
    }

    /**
     * @return the partyAffiliationZip
     */
    public String getPartyAffiliationZip() {
        return partyAffiliationZip;
    }

    /**
     * @param partyAffiliationZip the partyAffiliationZip to set
     */
    public void setPartyAffiliationZip(String partyAffiliationZip) {
        this.partyAffiliationZip = partyAffiliationZip;
    }

    /**
     * @return the partyAffiliationCountry
     */
    public String getPartyAffiliationCountry() {
        return partyAffiliationCountry;
    }

    /**
     * @param partyAffiliationCountry the partyAffiliationCountry to set
     */
    public void setPartyAffiliationCountry(String partyAffiliationCountry) {
        this.partyAffiliationCountry = partyAffiliationCountry;
    }

    /**
     * @return the partyAffiliationEmail
     */
    public String getPartyAffiliationEmail() {
        return partyAffiliationEmail;
    }

    /**
     * @param partyAffiliationEmail the partyAffiliationEmail to set
     */
    public void setPartyAffiliationEmail(String partyAffiliationEmail) {
        this.partyAffiliationEmail = partyAffiliationEmail;
    }

    /**
     * @return the partyAffiliationPhone
     */
    public String getPartyAffiliationPhone() {
        return partyAffiliationPhone;
    }

    /**
     * @param partyAffiliationPhone the partyAffiliationPhone to set
     */
    public void setPartyAffiliationPhone(String partyAffiliationPhone) {
        this.partyAffiliationPhone = partyAffiliationPhone;
    }

    /**
     * @return the partyAffiliationTTY
     */
    public String getPartyAffiliationTTY() {
        return partyAffiliationTTY;
    }

    /**
     * @param partyAffiliationTTY the partyAffiliationTTY to set
     */
    public void setPartyAffiliationTTY(String partyAffiliationTTY) {
        this.partyAffiliationTTY = partyAffiliationTTY;
    }

    /**
     * @return the partyAffiliationFax
     */
    public String getPartyAffiliationFax() {
        return partyAffiliationFax;
    }

    /**
     * @param partyAffiliationFax the partyAffiliationFax to set
     */
    public void setPartyAffiliationFax(String partyAffiliationFax) {
        this.partyAffiliationFax = partyAffiliationFax;
    }

    /**
     * @return the partyAffiliationUrl
     */
    public String getPartyAffiliationUrl() {
        return partyAffiliationUrl;
    }

    /**
     * @param partyAffiliationUrl the partyAffiliationUrl to set
     */
    public void setPartyAffiliationUrl(String partyAffiliationUrl) {
        this.partyAffiliationUrl = partyAffiliationUrl;
    }
}
