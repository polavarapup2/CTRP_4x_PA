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

import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author mshestopalov
 *
 */
@SuppressWarnings("PMD.TooManyFields")
public class BaseStudyProtocolQueryDTO implements Serializable {
    private static final long serialVersionUID = -5823207454734658529L;
    private Long studyProtocolId;
    private String nciIdentifier;
    private String officialTitle;
    private StudyStatusCode studyStatusCode;
    private Date studyStatusDate;
    
    private String leadOrganizationName;
    private Long leadOrganizationId;
    private Long leadOrganizationPOId;
    private String leadOrganizationTrialIdentifier;

    private String localStudyProtocolIdentifier;
    private StudyTypeCode studyTypeCode;
    private PhaseCode phaseCode;
    private PhaseAdditionalQualifierCode phaseAdditionalQualifier;
    private String studyProtocolType;
    private String nctIdentifier;
    private String ctepId;
    private String dcpId;
    private String cdrId;
    private String ccrId;
    
    
    /**
     * 
     * @return leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }

    /**
     * 
     * @param leadOrganizationName leadOrganizationName
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }

    /**
     * 
     * @return leadOrganizationId
     */
    public Long getLeadOrganizationId() {
        return leadOrganizationId;
    }

    /**
     * 
     * @param leadOrganizationId leadOrganizationId
     */
    public void setLeadOrganizationId(Long leadOrganizationId) {
        this.leadOrganizationId = leadOrganizationId;
    }
    
    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * 
     * @return nciIdentifier
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
     * @return officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * 
     * @return studyStatusCode
     */
    public StudyStatusCode getStudyStatusCode() {
        return studyStatusCode;
    }

    /**
     * 
     * @param studyStatusCode studyStatusCode
     */
    public void setStudyStatusCode(StudyStatusCode studyStatusCode) {
        this.studyStatusCode = studyStatusCode;
    }

    /**
     * 
     * @return studyStatusDate
     */
    public Date getStudyStatusDate() {
        return studyStatusDate;
    }

    /**
     * 
     * @param studyStatusDate studyStatusDate
     */
    public void setStudyStatusDate(Date studyStatusDate) {
        this.studyStatusDate = studyStatusDate;
    }
    
    /**
     * 
     * @return localStudyProtocolIdentifier
     */
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
     * 
     * @return studyTypeCode
     */
    public StudyTypeCode getStudyTypeCode() {
        return studyTypeCode;
    }

    /**
     * 
     * @param studyTypeCode studyTypeCode
     */
    public void setStudyTypeCode(StudyTypeCode studyTypeCode) {
        this.studyTypeCode = studyTypeCode;
    }

    /**
     * @return the phaseCode
     */
    public PhaseCode getPhaseCode() {
        return phaseCode;
    }

    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * 
     * @return studyProtocolType
     */
    public String getStudyProtocolType() {
        return studyProtocolType;
    }

    /**
     * 
     * @param studyProtocolType studyProtocolType
     */
    public void setStudyProtocolType(String studyProtocolType) {
        this.studyProtocolType = studyProtocolType;
    }

    /**
     * @param phaseAdditionalQualifier the phaseAdditionalQualifier to set
     */
    public void setPhaseAdditionalQualifier(PhaseAdditionalQualifierCode phaseAdditionalQualifier) {
        this.phaseAdditionalQualifier = phaseAdditionalQualifier;
    }

    /**
     * @return the phaseAdditionalQualifier
     */
    public PhaseAdditionalQualifierCode getPhaseAdditionalQualifier() {
        return phaseAdditionalQualifier;
    }

    /**
     * @return the leadOrganizationTrialIdentifier
     */
    public String getLeadOrganizationTrialIdentifier() {
        return leadOrganizationTrialIdentifier;
    }

    /**
     * @param leadOrganizationTrialIdentifier the leadOrganizationTrialIdentifier to set
     */
    public void setLeadOrganizationTrialIdentifier(String leadOrganizationTrialIdentifier) {
        this.leadOrganizationTrialIdentifier = leadOrganizationTrialIdentifier;
    }

    /**
     * @return the nctIdentifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }

    /**
     * @param nctIdentifier the nctIdentifier to set
     */
    public void setNctIdentifier(String nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }

    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

    /**
     * @return the dcpId
     */
    public String getDcpId() {
        return dcpId;
    }

    /**
     * @param dcpId the dcpId to set
     */
    public void setDcpId(String dcpId) {
        this.dcpId = dcpId;
    }

    /**
     * @return the leadOrganizationPOId
     */
    public Long getLeadOrganizationPOId() {
        return leadOrganizationPOId;
    }

    /**
     * @param leadOrganizationPOId the leadOrganizationPOId to set
     */
    public void setLeadOrganizationPOId(Long leadOrganizationPOId) {
        this.leadOrganizationPOId = leadOrganizationPOId;
    }
    
    /**
     * @param poID
     *            the leadOrganizationPOId to set
     */
    public void setLeadOrganizationPOId(String poID) {
        if (NumberUtils.isNumber(poID)) {
            setLeadOrganizationPOId(Long.parseLong(poID));
        }
    }
    
    /**
     * @return NciIdentifierTruncated
     */
    public String getNciIdentifierTruncated() {
        return StringUtils.replace(getNciIdentifier(), "NCI-", "");
    }

    /**
     * @return the cdrId
     */
    public String getCdrId() {
        return cdrId;
    }

    /**
     * @param cdrId the cdrId to set
     */
    public void setCdrId(String cdrId) {
        this.cdrId = cdrId;
    }

    /**
     * @return the ccrId
     */
    public String getCcrId() {
        return ccrId;
    }

    /**
     * @param ccrId the ccrId to set
     */
    public void setCcrId(String ccrId) {
        this.ccrId = ccrId;
    }
    
}
