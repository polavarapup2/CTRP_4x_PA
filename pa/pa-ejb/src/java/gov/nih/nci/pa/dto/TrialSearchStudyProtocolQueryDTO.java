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

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author mshestopalov
 *
 */
@SuppressWarnings("PMD.TooManyFields")
public class TrialSearchStudyProtocolQueryDTO extends UpdateableStudyProtocolQueryDTO {
    private static final long serialVersionUID = -4576252706235998842L;
    private DocumentWorkflowStatusCode lastOffHollStatusCode;
    private DocumentWorkflowStatusCode documentWorkflowStatusCode;
    private DocumentWorkflowStatusCode previousDocumentWorkflowStatusCode;
    private Date documentWorkflowStatusDate;
    private String piFullName;
    private Long piId;
    private String primaryPurpose;
    private String primaryPurposeOtherText;
    private String onHoldReasons;    
    private String onHoldDate;
    private String offHoldDate;
    private Boolean ctgovXmlRequiredIndicator;
    private SubmissionTypeCode submissionTypeCode;
    private List<String> otherIdentifiers = new ArrayList<String>();
    private Set<String> diseaseNames = new TreeSet<String>();
    private Set<String> interventionTypes = new TreeSet<String>();
    private Long studyInboxId;
    private String studySubtypeCode;
    private String studyModelCode;
    private String studyModelOtherText;
    private String timePerspectiveCode;
    private String timePerspectiveOtherText;
    private String submitterOrgName;
    
    private Integer processingPriority;
    private String processingComments;
    private Long assignedUserId;
    
    
    /**
     * @return the studyInboxId
     */
    public Long getStudyInboxId() {
        return studyInboxId;
    }

    /**
     * @param studyInboxId the studyInboxId to set
     */
    public void setStudyInboxId(Long studyInboxId) {
        this.studyInboxId = studyInboxId;
    }



    /**
     * @return the submissionTypeCode
     */
    public SubmissionTypeCode getSubmissionTypeCode() {
        return submissionTypeCode;
    }

    /**
     * @param submissionTypeCode the submissionTypeCode to set
     */
    public void setSubmissionTypeCode(SubmissionTypeCode submissionTypeCode) {
        this.submissionTypeCode = submissionTypeCode;
    }

   

    /**
     * @return the lastOffHollStatusCode
     */
    public DocumentWorkflowStatusCode getLastOffHollStatusCode() {
        return lastOffHollStatusCode;
    }

    /**
     * @param lastOffHollStatusCode the lastOffHollStatusCode to set
     */
    public void setLastOffHollStatusCode(DocumentWorkflowStatusCode lastOffHollStatusCode) {
        this.lastOffHollStatusCode = lastOffHollStatusCode;
    }

