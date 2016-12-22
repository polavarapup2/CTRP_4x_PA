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
import gov.nih.nci.pa.service.StudyProcessingErrorServiceLocal;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


/**
 *
 * @author Harsha
 *
 */
public class JndiServiceLocator implements ServiceLocator {

    private static final Logger LOG = Logger.getLogger(JndiServiceLocator.class);
    private Context ctx;

    /**
     * Constructor.
     */
    public JndiServiceLocator() {
        try {
            ctx = new InitialContext();
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    /**
     * @return pdq trial registration bean.
     */
    @Override
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return lookup("PDQTrialRegistrationServiceBean" 
                + "!gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote");
    }

    /**
     * @return pdq trial abstraction bean.
     */
    @Override
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return lookup("PDQTrialAbstractionServiceBean" 
                + "!gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote");
    }

    /**
     * @return protocol service
     */
    @Override
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return lookup("StudyProtocolBeanLocal!gov.nih.nci.pa.service.StudyProtocolServiceLocal");
    }

    /**
     * @return PAOrganizationServiceRemote remote interface
     */
    @Override
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return lookup("PAOrganizationServiceBean!gov.nih.nci.pa.service.util.PAOrganizationServiceRemote");
    }

    /**
     * @return PAPersonService
     */
    @Override
    public PAPersonServiceRemote getPAPersonService() {
        return lookup("PAPersonServiceBean!gov.nih.nci.pa.service.util.PAPersonServiceRemote");
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    @Override
    public RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        return lookup("RegulatoryInformationBean!gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal");
    }

    /**
     * @return StudyIndldeServiceLocal
     *
     */
    @Override
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return lookup("StudyIndldeBeanLocal!gov.nih.nci.pa.service.StudyIndldeServiceLocal");
    }

    /**
     * @return DocumentWorkflowStatusServiceLocal
     *
     */
    @Override
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return lookup("DocumentWorkflowStatusBeanLocal!gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal");
    }

    /**
     * @return StudyOverallStatusServiceLocal
     */
    @Override
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return lookup("StudyOverallStatusBeanLocal!gov.nih.nci.pa.service.StudyOverallStatusServiceLocal");
    }

    /**
     * @return StudyResourcingServiceLocal
     */
    @Override
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return lookup("StudyResourcingBeanLocal!gov.nih.nci.pa.service.StudyResourcingServiceLocal");
    }

    /**
     * @return StudyResourcingServiceLocal
     */
    @Override
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return lookup("StudyRegulatoryAuthorityBeanLocal!gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal");
    }

    /**
     * @return LookUpTableServiceRemote
     */
    @Override
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookup("LookUpTableServiceBean!gov.nih.nci.pa.service.util.LookUpTableServiceRemote");
    }

    /**
     * @return ProtocolQueryServiceRemote
     */
    @Override
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return lookup("ProtocolQueryServiceBean!gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal");
    }

    /**
     * @return StudySiteServiceLocal
     */
    @Override
    public StudySiteServiceLocal getStudySiteService() {
        return lookup("StudySiteBeanLocal!gov.nih.nci.pa.service.StudySiteServiceLocal");
    }

    /**
     * @return StudySiteAccrualStatusServiceLocal
     */
    @Override
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return lookup("StudySiteAccrualStatusBeanLocal!gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal");
    }

    /**
     * @return DocumentServiceLocal
     */
    @Override
    public DocumentServiceLocal getDocumentService() {
        return lookup("DocumentBeanLocal!gov.nih.nci.pa.service.DocumentServiceLocal");
    }

    /**
     * @return StratumGroupServiceLocal
     */
    @Override
    public StratumGroupServiceLocal getStratumGroupService() {
        return lookup("StratumGroupBeanLocal!gov.nih.nci.pa.service.StratumGroupServiceLocal");
    }

    /**
     * @return PAHealthCareProviderRemote
     */
    @Override
    public PAHealthCareProviderLocal getPAHealthCareProviderService() {
        return lookup("PAHealthCareProviderServiceBean!gov.nih.nci.pa.service.util.PAHealthCareProviderLocal");
    }

    /**
     * @return StudySiteService
     */
    @Override
    public StudySiteContactServiceLocal getStudySiteContactService() {
        return lookup("StudySiteContactBeanLocal!gov.nih.nci.pa.service.StudySiteContactServiceLocal");
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    @Override
    public InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        return lookup("InterventionAlternateNameServiceBean"
                + "!gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal");
    }

    /**
     * @return InterventionServiceLocal
     */
    @Override
    public InterventionServiceLocal getInterventionService() {
        return lookup("InterventionBeanLocal!gov.nih.nci.pa.service.InterventionServiceLocal");
    }

    /**
     * @return PlannedActivityServiceLocal
     */
    @Override
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return lookup("PlannedActivityBeanLocal!gov.nih.nci.pa.service.PlannedActivityServiceLocal");
    }

    /**
     * @return OutcomeMeasureServiceLocal
     */
    @Override
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        return lookup("StudyOutcomeMeasureBeanLocal!gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal");
    }

    /**
     * @return ArmServcieLocal
     */
    @Override
    public ArmServiceLocal getArmService() {
        return lookup("ArmBeanLocal!gov.nih.nci.pa.service.ArmServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
        return lookup("CTGovXmlGeneratorServiceBeanLocal!gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return lookup("PDQXmlGeneratorServiceBean!gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractionCompletionServiceLocal getAbstractionCompletionService() {
        return lookup("AbstractionCompletionServiceBean" 
                + "!gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal");
    }

    /**
     * @return PDQDiseaseAlternameService
     */
    @Override
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return lookup("PDQDiseaseAlternameBeanLocal!gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal");
    }

    /**
     * @return PDQDiseaseParentService
     */
    @Override
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        return lookup("PDQDiseaseParentServiceBean!gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote");
    }

    /**
     * @return PDQDiseaseService
     */
    @Override
    public PDQDiseaseServiceLocal getDiseaseService() {
        return lookup("PDQDiseaseBeanLocal!gov.nih.nci.pa.service.PDQDiseaseServiceLocal");
    }

    /**
     * @return StudyDiseaseServiceLocal
     */
    @Override
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return lookup("StudyDiseaseBeanLocal!gov.nih.nci.pa.service.StudyDiseaseServiceLocal");
    }

    /**
     * @return StudyContactServiceLocal
     */
    @Override
    public StudyContactServiceLocal getStudyContactService() {
        return lookup("StudyContactBeanLocal!gov.nih.nci.pa.service.StudyContactServiceLocal");
    }

    /**
     * @return StudyMilestoneServiceLocal
     */
    @Override
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return lookup("StudyMilestoneBeanLocal!gov.nih.nci.pa.service.StudyMilestoneServicelocal");
    }

    /**
     * @return RegistryUserServiceLocal
     */
    @Override
    public RegistryUserServiceLocal getRegistryUserService() {
        return lookup("RegistryUserBeanLocal!gov.nih.nci.pa.service.util.RegistryUserServiceLocal");
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return lookup("TSRReportGeneratorServiceBean!gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal() {
        return lookup("TSRReportGeneratorServiceBean!gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal");
    }                  

    /**
     * @return StudyOnholdServiceLocal
     */
    @Override
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return lookup("StudyOnholdBeanLocal!gov.nih.nci.pa.service.StudyOnholdServiceLocal");
    }

    /**
     * @return MailManagerServiceLocal
     */
    @Override
    public MailManagerServiceLocal getMailManagerService() {
        return lookup("MailManagerBeanLocal!gov.nih.nci.pa.service.util.MailManagerServiceLocal");
    }

    /**
     * @return StudyObjectiveServiceLocal
     */
    @Override
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return lookup("StudyObjectiveBeanLocal!gov.nih.nci.pa.service.StudyObjectiveServiceLocal");
    }

    /**
     * @return StudyRecruitmentStatusService
     */
    @Override
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return lookup("StudyRecruitmentStatusBeanLocal!gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal");
    }

    /**
     * @return StudyMilestoneService
     */
    @Override
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return lookup("StudyMilestoneTasksServiceBean!gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal");
    }

    /**
     * @return OrganizationCorrelationServiceRemote
     */
    @Override
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return lookup("OrganizationCorrelationServiceBean" 
                + "!gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return lookup("StudySiteAccrualAccessServiceBean" 
                + "!gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return lookup("TrialRegistrationBeanLocal!gov.nih.nci.pa.service.TrialRegistrationServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyInboxServiceLocal getStudyInboxService() {
        return lookup("StudyInboxServiceBean!gov.nih.nci.pa.service.StudyInboxServiceLocal");
    }

    /**
     * @return StudySiteOverallStatusServiceLocal
     */
    @Override
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return lookup("StudySiteOverallStatusBeanLocal!gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return lookup("StudyCheckoutServiceBean!gov.nih.nci.pa.service.StudyCheckoutServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return lookup("PlannedSubstanceAdministrationServiceBean"
                + "!gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote");
    }

    /**
     * @return protocol service
     */
    @Override
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return lookup("StudyRelationshipBeanLocal!gov.nih.nci.pa.service.StudyRelationshipServiceLocal");
    }

    /**
     * @return service for partial save
     */
    @Override
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return lookup("StudyProtocolStageBeanLocal!gov.nih.nci.pa.service.StudyProtocolStageServiceLocal");
    }

    /**
     * @return service for ProprietaryTrial
     */
    @Override
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return lookup("ProprietaryTrialManagementBeanLocal" 
                + "!gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return lookup("ParticipatingSiteBeanLocal!gov.nih.nci.pa.service.ParticipatingSiteServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        return lookup("PDQUpdateGeneratorTaskServiceBean" 
                + "!gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return lookup("PlannedMarkerServiceBean!gov.nih.nci.pa.service.PlannedMarkerServiceLocal");
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        return lookup("MarkerAttributesBeanLocal!gov.nih.nci.pa.service.MarkerAttributesServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditTrailServiceLocal getAuditTrailService() {
        return lookup("AuditTrailServiceBean!gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialUploadService getPDQTrialUploadService() {
        return lookup("PDQTrialUploadBean!gov.nih.nci.pa.service.util.PDQTrialUploadService");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        return lookup("ParticipatingOrgServiceBean!gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyServiceLocal getFamilyService() {
        return lookup("FamilyServiceBeanLocal!gov.nih.nci.pa.service.util.FamilyServiceLocal");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public CTGovUploadServiceLocal getCTGovUploadService() {
        return lookup("CTGovUploadServiceBeanLocal!gov.nih.nci.pa.service.util.CTGovUploadServiceLocal");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService() {
        return lookup("PlannedMarkerSyncWithCaDSRBeanLocal"
                + "!gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrialDataVerificationServiceLocal getTrialDataVerificationService() {
        return lookup("TrialDataVerificationBeanLocal!gov.nih.nci.pa.service.TrialDataVerificationServiceLocal");
    }

    @Override
    public I2EGrantsServiceLocal getI2EGrantsService() {
        return lookup("I2EGrantsServiceBean!gov.nih.nci.pa.service.util.I2EGrantsServiceLocal");
    }
    @Override
    public CTGovSyncServiceLocal getCTGovSyncService() {
        return lookup("CTGovSyncServiceBean!gov.nih.nci.pa.service.util.CTGovSyncServiceLocal");
    }
    
    @Override
    public CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {
        return lookup("CTGovSyncNightlyServiceBeanLocal!gov.nih.nci.pa.service.util.CTGovSyncNightlyServiceLocal");
    }

    @Override
    public ProtocolComparisonServiceLocal getProtocolComparisonService() {
        return lookup("ProtocolComparisonServiceBean!gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal");
    }
    
    @Override
    public PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
        return lookup("PendingPatientAccrualsServiceBean" 
                + "!gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal");
    }

    @Override
    public StudyIdentifiersService getStudyIdentifiersService() {
        return lookup("StudyIdentifiersBeanLocal!gov.nih.nci.pa.service.StudyIdentifiersServiceLocal");
    }

    @Override
    public AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService() {
        return lookup("AccrualDiseaseTerminologyServiceBean"
                + "!gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote");
    }

    @Override
    public AccrualUtilityService getAccrualUtilityService() {
        return lookup("AccrualUtilityServiceBean!gov.nih.nci.pa.service.util.AccrualUtilityServiceLocal");
    }

    @SuppressWarnings("unchecked")
    private <T> T lookup(String name) {
        T svc = null;
        try {
            svc = (T) ctx.lookup("java:global/pa/pa-ejb/" + name);
        } catch (NamingException e) {
            LOG.error(e, e);
        }
        return svc;
    }

    @Override
    public PlannedMarkerSynonymsServiceLocal getPMSynonymService() {
        return lookup("PlannedMarkerSynonymsBeanLocal"
                + "!gov.nih.nci.pa.service.PlannedMarkerSynonymsServiceLocal");
    }

    @Override
    public StudySubjectServiceLocal getStudySubjectService() {
        return lookup("StudySubjectBeanLocal!gov.nih.nci.pa.service.StudySubjectServiceLocal");
    }

    @Override
    public FlaggedTrialService getFlaggedTrialService() {
        return lookup("FlaggedTrialServiceBean!gov.nih.nci.pa.service.util.FlaggedTrialServiceLocal");
    }

    @Override
    public StatusTransitionService getStatusTransitionService() {
        return lookup("StatusTransitionServiceBeanLocal!gov.nih.nci.pa.service.status.StatusTransitionServiceLocal");
    }

    @Override
    public StudyRecordService getStudyRecordService() {
        return lookup("StudyRecordServiceLocal!gov.nih.nci.pa.service.StudyRecordService");
    }
    @Override
    public StudyProcessingErrorServiceLocal getStudyProcessingErrorService() {
        return lookup("StudyProcessingErrorBeanLocal!gov.nih.nci.pa.service.StudyProcessingErrorServiceLocal");
    }

    @Override
    public FamilyProgramCodeService getProgramCodesFamilyService() {
        return lookup("FamilyProgramCodeBeanLocal!gov.nih.nci.pa.service.util.FamilyProgramCodeServiceLocal");
    }
}
