/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorOptions;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.service.MockPAOrganizationService;
import gov.nih.nci.registry.service.MockPAPersonServiceRemote;
import gov.nih.nci.registry.service.MockProtocolQueryService;
import gov.nih.nci.registry.util.ComparableOrganizationDTO;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.Action;

/**
 * @author Vrushali
 *
 */
public class SearchTrialActionTest extends AbstractHibernateTestCase {
    private static final int MAX_CACHE_SIZE = 10;
    private SearchTrialAction action;
    private RegistryUserServiceLocal registryUserService;
    private CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService = mock(CTGovXmlGeneratorServiceLocal.class);
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService = mock(TSRReportGeneratorServiceRemote.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService = mock(AccrualDiseaseTerminologyServiceRemote.class);
    /**
     * Initialization.
     * @throws Exception in case of error
     */
    @Before
    public void setup() throws Exception {
        Organization org = new Organization();
        org.setIdentifier("1");
        org.setCity("city");
        org.setCountryName("countryName");
        org.setName("name");
        org.setPostalCode("postalCode");

        Person person = new Person();
        person.setIdentifier("1");
        person.setFirstName("firstName");
        person.setLastName("lastName");

        PAContactDTO contactDTO = new PAContactDTO();
        contactDTO.setFullName("Contact User");

        CorrelationUtilsRemote correlationUtils = mock(CorrelationUtilsRemote.class);
        when(correlationUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
        when(correlationUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(correlationUtils.getContactByPAOrganizationalContactId(any(Long.class))).thenReturn(contactDTO);

        action = new SearchTrialAction();
        action.prepare();
        TrialUtil trialUtils = new TrialUtil();
        trialUtils.setCorrelationUtils(correlationUtils);
        action.setTrialUtils(trialUtils);

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("firstName");
        request.setSession(session);

        ServletActionContext.setRequest(request);
        action.setServletRequest(request);

        HttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);

        CSMUserService.setInstance(new MockCSMUserService());
        registryUserService = mock(RegistryUserServiceLocal.class);
        action.setRegistryUserService(registryUserService);
        action.setCtGovXmlGeneratorService(ctGovXmlGeneratorService);
        action.setTsrReportGeneratorService(tsrReportGeneratorService);
        action.setAccrualDiseaseTerminologyService(accrualDiseaseTerminologyService);
    }

    @Test
    public void testRecordsProperty(){
        assertNull(action.getRecords());
    }

    @Test
    public void testShowCriteria(){
        assertEquals("criteria", action.showCriteria());
    }

    
    
    @Test
    public void testQueryErr(){
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("abc");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("avs");
        action.setCriteria(criteria );
        assertEquals("error", action.query());
    }

    @Test
    public void testQueryInCtGov(){
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("NCT01861054");
        criteria.setIdentifierType("NCT");        
        action.setCriteria(criteria);
        assertEquals("ctgovimport", action.query());
        assertNotNull(action.getStudy());
        assertEquals("NCT01861054", action.getStudy().getNctId());
    }
    
    @Test
    public void testImportCtGovTrial(){
        UsernameHolder.setUserCaseSensitive("firstName");
        
        action.prepare();
        action.setNctIdToImport("NCT01861054");
        assertFalse(action.isShowAddMySite());
        assertEquals("view", action.importTrial());
        assertNotNull(action.getStudy());
        assertEquals("NCT01861054", action.getStudy().getNctId());
        assertTrue(action.isShowAddMySite());
    }
    
    @Test
    public void testQuery(){
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setPhaseCode("");
        criteria.setPrimaryPurposeCode("");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }
    

    @Test
    public void testQueryWithException() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("ThrowException");
        criteria.setOrganizationType("");
        action.setCriteria(criteria);
        assertEquals("error", action.query());
    }

    @Test
    public void testQueryNCIType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("a");
        criteria.setIdentifierType("NCI");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setOrganizationId("");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryNCTType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("nct");
        criteria.setIdentifierType("NCT");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryLeadOrgType() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("lead");
        criteria.setIdentifierType("Lead Organization");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("organizationType");
        criteria.setOrganizationId("1");
        criteria.setParticipatingSiteId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testQueryPI() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setPrincipalInvestigatorId("1");
        action.setCriteria(criteria);
        assertEquals("success", action.query());
    }

