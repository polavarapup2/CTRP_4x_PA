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
package gov.nih.nci.pa.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.enums.AssayUseCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.TissueCollectionMethodCode;
import gov.nih.nci.pa.enums.TissueSpecimenTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
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
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorOptions;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author ludetc
 *
 * Extend this class whenever you need mocikto-mocked services.
 * Set the instances in your test class.
 */
@Ignore
public class AbstractMockitoTest {

    // platform independent new line character
    protected static final String NEWLINE = System.getProperty("line.separator");

    protected PoServiceLocator poSvcLoc;
    protected StudyProtocolServiceLocal spSvc;
    protected StudySiteServiceLocal studySiteSvc;
    protected RegistryUserServiceLocal regUserSvc;
    protected StudyIndldeServiceLocal studyIndIdeSvc;
    protected OrganizationCorrelationServiceRemote orgSvc;
    protected StudyContactServiceLocal studyContactSvc;
    protected gov.nih.nci.pa.service.correlation.CorrelationUtils corUtils;
    protected CorrelationUtils commonsCorrUtils = new CorrelationUtils();
    protected StudyRegulatoryAuthorityServiceLocal studyRegAuthSvc;
    protected RegulatoryInformationServiceLocal regulInfoSvc;
    protected StudyOverallStatusServiceLocal studyOverallStatusSvc;
    protected StudyRecruitmentStatusServiceLocal studyRecruitmentStatusSvc;
    protected StudyOutcomeMeasureServiceLocal studyOutcomeMeasureSvc;
    protected StudyDiseaseServiceLocal studyDiseaseSvc;
    protected ArmServiceLocal armSvc;
    protected PlannedActivityServiceLocal plannedActSvc;
    protected DocumentWorkflowStatusServiceLocal dwsSvc;
    protected PDQDiseaseServiceLocal diseaseSvc;
    protected StudySiteAccrualStatusServiceLocal studySiteAccrualStatusSvc;
    protected StudySiteContactServiceLocal studySiteContactSvc;
    protected OrganizationEntityServiceRemote poOrgSvc;
    protected PersonEntityServiceRemote poPerSvc;
    protected ResearchOrganizationCorrelationServiceRemote poRoSvc;
    protected InterventionServiceLocal interventionSvc;
    protected StudyResourcingServiceLocal studyResourcingSvc;
    protected InterventionAlternateNameServiceLocal interventionAltNameSvc;
    protected DocumentServiceLocal documentSvc;
    protected LookUpTableServiceRemote lookupSvc;
    protected ClinicalResearchStaffCorrelationServiceRemote poCrsSvc;
    protected HealthCareFacilityCorrelationServiceRemote poHcfSvc;
    protected IdentifiedPersonCorrelationServiceRemote poIpSvc;
    protected StratumGroupServiceLocal stratumGroupSvc;
    protected PlannedMarkerServiceLocal plannedMarkerSvc;
    protected MailManagerServiceLocal mailManagerSvc;
    protected ProtocolQueryServiceLocal protocolQueryServiceLocal;
    protected CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorServiceLocal;
    protected FamilyServiceRemote familySvc;
    protected AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyServiceRemote;
    protected CTGovSyncServiceLocal ctGovSyncServiceLocal;
    protected IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote;

    protected Ii spId;
    protected InterventionalStudyProtocolDTO spDto;
    protected StudySiteDTO studySiteDto;
    protected List<StudySiteDTO> studySiteDtoList;
    protected List<StratumGroupDTO> stratumGroupDtoList;
    protected RegistryUser regUser;
    protected StudyIndldeDTO studySiteIndIdeDto;
    protected List<StudyIndldeDTO> studySiteIndIdeDtoList;
    protected Organization org;
    protected List<Organization> orgList;
    protected StudyContactDTO studyContactDto;
    protected List<StudyContactDTO> scDtoList;
    protected Person person;
    protected StudyRegulatoryAuthorityDTO studyRegulatoryAuthDto;
    protected RegulatoryAuthority ra;
    protected Country country;
    protected StudyOverallStatusDTO studyOverallStatusDto;
    protected StudyRecruitmentStatusDTO studyRecruitmentStatusDto;
    protected InterventionalStudyProtocolDTO interventionalSPDto;
    protected StudyOutcomeMeasureDTO studyOutcomeMeasureDto;
    protected List<StudyOutcomeMeasureDTO> studyOutcomeMeasureDtoList;
    protected StudyDiseaseDTO studyDiseaseDto;
    protected List<StudyDiseaseDTO> studyDiseaseDtoList;
    protected ArmDTO armDto;
    protected ResearchOrganization researchOrg;
    protected ClinicalResearchStaff clinicalReStaff;
    protected ClinicalResearchStaffDTO clinicalReStaffDto;
    protected HealthCareFacility healthCareFacility;
    protected HealthCareFacilityDTO healthCareFacilityDto;
    protected ResearchOrganizationDTO researchOrgDto;
    protected List<ArmDTO> armDtoList;
    protected PlannedActivityDTO plannedActDto;
    protected List<PlannedActivityDTO> plannedActDtoList;
    protected DocumentWorkflowStatusDTO dwsDto;
    protected List<DocumentWorkflowStatusDTO> dwsDtoList;
    protected PDQDiseaseDTO diseaseDto;
    protected PersonDTO personDto;
    protected PAContactDTO paContactDto;
    protected OrganizationDTO orgDto;
    protected StudySiteAccrualStatusDTO studyStieAccrualStatusDto;
    protected StudySiteContactDTO studySiteContactDto;
    protected List<StudySiteContactDTO> studySiteContactDtoList;
    protected PlannedEligibilityCriterionDTO plannedECDto;
    protected List<PlannedEligibilityCriterionDTO> plannedEcDtoList;
    protected InterventionDTO interventionDto;
    protected InterventionAlternateNameDTO interventionAltNameDto;
    protected List<InterventionAlternateNameDTO> interventionAltNameDtoList;
    protected NonInterventionalStudyProtocolDTO observationalSPDto;
    protected List<StudyResourcingDTO> studyResourcingDtoList;
    protected PlannedMarkerDTO plannedMarkerDto;
    protected List<PlannedMarkerDTO> plannedMarkerDtoList;
    protected List<IdentifiedPersonDTO> identifiedPersonDtoList;
    protected IdentifiedPersonDTO identifiedPersonDto;
    protected List<StudyProtocolQueryDTO> queryDTOList = new ArrayList<StudyProtocolQueryDTO>();
    

