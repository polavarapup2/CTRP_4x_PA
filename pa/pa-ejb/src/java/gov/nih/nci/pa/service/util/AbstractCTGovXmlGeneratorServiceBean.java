/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.util; // NOPMD

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactService;
import gov.nih.nci.pa.service.StudySiteContactServiceCachingDecorator;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;

/**
 * Base level class for xml generation. Contains needed ejb components.
 * @author mshestopalov
 *
 */
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
    SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
@SuppressWarnings("PMD.TooManyFields")
public class AbstractCTGovXmlGeneratorServiceBean {
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    @EJB
    private StudyDiseaseServiceLocal studyDiseaseService;
    @EJB
    private ArmServiceLocal armService;
    @EJB
    private PlannedActivityServiceLocal plannedActivityService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;
    @EJB
    private StudyContactServiceLocal studyContactService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB
    private OrganizationCorrelationServiceRemote orgCorrelationService;
    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService;
    @EJB
    private RegulatoryInformationServiceLocal regulatoryInformationService;
    @EJB
    private PDQDiseaseServiceLocal diseaseService;
    @EJB
    private InterventionServiceLocal interventionService;
    @EJB
    private InterventionAlternateNameServiceLocal interventionAlternateNameService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private StudyResourcingServiceLocal studyResourcingService;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;

    private CorrelationUtils corUtils = new CorrelationUtils();
    private PAServiceUtils paServiceUtil = new PAServiceUtils();

    private static Map<String, String> nv = null;

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @return studySiteContactService the studySiteContactService
     */
    public StudySiteContactService getStudySiteContactService() {
        return new StudySiteContactServiceCachingDecorator(
                this.studySiteContactService);
    }


    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @return studyContactService the studyContactService
     */
    public StudyContactServiceLocal getStudyContactService() {
        return this.studyContactService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param studyRegulatoryAuthoritySvc the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthoritySvc) {
        this.studyRegulatoryAuthorityService = studyRegulatoryAuthoritySvc;
    }

    /**
     * @param orgCorrelationService the orgCorrelationService to set
     */
    public void setOrgCorrelationService(OrganizationCorrelationServiceRemote orgCorrelationService) {
        this.orgCorrelationService = orgCorrelationService;
    }

    /**
     * OrganizationCorrelationServiceRemote.
     * @return remote inf for OrganizationCorrelationService.
     */
    public OrganizationCorrelationServiceRemote getOrgCorrelationService() {
        return this.orgCorrelationService;
    }

    /**
     * @param documentWorkflowStatusService the documentWorkflowStatusService to set
     */
    public void setDocumentWorkflowStatusService(DocumentWorkflowStatusServiceLocal documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceLocal regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param diseaseService the diseaseService to set
     */
    public void setDiseaseService(PDQDiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param interventionAltNameSvc the interventionAlternateNameService to set
     */
    public void setInterventionAlternateNameService(InterventionAlternateNameServiceLocal interventionAltNameSvc) {
        this.interventionAlternateNameService = interventionAltNameSvc;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @return RegistryUserServiceLocal
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return this.registryUserService;
    }

    /**
     * @param studyResSvc the StudyResourcingServiceLocal to set.
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResSvc) {
        this.studyResourcingService = studyResSvc;
    }

    /**
     * @return the studySiteService
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @return the studyOverallStatusService
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    /**
     * @return the studyIndldeService
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndldeService;
    }

    /**
     * @return the studyDiseaseService
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return studyDiseaseService;
    }

    /**
     * @return the armService
     */
    public ArmServiceLocal getArmService() {
        return armService;
    }

    /**
     * @return the plannedActivityService
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return plannedActivityService;
    }

    /**
     * @return the studySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * @return the studyOutcomeMeasureService
     */
    public StudyOutcomeMeasureServiceLocal getStudyOutcomeMeasureService() {
        return studyOutcomeMeasureService;
    }

    /**
     * @return the studyRegulatoryAuthorityService
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return studyRegulatoryAuthorityService;
    }

    /**
     * @return the documentWorkflowStatusService
     */
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return documentWorkflowStatusService;
    }

    /**
     * @return the regulatoryInformationService
     */
    public RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        return regulatoryInformationService;
    }

    /**
     * @return the diseaseService
     */
    public PDQDiseaseServiceLocal getDiseaseService() {
        return diseaseService;
    }

    /**
     * @return the interventionService
     */
    public InterventionServiceLocal getInterventionService() {
        return interventionService;
    }

