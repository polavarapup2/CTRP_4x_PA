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
package gov.nih.nci.pa.domain;


import java.util.Date;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Representation of a planned marker.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Entity
@Table(name = "PLANNED_MARKER")
public class PlannedMarker extends PlannedObservation {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_COLUMN_LENGTH = 200;
    /** Maximum length of planned attribute column length . */
    private static final int ATTRIBUTE_COLUMN_LENGTH = 200;
    private String hugoBiomarkerCode;
    private String assayTypeCode;
    private String assayTypeOtherText;
    private String assayUseCode;
    private String assayPurposeCode;
    private String assayPurposeOtherText;
    private String tissueSpecimenTypeCode;
    private String specimenTypeOtherText;
    private String tissueCollectionMethodCode;
    private ActiveInactivePendingCode statusCode;
    private String evaluationTypeCode;
    private String evaluationTypeOtherText;
    private PlannedMarkerSyncWithCaDSR permissibleValue; 
    private Date dateEmailSent;


    /**
     * @return the hugoBiomarkerCode
     */
    @Column(name = "HUGO_BIOMARKER_CODE", length = DEFAULT_COLUMN_LENGTH)
    public String getHugoBiomarkerCode() {
        return hugoBiomarkerCode;
    }

    /**
     * @param hugoBiomarkerCode the hugoBiomarkerCode to set
     */
    public void setHugoBiomarkerCode(String hugoBiomarkerCode) {
        this.hugoBiomarkerCode = hugoBiomarkerCode;
    }

    /**
     * @return the assayTypeCode
     */
    @Column(name = "ASSAY_TYPE_CODE", length = ATTRIBUTE_COLUMN_LENGTH, nullable = false)
   // @Enumerated(EnumType.STRING)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
    public String getAssayTypeCode() {
        return assayTypeCode;
    }

    /**
     * @param assayTypeCode the assayTypeCode to set
     */
    public void setAssayTypeCode(String assayTypeCode) {
        this.assayTypeCode = assayTypeCode;
    }

    /**
     * @return the assayTypeOtherText
     */
    @Column(name = "ASSAY_TYPE_OTHER_TEXT", length = DEFAULT_COLUMN_LENGTH)
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
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
     * @return the assayUseCode
     */
    @Column(name = "ASSAY_USE_CODE", length = DEFAULT_COLUMN_LENGTH, nullable = false)
   // @Enumerated(EnumType.STRING)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
    public String getAssayUseCode() {
        return assayUseCode;
    }

    /**
     * @param assayUseCode the assayUseCode to set
     */
    public void setAssayUseCode(String assayUseCode) {
        this.assayUseCode = assayUseCode;
    }

    /**
     * @return the assayPurposeCode
     */
    @Column(name = "ASSAY_PURPOSE_CODE", length = DEFAULT_COLUMN_LENGTH, nullable = false)
   // @Enumerated(EnumType.STRING)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
    public String getAssayPurposeCode() {
        return assayPurposeCode;
    }

    /**
     * @param assayPurposeCode the assayPurposeCode to set
     */
    public void setAssayPurposeCode(String assayPurposeCode) {
        this.assayPurposeCode = assayPurposeCode;
    }

    /**
     * @return the assayPurposeOtherText
     */
    @Column(name = "ASSAY_PURPOSE_OTHER_TEXT", length = DEFAULT_COLUMN_LENGTH)
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
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
     * @return the tissueSpecimenTypeCode
     */
    @Column(name = "TISSUE_SPECIMEN_TYPE_CODE", length = ATTRIBUTE_COLUMN_LENGTH, nullable = false)
   // @Enumerated(EnumType.STRING)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
    public String getTissueSpecimenTypeCode() {
        return tissueSpecimenTypeCode;
    }

    /**
     * @param tissueSpecimenTypeCode the tissueSpecimenTypeCode to set
     */
    public void setTissueSpecimenTypeCode(String tissueSpecimenTypeCode) {
        this.tissueSpecimenTypeCode = tissueSpecimenTypeCode;
    }

    /**
     * @return the tissueCollectionMethodCode
     */
    @Column(name = "TISSUE_COLLECTION_METHOD_CODE", length = DEFAULT_COLUMN_LENGTH)
    public String getTissueCollectionMethodCode() {
        return tissueCollectionMethodCode;
    }

    /**
     * @param tissueCollectionMethodCode the tissueCollectionMethodCode to set
     */
    public void setTissueCollectionMethodCode(String tissueCollectionMethodCode) {
        this.tissueCollectionMethodCode = tissueCollectionMethodCode;
    }

    /**
    *
    * @return statusCode
    */
   @Column(name = "STATUS_CODE", length = DEFAULT_COLUMN_LENGTH, nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   public ActiveInactivePendingCode getStatusCode() {
       return statusCode;
   }
   /**
    *
    * @param statusCode statusCode
    */
   public void setStatusCode(ActiveInactivePendingCode statusCode) {
       this.statusCode = statusCode;
   }
   /**
    * @return the evaluationTypeCode
    */
   @Column(name = "Evaluation_Type_Code", length = ATTRIBUTE_COLUMN_LENGTH, nullable = false)
   @NotNull
    public String getEvaluationTypeCode() {
        return evaluationTypeCode;
    }
   /**
   *
   * @param evaluationTypeCode evaluationTypeCode
   */
    public void setEvaluationTypeCode(String evaluationTypeCode) {
        this.evaluationTypeCode = evaluationTypeCode;
    }
    /**
     * @return the evaluationTypeOtherText
     */
    @Column(name = "EVALUATION_TYPE_OTHER_TEXT", length = DEFAULT_COLUMN_LENGTH)
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
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
     * @return the specimenTypeOtherText
     */
    @Column(name = "SPECIMEN_TYPE_OTHER_TEXT", length = DEFAULT_COLUMN_LENGTH)
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
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
    *
    * @return permisibleValue permisibleValue
    */
   @ManyToOne
   @JoinColumn(name = "PM_SYNC_IDENTIFIER")
    public PlannedMarkerSyncWithCaDSR getPermissibleValue() {
        return permissibleValue;
    }
   /**
   *
   * @param permissibleValue permissibleValue
   */
    public void setPermissibleValue(PlannedMarkerSyncWithCaDSR permissibleValue) {
        this.permissibleValue = permissibleValue;
    }
    /**
     * 
     * @return dateEmailSent dateEmailSent
     */
    @Column(name = "DATE_EMAIL_SENT_CADSR")
    @Temporal(TemporalType.TIMESTAMP)
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

}
