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

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.dto.CaDSRWebDTO;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSynonymsServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Property;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Gaurav Gupta
 *
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity",  
      "PMD.TooManyMethods", "PMD.TooManyFields" })
public class BioMarkersQueryAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -2137469104765932059L;
    /**
     * to view edit marker name page.
     */
    protected static final String MARKER_EDIT = "edit";
    /**
     * to submit marker question.
     */
    protected static final String MARKER_QUESTION = "question";
    private PlannedMarkerServiceLocal plannedMarkerService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private List<PlannedMarkerDTO> plannedMarkers;
    private List<PlannedMarkerWebDTO> plannedMarkerList;
    private PlannedMarkerWebDTO plannedMarker = new PlannedMarkerWebDTO();
    private StudyProtocolService studyProtocolService;
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService;
    private LookUpTableServiceRemote lookUpTableService;
    private PlannedMarkerSynonymsServiceLocal pmSynonymService;
    private ApplicationService appService;
    private String selectedRowIdentifier;
    private String selectedRowDocument;
    private String trialId;
    private String markerName;
    private Long id = null;
    private String caDsrId;
    
    private static final Logger LOG = Logger.getLogger(BioMarkersQueryAction.class);
    
    @Override
    public void prepare() {
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        permissibleService = PaRegistry.getPMWithCaDSRService();
        pmSynonymService = PaRegistry.getPMSynonymService();
        lookUpTableService = PaRegistry.getLookUpTableService();
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error("Error attempting to instantiate caDSR Application Service.", e);
        } 
    }

    @Override
    public String execute() throws PAException {
        List<PlannedMarkerWebDTO> pmList = new ArrayList<PlannedMarkerWebDTO>();
        List<Long> identifiersList = new ArrayList<Long>();
        plannedMarkers = plannedMarkerService.getPlannedMarkers();
        for (PlannedMarkerDTO dto : plannedMarkers) {
            identifiersList.add(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }
        Map<Long, String> identifierMap = studyProtocolService.getTrialNciId(identifiersList);
        Map<Long, String> trialStatusList = studyProtocolService.getTrialProcessingStatus(identifiersList);
        Map<Long, DocumentDTO> trialProtocolDocumentList = PaRegistry.getDocumentService()
                .getDocumentByIDListAndType(identifiersList, DocumentTypeCode.PROTOCOL_DOCUMENT);
        for (PlannedMarkerDTO dto : plannedMarkers) {
            if (dto.getUserLastCreated() != null 
                    && !StringUtils.equalsIgnoreCase("REJECTED", trialStatusList
                            .get(IiConverter.convertToLong(dto.getStudyProtocolIdentifier())))
                    && !StringUtils.endsWithIgnoreCase("SUBMISSION_TERMINATED", trialStatusList
                            .get(IiConverter.convertToLong(dto.getStudyProtocolIdentifier())))
                    && identifierMap.containsKey(IiConverter
                            .convertToLong(dto.getStudyProtocolIdentifier()))) {
                    pmList.add(populateWebDTO(dto, identifierMap, trialStatusList, trialProtocolDocumentList));
            }         
        } 
        if (CollectionUtils.isNotEmpty(pmList)) {
            Collections.sort(pmList, new Comparator<PlannedMarkerWebDTO>() {
                public int compare(PlannedMarkerWebDTO o1, PlannedMarkerWebDTO o2) {
                    return o2.getCreationDate().compareTo(
                           o1.getCreationDate());
                }
            });              
        }
        setPlannedMarkerList(pmList);
        return SUCCESS;
    }

    /**
     * 
     * @return string marker edit jsp
     * @throws PAException exception
     */
    public String edit() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        plannedMarker = populateWebDTO(marker, null, null, null);
        return MARKER_EDIT;
    }
    /**
     * 
     * @return string marker Success jsp
     * @throws PAException exception
     */
    public String search() throws PAException {
        List<PlannedMarkerWebDTO> pmList = new ArrayList<PlannedMarkerWebDTO>();
        List<PlannedMarkerDTO> markers = new ArrayList<PlannedMarkerDTO>();
        String nciIdentifier = getTrialId();
        String markerNames =  getMarkerName();
        if (StringUtils.isEmpty(nciIdentifier) && StringUtils.isEmpty(markerNames)) {
            execute();
        } else {
            List<Long> identifiersList = new ArrayList<Long>();
            
            markers = plannedMarkerService
                   .getPendingPlannedMarkersShortNameAndNCIId(markerNames, nciIdentifier);

            if (!markers.isEmpty()) {
                for (PlannedMarkerDTO dto : markers) {
                    identifiersList.add(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
                }
                Map<Long, String> identifierMap = studyProtocolService.getTrialNciId(identifiersList);
                Map<Long, String> trialStatusList = studyProtocolService.getTrialProcessingStatus(identifiersList);
                Map<Long, DocumentDTO> trialProtocolDocumentList = PaRegistry.getDocumentService()
                        .getDocumentByIDListAndType(identifiersList, DocumentTypeCode.PROTOCOL_DOCUMENT);
                for (PlannedMarkerDTO dto : markers) {
                    if (dto.getUserLastCreated() != null 
                            && !StringUtils.equalsIgnoreCase("REJECTED", trialStatusList
                                    .get(IiConverter.convertToLong(dto.getStudyProtocolIdentifier())))
                            && !StringUtils.endsWithIgnoreCase("SUBMISSION_TERMINATED", trialStatusList
                              .get(IiConverter.convertToLong(dto.getStudyProtocolIdentifier())))
                            && identifierMap.containsKey(IiConverter
                                    .convertToLong(dto.getStudyProtocolIdentifier()))) {
                            pmList.add(populateWebDTO(dto, identifierMap, trialStatusList
                                  , trialProtocolDocumentList));    
                    }         
                }
                if (CollectionUtils.isNotEmpty(pmList)) {
                    Collections.sort(pmList, new Comparator<PlannedMarkerWebDTO>() {
                        public int compare(PlannedMarkerWebDTO o1, PlannedMarkerWebDTO o2) {
                            return o2.getCreationDate().compareTo(
                                   o1.getCreationDate());
                        }
                    });              
                }
                setPlannedMarkerList(pmList);
            }
        } 
        return SUCCESS;
    }
    /**
     * 
     * @return string submit marker question jsp
     * @throws PAException exception
     */
    public String sendQuestion() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        plannedMarker = populateWebDTO(marker, null, null, null);
        try {
            String emailId = PaRegistry.getMailManagerService().getMarkerEmailAddress(marker);
            plannedMarker.setCsmUserEmailId(emailId);
            if (StringUtils.isBlank(emailId)) {
                addActionError(getText("error.plannedMarker.question"));
            }
        } catch (PAException e) {
            LOG.error(e, e);
            addActionError(e.getMessage());
        }
        return MARKER_QUESTION;
    }

    /**
     * 
     * @return string marker view jsp
     * @throws PAException exception
     */
    public String sendQuestionMail() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(plannedMarker.getId()));
        PlannedMarkerWebDTO webDTO = populateWebDTO(marker, null, null, null);
        try {
            PaRegistry.getMailManagerService().sendMarkerQuestionToCTROMail(webDTO.getNciIdentifier(),
                    webDTO.getCsmUserEmailId(), marker, plannedMarker.getQuestion());
        } catch (PAException e) {
            LOG.error(e, e);
            addActionError(e.getMessage());
        }
        return execute();
    }

    /**
     * Updates the marker name and change status to ACTIVE.
     * @return string
     * @throws PAException exception
     */
    public String update() throws PAException {
            PlannedMarkerDTO marker = plannedMarkerService.get(IiConverter.convertToIi(getPlannedMarker().getId()));
            PlannedMarkerWebDTO webDTO = populateWebDTO(marker, null, null, null);
            
         // check the value with the planned_marker_sync_cadsr table  as this table is in sync with the cadsr. 
            List<PlannedMarkerDTO> markerDTOs = plannedMarkerService
            .getPendingPlannedMarkersWithName(getPlannedMarker().getName());
            List<PlannedMarkerSyncWithCaDSRDTO> acceptValues = 
                permissibleService.getValuesByName(getPlannedMarker().getName());
            if (!acceptValues.isEmpty()) {
                if (StringUtils.equals(acceptValues.get(0).getStatusCode().toString(), 
                        ActiveInactivePendingCode.ACTIVE.getName())) { 
                for (PlannedMarkerDTO markerDTO : markerDTOs) {
                    markerDTO.setLongName(StConverter.convertToSt(plannedMarker.getName()));
                    markerDTO.setName(StConverter.convertToSt(plannedMarker.getName()));
                    markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    markerDTO.setPermissibleValue(IiConverter
                            .convertToIi(Long.valueOf(acceptValues.get(0).getIdentifier().toString())));
                   
                    if (markerDTO.getIdentifier().getExtension().equals(marker.getIdentifier().getExtension())) {
                        marker = markerDTO;
                    }
                }
                } else {
                    addActionError("An exact match in caDSR could not be found. Please check" 
                            + " the permissible value name. Unable to update new Biomarker."); 
                }
            }
            if (!hasActionErrors()) {
                try {                                
                    PaRegistry.getMailManagerService().sendMarkerAcceptanceMailToCDE(
                            webDTO.getNciIdentifier(), webDTO.getCsmUserEmailId(), marker);           
                } catch (PAException e) {
                    LOG.error(e, e);
                    addActionError(e.getMessage());
                }
            }
            
        return execute();
    }

    /**
     * Changes marker status to ACTIVE.
     * @return string
     * @throws PAException exception
     * @throws ApplicationException 
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public String accept() throws PAException, ApplicationException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        String newcaDsrId = getCaDsrId();
        Long oldPvId = IiConverter.convertToLong(marker.getPermissibleValue());
        List<PlannedMarkerDTO> markerDTOs = plannedMarkerService.getPendingPlannedMarkerWithSyncID(oldPvId);
        // already present in planned_marker_sync_cadsr table?
        List<Number> newPvId = permissibleService.getIdentifierByCadsrId(Long.parseLong(newcaDsrId));
        if (!newPvId.isEmpty()) { 
            // update all pending markers with the new pv value and make them active
            for (PlannedMarkerDTO markerDTO : markerDTOs) {
                markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                markerDTO.setPermissibleValue(IiConverter.convertToIi(newPvId.get(0).longValue()));
                plannedMarkerService.update(markerDTO);
            }
            // delete the pending marker now which is just an extra,
            permissibleService.deleteById(oldPvId); // pending one need to check while debug
        } else {
            // Insert the new Cadsr value and update the marker with the insert PV value. and delete the old value 
            List<Object> permissibleValues = caDsrLookUp(newcaDsrId);
            if (!permissibleValues.isEmpty()) {
                  CaDSRWebDTO caDsrdto = getSearchResults(permissibleValues.get(0));
                  permissibleService.insertValues(caDsrdto.getPublicId(), caDsrdto.getVmName()
                    , caDsrdto.getVmMeaning(), caDsrdto.getVmDescription(), null
                    , caDsrdto.getPvValue(), ActiveInactivePendingCode.ACTIVE.getName());
               List<Number> insertedPvId = permissibleService.getIdentifierByCadsrId(Long.parseLong(newcaDsrId));
               if (caDsrdto.getAltNames() != null && !caDsrdto.getAltNames().isEmpty()) {
                  pmSynonymService.insertValues(insertedPvId.get(0).longValue(), 
                    caDsrdto.getAltNames(), ActiveInactivePendingCode.ACTIVE.getName());
               }
               for (PlannedMarkerDTO markerDTO : markerDTOs) {
                   markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                   if (!insertedPvId.isEmpty()) {
                        markerDTO.setPermissibleValue(IiConverter.convertToIi(insertedPvId.get(0).longValue()));
                   }
                   plannedMarkerService.update(markerDTO);
              }
                 permissibleService.deleteById(oldPvId); 
            }
        }
        //send acceptance email commented as part of PO-7862
        return execute();
    }        
    
    private List<Object> caDsrLookUp(String newcaDsrId) {
        List<Object> permissibleValues = new ArrayList<Object>();
        try {
             String publicID = lookUpTableService.getPropertyValue("CDE_PUBLIC_ID");
             String latestVersionIndicator = lookUpTableService
                  .getPropertyValue("Latest_Version_Indicator");
             String cdeVersion = lookUpTableService
                     .getPropertyValue("CDE_Version");
             DetachedCriteria detachedCrit = DetachedCriteria.forClass(DataElement.class).add(Property
                     .forName("publicID").eq(Long.parseLong(publicID)));
             if (StringUtils.equalsIgnoreCase(latestVersionIndicator, "No")) {
                 detachedCrit.add(Property.forName("version").eq(Float.parseFloat(cdeVersion)));
             } else {
                 detachedCrit.add(Property.forName("latestVersionIndicator").eq("Yes"));
             }
             detachedCrit.setFetchMode("valueDomain", FetchMode.JOIN);
             List<DataElement> results = (List<DataElement>) (List<?>) appService.query(detachedCrit);
             if (results.size() < 1) {
                 throw new PAException("Search of caDSR returned no results.");
             }
             DataElement de = results.get(0);
             String vdId = ((EnumeratedValueDomain) de.getValueDomain()).getId();
             DetachedCriteria criteria = DetachedCriteria.forClass(ValueDomainPermissibleValue.class, "vdpv");
             criteria.add(Property.forName("enumeratedValueDomain.id").eq(vdId));
             criteria.setFetchMode("permissibleValue", FetchMode.JOIN);
             criteria.setFetchMode("permissibleValue.valueMeaning", FetchMode.JOIN);
             criteria.createAlias("permissibleValue", "pv").createAlias("pv.valueMeaning", "vm");
             criteria.add(Expression.eq("vm.publicID", Long.valueOf(newcaDsrId)));
             permissibleValues = appService.query(criteria);
            
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("error.plannedMarker.request.caDSRLookup"));
        }
        return permissibleValues;
    }
    
    private CaDSRWebDTO getSearchResults(Object permissibleValue) throws ApplicationException {
        ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) permissibleValue;
        CaDSRWebDTO dto = new CaDSRWebDTO();
        setCaDSRWebDTO(vdpv, dto);
        return dto;
    }
    
    private CaDSRWebDTO setCaDSRWebDTO(ValueDomainPermissibleValue vdpv, CaDSRWebDTO dto) 
            throws ApplicationException {
         List<String> altNames = new ArrayList<String>();
         StringBuffer synonymName = new StringBuffer();
         ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
         String hql = "select vm.designationCollection from ValueMeaning vm where vm.id='"
              + vm.getId() + "'";
         HQLCriteria criteria = new HQLCriteria(hql);
         List<Object> desgs = appService.query(criteria);
         for (int j = 0; j < desgs.size(); j++) {
            Designation designation = (Designation) desgs.get(j);
            if (StringUtils.equalsIgnoreCase(designation.getType(), "Biomarker Synonym")) {
                if (synonymName.length() == 0) {
                   synonymName.append(designation.getName());
                } else {
                    synonymName.append("; ");
                    synonymName.append(designation.getName());
                  }
                  altNames.add(designation.getName());
            }
         }
         if (synonymName.length() != 0) {
            dto.setVmName(vdpv.getPermissibleValue().getValue() + " (" +  synonymName.toString() + ")");
         } else {
             dto.setVmName(vdpv.getPermissibleValue().getValue());
         }
         dto.setVmMeaning(vm.getLongName());
         dto.setAltNames(altNames);
         dto.setVmDescription(vm.getDescription());
         dto.setPublicId(vm.getPublicID());
         dto.setId(vm.getId());
         dto.setPvValue(vdpv.getPermissibleValue().getValue());
        return dto;
    }
    /**
     * Creates plannedMarkerWebDTO from plannedMarkerDTO.
     * @param markerDTO plannedMarkerDTO
     * @return plannedMarkerWebDTO
     * @throws PAException exception
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    private PlannedMarkerWebDTO populateWebDTO(PlannedMarkerDTO markerDTO, Map<Long, String> map, 
            Map<Long, String> statusMap, Map<Long, DocumentDTO> docMap) throws PAException {
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setId(IiConverter.convertToLong(markerDTO.getIdentifier()));
        webDTO.setName(StConverter.convertToString(markerDTO.getName()));
        webDTO.setMeaning(StConverter.convertToString(markerDTO.getLongName()));
        webDTO.setStatus(CdConverter.convertCdToString(markerDTO.getStatusCode()));
        webDTO.setCreationDate(TsConverter.convertToTimestamp(markerDTO.getDateLastCreated()));
        String nciIdentifier = "";
        String userId = "";
        User csmUser;
        String emailId = "";
        String status = "";
        String fileName = "";
        if (markerDTO.getStudyProtocolIdentifier() != null) {
            if (map == null && statusMap == null && docMap == null) {
             // For checking inActive trials to avoid exception in edit, question and accept mode 
                // inActive trials does not have Marker's userLastCreated. 
                if (markerDTO.getUserLastCreated() != null) { 
                    StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(IiConverter
                            .convertToLong(markerDTO.getStudyProtocolIdentifier()));
                    nciIdentifier = studyProtocolQueryDTO.getNciIdentifier();
                }
            } else {
                if (statusMap.containsKey(IiConverter
                        .convertToLong(markerDTO.getStudyProtocolIdentifier()))) {
                    status = DocumentWorkflowStatusCode.valueOf(statusMap.get(IiConverter
                          .convertToLong(markerDTO.getStudyProtocolIdentifier())))
                          .getDisplayName();
                }
                nciIdentifier = map.get(IiConverter.convertToLong(markerDTO.getStudyProtocolIdentifier()));
                if (docMap.containsKey(IiConverter
                        .convertToLong(markerDTO.getStudyProtocolIdentifier()))) {
                  fileName = StConverter.convertToString(docMap.get(IiConverter.convertToLong(markerDTO
                          .getStudyProtocolIdentifier())).getFileName());
                  webDTO.setProtocolDocumentID(IiConverter.convertToString(docMap
                         .get(IiConverter.convertToLong(markerDTO
                          .getStudyProtocolIdentifier())).getIdentifier())); 
                }
            }
            if (markerDTO.getUserLastCreated() != null) {
                userId = StConverter.convertToString(markerDTO.getUserLastCreated());
                csmUser = CSMUserService.getInstance().getCSMUserById(Long.valueOf(userId));
                emailId = csmUser.getEmailId();
            }
        }
        webDTO.setTrialStatus(status);
        webDTO.setQuestion("");
        webDTO.setNciIdentifier(nciIdentifier);
        webDTO.setCsmUserEmailId(emailId);
        webDTO.setProtocolDocument(fileName);
        webDTO.setPermissibleValue(markerDTO.getPermissibleValue());
        webDTO.setDateEmailSent(TsConverter.convertToTimestamp(markerDTO.getDateEmailSent()));
        return webDTO;
    }
    /**
     * @return result
     * @throws PAException  PAException
     */
    public String saveFile() throws PAException {
        try {
            DocumentDTO  docDTO = 
                PaRegistry.getDocumentService().get(IiConverter.convertToIi(getSelectedRowDocument()));
            PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
                    .get(IiConverter.convertToIi(id));
            PlannedMarkerWebDTO webDTO = populateWebDTO(marker, null, null, null);
            StringBuffer fileName = new StringBuffer();
            fileName.append(webDTO.getNciIdentifier()).append('-').append(docDTO.getFileName().getValue());
            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/octet-stream");
            servletResponse.setContentLength(docDTO.getText().getData().length);
            servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
            servletResponse.setHeader("Pragma", "public");
            servletResponse.setHeader("Cache-Control", "max-age=0");
            ByteArrayInputStream bStream = new ByteArrayInputStream(docDTO.getText().getData());
            ServletOutputStream out = servletResponse.getOutputStream();
            IOUtils.copy(bStream, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException err) {
            LOG.error("TrialDocumentAction failed with FileNotFoundException: "
                    + err, err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            execute();
            return ERROR;
        } catch (Exception e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            execute();
            return ERROR;
        }
        return NONE;
    }

    /**
     * @return plannedMarkerService
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * 
     * @param plannedMarkerService plannedMarkerService
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * 
     * @return plannedMarkerList
     */
    public List<PlannedMarkerWebDTO> getPlannedMarkerList() {
        return plannedMarkerList;
    }

    /**
     * 
     * @param plannedMarkerList plannedMarkerList
     */
    public void setPlannedMarkerList(List<PlannedMarkerWebDTO> plannedMarkerList) {
        this.plannedMarkerList = plannedMarkerList;
    }

    /**
     * 
     * @return plannedMarkers 
     */
    public List<PlannedMarkerDTO> getPlannedMarkers() {
        return plannedMarkers;
    }

    /**
     * 
     * @param plannedMarkers plannedMarkers
     */
    public void setPlannedMarkers(List<PlannedMarkerDTO> plannedMarkers) {
        this.plannedMarkers = plannedMarkers;
    }

    /**
     *  
     * @return protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * 
     * @param protocolQueryService protocolQueryService
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * 
     * @return PlannedMarkerWebDTO
     */
    public PlannedMarkerWebDTO getPlannedMarker() {
        return plannedMarker;
    }

    /**
     * 
     * @param plannedMarker PlannedMarkerWebDTO
     */
    public void setPlannedMarker(PlannedMarkerWebDTO plannedMarker) {
        this.plannedMarker = plannedMarker;
    }

    /**
     * 
     * @return selectedRowIdentifier
     */
    public String getSelectedRowIdentifier() {
        return selectedRowIdentifier;
    }

    /**
     * 
     * @param selectedRowIdentifier selectedRowIdentifier
     */
    public void setSelectedRowIdentifier(String selectedRowIdentifier) {
        this.selectedRowIdentifier = selectedRowIdentifier;
    }
    
    
    /**
     * 
     * @return trialId
     */
    public String getTrialId() {
        return trialId;
    }
    /**
     * 
     * @param trialId trialId
     */
    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    /**
     * 
     * @return markerName
     */
    public String getMarkerName() {
        return markerName;
    }
    /**
     * 
     * @param markerName markerName
     */
    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    /**
     * 
     * @return studyProtocolService
     */
    public StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }
    /**
     * 
     * @param studyProtocolService studyProtocolService
     */
    public void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    } 
   
    /**
     * @param permissibleService the permissibleService to set
     */
    public void setPermissibleService(
            PlannedMarkerSyncWithCaDSRServiceLocal permissibleService) {
        this.permissibleService = permissibleService;
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
     * 
     * @return selectedRowDocument
     */
    public String getSelectedRowDocument() {
        return selectedRowDocument;
    }
    /**
     * 
     * @param selectedRowDocument selectedRowDocument
     */
    public void setSelectedRowDocument(String selectedRowDocument) {
        this.selectedRowDocument = selectedRowDocument;
        }
    /**
     * 
     * @return caDsrId caDsrId
     */
    public String getCaDsrId() {
        return caDsrId;
    }
    /**
     * 
     * @param caDsrId caDsrId
     */
    public void setCaDsrId(String caDsrId) {
        this.caDsrId = caDsrId;
    }
    /**
     * @return the appService
     */
    public ApplicationService getAppService() {
        return appService;
    }

    /**
     * @param appService the appService to set
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
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
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }
}