    /**
     * @return the interventionAlternateNameService
     */
    public InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        return interventionAlternateNameService;
    }

   

    /**
     * @return the studyResourcingService
     */
    public StudyResourcingServiceLocal getStudyResourcingService() {
        return studyResourcingService;
    }

    /**
    *
    * @param cUtils the CorrelationUtils to set
    */
   public void setCorrelationUtils(CorrelationUtils cUtils) {
       this.corUtils = cUtils;
   }

   /**
    * @param paServiceUtil the paServiceUtil to set
    */
   public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
       this.paServiceUtil = paServiceUtil;
   }

   /**
    * @return the paServiceUtil
    */
   public PAServiceUtils getPaServiceUtil() {
       return paServiceUtil;
   }

   /**
    * @return the corUtils
    */
   public CorrelationUtils getCorUtils() {
       return corUtils;
   }

    /**
     * @param nv the nv to set
     */
    public static void setNv(Map<String, String> nv) {
        AbstractCTGovXmlGeneratorServiceBean.nv = nv;
    }

    /**
     * @return the nv
     */
    public static Map<String, String> getNv() {
        return nv;
    }
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * createCtGovValues.
     */
    protected static void createCtGovValues() {
        Map<String, String> nvMap = new HashMap<String, String>();
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode(), "Approved");
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode(), "Exempt");
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode(), "Not Required");
        nvMap.put(AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode(), "Randomized");
        nvMap.put(AllocationCode.NON_RANDOMIZED_TRIAL.getCode(), "Non-randomized");
        nvMap.put(BlindingSchemaCode.OPEN.getCode(), "Open Label");
        nvMap.put(BlindingSchemaCode.SINGLE_BLIND.getCode(), "Single Blind");
        nvMap.put(BlindingSchemaCode.DOUBLE_BLIND.getCode(), "Double Blind");
        nvMap.put(DesignConfigurationCode.SINGLE_GROUP.getCode(), "Single Group Assignment");
        nvMap.put(DesignConfigurationCode.PARALLEL.getCode(), "Parallel Assignment");
        nvMap.put(DesignConfigurationCode.CROSSOVER.getCode(), "Crossover Assignment");
        nvMap.put(DesignConfigurationCode.FACTORIAL.getCode(), "Factorial Assignment");
        nvMap.put(StudyClassificationCode.SAFETY.getCode(), "Safety Study");
        nvMap.put(StudyClassificationCode.EFFICACY.getCode(), "Efficacy Study");
        nvMap.put(StudyClassificationCode.SAFETY_OR_EFFICACY.getCode(), "Safety/Efficacy Study");
        nvMap.put(StudyClassificationCode.BIO_EQUIVALENCE.getCode(), "Bio-equivalence Study");
        nvMap.put(StudyClassificationCode.BIO_AVAILABILITY.getCode(), "Bio-availability Study");
        nvMap.put(StudyClassificationCode.PHARMACOKINETICS.getCode(), "Pharmacokinetics Study");
        nvMap.put(StudyClassificationCode.PHARMACODYNAMICS.getCode(), "Pharmacodynamics Study");
        nvMap.put(StudyClassificationCode.PHARMACOKINETICS_OR_DYNAMICS.getCode(), "Pharmacokinetics/dynamics Study");
        nvMap.put(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode(), "Principal Investigator");
        nvMap.put(AllocationCode.NA.getCode(), XmlGenHelper.NA);
        nvMap.put(RecruitmentStatusCode.IN_REVIEW.getCode(), "Not yet recruiting");
        nvMap.put(RecruitmentStatusCode.APPROVED.getCode(), "Not yet recruiting");
        nvMap.put(RecruitmentStatusCode.WITHDRAWN.getCode(), "Withdrawn");
        nvMap.put(RecruitmentStatusCode.ACTIVE.getCode(), "Recruiting");
        nvMap.put(RecruitmentStatusCode.ENROLLING_BY_INVITATION.getCode(), "Enrolling by Invitation");
        nvMap.put(RecruitmentStatusCode.CLOSED_TO_ACCRUAL.getCode(), "Active, not recruiting");
        nvMap.put(RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode(), "Active, not recruiting");
        nvMap.put(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(), "Suspended");
        nvMap.put(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode(), "Suspended");
        nvMap.put(RecruitmentStatusCode.COMPLETED.getCode(), "Completed");
        nvMap.put(RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE.getCode(), "Terminated");
        setNv(Collections.unmodifiableMap(nvMap));
    }

    /**
     * convertToCtValues.
     * @param cd values.
     * @return string
     */
    protected static String convertToCtValues(Cd cd) {
        if (ISOUtil.isCdNull(cd)) {
            return null;
        }
        if (getNv().containsKey(cd.getCode())) {
            return getNv().get(cd.getCode());
        }
        return cd.getCode();
    }

    /**
     * @return the protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

}
