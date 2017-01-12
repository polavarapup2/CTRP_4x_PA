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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
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
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author vrushali
 *
 */
public class PDQTrialAbstractionServiceTest extends AbstractHibernateTestCase {
    private final PDQTrialAbstractionServiceBean bean = new PDQTrialAbstractionServiceBean();
    private PoServiceLocator poSvcLoc;
    private OrganizationEntityServiceRemote poOrgSvc;
    private PersonEntityServiceRemote poPersonSvc;
    private ClinicalResearchStaffCorrelationServiceRemote poCrscSvc;
    private HealthCareProviderCorrelationServiceRemote poHcpcSvc;
    private HealthCareFacilityCorrelationServiceRemote poHcfSvc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;
    private IdentifiedOrganizationCorrelationServiceRemote identifierOrgSvc;
    private ServiceLocator paSvcLoc;
    private OrganizationCorrelationServiceRemote orgCorrelationSvc;
    private RegulatoryInformationServiceLocal regulatorySvc;
    private LookUpTableServiceRemote lookupSvc;
    private StudyProtocolServiceLocal studyProtocolSvc;
    private StudySiteBeanLocal studySiteSvc;
    private PDQDiseaseServiceLocal diseaseSvc;
    private InterventionServiceLocal interventionSvc;
    private PlannedActivityServiceLocal plannedActivitySvc;
    private ArmServiceLocal armSvc;
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeseasureSvc;
    private StudyMilestoneServicelocal studyMilestoneSvc;
    private ParticipatingSiteServiceLocal participatingSiteSvc;
    private final URL testXMLUrl = this.getClass().getResource("/CDR64184.xml");
    private final URL testInvalidLocationsXMLUrl = this.getClass().getResource("/CDR64184-invalid-location.xml");
    private final URL testInvalidLocationContactPhoneXMLUrl = this.getClass().getResource("/CDR64184-invalid-location-contact-phone.xml");
    private final URL testDoubleBlindXMLUrl = this.getClass().getResource("/CDR360805.xml");
    private final URL testNoLocationsXMLUrl = this.getClass().getResource("/CDR360805-no-locations.xml");
    private OrganizationalContactCorrelationServiceRemote orgContactSvc;

    @Before
    public void setUp() throws Exception {
        TestSchema.primeData();
        setupPoSvc();
        setUpPaSvc();
        setupOrgCorrelationSvcMock();
    }

    /**
     * @throws PAException
     *
     */
    private void setUpPaSvc() throws PAException {
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        regulatorySvc = mock(RegulatoryInformationServiceLocal.class);
        when(paSvcLoc.getRegulatoryInformationService()).thenReturn(regulatorySvc);
        when(regulatorySvc.getRegulatoryAuthorityId(anyString(),anyString())).thenReturn(1L);
        lookupSvc = mock (LookUpTableServiceRemote.class);
        when(paSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        Country country = new Country();
        country.setAlpha3("USA");
        when(lookupSvc.getCountryByName(anyString())).thenReturn(country);
        studyProtocolSvc = mock(StudyProtocolBeanLocal.class);
        when(paSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolSvc);
        when(studyProtocolSvc.getInterventionalStudyProtocol(any(Ii.class))).thenReturn(
                new InterventionalStudyProtocolDTO());
        studySiteSvc = mock(StudySiteBeanLocal.class);

        StudySiteDTO leadOrg = new StudySiteDTO();
        leadOrg.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        leadOrg.setStatusCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        leadOrg.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("abc"));

        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(Arrays.asList(leadOrg));
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteSvc);
        diseaseSvc = mock(PDQDiseaseServiceLocal.class);
        when(paSvcLoc.getDiseaseService()).thenReturn(diseaseSvc);
        interventionSvc =  mock(InterventionServiceLocal.class);
        when(paSvcLoc.getInterventionService()).thenReturn(interventionSvc);
        plannedActivitySvc =  mock (PlannedActivityServiceLocal.class);
        when(paSvcLoc.getPlannedActivityService()).thenReturn(plannedActivitySvc);
        armSvc =  mock(ArmServiceLocal.class);
        when(paSvcLoc.getArmService()).thenReturn(armSvc);
        studyOutcomeMeseasureSvc =  mock (StudyOutcomeMeasureServiceLocal.class);
        when(paSvcLoc.getOutcomeMeasureService()).thenReturn(studyOutcomeMeseasureSvc);

        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        when(studyProtocolSvc.getStudyProtocol(any(Ii.class))).thenReturn(spDTO);
        studyMilestoneSvc = mock (StudyMilestoneServicelocal.class);
        when(paSvcLoc.getStudyMilestoneService()).thenReturn(studyMilestoneSvc);