    @Test
    public void testExecute() throws PAException {
        action.setTrialAction("");
        assertEquals("success", action.execute());

        action.setTrialAction("view");
        assertEquals("success", action.execute());

        ServletActionContext.getRequest().getSession().setAttribute("protocolId", "1");
        action.setTrialAction("submit");
        Set<RegistryUser> trialOwners = new HashSet<RegistryUser>();
        RegistryUser user = new RegistryUser();
        User csmUser = new User();
        csmUser.setFirstName("firstName");
        csmUser.setEmailId("emailId@gmail.com");
        csmUser.setLastName("lastName");
        csmUser.setLoginName("loginName");
        user.setCsmUser(csmUser);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        trialOwners.add(user);
        when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
        assertEquals("view", action.execute());

        action.setTrialAction("amend");
        assertEquals("view", action.execute());

        action.setTrialAction("update");
        assertEquals("view", action.execute());

    }

    @Test
    public void testView() throws PAException {
        action.setStudyProtocolId(1L);
        registryUserService = mock(RegistryUserServiceLocal.class);
        action.setRegistryUserService(registryUserService);
        when(registryUserService.hasTrialAccess("user",action.getStudyProtocolId())).thenReturn(false);
        assertEquals("view", action.view());
    }

    @Test
    public void testViewProp() throws PAException {
        action.setStudyProtocolId(1L);
        registryUserService = mock(RegistryUserServiceLocal.class);
        action.setRegistryUserService(registryUserService);
        action.setStudyProtocolService(studyProtocolService);
        when(registryUserService.hasTrialAccess("user",action.getStudyProtocolId())).thenReturn(true);
        StudyProtocolDTO protocolDTO = new StudyProtocolDTO();
        protocolDTO.setIdentifier(IiConverter.convertToIi(action.getStudyProtocolId()));
        protocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(protocolDTO);
        assertEquals("view", action.view());
    }
    
    @Test
    public void testViewDoc() throws Exception {
        action.setIdentifier(1L);
        assertEquals(Action.NONE, action.viewDoc());
    }

    @Test
    public void testGetMyPartiallySavedTrial() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("officialTitle");
        action.setCriteria(criteria);
        assertEquals("success", action.getMyPartiallySavedTrial());

