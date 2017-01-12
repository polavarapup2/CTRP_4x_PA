package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test NCIt look up service
 * @author Gopalakrishnan Unnikrishnan
 *
 */
public class NCItTermsLookupTest  {

    NCItTermsLookup lookup;
    public static final int NCIT_API_MOCK_PORT = (int) (50000+Math.random()*1000);
    private String lexEVSURL = null;
    private String lexApiURL = null;
    LookUpTableServiceRemote lookUpTableSrv = mock(LookUpTableServiceRemote.class);
    MockNCITServer mockNCITServer = new MockNCITServer();

    @Before
    public void setUp() throws Exception {
        
        lexEVSURL ="http://localhost:"+ NCIT_API_MOCK_PORT + "/lexEVSAPI/";
        lexApiURL ="http://localhost:"+ NCIT_API_MOCK_PORT + "/lexApiURL/"
        +"{CODE}";
    
        
        when(lookUpTableSrv.getPropertyValue("ctrp.lexEVSURL")).thenReturn(lexEVSURL);
        when(lookUpTableSrv.getPropertyValue("ctrp.lexAPIURL")).thenReturn(lexApiURL);
        
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableSrv);
        
        lookup = new NCItTermsLookup();
         mockNCITServer.startServer( NCIT_API_MOCK_PORT);
        
    }

    @After
    public void tearDown() throws Exception {
        mockNCITServer.stopServer();
    }

    @Test
    public void testlookupDisease() throws Exception {
        DiseaseWebDTO disease = lookup.lookupDisease("C4878");
        assertNotNull(disease);
        assertEquals("C4878", disease.getNtTermIdentifier());
        assertEquals("Lung Carcinoma", disease.getPreferredName());
        assertEquals("Lung Cancer", disease.getMenuDisplayName());
        assertFalse(disease.getAlterNameList().isEmpty());
        assertTrue(disease.getAlterNameList().contains("Cancer of Lung"));
        assertTrue(disease.getAlterNameList().contains("Cancer of the Lung"));
        assertTrue(disease.getAlterNameList().contains("Carcinoma of the Lung"));
        assertFalse(disease.getParentTermList().isEmpty());
        assertTrue(disease.getParentTermList().contains("C2916: Carcinoma"));
        assertTrue(disease.getParentTermList().contains("C7377: Malignant Lung Neoplasm"));
        assertFalse(disease.getChildTermList().isEmpty());
        assertTrue(disease.getChildTermList().contains("C27925: Asbestos-Related Lung Carcinoma"));
        assertTrue(disease.getChildTermList().contains("C5641: Occult Lung Carcinoma"));
        assertTrue(disease.getChildTermList().contains("C45544: Lung Mucoepidermoid Carcinoma"));
        
    }

    @Test
    public void testlookupNonExistingDisease() throws Exception {
        DiseaseWebDTO disease = lookup.lookupDisease("C1");
        assertNull(disease);
    }

    @Test
    public void testlookupInterventionNoSynonym() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C2810");
        assertNotNull(intervention);
        assertEquals("C2810", intervention.getNtTermIdentifier());
        assertEquals("SB-AS02B Adjuvant", intervention.getName());
        assertTrue(!intervention.getAlterNames().values().contains("SY"));
    }

    @Test
    public void testlookupIntervention() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C26446");
        assertNotNull(intervention);
        assertEquals("C26446", intervention.getNtTermIdentifier());
        assertEquals("Dendritic Cell-Autologous Lung Tumor Vaccine", intervention.getName());
        assertFalse(intervention.getAlterNames().isEmpty());
        assertTrue(intervention.getAlterNames().keySet().contains("DCVax-Lung"));
    }

    @Test
    public void testlookupNonExistingIntervention() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C1");
        assertNull(intervention);
    }
    
   @Test
    public void testFetchParent() throws Exception {
    	List<String> parentTermsList = lookup.fetchTree("C4878", true);
    	assertFalse(parentTermsList.isEmpty());
    	assertTrue(parentTermsList.contains("C2916"));
    }
   
   @Test
   public void testFetchChild() throws Exception {
   	List<String> parentTermsList = lookup.fetchTree("C4878", false);
   	assertFalse(parentTermsList.isEmpty());
   	assertTrue(parentTermsList.contains("C27925"));
   }
}
