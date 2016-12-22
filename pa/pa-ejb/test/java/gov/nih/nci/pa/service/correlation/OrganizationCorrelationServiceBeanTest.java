/**
 *
 */
package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


/**
 * @author vrushali
 *
 */
public class OrganizationCorrelationServiceBeanTest extends AbstractHibernateTestCase {
    private final OrganizationCorrelationServiceBean bean = new OrganizationCorrelationServiceBean();;
    private CorrelationUtils corrUtils;
    private final HealthCareFacility hcfBO = new HealthCareFacility();
    private HealthCareFacilityCorrelationServiceRemote poHcfSvc;
    private ResearchOrganizationCorrelationServiceRemote poRoSvc;
    private PoServiceLocator poSvcLoc;
    private OrganizationEntityServiceRemote poOrgSvc;
    private OversightCommitteeCorrelationServiceRemote poOcSvc;

    @Before
    public void setUp() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        OrganizationCorrelationServiceBean.resetCache();
    }

    @Test
    public void testCreateHealthCareFacilityCorrelations() throws PAException, NullifiedRoleException,
            NullifiedEntityException, EntityValidationException, CurationException {
        try {
            bean.createHealthCareFacilityCorrelations(null);
            fail();
        } catch (PAException e) {
            assertEquals(" Organization PO Identifier is null", e.getMessage());
        }
        try {
            bean.createHealthCareFacilityCorrelations("2");
            fail("Nullified org");
        } catch (PAException e) {
            assertEquals("This Organization is no longer available (id = 2) , instead use OrgName (id = 22)",
                         e.getMessage());
        }
        setUpCorrSvcMock();
        bean.createHealthCareFacilityCorrelations("1");
        setupCorSvcMockForHCF();
        bean.createHealthCareFacilityCorrelations("1");
        try {
            bean.createHealthCareFacilityCorrelations("12");
            fail("No org Found");
        } catch (PAException e) {
            assertEquals("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = 12.", e.getMessage());
        }
        setupPoSvcLocMock();
        setupCorSvcMockForHCF();
        bean.createHealthCareFacilityCorrelations("1");

        List<HealthCareFacilityDTO> hcfDTOList = new ArrayList<HealthCareFacilityDTO>();
        hcfDTOList.add(new HealthCareFacilityDTO());
        hcfDTOList.add(new HealthCareFacilityDTO());
        when(poHcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(hcfDTOList);
        bean.createHealthCareFacilityCorrelations("1");
        when(poHcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(null);
        NullifiedRoleException nRE = new NullifiedRoleException(IiConverter.convertToPoClinicalResearchStaffIi("1"));
        when(poHcfSvc.getCorrelation(any(Ii.class))).thenThrow(nRE);
        try {
            bean.createHealthCareFacilityCorrelations("1");
            fail("NullifiedRoleException exception during get ClinicalResearchStaff");
        } catch (PAException e) {
            assertEquals("The Clinical Research Staff is no longer available (id = 1)", e.getMessage());
        }
        setupPoSvcLocMock();
        setupCorSvcMockForHCF();
        Map<String, String[]> errors = new HashMap<String, String[]>();
        EntityValidationException eVE = new EntityValidationException(errors);
        when(poHcfSvc.createCorrelation(any(HealthCareFacilityDTO.class))).thenThrow(eVE);
        try {
            bean.createHealthCareFacilityCorrelations("1");
            fail("Validation exception during create ClinicalResearchStaff ");
        } catch (PAException e) {
            assertEquals("Validation exception during create ClinicalResearchStaff", e.getMessage());
        }
        setupPoSvcLocMock();
        setupCorSvcMockForHCF();
        CurationException cE = new CurationException();
        when(poHcfSvc.createCorrelation(any(HealthCareFacilityDTO.class))).thenThrow(cE);
        try {
            bean.createHealthCareFacilityCorrelations("1");
            fail("CurationException during create ClinicalResearchStaff");
        } catch (PAException e) {
            assertEquals("CurationException during create ClinicalResearchStaff", e.getMessage());
        }
        setupPoSvcLocMock();
        setupCorSvcMockForHCF();
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(new Organization());
        bean.createHealthCareFacilityCorrelations("1");
    }

    @Test
    public void testCreateResearchOrganizationCorrelations() throws PAException, NullifiedEntityException,
            NullifiedRoleException, EntityValidationException, CurationException {
        try {
            bean.createResearchOrganizationCorrelations(null);
            fail();
        } catch (PAException e) {
            assertEquals(" Organization PO Identifier is null", e.getMessage());
        }
        try {
            bean.createResearchOrganizationCorrelations("2");
            fail("Nullified org");
        } catch (PAException e) {
            assertEquals("This Organization is no longer available (id = 2) , instead use OrgName (id = 22)",
                         e.getMessage());
        }
        try {
            bean.createResearchOrganizationCorrelations("12");
            fail("No org Found");
        } catch (PAException e) {
            assertEquals("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = 12.", e.getMessage());
        }
        setUpCorrSvcMock();
        bean.createResearchOrganizationCorrelations("1");
        ResearchOrganization roBO = new ResearchOrganization();
        roBO.setIdentifier("4");
        roBO.setId(1L);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(roBO);
        assertNotNull(bean.createResearchOrganizationCorrelations("1"));
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(new Organization());
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(roBO);
        assertNotNull(bean.createResearchOrganizationCorrelations("1"));
        setupPoSvcLocMock();
        List<ResearchOrganizationDTO> roDTOList = new ArrayList<ResearchOrganizationDTO>();
        roDTOList.add(new ResearchOrganizationDTO());
        roDTOList.add(new ResearchOrganizationDTO());
        when(poRoSvc.search(any(ResearchOrganizationDTO.class))).thenReturn(roDTOList);
        bean.createResearchOrganizationCorrelations("1");
        when(poRoSvc.search(any(ResearchOrganizationDTO.class))).thenReturn(null);
        NullifiedRoleException nRE = new NullifiedRoleException(IiConverter.convertToPoClinicalResearchStaffIi("1"));
        when(poRoSvc.getCorrelation(any(Ii.class))).thenThrow(nRE);
        try {
            bean.createResearchOrganizationCorrelations("1");
            fail("NullifiedRoleException exception during research org");
        } catch (PAException e) {
            assertEquals("The Clinical Research Staff is no longer available (id = 1)", e.getMessage());
        }
        setupPoSvcLocMock();
        Map<String, String[]> errors = new HashMap<String, String[]>();
        EntityValidationException eVE = new EntityValidationException(errors);
        when(poRoSvc.createCorrelation(any(ResearchOrganizationDTO.class))).thenThrow(eVE);
        try {
            bean.createResearchOrganizationCorrelations("1");
            fail("Validation exception during create Research Org");
        } catch (PAException e) {
            assertEquals("Validation exception during create ClinicalResearchStaff", e.getMessage());
        }
        setupPoSvcLocMock();
        CurationException cE = new CurationException();
        when(poRoSvc.createCorrelation(any(ResearchOrganizationDTO.class))).thenThrow(cE);
        try {
            bean.createResearchOrganizationCorrelations("1");
            fail("CurationException during create Research org ");
        } catch (PAException e) {
            assertEquals("Curation exception during create ClinicalResearchStaff", e.getMessage());
        }
    }

    @Test
    public void testGetPoResearchOrganizationByEntityIdentifier() throws NullifiedEntityException,
            NullifiedRoleException, PAException {
        CacheUtils.getPoResearchOrganizationByEntityIdentifierCache().removeAll();
        try {
            bean.getPoResearchOrganizationByEntityIdentifier(null);
            fail("Ii Cannot be null");
        } catch (PAException e) {
            assertEquals("Ii Cannot be null", e.getMessage());
        }
        setupPoSvcLocMock();
        when(poRoSvc.search(any(ResearchOrganizationDTO.class))).thenReturn(null);
        Ii orgPoIdentifier = new Ii();
        orgPoIdentifier.setExtension("1");
        assertNull(bean.getPoResearchOrganizationByEntityIdentifier(orgPoIdentifier));
        List<ResearchOrganizationDTO> roList = new ArrayList<ResearchOrganizationDTO>();
        ResearchOrganizationDTO rOrg = new ResearchOrganizationDTO();
        Ii roIdentifier = new Ii();
        roIdentifier.setExtension("1");
        roIdentifier.setRoot("2.16.840.1.113883.3.26.4");
        roIdentifier.setReliability(IdentifierReliability.ISS);
        rOrg.setIdentifier(DSetConverter.convertIiToDset(roIdentifier));
        roList.add(rOrg);
        when(poRoSvc.search(any(ResearchOrganizationDTO.class))).thenReturn(roList);
        assertNotNull(bean.getPoResearchOrganizationByEntityIdentifier(orgPoIdentifier));
    }

    @Test
    public void testGetPOOrgIdentifierByIdentifierType() throws Exception {
        try {
            bean.getPOOrgIdentifierByIdentifierType("");
            fail("Org name is null");
        } catch (PAException e) {
            assertEquals("Org name is null", e.getMessage());
        }
        try {
            bean.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
            fail("No org found");
        } catch (PAException e) {
            assertEquals("No org found", e.getMessage());
        }
        setupPoSvcLocMock();
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(null);
        try {
            bean.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
            fail("No org found");
        } catch (PAException e) {
            assertEquals("No org found", e.getMessage());
        }
        List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
        OrganizationDTO orgDTO = new OrganizationDTO();
        Ii orgPoIdentifier = new Ii();
        orgPoIdentifier.setExtension("1");
        orgDTO.setIdentifier(orgPoIdentifier);
        orgDTO.setName(EnOnConverter.convertToEnOn(PAConstants.CTEP_ORG_NAME));
        orgList.add(orgDTO);
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgList);
        assertNotNull(bean.getPOOrgIdentifierByIdentifierType(PAConstants.CTEP_IDENTIFIER_TYPE));
        // second time through uses cache
        assertNotNull(bean.getPOOrgIdentifierByIdentifierType(PAConstants.CTEP_IDENTIFIER_TYPE));
    }

    @Test
    public void testGetPOOrgIdentifierByIdentifierTypeDuplicate() throws Exception {
        List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
        OrganizationDTO orgDTO = new OrganizationDTO();
        Ii orgPoIdentifier = new Ii();
        orgPoIdentifier.setExtension("1");
        orgDTO.setIdentifier(orgPoIdentifier);
        orgDTO.setName(EnOnConverter.convertToEnOn(PAConstants.DCP_ORG_NAME));
        orgList.add(orgDTO);
        orgList.add(orgDTO);
        setupPoSvcLocMock();
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgList);
        try {
            bean.getPOOrgIdentifierByIdentifierType(PAConstants.DCP_IDENTIFIER_TYPE);
            fail("more than 1 record");
        } catch (PAException e) {
            assertEquals("There cannot be more than 1 record for " + PAConstants.DCP_ORG_NAME, e.getMessage());
        }
    }

    @Test
    public void testGetPOOrgIdentifierByIdentifierTypeSimilar() throws Exception {
        List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
        OrganizationDTO orgDTO = new OrganizationDTO();
        Ii orgPoIdentifier = new Ii();
        orgPoIdentifier.setExtension("1");
        orgDTO.setIdentifier(orgPoIdentifier);
        orgDTO.setName(EnOnConverter.convertToEnOn(PAConstants.DCP_ORG_NAME));
        orgList.add(orgDTO);
        OrganizationDTO orgDtoBad = new OrganizationDTO();
        orgDtoBad.setIdentifier(orgPoIdentifier);
        orgDtoBad.setName(EnOnConverter.convertToEnOn("Oregon " + PAConstants.DCP_ORG_NAME));
        orgList.add(orgDtoBad);
        setupPoSvcLocMock();
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgList);
        assertNotNull(bean.getPOOrgIdentifierByIdentifierType(PAConstants.DCP_IDENTIFIER_TYPE));
    }

    @Test
    public void testGetPOOrgIdentifierByIdentifierTypeTooManyResults() throws Exception {
        TooManyResultsException tmException = new TooManyResultsException(0);
        setupPoSvcLocMock();
        when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(tmException);
        try {
            bean.getPOOrgIdentifierByIdentifierType(PAConstants.DCP_IDENTIFIER_TYPE);
            fail("TooManyResultsException ");
        } catch (PAException e) {
            assertEquals("gov.nih.nci.coppa.services.TooManyResultsException", e.getMessage());
        }
    }

    @Test
    public void testCreatePAOrganizationUsingPO() throws PAException {
        setUpCorrSvcMock();
        Organization org = bean.createPAOrganizationUsingPO(new OrganizationDTO());
        assertNull(org);
    }

    @Test
    public void testGetOrganizationByFunctionRole() throws PAException {
        ServiceLocator paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        StudySiteServiceLocal studySiteSvc = mock(StudySiteServiceLocal.class);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteSvc);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(null);
        assertNull(bean.getOrganizationByFunctionRole(null, null));
        List<StudySiteDTO> siteList = new ArrayList<StudySiteDTO>();
        StudySiteDTO ssDTO = new StudySiteDTO();
        ssDTO.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
        siteList.add(ssDTO);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(siteList);
        setUpCorrSvcMock();
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(null);
        assertNull(bean.getOrganizationByFunctionRole(new Ii(), new Cd()));
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setResearchOrganizationIi(new Ii());
        siteList.add(siteDTO);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(siteList);
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(new Organization());
        assertNotNull(bean.getOrganizationByFunctionRole(new Ii(), new Cd()));
    }

    @Test
    public void testCreateOversightCommitteeCorrelations() throws PAException, NullifiedEntityException,
            NullifiedRoleException, EntityValidationException, CurationException {
        try {
            bean.createOversightCommitteeCorrelations(null);
            fail();
        } catch (PAException e) {
            assertEquals("Organization PO Identifier is null", e.getMessage());
        }
        try {
            bean.createOversightCommitteeCorrelations("2");
            fail("Nullified org");
        } catch (PAException e) {
            assertEquals("This Organization is no longer available (id = 2) , instead use OrgName (id = 22)",
                         e.getMessage());
        }
        try {
            bean.createOversightCommitteeCorrelations("12");
            fail("No org Found");
        } catch (PAException e) {
            assertEquals("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = 12.", e.getMessage());
        }
        setUpCorrSvcMock();
        try {
            bean.createOversightCommitteeCorrelations("1");
            fail();
        } catch (PAException e) {
            assertEquals("Error thrown during get/create PO OversightCommitte w/type code = "
                    + "Institutional Review Board (IRB).", e.getMessage());
        }
        OversightCommittee ocBO = new OversightCommittee();
        ocBO.setIdentifier("4");
        ocBO.setId(1L);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(ocBO);
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(null);
        setupPoSvcLocMock();
        List<OversightCommitteeDTO> ocDTOList = new ArrayList<OversightCommitteeDTO>();
        OversightCommitteeDTO ocDTO = new OversightCommitteeDTO();
        Ii ocIi = IiConverter.convertToIi("1");
        ocIi.setReliability(IdentifierReliability.ISS);
        ocIi.setRoot("2.16.840.1.113883.3.26.4.4.4");
        ocDTO.setIdentifier(DSetConverter.convertIiToDset(ocIi));
        ocDTOList.add(ocDTO);
        ocDTOList.add(new OversightCommitteeDTO());
        when(poOcSvc.search(any(OversightCommitteeDTO.class))).thenReturn(ocDTOList);
        assertNotNull(bean.createOversightCommitteeCorrelations("1"));
        when(corrUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(new Organization());
        ocDTOList.add(new OversightCommitteeDTO());
        when(poOcSvc.search(any(OversightCommitteeDTO.class))).thenReturn(ocDTOList);
        assertNotNull(bean.createOversightCommitteeCorrelations("1"));
        when(poOcSvc.search(any(OversightCommitteeDTO.class))).thenReturn(null);
        when(poOcSvc.getCorrelation(any(Ii.class))).thenReturn(ocDTO);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(null);
        assertNull(bean.createOversightCommitteeCorrelations("1"));
        NullifiedRoleException nRE = new NullifiedRoleException(IiConverter.convertToPoOversightCommitteeIi("1"));
        when(poOcSvc.getCorrelation(any(Ii.class))).thenThrow(nRE);
        try {
            bean.createOversightCommitteeCorrelations("1");
            fail("NullifiedRoleException exception during oversight committee");
        } catch (PAException e) {
            assertEquals("The Oversight Committee is no longer available (id = 1)", e.getMessage());
        }
        setupPoSvcLocMock();
        Map<String, String[]> errors = new HashMap<String, String[]>();
        EntityValidationException eVE = new EntityValidationException(errors);
        when(poOcSvc.createCorrelation(any(OversightCommitteeDTO.class))).thenThrow(eVE);
        try {
            bean.createOversightCommitteeCorrelations("1");
            fail("Validation exception during create Oversight Committee");
        } catch (PAException e) {
            assertEquals("Validation exception during create PO OversightCommittee.", e.getMessage());
        }
        setupPoSvcLocMock();
        CurationException cE = new CurationException();
        when(poOcSvc.createCorrelation(any(OversightCommitteeDTO.class))).thenThrow(cE);
        try {
            bean.createOversightCommitteeCorrelations("1");
            fail("CurationException during create Oversight Committee");
        } catch (PAException e) {
            assertEquals("Error thrown during get/create PO OversightCommitte w/type code = Institutional Review Board (IRB).",
                         e.getMessage());
        }
    }

    @Test
    public void testIsAffiliatedWithTrial() throws Exception {
        assertFalse(bean.isAffiliatedWithTrial(1L, null, StudySiteFunctionalCode.LEAD_ORGANIZATION));
        assertFalse(bean.isAffiliatedWithTrial(null, 1L, StudySiteFunctionalCode.LEAD_ORGANIZATION));
    }

    private void setUpCorrSvcMock() {
        corrUtils = mock(CorrelationUtils.class);
        bean.setCorrUtils(corrUtils);
    }

    private void setupCorSvcMockForHCF() throws PAException, NullifiedRoleException {
        hcfBO.setIdentifier("5");
        corrUtils = mock(CorrelationUtils.class);
        when(corrUtils.getStructuralRoleByIi(any(Ii.class))).thenReturn(hcfBO);
        bean.setCorrUtils(corrUtils);
    }

    private void setupPoSvcLocMock() throws PAException, NullifiedEntityException, NullifiedRoleException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poHcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        poRoSvc = mock(ResearchOrganizationCorrelationServiceRemote.class);
        poOcSvc = mock(OversightCommitteeCorrelationServiceRemote.class);
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(poHcfSvc);
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(poRoSvc);
        when(poSvcLoc.getOversightCommitteeCorrelationService()).thenReturn(poOcSvc);
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn("Mock org name"));
        when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(orgDto);
        when(poHcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(null);
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        when(poHcfSvc.getCorrelation(any(Ii.class))).thenReturn(hcfDTO);
        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        roDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToIi("1")));
        when(poRoSvc.getCorrelation(any(Ii.class))).thenReturn(roDTO);
    }
    
    @Test
    public void testGetOrganizationByStudySite() throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        List<ResearchOrganization> rsorgList = new ArrayList<ResearchOrganization>();
        Organization org = TestSchema.createOrganizationObj();
        session.saveOrUpdate(org);
        
        ResearchOrganization rsorg = new ResearchOrganization();
        rsorg.setOrganization(org);
        rsorg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        rsorg.setIdentifier("abc");
        session.saveOrUpdate(rsorg);
        
        HealthCareFacility hfc = TestSchema.createHealthCareFacilityObj(org);
        session.saveOrUpdate(hfc);

        StudyProtocol studyProtocol = TestSchema.createStudyProtocolObj();
        session.saveOrUpdate(studyProtocol);
        
        
        StudySite sPart = new StudySite();
        sPart.setFunctionalCode(StudySiteFunctionalCode.FUNDING_SOURCE);
        sPart.setHealthCareFacility(hfc);
        sPart.setLocalStudyProtocolIdentifier("Local SP ID 01");
        sPart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sPart.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("6/1/2008"));
        sPart.setAccrualDateRangeLow(ISOUtil
                .dateStringToTimestamp("03/11/2012"));
        sPart.setStudyProtocol(studyProtocol);
        sPart.setResearchOrganization(rsorg);
        session.saveOrUpdate(sPart);
        List<StudySite> ssList = new ArrayList<StudySite>();
        rsorg.setStudySites(ssList);
        rsorgList.add(rsorg);
        org.setResearchOrganizations(rsorgList);
        ssList.add(sPart);
        List<Organization> orgs = bean.getOrganizationByStudySite(1L, StudySiteFunctionalCode.COLLABORATORS);
        assertTrue(orgs.size() >= 1); 
        List<String> orgValues = new ArrayList<String>();
        for (Organization orgValue : orgs) {
           orgValues.add(orgValue.getName());
        }
        Set<String> duplicatesOrgs = new LinkedHashSet<String>();
        Set<String> uniquesorgs = new HashSet<String>();
        for(String t : orgValues) {
            if(!uniquesorgs.add(t)) {
                duplicatesOrgs.add(t);
            }
        }
        assertTrue(duplicatesOrgs.size() == 0);
        List<ResearchOrganization> rorgList = orgs.get(0).getResearchOrganizations();
        assertTrue(rorgList.size() == 1);
        List<StudySite> ssLists = orgs.get(0).getResearchOrganizations().get(0).getStudySites();
        assertTrue(ssLists.size() == 1);
    }
}
