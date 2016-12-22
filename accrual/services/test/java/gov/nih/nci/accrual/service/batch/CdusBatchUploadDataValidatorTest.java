package gov.nih.nci.accrual.service.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.service.batch.BaseBatchUploadReader.BatchFileErrors;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
/**
 * @author Hugh Reinhart
 */
public class CdusBatchUploadDataValidatorTest extends AbstractBatchUploadReaderTest {

    CdusBatchUploadDataValidator v = cdusBatchUploadDataValidator;
    String loginName = "Abstractor: test";
    CSMUserUtil mockcsm = mock(CSMUserUtil.class);
    OrganizationEntityServiceRemote mockorg = mock(OrganizationEntityServiceRemote.class);
    
    IdentifiedOrganizationCorrelationServiceRemote ioRemote = mock(IdentifiedOrganizationCorrelationServiceRemote.class);

    @Before
    public void init () throws Exception {
        when(mockcsm.isUserInGroup(anyString(), anyString())).thenReturn(true);
        CSMUserService.setInstance(mockcsm);
        when(mockorg.search(any(OrganizationSearchCriteriaDTO.class), any(LimitOffset.class))).thenAnswer(
                new Answer<List<OrganizationDTO>>() {
            public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
                result.add(new OrganizationDTO());
                return result;
            }
        });
        when(poServiceLoc.getOrganizationEntityService()).thenReturn(mockorg);
        
        when(poServiceLoc.getIdentifiedOrganizationEntityService()).thenReturn(ioRemote);
        

        RegistryUser ru = new RegistryUser();
        User cu = new User();
        ru.setCsmUser(cu);
        v.setRu(ru);
    }

    @Test
    public void  testInitializeOrganizationListsSuAbstractor() throws Exception {
        v.initializeOrganizationLists();
        assertEquals(4, v.getListOfBlankIds().size());
        assertTrue(v.getListOfBlankIds().containsKey(null));
        assertTrue(v.getListOfBlankIds().containsKey(""));
        assertTrue(v.getListOfBlankIds().containsKey("00000"));
        assertTrue(v.getListOfBlankIds().containsKey("CTSU"));
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        assertEquals(PAUtil.dateStringToDateTime("3/1/2014"), v.getBlanksOrgDate());
    }

    @Test
    public void  testInitializeOrganizationListsSubmitter() throws Exception {
        when(mockcsm.isUserInGroup(anyString(), anyString())).thenReturn(false);
        v.initializeOrganizationLists();
        assertEquals(0, v.getListOfBlankIds().size());
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        assertEquals(sdf.format(new Date(0L)), sdf.format(v.getBlanksOrgDate()));
    }

    @Test
    public void  testInitializeOrganizationListsError() throws Exception {
        when(paSvcLocator.getLookUpTableService().getPropertyValue(REJECT_BLANKS_DATE)).thenReturn("xyzzy");
        v.initializeOrganizationLists();
        assertEquals(4, v.getListOfBlankIds().size());
        assertTrue(v.getListOfBlankIds().containsKey(null));
        assertTrue(v.getListOfBlankIds().containsKey(""));
        assertTrue(v.getListOfBlankIds().containsKey("00000"));
        assertTrue(v.getListOfBlankIds().containsKey("CTSU"));
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        assertEquals(sdf.format(new Date(0L)), sdf.format(v.getBlanksOrgDate()));
    }
    
    @Test
    public void testIsCorrectOrganizationIdNullifiedPoId() throws Exception {
        v.initializeOrganizationLists();

        NullifiedEntityException ex = new NullifiedEntityException(
                IiConverter.convertToPoOrganizationIi("12345"),
                IiConverter.convertToPoOrganizationIi("67890"));
        when(
                mockorg.getOrganization(eq(IiConverter
                        .convertToPoOrganizationIi("12345")))).thenThrow(ex);
        
        OrganizationDTO dupeOrg = new OrganizationDTO();
        dupeOrg.setIdentifier(IiConverter
                        .convertToPoOrganizationIi("67890"));
        dupeOrg.setName(EnOnConverter.convertToEnOn("Mayo Clinic"));
        when(
                mockorg.getOrganization(eq(IiConverter
                        .convertToPoOrganizationIi("67890")))).thenReturn(dupeOrg);
        
        IdentifiedOrganizationDTO io = new IdentifiedOrganizationDTO();
        Ii ctepID = new Ii();
        ctepID.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepID.setExtension("DUPE01");
        io.setAssignedId(ctepID);
        
        Ii playerID= new Ii();
        playerID.setExtension("67890");
        io.setPlayerIdentifier(playerID);        
        when(ioRemote.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(Arrays.asList(io));

        assertFalse(v.isCorrectOrganizationId("12345", null, false));
        BatchFileErrors bfErrors = v.getBfErrors();
        String msg = bfErrors.toString();
        System.out.print(msg);
        
        assertTrue(msg.contains("This organization's record has been nullified and merged with another organization"));
        assertTrue(msg.contains("The new organization is:"));
        assertTrue(msg.contains("Name: Mayo Clinic"));
        assertTrue(msg.contains("PO ID: 67890"));
        assertTrue(msg.contains("CTEP ID: DUPE01"));
    }
    
    @Test
    public void testIsCorrectOrganizationIdNullifiedCtepId() throws Exception {
        v.initializeOrganizationLists();
        
        when(
                mockorg.getDuplicateOfNullifiedOrg(eq("MN001"))).thenReturn(IiConverter
                        .convertToPoOrganizationIi("67890"));
        
        OrganizationDTO dupeOrg = new OrganizationDTO();
        dupeOrg.setIdentifier(IiConverter
                        .convertToPoOrganizationIi("67890"));
        dupeOrg.setName(EnOnConverter.convertToEnOn("Mayo Clinic"));
        when(
                mockorg.getOrganization(eq(IiConverter
                        .convertToPoOrganizationIi("67890")))).thenReturn(dupeOrg);
        
        IdentifiedOrganizationDTO io = new IdentifiedOrganizationDTO();
        Ii ctepID = new Ii();
        ctepID.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepID.setExtension("DUPE01");
        io.setAssignedId(ctepID);
        
        Ii playerID= new Ii();
        playerID.setExtension("67890");
        io.setPlayerIdentifier(playerID);        
        when(ioRemote.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(Arrays.asList(io));

        assertFalse(v.isCorrectOrganizationId("MN001", null, false));
        BatchFileErrors bfErrors = v.getBfErrors();
        String msg = bfErrors.toString();
        System.out.print(msg);
        
        assertTrue(msg.contains("This organization's record has been nullified and merged with another organization"));
        assertTrue(msg.contains("The new organization is:"));
        assertTrue(msg.contains("Name: Mayo Clinic"));
        assertTrue(msg.contains("PO ID: 67890"));
        assertTrue(msg.contains("CTEP ID: DUPE01"));
    }
    
}