        participatingSiteSvc = mock(ParticipatingSiteServiceLocal.class);
        ParticipatingSiteDTO siteDTO = new ParticipatingSiteDTO();
        siteDTO.setIdentifier(IiConverter.convertToIi(1L));
        when(participatingSiteSvc.createStudySiteParticipant(any(StudySiteDTO.class), any(StudySiteAccrualStatusDTO.class),
                any(Ii.class))).thenReturn(siteDTO);
        when(paSvcLoc.getParticipatingSiteService()).thenReturn(participatingSiteSvc);

        StudyOverallStatusDTO overallStatus = new StudyOverallStatusDTO();
        overallStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        StudyOverallStatusServiceLocal studyOverallStatusSvc = mock(StudyOverallStatusServiceLocal.class);
        when(studyOverallStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(overallStatus);

        when(paSvcLoc.getStudyOverallStatusService()).thenReturn(studyOverallStatusSvc);
    }

    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException, CurationException, EntityValidationException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poPersonSvc = mock(PersonEntityServiceRemote.class);
        poCrscSvc = mock(ClinicalResearchStaffCorrelationServiceRemote.class);
        poHcpcSvc = mock(HealthCareProviderCorrelationServiceRemote.class);
        poHcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        orgContactSvc = mock(OrganizationalContactCorrelationServiceRemote.class);
        

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(poCrscSvc);
        when(poSvcLoc.getHealthCareProviderCorrelationService()).thenReturn(poHcpcSvc);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(poHcfSvc);
        when(poSvcLoc.getOrganizationalContactCorrelationService()).thenReturn(orgContactSvc);
        
        when(orgContactSvc.search(any(OrganizationalContactDTO.class))).thenReturn(new ArrayList<OrganizationalContactDTO>());
        
        Ii ii = new Ii();
        ii.setRoot(IiConverter.ORGANIZATIONAL_CONTACT_ROOT);
        ii.setExtension("1");
        when(orgContactSvc.createCorrelation(any(OrganizationalContactDTO.class))).thenReturn(ii);
        
        List<HealthCareFacilityDTO> hcfDtos = new ArrayList<HealthCareFacilityDTO>();
        HealthCareFacilityDTO hcfDto = new HealthCareFacilityDTO();
        DSet<Ii> dset = new DSet<Ii>();
        dset.setItem(new HashSet<Ii>());
        dset.getItem().add(IiConverter.convertToPoHealthCareFacilityIi("2"));
        hcfDto.setIdentifier(dset);
        hcfDtos.add(hcfDto);
        when(poHcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(hcfDtos);

        List<OrganizationDTO> orgDtos = new ArrayList<OrganizationDTO>();
        orgDtos.add(new OrganizationDTO());
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgDtos);

