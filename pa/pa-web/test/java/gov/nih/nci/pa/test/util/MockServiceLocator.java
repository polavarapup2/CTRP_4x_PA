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
package gov.nih.nci.pa.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
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
import gov.nih.nci.pa.service.StratumGroupBeanLocal;
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
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockOrganizationCorrelationService;
import gov.nih.nci.service.MockPlannedActivityService;
import gov.nih.nci.service.MockProtocolQueryService;
import gov.nih.nci.service.MockRegistryUserService;
import gov.nih.nci.service.MockStudyDiseaseService;
import gov.nih.nci.service.MockStudyIndIdeService;
import gov.nih.nci.service.MockStudyMilestoneService;
import gov.nih.nci.service.MockStudyOnholdService;
import gov.nih.nci.service.MockStudyOverallStatusService;
import gov.nih.nci.service.MockStudyProtocolService;
import gov.nih.nci.service.MockStudySiteService;
import gov.nih.nci.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.service.util.MockStudySiteAccrualAccessService;
import gov.nih.nci.service.util.MockTSRReportGeneratorService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;


/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {

    public static AccrualDiseaseTerminologyServiceRemote accDiseaseTerminologyService = Mockito.mock(AccrualDiseaseTerminologyServiceRemote.class);

    private final StudyIdentifiersService studyIdentifiersService = Mockito.mock(StudyIdentifiersService.class);
    private final StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
    private final StudyOverallStatusServiceLocal studyOverallStatusService = new MockStudyOverallStatusService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final PlannedActivityServiceLocal plannedActivityService = new MockPlannedActivityService();
    private final StudyOnholdServiceLocal studyOnholdService = new MockStudyOnholdService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final StudyDiseaseServiceLocal studyDiseaseService = new MockStudyDiseaseService();
    private final StudyMilestoneServicelocal studyMilestoneService = new MockStudyMilestoneService();
    private final RegistryUserServiceLocal registryUserService = new MockRegistryUserService();
    private final StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService = new MockStudySiteAccrualAccessService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    private final StudyIndldeServiceLocal studyIndIdeService = new MockStudyIndIdeService();
    private final TSRReportGeneratorServiceRemote tsrReportGeneratorService = new MockTSRReportGeneratorService();
    private FamilyProgramCodeService familyProgramCodeService;

    /**
     * @return mock service
     */
    @Override
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @return StudyOverallStatusServiceRemote
     */
    @Override
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    /**
     * @return StudySiteServiceRemote
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
        StudySiteAccrualStatusServiceLocal ssas = mock(StudySiteAccrualStatusServiceLocal.class);
        try {
            StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
            //dto.setStatusCode(CdConverter.convertStringToCd("statuscd"));
            dto.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.ACTIVE.getCode()));
            dto.setStatusDate(TsConverter.convertToTs(new Date()));
            dto.setComments(StConverter.convertToSt("some comments"));
			when(ssas.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return ssas;
    }

    /**
     * @return InterventionServiceLocal
     */
    @Override
    public InterventionServiceLocal getInterventionService() {
        InterventionServiceLocal svc = mock(InterventionServiceLocal.class);
        List<InterventionDTO> results = new ArrayList<InterventionDTO>();
        InterventionDTO dto = new InterventionDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("Test Int. 001"));
        dto.setCtGovTypeCode(CdConverter.convertToCd(InterventionTypeCode.DRUG));
        results.add(dto);

        dto = new InterventionDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setName(StConverter.convertToSt("Test Int. 002"));
        dto.setCtGovTypeCode(CdConverter.convertToCd(InterventionTypeCode.DEVICE));
        results.add(dto);
        try {
            when(svc.search(any(InterventionDTO.class))).thenReturn(results);
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    @Override
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return plannedActivityService;
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    @Override
    public InterventionAlternateNameServiceLocal getInterventionAlternateNameService() {
        List<InterventionAlternateNameDTO> results = new ArrayList<InterventionAlternateNameDTO>();
        InterventionAlternateNameDTO dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setInterventionIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("altername 1"));
        results.add(dto);

        dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setInterventionIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("altername 2"));
        results.add(dto);

        InterventionAlternateNameServiceLocal svc = mock(InterventionAlternateNameServiceLocal.class);
        try {
            when(svc.getByIntervention(any(Ii.class))).thenReturn(results);
            when(svc.getByIntervention(any(Ii[].class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }


    /**
     * @return StudyOnholdServiceRemote
     */
    @Override
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return studyOnholdService;
    }

    /**
     * @return null
     */
    @Override
    public PAOrganizationServiceRemote getPAOrganizationService() {
        PAOrganizationServiceRemote svc = mock(PAOrganizationServiceRemote.class);
        try {
            when(svc.getOrganizationByIndetifers(any(Organization.class))).thenAnswer(new Answer<Organization>() {
                @Override
                public Organization answer(InvocationOnMock invocation) throws Throwable {
                    Object[] args = invocation.getArguments();
                    Organization org = (Organization) args[0];
                    Organization result = new Organization();
                    if (org.getId().equals(1L)) {
                        result.setName("Organization #1");
                    } else if (org.getId().equals(2L)) {
                        result.setName("Organization #2");
                        result.setStatusCode(EntityStatusCode.NULLIFIED);
                        //result.setIdentifier("2");
                    }
                    return result;
                }
            });
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     *
     * @return PAPersonServiceRemote
     */
    @Override
    public PAPersonServiceRemote getPAPersonService() {
        return null;
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    @Override
    public RegulatoryInformationServiceLocal getRegulatoryInformationService() {
        RegulatoryInformationServiceLocal svc = mock(RegulatoryInformationServiceLocal.class);
        try {
            when(svc.getDistinctCountryNames()).thenReturn(new ArrayList<CountryRegAuthorityDTO>());
            when(svc.getDistinctCountryNamesStartWithUSA()).thenReturn(new ArrayList<CountryRegAuthorityDTO>());
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
        StudyResourcingServiceLocal svc = mock(StudyResourcingServiceLocal.class);

        StudyResourcingDTO dto = new StudyResourcingDTO();
        dto.setActiveIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("CA"));
        dto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        dto.setInactiveCommentText(StConverter.convertToSt("test"));
        dto.setNciDivisionProgramCode(CdConverter.convertStringToCd(NciDivisionProgramCode.CCR.getCode()));
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("NIH"));
        dto.setSerialNumber(StConverter.convertToSt("1"));
        dto.setOrganizationIdentifier(IiConverter.convertToPaOrganizationIi(2L));
        dto.setTypeCode(CdConverter.convertStringToCd("code"));
        
        List<StudyResourcingDTO> dtoList = new ArrayList<StudyResourcingDTO>();
        dtoList.add(dto);
        try {
            when(svc.getStudyResourcingById(any(Ii.class))).thenReturn(dto);
            when(svc.getStudyResourcingByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyResourcingDTO>());
            when(svc.getSummary4ReportedResourcing(any(Ii.class))).thenReturn(dtoList);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return null;
    }

    @Override
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpService;
    }

    @Override
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    @Override
    public DocumentServiceLocal getDocumentService() {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Protocol_Document.doc"));
        docDTO.setText(EdConverter.convertToEd("test".getBytes()));
        docDTO.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));

        DocumentServiceLocal svc = mock(DocumentServiceLocal.class);
        try {
            when(svc.get(any(Ii.class))).thenReturn(docDTO);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public StudySiteContactServiceLocal getStudySiteContactService() {
        StudySiteContactServiceLocal svc = mock(StudySiteContactServiceLocal.class);
        List<StudySiteContactDTO> results = new ArrayList<StudySiteContactDTO>();
        try {
            when(svc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public PAHealthCareProviderLocal getPAHealthCareProviderService() {
        PaPersonDTO paPersonDTO = new PaPersonDTO();
        paPersonDTO.setFullName("John Investigator");

        PAHealthCareProviderLocal svc = mock(PAHealthCareProviderLocal.class);
        try {
            when(svc.getPersonsByStudySiteId(any(Long.class), any(String.class))).thenReturn(Arrays.asList(paPersonDTO));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        List<StudyOutcomeMeasureDTO> results = new ArrayList<StudyOutcomeMeasureDTO>();

        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        results.add(dto);

        dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        results.add(dto);

        StudyOutcomeMeasureServiceLocal svc = mock(StudyOutcomeMeasureServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(results);
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public ArmServiceLocal getArmService() {
        ArmServiceLocal svc = mock(ArmServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<ArmDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndIdeService;
    }

    @Override
    public CTGovXmlGeneratorServiceLocal getCTGovXmlGeneratorService() {
        CTGovXmlGeneratorServiceLocal svc = mock(CTGovXmlGeneratorServiceLocal.class);
        try {
            when(svc.generateCTGovXml(any(Ii.class))).thenReturn("Test XML");
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public AbstractionCompletionServiceLocal getAbstractionCompletionService() {
        AbstractionCompletionServiceLocal svc = mock(AbstractionCompletionServiceLocal.class);
        try {
            when(svc.validateAbstractionCompletion(any(Ii.class))).thenReturn(new ArrayList<AbstractionCompletionDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    @Override
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        PDQDiseaseAlternameServiceLocal svc = mock(PDQDiseaseAlternameServiceLocal.class);
        PDQDiseaseAlternameDTO dto = new PDQDiseaseAlternameDTO();
        dto.setAlternateName(StConverter.convertToSt("name1"));
        try {
            when(svc.getByDisease(any(Ii.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    @Override
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        PDQDiseaseParentServiceRemote svc = mock(PDQDiseaseParentServiceRemote.class);
        PDQDiseaseParentDTO dto = new PDQDiseaseParentDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setParentDiseaseIdentifier(IiConverter.convertToIi("1"));
        List<PDQDiseaseParentDTO> results = Arrays.asList(dto);

        try {
            when(svc.getByChildDisease(any(Ii.class))).thenReturn(results);
            when(svc.getByChildDisease(any(Ii[].class))).thenReturn(results);
            when(svc.getByParentDisease(any(Ii.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    @Override
    public PDQDiseaseServiceLocal getDiseaseService() {
        PDQDiseaseServiceLocal svc = mock(PDQDiseaseServiceLocal.class);
        PDQDiseaseDTO dto = new PDQDiseaseDTO();
        dto.setDiseaseCode(StConverter.convertToSt("code"));
        dto.setDisplayName(StConverter.convertToSt("disease"));
        dto.setNtTermIdentifier(StConverter.convertToSt("1"));
        dto.setPreferredName(StConverter.convertToSt("disease"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        try {
            when(svc.search(any(PDQDiseaseDTO.class))).thenReturn(Arrays.asList(dto));
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    @Override
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return studyDiseaseService;
    }

    /**
     * @return StudyContact
     */
    @Override
    public StudyContactServiceLocal getStudyContactService() {
        StudyContactServiceLocal svc = mock (StudyContactServiceLocal.class);
        List<StudyContactDTO> results = new ArrayList<StudyContactDTO>();
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(results);
            when(svc.getByStudyProtocol(any(Ii.class), any(List.class))).thenReturn(results);
            when(svc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(results);
            when(svc.search(any(StudyContactDTO.class), any(gov.nih.nci.coppa.services.LimitOffset.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        } catch (TooManyResultsException e) {
            //Unreachable
        }

        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return studyMilestoneService;
    }

    @Override
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return tsrReportGeneratorService;
    }

    @Override
    public TSRReportGeneratorServiceLocal getTSRReportGeneratorServiceLocal() {
        return tsrReportGeneratorService;
    }

    @Override
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    @Override
    public MailManagerServiceLocal getMailManagerService() {
        MailManagerServiceLocal svc = mock(MailManagerServiceLocal.class);
        return svc;
    }

    @Override
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        StudyObjectiveServiceLocal svc = mock(StudyObjectiveServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyObjectiveDTO>());
        } catch (PAException e) {
            //unreachable
        }
        return svc;
    }

    /**
     * @return the stratumGroupService
     */
    @Override
    public StratumGroupServiceLocal getStratumGroupService() {
        return mock(StratumGroupBeanLocal.class);
    }

    /**
     * @return the studyMilestoneTasksService
     */
    @Override
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return mock(StudyMilestoneTasksServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return studySiteAccrualAccessService;
    }

    @Override
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    @Override
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return mock(StudyRecruitmentStatusServiceLocal.class);
    }
    /**
     * @return the studyInboxService
     */
    @Override
    public StudyInboxServiceLocal getStudyInboxService() {
        StudyInboxServiceLocal svc = mock(StudyInboxServiceLocal.class);
        return svc;
    }

    @Override
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return mock(TrialRegistrationServiceLocal.class);
    }

    @Override
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        StudySiteOverallStatusServiceLocal svc = mock(StudySiteOverallStatusServiceLocal.class);
        return svc;
    }

    @Override
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        StudyCheckoutServiceLocal svc = mock(StudyCheckoutServiceLocal.class);
        return svc;
    }

    @Override
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        PlannedSubstanceAdministrationServiceRemote svc = mock(PlannedSubstanceAdministrationServiceRemote.class);
        return svc;
    }

    @Override
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return null;
    }

    @Override
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return null;
    }

    @Override
    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        ParticipatingSiteServiceLocal svc = mock(ParticipatingSiteServiceLocal.class);
        ParticipatingSiteDTO dto = new ParticipatingSiteDTO();
        Ii ii = new Ii();
        ii.setExtension("1");
        dto.setIdentifier(ii);
        try {
			when(svc.createStudySiteParticipant(any(StudySiteDTO.class), any(StudySiteAccrualStatusDTO.class), any(Ii.class))).thenReturn(dto);
		} catch (PAException e) {
			
		}
		return svc;
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
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
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        Map<String, String> values = new HashMap<String, String>();
        MarkerAttributesServiceLocal msl = mock(MarkerAttributesServiceLocal.class);
        values.put("ASSAY_TYPE1", "Other");
        values.put("BIOMARKER_USE1", "Integral");
        values.put("BIOMARKER_PURPOSE1", "Stratification Factor");
        values.put("SPECIMEN_TYPE1", "Serum");
        values.put("Unspecified1", "SPECIMEN_COLLECTION");
        values.put("EVALUATION_TYPE1", "Subtyping");
        try {
            when(msl.getAllMarkerAttributes()).thenReturn(values);
        } catch (PAException e) {
          //Unreachable
        }
        return msl;
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
        AuditLogRecord markerRecord = new AuditLogRecord(AuditType.INSERT, "PLANNED_MARKER", 1L, "testUser", new Date());
        AuditLogDetail markerDetail = new AuditLogDetail(markerRecord, "name", "", "name");

        AuditLogRecord nciRecord = new AuditLogRecord(AuditType.INSERT, "STUDY_RESOURCING", 1L, "testUser", new Date());
        AuditLogDetail nciDetail = new AuditLogDetail(nciRecord, "programCodeText", "", "programCode");

        AuditTrailServiceLocal svc = mock(AuditTrailServiceLocal.class);

        when(svc.getAuditTrail(eq(StudyResourcing.class), any(Ii.class), any(Date.class), any(Date.class)))
            .thenReturn(Arrays.asList(nciDetail));
        when(svc.getAuditTrailByStudyProtocol(eq(PlannedMarker.class), any(Ii.class), any(Date.class), any(Date.class)))
            .thenReturn(Arrays.asList(markerDetail));
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        ParticipatingOrgServiceLocal svc = mock(ParticipatingOrgServiceLocal.class);
        List<ParticipatingOrgDTO> rList = new ArrayList<ParticipatingOrgDTO>();
        ParticipatingOrgDTO r1 = new ParticipatingOrgDTO();
        r1.setName("Site 1");
        r1.setPoId("123");
        r1.setProgramCodeText("Program Code Text");
        r1.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE);
        r1.setRecruitmentStatusDate(new Timestamp((new Date()).getTime()));
        r1.setStatusCode(FunctionalRoleStatusCode.PENDING);
        r1.setStudySiteId(1L);
        r1.setTargetAccrualNumber(100);
        List<PaPersonDTO> personList = new ArrayList<PaPersonDTO>();
        personList.add(new PaPersonDTO());
        r1.setPrimaryContacts(personList);
        r1.setPrincipalInvestigators(personList);
        r1.setSubInvestigators(personList);
        rList.add(r1);
        ParticipatingOrgDTO r2 = new ParticipatingOrgDTO();
        r2.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        r2.setStudySiteId(2L);
        r2.setPrimaryContacts(new ArrayList<PaPersonDTO>());
        r2.setPrincipalInvestigators(new ArrayList<PaPersonDTO>());
        r2.setSubInvestigators(new ArrayList<PaPersonDTO>());
        rList.add(r2);
        try {
            when(svc.getTreatingSites(any(Long.class))).thenReturn(rList);
        } catch (PAException e) {
            // unreachable
        }
        return svc;
    }

    @Override
    public FamilyServiceLocal getFamilyService() {
        return null;
    }

    @Override
    public CTGovUploadServiceLocal getCTGovUploadService() {        
        return Mockito.mock(CTGovUploadServiceLocal.class);
    }

    @Override
    public PlannedMarkerSyncWithCaDSRServiceLocal getPMWithCaDSRService() {
        return Mockito.mock(PlannedMarkerSyncWithCaDSRServiceLocal.class);
    }

    @Override
    public TrialDataVerificationServiceLocal getTrialDataVerificationService() {
        return Mockito.mock(TrialDataVerificationServiceLocal.class);
    }

    @Override
    public I2EGrantsServiceLocal getI2EGrantsService() {
        return Mockito.mock(I2EGrantsServiceLocal.class);
    }

    @Override
    public CTGovSyncServiceLocal getCTGovSyncService() {       
        return Mockito.mock(CTGovSyncServiceLocal.class);
    }

    @Override
    public CTGovSyncNightlyServiceLocal getCTGovSyncNightlyService() {
        return Mockito.mock(CTGovSyncNightlyServiceLocal.class);
    }

    @Override
    public ProtocolComparisonServiceLocal getProtocolComparisonService() {
        return Mockito.mock(ProtocolComparisonServiceLocal.class);
    }

	@Override
	public PendingPatientAccrualsServiceLocal getPendingPatientAccrualsService() {
		return Mockito.mock(PendingPatientAccrualsServiceLocal.class);
	}

    @Override
    public StudyIdentifiersService getStudyIdentifiersService() {
        return studyIdentifiersService;
    }

    @Override
    public AccrualDiseaseTerminologyServiceRemote getAccrualDiseaseTerminologyService() {
        return accDiseaseTerminologyService;
    }

    @Override
    public AccrualUtilityService getAccrualUtilityService() {
        return Mockito.mock(AccrualUtilityService.class);
    }
    @Override
    public PlannedMarkerSynonymsServiceLocal getPMSynonymService() {
        return Mockito.mock(PlannedMarkerSynonymsServiceLocal.class);
    }

    @Override
    public StudySubjectServiceLocal getStudySubjectService() {
        return Mockito.mock(StudySubjectServiceLocal.class);
    }

    @Override
    public FlaggedTrialService getFlaggedTrialService() {       
        return null;
    }

    @Override
    public StatusTransitionService getStatusTransitionService() {
        StatusTransitionService statusTransitionService =  Mockito.mock(StatusTransitionService.class);
        try {
            when(statusTransitionService.validateStatusTransition(
                    any(AppName.class), any(TrialType.class), any(TransitionFor.class),
                    anyString() , any(Date.class), anyString(), any(Date.class)
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
        return Mockito.mock(StudyRecordService.class);
    }  
    
    @Override
    public StudyProcessingErrorService getStudyProcessingErrorService() {
        return Mockito.mock(StudyProcessingErrorService.class);
    }

	@Override
	public FamilyProgramCodeService getProgramCodesFamilyService() {
        if (familyProgramCodeService == null) {
            familyProgramCodeService =  Mockito.mock(FamilyProgramCodeService.class);
        }
		return  familyProgramCodeService;
	}  
}
