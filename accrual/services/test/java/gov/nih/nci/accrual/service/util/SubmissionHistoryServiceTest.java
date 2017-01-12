package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SubmissionHistoryServiceTest extends AbstractServiceTest<SubmissionHistoryService> {

    private RegistryUser user;
    private RegistryUser otherUser;
    private StudyProtocolDTO sp;
    private OrganizationEntityServiceRemote oes;
    private FamilyServiceRemote fs;
    private OrganizationCorrelationServiceRemote ocSvc;
    private StudySiteServiceLocal ssSvc;

    @Before
    public void setupFamilyHelper() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
    }

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        SubmissionHistoryBean shb = new SubmissionHistoryBean();
        shb.setSearchTrialSvc(new SearchTrialBean());
        bean = shb;

        ServiceLocatorPaInterface slPa = mock(ServiceLocatorPaInterface.class);
        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        sp = new StudyProtocolDTO();
        sp.setIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        when(spSvc.getStudyProtocol(any(Ii.class))).thenReturn(sp);
        when(slPa.getStudyProtocolService()).thenReturn(spSvc);
        PaServiceLocator.getInstance().setServiceLocator(slPa);

        ServiceLocator sl = mock(ServiceLocator.class);
        ocSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(sl.getOrganizationCorrelationService()).thenReturn(ocSvc);

        ssSvc = mock(StudySiteServiceLocal.class);
        when(ssSvc.getOrganizationByStudySiteId(anyLong())).thenAnswer(new Answer<Organization>() {
            @Override
            public Organization answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Organization result = null;
                for (StudySite ss : TestSchema.studySites) {
                    if (ss.getId().equals(args[0])) {
                        if (ss.getHealthCareFacility() != null) {
                            result = ss.getHealthCareFacility().getOrganization();
                        }
                        if (ss.getResearchOrganization() != null) {
                            result = ss.getHealthCareFacility().getOrganization();
                        }
                    }
                }
                return result;
            }
        });
        when(sl.getStudySiteService()).thenReturn(ssSvc);
        
        PaRegistry.getInstance().setServiceLocator(sl);
        user = TestSchema.registryUsers.get(0);

        User otherCsmUser = TestSchema.createUser();
        otherUser = new RegistryUser();
        otherUser.setFirstName("Test");
        otherUser.setLastName("User");
        otherUser.setEmailAddress("test@example.com");
        otherUser.setPhone("123-456-7890");
        otherUser.setCsmUser(otherCsmUser);
        otherUser.setAffiliatedOrganizationId(TestSchema.organizations.get(1).getId());
        TestSchema.addUpdObject(otherUser);
    }

    @Test
    public void searchNoDataTest() throws Exception {
        List<HistoricalSubmissionDto> rList = bean.search(null, null, null);
        assertTrue(rList.isEmpty());
        rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());
    }

    @Test
    public void searchGuiAbbreviatedTest() throws Exception {
        // entered through UI
        StudySiteSubjectAccrualCount dataGui = new StudySiteSubjectAccrualCount();
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui.setStudySite(TestSchema.studySites.get(1));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(dataGui);

        // entered through BATCH
        StudySiteSubjectAccrualCount dataBatch = new StudySiteSubjectAccrualCount();
        dataBatch.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        dataBatch.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataBatch.setStudySite(TestSchema.studySites.get(1));
        dataBatch.setDateLastUpdated(new Date());
        dataBatch.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(dataBatch);
        
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
    }

    @Test
    public void searchGuiCompleteTest() throws Exception {
        // entered through UI
        StudySubject dataGui = new StudySubject();
        dataGui.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui.setStudySite(TestSchema.studySites.get(1));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setUserLastUpdated(user.getCsmUser());
        dataGui.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui);

        // entered through BATCH
        StudySubject dataBatch = new StudySubject();
        dataBatch.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataBatch.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        dataBatch.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataBatch.setStudySite(TestSchema.studySites.get(1));
        dataBatch.setDateLastUpdated(new Date());
        dataBatch.setUserLastUpdated(user.getCsmUser());
        dataBatch.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataBatch);

        // deleted
        StudySubject dataDeleted = new StudySubject();
        dataDeleted.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        dataDeleted.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataDeleted.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataDeleted.setStudySite(TestSchema.studySites.get(1));
        dataDeleted.setDateLastUpdated(new Date());
        dataDeleted.setUserLastUpdated(user.getCsmUser());
        dataDeleted.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataBatch);
        
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
    }
    
    @Test
    public void searchBatchTest() throws Exception {
        BatchFile bf = new BatchFile();
        bf.setDateLastCreated(new Date());
        bf.setUserLastCreated(user.getCsmUser());
        bf.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf.setSubmitter(user);
        bf.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf);
 
        AccrualCollections ac = new AccrualCollections();
        ac.setBatchFile(bf);
        ac.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac.setResults("abccb");
        TestSchema.addUpdObject(ac);

        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
        HistoricalSubmissionDto dto = rList.get(0);
        assertEquals("NCI-2009-00001", dto.getNciNumber());
        assertEquals(bf.getId(), dto.getBatchFileIdentifier());
        assertEquals(bf.getDateLastCreated(), dto.getDate());
        assertTrue(dto.getFileName().contains(bf.getFileLocation()));
        assertEquals("No", dto.getResult());
        assertEquals(bf.getSubmissionTypeCode(), dto.getSubmissionType());
        assertEquals(AccrualUtil.getDisplayName(user), dto.getUsername());
    }

    @Test 
    public void searchWithDatesTest() throws Exception {
        Date early = PAUtil.dateStringToDateTime("1/1/2012");
        Date middle = PAUtil.dateStringToDateTime("1/10/2012");
        Date late = PAUtil.dateStringToDateTime("1/20/2012");
        
        // COMPLETE
        StudySubject dataGui1 = new StudySubject();
        dataGui1.setAssignedIdentifier("assignedId1");
        dataGui1.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui1.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui1.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui1.setStudySite(TestSchema.studySites.get(1));
        dataGui1.setDateLastUpdated(early);
        dataGui1.setUserLastUpdated(user.getCsmUser());
        dataGui1.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui1);
        StudySubject dataGui2 = new StudySubject();
        dataGui2.setAssignedIdentifier("assignedId2");
        dataGui2.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui2.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui2.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui2.setStudySite(TestSchema.studySites.get(1));
        dataGui2.setDateLastUpdated(middle);
        dataGui2.setUserLastUpdated(user.getCsmUser());
        dataGui2.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui2);

        // ABBREVIATED
        StudySiteSubjectAccrualCount abbrGui1 = new StudySiteSubjectAccrualCount();
        abbrGui1.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        abbrGui1.setStudyProtocol(TestSchema.studyProtocols.get(0));
        abbrGui1.setStudySite(TestSchema.studySites.get(1));
        abbrGui1.setDateLastUpdated(late);
        abbrGui1.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(abbrGui1);
        StudySiteSubjectAccrualCount abbrGui2 = new StudySiteSubjectAccrualCount();
        abbrGui2.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        abbrGui2.setStudyProtocol(TestSchema.studyProtocols.get(0));
        abbrGui2.setStudySite(TestSchema.studySites.get(1));
        abbrGui2.setDateLastUpdated(middle);
        abbrGui2.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(abbrGui2);

        // BATCH
        BatchFile bf1 = new BatchFile();
        bf1.setDateLastCreated(early);
        bf1.setUserLastCreated(user.getCsmUser());
        bf1.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf1.setSubmitter(user);
        bf1.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf1);
        AccrualCollections ac1 = new AccrualCollections();
        ac1.setBatchFile(bf1);
        ac1.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac1.setResults("abccb");
        TestSchema.addUpdObject(ac1);
        BatchFile bf2 = new BatchFile();
        bf2.setDateLastCreated(middle);
        bf2.setUserLastCreated(user.getCsmUser());
        bf2.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf2.setSubmitter(user);
        bf2.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf2);
        AccrualCollections ac2 = new AccrualCollections();
        ac2.setBatchFile(bf2);
        ac2.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac2.setResults("abccb");
        TestSchema.addUpdObject(ac2);

        List<HistoricalSubmissionDto> rList = bean.search(PAUtil.dateStringToTimestamp("1/5/2012"), 
                PAUtil.dateStringToTimestamp("1/15/2012"), user);
        assertEquals(3, rList.size());
        int completeCnt = 0;
        int abbrCnt = 0;
        int batchCnt = 0;
        for (HistoricalSubmissionDto dto : rList) {
            if (dto.getCompleteTrialId() != null) {
                completeCnt++;
                assertNotNull(dto.getAssignedIdentifier());
                assertNotNull(dto.getStudySubjectId());
            }
            if (dto.getAbbreviatedTrialId() != null) {
                abbrCnt++;
            }
            if (dto.getBatchFileIdentifier() != null) {
                batchCnt++;
            }
        }
        assertEquals(1, completeCnt);
        assertEquals(1, abbrCnt);
        assertEquals(1, batchCnt);
    }

    @Test
    public void getBatchResultTest() throws Exception {
        SubmissionHistoryBean b = (SubmissionHistoryBean) bean;
        
        AccrualCollections ac = new AccrualCollections();
        ac.setPassedValidation(false);
        assertEquals("No", b.getBatchResult(ac));
        
        ac.setPassedValidation(true);
        ac.setTotalImports(50);
        assertEquals("Yes", b.getBatchResult(ac));
        
        ac.setTotalImports(null);
        ac.setDateLastCreated(new Date());
        assertNull(b.getBatchResult(ac));
        
        ac.setDateLastCreated(PAUtil.dateStringToDateTime("7/23/2012"));
        assertEquals("No", b.getBatchResult(ac));
    }

    @Test
    public void showIfNonIndustrialAndLeadOrgTest() throws Exception {        
        clearAccess();
        grantAccess(1);        

        clearCache();
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        createSubjectSite2(otherUser);
        clearCache();
        rList = bean.search(null, null, user);
        assertFalse(rList.isEmpty());
    }

    @Test
    public void showIfNonIndustrialAndParticipatingSiteTest() throws Exception {
        clearAccess();
        grantAccess(4);
        createSubjectSite2(otherUser);

        clearCache();
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        // affiliate with site
        clearCache();
        rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        // affiliate with family
        affiliateWithFamily();
        clearCache();
        rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        // give accrual access
        grantAccess(2);
        clearCache();
        rList = bean.search(null, null, user);
        assertFalse(rList.isEmpty());
    }

    @Test
    public void showIfIndustrialAndAndParticipatingSiteTest() throws Exception {
        affiliateWithFamily();
        
        final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocols.get(3).getId());
        protocol.setProprietaryTrialIndicator(true);
        session.update(protocol);
        session.flush();
        
        clearAccess();
        grantAccess(4);
        createSubjectSite2(otherUser);

        clearCache();
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        StudySiteSubjectAccrualCount dataGui = new StudySiteSubjectAccrualCount();
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(protocol);
        dataGui.setStudySite(TestSchema.studySites.get(7));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(dataGui);
        clearCache();
        grantAccess(7);
        rList = bean.search(null, null, user);
        assertFalse(rList.isEmpty());
    }

    @Test
    public void showIfIndustrialAndLeadOrgTest() throws Exception {
        affiliateWithFamily();
        clearAccess();
        grantAccess(4);
        createSubjectSite2(otherUser);

        clearCache();
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        clearCache();
        rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocols.get(2).getId());
        protocol.setProprietaryTrialIndicator(true);
        session.update(protocol);
        session.flush();
        StudySiteSubjectAccrualCount dataGui = new StudySiteSubjectAccrualCount();
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(protocol);
        dataGui.setStudySite(TestSchema.studySites.get(6));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setUserLastUpdated(user.getCsmUser());
        TestSchema.addUpdObject(dataGui);
        clearCache();
        grantAccess(6);
        rList = bean.search(null, null, user);
        assertFalse(rList.isEmpty());
    }

    @Test
    public void showIfAffiliatedOrgNotPartOfFamilyTest() throws Exception {
        clearAccess();
        StudySubject dataGui = new StudySubject();
        dataGui.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(TestSchema.studyProtocols.get(3));
        dataGui.setStudySite(TestSchema.studySites.get(8));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setUserLastUpdated(otherUser.getCsmUser());
        dataGui.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui);        
        
        clearCache();
        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());

        // create another submitter with the same affiliation as user
        User otherCsmUser = TestSchema.createUser();
        RegistryUser ru = new RegistryUser();
        ru.setFirstName("Test");
        ru.setLastName("User");
        ru.setEmailAddress("test@example.com");
        ru.setPhone("123-456-7890");
        ru.setCsmUser(otherCsmUser);
        ru.setAffiliatedOrganizationId(user.getAffiliatedOrganizationId());
        TestSchema.addUpdObject(ru);
        //createSubjectSite2(ru);
        dataGui.setUserLastUpdated(ru.getCsmUser());
        TestSchema.addUpdObject(dataGui);
        grantAccess(7);
        clearCache();
        rList = bean.search(null, null, user);
        assertFalse(rList.isEmpty());
    }
    
    @Test
    public void junitCoverage()  throws Exception {
    	User otherCsmUser = TestSchema.createUser();
    	RegistryUser ru = new RegistryUser();
    	ru.setFirstName("Test");
    	ru.setLastName("User");
    	ru.setEmailAddress("test@example.com");
    	ru.setPhone("123-456-7890");
    	ru.setCsmUser(otherCsmUser);
    	ru.setAffiliatedOrganizationId(user.getAffiliatedOrganizationId());
    	TestSchema.addUpdObject(ru);
    	createSubjectSite2(ru);
    	createSubjectSite2(otherUser);
    	clearCache();
    	List<HistoricalSubmissionDto> rList = bean.search(null, null, otherUser);
    	assertTrue(rList.isEmpty());
    	clearCache();
    	rList = bean.search(null, null, user);
    	assertFalse(rList.isEmpty());
    }

    private void createSubjectSite2(RegistryUser submitter) {
        StudySubject subject = new StudySubject();
        subject.setAssignedIdentifier("assignedId1");
        subject.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subject.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        subject.setStudyProtocol(TestSchema.studyProtocols.get(0));
        subject.setStudySite(TestSchema.studySites.get(2));
        subject.setDateLastUpdated(new Date());
        subject.setUserLastUpdated(submitter.getCsmUser());
        subject.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(subject);
    }

    private void affiliateWithFamily() throws Exception {
        OrganizationDTO org = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);

        Set<Ii> familySet = new HashSet<Ii>();
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        dset.setItem(familySet);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        when(fs.getActiveRelationships(anyLong())).thenReturn(getRelationships(
                new Long[] {user.getAffiliatedOrganizationId(), otherUser.getAffiliatedOrganizationId()}));
    }


    private void clearAccess() {
        SQLQuery qry = PaHibernateUtil.getCurrentSession().createSQLQuery("DELETE FROM study_site_accrual_access");
        qry.executeUpdate();
    }

    private void grantAccess(Integer site) {
        grantAccess(user, TestSchema.studySites.get(site));
        grantAccess(otherUser, TestSchema.studySites.get(site));
    }

    private void grantAccess(RegistryUser ru, StudySite ss) {
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(ru);
        ssaa.setStudySite(ss);
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(ssaa);
    }

    private FamilyDTO getPoFamilyDTO(Long id) {
        FamilyDTO family = new FamilyDTO();
        family.setIdentifier(IiConverter.convertToPoFamilyIi(id.toString()));
        family.setName(EnOnConverter.convertToEnOn("family" + id));
        return family;
    }

    private List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }

    private void clearCache() {
        Cache cache = ((SubmissionHistoryBean) bean).getTrialDataCache();
        cache.removeAll();
    }
}
