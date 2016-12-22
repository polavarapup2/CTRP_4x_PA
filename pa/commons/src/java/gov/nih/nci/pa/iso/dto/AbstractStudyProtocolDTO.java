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

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;

/**
 * DTO for transferring Abstract Study Protocol objects.
 * @author moweis
 *
 */
@SuppressWarnings("PMD.TooManyFields")
public abstract class AbstractStudyProtocolDTO extends BaseDTO {

    private static final long serialVersionUID = 237182631792206585L;
    private Bl ctgovXmlRequiredIndicator;
    private Bl dataMonitoringCommitteeAppointedIndicator;
    private Bl delayedpostingIndicator;
    private Bl fdaRegulatedIndicator;
    private Bl proprietaryTrialIndicator;
    private Bl section801Indicator;
    private Cd phaseCode;
    private Cd phaseAdditionalQualifierCode;
    private St officialTitle;
    private Cd primaryPurposeCode;
    private Cd primaryPurposeAdditionalQualifierCode;
    private St primaryPurposeOtherText;   
    private St programCodeText;
    private St studyProtocolType;
    private St userLastCreated;

    private Ts startDate;
    private Ts dateLastCreated;
    private Ts primaryCompletionDate;
    private Ts completionDate;
    private Cd startDateTypeCode;
    private Cd primaryCompletionDateTypeCode;
    private Cd completionDateTypeCode;
    private Cd consortiaTrialCategoryCode;
    private St accrualDiseaseCodeSystem;

    /**
     * @return the officialTitle
     */
    public St getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(St officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * @return the phaseCode
     */
    public Cd getPhaseCode() {
        return phaseCode;
    }

    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(Cd phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * @return the phaseAdditionalQualifierCode
     */
    public Cd getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }

    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(Cd phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }

    /**
     * @return the primaryPurposeCode
     */
    public Cd getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }

    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(Cd primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }

    /**
     * @return the primaryPurposeAdditionalQualifierCode
     */
    public Cd getPrimaryPurposeAdditionalQualifierCode() {
        return primaryPurposeAdditionalQualifierCode;
    }

    /**
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode to set
     */
    public void setPrimaryPurposeAdditionalQualifierCode(Cd primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }

    /**
     * @return the programCodeText
     */
    public St getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(St programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * @return the studyProtocolType
     */
    public St getStudyProtocolType() {
        return studyProtocolType;
    }

    /**
     * @param studyProtocolType the studyProtocolType to set
     */
    public void setStudyProtocolType(St studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }

    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    public Bl getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(Bl dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @return the fdaRegulatedIndicator
     */
    public Bl getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }

    /**
     * @param fdaRegulatedIndicator the fdaRegulatedIndicator to set
     */
    public void setFdaRegulatedIndicator(Bl fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }

    /**
     * @return the section801Indicator
     */
    public Bl getSection801Indicator() {
        return section801Indicator;
    }

    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(Bl section801Indicator) {
        this.section801Indicator = section801Indicator;
    }

    /**
     * @return the delayedpostingIndicator
     */
    public Bl getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }

    /**
     * @param delayedpostingIndicator the delayedpostingIndicator to set
     */
    public void setDelayedpostingIndicator(Bl delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }

    /**
     * @return the userLastCreated
     */
    public St getUserLastCreated() {
        return userLastCreated;
    }

    /**
     * @param userLastCreated the userLastCreated to set
     */
    public void setUserLastCreated(St userLastCreated) {
        this.userLastCreated = userLastCreated;
    }

    /**
     * @return the proprietaryTrialIndicator
     */
    public Bl getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }

    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(Bl proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }

    /**
     * @return the ctgovXmlRequiredIndicator
     */
    public Bl getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }

    /**
     * @param ctgovXmlRequiredIndicator the ctgovXmlRequiredIndicator to set
     */
    public void setCtgovXmlRequiredIndicator(Bl ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }

    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(St primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }

    /**
     * @return the primaryPurposeOtherText
     */
    public St getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }

    /**
     * @return the startDate
     */
    public Ts getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Ts startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the primaryCompletionDate
     */
    public Ts getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(Ts primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * @return the completionDate
     */
    public Ts getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(Ts completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the startDateTypeCode
     */
    public Cd getStartDateTypeCode() {
        return startDateTypeCode;
    }

    /**
     * @param startDateTypeCode the startDateTypeCode to set
     */
    public void setStartDateTypeCode(Cd startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }

    /**
     * @return the primaryCompletionDateTypeCode
     */
    public Cd getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }

    /**
     * @param primaryCompletionDateTypeCode the primaryCompletionDateTypeCode to set
     */
    public void setPrimaryCompletionDateTypeCode(Cd primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }

    /**
     * @return the completionDateTypeCode
     */
    public Cd getCompletionDateTypeCode() {
        return completionDateTypeCode;
    }

    /**
     * @param completionDateTypeCode the completionDateTypeCode to set
     */
    public void setCompletionDateTypeCode(Cd completionDateTypeCode) {
        this.completionDateTypeCode = completionDateTypeCode;
    }

    /**
     * @return the dateLastCreated
     */
    public Ts getDateLastCreated() {
        return dateLastCreated;
    }

    /**
     * @param dateLastCreated the dateLastCreated to set
     */
    public void setDateLastCreated(Ts dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }

    /**
     * @return the consortiaTrialCategoryCode
     */
    public Cd getConsortiaTrialCategoryCode() {
        return consortiaTrialCategoryCode;
    }

    /**
     * @param consortiaTrialCategoryCode the consortiaTrialCategoryCode to set
     */
    public void setConsortiaTrialCategoryCode(Cd consortiaTrialCategoryCode) {
        this.consortiaTrialCategoryCode = consortiaTrialCategoryCode;
    }

    /**
     * @return the accrual disease code system (e.g. SDC)
     */
    public St getAccrualDiseaseCodeSystem() {
        return accrualDiseaseCodeSystem;
    }

    /**
     * @param accrualDiseaseCodeSystem the system used for accrual disease
     */
    public void setAccrualDiseaseCodeSystem(St accrualDiseaseCodeSystem) {
        this.accrualDiseaseCodeSystem = accrualDiseaseCodeSystem;
    }
}