    @Before
    public void setUp() throws Exception {
       setupSpDto();

       setupSsDto();

       setupRegUser();

       setupSIndDto();

       setupOrg();

       DSet<Tel> telAd = setupScDto();

       setupPerson(telAd);

       setupSraDto();

       setupSubGroup();

       ra = new RegulatoryAuthority();
       country = new Country();

       studyOverallStatusDto = new StudyOverallStatusDTO();
       studyOverallStatusDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.WITHDRAWN.getCode()));
       studyOverallStatusDto.setStatusDate(TsConverter.convertToTs(new Timestamp(0)));
       studyOverallStatusDto.setStudyProtocolIdentifier(spId);

       studyRecruitmentStatusDto = new StudyRecruitmentStatusDTO();
       studyRecruitmentStatusDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.COMPLETED));

       setupIspDto();

       setupSomDto();

       setupSdDto();

       setupArmDto();

       setupPlActDto();

       setupPlannedMarkerDto();

       setupDwsDto();

       diseaseDto = new PDQDiseaseDTO();
       diseaseDto.setPreferredName(StConverter.convertToSt("some disease"));
       diseaseDto.setDiseaseCode(StConverter.convertToSt("diseaseCode"));
       diseaseDto.setDisplayName(StConverter.convertToSt("menuDisplayName"));
       diseaseDto.setNtTermIdentifier(StConverter.convertToSt("ntTerm"));

       paContactDto = new PAContactDTO();
       paContactDto.setTitle("some title");

       setupOrgDto(telAd);

       studyStieAccrualStatusDto = new StudySiteAccrualStatusDTO();
       studyStieAccrualStatusDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.IN_REVIEW));

       setupSsConDto(telAd);

       setupPlEcDto();

       setupInterDto();

       setupStudyResDtos();

       observationalSPDto = new NonInterventionalStudyProtocolDTO();

       researchOrg = new ResearchOrganization();
       researchOrg.setId(1L);
       researchOrg.setOrganization(org);

       clinicalReStaff = new ClinicalResearchStaff();
       clinicalReStaff.setId(1L);
       clinicalReStaff.setPerson(person);
       clinicalReStaff.setOrganization(org);

       healthCareFacility = new HealthCareFacility();
       healthCareFacility.setId(1L);
       healthCareFacility.setOrganization(org);
       Ii ctepOrgIi = new Ii();
       ctepOrgIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
       ctepOrgIi.setExtension("ctep org id");

       researchOrgDto = new ResearchOrganizationDTO();
       researchOrgDto.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoResearchOrganizationIi("1")));
       researchOrgDto.getIdentifier().getItem().add(ctepOrgIi);
       researchOrgDto.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));

       clinicalReStaffDto = new ClinicalResearchStaffDTO();
       clinicalReStaffDto.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("1")));
       clinicalReStaffDto.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));

       healthCareFacilityDto = new HealthCareFacilityDTO();
       healthCareFacilityDto.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealthCareFacilityIi("1")));
       healthCareFacilityDto.getIdentifier().getItem().add(ctepOrgIi);
       healthCareFacilityDto.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));

       identifiedPersonDto = new IdentifiedPersonDTO();
       Ii assignedIi = new Ii();
       assignedIi.setExtension("ctep");
       assignedIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
       identifiedPersonDto.setAssignedId(assignedIi);
       identifiedPersonDtoList = new ArrayList<IdentifiedPersonDTO>();
       identifiedPersonDtoList.add(identifiedPersonDto);

       setupMocks();
     }

    private void setupSubGroup() {
        stratumGroupDtoList = new ArrayList<StratumGroupDTO>();
        StratumGroupDTO sgDto = new StratumGroupDTO();
        sgDto.setDescription(StConverter.convertToSt(""));
        sgDto.setGroupNumberText(StConverter.convertToSt(""));
        stratumGroupDtoList.add(sgDto);
    }

    private void setupInterDto() {
        interventionDto = new InterventionDTO();

        interventionAltNameDtoList = new ArrayList<InterventionAlternateNameDTO>();
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.SYNONYM));
        interventionAltNameDto.setName(StConverter.convertToSt("duplicate name"));
        interventionAltNameDtoList.add(interventionAltNameDto);
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.ABBREVIATION));
        interventionAltNameDto.setName(StConverter.convertToSt("duplicate name"));
        interventionAltNameDtoList.add(interventionAltNameDto);
        
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.ABBREVIATION));
        interventionAltNameDto.setName(StConverter.convertToSt("Z name starting with Z"));
        interventionAltNameDtoList.add(interventionAltNameDto);
        
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.ABBREVIATION));
        interventionAltNameDto.setName(StConverter.convertToSt("A name starting with A"));
        interventionAltNameDtoList.add(interventionAltNameDto);

        interventionDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));

        interventionDto.setName(StConverter.convertToSt("This is to test if name is more than 160 characters hence"
                + " adding very long name to check. Also this string should contains very long name full 200 length "
                +" string to check we are setting this string 123 " ));
        
    }

    private void setupPlEcDto() {
        plannedEcDtoList = new ArrayList<PlannedEligibilityCriterionDTO>();
        plannedECDto = new PlannedEligibilityCriterionDTO();
        Ivl<Pq> ivlPq = new Ivl<Pq>();
        Pq pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        ivlPq.setLow(pq);
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(1));
        plannedECDto.setCriterionName(StConverter.convertToSt("GENDER"));
        plannedECDto.setEligibleGenderCode(CdConverter.convertStringToCd("M"));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);

        plannedECDto = new PlannedEligibilityCriterionDTO();
        plannedECDto.setCriterionName(StConverter.convertToSt("AGE"));
        ivlPq = new Ivl<Pq>();
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        pq.setUnit("some unit");
        ivlPq.setLow(pq);
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(999L));
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(2));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(true));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);
    }

    private void setupSsConDto(DSet<Tel> telAd) {
        studySiteContactDtoList = new ArrayList<StudySiteContactDTO>();
        studySiteContactDto = new StudySiteContactDTO();
        studySiteContactDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
        studySiteContactDto.setTelecomAddresses(telAd);
        studySiteContactDto.setClinicalResearchStaffIi(IiConverter.convertToPoClinicalResearchStaffIi("1"));
        studySiteContactDto.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        studySiteContactDtoList.add(studySiteContactDto);

        studySiteContactDto = new StudySiteContactDTO();
        studySiteContactDto.setRoleCode(CdConverter
                .convertStringToCd(StudySiteContactRoleCode.COORDINATING_INVESTIGATOR.getCode()));
        studySiteContactDto.setClinicalResearchStaffIi(IiConverter.convertToPoClinicalResearchStaffIi("1"));
        studySiteContactDto.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        studySiteContactDto.setTelecomAddresses(telAd);
        studySiteContactDto.setOrganizationalContactIi(spId);
        studySiteContactDtoList.add(studySiteContactDto);
    }

    private void setupOrgDto(DSet<Tel> telAd) {
        orgDto = new OrganizationDTO();
        orgDto.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        orgDto.setName(EnOnConverter.convertToEnOn("name"));
        orgDto.setTelecomAddress(telAd);
        Ad adr = AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
        orgDto.setPostalAddress(adr);
        orgDto.setName(EnOnConverter.convertToEnOn("some org name"));
    }

    private void setupDwsDto() {
        dwsDtoList = new ArrayList<DocumentWorkflowStatusDTO>();
        dwsDto = new DocumentWorkflowStatusDTO();
        Ivl<Ts> ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        dwsDto.setStatusDateRange(ivlTs);
        dwsDtoList.add(dwsDto);
    }

    private void setupPlActDto() {
        plannedActDtoList = new ArrayList<PlannedActivityDTO>();
        plannedActDto = new PlannedActivityDTO();
        plannedActDto.setCategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
        plannedActDto.setSubcategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
        plannedActDtoList.add(plannedActDto);
    }

    private void setupPlannedMarkerDto() {
        plannedMarkerDtoList = new ArrayList<PlannedMarkerDTO>();
        plannedMarkerDto = new PlannedMarkerDTO();
        plannedMarkerDto.setName(StConverter.convertToSt("Biomarker"));
        plannedMarkerDto.setLongName(StConverter.convertToSt("Biomarker long name"));
        plannedMarkerDto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO Biomarker Code"));
        plannedMarkerDto.setAssayTypeCode(CdConverter.convertToCd(AssayTypeCode.OTHER));
        plannedMarkerDto.setAssayTypeOtherText(StConverter.convertToSt("Assay Type Other Text"));
        plannedMarkerDto.setAssayUseCode(CdConverter.convertToCd(AssayUseCode.INTEGRAL));
        plannedMarkerDto.setAssayPurposeCode(CdConverter.convertToCd(AssayPurposeCode.ELIGIBILITY_CRITERION));
        plannedMarkerDto.setAssayPurposeOtherText(StConverter.convertToSt("Assay Purpose Other Text"));
        plannedMarkerDto.setTissueSpecimenTypeCode(CdConverter.convertToCd(TissueSpecimenTypeCode.TISSUE));
        plannedMarkerDto.setTissueCollectionMethodCode(CdConverter.convertToCd(TissueCollectionMethodCode.MANDATORY));
        plannedMarkerDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        plannedMarkerDtoList.add(plannedMarkerDto);
    }

    private void setupArmDto() {
        armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("Bname"));
        armDto.setDescriptionText(StConverter.convertToSt("some description"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDtoList = new ArrayList<ArmDTO>();
        armDtoList.add(armDto);
        armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("Aname"));
        armDto.setDescriptionText(StConverter.convertToSt("some description"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDtoList.add(armDto);
    }

    private void setupSdDto() {
        studyDiseaseDtoList = new ArrayList<StudyDiseaseDTO>();
        studyDiseaseDto = new StudyDiseaseDTO();
        studyDiseaseDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        studyDiseaseDto.setDiseaseIdentifier(spId);
        studyDiseaseDtoList.add(studyDiseaseDto);

        studyDiseaseDto = new StudyDiseaseDTO();
        studyDiseaseDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        studyDiseaseDtoList.add(studyDiseaseDto);
    }

    private void setupSomDto() {
        studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("some name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.PRIMARY.getCode()));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("A name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.PRIMARY.getCode()));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        
        
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("some name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.SECONDARY.getCode()));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("A name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.SECONDARY.getCode()));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("some name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(false));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.OTHER_PRE_SPECIFIED.getCode()));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("A name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(false));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.OTHER_PRE_SPECIFIED.getCode()));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
    }

    private void setupIspDto() {
        interventionalSPDto = new InterventionalStudyProtocolDTO();
        List<Cd> blindingRoles = new ArrayList<Cd>();
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.CAREGIVER.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.INVESTIGATOR.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.OUTCOMES_ASSESSOR.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.SUBJECT.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd("some unknown code"));
        interventionalSPDto.setBlindedRoleCode(DSetConverter.convertCdListToDSet(blindingRoles));
        interventionalSPDto.setPrimaryPurposeCode(CdConverter.convertStringToCd("PurposeCode"));
        interventionalSPDto.setPhaseCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setDesignConfigurationCode(CdConverter.convertStringToCd("DesignCode"));
        interventionalSPDto.setNumberOfInterventionGroups(IntConverter.convertToInt(0));
        interventionalSPDto.setBlindingSchemaCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setAllocationCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setPrimaryPurposeOtherText(StConverter.convertToSt(""));
        Ivl<Int> ivlint = new Ivl<Int>();
        ivlint.setLow(IntConverter.convertToInt(0));
        interventionalSPDto.setTargetAccrualNumber(ivlint);
    }

    private void setupSraDto() {
        studyRegulatoryAuthDto = new StudyRegulatoryAuthorityDTO();
        studyRegulatoryAuthDto.setRegulatoryAuthorityIdentifier(spId);
    }

    private void setupPerson(DSet<Tel> telAd) {
        person = new Person();
        person.setFirstName("first name");
        person.setLastName("last Name");

        personDto = new PersonDTO();
        personDto.setIdentifier(IiConverter.convertToPoPersonIi("1"));
        personDto.setName(EnPnConverter.convertToEnPn("1", "2", "3", "4", "5"));
        Ad adr = AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
        personDto.setPostalAddress(adr);
        personDto.setTelecomAddress(telAd);
    }



    private DSet<Tel> setupScDto() throws URISyntaxException {
        studyContactDto = new StudyContactDTO();
        studyContactDto.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        studyContactDto.setClinicalResearchStaffIi(IiConverter.convertToPoClinicalResearchStaffIi("1"));
        studyContactDto.setOrganizationalContactIi(spId);
        studyContactDto.setHealthCareProviderIi(spId);
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:X"));
        telSet.add(email);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:111-222-3333ext444"));
        telSet.add(phone);
        TelUrl url = new TelUrl();
        url.setValue(new URI("http://ctrp.com"));
        telSet.add(url);
        telAd.setItem(telSet);
        studyContactDto.setTelecomAddresses(telAd);
        scDtoList = new ArrayList<StudyContactDTO>();
        scDtoList.add(studyContactDto);
        return telAd;
    }

    private void setupStudyResDtos() {
        studyResourcingDtoList = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO studyResDto = new StudyResourcingDTO();
        Cd cd = new Cd();
        cd.setCode("U10");
        studyResDto.setFundingMechanismCode(cd);
        cd = new Cd();
        cd.setCode("Abbreviated");
        studyResDto.setTypeCode(cd);
        cd = new Cd();
        cd.setCode("CA");
        studyResDto.setNihInstitutionCode(cd);
        studyResDto.setNciDivisionProgramCode(cd);
        studyResDto.setSerialNumber(StConverter.convertToSt("177"));
        studyResDto.setActiveIndicator(BlConverter.convertToBl(true));
        studyResourcingDtoList.add(studyResDto);
    }

    private void setupOrg() {
        orgList = new ArrayList<Organization>();
        org = new Organization();
        org.setName("some name");
        orgList.add(org);
    }

    private void setupSIndDto() {
        studySiteIndIdeDto = new StudyIndldeDTO();
        studySiteIndIdeDto.setIndldeTypeCode(CdConverter.convertStringToCd("code"));
        studySiteIndIdeDto.setHolderTypeCode(CdConverter.convertStringToCd("code"));
        studySiteIndIdeDto.setNciDivProgHolderCode(CdConverter.convertStringToCd("code"));
        studySiteIndIdeDto.setNihInstHolderCode(CdConverter.convertStringToCd("code"));
        studySiteIndIdeDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        studySiteIndIdeDto.setExpandedAccessStatusCode(CdConverter.convertStringToCd("Available"));
        studySiteIndIdeDtoList = new ArrayList<StudyIndldeDTO>();
        studySiteIndIdeDtoList.add(studySiteIndIdeDto);
    }

    private void setupRegUser() {
        regUser = new RegistryUser();
        regUser.setPrsOrgName("prs Org Name");
        regUser.setAffiliatedOrganizationId(1L);
    }

    private void setupSsDto() {
        studySiteDtoList = new ArrayList<StudySiteDTO>();
        studySiteDto = new StudySiteDTO();
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter
                .convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode()));
        studySiteDto.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
        studySiteDto.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
        studySiteDto.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.SUSPENDED));
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        studySiteDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LEAD_ORG_1"));
        studySiteDtoList.add(studySiteDto);
        StudySiteDTO leadOrgDTO = new StudySiteDTO();
        leadOrgDTO.setReviewBoardApprovalStatusCode(CdConverter
                .convertStringToCd(""));
        leadOrgDTO.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
        leadOrgDTO.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
        leadOrgDTO.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.ACTIVE));
        leadOrgDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        studySiteDtoList.add(leadOrgDTO);
    }

    private void setupSpDto() {
        spId = new Ii();
        spId.setExtension("1");

        spDto = new InterventionalStudyProtocolDTO();
        spDto.setPublicTitle(StConverter.convertToSt("title"));
        spDto.setAcronym(StConverter.convertToSt("acronym"));
        spDto.setOfficialTitle(StConverter.convertToSt("off title"));
        spDto.setIdentifier(spId);
        spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        spDto.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDto.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        spDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        spDto.setSection801Indicator(BlConverter.convertToBl(true));
        spDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        spDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
        spDto.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode.ABBREVIATED));
        spDto.setStartDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setStartDateTypeCode(CdConverter.convertStringToCd("Actual"));
        spDto.setPrimaryCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd("Anticipated"));
        spDto.setCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setCompletionDateTypeCode(CdConverter.convertStringToCd("Anticipated"));
        spDto.setPublicDescription(StConverter.convertToSt("public description"));
        spDto.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDto.setPublicTitle(StConverter.convertToSt("public title"));
        spDto.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(true));
        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd("TREATMENT"));
        
        spDto.setPhaseCode(CdConverter.convertToCd((Lov)null));
        spDto.setDesignConfigurationCode(CdConverter.convertToCd((Lov)null)); 
        spDto.setNumberOfInterventionGroups(IntConverter.convertToInt((Integer)null));
        spDto.setBlindingSchemaCode(CdConverter.convertToCd((Lov)null));
        spDto.setAllocationCode(CdConverter.convertToCd((Lov)null)) ;
        final Ivl<Int> targetAccrualNumber = new Ivl<Int>();
        targetAccrualNumber.setLow(IntConverter.convertToInt((Integer)null));
        spDto.setTargetAccrualNumber(targetAccrualNumber);


        DSet<Ii> secondaryIdentifiers = new DSet<Ii>();
        Ii assignedId = new Ii();
        assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        assignedId.setExtension("NCI_2010_0001");
        Set<Ii> iis = new HashSet<Ii>();
        iis.add(assignedId);
        
        Ii otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("OtherId");
        iis.add(otherId);
        
        otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("AAAAId");
        iis.add(otherId);
        
        secondaryIdentifiers.setItem(iis);
        
        spDto.setSecondaryIdentifiers(secondaryIdentifiers);
    }

    private void setupMocks() throws PAException, NullifiedRoleException, NullifiedEntityException, IOException, TooManyResultsException {
        setupSpSvcMock();
        setupSsSvcMock();
        setupRegSvcMock();
        setupSIndSvcMock();
        setupOrgSvcMock();
        setupScSvcMock();
        setupCorSvcMock();
        setupSraMock();
        setupRiMock();
        setupSosSvc();
        setupSrsSvc();
        setupSomSvc();
        setupSdSvc();
        setupArmSvc();
        setupPlActSvc();
        setupPlannedMarkerSvc();
        setupDwsSvc();
        setupDisSvc();
        setupSsasSvc();
        setupSsconSvc();
        setupInterSvc();
        setupPoSvc();
        setupStudyResSvc();
        setupDocSvc();
        setupMailMgrSvc();
        setupProtocolQueryServiceMock();
        setupCtGovXmlMock();
        setupAccrualDiseaseMock();

        setupPaRegistry();

    }

    private void setupAccrualDiseaseMock() {
        accrualDiseaseTerminologyServiceRemote = mock(AccrualDiseaseTerminologyServiceRemote.class);
        when(
                accrualDiseaseTerminologyServiceRemote
                        .canChangeCodeSystem(any(Long.class))).thenReturn(true);
    }

    @SuppressWarnings("unchecked")
    private void setupCtGovXmlMock() throws PAException, IOException {
        ctGovXmlGeneratorServiceLocal = mock(CTGovXmlGeneratorServiceLocal.class);
        when(
                ctGovXmlGeneratorServiceLocal.generateCTGovXml(any(Ii.class),
                        any(CTGovXmlGeneratorOptions[].class))).thenReturn(
                IOUtils.toString(getClass().getResourceAsStream(
                        "/CDR360805.xml")));
        when(
                ctGovXmlGeneratorServiceLocal.generateCTGovXml(any(List.class),
                        any(CTGovXmlGeneratorOptions[].class))).thenReturn(
                IOUtils.toString(getClass().getResourceAsStream(
                        "/CDR360805.xml")));        
    }

    private void setupProtocolQueryServiceMock() throws PAException, NullifiedEntityException, NullifiedRoleException, TooManyResultsException {
        protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        final StudyProtocolQueryDTO summaryDTO = new StudyProtocolQueryDTO();
        summaryDTO.setLeadOrganizationName("NCI");
        when(
                protocolQueryServiceLocal
                        .getTrialSummaryByStudyProtocolId(any(Long.class)))
                .thenReturn(summaryDTO);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(queryDTOList);
       
        
       
        
        when(
                protocolQueryServiceLocal
                        .getTrialSummaryByStudyProtocolId(anyLong()))
                .thenAnswer(new Answer<StudyProtocolQueryDTO>() {
                    @Override
                    public StudyProtocolQueryDTO answer(InvocationOnMock invocation) throws Throwable {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                            Long id = (Long) arguments[0];
                          
                          
                            if(id==1L) {
                                StudyProtocolQueryDTO studyProtocolQueryDTOSummary = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTOSummary.setLeadOrganizationId(1L);
                                studyProtocolQueryDTOSummary.setLeadOrganizationPOId(1L);
                                return studyProtocolQueryDTOSummary;
                            }
                            else if(id==2L) {{
                                StudyProtocolQueryDTO studyProtocolQueryDTOSummary = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTOSummary.setLeadOrganizationId(2L);
                                studyProtocolQueryDTOSummary.setLeadOrganizationPOId(2L);
                                return studyProtocolQueryDTOSummary;
                            }
                        }
                            else {
                                StudyProtocolQueryDTO studyProtocolQueryDTOSummary = new StudyProtocolQueryDTO();
                                studyProtocolQueryDTOSummary.setLeadOrganizationId(3L);
                                studyProtocolQueryDTOSummary.setLeadOrganizationPOId(3L);
                                return studyProtocolQueryDTOSummary;
                            }
                        }
                        return null;
                    }
                });
        
      
      
        
        
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0001");
        dto.setNctIdentifier("NCI20120001");
       
        dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(1L);
        dto.setLeadOrganizationPOId(1L);
        
        queryDTOList.add(dto);
        
        dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0002");
        dto.setNctIdentifier("NCI20120002");
        dto.setStudyStatusCode(StudyStatusCode.IN_REVIEW);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(2L);
        dto.setLeadOrganizationPOId(2L);
        
        queryDTOList.add(dto);
        
        dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0002");
        dto.setNctIdentifier("NCI20120002");
        dto.setStudyStatusCode(StudyStatusCode.IN_REVIEW);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(3L);
        dto.setLeadOrganizationPOId(3L);
        
        queryDTOList.add(dto);
        
        

    }

    private void setupPaRegistry() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);

        OrganizationCorrelationServiceRemote orgCorSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgCorSvc.getOrganizationByFunctionRole(any(Ii.class), any(Cd.class))).thenReturn(org);
        when(orgCorSvc.getPOOrgIdentifierByIdentifierType(any(String.class))).thenReturn(new String("1"));
        when(orgCorSvc.getPoResearchOrganizationByEntityIdentifier(any(Ii.class))).thenReturn(new Ii());

        stratumGroupSvc = mock(StratumGroupServiceLocal.class);
        when(stratumGroupSvc.getByStudyProtocol(any(Ii.class))).thenReturn(stratumGroupDtoList);

        PAOrganizationServiceRemote paOrgSvc = mock(PAOrganizationServiceRemote.class);

        lookupSvc = mock(LookUpTableServiceRemote.class);
        ctGovSyncServiceLocal = mock(CTGovSyncServiceLocal.class);
        List<Country> countryList = new ArrayList<Country>();
        Country cnt = new Country();
        cnt.setAlpha3("USA");
        cnt.setAlpha2("US");
        cnt.setName("United States");
        countryList.add(cnt);
        when(lookupSvc.getCountries()).thenReturn(countryList);
        when(lookupSvc.getCountryByName(anyString())).thenReturn(cnt);
        when(lookupSvc.searchCountry(any(Country.class))).thenReturn(countryList);
        when(lookupSvc.getPropertyValue("rss.leadOrgs")).thenReturn("American College of Surgeons Oncology Trials Group");
        when(lookupSvc.getPropertyValue("ctep.ccr.trials")).thenReturn("LEAD_ORG_ID_0002");
        when(lookupSvc.getPropertyValue("ctgov.ftp.enabled")).thenReturn("true");
        when(lookupSvc.getPropertyValue("ctep.ccr.learOrgIds")).thenReturn("NCICCR");
        PDQXmlGeneratorServiceRemote pdqXmlGeneratorSvc = mock(PDQXmlGeneratorServiceRemote.class);
        when(pdqXmlGeneratorSvc.generatePdqXml(any(Ii.class))).thenReturn("<pdq></pdq>");

        when(paRegSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorSvc);
        when(paRegSvcLoc.getPAOrganizationService()).thenReturn(paOrgSvc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(spSvc);
        when(paRegSvcLoc.getPDQXmlGeneratorService()).thenReturn(pdqXmlGeneratorSvc);
        when(paRegSvcLoc.getStudySiteService()).thenReturn(studySiteSvc);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        when(paRegSvcLoc.getStudyResoucringService()).thenReturn(studyResourcingSvc);
        when(paRegSvcLoc.getStratumGroupService()).thenReturn(stratumGroupSvc);
        when(paRegSvcLoc.getMailManagerService()).thenReturn(mailManagerSvc);        
        when(paRegSvcLoc.getRegulatoryInformationService()).thenReturn(regulInfoSvc);
        when(paRegSvcLoc.getAccrualDiseaseTerminologyService()).thenReturn(accrualDiseaseTerminologyServiceRemote);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        clinicalStudy.setOverallStatus("Completed");
        CTGovStudyAdapter ctGovStudyAdapter = new CTGovStudyAdapter(clinicalStudy);
      
        
        when(ctGovSyncServiceLocal.getAdaptedCtGovStudyByNctId(anyString())).thenReturn(ctGovStudyAdapter);
        
        
       
    }

    private void setupDocSvc() throws PAException {
        documentSvc = mock(DocumentServiceLocal.class);
        when(documentSvc.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(null);
    }

    private void setupMailMgrSvc() {
        mailManagerSvc = mock(MailManagerServiceLocal.class);
    }

    private void setupPoSvc() throws NullifiedEntityException, PAException, NullifiedRoleException , TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poPerSvc = mock(PersonEntityServiceRemote.class);
        poRoSvc = mock(ResearchOrganizationCorrelationServiceRemote.class);
        poCrsSvc = mock(ClinicalResearchStaffCorrelationServiceRemote.class);
        poIpSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        poHcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        familySvc = mock(FamilyServiceRemote.class);
      
        identifiedOrganizationCorrelationServiceRemote =mock(IdentifiedOrganizationCorrelationServiceRemote.class);

        when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(orgDto);
        when(poRoSvc.getCorrelation(any(Ii.class))).thenReturn(researchOrgDto);
        when(poCrsSvc.getCorrelation(any(Ii.class))).thenReturn(clinicalReStaffDto);
        when(poIpSvc.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(identifiedPersonDtoList);
        when(poHcfSvc.getCorrelation(any(Ii.class))).thenReturn(healthCareFacilityDto);
        when(poPerSvc.getPerson(any(Ii.class))).thenReturn(personDto);

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(poRoSvc);
        when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(poCrsSvc);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(poIpSvc);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPerSvc);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(poHcfSvc);
        when(poSvcLoc.getFamilyService()).thenReturn(familySvc);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifiedOrganizationCorrelationServiceRemote);
        
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
        id.setExtension("4648");
        id.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        id.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
        ctpDto.setAssignedId(id);
        ctepList.add(ctpDto);
        
        ctpDto = new IdentifiedOrganizationDTO();
        ctpDto.setPlayerIdentifier(IiConverter.convertToIi(2L));
        id = new Ii();
       id.setExtension("4648");
       id.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
       id.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
       ctpDto.setAssignedId(id);
       ctepList.add(ctpDto);
        
       ctpDto = new IdentifiedOrganizationDTO();
        ctpDto.setPlayerIdentifier(IiConverter.convertToIi(3L));
         id = new Ii();
        id.setExtension("NCICCR");
        id.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        id.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
        ctpDto.setAssignedId(id);
        ctepList.add(ctpDto);
        
        when(identifiedOrganizationCorrelationServiceRemote.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(ctepList);
        
           
        
        when(
                poOrgSvc.search(any(OrganizationSearchCriteriaDTO.class), 
                        any(LimitOffset.class)))
                .thenAnswer(new Answer<List<OrganizationDTO>>() {
                    @Override
                    public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                            Long id = new Long(((OrganizationSearchCriteriaDTO) arguments[0]).getIdentifier());
                          
                          
                            if(id==1L) {
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
                            else  if(id==2L) { {
                                List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
                                OrganizationDTO  ctePOrgDto = new OrganizationDTO();
                                ctePOrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi("2"));
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
                            
                        }
                            else   {
                                List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
                                OrganizationDTO  ctePOrgDto = new OrganizationDTO();
                                ctePOrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi("3"));
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
                            
                        }
                        return null;
                    }
                });
    }

    private void setupStudyResSvc() throws PAException {
        studyResourcingSvc = mock(StudyResourcingServiceLocal.class);
        when(studyResourcingSvc.getStudyResourcingByStudyProtocol(any(Ii.class))).thenReturn(studyResourcingDtoList);
        when(studyResourcingSvc.getSummary4ReportedResourcing(any(Ii.class))).thenReturn(studyResourcingDtoList);
    }

    private void setupInterSvc() throws PAException {
        interventionSvc = mock(InterventionServiceLocal.class);
        when(interventionSvc.get(any(Ii.class))).thenReturn(interventionDto);

        interventionAltNameSvc = mock(InterventionAlternateNameServiceLocal.class);
        when(interventionAltNameSvc.getByIntervention(any(Ii.class))).thenReturn(interventionAltNameDtoList);
    }

    private void setupSsconSvc() throws PAException {
        studySiteContactSvc = mock(StudySiteContactServiceLocal.class);
        when(studySiteContactSvc.getByStudySite(any(Ii.class))).thenReturn(studySiteContactDtoList);
        when(studySiteContactSvc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(studySiteContactDtoList);
    }

    private void setupSsasSvc() throws PAException {
        studySiteAccrualStatusSvc = mock(StudySiteAccrualStatusServiceLocal.class);
        when(studySiteAccrualStatusSvc.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(studyStieAccrualStatusDto);
    }

    private void setupDisSvc() throws PAException {
        diseaseSvc = mock(PDQDiseaseServiceLocal.class);
        when(diseaseSvc.get(any(Ii.class))).thenReturn(diseaseDto);
    }

    private void setupDwsSvc() throws PAException {
        dwsSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwsSvc.getByStudyProtocol(any(Ii.class))).thenReturn(dwsDtoList);
        when(dwsSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwsDto);
    }

    private void setupPlActSvc() throws PAException {
        plannedActSvc = mock(PlannedActivityServiceLocal.class);
        when(plannedActSvc.getByStudyProtocol(any(Ii.class))).thenReturn(plannedActDtoList);
        when(plannedActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(plannedEcDtoList);
    }

    private void setupPlannedMarkerSvc() throws PAException {
        plannedMarkerSvc = mock(PlannedMarkerServiceLocal.class);
        when(plannedMarkerSvc.getByStudyProtocol(any(Ii.class))).thenReturn(plannedMarkerDtoList);
        when(plannedMarkerSvc.get(any(Ii.class))).thenReturn(plannedMarkerDto);
    }

    private void setupArmSvc() throws PAException {
        armSvc = mock(ArmServiceLocal.class);
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(armDtoList);
        when(armSvc.getByPlannedActivity(any(Ii.class))).thenReturn(armDtoList);
    }

    private void setupSdSvc() throws PAException {
        studyDiseaseSvc = mock(StudyDiseaseServiceLocal.class);
        when(studyDiseaseSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyDiseaseDtoList);
    }

    private void setupSomSvc() throws PAException {
        studyOutcomeMeasureSvc = mock(StudyOutcomeMeasureServiceLocal.class);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
    }

    private void setupSrsSvc() throws PAException {
        studyRecruitmentStatusSvc = mock(StudyRecruitmentStatusServiceLocal.class);
        when(studyRecruitmentStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyRecruitmentStatusDto);
    }

    private void setupSosSvc() throws PAException {
        studyOverallStatusSvc = mock(StudyOverallStatusServiceLocal.class);
        when(studyOverallStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyOverallStatusDto);
    }

    private void setupRiMock() throws PAException {
        regulInfoSvc = mock(RegulatoryInformationServiceLocal.class);
        when(regulInfoSvc.get(anyLong())).thenReturn(ra);
        when(regulInfoSvc.getRegulatoryAuthorityCountry(anyLong())).thenReturn(country);
        
        final CountryRegAuthorityDTO usa = new CountryRegAuthorityDTO();
        usa.setAlpha3("USA");
        usa.setName("United States");
        when(regulInfoSvc.getDistinctCountryNames()).thenReturn(Arrays.asList(usa));
        when(regulInfoSvc.getRegulatoryAuthorityId(eq("Federal Government"), eq("United States"))).thenReturn(1L);
    }

    private void setupSraMock() throws PAException {
        studyRegAuthSvc = mock(StudyRegulatoryAuthorityServiceLocal.class);
        when(studyRegAuthSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyRegulatoryAuthDto);
    }

    private void setupCorSvcMock() throws PAException, NullifiedRoleException {
        final Answer<StructuralRole> getStructuralRoleByIiAnswer = new Answer<StructuralRole>() {
            @Override
            public StructuralRole answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii input = (Ii) args[0];
                if (ISOUtil.isIiNull(input)) {
                  return null;
                } else if (IiConverter.RESEARCH_ORG_ROOT.equals(input.getRoot())) {
                    return researchOrg;
                } else if (IiConverter.CLINICAL_RESEARCH_STAFF_ROOT.equals(input.getRoot())) {
                    return clinicalReStaff;
                } else if (IiConverter.HEALTH_CARE_FACILITY_ROOT.equals(input.getRoot())) {
                    return healthCareFacility;
                }
                return null;
            }
        };
        corUtils = mock(gov.nih.nci.pa.service.correlation.CorrelationUtils.class);
        when(corUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(corUtils.getContactByPAOrganizationalContactId(anyLong())).thenReturn(paContactDto);
        when(corUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
        when(corUtils.getStructuralRoleByIi(any(Ii.class))).thenAnswer(getStructuralRoleByIiAnswer);

        commonsCorrUtils = mock(CorrelationUtils.class);
        when(commonsCorrUtils.getStructuralRoleByIi(any(Ii.class))).thenAnswer(getStructuralRoleByIiAnswer);
    }

    private void setupScSvcMock() throws PAException {
        studyContactSvc = mock(StudyContactServiceLocal.class);
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(scDtoList);
    }

    private void setupOrgSvcMock() throws PAException {
        orgSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgSvc.getOrganizationByFunctionRole(any(Ii.class), any(Cd.class))).thenReturn(org);
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class))).thenReturn(orgList);
        when(orgSvc.getROByFunctionRole(any(Ii.class), any(Cd.class)))
            .thenReturn(IiConverter.convertToPoResearchOrganizationIi("1"));
    }

    private void setupSIndSvcMock() throws PAException {
        studyIndIdeSvc = mock(StudyIndldeServiceLocal.class);
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studySiteIndIdeDtoList);
    }

    private void setupRegSvcMock() throws PAException {
        regUserSvc = mock(RegistryUserServiceLocal.class);
        when(regUserSvc.getUser(anyString())).thenReturn(regUser);

        List<String> names = new ArrayList<String>();
        names.add("I am a trial owner");
        when(regUserSvc.getTrialOwnerNames(anyLong())).thenReturn(names);
    }

    private void setupSsSvcMock() throws PAException {
        studySiteSvc = mock(StudySiteServiceLocal.class);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studySiteDtoList);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(studySiteDtoList);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(List.class))).thenReturn(studySiteDtoList);
    }

    private void setupSpSvcMock() throws PAException {
        spSvc = mock(StudyProtocolServiceLocal.class);
        when(spSvc.getStudyProtocol((Ii)anyObject())).thenReturn(spDto);
        when(spSvc.getInterventionalStudyProtocol((Ii)anyObject())).thenReturn(interventionalSPDto);
        when(spSvc.getNonInterventionalStudyProtocol(any(Ii.class))).thenReturn(observationalSPDto);
        when(spSvc.getAbstractedCollaborativeTrials()).thenReturn(Arrays.asList((StudyProtocolDTO)spDto));
    }

    public PoServiceLocator getPoSvcLoc() {
        return poSvcLoc;
    }

    public StudyProtocolServiceLocal getSpSvc() {
        return spSvc;
    }

    public StudySiteServiceLocal getStudySiteSvc() {
        return studySiteSvc;
    }

    public RegistryUserServiceLocal getRegUserSvc() {
        return regUserSvc;
    }

    public StudyIndldeServiceLocal getStudyIndIdeSvc() {
        return studyIndIdeSvc;
    }

    public OrganizationCorrelationServiceRemote getOrgSvc() {
        return orgSvc;
    }

    public StudyContactServiceLocal getStudyContactSvc() {
        return studyContactSvc;
    }

    public gov.nih.nci.pa.service.correlation.CorrelationUtils getCorUtils() {
        return corUtils;
    }

    public CorrelationUtils getCommonsCorrUtils() {
        return commonsCorrUtils;
    }

    public StudyRegulatoryAuthorityServiceLocal getStudyRegAuthSvc() {
        return studyRegAuthSvc;
    }

    public RegulatoryInformationServiceLocal getRegulInfoSvc() {
        return regulInfoSvc;
    }

    public StudyOverallStatusServiceLocal getStudyOverallStatusSvc() {
        return studyOverallStatusSvc;
    }

    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusSvc() {
        return studyRecruitmentStatusSvc;
    }

    public StudyOutcomeMeasureServiceLocal getStudyOutcomeMeasureSvc() {
        return studyOutcomeMeasureSvc;
    }

    public StudyDiseaseServiceLocal getStudyDiseaseSvc() {
        return studyDiseaseSvc;
    }

    public ArmServiceLocal getArmSvc() {
        return armSvc;
    }

    public PlannedActivityServiceLocal getPlannedActSvc() {
        return plannedActSvc;
    }

    public DocumentWorkflowStatusServiceLocal getDwsSvc() {
        return dwsSvc;
    }

    public PDQDiseaseServiceLocal getDiseaseSvc() {
        return diseaseSvc;
    }

    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusSvc() {
        return studySiteAccrualStatusSvc;
    }

    public StudySiteContactServiceLocal getStudySiteContactSvc() {
        return studySiteContactSvc;
    }

    public OrganizationEntityServiceRemote getPoOrgSvc() {
        return poOrgSvc;
    }

    public PersonEntityServiceRemote getPoPerSvc() {
        return poPerSvc;
    }

    public ResearchOrganizationCorrelationServiceRemote getPoRoSvc() {
        return poRoSvc;
    }

    public InterventionServiceLocal getInterventionSvc() {
        return interventionSvc;
    }

    public StudyResourcingServiceLocal getStudyResourcingSvc() {
        return studyResourcingSvc;
    }

    public InterventionAlternateNameServiceLocal getInterventionAltNameSvc() {
        return interventionAltNameSvc;
    }

    public DocumentServiceLocal getDocumentSvc() {
        return documentSvc;
    }

    public LookUpTableServiceRemote getLookupSvc() {
        return lookupSvc;
    }

    public ClinicalResearchStaffCorrelationServiceRemote getPoCrsSvc() {
        return poCrsSvc;
    }

    public HealthCareFacilityCorrelationServiceRemote getPoHcfSvc() {
        return poHcfSvc;
    }

    public IdentifiedPersonCorrelationServiceRemote getPoIpSvc() {
        return poIpSvc;
    }

    public StratumGroupServiceLocal getStratumGroupSvc() {
        return stratumGroupSvc;
    }

    public PlannedMarkerServiceLocal getPlannedMarkerSvc() {
        return plannedMarkerSvc;
    }

    public Ii getSpId() {
        return spId;
    }

    public StudyProtocolDTO getSpDto() {
        return spDto;
    }

    public StudySiteDTO getStudySiteDto() {
        return studySiteDto;
    }

    public List<StudySiteDTO> getStudySiteDtoList() {
        return studySiteDtoList;
    }

    public List<StratumGroupDTO> getStratumGroupDtoList() {
        return stratumGroupDtoList;
    }

    public RegistryUser getRegUser() {
        return regUser;
    }

    public StudyIndldeDTO getStudySiteIndIdeDto() {
        return studySiteIndIdeDto;
    }

    public List<StudyIndldeDTO> getStudySiteIndIdeDtoList() {
        return studySiteIndIdeDtoList;
    }

    public Organization getOrg() {
        return org;
    }

    public List<Organization> getOrgList() {
        return orgList;
    }

    public StudyContactDTO getStudyContactDto() {
        return studyContactDto;
    }

    public List<StudyContactDTO> getScDtoList() {
        return scDtoList;
    }

    public Person getPerson() {
        return person;
    }

    public StudyRegulatoryAuthorityDTO getStudyRegulatoryAuthDto() {
        return studyRegulatoryAuthDto;
    }

    public RegulatoryAuthority getRa() {
        return ra;
    }

    public Country getCountry() {
        return country;
    }

    public StudyOverallStatusDTO getStudyOverallStatusDto() {
        return studyOverallStatusDto;
    }

    public StudyRecruitmentStatusDTO getStudyRecruitmentStatusDto() {
        return studyRecruitmentStatusDto;
    }

    public InterventionalStudyProtocolDTO getInterventionalSPDto() {
        return interventionalSPDto;
    }

    public StudyOutcomeMeasureDTO getStudyOutcomeMeasureDto() {
        return studyOutcomeMeasureDto;
    }

    public List<StudyOutcomeMeasureDTO> getStudyOutcomeMeasureDtoList() {
        return studyOutcomeMeasureDtoList;
    }

    public StudyDiseaseDTO getStudyDiseaseDto() {
        return studyDiseaseDto;
    }

    public List<StudyDiseaseDTO> getStudyDiseaseDtoList() {
        return studyDiseaseDtoList;
    }

    public ArmDTO getArmDto() {
        return armDto;
    }

    public ResearchOrganization getResearchOrg() {
        return researchOrg;
    }

    public ClinicalResearchStaff getClinicalReStaff() {
        return clinicalReStaff;
    }

    public ClinicalResearchStaffDTO getClinicalReStaffDto() {
        return clinicalReStaffDto;
    }

    public HealthCareFacility getHealthCareFacility() {
        return healthCareFacility;
    }

    public HealthCareFacilityDTO getHealthCareFacilityDto() {
        return healthCareFacilityDto;
    }

    public ResearchOrganizationDTO getResearchOrgDto() {
        return researchOrgDto;
    }

    public List<ArmDTO> getArmDtoList() {
        return armDtoList;
    }

    public PlannedActivityDTO getPlannedActDto() {
        return plannedActDto;
    }

    public List<PlannedActivityDTO> getPlannedActDtoList() {
        return plannedActDtoList;
    }

    public DocumentWorkflowStatusDTO getDwsDto() {
        return dwsDto;
    }

    public List<DocumentWorkflowStatusDTO> getDwsDtoList() {
        return dwsDtoList;
    }

    public PDQDiseaseDTO getDiseaseDto() {
        return diseaseDto;
    }

    public PersonDTO getPersonDto() {
        return personDto;
    }

    public PAContactDTO getPaContactDto() {
        return paContactDto;
    }

    public OrganizationDTO getOrgDto() {
        return orgDto;
    }

    public StudySiteAccrualStatusDTO getStudyStieAccrualStatusDto() {
        return studyStieAccrualStatusDto;
    }

    public StudySiteContactDTO getStudySiteContactDto() {
        return studySiteContactDto;
    }

    public List<StudySiteContactDTO> getStudySiteContactDtoList() {
        return studySiteContactDtoList;
    }

    public PlannedEligibilityCriterionDTO getPlannedECDto() {
        return plannedECDto;
    }

    public List<PlannedEligibilityCriterionDTO> getPlannedEcDtoList() {
        return plannedEcDtoList;
    }

    public InterventionDTO getInterventionDto() {
        return interventionDto;
    }

    public InterventionAlternateNameDTO getInterventionAltNameDto() {
        return interventionAltNameDto;
    }

    public List<InterventionAlternateNameDTO> getInterventionAltNameDtoList() {
        return interventionAltNameDtoList;
    }

    public NonInterventionalStudyProtocolDTO getObservationalSPDto() {
        return observationalSPDto;
    }

    public List<StudyResourcingDTO> getStudyResourcingDtoList() {
        return studyResourcingDtoList;
    }

    public PlannedMarkerDTO getPlannedMarkerDto() {
        return plannedMarkerDto;
    }

    public List<PlannedMarkerDTO> getPlannedMarkerDtoList() {
        return plannedMarkerDtoList;
    }

    public List<IdentifiedPersonDTO> getIdentifiedPersonDtoList() {
        return identifiedPersonDtoList;
    }

    public IdentifiedPersonDTO getIdentifiedPersonDto() {
        return identifiedPersonDto;
    }
    
    public ProtocolQueryServiceLocal getProtocolQueryServiceLocal() {
        return protocolQueryServiceLocal;
    }
    
    public CTGovXmlGeneratorServiceLocal getCtGovXmlGeneratorServiceLocal() {
        return ctGovXmlGeneratorServiceLocal;
    }

    public CTGovSyncServiceLocal getCtGovSyncServiceLocal() {
        return ctGovSyncServiceLocal;
    }

}
