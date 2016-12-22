/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.DoseForm;
import gov.nih.nci.pa.domain.DoseFrequency;
import gov.nih.nci.pa.domain.MethodCode;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.RouteOfAdministration;
import gov.nih.nci.pa.domain.TargetSite;
import gov.nih.nci.pa.domain.UnitOfMeasurement;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.BaseLookUpService;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * @author Hugh Reinhart
 * @since 10/31/2008
 */
public final class TrialInterventionsAction extends AbstractListEditAction {

    private static final long serialVersionUID = 3643161558861866000L;

    private static final String SPACE = " ";
    private static final String COMMA_SPACE = ", ";
    private static final String DASH = "-";
    private static final String MESSAGE = "Numeric value between 0.0-999999.99";
    private static final String MIN = "0.0";
    private static final String MAX = "999999.99";

    private InterventionAlternateNameServiceLocal interventionAlternateNameService;
    private InterventionServiceLocal interventionService;
    private PlannedActivityServiceLocal plannedActivityService;

    private List<InterventionWebDTO> interventionsList;
    private String interventionIdentifier;
    private String interventionType;
    private String interventionName;
    private String interventionDescription;
    private String interventionOtherNames;
    private String selectedType;

    // DRUG & RADIATION Attributes

    private String minDoseValue;
    private String maxDoseValue;
    private String doseUOM;
    private String doseDurationValue;
    private String doseDurationUOM;
    private String doseRegimen;
    private String minDoseTotalValue;
    private String maxDoseTotalValue;
    private String doseTotalUOM;
    private String routeOfAdministration;
    private String doseForm;
    private String doseFrequency;
    private String doseFrequencyCode;

    private String approachSite;
    private String targetSite;

    // PLANNED PROCEDURE Attributes
    private String procedureName;
    
