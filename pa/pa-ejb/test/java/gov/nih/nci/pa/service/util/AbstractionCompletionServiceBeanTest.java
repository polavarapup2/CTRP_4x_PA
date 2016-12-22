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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceBeanTest;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Michael Visee
 */
public class AbstractionCompletionServiceBeanTest {
    private final ArmServiceLocal armService = mock(ArmServiceLocal.class);
    private final DocumentServiceLocal documentService = mock(DocumentServiceLocal.class);
    private final InterventionServiceLocal interventionService = mock(InterventionServiceLocal.class);
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = mock(OrganizationCorrelationServiceRemote.class);
    private final PlannedActivityServiceLocal plannedActivityService = mock(PlannedActivityServiceLocal.class);
    private final PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    private final ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private final RegulatoryInformationServiceLocal regulatoryInformationService = mock(RegulatoryInformationServiceLocal.class);
    private final StudyContactServiceLocal studyContactService = mock(StudyContactServiceLocal.class);
    private final StudyDiseaseServiceLocal studyDiseaseService = mock(StudyDiseaseServiceLocal.class);
    private final StudyIndldeServiceLocal studyIndldeService = mock(StudyIndldeServiceLocal.class);
    private final StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = mock(StudyOutcomeMeasureServiceLocal.class);
    private final StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final StudyRecruitmentStatusServiceLocal studyRecruitmentStatusService = mock(StudyRecruitmentStatusServiceLocal.class);
    private final StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = mock(StudyRegulatoryAuthorityServiceLocal.class);
    private final StudyResourcingServiceLocal studyResourcingService = mock(StudyResourcingServiceLocal.class);
    private final StudySiteServiceLocal studySiteService = mock(StudySiteServiceLocal.class);
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = mock(StudySiteAccrualStatusServiceLocal.class);
    private final StudySiteContactServiceLocal studySiteContactService = mock(StudySiteContactServiceLocal.class);
    private final PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);

    /**
     * Creates a real AbstractionCompletionServiceBean and inject the mock services in it.
     * @return A real AbstractionCompletionServiceBean with mock services injected.
     * @throws PAException 
     */
    private AbstractionCompletionServiceBean createAbstractionCompletionServiceBean() throws PAException {
        AbstractionCompletionServiceBean sbstractionCompletionServiceBean = new AbstractionCompletionServiceBean();
        setDependencies(sbstractionCompletionServiceBean);
        prepareMocks();
        return sbstractionCompletionServiceBean;
    }

    private void prepareMocks() throws PAException {
        PlannedEligibilityCriterionDTO criterionDTO = new PlannedEligibilityCriterionDTO();
        criterionDTO.setCategoryCode(CdConverter.convertStringToCd("Other"));
        when(
                plannedActivityService
                        .getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class)))
                .thenReturn(Arrays.asList(criterionDTO));
    }

    /**
     * Creates a mock AbstractionCompletionServiceBean and inject the mock services in it.
     * @return A mock AbstractionCompletionServiceBean with mock services injected.
     */
    private AbstractionCompletionServiceBean createAbstractionCompletionServiceBeanMock() {
        AbstractionCompletionServiceBean service = mock(AbstractionCompletionServiceBean.class);
        doCallRealMethod().when(service).setArmService(armService);
        doCallRealMethod().when(service).setDocumentService(documentService);
        doCallRealMethod().when(service).setInterventionService(interventionService);
        doCallRealMethod().when(service).setOrganizationCorrelationService(organizationCorrelationService);
        doCallRealMethod().when(service).setPlannedActivityService(plannedActivityService);
        doCallRealMethod().when(service).setPlannedMarkerService(plannedMarkerService);
        doCallRealMethod().when(service).setRegulatoryInformationService(regulatoryInformationService);
        doCallRealMethod().when(service).setStudyContactService(studyContactService);
        doCallRealMethod().when(service).setStudyDiseaseService(studyDiseaseService);
        doCallRealMethod().when(service).setStudyIndldeService(studyIndldeService);
        doCallRealMethod().when(service).setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        doCallRealMethod().when(service).setStudyOverallStatusService(studyOverallStatusService);
        doCallRealMethod().when(service).setStudyProtocolService(studyProtocolService);        
        doCallRealMethod().when(service).setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        doCallRealMethod().when(service).setStudyResourcingService(studyResourcingService);
        doCallRealMethod().when(service).setStudySiteService(studySiteService);
        doCallRealMethod().when(service).setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        doCallRealMethod().when(service).setStudySiteContactService(studySiteContactService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given AbstractionCompletionServiceBean.
     * @param service The AbstractionCompletionServiceBean to setup with mock services
     */
    private void setDependencies(AbstractionCompletionServiceBean service) {
        service.setArmService(armService);
        service.setDocumentService(documentService);
        service.setInterventionService(interventionService);
        service.setOrganizationCorrelationService(organizationCorrelationService);
        service.setPlannedActivityService(plannedActivityService);
        service.setPlannedMarkerService(plannedMarkerService);
        service.setRegulatoryInformationService(regulatoryInformationService);
        service.setStudyContactService(studyContactService);
        service.setStudyDiseaseService(studyDiseaseService);
        service.setStudyIndldeService(studyIndldeService);
        service.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        service.setStudyOverallStatusService(studyOverallStatusService);
        service.setStudyProtocolService(studyProtocolService);        
        service.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        service.setStudyResourcingService(studyResourcingService);
        service.setStudySiteService(studySiteService);
        service.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        service.setStudySiteContactService(studySiteContactService);
        service.setPaServiceUtil(paServiceUtils);
    }
    
    @Test
    public void testStudyprotocolIiIsNull() throws PAException {
    	AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
    	try {
    		sut.validateAbstractionCompletion(null);
    		fail("Study Protocol Identifier is null");
    	} catch (PAException e) {
    		assertEquals("Study Protocol Identifier is null",  e.getMessage());
    	}
    }
    
    @Test
    public void testVerify() throws PAException {
    	AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        Ii spIi = IiConverter.convertToIi(1L);
    	InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
    	 ispDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
         ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
         ispDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
         StudyIndldeDTO sIndDto = new StudyIndldeDTO();
         sIndDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
         List<StudyIndldeDTO> sIndDtoList = new ArrayList<StudyIndldeDTO>();
         sIndDtoList.add(sIndDto);        
        when(studyIndldeService.getByStudyProtocol(any(Ii.class))).thenReturn(sIndDtoList);
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(ispDTO);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(anyLong())).thenReturn(new StudyProtocolQueryDTO());
        
        List<StudySiteDTO> ssDtos = new ArrayList<StudySiteDTO>();
        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ss.setLocalStudyProtocolIdentifier("dde3ef4c-e607-4366-9265-4d4a8b2b285d");
        final ResearchOrganization ro = PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTEP_ORG_NAME);
        ro.setIdentifier("1");
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.getOrganization().setStatusCode(EntityStatusCode.ACTIVE);
        ss.setResearchOrganization(ro);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        ss.setStudyProtocol(sp);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ssDtos.add(Converters.get(StudySiteConverter.class).convertFromDomainToDto(ss));
        ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ss.setLocalStudyProtocolIdentifier("NCT_2013-07-26T12:36:24-4d4a8b2b285d");
        final ResearchOrganization ro2 = PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTGOV_ORG_NAME);
        ro2.setIdentifier("2");
        ro2.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro2.getOrganization().setStatusCode(EntityStatusCode.ACTIVE);
        ss.setResearchOrganization(ro2);
        ss.setStudyProtocol(sp);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ssDtos.add(Converters.get(StudySiteConverter.class).convertFromDomainToDto(ss));
        when(studySiteService.getByStudyProtocol(any(Ii.class), Matchers.anyListOf(StudySiteDTO.class))).thenReturn(ssDtos);
        CorrelationUtils corrUtils = mock(CorrelationUtils.class);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenAnswer(new Answer<StructuralRole>() {
            @Override
            public StructuralRole answer(InvocationOnMock invocation) throws Throwable {
                Object args[] = invocation.getArguments();
                Ii ii = (Ii) args[0];
                if (ii.getExtension().equals("1")) {
                    return ro;
                }
                return ro2;
            }
          });
        sut.setCorrelationUtils(corrUtils);
    	try {
    		sut.validateAbstractionCompletion(spIi);
    		//fail("Study Protocol Identifier is null");
    	} catch (PAException e) {
    		//assertEquals("Study Protocol Identifier is null",  e.getMessage());
    	}
    }

    /**
     * test the isStudySiteRecruiting method with no site.
     * Input: No site associated with the study
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNoSite() throws PAException {
        testIsStudySiteRecruiting(new ArrayList<StudySiteDTO>(), new ArrayList<StudySiteAccrualStatusDTO>(), false);
    }

    /**
     * test the isStudySiteRecruiting method with no site.
     * Input: one site associated with the study
     *        No Accrual status associated with the site
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNoSiteAccrualStatus() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        testIsStudySiteRecruiting(studySites, new ArrayList<StudySiteAccrualStatusDTO>(), false);
    }

    /**
     * test the isStudySiteRecruiting method with no site.
     * Input: one site associated with the study one non recruiting
     *        Accrual status associated with the site
     * Output: false
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingNotRecruitingSite() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        List<StudySiteAccrualStatusDTO> statuses = createStudySiteAccrualStatuses(RecruitmentStatusCode.WITHDRAWN);
        testIsStudySiteRecruiting(studySites, statuses, false);
    }

    /**
     * test the isStudySiteRecruiting method with no site.
     * Input: one site associated with the study one recruiting
     *        Accrual status associated with the site
     * Output: true
     * @throws PAException If an error occurs
     */
    @Test
    public void testIsStudySiteRecruitingRecruitingSite() throws PAException {
        List<StudySiteDTO> studySites = createStudySites();
        List<StudySiteAccrualStatusDTO> statuses = createStudySiteAccrualStatuses(RecruitmentStatusCode.ACTIVE);
        testIsStudySiteRecruiting(studySites, statuses, true);
    }

    /**
     * Creates a list of StudySiteDTO with only one StudySiteDTO.
     * @return a list of StudySiteDTO with only one StudySiteDTO.
     */
    private List<StudySiteDTO> createStudySites() {
        List<StudySiteDTO> studySites = new ArrayList<StudySiteDTO>();
        StudySiteDTO dto = new StudySiteDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        studySites.add(dto);
        return studySites;
    }

    /**
     * Creates a list of StudySiteAccrualStatusDTO with one StudySiteAccrualStatusDTO having the given status.
     * @param accrualStatus The RecruitmentStatusCode to set in the dto.
     * @return a list of StudySiteAccrualStatusDTO with one StudySiteAccrualStatusDTO having the given status
     */
    private List<StudySiteAccrualStatusDTO> createStudySiteAccrualStatuses(RecruitmentStatusCode accrualStatus) {
        List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setStatusCode(CdConverter.convertToCd(accrualStatus));
        studySiteAccrualStatuses.add(dto);
        return studySiteAccrualStatuses;
    }

    private void testIsStudySiteRecruiting(List<StudySiteDTO> studySites,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses, boolean expectedResult) throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        Ii spIi = IiConverter.convertToIi(1L);
        when(studySiteService.getByStudyProtocol(eq(spIi), any(StudySiteDTO.class))).thenReturn(studySites);
        Ii siteId = IiConverter.convertToIi(1L);
        when(studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(siteId))
            .thenReturn(studySiteAccrualStatuses.isEmpty() ? null : studySiteAccrualStatuses.get(0));
        boolean result = sut.isStudySiteRecruiting(spIi);
        assertEquals("Wrong result returned by isStudySiteRecruiting", expectedResult, result);
        ArgumentCaptor<StudySiteDTO> studySiteDTOCaptor = ArgumentCaptor.forClass(StudySiteDTO.class);
        verify(studySiteService).getByStudyProtocol(eq(spIi), studySiteDTOCaptor.capture());
        StudySiteDTO studySiteDto = studySiteDTOCaptor.getValue();
        assertEquals("Wrong functional code passed to studySiteService",
                     StudySiteFunctionalCode.TREATING_SITE.getCode(),
                     CdConverter.convertCdToString(studySiteDto.getFunctionalCode()));
        if (studySites.isEmpty()) {
            verify(studySiteAccrualStatusService, never()).getStudySiteAccrualStatusByStudySite(siteId);
        } else {
            verify(studySiteAccrualStatusService).getCurrentStudySiteAccrualStatusByStudySite(siteId);
        }
    }
    
    /**
     * @throws PAException 
     * 
     */
    @Test
    public void testEnforceEligibilitySamplingAndPopulation() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        NonInterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createNonInterventionalStudyProtocolDTOObj();
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceEligibility(dto, errors);
        assertTrue(errors.hasError(" Sampling Method is required "));
        assertTrue(errors.hasError(" Study Population Description is required "));
        
    }
    
    @Test
    public void testEnforceArmGroup_InterventionalArmType() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setStudyProtocolType(StConverter.convertToSt(InterventionalStudyProtocol.class.getSimpleName()));
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        dto.setName(StConverter.convertToSt("name"));
        List<ArmDTO> armDtos = new ArrayList<>();
        armDtos.add(dto);
        
        when(armService.getByStudyProtocol(eq(spIi))).thenReturn(armDtos);
        sut.enforceArmGroup(spIi, ispDTO, errors);
        assertTrue(errors.hasError("Arm Type is required: "+dto.getName().getValue()));
    }
    
    @Test
    public void testEnforceArmGroup_NonInterventionalArmType() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        NonInterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createNonInterventionalStudyProtocolDTOObj();
        ispDTO.setStudyProtocolType(StConverter.convertToSt(NonInterventionalStudyProtocolDTO.class.getSimpleName()));
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        dto.setName(StConverter.convertToSt("name"));
        List<ArmDTO> armDtos = new ArrayList<>();
        armDtos.add(dto);
        
        when(armService.getByStudyProtocol(eq(spIi))).thenReturn(armDtos);
        sut.enforceArmGroup(spIi, ispDTO, errors);
        assertFalse(errors.hasError("Arm Type is required: "+dto.getName().getValue()));
    }


    
    @Test
    public void testDoubleBlindingSchema() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setBlindingSchemaCode(CdConverter
                .convertToCd(BlindingSchemaCode.DOUBLE_BLIND));
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceBlindingSchemaRules(dto, errors);
        assertTrue(errors.hasError("At least two masking roles must be specified for \"Double Blind\" masking."));
        
    }
    
    @Test
    public void testDcpTrialsCanHaveNA_Pcd() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setPrimaryCompletionDate(TsConverter.convertToTs(new Date()));
        dto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.NA));
        
        when(
                paServiceUtils.getCtepOrDcpId(
                        eq(IiConverter.convertToLong(dto.getIdentifier())),
                        eq(PAConstants.DCP_IDENTIFIER_TYPE))).thenReturn(
                "DCPID");
        
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceTrialStatus(dto, errors);
        assertFalse(errors.hasError("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        
    }
    
    @Test
    public void testOnlyDcpTrialsCanHaveNAPcd() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.NA));
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceTrialStatus(dto, errors);
        assertTrue(errors.hasError("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'."));
        
    }
    
    @Test
    public void testPcdMustBeNullIfNA() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest
                .createInterventionalStudyProtocolDTOObj();
        dto.setPrimaryCompletionDate(TsConverter.convertToTs(new Date()));
        dto.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.NA));
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceTrialStatus(dto, errors);
        assertTrue(errors
                .hasError("When the Primary Completion Date Type is set to 'N/A', "
                        + "the Primary Completion Date must be null."));

    }
    
    @Test
    public void testPcdMustBeProvided() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest
                .createInterventionalStudyProtocolDTOObj();
        dto.setPrimaryCompletionDate(TsConverter.convertToTs(null));
        dto.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceTrialStatus(dto, errors);
        assertTrue(errors
                .hasError("PrimaryCompletionDate must be Entered."));

    }
    
    @Test
    public void testOpenBlindingSchema() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setBlindingSchemaCode(CdConverter
                .convertToCd(BlindingSchemaCode.OPEN));
        
        dto.setBlindedRoleCode(new DSet<Cd>());
        dto.getBlindedRoleCode().setItem(new HashSet<Cd>());
        dto.getBlindedRoleCode().getItem().add(CdConverter.convertToCd(BlindingRoleCode.CAREGIVER));
        
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceBlindingSchemaRules(dto, errors);
        assertTrue(errors.hasError("Open Blinding Schema code cannot have any Blinded codes."));
        
    }
    
    @Test
    public void testSingleBlindingSchema() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setBlindingSchemaCode(CdConverter
                .convertToCd(BlindingSchemaCode.SINGLE_BLIND));
        
        dto.setBlindedRoleCode(new DSet<Cd>());
        dto.getBlindedRoleCode().setItem(new HashSet<Cd>());
        dto.getBlindedRoleCode().getItem().add(CdConverter.convertToCd(BlindingRoleCode.CAREGIVER));
        dto.getBlindedRoleCode().getItem().add(CdConverter.convertToCd(BlindingRoleCode.INVESTIGATOR));
        
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceBlindingSchemaRules(dto, errors);
        assertTrue(errors.hasError("Only one masking role must be specified for \"Single Blind\" masking."));
        
    }


    @Test
    public void testSingleBlindingSingleCodeSchema() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBean();        
        InterventionalStudyProtocolDTO dto = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        dto.setBlindingSchemaCode(CdConverter
                .convertToCd(BlindingSchemaCode.SINGLE_BLIND));
        AbstractionMessageCollection errors = new AbstractionMessageCollection();
        sut.enforceBlindingSchemaRules(dto, errors);
        assertTrue(errors.hasError("Single Blinding Schema code must have 1 Blinded code."));
        
    }
    
}
