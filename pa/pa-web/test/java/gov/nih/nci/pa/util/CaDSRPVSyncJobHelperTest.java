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
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class CaDSRPVSyncJobHelperTest {
    CaDSRPVSyncJobHelper helper;
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService = mock(PlannedMarkerSyncWithCaDSRServiceLocal.class);
    // ApplicationService appService = mock(ApplicationService.class);
    CaDSRPVSyncJobHelper helperMock = mock(CaDSRPVSyncJobHelper.class);
    /** The CDE public Id for Assay Type Attribute. */
    ApplicationService appService = mock(ApplicationService.class);
    private static final Long CDE_PUBLIC_ID = 5473L;
    private List<Object> results = new ArrayList<Object>();

    @Before
    public void setUp() throws Exception {
        helper = new CaDSRPVSyncJobHelper();
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        helperMock.setPlannedMarkerService(plannedMarkerService);
        helperMock.setPermissibleService(permissibleService);
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");
        DataElement de = new DataElement();
        de.setValueDomain(vd);
        List<Object> deResults = new ArrayList<Object>();
        deResults.add(de);
        when(helperMock.getApplicationService()).thenReturn(appService);
        when(appService.search(eq(DataElement.class), any(DataElement.class)))
                .thenReturn(deResults);
        PermissibleValue pv = new PermissibleValue();
        pv.setValue("N-Cadherin");
        ValueMeaning vm = new ValueMeaning();
        vm.setLongName("N-Cadherin");
        vm.setDescription("cadherin");
        vm.setPublicID(2578250L);
        pv.setValueMeaning(vm);
        ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(results);
        helperMock.setAppService(appService);

    }

//    @Test
    public void getAllValuesFromCaDSRTest() throws Exception {
        CaDSRPVSyncJobHelper helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getAllValuesFromCaDSR();
        List<CaDSRDTO> list = helperMock1.getAllValuesFromCaDSR();
        assertTrue(list.size() > 0);
    }

    private CaDSRPVSyncJobHelper createHelperMock() throws Exception {
        CaDSRPVSyncJobHelper helperMock = mock(CaDSRPVSyncJobHelper.class);
        List<CaDSRDTO> values1 = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setId("1");
        value.setPublicId(2578250L);
        value.setVersion("4.0");
        value.setVmMeaning("N-Cadherin");
        value.setVmName("N-Cadherin");
        value.setVmDescription("cadherin");
        values1.add(value);
        when(helperMock.getApplicationService()).thenReturn(appService);
        helperMock.setAppService(appService);
        when(helperMock.getSearchResults(results)).thenReturn(values1);
        return helperMock;
    }

    @Test
    public void getSearchResultsTest() throws Exception {
        CaDSRPVSyncJobHelper helperMock1 = createHelperMock();
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        List<CaDSRDTO> list = helperMock1.getSearchResults(results);
        assertTrue(list.size() > 0);
    }

    @Test
    public void updatePlannedMarkerSyncTableTest() throws Exception {
        CaDSRPVSyncJobHelper helperMock1 = createHelperMock();
        when(PaRegistry.getPMWithCaDSRService()).thenReturn(permissibleService);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(
                plannedMarkerService);
        doCallRealMethod().when(helperMock1).getSearchResults(results);
        List<CaDSRDTO> list = helperMock1.getSearchResults(results);
        when(helperMock1.getAllValuesFromCaDSR()).thenReturn(list);
        doCallRealMethod().when(helperMock1).updatePlannedMarkerSyncTable();
        helperMock1.updatePlannedMarkerSyncTable();

    }

}