        List<PersonDTO> personDtos = new ArrayList<PersonDTO>();
        personDtos.add(new PersonDTO());
        when(poPersonSvc.search(any(PersonDTO.class), any(LimitOffset.class))).thenReturn(personDtos);

        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);

        identifierOrgSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrgSvc);
        List<IdentifiedOrganizationDTO> orgList = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO idOrgDTO = new IdentifiedOrganizationDTO();
        idOrgDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        when(identifierOrgSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(orgList);
    }

    private void setupOrgCorrelationSvcMock() throws PAException {
        orgCorrelationSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgCorrelationSvc.getPOOrgIdentifierByIdentifierType(anyString())).thenReturn("1");
        bean.setOrgCorrelationService(orgCorrelationSvc);
    }

    @Test
    public void testLoadAbstractionElementPDQXml() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        when(paServiceUtil.findEntity(any(OrganizationDTO.class))).thenReturn(new OrganizationDTO());
        bean.setPaServiceUtils(paServiceUtil);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1l);
        try {
            bean.loadAbstractionElementFromPDQXml(null, null);
            fail("Ii should not be null");
        } catch (PAException e) {
            assertEquals("Ii should not be null.", e.getMessage());
        }
        try {
            bean.loadAbstractionElementFromPDQXml(null, studyProtocolIi);
            fail("URL is not set, call setUrl first");
        } catch (Exception e) {
            assertEquals("URL is not set, call setUrl first.", e.getMessage());
        }
        bean.loadAbstractionElementFromPDQXml(testXMLUrl, studyProtocolIi);
        verify(studyMilestoneSvc, org.mockito.Mockito.times(5)).create(any(StudyMilestoneDTO.class));
        assertNotNull(bean.getOrgCorrelationService());
        assertNotNull(bean.getPaServiceUtils());
    }

    @Test
    public void testLoadAbstractionElementPDQXmlInvalidLocations() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        when(paServiceUtil.findEntity(any(OrganizationDTO.class))).thenReturn(new OrganizationDTO());
        Ii ctepHcfIi = new Ii();
        ctepHcfIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepHcfIi.setExtension("11074");
        HealthCareFacilityDTO criteria = new HealthCareFacilityDTO();
        criteria.setIdentifier(DSetConverter.convertIiToDset(ctepHcfIi));
        List<HealthCareFacilityDTO> hcfDtos = new ArrayList<HealthCareFacilityDTO>();
        when(poHcfSvc.search(refEq(criteria))).thenReturn(hcfDtos);
        bean.setPaServiceUtils(paServiceUtil);
        bean.loadAbstractionElementFromPDQXml(testInvalidLocationsXMLUrl, IiConverter.convertToStudyProtocolIi(1l));
        // testInvalidLocationsXMLUrl has 12 valid locations and 2 invalid locations: one with an empty CTEP-ID, and one
        // with CTEP-ID=11074 (mocked above).
        verify(participatingSiteSvc, org.mockito.Mockito.times(12)).createStudySiteParticipant(any(StudySiteDTO.class),
                any(StudySiteAccrualStatusDTO.class), any(Ii.class));
        assertNotNull(bean.getOrgCorrelationService());
        assertNotNull(bean.getPaServiceUtils());
    }

    @Test(expected = PAException.class)
    public void testLoadAbstractionElementPDQXmlInvalidLocationContactPhone() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        when(paServiceUtil.findEntity(any(OrganizationDTO.class))).thenReturn(new OrganizationDTO());
        bean.setPaServiceUtils(paServiceUtil);
        bean.loadAbstractionElementFromPDQXml(testInvalidLocationContactPhoneXMLUrl, IiConverter.convertToStudyProtocolIi(1l));
    }

    @Test
    public void testLoadAbstractElementDoubleBlindPDQXml() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock(PAServiceUtils.class);
        when(paServiceUtil.createEntity(any(OrganizationDTO.class))).thenAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                if (args[0] instanceof OrganizationDTO) {
                    OrganizationDTO org = (OrganizationDTO) args[0];
                    org.setIdentifier(IiConverter.convertToIi(1L));
                    return org;
                }
                PersonDTO person = (PersonDTO) args[0];
                person.setIdentifier(IiConverter.convertToIi(1L));
                return person;
            }
        });
        bean.setPaServiceUtils(paServiceUtil);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1L);
        bean.loadAbstractionElementFromPDQXml(testDoubleBlindXMLUrl, studyProtocolIi);
        verify(studyMilestoneSvc, org.mockito.Mockito.times(5)).create(any(StudyMilestoneDTO.class));
        assertNotNull(bean.getOrgCorrelationService());
        assertNotNull(bean.getPaServiceUtils());
    }

    @Test
    public void testLoadNoLocationsPDQXml() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock(PAServiceUtils.class);
        when(paServiceUtil.createEntity(any(OrganizationDTO.class))).thenAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                if (args[0] instanceof OrganizationDTO) {
                    OrganizationDTO org = (OrganizationDTO) args[0];
                    org.setIdentifier(IiConverter.convertToIi(1L));
                    return org;
                }
                PersonDTO person = (PersonDTO) args[0];
                person.setIdentifier(IiConverter.convertToIi(1L));
                return person;
            }
        });
        bean.setPaServiceUtils(paServiceUtil);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1L);
        bean.loadAbstractionElementFromPDQXml(testNoLocationsXMLUrl, studyProtocolIi);
        verify(studyMilestoneSvc, org.mockito.Mockito.times(5)).create(any(StudyMilestoneDTO.class));
        verify(participatingSiteSvc, org.mockito.Mockito.times(1)).createStudySiteParticipant(any(StudySiteDTO.class),
                any(StudySiteAccrualStatusDTO.class), any(OrganizationDTO.class), any(HealthCareFacilityDTO.class));
        assertNotNull(bean.getOrgCorrelationService());
        assertNotNull(bean.getPaServiceUtils());
    }

    @Test
    public void testLoadAbstractionElementPDQXmlSearch() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        bean.setPaServiceUtils(paServiceUtil);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1l);
        when(diseaseSvc.search(any(PDQDiseaseDTO.class))).thenReturn(Arrays.asList(new PDQDiseaseDTO()));
        when(paServiceUtil.findEntity(any(OrganizationDTO.class))).thenReturn(new OrganizationDTO());
        StudyDiseaseServiceLocal studyDiseaseSvc = mock (StudyDiseaseServiceLocal.class);
        when(paSvcLoc.getStudyDiseaseService()).thenReturn(studyDiseaseSvc);
        when(interventionSvc.search(any(InterventionDTO.class))).thenReturn(Arrays.asList(new InterventionDTO()));
        when(plannedActivitySvc.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(new PlannedActivityDTO()));
        bean.loadAbstractionElementFromPDQXml(testXMLUrl, studyProtocolIi);
        verify(diseaseSvc, org.mockito.Mockito.atLeastOnce()).search(any(PDQDiseaseDTO.class));
        verify(studyDiseaseSvc, org.mockito.Mockito.atLeastOnce()).create(any(StudyDiseaseDTO.class));

        verify(plannedActivitySvc, org.mockito.Mockito.atLeastOnce()).createPlannedEligibilityCriterion(
                any(PlannedEligibilityCriterionDTO .class));
        verify(studySiteSvc, org.mockito.Mockito.atLeastOnce()).create(any(StudySiteDTO.class));
    }

    @Test
    public void testLoadAbstractionElementPDQXmlException() throws PAException, IOException {
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        bean.setPaServiceUtils(paServiceUtil);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1l);
        when(diseaseSvc.search(any(PDQDiseaseDTO.class))).thenReturn(Arrays.asList(new PDQDiseaseDTO()));
        when(paServiceUtil.findEntity(any(OrganizationDTO.class))).thenReturn(new OrganizationDTO());
        StudyDiseaseServiceLocal studyDiseaseSvc = mock (StudyDiseaseServiceLocal.class);
        when(paSvcLoc.getStudyDiseaseService()).thenReturn(studyDiseaseSvc);
        when(interventionSvc.search(any(InterventionDTO.class))).thenReturn(Arrays.asList(new InterventionDTO()));
        when(plannedActivitySvc.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(new PlannedActivityDTO()));
        when(studyProtocolSvc.updateInterventionalStudyProtocol(any(InterventionalStudyProtocolDTO.class), 
        		any(String.class))).thenThrow(new PAException());
        when(studyDiseaseSvc.create(any(StudyDiseaseDTO.class))).thenThrow(new PAException());
        when(plannedActivitySvc.create(any(PlannedActivityDTO.class))).thenThrow(new PAException());
        when(armSvc.create(any(ArmDTO.class))).thenThrow(new PAException());
        when(studyOutcomeMeseasureSvc.create(any(StudyOutcomeMeasureDTO.class))).thenThrow(new PAException());
        when(plannedActivitySvc.createPlannedEligibilityCriterion(any(PlannedEligibilityCriterionDTO .class)))
            .thenThrow(new PAException());
        when(studySiteSvc.create(any(StudySiteDTO.class))).thenThrow(new PAException());
        bean.loadAbstractionElementFromPDQXml(testXMLUrl, studyProtocolIi);
        verify(plannedActivitySvc, org.mockito.Mockito.atLeastOnce()).createPlannedSubstanceAdministration(
                any(PlannedSubstanceAdministrationDTO .class));
        verify(plannedActivitySvc, org.mockito.Mockito.atLeastOnce()).createPlannedProcedure(
                any(PlannedProcedureDTO.class));
    }
}
