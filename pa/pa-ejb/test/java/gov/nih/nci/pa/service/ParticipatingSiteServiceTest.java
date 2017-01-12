package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ParticipatingSiteServiceTest extends AbstractHibernateTestCase {

    private final ParticipatingSiteBeanLocal bean = new ParticipatingSiteBeanLocal();
    private ParticipatingSiteServiceLocal localBean = null;
    private final ParticipatingSiteServiceBean rBean = new ParticipatingSiteServiceBean();
    private ParticipatingSiteServiceRemote remoteBean = null;
    private final StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();
    private final StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    private final StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();
    private final StudySiteAccrualStatusBeanLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    private final OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();
    private Long poSqn = 1L;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        PaRegistry.getInstance().setServiceLocator(new MockPaRegistryServiceLocator());
        bean.setStudyProtocolService(studyProtocolService);
        bean.setStudySiteService(studySiteService);
        bean.setStudySiteContactService(studySiteContactService);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setOrganizationCorrelationService(ocsr);
        bean.setAccrualAccessServiceLocal(mock(StudySiteAccrualAccessServiceLocal.class));
        localBean = bean;

        rBean.setStudyProtocolService(studyProtocolService);
        rBean.setStudySiteService(studySiteService);
        rBean.setStudySiteContactService(studySiteContactService);
        rBean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        rBean.setOrganizationCorrelationService(ocsr);
        rBean.setAccrualAccessServiceLocal(mock(StudySiteAccrualAccessServiceLocal.class));
        remoteBean = rBean;
        
        studySiteAccrualStatusService.setStudySiteAccrualAccessServiceLocal(mock(StudySiteAccrualAccessServiceLocal.class));        
    }

    private void setupPoMockito() throws Exception {
        PoServiceLocator poMock = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poMock);

        OrganizationEntityServiceRemote oeSvc = mock(OrganizationEntityServiceRemote.class);
        when(oeSvc.createOrganization(any(OrganizationDTO.class))).thenReturn(IiConverter.convertToPoOrganizationIi(String.valueOf(poSqn++)));
        when(oeSvc.getOrganization(any(Ii.class))).thenAnswer(new Answer<OrganizationDTO>() {
            @Override
            public OrganizationDTO answer(InvocationOnMock invocation) throws Throwable {
                Ii ii = (Ii) invocation.getArguments()[0];
                String id = ii.getExtension();
                OrganizationDTO result = new OrganizationDTO();
                result.setIdentifier(ii);
                result.setName(EnOnConverter.convertToEnOn(id));
                result.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
                result.setPostalAddress(AddressConverterUtil.create(id, id, id, "MD", "12345", "USA"));
                return result;
            }
        });
        when(poMock.getOrganizationEntityService()).thenReturn(oeSvc);

        HealthCareFacilityCorrelationServiceRemote hcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(hcfSvc.search(any(HealthCareFacilityDTO.class))).thenAnswer(new Answer<List<HealthCareFacilityDTO>>() {
            @Override
            public List<HealthCareFacilityDTO> answer(InvocationOnMock invocation) throws Throwable {
                List<HealthCareFacilityDTO> result = new ArrayList<HealthCareFacilityDTO>();
                HealthCareFacilityDTO criteria = (HealthCareFacilityDTO) invocation.getArguments()[0];
                if (!ISOUtil.isIiNull(criteria.getPlayerIdentifier())) {
                    Ii ii = IiConverter.convertToPoHealthCareFacilityIi(criteria.getPlayerIdentifier().getExtension());
                    HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
                    hcf.setIdentifier(DSetConverter.convertIiToDset(ii));
                    result.add(hcf);
                }
                return result;
            }
        });
        when(hcfSvc.getCorrelation(any(Ii.class))).thenAnswer(new Answer<HealthCareFacilityDTO>() {
            @Override
            public HealthCareFacilityDTO answer(InvocationOnMock invocation) throws Throwable {
                Ii ii = (Ii) invocation.getArguments()[0];
                String id = IiConverter.convertToString(ii);
                HealthCareFacilityDTO result = new HealthCareFacilityDTO();
                result.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealthCareFacilityIi(id)));
                result.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(id));
                result.setStatus(CdConverter.convertStringToCd("ACTIVE"));
                return result;
            }
        });
        when(poMock.getHealthCareFacilityCorrelationService()).thenReturn(hcfSvc);
    }

    private OrganizationDTO getOrg1() {
        List<String> telList = new ArrayList<String>();
        telList.add("111-222-3333");
        List<String> emailList = new ArrayList<String>();
        emailList.add("aaa@bbb.com");

        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("my org"));
        org.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        org.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville", "MD", "20855",
                                                         "USA"));
        org.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        org.setTelecomAddress(DSetConverter.convertListToDSet(emailList, "EMAIL", org.getTelecomAddress()));
        return org;
    }

    private PersonDTO getPerson1() {
        List<String> telList = new ArrayList<String>();
        telList.add("222-333-4444");
        List<String> emailList = new ArrayList<String>();
        emailList.add("bbb@ccc.com");

        PersonDTO person = new PersonDTO();
        person.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person.setName(EnPnConverter.convertToEnPn("first", "middle", "lastName", "prefix", "suffix"));
        person.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville", "MD",
                                                            "20855", "USA"));
        person.setSexCode(CdConverter.convertStringToCd("MALE"));
        person.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person.setTelecomAddress(DSetConverter.convertListToDSet(emailList, "EMAIL", person.getTelecomAddress()));
        return person;
    }

    private PersonDTO getPerson2() {
        List<String> telList = new ArrayList<String>();
        telList.add("333-444-5555");
        List<String> emailList = new ArrayList<String>();
        emailList.add("ccc@ddd.com");

        PersonDTO person2 = new PersonDTO();
        person2.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person2.setName(EnPnConverter.convertToEnPn("first2", "middle2", "lastName2", "prefix", "suffix"));
        person2.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville", "MD",
                                                             "20855", "USA"));
        person2.setSexCode(CdConverter.convertStringToCd("MALE"));
        person2.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person2.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person2.setTelecomAddress(DSetConverter.convertListToDSet(emailList, "EMAIL", person2.getTelecomAddress()));
        return person2;
    }

    private StudySiteDTO getBasicStudySiteDTO(Ii spIi) {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()
                                                                                       - Long.valueOf("300000000")),
                                                                               null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LOCAL SP ID"));
        studySiteDTO.setStudyProtocolIdentifier(spIi);
        return studySiteDTO;
    }

    private StudySiteAccrualStatusDTO getStatusDTO() {
        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.ACTIVE.getCode()));

        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime()
                - Long.valueOf("300000000"))));
        return currentStatus;
    }

    private Ii mimicCtepSynch(Ii hcfIi, String ctepExt) throws NullifiedRoleException, PAException,
            EntityValidationException {
        // mimic ctep synch and update

        HealthCareFacilityDTO hcfDTO = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(hcfIi);
        hcfDTO.setIdentifier(new DSet<Ii>());
        hcfDTO.getIdentifier().setItem(new HashSet<Ii>());
        Ii ctepIdForOrg = new Ii();
        ctepIdForOrg.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepIdForOrg.setExtension(ctepExt);
        ctepIdForOrg.setReliability(IdentifierReliability.ISS);
        hcfDTO.getIdentifier().getItem().add(ctepIdForOrg);
        PoRegistry.getHealthCareFacilityCorrelationService().updateCorrelation(hcfDTO);
        return ctepIdForOrg;
    }

    @Test
    public void createAndUpdateSiteForPropTrialTest() throws Exception {
        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00088", "CTEP_ID",
        		"DCP_ID", "NCT_ID", true);
        Ii spSecId = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00088");
        OrganizationDTO org = getOrg1();
        PersonDTO person = getPerson1();
        StudySiteDTO studySiteDTO = getBasicStudySiteDTO(spSecId);
        StudySiteAccrualStatusDTO currentStatus = getStatusDTO();

        ParticipatingSiteDTO dto = localBean.createStudySiteParticipant(studySiteDTO, currentStatus, org, null);
        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null, person,
                                           StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());

        StudySiteDTO freshSiteDTO = studySiteService.get(dto.getIdentifier());

        assertEquals("Treating Site", freshSiteDTO.getFunctionalCode().getCode());

        StudySiteAccrualStatusDTO freshAccStatus = studySiteAccrualStatusService
            .getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.ACTIVE.getCode(), freshAccStatus.getStatusCode().getCode());

        List<StudySiteContactDTO> contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(1, contList.size());

        // mimic ctep synch and update
        Ii ctepIdForOrg = mimicCtepSynch(freshSiteDTO.getHealthcareFacilityIi(), "TEST_CTEP_ORG1");

        // test update.
        studySiteDTO.setIdentifier(null);
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()
                                                                                       - Long.valueOf("300000000")),
                                                                               new Timestamp(new Date().getTime()
                                                                                       - Long.valueOf("200000000"))));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("changedLocalSp"));
        studySiteDTO.setStudyProtocolIdentifier(spSecId);
        currentStatus.setIdentifier(null);
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.COMPLETED.getCode()));

        PersonDTO person2 = getPerson2();
        DSet<Tel> dsetTel = new DSet<Tel>();
        Tel phone1 = new Tel();
        phone1.setValue(new URI("tel:" + URLEncoder.encode("111-111-1111", "UTF-8")));
        dsetTel.setItem(new HashSet<Tel>());
        dsetTel.getItem().add(phone1);
        Tel mail1 = new Tel();
        mail1.setValue(new URI("mailto:" + URLEncoder.encode("aaa@example.com", "UTF-8")));
        dsetTel.getItem().add(mail1);

        Ii oldIi = localBean.getParticipatingSiteIi(spSecId, ctepIdForOrg);
        studySiteDTO.setIdentifier(oldIi);

        localBean.updateStudySiteParticipant(studySiteDTO, currentStatus);
        try {
            localBean.addStudySitePrimaryContact(oldIi, null, null, person2, null);
            fail();
        } catch (PAException e) {
            // expected
        }
        localBean.addStudySitePrimaryContact(oldIi, null, null, person2, dsetTel);

        StudySiteDTO fresherSiteDTO = studySiteService.get(dto.getIdentifier());
        assertEquals("changedLocalSp", fresherSiteDTO.getLocalStudyProtocolIdentifier().getValue());

        StudySiteAccrualStatusDTO fresherAccStatus = studySiteAccrualStatusService
            .getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.COMPLETED.getCode(), fresherAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(2, contList.size());

        List<ParticipatingSiteDTO> sites = remoteBean.getParticipatingSitesByStudyProtocol(spSecId);
        assertTrue(sites.size() == 1);
        assertEquals(oldIi.getExtension(), sites.get(0).getIdentifier().getExtension());
        
        ParticipatingSiteContactDTO participatingSiteContactDTO = new ParticipatingSiteContactDTO();
        participatingSiteContactDTO.setPersonDTO(person);
        StudySiteContactDTO studySiteContactDTO = contList.get(0);
        
		List<String> phones = new ArrayList<String>();
        phones.add("1234567890");
		DSet<Tel> dSet = new DSet<Tel>();
        dSet = DSetConverter.convertListToDSet(phones, "PHONE", dSet);
        studySiteContactDTO.setTelecomAddresses(dSet);
		participatingSiteContactDTO.setStudySiteContactDTO(studySiteContactDTO);
		HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
        hcpDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealthcareProviderIi("1")));
        hcpDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));        
        participatingSiteContactDTO.setAbstractPersonRoleDTO(hcpDTO);		        
        List<ParticipatingSiteContactDTO> contacts = new ArrayList<ParticipatingSiteContactDTO>();
        contacts.add(participatingSiteContactDTO);        
        when(PaRegistry.getRegistryUserService().isTrialOwner(any(Long.class), any(Long.class))).thenReturn(true);
        when(PaRegistry.getRegistryUserService().getUser(any(String.class))).thenReturn(new RegistryUser());        
        try {
        	remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, contacts);
        	fail();
        } catch (Exception e) {
            // expected to have rolecode
        }        
        studySiteContactDTO.setRoleCode(CdConverter
                .convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
        try {
        	remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, contacts);
        	fail();
        } catch (Exception e) {
            // expected to have primary indicator
        }        
        studySiteContactDTO.setPrimaryIndicator(BlConverter.convertToBl(true));
        remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, contacts);
        
        OrganizationalContactDTO ocDTO = new OrganizationalContactDTO();
        ocDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoOrganizationalContactIi("1")));
        ocDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        ocDTO.setTitle(StConverter.convertToSt("Title"));
        ocDTO.setTypeCode(CdConverter.convertStringToCd("Site"));
        participatingSiteContactDTO.setAbstractPersonRoleDTO(ocDTO);
        remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, contacts);
        
        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        crsDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("1")));
        crsDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        participatingSiteContactDTO.setAbstractPersonRoleDTO(crsDTO);
        remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, contacts);
        
        StudyProtocolDTO spdto = null;
        rBean.setStudyProtocolService(mock(StudyProtocolBeanLocal.class));
        bean.setStudyProtocolService(mock(StudyProtocolBeanLocal.class));
        when(bean.getStudyProtocolService().getStudyProtocol(any(Ii.class))).thenReturn(spdto);
        when(rBean.getStudyProtocolService().getStudyProtocol(any(Ii.class))).thenReturn(spdto);
        localBean = bean;
        remoteBean = rBean;
        try {
        	remoteBean.updateStudySiteParticipant(studySiteDTO, currentStatus, null);
        	fail();
        } catch (Exception e) {
            // expected trial does not exist
        }        
    }

    @Test
    public void createAndUpdateSiteForNonPropTrialTest() throws Exception {
        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00099", "CTEP_ID",
                "DCP_ID", "NCT_ID", false);
        Ii spSecId = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00099");
        OrganizationDTO org = getOrg1();
        PersonDTO person = getPerson1();
        PersonDTO person2 = getPerson2();
        StudySiteDTO studySiteDTO = getBasicStudySiteDTO(spSecId);
        StudySiteAccrualStatusDTO currentStatus = getStatusDTO();

        ParticipatingSiteDTO dto = localBean.createStudySiteParticipant(studySiteDTO, currentStatus, org, null);
        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null, person,
                                           StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode());
        List<StudySiteContactDTO> contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(1, contList.size());

        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null, person2,
                                           StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode());
        List<ClinicalResearchStaffDTO> crsDTOs = PoRegistry.getClinicalResearchStaffCorrelationService()
            .getCorrelationsByPlayerIds(new Ii[] {person2.getIdentifier() });
        List<HealthCareProviderDTO> hcpDTOs = PoRegistry.getHealthCareProviderCorrelationService()
            .getCorrelationsByPlayerIds(new Ii[] {person2.getIdentifier() });
        localBean.addStudySitePrimaryContact(dto.getIdentifier(), crsDTOs.get(0), hcpDTOs.get(0), null,
                                             person2.getTelecomAddress());

        StudySiteDTO freshSiteDTO = studySiteService.get(dto.getIdentifier());

        assertEquals("Treating Site", freshSiteDTO.getFunctionalCode().getCode());

        StudySiteAccrualStatusDTO freshAccStatus = studySiteAccrualStatusService
            .getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.ACTIVE.getCode(), freshAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        // 1 princ, 1 sub, 1 prim
        assertEquals(3, contList.size());

        // mimic ctep synch and update
        Ii ctepIdForOrg = mimicCtepSynch(freshSiteDTO.getHealthcareFacilityIi(), "TEST_CTEP_ORG2");

        // test update.
        studySiteDTO.setIdentifier(null);
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()
                                                                                       - Long.valueOf("300000000")),
                                                                               new Timestamp(new Date().getTime()
                                                                                       - Long.valueOf("200000000"))));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("changedLocalSp"));
        studySiteDTO.setStudyProtocolIdentifier(spSecId);
        currentStatus.setIdentifier(null);
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.COMPLETED.getCode()));

        Ii oldIi = localBean.getParticipatingSiteIi(spSecId, ctepIdForOrg);
        studySiteDTO.setIdentifier(oldIi);
        DSet<Tel> dsetTel = new DSet<Tel>();
        Tel phone1 = new Tel();
        phone1.setValue(new URI("tel:" + URLEncoder.encode("111-111-1111", "UTF-8")));
        dsetTel.setItem(new HashSet<Tel>());
        dsetTel.getItem().add(phone1);
        Tel mail1 = new Tel();
        mail1.setValue(new URI("mailto:" + URLEncoder.encode("aaa@example.com", "UTF-8")));
        dsetTel.getItem().add(mail1);
        localBean.updateStudySiteParticipant(studySiteDTO, currentStatus);
        localBean.addStudySitePrimaryContact(oldIi, null, null, person, dsetTel);

        StudySiteDTO fresherSiteDTO = studySiteService.get(dto.getIdentifier());
        assertEquals("changedLocalSp", fresherSiteDTO.getLocalStudyProtocolIdentifier().getValue());

        StudySiteAccrualStatusDTO fresherAccStatus = studySiteAccrualStatusService
            .getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.COMPLETED.getCode(), fresherAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(3, contList.size());
        // check that the primary contact has been overwritten w/ generic data.
        OrganizationalContactDTO orgDTO = new OrganizationalContactDTO();
        orgDTO.setTitle(StConverter.convertToSt("titleOrgContact"));

        List<String> emailList = new ArrayList<String>();
        List<String> telList = new ArrayList<String>();
        emailList.add("test@example.com");
        telList.add("123-456-7890");
        DSet<Tel> phoneAndEmail = new DSet<Tel>();
        phoneAndEmail = DSetConverter.convertListToDSet(emailList, "EMAIL", phoneAndEmail);
        phoneAndEmail = DSetConverter.convertListToDSet(telList, "PHONE", phoneAndEmail);

        orgDTO.setTelecomAddress(phoneAndEmail);
        orgDTO.setTypeCode(CdConverter.convertStringToCd("Site"));
        localBean.addStudySiteGenericContact(oldIi, orgDTO, true, phoneAndEmail);
        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(3, contList.size());
        for (StudySiteContactDTO item : contList) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(item.getRoleCode().getCode())) {
                assertNull(item.getClinicalResearchStaffIi());
                assertNull(item.getHealthCareProviderIi());
                assertNotNull(item.getOrganizationalContactIi());
            }
        }
    }

    @Test(expected = PAException.class)
    public void testNoIiForLocalUpdate() throws PAException {
        localBean.updateStudySiteParticipant(new StudySiteDTO(), new StudySiteAccrualStatusDTO());
    }

    @Test(expected = PAException.class)
    public void testNoIiForRemoteUpdate() throws PAException {
        remoteBean.updateStudySiteParticipant(new StudySiteDTO(), new StudySiteAccrualStatusDTO(), null);
    }


    /**
     * Tests validation of generic contacts
     * @throws Exception on error
     */
    @Test
    public void testGenericContactValidation() throws Exception {
        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00100", "CTEP_ID",
                "DCP_ID", "NCT_ID", false);
        Ii spSecId = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00100");
        OrganizationDTO org = getOrg1();
        PersonDTO person = getPerson1();
        PersonDTO person2 = getPerson2();
        StudySiteDTO studySiteDTO = getBasicStudySiteDTO(spSecId);
        StudySiteAccrualStatusDTO currentStatus = getStatusDTO();

        ParticipatingSiteDTO dto = localBean.createStudySiteParticipant(studySiteDTO, currentStatus, org, null);
        // localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null,
        // person, StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode());

        // localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null, person2,
        // StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode());
        // List<ClinicalResearchStaffDTO> crsDTOs =
        // PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelationsByPlayerIds(new
        // Ii[]{person2.getIdentifier()});
        // List<HealthCareProviderDTO> hcpDTOs =
        // PoRegistry.getHealthCareProviderCorrelationService().getCorrelationsByPlayerIds(new
        // Ii[]{person2.getIdentifier()});
        // localBean.addStudySitePrimaryContact(dto.getIdentifier(), crsDTOs.get(0), hcpDTOs.get(0), null,
        // person2.getTelecomAddress());

        StudySiteDTO freshSiteDTO = studySiteService.get(dto.getIdentifier());
        Ii ctepIdForOrg = mimicCtepSynch(freshSiteDTO.getHealthcareFacilityIi(), "TEST_CTEP_ORG3");
        Ii oldIi = localBean.getParticipatingSiteIi(spSecId, ctepIdForOrg);

        OrganizationalContactDTO orgDTO = new OrganizationalContactDTO();
        orgDTO.setTitle(StConverter.convertToSt("Generic Contact"));
        orgDTO.setTypeCode(CdConverter.convertStringToCd("Responsible Party"));

        try {
            localBean.addStudySiteGenericContact(oldIi, orgDTO, true, null);
            fail();
        } catch (PAException e) {
            // expected
        }

        orgDTO.setTypeCode(CdConverter.convertStringToCd("Site"));
        try {
            localBean.addStudySiteGenericContact(oldIi, orgDTO, true, null);
            fail();
        } catch (PAException e) {
            // expected
        }

        List<String> emailList = new ArrayList<String>();
        emailList.add("test@example.com");
        DSet<Tel> phoneAndEmail = new DSet<Tel>();
        phoneAndEmail = DSetConverter.convertListToDSet(emailList, "EMAIL", phoneAndEmail);
        orgDTO.setTelecomAddress(phoneAndEmail);
        try {
            localBean.addStudySiteGenericContact(oldIi, orgDTO, true, null);
            fail();
        } catch (PAException e) {
            // expected
        }

        phoneAndEmail = new DSet<Tel>();
        List<String> telList = new ArrayList<String>();
        telList.add("123-456-7890");
        phoneAndEmail = DSetConverter.convertListToDSet(telList, "PHONE", phoneAndEmail);
        orgDTO.setTelecomAddress(phoneAndEmail);
        try {
            localBean.addStudySiteGenericContact(oldIi, orgDTO, true, null);
            fail();
        } catch (PAException e) {
            // expected
        }

        phoneAndEmail = new DSet<Tel>();
        phoneAndEmail = DSetConverter.convertListToDSet(emailList, "EMAIL", phoneAndEmail);
        phoneAndEmail = DSetConverter.convertListToDSet(telList, "PHONE", phoneAndEmail);
        orgDTO.setTelecomAddress(phoneAndEmail);
        try {
            localBean.addStudySiteGenericContact(oldIi, orgDTO, true, null);
            fail();
        } catch (PAException e) {
            // expected
        }
        localBean.addStudySiteGenericContact(oldIi, orgDTO, true, phoneAndEmail);

    }

    @Test
    public void mergeParticipatingSitesNull() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Called ParticipatingSiteServiceBean.mergeParicipatingSites() with null argument.");
        localBean.mergeParicipatingSites(null, null);
    }

    @Test
    public void mergeParticipatingSitesNotFound() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Site not found when merging participating sites.");
        localBean.mergeParicipatingSites(-1L, -1L);
    }

    @Test
    public void mergeParticipatingSitesDifferentStudies() throws Exception {
        setupPoMockito();
        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00101", "CTEP_ID_1",
                "DCP_ID_1", "NCT_ID_1", false);
        Ii spSecId1 = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00101");
        OrganizationDTO org1 = getOrg1();
        StudySiteDTO studySiteDTO1 = getBasicStudySiteDTO(spSecId1);
        StudySiteAccrualStatusDTO currentStatus1 = getStatusDTO();
        ParticipatingSiteDTO dto1 = localBean.createStudySiteParticipant(studySiteDTO1, currentStatus1, org1, null);


        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00102", "CTEP_ID_2",
                "DCP_ID_2", "NCT_ID_2", false);
        Ii spSecId2 = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00102");
        OrganizationDTO org2 = getOrg1();
        StudySiteDTO studySiteDTO2 = getBasicStudySiteDTO(spSecId2);
        StudySiteAccrualStatusDTO currentStatus2 = getStatusDTO();
        ParticipatingSiteDTO dto2 = localBean.createStudySiteParticipant(studySiteDTO2, currentStatus2, org2, null);

        thrown.expect(PAException.class);
        thrown.expectMessage("Trying to merge participating sites from different trials.");
        localBean.mergeParicipatingSites(IiConverter.convertToLong(dto1.getIdentifier()),
                IiConverter.convertToLong(dto2.getIdentifier()));
    }

    @Test
    public void mergeParticipatingSitesIndustrial() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addAccrualCount(ssIds[0], 5);
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertEquals((Integer) 5, ss.getAccrualCount().getAccrualCount());

        addAccrualCount(ssIds[1], 10);
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals((Integer) 10, ss.getAccrualCount().getAccrualCount());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();
        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);
        // verify transfer of count to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals((Integer) 15, ss.getAccrualCount().getAccrualCount());
    }

    @Test
    public void mergeParticipatingSitesIndustrialNoSrcCount() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addAccrualCount(ssIds[1], 10);
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals((Integer) 10, ss.getAccrualCount().getAccrualCount());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();
        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);
        // verify transfer of count to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals((Integer) 10, ss.getAccrualCount().getAccrualCount());
    }

    @Test
    public void mergeParticipatingSitesIndustrialNoDestCount() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addAccrualCount(ssIds[0], 5);
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertEquals((Integer) 5, ss.getAccrualCount().getAccrualCount());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();

        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);
        // verify transfer of count to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertNotNull(ss);
        assertEquals((Integer) 5, ss.getAccrualCount().getAccrualCount());
    }

    @Test
    public void mergeParticipatingSitesComplete() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addStudySubject(ssIds[0], "site0subject");
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site0subject", ss.getStudySubjects().get(0).getAssignedIdentifier());

        addStudySubject(ssIds[1], "site1subject");
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site1subject", ss.getStudySubjects().get(0).getAssignedIdentifier());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();

        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);

        // verify transfer of subject to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertNotNull(ss);
        assertEquals(2L, ss.getStudySubjects().size());
        Set<String> sitePatientIds = new HashSet<String>();
        for (StudySubject subj : ss.getStudySubjects()) {
            sitePatientIds.add(subj.getAssignedIdentifier());
        }
        assertTrue(sitePatientIds.contains("site0subject"));
        assertTrue(sitePatientIds.contains("site1subject"));
    }

    @Test
    public void mergeParticipatingSitesCompleteDuplicatePatients() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addStudySubject(ssIds[0], "duplicate_subject");
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("duplicate_subject", ss.getStudySubjects().get(0).getAssignedIdentifier());

        addStudySubject(ssIds[1], "duplicate_subject");
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("duplicate_subject", ss.getStudySubjects().get(0).getAssignedIdentifier());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();


        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);

        // verify transfer of subject to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertNotNull(ss);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("duplicate_subject", ss.getStudySubjects().get(0).getAssignedIdentifier());
    }

    @Test
    public void mergeParticipatingSitesCompleteNoSrcPatient() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addStudySubject(ssIds[1], "site1subject");
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site1subject", ss.getStudySubjects().get(0).getAssignedIdentifier());

        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();

        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);

        // verify transfer of subject to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertNotNull(ss);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site1subject", ss.getStudySubjects().get(0).getAssignedIdentifier());
    }

    @Test
    public void mergeParticipatingSitesCompleteNoTargetPatient() throws Exception {
        setupPoMockito();
        Session sess = PaHibernateUtil.getCurrentSession();
        Long[] ssIds = setUp2SitesOnTrial();

        addStudySubject(ssIds[0], "site0subject");
        StudySite ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site0subject", ss.getStudySubjects().get(0).getAssignedIdentifier());


        localBean.mergeParicipatingSites(ssIds[0], ssIds[1]);
        sess.flush();
        sess.clear();

        // verify deletion of src study site
        ss = (StudySite) sess.get(StudySite.class, ssIds[0]);
        assertNull(ss);

        // verify transfer of subject to target
        ss = (StudySite) sess.get(StudySite.class, ssIds[1]);
        assertNotNull(ss);
        assertEquals(1L, ss.getStudySubjects().size());
        assertEquals("site0subject", ss.getStudySubjects().get(0).getAssignedIdentifier());
    }

    private Long[] setUp2SitesOnTrial() throws Exception {
        StudyProtocolTestHelper.prepareStudyProtocol(studyProtocolService, "NCI-2010-00101", "CTEP_ID_1",
                "DCP_ID_1", "NCT_ID_1", false);
        Ii spSecId1 = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00101");
        OrganizationDTO org1 = getOrg1();
        org1.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        StudySiteDTO studySiteDTO1 = getBasicStudySiteDTO(spSecId1);
        StudySiteAccrualStatusDTO currentStatus1 = getStatusDTO();
        ParticipatingSiteDTO dto1 = localBean.createStudySiteParticipant(studySiteDTO1, currentStatus1, org1, null);
        Long ssId1 = IiConverter.convertToLong(dto1.getIdentifier());

        OrganizationDTO org2 = getOrg1();
        org2.setIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        StudySiteDTO studySiteDTO2 = getBasicStudySiteDTO(spSecId1);
        StudySiteAccrualStatusDTO currentStatus2 = getStatusDTO();
        ParticipatingSiteDTO dto2 = localBean.createStudySiteParticipant(studySiteDTO2, currentStatus2, org2, null);
        Long ssId2 = IiConverter.convertToLong(dto2.getIdentifier());
        return new Long[]{ssId1, ssId2};
    }

    private void addAccrualCount(Long studySiteId, Integer count) {
        Session sess = PaHibernateUtil.getCurrentSession();
        StudySite ss = (StudySite) sess.get(StudySite.class, studySiteId);
        StudySiteSubjectAccrualCount sssac = new StudySiteSubjectAccrualCount();
        sssac.setAccrualCount(count);
        sssac.setStudyProtocol(ss.getStudyProtocol());
        sssac.setStudySite(ss);
        sssac.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        sssac.setDateLastUpdated(new Date());
        sess.save(sssac);
        sess.flush();
        sess.clear();
    }

    private void addStudySubject(Long studySiteId, String assignedIdentifier) {
        Session sess = PaHibernateUtil.getCurrentSession();
        StudySite ss = (StudySite) sess.get(StudySite.class, studySiteId);
        Country country = new Country();
        sess.save(country);
        Patient pat = new Patient();
        pat.setBirthDate(new Timestamp(new Date().getTime()));
        pat.setCountry(country);
        pat.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        sess.save(pat);
        StudySubject subj = new StudySubject();
        subj.setAssignedIdentifier(assignedIdentifier);
        subj.setPatient(pat);
        subj.setStudySite(ss);
        subj.setStudyProtocol(ss.getStudyProtocol());
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        sess.save(subj);
        sess.flush();
        sess.clear();
    }
}
