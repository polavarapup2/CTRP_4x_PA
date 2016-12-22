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
package gov.nih.nci.pa.dto;

import gov.nih.nci.iso21090.Ii;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
/**
 * Web DTO for planned markers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class PlannedMarkerWebDTO implements Serializable {
    private static final long serialVersionUID = -6084195422625807929L;
    private Long id;
    private String name;
    private String meaning;
    private String description;
    private String assayType;
    private String assayTypeOtherText;
    private String assayUse;
    private String assayPurpose;
    private String assayPurposeOtherText;
    private String tissueSpecimenType;
    private String specimenTypeOtherText;
    private String tissueCollectionMethod;
    private String status;
    private String trialStatus;
    private String hugoCode;
    private boolean foundInHugo = false;
    private String fromEmail;
    private String message;
    private String evaluationType;
    private String evaluationTypeOtherText;
    private String nciIdentifier;
    private String csmUserEmailId;
    private String question;
    private List<String> selectedAssayType;
    private List<String> selectedAssayPurpose;
    private List<String> selectedTissueSpecType;
    private List<String> selectedEvaluationType;
    private String protocolDocument;
    private String protocolDocumentID;
    private Ii permissibleValue;
    private Long cadsrId;
    private Date creationDate;
    private Date dateEmailSent;
    private String synonymNames;
    private String pvValue;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the meaning
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * @param meaning the meaning to set
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the assayType
     */
    public String getAssayType() {
        return assayType;
    }

    /**
     * @param assayType the assayType to set
     */
    public void setAssayType(String assayType) {
        this.assayType = assayType;
    }

    /**
     * @return the assayTypeOtherText
     */
    public String getAssayTypeOtherText() {
        return assayTypeOtherText;
    }

    /**
     * @param assayTypeOtherText the assayTypeOtherText to set
     */
    public void setAssayTypeOtherText(String assayTypeOtherText) {
        this.assayTypeOtherText = assayTypeOtherText;
    }

    /**
     * @return the assayUse
     */
    public String getAssayUse() {
        return assayUse;
    }

    /**
     * @param assayUse the assayUse to set
     */
    public void setAssayUse(String assayUse) {
        this.assayUse = assayUse;
    }

    /**
     * @return the assayPurpose
     */
    public String getAssayPurpose() {
        return assayPurpose;
    }

    /**
     * @param assayPurpose the assayPurpose to set
     */
    public void setAssayPurpose(String assayPurpose) {
        this.assayPurpose = assayPurpose;
    }

    /**
     * @return the assayPurposeOtherText
     */
    public String getAssayPurposeOtherText() {
        return assayPurposeOtherText;
    }

    /**
     * @param assayPurposeOtherText the assayPurposeOtherText to set
     */
    public void setAssayPurposeOtherText(String assayPurposeOtherText) {
        this.assayPurposeOtherText = assayPurposeOtherText;
    }

    /**
     * @return the tissueSpecimenType
     */
    public String getTissueSpecimenType() {
        return tissueSpecimenType;
    }

    /**
     * @param tissueSpecimenType the tissueSpecimenType to set
     */
    public void setTissueSpecimenType(String tissueSpecimenType) {
        this.tissueSpecimenType = tissueSpecimenType;
    }

    /**
     * @return the tissueCollectionMethod
     */
    public String getTissueCollectionMethod() {
        return tissueCollectionMethod;
    }

    /**
     * @param tissueCollectionMethod the tissueCollectionMethod to set
     */
    public void setTissueCollectionMethod(String tissueCollectionMethod) {
        this.tissueCollectionMethod = tissueCollectionMethod;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the trialStatus
     */
    public String getTrialStatus() {
        return trialStatus;
    }

    /**
     * @param trialStatus the trialStatus to set
     */
    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

    /**
     * @return the hugoCode
     */
    public String getHugoCode() {
        return hugoCode;
    }

    /**
     * @param hugoCode the hugoCode to set
     */
    public void setHugoCode(String hugoCode) {
        this.hugoCode = hugoCode;
    }

    /**
     * @return the foundInHugo
     */
    public boolean isFoundInHugo() {
        return foundInHugo;
    }

    /**
     * @param foundInHugo the foundInHugo to set
     */
    public void setFoundInHugo(boolean foundInHugo) {
        this.foundInHugo = foundInHugo;
    }

    /**
     * @return the fromEmail
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * @param fromEmail the fromEmail to set
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return nciIdentifier nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * 
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * 
     * @return csmUserEmailId 
     */
    public String getCsmUserEmailId() {
        return csmUserEmailId;
    }

    /**
     * 
     * @param csmUserEmailId csmUserEmailId
     */
    public void setCsmUserEmailId(String csmUserEmailId) {
        this.csmUserEmailId = csmUserEmailId;
    }

    /**
     * 
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 
     * @param question question
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * 
     * @return selectedAssayType
     */
    public List<String> getSelectedAssayType() {
        return selectedAssayType;
    }
    /**
     * 
     * @param selectedAssayType selectedAssayType
     */
    public void setSelectedAssayType(List<String> selectedAssayType) {
        this.selectedAssayType = selectedAssayType;
    }
    /**
     * 
     * @return selectedAssayPurpose
     */
    public List<String> getSelectedAssayPurpose() {
        return selectedAssayPurpose;
    }
    /**
     * 
     * @param selectedAssayPurpose selectedAssayPurpose
     */
    public void setSelectedAssayPurpose(List<String> selectedAssayPurpose) {
        this.selectedAssayPurpose = selectedAssayPurpose;
    }
    /**
     * 
     * @return selectedTissueSpecType
     */
    public List<String> getSelectedTissueSpecType() {
        return selectedTissueSpecType;
    }
    /**
     * 
     * @param selectedTissueSpecType selectedTissueSpecType
     */
    public void setSelectedTissueSpecType(List<String> selectedTissueSpecType) {
        this.selectedTissueSpecType = selectedTissueSpecType;
    }
    /**
     * 
     * @return evaluationType
     */
    public String getEvaluationType() {
        return evaluationType;
    }
    /**
     * 
     * @param evaluationType evaluationType
     */
    public void setEvaluationType(String evaluationType) {
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
    public String getEvaluationTypeOtherText() {
        return evaluationTypeOtherText;
    }
    /**
     * 
     * @param evaluationTypeOtherText evaluationTypeOtherText
     */
    public void setEvaluationTypeOtherText(String evaluationTypeOtherText) {
        this.evaluationTypeOtherText = evaluationTypeOtherText;
    }
    /**
     * 
     * @return specimenTypeOtherText
     */
    public String getSpecimenTypeOtherText() {
        return specimenTypeOtherText;
    }
    /**
     * 
     * @param specimenTypeOtherText specimenTypeOtherText
     */
    public void setSpecimenTypeOtherText(String specimenTypeOtherText) {
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
    public Long getCadsrId() {
        return cadsrId;
    }
    
    /**
     * 
     * @param cadsrId cadsrId
     */
    public void setCadsrId(Long cadsrId) {
        this.cadsrId = cadsrId;
    }
    /**
     * 
     * @return protocolDocument protocolDocument
     */
    public String getProtocolDocument() {
        return protocolDocument;
    }
    /**
     * 
     * @param protocolDocument protocolDocument
     */
    public void setProtocolDocument(String protocolDocument) {
        this.protocolDocument = protocolDocument;
        }
    /**
     * 
     * @return protocolDocumentID protocolDocumentID
     */
    public String getProtocolDocumentID() {
        return protocolDocumentID;
        }
    /**
     * 
     * @param protocolDocumentID protocolDocumentID
     */
    public void setProtocolDocumentID(String protocolDocumentID) {
        this.protocolDocumentID = protocolDocumentID;
    }
    
    /**
     * 
     * @return creationDate creationDate
     */ 
    public Date getCreationDate() {
        return creationDate;
    }
    /**
     * 
     * @param creationDate creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * 
     * @return dateEmailSent dateEmailSent
     */
    public Date getDateEmailSent() {
        return dateEmailSent;
    }
    /**
     * 
     * @param dateEmailSent dateEmailSent
     */
    public void setDateEmailSent(Date dateEmailSent) {
        this.dateEmailSent = dateEmailSent;
    }
    /**
     * 
     * @return synonymNames synonymNames
     */
    public String getSynonymNames() {
        return synonymNames;
    }
    /**
     * 
     * @param synonymNames synonymNames
     */
    public void setSynonymNames(String synonymNames) {
        this.synonymNames = synonymNames;
    }
    
    /**
     * 
     * @return pvValue pvValue
     */
    public String getPvValue() {
        return pvValue;
    }
    /**
     * 
     * @param pvValue pvValue
     */
    public void setPvValue(String pvValue) {
        this.pvValue = pvValue;
    }
}
