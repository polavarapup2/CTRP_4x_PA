/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.search;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Abraham J. Evans-EL
 *
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength",
        "PMD.TooManyMethods" })
public class StudyProtocolOptions {

    private boolean excludeRejectedTrials;
    private boolean excludeTerminatedTrials;
    private boolean myTrialsOnly;
    private boolean searchOnHoldTrials;   
    private boolean searchOffHoldTrials;
    private List<SubmissionTypeCode> trialSubmissionTypes = new ArrayList<SubmissionTypeCode>();
    private boolean lockedTrials;
    private boolean searchCTEPTrials;
    private boolean searchDCPTrials;    
    private boolean searchCTEPAndDCPTrials;
    private Long userId;
    private String lockedUser;
    private boolean inboxProcessing;
    private List<PhaseCode> phaseCodes = new ArrayList<PhaseCode>();
    private String countryName;   
    private List<String> states = new ArrayList<String>();
    private String city;
    private List<Long> summary4AnatomicSites = new ArrayList<Long>();
    private List<Long> bioMarkers = new ArrayList<Long>();
    private List<Long> leadOrganizationIds = new ArrayList<Long>(); 
    private List<Long> pdqDiseases = new ArrayList<Long>();
    private List<Long> participatingSiteIds = new ArrayList<Long>();
    private List<Long> interventionIds = new ArrayList<Long>();
    private List<Long> interventionAlternateNameIds = new ArrayList<Long>();
    private List<InterventionTypeCode> interventionTypes = new ArrayList<InterventionTypeCode>();
    private Boolean ctgovXmlRequiredIndicator; 
    private String anyTypeIdentifier;
    private Boolean checkedOut;
    private Date submittedOnOrAfter;
    private Date submittedOnOrBefore;
    private String submitterAffiliateOrgId;
    private List<String> submitterAffiliateOrgName = new ArrayList<String>();
    private Boolean nciSponsored;
    private Boolean hasTweets;
    private Boolean holdRecordExists;
    private boolean excludeCtepDcpTrials;
    private MilestoneCode currentOrPreviousMilestone;  
    private List<OnholdReasonCode> onholdReasons = new ArrayList<OnholdReasonCode>();    
    private List<String> onholdOtherReasonCategories = new ArrayList<String>();
    private List<MilestoneFilter> milestoneFilters = new ArrayList<MilestoneFilter>();    
    private List<Integer> processingPriority = new ArrayList<Integer>();
    private List<String> studySource = new ArrayList<>();
    private List<String> primaryPurposeCodes = new ArrayList<String>();
    private List<Boolean> section801Indicators;
    private Date pcdFromDate;
    private Date pcdToDate;
    private List<ActualAnticipatedTypeCode> pcdDateTypes;
    private StudyFlagReasonCode notFlaggedWith;
    private List<RecruitmentStatusCode> siteStatusCodes = new ArrayList<>();
    private Date reportingPeriodStart;
    private Date reportingPeriodEnd;
    private Set<StudyStatusCode> studyStatusCodes = new HashSet<StudyStatusCode>();
    private List<Long> programCodeIds = new ArrayList<Long>();

    /**
     * @return excludeRejectedTrials
     */
    public boolean isExcludeRejectedTrials() {
        return excludeRejectedTrials;
    }

    /**
     * @param excludeRejectedTrials whether to exclude rejected trials
     */
    public void setExcludeRejectedTrials(boolean excludeRejectedTrials) {
        this.excludeRejectedTrials = excludeRejectedTrials;
    }

    /**
     * @return myTrialsOnly
     */
    public boolean isMyTrialsOnly() {
        return myTrialsOnly;
    }

    /**
     * @param myTrialsOnly whether to only include my trials
     */
    public void setMyTrialsOnly(boolean myTrialsOnly) {
        this.myTrialsOnly = myTrialsOnly;
    }

    /**
     * @return searchOnHoldTrials
     */
    public boolean isSearchOnHoldTrials() {
        return searchOnHoldTrials;
    }

