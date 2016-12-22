package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PendingPatientAccrualsServiceBeanTest extends AbstractHibernateTestCase {
	
	private PendingPatientAccrualsServiceBean bean;
	private ParticipatingOrgServiceBean partOrgSrv = new ParticipatingOrgServiceBean();
    private StudySiteAccrualStatusServiceLocal ssas;
    private PAHealthCareProviderLocal paHcp;
	private LookUpTableServiceRemote lookUpTableService;
    private MailManagerServiceLocal mailManagerSvc = mock(MailManagerServiceLocal.class);
    private StudyProtocolServiceLocal spSvc = mock(StudyProtocolServiceLocal.class);
    private ServiceLocator priorServiceLocator;
	
	@Before
    public void setup() throws Exception {
        bean = new PendingPatientAccrualsServiceBean();
        lookUpTableService = mock(LookUpTableServiceRemote.class);
        List<Country> countryList = new ArrayList<Country>();
        Country c = new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        c.setId(1L);
        countryList.add(c);
		when(lookUpTableService.searchCountry(any(Country.class))).thenReturn(countryList);
		when(lookUpTableService.getPropertyValue("accrualjob.email.subject")).thenReturn("accrualjob subject");
		when(lookUpTableService.getPropertyValue("accrualjob.email.body")).thenReturn("${CurrentDate}, ${tableRows}");
		when(lookUpTableService.getPropertyValue("deletePendingAccruals.email.subject")).thenReturn("deletePendingAccruals subject");
		when(lookUpTableService.getPropertyValue("deletePendingAccruals.email.body")).thenReturn("deletePendingAccruals body ${CurrentDate}, ${tableRows}");
        
		ssas = mock(StudySiteAccrualStatusServiceLocal.class);
        when(ssas.getCurrentStudySiteAccrualStatus(any(Long[].class))).thenReturn(
                new HashMap<Long, StudySiteAccrualStatus>());
        paHcp = mock(PAHealthCareProviderLocal.class);
        when(paHcp.getPersonsByStudySiteId(any(Long[].class), any(String.class))).thenReturn(
                new HashMap<Long, List<PaPersonDTO>>());
        partOrgSrv.setStudySiteAccrualStatusService(ssas);
        partOrgSrv.setPaHealthCareProviderService(paHcp);
        
        bean.setPartOrgSrv(partOrgSrv);
        bean.setLookUpTableService(lookUpTableService);
        bean.setMailManagerService(mailManagerSvc);
        bean.setStudySiteAccrualAccessSrv(mock(StudySiteAccrualAccessServiceLocal.class));
        bean.setStudyProtocolSrv(spSvc);
        TestSchema.primeData();
        PoServiceLocator poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        IdentifiedOrganizationCorrelationServiceRemote identifierOrganizationSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrganizationSvc);
        when(identifierOrganizationSvc.getCorrelationsByPlayerIdsWithoutLimit(any(Long[].class))).thenReturn(new ArrayList<IdentifiedOrganizationDTO>());
        when(identifierOrganizationSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(new ArrayList<IdentifiedOrganizationDTO>());
        OrganizationEntityServiceRemote poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        List<OrganizationDTO> orgDtos = new ArrayList<OrganizationDTO>();
        orgDtos.add(new OrganizationDTO());
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgDtos);
        
        priorServiceLocator = PaRegistry.getInstance().getServiceLocator();
        ServiceLocator paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        
        final StudySiteServiceLocal studySiteServiceLocal = mock(StudySiteServiceLocal.class);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteServiceLocal);
        
        final OrganizationCorrelationServiceRemote correlationServiceRemote = mock(OrganizationCorrelationServiceRemote.class);
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(correlationServiceRemote);
        
        PAServiceUtils paSrvUtil = mock(PAServiceUtils.class);
		bean.setPaServiceUtils(paSrvUtil);
        when(paSrvUtil.getCtepOrDcpId(any(Long.class), any(String.class))).thenReturn("test");
    }
	
	@After
	public void done() {
	    PaRegistry.getInstance().setServiceLocator(priorServiceLocator);
	}
	
	@Test
    public void testServiceMethods() throws Exception {
		Long spId = TestSchema.studyProtocolIds.get(0);
        Long ssId = TestSchema.studySiteIds.get(0);
        Map<Long, StudySiteAccrualStatus> rMap = new HashMap<Long, StudySiteAccrualStatus>();
        StudySiteAccrualStatus r = new StudySiteAccrualStatus();
        StudySite ss = new StudySite();
        ss.setId(ssId);
        r.setStudySite(ss);
        r.setStatusCode(RecruitmentStatusCode.ACTIVE);
        r.setStatusDate(new Timestamp((new Date()).getTime()));
        rMap.put(ssId, r);
        when(ssas.getCurrentStudySiteAccrualStatus(any(Long[].class))).thenReturn(rMap);

        Map<Long, List<PaPersonDTO>> pimap = new HashMap<Long, List<PaPersonDTO>>();
        List<PaPersonDTO> pis = new ArrayList<PaPersonDTO>();
        pis.add(new PaPersonDTO());
        pimap.put(ssId, pis);
        when(paHcp.getPersonsByStudySiteId(any(Long[].class),
                eq(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName()))).thenReturn(pimap);
        
        User user = new User();
        user.setLoginName("SuAbstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        TestSchema.addUpdObject(user);
        
        RegistryUser ru =  new RegistryUser();
        ru.setCsmUser(user);
        ru.setFirstName("Joe");
        ru.setLastName("Smith");
        ru.setEmailAddress("test@test.com");
        TestSchema.addUpdObject(ru);
        
        AccrualDisease ad = new AccrualDisease();
        ad.setCodeSystem("ICD");
        ad.setDiseaseCode("1111");
        ad.setPreferredName("11111");
        ad.setDisplayName("11111");
        TestSchema.addUpdObject(ad);
        
        PatientStage ps = new PatientStage();
        ps.setStudyProtocolIdentifier(spId);
        ps.setStudyIdentifier("NCI-2009-00002");
        ps.setAssignedIdentifier("testassignedIdentifier");        
        ps.setCountryCode("US");
        ps.setDiseaseCode(ad.getId().toString());
        ps.setStudySite("1");
        ps.setUserLastCreated(user);
        ps.setFileName("fileName");
        TestSchema.addUpdObject(ps);
        
        ps = new PatientStage();
        ps.setStudyProtocolIdentifier(spId);
        ps.setStudyIdentifier("NCI-2009-00002");
        ps.setAssignedIdentifier("testassignedIdentifier2");        
        ps.setCountryCode("US");
        ps.setDiseaseCode(ad.getId().toString());
        ps.setStudySite("2013");
        ps.setUserLastCreated(user);
        ps.setFileName("fileName");
        TestSchema.addUpdObject(ps);
        
        ps = new PatientStage();
        ps.setStudyProtocolIdentifier(TestSchema.studyProtocolIds.get(1));
        ps.setStudyIdentifier("NCI-2013-00002");
        ps.setAssignedIdentifier("test2");        
        ps.setCountryCode("US");
        ps.setDiseaseCode(ad.getId().toString());
        ps.setStudySite("1");
        ps.setUserLastCreated(user);
        ps.setFileName("fileName2");
        TestSchema.addUpdObject(ps);
        
        bean.setUseTestSeq(true);
        
        List<PatientStage> results = bean.getAllPatientsStage("'; drop table abc;");
        assertEquals(0, results.size());

        StudyProtocolDTO dto = new StudyProtocolDTO();
        Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        Ii spSecId = new Ii();
		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
		spSecId.setExtension("NCI-2009-00002");
		secondaryIdentifiers.add(spSecId);
		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
		when(spSvc.loadStudyProtocol(any(Ii.class))).thenReturn(dto);
		
        results = bean.getAllPatientsStage("NCI-2009-00002");
        assertEquals(2, results.size());        
        
        results = bean.getAllPatientsStage(null);
        assertEquals(3, results.size());
        
        bean.readAndProcess();
        
        List<Long> deleteIds = new ArrayList<Long>();
        deleteIds.add(results.get(0).getId());
        bean.deletePatientStage(deleteIds);
		
	}

}
