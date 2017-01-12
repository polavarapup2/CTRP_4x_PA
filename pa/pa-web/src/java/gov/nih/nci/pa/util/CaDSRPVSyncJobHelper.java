package gov.nih.nci.pa.util;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class CaDSRPVSyncJobHelper {
    private ApplicationService appService;
    private static final Integer CHUNK_SIZE = 1000;
    /** The LOG details. */
    private static final Logger LOG = Logger
            .getLogger(CaDSRPVSyncJobHelper.class);
    private PlannedMarkerServiceLocal plannedMarkerService;
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService;
    /** 
     * Public id 
     */
    private static final String PUBLIC_ID = "vm.publicID";
    /**
     * updates the planned marker sync table
     * @throws Exception  Exception
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void updatePlannedMarkerSyncTable() throws Exception {
        permissibleService = PaRegistry.getPMWithCaDSRService();
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        List<CaDSRDTO> values = getAllValuesFromCaDSR();
        if (!values.isEmpty()) {
           permissibleService.syncTableWithCaDSR(values);
        }
    }

    /**
     * 
     * @return map<Stirng, String> map
     * @throws Exception  Exception
     */
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "unchecked", "PMD.ExcessiveMethodLength" })
    public List<CaDSRDTO> getAllValuesFromCaDSR() throws Exception {
        List<CaDSRDTO> values = new ArrayList<CaDSRDTO>();

        appService = getApplicationService();
        try {
            String publicID = PaRegistry.getLookUpTableService().getPropertyValue("CDE_PUBLIC_ID");
            String latestVersionIndicator = PaRegistry.getLookUpTableService()
                 .getPropertyValue("Latest_Version_Indicator");
            String cdeVersion = PaRegistry.getLookUpTableService()
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
            String vdId = ((ValueDomain) de.getValueDomain()).getId();

            DetachedCriteria dc = constructBaseCriteria(vdId);
            ProjectionList proj = Projections.projectionList();
            proj.add(Projections.min(PUBLIC_ID));
            proj.add(Projections.groupProperty("enumeratedValueDomain.id"));
            dc.setProjection(proj);
            List<Object> qRslt = appService.query(dc);
            Long minId = (Long) ((Object[]) qRslt.get(0))[0];

            dc = constructBaseCriteria(vdId);
            proj = Projections.projectionList();
            proj.add(Projections.max(PUBLIC_ID));
            proj.add(Projections.groupProperty("enumeratedValueDomain.id"));
            dc.setProjection(proj);
            qRslt = appService.query(dc);
            Long maxId = (Long) ((Object[]) qRslt.get(0))[0];

            List<Object> result = new ArrayList<Object>();
            for (Long id = minId; id <= maxId; id += CHUNK_SIZE) {
                dc = constructBaseCriteria(vdId);
                dc.add(Property.forName(PUBLIC_ID).ge(id));
                dc.add(Property.forName(PUBLIC_ID).lt(id + CHUNK_SIZE));
                List<Object> permissibleValues = appService.query(dc);
                result.addAll(new ArrayList<Object>(permissibleValues));
            }
            values = getSearchResults(result);
        } catch (Exception e) {
            LOG.error("Error while querying caDSR", e);
            throw new Exception(e);
        }
        return values;
    }

    /**
     * 
     * @return ApplicationService appService
     * @throws Exception  Exception
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public ApplicationService getApplicationService() throws Exception {
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error(
                    "Error attempting to instantiate caDSR Application Service.",
                    e);
            throw new Exception(e);
        }
        return appService;
    }

    private DetachedCriteria constructBaseCriteria(String vdId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ValueDomainPermissibleValue.class);
        criteria.add(Property.forName("enumeratedValueDomain.id").eq(vdId));
        criteria.setFetchMode("permissibleValue", FetchMode.EAGER);
        criteria.setFetchMode("permissibleValue.valueMeaning", FetchMode.EAGER);
        criteria.setFetchMode("permissibleValue.valueMeaning.designationCollection", FetchMode.EAGER);
        criteria.setFetchMode("permissibleValue.valueMeaning.conceptDerivationRule", FetchMode.EAGER);
        criteria.createAlias("permissibleValue", "pv").createAlias("pv.valueMeaning", "vm");
        return criteria;
    }

    /**
     * gets the results 
     * @param permissibleValues permissibleValues
     * @return List resultsList
     */
    protected List<CaDSRDTO> getSearchResults(
            List<Object> permissibleValues) {
        List<CaDSRDTO> resultsList = new ArrayList<CaDSRDTO>();
        List<Long> publicids = new ArrayList<Long>();
        for (Object obj : permissibleValues) {
            ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) obj;
            CaDSRDTO dto = new CaDSRDTO();
            ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
          if (!publicids.contains(vm.getPublicID())) {
            setCaDSRDTO(vdpv, dto);
            dto.setId(vdpv.getId());
            publicids.add(vm.getPublicID());
            resultsList.add(dto);  
        }
        }
        return resultsList;
    }

    private CaDSRDTO setCaDSRDTO(ValueDomainPermissibleValue vdpv, CaDSRDTO dto) {
        List<String> altNames = new ArrayList<String>();
        ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
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
                     altNames.add(designation.getName());
                }
            }
        }
        if (synonymName.length() != 0) {
            dto.setVmName(vdpv.getPermissibleValue().getValue() + " (" +  synonymName.toString() + ")");
        } else {
            dto.setVmName(vdpv.getPermissibleValue().getValue());
        }
        dto.setAltNames(altNames);
        dto.setPvValue(vdpv.getPermissibleValue().getValue());
        dto.setVmMeaning(vm.getLongName());
        dto.setVmDescription(vm.getDescription());
        dto.setPublicId(vm.getPublicID());
        if (vm.getConceptDerivationRule() != null) {
            dto.setNtTermIdentifier(vm.getConceptDerivationRule().getName());
        }
        return dto;
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
     * @return the plannedMarkerServiceLocal
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
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
     * @return the permissibleService
     */
    public PlannedMarkerSyncWithCaDSRServiceLocal getPermissibleService() {
        return permissibleService;
    }

    /**
     * @param permissibleService
     *            the permissibleService to set
     */
    public void setPermissibleService(
            PlannedMarkerSyncWithCaDSRServiceLocal permissibleService) {
        this.permissibleService = permissibleService;
    }

}
