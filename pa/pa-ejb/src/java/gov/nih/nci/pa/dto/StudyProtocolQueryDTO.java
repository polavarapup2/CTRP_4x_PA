package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * StudyProtocolQueryDTO for transferring Study Protocol object .
 * 
 * @author Naveen Amiruddin
 * @since 07/22/2007
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.TooManyFields",
        "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" })
public class StudyProtocolQueryDTO extends TrialSearchStudyProtocolQueryDTO
        implements Serializable {

    private static final String COMPLETE = "Complete";

    private static final String ABBREVIATED = "Abbreviated";

    private static final String AMENDMENT = "Amendment";

    private static final long serialVersionUID = 8200069337460780488L;

    private boolean viewTSR;
    private boolean proprietaryTrial;
    private Date recordVerificationDate;
    private Boolean showSendXml = false;
    private Boolean showViewTSR = false;
    private String summ4FundingSrcCategory;
    private boolean searcherTrialOwner = false;
    private String nctNumber;

    private String phaseName;
    private Date startDate;
    private String sponsorName;
    private String summary4FundingSponsorType;
    private String responsiblePartyName;
    private String category;

    private String lastUpdatedUserDisplayName;

    private String lastUpdaterDisplayName;

    private String recentHoldReason;
    private String recentHoldDescription;
    private String recentHoldCategory;
    private Date recentOnHoldDate;
    private Date recentOffHoldDate;
    private Date activeHoldDate;
    private OnholdReasonCode activeHoldReason;
    private String activeHoldReasonCategory;
    private boolean verifyData = false;
    private boolean showAccrualOption = false;
    private Date verificationDueDate;
    private Set<StudyAlternateTitleDTO> studyAlternateTitles;
    private String studySource;
    private List<OrganizationDTO> orgsThatCanBeAddedAsSite;
    private String accrualDiseaseCode;
    private List<StudyOnholdDTO> allHolds = new ArrayList<>();
    private List<ProgramCodeDTO> programCodes = new ArrayList<>();
    private String designeeNamesList;

   /**
     * Whether this trial permits self-registration of participating sites.
     * 
     * @see https://tracker.nci.nih.gov/browse/PO-2034
     */
    private boolean siteSelfRegistrable;

    /**
     * Whether the current user's organization is a participating site on this
     * trial.
     */
    private boolean currentUserHasSite;

    /**
     * Whether the current user's organization is a participating site on this
     * trial and the current user is a study site owner for.
     */
    private boolean currentUserIsSiteOwner;

    private Date primaryCompletionDate;

    private Long poOrganizationId;

    private Integer bizDaysOnHoldCTRP;

    private Integer bizDaysOnHoldSubmitter;

    private Integer bizDaysSinceSubmitted;

    /**
     * Expected date.
     */
    private Date expectedAbstractionCompletionDate;
    
    /**
     * Calculated.
     */
    private Date calculatedAbstractionCompletionDate;
    
    /**
     * Submission Plus 10 Business Days
     */
    private Date calculatedSubmissionPlusTenBizDate;
    
    /**
     * Comes from DB
     */
    private Date overriddenExpectedAbstractionCompletionDate;
    /**
     * Comes from DB
     */
    private String overriddenExpectedAbstractionCompletionComments;

    //Results reporting dates
    private Date pcdSentToPIODate;
    private Date pcdConfirmedDate;
    private Date desgneeNotifiedDate;
    private Date reportingInProcessDate;
    private Date threeMonthReminderDate;
    private Date fiveMonthReminderDate;
    private Date sevenMonthEscalationtoPIODate;
    private Date resultsSentToPIODate;
    private Date resultsApprovedByPIODate;
    private Date prsReleaseDate;
    private Date qaCommentsReturnedDate;
    private Date trialPublishedDate;   
    
    private Date ctroUserCreatedDate; 
    private Date ccctUserCreatedDate;
    
    private String ctroUserName;
    private String ccctUserName;
   
    /**
     * 
     * @return ctro user name
     */
    public String getCtroUserName() {
        return ctroUserName;
    }
    
    /**      
     * @param ctroUserName
     *              sets user name for the ctro
     */
    public void setCtroUserName(String ctroUserName) {
        this.ctroUserName = ctroUserName;
    }

    /**
     * 
     * @return ccct user name
     */
    public String getCcctUserName() {
        return ccctUserName;
    }

    /**      
     * @param ccctUserName
     *              sets user name for the ccct 
     */
    public void setCcctUserName(String ccctUserName) {
        this.ccctUserName = ccctUserName;
    }

    /**
     * 
     * @return ccctUserCreatedDate
     */
    public Date getCcctUserCreatedDate() {        
        return ccctUserCreatedDate;
    }
    
    /**
     * 
     * @param ccctUserCreatedDate
     *             sets user reviewed date
     */    
    public void setCcctUserCreatedDate(Date ccctUserCreatedDate) {
        this.ccctUserCreatedDate = ccctUserCreatedDate;
    }
    
    /**
     * 
     * @return ctroUserCreatedDate
     */
    public Date getCtroUserCreatedDate() {        
        return ctroUserCreatedDate; 
    }

    /**
     * 
     * @param ctroUserCreatedDate
     *           sets ctro user reviewed date
     */
    public void setCtroUserCreatedDate(Date ctroUserCreatedDate) {
        this.ctroUserCreatedDate = ctroUserCreatedDate;
    }
    
    /**
     * @return link
     */
    public String getAmend() {
        boolean isProprietaryTrial = isProprietaryTrial();
        boolean isOwner = isSearcherTrialOwner();
        DocumentWorkflowStatusCode dwfs = getDocumentWorkflowStatusCode();
        StudyStatusCode studyStatusCode = getStudyStatusCode();

        if (!isProprietaryTrial && isAmendDWFS(dwfs) && isOwner
                && isAmendStatus(studyStatusCode)) {
            return "Amend";
        }
        return "";
    }

    private boolean isAmendStatus(StudyStatusCode statusCode) {
        return !(StudyStatusCode.WITHDRAWN.equals(statusCode)
                || StudyStatusCode.COMPLETE.equals(statusCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE
                    .equals(statusCode));
    }

    private boolean isAmendDWFS(DocumentWorkflowStatusCode dwfs) {
        return DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                .equals(dwfs)
                || DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE
                        .equals(dwfs);
    }

    /**
     * @return the trialCategory
     */
    public String getTrialCategory() {
        return isProprietaryTrial() ? "Abbreviated Trial" : "Complete Trial";
    }

    /**
     * @return the isProprietaryTrial
     */
    public boolean isProprietaryTrial() {
        return proprietaryTrial;
    }

    /**
     * @param proprietaryTrial
     *            the isProprietaryTrial to set
     */
    public void setProprietaryTrial(boolean proprietaryTrial) {
        this.proprietaryTrial = proprietaryTrial;
    }

    /**
     * 
     * @return tsr
     */
    public boolean isViewTSR() {
        return viewTSR;
    }

    /**
     * 
     * @param viewTSR
     *            tsr
     */
    public void setViewTSR(boolean viewTSR) {
        this.viewTSR = viewTSR;
    }

    /**
     * @return the recordVerificationDate
     */
    public Date getRecordVerificationDate() {
        return recordVerificationDate;
    }

    /**
     * @param recordVerificationDate
     *            the recordVerificationDate to set
     */
    public void setRecordVerificationDate(Date recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }

    /**
     * @return showSendXml
     */
    public Boolean getShowSendXml() {
        return showSendXml;
    }

    /**
     * @param showSendXml
     *            showSendXml to set
     */
    public void setShowSendXml(Boolean showSendXml) {
        this.showSendXml = showSendXml;
    }

    /**
     * 
     * @return showViewTSR
     */
    public Boolean getShowViewTSR() {
        return showViewTSR;
    }

    /**
     * 
     * @param showViewTSR
     *            showViewTSR
     */
    public void setShowViewTSR(Boolean showViewTSR) {
        this.showViewTSR = showViewTSR;
    }

    /**
     * This field is set to true if and only if the person performing the search
     * is considered a trial owner.
     * 
     * @return the isSearcherTrialOwner
     */
    public boolean isSearcherTrialOwner() {
        return searcherTrialOwner;
    }

    /**
     * @param isSearcherTrialOwner
     *            the isSearcherTrialOwner to set
     */
    public void setSearcherTrialOwner(boolean isSearcherTrialOwner) {
        this.searcherTrialOwner = isSearcherTrialOwner;
    }

    /**
     * 
     * @return verifyData verifyData
     */

    public boolean isVerifyData() {
        return verifyData;
    }

    /**
     * @param verifyData
     *            the verifyData to set
     */
    public void setVerifyData(boolean verifyData) {
        this.verifyData = verifyData;
    }

    /**
     * @param summ4FundingSrcCategory
     *            the summ4FundingSrcCategory to set
     */
    public void setSumm4FundingSrcCategory(String summ4FundingSrcCategory) {
        this.summ4FundingSrcCategory = summ4FundingSrcCategory;
    }

    /**
     * @return the summ4FundingSrcCategory
     */
    public String getSumm4FundingSrcCategory() {
        return summ4FundingSrcCategory;
    }

    /**
     * @return the nctNumber
     */
    public String getNctNumber() {
        return nctNumber;
    }

    /**
     * @param nctNumber
     *            the nctNumber to set
     */
    public void setNctNumber(String nctNumber) {
        this.nctNumber = nctNumber;
    }

    /**
     * @return the siteSelfRegistrable
     */
    public boolean isSiteSelfRegistrable() {
        return siteSelfRegistrable;
    }

    /**
     * @param siteSelfRegistrable
     *            the siteSelfRegistrable to set
     */
    public void setSiteSelfRegistrable(boolean siteSelfRegistrable) {
        this.siteSelfRegistrable = siteSelfRegistrable;
    }

    /**
     * @return the currentUserHasSite
     */
    public boolean isCurrentUserHasSite() {
        return currentUserHasSite;
    }

    /**
     * @param currentUserHasSite
     *            the currentUserHasSite to set
     */
    public void setCurrentUserHasSite(boolean currentUserHasSite) {
        this.currentUserHasSite = currentUserHasSite;
    }

    /**
     * Tells whether the currently logged in user can add his/her site to the
     * trial.
     * 
     * @return boolean
     */
    public boolean isCurrentUserCanAddSite() {
        return isSiteSelfRegistrable() && !isCurrentUserHasSite();
    }

    /**
     * Tells whether the currently logged in user can edit his/her site info.
     * 
     * @return boolean
     */
    public boolean isCurrentUserCanEditSite() {
        return isSiteSelfRegistrable() && isCurrentUserHasSite()
                && isCurrentUserIsSiteOwner();
    }

    /**
     * @return the currentUserIsSiteOwner
     */
    public boolean isCurrentUserIsSiteOwner() {
        return currentUserIsSiteOwner;
    }

    /**
     * @param currentUserIsSiteOwner
     *            the currentUserIsSiteOwner to set
     */
    public void setCurrentUserIsSiteOwner(boolean currentUserIsSiteOwner) {
        this.currentUserIsSiteOwner = currentUserIsSiteOwner;
    }

    /**
     * @return string
     */
    public String getPhaseName() {
        return phaseName;
    }

    /**
     * 
     * @param phaseName
     *            phaseCode
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * 
     * @return date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *            startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return string
     */
    public String getSponsorName() {
        return sponsorName;
    }

    /**
     * 
     * @param sponsorName
     *            sponsorName
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    /**
     * 
     * @return string
     */
    public String getSummary4FundingSponsorType() {
        return summary4FundingSponsorType;
    }

    /**
     * 
     * @param summary4FundingSponsorName
     *            summary4FundingSponsorName
     */
    public void setSummary4FundingSponsorType(String summary4FundingSponsorName) {
        this.summary4FundingSponsorType = summary4FundingSponsorName;
    }

    /**
     * 
     * @return string
     */
    public String getResponsiblePartyName() {
        return responsiblePartyName;
    }

    /**
     * 
     * @param responsiblePartyName
     *            responsiblePartyName
     */
    public void setResponsiblePartyName(String responsiblePartyName) {
        this.responsiblePartyName = responsiblePartyName;
    }

    /**
     * 
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *            category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return String
     */
    public String getLastUpdatedUserDisplayName() {
        return lastUpdatedUserDisplayName;
    }

    /**
     * 
     * @param lastUpdatedUserDisplayName
     *            lastUpdatedUserDisplayName
     */
    public void setLastUpdatedUserDisplayName(String lastUpdatedUserDisplayName) {
        this.lastUpdatedUserDisplayName = lastUpdatedUserDisplayName;
    }

    /**
     * 
     * @return String
     */
    public String getLastUpdaterDisplayName() {
        return lastUpdaterDisplayName;
    }

    /**
     * 
     * @param lastUpdaterDisplayName
     *            lastUpdaterDisplayName
     */
    public void setLastUpdaterDisplayName(String lastUpdaterDisplayName) {
        this.lastUpdaterDisplayName = lastUpdaterDisplayName;
    }

    /**
     * 
     * @return PrimaryCompletionDate
     */
    public Date getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * 
     * @param primaryCompletionDate
     *            primaryCompletionDate
     */
    public void setPrimaryCompletionDate(Date primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * Determines whether to show actions dropdown or not.
     * 
     * @return boolean
     */
    public boolean isActionVisible() {
        return StringUtils.isNotEmpty(getUpdate())
                || StringUtils.isNotEmpty(getAmend())
                || StringUtils.isNotEmpty(getStatusChangeLinkText())
                || getShowSendXml().booleanValue()
                || getShowViewTSR().booleanValue() || isCurrentUserCanAddSite()
                || isCurrentUserCanEditSite();
    }

    /**
     * @return CtepOrDcp
     */
    public String getCtepOrDcp() {
        if (StringUtils.isNotBlank(getCtepId())
                && StringUtils.isBlank(getDcpId())) {
            return "CTEP";
        }
        if (StringUtils.isNotBlank(getDcpId())) {
            return "DCP";
        }
        return "";
    }

    /**
     * @return the checkedOutByMe
     */
    public boolean isCheckedOutByMe() {
        return UsernameHolder.getUser().equalsIgnoreCase(
                getAdminCheckout().getCheckoutBy())
                || UsernameHolder.getUser().equalsIgnoreCase(
                        getScientificCheckout().getCheckoutBy());
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForAdminProcessing() {
        return MilestoneCode.SUBMISSION_ACCEPTED == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForAdminQC() {
        return MilestoneCode.ADMINISTRATIVE_READY_FOR_QC == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForTSRSubmission() {
        return MilestoneCode.READY_FOR_TSR == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isSubmittedNotAccepted() {
        return MilestoneCode.SUBMISSION_RECEIVED == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForScientificProcessing() {
        return MilestoneCode.READY_FOR_SCIENTIFIC_PROCESSING_LIST
                .contains(getMilestones().getActiveMilestone().getMilestone())
                && findMilestoneInHistory(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE) == null;
    }

    /**
     * @return isReadyForAdminProcessing
     */
    public boolean isReadyForScientificQC() {
        return MilestoneCode.SCIENTIFIC_READY_FOR_QC == getMilestones()
                .getActiveMilestone().getMilestone();
    }

    /**
     * @return the recentHoldReason
     */
    public String getRecentHoldReason() {
        return recentHoldReason;
    }

    /**
     * @param recentHoldReason
     *            the recentHoldReason to set
     */
    public void setRecentHoldReason(String recentHoldReason) {
        this.recentHoldReason = recentHoldReason;
    }

    /**
     * @return the recentOnHoldDate
     */
    public Date getRecentOnHoldDate() {
        return recentOnHoldDate;
    }

    /**
     * @param recentOnHoldDate
     *            the recentOnHoldDate to set
     */
    public void setRecentOnHoldDate(Date recentOnHoldDate) {
        this.recentOnHoldDate = recentOnHoldDate;
    }

    /**
     * @return the recentOffHoldDate
     */
    public Date getRecentOffHoldDate() {
        return recentOffHoldDate;
    }

    /**
     * @param recentOffHoldDate
     *            the recentOffHoldDate to set
     */
    public void setRecentOffHoldDate(Date recentOffHoldDate) {
        this.recentOffHoldDate = recentOffHoldDate;
    }

    /**
     * @return MilestoneListForReporting
     */
    public List<MilestoneDTO> getMilestoneListForReporting() {
        List<MilestoneDTO> list = new ArrayList<MilestoneDTO>();
        for (MilestoneCode code : MilestoneCode.getMilestoneCodesForReporting()) {
            MilestoneDTO historicMilestone = findMilestoneInHistory(code);
            if (historicMilestone != null) {
                list.add(historicMilestone);
            } else {
                MilestoneDTO dto = new MilestoneDTO();
                dto.setMilestone(code);
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * @param code
     *            MilestoneCode
     * @return MilestoneDTO
     */
    public MilestoneDTO getMilestoneForReporting(final MilestoneCode code) {
        return (MilestoneDTO) CollectionUtils.find(
                getMilestoneListForReporting(), new Predicate() {
                    @Override
                    public boolean evaluate(Object obj) {
                        return code == ((MilestoneDTO) obj).getMilestone();
                    }
                });
    }

    private MilestoneDTO findMilestoneInHistory(final MilestoneCode code) {
        return (MilestoneDTO) CollectionUtils.find(getMilestoneHistory(),
                new Predicate() {
                    @Override
                    public boolean evaluate(Object mstone) {
                        return code.equals(((MilestoneDTO) mstone)
                                .getMilestone());
                    }
                });
    }

    /**
     * @return the recentHoldDescription
     */
    public String getRecentHoldDescription() {
        return recentHoldDescription;
    }

    /**
     * @param recentHoldDescription
     *            the recentHoldDescription to set
     */
    public void setRecentHoldDescription(String recentHoldDescription) {
        this.recentHoldDescription = recentHoldDescription;
    }

    /**
     * 
     * @return verificationDueDate verificationDueDate
     */
    public Date getVerificationDueDate() {
        return verificationDueDate;
    }

    /**
     * 
     * @param verificationDueDate
     *            verificationDueDate
     */
    public void setVerificationDueDate(Date verificationDueDate) {
        this.verificationDueDate = verificationDueDate;
    }

    /**
     * @return study alternate titles
     */
    public Set<StudyAlternateTitleDTO> getStudyAlternateTitles() {
        return studyAlternateTitles;
    }

    /**
     * @param studyAlternateTitles
     *            study alternate titles to set
     */
    public void setStudyAlternateTitles(
            Set<StudyAlternateTitleDTO> studyAlternateTitles) {
        this.studyAlternateTitles = studyAlternateTitles;
    }

    /**
     * 
     * @return the source where the study was created.
     */
    public String getStudySource() {
        return studySource;
    }

    /**
     * 
     * @param studySource
     *            sets which pathway was used to submit the study.
     */
    public void setStudySource(StudySourceCode studySource) {
        if (studySource == null) {
            this.studySource = "";
        } else {
            this.studySource = studySource.getCode();
        }
    }

    /**
     * 
     * @param studySource
     *            sets which pathway was used to submit the study.
     */
    public void setStudySource(String studySource) {
        this.studySource = studySource;
    }

    /**
     * @return the orgsThatCanBeAddedAsSite
     */
    public List<OrganizationDTO> getOrgsThatCanBeAddedAsSite() {
        return orgsThatCanBeAddedAsSite;
    }

    /**
     * @param orgsThatCanBeAddedAsSite
     *            the orgsThatCanBeAddedAsSite to set
     */
    public void setOrgsThatCanBeAddedAsSite(
            List<OrganizationDTO> orgsThatCanBeAddedAsSite) {
        this.orgsThatCanBeAddedAsSite = orgsThatCanBeAddedAsSite;
    }

    /**
     * 
     * @return showAccrualOption showAccrualOption
     */
    public boolean isShowAccrualOption() {
        return showAccrualOption;
    }

    /**
     * 
     * @param showAccrualOption
     *            showAccrualOption
     */
    public void setShowAccrualOption(boolean showAccrualOption) {
        this.showAccrualOption = showAccrualOption;
    }

    /**
     * 
     * @return accrualDiseaseCode accrualDiseaseCode
     */
    public String getAccrualDiseaseCode() {
        return accrualDiseaseCode;
    }

    /**
     * 
     * @param accrualDiseaseCode
     *            accrualDiseaseCode
     */
    public void setAccrualDiseaseCode(String accrualDiseaseCode) {
        this.accrualDiseaseCode = accrualDiseaseCode;
    }

    /**
     * This sets the PO id of the Submiting organization.
     * 
     * @param poOrgId
     *            the PO id for the organization.
     */
    public void setSubmitterOrgId(Long poOrgId) {
        this.poOrganizationId = poOrgId;
    }

    /**
     * Returns the PO ID for the submiting organization.
     * 
     * @return the PO ID for the organization.
     */
    public Long getSubmitterOrgId() {
        return poOrganizationId;
    }
    
    /**
     *  PO-9400 Update the calculation of the "Submission Plus 10 Business Days"
     *  (Submission Plus 10 Business Days)= (Submission Date)+ (Business Days on-Hold (Submitter)) + 10 business days. 
     *  The date may not fall on a holiday or a weekend
     *  @return Date
     */
    public Date getCalculatedSubmissionPlusTenBizDate() {
        if (calculatedSubmissionPlusTenBizDate == null) {
            calculatedSubmissionPlusTenBizDate = pushToNextBusinessDayIfNotBusinessDay(PAUtil
                    .addBusinessDays(getLastCreated().getDateLastCreatedPlusTenBiz(), getBizDaysOnHoldSubmitter()));
        }
        return calculatedSubmissionPlusTenBizDate;
    }
    
    private Date pushToNextBusinessDayIfNotBusinessDay(Date calculatedDate) {
        return PAUtil.isBusinessDay(calculatedDate) ? calculatedDate : PAUtil.addBusinessDays(calculatedDate, 1);
    }    
    
    // CHECKSTYLE:OFF
    /**
     * @return Date
     */
    public final Date getExpectedAbstractionCompletionDate() {
        return expectedAbstractionCompletionDate != null ? expectedAbstractionCompletionDate
                : (expectedAbstractionCompletionDate = (overriddenExpectedAbstractionCompletionDate == null ? 
                        getCalculatedAbstractionCompletionDate()
                        : overriddenExpectedAbstractionCompletionDate));
    }

    /**
     * @return Date
     *  PO-9399 - update calculation of the Expected Abstraction Abstraction Completion Date value 
     *  The date may not fall on a holiday or a weekend  pushed to the next business day.
     */
    public final Date getCalculatedAbstractionCompletionDate() {
        if (calculatedAbstractionCompletionDate == null) {
            calculatedAbstractionCompletionDate = pushToNextBusinessDayIfNotBusinessDay(PAUtil
                    .addBusinessDays(getLastCreated().getDateLastCreatedPlusTenBiz(), getBizDaysOnHoldSubmitter()));
        }
        return calculatedAbstractionCompletionDate;
    }

    /**
     * Business Days Elapsed Since Submitted is a calculated field. It is equal
     * to Today's date minus Submission Date minus (weekend and federal holiday
     * days between Today date and the Submission Date, inclusive)
     * PO-9397 - Update the calculation of the "Business Days Since Submitted" 
     * date on the Dashboard-Workload tab (Do not include submission date)
     * @return int BizDaysOnHoldSubmitter
     */
    public final Integer getBizDaysSinceSubmitted() {
        if (bizDaysSinceSubmitted == null) {
            if (getLastCreated().getDateLastCreated() != null
                    && PAUtil.isBusinessDay(getLastCreated().getDateLastCreated())) {
                bizDaysSinceSubmitted = PAUtil.getBusinessDaysBetween(
                        PAUtil.addBusinessDays(getLastCreated().getDateLastCreated(), 1), new Date());
            } else {
                bizDaysSinceSubmitted = PAUtil.getBusinessDaysBetween(getLastCreated().getDateLastCreated(),
                        new Date());
            }
        }
        return bizDaysSinceSubmitted;
    }

    /**
     * @return int BizDaysOnHoldSubmitter
     */
    public final Integer getBizDaysOnHoldSubmitter() {
        return bizDaysOnHoldSubmitter != null ? bizDaysOnHoldSubmitter
                : (bizDaysOnHoldSubmitter = getBizDaysOnHold("Submitter"));
    }

    /**
     * @return int BizDaysOnHoldCTRP
     */
    public final Integer getBizDaysOnHoldCTRP() {
        return bizDaysOnHoldCTRP != null ? bizDaysOnHoldCTRP
                : (bizDaysOnHoldCTRP = getBizDaysOnHold("CTRP"));
    }

    private int getBizDaysOnHold(String cat) {
        HashSet<String> holdDateSet = new HashSet<>();
        for (StudyOnholdDTO hold : getAllHolds()) {
            if (cat.equalsIgnoreCase(StConverter.convertToString(hold
                    .getOnHoldCategory()))) {
                Date holdStart = IvlConverter.convertTs().convertLow(
                        hold.getOnholdDate());
                Date holdEnd = IvlConverter.convertTs().convertHigh(
                        hold.getOnholdDate());
                
                holdEnd = (holdEnd == null ? new Date() : holdEnd);
                while ((holdStart.compareTo(holdEnd) <= 0) || DateUtils.isSameDay(holdStart, holdEnd)) {
                    Date current = holdStart;
                    int bDays = PAUtil.getBusinessDaysBetween(current,holdStart);
                    if (bDays > 0){
                        holdDateSet.add(PAUtil.convertTsToFormattedDate(TsConverter
                                .convertToTs(current)));
                    }
                    holdStart = DateUtils.addDays(current, 1);
                }
           }
        }
        return holdDateSet.size();
    }

    /**
     * @return the recentHoldCategory
     */
    public String getRecentHoldCategory() {
        return recentHoldCategory;
    }

    /**
     * @param recentHoldCategory
     *            the recentHoldCategory to set
     */
    public void setRecentHoldCategory(String recentHoldCategory) {
        this.recentHoldCategory = recentHoldCategory;
    }

    /**
     * @return the allHolds
     */
    public List<StudyOnholdDTO> getAllHolds() {
        return allHolds;
    }

    /**
     * @param allHolds
     *            the allHolds to set
     */
    public void setAllHolds(List<StudyOnholdDTO> allHolds) {
        this.allHolds = allHolds;
    }

    /**
     * @return date
     */
    public Date getAdminAbstractionCompletedDate() {
        return getDateOfMilestone(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE);
    }

    /**
     * @return date
     */
    public Date getAdminQCCompletedDate() {
        return getDateOfMilestone(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE);
    }

    /**
     * @return date
     */
    public Date getScientificAbstractionCompletedDate() {
        return getDateOfMilestone(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE);
    }

    /**
     * @return date
     */
    public Date getScientificQCCompletedDate() {
        return getDateOfMilestone(MilestoneCode.SCIENTIFIC_QC_COMPLETE);
    }

    /**
     * @return date
     */
    public Date getReadyForTSRDate() {
        return getDateOfMilestone(MilestoneCode.READY_FOR_TSR);
    }

    /**
     * @return date when submission was accepted.
     */
    public Date getAcceptedDate() {
        return getDateOfMilestone(MilestoneCode.SUBMISSION_ACCEPTED);
    }

    private Date getDateOfMilestone(MilestoneCode mc) {
        MilestoneDTO milestone = findMilestoneInHistory(mc);
        return milestone != null ? milestone.getMilestoneDate() : null;
    }

    /**
     * @return the activeHoldDate
     */
    public Date getActiveHoldDate() {
        return activeHoldDate;
    }

    /**
     * @param activeHoldDate
     *            the activeHoldDate to set
     */
    public void setActiveHoldDate(Date activeHoldDate) {
        this.activeHoldDate = activeHoldDate;
    }

    /**
     * @return SubmissionType
     */
    public final String getSubmissionType() {
        if (StringUtils.isNotBlank(getAmendmentNumber())
                || getAmendmentDate() != null) {
            return AMENDMENT;
        } else if (isProprietaryTrial()) {
            return ABBREVIATED;
        } else {
            return COMPLETE;
        }
    }

    /**
     * @return the overriddenExpectedAbstractionCompletionDate
     */
    public Date getOverriddenExpectedAbstractionCompletionDate() {
        return overriddenExpectedAbstractionCompletionDate;
    }

    /**
     * @param overriddenExpectedAbstractionCompletionDate the overriddenExpectedAbstractionCompletionDate to set
     */
    public void setOverriddenExpectedAbstractionCompletionDate(
            Date overriddenExpectedAbstractionCompletionDate) {
        this.overriddenExpectedAbstractionCompletionDate = overriddenExpectedAbstractionCompletionDate;
    }

    /**
     * @return the overriddenExpectedAbstractionCompletionComments
     */
    public String getOverriddenExpectedAbstractionCompletionComments() {
        return overriddenExpectedAbstractionCompletionComments;
    }

    /**
     * @param overriddenExpectedAbstractionCompletionComments
     *            the overriddenExpectedAbstractionCompletionComments to set
     */
    public void setOverriddenExpectedAbstractionCompletionComments(
            String overriddenExpectedAbstractionCompletionComments) {
        this.overriddenExpectedAbstractionCompletionComments = overriddenExpectedAbstractionCompletionComments;
    }
    
    /**
     * @return boolean isAbstractionCompletionDateOverridden
     */
    public boolean isAbstractionCompletionDateOverridden() {
        return getOverriddenExpectedAbstractionCompletionDate() != null
                && !DateUtils.isSameDay(getCalculatedAbstractionCompletionDate(),
                        getOverriddenExpectedAbstractionCompletionDate());
    }
    /**
     * @return the pcdSentToPIODate
     */
    public Date getPcdSentToPIODate() {
        return pcdSentToPIODate;
    }

    /**
     * @param pcdSentToPIODate the pcdSentToPIODate to set
     */
    public void setPcdSentToPIODate(Date pcdSentToPIODate) {
        this.pcdSentToPIODate = pcdSentToPIODate;
    }

    /**
     * @return the pcdConfirmedDate
     */
    public Date getPcdConfirmedDate() {
        return pcdConfirmedDate;
    }

    /**
     * @param pcdConfirmedDate the pcdConfirmedDate to set
     */
    public void setPcdConfirmedDate(Date pcdConfirmedDate) {
        this.pcdConfirmedDate = pcdConfirmedDate;
    }

    /**
     * @return the desgneeNotifiedDate
     */
    public Date getDesgneeNotifiedDate() {
        return desgneeNotifiedDate;
    }

    /**
     * @param desgneeNotifiedDate the desgneeNotifiedDate to set
     */
    public void setDesgneeNotifiedDate(Date desgneeNotifiedDate) {
        this.desgneeNotifiedDate = desgneeNotifiedDate;
    }

    /**
     * @return the reportingInProcessDate
     */
    public Date getReportingInProcessDate() {
        return reportingInProcessDate;
    }

    /**
     * @param reportingInProcessDate the reportingInProcessDate to set
     */
    public void setReportingInProcessDate(Date reportingInProcessDate) {
        this.reportingInProcessDate = reportingInProcessDate;
    }

    /**
     * @return the threeMonthReminderDate
     */
    public Date getThreeMonthReminderDate() {
        return threeMonthReminderDate;
    }

    /**
     * @param threeMonthReminderDate the threeMonthReminderDate to set
     */
    public void setThreeMonthReminderDate(Date threeMonthReminderDate) {
        this.threeMonthReminderDate = threeMonthReminderDate;
    }

    /**
     * @return the fiveMonthReminderDate
     */
    public Date getFiveMonthReminderDate() {
        return fiveMonthReminderDate;
    }

    /**
     * @param fiveMonthReminderDate the fiveMonthReminderDate to set
     */
    public void setFiveMonthReminderDate(Date fiveMonthReminderDate) {
        this.fiveMonthReminderDate = fiveMonthReminderDate;
    }

    /**
     * @return the sevenMonthEscalationtoPIODate
     */
    public Date getSevenMonthEscalationtoPIODate() {
        return sevenMonthEscalationtoPIODate;
    }

    /**
     * @param sevenMonthEscalationtoPIODate the sevenMonthEscalationtoPIODate to set
     */
    public void setSevenMonthEscalationtoPIODate(Date sevenMonthEscalationtoPIODate) {
        this.sevenMonthEscalationtoPIODate = sevenMonthEscalationtoPIODate;
    }

    /**
     * @return the resultsSentToPIODate
     */
    public Date getResultsSentToPIODate() {
        return resultsSentToPIODate;
    }

    /**
     * @param resultsSentToPIODate the resultsSentToPIODate to set
     */
    public void setResultsSentToPIODate(Date resultsSentToPIODate) {
        this.resultsSentToPIODate = resultsSentToPIODate;
    }

    /**
     * @return the resultsApprovedByPIODate
     */
    public Date getResultsApprovedByPIODate() {
        return resultsApprovedByPIODate;
    }

    /**
     * @param resultsApprovedByPIODate the resultsApprovedByPIODate to set
     */
    public void setResultsApprovedByPIODate(Date resultsApprovedByPIODate) {
        this.resultsApprovedByPIODate = resultsApprovedByPIODate;
    }

    /**
     * @return the prsReleaseDate
     */
    public Date getPrsReleaseDate() {
        return prsReleaseDate;
    }

    /**
     * @param prsReleaseDate the prsReleaseDate to set
     */
    public void setPrsReleaseDate(Date prsReleaseDate) {
        this.prsReleaseDate = prsReleaseDate;
    }

    /**
     * @return the qaCommentsReturnedDate
     */
    public Date getQaCommentsReturnedDate() {
        return qaCommentsReturnedDate;
    }

    /**
     * @param qaCommentsReturnedDate the qaCommentsReturnedDate to set
     */
    public void setQaCommentsReturnedDate(Date qaCommentsReturnedDate) {
        this.qaCommentsReturnedDate = qaCommentsReturnedDate;
    }

    /**
     * @return the trialPublishedDate
     */
    public Date getTrialPublishedDate() {
        return trialPublishedDate;
    }

    /**
     * @param trialPublishedDate the trialPublishedDate to set
     */
    public void setTrialPublishedDate(Date trialPublishedDate) {
        this.trialPublishedDate = trialPublishedDate;
    }
    


    /**
     * @return the activeHoldReason
     */
    public OnholdReasonCode getActiveHoldReason() {
        return activeHoldReason;
    }

    /**
     * @param activeHoldReason the activeHoldReason to set
     */
    public void setActiveHoldReason(OnholdReasonCode activeHoldReason) {
        this.activeHoldReason = activeHoldReason;
    }

    /**
     * @return the activeHoldReasonCategory
     */
    public String getActiveHoldReasonCategory() {
        return activeHoldReasonCategory;
    }

    /**
     * @param activeHoldReasonCategory the activeHoldReasonCategory to set
     */
    public void setActiveHoldReasonCategory(String activeHoldReasonCategory) {
        this.activeHoldReasonCategory = activeHoldReasonCategory;
    }

    /**
     * @return designeeNamesList
     */
    public String getDesigneeNamesList() {
        return designeeNamesList;
    }

    /**
     * @param designeeNamesList designeeNamesList
     */
    public void setDesigneeNamesList(String designeeNamesList) {
        this.designeeNamesList = designeeNamesList;
    }

    /**
     * Gets the programCodes list
     * @return the programCodes  list
     */
    public List<ProgramCodeDTO> getProgramCodes() {
        return programCodes;
    }

    /**
     * Sets the  programCodes
     * @param programCodes  the programCodes list
     */
    public void setProgramCodes(List<ProgramCodeDTO> programCodes) {
        this.programCodes = programCodes;
    }
    /**
     * Will return the sorted set of program codes.
     * @return list of program codes
     */
    public List<ProgramCodeDTO> getProgramCodesAsOrderedList() {
        List<ProgramCodeDTO> list = new ArrayList<ProgramCodeDTO>(programCodes);
        Collections.sort(list, new Comparator<ProgramCodeDTO>() {
            @Override
            public int compare(ProgramCodeDTO o1, ProgramCodeDTO o2) {
                return o1.getProgramCode().compareTo(o2.getProgramCode());
            }
        });

        return list;
    }


}
