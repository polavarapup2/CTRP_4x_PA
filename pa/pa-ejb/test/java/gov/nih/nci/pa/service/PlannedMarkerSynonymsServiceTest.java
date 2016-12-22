package gov.nih.nci.pa.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

public class PlannedMarkerSynonymsServiceTest extends
        AbstractHibernateTestCase {
       
    private final PlannedMarkerSynonymsBeanLocal bean = new PlannedMarkerSynonymsBeanLocal();
    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
    }
    
    @Test 
    public void insertValuesTest() throws PAException {
        List<String> synonyms = new ArrayList<String>();
        synonyms.add("aplha");
        synonyms.add("beta");
        bean.insertValues(1L, synonyms, ActiveInactivePendingCode.ACTIVE.getName());
        List<Number> list = bean.getIdentifierBySyncId(1L);
        assertTrue(list.size() > 0);
        assertTrue(list.get(0).intValue() == 1);
        List<String> AltList = bean.getAltNamesBySyncId(1L);
        assertTrue(AltList.size() > 0);
        assertTrue(AltList.get(0) == "alpha");
    }
    
    @Test
    public void getAltNamesBySyncIdTest() throws PAException {
        List<String> list = bean.getAltNamesBySyncId(1L);
        assertTrue(list.size() > 0);
        assertTrue(list.get(0) == "alpha");
    }
    
    @Test
    public void insertAndUpdateLogicTest() throws PAException {
        List<String> oldSynonyms = new ArrayList<String>();
        oldSynonyms.add("aplha");
        List<String> synonyms = new ArrayList<String>();
        synonyms.add("aplha");
        
        bean.insertAndUpdateLogic(oldSynonyms, synonyms, ActiveInactivePendingCode.ACTIVE.getName(), 1L);
        List<String> list = bean.getAltNamesBySyncId(1L);
        assertTrue(list.size() > 0);
        assertTrue(list.get(0) == "alpha");
        
        synonyms = new ArrayList<String>();
        synonyms.add("aplha_new");
        bean.insertAndUpdateLogic(oldSynonyms, synonyms, ActiveInactivePendingCode.ACTIVE.getName(), 1L);
        list = bean.getAltNamesBySyncId(1L);
        assertTrue(list.size() > 0);
        assertTrue(list.get(0) == "aplha_new");
        
        synonyms = new ArrayList<String>();
        bean.insertAndUpdateLogic(oldSynonyms, synonyms, ActiveInactivePendingCode.ACTIVE.getName(), 1L);
        list = bean.getAltNamesBySyncId(1L);
        assertTrue(list.size() > 0);
        assertTrue(list.get(0) == "aplha_new");
    }

}
