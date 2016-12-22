package gov.nih.nci.registry.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSynonymsServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIdentifiersService;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProcessingErrorService;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecordService;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.StudySubjectServiceLocal;
import gov.nih.nci.pa.service.TrialDataVerificationServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.AccrualUtilityService;
import gov.nih.nci.pa.service.util.CTGovSyncNightlyServiceLocal;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.CTGovUploadServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.FlaggedTrialService;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialUploadService;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.registry.service.MockCTGovSyncService;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockOrganizationCorrelationService;
import gov.nih.nci.registry.service.MockPAOrganizationService;
import gov.nih.nci.registry.service.MockPAPersonServiceRemote;
import gov.nih.nci.registry.service.MockProtocolQueryService;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.service.MockStudyProtocolStageService;
import gov.nih.nci.registry.service.MockStudyResourcingService;
import gov.nih.nci.registry.service.MockStudySiteAccrualStatusService;
import gov.nih.nci.registry.service.MockStudySiteService;
import gov.nih.nci.registry.service.MockTrialRegistrationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class RegistrationMockServiceLocator implements ServiceLocator {

    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final LookUpTableServiceRemote lookUpTableService = new MockLookUpTableService();
    private final TrialRegistrationServiceLocal  trialRegistrationService = new MockTrialRegistrationService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final StudyResourcingServiceLocal studyResourcingService = new MockStudyResourcingService();
    private final PAOrganizationServiceRemote paOrganizationService = new MockPAOrganizationService();
    private final PAPersonServiceRemote paPersonServiceRemote = new MockPAPersonServiceRemote();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final RegistryUserServiceLocal registryUserService = new MockRegistryUserService();
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new MockStudySiteAccrualStatusService();
    private final StudyProtocolStageServiceLocal studyProtocolStageService = new MockStudyProtocolStageService();

    public static StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService = mock(StudySiteAccrualAccessServiceLocal.class);
    public static FamilyServiceLocal familyService = mock(FamilyServiceLocal.class);
    public static AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService = mock(AccrualDiseaseTerminologyServiceRemote.class);

    private static List<CountryRegAuthorityDTO> regAuthorityCountries;
    static {
        regAuthorityCountries = new ArrayList<CountryRegAuthorityDTO>();
        CountryRegAuthorityDTO rac = new CountryRegAuthorityDTO();
        rac.setAlpha2("US");
        rac.setAlpha3("USA");
        rac.setId(1L);
        rac.setName("United States");
        rac.setNumeric("840");
        regAuthorityCountries.add(rac);
    }

    private static List<RegulatoryAuthOrgDTO> regAuthorityOrgs;
    static {
        regAuthorityOrgs = new ArrayList<RegulatoryAuthOrgDTO>();
        RegulatoryAuthOrgDTO rao = new RegulatoryAuthOrgDTO();
        rao.setId(1L);
        rao.setName("Federal Government");
        regAuthorityOrgs.add(rao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentServiceLocal getDocumentService() {
        List<DocumentDTO> results = new ArrayList<DocumentDTO>();

        DocumentDTO dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("Protocol Document"));
        dto.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        dto.setIdentifier(IiConverter.convertToDocumentIi(1L));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        results.add(dto);
        dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("IRB Approval Document"));
        dto.setText(EdConverter.convertToEd("IRB Approval Document".getBytes()));
        dto.setIdentifier(IiConverter.convertToDocumentIi(2L));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        results.add(dto);

        DocumentServiceLocal svc = mock(DocumentServiceLocal.class);
        try {
            when(svc.get(any(Ii.class))).thenReturn(dto);
            when(svc.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpTableService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailManagerServiceLocal getMailManagerService() {
        return mock(MailManagerServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return paOrganizationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PAPersonServiceRemote getPAPersonService() {
        return paPersonServiceRemote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        RegulatoryInformationServiceLocal svc = mock(RegulatoryInformationServiceLocal.class);
        try {
            when(svc.getDistinctCountryNames()).thenReturn(regAuthorityCountries);
            when(svc.getDistinctCountryNamesStartWithUSA()).thenReturn(regAuthorityCountries);
            when(svc.getRegulatoryAuthorityNameId(any(Long.class))).thenReturn(new ArrayList<RegulatoryAuthOrgDTO>());
            when(svc.getRegulatoryAuthorityNameId(1L)).thenReturn(regAuthorityOrgs);
            when(svc.getRegulatoryAuthorityInfo(any(Long.class))).thenReturn(Arrays.asList(1L, 2L));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyContactServiceLocal getStudyContactService() {
        return mock(StudyContactServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyIndldeServiceLocal getStudyIndldeService() {
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIndldeTypeCode(CdConverter.convertStringToCd("IND"));
        dto.setIndldeNumber(StConverter.convertToSt("Ind no"));
        dto.setGrantorCode(CdConverter.convertStringToCd("CDER"));
        dto.setHolderTypeCode(CdConverter.convertStringToCd("Investigator"));
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setIdentifier(IiConverter.convertToStudyIndIdeIi(1L));
        dto.setExpandedAccessStatusCode(CdConverter.convertStringToCd("expandedAccessType"));
        dto.setNciDivProgHolderCode(CdConverter.convertStringToCd("programCode"));
        dto.setNihInstHolderCode(CdConverter.convertStringToCd(""));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));

        StudyIndldeServiceLocal svc = mock(StudyIndldeServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        StudyOverallStatusServiceLocal svc = mock(StudyOverallStatusServiceLocal.class);
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIdentifier(IiConverter.convertToStudyOverallStatusIi(1L));
        dto.setStatusCode(CdConverter.convertStringToCd("Active"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/20/2008")));
        dto.setReasonText(null);
        try {
            when(svc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteContactServiceLocal getStudySiteContactService() {
        StudySiteContactDTO dto = new StudySiteContactDTO();
        dto.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
        dto.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelPhone phone = new TelPhone();
        phone.setValue(URI.create("tel:phone"));
        telSet.add(phone);
        TelEmail email = new TelEmail();
        email.setValue(URI.create("mailto:email"));
        telSet.add(email);
        telecomAddresses.setItem(telSet);
        dto.setTelecomAddresses(telecomAddresses);

        StudySiteContactServiceLocal svc = mock(StudySiteContactServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolServiceLocal getStudyProtocolService() {
        final StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setId(1L);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");


        final InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
        isp.setId(1L);
        dates = isp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        isp.setOfficialTitle("officialTitle");

        StudyProtocolServiceLocal svc = mock(StudyProtocolServiceLocal.class);
        try {
            when(svc.getStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
                @Override
                public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
                    StudyProtocolDTO dto = null;
                    Object[] args = invocation.getArguments();
                    Ii ii = (Ii) args[0];
                    if (ii.getExtension().equals("NCI-2013-001")) {
                        dto = StudyProtocolConverter.convertFromDomainToDTO(isp);
                        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(3L));
                    } else if (sp.getId().equals(IiConverter.convertToLong(ii))) {
                        dto = StudyProtocolConverter.convertFromDomainToDTO(sp);
                    } else if (IiConverter.convertToLong(ii).equals(3L)) {
                        dto = StudyProtocolConverter.convertFromDomainToDTO(isp);
                        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(3L));
                    } 
                    return dto;
                }
            });
            when(svc.createInterventionalStudyProtocol(any(InterventionalStudyProtocolDTO.class)))
                .thenReturn(IiConverter.convertToStudyProtocolIi(2L));
            when(svc.getInterventionalStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
                @Override
                public InterventionalStudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
                    InterventionalStudyProtocolDTO dto = null;
                    Object[] args = invocation.getArguments();
                    Ii ii = (Ii) args[0];
                    if (IiConverter.convertToLong(ii).equals(1L)) {
                        dto = InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
                    }
                    return dto;
                }
            });
            when(svc.getAbstractedCollaborativeTrials()).thenReturn(new ArrayList<StudyProtocolDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        StudyRegulatoryAuthorityServiceLocal svc = mock(StudyRegulatoryAuthorityServiceLocal.class);
        StudyRegulatoryAuthorityDTO dto = new StudyRegulatoryAuthorityDTO();
        dto.setIdentifier(IiConverter.convertToStudyRegulatoryAuthorityIi(1L));
        dto.setRegulatoryAuthorityIdentifier(IiConverter.convertToStudyRegulatoryAuthorityIi(1L));
        try {
            when(svc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return studyResourcingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return trialRegistrationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractionCompletionServiceLocal getAbstractionCompletionService() {
        return mock(AbstractionCompletionServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArmServiceLocal getArmService() {
        return mock(ArmServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
        return mock(CTGovXmlGeneratorServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return mock(PDQDiseaseAlternameServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        return mock(PDQDiseaseParentServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseServiceLocal getDiseaseService() {
        return mock(PDQDiseaseServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return mock(DocumentWorkflowStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        return mock(InterventionAlternateNameServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionServiceLocal getInterventionService() {
        return mock(InterventionServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PAHealthCareProviderLocal getPAHealthCareProviderService() {
        return mock(PAHealthCareProviderLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return mock(PlannedActivityServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return mock(PlannedSubstanceAdministrationServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StratumGroupServiceLocal getStratumGroupService() {
        return mock(StratumGroupServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return mock(StudyCheckoutServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return mock(StudyDiseaseServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxServiceLocal getStudyInboxService() {
        return mock(StudyInboxServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return mock(StudyMilestoneServicelocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return mock(StudyMilestoneTasksServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return mock(StudyObjectiveServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return mock(StudyOnholdServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return mock(StudyRecruitmentStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return mock(StudyRelationshipServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return studySiteAccrualAccessService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return mock(StudySiteOverallStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return mock(TSRReportGeneratorServiceRemote.class);
    }

    @Override
    public TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal() {
        return mock(TSRReportGeneratorServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialUploadService getPDQTrialUploadService() {
        return mock(PDQTrialUploadService.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return studyProtocolStageService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return mock(ProprietaryTrialManagementServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return mock(ParticipatingSiteServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        return mock(StudyOutcomeMeasureServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return mock(PDQXmlGeneratorServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        return mock(PDQUpdateGeneratorTaskServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return mock(PDQTrialAbstractionServiceBeanRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return mock(PDQTrialRegistrationServiceBeanRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));

        PlannedMarkerServiceLocal svc = mock(PlannedMarkerServiceLocal.class);
        try {
            when(svc.getPlannedMarker(any(Ii.class))).thenReturn(dto);
            when(svc.get(any(Ii.class))).thenReturn(dto);
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<PlannedMarkerDTO>());
            when(svc.copy(any(Ii.class), any(Ii.class))).thenReturn(new HashMap<Ii, Ii>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditTrailServiceLocal getAuditTrailService() {
        return mock(AuditTrailServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyServiceLocal getFamilyService() {
        return familyService;
    }

    
    @Override
	public CTGovUploadServiceLocal getCTGovUploadService() {        
        return Mockito.mock(CTGovUploadServiceLocal.class);
    }

    @Override
    public PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TrialDataVerificationServiceLocal getTrialDataVerificationService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public I2EGrantsServiceLocal getI2EGrantsService() {
        return mock(I2EGrantsServiceLocal.class);
    }

    @Override
    public CTGovSyncServiceLocal getCTGovSyncService() {
        return new MockCTGovSyncService();
    }

    @Override
    public CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {        
        return mock(CTGovSyncNightlyServiceLocal.class);
    }

    @Override
    public ProtocolComparisonServiceLocal getProtocolComparisonService() {
        return mock(ProtocolComparisonServiceLocal.class);
    }

	@Override
	public PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
		return mock(PendingPatientAccrualsServiceLocal.class);
	}

    @Override
    public StudyIdentifiersService getStudyIdentifiersService() {
        return mock(StudyIdentifiersService.class);
    }

    @Override
    public AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService() {
        return accrualDiseaseTerminologyService;
    }

    
    public AccrualUtilityService getAccrualUtilityService() {
        return mock(AccrualUtilityService.class);
    }

	@Override
	public PlannedMarkerSynonymsServiceLocal getPMSynonymService() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public StudySubjectServiceLocal getStudySubjectService() {
        // TODO Auto-generated method stub
        return null;
    }

    public FlaggedTrialService getFlaggedTrialService() {   
        return null;
    }

    @Override
    public StatusTransitionService getStatusTransitionService() {
        StatusTransitionService statusTransitionService = mock(StatusTransitionService.class);
        try {
            when(statusTransitionService.validateStatusTransition(
                    any(AppName.class), any(TrialType.class), any(TransitionFor.class),
                    anyString(), any(Date.class), anyString(), any(Date.class)
                    )).thenAnswer(new Answer<List<StatusDto>>() {

                        @Override
                        public List<StatusDto> answer(InvocationOnMock invocation)
                                throws Throwable {
                            Object[] args = invocation.getArguments();
                            List<StatusDto> statusDtos = new ArrayList<StatusDto>();
                            StatusDto dto = new StatusDto();
                            dto.setStatusCode((String)args[5]);
                            dto.setStatusDate(Calendar.getInstance().getTime());
                            statusDtos.add(dto);
                            return statusDtos;
                        }
                    });
        } catch (PAException e) {
            //Nothing to do
        }
        return statusTransitionService;
    }

    @Override
    public StudyRecordService getStudyRecordService() {
        // TODO Auto-generated method stub
        return null;
    }

     @Override
	    public StudyProcessingErrorService getStudyProcessingErrorService() {
	        return null;
    }

	@Override
	public FamilyProgramCodeService getProgramCodesFamilyService() {
		// TODO Auto-generated method stub
		return null;
	}
}
