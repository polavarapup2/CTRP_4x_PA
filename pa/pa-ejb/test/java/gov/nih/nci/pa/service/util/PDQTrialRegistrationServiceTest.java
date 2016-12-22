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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceBean;
import gov.nih.nci.pa.service.DocumentServiceBean;
import gov.nih.nci.pa.service.DocumentWorkflowStatusBeanLocal;
import gov.nih.nci.pa.service.MockPoClinicalResearchStaffCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareProviderCorrelationService;
import gov.nih.nci.pa.service.MockPoResearchOrganizationCorrelationService;
import gov.nih.nci.pa.service.MockProtocolQueryService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StratumGroupBeanLocal;
import gov.nih.nci.pa.service.StudyContactServiceBean;
import gov.nih.nci.pa.service.StudyDiseaseServiceBean;
import gov.nih.nci.pa.service.StudyInboxServiceBean;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeBeanLocal;
import gov.nih.nci.pa.service.StudyMilestoneServiceBean;
import gov.nih.nci.pa.service.StudyObjectiveServiceBean;
import gov.nih.nci.pa.service.StudyOnholdBeanLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureBeanLocal;
import gov.nih.nci.pa.service.StudyOverallStatusBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceBean;
import gov.nih.nci.pa.service.StudyRecruitmentStatusBeanLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceBean;
import gov.nih.nci.pa.service.StudyResourcingBeanLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceBean;
import gov.nih.nci.pa.service.StudySiteServiceBean;
import gov.nih.nci.pa.service.TrialRegistrationBeanLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.CorrelationUtils;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author vrushali
 *
 */
public class PDQTrialRegistrationServiceTest extends AbstractHibernateTestCase {
    private final PDQTrialRegistrationServiceBean bean = new PDQTrialRegistrationServiceBean();

    private PoServiceLocator poSvcLoc;
    private OrganizationEntityServiceRemote poOrgSvc;
    private PersonEntityServiceRemote poPersonSvc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;
    private ServiceLocator paSvcLoc;
    private TrialRegistrationBeanLocal trialRegistrationSvc;
    private OrganizationCorrelationServiceRemote orgCorrelationSvc;
    private ProtocolQueryServiceLocal protocolQuerySvc;
    private MailManagerServiceLocal mailManagerSerivceLocal;
    private final URL testXMLUrl = this.getClass().getResource("/sample-pdq-register.xml");
    private final URL testUpdateXMLUrl = this.getClass().getResource("/sample-pdq-update.xml");
    private final Map<Ii, OrganizationDTO> mockOrgs = new HashMap<Ii, OrganizationDTO>();
    private final PersonDTO mockPerson = new PersonDTO();
    private OrganizationalContactCorrelationServiceRemote orgContactSvc;
    protected LookUpTableServiceRemote lookupSvc;
    protected FamilyServiceRemote familySvc;
    protected IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote;
    protected HealthCareFacilityCorrelationServiceRemote poHcfSvc;
    

    @Before
    public void setUp() throws Exception {
        setupOrgCorrelationSvcMock();
        setupPoSvc();
        setUpPaSvc();

        Country c = new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        c.setName("United States");
        TestSchema.addUpdObject(c);

        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("Food and Drug Administration");
        ra.setCountry(c);
        TestSchema.addUpdObject(ra);

        RegulatoryAuthority ra2 = new RegulatoryAuthority();
        ra2.setAuthorityName("Institutional Review Board");
        ra2.setCountry(c);
        TestSchema.addUpdObject(ra2);
        
        User csmUser = new User();
        csmUser.setLoginName("loginName");
        csmUser.setFirstName("firstName");
        csmUser.setLastName("lastName");
        csmUser.setUpdateDate(new Date());
        TestSchema.addUpdObject(csmUser);
        
        RegistryUser create = new RegistryUser();
        create.setAddressLine("xxxxx");
        create.setAffiliateOrg("aff");
        create.setCity("city");
        create.setCountry("country");
        create.setCsmUser(csmUser);
        create.setFirstName("firstname");
        create.setLastName("lastname");
        create.setMiddleName("middlename");
        create.setPhone("1111");
        create.setPostalCode("00000");
        create.setState("va");
        create.setPrsOrgName("prsOrgName");
        create.setAffiliatedOrganizationId(501L);
        create.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        TestSchema.addUpdObject(create);
        
    }

    /**
     * @throws PAException
     *
     */
    private void setUpPaSvc() throws PAException, IOException , NoSuchFieldException, IllegalAccessException {
        CSMUserService.setInstance(new MockCSMUserService());

        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);

