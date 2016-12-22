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
package gov.nih.nci.pa.action;

import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSynonymsServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 * Action class for listing/manipulating planned markers.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class PlannedMarkerAction extends AbstractListEditAction {

    private static final long serialVersionUID = 560802697544499600L;
    private static final Logger LOG = Logger
            .getLogger(PlannedMarkerAction.class);

    private ApplicationService appService;
    private PlannedMarkerServiceLocal plannedMarkerService;
    private MarkerAttributesServiceLocal markerAttributesService;
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService;
    private PlannedMarkerSynonymsServiceLocal pmSynonymService;
    private PlannedMarkerWebDTO plannedMarker = new PlannedMarkerWebDTO();
    private List<PlannedMarkerWebDTO> plannedMarkerList;
    private String cdeId;
    private boolean saveResetAttribute = false;
    private boolean saveResetMarker = false;
    private String cadsrId = "";
    private boolean pendingStatus;
    private String nciIdentifier;
    private PlannedMarkerDTO newlyCreatedMarker;

    /**
     * to compare the Attribute values with Other
     */
    protected static final String OTHER = "Other";
    /**
     * to compare the if other Text Present or not
     */
    protected static final String EMPTY = "empty";
    /**
     * to compare the if attribute Text not present
     */
    protected static final String NOTPRESENT = "notPresent";
    
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error(
                    "Error attempting to instatiate caDSR Application Service.",
                    e);
        }
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        markerAttributesService = PaRegistry.getMarkerAttributesService();
        permissibleService = PaRegistry.getPMWithCaDSRService();
        pmSynonymService = PaRegistry.getPMSynonymService();
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public String add() throws PAException {
        enforceBusinessRules();
        if (!hasFieldErrors()) {
            PlannedMarkerDTO marker = populateDTO(false);
            if (StringUtils.isNotEmpty(getPlannedMarker().getMeaning())
                    && !pendingStatus) {
                marker.setStatusCode(CdConverter
                        .convertToCd(ActiveInactivePendingCode.ACTIVE));
                Long cadsrId1 = IiConverter.convertToLong(marker.getCadsrId());
                List<Number> listofValues = permissibleService
                        .getIdentifierByCadsrId(cadsrId1);
                if (!listofValues.isEmpty()) {
                    marker.setPermissibleValue(IiConverter.convertToIi(listofValues.get(0).longValue()));
                } else {
                   permissibleService.insertValues(cadsrId1, getPlannedMarker().getName(),
                           getPlannedMarker().getMeaning(), getPlannedMarker().getDescription(), null,
                           getPlannedMarker().getPvValue(), 
                           ActiveInactivePendingCode.ACTIVE.getName());
                   List<Number> insertedPvId = permissibleService.getIdentifierByCadsrId(cadsrId1);
                   if (!insertedPvId.isEmpty()) {
                     marker.setPermissibleValue(IiConverter.convertToIi(insertedPvId.get(0).longValue()));
                   }
                }
                List<Number> listofSynValues = pmSynonymService.getIdentifierBySyncId(IiConverter
                    .convertToLong(marker.getPermissibleValue()));
                if (listofSynValues.isEmpty() && marker.getSynonymNames() != null 
                      && !marker.getSynonymNames().isEmpty()) {
                      pmSynonymService.insertValues(IiConverter.convertToLong(marker.getPermissibleValue()), 
                          marker.getSynonymNames(), ActiveInactivePendingCode.ACTIVE.getName());
                }
                pendingStatus = false;
            } else {
                marker.setStatusCode(CdConverter
                        .convertToCd(ActiveInactivePendingCode.PENDING));
                String name = StConverter.convertToString(marker.getName());
                List<Number> identifier = permissibleService
                        .getPendingIdentifierByCadsrName(name);
                if (!identifier.isEmpty()) {
                    marker.setPermissibleValue(IiConverter.convertToIi(identifier.get(0).longValue()));
                }
                // email date sent is saved. 
                if (getPlannedMarker().getDateEmailSent() != null) {
                     marker.setDateEmailSent(TsConverter.convertToTs(new Date()));
                }
                pendingStatus = true;
            }

            try {
                newlyCreatedMarker = plannedMarkerService.create(marker);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors() || hasFieldErrors()) {
            saveResetAttribute = false;
            saveResetMarker = false;
            PlannedMarkerDTO marker = populateDTO(false);
            Map<String, String> values = markerAttributesService
                    .getAllMarkerAttributes();
            plannedMarker = populateWebDTO(marker, values);
            if (getPlannedMarker().getDateEmailSent() != null) {
               plannedMarker.setDateEmailSent(new Date());
            }
            return super.create();
        }
        return currentActionType();
    }

    private String currentActionType() throws PAException {
        if (saveResetAttribute || saveResetMarker) {
            return save();     
          } else {
            return super.add();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService().get(
                IiConverter.convertToIi(getSelectedRowIdentifier()));
        Map<String, String> values = markerAttributesService
                .getAllMarkerAttributes();
        plannedMarker = populateWebDTO(marker, values);
        return super.edit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() throws PAException {
        if (getPlannedMarker().getId() != null) {
            enforceBusinessRules();
            if (!hasFieldErrors()) {
                PlannedMarkerDTO marker = populateDTO(true);
                try {
                    Long cadsrId2 = IiConverter.convertToLong(marker.getCadsrId());
                    List<Number> listofValues = permissibleService
                            .getIdentifierByCadsrId(cadsrId2);
                    if (!listofValues.isEmpty()) {
                        marker.setPermissibleValue(IiConverter.convertToIi(listofValues.get(0).longValue()));
                    } else {
                        marker.setPermissibleValue(IiConverter.convertToIi(cadsrId2));
                    }
    
                    plannedMarkerService.update(marker);
                } catch (PAException e) {
                    LOG.error(e, e);
                    addActionError(e.getMessage());
                }
            }
            if (hasActionErrors() || hasFieldErrors()) {
                PlannedMarkerDTO marker = populateDTO(false);
                Map<String, String> values = markerAttributesService
                        .getAllMarkerAttributes();
                plannedMarker = populateWebDTO(marker, values);
                return AR_EDIT;
            }
        } else {
            return add();
        }
        return currentEditActionType();
    }
    
    private String currentEditActionType() throws PAException {
        if (saveResetAttribute || saveResetMarker) {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            return super.edit();   
        } else {
            return super.update();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            return super.delete();
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return execute();
    }

    /**
     * Loads and sets the various properties from caDSR.
     * 
     * @return edit
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public String displaySelectedCDE() {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(ValueDomainPermissibleValue.class);
            criteria.add(Property.forName("id").eq(getCdeId()));
            criteria.setFetchMode("permissibleValue", FetchMode.EAGER);
            criteria.setFetchMode("permissibleValue.valueMeaning", FetchMode.EAGER);
            criteria.setFetchMode("permissibleValue.valueMeaning.designationCollection", FetchMode.EAGER);
            criteria.createAlias("permissibleValue", "pv").createAlias("pv.valueMeaning", "vm");
            List<Object> results = (List<Object>) (List<?>) appService.query(criteria);
            if (results.size() < 1) {
               throw new PAException("Search of caDSR returned no results.");
             }
            ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) results.get(0);
            ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
            PlannedMarkerWebDTO dto = new PlannedMarkerWebDTO();
            StringBuffer synonymName = new StringBuffer();
            List<Designation> alternativeNames = new ArrayList<Designation>();
            if (vm.getDesignationCollection() != null && !vm.getDesignationCollection().isEmpty()) {
                alternativeNames.addAll(vm.getDesignationCollection());
                for (Designation designation : alternativeNames) {
                    if (StringUtils.equalsIgnoreCase(designation.getType(), "Biomarker Synonym")) {
                         if (synonymName.length() == 0) {
                              synonymName.append(designation.getName());
                         } else {
                              synonymName.append("; ");
                              synonymName.append(designation.getName());
                         }
                    }
                }
            }
            dto.setSynonymNames(synonymName.toString());
            if (dto.getSynonymNames() != null && !dto.getSynonymNames().isEmpty()) {
                 dto.setName(vdpv.getPermissibleValue().getValue() + " (" + dto.getSynonymNames() + ")");
            } else {
                 dto.setName(vdpv.getPermissibleValue().getValue());
            }
            dto.setDescription(vm.getDescription());
            dto.setMeaning(vm.getLongName());
            dto.setCadsrId(vm.getPublicID());
            dto.setStatus(ActiveInactivePendingCode.ACTIVE.getCode());
            dto.setPvValue(vdpv.getPermissibleValue().getValue());
            if (dto.getCadsrId() != null) {
                cadsrId = vm.getPublicID().toString();
            }
            if (getSelectedRowIdentifier() != null) {
                dto.setId(Long.valueOf(getSelectedRowIdentifier()));
            }
            setPreSelectedAttributeValues(dto);
            setPlannedMarker(dto);
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return AR_EDIT;
    }

    private void setPreSelectedAttributeValues(PlannedMarkerWebDTO dto) throws PAException {
        Map<String, String> markerValues = markerAttributesService.getAllMarkerAttributes();
        List<String> evalTypeList = selectedTypeValues(getPlannedMarker().getEvaluationType(), markerValues,
                    BioMarkerAttributesCode.EVALUATION_TYPE.getName());
        dto.setSelectedEvaluationType(evalTypeList);   
        dto.setEvaluationTypeOtherText(getPlannedMarker().getEvaluationTypeOtherText());
        List<String> assayTypeList = selectedTypeValues(getPlannedMarker().getAssayType(), markerValues,
                    BioMarkerAttributesCode.ASSAY_TYPE.getName());
        dto.setSelectedAssayType(assayTypeList);
        dto.setAssayTypeOtherText(getPlannedMarker().getAssayTypeOtherText());
        List<String> bioPurposeTypeList = selectedTypeValues(getPlannedMarker().getAssayPurpose(), markerValues,
                    BioMarkerAttributesCode.BIOMARKER_PURPOSE.getName());
        dto.setSelectedAssayPurpose(bioPurposeTypeList);
        dto.setAssayUse(getPlannedMarker().getAssayUse());      
        List<String> specimenTypeList = selectedTypeValues(getPlannedMarker().getTissueSpecimenType(), markerValues,
                    BioMarkerAttributesCode.SPECIMEN_TYPE.getName());
        dto.setSelectedTissueSpecType(specimenTypeList);
        dto.setSpecimenTypeOtherText(getPlannedMarker().getSpecimenTypeOtherText()); 
    }
    /**
     * Reloads the planned marker screen with the requested marker name and hugo
     * code.
     * 
     * @return edit
     */
    public String displayRequestedCDE() {
        return AR_EDIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadListForm() throws PAException {
        List<PlannedMarkerWebDTO> pmList = new ArrayList<PlannedMarkerWebDTO>();
        List<PlannedMarkerDTO> results = plannedMarkerService
                .getByStudyProtocol(getSpIi());
        Map<String, String> values = markerAttributesService
                .getAllMarkerAttributes();
        for (PlannedMarkerDTO dto : results) {
            pmList.add(populateWebDTO(dto, values));
        }
        setPlannedMarkerList(pmList);
    }
    
    /**
     * 
     * @return string
     * @throws PAException PAException
     */
    public String viewSelectedProtocolMarker() throws PAException {
        try {
            Ii protocolID = IiConverter.convertToIi(getNciIdentifier()); 
            protocolID.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            StudyProtocolDTO dto = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(protocolID);
            if (dto !=  null) {
                 setSpIi(dto.getIdentifier());
                 loadListForm();
                 StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry.getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(dto.getIdentifier()));
                 ActionUtils.loadProtocolDataInSession(studyProtocolQueryDTO, new CorrelationUtils(),
                        new PAServiceUtils());  
            } 
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return AR_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (saveResetAttribute) {
            if (newlyCreatedMarker != null) {
                getPlannedMarker().setId(IiConverter.convertToLong(newlyCreatedMarker.getIdentifier()));
            }
            PlannedMarkerDTO markerDTO = populateDTO(true);
            markerDTO.setAssayUseCode(null);
            markerDTO.setAssayPurposeOtherText(null);
            markerDTO.setSpecimenTypeOtherText(null);
            markerDTO.setEvaluationTypeOtherText(null);
            plannedMarker = populateWebDTO(markerDTO, null);
            plannedMarker.setId(null);
            saveResetAttribute = false;
        } else if (saveResetMarker) {
            if (newlyCreatedMarker != null) {
                getPlannedMarker().setId(IiConverter.convertToLong(newlyCreatedMarker.getIdentifier()));
            }
            PlannedMarkerDTO markerDTO = populateDTO(true);
            markerDTO.setName(null);
            markerDTO.setLongName(null);
            markerDTO.setTextDescription(null);
            Map<String, String> values = markerAttributesService.getAllMarkerAttributes();
            plannedMarker = populateWebDTO(markerDTO, values);
            plannedMarker.setId(null);
            saveResetMarker = false;
        }
        if (plannedMarker != null && plannedMarker.getId() != null) {
            PlannedMarkerDTO markerDTO = plannedMarkerService.get(IiConverter.convertToIi(plannedMarker.getId()));
            Map<String, String> values = markerAttributesService.getAllMarkerAttributes();
            if (!ISOUtil.isIiNull(plannedMarker.getPermissibleValue())) {
                cadsrId = IiConverter.convertToLong(plannedMarker.getPermissibleValue()).toString();
            }
            plannedMarker = populateWebDTO(markerDTO, values);
            if (!cadsrId.isEmpty()) {
                plannedMarker.setCadsrId(Long.valueOf(cadsrId));
            }
        }
    }

    private void enforceBusinessRules() {
        if (StringUtils.isEmpty(getPlannedMarker().getName())) {
            addFieldError("plannedMarker.name",
                    getText("error.plannedMarker.name"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayType())) {
            addFieldError("plannedMarker.assayType",
                    getText("error.plannedMarker.assayType"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayUse())) {
            addFieldError("plannedMarker.assayUse",
                    getText("error.plannedMarker.assayUse"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayPurpose())) {
            addFieldError("plannedMarker.assayPurpose",
                    getText("error.plannedMarker.assayPurpose"));
        }

        if (StringUtils.isEmpty(getPlannedMarker().getEvaluationType())) {
            addFieldError("plannedMarker.evaluationType",
                    getText("error.plannedMarker.evaluationType"));
        }
        enforceAdditionalBusinessRules();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void enforceAdditionalBusinessRules() {

        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getAssayType(),
                        getPlannedMarker().getAssayTypeOtherText()), EMPTY)) {
            addFieldError("plannedMarker.assayTypeOtherText",
                    getText("error.plannedMarker.assayTypeOtherText"));
        }

        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getAssayPurpose(),
                        getPlannedMarker().getAssayPurposeOtherText()), EMPTY)) {
            addFieldError("plannedMarker.assayPurposeOtherTex",
                    getText("error.plannedMarker.assayPurposeOtherText"));
        }

        if (StringUtils.equals(otherTextPresent(
                        getPlannedMarker().getEvaluationType(),
                        getPlannedMarker().getEvaluationTypeOtherText()), EMPTY)) {
            addFieldError("plannedMarker.evaluationTypeOtherText",
                    getText("error.plannedMarker.evaluationTypeOtherText"));
        }

        if (StringUtils.isEmpty(getPlannedMarker().getTissueSpecimenType())) {
            addFieldError("plannedMarker.tissueSpecimenType",
                    getText("error.plannedMarker.tissueSpecimenType"));
        }
        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getTissueSpecimenType(),
                        getPlannedMarker().getSpecimenTypeOtherText()), EMPTY)) {
            addFieldError("plannedMarker.specimenTypeOtherText",
                    getText("error.plannedMarker.specimenTypeOtherText"));
        }
    }

    private String otherTextPresent(String typeValue, String otherText) {
        if (typeValue != null) {
            String[] typeList = typeValue.split(",\\s*");
            if (typeList != null) {
                for (int i = 0; i < typeList.length; i++) {
                    if (StringUtils.equals(typeList[i], OTHER)) {
                        if (StringUtils.isEmpty(otherText)) {
                            return EMPTY;
                        } else {
                            return OTHER;
                        }
                    }
                }
            }
        }
        return NOTPRESENT;
    }
    
    private List<String> getSynonymList(String synonym) {
        List<String> synonymList = new ArrayList<String>();
        if (synonym != null) {
            String[] synonyms = synonym.split(";\\s*");
            if (synonyms != null) {
                for (int i = 0; i < synonyms.length; i++) {
                     synonymList.add(synonyms[i]);
                }
            }
        }
        return synonymList;
    }

    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
    private PlannedMarkerWebDTO populateWebDTO(PlannedMarkerDTO markerDTO,
            Map<String, String> markerValues) throws PAException {
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO = new PlannedMarkerWebDTO();
        if (markerDTO != null) {
            webDTO.setId(IiConverter.convertToLong(markerDTO.getIdentifier()));
            webDTO.setName(StConverter.convertToString(markerDTO.getName()));
            webDTO.setPvValue(StConverter.convertToString(markerDTO.getPvValue()));
            if (pendingStatus) {
                webDTO.setMeaning(null);
            } else {
                webDTO.setMeaning(StConverter.convertToString(markerDTO
                        .getLongName()));
            }
            webDTO.setDescription(StConverter.convertToString(markerDTO
                    .getTextDescription()));
            webDTO.setStatus(CdConverter.convertCdToString(markerDTO
                    .getStatusCode()));
            webDTO.setHugoCode(CdConverter.convertCdToString(markerDTO
                    .getHugoBiomarkerCode()));
            StringBuffer synonymName = new StringBuffer();
            if (markerDTO.getSynonymNames() != null && !markerDTO.getSynonymNames().isEmpty()) {
             for (String synonym : markerDTO.getSynonymNames()) {
               if (synonymName.length() == 0) {
                   synonymName.append(synonym);
               } else {
                   synonymName.append("; ");
                   synonymName.append(synonym);
               }
             }
               webDTO.setSynonymNames(synonymName.toString());
            }
            if (webDTO.getStatus() != null) {
                if (webDTO.getStatus().equals(
                        ActiveInactivePendingCode.ACTIVE.getCode())) {
                    if (markerDTO.getCadsrId() != null) {
                        webDTO.setCadsrId(IiConverter.convertToLong(markerDTO
                                .getCadsrId()));
                    } else {
                        List<PlannedMarkerSyncWithCaDSRDTO> listofValues = permissibleService
                                .getValuesById(IiConverter.convertToLong(markerDTO
                                        .getPermissibleValue()));
                        webDTO.setPermissibleValue(listofValues.get(0).getCaDSRId());
                    }
                } else if (webDTO.getStatus().equals(
                        ActiveInactivePendingCode.PENDING.getCode())) {
                    webDTO.setPermissibleValue(markerDTO.getPermissibleValue());
                } else {
                    webDTO.setPermissibleValue(markerDTO.getPermissibleValue());
                }
            }
            webDTO.setFoundInHugo(StringUtils.isNotEmpty(CdConverter
                    .convertCdToString(markerDTO.getHugoBiomarkerCode())));
            webDTO.setAssayType(CdConverter.convertCdToString(markerDTO
                    .getAssayTypeCode()));
            if (markerDTO.getAssayTypeCode() != null) {
                List<String> assayTypeList = selectedTypeValues(markerDTO
                        .getAssayTypeCode().getCode(), markerValues,
                        BioMarkerAttributesCode.ASSAY_TYPE.getName());
                webDTO.setSelectedAssayType(assayTypeList);
            }
            webDTO.setAssayTypeOtherText(StConverter.convertToString(markerDTO
                    .getAssayTypeOtherText()));
            webDTO.setAssayUse(CdConverter.convertCdToString(markerDTO
                    .getAssayUseCode()));
            if (markerDTO.getAssayPurposeCode() != null) {
                List<String> assayPurposeList = selectedTypeValues(markerDTO
                        .getAssayPurposeCode().getCode(), markerValues,
                        BioMarkerAttributesCode.BIOMARKER_PURPOSE.getName());
                webDTO.setSelectedAssayPurpose(assayPurposeList);
            }
            webDTO.setAssayPurpose(CdConverter.convertCdToString(markerDTO.getAssayPurposeCode()));
            webDTO.setAssayPurposeOtherText(StConverter.convertToString(markerDTO.getAssayPurposeOtherText()));
            webDTO.setTissueSpecimenType(CdConverter.convertCdToString(markerDTO.getTissueSpecimenTypeCode()));
            if (markerDTO.getTissueSpecimenTypeCode() != null) {
                List<String> tissueSpecTypeList = selectedTypeValues(markerDTO
                        .getTissueSpecimenTypeCode().getCode(), markerValues,
                        BioMarkerAttributesCode.SPECIMEN_TYPE.getName());
                webDTO.setSelectedTissueSpecType(tissueSpecTypeList);
            }
            webDTO.setSpecimenTypeOtherText(StConverter
                    .convertToString(markerDTO.getSpecimenTypeOtherText()));
            webDTO.setEvaluationType(CdConverter.convertCdToString(markerDTO
                    .getEvaluationType()));
            if (markerDTO.getEvaluationType() != null) {
                List<String> evalTypeList = selectedTypeValues(markerDTO
                        .getEvaluationType().getCode(), markerValues,
                        BioMarkerAttributesCode.EVALUATION_TYPE.getName());
                webDTO.setSelectedEvaluationType(evalTypeList);
            }
            webDTO.setEvaluationTypeOtherText(StConverter
                    .convertToString(markerDTO.getEvaluationTypeOtherText()));
            webDTO.setDateEmailSent(TsConverter.convertToTimestamp(markerDTO.getDateEmailSent()));
        }
        return webDTO;
    }

    private List<String> selectedTypeValues(String typeValue,
            Map<String, String> markerValues, String type) {
        List<String> typeList = new ArrayList<String>();
        if (typeValue != null) {
            String[] typeSplit = typeValue.split(",\\s*");
            for (String typeValues : typeSplit) {
                for (int i = 0; markerValues != null && i < markerValues.size()
                        && markerValues.containsKey(type + i); i++) {
                    if (markerValues.get(type + i).equalsIgnoreCase(typeValues)) {
                        typeList.add(typeValues);
                        break;
                    }
                }
            }
        }
        return typeList;

    }

    private List<String> deletedTypeValues(String typeValue,
            Map<String, String> markerValues, String type) {
        List<String> typeList = new ArrayList<String>();
        List<String> valueList = new ArrayList<String>();
        if (typeValue != null) {
            for (int i = 0; markerValues != null && i < markerValues.size()
            && markerValues.containsKey(type + i); i++) {
                valueList.add(markerValues.get(type + i));
            }
            String[] typeSplit = typeValue.split(",\\s*");
            for (String typeValues : typeSplit) {
               if (!containsIgnoreCase(valueList, typeValues)) {
                        typeList.add(typeValues);
                        break;
                    }
                }   
        }
        return typeList;
    }
    
    
    private boolean containsIgnoreCase(List <String> list, String value) {
        Iterator <String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(value)) {
                 return true;
             }
        }
        return false;
   }
    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
    private PlannedMarkerDTO populateDTO(boolean isEdit) throws PAException {
        PlannedMarkerDTO marker = new PlannedMarkerDTO();
        marker.setIdentifier(IiConverter.convertToIi(getPlannedMarker().getId()));
        marker.setName(StConverter.convertToSt(getPlannedMarker().getName()));
        if (getPlannedMarker().getDateEmailSent() != null) {
          marker.setDateEmailSent(TsConverter.convertToTs(
               getPlannedMarker().getDateEmailSent()));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getMeaning()) || isEdit) {
            marker.setLongName(marker.getName());
            if (StringUtils.isEmpty(getPlannedMarker().getMeaning())) {
                pendingStatus = true; 
            }
        } else {
            marker.setLongName(StConverter.convertToSt(getPlannedMarker().getMeaning()));
        }
        marker.setTextDescription(StConverter.convertToSt(getPlannedMarker().getDescription()));
        if (getPlannedMarker().isFoundInHugo()) {
            marker.setHugoBiomarkerCode(CdConverter.convertStringToCd(getPlannedMarker().getHugoCode()));
        }
        marker.setCadsrId(IiConverter.convertToIi(getPlannedMarker().getCadsrId()));
        if (getPlannedMarker().getSynonymNames() != null && !getPlannedMarker().getSynonymNames().isEmpty()) {
           marker.setSynonymNames(getSynonymList(getPlannedMarker().getSynonymNames()));
        }
        marker.setPvValue(StConverter.convertToSt(getPlannedMarker().getPvValue()));
        if (isEdit) {
            PlannedMarkerDTO oldValue = plannedMarkerService.getPlannedMarkerWithID(getPlannedMarker().getId());
            Map<String, String> markerValues = markerAttributesService.getAllMarkerAttributes();
            if (oldValue != null) {
                if (oldValue.getPermissibleValue() != null
                        && StringUtils.equals(getPlannedMarker().getStatus(),
                                ActiveInactivePendingCode.PENDING.getCode())) {
                    Long id = IiConverter.convertToLong(oldValue.getPermissibleValue());
                    List<PlannedMarkerSyncWithCaDSRDTO> list = permissibleService.getValuesById(id);
                    if (!marker.getName().equals(list.get(0).getName())) {
                        permissibleService.updateValueById(getPlannedMarker().getName(), id);
                    }
                }
                List<String> assayTypeList = deletedTypeValues(oldValue.getAssayTypeCode().getCode(), markerValues,
                        BioMarkerAttributesCode.ASSAY_TYPE.getName());
                String assayTypeValue = "";
                if (assayTypeList != null) {
                    for (String delAssayValue : assayTypeList) {
                        assayTypeValue = delAssayValue + ", ";
                    }
                    marker.setAssayTypeCode(CdConverter.convertStringToCd(assayTypeValue
                                    + getPlannedMarker().getAssayType()));
                } else {
                    marker.setAssayTypeCode(CdConverter.convertStringToCd(getPlannedMarker()
                                    .getAssayType()));
                }
                List<String> assayUseList = deletedTypeValues(oldValue.getAssayUseCode().getCode(), markerValues,
                        BioMarkerAttributesCode.BIOMARKER_USE.getName());
                String assayUseValue = "";
                if (assayUseList != null) {
                    for (String delAssayUseValue : assayUseList) {
                        assayUseValue = delAssayUseValue + ", ";
                    }
                    marker.setAssayUseCode(CdConverter.convertStringToCd(assayUseValue
                                    + getPlannedMarker().getAssayUse()));
                } else {
                    marker.setAssayUseCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayUse()));
                }
                List<String> assayPurposeList = deletedTypeValues(oldValue.getAssayPurposeCode().getCode(), 
                        markerValues, BioMarkerAttributesCode.BIOMARKER_PURPOSE.getName());
                String assayPurposeValue = "";
                if (assayPurposeList != null) {
                    for (String delAssayPurposeValue : assayPurposeList) {
                        assayPurposeValue = delAssayPurposeValue + ", ";
                    }
                    marker.setAssayPurposeCode(CdConverter.convertStringToCd(assayPurposeValue
                                    + getPlannedMarker().getAssayPurpose()));
                } else {
                    marker.setAssayPurposeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayPurpose()));
                }
                List<String> specimenTypeList = deletedTypeValues(oldValue
                        .getTissueSpecimenTypeCode().getCode(), markerValues,
                        BioMarkerAttributesCode.SPECIMEN_TYPE.getName());
                String specimenTypeValue = "";
                if (specimenTypeList != null) {
                    for (String delSpecValue : specimenTypeList) {
                        specimenTypeValue = delSpecValue + ", ";
                    }
                    marker.setTissueSpecimenTypeCode(CdConverter.convertStringToCd(specimenTypeValue
                                    + getPlannedMarker().getTissueSpecimenType()));
                } else {
                    marker.setTissueSpecimenTypeCode(CdConverter.convertStringToCd(getPlannedMarker()
                                    .getTissueSpecimenType()));
                }
                List<String> evalTypeList = deletedTypeValues(oldValue.getEvaluationType().getCode(), markerValues,
                        BioMarkerAttributesCode.EVALUATION_TYPE.getName());
                String evalTypeValue = "";
                if (evalTypeList != null) {
                    for (String delEvalValue : evalTypeList) {
                        evalTypeValue = delEvalValue + ", ";
                    }
                    marker.setEvaluationType(CdConverter.convertStringToCd(evalTypeValue
                                    + getPlannedMarker().getEvaluationType()));
                } else {
                    marker.setEvaluationType(CdConverter.convertStringToCd(getPlannedMarker().getEvaluationType()));
                }
            } else {
                marker.setAssayTypeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayType()));
                marker.setAssayUseCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayUse()));
                marker.setAssayPurposeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayPurpose()));
                marker.setTissueSpecimenTypeCode(CdConverter.convertStringToCd(getPlannedMarker()
                                .getTissueSpecimenType()));
                marker.setEvaluationType(CdConverter.convertStringToCd(getPlannedMarker().getEvaluationType()));
            }
        } else {
            marker.setAssayTypeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayType()));
            marker.setAssayUseCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayUse()));
            marker.setAssayPurposeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayPurpose()));
            marker.setTissueSpecimenTypeCode(CdConverter.convertStringToCd(getPlannedMarker()
                            .getTissueSpecimenType()));
            marker.setEvaluationType(CdConverter.convertStringToCd(getPlannedMarker().getEvaluationType()));
        }
        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getAssayType(),
                        getPlannedMarker().getAssayTypeOtherText()), OTHER)) {
            marker.setAssayTypeOtherText(StConverter.convertToSt(getPlannedMarker().getAssayTypeOtherText()));
        }
        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getAssayPurpose(),
                        getPlannedMarker().getAssayPurposeOtherText()), OTHER)) {
            marker.setAssayPurposeOtherText(StConverter.convertToSt(getPlannedMarker().getAssayPurposeOtherText()));
        }
        if (StringUtils.equals(
                otherTextPresent(getPlannedMarker().getTissueSpecimenType(),
                        getPlannedMarker().getSpecimenTypeOtherText()), OTHER)) {
            marker.setSpecimenTypeOtherText(StConverter.convertToSt(getPlannedMarker().getSpecimenTypeOtherText()));
        }
        if (StringUtils.equals(otherTextPresent(
                        getPlannedMarker().getEvaluationType(),
                        getPlannedMarker().getEvaluationTypeOtherText()), OTHER)) {
           marker.setEvaluationTypeOtherText(StConverter.convertToSt(getPlannedMarker().getEvaluationTypeOtherText()));
        }
        marker.setStatusCode(CdConverter.convertStringToCd(getPlannedMarker().getStatus()));
        marker.setStudyProtocolIdentifier(getSpIi());
        return marker;
    }

    /**
     * @return the plannedMarker
     */
    public PlannedMarkerWebDTO getPlannedMarker() {
        return plannedMarker;
    }

    /**
     * @param plannedMarker
     *            the plannedMarker to set
     */
    public void setPlannedMarker(PlannedMarkerWebDTO plannedMarker) {
        this.plannedMarker = plannedMarker;
    }

    /**
     * @return the plannedMarkerList
     */
    public List<PlannedMarkerWebDTO> getPlannedMarkerList() {
        return plannedMarkerList;
    }

    /**
     * @param plannedMarkerList
     *            the plannedMarkerList to set
     */
    public void setPlannedMarkerList(List<PlannedMarkerWebDTO> plannedMarkerList) {
        this.plannedMarkerList = plannedMarkerList;
    }

    /**
     * @return the cdeId
     */
    public String getCdeId() {
        return cdeId;
    }

    /**
     * @param cdeId
     *            the cdeId to set
     */
    public void setCdeId(String cdeId) {
        this.cdeId = cdeId;
    }

    /**
     * @return the appService
     */
    public ApplicationService getAppService() {
        return appService;
    }

    /**
     * @param appService
     *            the appService to set
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }

    /**
     * @param plannedMarkerService
     *            the plannedMarkerService to set
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * @param markerAttributesService
     *            the markerAttributesService to set
     */
    public void setMarkerAttributesService(
            MarkerAttributesServiceLocal markerAttributesService) {
        this.markerAttributesService = markerAttributesService;
    }

    /**
     * @param permissibleService
     *            the permissibleService to set
     */
    public void setPermissibleService(
            PlannedMarkerSyncWithCaDSRServiceLocal permissibleService) {
        this.permissibleService = permissibleService;
    }
    
    /**
     * 
     * @param pmSynonymService pmSynonymService
     */
    public void setPmSynonymService(
            PlannedMarkerSynonymsServiceLocal pmSynonymService) {
        this.pmSynonymService = pmSynonymService;
    }
    /**
     * 
     * @param newlyCreatedMarker newlyCreatedMarker
     */
    public void setNewlyCreatedMarker(PlannedMarkerDTO newlyCreatedMarker) {
        this.newlyCreatedMarker = newlyCreatedMarker;
    }
    @SuppressWarnings("deprecation")
    @Override
    public void deleteObject(Long objectId) throws PAException {
        plannedMarkerService.delete(IiConverter.convertToIi(objectId));
    }

    /**
     * @return the saveResetAttribute
     */
    public boolean isSaveResetAttribute() {
        return saveResetAttribute;
    }

    /**
     * @param saveResetAttribute
     *            the saveResetAttribute to set
     */
    public void setSaveResetAttribute(boolean saveResetAttribute) {
        this.saveResetAttribute = saveResetAttribute;
    }
    /**
     * @return the saveResetMarker
     */
    public boolean isSaveResetMarker() {
        return saveResetMarker;
    }
    /**
     * @param saveResetMarker
     *            the saveResetMarker to set
     */
    public void setSaveResetMarker(boolean saveResetMarker) {
        this.saveResetMarker = saveResetMarker;
    }

    /**
     * @return the pendingStatus
     */
    public boolean isPendingStatus() {
        return pendingStatus;
    }

    /**
     * @param pendingStatus
     *            the pendingStatus to set
     */
    public void setPendingStatus(boolean pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    /**
     * @return the cadsrId
     */
    public String getCadsrId() {
        return cadsrId;
    }

    /**
     * @param cadsrId
     *            the cadsrId to set
     */
    public void setCadsrId(String cadsrId) {
        this.cadsrId = cadsrId;
    }
    /**
     * 
     * @return nciIdentifier nciIdentifier
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

    
}
