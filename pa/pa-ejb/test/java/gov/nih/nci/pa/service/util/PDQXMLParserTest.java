/**
 *
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vrushali
 *
 */
public class PDQXMLParserTest {
    private final URL testXMLUrl = this.getClass().getResource("/sample-with-location.xml");

    private PDQAbstractionXMLParser abstractionElementParser;
    private PoServiceLocator poSvcLoc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;
    private IdentifiedOrganizationCorrelationServiceRemote identifierOrgSvc;
    private ServiceLocator paSvcLoc;
    private LookUpTableServiceRemote lookupSvc;
    private HealthCareFacilityCorrelationServiceRemote hcfSvc;

    @Before
    public void setup() throws PAException, NullifiedEntityException, TooManyResultsException {
        abstractionElementParser = new PDQAbstractionXMLParser();
        setupPoSvc();
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        lookupSvc = mock (LookUpTableServiceRemote.class);
        when(paSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        Country country = new Country();
        country.setAlpha3("USA");
        when(lookupSvc.getCountryByName(anyString())).thenReturn(country);
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        abstractionElementParser.setPaServiceUtils(paServiceUtil);
        when(paServiceUtil.getOrganizationByCtepId(anyString())).thenReturn(new OrganizationDTO());
    }
    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        identifierOrgSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrgSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);
        List<IdentifiedOrganizationDTO> orgList = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO idOrgDTO = new IdentifiedOrganizationDTO();
        idOrgDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        when(identifierOrgSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(orgList);
        hcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(hcfSvc);
        List<HealthCareFacilityDTO> hcfDtos = new ArrayList<HealthCareFacilityDTO>();
        HealthCareFacilityDTO hcfDto = new HealthCareFacilityDTO();
        DSet<Ii> dset = new DSet<Ii>();
        dset.setItem(new HashSet<Ii>());
        dset.getItem().add(IiConverter.convertToPoHealthCareFacilityIi("2"));
        hcfDto.setIdentifier(dset);
        hcfDtos.add(hcfDto);
        when(hcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(hcfDtos);
    }

    @Test(expected=IllegalStateException.class)
    public void testIForgotToCallSetURL() throws PAException {
        abstractionElementParser.parse();
        abstractionElementParser.getIspDTO();
    }

    @Test
    public void testReadStudyDesign() throws PAException {
        setURLAndParse();
        assertEquals(3, abstractionElementParser.getIspDTO().getNumberOfInterventionGroups().getValue().intValue());
        assertEquals("Randomized", abstractionElementParser.getIspDTO().getAllocationCode().getCode());
        assertEquals("3", IntConverter.convertToString(
                abstractionElementParser.getIspDTO().getNumberOfInterventionGroups()));
        assertTrue(abstractionElementParser.getIspDTO().getPublicTitle().getValue().startsWith(
                "Bevacizumab With or Without Interferon "));
        assertTrue(abstractionElementParser.getIspDTO().getPublicDescription().getValue().startsWith(
                "RATIONALE: Monoclonal antibodies,"));
        assertTrue(abstractionElementParser.getIspDTO().getScientificDescription().getValue().startsWith("OBJECTIVES:"));

        assertTrue(StringUtils.isEmpty(abstractionElementParser.getIspDTO().getKeywordText().getValue()));
        assertEquals("10/05/2007", TsConverter.convertToString(
                abstractionElementParser.getIspDTO().getRecordVerificationDate()));
        assertEquals(65, abstractionElementParser.getIspDTO().getTargetAccrualNumber().getLow().getValue().intValue());


    }
    @Test
    public void testReadOutcomes() throws PAException {
        setURLAndParse();
        assertEquals(2, abstractionElementParser.getOutcomeMeasureDTOs().size());
        List <StudyOutcomeMeasureDTO> outList = abstractionElementParser.getOutcomeMeasureDTOs();
        assertTrue(outList.get(0).getPrimaryIndicator().getValue());
    }


    @Test
    public void testReadIntervention() throws PAException {
        setURLAndParse();
        assertEquals(2, abstractionElementParser.getListOfInterventionsDTOS().size());
        Map<InterventionDTO, List<ArmDTO>> map = abstractionElementParser.getArmInterventionMap();
        for (Iterator<InterventionDTO> iter = map.keySet().iterator(); iter.hasNext();) {
            InterventionDTO interventionDTO = iter.next();
            assertEquals("Biological/Vaccine", interventionDTO.getTypeCode().getCode());
            List<ArmDTO> armList = map.get(interventionDTO);
            for (ArmDTO armDTO: armList){
                assertEquals("Arm I",armDTO.getName().getValue());
                break;
            }

            break;
      }
    }
    @Test
    public void testReadCondition() throws PAException {
        setURLAndParse();
        assertEquals(2,abstractionElementParser.getListOfDiseaseDTOs().size());
        assertEquals(abstractionElementParser.getListOfDiseaseDTOs().get(0).getDiseaseCode().getValue(),"CDR0000038837");
    }

    @Test
    public void testArmGroup() throws PAException {
        setURLAndParse();
        assertEquals(3,abstractionElementParser.getListOfArmDTOS().size());
        assertEquals(abstractionElementParser.getListOfArmDTOS().get(0).getName().getValue(),"Arm I");
        assertEquals(abstractionElementParser.getListOfArmDTOS().get(0).getTypeCode().getCode(),"Experimental");
    }
    @Test
    public void testReadLocations() throws PAException {
        setURLAndParse();
        Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO,Map<PoDto, String>>> location
            = abstractionElementParser.getLocationsMap();
        Set<String> locOrgNames = new HashSet<String>();
        locOrgNames.add("London Regional Cancer Program at London Health Sciences Centre");
        locOrgNames.add("Adena Regional Medical Center");
        locOrgNames.add("Adventist Medical Center");

        Set<String> orgNames = new HashSet<String>();
        for (OrganizationDTO locOrg : location.keySet()) {
            orgNames.add(EnOnConverter.convertEnOnToString(locOrg.getName()));
            for (StudySiteAccrualStatusDTO recrutingStatus : location.get(locOrg).keySet()) {
                assertEquals(RecruitmentStatusCode.ACTIVE.getCode(), recrutingStatus.getStatusCode().getCode());
            }
        }
        assertTrue(CollectionUtils.isEqualCollection(locOrgNames, orgNames));
    }
    @Test
    public void testReadIrbInfo() throws PAException {
        setURLAndParse();
        assertNotNull(abstractionElementParser.getIrbOrgDTO());
    }
    @Test
    public void testReadEligibility() throws PAException {
        setURLAndParse();
        PlannedEligibilityCriterionDTO  eligibleCriterionDTO = abstractionElementParser.getEligibilityList().get(0);
        assertNotNull(eligibleCriterionDTO);
        assertEquals(EligibleGenderCode.BOTH.getCode(), eligibleCriterionDTO.getEligibleGenderCode().getCode());
        eligibleCriterionDTO = abstractionElementParser.getEligibilityList().get(1);
        assertEquals(18,IvlConverter.convertPq().convertLow(eligibleCriterionDTO.getValue()).getValue().intValue());
        assertEquals(120,IvlConverter.convertPq().convertHigh(eligibleCriterionDTO.getValue()).getValue().intValue());
        assertEquals("no", abstractionElementParser.getHealthyVolunteers());
    }
    @Test
    public void testReadCollaborators() throws PAException {
        setURLAndParse();
        assertNotNull(abstractionElementParser.getCollaboratorOrgDTOs());
        OrganizationDTO orgDTO = abstractionElementParser.getCollaboratorOrgDTOs().get(0);
        assertEquals("Eastern Cooperative Oncology Group", EnOnConverter.convertEnOnToString(orgDTO.getName()));
    }
    /**
     *
     */
    private void setURLAndParse() throws PAException {
        abstractionElementParser.setUrl(testXMLUrl);
        abstractionElementParser.parse();
    }
    @Test
    public void testProperties() {
        abstractionElementParser.setHealthyVolunteers("healthyVolunteers");
        assertNotNull(abstractionElementParser.getHealthyVolunteers());

        abstractionElementParser.setIrbOrgDTO(new OrganizationDTO());
        assertNotNull(abstractionElementParser.getIrbOrgDTO());

        abstractionElementParser.setEligibilityList(new ArrayList<PlannedEligibilityCriterionDTO>());
        assertNotNull(abstractionElementParser.getEligibilityList());
    }

}