        mailManagerSerivceLocal = mock(MailManagerServiceLocal.class);
        
        lookupSvc = mock(LookUpTableServiceRemote.class);
        
        when(lookupSvc.getPropertyValue("ctep.ccr.learOrgIds")).thenReturn("NCICCR");

        trialRegistrationSvc = new TrialRegistrationBeanLocal();
        trialRegistrationSvc.setStudyOverallStatusService(new StudyOverallStatusBeanLocal());
        trialRegistrationSvc.setStudyIndldeService(new StudyIndldeBeanLocal());
        final StudyProtocolServiceBean studyProtocolService = new StudyProtocolServiceBean();
        studyProtocolService.setProtocolQueryService(new MockProtocolQueryService());
        trialRegistrationSvc.setStudyProtocolService(studyProtocolService);
        trialRegistrationSvc.setOcsr(orgCorrelationSvc);
        StudySiteServiceBean studySiteService = new StudySiteServiceBean();
        CorrelationUtils corrUtils = mock(CorrelationUtils.class);
        ResearchOrganization roBO = new ResearchOrganization();
        roBO.setId(1L);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(roBO);
        studySiteService.setCorrUtils(corrUtils);
		trialRegistrationSvc.setStudySiteService(studySiteService);
        trialRegistrationSvc.setRegistryUserServiceLocal(new MockRegistryUserServiceBean());
        trialRegistrationSvc.setDocumentWorkFlowStatusService(new DocumentWorkflowStatusBeanLocal());
        trialRegistrationSvc.setStudyInboxServiceLocal(new StudyInboxServiceBean());
        trialRegistrationSvc.setStudyRegulatoryAuthorityService(new StudyRegulatoryAuthorityBeanLocal());
        trialRegistrationSvc.setMailManagerSerivceLocal(mailManagerSerivceLocal);
        trialRegistrationSvc.setDocumentService(new DocumentServiceBean());
        trialRegistrationSvc.setStudyRelationshipService(new StudyRelationshipServiceBean());
        trialRegistrationSvc.setRegulatoryInfoBean(new RegulatoryInformationBean());
        trialRegistrationSvc.setStudyResourcingService(mock(StudyResourcingServiceLocal.class));
        trialRegistrationSvc.setLookUpTableServiceRemote(lookupSvc);
        
        
        
        // cannot use Mockito because multiple other methods in PAServiceUtils are used
        // in the process of registering a trial.
        trialRegistrationSvc.setPaServiceUtils(new MockPAServiceUtils());

        TSRReportGeneratorServiceRemote mockTsrGeneratorSvc = mock(TSRReportGeneratorServiceRemote.class);
        ByteArrayOutputStream tsrReport = new ByteArrayOutputStream();
        tsrReport.write("Mock TSR Report".getBytes());
        when(mockTsrGeneratorSvc.generateRtfTsrReport(any(Ii.class))).thenReturn(tsrReport);
        trialRegistrationSvc.setTsrReportService(mockTsrGeneratorSvc);

        StudyMilestoneServiceBean studyMilestoneSvc = new StudyMilestoneServiceBean();
        AbstractionCompletionServiceBean abstractionCompletionSvc = mock(AbstractionCompletionServiceBean.class);
        studyMilestoneSvc.setAbstractionCompletionService(abstractionCompletionSvc);
        studyMilestoneSvc.setDocumentWorkflowStatusService(new DocumentWorkflowStatusBeanLocal());
        studyMilestoneSvc.setMailManagerService(mailManagerSerivceLocal);
        StudyInboxServiceLocal mockInboxSvc = mock(StudyInboxServiceLocal.class);
        studyMilestoneSvc.setStudyInboxService(mockInboxSvc);
        studyMilestoneSvc.setStudyOnholdService(new StudyOnholdBeanLocal());
        studyMilestoneSvc.setStudyProtocolService(new StudyProtocolBeanLocal());

