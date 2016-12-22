/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
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
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
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


/**
 * Interface used to locate the services used by pa-web.
 *
 * @author Harsha
 *
 */
public interface ServiceLocator {

    /**
     * @return pdq trial registration bean.
     */
    PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote();

    /**
     * @return pdq trial abstraction bean.
     */
    PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote();

    /**
     *
     * @return StudyProtocolServiceRemote studyProtocolServiceRemote
     */
    StudyProtocolServiceLocal getStudyProtocolService();


    /**
     * @return ProtocolOrganizationServiceRemote
     */
    PAOrganizationServiceRemote getPAOrganizationService();


    /**
     * @return PAPersonServiceRemote
     */
    PAPersonServiceRemote getPAPersonService();

    /**
     * @return RegulatoryInformationServiceRemote
     */
    RegulatoryInformationServiceLocal getRegulatoryInformationService();

    /**
     * @return StudyIndldeServiceLocal
     */
    StudyIndldeServiceLocal getStudyIndldeService();


    /**
     * @return StudyOverallStatusServiceLocal
     */
    StudyOverallStatusServiceLocal getStudyOverallStatusService();

    /**
     * @return StudyResourcingServiceLocal
     */
    StudyResourcingServiceLocal getStudyResoucringService();

    /**
     * @return StudyRegulatoryAuthorityServiceLocal
     */
    StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService();
    /**
    * @return LookUpTableServiceRemote
    */
    LookUpTableServiceRemote getLookUpTableService();

    /**
     * @return ProtocolQueryServiceRemote
     */
    ProtocolQueryServiceLocal getProtocolQueryService();

    /**
     * @return StudySiteServiceLocal
     */
    StudySiteServiceLocal getStudySiteService();

    /**
     * @return StudySiteAccrualStatusServiceLocal
     */
    StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService();

    /**
     * @return DocumentServiceLocal
     */
    DocumentServiceLocal getDocumentService();

    /**
     * @return StratumGroupServiceLocal
     */
    StratumGroupServiceLocal getStratumGroupService();

    /**
    * @return StudySiteContactServiceLocal
    */
    StudySiteContactServiceLocal getStudySiteContactService();
    /**
     * @return PAHealthCareProviderRemote
     */
    PAHealthCareProviderLocal getPAHealthCareProviderService();

    /**
     * @return PlannedActivityServiceLocal
     */
    PlannedActivityServiceLocal getPlannedActivityService();

    /**
     * @return InterventionServiceLocal
     */
    InterventionServiceLocal getInterventionService();

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    InterventionAlternateNameServiceLocal getInterventionAlternateNameService();

    /**
     * @return OutcomeMeasureServiceLocal
     */
    StudyOutcomeMeasureServiceLocal getOutcomeMeasureService();

    /**
     * @return ArmServiceLocal
     */
    ArmServiceLocal getArmService();
    /**
     * @return CTGovXmlGeneratorService
     */
    CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService();

    /**
     * @return PDQXmlGeneratorService
     */
    PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService();

    /**
     * @return AbstractionCompletionService
     */
    AbstractionCompletionServiceLocal getAbstractionCompletionService();

    /**
     * @return StudyIndldeServiceLocal
     */
    DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService();

    /**
     * @return StudyDiseaseServiceLocal
     */
    StudyDiseaseServiceLocal getStudyDiseaseService();

    /**
     * @return DiseaseService
     */
    PDQDiseaseServiceLocal getDiseaseService();

    /**
     * @return DiseaseAlternameService
     */
    PDQDiseaseAlternameServiceLocal getDiseaseAlternameService();

    /**
     * @return DiseaseParentService
     */
    PDQDiseaseParentServiceRemote getDiseaseParentService();

    /**
     * @return StudyContactServiceLocal
     */
    StudyContactServiceLocal getStudyContactService();

    /**
     * @return StudyMilestoneServiceLocal
     */
    StudyMilestoneServicelocal getStudyMilestoneService();

    /**
     * @return RegistryUserServiceLocal
     */
    RegistryUserServiceLocal getRegistryUserService();

    

    /**
     * @return TSRReportGeneratorServiceRemote
     */
    TSRReportGeneratorServiceRemote getTSRReportGeneratorService();

    /**
     * @return TSRReportGeneratorServiceLocal
     */
    TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal();

    /**
     * @return StudyOnholdService
     */
    StudyOnholdServiceLocal getStudyOnholdService();

    /**
     * @return MailManagerService
     */
    MailManagerServiceLocal getMailManagerService();

