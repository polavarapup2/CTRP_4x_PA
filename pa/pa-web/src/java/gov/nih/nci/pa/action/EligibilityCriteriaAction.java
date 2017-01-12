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

import gov.nih.nci.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.UnitOfMeasurement;
import gov.nih.nci.pa.dto.CaDSRWebDTO;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.BaseLookUpService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * @author Kalpana Guthikonda
 * @since 11/12/2008
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class EligibilityCriteriaAction extends AbstractMultiObjectDeleteAction {

    private static final Logger LOG  = Logger.getLogger(EligibilityCriteriaAction.class);
    
    private static final String STRUCTURED = "Structured";
    private static final String LAB_TEST_VALUES = "labTestNameValues";
    private static final String LAB_TEST_UOM_VALUES = "labTestUoMValues";
    private static final long serialVersionUID = -5307419735675359757L;
    private static final String ELIGIBILITY = "eligibility";
    private static final String ELIGIBILITYADD = "eligibilityAdd";
    private static final int MAXIMUM_CHAR_POPULATION = 800;
    private static final int MAXIMUM_CHAR_DESCRIPTION = 5000;
    private static final String SP = " ";
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    private Long id = null;
    private String page;
    private String acceptHealthyVolunteersIndicator;
    private String eligibleGenderCode;
    private String maximumValue;
    private String valueUnit;
    private String minimumValue;
    private String eligibleGenderCodeId = null;
    private String valueId = null;
    private List<ISDesignDetailsWebDTO> eligibilityList;
    private List<ISDesignDetailsWebDTO> list = null;
    private static final int RECORDSVALUE = 2;
    private String studyPopulationDescription;
    private String samplingMethodCode;
    private List<CaDSRWebDTO> cadsrResult = new ArrayList<CaDSRWebDTO>();
    private static List<ClassificationSchemeItem> csisResult = null;
    private DataElement cdeResult;
    private static List<String> permValues;
    private static String cdeDatatype;
    private static String cdeCategoryCode;
    private static final int TOTAL_COUNT = 3;
    private String minValueUnit;
    private String maxValueUnit;

    /**
     *
     * @return String
     */
    public String query() {
        try {
            eligibilityList = null;
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.STUDY_PROTOCOL_II);
            List<PlannedEligibilityCriterionDTO> pecList = PaRegistry.getPlannedActivityService()
                .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
            if (CollectionUtils.isNotEmpty(pecList)) {
                list = new ArrayList<ISDesignDetailsWebDTO>();
                for (PlannedEligibilityCriterionDTO dto : pecList) {
                    list.add(setEligibilityDetailsDTO(dto));
                }
                if (list.size() > RECORDSVALUE) {
                    eligibilityList = new ArrayList<ISDesignDetailsWebDTO>();
                    for (ISDesignDetailsWebDTO weblist : list) {
                        if (weblist.getCriterionName() == null
                                || !weblist.getCriterionName().equalsIgnoreCase("GENDER")
                                && !weblist.getCriterionName().equalsIgnoreCase("AGE")) {
                            eligibilityList.add(weblist);
                        }
                    }
                }
            }

            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            if (spDTO.getAcceptHealthyVolunteersIndicator().getValue() != null) {
                acceptHealthyVolunteersIndicator = spDTO.getAcceptHealthyVolunteersIndicator().getValue().toString();
            }
            StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
            if (spqDTO.getStudyProtocolType().equalsIgnoreCase("NonInterventionalStudyProtocol")) {
                NonInterventionalStudyProtocolDTO ospDTO = PaRegistry.getStudyProtocolService()
                    .getNonInterventionalStudyProtocol(studyProtocolIi);
                if (ospDTO != null) {
                    if (ospDTO.getSamplingMethodCode() != null && ospDTO.getSamplingMethodCode().getCode() != null) {
                        samplingMethodCode = ospDTO.getSamplingMethodCode().getCode().toString();
                    }
                    if (ospDTO.getStudyPopulationDescription() != null
                            && ospDTO.getStudyPopulationDescription().getValue() != null) {
                        studyPopulationDescription = ospDTO.getStudyPopulationDescription().getValue().toString();
                    }
                }
            }
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return ELIGIBILITY;
    }

    /**
     * @return res
     */
    public String save() {
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return ELIGIBILITY;
        }
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.STUDY_PROTOCOL_II);

            StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
            if (spqDTO.getStudyProtocolType().equalsIgnoreCase("NonInterventionalStudyProtocol")) {
                NonInterventionalStudyProtocolDTO ospDTO = new NonInterventionalStudyProtocolDTO();
                ospDTO = PaRegistry.getStudyProtocolService().getNonInterventionalStudyProtocol(studyProtocolIi);
                ospDTO.setStudyPopulationDescription(StConverter.convertToSt(studyPopulationDescription));
                ospDTO.setSamplingMethodCode(CdConverter.convertToCd(SamplingMethodCode.getByCode(samplingMethodCode)));
                ospDTO = PaRegistry.getStudyProtocolService().updateNonInterventionalStudyProtocol(ospDTO);
            }

            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            spDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(Boolean
                .valueOf(acceptHealthyVolunteersIndicator)));
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);

            PlannedEligibilityCriterionDTO pecDTOGender = new PlannedEligibilityCriterionDTO();
            PlannedEligibilityCriterionDTO pecDTOAge = new PlannedEligibilityCriterionDTO();

            if (eligibleGenderCode != null) {
                pecDTOGender.setStudyProtocolIdentifier(studyProtocolIi);
                pecDTOGender.setCriterionName(StConverter.convertToSt("GENDER"));
                pecDTOGender.setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode
                    .getByCode(eligibleGenderCode)));
                pecDTOGender.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
                pecDTOGender.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
                if (StringUtils.isNotEmpty(eligibleGenderCodeId)) {
                    pecDTOGender.setIdentifier(IiConverter.convertToIi(eligibleGenderCodeId));
                    PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTOGender);
                } else {
                    PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTOGender);
                }
            }
            if (minimumValue != null || maximumValue != null) {
                pecDTOAge.setStudyProtocolIdentifier(studyProtocolIi);
                pecDTOAge.setCriterionName(StConverter.convertToSt("AGE"));
                pecDTOAge.setValue(convertToIvlPq(minValueUnit, minimumValue, maxValueUnit, maximumValue));
                pecDTOAge.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
                pecDTOAge.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
                if (StringUtils.isNotEmpty(valueId)) {
                    pecDTOAge.setIdentifier(IiConverter.convertToIi(valueId));
                    PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTOAge);
                } else {
                    PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTOAge);
                }
            }
            if (StringUtils.isNotEmpty(eligibleGenderCodeId)) {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            }
            reOrder();
            query();
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return ELIGIBILITY;
    }

    private Ivl<Pq> convertToIvlPq(String minUom, String minValue, String maxUom, String maxValue) {
        if (minUom == null && minValue == null && maxValue == null && maxUom == null) {
            return null;
        }
        IvlConverter.JavaPq low = new IvlConverter.JavaPq(minUom, PAUtil.convertStringToDecimal(minValue), null);
        IvlConverter.JavaPq high = new IvlConverter.JavaPq(maxUom, PAUtil.convertStringToDecimal(maxValue), null);
        return IvlConverter.convertPq().convertToIvl(low, high);
    }

    /**
     * @return result
     */
    @Override
    public String input() {
        ServletActionContext.getRequest().getSession().setAttribute(LAB_TEST_VALUES, null);
        ServletActionContext.getRequest().getSession().setAttribute(LAB_TEST_UOM_VALUES, null);
        return ELIGIBILITYADD;
    }

    /**
     * Re order.
     *
     * @return the string
     */
    public String reOrder() {
        StringBuffer ruleError = new StringBuffer();
        HashSet<String> order = new HashSet<String>();
        try {
            if (getEligibilityList() != null) {
                for (ISDesignDetailsWebDTO dto : getEligibilityList()) {
                    ruleError.append(rulesForDisplayOrder(dto.getDisplayOrder()));
                    checkDisplayOrderExists(dto.getDisplayOrder(), Long.parseLong(dto.getId()), 
                            buildDisplayOrderUIList(), order);
                }
                if (ruleError.length() > 0) {
                    addFieldError("reOrder", ruleError.toString());
                    populateList();
                    return ELIGIBILITY;
                }
                if (!order.isEmpty()) {
                    StringBuffer orderStr = new StringBuffer("Display Order(s) exist: ");
                    orderStr.append(order.toString());
                    addFieldError("reOrder", orderStr.toString());
                } else {
                    fixDisplayOrder();
                    for (ISDesignDetailsWebDTO dto : eligibilityList) {
                        PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(dto, 
                                Long.parseLong(dto.getId()));
                        PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
                    }
                    ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                    query();
                }
            }
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return ELIGIBILITY;
    }
    
        
        /**
         * Sets the correct numbering to display orders in eligibility criteria.
         */
        private void fixDisplayOrder() {
            if (eligibilityList != null && !eligibilityList.isEmpty()) {
                Collections.sort(eligibilityList, iSDesignDetailsWebDTOComparator);
                int i = 1;
                for (ISDesignDetailsWebDTO dto : eligibilityList) {
                    dto.setDisplayOrder(String.valueOf(i));
                    i++;
                }
            }
        }
        
        /**
         * 
         */
        private static Comparator<ISDesignDetailsWebDTO> iSDesignDetailsWebDTOComparator 
            = new Comparator<ISDesignDetailsWebDTO>() {
        
            @Override
            public int compare(ISDesignDetailsWebDTO dto1, ISDesignDetailsWebDTO dto2) {       
                Integer displayOrder1 = Integer.valueOf(dto1.getDisplayOrder());
                Integer displayOrder2 = Integer.valueOf(dto2.getDisplayOrder());
                return displayOrder1.compareTo(displayOrder2);
            }    
        };
     

    /**
     * @throws PAException
     */
    private void populateList() throws PAException {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        List<PlannedEligibilityCriterionDTO> pecList = PaRegistry.getPlannedActivityService()
            .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
        if (pecList != null && !pecList.isEmpty()) {
            list = new ArrayList<ISDesignDetailsWebDTO>();
            for (PlannedEligibilityCriterionDTO dto : pecList) {
                list.add(setEligibilityDetailsDTO(dto));
            }
        }
    }

    /**
     * Generate.
     *
     * @return the string
     *
     * @throws PAException the PA exception
     */
    public String generate() throws PAException {
        StringBuffer generatedName = new StringBuffer();

        generatedName.append(appendGenName());
        generatedName.append(appendGenOp());

        if (StringUtils.isNotEmpty(webDTO.getValueIntegerMin())) {
            generatedName.append(webDTO.getValueIntegerMin());
            if (StringUtils.isNotEmpty(webDTO.getValueIntegerMax())) {
                generatedName.append(" - ").append(webDTO.getValueIntegerMax());
            }
            if (StringUtils.isNotEmpty(webDTO.getUnit())) {
                generatedName.append(SP).append(webDTO.getUnit());
            }
        } else if (StringUtils.isNotEmpty(webDTO.getValueText())) {
            generatedName.append(SP).append(webDTO.getValueText());
        }
        webDTO.setTextDescription(generatedName.toString());
        webDTO.setOperator(webDTO.getOperator());
        return ELIGIBILITYADD;
    }

    private String appendGenName() {
        StringBuffer generatedName = new StringBuffer();
        if (StringUtils.isNotEmpty(webDTO.getLabTestNameValueText())) {
            generatedName.append(webDTO.getLabTestNameValueText());
        } else if (StringUtils.isNotEmpty(webDTO.getCriterionName())) {
            generatedName.append(webDTO.getCriterionName());
        }
        return generatedName.toString();
    }

    private String appendGenOp() {
        StringBuffer generatedName = new StringBuffer();
        if (StringUtils.isNotEmpty(webDTO.getOperator()) && webDTO.getOperator().equalsIgnoreCase("In")) {
            generatedName.append(SP).append("In:");
        }
        if (StringUtils.isNotEmpty(webDTO.getOperator()) && !webDTO.getOperator().equalsIgnoreCase("In")) {
            generatedName.append(SP).append(webDTO.getOperator());
        }
        return generatedName.toString();
    }

    /**
     * @return result
     */
    public String create() {
        try {
            query();
            enforceEligibilityBusinessRules();
            if (hasFieldErrors()) {
                return ELIGIBILITYADD;
            }
            PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(webDTO, null);
            PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO);
            query();
            reOrder();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return ELIGIBILITY;
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return ELIGIBILITYADD;
        }
    }

    /**
     * @return result
     */
    public String edit() {
        ServletActionContext.getRequest().getSession().setAttribute(LAB_TEST_VALUES, null);
        ServletActionContext.getRequest().getSession().setAttribute(LAB_TEST_UOM_VALUES, null);
        try {
            PlannedEligibilityCriterionDTO sgDTO = PaRegistry.getPlannedActivityService()
                .getPlannedEligibilityCriterion(IiConverter.convertToIi(id));
            webDTO = setEligibilityDetailsDTO(sgDTO);
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return ELIGIBILITYADD;
    }

    /**
     * @return result
     */
    public String update() {
        try {
            query();
            enforceEligibilityBusinessRules();
            if (hasFieldErrors()) {
                return ELIGIBILITYADD;
            }
            PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(webDTO, id);
            PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            query();
        } catch (Exception e) {
            addActionError("There was an unexpected error saving the record.");
            LOG.error(e.getMessage(), e);
            return ELIGIBILITYADD;
        }
        return ELIGIBILITY;
    }

    /**
     * @return result
     */
    public String delete() {
        String message = null;
        try {
            deleteSelectedObjects();
            message = Constants.MULTI_DELETE_MESSAGE;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        query();
        reOrder();
        ServletActionContext.getRequest().setAttribute(
                Constants.SUCCESS_MESSAGE, message);
        return ELIGIBILITY;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {
        PaRegistry.getPlannedActivityService()
                .deletePlannedEligibilityCriterion(
                        IiConverter.convertToIi(objectId));
    }

    /**
     * Display selected type.
     *
     * @return the string
     *
     * @throws PAException the PA exception
     */
    public String displaySelectedType() throws PAException {
        String uomid = ServletActionContext.getRequest().getParameter("id");
        String className = ServletActionContext.getRequest().getParameter("className");
        String divName = ServletActionContext.getRequest().getParameter("divName");
        if (StringUtils.isNotEmpty(uomid) && "UnitOfMeasurement".equalsIgnoreCase(className)
                && divName.contains("loadUOM")) {
            BaseLookUpService<UnitOfMeasurement> lookUpService = new BaseLookUpService<UnitOfMeasurement>(
                    UnitOfMeasurement.class);
            UnitOfMeasurement uom = lookUpService.getById(Long.parseLong(uomid));
            webDTO.setUnit(uom.getCode());
        }
        return divName;
    }

    private PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(ISDesignDetailsWebDTO dtoWeb,
            Long identifier) {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
        pecDTO.setIdentifier(IiConverter.convertToIi(identifier));
        pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
        pecDTO.setCriterionName(StConverter.convertToSt(dtoWeb.getCriterionName()));
        pecDTO.setValue(convertToIvlPq(dtoWeb.getUnit(), dtoWeb.getValueIntegerMin(), dtoWeb.getUnit(),
                                       dtoWeb.getValueIntegerMax()));
        if (dtoWeb.getInclusionIndicator() == null) {
            pecDTO.setInclusionIndicator(BlConverter.convertToBl(null));
        } else if (dtoWeb.getInclusionIndicator().equalsIgnoreCase("Inclusion")) {
            pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
        pecDTO.setTextDescription(StConverter.convertToSt(dtoWeb.getTextDescription()));
        pecDTO.setOperator(StConverter.convertToSt(dtoWeb.getOperator()));
        pecDTO.setDisplayOrder(IntConverter.convertToInt(dtoWeb.getDisplayOrder()));
        pecDTO.setTextValue(StConverter.convertToSt(dtoWeb.getValueText()));
        if (dtoWeb.getStructuredType() == null) {
            pecDTO.setStructuredIndicator(BlConverter.convertToBl(null));
        } else if (dtoWeb.getStructuredType().equalsIgnoreCase(STRUCTURED)) {
            pecDTO.setStructuredIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            pecDTO.setStructuredIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        pecDTO.setCdePublicIdentifier(IiConverter.convertToIi(dtoWeb.getCdePublicIdentifier()));
        pecDTO.setCdeVersionNumber(StConverter.convertToSt(dtoWeb.getCdeVersionNumber()));
        pecDTO.setSubcategoryCode(CdConverter.convertStringToCd(dtoWeb.getCdeCategoryCode()));
        return pecDTO;
    }

    private ISDesignDetailsWebDTO setEligibilityDetailsDTO(PlannedEligibilityCriterionDTO dto) {
        ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
        if (dto != null) {
            if (dto.getEligibleGenderCode().getCode() != null) {
                eligibleGenderCode = dto.getEligibleGenderCode().getCode();
                eligibleGenderCodeId = dto.getIdentifier().getExtension();
            }
            if (dto.getCriterionName().getValue() != null && dto.getCriterionName().getValue().equals("AGE")) {
                if (dto.getValue().getHigh().getValue() != null) {
                    maximumValue = String.valueOf(dto.getValue().getHigh().getValue());
                    maxValueUnit = dto.getValue().getHigh().getUnit();
                }
                if (dto.getValue().getLow().getValue() != null) {
                    minimumValue = String.valueOf(dto.getValue().getLow().getValue());
                    minValueUnit = dto.getValue().getLow().getUnit();
                }
                valueId = dto.getIdentifier().getExtension();
            }
            if (dto.getCriterionName().getValue() != null) {
                webdto.setCriterionName(dto.getCriterionName().getValue());
            }
            if (dto.getInclusionIndicator().getValue() != null) {
                if (BlConverter.convertToBool(dto.getInclusionIndicator())) {
                    webdto.setInclusionIndicator(("Inclusion"));
                } else {
                    webdto.setInclusionIndicator("Exclusion");
                }
            }
            if (dto.getIdentifier() != null) {
                webdto.setId(dto.getIdentifier().getExtension());
            }
            if (dto.getOperator() != null) {
                webdto.setOperator(dto.getOperator().getValue());
            }
            if (dto.getTextDescription() != null) {
                webdto.setTextDescription(dto.getTextDescription().getValue());
            }
            if (dto.getValue().getLow().getValue() != null) {
                webdto.setValueIntegerMin(dto.getValue().getLow().getValue().toString());
            }
            if (dto.getValue().getHigh().getValue() != null) {
                webdto.setValueIntegerMax(dto.getValue().getHigh().getValue().toString());
            }
            if (dto.getValue().getLow().getUnit() != null) {
                webdto.setUnit(dto.getValue().getLow().getUnit());
            }
            if (dto.getDisplayOrder() != null) {
                webdto.setDisplayOrder(IntConverter.convertToString(dto.getDisplayOrder()));
            }
            if (dto.getTextValue() != null) {
                webdto.setValueText(StConverter.convertToString(dto.getTextValue()));
            }
            if (dto.getStructuredIndicator().getValue() != null) {
                if (BlConverter.convertToBool(dto.getStructuredIndicator())) {
                    webdto.setStructuredType(STRUCTURED);
                } else {
                    webdto.setStructuredType("Unstructured");
                }
            }
            if (dto.getSubcategoryCode().getCode() != null) {
                webdto.setCdeCategoryCode(dto.getSubcategoryCode().getCode());
            }
        }
        return webdto;
    }

    private void enforceBusinessRules() {
        StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.TRIAL_SUMMARY);
        if (spqDTO.getStudyProtocolType().equalsIgnoreCase("NonInterventionalStudyProtocol")) {
            if (StringUtils.isEmpty(studyPopulationDescription)) {
                addFieldError("studyPopulationDescription", getText("error.trialPopulationDescription"));
            }
            if (StringUtils.isNotEmpty(studyPopulationDescription)
                    && studyPopulationDescription.length() > MAXIMUM_CHAR_POPULATION) {
                addFieldError("studyPopulationDescription", getText("error.spType.population.maximumChar"));
            }
            if (StringUtils.isEmpty(samplingMethodCode)) {
                addFieldError("samplingMethodCode", getText("error.samplingMethod"));
            }
        }
        if (StringUtils.isEmpty(acceptHealthyVolunteersIndicator)) {
            addFieldError("acceptHealthyVolunteersIndicator", getText("error.acceptHealthyVolunteersIndicator"));
        }
        if (StringUtils.isEmpty(eligibleGenderCode)) {
            addFieldError("eligibleGenderCode", getText("error.eligibleGenderCode"));
        }
        if (StringUtils.isEmpty(maximumValue)) {
            addFieldError("maximumValue", getText("error.maximumValue"));
        }
        if (!NumberUtils.isNumber(maximumValue)) {
            addFieldError("maximumValue", getText("error.numeric"));
        }
        if (StringUtils.isNotEmpty(maximumValue) && !maximumValue.matches("\\d+(\\.0*)?")) {
            addFieldError("maximumValue", getText("error.wholenumber.maximumValue"));
        }
        if (StringUtils.isEmpty(minValueUnit)) {
            addFieldError("minValueUnit", getText("error.valueUnit"));
        }
        if (StringUtils.isEmpty(maxValueUnit)) {
            addFieldError("maxValueUnit", getText("error.valueUnit"));
        }
        String strMinVal = "minimumValue";
        if (StringUtils.isEmpty(minimumValue)) {
            addFieldError(strMinVal, getText("error.minimumValue"));
        }
        if (!NumberUtils.isNumber(minimumValue)) {
            addFieldError(strMinVal, getText("error.numeric"));
        }
        if (StringUtils.isNotEmpty(minimumValue) && !minimumValue.matches("\\d+(\\.0*)?")) {
            addFieldError(strMinVal, getText("error.wholenumber.minimumValue"));
        }
        if (StringUtils.isNotEmpty(minValueUnit) && StringUtils.isNotEmpty(maxValueUnit)
                && NumberUtils.isNumber(minimumValue) && NumberUtils.isNumber(maximumValue)) {
            double minVal = Double.parseDouble(minimumValue);
            double maxVal = Double.parseDouble(maximumValue);
            if (minValueUnit.equalsIgnoreCase(maxValueUnit) && minVal > maxVal) {
                addFieldError(strMinVal, "Minimum age should not be greater than maximum age.");
            }
            if (!PAUtil.isUnitLessOrSame(minValueUnit, maxValueUnit)) {
                addFieldError("minValueUnit", "Minimum UOM should not be greater than maximum UOM.");
            }
            if (PAUtil.isUnitLessOrSame(minValueUnit, maxValueUnit)) {
                double minValUOM = convertToMinutes(minVal, minValueUnit);
                double maxValUOM = convertToMinutes(maxVal, maxValueUnit);
                if (minValUOM > maxValUOM) {
                    addFieldError(strMinVal, "Minimum age should not be greater than maximum age.");
                }
            }
        }
    }

    private double convertToMinutes(double value, String unit) {
        double retMaxVal = 0;
        UnitsCode unitCode = UnitsCode.getByCode(unit);
        retMaxVal = unitCode.getMinuteMultiplicationFactor() * Double.valueOf(value);
        return retMaxVal;
    }

    private void enforceEligibilityBusinessRules() throws PAException {
        if (StringUtils.isEmpty(webDTO.getDisplayOrder())) {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.STUDY_PROTOCOL_II);
            int displayOrderValue = PaRegistry.getPlannedActivityService().getMaxDisplayOrderValue(studyProtocolIi);
            webDTO.setDisplayOrder(displayOrderValue + 1 + "");
        }
        if (StringUtils.isNotEmpty(webDTO.getTextDescription())
                && webDTO.getTextDescription().length() > MAXIMUM_CHAR_DESCRIPTION) {
            addFieldError("webDTO.TextDescription", getText("error.spType.description.maximumChar"));
        }        
        validateStructuredTypeRules();
        
        if (StringUtils.isNotEmpty(webDTO.getTextDescription())
                && getEligibilityList() != null) {
            for (ISDesignDetailsWebDTO detailsWebDTO : getEligibilityList()) {
                if (webDTO.getTextDescription().equals(detailsWebDTO.getTextDescription())
                        && !StringUtils.equals(webDTO.getId(), detailsWebDTO.getId())) {
                    addFieldError("webDTO.TextDescription", "Duplicate description");
                }
            }
        }
    }

    private void validateStructuredTypeRules() {
        if (StringUtils.isNotEmpty(webDTO.getStructuredType())) {
            if (STRUCTURED.equalsIgnoreCase(webDTO.getStructuredType())) {
                if (StringUtils.isEmpty(webDTO.getTextDescription()) || isBuildCriterionEmpty()) {
                    addFieldError("webDTO.buldcriterion", getText("error.mandatory"));
                }
                if (isBuildCriterionEmpty()) {
                    addFieldError("webDTO.buldcriterion", getText("error.buldcriterion"));
                }
                if (StringUtils.isNotEmpty(webDTO.getValueIntegerMax())
                        && StringUtils.isEmpty(webDTO.getValueIntegerMin())) {
                    addFieldError("webDTO.valueIntegerMin", "Minimum value must be entered");
                }
            } else if (StringUtils.isEmpty(webDTO.getTextDescription())) {
                addFieldError("webDTO.TextDescription", getText("error.unstructured.description"));
            }
        } else {
            addFieldError("webDTO.structuredType", getText("error.structuredType.mandatory"));
        }
    }

    private boolean isBuildCriterionEmpty() {
        int emptyCount = 0;
        boolean retVal = false;
        if (StringUtils.isEmpty(webDTO.getCriterionName())) {
            emptyCount++;
        }
        if (StringUtils.isEmpty(webDTO.getOperator())) {
            emptyCount++;
        }
        if (StringUtils.isEmpty(webDTO.getValueIntegerMin()) && StringUtils.isEmpty(webDTO.getValueText())) {
            emptyCount++;
        }
        if (emptyCount != 0 && emptyCount <= TOTAL_COUNT) {
            retVal = true;
        }
        return retVal;
    }

    private String rulesForDisplayOrder(String displayOrder) throws PAException {
        StringBuffer err = new StringBuffer();
        if (StringUtils.isEmpty(displayOrder)) {
            err.append("Please enter display order\n");
        }
        if (StringUtils.isNotEmpty(displayOrder)) {
            try {
                Integer.parseInt(displayOrder);
            } catch (NumberFormatException e) {
                err.append("Please enter a numeric value for display order\n");
            }
        }
        return err.toString();
    }

    private String checkDisplayOrderExists(String displayOrder, Long dtoId, Map<String, String> orderList,
            Set<String> order) throws PAException {
        boolean exists = false;

        if (MapUtils.isNotEmpty(orderList) && doesContainInList(displayOrder, dtoId, orderList)) {
            order.add(displayOrder);
            exists = true;
        }
        if (exists) {
            return order.toString();
        }
        return "";
    }

    private boolean doesContainInList(String displayOrder, Long dtoId, Map<String, String> dList) {
        boolean containsInList = false;
        ArrayList<String> valueList = new ArrayList<String>();
        if (MapUtils.isNotEmpty(dList)) {
            for (String keyId : dList.keySet()) {
                if (dtoId == null || !dtoId.toString().equals(keyId)) {
                    valueList.add(dList.get(keyId));
                }
            }
        }
        if (!valueList.isEmpty() && valueList.contains(displayOrder)) {
            containsInList = true;
        }

        return containsInList;
    }

    private Map<String, String> buildDisplayOrderUIList() throws PAException {
        Map<String, String> orderListUI = new HashMap<String, String>();
        if (getEligibilityList() != null && !getEligibilityList().isEmpty()) {
            for (ISDesignDetailsWebDTO dto : getEligibilityList()) {
                if (dto.getDisplayOrder() != null) {
                    orderListUI.put(dto.getId(), dto.getDisplayOrder());
                }
            }
        }
        return orderListUI;
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
     * @return eligibleGenderCode
     */
    public String getEligibleGenderCode() {
        return eligibleGenderCode;
    }

    /**
     * @param eligibleGenderCode eligibleGenderCode
     */
    public void setEligibleGenderCode(String eligibleGenderCode) {
        this.eligibleGenderCode = eligibleGenderCode;
    }

    /**
     * @return maximumValue
     */
    public String getMaximumValue() {
        return maximumValue;
    }

    /**
     * @param maximumValue maximumValue
     */
    public void setMaximumValue(String maximumValue) {
        this.maximumValue = maximumValue;
    }

    /**
     * @return minimumValue
     */
    public String getMinimumValue() {
        return minimumValue;
    }

    /**
     * @param minimumValue minimumValue
     */
    public void setMinimumValue(String minimumValue) {
        this.minimumValue = minimumValue;
    }

    /**
     * @return eligibleGenderCodeId
     */
    public String getEligibleGenderCodeId() {
        return eligibleGenderCodeId;
    }

    /**
     * @param eligibleGenderCodeId eligibleGenderCodeId
     */
    public void setEligibleGenderCodeId(String eligibleGenderCodeId) {
        this.eligibleGenderCodeId = eligibleGenderCodeId;
    }

    /**
     * @return acceptHealthyVolunteersIndicator
     */
    public String getAcceptHealthyVolunteersIndicator() {
        return acceptHealthyVolunteersIndicator;
    }

    /**
     * @param acceptHealthyVolunteersIndicator acceptHealthyVolunteersIndicator
     */
    public void setAcceptHealthyVolunteersIndicator(String acceptHealthyVolunteersIndicator) {
        this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
    }

    /**
     * @return eligibilityList
     */
    public List<ISDesignDetailsWebDTO> getEligibilityList() {
        return eligibilityList;
    }

    /**
     * @param eligibilityList eligibilityList
     */
    public void setEligibilityList(List<ISDesignDetailsWebDTO> eligibilityList) {
        this.eligibilityList = eligibilityList;
    }

    /**
     * @return studyPopulationDescription
     */
    public String getStudyPopulationDescription() {
        return studyPopulationDescription;
    }

    /**
     * @param studyPopulationDescription studyPopulationDescription
     */
    public void setStudyPopulationDescription(String studyPopulationDescription) {
        this.studyPopulationDescription = studyPopulationDescription;
    }

    /**
     * @return samplingMethodCode
     */
    public String getSamplingMethodCode() {
        return samplingMethodCode;
    }

    /**
     * @param samplingMethodCode samplingMethodCode
     */
    public void setSamplingMethodCode(String samplingMethodCode) {
        this.samplingMethodCode = samplingMethodCode;
    }

    /**
     * @return list
     */
    public List<ISDesignDetailsWebDTO> getList() {
        return list;
    }

    /**
     * @param list list
     */
    public void setList(List<ISDesignDetailsWebDTO> list) {
        this.list = list;
    }

    /**
     * @return the valueUnit
     */
    public String getValueUnit() {
        return valueUnit;
    }

    /**
     * @param valueUnit the valueUnit to set
     */
    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    /**
     * @return the valueId
     */
    public String getValueId() {
        return valueId;
    }

    /**
     * @param valueId the valueId to set
     */
    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    /**
     *
     * @return cadsrResult
     */
    public List<CaDSRWebDTO> getCadsrResult() {
        return cadsrResult;
    }

    /**
     *
     * @param result CaDSRWebDTO
     */
    public void setCadsrResult(List<CaDSRWebDTO> result) {
        cadsrResult = result;
    }

    /**
     *
     * @return csisResult
     */
    public List<ClassificationSchemeItem> getCsisResult() {
        return csisResult;
    }

    /**
     *
     * @return cdeResult
     */
    public DataElement getCdeResult() {
        return cdeResult;
    }

    /**
     *
     * @return permValues
     */
    public List<String> getPermValues() {
        return permValues;
    }

    /**
     *
     * @return cdeDatatype
     */
    public String getCdeDatatype() {
        return cdeDatatype;
    }

    /**
     * @return the cdeCategoryCode
     */
    public String getCdeCategoryCode() {
        return cdeCategoryCode;
    }

    /**
     * @param cdeCategoryCode the cdeCategoryCode to set
     */
    public void setCdeCategoryCode(String cdeCategoryCode) {
        EligibilityCriteriaAction.cdeCategoryCode = cdeCategoryCode;
    }

    /**
     * @return the minValueUnit
     */
    public String getMinValueUnit() {
        return minValueUnit;
    }

    /**
     * @param minValueUnit the minValueUnit to set
     */
    public void setMinValueUnit(String minValueUnit) {
        this.minValueUnit = minValueUnit;
    }

    /**
     * @return the maxValueUnit
     */
    public String getMaxValueUnit() {
        return maxValueUnit;
    }

    /**
     * @param maxValueUnit the maxValueUnit to set
     */
    public void setMaxValueUnit(String maxValueUnit) {
        this.maxValueUnit = maxValueUnit;
    }

 }
