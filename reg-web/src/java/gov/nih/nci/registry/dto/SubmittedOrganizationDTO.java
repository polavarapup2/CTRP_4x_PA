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
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * The Class SubmittedOrganizationDTO.
 *
 * @author Kalpana Guthikonda
 * @since May 18 2010
 */
public class SubmittedOrganizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String recruitmentStatus;
    private String recruitmentStatusDate;
    private String targetAccrualNumber;
    private String investigator;
    private Long investigatorId;
    private String programCode;
    private String siteLocalTrialIdentifier;
    private String sitePoId;
    private String dateOpenedforAccrual;
    private String dateClosedforAccrual;
    private String nameInvestigator;
    private Collection<StatusDto> statusHistory = new ArrayList<>();
    
    private int index;

    /**
     * Instantiates a new submitted organization dto.
     */
    public SubmittedOrganizationDTO() {
     // default constructor
    }

    /**
     * Instantiates a new submitted organization dto.
     * @param sp the sp
     * @param ssas the ssas
     * @param orgBo the org bo
     * @param statusHistory statusHistory
     */
    public SubmittedOrganizationDTO(StudySiteDTO sp,
            StudySiteAccrualStatusDTO ssas,
            Collection<StatusDto> statusHistory, Organization orgBo) {
        id = IiConverter.convertToString(sp.getIdentifier());
        name = orgBo.getName();
        this.statusHistory = statusHistory;
        if (ssas == null || ssas.getStatusCode() == null || ssas.getStatusDate() == null) {
            recruitmentStatus = "";
            recruitmentStatusDate = "";
        } else {
            recruitmentStatus = CdConverter.convertCdToString(ssas.getStatusCode());
            recruitmentStatusDate = PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                    ssas.getStatusDate()).toString());
        }
        if (IntConverter.convertToInteger(sp.getTargetAccrualNumber()) == null) {
            targetAccrualNumber = null;
        } else {
            targetAccrualNumber = IntConverter.convertToInteger(sp.getTargetAccrualNumber()).toString();
        }
        if (!ISOUtil.isStNull(sp.getLocalStudyProtocolIdentifier())) {
           siteLocalTrialIdentifier = StConverter.convertToString(
                    sp.getLocalStudyProtocolIdentifier());
        } else {
            siteLocalTrialIdentifier = null;
        }
        dateOpenedforAccrual = IvlConverter.convertTs().convertLowToString(sp.getAccrualDateRange());
        dateClosedforAccrual = IvlConverter.convertTs().convertHighToString(sp.getAccrualDateRange());
        programCode = StConverter.convertToString(sp.getProgramCodeText());
    }

    /**
     * @return the programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * @param programCode the programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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
     * @return the recruitmentStatus
     */
    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     * @param recruitmentStatus the recruitmentStatus to set
     */
    public void setRecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    /**
     * @return the recruitmentStatusDate
     */
    public String getRecruitmentStatusDate() {
        return recruitmentStatusDate;
    }

    /**
     * @param recruitmentStatusDate the recruitmentStatusDate to set
     */
    public void setRecruitmentStatusDate(String recruitmentStatusDate) {
        this.recruitmentStatusDate = recruitmentStatusDate;
    }

    /**
     * @return the targetAccrualNumber
     */
    public String getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(String targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    /**
     * @return the investigator
     */
    public String getInvestigator() {
        return investigator;
    }

    /**
     * @param investigator the investigator to set
     */
    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    /**
     * @return the siteLocalTrialIdentifier
     */
    public String getSiteLocalTrialIdentifier() {
        return siteLocalTrialIdentifier;
    }

    /**
     * @param siteLocalTrialIdentifier the siteLocalTrialIdentifier to set
     */
    public void setSiteLocalTrialIdentifier(String siteLocalTrialIdentifier) {
        this.siteLocalTrialIdentifier = siteLocalTrialIdentifier;
    }

    /**
     * @return the dateOpenedforAccrual
     */
    public String getDateOpenedforAccrual() {
        return dateOpenedforAccrual;
    }

    /**
     * @param dateOpenedforAccrual the dateOpenedforAccrual to set
     */
    public void setDateOpenedforAccrual(String dateOpenedforAccrual) {
        this.dateOpenedforAccrual = dateOpenedforAccrual;
    }

    /**
     * @return the dateClosedforAccrual
     */
    public String getDateClosedforAccrual() {
        return dateClosedforAccrual;
    }

    /**
     * @param dateClosedforAccrual the dateClosedforAccrual to set
     */
    public void setDateClosedforAccrual(String dateClosedforAccrual) {
        this.dateClosedforAccrual = dateClosedforAccrual;
    }

    /**
     * Gets the name investigator.
     * @return the name investigator
     */
    public String getNameInvestigator() {
        return nameInvestigator;
    }

    /**
     * Sets the name investigator.
     * @param nameInvestigator the new name investigator
     */
    public void setNameInvestigator(String nameInvestigator) {
        this.nameInvestigator = nameInvestigator;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SubmittedOrganizationDTO [id=" + id + ", name=" + name
                + ", recruitmentStatus=" + recruitmentStatus
                + ", recruitmentStatusDate=" + recruitmentStatusDate
                + ", targetAccrualNumber=" + targetAccrualNumber
                + ", investigator=" + investigator + ", investigatorId="
                + investigatorId + ", programCode=" + programCode
                + ", siteLocalTrialIdentifier=" + siteLocalTrialIdentifier
                + ", dateOpenedforAccrual=" + dateOpenedforAccrual
                + ", dateClosedforAccrual=" + dateClosedforAccrual
                + ", nameInvestigator=" + nameInvestigator + "]";
    }

    /**
     * @return the investigatorId
     */
    public Long getInvestigatorId() {
        return investigatorId;
    }

    /**
     * @param investigatorId the investigatorId to set
     */
    public void setInvestigatorId(Long investigatorId) {
        this.investigatorId = investigatorId;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the sitePoId
     */
    public String getSitePoId() {
        return sitePoId;
    }

    /**
     * @param sitePoId the sitePoId to set
     */
    public void setSitePoId(String sitePoId) {
        this.sitePoId = sitePoId;
    }

    /**
     * @return boolean
     */
    public boolean isBlank() {
        return investigatorId == null
                && StringUtils.isBlank(siteLocalTrialIdentifier)
                && StringUtils.isBlank(recruitmentStatus)
                && StringUtils.isBlank(recruitmentStatusDate)
                && StringUtils.isBlank(dateOpenedforAccrual)
                && StringUtils.isBlank(dateClosedforAccrual)
                && CollectionUtils.isEmpty(statusHistory);
    }

    /**
     * @return the statusHistory
     */
    public Collection<StatusDto> getStatusHistory() {
        return statusHistory;
    }

    /**
     * @param statusHistory the statusHistory to set
     */
    public void setStatusHistory(Collection<StatusDto> statusHistory) {
        this.statusHistory = statusHistory;
    }
}
