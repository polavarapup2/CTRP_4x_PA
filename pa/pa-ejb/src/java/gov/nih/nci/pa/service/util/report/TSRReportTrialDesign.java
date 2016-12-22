/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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

package gov.nih.nci.pa.service.util.report;


/**
 * Trial Design.
 *
 * @author kkanchinadam
 */
@SuppressWarnings("PMD.TooManyFields")
public class TSRReportTrialDesign {
    private String type;
    private String primaryPurpose;
    private String primaryPurposeAdditonalQualifier;
    private String primaryPurposeOtherText;
    private String phase;
    private String phaseAdditonalQualifier;
    private String interventionModel;
    private String numberOfArms;
    private String masking;
    private String maskedRoles;
    private String allocation;
    private String studyClassification;
    private String targetEnrollment;
    private String studyModel;
    private String timePerspective;
    private String studyModelOtherText;
    private String timePerspectiveOtherText;
    private String secondaryPurpose;
    private String secondaryPurposeOtherText;
    private String biospecimenDescription;
    private String biospecimenRetentionCode;
    private String numberOfGroups;
    private String studySubtypeCode;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the primaryPurpose
     */
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
     * @return the primaryPurposeAdditonalQualifier
     */
    public String getPrimaryPurposeAdditonalQualifier() {
        return primaryPurposeAdditonalQualifier;
    }

    /**
     * @param primaryPurposeAdditonalQualifier the primaryPurposeAdditonalQualifier to set
     */
    public void setPrimaryPurposeAdditonalQualifier(
            String primaryPurposeAdditonalQualifier) {
        this.primaryPurposeAdditonalQualifier = primaryPurposeAdditonalQualifier;
    }

    /**
     * @return the phase
     */
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
     * @return the phaseAdditonalQualifier
     */
    public String getPhaseAdditonalQualifier() {
        return phaseAdditonalQualifier;
    }

    /**
     * @param phaseAdditonalQualifier the phaseAdditonalQualifier to set
     */
    public void setPhaseAdditonalQualifier(String phaseAdditonalQualifier) {
        this.phaseAdditonalQualifier = phaseAdditonalQualifier;
    }

    /**
     * @return the interventionModel
     */
    public String getInterventionModel() {
        return interventionModel;
    }

    /**
     * @param interventionModel the interventionModel to set
     */
    public void setInterventionModel(String interventionModel) {
        this.interventionModel = interventionModel;
    }

    /**
     * @return the numberOfArms
     */
    public String getNumberOfArms() {
        return numberOfArms;
    }

    /**
     * @param numberOfArms the numberOfArms to set
     */
    public void setNumberOfArms(String numberOfArms) {
        this.numberOfArms = numberOfArms;
    }

    /**
     * @return the masking
     */
    public String getMasking() {
        return masking;
    }

    /**
     * @param masking the masking to set
     */
    public void setMasking(String masking) {
        this.masking = masking;
    }

    /**
     * @return the maskedRoles
     */
    public String getMaskedRoles() {
        return maskedRoles;
    }

    /**
     * @param maskedRoles the maskedRoles to set
     */
    public void setMaskedRoles(String maskedRoles) {
        this.maskedRoles = maskedRoles;
    }

    /**
     * @return the allocation
     */
    public String getAllocation() {
        return allocation;
    }

    /**
     * @param allocation the allocation to set
     */
    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    /**
     * @return the studyClassification
     */
    public String getStudyClassification() {
        return studyClassification;
    }

    /**
     * @param studyClassification the studyClassification to set
     */
    public void setStudyClassification(String studyClassification) {
        this.studyClassification = studyClassification;
    }

    /**
     * @return the targetEnrollment
     */
    public String getTargetEnrollment() {
        return targetEnrollment;
    }

    /**
     * @param targetEnrollment the targetEnrollment to set
     */
    public void setTargetEnrollment(String targetEnrollment) {
        this.targetEnrollment = targetEnrollment;
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
     * @return the studyModel
     */
    public String getStudyModel() {
        return studyModel;
    }

    /**
     * @param studyModel the studyModel to set
     */
    public void setStudyModel(String studyModel) {
        this.studyModel = studyModel;
    }

    /**
     * @return the timePerspective
     */
    public String getTimePerspective() {
        return timePerspective;
    }

    /**
     * @param timePerspective the timePerspective to set
     */
    public void setTimePerspective(String timePerspective) {
        this.timePerspective = timePerspective;
    }

    /**
     * @return the studyModelOtherText
     */
    public String getStudyModelOtherText() {
        return studyModelOtherText;
    }

    /**
     * @param studyModelOtherText the studyModelOtherText to set
     */
    public void setStudyModelOtherText(String studyModelOtherText) {
        this.studyModelOtherText = studyModelOtherText;
    }

    /**
     * @return the timePerspectiveOtherText
     */
    public String getTimePerspectiveOtherText() {
        return timePerspectiveOtherText;
    }

    /**
     * @param timePerspectiveOtherText the timePerspectiveOtherText to set
     */
    public void setTimePerspectiveOtherText(String timePerspectiveOtherText) {
        this.timePerspectiveOtherText = timePerspectiveOtherText;
    }

    /**
     * @return the secondaryPurpose
     */
    public String getSecondaryPurpose() {
        return secondaryPurpose;
    }

    /**
     * @param secondaryPurpose the secondaryPurpose to set
     */
    public void setSecondaryPurpose(String secondaryPurpose) {
        this.secondaryPurpose = secondaryPurpose;
    }

    /**
     * @return the secondaryPurposeOtherText
     */
    public String getSecondaryPurposeOtherText() {
        return secondaryPurposeOtherText;
    }

    /**
     * @param secondaryPurposeOtherText the secondaryPurposeOtherText to set
     */
    public void setSecondaryPurposeOtherText(String secondaryPurposeOtherText) {
        this.secondaryPurposeOtherText = secondaryPurposeOtherText;
    }
    
    /**
     * @return biospecimenDescription
     */
    public String getBiospecimenDescription() {
        return biospecimenDescription;
    }
    /**
     * @param biospecimenDescription biospecimenDescription
     */
    public void setBiospecimenDescription(String biospecimenDescription) {
        this.biospecimenDescription = biospecimenDescription;
    }
    /**
     * @return biospecimenRetentionCode
     */
    public String getBiospecimenRetentionCode() {
        return biospecimenRetentionCode;
    }
    /**
     * @param biospecimenRetentionCode biospecimenRetentionCode
     */
    public void setBiospecimenRetentionCode(String biospecimenRetentionCode) {
        this.biospecimenRetentionCode = biospecimenRetentionCode;
    }
    /**
     * @return numberOfGroups
     */
    public String getNumberOfGroups() {
        return numberOfGroups;
    }
    /**
     * @param numberOfGroups numberOfGroups
     */
    public void setNumberOfGroups(String numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
    /**
     * @return the studySubtypeCode
     */
    public String getStudySubtypeCode() {
        return studySubtypeCode;
    }
    /**
     * @param studySubtypeCode the studySubtypeCode to set
     */
    public void setStudySubtypeCode(String studySubtypeCode) {
        this.studySubtypeCode = studySubtypeCode;
    }
}
