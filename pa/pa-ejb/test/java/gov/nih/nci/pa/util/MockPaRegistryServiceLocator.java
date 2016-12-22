/**
 *
 */
package gov.nih.nci.pa.util;

import static org.mockito.Mockito.mock;
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
import gov.nih.nci.pa.service.RegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceRemote;
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
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecordService;
import gov.nih.nci.pa.service.StudyRecordServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusBeanLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.StudySubjectServiceLocal;
import gov.nih.nci.pa.service.TrialDataVerificationServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.status.StatusTransitionService;
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
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderServiceBean;
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
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import org.mockito.Mockito;


/**
 * @author vrushali
 *
 */
public class MockPaRegistryServiceLocator implements ServiceLocator  {

    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();
    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();
    PAHealthCareProviderLocal paHealthCareProvider = new PAHealthCareProviderServiceBean();
    RegistryUserServiceLocal regSvc = mock(RegistryUserServiceLocal.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @return StudySiteAccrualStatusServiceRemote
     */
    @Override
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionServiceLocal getInterventionService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnholdServiceLocal getStudyOnholdService() {
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
   @Override
public PAPersonServiceRemote getPAPersonService() {
       return null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
public RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        return null;
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public  OrganizationEntityServiceRemote getPoOrganizationEntityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpService;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentServiceLocal getDocumentService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteContactServiceLocal getStudySiteContactService() {
        return studySiteContactService;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPoPersonEntityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PAHealthCareProviderLocal getPAHealthCareProviderService() {
        return paHealthCareProvider;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StratumGroupServiceRemote getSubGroupsService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasurService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArmServiceLocal getArmService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractionCompletionServiceLocal getAbstractionCompletionService() {
       return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseServiceLocal getDiseaseService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyContactServiceLocal getStudyContactService() {
         return null;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
       return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal() {
       return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegistryUserServiceLocal getRegistryUserService() {
        return regSvc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailManagerServiceLocal getMailManagerService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PAHealthCareProviderLocal getHealthCareProviderRemote() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityServiceLocal getRegulatoryAuthorityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StratumGroupServiceLocal getStratumGroupService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return ocsr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxServiceLocal getStudyInboxService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return null;
    }

    @Override
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return null;
    }

   

    /**
     * {@inheritDoc}
     */
    @Override
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditTrailServiceLocal getAuditTrailService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialUploadService getPDQTrialUploadService() {
        return null;
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
    public FamilyServiceLocal getFamilyService() {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CTGovSyncServiceLocal getCTGovSyncService() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {
        return null;
    }

    @Override
    public ProtocolComparisonServiceLocal getProtocolComparisonService() {       
        return null;
    }

	@Override
	public PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
		return null;
	}

    @Override
    public StudyIdentifiersService getStudyIdentifiersService() {       
        return mock(StudyIdentifiersService.class);
    }

    @Override
    public AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService() {
        return null;
    }

    @Override
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

    @Override
    public FlaggedTrialService getFlaggedTrialService() {       
        return null;
    }

    @Override
    public StatusTransitionService getStatusTransitionService() {
        return mock(StatusTransitionService.class);
    }

    @Override
    public StudyRecordService getStudyRecordService() {
        return mock(StudyRecordServiceLocal.class);
    }
        
    @Override
    public StudyProcessingErrorService getStudyProcessingErrorService() {
        return mock(StudyProcessingErrorService.class);
    }

	@Override
	public FamilyProgramCodeService getProgramCodesFamilyService() {
		return mock(FamilyProgramCodeService.class);
	}
}
