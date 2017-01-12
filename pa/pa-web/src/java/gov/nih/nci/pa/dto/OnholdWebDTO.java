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
package gov.nih.nci.pa.dto;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
* @author Hugh Reinhart
* @since 2/19/2009
*/

public class OnholdWebDTO implements Serializable {
    private static final long serialVersionUID = -8451481638841127437L;
    private String identifier;
    private String reasonCode;
    private String reasonText;
    private String dateLow;
    private String dateHigh;
    private String processingLog;
    private String reasonCategory;
    private List<String> allReasonCategoryValuesList = new ArrayList<>(); 

    /** */
    public OnholdWebDTO() {
        // default constructor
    }
    /**
     * @param isoDto the is dto
     */
    public OnholdWebDTO(StudyOnholdDTO isoDto) {
        this.identifier = IiConverter.convertToString(isoDto.getIdentifier());
        this.reasonCode = CdConverter.convertCdToString(isoDto.getOnholdReasonCode());
        this.reasonText = StConverter.convertToString(isoDto.getOnholdReasonText());
        this.dateLow = IvlConverter.convertTs().convertLowToString(isoDto.getOnholdDate());
        this.dateHigh = IvlConverter.convertTs().convertHighToString(isoDto.getOnholdDate());
        this.processingLog = StConverter.convertToString(isoDto.getProcessingLog());
        this.reasonCategory = StConverter.convertToString(isoDto.getOnHoldCategory());
    }
    /**
     * @param studyProtocolIi Ii for the associated study protocol
     * @return iso dto
     */
    public StudyOnholdDTO getIsoDto(Ii studyProtocolIi) {
        StudyOnholdDTO isoDto = new StudyOnholdDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(getIdentifier()));
        isoDto.setStudyProtocolIdentifier(studyProtocolIi);
        isoDto.setOnholdReasonCode(CdConverter.convertStringToCd(getReasonCode()));
        isoDto.setOnholdReasonText(StConverter.convertToSt(getReasonText()));
        isoDto.setOnholdDate(IvlConverter.convertTs().convertToIvl(getDateLow(), getDateHigh()));
        isoDto.setProcessingLog(StConverter.convertToSt(processingLog));
        isoDto.setOnHoldCategory(StConverter.convertToSt(getReasonCategory()));
        return isoDto;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }
    /**
     * @param reasonCode the reasonCode to set
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    /**
     * @return the reasonText
     */
    public String getReasonText() {
        return reasonText;
    }
    /**
     * @param reasonText the reasonText to set
     */
    public void setReasonText(String reasonText) {
        this.reasonText = StringUtils.left(reasonText, PAAttributeMaxLen.LEN_4000);
    }
    /**
     * @return the dateLow
     */
    public String getDateLow() {
        return dateLow;
    }
    /**
     * @param dateLow the dateLow to set
     */
    public void setDateLow(String dateLow) {
        this.dateLow = PAUtil.normalizeDateString(dateLow);
    }
    /**
     * @return the dateHigh
     */
    public String getDateHigh() {
        return dateHigh;
    }
    /**
     * @param dateHigh the dateHigh to set
     */
    public void setDateHigh(String dateHigh) {
        this.dateHigh = PAUtil.normalizeDateString(dateHigh);
    }
    /**
     * @return the processingLog
     */
    public String getProcessingLog() {
        return processingLog;
    }
    /**
     * @param processingLog the processingLog to set
     */
    public void setProcessingLog(String processingLog) {
        this.processingLog = processingLog;
    }
    
    /**
     * @return reasonCategory
     */
    public String getReasonCategory() {
        return reasonCategory;
    }
    
    /**
     * @param reasonCategory reasonCategory
     */
    public void setReasonCategory(String reasonCategory) {
        this.reasonCategory = reasonCategory;
    }
    /**
     * @return allReasonCategoryValuesList allReasonCategoryValuesList
     */
    public List<String> getAllReasonCategoryValuesList() {
        return allReasonCategoryValuesList;
    }
    /**
     * @param allReasonCategoryValuesList allReasonCategoryValuesList
     */
    public void setAllReasonCategoryValuesList(
            List<String> allReasonCategoryValuesList) {
        this.allReasonCategoryValuesList = allReasonCategoryValuesList;
    }
}