    /**
     *
     * @return StudyObjectiveServiceLocal
     */
    StudyObjectiveServiceLocal getStudyObjectiveService();

    /**
     *
     * @return StudyRecruitmentStatusServiceLocal
     */
    StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService();

    /**
     * @return StudyMilestoneTasksService
     */
    StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService();

    /**
     * @return OrganizationCorrelationService
     */
    OrganizationCorrelationServiceRemote getOrganizationCorrelationService();

    /**
     * @return StudySiteAccrualAccessService
     */
    StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService();

    /**
     * @return StudyInboxService
     */
    StudyInboxServiceLocal getStudyInboxService();

    /**
     * @return TrialRegistrationServiceLocal
     */
    TrialRegistrationServiceLocal getTrialRegistrationService();

    /**
     * @return StudySiteOverallStatusServiceLocal
     */
    StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService();

    /**
     * @return StudyCheckoutService
     */
    StudyCheckoutServiceLocal getStudyCheckoutService();

    /**
     * @return PlannedSubstanceAdministrationServiceRemote
     */
    PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService();

    /**
     * Gets the study relationship service.
     *
     * @return the study relationship service
     */
    StudyRelationshipServiceLocal getStudyRelationshipService();

    /**
     * @return service for partial save
     */
    StudyProtocolStageServiceLocal getStudyProtocolStageService();

    /**
     * @return service for ProprietaryTrial
     */
    ProprietaryTrialManagementServiceLocal getProprietaryTrialService();

    /**
     * @return service for Participating Sites.
     */
    ParticipatingSiteServiceLocal getParticipatingSiteService();

    /**
    * @return service for PDQ Update Generator Task Service
    */
   PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService();

   /**
    * @return the planned marker service
    */
   PlannedMarkerServiceLocal getPlannedMarkerService();
   
   /**
    * @return the planned marker service
    */
   MarkerAttributesServiceLocal getMarkerAttributesService();

   /**
    * @return the plannedMarkerSyncWithCaDSR Service
    */
   PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService();
   
   /**
    * @return the PlannedMarkerSynonymsServiceLocal Service
    */
   PlannedMarkerSynonymsServiceLocal getPMSynonymService();
   

   /**
    * @return the audit history service
    */
   AuditTrailServiceLocal getAuditTrailService();
   
   /**
    * @return the PDQ trial upload service
    */
   PDQTrialUploadService  getPDQTrialUploadService();

    /**
     * @return the participating org service
     */
    ParticipatingOrgServiceLocal getParticipatingOrgService();

    /**
     * @return the family service
     */
    FamilyServiceLocal getFamilyService();

    /**
     * @return CTGovUploadServiceLocal
     */
    CTGovUploadServiceLocal getCTGovUploadService();

    /**
     * @return the TrialDataVerificationServiceLocal
     */
    TrialDataVerificationServiceLocal getTrialDataVerificationService();

    /**
     * @return the I2E Grants service
     */
    I2EGrantsServiceLocal getI2EGrantsService();

    /**
     * @return CTGovSyncServiceLocal
     */
    CTGovSyncServiceLocal getCTGovSyncService();
    
    /**
     * @return CTGovSyncNightlyServiceLocal
     */
    CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService();

    /**
     * @return ProtocolComparisonServiceLocal
     */
    ProtocolComparisonServiceLocal getProtocolComparisonService();
    
    /**
     * @return ReadAndProcessPatientsServiceLocal
     */
    PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService();

    /**
     * @return StudyIdentifiersService
     */
    StudyIdentifiersService getStudyIdentifiersService();

    /**
     * @return AccrualDiseaseTerminologyService
     */
    AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService();

    /**
     * @return AccrualUtilityService
     */
    AccrualUtilityService getAccrualUtilityService();

    /**
     * 
     * @return StudySubjectServiceLocal
     */
    StudySubjectServiceLocal getStudySubjectService();

    /**
     * @return FlaggedTrialService
     */
    FlaggedTrialService getFlaggedTrialService();
    
    /**
     * @return StatusTransitionService
     */
    StatusTransitionService getStatusTransitionService();
    
    /**
     * @return StudyNotesServiceLocal
     */
    StudyRecordService getStudyRecordService();
    
    /**
     * @return StudyProcessingErrorService
     */
    StudyProcessingErrorService getStudyProcessingErrorService();
    
    /**
     * @return FamilyServiceLocal
     */
    FamilyProgramCodeService getProgramCodesFamilyService();
}