    /**
     * 
     * @return documentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        return documentWorkflowStatusCode;
    }

    /**
     * 
     * @param documentWorkflowStatusCode documentWorkflowStatusCode
     */
    public void setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        this.documentWorkflowStatusCode = documentWorkflowStatusCode;
    }

    /**
     * 
     * @return documentWorkflowStatusDate
     */
    public Date getDocumentWorkflowStatusDate() {
        return documentWorkflowStatusDate;
    }

    /**
     * 
     * @param documentWorkflowStatusDate documentWorkflowStatusDate
     */
    public void setDocumentWorkflowStatusDate(Date documentWorkflowStatusDate) {
        this.documentWorkflowStatusDate = documentWorkflowStatusDate;
    }

 

    /**
     * 
     * @return piFullName
     */
    public String getPiFullName() {
        return piFullName;
    }

    /**
     * 
     * @param piFullName piFullName
     */
    public void setPiFullName(String piFullName) {
        this.piFullName = piFullName;
    }

    /**
     * 
     * @return piId
     */
    public Long getPiId() {
        return piId;
    }

    /**
     * 
     * @param piId piId
     */
    public void setPiId(Long piId) {
        this.piId = piId;
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
     * @return the primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }

    /**
     * @param primaryPurposeOtherText the primaryPurposeOtherText to set
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }



    /**
     * 
     * @return onHoldReasons
     */
    public String getOnHoldReasons() {
        return onHoldReasons;
    }

    /**
     * 
     * @param onHoldReasons onHoldReasons
     */
    public void setOnHoldReasons(String onHoldReasons) {
        this.onHoldReasons = onHoldReasons;
    }

    /**
     * 
     * @return offHoldDates
     */
    public String getOnHoldDate() {
        return onHoldDate;
    }

    /**
     * 
     * @param offHoldDates offHoldDates
     */
    public void setOnHoldDate(String offHoldDates) {
        this.onHoldDate = offHoldDates;
    }

    /**
     * @return the ctgovXmlRequiredIndicator
     */
    public Boolean getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }

    /**
     * @param ctgovXmlRequiredIndicator ctgovXmlRequiredIndicator to set
     */
    public void setCtgovXmlRequiredIndicator(Boolean ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }

    /**
     * Will return the identifers as comma separated strings
     * @return - identifiers
     */
    public String getAllIdentifiersAsString() {
        List<String> ids = new ArrayList<String>();
        if (StringUtils.isNotEmpty(getNciIdentifier())) {
            ids.add(getNciIdentifier());
        }
        if (StringUtils.isNotEmpty(getNctIdentifier())) {
            ids.add(getNctIdentifier());
        }
        if (StringUtils.isNotEmpty(getDcpId())) {
            ids.add(getDcpId());
        }

        if (StringUtils.isNotEmpty(getCtepId())) {
            ids.add(getCtepId());
        }
        if (CollectionUtils.isNotEmpty(getOtherIdentifiers())) {
            ids.addAll(getOtherIdentifiers());
        }
        return StringUtils.join(ids, ", ");
    }
    
    /**
     * @return OtherIdentifiers as String
     */    
    public String getOtherIdentifiersAsString() {
        return StringUtils.join(getOtherIdentifiers(), "   ");
    }
    
    /**
     * @return secondaryIdentifiers
     */
    public List<String> getOtherIdentifiers() {
        return otherIdentifiers;
    }

    /**
     * @param otherIdentifiers the secondary identifiers to set
     */
    public void setOtherIdentifiers(List<String> otherIdentifiers) {
        this.otherIdentifiers = otherIdentifiers;
    }
    
    /**
     * @param diseaseNames the diseaseNames to set
     */
    public void setDiseaseNames(Set<String> diseaseNames) {
        this.diseaseNames = diseaseNames;
    }

    /**
     * @return the diseaseNames
     */
    public Set<String> getDiseaseNames() {
        return diseaseNames;
    }
    
    /**
     * @param intTypes the interventionTypes to set
     */
    public void setInterventionType(Set<String> intTypes) {
        this.interventionTypes = intTypes;
    }

    /**
     * @return the interventionTypes
     */
    public Set<String> getInterventionTypes() {
        return interventionTypes;
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

    /**
     * @return the studyModelCode
     */
    public String getStudyModelCode() {
        return studyModelCode;
    }

    /**
     * @param studyModelCode the studyModelCode to set
     */
    public void setStudyModelCode(String studyModelCode) {
        this.studyModelCode = studyModelCode;
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
     * @return the timePerspectiveCode
     */
    public String getTimePerspectiveCode() {
        return timePerspectiveCode;
    }

    /**
     * @param timePerspectiveCode the timePerspectiveCode to set
     */
    public void setTimePerspectiveCode(String timePerspectiveCode) {
        this.timePerspectiveCode = timePerspectiveCode;
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
     * @return the submitterOrgName
     */
    public String getSubmitterOrgName() {
        return submitterOrgName;
    }

    /**
     * @param submitterOrgName the submitterOrgName to set
     */
    public void setSubmitterOrgName(String submitterOrgName) {
        this.submitterOrgName = submitterOrgName;
    }

    /**
     * @return the processingPriority
     */
    public Integer getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @return the offHoldDate
     */
    public String getOffHoldDate() {
        return offHoldDate;
    }

    /**
     * @param offHoldDate the offHoldDate to set
     */
    public void setOffHoldDate(String offHoldDate) {
        this.offHoldDate = offHoldDate;
    }

    /**
     * @return the processingComments
     */
    public String getProcessingComments() {
        return processingComments;
    }

    /**
     * @param processingComments the processingComments to set
     */
    public void setProcessingComments(String processingComments) {
        this.processingComments = processingComments;
    }

    /**
     * @return the assignedUserId
     */
    public Long getAssignedUserId() {
        return assignedUserId;
    }

    /**
     * @param assignedUserId the assignedUserId to set
     */
    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    /**
     * @return the previousDocumentWorkflowStatusCode
     */
    public DocumentWorkflowStatusCode getPreviousDocumentWorkflowStatusCode() {
        return previousDocumentWorkflowStatusCode;
    }

    /**
     * @param previousDocumentWorkflowStatusCode the previousDocumentWorkflowStatusCode to set
     */
    public void setPreviousDocumentWorkflowStatusCode(
            DocumentWorkflowStatusCode previousDocumentWorkflowStatusCode) {
        this.previousDocumentWorkflowStatusCode = previousDocumentWorkflowStatusCode;
    }  
    
    
}