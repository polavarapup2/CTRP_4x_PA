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
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyObjectiveTypeCode;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * action for study title and description.
 * @author Anupama Sharma
 *
 */
public class TrialDescriptionAction extends ActionSupport {

    private static final long serialVersionUID = -263739685830642951L;
    private static final int PUBLIC_TITLE = 300;
    private static final int PUBLIC_DESCRIPTION = 5000;
    private static final String RESULT = "edit";

    private static final String MAX_LEN = "2000";
    private static final String MAX_LEN_OUTLINE = "32000";
    private String trialBriefTitle;
    private String trialBriefSummary;
    private String outline;
    private String primary = "";
    private String secondary = "";
    private String ternary = "";
    private String studyObjectiveIip;
    private String studyObjectiveIis;
    private String studyObjectiveIit;
    /**
     * @return res
     */
    @SkipValidation
    public String query() {
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);

            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            copy(spDTO);
            getStudyObjectiveFromDB(studyProtocolIi);

        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return RESULT;
    }

    /**
     * @return result
     */
    public String update() {
       if (hasFieldErrors()) {
          return RESULT;
        }
        save();
        query();
        return RESULT;
    }

    private void save() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            updateStudyProtocol(studyProtocolIi);
            updateStudyObjective(studyProtocolIi);
            getStudyObjectiveFromDB(studyProtocolIi);
            StudyProtocolQueryDTO  studyProtocolQueryDTO =
                PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                        Long.valueOf(studyProtocolIi.getExtension()));
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(StringUtils.abbreviate(studyProtocolQueryDTO.getOfficialTitle(),
                    PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }

    }
    private void copy(StudyProtocolDTO spDTO) {
        setTrialBriefTitle(spDTO.getPublicTitle().getValue());
        setTrialBriefSummary(spDTO.getPublicDescription().getValue());
        setOutline(spDTO.getScientificDescription().getValue());
    }

    private void updateStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setPublicTitle(StConverter.convertToSt(StringUtils.left(getTrialBriefTitle(), PUBLIC_TITLE)));
        spDTO.setPublicDescription(StConverter.convertToSt(StringUtils.left(getTrialBriefSummary()
                   , PUBLIC_DESCRIPTION)));
        spDTO.setScientificDescription(StConverter.convertToSt(getOutline()));
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
    }
    private void getStudyObjectiveFromDB(Ii studyProtocolIi) throws PAException {
        List<StudyObjectiveDTO> studyObjList = PaRegistry.getStudyObjectiveService().
                    getByStudyProtocol(studyProtocolIi);
        for (StudyObjectiveDTO dto : studyObjList) {
            if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.PRIMARY.getCode())) {
                setPrimary(StConverter.convertToString(dto.getDescription()));
                setStudyObjectiveIip(IiConverter.convertToString(dto.getIdentifier()));
            }
            if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.SECONDARY.getCode())) {
                setSecondary(StConverter.convertToString(dto.getDescription()));
                setStudyObjectiveIis(IiConverter.convertToString(dto.getIdentifier()));
            }
            if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.TERNARY.getCode())) {
                setTernary(StConverter.convertToString(dto.getDescription()));
                setStudyObjectiveIit(IiConverter.convertToString(dto.getIdentifier()));
            }
        }
    }
    private void updateStudyObjective(Ii studyProtocolIi) throws PAException {
        StudyObjectiveDTO dto = new StudyObjectiveDTO();
        if (StringUtils.isNotEmpty(getPrimary())) {
            dto.setDescription(StConverter.convertToSt(getPrimary()));
        } else {
            dto.setDescription(StConverter.convertToSt(""));
        }
        dto.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.PRIMARY));
        dto.setStudyProtocolIdentifier(studyProtocolIi);
        if (getStudyObjectiveIip() != null) {
            dto.setIdentifier(IiConverter.convertToIi(getStudyObjectiveIip()));
        }
        saveOrUpdate(dto);
        dto = new StudyObjectiveDTO();
        if (StringUtils.isNotEmpty(getSecondary())) {
            dto.setDescription(StConverter.convertToSt(getSecondary()));
        } else {
            dto.setDescription(StConverter.convertToSt(""));
        }
        dto.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.SECONDARY));
        dto.setStudyProtocolIdentifier(studyProtocolIi);
        if (getStudyObjectiveIis() != null) {
            dto.setIdentifier(IiConverter.convertToIi(getStudyObjectiveIis()));
        }
        saveOrUpdate(dto);
        dto = new StudyObjectiveDTO();
        if (StringUtils.isNotEmpty(getTernary())) {
            dto.setDescription(StConverter.convertToSt(getTernary()));
        } else {
            dto.setDescription(StConverter.convertToSt(""));
        }
        dto.setTypeCode(CdConverter.convertToCd(StudyObjectiveTypeCode.TERNARY));
        dto.setStudyProtocolIdentifier(studyProtocolIi);
        if (getStudyObjectiveIit() != null) {
            dto.setIdentifier(IiConverter.convertToIi(getStudyObjectiveIit()));
        }
        saveOrUpdate(dto);
    }

    /**
     * @param dto
     * @throws PAException
     */
    private void saveOrUpdate(StudyObjectiveDTO dto) throws PAException {
        if (StringUtils.isNotEmpty(IiConverter.convertToString(dto.getIdentifier()))) {
            PaRegistry.getStudyObjectiveService().update(dto);
        } else {
            PaRegistry.getStudyObjectiveService().create(dto);
        }
    }
    /**
     * @return the trialBriefTitle
     */
    public String getTrialBriefTitle() {
        return trialBriefTitle;
    }

    /**
     * @param trialBriefTitle the trialBriefTitle to set
     */
    public void setTrialBriefTitle(String trialBriefTitle) {
        this.trialBriefTitle = trialBriefTitle;
    }

    /**
     * @return the trialBriefSummary
     */
    public String getTrialBriefSummary() {
        return trialBriefSummary;
    }

    /**
     * @param trialBriefSummary the trialBriefSummary to set
     */
    public void setTrialBriefSummary(String trialBriefSummary) {
        this.trialBriefSummary = trialBriefSummary;
    }

    /**
     * @return the outline
     */
    @StringLengthFieldValidator(message = "Outline must be 32000 characters max", maxLength = MAX_LEN_OUTLINE)
    public String getOutline() {
        return outline;
    }

    /**
     * @param outline the outline to set
     */
    public void setOutline(String outline) {
        this.outline = outline;
    }

    /**
     * @return the primary
     */
    @StringLengthFieldValidator(message = "Primary must be 2000 characters max", maxLength = MAX_LEN)
    public String getPrimary() {
        return primary;
    }

    /**
     * @param primary the primary to set
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * @return the secondary
     */
    @StringLengthFieldValidator(message = "Secondary must be 2000 characters max", maxLength = MAX_LEN)
    public String getSecondary() {
        return secondary;
    }

    /**
     * @param secondary the secondary to set
     */
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    /**
     * @return the ternary
     */
    @StringLengthFieldValidator(message = "Ternary must be 2000 characters max", maxLength = MAX_LEN)
    public String getTernary() {
        return ternary;
    }

    /**
     * @param ternary the ternary to set
     */
    public void setTernary(String ternary) {
        this.ternary = ternary;
    }

    /**
     * @return the studyObjectiveIip
     */
    public String getStudyObjectiveIip() {
        return studyObjectiveIip;
    }

    /**
     * @param studyObjectiveIip the studyObjectiveIip to set
     */
    public void setStudyObjectiveIip(String studyObjectiveIip) {
        this.studyObjectiveIip = studyObjectiveIip;
    }

    /**
     * @return the studyObjectiveIis
     */
    public String getStudyObjectiveIis() {
        return studyObjectiveIis;
    }

    /**
     * @param studyObjectiveIis the studyObjectiveIis to set
     */
    public void setStudyObjectiveIis(String studyObjectiveIis) {
        this.studyObjectiveIis = studyObjectiveIis;
    }

    /**
     * @return the studyObjectiveIit
     */
    public String getStudyObjectiveIit() {
        return studyObjectiveIit;
    }

    /**
     * @param studyObjectiveIit the studyObjectiveIit to set
     */
    public void setStudyObjectiveIit(String studyObjectiveIit) {
        this.studyObjectiveIit = studyObjectiveIit;
    }





}
