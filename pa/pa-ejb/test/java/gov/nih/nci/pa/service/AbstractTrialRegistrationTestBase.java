package gov.nih.nci.pa.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovUploadServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.service.util.MockRegistryUserServiceBean;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationBean;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.SystemUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public abstract class AbstractTrialRegistrationTestBase extends
        AbstractHibernateTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    protected final TrialRegistrationBeanLocal bean = new TrialRegistrationBeanLocal();
    protected final ArmServiceLocal armService = new ArmBeanLocal();
    protected final DocumentServiceLocal documentService = new DocumentBeanLocal();
    protected final DocumentWorkflowStatusServiceLocal documentWrkService = new DocumentWorkflowStatusBeanLocal();
    protected final LookUpTableServiceRemote lookUpTableServiceRemote = new MockLookUpTableServiceBean();
    protected OrganizationEntityServiceRemote poOrgSvc;
    protected PersonEntityServiceRemote poPersonSvc;
    protected PoServiceLocator poSvcLoc;
    protected final RegulatoryInformationServiceLocal regulatoryInfoSvc = new RegulatoryInformationBean();
    protected ServiceLocator paSvcLoc;
    protected final StudyContactServiceLocal studyContactSvc = new StudyContactBeanLocal();
    protected final StudyIndldeServiceLocal studyIndldeService = new StudyIndldeBeanLocal();
    protected final StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureBeanLocal();
    protected final StudyOverallStatusBeanLocal studyOverallStatusService = new StudyOverallStatusBeanLocal();
    protected final StudyProtocolBeanLocal studyProtocolService = new StudyProtocolBeanLocal();
    protected final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    protected final StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityBeanLocal();
    protected final StudyResourcingBeanLocal studyResourcingService = new StudyResourcingBeanLocal();
    protected final StudySiteAccrualStatusBeanLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    protected final StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();
    protected final StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    protected final PlannedActivityServiceLocal plannedActivityService = new PlannedActivityBeanLocal();
    protected final AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService = mock(AccrualDiseaseTerminologyServiceRemote.class);
    protected Ii spIi;
    protected final PAServiceUtils paServiceUtils = new MockPAServiceUtils();
    
    protected OrganizationCorrelationServiceBean ocsr;
    
    protected ClinicalResearchStaffCorrelationServiceRemote crsSvc ;
    protected HealthCareProviderCorrelationServiceRemote hcpSvc ;
    protected StudyMilestoneServiceBean studyMilestoneSvc;
    protected StudyInboxServiceLocal studyInboxSvc;
    protected MailManagerServiceLocal mailSvc;
    protected FamilyServiceRemote familySvc;
    protected IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote;
    protected HealthCareFacilityCorrelationServiceRemote poHcfSvc;
    protected CTGovUploadServiceLocal ctGovUploadServiceLocal;

    protected StudyRecordServiceLocal studyNotesService = new StudyRecordServiceLocal();
    protected StudyProcessingErrorService studyProcessingErrorService = new StudyProcessingErrorBeanLocal();
    
    public AbstractTrialRegistrationTestBase() {
        super();
    }
    
    @After
    public void shutdown() {
        PaHibernateUtil.getCurrentSession()
                .createSQLQuery("drop table if exists rv_dcp_id")
                .executeUpdate();
        PaHibernateUtil.getCurrentSession().flush();
    }

    /**
     * Initialization method.
     * @throws Exception If an error occurs
     */
    @SuppressWarnings("deprecation")
    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        PAProperties prop = new PAProperties();
        prop.setName("fromaddress");
        prop.setValue("ncictro@mail.nih.gov");
        TestSchema.addUpdObject(prop);
        StudyProtocol sp = TestSchema.creatOriginalStudyProtocolObj(ActStatusCode.ACTIVE, "NCI-2009-00004", "6");
        TestSchema.addUpdObject(TestSchema.createSubmittedDocumentWorkflowStatus(sp));
        StudyProtocol spInActive = TestSchema.creatOriginalStudyProtocolObj(ActStatusCode.INACTIVE,"NCI-2009-00003", "7");
        TestSchema.addUpdObject(TestSchema.createSubmittedDocumentWorkflowStatus(spInActive));
        studyProtocolService.setProtocolQueryService(protocolQueryService);
        StudyProtocol sp1 = TestSchema.createAmendStudyProtocolObj("NCI-2009-00003");
        RegistryUser ru = TestSchema.getRegistryUser();
        sp1.setUserLastCreated(ru.getUserLastCreated());
        sp1.setUserLastUpdated(ru.getUserLastUpdated());
        TestSchema.addUpdObject(TestSchema.createSubmittedDocumentWorkflowStatus(sp1));
        StudyProtocol spInActive1 = TestSchema.creatOriginalStudyProtocolObj(ActStatusCode.INACTIVE,"NCI-2009-00004", "8");
        TestSchema.addUpdObject(TestSchema.createSubmittedDocumentWorkflowStatus(spInActive1));
        StudyProtocol sp2 = TestSchema.createRejectAmendStudyProtocolObj("NCI-2009-00004");
        RegistryUser ru1 = TestSchema.getRegistryUser();
        sp2.setUserLastCreated(ru1.getUserLastCreated());
        sp2.setUserLastUpdated(ru1.getUserLastUpdated());
        TestSchema.addUpdObject(TestSchema.createSubmittedDocumentWorkflowStatus(sp2));
        studyOverallStatusService.setDocumentWorkFlowStatusService(documentWrkService);
        studyOverallStatusService.setStudyProtocolService(studyProtocolService);
    
        bean.setLookUpTableServiceRemote(lookUpTableServiceRemote);
        bean.setStudyProtocolService(studyProtocolService);
        bean.setProtocolQueryService(protocolQueryService);
        bean.setStudyOverallStatusService(studyOverallStatusService);
        bean.setStudyIndldeService(studyIndldeService);
        bean.setStudyResourcingService(studyResourcingService);
        bean.setDocumentService(documentService);
        bean.setStudySiteService(studySiteService);
        bean.setStudySiteContactService(studySiteContactService);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        bean.setRegulatoryInfoBean(regulatoryInfoSvc);
        bean.setDocumentWorkFlowStatusService(documentWrkService);
        bean.setRegistryUserServiceLocal(new MockRegistryUserServiceBean());
        bean.setPaServiceUtils(new MockPAServiceUtils());
        bean.setStudyContactService(studyContactSvc);
        bean.setArmService(armService);
        bean.setPlannedActivityService(plannedActivityService);
        bean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        bean.setAccrualDiseaseTerminologyService(accrualDiseaseTerminologyService);

        when(accrualDiseaseTerminologyService.canChangeCodeSystem(anyLong())).thenReturn(true);
        when(accrualDiseaseTerminologyService.getCodeSystem(anyLong())).thenReturn("SDC");
        when(accrualDiseaseTerminologyService.getValidCodeSystems()).thenReturn(
                Arrays.asList(new String[]{"SDC","ICD9","ICD10","ICD-O-3"}));

        studySiteAccrualStatusService.setStudySiteAccrualAccessServiceLocal(mock(StudySiteAccrualAccessServiceLocal.class));
        studyProtocolService.setRegistryUserService(mock(RegistryUserServiceLocal.class));
        CSMUserService.setInstance(new MockCSMUserService());
    
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
    
        when (paSvcLoc.getDocumentWorkflowStatusService()).thenReturn(documentWrkService);
        when (paSvcLoc.getLookUpTableService()).thenReturn(lookUpTableServiceRemote);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteService);
        
        OrganizationCorrelationServiceBean.resetCache();
        ocsr = mock(OrganizationCorrelationServiceBean.class);
        when(ocsr.getPoResearchOrganizationByEntityIdentifier(any(Ii.class))).thenAnswer(new Answer<Ii>() {
            @Override
            public Ii answer(InvocationOnMock invocation) throws Throwable {
                Ii ii = (Ii) invocation.getArguments()[0];
                return IiConverter.convertToPoResearchOrganizationIi(ii.getExtension());
            }
        });
        when(ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE)).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {               
                return "abc";
            }
        });
        
        when(ocsr.createResearchOrganizationCorrelations(any(String.class))).thenAnswer(new Answer<Long>() {
            @Override
            public Long answer(InvocationOnMock invocation) throws Throwable {
                String id = (String) invocation.getArguments()[0];
                 if(id.equals("1")) {
                     return new Long(1l);
                 }
                 else  {
                     return 0l;
                 }
            }
        });
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(ocsr);
        
        File tempDir;
        tempDir = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        tempDir.mkdirs();
        Field field = PaEarPropertyReader.class.getDeclaredField("PROPS");
        field.setAccessible(true);
        Properties earProps = (Properties) field.get(null);
        earProps.setProperty("doc.upload.path", tempDir.getAbsolutePath());
        
        
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        CTGovStudyAdapter ctGovStudyAdapter = new CTGovStudyAdapter(clinicalStudy);
      
        
     
    
        mailSvc = mock(MailManagerServiceLocal.class);
        familySvc = mock(FamilyServiceRemote.class);
        identifiedOrganizationCorrelationServiceRemote =mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        poHcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        ctGovUploadServiceLocal = mock(CTGovUploadServiceLocal.class);
        
        when(ctGovUploadServiceLocal.checkIfTrialExcludeAndUpdateCtroOverride(any(Long.class), any(String.class), any(String.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                Long id= (Long) invocation.getArguments()[0];
                String nctId = (String) invocation.getArguments()[2];
                if(nctId.equals("test_NCT_update_terminal_status")) {
                    Session session = PaHibernateUtil.getCurrentSession();
                    StudyProtocol studyProtocol = 
                            (StudyProtocol) session.get(StudyProtocol.class, id);
                    studyProtocol.setCtroOverride(true);
                    session.update(studyProtocol);
                    session.flush();
                    return true;
                }
                else {
                    return false;    
                }
                
            }
        });

        studyInboxSvc = new StudyInboxServiceBean();
        studyMilestoneSvc = new StudyMilestoneServiceBean();
        AbstractionCompletionServiceLocal abstractionCompletionSvc = mock(AbstractionCompletionServiceLocal.class);
        StudyOnholdServiceLocal studyOnholdSvc = new StudyOnholdBeanLocal();
        StudyRelationshipBeanLocal studyRelationshipSvc = new StudyRelationshipBeanLocal();
        TSRReportGeneratorServiceRemote tsrReportSvc = mock(TSRReportGeneratorServiceRemote.class);
        ByteArrayOutputStream rtfResults = new ByteArrayOutputStream();
        rtfResults.write("RTF Report".getBytes());
        when(tsrReportSvc.generateHtmlTsrReport(any(Ii.class))).thenReturn(rtfResults);
        when(tsrReportSvc.generatePdfTsrReport(any(Ii.class))).thenReturn(rtfResults);
        when(tsrReportSvc.generateRtfTsrReport(any(Ii.class))).thenReturn(rtfResults);
    
        studyMilestoneSvc.setAbstractionCompletionService(abstractionCompletionSvc);
        studyMilestoneSvc.setDocumentWorkflowStatusService(documentWrkService);
        studyMilestoneSvc.setStudyInboxService(studyInboxSvc);
        studyMilestoneSvc.setStudyOnholdService(studyOnholdSvc);
        studyMilestoneSvc.setStudyProtocolService(studyProtocolService);
        
        studyResourcingService.setLookUpTableSvc(lookUpTableServiceRemote);
        studyResourcingService.setStudyProtocolSvc(studyProtocolService);
        studyResourcingService.setPaServiceUtils(paServiceUtils);
    
        when(paSvcLoc.getStudyMilestoneService()).thenReturn(studyMilestoneSvc);
        when(paSvcLoc.getStudyIndldeService()).thenReturn(studyIndldeService);
        when(paSvcLoc.getDocumentService()).thenReturn(documentService);
        when(paSvcLoc.getStudyContactService()).thenReturn(studyContactSvc);
        when(paSvcLoc.getStudyRegulatoryAuthorityService()).thenReturn(studyRegulatoryAuthorityService);
        when(paSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolService);
        when(paSvcLoc.getStudyDiseaseService()).thenReturn(new StudyDiseaseServiceBean());
        when(paSvcLoc.getStudyObjectiveService()).thenReturn(new StudyObjectiveBeanLocal());
        when(paSvcLoc.getStratumGroupService()).thenReturn(new StratumGroupBeanLocal());
        when(paSvcLoc.getStudyResoucringService()).thenReturn(studyResourcingService);
        when(paSvcLoc.getStudyOnholdService()).thenReturn(studyOnholdSvc);
        when(paSvcLoc.getStudyOverallStatusService()).thenReturn(studyOverallStatusService);
        when(paSvcLoc.getStudyRecruitmentStatusService()).thenReturn(new StudyRecruitmentStatusBeanLocal());
        when(paSvcLoc.getArmService()).thenReturn(armService);
        when(paSvcLoc.getOutcomeMeasureService()).thenReturn(studyOutcomeMeasureService);
        when(paSvcLoc.getPlannedMarkerService()).thenReturn(new PlannedMarkerServiceBean());
        when(paSvcLoc.getAbstractionCompletionService()).thenReturn(abstractionCompletionSvc);
        when(paSvcLoc.getMailManagerService()).thenReturn(mailSvc);
        when(paSvcLoc.getStudyRecordService()).thenReturn(studyNotesService);
        when(paSvcLoc.getStudyProcessingErrorService()).thenReturn(studyProcessingErrorService);
       
        
        
        bean.setOcsr(ocsr);
        bean.setMailManagerSerivceLocal(mailSvc);
        bean.setStudyMilestoneService(studyMilestoneSvc);
        bean.setStudyInboxServiceLocal(studyInboxSvc);
        bean.setTsrReportService(tsrReportSvc);
        bean.setStudyRelationshipService(studyRelationshipSvc);
        bean.setCtGovUploadServiceLocal(ctGovUploadServiceLocal);
        
        setupPoSvc();        
        PaHibernateUtil.getCurrentSession().flush();
    }

    @SuppressWarnings("deprecation")
    protected void setupPoSvc() throws NullifiedEntityException,
            NullifiedRoleException, PAException, TooManyResultsException {
                poSvcLoc = mock(PoServiceLocator.class);
                PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
                poOrgSvc = mock(OrganizationEntityServiceRemote.class);
                poPersonSvc = mock(PersonEntityServiceRemote.class);
            
                ResearchOrganizationCorrelationServiceRemote roCorrelationSvc =
                    mock(ResearchOrganizationCorrelationServiceRemote.class);
                crsSvc = mock(ClinicalResearchStaffCorrelationServiceRemote.class);
                hcpSvc = mock(HealthCareProviderCorrelationServiceRemote.class);
            
                ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
                roDTO.setPlayerIdentifier(IiConverter.convertToIi(1L));
                roDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("1")));
            
                ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
                crsDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("1")));
            
                OrganizationDTO org = new OrganizationDTO();
                org.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
                org.setName(EnOnConverter.convertToEnOn("Org Name"));
                org.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
                org.setPostalAddress(AddressConverterUtil.create("2115 Executive Blvd.", "", "Rockville", "MD", "27852", "USA"));
            
                HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
                hcpDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealthcareProviderIi("1")));
                hcpDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            
                PersonDTO person = new PersonDTO();
                person.setIdentifier(IiConverter.convertToPoPersonIi("1"));
            
                when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(org);
                when(poSvcLoc.getFamilyService()).thenReturn(familySvc);
                when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifiedOrganizationCorrelationServiceRemote);
                when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(poHcfSvc);
                
                when(
                        poOrgSvc.search(any(OrganizationSearchCriteriaDTO.class), 
                                any(LimitOffset.class)))
                        .thenAnswer(new Answer<List<OrganizationDTO>>() {
                            @Override
                            public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                                Object[] arguments = invocation.getArguments();
                                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                                         List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
                                        OrganizationDTO  ctePOrgDto = new OrganizationDTO();
                                        ctePOrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
                                        ctePOrgDto.setName(EnOnConverter.convertToEnOn("name"));
                                        Ad adr = AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
                                        ctePOrgDto.setPostalAddress(adr);
                                        ctePOrgDto.setName(EnOnConverter.convertToEnOn("some org name"));
                                        DSet<Ii> dset = new DSet<Ii>();
                                        Set<Ii> familySet = new HashSet<Ii>();
                                        familySet.add(IiConverter.convertToPoFamilyIi("1"));
                                        dset.setItem(familySet);
                                        ctePOrgDto.setFamilyOrganizationRelationships(dset);
                                        list.add(ctePOrgDto);
                                        
                                        return list;
                                    
                                    
                                }
                                return null;
                            }
                        });
                Map<Ii, FamilyDTO> results = new HashMap<Ii, FamilyDTO>();
                FamilyDTO dto = new FamilyDTO();
                dto.setIdentifier(IiConverter.convertToIi(1L));
                dto.setName(EnOnConverter.convertToEnOn("value"));
                results.put(IiConverter.convertToPoFamilyIi("1"), dto);
                when(familySvc.getFamilies(any(Set.class))).thenReturn(results);
                
                List<IdentifiedOrganizationDTO> ctepList = new ArrayList<IdentifiedOrganizationDTO>();
                IdentifiedOrganizationDTO ctpDto = new IdentifiedOrganizationDTO();
                ctpDto.setPlayerIdentifier(IiConverter.convertToIi(1L));
                Ii id = new Ii();
                id.setExtension("NCICCR");
                id.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
                id.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
                ctpDto.setAssignedId(id);
                ctepList.add(ctpDto);
                
              
                
                when(identifiedOrganizationCorrelationServiceRemote.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(ctepList);
                
                
                
                
                when(poPersonSvc.getPerson(any(Ii.class))).thenReturn(person);
                when(roCorrelationSvc.getCorrelation(any(Ii.class))).thenReturn(roDTO);
                when(crsSvc.getCorrelation(any(Ii.class))).thenReturn(crsDTO);
                when(hcpSvc.getCorrelation(any(Ii.class))).thenReturn(hcpDTO);
            
                when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
                when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
                when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(roCorrelationSvc);
                when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(crsSvc);
                when(poSvcLoc.getHealthCareProviderCorrelationService()).thenReturn(hcpSvc);
                
                final IdentifiedPersonCorrelationServiceRemote mock = mock(IdentifiedPersonCorrelationServiceRemote.class);
                when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(mock);
            }

}