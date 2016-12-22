/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.StudyProcessingError;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * StudyProtocolDTO for transferring Study Protocol object .
 * @author Naveen Amiruddin
 * @since 08/22/2008
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class StudyProtocolDTO extends AbstractStudyProtocolDTO {

    private static final long serialVersionUID = 7235772554482606133L;
    private St acronym;
    private Cd accrualReportingMethodCode;
    //TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolDTO
    //once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
    private DSet<Ii> secondaryIdentifiers;
    private DSet<Cd> summary4AnatomicSites;
    private DSet<Tel> recordOwners;
    private Bl expandedAccessIndicator;
    private Bl reviewBoardApprovalRequiredIndicator;
    private St publicDescription;
    private St publicTitle;
    private Ts recordVerificationDate;
    private St scientificDescription;
    private St keywordText;
    private Bl acceptHealthyVolunteersIndicator;
    private Bl ctroOverride;
    private Cd statusCode;
    private Ts statusDate;
    private St amendmentNumber;
    private Ts amendmentDate;
    private Cd amendmentReasonCode;
    private Int submissionNumber;
    private Ivl<Int> targetAccrualNumber;
    private Int finalAccrualNumber;
    private List<DocumentWorkflowStatusCode> processingStatuses;
    private DSet<St> secondaryPurposes = new DSet<St>();
    private St secondaryPurposeOtherText;   
    private St comments;
    private Int processingPriority;
    private Ii assignedUser;    
    private Bl nciGrant;
    private Set<StudyAlternateTitleDTO> studyAlternateTitles;
    private Cd studySource;
    private Ii submitingOgranization;
    private String ctroOverideFlagComments;
    private Ts expectedAbstractionCompletionDate;
    private St expectedAbstractionCompletionComments;
    //Results reporting dates
    private Ts pcdSentToPIODate;
    private Ts pcdConfirmedDate;
    private Ts desgneeNotifiedDate;
    private Ts reportingInProcessDate;
    private Ts threeMonthReminderDate;
    private Ts fiveMonthReminderDate;
    private Ts sevenMonthEscalationtoPIODate;
    private Ts resultsSentToPIODate;
    private Ts resultsApprovedByPIODate;
    private Ts prsReleaseDate;
    private Ts qaCommentsReturnedDate;
    private Ts trialPublishedDate;
    private Bl delayedPostingIndicatorChanged;
    private Bl useStandardLanguage;
    private Bl dateEnteredInPrs;
    private Bl designeeAccessRevoked;
    private Ts designeeAccessRevokedDate;
    private Bl changesInCtrpCtGov;
    private Ts changesInCtrpCtGovDate;
    private Bl sendToCtGovUpdated;
    private List<StudyProcessingError> studyProcessingErrors = new ArrayList<StudyProcessingError>();    
    private List<ProgramCodeDTO> programCodes;    
    
    /**
     *
     * @return acronym
     */
    public St getAcronym() {
        return acronym;
    }

    /**
     *
     * @param acronym acronym
     */
    public void setAcronym(St acronym) {
        this.acronym = acronym;
    }


    /**
     *
     * @return accrualReportingMethodCode
     */
    public Cd getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }

    /**
     *
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(Cd accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }

    /**
     *
     * @return expandedAccessIndicator
     */
    public Bl getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     *
     * @param expandedAccessIndicator expandedAccessIndicator
     */
    public void setExpandedAccessIndicator(Bl expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }


    /**
     * @return the reviewBoardApprovalRequiredIndicator
     */
    public Bl getReviewBoardApprovalRequiredIndicator() {
        return reviewBoardApprovalRequiredIndicator;
    }

    /**
     * @param reviewBoardApprovalRequiredIndicator the reviewBoardApprovalRequiredIndicator to set
     */
    public void setReviewBoardApprovalRequiredIndicator(Bl reviewBoardApprovalRequiredIndicator) {
        this.reviewBoardApprovalRequiredIndicator = reviewBoardApprovalRequiredIndicator;
    }

    /**
     *
     * @return publicDescription
     */
    public St getPublicDescription() {
        return publicDescription;
    }

    /**
     *
     * @param publicDescription publicDescription
     */
    public void setPublicDescription(St publicDescription) {
        this.publicDescription = publicDescription;
    }

    /**
     *
     * @return publicTitle
     */
    public St getPublicTitle() {
        return publicTitle;
    }

    /**
     *
     * @param publicTitle publicTitle
     */
    public void setPublicTitle(St publicTitle) {
        this.publicTitle = publicTitle;
    }

    /**
     *
     * @return recordVerificationDate
     */
    public Ts getRecordVerificationDate() {
        return recordVerificationDate;
    }

    /**
     *
     * @param recordVerificationDate recordVerificationDate
     */
    public void setRecordVerificationDate(Ts recordVerificationDate) {
        this.recordVerificationDate = recordVerificationDate;
    }

    /**
     *
     * @return scientificDescription
     */
    public St getScientificDescription() {
        return scientificDescription;
    }

    /**
     *
     * @param scientificDescription scientificDescription
     */
    public void setScientificDescription(St scientificDescription) {
        this.scientificDescription = scientificDescription;
    }

    /**
     * @return keywordText
     */
    public St getKeywordText() {
        return keywordText;
    }

    /**
     * @param keywordText keywordText
     */
    public void setKeywordText(St keywordText) {
        this.keywordText = keywordText;
    }
    /**
     * @return acceptHealthyVolunteersIndicator
     */
    public Bl getAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }

    /**
     * @param acceptHealthyVolunteersIndicator acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(
            Bl acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }

    /**
     * @return the statusCode
     */
    public Cd getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusDate
     */
    public Ts getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Ts statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the amendmentNumber
     */
    public St getAmendmentNumber() {
        return amendmentNumber;
    }

    /**
     * @param amendmentNumber the amendmentNumber to set
     */
    public void setAmendmentNumber(St amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    /**
     * @return the amendmentDate
     */
    public Ts getAmendmentDate() {
        return amendmentDate;
    }

    /**
     * @param amendmentDate the amendmentDate to set
     */
    public void setAmendmentDate(Ts amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    /**
     * @return the amendmentReasonCode
     */
    public Cd getAmendmentReasonCode() {
        return amendmentReasonCode;
    }

    /**
     * @param amendmentReasonCode the amendmentReasonCode to set
     */
    public void setAmendmentReasonCode(Cd amendmentReasonCode) {
        this.amendmentReasonCode = amendmentReasonCode;
    }

    /**
     * @return the submissionNumber
     */
    public Int getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(Int submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * @return the targetAccrualNumber
     */
    public Ivl<Int> getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(Ivl<Int> targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    /**
     * @return the secondaryIdentifiers
     */
    public DSet<Ii> getSecondaryIdentifiers() {
        return secondaryIdentifiers;
    }

    /**
     * @param secondaryIdentifiers the secondaryIdentifiers to set
     */
    public void setSecondaryIdentifiers(DSet<Ii> secondaryIdentifiers) {
        this.secondaryIdentifiers = secondaryIdentifiers;
    }

    /**
     * @param summary4AnatomicSites the summary4AnatomicSites to set
     */
    public void setSummary4AnatomicSites(DSet<Cd> summary4AnatomicSites) {
        this.summary4AnatomicSites = summary4AnatomicSites;
    }

    /**
     * @return the summary4AnatomicSites
     */
    public DSet<Cd> getSummary4AnatomicSites() {
        return summary4AnatomicSites;
    }

    /**
     * @return the recordOwners
     */
    public DSet<Tel> getRecordOwners() {
        return recordOwners;
    }

    /**
     * @param recordOwners the recordOwners to set
     */
    public void setRecordOwners(DSet<Tel> recordOwners) {
        this.recordOwners = recordOwners;
    }

    /**
     * @return the processingStatuses
     */
    public List<DocumentWorkflowStatusCode> getProcessingStatuses() {
        return processingStatuses;
    }

    /**
     * @param processingStatuses the processingStatuses to set
     */
    public void setProcessingStatuses(
            List<DocumentWorkflowStatusCode> processingStatuses) {
        this.processingStatuses = processingStatuses;
    }
    
    /**
     * @return the secondaryPurposes
     */
    public DSet<St> getSecondaryPurposes() {
        return secondaryPurposes;
    }

    /**
     * @param secondaryPurposes the secondaryPurposes to set
     */
    public void setSecondaryPurposes(DSet<St> secondaryPurposes) {
        this.secondaryPurposes = secondaryPurposes;
    }

    /**
     * @return the comments
     */
    public St getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(St comments) {
        this.comments = comments;
    }

    /**
     * @return the processingPriority
     */
    public Int getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(Int processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @return the assignedUser
     */
    public Ii getAssignedUser() {
        return assignedUser;
    }

    /**
     * @param assignedUser the assignedUser to set
     */
    public void setAssignedUser(Ii assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * @return the ctroOverride
     */
    public Bl getCtroOverride() {
        return ctroOverride;
    }

    /**
     * @param ctroOverride the ctroOverride to set
     */
    public void setCtroOverride(Bl ctroOverride) {
        this.ctroOverride = ctroOverride;
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
     * @return the finalAccrualNumber
     */
    public Int getFinalAccrualNumber() {
        return finalAccrualNumber;
    }

    /**
     * @param finalAccrualNumber the finalAccrualNumber to set
     */
    public void setFinalAccrualNumber(Int finalAccrualNumber) {
        this.finalAccrualNumber = finalAccrualNumber;
    }

    /**
     * @return study alternate titles
     */
    public Set<StudyAlternateTitleDTO> getStudyAlternateTitles() {
        return studyAlternateTitles;
    }

    /**
     * @param studyAlternateTitles study alternate titles to set
     */
    public void setStudyAlternateTitles(
            Set<StudyAlternateTitleDTO> studyAlternateTitles) {
        this.studyAlternateTitles = studyAlternateTitles;
    }
    
    /**
     * Returns where the study was created
     * @return the source that created the study
     */
    public Cd getStudySource() {
        return studySource;
    }
    
    /**
     * Sets where the study was created
     * @param source the source that created the study.
     */
    public void setStudySource(Cd source) {
        studySource = source;
    }

    /**
     * @return the submitingOgranization
     */
    public Ii getSubmitingOgranization() {
        return submitingOgranization;
    }
    
    /**
     * @param submitingOgranization the submitingOgranization to set
     */
    public void setSubmitingOgranization(Ii submitingOgranization) {
        this.submitingOgranization = submitingOgranization;
    }

    /**
     * @return ctroOverideFlagComments
     */
    public String getCtroOverideFlagComments() {
        return ctroOverideFlagComments;
    }

    /**
     * @param ctroOverideFlagComments ctroOverideFlagComments
     */
    public void setCtroOverideFlagComments(String ctroOverideFlagComments) {
        this.ctroOverideFlagComments = ctroOverideFlagComments;
    }

    /**
     * @return the expectedAbstractionCompletionDate
     */
    public Ts getExpectedAbstractionCompletionDate() {
        return expectedAbstractionCompletionDate;
    }

    /**
     * @param expectedAbstractionCompletionDate the expectedAbstractionCompletionDate to set
     */
    public void setExpectedAbstractionCompletionDate(
            Ts expectedAbstractionCompletionDate) {
        this.expectedAbstractionCompletionDate = expectedAbstractionCompletionDate;
    }

    /**
     * @return the expectedAbstractionCompletionComments
     */
    public St getExpectedAbstractionCompletionComments() {
        return expectedAbstractionCompletionComments;
    }

    /**
     * @param expectedAbstractionCompletionComments the expectedAbstractionCompletionComments to set
     */
    public void setExpectedAbstractionCompletionComments(
            St expectedAbstractionCompletionComments) {
        this.expectedAbstractionCompletionComments = expectedAbstractionCompletionComments;
    }

    /**
     * @return the pCDSentToPIODate
     */
    public Ts getPcdSentToPIODate() {
        return pcdSentToPIODate;
    }

    /**
     * @param pcdSentToPIODate the pcdSentToPIODate to set
     */
    public void setPcdSentToPIODate(Ts pcdSentToPIODate) {
        this.pcdSentToPIODate = pcdSentToPIODate;
    }

    /**
     * @return the pCDConfirmedDate
     */
    public Ts getPCDConfirmedDate() {
        return pcdConfirmedDate;
    }

    /**
     * @param pcdConfirmedDate the pcdConfirmedDate to set
     */
    public void setPcdConfirmedDate(Ts pcdConfirmedDate) {
        this.pcdConfirmedDate = pcdConfirmedDate;
    }

    /**
     * @return the desgneeNotifiedDate
     */
    public Ts getDesgneeNotifiedDate() {
        return desgneeNotifiedDate;
    }

    /**
     * @param desgneeNotifiedDate the desgneeNotifiedDate to set
     */
    public void setDesgneeNotifiedDate(Ts desgneeNotifiedDate) {
        this.desgneeNotifiedDate = desgneeNotifiedDate;
    }

    /**
     * @return the reportingInProcessDate
     */
    public Ts getReportingInProcessDate() {
        return reportingInProcessDate;
    }

    /**
     * @param reportingInProcessDate the reportingInProcessDate to set
     */
    public void setReportingInProcessDate(Ts reportingInProcessDate) {
        this.reportingInProcessDate = reportingInProcessDate;
    }

    /**
     * @return the threeMonthReminderDate
     */
    public Ts getThreeMonthReminderDate() {
        return threeMonthReminderDate;
    }

    /**
     * @param threeMonthReminderDate the threeMonthReminderDate to set
     */
    public void setThreeMonthReminderDate(Ts threeMonthReminderDate) {
        this.threeMonthReminderDate = threeMonthReminderDate;
    }

    /**
     * @return the fiveMonthReminderDate
     */
    public Ts getFiveMonthReminderDate() {
        return fiveMonthReminderDate;
    }

    /**
     * @param fiveMonthReminderDate the fiveMonthReminderDate to set
     */
    public void setFiveMonthReminderDate(Ts fiveMonthReminderDate) {
        this.fiveMonthReminderDate = fiveMonthReminderDate;
    }

    /**
     * @return the sevenMonthEscalationtoPIODate
     */
    public Ts getSevenMonthEscalationtoPIODate() {
        return sevenMonthEscalationtoPIODate;
    }

    /**
     * @param sevenMonthEscalationtoPIODate the sevenMonthEscalationtoPIODate to set
     */
    public void setSevenMonthEscalationtoPIODate(Ts sevenMonthEscalationtoPIODate) {
        this.sevenMonthEscalationtoPIODate = sevenMonthEscalationtoPIODate;
    }

    /**
     * @return the resultsSentToPIODate
     */
    public Ts getResultsSentToPIODate() {
        return resultsSentToPIODate;
    }

    /**
     * @param resultsSentToPIODate the resultsSentToPIODate to set
     */
    public void setResultsSentToPIODate(Ts resultsSentToPIODate) {
        this.resultsSentToPIODate = resultsSentToPIODate;
    }

    /**
     * @return the resultsApprovedByPIODate
     */
    public Ts getResultsApprovedByPIODate() {
        return resultsApprovedByPIODate;
    }

    /**
     * @param resultsApprovedByPIODate the resultsApprovedByPIODate to set
     */
    public void setResultsApprovedByPIODate(Ts resultsApprovedByPIODate) {
        this.resultsApprovedByPIODate = resultsApprovedByPIODate;
    }

    /**
     * @return the prsReleaseDate
     */
    public Ts getPrsReleaseDate() {
        return prsReleaseDate;
    }

    /**
     * @param prsReleaseDate the prsReleaseDate to set
     */
    public void setPrsReleaseDate(Ts prsReleaseDate) {
        this.prsReleaseDate = prsReleaseDate;
    }

    /**
     * @return the qaCommentsReturnedDate
     */
    public Ts getQaCommentsReturnedDate() {
        return qaCommentsReturnedDate;
    }

    /**
     * @param qaCommentsReturnedDate the qaCommentsReturnedDate to set
     */
    public void setQaCommentsReturnedDate(Ts qaCommentsReturnedDate) {
        this.qaCommentsReturnedDate = qaCommentsReturnedDate;
    }

    /**
     * @return the trialPublishedDate
     */
    public Ts getTrialPublishedDate() {
        return trialPublishedDate;
    }

    /**
     * @param trialPublishedDate the trialPublishedDate to set
     */
    public void setTrialPublishedDate(Ts trialPublishedDate) {
        this.trialPublishedDate = trialPublishedDate;
    }


    /**
     * 
     * @return delayedPostingIndicatorChanged delayedPostingIndicatorChanged
     */
    public Bl getDelayedPostingIndicatorChanged() {
         return delayedPostingIndicatorChanged;
    }
    /**
     *  
     * @param delayedPostingIndicatorChanged delayedPostingIndicatorChanged
     */
    public void setDelayedPostingIndicatorChanged(Bl delayedPostingIndicatorChanged) {
        this.delayedPostingIndicatorChanged = delayedPostingIndicatorChanged;
    }

    /**
     * @return useStandardLanguage
     */
    public Bl getUseStandardLanguage() {
        return useStandardLanguage;
    }

    /**
     * @param useStandardLanguage useStandardLanguage
     */
    public void setUseStandardLanguage(Bl useStandardLanguage) {
        this.useStandardLanguage = useStandardLanguage;
    }

    /**
     * @return dateEnteredInPrs
     */
    public Bl getDateEnteredInPrs() {
        return dateEnteredInPrs;
    }

    /**
     * @param dateEnteredInPrs dateEnteredInPrs
     */
    public void setDateEnteredInPrs(Bl dateEnteredInPrs) {
        this.dateEnteredInPrs = dateEnteredInPrs;
    }

    /**
     * @return designeeAccessRevoked
     */
    public Bl getDesigneeAccessRevoked() {
        return designeeAccessRevoked;
    }

    /**
     * @param designeeAccessRevoked designeeAccessRevoked
     */
    public void setDesigneeAccessRevoked(Bl designeeAccessRevoked) {
        this.designeeAccessRevoked = designeeAccessRevoked;
    }

    /**
     * @return designeeAccessRevokedDate
     */
    public Ts getDesigneeAccessRevokedDate() {
        return designeeAccessRevokedDate;
    }

    /**
     * @param designeeAccessRevokedDate designeeAccessRevokedDate
     */
    public void setDesigneeAccessRevokedDate(Ts designeeAccessRevokedDate) {
        this.designeeAccessRevokedDate = designeeAccessRevokedDate;
    }

    /**
     * @return changesInCtrpCtGov
     */
    public Bl getChangesInCtrpCtGov() {
        return changesInCtrpCtGov;
    }

    /**
     * @param changesInCtrpCtGov changesInCtrpCtGov
     */
    public void setChangesInCtrpCtGov(Bl changesInCtrpCtGov) {
        this.changesInCtrpCtGov = changesInCtrpCtGov;
    }

    /**
     * @return changesInCtrpCtGovDate
     */
    public Ts getChangesInCtrpCtGovDate() {
        return changesInCtrpCtGovDate;
    }

    /**
     * @param changesInCtrpCtGovDate changesInCtrpCtGovDate
     */
    public void setChangesInCtrpCtGovDate(Ts changesInCtrpCtGovDate) {
        this.changesInCtrpCtGovDate = changesInCtrpCtGovDate;
    }

    /**
     * @return sendToCtGovUpdated
     */
    public Bl getSendToCtGovUpdated() {
        return sendToCtGovUpdated;
    }

    /**
     * @param sendToCtGovUpdated sendToCtGovUpdated
     */
    public void setSendToCtGovUpdated(Bl sendToCtGovUpdated) {
        this.sendToCtGovUpdated = sendToCtGovUpdated;
    }

    /**
     * @return the studyProcessingErrors
     */
    public List<StudyProcessingError> getStudyProcessingErrors() {
        return studyProcessingErrors;
    }

    /**
     * @param studyProcessingErrors the studyProcessingErrors to set
     */
    public void setStudyProcessingErrors(
            List<StudyProcessingError> studyProcessingErrors) {
        this.studyProcessingErrors = studyProcessingErrors;
    }
    
    /**
     * @return the programCodes
     */
    public List<ProgramCodeDTO> getProgramCodes() {
        return programCodes;
    }

    /**
     * @param programCodesDTO the program codes dto to set
     */
    public void setProgramCodes(List<ProgramCodeDTO> programCodesDTO) {
        this.programCodes = programCodesDTO;
    }

    /**
     * Will return the program codes as semicolon separated string.
     * @return a string representing progam codes.
     */
    public String getProgramCodesAsString() {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(getProgramCodes())) {
            for (ProgramCodeDTO pgc : getProgramCodes()) {
                if (sb.length() > 0) {
                    sb.append("; ");
                }
                sb.append(pgc.getProgramCode());
            }
       }
       return sb.toString();

    }

    /**
     * Will find the matching program code
     * @param id - id of the program code
     * @return  ProgramCodeDTO
     */
    public ProgramCodeDTO findProgramCode(Long id) {
        if (CollectionUtils.isNotEmpty(programCodes)) {
            for (ProgramCodeDTO pgc : programCodes) {
                if (pgc.getId().equals(id)) {
                    return pgc;
                }
            }
        }
        return null;
    }
    
}