        when(paSvcLoc.getTrialRegistrationService()).thenReturn(trialRegistrationSvc);
        when(paSvcLoc.getStudyMilestoneService()).thenReturn(studyMilestoneSvc);
        when(paSvcLoc.getDocumentWorkflowStatusService()).thenReturn(new DocumentWorkflowStatusBeanLocal());
        when(paSvcLoc.getStudyIndldeService()).thenReturn(new StudyIndldeBeanLocal());
        when(paSvcLoc.getStudyResoucringService()).thenReturn(new StudyResourcingBeanLocal());
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorrelationSvc);
        when(paSvcLoc.getStudyContactService()).thenReturn(new StudyContactServiceBean());
        when(paSvcLoc.getStudyRegulatoryAuthorityService()).thenReturn(new StudyRegulatoryAuthorityBeanLocal());
        when(paSvcLoc.getRegulatoryInformationService()).thenReturn(new RegulatoryInformationBean());
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteService);
        when(paSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolService);
        when(paSvcLoc.getStudyDiseaseService()).thenReturn(new StudyDiseaseServiceBean());
        when(paSvcLoc.getStudyObjectiveService()).thenReturn(new StudyObjectiveServiceBean());
        when(paSvcLoc.getStratumGroupService()).thenReturn(new StratumGroupBeanLocal());
        when(paSvcLoc.getStudyOverallStatusService()).thenReturn(new StudyOverallStatusBeanLocal());
        when(paSvcLoc.getStudyOnholdService()).thenReturn(new StudyOnholdBeanLocal());
        when(paSvcLoc.getStudyRecruitmentStatusService()).thenReturn(new StudyRecruitmentStatusBeanLocal());
        when(paSvcLoc.getArmService()).thenReturn(new ArmServiceBean());
        when(paSvcLoc.getStudySiteContactService()).thenReturn(new StudySiteContactServiceBean());
        when(paSvcLoc.getOutcomeMeasureService()).thenReturn(new StudyOutcomeMeasureBeanLocal());
        when(paSvcLoc.getTSRReportGeneratorService()).thenReturn(new TSRReportGeneratorServiceBean());
        when(paSvcLoc.getTSRReportGeneratorServiceLocal()).thenReturn(new TSRReportGeneratorServiceBean());
        when(paSvcLoc.getDocumentService()).thenReturn(new DocumentServiceBean());
        when(paSvcLoc.getLookUpTableService()).thenReturn(new MockLookUpTableServiceBean());

        List<StudyProtocolQueryDTO> queryResults = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO result = new StudyProtocolQueryDTO();
        result.setLeadOrganizationName("Arthur G. James Cancer Hospital and Richard J. Solove Research Institute at "
                + "Ohio State University Comprehensive Cancer Center");
        result.setPiFullName("William E. Carson");
        result.setStudyProtocolId(1L);
        queryResults.add(result);

        protocolQuerySvc = mock(ProtocolQueryServiceBean.class);
        when(protocolQuerySvc.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
            .thenReturn(new ArrayList<StudyProtocolQueryDTO>()).thenReturn(queryResults);
        bean.setProtocolQueryService(protocolQuerySvc);
        bean.setPaServiceUtils(new MockPAServiceUtils());
        
       File tempDir;
        tempDir = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        tempDir.mkdirs();
        Field field = PaEarPropertyReader.class.getDeclaredField("PROPS");
        field.setAccessible(true);
        Properties earProps = (Properties) field.get(null);
        earProps.setProperty("doc.upload.path", tempDir.getAbsolutePath());
    }

    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException, CurationException, EntityValidationException, NullifiedRoleException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poPersonSvc = mock(PersonEntityServiceRemote.class);
        orgContactSvc = mock(OrganizationalContactCorrelationServiceRemote.class);
        familySvc = mock(FamilyServiceRemote.class);
        identifiedOrganizationCorrelationServiceRemote =mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        poHcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(new MockPoResearchOrganizationCorrelationService());
        when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(new MockPoClinicalResearchStaffCorrelationService());
        when(poSvcLoc.getHealthCareProviderCorrelationService()).thenReturn(new MockPoHealthCareProviderCorrelationService());
        when(poSvcLoc.getOrganizationalContactCorrelationService()).thenReturn(orgContactSvc);
        when(poSvcLoc.getFamilyService()).thenReturn(familySvc);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifiedOrganizationCorrelationServiceRemote);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(poHcfSvc);
        
        when(orgContactSvc.search(any(OrganizationalContactDTO.class))).thenReturn(new ArrayList<OrganizationalContactDTO>());
        
        Ii ii = new Ii();
        ii.setRoot(IiConverter.ORGANIZATIONAL_CONTACT_ROOT);
        ii.setExtension("1");
        when(orgContactSvc.createCorrelation(any(OrganizationalContactDTO.class))).thenReturn(ii);
        
        when(orgContactSvc.getCorrelation(any(Ii.class))).thenReturn(new OrganizationalContactDTO());

        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenAnswer(new Answer<List<OrganizationDTO>>() {
            @Override
            public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                OrganizationDTO org = (OrganizationDTO) args[0];
                org.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
                OrganizationDTO mockOrg = new OrganizationDTO();
                mockOrg.setIdentifier(org.getIdentifier());
                mockOrg.setName(org.getName());
                mockOrg.setPostalAddress(org.getPostalAddress());
                mockOrg.setStatusCode(CdConverter.convertToCd(EntityStatusCode.PENDING));
                mockOrg.setTelecomAddress(org.getTelecomAddress());
                mockOrgs.put(org.getIdentifier(), mockOrg);
                return Arrays.asList(org);
            }});
        when(poOrgSvc.getOrganization(any(Ii.class))).thenAnswer(new Answer<OrganizationDTO>() {
            @Override
            public OrganizationDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii id = (Ii) args[0];
                return mockOrgs.get(id);
            }});

        when(poPersonSvc.search(any(PersonDTO.class), any(LimitOffset.class))).thenAnswer(new Answer<List<PersonDTO>>() {
            @Override
            public List<PersonDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                PersonDTO person = (PersonDTO) args[0];
                person.setIdentifier(IiConverter.convertToPoPersonIi("1"));
                mockPerson.setIdentifier(person.getIdentifier());
                mockPerson.setName(person.getName());
                return Arrays.asList(person);
            }});
        
        when(
                poOrgSvc.search(any(OrganizationSearchCriteriaDTO.class), 
                        any(LimitOffset.class)))
                .thenAnswer(new Answer<List<OrganizationDTO>>() {
                    @Override
                    public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] arguments = invocation.getArguments();
                        if (arguments != null && arguments.length > 0 && arguments[0] != null) {
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
                        return null;
                    }
                });
        
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
        
       
        
        when(identifiedOrganizationCorrelationServiceRemote.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(ctepList);
        
        
        mockPerson.setIdentifier(IiConverter.convertToPoPersonIi("1"));
        mockPerson.setPostalAddress(AddressConverterUtil.create("UNKNOWN", "UNKNOWN", "UNKNOWN", "MD", "00000",
        "USA"));
        mockPerson.setStatusCode(CdConverter.convertToCd(EntityStatusCode.PENDING));
        mockPerson.setTelecomAddress(DSetConverter.convertListToDSet(Arrays.asList("111-111-1111"),
                DSetConverter.TYPE_PHONE, null));
        when(poPersonSvc.getPerson(any(Ii.class))).thenReturn(mockPerson);

        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);
    }

    private void setupOrgCorrelationSvcMock() throws PAException {
        orgCorrelationSvc = new OrganizationCorrelationServiceBean();
        bean.setOrgCorrelationService(orgCorrelationSvc);
    }

    @Test
    public void testLoadPDQXml() throws PAException, IOException {
      
        try {
            assertNull(bean.loadRegistrationElementFromPDQXml(null,null));
            fail("URL is not set, call setUrl first");
        } catch (Exception e) {
            assertEquals("URL is not set, call setUrl first.", e.getMessage());
        }
        
        Ii trialIi = bean.loadRegistrationElementFromPDQXml(testXMLUrl, "loginName");
        assertNotNull("Null identifier returned when registering a trial", trialIi);

        Ii oldNciId = PAUtil.getAssignedIdentifier(PaRegistry.getStudyProtocolService().getStudyProtocol(trialIi));

        //Accept the trial then re-run to test updating.
        bean.getPaServiceUtils().createMilestone(trialIi, MilestoneCode.SUBMISSION_ACCEPTED,
                StConverter.convertToSt("Accepted."), null);

        PaHibernateUtil.getCurrentSession().flush();
        Ii newTrialIi = bean.loadRegistrationElementFromPDQXml(testUpdateXMLUrl, "loginName");
        Ii newNciId = PAUtil.getAssignedIdentifier(PaRegistry.getStudyProtocolService().getStudyProtocol(trialIi));
        assertNotNull("Null identifier returned when updating a trial.", trialIi);
        assertFalse("Study Protocol id should not be equal.", trialIi.getExtension().equals(newTrialIi.getExtension()));
        assertEquals("NCI IDs should be equal.", oldNciId.getExtension(), newNciId.getExtension());
    }

    @Test
    public void testProperties() {
        assertNotNull(bean.getPaServiceUtils());
        assertNotNull(bean.getOrgCorrelationService());
        assertNotNull(bean.getProtocolQueryService());
        bean.setPaServiceUtils(null);
        assertNull(bean.getPaServiceUtils());
    }
}
