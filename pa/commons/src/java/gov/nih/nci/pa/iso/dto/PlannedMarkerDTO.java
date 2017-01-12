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

import java.util.List;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
/**
 * DTO for representing a study's planned markers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings("PMD.TooManyFields")
public class PlannedMarkerDTO extends PlannedActivityDTO {
    private static final long serialVersionUID = 1L;

    private St name;
    private St longName;
    private Cd hugoBiomarkerCode;
    private Cd assayTypeCode;
    private St assayTypeOtherText;
    private Cd assayUseCode;
    private Cd assayPurposeCode;
    private St assayPurposeOtherText;
    private Cd tissueSpecimenTypeCode;
    private St specimenTypeOtherText;
    private Cd tissueCollectionMethodCode;
    private Cd statusCode;
    private Cd evaluationType;
    private St evaluationTypeOtherText;
    private List<String> slectedAssayType;
    private List<String> selectedAssayPurpose;
    private List<String> selectedTissueSpecType;
    private List<String> selectedEvaluationType; 
    private Ii permissibleValue;
    private Ii cadsrId;
    private Ts dateLastCreated;
    private Ts dateEmailSent;
    private List<String> synonymNames;
    private St pvValue;
    /**
     * @return the name
     */
    public St getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
    }


    /**
     * @return the longName
     */
    public St getLongName() {
        return longName;
    }

    /**
     * @param longName the longName to set
     */
    public void setLongName(St longName) {
        this.longName = longName;
    }

    /**
     * @return the hugoBiomarkerCode
     */
    public Cd getHugoBiomarkerCode() {
        return hugoBiomarkerCode;
    }

    /**
     * @param hugoBiomarkerCode the hugoBiomarkerCode to set
     */
    public void setHugoBiomarkerCode(Cd hugoBiomarkerCode) {
        this.hugoBiomarkerCode = hugoBiomarkerCode;
    }

    /**
     * @return the assayTypeCode
     */
    public Cd getAssayTypeCode() {
        return assayTypeCode;
    }

    /**
     * @param assayTypeCode the assayTypeCode to set
     */
    public void setAssayTypeCode(Cd assayTypeCode) {
        this.assayTypeCode = assayTypeCode;
    }

    /**
     * @return the assayTypeOtherText
     */
    public St getAssayTypeOtherText() {
        return assayTypeOtherText;
    }

    /**
     * @param assayTypeOtherText the assayTypeOtherText to set
     */
    public void setAssayTypeOtherText(St assayTypeOtherText) {
        this.assayTypeOtherText = assayTypeOtherText;
    }

    /**
     * @return the assayUseCode
     */
    public Cd getAssayUseCode() {
        return assayUseCode;
    }

    /**
     * @param assayUseCode the assayUseCode to set
     */
    public void setAssayUseCode(Cd assayUseCode) {
        this.assayUseCode = assayUseCode;
    }

    /**
     * @return the assayPurposeCode
     */
    public Cd getAssayPurposeCode() {
        return assayPurposeCode;
    }

    /**
     * @param assayPurposeCode the assayPurposeCode to set
     */
    public void setAssayPurposeCode(Cd assayPurposeCode) {
        this.assayPurposeCode = assayPurposeCode;
    }

    /**
     * @return the assayPurposeOtherText
     */
    public St getAssayPurposeOtherText() {
        return assayPurposeOtherText;
    }

    /**
     * @param assayPurposeOtherText the assayPurposeOtherText to set
     */
    public void setAssayPurposeOtherText(St assayPurposeOtherText) {
        this.assayPurposeOtherText = assayPurposeOtherText;
    }

    /**
     * @return the tissueSpecimenTypeCode
     */
    public Cd getTissueSpecimenTypeCode() {
        return tissueSpecimenTypeCode;
    }

    /**
     * @param tissueSpecimenTypeCode the tissueSpecimenTypeCode to set
     */
    public void setTissueSpecimenTypeCode(Cd tissueSpecimenTypeCode) {
        this.tissueSpecimenTypeCode = tissueSpecimenTypeCode;
    }

    /**
     * @return the tissueCollectionMethodCode
     */
    public Cd getTissueCollectionMethodCode() {
        return tissueCollectionMethodCode;
    }

    /**
     * @param tissueCollectionMethodCode the tissueCollectionMethodCode to set
     */
    public void setTissueCollectionMethodCode(Cd tissueCollectionMethodCode) {
        this.tissueCollectionMethodCode = tissueCollectionMethodCode;
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
     * @return the slectedAssayType
     */
    public List<String> getSlectedAssayType() {
        return slectedAssayType;
    }
    /**
     * @param slectedAssayType the slectedAssayType to set
     */
    public void setSlectedAssayType(List<String> slectedAssayType) {
        this.slectedAssayType = slectedAssayType;
    } 
    /**
     * @return the selectedAssayPurpose
     */
    public List<String> getSelectedAssayPurpose() {
        return selectedAssayPurpose;
    }
    /**
     * @param selectedAssayPurpose the selectedAssayPurpose to set
     */
    public void setSelectedAssayPurpose(List<String> selectedAssayPurpose) {
        this.selectedAssayPurpose = selectedAssayPurpose;
    } 
    /**
     * @return the selectedTissueSpecType
     */
    public List<String> getSelectedTissueSpecType() {
        return selectedTissueSpecType;
    }
    /**
     * @param selectedTissueSpecType the selectedTissueSpecType to set
     */
    public void setSelectedTissueSpecType(List<String> selectedTissueSpecType) {
        this.selectedTissueSpecType = selectedTissueSpecType;
    }
    /**
     * @return the evaluationType
     */
    public Cd getEvaluationType() {
        return evaluationType;
    }
    /**
     * @param evaluationType the evaluationType to set
     */
    public void setEvaluationType(Cd evaluationType) {
        this.evaluationType = evaluationType;
    }
    /**
     * 
     * @return selectedEvaluationType
     */
    public List<String> getSelectedEvaluationType() {
        return selectedEvaluationType;
    }
    /**
     * 
     * @param selectedEvaluationType selectedEvaluationType
     */
    public void setSelectedEvaluationType(List<String> selectedEvaluationType) {
        this.selectedEvaluationType = selectedEvaluationType;
    }
    /**
     * 
     * @return evaluationTypeOtherText
     */
    public St getEvaluationTypeOtherText() {
        return evaluationTypeOtherText;
    }
    /**
     * 
     * @param evaluationTypeOtherText evaluationTypeOtherText
     */
    public void setEvaluationTypeOtherText(St evaluationTypeOtherText) {
        this.evaluationTypeOtherText = evaluationTypeOtherText;
    }
    /**
     * 
     * @return specimenTypeOtherText
     */
    public St getSpecimenTypeOtherText() {
        return specimenTypeOtherText;
    }
    /**
     * 
     * @param specimenTypeOtherText specimenTypeOtherText
     */
    public void setSpecimenTypeOtherText(St specimenTypeOtherText) {
        this.specimenTypeOtherText = specimenTypeOtherText;
    }
    /**
     * @return the permissibleValue
     */
    public Ii getPermissibleValue() {
        return permissibleValue;
    }
    /**
     * 
     * @param permissibleValue permissibleValue
     */
    public void setPermissibleValue(Ii permissibleValue) {
        this.permissibleValue = permissibleValue;
    }
    /**
     * @return the cadsrId
     */
    public Ii getCadsrId() {
        return cadsrId;
    }
    
    /**
     * 
     * @param cadsrId cadsrId
     */
    public void setCadsrId(Ii cadsrId) {
        this.cadsrId = cadsrId;
    }
    
    /**
     * 
     * @return dateLastCreated dateLastCreated
     */
    public Ts getDateLastCreated() {
        return dateLastCreated;
    }
    /**
     * 
     * @param dateLastCreated dateLastCreated
     */
    public void setDateLastCreated(Ts dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }
    /**
     * 
     * @return dateEmailSent dateEmailSent
     */
    public Ts getDateEmailSent() {
        return dateEmailSent;
    }
   /**
    *  
    * @param dateEmailSent dateEmailSent
    */
    public void setDateEmailSent(Ts dateEmailSent) {
        this.dateEmailSent = dateEmailSent;
    }
    /**
     * 
     * @return synonymNames synonymNames
     */
    public List<String> getSynonymNames() {
        return synonymNames;
    }
    /**
     * 
     * @param synonymNames synonymNames
     */
    public void setSynonymNames(List<String> synonymNames) {
        this.synonymNames = synonymNames;
    }
    /**
     * 
     * @return pvValue pvValue
     */
    public St getPvValue() {
        return pvValue;
    }
    /**
     * 
     * @param pvValue pvValue
     */
    public void setPvValue(St pvValue) {
        this.pvValue = pvValue;
    }
}