        criteria = new SearchProtocolCriteria();
        criteria.setOfficialTitle("ThrowException");
        action.setCriteria(criteria);
        assertEquals("success", action.getMyPartiallySavedTrial());
        assertNotNull(action.getActionErrors());
    }

    @Test
    public void testPartiallySubmittedView() {
        assertEquals("error", action.partiallySubmittedView());

        action.setStudyProtocolId(1L);
        assertEquals("partialView", action.partiallySubmittedView());
    }


    @Test
    public void testViewXml() throws IOException, PAException {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setNciIdentifier("NCI-2009-00001");
        queryCriteria.setCtgovXmlRequiredIndicator("true");
        ServletActionContext.getRequest().getSession().setAttribute("studySearchCriteria", queryCriteria);
        assertEquals("error", action.viewXML());
        
        action.setStudyProtocolId(1L);
        Ii spIi = IiConverter.convertToStudyProtocolIi(action.getStudyProtocolId());
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi)).thenReturn("xmlData");
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS)).thenReturn("xmlData");
        String result = action.viewXML();
        assertEquals(null, result);
        HttpServletResponse response = ServletActionContext.getResponse();
        assertEquals("Wrong content type", "application/xml", response.getContentType());
        assertEquals("", "UTF-8", response.getCharacterEncoding());
        verify(ctGovXmlGeneratorService).generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
    }
    
    @Test
    public void testViewXmlException() throws IOException, PAException {
        action.setStudyProtocolId(1L);
        Ii spIi = IiConverter.convertToStudyProtocolIi(action.getStudyProtocolId());
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi)).thenThrow(new NullPointerException());
        when(ctGovXmlGeneratorService.generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS)).thenThrow(new NullPointerException());
        String result = action.viewXML();
        assertEquals("error", result);
        verify(ctGovXmlGeneratorService).generateCTGovXml(spIi,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
    }
    
    @Test
    public void testViewTSR() throws IOException, PAException {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setNciIdentifier("NCI-2009-00001");
        ServletActionContext.getRequest().getSession().setAttribute("studySearchCriteria", queryCriteria);
        queryCriteria.setStudyProtocolId(1L);
        String fileNameDateStr = DateFormatUtils.format(new Date(), PAConstants.TSR_DATE_FORMAT);
        assertEquals("error", action.viewTSR());
        action.setStudyProtocolId(1L);
        Ii spIi = IiConverter.convertToIi(action.getStudyProtocolId());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write("rtfData".getBytes());
        when(tsrReportGeneratorService.generateRtfTsrReport(any(Ii.class))).thenReturn(output);
        String result = action.viewTSR();
        assertEquals(null, result);
        MockHttpServletResponse response = (MockHttpServletResponse) ServletActionContext.getResponse();
        assertEquals("Wrong content type", "application/rtf;", response.getContentType());
        String contentDisposition = response.getHeader("Content-Disposition");
        assertTrue(contentDisposition.matches(".*" + fileNameDateStr + ".*"));
        verify(tsrReportGeneratorService).generateRtfTsrReport(spIi);
    }
    
    @Test
    public void testViewTSRFailure() {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setNciIdentifier("NCI-2009-00001");
        ServletActionContext.getRequest().getSession().setAttribute("studySearchCriteria", queryCriteria);
        assertEquals("error", action.viewTSR());
    }
    
    @Test
    public void testMyTrialsOnly() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setMyTrialsOnly(true);
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        action.setCriteria(criteria);

        assertEquals("success", action.query());
        assertTrue(action.getRecords().size() >= 1);
    }
    
    @Test
    public void testGetOrganizationsAssociatedWithStudyProtocol() throws PAException, InterruptedException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        initializeCache("CRITERIA_COLLECTIONS_CACHE", 3, 3);
        
        // note the instance equality operator. Do not use equals here.
        assertTrue(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertTrue(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));    
        
        // Point the mock at different collections.
        MockPAOrganizationService.leadPaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
        MockPAOrganizationService.sitePaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
        
        // now should be false because previous cached objects will be returned instead of above ones.
        assertFalse(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertFalse(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));
        
        Thread.sleep(7000);
        
        // cache has expired; so now we should get out new fresh objects back.
        assertTrue(MockPAOrganizationService.leadPaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        assertTrue(MockPAOrganizationService.sitePaOrganizationDTOs == action
                .getOrganizationsAssociatedWithStudyProtocol("Participating Site"));           
    }
    
    @Test
    public void testGetAllPrincipalInvestigators() throws PAException, InterruptedException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        initializeCache("CRITERIA_COLLECTIONS_CACHE", 3, 3);
        
        // note the instance equality operator. Do not use equals here.
        assertTrue(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());         
        
        // Point the mock at different collections.
        MockPAPersonServiceRemote.investigators = new ArrayList<PaPersonDTO>();        
        
        // now should be false because previous cached objects will be returned instead of above ones.
        assertFalse(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());        
        
        Thread.sleep(7000);
        
        // cache has expired; so now we should get out new fresh objects back.
        assertTrue(MockPAPersonServiceRemote.investigators == action
                .getAllPrincipalInvestigators());         
                 
    }
    
    @Test
    public void testGetLeadAndParticipatingOrganizations() throws PAException,
            IllegalAccessException, InvocationTargetException {
        SearchTrialAction searchTrialAction = mock(SearchTrialAction.class);

        PaOrganizationDTO org1 = new PaOrganizationDTO();
        org1.setId("1");
        org1.setName("o1");
        PaOrganizationDTO org2 = new PaOrganizationDTO();
        org2.setId("2");
        org2.setName("o2");
        PaOrganizationDTO org3 = new PaOrganizationDTO();
        org3.setId("3");
        org3.setName("o3");
        PaOrganizationDTO org4 = new PaOrganizationDTO();
        org4.setId("4");
        org4.setName("o4");
        PaOrganizationDTO org5 = new PaOrganizationDTO();
        org5.setId("1");
        org5.setName("o1");

        when(
                searchTrialAction
                        .getOrganizationsAssociatedWithStudyProtocol(eq("Lead Organization")))
                .thenReturn(
                        Arrays.asList(org4, org3, org3, org3, org3, org3, org3,
                                org5));
        when(
                searchTrialAction
                        .getOrganizationsAssociatedWithStudyProtocol(eq("Participating Site")))
                .thenReturn(
                        Arrays.asList(org4, org3, org1, org2, org4, org3, org1,
                                org2, org5));
        when(searchTrialAction.getLeadAndParticipatingOrganizations())
                .thenCallRealMethod();

        assertEquals(4, searchTrialAction
                .getLeadAndParticipatingOrganizations().size());
        assertEquals("1", new ArrayList<ComparableOrganizationDTO>(
                searchTrialAction.getLeadAndParticipatingOrganizations())
                .get(0).getId());
        assertEquals("2", new ArrayList<ComparableOrganizationDTO>(
                searchTrialAction.getLeadAndParticipatingOrganizations())
                .get(1).getId());
        assertEquals("3", new ArrayList<ComparableOrganizationDTO>(
                searchTrialAction.getLeadAndParticipatingOrganizations())
                .get(2).getId());
        assertEquals("4", new ArrayList<ComparableOrganizationDTO>(
                searchTrialAction.getLeadAndParticipatingOrganizations())
                .get(3).getId());

    }
    
    @Test
    public void testConvertIdentifierType_NCI() {
        SearchTrialAction action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("NCI_ID");
        criteria.setIdentifierType("NCI");
        action.setCriteria(criteria);
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        action.convertIdentifierType(queryCriteria);
        
        assertEquals("NCI_ID", queryCriteria.getNciIdentifier());
    }
    
    @Test
    public void testConvertIdentifierType_LeadOrg() {
        SearchTrialAction action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("LO_ID");
        criteria.setIdentifierType("Lead Organization");
        action.setCriteria(criteria);
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        action.convertIdentifierType(queryCriteria);
        
        assertEquals("LO_ID", queryCriteria.getLeadOrganizationTrialIdentifier());
    }
    
    @Test
    public void testConvertIdentifierType_NCT() {
        SearchTrialAction action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("NCT_ID");
        criteria.setIdentifierType("NCT");
        action.setCriteria(criteria);
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        action.convertIdentifierType(queryCriteria);
        
        assertEquals("NCT_ID", queryCriteria.getNctNumber());
    }
    
    @Test
    public void testConvertIdentifierType_Other() {
        SearchTrialAction action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("OTHER_ID");
        criteria.setIdentifierType("Other Identifier");
        action.setCriteria(criteria);
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        action.convertIdentifierType(queryCriteria);
        
        assertEquals("OTHER_ID", queryCriteria.getOtherIdentifier());
    }    
    
    @Test
    public void testConvertIdentifierType_AnyType() {
        SearchTrialAction action = new SearchTrialAction();
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("ANY_ID");
        criteria.setIdentifierType("All");
        action.setCriteria(criteria);
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        action.convertIdentifierType(queryCriteria);
        
        assertEquals("ANY_ID", queryCriteria.getAnyTypeIdentifier());
    }       
    
    @Test
    public void testQueryByAllIdentifiers() {
        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("ABC");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setPhaseCode("");
        criteria.setPrimaryPurposeCode("");
        action.setCriteria(criteria);

        assertEquals("error", action.query());
        assertEquals("error.search.identifierType", action.getFieldErrors()
                .get("criteria.identifierType").get(0));
        
        action.clearErrorsAndMessages();
        criteria.setIdentifierType("All");        
        assertEquals("success", action.query());

    }
    
    @Test
    public void testQueryCacheHit() throws PAException, InterruptedException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {

        initializeCache("SEARCH_RESULTS_CACHE", 5, 5);
        Cache cache = CacheUtils.getSearchResultsCache();
        cache.removeAll();

        SearchProtocolCriteria criteria = new SearchProtocolCriteria();
        criteria.setIdentifier("");
        criteria.setIdentifierType("");
        criteria.setOfficialTitle("");
        criteria.setOrganizationType("");
        criteria.setPhaseCode("");
        criteria.setPrimaryPurposeCode("");
        criteria.setMyTrialsOnly(true); // this will cause the mock to return
                                        // entire MockProtocolQueryService.list.
        List<StudyProtocolQueryDTO> searchResults = MockProtocolQueryService.list;
        action.setCriteria(criteria);

        StudyProtocolQueryCriteria studyProtocolQueryCriteria = action
                .convertToStudyProtocolQueryCriteria();
        String key = studyProtocolQueryCriteria.getUniqueCriteriaKey();

        // Make sure results are cached.
        ((MockHttpServletRequest) ServletActionContext.getRequest())
                .setMethod("POST");
        assertEquals("success", action.query());
        assertTrue(action.getRecords() == searchResults);
        Element element = cache.get((Object) key);
        assertNotNull(element);
        assertNotNull(element.getObjectValue());
        assertTrue(searchResults == element.getObjectValue());

        // Subsequent call as GET with same criteria should return cached
        // object.
        // Let's change the cached object.
        final ArrayList newSearchResults = new ArrayList();
        element = new Element(key, newSearchResults);
        cache.put(element);
        ((MockHttpServletRequest) ServletActionContext.getRequest())
                .setMethod("GET");
        assertEquals("success", action.query());
        assertFalse(action.getRecords() == searchResults);
        element = cache.get((Object) key);
        assertNotNull(element);
        assertNotNull(element.getObjectValue());
        assertTrue(newSearchResults == element.getObjectValue());

        // A change in search criteria should result in a cache miss and in a
        // new cache entry.
        criteria.setOfficialTitle("title");
        ((MockHttpServletRequest) ServletActionContext.getRequest())
                .setMethod("GET");
        assertEquals("success", action.query());
        assertTrue(action.getRecords() == searchResults);
        final String newKey = action.convertToStudyProtocolQueryCriteria()
                .getUniqueCriteriaKey();
        element = cache.get((Object) newKey);
        assertFalse(newKey.equals(key));
        assertNotNull(element.getObjectValue());
        assertTrue(searchResults == element.getObjectValue());

        // Make sure items in cache expire.
        Thread.sleep(10000);
        element = cache.get((Object) key);
        assertNull(element);
        element = cache.get((Object) newKey);
        assertNull(element);

        // Make sure the cache is limited in size.
        cache.removeAll();
        for (int i = 0; i < 100; i++) {
            criteria.setOfficialTitle("title" + i);
            ((MockHttpServletRequest) ServletActionContext.getRequest())
                    .setMethod("POST");
            assertEquals("success", action.query());
        }
        assertEquals(MAX_CACHE_SIZE, cache.getSize());

    }
    @Test
    public void testQueryShow() throws PAException, InterruptedException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {

            Cache cache = CacheUtils.getSearchResultsCache();
            cache.removeAll();

            SearchProtocolCriteria criteria = new SearchProtocolCriteria();
            criteria.setIdentifier("");
            criteria.setIdentifierType("");
            criteria.setOfficialTitle("");
            criteria.setOrganizationType("");
            criteria.setPhaseCode("");
            criteria.setPrimaryPurposeCode("");
            criteria.setMyTrialsOnly(true); // this will cause the mock to return
                                            // entire MockProtocolQueryService.list.
            List<StudyProtocolQueryDTO> searchResults = MockProtocolQueryService.list;
            StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
            dto.setLeadOrganizationId(3L);
            dto.setStudyProtocolId(3L);
            dto.setNciIdentifier("NCI-2009-00015");
            dto.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier3");
            dto.setOfficialTitle("officialTitle test");
            dto.setStudyStatusCode(StudyStatusCode.ACTIVE);
            dto.setProprietaryTrial(false);
            dto.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
            dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
            dto.setCtgovXmlRequiredIndicator(true);
            dto.setSummary4FundingSponsorType(SummaryFourFundingCategoryCode.INSTITUTIONAL.getName());
            dto.getLastCreated().setUserLastCreated("firstName");
            dto.setSearcherTrialOwner(true);
            dto.setCurrentUserIsSiteOwner(true);
            searchResults.add(dto);
            action.setCriteria(criteria);

            StudyProtocolQueryCriteria studyProtocolQueryCriteria = action
                    .convertToStudyProtocolQueryCriteria();
            String key = studyProtocolQueryCriteria.getUniqueCriteriaKey();

            // Make sure results are cached.
            ((MockHttpServletRequest) ServletActionContext.getRequest())
                    .setMethod("POST");
            assertEquals("success", action.query());
            assertTrue(action.getRecords() == searchResults);
    }

    private void initializeCache(String name, int ttl, int tti)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = CacheUtils.class.getDeclaredField("CACHE_MANAGER");
        field.setAccessible(true);
        CacheManager cacheManager = (CacheManager) field.get(null);
        cacheManager.removeCache(name);
        Cache cache = new Cache(name, MAX_CACHE_SIZE, false, false, ttl, tti);
        cacheManager.addCache(cache);
    }   
    @Test
    public void testSaveAccrualDiseaseCode() throws PAException {
        when(accrualDiseaseTerminologyService.canChangeCodeSystem(any(Long.class))).thenReturn(true);
        action.setAccrualDiseaseTerminology("SDC");
        List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setStudyProtocolId(1L);
        action.setRecords(records);
        assertEquals("success", action.saveAccrualDiseaseCode());
    }
}
