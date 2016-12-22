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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.OutcomeMeasureWebDTO;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.opensymphony.xwork2.Preparable;
/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class InterventionalStudyDesignAction extends AbstractMultiObjectDeleteAction implements Preparable {

    /**
     * Maximum length for Outcome name and time frame.
     */
    static final int MAXIMUM_CHAR_OUTCOME_NAME = 254;
    /**
     * Maximum length for Outcome description.
     */
    static final int MAXIMUM_CHAR_OUTCOME_DESC = 999;
    private static final long serialVersionUID = -8139821069851279621L;
    private static final String OUTCOME = "outcome";
    private static final String FALSE = "false";
    private static final String OUTCOMEADD = "outcomeAdd";
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    private String subject;
    private String investigator;
    private String caregiver;
    private String outcomesassessor;
    private List<ISDesignDetailsWebDTO> outcomeList;
    private Long id = null;
    private String page;
    private String orderString;
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;

    /**
     * @return res
     */
    public String detailsQuery() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
            ispDTO = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(studyProtocolIi);
            webDTO = setDesignDetailsDTO(ispDTO);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }
    
    @Override
    public String execute() {
        return detailsQuery();
    }

    /**
     * @return res
     * @throws PAException PAException
     */
    public String update() throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        InterventionalStudyProtocolDTO ispDTO = PaRegistry
                .getStudyProtocolService().getInterventionalStudyProtocol(
                        studyProtocolIi);
        enforceBusinessRules(ispDTO);
        if (hasFieldErrors()) {
            return "details";
        }
        try {            
            setPhaseAndPurpose(ispDTO);
            ispDTO.setBlindingSchemaCode(CdConverter.convertToCd(BlindingSchemaCode.getByCode(
                    webDTO.getBlindingSchemaCode())));
            ispDTO.setExpandedAccessIndicator(BlConverter.convertToBl(webDTO.isExpandedIndicator()));
            ispDTO.setDesignConfigurationCode(CdConverter.convertToCd(DesignConfigurationCode.getByCode(
                    webDTO.getDesignConfigurationCode())));
            ispDTO.setNumberOfInterventionGroups(IntConverter.convertToInt(webDTO.getNumberOfInterventionGroups()));
            ispDTO.setAllocationCode(CdConverter.convertToCd(AllocationCode.getByCode(webDTO.getAllocationCode())));
            ispDTO.setTargetAccrualNumber(IvlConverter.convertInt().convertToIvl(
                    webDTO.getMinimumTargetAccrualNumber(), null));
            ispDTO.setFinalAccrualNumber(IntConverter.convertToInt(webDTO.getFinalAccrualNumber()));
            ispDTO.setStudyClassificationCode(CdConverter.convertToCd(StudyClassificationCode.getByCode(
                    webDTO.getStudyClassificationCode())));
            List<Cd> cds = new ArrayList<Cd>();
            if (webDTO.getBlindingSchemaCode() != null && !webDTO.getBlindingSchemaCode().equalsIgnoreCase("open")) {
                addToCdToList(caregiver, cds);
                addToCdToList(investigator, cds);
                addToCdToList(outcomesassessor, cds);
                addToCdToList(subject, cds);
            }
            ispDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(cds));
            PaRegistry.getStudyProtocolService().updateInterventionalStudyProtocol(ispDTO, "DesignDetails");
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            detailsQuery();           
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }
    
    
    /**
     * @return String
     * @throws PAException
     */
    public String changeStudyType() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            if (PAConstants.NON_INTERVENTIONAL.equalsIgnoreCase(webDTO
                    .getStudyType())) {
                PaRegistry.getStudyProtocolService().changeStudyProtocolType(
                        studyProtocolIi, StudyTypeCode.NON_INTERVENTIONAL);
                ServletActionContext.getRequest().setAttribute(
                        Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                return "nisdesign";
            }
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }
    
    /**
     *
     * @param input string
     * @param cds List<Cd>
     */
    private void addToCdToList(String input, List<Cd> cds) {
        if (!StringUtils.equalsIgnoreCase(input, FALSE)) {
            cds.add(CdConverter.convertStringToCd(input));
        }
    }
    /**
     * @param ispDTO
     */
    private void setPhaseAndPurpose(InterventionalStudyProtocolDTO ispDTO) {
        ispDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(webDTO.getPhaseCode())));
        if (PAUtil.isPhaseCodeNA(webDTO.getPhaseCode())) {
            ispDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(
                    PhaseAdditionalQualifierCode.getByCode(webDTO.getPhaseAdditionalQualifierCode())));
        } else {
            ispDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd((Lov) null));
        }
        setPurpose(ispDTO);
    }

    /**
     * @param ispDTO
     */
    private void setPurpose(InterventionalStudyProtocolDTO ispDTO) {
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.getByCode(
                webDTO.getPrimaryPurposeCode())));
        if (PAUtil.isPrimaryPurposeCodeOther(webDTO.getPrimaryPurposeCode())) {
            ispDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(
              PrimaryPurposeAdditionalQualifierCode.getByCode(webDTO.getPrimaryPurposeAdditionalQualifierCode())));
        } else {
            ispDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd((Lov) null));
        }
        if (PAUtil.isPrimaryPurposeAdditionQualifierCodeOther(webDTO.getPrimaryPurposeCode(),
                webDTO.getPrimaryPurposeAdditionalQualifierCode())) {
            ispDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(webDTO.getPrimaryPurposeOtherText()));
        } else {
            ispDTO.setPrimaryPurposeOtherText(null);
        }
        if (CollectionUtils.isEmpty(webDTO.getSecondaryPurposes())) {
            ispDTO.setSecondaryPurposes(null);
        } else {
            ispDTO.setSecondaryPurposes(DSetConverter
                    .convertListStToDSet(webDTO.getSecondaryPurposes()));
            ispDTO.setSecondaryPurposeOtherText(StConverter.convertToSt(webDTO.getSecondaryPurposeOtherText()));
        }
    }

    private void enforceBusinessRules(StudyProtocolDTO ispDTO) {
        boolean abbr = BlConverter.convertToBool(ispDTO.getProprietaryTrialIndicator());
        addErrors(webDTO.getPrimaryPurposeCode(), "webDTO.primaryPurposeCode", "error.primary");
        addErrors(webDTO.getPhaseCode(), "webDTO.phaseCode", "error.phase");
        final String arms = webDTO.getNumberOfInterventionGroups();
        if (!abbr) {
            addErrors(webDTO.getDesignConfigurationCode(), "webDTO.designConfigurationCode", "error.intervention");
            addErrors(arms, "webDTO.numberOfInterventionGroups", "error.arms");
            addErrors(webDTO.getBlindingSchemaCode(), "webDTO.blindingSchemaCode", "error.masking");
            addErrors(webDTO.getAllocationCode(), "webDTO.allocationCode", "error.allocation");
            addErrors(webDTO.getMinimumTargetAccrualNumber(), "webDTO.minimumTargetAccrualNumber",
                    "error.target.enrollment");
        }
        validateTragetAccrualNumber();
        if (StringUtils.isNotBlank(arms) && !NumberUtils.isDigits(arms)) {
            addFieldError("webDTO.numberOfInterventionGroups", getText("error.numeric"));
        }
        if (NumberUtils.isNumber(arms)
                && NumberUtils.toInt(arms) < 1) {
            addFieldError("webDTO.numberOfInterventionGroups",
                    getText("error.positiveNumOfArms"));
        }
        if (PAUtil.isPrimaryPurposeOtherCodeReq(webDTO.getPrimaryPurposeCode(),
               webDTO.getPrimaryPurposeAdditionalQualifierCode())) {
            addFieldError("webDTO.primaryPurposeAdditionalQualifierCode", getText("error.otherPurposeCode"));
        }
        //validate Purpose when Selected value is OTHER
        if (PAUtil.isPrimaryPurposeOtherTextReq(webDTO.getPrimaryPurposeCode(),
                webDTO.getPrimaryPurposeAdditionalQualifierCode(), webDTO.getPrimaryPurposeOtherText())) {
            addFieldError("webDTO.primaryPurposeOtherText", getText("error.otherPurposeText"));
        }
    }

    private void validateTragetAccrualNumber() {
        try {
            Integer tarAccrual = NumberUtils.createInteger(webDTO.getMinimumTargetAccrualNumber());
            if (tarAccrual != null && tarAccrual < 0) {
                addFieldError("webDTO.minimumTargetAccrualNumber", getText("error.negative"));
            }
        } catch (NumberFormatException e) {
                addFieldError("webDTO.minimumTargetAccrualNumber", getText("error.numeric"));
        }
        
        if (StringUtils.isNotBlank(webDTO.getFinalAccrualNumber())) {
            try {
                Integer tarAccrual = NumberUtils.createInteger(webDTO
                        .getFinalAccrualNumber());
                if (tarAccrual != null && tarAccrual < 0) {
                    addFieldError("webDTO.finalAccrualNumber",
                            getText("error.negative"));
                }
            } catch (NumberFormatException e) {
                addFieldError("webDTO.finalAccrualNumber",
                        getText("error.numeric"));
            }
        }
        
    }

    private void addErrors(String fieldValue, String fieldName, String errMsg) {
        if (StringUtils.isEmpty(fieldValue)) {
            addFieldError(fieldName, getText(errMsg));
        }
     }

    private ISDesignDetailsWebDTO setDesignDetailsDTO(InterventionalStudyProtocolDTO ispDTO) {
        ISDesignDetailsWebDTO dto = new ISDesignDetailsWebDTO();
        if (ispDTO != null) {
            dto.setStudyType(PAConstants.INTERVENTIONAL);
            convertPhase(ispDTO, dto);
            convertPurpose(ispDTO, dto);
            convertBlindingShemaCode(ispDTO, dto);
            convertDesignConfigurationCode(ispDTO, dto);
            convertAllocationCode(ispDTO, dto);
            convertNumInterventionGroups(ispDTO, dto);
            convertBlindedRoleCodes(ispDTO);
            convertTargetAccrualNumber(ispDTO, dto);
            convertStudyClassificationCode(ispDTO, dto);
            if (!ISOUtil.isBlNull(ispDTO.getExpandedAccessIndicator())) {
                dto.setExpandedIndicator(BlConverter.convertToBoolean(ispDTO
                        .getExpandedAccessIndicator()));
            }
            
        }
        return dto;
    }

    private void convertStudyClassificationCode(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getStudyClassificationCode() != null) {
            dto.setStudyClassificationCode(ispDTO.getStudyClassificationCode().getCode());
        }
    }

    private void convertTargetAccrualNumber(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getTargetAccrualNumber().getLow().getValue() != null) {
            dto.setMinimumTargetAccrualNumber(
                    ispDTO.getTargetAccrualNumber().getLow().getValue().toString());
        }
        if (!ISOUtil.isIntNull(ispDTO.getFinalAccrualNumber())) {
            dto.setFinalAccrualNumber(IntConverter.convertToString(ispDTO.getFinalAccrualNumber()));
        }
    }

    private void convertNumInterventionGroups(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
            dto.setNumberOfInterventionGroups(ispDTO.getNumberOfInterventionGroups().getValue().toString());
        }
    }

    private void convertAllocationCode(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getAllocationCode() != null) {
            dto.setAllocationCode(ispDTO.getAllocationCode().getCode());
        }
    }

    private void convertDesignConfigurationCode(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getDesignConfigurationCode() != null) {
            dto.setDesignConfigurationCode(ispDTO.getDesignConfigurationCode().getCode());
        }
    }

    private void convertBlindingShemaCode(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getBlindingSchemaCode() != null) {
            dto.setBlindingSchemaCode(ispDTO.getBlindingSchemaCode().getCode());
        }
    }

    private void convertPurpose(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getPrimaryPurposeCode() != null) {
            dto.setPrimaryPurposeCode(ispDTO.getPrimaryPurposeCode().getCode());
        }
        if (ispDTO.getPrimaryPurposeAdditionalQualifierCode() != null) {
            dto.setPrimaryPurposeAdditionalQualifierCode(ispDTO.getPrimaryPurposeAdditionalQualifierCode()
                                                         .getCode());
        }
        if (ispDTO.getPrimaryPurposeOtherText() != null) {
            dto.setPrimaryPurposeOtherText(StConverter.convertToString(ispDTO.getPrimaryPurposeOtherText()));
        }
        if (ispDTO.getSecondaryPurposes() != null) {
            dto.setSecondaryPurposes(DSetConverter.convertDSetStToList(ispDTO
                    .getSecondaryPurposes()));            
        }
        if (ispDTO.getSecondaryPurposeOtherText() != null) {
            dto.setSecondaryPurposeOtherText(StConverter.convertToString(ispDTO.getSecondaryPurposeOtherText()));
        }
    }

    private void convertPhase(InterventionalStudyProtocolDTO ispDTO, ISDesignDetailsWebDTO dto) {
        if (ispDTO.getPhaseCode() != null) {
            dto.setPhaseCode(ispDTO.getPhaseCode().getCode());
        }
        if (ispDTO.getPhaseAdditionalQualifierCode() != null) {
            dto.setPhaseAdditionalQualifierCode(ispDTO.getPhaseAdditionalQualifierCode().getCode());
        }
    }

    private void convertBlindedRoleCodes(InterventionalStudyProtocolDTO ispDTO) {
        List<Cd> blindedRoleCodes =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
        for (Cd blindedRoleCode : blindedRoleCodes) {
            String codeValue = blindedRoleCode.getCode();
            if (BlindingRoleCode.CAREGIVER.getCode().equals(codeValue)) {
                this.caregiver = BlindingRoleCode.CAREGIVER.getCode();
                continue;
            }
            if (BlindingRoleCode.INVESTIGATOR.getCode().equals(codeValue)) {
                this.investigator = BlindingRoleCode.INVESTIGATOR.getCode();
                continue;
            }
            if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(codeValue)) {
                this.outcomesassessor = BlindingRoleCode.OUTCOMES_ASSESSOR.getCode();
                continue;
            }
            if (BlindingRoleCode.SUBJECT.getCode().equals(codeValue)) {
                this.subject = BlindingRoleCode.SUBJECT.getCode();
                continue;
            }
        }
    }

    /**
     * @return result
     */
    public String outcomeQuery()  {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            List<StudyOutcomeMeasureDTO> isoList = PaRegistry.getStudyOutcomeMeasurService().
            getByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {
                outcomeList = new ArrayList<ISDesignDetailsWebDTO>();
                for (StudyOutcomeMeasureDTO dto : isoList) {
                    outcomeList.add(setOutcomeMeasureDTO(dto));
                }
            } else {
                setSuccessMessageIfNotYet(
                        getText("No OutcomeMeasures exists for the trial."));
            }

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOME;
    }

    /**
     * @return result
     */
    public String outcomeinput() {
        return OUTCOMEADD;
    }

    /**
     * @return result
     */
    public String outcomecreate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO sgDTO = new StudyOutcomeMeasureDTO();
            sgDTO.setStudyProtocolIdentifier(studyProtocolIi);
            sgDTO.setName(StConverter.convertToSt(webDTO.getOutcomeMeasure().getName()));
            sgDTO.setDescription(StConverter.convertToSt(webDTO.getOutcomeMeasure().getDescription()));
            sgDTO.setTypeCode(CdConverter.convertStringToCd(webDTO.getOutcomeMeasure().getTypeCode()));
            sgDTO.setSafetyIndicator(BlConverter.convertToBl(
                    Boolean.valueOf(webDTO.getOutcomeMeasure().getSafetyIndicator())));
            sgDTO.setTimeFrame(StConverter.convertToSt(webDTO.getOutcomeMeasure().getTimeFrame()));
            PaRegistry.getStudyOutcomeMeasurService().create(sgDTO);
            outcomeQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return OUTCOME;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }
    }
    /**
     * @return result
     */
    public String outcomeedit() {
        try {
            StudyOutcomeMeasureDTO  sgDTO =
                PaRegistry.getStudyOutcomeMeasurService().get(IiConverter.convertToStudyOutcomeMeasureIi(id));
            webDTO = setOutcomeMeasureDTO(sgDTO);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOMEADD;
    }
    
    /**
     * @return result
     */
    public String outcomeCopy() {
        outcomeedit();
        setId(null);
        if (webDTO != null && webDTO.getOutcomeMeasure() != null) {
            webDTO.getOutcomeMeasure().setId(null);            
        }
        return OUTCOMEADD;
    }
    

    /**
     * @return result
     */
    public String outcomeupdate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO studyOutcomeMeasure = new StudyOutcomeMeasureDTO();
            studyOutcomeMeasure.setIdentifier(IiConverter.convertToStudyOutcomeMeasureIi(id));
            studyOutcomeMeasure.setStudyProtocolIdentifier(studyProtocolIi);
            OutcomeMeasureWebDTO webOutcomeMeasure = webDTO.getOutcomeMeasure();
            studyOutcomeMeasure.setName(StConverter.convertToSt(webOutcomeMeasure.getName()));
            studyOutcomeMeasure.setDescription(StConverter.convertToSt(webOutcomeMeasure.getDescription()));
            studyOutcomeMeasure.setTypeCode(CdConverter.convertStringToCd(webOutcomeMeasure.getTypeCode()));
            studyOutcomeMeasure.setSafetyIndicator(BlConverter.convertToBl(webOutcomeMeasure.getSafetyIndicator()));
            studyOutcomeMeasure.setTimeFrame(StConverter.convertToSt(webOutcomeMeasure.getTimeFrame()));
            studyOutcomeMeasure.setDisplayOrder(IntConverter
                    .convertToInt(webOutcomeMeasure.getDisplayOrder()));
            PaRegistry.getStudyOutcomeMeasurService().update(studyOutcomeMeasure);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            outcomeQuery();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }
        return OUTCOME;
    }

    private void enforceOutcomeBusinessRules() {
        if (webDTO.getOutcomeMeasure().getTypeCode() == null) {
            addFieldError("webDTO.outcomeMeasure.type", getText("error.outcome.type"));
        }
        addErrors(webDTO.getOutcomeMeasure().getName(), "webDTO.outcomeMeasure.name", "error.outcome.title");
        if (StringUtils.length(webDTO.getOutcomeMeasure().getName()) > MAXIMUM_CHAR_OUTCOME_NAME) {
          addFieldError("webDTO.outcomeMeasure.name", getText("error.maximumChar.254"));
        }
        if (StringUtils.length(webDTO.getOutcomeMeasure().getDescription()) > MAXIMUM_CHAR_OUTCOME_DESC) {
          addFieldError("webDTO.outcomeMeasure.description", getText("error.maximumChar.999"));
        }
        addErrors(webDTO.getOutcomeMeasure().getTimeFrame(), "webDTO.outcomeMeasure.timeFrame",
                "error.outcome.timeFrame");
        if (StringUtils.length(webDTO.getOutcomeMeasure().getTimeFrame()) > MAXIMUM_CHAR_OUTCOME_NAME) {
          addFieldError("webDTO.outcomeMeasure.timeFrame", getText("error.maximumChar.254"));
        }
        if (webDTO.getOutcomeMeasure().getSafetyIndicator() == null) {
            addFieldError("webDTO.outcomeMeasure.safetyIndicator", getText("error.outcome.safety"));
        }
    }

    /**
     * @return result
     */
    public String outcomedelete()  {
        try {
            deleteSelectedObjects();            
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return outcomeQuery();
    }
    
    /**
     * Save new outcome order.
     * 
     * @return result
     * @throws PAException PAException
     */
    public String outcomeOrder() throws PAException {
        List<String> ids = Arrays.asList(getOrderString().split(";"));
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
        studyOutcomeMeasureService.reorderOutcomes(
                studyProtocolIi, ids);
        return null;
    }
    
    @Override
    public void deleteObject(Long objectId) throws PAException {
        PaRegistry.getStudyOutcomeMeasurService().delete(IiConverter.convertToStudyOutcomeMeasureIi(objectId));      
    }

    private ISDesignDetailsWebDTO setOutcomeMeasureDTO(StudyOutcomeMeasureDTO dto) {
        ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
        if (dto != null) {
            webdto.getOutcomeMeasure().setTypeCode(CdConverter.convertCdToString(dto.getTypeCode()));
            webdto.getOutcomeMeasure().setName(StConverter.convertToString(dto.getName()));
            webdto.getOutcomeMeasure().setDescription(StConverter.convertToString(dto.getDescription()));
            webdto.getOutcomeMeasure().setTimeFrame(StConverter.convertToString(dto.getTimeFrame()));
            webdto.getOutcomeMeasure().setSafetyIndicator(BlConverter.convertToBoolean(dto.getSafetyIndicator()));
            webdto.getOutcomeMeasure().setId(IiConverter.convertToLong(dto.getIdentifier()).toString());
            webdto.getOutcomeMeasure().setDisplayOrder(IntConverter.convertToInteger(dto.getDisplayOrder()));
        }
        return webdto;
    }

    /**
     * @return webDTO
     */
    public ISDesignDetailsWebDTO getWebDTO() {
        return webDTO;
    }


    /**
     * @param webDTO webDTO
     */
    public void setWebDTO(ISDesignDetailsWebDTO webDTO) {
        this.webDTO = webDTO;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return investigator
     */
    public String getInvestigator() {
        return investigator;
    }

    /**
     * @param investigator investigator
     */
    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    /**
     * @return caregiver
     */
    public String getCaregiver() {
        return caregiver;
    }

    /**
     * @param caregiver caregiver
     */
    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    /**
     * @return outcomesassessor
     */
    public String getOutcomesassessor() {
        return outcomesassessor;
    }

    /**
     * @param outcomesassessor outcomesassessor
     */
    public void setOutcomesassessor(String outcomesassessor) {
        this.outcomesassessor = outcomesassessor;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return outcomeList
     */
    public List<ISDesignDetailsWebDTO> getOutcomeList() {
        return outcomeList;
    }

    /**
     * @param outcomeList outcomeList
     */
    public void setOutcomeList(List<ISDesignDetailsWebDTO> outcomeList) {
        this.outcomeList = outcomeList;
    }

    /**
     *
     * @return boolean value
     */
    public boolean isCaregiverChecked() {
        if (BlindingRoleCode.CAREGIVER.getCode().equals(this.caregiver)) {
            return true;
        }
        return false;
    }
    /**
     *
     * @return boolean value
     */
    public boolean isInvestigatorChecked() {
        return BlindingRoleCode.INVESTIGATOR.getCode().equals(this.investigator);
    }

    /**
     *
     * @return boolean value
     */
    public boolean isOutcomesAssessorChecked() {
        return BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(this.outcomesassessor);
    }

    /**
     *
     * @return boolean value
     */
    public boolean isSubjectChecked() {
        return BlindingRoleCode.SUBJECT.getCode().equals(this.subject);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        if (this.webDTO != null) {
            this.webDTO.setPrimaryPurposeAdditionalQualifierCode(PAUtil
                    .lookupPrimaryPurposeAdditionalQualifierCode(this.webDTO.getPrimaryPurposeCode()));
        }
        studyOutcomeMeasureService = PaRegistry.getStudyOutcomeMeasurService();
    }

    /**
     * @return the orderString
     */
    public String getOrderString() {
        return orderString;
    }

    /**
     * @param orderString the orderString to set
     */
    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(
            StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }
}