    /**
     * @param searchOnHoldTrials whether to search onhold trials
     */
    public void setSearchOnHoldTrials(boolean searchOnHoldTrials) {
        this.searchOnHoldTrials = searchOnHoldTrials;
    }
    

    /**
     * @return lockedTrials
     */
    public boolean isLockedTrials() {
        return lockedTrials;
    }

    /**
     * @param lockedTrials whether to include locked trials
     */
    public void setLockedTrials(boolean lockedTrials) {
        this.lockedTrials = lockedTrials;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the id of the user performing the search
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return participating site ids
     */
    public List<Long> getParticipatingSiteIds() {
        return participatingSiteIds;
    }

    /**
     * @param participatingSiteIds the ids of the participating sites
     */
    public void setParticipatingSiteIds(List<Long> participatingSiteIds) {
        this.participatingSiteIds = participatingSiteIds;
    }

    /**
     * @return lockedUser
     */
    public String getLockedUser() {
        return lockedUser;
    }

    /**
     * @param lockedUser the user that has locked the trial
     */
    public void setLockedUser(String lockedUser) {
        this.lockedUser = lockedUser;
    }

    /**
     * @return inboxProcessing
     */
    public boolean isInboxProcessing() {
        return inboxProcessing;
    }

    /**
     * @param inboxProcessing whether to include inbox processing entries
     */
    public void setInboxProcessing(boolean inboxProcessing) {
        this.inboxProcessing = inboxProcessing;
    }

    /**
     * @return the phaseCodes
     */
    public List<PhaseCode> getPhaseCodes() {
        return phaseCodes;
    } 

    /**
     * @param phaseCodes the phaseCodes to set
     */
    public void setPhaseCodes(List<PhaseCode> phaseCodes) {
        this.phaseCodes = phaseCodes;
    }

    /**
     * @param phaseCodeValues the phaseCodes to set
     */
    public void setPhaseCodesByValues(List<String> phaseCodeValues) {        
        for (String phaseCode : phaseCodeValues) {
            this.phaseCodes.add(PhaseCode.getByCode(phaseCode));
        }
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the states
     */
    public List<String> getStates() {
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(List<String> states) {
        this.states = states;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }  
    
    
    /**
     * @return the summary4AnatomicSites
     */
    public List<Long> getSummary4AnatomicSites() {
        return summary4AnatomicSites;
    }

    /**
     * @param summary4AnatomicSites the summary4AnatomicSites to set
     */
    public void setSummary4AnatomicSites(List<Long> summary4AnatomicSites) {
        this.summary4AnatomicSites = summary4AnatomicSites;
    }

    /**
     * return true if criteria contains location data.
     * @return boolean
     */
    public boolean isByLocation() {
        return (StringUtils.isNotBlank(getCountryName()) || StringUtils.isNotBlank(getCity()) 
                || CollectionUtils.isNotEmpty(getStates()));
    }

    /**
     * @return the bioMarkers
     */
    public List<Long> getBioMarkers() {
        return bioMarkers;
    }

    /**
     * @param bioMarkers the bioMarkers to set
     */
    public void setBioMarkers(List<Long> bioMarkers) {
        this.bioMarkers = bioMarkers;
    }

    /**
     * @return the leadOrganizationIds
     */
    public List<Long> getLeadOrganizationIds() {
        return leadOrganizationIds;
    }

    /**
     * @param leadOrganizationIds the leadOrganizationIds to set
     */
    public void setLeadOrganizationIds(List<Long> leadOrganizationIds) {
        this.leadOrganizationIds = leadOrganizationIds;
    }

    /**
     * @return the pdqDiseases
     */
    public List<Long> getPdqDiseases() {
        return pdqDiseases;
    }

    /**
     * @param pdqDiseases the pdqDiseases to set
     */
    public void setPdqDiseases(List<Long> pdqDiseases) {
        this.pdqDiseases = pdqDiseases;
    }

    /**
     * @return the interventionIds
     */
    public List<Long> getInterventionIds() {
        return interventionIds;
    }

    /**
     * @param interventionIds the interventionIds to set
     */
    public void setInterventionIds(List<Long> interventionIds) {
        this.interventionIds = interventionIds;
    }

    /**
     * @return the interventionAlternateNameIds
     */
    public List<Long> getInterventionAlternateNameIds() {
        return interventionAlternateNameIds;
    }

    /**
     * @param interventionAlternateNameIds the interventionAlternateNameIds to set
     */
    public void setInterventionAlternateNameIds(List<Long> interventionAlternateNameIds) {
        this.interventionAlternateNameIds = interventionAlternateNameIds;
    }

    /**
     * @return the interventionTypes
     */
    public List<InterventionTypeCode> getInterventionTypes() {
        return interventionTypes;
    }

    /**
     * @param interventionTypes the interventionTypes to set
     */
    public void setInterventionTypes(List<String> interventionTypes) {
        this.interventionTypes = new ArrayList<InterventionTypeCode>();
        for (String intvType : interventionTypes) {
            this.interventionTypes.add(InterventionTypeCode.getByCode(intvType));
        }
    }

    /**
     * gets ctgoc xml required value.
     * 
     * @return boolean
     */
    public Boolean getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }

    /**
     * sets ctgov xml required value.
     * 
     * @param ctgovXmlRequiredIndicator
     *            xml indicator
     */
    public void setCtgovXmlRequiredIndicator(Boolean ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }

    /**
     * @return the searchCTEPTrials
     */
    public boolean isSearchCTEPTrials() {
        return searchCTEPTrials;
    }

    /**
     * @param searchCTEPTrials
     *            the searchCTEPTrials to set
     */
    public void setSearchCTEPTrials(boolean searchCTEPTrials) {
        this.searchCTEPTrials = searchCTEPTrials;
    }

    /**
     * @return the searchDCPTrials
     */
    public boolean isSearchDCPTrials() {
        return searchDCPTrials;
    }

    /**
     * @param searchDCPTrials
     *            the searchDCPTrials to set
     */
    public void setSearchDCPTrials(boolean searchDCPTrials) {
        this.searchDCPTrials = searchDCPTrials;
    }

    /**
     * @return the searchOffHoldTrials
     */
    public boolean isSearchOffHoldTrials() {
        return searchOffHoldTrials;
    }

    /**
     * @param searchOffHoldTrials the searchOffHoldTrials to set
     */
    public void setSearchOffHoldTrials(boolean searchOffHoldTrials) {
        this.searchOffHoldTrials = searchOffHoldTrials;
    }

    /**
     * @return the searchCTEPAndDCPTrials
     */
    public boolean isSearchCTEPAndDCPTrials() {
        return searchCTEPAndDCPTrials;
    }

    /**
     * @param searchCTEPAndDCPTrials the searchCTEPAndDCPTrials to set
     */
    public void setSearchCTEPAndDCPTrials(boolean searchCTEPAndDCPTrials) {
        this.searchCTEPAndDCPTrials = searchCTEPAndDCPTrials;
    }

    /**
     * @return the anyTypeIdentifier
     */
    public String getAnyTypeIdentifier() {
        return anyTypeIdentifier;
    }

    /**
     * @param anyTypeIdentifier the anyTypeIdentifier to set
     */
    public void setAnyTypeIdentifier(String anyTypeIdentifier) {
        this.anyTypeIdentifier = anyTypeIdentifier;
    }

    /**
     * @return the checkedOut
     */
    public Boolean getCheckedOut() {
        return checkedOut;
    }

    /**
     * @param checkedOut the checkedOut to set
     */
    public void setCheckedOut(Boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    /**
     * @return the submittedOnOrAfter
     */
    public Date getSubmittedOnOrAfter() {
        return submittedOnOrAfter;
    }

    /**
     * @param submittedOnOrAfter the submittedOnOrAfter to set
     */
    public void setSubmittedOnOrAfter(Date submittedOnOrAfter) {
        this.submittedOnOrAfter = submittedOnOrAfter;
    }

    /**
     * @return the submittedOnOrBefore
     */
    public Date getSubmittedOnOrBefore() {
        return submittedOnOrBefore;
    }

    /**
     * @param submittedOnOrBefore the submittedOnOrBefore to set
     */
    public void setSubmittedOnOrBefore(Date submittedOnOrBefore) {
        this.submittedOnOrBefore = submittedOnOrBefore;
    }

    /**
     * @return the submitterAffiliateOrgId
     */
    public String getSubmitterAffiliateOrgId() {
        return submitterAffiliateOrgId;
    }

    /**
     * @param submitterAffiliateOrgId the submitterAffiliateOrgId to set
     */
    public void setSubmitterAffiliateOrgId(String submitterAffiliateOrgId) {
        this.submitterAffiliateOrgId = submitterAffiliateOrgId;
    }

    /**
     * 
     * @return submitterAffiliateOrgName submitterAffiliateOrgName
     */

    public List<String> getSubmitterAffiliateOrgName() {
        return submitterAffiliateOrgName;
    }
    /**
     * 
     * @param submitterAffiliateOrgName submitterAffiliateOrgName
     */
    public void setSubmitterAffiliateOrgName(List<String> submitterAffiliateOrgName) {
        this.submitterAffiliateOrgName = submitterAffiliateOrgName;
    }

    /**
     * @return the trialSubmissionTypes
     */
    public List<SubmissionTypeCode> getTrialSubmissionTypes() {
        return trialSubmissionTypes;
    }

    /**
     * @param trialSubmissionTypes the trialSubmissionTypes to set
     */
    public void setTrialSubmissionTypes(
            List<SubmissionTypeCode> trialSubmissionTypes) {
        this.trialSubmissionTypes = trialSubmissionTypes;
    }
    
    /**
     * @param code SubmissionTypeCode
     */
    public void setTrialSubmissionType(SubmissionTypeCode code) {
        this.trialSubmissionTypes.add(code);
    }

    /**
     * @return the nciSponsored
     */
    public Boolean getNciSponsored() {
        return nciSponsored;
    }

    /**
     * @param nciSponsored the nciSponsored to set
     */
    public void setNciSponsored(Boolean nciSponsored) {
        this.nciSponsored = nciSponsored;
    }

    /**
     * @return the holdRecordExists
     */
    public Boolean getHoldRecordExists() {
        return holdRecordExists;
    }

    /**
     * @param holdRecordExists the holdRecordExists to set
     */
    public void setHoldRecordExists(Boolean holdRecordExists) {
        this.holdRecordExists = holdRecordExists;
    }

    /**
     * @return the excludeCtepDcpTrials
     */
    public boolean isExcludeCtepDcpTrials() {
        return excludeCtepDcpTrials;
    }

    /**
     * @param excludeCtepDcpTrials the excludeCtepDcpTrials to set
     */
    public void setExcludeCtepDcpTrials(boolean excludeCtepDcpTrials) {
        this.excludeCtepDcpTrials = excludeCtepDcpTrials;
    }

    /**
     * @return the currentOrPreviousMilestone
     */
    public MilestoneCode getCurrentOrPreviousMilestone() {
        return currentOrPreviousMilestone;
    }

    /**
     * @param currentOrPreviousMilestone the currentOrPreviousMilestone to set
     */
    public void setCurrentOrPreviousMilestone(
            MilestoneCode currentOrPreviousMilestone) {
        this.currentOrPreviousMilestone = currentOrPreviousMilestone;
    }

    /**
     * @return the onholdReasons
     */
    public List<OnholdReasonCode> getOnholdReasons() {
        return onholdReasons;
    }

    /**
     * @param onholdReasons the onholdReasons to set
     */
    public void setOnholdReasons(List<OnholdReasonCode> onholdReasons) {
        this.onholdReasons = onholdReasons;
    }
    
    /**
     * @author Denis G. Krylov
     *
     */
    public static final class MilestoneFilter implements Serializable {
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private List<MilestoneCode> activeMilestones = new ArrayList<MilestoneCode>();
        private List<MilestoneCode> milestonesToExclude = new ArrayList<MilestoneCode>();
        
        
        
        /**
         * @param activeMilestones activeMilestones
         * @param milestonesToExclude milestonesToExclude
         */
        public MilestoneFilter(List<MilestoneCode> activeMilestones,
                List<MilestoneCode> milestonesToExclude) {
            this.activeMilestones = activeMilestones;
            this.milestonesToExclude = milestonesToExclude;
        }
        /**
         * @return the activeMilestones
         */
        public List<MilestoneCode> getActiveMilestones() {
            return activeMilestones;
        }
        /**
         * @param activeMilestones the activeMilestones to set
         */
        public void setActiveMilestones(List<MilestoneCode> activeMilestones) {
            this.activeMilestones = activeMilestones;
        }
        /**
         * @return the milestonesToExclude
         */
        public List<MilestoneCode> getMilestonesToExclude() {
            return milestonesToExclude;
        }
        /**
         * @param milestonesToExclude the milestonesToExclude to set
         */
        public void setMilestonesToExclude(List<MilestoneCode> milestonesToExclude) {
            this.milestonesToExclude = milestonesToExclude;
        }        
    }

    /**
     * @return the milestoneFilters
     */
    public List<MilestoneFilter> getMilestoneFilters() {
        return milestoneFilters;
    }

    /**
     * @param milestoneFilters the milestoneFilters to set
     */
    public void setMilestoneFilters(List<MilestoneFilter> milestoneFilters) {
        this.milestoneFilters = milestoneFilters;
    }

    /**
     * @return the processingPriority
     */
    public List<Integer> getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(List<Integer> processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @return the excludeTerminatedTrials
     */
    public boolean isExcludeTerminatedTrials() {
        return excludeTerminatedTrials;
    }

    /**
     * @param excludeTerminatedTrials the excludeTerminatedTrials to set
     */
    public void setExcludeTerminatedTrials(boolean excludeTerminatedTrials) {
        this.excludeTerminatedTrials = excludeTerminatedTrials;
    }

    

    /**
     * @return the primaryPurposeCodes
     */
    public List<String> getPrimaryPurposeCodes() {
        return primaryPurposeCodes;
    }

    /**
     * @param primaryPurposeCodes the primaryPurposeCodes to set
     */
    public void setPrimaryPurposeCodes(List<String> primaryPurposeCodes) {
        this.primaryPurposeCodes = primaryPurposeCodes;
    }

    /**
     * @return List<PrimaryPurposeCode>
     */
    public List<PrimaryPurposeCode> getPrimaryPurposeCodeEnums() {
        List<PrimaryPurposeCode> list = new ArrayList<>();
        for (String code : getPrimaryPurposeCodes()) {
            list.add(PrimaryPurposeCode.getByCode(code));
        }
        return list;
    }

    /**
     * @return the studySource
     */
    public List<String> getStudySource() {
        return studySource;
    }

    /**
     * @param studySource the studySource to set
     */
    public void setStudySource(List<String> studySource) {
        this.studySource = studySource;
    }

    /**
     * @return the onholdReasonCategories
     */
    public List<String> getOnholdOtherReasonCategories() {
        return onholdOtherReasonCategories;
    }

    /**
     * @param onholdReasonCategories the onholdReasonCategories to set
     */
    public void setOnholdOtherReasonCategories(List<String> onholdReasonCategories) {
        this.onholdOtherReasonCategories = onholdReasonCategories;
    }

  /**
     * @return the section801Indicators
     */
    public List<Boolean> getSection801Indicators() {
        return section801Indicators;
    }

    /**
     * @param section801Indicators the section801Indicators to set
     */
    public void setSection801Indicators(List<Boolean> section801Indicators) {
        this.section801Indicators = section801Indicators;
    }

    /**
     * @return the pcdFromDate
     */
    public Date getPcdFromDate() {
        return pcdFromDate;
    }

    /**
     * @param pcdFromDate the pcdFromDate to set
     */
    public void setPcdFromDate(Date pcdFromDate) {
        this.pcdFromDate = pcdFromDate;
    }

    /**
     * @return the pcdToDate
     */
    public Date getPcdToDate() {
        return pcdToDate;
    }

    /**
     * @param pcdToDate the pcdToDate to set
     */
    public void setPcdToDate(Date pcdToDate) {
        this.pcdToDate = pcdToDate;
    }

    /**
     * @return the pcdDateTypes
     */
    public List<ActualAnticipatedTypeCode> getPcdDateTypes() {
        return pcdDateTypes;
    }

    /**
     * @param pcdDateTypes the pcdDateTypes to set
     */
    public void setPcdDateTypes(List<ActualAnticipatedTypeCode> pcdDateTypes) {
        this.pcdDateTypes = pcdDateTypes;
    }

    /**
     * @return the hasTweets
     */
    public Boolean getHasTweets() {
        return hasTweets;
    }

    /**
     * @param hasTweets the hasTweets to set
     */
    public void setHasTweets(Boolean hasTweets) {
        this.hasTweets = hasTweets;
    }

    /**
     * @return the notFlaggedWith
     */
    public StudyFlagReasonCode getNotFlaggedWith() {
        return notFlaggedWith;
    }

    /**
     * @param notFlaggedWith the notFlaggedWith to set
     */
    public void setNotFlaggedWith(StudyFlagReasonCode notFlaggedWith) {
        this.notFlaggedWith = notFlaggedWith;
    }

    /**
     * @return the siteStatusCodes
     */
    public List<RecruitmentStatusCode> getSiteStatusCodes() {
        return siteStatusCodes;
    }

    /**
     * @param siteStatusCodes the siteStatusCodes to set
     */
    public void setSiteStatusCodes(List<RecruitmentStatusCode> siteStatusCodes) {
        this.siteStatusCodes = siteStatusCodes;
    }

    /**
     *
     * @return the reportingPeriodStart
     */
    public Date getReportingPeriodStart() {
        return reportingPeriodStart;
    }

    /**
     * Sets the reportingPeriodStart
     * @param reportingPeriodStart  the reportingPeriodStart
     */
    public void setReportingPeriodStart(Date reportingPeriodStart) {
        this.reportingPeriodStart = reportingPeriodStart;
    }

    /**
     *
     * @return the reportingPeriodEnd
     */
    public Date getReportingPeriodEnd() {
        return reportingPeriodEnd;
    }

    /**
     * Sets the reportingPeriodEnd
     * @param reportingPeriodEnd  the reportingPeriodEnd
     */
    public void setReportingPeriodEnd(Date reportingPeriodEnd) {
        this.reportingPeriodEnd = reportingPeriodEnd;
    }

    /**
     * Gets the studyStatusCodes
     * @return  the studyStatusCodes
     */
    public Set<StudyStatusCode> getStudyStatusCodes() {
        return studyStatusCodes;
    }

    /**
     * Sets the studyStatusCodes
     * @param studyStatusCodes   the studyStatusCodes
     */
    public void setStudyStatusCodes(Set<StudyStatusCode> studyStatusCodes) {
        this.studyStatusCodes = studyStatusCodes;
    }

    /**
     * The programCodeIds
     * @return  the programCodeIDs
     */
    public List<Long> getProgramCodeIds() {
        return programCodeIds;
    }

    /**
     * The programCodeIds
     * @param programCodeIds the programCode IDs
     */
    public void setProgramCodeIds(List<Long> programCodeIds) {
        this.programCodeIds = programCodeIds;
    }
}
