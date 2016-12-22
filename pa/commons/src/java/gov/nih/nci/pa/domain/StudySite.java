/**
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
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Where;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 */
@Entity
@Table(name = "STUDY_SITE")
@org.hibernate.annotations.Table(appliesTo = "STUDY_SITE", indexes = 
                                 {@Index(name = "study_site_study_protocol_idx", 
                                 columnNames = { "STUDY_PROTOCOL_IDENTIFIER" }),
                                 @Index(name = "study_site_functional_code_idx", 
                                 columnNames = { "FUNCTIONAL_CODE" }) })
@SuppressWarnings("PMD.TooManyFields")
public class StudySite extends OrganizationFunctionalRole {
    private static final long serialVersionUID = 1234567890L;
    private static final String MAPPED_BY_SS = "studySite";

    private String localStudyProtocolIdentifier;
    private String reviewBoardApprovalNumber;
    private ReviewBoardApprovalStatusCode reviewBoardApprovalStatusCode;
    private Integer targetAccrualNumber;
    private Timestamp reviewBoardApprovalDate;
    private HealthCareFacility healthCareFacility;
    private ResearchOrganization researchOrganization;
    private OversightCommittee oversightCommittee;
    private List<StudySiteAccrualStatus> studySiteAccrualStatuses = new ArrayList<StudySiteAccrualStatus>();
    private List<StudySiteAccrualAccess> studySiteAccrualAccess;
    private List<StudySiteContact> studySiteContacts = new ArrayList<StudySiteContact>();
    private List<StudySubject> studySubjects;
    private String reviewBoardOrganizationalAffiliation;
    private String programCodeText;
    private Timestamp accrualDateRangeHigh;
    private Timestamp accrualDateRangeLow;
    private SortedSet<StudySiteSubjectAccrualCount> accrualCounts;
    private Set<RegistryUser> studySiteOwners = new HashSet<RegistryUser>();

    /**
     * @return the programCode
     */
    @Column(name = "PROGRAM_CODE_TEXT")
    @Searchable
    public String getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCode the programCode to set
     */
    public void setProgramCodeText(String programCode) {
        programCodeText = programCode;
    }

