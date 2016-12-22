/**
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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.service.util.MockRegistryUserServiceBean;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;


/**
 * Test class for the {@link ProprietaryTrialManagementBeanLocal}
 * 
 * @author Denis G. Krylov
 */
public class ProprietaryTrialManagementBeanTest extends
        AbstractHibernateTestCase {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ProprietaryTrialManagementBeanLocal bean = new ProprietaryTrialManagementBeanLocal();
    private final ArmServiceLocal armService = new ArmBeanLocal();
    private final DocumentServiceLocal documentService = new DocumentBeanLocal();
    private final DocumentWorkflowStatusServiceLocal documentWrkService = new DocumentWorkflowStatusBeanLocal();
    private final LookUpTableServiceRemote lookUpTableServiceRemote = new MockLookUpTableServiceBean();
    private OrganizationEntityServiceRemote poOrgSvc;
    private PersonEntityServiceRemote poPersonSvc;
    private PoServiceLocator poSvcLoc;
    private ServiceLocator paSvcLoc;
    private final StudyContactServiceLocal studyContactSvc = new StudyContactBeanLocal();
    private final StudyIndldeServiceLocal studyIndldeService = new StudyIndldeBeanLocal();
    private final StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureBeanLocal();
    private final StudyOverallStatusBeanLocal studyOverallStatusService = new StudyOverallStatusBeanLocal();
    private final StudyProtocolBeanLocal studyProtocolService = new StudyProtocolBeanLocal();
    private final StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityBeanLocal();
    private final StudyResourcingServiceLocal studyResourcingService = new StudyResourcingBeanLocal();
    private final StudySiteAccrualStatusBeanLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    private final StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    private final ProtocolQueryServiceLocal protocolQueryServiceLocal = new MockProtocolQueryService();
    private final StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal = mock(StudySiteAccrualAccessServiceLocal.class);
    private final MailManagerServiceLocal mailSvc = mock(MailManagerServiceLocal.class);

    private Ii spIi;
    private RegistryUser studyOwner;
    private File tempDir;
    private String docUploadPath;

    /**
     * Initialization method.
     * 
     * @throws Exception
     *             If an error occurs
     */
    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));

        // Change trial to abbreviated.
        final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        protocol.setProprietaryTrialIndicator(true);
        session.update(protocol);
        session.flush();

        // Remember study owner.
        TestSchema.addOwners(protocol);
        TestSchema.addUpdObject(TestSchema
                .createDocumentWorkflowStatus(protocol));
        studyOwner = protocol.getStudyOwners().iterator().next();
        session.flush();

        // Fix RO's identifier.
        ResearchOrganization ro = (ResearchOrganization) session.get(
                ResearchOrganization.class,
                TestSchema.researchOrganizationIds.get(0));
        ro.setIdentifier("1");
        session.update(ro);
        session.flush();

        // Setup PaEarPropertyReader
        tempDir = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        tempDir.mkdirs();
        Field field = PaEarPropertyReader.class.getDeclaredField("PROPS");
        field.setAccessible(true);
        Properties earProps = (Properties) field.get(null);
        docUploadPath = earProps.getProperty("doc.upload.path");
        earProps.setProperty("doc.upload.path", tempDir.getAbsolutePath());

        // Dummy protocol docs.
        File protocolDir = new File(tempDir, "NCI-2009-00001");
        protocolDir.mkdir();
        FileUtils.writeStringToFile(new File(protocolDir,
                "1-Protocol_Document.doc"), "");
        FileUtils.writeStringToFile(new File(protocolDir,
                "2-IRB_Approval_Document.doc"), "");

        // The rest of the setup is copied from TrialRegistrationServiceTest.
        PAProperties prop = new PAProperties();
        prop.setName("fromaddress");
        prop.setValue("ncictro@mail.nih.gov");
        TestSchema.addUpdObject(prop);

        studySiteAccrualStatusService.setStudySiteAccrualAccessServiceLocal(studySiteAccrualAccessServiceLocal);
        
        studyProtocolService.setProtocolQueryService(protocolQueryServiceLocal);
        
        studyOverallStatusService
                .setDocumentWorkFlowStatusService(documentWrkService);
        studyOverallStatusService.setStudyProtocolService(studyProtocolService);

        bean.setStudyProtocolService(studyProtocolService);
        bean.setDocumentService(documentService);
        bean.setStudySiteService(studySiteService);
        bean.setProtocolQueryServiceLocal(protocolQueryServiceLocal);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setDocWrkFlowStatusService(documentWrkService);
        bean.setUserServiceLocal(new MockRegistryUserServiceBean());
        bean.setPaServiceUtils(new MockPAServiceUtils());
        bean.setStudyResourcingServiceLocal(studyResourcingService);

        CSMUserService.setInstance(new MockCSMUserService());
        bean.setCsmUserService(CSMUserService.getInstance());

        paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);

        when(paSvcLoc.getDocumentWorkflowStatusService()).thenReturn(
                documentWrkService);
        when(paSvcLoc.getLookUpTableService()).thenReturn(
                lookUpTableServiceRemote);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteService);

        OrganizationCorrelationServiceRemote ocsr = new MockOrganizationCorrelationService();
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(ocsr);

        StudyInboxServiceBean studyInboxSvc = new StudyInboxServiceBean();
        studyInboxSvc.setDocWrkFlowStatusService(documentWrkService);
        studyInboxSvc.setProtocolQueryServiceLocal(protocolQueryServiceLocal);
        studyInboxSvc.setStudyResourcingServiceLocal(studyResourcingService);
        studyInboxSvc.setStudySiteServiceLocal(studySiteService);
        studyInboxSvc.setDocumentServiceLocal(documentService);
        studyInboxSvc.setStudySiteAccrualStatusService(studySiteAccrualStatusService);

        StudyMilestoneServiceBean studyMilestoneSvc = new StudyMilestoneServiceBean();
        AbstractionCompletionServiceLocal abstractionCompletionSvc = mock(AbstractionCompletionServiceLocal.class);
        StudyOnholdServiceLocal studyOnholdSvc = new StudyOnholdBeanLocal();

        studyMilestoneSvc
                .setAbstractionCompletionService(abstractionCompletionSvc);
        studyMilestoneSvc.setDocumentWorkflowStatusService(documentWrkService);
        studyMilestoneSvc.setStudyInboxService(studyInboxSvc);
        studyMilestoneSvc.setStudyOnholdService(studyOnholdSvc);
        studyMilestoneSvc.setStudyProtocolService(studyProtocolService);

        when(paSvcLoc.getStudyMilestoneService()).thenReturn(studyMilestoneSvc);
        when(paSvcLoc.getStudyIndldeService()).thenReturn(studyIndldeService);
        when(paSvcLoc.getDocumentService()).thenReturn(documentService);
        when(paSvcLoc.getStudyContactService()).thenReturn(studyContactSvc);
        when(paSvcLoc.getStudyRegulatoryAuthorityService()).thenReturn(
                studyRegulatoryAuthorityService);
        when(paSvcLoc.getStudyProtocolService()).thenReturn(
                studyProtocolService);
        when(paSvcLoc.getStudyDiseaseService()).thenReturn(
                new StudyDiseaseServiceBean());
        when(paSvcLoc.getStudyObjectiveService()).thenReturn(
                new StudyObjectiveBeanLocal());
        when(paSvcLoc.getStratumGroupService()).thenReturn(
                new StratumGroupBeanLocal());
        when(paSvcLoc.getStudyResoucringService()).thenReturn(
                studyResourcingService);
        when(paSvcLoc.getStudyOnholdService()).thenReturn(studyOnholdSvc);
        when(paSvcLoc.getStudyOverallStatusService()).thenReturn(
                studyOverallStatusService);
        when(paSvcLoc.getStudyRecruitmentStatusService()).thenReturn(
                new StudyRecruitmentStatusBeanLocal());
        when(paSvcLoc.getArmService()).thenReturn(armService);
        when(paSvcLoc.getOutcomeMeasureService()).thenReturn(
                studyOutcomeMeasureService);
        when(paSvcLoc.getPlannedMarkerService()).thenReturn(
                new PlannedMarkerServiceBean());
        when(paSvcLoc.getMailManagerService()).thenReturn(mailSvc);

        bean.setMailManagerSerivceLocal(mailSvc);
        bean.setStudyMilestoneService(studyMilestoneSvc);
        bean.setStudyInboxServiceLocal(studyInboxSvc);

        setupPoSvc();
    }

    @After
    public final void cleanup() throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        FileUtils.deleteQuietly(tempDir);
        Field field = PaEarPropertyReader.class.getDeclaredField("PROPS");
        field.setAccessible(true);
        Properties earProps = (Properties) field.get(null);
        earProps.setProperty("doc.upload.path", docUploadPath);
    }

    private void setupPoSvc() throws NullifiedEntityException,
            NullifiedRoleException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poPersonSvc = mock(PersonEntityServiceRemote.class);

        ResearchOrganizationCorrelationServiceRemote roCorrelationSvc = mock(ResearchOrganizationCorrelationServiceRemote.class);
        ClinicalResearchStaffCorrelationServiceRemote crsSvc = mock(ClinicalResearchStaffCorrelationServiceRemote.class);
        HealthCareProviderCorrelationServiceRemote hcpSvc = mock(HealthCareProviderCorrelationServiceRemote.class);

        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        roDTO.setPlayerIdentifier(IiConverter.convertToIi(1L));
        roDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter
                .convertToPoClinicalResearchStaffIi("1")));

        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        crsDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter
                .convertToPoClinicalResearchStaffIi("1")));

        OrganizationDTO org1 = new OrganizationDTO();
        org1.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        org1.setName(EnOnConverter.convertToEnOn("Org Name"));
        org1.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        org1.setPostalAddress(AddressConverterUtil.create(
                "2115 Executive Blvd.", "", "Rockville", "MD", "27852", "USA"));

        OrganizationDTO org2 = new OrganizationDTO();
        org2.setIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        org2.setName(EnOnConverter.convertToEnOn("Org Name 2"));
        org2.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        org2.setPostalAddress(AddressConverterUtil.create(
                "2115 Executive Blvd.", "", "Rockville", "MD", "27852", "USA"));

        HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
        hcpDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter
                .convertToPoHealthcareProviderIi("1")));
        hcpDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));

        PersonDTO person = new PersonDTO();
        person.setIdentifier(IiConverter.convertToPoPersonIi("1"));

        when(
                poOrgSvc.getOrganization(eq(IiConverter
                        .convertToPoOrganizationIi("1")))).thenReturn(org1);
        when(
                poOrgSvc.getOrganization(eq(IiConverter
                        .convertToPoOrganizationIi("2")))).thenReturn(org2);
        when(poPersonSvc.getPerson(any(Ii.class))).thenReturn(person);
        when(roCorrelationSvc.getCorrelation(any(Ii.class))).thenReturn(roDTO);
        when(crsSvc.getCorrelation(any(Ii.class))).thenReturn(crsDTO);
        when(hcpSvc.getCorrelation(any(Ii.class))).thenReturn(hcpDTO);

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(
                roCorrelationSvc);
        when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(
                crsSvc);
        when(poSvcLoc.getHealthCareProviderCorrelationService()).thenReturn(
                hcpSvc);
    }

    @Test
    public void testUpdate() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NEW_NCT_ID");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        // Only very limited set of fields can be changed by update.
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt("NEW TITLE"));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.IV));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter
                .convertToCd(PrimaryPurposeCode.PREVENTION));

        // Alter participating site and its accrual status
        studySiteDTOs.get(0).setProgramCodeText(
                StConverter.convertToSt("NEW_PG_CODE"));
        studySiteDTOs.get(0).setLocalStudyProtocolIdentifier(
                StConverter.convertToSt("NEW_LOC_ID"));
        studySiteDTOs.get(0).setAccrualDateRange(
                IvlConverter.convertTs().convertToIvl(
                        ISOUtil.dateStringToTimestamp("1/1/2011"),
                        ISOUtil.dateStringToTimestamp("1/1/2012")));
        studySiteAccrualDTOs
                .get(0)
                .setStatusCode(
                        CdConverter
                                .convertToCd(RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        studySiteAccrualDTOs.get(0).setStatusDate(
                TsConverter.convertToTs(ISOUtil
                        .dateStringToTimestamp("1/1/2012")));
        
        Mockito.reset(mailSvc);
        bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                leadOrganizationIdentifier, nctIdentifier, summary4TypeCode,
                documents, studySiteDTOs, studySiteAccrualDTOs);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.clear();

        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        assertEquals("NEW TITLE", protocol.getOfficialTitle());
        assertEquals(PhaseCode.IV, protocol.getPhaseCode());
        assertEquals(PrimaryPurposeCode.PREVENTION,
                protocol.getPrimaryPurposeCode());
        assertEquals(
                "NEW_NCT_ID",
                getStudySite(protocol,
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER)
                        .getLocalStudyProtocolIdentifier());
        final StudySite leadOrg = getStudySite(protocol,
                StudySiteFunctionalCode.LEAD_ORGANIZATION);
        assertEquals("NEW_LEAD_ID", leadOrg.getLocalStudyProtocolIdentifier());
        assertEquals("1", leadOrg.getResearchOrganization().getIdentifier());

        final StudySite treatingSite = getStudySite(protocol,
                StudySiteFunctionalCode.TREATING_SITE);
        assertEquals("NEW_PG_CODE", treatingSite.getProgramCodeText());
        assertEquals("NEW_LOC_ID",
                treatingSite.getLocalStudyProtocolIdentifier());

        assertEquals(ISOUtil.dateStringToTimestamp("1/1/2011"),
                treatingSite.getAccrualDateRangeLow());

        assertEquals(ISOUtil.dateStringToTimestamp("1/1/2012"),
                treatingSite.getAccrualDateRangeHigh());

        assertEquals(RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION,
                treatingSite.getStudySiteAccrualStatuses().get(1)
                        .getStatusCode());

        assertEquals(ISOUtil.dateStringToTimestamp("1/1/2012"), treatingSite
                .getStudySiteAccrualStatuses().get(1).getStatusDate());

        List<Document> updatedDocs = session.createQuery(
                "from Document d where d.activeIndicator=true and d.studyProtocol.id="
                        + protocol.getId() + " order by d.fileName").list();
        assertEquals("Updated_Other.doc", updatedDocs.get(0).getFileName());
        assertEquals("Updated_Protocol.doc", updatedDocs.get(1).getFileName());
        
        Mockito.verify(mailSvc).sendUpdateNotificationMail(any(Ii.class), any(String.class));
        
    }
    
    
    @Test(expected = PAException.class)
    public void testUpdateFailure_ValidationBlanks() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = null;
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        // Only very limited set of fields can be changed by update.
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt(""));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd((Lov) null));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd((Lov) null));
        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Lead Organization DTO cannot be null"));           
            Assert.assertTrue(msg.contains("Official Title cannot be null"));
            Assert.assertTrue(msg.contains("Purpose cannot be null"));
            Assert.assertTrue(msg.contains("Phase cannot be null"));
            throw e;
        }

    }

    @Test(expected = PAException.class)
    public void testUpdateFailure_ValidationOwner() throws Exception {
        createNewUser();

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("abc"));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NEW_NCT_ID");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Updates to the trial can only be submitted by either an owner of the trial or a lead organization admin"));
            throw e;
        }

    }

    @Test(expected = PAException.class)
    public void testUpdateFailure_ValidationNctAndProtocolDoc_StudySites_DocWrkStatus()
            throws Exception {

        final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocolIds.get(0));

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        // Alter participating site and its accrual status
        studySiteDTOs.get(0).setIdentifier(
                IiConverter.convertToStudySiteIi(Long.MAX_VALUE));
        studySiteAccrualDTOs.get(0).setStudySiteIi(
                IiConverter.convertToStudySiteIi(Long.MIN_VALUE));

        DocumentWorkflowStatus stat = TestSchema
                .createRejectedDocumentWorkflowStatus(protocol);
        session.saveOrUpdate(stat);
        session.flush();

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Provide either ClinicalTrials.gov Identifier or Protocol Trial Template"));
            Assert.assertTrue(msg
                    .contains("Study site identifier not found for"));
            Assert.assertTrue(msg
                    .contains("Study site identifier not found in Study Site Accrual Status DTO"));
            Assert.assertTrue(msg
                    .contains("Only Trials with processing status Accepted or Abstracted or  "
                            + " Abstraction Verified No Response or   Abstraction Verified No Response can be Updated."));
            stat = TestSchema.createDocumentWorkflowStatus(protocol);
            session.saveOrUpdate(stat);
            session.flush();
            throw e;
        }

    }

    @Test(expected = PAException.class)
    public void testUpdateFailure_ValidationDocuments() throws Exception {

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NCT");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getInvalidStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Document A has an invalid file type"));
            Assert.assertFalse(msg
                    .contains("Document B has an invalid file type"));
            Assert.assertTrue(msg.contains("Document id 100 does not exist"));
            Assert.assertTrue(msg.contains("Document id 200 does not exist"));

            throw e;
        }

    }

    //@Test(expected = PAException.class)
    public void testUpdateFailure_ValidationSummary4() throws Exception {

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = null;
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NCT");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.EXTERNALLY_PEER_REVIEWED);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Summary Four Organization cannot be null"));
            Assert.assertTrue(msg
                    .contains("Please enter valid value for Summary 4 Sponsor Category"));
            throw e;
        }

    }

    @Test
    public void testUpdateFailure_RecuritmentStatus() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(studyOwner
                .getCsmUser().getLoginName()));

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NEW_NCT_ID");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        studySiteDTOs.get(0).setLocalStudyProtocolIdentifier(
                StConverter.convertToSt(""));
        studySiteDTOs.get(0).setAccrualDateRange(
                IvlConverter.convertTs().convertToIvl(
                        ISOUtil.dateStringToTimestamp("1/1/2023"),
                        ISOUtil.dateStringToTimestamp("1/1/2022")));
        studySiteAccrualDTOs.get(0).setStatusCode(
                CdConverter.convertStringToCd("abc"));
        studySiteAccrualDTOs.get(0).setStatusDate(
                TsConverter.convertToTs(ISOUtil
                        .dateStringToTimestamp("1/1/2022")));

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
            Assert.fail();
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Participating sites cannot have an empty Local Trial Identifier"));
            Assert.assertTrue(msg
                    .contains("Please enter valid RecruitmentStatusCode"));
            Assert.assertTrue(msg
                    .contains("Site recruitment Status Date cannot be in the future"));
            Assert.assertTrue(msg
                    .contains("Date Opened for Accrual cannot be in the future"));
            Assert.assertTrue(msg
                    .contains("Date Closed For Accrual cannot be in the future"));
            Assert.assertTrue(msg
                    .contains("Date Closed for Accrual must be same or bigger "
                            + " than Date Opened for Accrual."));
        }

        studySiteDTOs.get(0).setLocalStudyProtocolIdentifier(
                StConverter.convertToSt("LOCAL_SP_ID"));
        studySiteDTOs.get(0).setAccrualDateRange(
                IvlConverter.convertTs().convertToIvl(
                        ISOUtil.dateStringToTimestamp("1/1/2012"), null));
        studySiteAccrualDTOs
                .get(0)
                .setStatusCode(
                        CdConverter
                                .convertToCd(RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE));
        studySiteAccrualDTOs.get(0).setStatusDate(
                TsConverter.convertToTs(ISOUtil
                        .dateStringToTimestamp("1/1/2012")));

        try {
            bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
            Assert.fail();
        } catch (PAException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Assert.assertTrue(msg
                    .contains("Date Closed for Accrual must be a valid date for "
                            + RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE
                                    .getCode()));

        }        
        try {
        	bean.update(null, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        	fail("Study Protocol DTO is null");
        } catch (Exception e) {
            // expected to have dto
        }        
        try {
        	studyProtocolDTO.setIdentifier(null);
        	bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        	fail("Study Protocol DTO identifier is null");
        } catch (Exception e) {
            // expected to have dto identifier
        } 
        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp);
        DocumentBeanLocal documentSer = mock(DocumentBeanLocal.class);
        bean.setDocumentService(documentSer);
        when(paSvcLoc.getDocumentService()).thenReturn(documentSer);
        when(paSvcLoc.getDocumentService().getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<DocumentDTO>());
   	 	CSMUserUtil csmUserService = mock(CSMUserService.class);
   	 	when(csmUserService.getCSMUser(any(String.class))).thenReturn(null);
   	 	bean.setCsmUserService(csmUserService);
        try {
        	bean.update(spDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, null,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        	fail("ClinicalTrials.gov Identifier is required as there are no Documents");
        } catch (Exception e) {
            // expected to have studysite identifier
        }
        try {
        	studySiteDTOs.get(0).setStudyProtocolIdentifier(null);
        	studySiteDTOs.get(0).setIdentifier(null);
        	spDTO.setUserLastCreated(null);
        	bean.update(spDTO, leadOrganizationDTO, summary4Org,
                    leadOrganizationIdentifier, nctIdentifier,
                    summary4TypeCode, documents, studySiteDTOs,
                    studySiteAccrualDTOs);
        	fail("Study Protocol Identifier  from Study Site cannot be null Study Site Identifier cannot be null ");
        } catch (Exception e) {
            // expected to have studysite identifier
        }
    }

    /**
     * 
     */
    private void createNewUser() {
        User user = new User();
        user.setLoginName("abc");
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        TestSchema.addUpdObject(user);

        RegistryUser ru = new RegistryUser();
        ru.setFirstName("Test");
        ru.setLastName("User");
        ru.setEmailAddress("test@example.com");
        ru.setPhone("123-456-7890");
        ru.setCsmUser(user);
        ru.setUserLastCreated(user);
        ru.setUserLastUpdated(user);
        TestSchema.addUpdObject(ru);
    }

    @Test(expected = PAException.class)
    public void testUpdateFailure_NonIndustrial() throws Exception {
        // Change trial to non-abbreviated.
        final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        protocol.setProprietaryTrialIndicator(false);
        session.update(protocol);
        session.flush();

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        OrganizationDTO summary4Org = getSummary4Org();
        St leadOrganizationIdentifier = StConverter.convertToSt("NEW_LEAD_ID");
        St nctIdentifier = StConverter.convertToSt("NEW_NCT_ID");
        Cd summary4TypeCode = CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL);
        List<DocumentDTO> documents = getStudyDocuments();
        List<StudySiteDTO> studySiteDTOs = getStudySites();
        List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs = getStudySiteAccrualStatusDTO(studySiteDTOs);

        bean.update(studyProtocolDTO, leadOrganizationDTO, summary4Org,
                leadOrganizationIdentifier, nctIdentifier, summary4TypeCode,
                documents, studySiteDTOs, studySiteAccrualDTOs);

    }

    private StudySite getStudySite(StudyProtocol protocol,
            StudySiteFunctionalCode functionalCode) {
        final Session session = PaHibernateUtil.getCurrentSession();
        for (StudySite site : protocol.getStudySites()) {
            if (site.getFunctionalCode().equals(functionalCode)) {
                return (StudySite) session.get(StudySite.class, site.getId());
            }
        }
        return null;
    }

    private List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusDTO(
            List<StudySiteDTO> studySiteDTOs) throws PAException {
        List<StudySiteAccrualStatusDTO> list = new ArrayList<StudySiteAccrualStatusDTO>();
        for (StudySiteDTO siteDTO : studySiteDTOs) {
            list.add(studySiteAccrualStatusService
                    .getCurrentStudySiteAccrualStatusByStudySite(siteDTO
                            .getIdentifier()));
        }
        return list;
    }

    private List<StudySiteDTO> getStudySites() throws PAException {
        List<StudySiteDTO> list = new ArrayList<StudySiteDTO>();
        for (Long siteID : TestSchema.studySiteIds) {
            final StudySiteDTO site = studySiteService.get(IiConverter
                    .convertToStudySiteIi(siteID));
            if (site.getFunctionalCode().getCode()
                    .equals(StudySiteFunctionalCode.TREATING_SITE.getCode())) {
                list.add(site);
            }
        }
        return list;
    }

    private InterventionalStudyProtocolDTO getInterventionalStudyProtocol()
            throws PAException {
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService
                .getInterventionalStudyProtocol(spIi);
        return studyProtocolDTO;
    }

    private OrganizationDTO getLeadOrg() {
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter
                .convertToPoOrganizationIi("1"));
        return leadOrganizationDTO;
    }

    private OrganizationDTO getSummary4Org() {
        OrganizationDTO summary4OrgDTO = new OrganizationDTO();
        summary4OrgDTO
                .setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        return summary4OrgDTO;
    }

    private List<DocumentDTO> getStudyDocuments() {
        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc
                .setFileName(StConverter.convertToSt("Updated_Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document"
                .getBytes()));
        protocolDoc.setTypeCode(CdConverter
                .convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO otherDoc = new DocumentDTO();
        otherDoc.setFileName(StConverter.convertToSt("Updated_Other.doc"));
        otherDoc.setText(EdConverter.convertToEd("Other".getBytes()));
        otherDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        return Arrays.asList(protocolDoc, otherDoc);
    }

    private List<DocumentDTO> getInvalidStudyDocuments() {
        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setIdentifier(IiConverter.convertToDocumentIi(100L));
        protocolDoc.setFileName(StConverter.convertToSt("A"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document"
                .getBytes()));
        protocolDoc.setTypeCode(CdConverter
                .convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO otherDoc = new DocumentDTO();
        otherDoc.setIdentifier(IiConverter.convertToDocumentIi(200L));
        otherDoc.setFileName(StConverter.convertToSt("B"));
        otherDoc.setText(EdConverter.convertToEd("Other".getBytes()));
        otherDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        return Arrays.asList(protocolDoc, otherDoc);
    }

}
