package gov.nih.nci.pa.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class PlannedMarkerSyncWithCaDSRServiceTest extends
        AbstractHibernateTestCase {
    private final PlannedMarkerSyncWithCaDSRBeanLocal bean = new PlannedMarkerSyncWithCaDSRBeanLocal();
    
    private final PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    
    private final PlannedMarkerSynonymsServiceLocal pmSynonymService = mock(PlannedMarkerSynonymsServiceLocal.class);

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();

        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);

    }

    @Test
    public void getValuesByIdTest() throws PAException {
        TestSchema.createPlannedMarker();
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesById(1L);
        assertTrue(list.size() > 0);
    }

    @Test
    public void getValuesByNameTest() throws PAException {
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean
                .getValuesByName("Marker #1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void getIdentifierByCadsrIdTest() throws PAException {
        List<Number> list = bean.getIdentifierByCadsrId(12345L);
        assertTrue(list.size() > 0);
    }

    @Test
    public void getPendingIdentifierByCadsrNameTest() throws PAException {
        List<Number> list = bean.getPendingIdentifierByCadsrName("Marker #1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void getPendingIdentifierByCadsrNameTest1() throws PAException {
        List<Number> list = bean.getPendingIdentifierByCadsrName("name1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void syncTableWithCaDSRTest() throws PAException {
        
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getPMSynonymService()).thenReturn(pmSynonymService);
        List<CaDSRDTO> valuesList = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setPublicId(12345L);
        value.setVmName("name");
        value.setVmMeaning("meaning");
        value.setPvValue("pvValue");
        value.setVmDescription("description");
        valuesList.add(value);
        bean.syncTableWithCaDSR(valuesList);
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesByName("name");
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void updateValuesTest() throws PAException {
        bean.updateValueByName(12345L, "Aneuploidy", "Aneuploidy", "Aneuploidy", "c320", "Aneuploidy", ActiveInactivePendingCode.ACTIVE.getName());
        List<Number> list = bean.getIdentifierByCadsrId(12345L);
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void updateValueByNameTest() throws PAException {
        bean.insertValues(null, "PI3K", "PI3K", null , null, null, ActiveInactivePendingCode.PENDING.getName());
        bean.updateValueByName(null, "PI3K", "PI3K", null , ActiveInactivePendingCode.PENDING.getName());
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesByName("PI3K");
        assertTrue(list.size() > 0);
    }
    
    @Test 
    public void updateStatusCodeTest() throws PAException {
        bean.insertValues(1348L, "PI3K", "PI3K", null , null, null, ActiveInactivePendingCode.PENDING.getName());
        List<Number> list = bean.getIdentifierByCadsrId(1348L);
        assertTrue(list.size() > 0);
        bean.updateStatusCode(1348L, ActiveInactivePendingCode.ACTIVE.getName());
        List<PlannedMarkerSyncWithCaDSRDTO> list1 = bean.getValuesById(list.get(0).longValue());
        assertTrue(list1.size() > 0);
    }
    @Test
    public void syncTableCaDSRTest() throws PAException {
        PlannedMarkerSyncWithCaDSRBeanLocal beanmock = mock(PlannedMarkerSyncWithCaDSRBeanLocal.class);
        List<Number> list = new ArrayList<Number>();
        list.add(1);
        when(beanmock.getIdentifierByPvName(any(String.class))).thenReturn(list);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getPMSynonymService()).thenReturn(pmSynonymService);
        when(beanmock.getIdentifierByCadsrId(any(Long.class))).thenReturn(list);
        List<String> listofSynValues = new ArrayList<String>();
        listofSynValues.add("syn1");
        listofSynValues.add("syn2");
        when(pmSynonymService.getAltNamesBySyncId(any(Long.class))).thenReturn(listofSynValues);
        List<CaDSRDTO> valuesList = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setPublicId(12345L);
        value.setVmName("name");
        value.setVmMeaning("meaning");
        value.setPvValue("pvValue");
        value.setVmDescription("description");
        value.setAltNames(listofSynValues);
        valuesList.add(value);
        doCallRealMethod().when(beanmock).syncTableWithCaDSR(valuesList);
        beanmock.syncTableWithCaDSR(valuesList);
        doCallRealMethod().when(beanmock).getIdentifierByCadsrId(12345L);
        List<Number> list1 = beanmock.getIdentifierByCadsrId(12345L);
        assertTrue(list1.size() > 0);
    }
    
    @Test
    public void syncTableCaDSRNameLogicTest() throws PAException {
        PlannedMarkerSyncWithCaDSRBeanLocal beanmock = mock(PlannedMarkerSyncWithCaDSRBeanLocal.class);
        List<Number> list = new ArrayList<Number>();
        list.add(1);
        list.add(2);
        List<PlannedMarkerSyncWithCaDSRDTO> dtoList = new ArrayList<PlannedMarkerSyncWithCaDSRDTO>();
        PlannedMarkerSyncWithCaDSRDTO dto = new PlannedMarkerSyncWithCaDSRDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setName(StConverter.convertToSt("name"));
        dto.setPvName(StConverter.convertToSt("pvValue"));
        dto.setMeaning(StConverter.convertToSt("meaning"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        dtoList.add(dto);
        when(beanmock.getIdentifierByPvName(any(String.class))).thenReturn(list);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getPMSynonymService()).thenReturn(pmSynonymService);
        when(beanmock.getIdentifierByCadsrId(any(Long.class))).thenReturn(list);
        when(beanmock.getValuesById(any(Long.class))).thenReturn(dtoList);
        List<String> listofSynValues = new ArrayList<String>();
        listofSynValues.add("syn1");
        listofSynValues.add("syn2");
        when(pmSynonymService.getAltNamesBySyncId(any(Long.class))).thenReturn(listofSynValues);
        List<CaDSRDTO> valuesList = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setPublicId(12345L);
        value.setVmName("name");
        value.setVmMeaning("meaning");
        value.setPvValue("pvValue");
        value.setVmDescription("description");
        value.setAltNames(listofSynValues);
        valuesList.add(value);
        doCallRealMethod().when(beanmock).syncTableWithCaDSR(valuesList);
        beanmock.syncTableWithCaDSR(valuesList);
        doCallRealMethod().when(beanmock).getIdentifierByCadsrId(12345L);
        List<Number> list1 = beanmock.getIdentifierByCadsrId(12345L);
        assertTrue(list1.size() > 0);
        
        dtoList.clear();
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.INACTIVE));
        dtoList.add(dto);
        when(beanmock.getValuesById(any(Long.class))).thenReturn(dtoList);
        doCallRealMethod().when(beanmock).syncTableWithCaDSR(valuesList);
        beanmock.syncTableWithCaDSR(valuesList);
        doCallRealMethod().when(beanmock).getIdentifierByCadsrId(12345L);
        list1 = beanmock.getIdentifierByCadsrId(12345L);
        assertTrue(list1.size() > 0);
    }
    @Test
    public void syncTableCaDSRElseTest() throws PAException {
        PlannedMarkerSyncWithCaDSRBeanLocal beanmock = mock(PlannedMarkerSyncWithCaDSRBeanLocal.class);
        List<Number> list = new ArrayList<Number>();
        list.add(1);
        List<PlannedMarkerSyncWithCaDSRDTO> dtoList = new ArrayList<PlannedMarkerSyncWithCaDSRDTO>();
        PlannedMarkerSyncWithCaDSRDTO dto = new PlannedMarkerSyncWithCaDSRDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("name"));
        dto.setPvName(StConverter.convertToSt("pvValue"));
        dto.setMeaning(StConverter.convertToSt("meaning"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        dtoList.add(dto);
        when(beanmock.getIdentifierByPvName(any(String.class))).thenReturn(list);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getPMSynonymService()).thenReturn(pmSynonymService);
        when(beanmock.getValuesById(any(Long.class))).thenReturn(dtoList);
        when(beanmock.getIdentifierByName(any(String.class))).thenReturn(list);
        List<String> listofSynValues = new ArrayList<String>();
        listofSynValues.add("syn1");
        listofSynValues.add("syn2");
        when(pmSynonymService.getAltNamesBySyncId(any(Long.class))).thenReturn(listofSynValues);
        List<CaDSRDTO> valuesList = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setPublicId(12345L);
        value.setVmName("name");
        value.setVmMeaning("meaning");
        value.setPvValue("pvValue");
        value.setVmDescription("description");
        value.setAltNames(listofSynValues);
        valuesList.add(value);
        doCallRealMethod().when(beanmock).syncTableWithCaDSR(valuesList);
        beanmock.syncTableWithCaDSR(valuesList);
        doCallRealMethod().when(beanmock).getIdentifierByCadsrId(12345L);
        List<Number> list1 = beanmock.getIdentifierByCadsrId(12345L);
        assertTrue(list1.size() > 0);
    }
    
    @Test
    public void updateValueByIdTest() throws PAException {
       bean.updateValueById("PendingMarker", 2L);
       List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesById(1L);
       assertTrue(list.size() > 0);
    }
    
    @Test
    public void deleteByIdTest() throws PAException {
        bean.insertValues(null, "Marker", "Marker", null , null, null, ActiveInactivePendingCode.PENDING.getName());
        List<Number> list = bean.getIdentifierByName("Marker");
        bean.deleteById(list.get(0).longValue());
        List<PlannedMarkerSyncWithCaDSRDTO> list1 = bean.getValuesById(list.get(0).longValue());
        assertTrue(list1.size() == 0);
    }
}