    /**
     *
     * @return localStudyProtocolIdentifier
     */
    @Column(name = "LOCAL_SP_INDENTIFIER")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = "study_site_upper_local_sp_indentifier_idx")
    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }

    /**
     *
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }

    /**
     * @return the reviewBoardApprovalNumber
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_NUMBER")
    @Searchable
    public String getReviewBoardApprovalNumber() {
        return reviewBoardApprovalNumber;
    }

    /**
     * @param reviewBoardApprovalNumber the reviewBoardApprovalNumber to set
     */
    public void setReviewBoardApprovalNumber(String reviewBoardApprovalNumber) {
        this.reviewBoardApprovalNumber = reviewBoardApprovalNumber;
    }

    /**
     * @return the reviewBoardApprovalDate
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_DATE")
    @Searchable
    public Timestamp getReviewBoardApprovalDate() {
        return reviewBoardApprovalDate;
    }

    /**
     * @param reviewBoardApprovalDate the reviewBoardApprovalDate to set
     */
    public void setReviewBoardApprovalDate(Timestamp reviewBoardApprovalDate) {
        this.reviewBoardApprovalDate = reviewBoardApprovalDate;
    }

    /**
     * @return the reviewBoardApprovalStatusCode
     */
    @Column(name = "REVIEW_BOARD_APPROVAL_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @Searchable
    public ReviewBoardApprovalStatusCode getReviewBoardApprovalStatusCode() {
        return reviewBoardApprovalStatusCode;
    }

    /**
     * @param reviewBoardApprovalStatusCode the reviewBoardApprovalStatusCode to set
     */
    public void setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode reviewBoardApprovalStatusCode) {
        this.reviewBoardApprovalStatusCode = reviewBoardApprovalStatusCode;
    }

    /**
     * @return the targetAccrualNumber
     */
    @Column(name = "TARGET_ACCRUAL_NUMBER")
    @Searchable
    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    /**
     *
     * @return healthCareFacility
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "HEALTHCARE_FACILITY_IDENTIFIER", nullable = true)
    @Searchable(nested = true)
    public HealthCareFacility getHealthCareFacility() {
        return healthCareFacility;
    }

    /**
     *
     * @param healthCareFacility healthCareFacility
     */
    public void setHealthCareFacility(HealthCareFacility healthCareFacility) {
        this.healthCareFacility = healthCareFacility;
    }

    /**
     *
     * @return researchOrganization
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "RESEARCH_ORGANIZATION_IDENTIFIER", nullable = true)
    @Searchable(nested = true)
    @Index(name = "research_org_idx")
    public ResearchOrganization getResearchOrganization() {
        return researchOrganization;
    }

    /**
     *
     * @param researchOrganization ResearchOrganization
     */
    public void setResearchOrganization(ResearchOrganization researchOrganization) {
        this.researchOrganization = researchOrganization;
    }

    /**
     * @return the oversightCommittee
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "OVERSIGHT_COMMITTEE_IDENTIFIER", nullable = true)
    @Searchable(nested = true)
    public OversightCommittee getOversightCommittee() {
        return oversightCommittee;
    }

    /**
     * @param oversightCommittee the oversightCommittee to set
     */
    public void setOversightCommittee(OversightCommittee oversightCommittee) {
        this.oversightCommittee = oversightCommittee;
    }

    /**
     * @return the studySiteAccrualStatuses
     */
    @OneToMany(mappedBy = MAPPED_BY_SS)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Where(clause = "deleted='false'")
    public List<StudySiteAccrualStatus> getStudySiteAccrualStatuses() {
        return studySiteAccrualStatuses;
    }

    /**
     * @param studySiteAccrualStatuses the studySiteAccrualStatuses to set
     */
    public void setStudySiteAccrualStatuses(List<StudySiteAccrualStatus> studySiteAccrualStatuses) {
        this.studySiteAccrualStatuses = studySiteAccrualStatuses;
    }

    /**
     * @return the studySiteAccrualAccess
     */
    @OneToMany(mappedBy = MAPPED_BY_SS)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudySiteAccrualAccess> getStudySiteAccrualAccess() {
        return studySiteAccrualAccess;
    }

    /**
     * @param studySiteAccrualAccess the studySiteAccrualAccess to set
     */
    public void setStudySiteAccrualAccess(List<StudySiteAccrualAccess> studySiteAccrualAccess) {
        this.studySiteAccrualAccess = studySiteAccrualAccess;
    }

    /**
     * @return the studySubjects
     */
    @OneToMany(mappedBy = MAPPED_BY_SS)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }

    /**
     * @param studySubjects the studySubjects to set
     */
    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }

    /**
     * @return the studySiteContacts
     */
    @OneToMany(mappedBy = MAPPED_BY_SS)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    public List<StudySiteContact> getStudySiteContacts() {
        return studySiteContacts;
    }

    /**
     * @param studySiteContacts the studySiteContacts to set
     */
    public void setStudySiteContacts(List<StudySiteContact> studySiteContacts) {
        this.studySiteContacts = studySiteContacts;
    }

    /**
     * @return the reviewBoardOrganizationalAffiliation
     */
    @Column(name = "REVIEW_BOARD_ORGANIZATIONAL_AFFILIATION")
    @Searchable
    public String getReviewBoardOrganizationalAffiliation() {
        return reviewBoardOrganizationalAffiliation;
    }

    /**
     * @param reviewBoardOrganizationalAffiliation the reviewBoardOrganizationalAffiliation to set
     */
    public void setReviewBoardOrganizationalAffiliation(String reviewBoardOrganizationalAffiliation) {
        this.reviewBoardOrganizationalAffiliation = reviewBoardOrganizationalAffiliation;
    }

    /**
     * @return the accrualDateRangeHigh closed date
     */
    @Column(name = "ACCRUAL_DATE_RANGE_HIGH")
    @Searchable
    public Timestamp getAccrualDateRangeHigh() {
        return accrualDateRangeHigh;
    }

    /**
     * @param accrualDateRangeHigh the accrualDateRangeHigh to set
     */
    public void setAccrualDateRangeHigh(Timestamp accrualDateRangeHigh) {
        this.accrualDateRangeHigh = accrualDateRangeHigh;
    }

    /**
     * @return the accrualDateRangeLow opened date
     */
    @Column(name = "ACCRUAL_DATE_RANGE_LOW")
    @Searchable
    public Timestamp getAccrualDateRangeLow() {
        return accrualDateRangeLow;
    }

    /**
     * @param accrualDateRangeLow the accrualDateRangeLow to set
     */
    public void setAccrualDateRangeLow(Timestamp accrualDateRangeLow) {
        this.accrualDateRangeLow = accrualDateRangeLow;
    }

    /**
     * @return the accrualCounts
     */
    @OneToMany(mappedBy = MAPPED_BY_SS)    
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Sort(type = SortType.COMPARATOR, comparator = LastDateUpdateComparator.class)
    public SortedSet<StudySiteSubjectAccrualCount> getAccrualCounts() {
        return accrualCounts;
    }

    /**
     * @param accrualCounts the accrualCounts to set
     */
    public void setAccrualCounts(SortedSet<StudySiteSubjectAccrualCount> accrualCounts) {
        this.accrualCounts = accrualCounts;
    }
    
    /**
     * @return the latest accrualCount
     */
    @Transient
    public StudySiteSubjectAccrualCount getAccrualCount() {        
        if (CollectionUtils.isNotEmpty(accrualCounts)) {          
                return accrualCounts.first();           
        }
        return null;
    }
    
    /**
     * @return the studySiteOwners
     */
     @ManyToMany(mappedBy = "studySites")
     public Set<RegistryUser> getStudySiteOwners() {
        return studySiteOwners;
    }

    /**
     * @param studySiteOwners the studySiteOwners to set
     */
    public void setStudySiteOwners(Set<RegistryUser> studySiteOwners) {
        this.studySiteOwners = studySiteOwners;
    }
    
}
