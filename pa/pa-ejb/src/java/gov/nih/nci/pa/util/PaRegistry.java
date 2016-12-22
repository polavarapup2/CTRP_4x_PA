/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
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
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.service.util.PAOrganizationServiceCachingDecorator;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceCachingDecorator;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialUploadService;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;

/**
 *
 * @author Harsha
 *
 */
public final class PaRegistry { // NOPMD
    private static final PaRegistry PA_REGISTRY = new PaRegistry();
    private ServiceLocator serviceLocator;

    /**
     * Constructor for the singleton instance.
     */
    private PaRegistry() {
        serviceLocator = new JndiServiceLocator();
    }

    /**
     * @return the PO_REGISTRY
     */
    public static PaRegistry getInstance() {
        return PA_REGISTRY;
    }

    /**
     * @return PDQ Trial Registration Remote
     */
    public static PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return getInstance().getServiceLocator().getPDQTrialRegistrationServiceRemote();
    }

    /**
     * @return PDQ Trial Abstraction Remote
     */
    public static PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return getInstance().getServiceLocator().getPDQTrialAbstractionServiceRemote();
    }

    /**
     * Gets the org service from the service locator.
     *
     * @return the service.
     */
    public static StudyProtocolServiceLocal getStudyProtocolService() {
        return getInstance().getServiceLocator().getStudyProtocolService();
    }

    /**
     * Gets the org service from the service locator.
     *
     * @return PAOrganizationServiceRemote
     */
    public static PAOrganizationServiceRemote getPAOrganizationService() {
        return getInstance().getServiceLocator().getPAOrganizationService();
    }
    
    /**
     * {@link PAOrganizationServiceRemote} returned by this method has the same
     * functionality as {@link #getPAOrganizationService()}, except that caching is
     * enabled. Use this method if you are looking for performance optimizations
     * at a cost of small risk of encountering stale data. If only fresh data is
     * needed, use {@link #getPAOrganizationService()} instead.
     * 
     * @return PAOrganization Service.
     */    
    public static PAOrganizationServiceRemote getCachingPAOrganizationService() {
        return new PAOrganizationServiceCachingDecorator(getPAOrganizationService());
    }

    /**
     * @return the serviceLocator
     */
    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    /**
     * @param serviceLocator the serviceLocator to set
     */
    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    /**
     * @return PAPerson Service.
     */
    public static PAPersonServiceRemote getPAPersonService() {
        return getInstance().getServiceLocator().getPAPersonService();
    }
    
    /**
     * {@link PAPersonServiceRemote} returned by this method has the same
     * functionality as {@link #getPAPersonService()}, except that caching is
     * enabled. Use this method if you are looking for performance optimizations
     * at a cost of small risk of encountering stale data. If only fresh data is
     * needed, use {@link #getPAPersonService()} instead.
     * 
     * @return PAPerson Service.
     */
    public static PAPersonServiceRemote getCachingPAPersonService() {
        return new PAPersonServiceCachingDecorator(getPAPersonService());
    }
    

    /**
     *
     * @return RegulatoryInformationServiceRemote
     */
    public static RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        return getInstance().getServiceLocator().getRegulatoryInformationService();
    }

    /**
     *
     * @return StudyOverallStatusServiceRemote
     */
    public static StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return getInstance().getServiceLocator().getStudyOverallStatusService();
    }

    /**
     *
     * @return StudyIndldeServiceLocal
     */
    public static StudyIndldeServiceLocal getStudyIndldeService() {
        return getInstance().getServiceLocator().getStudyIndldeService();
    }

    /**
     *
     * @return StudyResourcingServiceLocal
     */
    public static StudyResourcingServiceLocal getStudyResourcingService() {
        return getInstance().getServiceLocator().getStudyResoucringService();
    }

    /**
     *
     * @return StudyRegulatoryAuthorityServiceLocal
     */
    public static StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return getInstance().getServiceLocator().getStudyRegulatoryAuthorityService();
    }

    /**
     *
     * @return LookUpTableServiceRemote
     */
    public static LookUpTableServiceRemote getLookUpTableService() {
        return getInstance().getServiceLocator().getLookUpTableService();
    }

    /**
     *
     * @return ProtocolQueryServiceRemote
     */
    public static ProtocolQueryServiceLocal getProtocolQueryService() {
        return getInstance().getServiceLocator().getProtocolQueryService();
    }
    
    /**
     * {@link ProtocolQueryServiceLocal} returned by this method has the same
     * functionality as {@link #getProtocolQueryService()}, except that caching
     * is enabled. Use this method if you are looking for performance
     * optimizations at a cost of small risk of encountering stale data. If only
     * fresh data is needed, use {@link #getProtocolQueryService()} instead.
     * 
     * @return PAPerson Service.
     */
    public static ProtocolQueryServiceLocal getCachingProtocolQueryService() {
        return new ProtocolQueryServiceCachingDecorator(
                getProtocolQueryService());
    }

    /**
     *
     * @return StudySiteService
     */
    public static StudySiteServiceLocal getStudySiteService() {
        return getInstance().getServiceLocator().getStudySiteService();
    }

    /**
     *
     * @return StudySiteAccrualStatusService
     */
    public static StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return getInstance().getServiceLocator().getStudySiteAccrualStatusService();
    }

    /**
     *
     * @return DocumentServiceLocal
     */
    public static DocumentServiceLocal getDocumentService() {
        return getInstance().getServiceLocator().getDocumentService();
    }

    /**
     *
     * @return StratumGroupServiceLocal
     */
    public static StratumGroupServiceLocal getStratumGroupService() {
        return getInstance().getServiceLocator().getStratumGroupService();
    }

    /**
     *
     * @return StudySiteService
     */
    public static StudySiteContactServiceLocal getStudySiteContactService() {
        return getInstance().getServiceLocator().getStudySiteContactService();
    }

    /**
     *
     * @return PAHealthCareProviderRemote
     */
    public static PAHealthCareProviderLocal getPAHealthCareProviderService() {
        return getInstance().getServiceLocator().getPAHealthCareProviderService();
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    public static PlannedActivityServiceLocal getPlannedActivityService() {
        return getInstance().getServiceLocator().getPlannedActivityService();
    }

    /**
     * @return InterventionServiceLocal
     */
    public static InterventionServiceLocal getInterventionService() {
        return getInstance().getServiceLocator().getInterventionService();
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    public static InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        return getInstance().getServiceLocator().getInterventionAlternateNameService();
    }

    /**
     * @return OutcomeMeasureServiceLocal
     */
    public static StudyOutcomeMeasureServiceLocal getStudyOutcomeMeasurService() {
        return getInstance().getServiceLocator().getOutcomeMeasureService();
    }

    /**
     * @return OutcomeMeasureServiceLocal
     */
    public static ArmServiceLocal getArmService() {
        return getInstance().getServiceLocator().getArmService();
    }

    /**
     * @return CTGovXmlGeneratorServiceLocal
     */
    public static CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
        return getInstance().getServiceLocator().getCTGovXmlGeneratorService();
    }

    /**
    *
    * @return PDQXmlGeneratorServiceRemote
    */
    public static PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return getInstance().getServiceLocator().getPDQXmlGeneratorService();
    }


    /**
     * @return AbstractionCompletionServiceRemote
     */
    public static AbstractionCompletionServiceLocal getAbstractionCompletionService() {
        return getInstance().getServiceLocator().getAbstractionCompletionService();
    }

    /**
     * @return DocumentWorkflowStatusServiceLocal
     */
    public static DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return getInstance().getServiceLocator().getDocumentWorkflowStatusService();
    }

    /**
     * @return DiseaseService
     */
    public static PDQDiseaseServiceLocal getDiseaseService() {
        return getInstance().getServiceLocator().getDiseaseService();
    }

    /**
     * @return DiseaseAlternameService
     */
    public static PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return getInstance().getServiceLocator().getDiseaseAlternameService();
    }

    /**
     * @return DiseaseParentService
     */
    public static PDQDiseaseParentServiceRemote getDiseaseParentService() {
        return getInstance().getServiceLocator().getDiseaseParentService();
    }

    /**
     * @return StudyDiseaseService
     */
    public static StudyDiseaseServiceLocal getStudyDiseaseService() {
        return getInstance().getServiceLocator().getStudyDiseaseService();
    }

    /**
     * @return StudyContactService
     */
    public static StudyContactServiceLocal getStudyContactService() {
        return getInstance().getServiceLocator().getStudyContactService();
    }

    /**
     * @return StudyMilestoneService
     */
    public static StudyMilestoneServicelocal getStudyMilestoneService() {
        return getInstance().getServiceLocator().getStudyMilestoneService();
    }

    /**
     * @return StudyMilestoneService
     */
    public static RegistryUserServiceLocal getRegistryUserService() {
        return getInstance().getServiceLocator().getRegistryUserService();
    }

    
    /**
     * @return TSRReportGeneratorServiceRemote
     */
    public static TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return getInstance().getServiceLocator().getTSRReportGeneratorService();
    }

    /**
     * @return TSRReportGeneratorServiceRemote
     */
    public static TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal() {
        return getInstance().getServiceLocator().getTSRReportGeneratorServiceLocal();
    }

    /**
     * @return StudyMilestoneService
     */
    public static StudyOnholdServiceLocal getStudyOnholdService() {
        return getInstance().getServiceLocator().getStudyOnholdService();
    }

    /**
     * @return MailManagerService
     */
    public static MailManagerServiceLocal getMailManagerService() {
        return getInstance().getServiceLocator().getMailManagerService();
    }

    /**
     *
     * @return StudyObjectiveService
     */
    public static StudyObjectiveServiceLocal getStudyObjectiveService() {
        return getInstance().getServiceLocator().getStudyObjectiveService();
    }

    /**
     *
     * @return StudyObjectiveService
     */
    public static StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return getInstance().getServiceLocator().getStudyRecruitmentStatusService();
    }

    /**
     * @return StudyMilestoneTasksService
     */
    public static StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return getInstance().getServiceLocator().getStudyMilestoneTasksService();
    }

    /**
     * @return OrganizationCorrelationService
     */
    public static OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return getInstance().getServiceLocator().getOrganizationCorrelationService();
    }

    /**
     * @return StudySiteAccrualAccessServiceLocal
     */
    public static StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return getInstance().getServiceLocator().getStudySiteAccrualAccessService();
    }

    /**
     * @return StudyInboxServiceLocal
     */
    public static StudyInboxServiceLocal getStudyInboxService() {
        return getInstance().getServiceLocator().getStudyInboxService();
    }

    /**
     * @return TrialRegistrationServiceLocal
     */
    public static TrialRegistrationServiceLocal getTrialRegistrationService() {
        return getInstance().getServiceLocator().getTrialRegistrationService();
    }

    /**
     * @return StudySiteOverallStatusServiceLocal
     */
    public static StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return getInstance().getServiceLocator().getStudySiteOverallStatusService();
    }

    /**
     * @return StudyCheckoutServiceLocal
     */
    public static StudyCheckoutServiceLocal getStudyCheckoutService() {
        return getInstance().getServiceLocator().getStudyCheckoutService();
    }

    /**
     * @return PlannedSubstanceAdministrationServiceRemote
     */
    public static PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return getInstance().getServiceLocator().getPlannedSubstanceAdministrationService();
    }

    /**
     * @return StudyRelationshipServiceLocal
     */
    public static StudyRelationshipServiceLocal getStudyRelationshipService() {
        return getInstance().getServiceLocator().getStudyRelationshipService();
    }

    /**
     * @return StudyProtocolStageServiceLocal
     */
    public static StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return getInstance().getServiceLocator().getStudyProtocolStageService();
    }

    /**
     * @return ProprietaryTrialManagementServiceLocal
     */
    public static ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return getInstance().getServiceLocator().getProprietaryTrialService();
    }

    /**
     * @return ParticipatingSiteServiceLocal
     */
    public static ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return getInstance().getServiceLocator().getParticipatingSiteService();
    }
    /**
     * @return ParticipatingSiteServiceLocal
     */
    public static PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        return getInstance().getServiceLocator().getPDQUpdateGeneratorTaskService();
    }

    /**
     * @return the planned marker service
     */
    public static PlannedMarkerServiceLocal getPlannedMarkerService() {
        return getInstance().getServiceLocator().getPlannedMarkerService();
    }
    
    /**
     * @return the Marker attributes service
     */
    public static MarkerAttributesServiceLocal getMarkerAttributesService() {
        return getInstance().getServiceLocator().getMarkerAttributesService();
    }
    
    /**
     * @return the PlannedMarkerSyncWithCaDSR Service
     */
    public static PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService() {
        return getInstance().getServiceLocator().getPMWithCaDSRService();
    }
    
    
    /**
     * @return the PlannedMarkerSynonymsServiceLocal Service
     */
    public static PlannedMarkerSynonymsServiceLocal getPMSynonymService() {
        return getInstance().getServiceLocator().getPMSynonymService();
    }
    /**
     * @return the audit trail service
     */
    public static AuditTrailServiceLocal getAuditTrailService() {
        return getInstance().getServiceLocator().getAuditTrailService();
    }
    
    /**
     * @return the PDQ trial upload service
     */
    public static PDQTrialUploadService getPDQTrialUploadService() {
        return getInstance().getServiceLocator().getPDQTrialUploadService();
    }

    /**
     * @return the participating org service
     */
    public static ParticipatingOrgServiceLocal getParticipatingOrgService() {
        return getInstance().getServiceLocator().getParticipatingOrgService();
    }

    /**
     * @return the participating org service
     */
    public static FamilyServiceLocal getFamilyService() {
        return getInstance().getServiceLocator().getFamilyService();
    }
    
    /**
     * @return CTGovUploadServiceLocal
     */
    public static CTGovUploadServiceLocal getCTGovUploadService() {
        return getInstance().getServiceLocator().getCTGovUploadService();
    }

    /**
     * @return the TrialDataVerificationServiceLocal
     */
    public static TrialDataVerificationServiceLocal getTrialDataVerificationService() {
        return getInstance().getServiceLocator().getTrialDataVerificationService();
    }
    /**
     * @return the I2E Grants service
     */
    public static I2EGrantsServiceLocal getI2EGrantsService() {
        return getInstance().getServiceLocator().getI2EGrantsService();
    }
    
    /**
     * @return CTGovSyncServiceLocal
     */
    public static CTGovSyncServiceLocal getCTGovSyncService() {
        return getInstance().getServiceLocator().getCTGovSyncService();
    }

    /**
     * @return CTGovSyncNightlyServiceLocal
     */
    public static CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {
        return getInstance().getServiceLocator().getCTGovSyncNightlyService();
    }   
    
    /**
     * @return ProtocolComparisonServiceLocal
     */
    public static ProtocolComparisonServiceLocal getProtocolComparisonService() {
        return getInstance().getServiceLocator().getProtocolComparisonService();
    }
    
    /**
     * @return ReadAndProcessPatientsServiceLocal
     */
    public static PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
        return getInstance().getServiceLocator().getPendingPatientAccrualsService();
    }
    
    /**
     * @return StudyIdentifiersService
     */
    public static StudyIdentifiersService getStudyIdentifiersService() {
        return getInstance().getServiceLocator().getStudyIdentifiersService();
    }

    /**
     * @return StudyIdentifiersService
     */
    public static AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService() {
        return getInstance().getServiceLocator().getAccrualDiseaseTerminologyService();
    }
    
    /**
     * @return StudyMilestoneService 
     */
    public static AccrualUtilityService getAccrualUtilityService() {
        return getInstance().getServiceLocator().getAccrualUtilityService();
    }
    /**
     * 
     * @return StudySubjectServiceLocal StudySubjectServiceLocal
     */
    public static StudySubjectServiceLocal getStudySubjectService() {
        return getInstance().getServiceLocator().getStudySubjectService();
    }
    
    /**
     * @return StudyMilestoneService
     */
    public static FlaggedTrialService getFlaggedTrialService() {
        return getInstance().getServiceLocator().getFlaggedTrialService();
    }
    
    /**
     * @return StatusTransitionService
     */
    public static StatusTransitionService getStatusTransitionService() {
        return getInstance().getServiceLocator().getStatusTransitionService();
    }
    
    /**
     * @return StudyNotesServiceLocal
     */
    public static StudyRecordService getStudyRecordService() {
        return getInstance().getServiceLocator().getStudyRecordService();
    }
    
    /**
     * @return StudyProcessingErrorService
     */
    public static StudyProcessingErrorService getStudyProcessingErrorService() {
        return getInstance().getServiceLocator().getStudyProcessingErrorService();
    }
   
    /**
     * @return FamilyProgramCodeService
     */
    public static FamilyProgramCodeService getProgramCodesFamilyService() {
        return getInstance().getServiceLocator().getProgramCodesFamilyService();
    }
    
}
