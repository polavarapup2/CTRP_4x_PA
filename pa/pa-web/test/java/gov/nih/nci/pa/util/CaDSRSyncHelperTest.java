package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.system.applicationservice.ApplicationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRSyncHelperTest extends AbstractHibernateTestCase {
    CaDSRSyncHelper helper;
    private MarkerAttributesServiceLocal markerAttributesService = mock(MarkerAttributesServiceLocal.class);
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    ApplicationService appService = mock(ApplicationService.class);
    CaDSRSyncHelper helperMock = mock(CaDSRSyncHelper.class);
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID_ASSAY = 2189871L;
    /** The CDE public Id for BioMarker Use Attribute. */
    private static final Long CDE_PUBLIC_ID_USE = 2939411L;
    /** The CDE public Id for BioMarker Purpose Attribute. */
    private static final Long CDE_PUBLIC_ID_PURPOSE = 2939397L;
    /** The CDE public Id for Specimen Type Attribute. */
    private static final Long CDE_PUBLIC_ID_SPECIMEN = 3111302L;
    /** The CDE public Id for Specimen Collection Attribute. */
    private static final Long CDE_PUBLIC_ID_SP_COL = 2939404L;
    /** The CDE public Id for EvaluationType Attribute. */
    private static final Long CDE_PUBLIC_ID_EVAL = 3645784L;
    private List<Object> results = new ArrayList<Object>();
    List<Object> deResults = new ArrayList<Object>();
    PermissibleValue pv = new PermissibleValue();
    ValueMeaning vm = new ValueMeaning();
    ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
    
    @Before
    public void setUp() throws Exception {
        helper = new CaDSRSyncHelper();
        helper.setMarkerAttributesService(markerAttributesService);
        helper.setPlannedMarkerService(plannedMarkerService);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getMarkerAttributesService()).thenReturn(markerAttributesService);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableSrv);
        helper.setLookUpTableService(lookUpTableSrv);
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");
        DataElement de = new DataElement();
        de.setValueDomain(vd);
        deResults.add(de);
        when(appService.search(eq(DataElement.class), any(DataElement.class))).thenReturn(deResults);
        pv.setValue("ELISA");
        vm.setLongName("ELISA");
        vm.setDescription("ELISA");
        vm.setPublicID(2189871L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");
        results.add(vdpv);
        TestSchema.caDSRSyncJobProperties();
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helper.setAppService(appService);
    }

    private CaDSRSyncHelper createHelperMock() throws Exception {
    	CaDSRSyncHelper helperMock = mock(CaDSRSyncHelper.class);
        when(helperMock.getApplicationService()).thenReturn(appService);
        helperMock.setAppService(appService);
        Map<Long, Map<String, String>> values = new HashMap<Long, Map<String, String>>();
        Map<String, String> innerMap = new HashMap<String, String>();
        innerMap.put("ELISA", "ELISA");
        values.put(2558713L, innerMap);
        when(helperMock.getSearchResults(results)).thenReturn(values);
        when(helperMock.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F)).thenReturn(values);
        return helperMock;
    }

    @Test
    public void updateMarkerTablesTest() throws Exception {
        CaDSRSyncHelper helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        Map<Long, Map<String, String>> values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F);
        doCallRealMethod().when(helperMock1).updateMarkerTables();
        helperMock1.updateMarkerTables();
        
        
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_EVAL, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_EVAL, 1.0F);
        doCallRealMethod().when(helperMock1).updateMarkerTables();
        helperMock1.updateMarkerTables();
        
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 1.0F);
        doCallRealMethod().when(helperMock1).updateMarkerTables();
        helperMock1.updateMarkerTables();
        
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_USE, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_USE, 1.0F);
        doCallRealMethod().when(helperMock1).updateMarkerTables();
        helperMock1.updateMarkerTables();
        
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 1.0F);
        doCallRealMethod().when(helperMock1).updateMarkerTables();
        helperMock1.updateMarkerTables();
        
    }  
    
    @Test
    public void getCaDSRValuesTest() throws Exception {
        CaDSRSyncHelper helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        Map<Long, Map<String, String>> values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F);
        Map<Long, Map<String, String>> result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F);
        assertTrue(result.size() > 0);
        Map<String, String> innerMap = result.get(2189871L);
        assertTrue(innerMap.containsKey("ELISA"));
        
        results.clear();
        pv.setValue("Integral");
        vm.setLongName("Integral");
        vm.setDescription("Integral");
        vm.setPublicID(2944941L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("2");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_USE, 1.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_USE, 1.0F);
        result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_USE, 1.0F);
        assertTrue(result.size() > 0);
        innerMap = result.get(2944941L);
        assertTrue(innerMap.containsKey("Integral"));
        
        results.clear();
        pv.setValue("Stratification Factor");
        vm.setLongName("Stratification Factor");
        vm.setDescription("Stratification Factor");
        vm.setPublicID(2939394L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("3");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 1.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 1.0F);
        result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 1.0F);
        assertTrue(result.size() > 0);
        innerMap = result.get(2939394L);
        assertTrue(innerMap.containsKey("Stratification Factor"));
        
        results.clear();
        pv.setValue("Serum");
        vm.setLongName("Serum");
        vm.setDescription("Serum");
        vm.setPublicID(3004972L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("2");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 1.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 1.0F);
        result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 1.0F);
        assertTrue(result.size() > 0);
        innerMap = result.get(3004972L);
        assertTrue(innerMap.containsKey("Serum"));
        
        
        results.clear();
        pv.setValue("Mandatory");
        vm.setLongName("Mandatory");
        vm.setDescription("Mandatory");
        vm.setPublicID(2939403L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("2");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_SP_COL, 1.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_SP_COL, 1.0F);
        result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_SP_COL, 1.0F);
        assertTrue(result.size() > 0);
        innerMap = result.get(2939403L);
        assertTrue(innerMap.containsKey("Mandatory"));
        
        
        results.clear();
        pv.setValue("Methylation");
        vm.setLongName("Methylation");
        vm.setDescription("Methylation");
        vm.setPublicID(3079271L);
        pv.setValueMeaning(vm);
        vdpv.setPermissibleValue(pv);
        vdpv.setId("2");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        values = helperMock1.getSearchResults(results);
        when(helperMock1.getCaDSRValues(CDE_PUBLIC_ID_EVAL, 1.0F)).thenReturn(values);
        doCallRealMethod().when(helperMock1).getCaDSRValues(CDE_PUBLIC_ID_EVAL, 1.0F);
        result = helperMock1.getCaDSRValues(CDE_PUBLIC_ID_EVAL, 1.0F);
        assertTrue(result.size() > 0);
        innerMap = result.get(3079271L);
        assertTrue(innerMap.containsKey("Methylation"));
        
    }
    
    
    @Test
    public void getSearchResultsTest() throws Exception {
         CaDSRSyncHelper helperMock1 = createHelperMock();
         doCallRealMethod().when(helperMock1).getSearchResults(results);
         Map<Long, Map<String, String>> values = helperMock1.getSearchResults(results);
         assertTrue(values.size() > 0);
         Map<String, String> innerMap = values.get(2189871L);
         assertTrue(innerMap.containsKey("ELISA"));
    }

    @Test
    public void syncPlannedMarkerAttributes() throws Exception {
        Map<Long, Map<String, String>> map = new HashMap<Long, Map<String,String>>();
        Map<String, String> value = new HashMap<String, String>();
        value.put("Microarray", "Microarray");
        map.put(2575508L, value);
        when(markerAttributesService.attributeValuesWithCaDSR(BioMarkerAttributesCode.ASSAY_TYPE)).thenReturn(map);
        helper.syncPlannedMarkerAttributes();
        Map<String, String> value1 = new HashMap<String, String>();
        value1.put("ELISA1", "ELISA1");
        map.put(2558713L, value1);
        when(markerAttributesService.attributeValuesWithCaDSR(BioMarkerAttributesCode.ASSAY_TYPE)).thenReturn(map);
        helper.syncPlannedMarkerAttributes();
    }

}
