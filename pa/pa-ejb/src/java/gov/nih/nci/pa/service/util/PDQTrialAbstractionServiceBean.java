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

package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
* service for loading abstraction element from pdq.xml into CTRP.
*
* @author vrushali
*/
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed(SUBMITTER_ROLE)
public class PDQTrialAbstractionServiceBean extends AbstractPDQTrialServiceHelper
            implements PDQTrialAbstractionServiceBeanRemote {
    @EJB
    private OrganizationCorrelationServiceRemote orgCorrelationService;

    private static final Logger LOG = Logger.getLogger(PDQTrialAbstractionServiceBean.class);
    private final CorrelationUtils corrUtils = new CorrelationUtils();
    private static final Map<String, String> ALLOCATION_CODE_MAP = new HashMap<String, String>();
    static {
        ALLOCATION_CODE_MAP.put("NA", AllocationCode.NA.getCode());
        ALLOCATION_CODE_MAP.put("RANDOMIZED", AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode());
        ALLOCATION_CODE_MAP.put("NON-RANDOMIZED", AllocationCode.NON_RANDOMIZED_TRIAL.getCode());
    }
    private static final Map<String, String> BLINDING_SCHEMA_MAP = new HashMap<String, String>();
    static {
        BLINDING_SCHEMA_MAP.put("OPEN LABEL", BlindingSchemaCode.OPEN.getCode());
        BLINDING_SCHEMA_MAP.put("SINGLE BLIND", BlindingSchemaCode.SINGLE_BLIND.getCode());
        BLINDING_SCHEMA_MAP.put("DOUBLE BLIND", BlindingSchemaCode.DOUBLE_BLIND.getCode());
    }
    private static final List<MilestoneCode> MILESTONE = new ArrayList<MilestoneCode>();
    static {
        MILESTONE.add(MilestoneCode.SUBMISSION_ACCEPTED);
        MILESTONE.add(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE);
        MILESTONE.add(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE);
        MILESTONE.add(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE);
        MILESTONE.add(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE);
    }
    private static final List<Cd> DOUBLE_BLIND_DEFAULT_ROLES = new ArrayList<Cd>();
    static {
        DOUBLE_BLIND_DEFAULT_ROLES.add(CdConverter.convertToCd(BlindingRoleCode.SUBJECT));
        DOUBLE_BLIND_DEFAULT_ROLES.add(CdConverter.convertToCd(BlindingRoleCode.INVESTIGATOR));
    }

    private static final Map<StudyStatusCode, RecruitmentStatusCode> DEFAULT_LOCATION_STATUS_MAP =
        initDefaultLocationStatusMap();

    private static Map<StudyStatusCode, RecruitmentStatusCode> initDefaultLocationStatusMap() {
        Map<StudyStatusCode, RecruitmentStatusCode> map = new HashMap<StudyStatusCode, RecruitmentStatusCode>();
        map.put(StudyStatusCode.IN_REVIEW, RecruitmentStatusCode.IN_REVIEW);
        map.put(StudyStatusCode.APPROVED, RecruitmentStatusCode.APPROVED);
        map.put(StudyStatusCode.ACTIVE, RecruitmentStatusCode.ACTIVE);
        map.put(StudyStatusCode.ENROLLING_BY_INVITATION, RecruitmentStatusCode.ENROLLING_BY_INVITATION);
        map.put(StudyStatusCode.CLOSED_TO_ACCRUAL, RecruitmentStatusCode.CLOSED_TO_ACCRUAL);
        map.put(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        map.put(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL, RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        map.put(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        map.put(StudyStatusCode.WITHDRAWN, RecruitmentStatusCode.WITHDRAWN);
        map.put(StudyStatusCode.ADMINISTRATIVELY_COMPLETE, RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE);
        map.put(StudyStatusCode.COMPLETE, RecruitmentStatusCode.COMPLETED);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Loads the PDQ xml's abstraction elements.
     * @param xmlUrl url
     * @param studyProtocolIi ii of trial
     *@throws PAException on error
     * @throws IOException on error
     */
    public void loadAbstractionElementFromPDQXml(URL xmlUrl, Ii studyProtocolIi) throws PAException, IOException {
        PDQAbstractionXMLParser parser = new PDQAbstractionXMLParser();
        parser.setUrl(xmlUrl);
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("Ii should not be null.");
        }
        parser.parse();
        LOG.debug("trial id:" + studyProtocolIi.getExtension());
        loadStudyProtocol(parser.getIspDTO(), studyProtocolIi);
        loadCollaborators(parser.getCollaboratorOrgDTOs(), studyProtocolIi);
        //Scientific Data
        loadConditions(parser.getListOfDiseaseDTOs() , studyProtocolIi);
        loadInterventions(parser.getListOfInterventionsDTOS(), studyProtocolIi);
        loadArms(parser.getArmInterventionMap(), parser.getListOfArmDTOS(), studyProtocolIi);
        loadOutcomeMeasures(parser.getOutcomeMeasureDTOs(), studyProtocolIi);
        loadEligibilityCriteria(parser, studyProtocolIi);
        //participating site info
        if (MapUtils.isNotEmpty(parser.getLocationsMap())) {
            loadParticipatingSites(parser.getLocationsMap(), studyProtocolIi);
        } else {
            loadDefaultParticipatingSite(studyProtocolIi);
        }

        //create new milestones
        StudyMilestoneDTO mileDTO = null;
        for (MilestoneCode code : MILESTONE) {
            mileDTO = new StudyMilestoneDTO();
            mileDTO.setStudyProtocolIdentifier(studyProtocolIi);
            mileDTO.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
            mileDTO.setMilestoneCode(CdConverter.convertToCd(code));
            PaRegistry.getStudyMilestoneService().create(mileDTO);
        }
    }
    /**
     * @param locationsMap
     * @param studyProtocolIi
     * @throws PAException If an error occurs.
     */
    private void loadParticipatingSites(Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>>
        locationsMap, Ii studyProtocolIi) throws PAException {
        for (OrganizationDTO locOrg : locationsMap.keySet()) {
            try {
                StudySiteDTO studySiteDTO = new StudySiteDTO();
                studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
                studySiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
                studySiteDTO.setIdentifier(null);
                studySiteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
                studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                            new Timestamp(new Date().getTime()), null));

                Map<StudySiteAccrualStatusDTO, Map<PoDto, String>> valueMap = locationsMap.get(locOrg);
                StudySiteAccrualStatusDTO recrutingStatus = null;
                for (StudySiteAccrualStatusDTO statusDTO : valueMap.keySet()) {
                    recrutingStatus = statusDTO;
                    Ii studySiteIi = PaRegistry.getParticipatingSiteService().createStudySiteParticipant(
                            studySiteDTO, recrutingStatus, locOrg.getIdentifier()).getIdentifier();

                    Map<PoDto, String> contactMap = valueMap.get(statusDTO);
                    addContact(studyProtocolIi, corrUtils.getPoOrgIiFromPaHcfIi(locOrg.getIdentifier()).getExtension(),
                            studySiteIi, contactMap);
                }
            } catch (Exception e) {
                LOG.error("error while loading participating site with PO hcf db id "
                        + locOrg.getIdentifier().getExtension(), e);
            }
        }
    }

    /**
     * Loads a default participating site with the organization set to be the lead org and
     * the recruitment status date equal to the lead_org_status status_date.
     * @param studyProtocolIi the ii of the study protocol create the default location for
     */
    private void loadDefaultParticipatingSite(Ii studyProtocolIi) {
        try {
            StudySiteDTO criteria = new StudySiteDTO();
            criteria.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            // There should only be one lead org associated with a study.
            StudySiteServiceLocal siteService = PaRegistry.getStudySiteService();
            List<StudySiteDTO> leadOrgs = siteService.getByStudyProtocol(studyProtocolIi, criteria);
            StudySiteDTO leadOrg = leadOrgs.get(0);
            StudyOverallStatusServiceLocal sosService = PaRegistry.getStudyOverallStatusService();
            StudyOverallStatusDTO overallStatus = sosService.getCurrentByStudyProtocol(studyProtocolIi);

            StudySiteAccrualStatusDTO accrualStatus = new StudySiteAccrualStatusDTO();
            accrualStatus.setStatusDate(overallStatus.getStatusDate());
            StudyStatusCode studyStatusCode = StudyStatusCode.getByCode(overallStatus.getStatusCode().getCode());
            accrualStatus.setStatusCode(CdConverter.convertToCd(DEFAULT_LOCATION_STATUS_MAP.get(studyStatusCode)));

            StudySiteDTO studySiteDTO = new StudySiteDTO();
            studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
            studySiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
            studySiteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
            Timestamp now = new Timestamp(new Date().getTime());
            studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(now, null));

            OrganizationDTO org = new OrganizationDTO();
            org.setIdentifier(corrUtils.getPoOrgIiFromPaRoIi(leadOrg.getResearchOrganizationIi()));
            PaRegistry.getParticipatingSiteService().createStudySiteParticipant(studySiteDTO, accrualStatus, org, null);
            LOG.warn("Successfully added default participating site for organization "
                    + IiConverter.convertToString(leadOrg.getIdentifier()));
        } catch (PAException e) {
            LOG.error("Error creating study protocol's default location " + studyProtocolIi.getExtension(), e);
        }
    }

    /**
     * @param collaboratorOrgDTOs
     * @param studyProtocolIi
     */
    private void loadCollaborators(List<OrganizationDTO> collaboratorOrgDTOs, Ii studyProtocolIi) {
        StudySiteDTO siteDTO = null;
        for (OrganizationDTO orgDTO : collaboratorOrgDTOs) {
            String orgName = EnOnConverter.convertEnOnToString(orgDTO.getName());
            if (!StringUtils.equalsIgnoreCase("NCI", orgName)) {
                try {
                    siteDTO = new StudySiteDTO();
                    OrganizationDTO poOrgDTO = findOrCreateEntity(orgDTO);
                    Long paROId = getOrgCorrelationService().createResearchOrganizationCorrelations(
                            IiConverter.convertToString(poOrgDTO.getIdentifier()));
                    siteDTO.setResearchOrganizationIi(IiConverter.convertToIi(paROId));
                    siteDTO.setStudyProtocolIdentifier(studyProtocolIi);
                    siteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
                    siteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
                    PaRegistry.getStudySiteService().create(siteDTO);
                } catch (PAException e) {
                    LOG.error("error loading collaborator for Org " + orgName, e);
                }
            }
        }
    }

    /**
     *
     * @param parser parser
     * @param studyProtocolIi ii
     */
    private void loadEligibilityCriteria(PDQAbstractionXMLParser parser, Ii studyProtocolIi) {
        try {
            List<PlannedEligibilityCriterionDTO> eligibilityList = parser.getEligibilityList();
            String healthyVolunteers = parser.getHealthyVolunteers();
            PDQEligibilityCriteriaParser eligibilityCriteria = new PDQEligibilityCriteriaParser();

            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            spDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(BooleanUtils.toBoolean(
                    healthyVolunteers)));
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);

            for (PlannedEligibilityCriterionDTO dto : eligibilityList) {
                dto.setStudyProtocolIdentifier(studyProtocolIi);
                PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(dto);
            }
            //create other criterion
            String[] splitOutput = eligibilityCriteria.readEligibilityEntryCriteria(
                    parser.getDocument().getRootElement().getChild("eligibility"));
            int displayOrder = 0;
            for (String otherCriterionDesc : splitOutput) {
                PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
                pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
                pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
                pecDTO.setStructuredIndicator(BlConverter.convertToBl(Boolean.FALSE));
                pecDTO.setTextDescription(StConverter.convertToSt(otherCriterionDesc));
                pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
                pecDTO.setDisplayOrder(IntConverter.convertToInt(displayOrder));
                PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO);
                displayOrder++;
            }
        } catch (PAException e) {
            LOG.error("error loading EligibilityCriteria for study Protocol id" + studyProtocolIi.getExtension(), e);
        }
    }

    /**
     * @param outcomeMeasureDTOs
     * @param studyProtocolIi
     * @throws PAException
     */
    private void loadOutcomeMeasures(List<StudyOutcomeMeasureDTO> outcomeMeasureDTOs, Ii studyProtocolIi) {
        for (StudyOutcomeMeasureDTO outDTO : outcomeMeasureDTOs) {
            outDTO.setStudyProtocolIdentifier(studyProtocolIi);
            if (ISOUtil.isStNull(outDTO.getTimeFrame())) {
                outDTO.setTimeFrame(StConverter.convertToSt("Not Provided"));
            }
            try {
                PaRegistry.getStudyOutcomeMeasurService().create(outDTO);
            } catch (PAException e) {
                LOG.error("error loading outcome measures.", e);
            }
        }
    }

    /**
     * @param armInterventionMap
     * @param listofArmDTOs
     * @param studyProtocolIi
     * @throws PAException
     */
    private void loadArms(Map<InterventionDTO, List<ArmDTO>> armInterventionMap, List<ArmDTO> listofArmDTOs,
            Ii studyProtocolIi) {
        for (ArmDTO armDTO : listofArmDTOs) {
            armDTO.setStudyProtocolIdentifier(studyProtocolIi);
            Set<Ii> interventionSet = new HashSet<Ii>();
            try {
                //get the associated intervention
                for (InterventionDTO interDTO : armInterventionMap.keySet()) {
                    List<ArmDTO> armList = armInterventionMap.get(interDTO);
                    for (ArmDTO aDTO : armList) {
                        getAssociatedInterventions(studyProtocolIi, armDTO, interventionSet, interDTO, aDTO);
                    }
                }
                DSet<Ii> interventions = new DSet<Ii>();
                interventions.setItem(interventionSet);
                armDTO.setInterventions(interventions);
                PaRegistry.getArmService().create(armDTO);
            } catch (PAException e) {
                LOG.error("error loading arm " + armDTO.getName().getValue(), e);
            }
        }
    }

    /**
     * @param studyProtocolIi
     * @param armDTO arm DTO
     * @param interventionSet
     * @param interDTO
     * @param aDTO arm label from xml
     * @throws PAException
     */
    private void getAssociatedInterventions(Ii studyProtocolIi, ArmDTO armDTO, Set<Ii> interventionSet,
            InterventionDTO interDTO, ArmDTO aDTO) throws PAException {
        if (StringUtils.equalsIgnoreCase(armDTO.getName().getValue(), aDTO.getName().getValue())) {
            List<InterventionDTO> searchList = PaRegistry.getInterventionService().search(interDTO);
            if (CollectionUtils.isNotEmpty(searchList)) {
                Ii interventionIi  = searchList.get(0).getIdentifier();
                List<PlannedActivityDTO> plaList = PaRegistry.getPlannedActivityService().getByStudyProtocol(
                        studyProtocolIi);
                for (PlannedActivityDTO paDTO : plaList) {
                    getPlannedActivityIi(interventionSet, interventionIi, paDTO);
                }
            }
        }
    }

    /**
     * @param interventionSet
     * @param interventionIi
     * @param paDTO
     */
    private void getPlannedActivityIi(Set<Ii> interventionSet, Ii interventionIi, PlannedActivityDTO paDTO) {
        if (StringUtils.equalsIgnoreCase(IiConverter.convertToString(paDTO.getInterventionIdentifier())
                , IiConverter.convertToString(interventionIi))) {
            interventionSet.add(paDTO.getIdentifier());
        }
    }

    /**
     * Load intervention for a trial.
     * @param listOfInterventionsDTOS
     * @param studyProtocolIi
     * @throws PAException
     */
    private void loadInterventions(List<InterventionDTO> listOfInterventionsDTOS, Ii studyProtocolIi) {
        Ii interventionIi = null;
        for (InterventionDTO dto : listOfInterventionsDTOS) {
            try {
                List<InterventionDTO> searchList = PaRegistry.getInterventionService().search(dto);
                if (CollectionUtils.isNotEmpty(searchList)) {
                    interventionIi  = searchList.get(0).getIdentifier();
                    if (createDrugOrRadiationIntervention(studyProtocolIi, interventionIi, dto)) {
                        continue;
                    }
                    if (StringUtils.equalsIgnoreCase(CdConverter.convertCdToString(dto.getTypeCode())
                        , ActivitySubcategoryCode.PROCEDURE_SURGERY.getCode())) {
                        PlannedProcedureDTO paDto = new PlannedProcedureDTO();
                        paDto.setStudyProtocolIdentifier(studyProtocolIi);
                        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.PLANNED_PROCEDURE));
                        paDto.setInterventionIdentifier(interventionIi);
                        paDto.setTextDescription(dto.getDescriptionText());
                        paDto.setSubcategoryCode(dto.getTypeCode());
                        PaRegistry.getPlannedActivityService().createPlannedProcedure(paDto);
                    } else {
                        PlannedActivityDTO paDto = new PlannedActivityDTO();
                        paDto.setIdentifier(null);
                        paDto.setStudyProtocolIdentifier(studyProtocolIi);
                        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
                        paDto.setInterventionIdentifier(interventionIi);
                        paDto.setTextDescription(dto.getDescriptionText());
                        paDto.setSubcategoryCode(dto.getTypeCode());
                        PaRegistry.getPlannedActivityService().create(paDto);
                    }
                } else {
                    LOG.debug("Intervention " + dto.getName().getValue() + " is not found.");
                }
            } catch (PAException e) {
                LOG.error("error loading intervention" + dto.getTypeCode().getCode(), e);
            }
        }
    }

    /**
     * @param studyProtocolIi
     * @param interventionIi
     * @param dto
     * @throws PAException
     */
    private boolean createDrugOrRadiationIntervention(Ii studyProtocolIi,
            Ii interventionIi, InterventionDTO dto) throws PAException {
        if (StringUtils.equalsIgnoreCase(CdConverter.convertCdToString(dto.getTypeCode())
               , ActivitySubcategoryCode.DRUG.getCode()) || StringUtils.equalsIgnoreCase(
               CdConverter.convertCdToString(dto.getTypeCode()), ActivitySubcategoryCode.RADIATION.getCode())) {
            PlannedSubstanceAdministrationDTO pSADto = new PlannedSubstanceAdministrationDTO();
            pSADto.setStudyProtocolIdentifier(studyProtocolIi);
            pSADto.setInterventionIdentifier(interventionIi);
            pSADto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION));
            pSADto.setTextDescription(dto.getDescriptionText());
            pSADto.setSubcategoryCode(dto.getTypeCode());
            PaRegistry.getPlannedActivityService().createPlannedSubstanceAdministration(pSADto);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Load the Diseases for a trial.
     * @param listOfDiseaseDTOs
     * @param studyProtocolIi
     * @throws PAException
     */
   private void loadConditions(List<PDQDiseaseDTO> listOfDiseaseDTOs, Ii studyProtocolIi) {
        for (PDQDiseaseDTO dto : listOfDiseaseDTOs) {
            StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
            studyDiseaseDTO.setStudyProtocolIdentifier(studyProtocolIi);
            Ii diseaseIdentifier = null;
            try {
                dto.setExactMatch(StConverter.convertToSt("true"));
                List<PDQDiseaseDTO> searchList = PaRegistry.getDiseaseService().search(dto);
                if (CollectionUtils.isNotEmpty(searchList)) {
                    diseaseIdentifier = searchList.get(0).getIdentifier();
                    studyDiseaseDTO.setDiseaseIdentifier(diseaseIdentifier);
                    studyDiseaseDTO.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.TRUE));
                    PaRegistry.getStudyDiseaseService().create(studyDiseaseDTO);
                } else {
                    LOG.debug("Condtion/Disease " + dto.getPreferredName().getValue() + " is not found.");
                }

            } catch (PAException e) {
                LOG.error("error loading condition " + dto.getPreferredName().getValue(), e);
            }
        }
    }

    /**
     * This will load info for Interventional Trial Design -> Design Details,Trial Description
     * and NCI specific information screen.
     * @param interventionalStudyProtocolDTO
     * @param studyProtocolIi
     * @throws PAException
     *
     */
    private void loadStudyProtocol(InterventionalStudyProtocolDTO interventionalStudyProtocolDTO, Ii studyProtocolIi) {
        try {
            InterventionalStudyProtocolDTO ispDTO = PaRegistry.getStudyProtocolService()
                .getInterventionalStudyProtocol(studyProtocolIi);

            ispDTO.setPublicTitle(interventionalStudyProtocolDTO.getPublicTitle());
            ispDTO.setPublicDescription(interventionalStudyProtocolDTO.getPublicDescription());
            ispDTO.setScientificDescription(interventionalStudyProtocolDTO.getScientificDescription());
            ispDTO.setRecordVerificationDate(interventionalStudyProtocolDTO.getRecordVerificationDate());
            ispDTO.setTargetAccrualNumber(interventionalStudyProtocolDTO.getTargetAccrualNumber());
            ispDTO.setKeywordText(interventionalStudyProtocolDTO.getKeywordText());
            String allocationCode =  ALLOCATION_CODE_MAP.get(StringUtils.upperCase(
                CdConverter.convertCdToString(interventionalStudyProtocolDTO.getAllocationCode())));
            ispDTO.setAllocationCode(CdConverter.convertToCd(AllocationCode.getByCode(allocationCode)));
            String blindingSchemaCode =  BLINDING_SCHEMA_MAP.get(StringUtils.upperCase(
                    CdConverter.convertCdToString(interventionalStudyProtocolDTO.getBlindingSchemaCode())));
            BlindingSchemaCode blindingCode = BlindingSchemaCode.getByCode(blindingSchemaCode);
            //PO-3322: If double-blind use default masking roles of "Subject" and "Investigator".
            if (blindingCode == BlindingSchemaCode.DOUBLE_BLIND) {
                ispDTO.setBlindingSchemaCode(CdConverter.convertToCd(blindingCode));
                ispDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(DOUBLE_BLIND_DEFAULT_ROLES));
            } else if (blindingCode == BlindingSchemaCode.SINGLE_BLIND) {
                //PO-3428: Defaulting single-blind to have the default role of "Subject".
                ispDTO.setBlindingSchemaCode(CdConverter.convertToCd(blindingCode));
                ispDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(
                        Arrays.asList(CdConverter.convertToCd(BlindingRoleCode.SUBJECT))));
            }
            ispDTO.setDesignConfigurationCode(interventionalStudyProtocolDTO.getDesignConfigurationCode());
            ispDTO.setStudyClassificationCode(interventionalStudyProtocolDTO.getStudyClassificationCode());
            ispDTO.setNumberOfInterventionGroups(interventionalStudyProtocolDTO.getNumberOfInterventionGroups());
            ispDTO.setAccrualReportingMethodCode(interventionalStudyProtocolDTO.getAccrualReportingMethodCode());

            PaRegistry.getStudyProtocolService().updateInterventionalStudyProtocol(ispDTO, null);
        } catch (PAException e) {
            LOG.error("error loading study protocol's related information" + studyProtocolIi.getExtension(), e);
        }
    }

    /**
     * @param orgCorrelationService the orgCorrelationService to set
     */
    public void setOrgCorrelationService(OrganizationCorrelationServiceRemote orgCorrelationService) {
        this.orgCorrelationService = orgCorrelationService;
    }

    /**
     * @return the orgCorrelationService
     */
    public OrganizationCorrelationServiceRemote getOrgCorrelationService() {
        return orgCorrelationService;
    }
}
