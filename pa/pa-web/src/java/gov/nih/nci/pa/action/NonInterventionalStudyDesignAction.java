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
import gov.nih.nci.pa.dto.OSDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Kalpana Guthikonda
 * @since 10/27/2008
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class NonInterventionalStudyDesignAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -3532986378452861444L;
    private OSDesignDetailsWebDTO webDTO =  new OSDesignDetailsWebDTO();
    private static final int MAXIMUM_CHAR = 200;
    
    private static final Logger LOG = Logger
            .getLogger(NonInterventionalStudyDesignAction.class);

    /**
     * @return res
     */
    public String detailsQuery() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            NonInterventionalStudyProtocolDTO ospDTO = PaRegistry
                    .getStudyProtocolService()
                    .getNonInterventionalStudyProtocol(studyProtocolIi);
            webDTO = setDesignDetailsDTO(ospDTO);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }

    /**
     * @return res
     * @throws PAException PAException
     */
    public String updateDesign() throws PAException { //NOPMD
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        NonInterventionalStudyProtocolDTO ospFromDatabaseDTO = PaRegistry
                .getStudyProtocolService()
                .getNonInterventionalStudyProtocol(studyProtocolIi);

        enforceBusinessRules(ospFromDatabaseDTO);
        if (hasFieldErrors()) {
            return "details";
        }
        try {
            setPhase(ospFromDatabaseDTO);
            ospFromDatabaseDTO.setPrimaryPurposeCode(CdConverter
                    .convertToCd(PrimaryPurposeCode.getByCode(webDTO
                            .getPrimaryPurposeCode())));
            if (PAUtil
                    .isPrimaryPurposeCodeOther(webDTO.getPrimaryPurposeCode())) {
                ospFromDatabaseDTO
                        .setPrimaryPurposeAdditionalQualifierCode(CdConverter
                                .convertToCd(PrimaryPurposeAdditionalQualifierCode.getByCode(webDTO
                                        .getPrimaryPurposeAdditionalQualifierCode())));
            } else {
                ospFromDatabaseDTO
                        .setPrimaryPurposeAdditionalQualifierCode(CdConverter
                                .convertToCd((Lov) null));
            }
            if (PAUtil
                    .isPrimaryPurposeCodeOther(webDTO.getPrimaryPurposeCode())) {
                ospFromDatabaseDTO.setPrimaryPurposeOtherText(StConverter
                        .convertToSt(webDTO.getPrimaryPurposeOtherText()));
            } else {
                ospFromDatabaseDTO.setPrimaryPurposeOtherText(null);
            }          
            ospFromDatabaseDTO.setStudySubtypeCode(
                    CdConverter.convertToCd(StudySubtypeCode.getByCode(webDTO.getStudySubtypeCode())));
            ospFromDatabaseDTO.setExpandedAccessIndicator(BlConverter.convertToBl(webDTO.isExpandedIndicator()));
            ospFromDatabaseDTO.setStudyModelCode(
                    CdConverter.convertToCd(StudyModelCode.getByCode(webDTO.getStudyModelCode())));
            ospFromDatabaseDTO.setTimePerspectiveCode(
                    CdConverter.convertToCd(TimePerspectiveCode.getByCode(webDTO.getTimePerspectiveCode())));
            ospFromDatabaseDTO.setBiospecimenRetentionCode(
                    CdConverter.convertToCd(BiospecimenRetentionCode.getByCode(webDTO.getBiospecimenRetentionCode())));
            ospFromDatabaseDTO.setNumberOfGroups(
                    IntConverter.convertToInt(webDTO.getNumberOfGroups()));
            ospFromDatabaseDTO.setStudyModelOtherText(
                    StConverter.convertToSt(webDTO.getStudyModelOtherText()));
            ospFromDatabaseDTO.setTimePerspectiveOtherText(
                    StConverter.convertToSt(webDTO.getTimePerspectiveOtherText()));
            ospFromDatabaseDTO.setBiospecimenDescription(
                    StConverter.convertToSt(webDTO.getBiospecimenDescription()));           
            ospFromDatabaseDTO.setTargetAccrualNumber(
                    IvlConverter.convertInt().convertToIvl(webDTO.getMinimumTargetAccrualNumber(), null));
            ospFromDatabaseDTO.setFinalAccrualNumber(IntConverter.convertToInt(webDTO.getFinalAccrualNumber()));
            PaRegistry.getStudyProtocolService().updateNonInterventionalStudyProtocol(ospFromDatabaseDTO);          
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            detailsQuery();
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }
    
    /**
     * @param ispDTO
     */
    private void setPhase(NonInterventionalStudyProtocolDTO ispDTO) {
        ispDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(webDTO.getPhaseCode())));
        if (PAUtil.isPhaseCodeNA(webDTO.getPhaseCode())) {
            ispDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(
                    PhaseAdditionalQualifierCode.getByCode(webDTO.getPhaseAdditionalQualifierCode())));
        } else {
            ispDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd((Lov) null));
        }       
    }

    /**
     * @return String
     * @throws PAException
     */
    public String changeStudyType() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);            
            if (PAConstants.INTERVENTIONAL.equalsIgnoreCase(webDTO
                    .getStudyType())) {
                PaRegistry.getStudyProtocolService().changeStudyProtocolType(
                        studyProtocolIi, StudyTypeCode.INTERVENTIONAL);
                ServletActionContext.getRequest().setAttribute(
                        Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                return "isdesign";
            }
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }

    private void enforceBusinessRules(StudyProtocolDTO spDTO) {
        boolean abbr = BlConverter.convertToBool(spDTO
                .getProprietaryTrialIndicator());
        validateBaseFields(abbr);
        validatePrimaryPurpose();
        validateStudyModelCode(abbr);
        validateTimePerspective(abbr);
        validateNumberOfGroups(abbr);
        validateMinTargetAccrual(abbr);

    }

    private void validateBaseFields(boolean abbr) {
        if (!abbr) {
            addErrors(webDTO.getStudySubtypeCode(), "webDTO.studySubtypeCode",
                    "error.studytype");
        }
        addErrors(webDTO.getPhaseCode(), "webDTO.phaseCode", "error.phase");
    }

    /**
     * 
     */
    private void validatePrimaryPurpose() {
        addErrors(webDTO.getPrimaryPurposeCode(), "webDTO.primaryPurposeCode", "error.primary");
        //validate Purpose when Selected value is OTHER
        if (PAUtil.isPrimaryPurposeOtherTextReq(webDTO.getPrimaryPurposeCode(),
                 webDTO.getPrimaryPurposeAdditionalQualifierCode(), webDTO.getPrimaryPurposeOtherText())) {
             addFieldError("webDTO.primaryPurposeOtherText", getText("error.otherPurposeText"));
        }
    }
    
    private void addErrors(String fieldValue, String fieldName, String errMsg) {
        if (StringUtils.isEmpty(fieldValue)) {
            addFieldError(fieldName, getText(errMsg));
        }
     }

    private void validateStudyModelCode(boolean abbr) {
        if (StringUtils.isEmpty(webDTO.getStudyModelCode())) {
            if (!abbr) {
                addFieldError("webDTO.studyModelCode", getText("error.studyModelCode"));
            }
        } else
        if (webDTO.getStudyModelCode().equalsIgnoreCase("Other")
                && StringUtils.isEmpty(webDTO.getStudyModelOtherText())) {
            addFieldError("webDTO.studyModelOtherText", getText("error.studyModelOtherText"));
        }
        if (StringUtils.isNotEmpty(webDTO.getStudyModelOtherText())
                && webDTO.getStudyModelOtherText().length() > MAXIMUM_CHAR) {
            addFieldError("webDTO.studyModelOtherText", getText("error.spType.other.maximumChar"));
        }
    }

    private void validateMinTargetAccrual(boolean abbr) { // NOPMD
        if (StringUtils.isEmpty(webDTO.getMinimumTargetAccrualNumber())) {
            if (!abbr) {
                addFieldError("webDTO.minimumTargetAccrualNumber", getText("error.target.enrollment"));
            }
        } else {
            try {
                Integer tarAccrual = NumberUtils.createInteger(webDTO.getMinimumTargetAccrualNumber());
                if (tarAccrual != null && tarAccrual < 0) {
                    addFieldError("webDTO.minimumTargetAccrualNumber", getText("error.negative"));
                }
            } catch (NumberFormatException e) {
                    addFieldError("webDTO.minimumTargetAccrualNumber", getText("error.numeric"));
            }
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

    private void validateNumberOfGroups(boolean abbr) {
        final String groups = webDTO.getNumberOfGroups();
        if (StringUtils.isEmpty(groups)) {
            if (!abbr) {
                addFieldError("webDTO.numberOfGroups", getText("error.numberOfGroups"));
            }
        } else {
            try {
                Integer.valueOf(groups);
                if (NumberUtils.toInt(groups) < 1) {
                    addFieldError("webDTO.numberOfGroups",
                            getText("error.positiveNumOfGroups"));
                }                
            } catch (NumberFormatException e) {
                addFieldError("webDTO.numberOfGroups", getText("error.numeric"));
            }
        }
    }

    /**
     * @param abbr 
     *
     */
    private void validateTimePerspective(boolean abbr) {
        if (StringUtils.isEmpty(webDTO.getTimePerspectiveCode())) {
            if (!abbr) {
                addFieldError("webDTO.timePerspectiveCode", getText("error.timePerspectiveCode"));
            }
        } else
        if (webDTO.getTimePerspectiveCode().equalsIgnoreCase("Other")
                && StringUtils.isEmpty(webDTO.getTimePerspectiveOtherText())) {
            addFieldError("webDTO.timePerspectiveOtherText", getText("error.timePerspectiveOtherText"));
        }

        if (StringUtils.isNotEmpty(webDTO.getTimePerspectiveOtherText())
                && webDTO.getTimePerspectiveOtherText().length() > MAXIMUM_CHAR) {
            addFieldError("webDTO.timePerspectiveOtherText", getText("error.spType.other.maximumChar"));
        }
    }

    /**
     * @param ispDTO InterventionalStudyProtocolDTO
     * @return DesignDetailsWebDTO
     */
    private OSDesignDetailsWebDTO setDesignDetailsDTO(NonInterventionalStudyProtocolDTO ospDTO) {
        OSDesignDetailsWebDTO dto = new OSDesignDetailsWebDTO();
        if (ospDTO != null) {
            convertPhase(ospDTO, dto);
            convertTimePerspective(ospDTO, dto);
            convertBiospecimenFields(ospDTO, dto);
            convertGroupsCohorts(ospDTO, dto);
            convertMinTargetAccrual(ospDTO, dto);
            convertStudyModelCode(ospDTO, dto);
            convertBaseFields(ospDTO, dto);
            if (!ISOUtil.isBlNull(ospDTO.getExpandedAccessIndicator())) {
               dto.setExpandedIndicator(BlConverter.convertToBoolean(ospDTO
                   .getExpandedAccessIndicator()));
            }
        }
        return dto;
    }

    private void convertPhase(NonInterventionalStudyProtocolDTO ospDTO,
            OSDesignDetailsWebDTO dto) {
        if (ospDTO.getPhaseCode() != null) {
            dto.setPhaseCode(ospDTO.getPhaseCode().getCode());
        }
        if (ospDTO.getPhaseAdditionalQualifierCode() != null) {
            dto.setPhaseAdditionalQualifierCode(ospDTO.getPhaseAdditionalQualifierCode().getCode());
        }
        
    }

    private void convertBaseFields(NonInterventionalStudyProtocolDTO ospDTO,
            OSDesignDetailsWebDTO dto) {
        if (ospDTO.getPrimaryPurposeCode() != null) {
            dto.setPrimaryPurposeCode(ospDTO.getPrimaryPurposeCode().getCode());
        }
        if (ospDTO.getPrimaryPurposeOtherText() != null) {
            dto.setPrimaryPurposeOtherText(StConverter.convertToString(ospDTO.getPrimaryPurposeOtherText()));
        }        
        if (ospDTO.getPrimaryPurposeAdditionalQualifierCode() != null) {
            dto.setPrimaryPurposeAdditionalQualifierCode(ospDTO.getPrimaryPurposeAdditionalQualifierCode()
                                                         .getCode());
        }        
        if (ospDTO.getStudySubtypeCode() != null) {
            dto.setStudySubtypeCode(ospDTO.getStudySubtypeCode().getCode());
        }
        dto.setStudyType(PAConstants.NON_INTERVENTIONAL);
        
    }

    /**
     * @param ospDTO
     * @param dto
     */
    private void convertGroupsCohorts(NonInterventionalStudyProtocolDTO ospDTO,
            OSDesignDetailsWebDTO dto) {
        if (ospDTO.getNumberOfGroups().getValue() != null) {
            dto.setNumberOfGroups(ospDTO.getNumberOfGroups().getValue().toString());
        }
    }

    /**
     * @param ospDTO
     * @param dto
     */
    private void convertBiospecimenFields(
            NonInterventionalStudyProtocolDTO ospDTO, OSDesignDetailsWebDTO dto) {
        if (ospDTO.getBiospecimenRetentionCode() != null) {
            dto.setBiospecimenRetentionCode(ospDTO.getBiospecimenRetentionCode().getCode());
        }

        if (ospDTO.getBiospecimenDescription() != null) {
            dto.setBiospecimenDescription(ospDTO.getBiospecimenDescription().getValue());
        }
    }

    private void convertTimePerspective(NonInterventionalStudyProtocolDTO ospDTO, OSDesignDetailsWebDTO dto) {
        if (ospDTO.getTimePerspectiveCode() != null) {
            dto.setTimePerspectiveCode(ospDTO.getTimePerspectiveCode().getCode());
        }
        if (ospDTO.getTimePerspectiveOtherText() != null) {
            dto.setTimePerspectiveOtherText(ospDTO.getTimePerspectiveOtherText().getValue());
        }
    }

    private void convertMinTargetAccrual(NonInterventionalStudyProtocolDTO ospDTO, OSDesignDetailsWebDTO dto) {
        if (ospDTO.getTargetAccrualNumber() != null && ospDTO.getTargetAccrualNumber().getLow() != null
                && ospDTO.getTargetAccrualNumber().getLow().getValue() != null) {
            dto.setMinimumTargetAccrualNumber(ospDTO.getTargetAccrualNumber().getLow().getValue().toString());
        }
        if (!ISOUtil.isIntNull(ospDTO.getFinalAccrualNumber())) {
            dto.setFinalAccrualNumber(IntConverter.convertToString(ospDTO.getFinalAccrualNumber()));
        }
    }

    private void convertStudyModelCode(NonInterventionalStudyProtocolDTO ospDTO, OSDesignDetailsWebDTO dto) {
        if (ospDTO.getStudyModelCode() != null) {
            dto.setStudyModelCode(ospDTO.getStudyModelCode().getCode());
        }

        if (ospDTO.getStudyModelOtherText() != null) {
            dto.setStudyModelOtherText(ospDTO.getStudyModelOtherText().getValue());
        }
    }

    /**
     * @return webDTO
     */
    public OSDesignDetailsWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO OSDesignDetailsWebDTO
     */
    public void setWebDTO(OSDesignDetailsWebDTO webDTO) {
        this.webDTO = webDTO;
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
    }
  
}
