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
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

import java.util.List;

/**
 *  DTO for tranferring Study Sites to Participating Sites.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class ParticipatingSiteDTO extends StudyDTO {
    private static final long serialVersionUID = 1L;

    private DSet<Ii> organizationRoleIdentifiers;
    private St localStudyProtocolIdentifier;
    private StudySiteAccrualStatusDTO studySiteAccrualStatus;
    private List<StudySiteContactDTO> studySiteContacts;

    private Cd reviewBoardApprovalStatusCode;
    private St reviewBoardOrganizationalAffiliation;
    private St reviewBoardApprovalNumber;
    private Ts reviewBoardApprovalDate;

    private Int targetAccrualNumber;
    private Ivl<Ts> accrualDateRange;
    private St programCodeText;
    private String siteOrgPoId;
    private String siteOrgName;
    
    private St createdUser;
    private Ts createdDt;
    private St lastUpdatedUser;
    private Ts lastUpdatedDt;

    /**
     * @return the organization role identifiers
     */
    public DSet<Ii> getOrganizationRoleIdentifiers() {
        return organizationRoleIdentifiers;
    }

    /**
     * @param organizationRoleIdentifiers the organization role identifiers to set
     */
    public void setOrganizationRoleIdentifiers(DSet<Ii> organizationRoleIdentifiers) {
        this.organizationRoleIdentifiers = organizationRoleIdentifiers;
    }

    /**
     * @return the local study protocol identifier
     */
    public St getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }

    /**
     * @param localStudyProtocolIdentifier the local study protocol identifier to set
     */
    public void setLocalStudyProtocolIdentifier(St localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }

    /**
     * @return the study site accrual status
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus() {
        return studySiteAccrualStatus;
    }

    /**
     * @param studySiteAccrualStatus the study site accrual access to set
     */
    public void setStudySiteAccrualStatus(StudySiteAccrualStatusDTO studySiteAccrualStatus) {
        this.studySiteAccrualStatus = studySiteAccrualStatus;
    }

    /**
     * @return the study site contacts
     */
    public List<StudySiteContactDTO> getStudySiteContacts() {
        return studySiteContacts;
    }

    /**
     * @param studySiteContacts the study site contacts to set
     */
    public void setStudySiteContacts(List<StudySiteContactDTO> studySiteContacts) {
        this.studySiteContacts = studySiteContacts;
    }

    /**
     * @return the review board approval status code
     */
    public Cd getReviewBoardApprovalStatusCode() {
        return reviewBoardApprovalStatusCode;
    }

    /**
     * @param reviewBoardApprovalStatusCode the review board approval status code to set
     */
    public void setReviewBoardApprovalStatusCode(Cd reviewBoardApprovalStatusCode) {
        this.reviewBoardApprovalStatusCode = reviewBoardApprovalStatusCode;
    }

    /**
     * @return the review board organizational affiliation
     */
    public St getReviewBoardOrganizationalAffiliation() {
        return reviewBoardOrganizationalAffiliation;
    }

    /**
     * @param reviewBoardOrganizationalAffiliation the review board organizational affiliation to set
     */
    public void setReviewBoardOrganizationalAffiliation(St reviewBoardOrganizationalAffiliation) {
        this.reviewBoardOrganizationalAffiliation = reviewBoardOrganizationalAffiliation;
    }

    /**
     * @return the review board approval number
     */
    public St getReviewBoardApprovalNumber() {
        return reviewBoardApprovalNumber;
    }

    /**
     * @param reviewBoardApprovalNumber the review board approval number to set
     */
    public void setReviewBoardApprovalNumber(St reviewBoardApprovalNumber) {
        this.reviewBoardApprovalNumber = reviewBoardApprovalNumber;
    }

    /**
     * @return the review board approval date
     */
    public Ts getReviewBoardApprovalDate() {
        return reviewBoardApprovalDate;
    }

    /**
     * @param reviewBoardApprovalDate the review board approval date to set
     */
    public void setReviewBoardApprovalDate(Ts reviewBoardApprovalDate) {
        this.reviewBoardApprovalDate = reviewBoardApprovalDate;
    }

    /**
     * @return the target accrual number
     */
    public Int getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the target accrual number to set
     */
    public void setTargetAccrualNumber(Int targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    /**
     * @return the accrual date range
     */
    public Ivl<Ts> getAccrualDateRange() {
        return accrualDateRange;
    }

    /**
     * @param accrualDateRange the accrual date range to set
     */
    public void setAccrualDateRange(Ivl<Ts> accrualDateRange) {
        this.accrualDateRange = accrualDateRange;
    }

    /**
     * @return the program code text
     */
    public St getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the program code text to set
     */
    public void setProgramCodeText(St programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * @return the siteOrgPoId
     */
    public String getSiteOrgPoId() {
        return siteOrgPoId;
    }

    /**
     * @param siteOrgPoId the siteOrgPoId to set
     */
    public void setSiteOrgPoId(String siteOrgPoId) {
        this.siteOrgPoId = siteOrgPoId;
    }

    /**
     * @return the createdUser
     */
    public St getCreatedUser() {
        return createdUser;
    }

    /**
     * @param createdUser the createdUser to set
     */
    public void setCreatedUser(St createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * @return the createdDt
     */
    public Ts getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt the createdDt to set
     */
    public void setCreatedDt(Ts createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return the lastUpdatedUser
     */
    public St getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    /**
     * @param lastUpdatedUser the lastUpdatedUser to set
     */
    public void setLastUpdatedUser(St lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    /**
     * @return the lastUpdatedDt
     */
    public Ts getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    /**
     * @param lastUpdatedDt the lastUpdatedDt to set
     */
    public void setLastUpdatedDt(Ts lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
    }

    /**
     * @return the siteOrgName
     */
    public String getSiteOrgName() {
        return siteOrgName;
    }

    /**
     * @param siteOrgName the siteOrgName to set
     */
    public void setSiteOrgName(String siteOrgName) {
        this.siteOrgName = siteOrgName;
    }

}