    private String orderString;
    private Integer displayOrder;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        interventionAlternateNameService = PaRegistry.getInterventionAlternateNameService();
        interventionService = PaRegistry.getInterventionService();
        plannedActivityService = PaRegistry.getPlannedActivityService();
    }

    /**
     * @return result
     * @throws PAException exception
     */
    @Override
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            return super.delete();
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());            
        }
        return execute();    
    }

    /**
     * @return result
     * @throws PAException exception
     */
    @Override
    public String add() throws PAException {
        try {
            enforceBusinessRules();
            if (isSubstance()) {
                plannedActivityService.createPlannedSubstanceAdministration(generateSubstanceIsoDto());
            } else if (isProcedure()) {
                plannedActivityService.createPlannedProcedure(generateProcedureIsoDto());
            } else {
                plannedActivityService.create(generateIsoDto());
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.add();
    }

    /**
     * @return result
     * @throws PAException exception
     */
    @Override
    public String update() throws PAException {
        try {
            enforceBusinessRules();
            if (isSubstance()) {
                PlannedSubstanceAdministrationDTO plannedDto = generateSubstanceIsoDto();
                plannedDto.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
                plannedDto.setDisplayOrder(IntConverter
                        .convertToInt(getDisplayOrder()));
                plannedActivityService.updatePlannedSubstanceAdministration(plannedDto);
            } else if (isProcedure()) {
                PlannedProcedureDTO plannedDto = generateProcedureIsoDto();
                plannedDto.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
                plannedDto.setDisplayOrder(IntConverter
                        .convertToInt(getDisplayOrder()));                
                plannedActivityService.updatePlannedProcedure(plannedDto);
            } else {
                PlannedActivityDTO pa = generateIsoDto();
                pa.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
                pa.setDisplayOrder(IntConverter
                        .convertToInt(getDisplayOrder()));                
                plannedActivityService.update(pa);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.update();
    }
    
    /**
     * Save new intervention order.
     * 
     * @return result
     * @throws PAException
     *             PAException
     */
    public String order() throws PAException {
        List<String> ids = Arrays.asList(getOrderString().split(";"));
        Ii studyProtocolIi = getSpIi();
        plannedActivityService.reorderInterventions(studyProtocolIi, ids);
        return null;
    }   

    /**
     * @return result
     * @throws PAException on error.
     */
    public String display() throws PAException {
        setInterventionIdentifier(ServletActionContext.getRequest().getParameter("interventionId"));
        if (StringUtils.isNotEmpty(getInterventionIdentifier())) {
            InterventionDTO iDto = interventionService.get(IiConverter.convertToIi(interventionIdentifier));
            setInterventionName(StConverter.convertToString(iDto.getName()));
            setInterventionOtherNames(otherNamesCSV(iDto.getIdentifier()));
            setInterventionType(CdConverter.convertCdToString(iDto.getCtGovTypeCode()));
        }
        return AR_EDIT;
    }

    /**
     * Display sub page.
     * 
     * @return the string
     * 
     * @throws PAException the PA exception
     */
    public String displaySubPage() throws PAException {
        setInterventionIdentifier(ServletActionContext.getRequest().getParameter("interventionId"));
        if (StringUtils.isNotEmpty(getInterventionIdentifier())) {
            setInterventionName(ServletActionContext.getRequest().getParameter("interventionName"));
            setInterventionOtherNames(ServletActionContext.getRequest().getParameter("interventionOtherNames"));
            setInterventionType(ServletActionContext.getRequest().getParameter("interventionType"));
        }
        return AR_EDIT;
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadListForm() throws PAException {
        setInterventionsList(new ArrayList<InterventionWebDTO>());
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(getSpIi());
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                getInterventionsList().add(generateWebDto(pa));
            }
        }
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (StringUtils.isNotEmpty(getSelectedRowIdentifier())) {
            InterventionWebDTO i = new InterventionWebDTO();
            if (getSelectedType().equals(ActivitySubcategoryCode.DRUG.getCode())
                    || getSelectedType().equals(ActivitySubcategoryCode.RADIATION.getCode())) {
                PlannedSubstanceAdministrationDTO plannedDto = plannedActivityService
                    .getPlannedSubstanceAdministration(IiConverter.convertToIi(getSelectedRowIdentifier()));
                i = generateWebDto(plannedDto);
                setDoseDurationUOM(i.getDoseDurationUOM());
                setDoseDurationValue(i.getDoseDurationValue());
                setDoseForm(i.getDoseForm());
                setDoseFrequency(i.getDoseFrequency());
                setDoseFrequencyCode(i.getDoseFrequencyCode());
                setDoseRegimen(i.getDoseRegimen());
                setDoseTotalUOM(i.getDoseTotalUOM());
                setMinDoseValue(i.getMinDoseValue());
                setMaxDoseValue(i.getMaxDoseValue());
                setMinDoseTotalValue(i.getMinDoseTotalValue());
                setMaxDoseTotalValue(i.getMaxDoseTotalValue());
                setDoseUOM(i.getDoseUOM());
                setRouteOfAdministration(i.getRouteOfAdministration());
                setInterventionType(plannedDto.getSubcategoryCode().getCode());
                setApproachSite(i.getApproachSite());
                setTargetSite(i.getTargetSite());
            } else if (getSelectedType().equals(ActivitySubcategoryCode.PROCEDURE_SURGERY.getCode())) {
                PlannedProcedureDTO plannedDto = plannedActivityService.getPlannedProcedure(IiConverter
                    .convertToIi(getSelectedRowIdentifier()));
                i = generateWebDto(plannedDto);
                setInterventionType(plannedDto.getSubcategoryCode().getCode());
                setProcedureName(i.getProcedureName());
                setTargetSite(i.getTargetSite());
            } else {
                PlannedActivityDTO paDto = plannedActivityService.get(IiConverter
                    .convertToIi(getSelectedRowIdentifier()));
                i = generateWebDto(paDto);
                setInterventionType(paDto.getSubcategoryCode().getCode());
            }
            setInterventionIdentifier(i.getIdentifier());
            setInterventionName(i.getName());
            setInterventionDescription(i.getDescription());
            setInterventionOtherNames(i.getOtherNames());
            setDisplayOrder(i.getDisplayOrder());
        }
    }

    /**
     * @return the interventionsList
     */
    public List<InterventionWebDTO> getInterventionsList() {
        return interventionsList;
    }

    /**
     * @param interventionsList the interventionsList to set
     */
    public void setInterventionsList(List<InterventionWebDTO> interventionsList) {
        this.interventionsList = interventionsList;
    }

    /**
     * @return the interventionIdentifier
     */
    public String getInterventionIdentifier() {
        return interventionIdentifier;
    }

    /**
     * @param interventionIdentifier the interventionIdentifier to set
     */
    public void setInterventionIdentifier(String interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
    }

    /**
     * @return the interventionType
     */
    public String getInterventionType() {
        return interventionType;
    }

    /**
     * @param interventionType the interventionType to set
     */
    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    /**
     * @return the interventionName
     */
    public String getInterventionName() {
        return interventionName;
    }

    /**
     * @param interventionName the interventionName to set
     */
    public void setInterventionName(String interventionName) {
        this.interventionName = interventionName;
    }

    /**
     * @return the interventionDescription
     */
    @StringLengthFieldValidator(message = "Length must be less than 5000 characters", maxLength = "5000")
    public String getInterventionDescription() {
        return interventionDescription;
    }

    /**
     * @param interventionDescription the interventionDescription to set
     */
    public void setInterventionDescription(String interventionDescription) {
        this.interventionDescription = StringUtils.left(interventionDescription,
                                                        PlannedActivity.TEXT_DESCRIPTION_LENGTH);
    }

    /**
     * @return the interventionOtherNames
     */
    public String getInterventionOtherNames() {
        return interventionOtherNames;
    }

    /**
     * @param interventionOtherNames the interventionOtherNames to set
     */
    public void setInterventionOtherNames(String interventionOtherNames) {
        this.interventionOtherNames = interventionOtherNames;
    }

    /**
     * @return result
     * @throws PAException on error.
     */
    public String displaySelectedType() throws PAException {
        String id = ServletActionContext.getRequest().getParameter("id");
        String className = ServletActionContext.getRequest().getParameter("className");
        String divName = ServletActionContext.getRequest().getParameter("divName");
        if (StringUtils.isNotEmpty(id)) {
            if ("DoseForm".equalsIgnoreCase(className)) {
                BaseLookUpService<DoseForm> lookUpService = new BaseLookUpService<DoseForm>(DoseForm.class);
                DoseForm df = lookUpService.getById(Long.parseLong(id));
                setDoseForm(df.getCode());
            } else if ("DoseFrequency".equalsIgnoreCase(className)) {
                BaseLookUpService<DoseFrequency> lookUpService = new BaseLookUpService<DoseFrequency>(
                        DoseFrequency.class);
                DoseFrequency dfreq = lookUpService.getById(Long.parseLong(id));
                setDoseFrequency(dfreq.getDisplayName());
                setDoseFrequencyCode(dfreq.getCode());
            } else if ("RouteOfAdministration".equalsIgnoreCase(className)) {
                BaseLookUpService<RouteOfAdministration> lookUpService = new BaseLookUpService<RouteOfAdministration>(
                        RouteOfAdministration.class);
                RouteOfAdministration roa = lookUpService.getById(Long.parseLong(id));
                setRouteOfAdministration(roa.getCode());
            } else if ("MethodCode".equalsIgnoreCase(className)) {
                BaseLookUpService<MethodCode> lookUpService = new BaseLookUpService<MethodCode>(MethodCode.class);
                MethodCode mc = lookUpService.getById(Long.parseLong(id));
                setProcedureName(mc.getCode());
            } else if ("UnitOfMeasurement".equalsIgnoreCase(className) && divName.contains("loadDoseUOM")) {
                BaseLookUpService<UnitOfMeasurement> lookUpService = new BaseLookUpService<UnitOfMeasurement>(
                        UnitOfMeasurement.class);
                UnitOfMeasurement uom = lookUpService.getById(Long.parseLong(id));
                setDoseUOM(uom.getCode());
            } else if ("UnitOfMeasurement".equalsIgnoreCase(className) && divName.contains("loadDoseDurationUOM")) {
                BaseLookUpService<UnitOfMeasurement> lookUpService = new BaseLookUpService<UnitOfMeasurement>(
                        UnitOfMeasurement.class);
                UnitOfMeasurement uom = lookUpService.getById(Long.parseLong(id));
                setDoseDurationUOM(uom.getCode());
            } else if ("UnitOfMeasurement".equalsIgnoreCase(className) && divName.contains("loadTotalDoseUOM")) {
                BaseLookUpService<UnitOfMeasurement> lookUpService = new BaseLookUpService<UnitOfMeasurement>(
                        UnitOfMeasurement.class);
                UnitOfMeasurement uom = lookUpService.getById(Long.parseLong(id));
                setDoseTotalUOM(uom.getCode());
            } else if ("TargetSite".equalsIgnoreCase(className) && divName.contains("TargetSite")) {
                BaseLookUpService<TargetSite> lookUpService = new BaseLookUpService<TargetSite>(TargetSite.class);
                TargetSite ts = lookUpService.getById(Long.parseLong(id));
                setTargetSite(ts.getCode());
            } else if ("TargetSite".equalsIgnoreCase(className) && divName.contains("ApproachSite")) {
                BaseLookUpService<TargetSite> lookUpService = new BaseLookUpService<TargetSite>(TargetSite.class);
                TargetSite ts = lookUpService.getById(Long.parseLong(id));
                setApproachSite(ts.getCode());
            }
        }
        return divName;
    }

    /**
     * @return the minDoseValue
     */
    @DoubleRangeFieldValidator(message = MESSAGE, minInclusive = MIN, maxInclusive = MAX)
    public String getMinDoseValue() {
        return minDoseValue;
    }

    /**
     * @param minDoseValue the minDoseValue to set
     */
    public void setMinDoseValue(String minDoseValue) {
        this.minDoseValue = minDoseValue;
    }

    /**
     * @return the maxDoseValue
     */
    @DoubleRangeFieldValidator(message = MESSAGE, minInclusive = MIN, maxInclusive = MAX)
    public String getMaxDoseValue() {
        return maxDoseValue;
    }

    /**
     * @param maxDoseValue the maxDoseValue to set
     */
    public void setMaxDoseValue(String maxDoseValue) {
        this.maxDoseValue = maxDoseValue;
    }

    /**
     * @return the doseUOM
     */
    public String getDoseUOM() {
        return doseUOM;
    }

    /**
     * @param doseUOM the doseUOM to set
     */
    public void setDoseUOM(String doseUOM) {
        this.doseUOM = doseUOM;
    }

    /**
     * @return the doseDurationValue
     */
    @DoubleRangeFieldValidator(message = MESSAGE, minInclusive = MIN, maxInclusive = MAX)
    public String getDoseDurationValue() {
        return doseDurationValue;
    }

    /**
     * @param doseDurationValue the doseDurationValue to set
     */
    public void setDoseDurationValue(String doseDurationValue) {
        this.doseDurationValue = doseDurationValue;
    }

    /**
     * @return the doseDurationUOM
     */
    public String getDoseDurationUOM() {
        return doseDurationUOM;
    }

    /**
     * @param doseDurationUOM the doseDurationUOM to set
     */
    public void setDoseDurationUOM(String doseDurationUOM) {
        this.doseDurationUOM = doseDurationUOM;
    }

    /**
     * @return the doseRegimen
     */
    @StringLengthFieldValidator(message = "Length must be less than 1000 characters", maxLength = "1000")
    public String getDoseRegimen() {
        return doseRegimen;
    }

    /**
     * @param doseRegimen the doseRegimen to set
     */
    public void setDoseRegimen(String doseRegimen) {
        this.doseRegimen = doseRegimen;
    }

    /**
     * @return the minDoseTotalValue
     */
    @DoubleRangeFieldValidator(message = MESSAGE, minInclusive = MIN, maxInclusive = MAX)
    public String getMinDoseTotalValue() {
        return minDoseTotalValue;
    }

    /**
     * @param minDoseTotalValue the minDoseTotalValue to set
     */
    public void setMinDoseTotalValue(String minDoseTotalValue) {
        this.minDoseTotalValue = minDoseTotalValue;
    }

    /**
     * @return the maxDoseTotalValue
     */
    @DoubleRangeFieldValidator(message = MESSAGE, minInclusive = MIN, maxInclusive = MAX)
    public String getMaxDoseTotalValue() {
        return maxDoseTotalValue;
    }

    /**
     * @param maxDoseTotalValue the maxDoseTotalValue to set
     */
    public void setMaxDoseTotalValue(String maxDoseTotalValue) {
        this.maxDoseTotalValue = maxDoseTotalValue;
    }

    /**
     * @return the doseTotalUOM
     */
    public String getDoseTotalUOM() {
        return doseTotalUOM;
    }

    /**
     * @param doseTotalUOM the doseTotalUOM to set
     */
    public void setDoseTotalUOM(String doseTotalUOM) {
        this.doseTotalUOM = doseTotalUOM;
    }

    /**
     * @return the routeOfAdministration
     */
    public String getRouteOfAdministration() {
        return routeOfAdministration;
    }

    /**
     * @param routeOfAdministration the routeOfAdministration to set
     */
    public void setRouteOfAdministration(String routeOfAdministration) {
        this.routeOfAdministration = routeOfAdministration;
    }

    /**
     * @return the doseForm
     */
    public String getDoseForm() {
        return doseForm;
    }

    /**
     * @param doseForm the doseForm to set
     */
    public void setDoseForm(String doseForm) {
        this.doseForm = doseForm;
    }

    /**
     * @return the doseFrequency
     */
    public String getDoseFrequency() {
        return doseFrequency;
    }

    /**
     * @param doseFrequency the doseFrequency to set
     */
    public void setDoseFrequency(String doseFrequency) {
        this.doseFrequency = doseFrequency;
    }

    /**
     * @return the procedureName
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * @param procedureName the procedureName to set
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * @return the selectedType
     */
    public String getSelectedType() {
        return selectedType;
    }

    /**
     * @param selectedType the selectedType to set
     */
    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    /**
     * @return the approachSite
     */
    public String getApproachSite() {
        return approachSite;
    }

    /**
     * @param approachSite the approachSite to set
     */
    public void setApproachSite(String approachSite) {
        this.approachSite = approachSite;
    }

    /**
     * @return the targetSite
     */
    public String getTargetSite() {
        return targetSite;
    }

    /**
     * @param targetSite the targetSite to set
     */
    public void setTargetSite(String targetSite) {
        this.targetSite = targetSite;
    }

    /**
     * Generate.
     * 
     * @return the string
     * 
     * @throws PAException the PA exception
     */
    public String generate() throws PAException {
        try {
            enforceBusinessRules();
            String description = "";
            if (isSubstance()) {
                description = convertInterventionToCSV(generateWebDto(generateSubstanceIsoDto()));
            } else if (isProcedure()) {
                description = convertInterventionToCSV(generateWebDto(generateProcedureIsoDto()));
            }
            setInterventionDescription(description);
        } catch (PAException e) {
            addActionError(e.getMessage());

        }
        return AR_EDIT;
    }

    private String convertInterventionToCSV(InterventionWebDTO interDto) {
        StringBuffer sbuf = new StringBuffer();

        if (interDto.getMinDoseValue() != null && !"".equals(interDto.getMinDoseValue())) {
            sbuf.append(interDto.getMinDoseValue());
        }
        if (interDto.getMaxDoseValue() != null && !"".equals(interDto.getMaxDoseValue())) {
            sbuf.append(DASH).append(interDto.getMaxDoseValue());
        }
        if (interDto.getDoseUOM() != null && !"".equals(interDto.getDoseUOM())) {
            sbuf.append(SPACE).append(interDto.getDoseUOM());
        }
        if (interDto.getDoseForm() != null && !"".equals(interDto.getDoseForm())) {
            sbuf.append(COMMA_SPACE);
            sbuf.append(interDto.getDoseForm());
        }
        if (interDto.getRouteOfAdministration() != null && !"".equals(interDto.getRouteOfAdministration())) {
            sbuf.append(COMMA_SPACE);
            sbuf.append(interDto.getRouteOfAdministration());
        }
        if (interDto.getDoseDurationValue() != null && !"".equals(interDto.getDoseDurationValue())) {
            sbuf.append(" for ");
            sbuf.append(interDto.getDoseDurationValue());
            if (interDto.getDoseDurationUOM() != null) {
                sbuf.append(SPACE);
                sbuf.append(interDto.getDoseDurationUOM());
            }
        }
        if (interDto.getDoseFrequency() != null && !"".equals(interDto.getDoseFrequency())) {
            sbuf.append(COMMA_SPACE);
            sbuf.append(interDto.getDoseFrequency());
        }
        if (interDto.getDoseRegimen() != null && !"".equals(interDto.getDoseRegimen())) {
            sbuf.append(COMMA_SPACE);
            sbuf.append(interDto.getDoseRegimen());
            sbuf.append(SPACE);
        }
        if (interDto.getMinDoseTotalValue() != null && !"".equals(interDto.getMinDoseTotalValue())) {
            sbuf.append(" Total dose:  ");
            sbuf.append(interDto.getMinDoseTotalValue());
            if (interDto.getMaxDoseTotalValue() != null) {
                sbuf.append(DASH).append(interDto.getMaxDoseTotalValue());
            }
            if (interDto.getDoseTotalUOM() != null) {
                sbuf.append(SPACE);
                sbuf.append(interDto.getDoseTotalUOM());
                sbuf.append(SPACE);
            }
        }
        if (interDto.getApproachSite() != null && !"".equals(interDto.getApproachSite())) {
            sbuf.append("Approach Site:  ");
            sbuf.append(interDto.getApproachSite());
            sbuf.append(SPACE);
        }
        if (interDto.getTargetSite() != null && !"".equals(interDto.getTargetSite())) {
            sbuf.append("Target Site:  ");
            sbuf.append(interDto.getTargetSite());
            sbuf.append(SPACE);
        }
        if (interDto.getProcedureName() != null && !"".equals(interDto.getProcedureName())) {
            sbuf.append("Method:  ");
            sbuf.append(interDto.getProcedureName());
        }
        return sbuf.toString();
    }

    private boolean isSubstance() {
        boolean isSubstanceType = false;
        if (getInterventionType().equals(ActivitySubcategoryCode.DRUG.getCode())
                || getInterventionType().equals(ActivitySubcategoryCode.RADIATION.getCode())) {
            isSubstanceType = true;
        }
        return isSubstanceType;
    }

    private boolean isProcedure() {
        boolean isProcedureType = false;
        if (getInterventionType().equals(ActivitySubcategoryCode.PROCEDURE_SURGERY.getCode())) {
            isProcedureType = true;
        }
        return isProcedureType;
    }

    private void enforceBusinessRules() throws PAException {
        StringBuffer sbuf = new StringBuffer();
        if (getInterventionType() == null) {
            sbuf.append("Intervention type must be set.\n");
        }
        if (StringUtils.isEmpty(getInterventionIdentifier())) {
            sbuf.append("An Intervention must be selected.");
        }
        if (getInterventionType() != null && isSubstance()) {

            if (getMinDoseValue() != null && !NumberUtils.isNumber(getMinDoseValue())) {
                sbuf.append("Please enter Numeric Min Dose Value.\n");
            }
            if (getMaxDoseValue() != null && !NumberUtils.isNumber(getMaxDoseValue())) {
                sbuf.append("Please enter Numeric Max Dose Value.\n");
            }
            if (getMaxDoseValue() != null && getMinDoseValue() == null) {
                sbuf.append("Please enter Min and Max Dose Value.\n");
            }
            if (getMaxDoseTotalValue() != null && getMinDoseTotalValue() == null) {
                sbuf.append("Please enter Min and Max Dose Total Value.\n");
            }
            if (getMaxDoseTotalValue() != null && !NumberUtils.isNumber(getMaxDoseTotalValue())) {
                sbuf.append("Please enter Numeric Max Dose Total Value.\n");
            }
            if (getMinDoseTotalValue() != null && !NumberUtils.isNumber(getMinDoseTotalValue())) {
                sbuf.append("Please enter Numeric Min Dose Total Value.\n");
            }
            if (getDoseUOM() != null && getMinDoseValue() == null) {
                sbuf.append("Please enter Min Dose Value for the UOM.\n");
            }
            if (getDoseUOM() == null && getMinDoseValue() != null) {
                sbuf.append("Please enter Dose UOM.\n");
            }
            if (getDoseTotalUOM() != null && getMinDoseTotalValue() == null) {
                sbuf.append("Please enter Min Dose Total Value for the UOM.\n");
            }
            if (getDoseTotalUOM() == null && getMinDoseTotalValue() != null) {
                sbuf.append("Please enter Dose Total UOM.\n");
            }
            if (getDoseDurationUOM() != null && getDoseDurationValue() == null) {
                sbuf.append("Please enter Dose Duration Value for the UOM.\n");
            }
            if (getDoseDurationUOM() == null && getDoseDurationValue() != null) {
                sbuf.append("Please enter Dose Duration UOM.\n");
            }
        }
        if (sbuf.toString().length() > 0) {
            throw new PAException(sbuf.toString());
        }

    }

    private PlannedActivityDTO generateIsoDto() {
        PlannedActivityDTO paDto = new PlannedActivityDTO();
        paDto.setIdentifier(null);
        paDto.setStudyProtocolIdentifier(getSpIi());
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        paDto.setInterventionIdentifier(IiConverter.convertToIi(getInterventionIdentifier()));
        paDto.setTextDescription(StConverter.convertToSt(getInterventionDescription()));
        paDto.setSubcategoryCode(CdConverter.convertStringToCd(getInterventionType()));
        return paDto;
    }

    private PlannedSubstanceAdministrationDTO generateSubstanceIsoDto() {
        PlannedSubstanceAdministrationDTO paDto = new PlannedSubstanceAdministrationDTO();
        paDto.setIdentifier(null);
        paDto.setStudyProtocolIdentifier(getSpIi());
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION));
        paDto.setInterventionIdentifier(IiConverter.convertToIi(getInterventionIdentifier()));
        paDto.setTextDescription(StConverter.convertToSt(getInterventionDescription()));
        paDto.setSubcategoryCode(CdConverter.convertStringToCd(getInterventionType()));
        paDto.setDose(convertToIvlPq(getDoseUOM(), getMinDoseValue(), getMaxDoseValue()));
        paDto.setDoseDescription(StConverter.convertToSt(getInterventionDescription()));
        paDto.setDoseDuration(convertToPq(getDoseDurationUOM(), getDoseDurationValue()));
        paDto.setDoseFormCode(CdConverter.convertStringToCd(getDoseForm()));
        paDto.setDoseFrequencyCode(CdConverter.convertStringToCd(getDoseFrequencyCode()));
        paDto.setDoseRegimen(StConverter.convertToSt(getDoseRegimen()));
        if (getInterventionType().equals(ActivitySubcategoryCode.DRUG.getCode())) {
            paDto.setRouteOfAdministrationCode(CdConverter.convertStringToCd(getRouteOfAdministration()));
        }
        paDto.setDoseTotal(convertToIvlPq(getDoseTotalUOM(), getMinDoseTotalValue(), getMaxDoseTotalValue()));
        if (getInterventionType().equals(ActivitySubcategoryCode.RADIATION.getCode())) {
            // add the target and approach site
            paDto.setApproachSiteCode(CdConverter.convertStringToCd(getApproachSite()));
            paDto.setTargetSiteCode(CdConverter.convertStringToCd(getTargetSite()));
        }
        return paDto;
    }

    private Ivl<Pq> convertToIvlPq(String uom, String minValue, String maxValue) {
        if (uom == null && minValue == null && maxValue == null) {
            return null;
        }
        IvlConverter.JavaPq low = new IvlConverter.JavaPq(uom, PAUtil.convertStringToDecimal(minValue), null);
        IvlConverter.JavaPq high = new IvlConverter.JavaPq(uom, PAUtil.convertStringToDecimal(maxValue), null);
        return IvlConverter.convertPq().convertToIvl(low, high);
    }

    private Pq convertToPq(String uom, String value) {
        if (uom == null && value == null) {
            return null;
        }
        Pq pq = new Pq();
        pq.setValue(PAUtil.convertStringToDecimal(value));
        pq.setUnit(uom);
        return pq;
    }

    private PlannedProcedureDTO generateProcedureIsoDto() {
        PlannedProcedureDTO paDto = new PlannedProcedureDTO();
        paDto.setIdentifier(null);
        paDto.setStudyProtocolIdentifier(getSpIi());
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.PLANNED_PROCEDURE));
        paDto.setInterventionIdentifier(IiConverter.convertToIi(getInterventionIdentifier()));
        paDto.setTextDescription(StConverter.convertToSt(getInterventionDescription()));
        paDto.setSubcategoryCode(CdConverter.convertStringToCd(getInterventionType()));
        paDto.setMethodCode(CdConverter.convertStringToCd(getProcedureName()));
        paDto.setTargetSiteCode(CdConverter.convertStringToCd(getTargetSite()));
        return paDto;
    }

    private InterventionWebDTO generateWebDto(PlannedActivityDTO pa) throws PAException {
        InterventionDTO i = interventionService.get(pa.getInterventionIdentifier());
        InterventionWebDTO webDto = new InterventionWebDTO();
        webDto.setIdentifier(IiConverter.convertToString(pa.getInterventionIdentifier()));
        webDto.setPlannedActivityIdentifier(IiConverter.convertToString(pa.getIdentifier()));
        webDto.setOtherNames(otherNamesCSV(i.getIdentifier()));
        webDto.setDescription(StConverter.convertToString(pa.getTextDescription()));
        webDto.setName(StConverter.convertToString(i.getName()));
        webDto.setType(CdConverter.convertCdToString(pa.getSubcategoryCode()));
        webDto.setCtGovType(CdConverter.convertCdToString(i.getCtGovTypeCode()));
        webDto.setDisplayOrder(IntConverter.convertToInteger(pa.getDisplayOrder()));
        return webDto;
    }

    private InterventionWebDTO generateWebDto(PlannedProcedureDTO pa) throws PAException {
        InterventionDTO i = interventionService.get(pa.getInterventionIdentifier());
        InterventionWebDTO webDto = new InterventionWebDTO();
        webDto.setIdentifier(IiConverter.convertToString(pa.getInterventionIdentifier()));
        webDto.setPlannedActivityIdentifier(IiConverter.convertToString(pa.getIdentifier()));
        webDto.setOtherNames(otherNamesCSV(i.getIdentifier()));
        webDto.setDescription(StConverter.convertToString(pa.getTextDescription()));
        webDto.setName(StConverter.convertToString(i.getName()));
        webDto.setType(CdConverter.convertCdToString(pa.getSubcategoryCode()));
        webDto.setCtGovType(CdConverter.convertCdToString(i.getCtGovTypeCode()));
        webDto.setProcedureName(!ISOUtil.isCdNull(pa.getMethodCode()) ? pa.getMethodCode().getCode() : "");
        webDto.setTargetSite(!ISOUtil.isCdNull(pa.getTargetSiteCode()) ? pa.getTargetSiteCode().getCode() : "");
        webDto.setDisplayOrder(IntConverter.convertToInteger(pa.getDisplayOrder()));
        return webDto;
    }

    private InterventionWebDTO generateWebDto(PlannedSubstanceAdministrationDTO pa) throws PAException {
        InterventionWebDTO webDto = new InterventionWebDTO();
        if (pa != null && pa.getInterventionIdentifier() != null) {
            InterventionDTO i = interventionService.get(pa.getInterventionIdentifier());
            webDto.setIdentifier(IiConverter.convertToString(pa.getInterventionIdentifier()));
            webDto.setPlannedActivityIdentifier(IiConverter.convertToString(pa.getIdentifier()));
            webDto.setOtherNames(otherNamesCSV(i.getIdentifier()));
            webDto.setDescription(StConverter.convertToString(pa.getTextDescription()));
            webDto.setName(StConverter.convertToString(i.getName()));
            webDto.setType(CdConverter.convertCdToString(pa.getSubcategoryCode()));
            webDto.setCtGovType(CdConverter.convertCdToString(i.getCtGovTypeCode()));
            webDto
                .setDoseDurationUOM(!ISOUtil.isPqUnitNull(pa.getDoseDuration()) ? pa.getDoseDuration().getUnit() : "");
            webDto.setDoseDurationValue(!ISOUtil.isPqValueNull(pa.getDoseDuration()) ? pa.getDoseDuration().getValue()
                .toString() : "");
            webDto.setDoseForm(!ISOUtil.isCdNull(pa.getDoseFormCode()) ? pa.getDoseFormCode().getCode() : "");
            webDto.setDoseFrequency(!ISOUtil.isCdNull(pa.getDoseFrequencyCode()) ? getDoseFreq(pa
                .getDoseFrequencyCode().getCode()) : "");
            webDto.setDoseFrequencyCode(!ISOUtil.isCdNull(pa.getDoseFrequencyCode()) ? pa.getDoseFrequencyCode()
                .getCode() : "");
            webDto.setDoseRegimen(!ISOUtil.isStNull(pa.getDoseRegimen()) ? pa.getDoseRegimen().getValue() : "");

            webDto.setMinDoseValue(!ISOUtil.isIvlLowNull(pa.getDose()) ? pa.getDose().getLow().getValue().toString()
                    : "");
            webDto.setMaxDoseValue(!ISOUtil.isIvlHighNull(pa.getDose()) ? pa.getDose().getHigh().getValue().toString()
                    : "");
            webDto.setDoseUOM(!ISOUtil.isIvlUnitNull(pa.getDose()) ? IvlConverter.convertPq()
                .convertLowToJavaPq(pa.getDose()).getUnit() : "");

            webDto.setDoseTotalUOM(!ISOUtil.isIvlUnitNull(pa.getDoseTotal()) ? IvlConverter.convertPq()
                .convertLowToJavaPq(pa.getDoseTotal()).getUnit() : "");
            webDto.setMinDoseTotalValue(!ISOUtil.isIvlLowNull(pa.getDoseTotal()) ? pa.getDoseTotal().getLow()
                .getValue().toString() : "");
            webDto.setMaxDoseTotalValue(!ISOUtil.isIvlHighNull(pa.getDoseTotal()) ? pa.getDoseTotal().getHigh()
                .getValue().toString() : "");
            webDto.setRouteOfAdministration(!ISOUtil.isCdNull(pa.getRouteOfAdministrationCode()) ? pa
                .getRouteOfAdministrationCode().getCode() : "");
            webDto.setApproachSite(!ISOUtil.isCdNull(pa.getApproachSiteCode()) ? pa.getApproachSiteCode().getCode()
                    : "");
            webDto.setTargetSite(!ISOUtil.isCdNull(pa.getTargetSiteCode()) ? pa.getTargetSiteCode().getCode() : "");
            webDto.setDisplayOrder(IntConverter.convertToInteger(pa.getDisplayOrder()));
        }
        return webDto;
    }

    private String otherNamesCSV(Ii interventionIi) throws PAException {
        List<InterventionAlternateNameDTO> ians = interventionAlternateNameService.getByIntervention(interventionIi);
        StringBuffer onBuff = new StringBuffer("");
        for (InterventionAlternateNameDTO ian : ians) {
            if (ians.get(0) != ian && !StringUtils.contains(onBuff.toString(), ian.getName().getValue())) {
                onBuff.append(", ");           
            }
            if (!StringUtils.contains(onBuff.toString(), ian.getName().getValue())) {
                onBuff.append(StConverter.convertToString(ian.getName()));
            }   
        }
        return onBuff.toString();
    }

    private String getDoseFreq(String doseFreqCode) throws PAException {
        String doseFreqDisplayName = "";
        BaseLookUpService<DoseFrequency> lookUpService = new BaseLookUpService<DoseFrequency>(DoseFrequency.class);
        DoseFrequency example = new DoseFrequency();
        example.setCode(doseFreqCode);
        List<DoseFrequency> dfreqs = lookUpService.search(new AnnotatedBeanSearchCriteria<DoseFrequency>(example));
        if (CollectionUtils.isNotEmpty(dfreqs)) {
            doseFreqDisplayName = dfreqs.get(0).getDisplayName();
        }
        return doseFreqDisplayName;
    }

    /**
     * @return the doseFrequencyCode
     */
    public String getDoseFrequencyCode() {
        return doseFrequencyCode;
    }

    /**
     * @param doseFrequencyCode the doseFrequencyCode to set
     */
    public void setDoseFrequencyCode(String doseFrequencyCode) {
        this.doseFrequencyCode = doseFrequencyCode;
    }

    /**
     * @param interventionAltNameSvc the interventionAlternateNameService to set
     */
    public void setInterventionAlternateNameService(InterventionAlternateNameServiceLocal interventionAltNameSvc) {
        this.interventionAlternateNameService = interventionAltNameSvc;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {
        plannedActivityService.delete(IiConverter.convertToIi(objectId));
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
     * @return the displayOrder
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }


}
