/**
 *
 */
package gov.nih.nci.pa.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.MockPoClinicalResearchStaffCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareFacilityCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareProviderCorrelationService;
import gov.nih.nci.pa.service.MockPoIdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.service.MockPoOrganizationalContactCorrelationService;
import gov.nih.nci.pa.service.MockPoOversightCommitteeCorrelationService;
import gov.nih.nci.pa.service.MockPoPersonEntityService;
import gov.nih.nci.pa.service.MockPoResearchOrganizationCorrelationService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockPoServiceLocator implements PoServiceLocator {

    private final OrganizationEntityServiceRemote orgEntityServiceRemote = new MockPoOrganizationEntityService();
    private final HealthCareFacilityCorrelationServiceRemote hcfService = new MockPoHealthCareFacilityCorrelationService();
    private final ResearchOrganizationCorrelationServiceRemote roService = new MockPoResearchOrganizationCorrelationService();
    private final OversightCommitteeCorrelationServiceRemote ocService = new MockPoOversightCommitteeCorrelationService();
    private final PersonEntityServiceRemote personEntityService = new MockPoPersonEntityService();
    private final ClinicalResearchStaffCorrelationServiceRemote crsService = new MockPoClinicalResearchStaffCorrelationService();
    private final HealthCareProviderCorrelationServiceRemote hcpService = new MockPoHealthCareProviderCorrelationService();
    private final OrganizationalContactCorrelationServiceRemote orgContact = new MockPoOrganizationalContactCorrelationService();

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
       return orgEntityServiceRemote;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return hcfService;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        return roService;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        return ocService;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        return crsService;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        return hcpService;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        return orgContact;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        return personEntityService;
    }

    /**
     * {@inheritDoc}
     * @throws NullifiedRoleException 
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService()  {
        IdentifiedOrganizationCorrelationServiceRemote svc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        List<IdentifiedOrganizationDTO> results = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO();
        dto.setPlayerIdentifier(IiConverter.convertToIi(1L));
        Ii id = new Ii();
        id.setExtension("4648");
        id.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        id.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
        dto.setAssignedId(id);
        results.add(dto);
        when(svc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(results);
        try {
            when(svc.getCorrelationsByPlayerIds(any(Ii[].class))).thenReturn(results);
        } catch (NullifiedRoleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        return new MockPoIdentifiedPersonCorrelationServiceRemote();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public FamilyServiceRemote getFamilyService() {
        FamilyServiceRemote svc = mock(FamilyServiceRemote.class);
        Map<Ii, FamilyDTO> results = new HashMap<Ii, FamilyDTO>();
        FamilyDTO dto = new FamilyDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(EnOnConverter.convertToEnOn("value"));
        results.put(IiConverter.convertToPoFamilyIi("1"), dto);
        when(svc.getFamilies(any(Set.class))).thenReturn(results);
        
        List<FamilyOrganizationRelationshipDTO> results2 = new ArrayList<>();
        FamilyOrganizationRelationshipDTO dto2 = new FamilyOrganizationRelationshipDTO();
        dto2.setIdentifier(IiConverter.convertToIi(1L));
        dto2.setOrgIdentifier(IiConverter.convertToIi(1L));
        results2.add(dto2);
        when(svc.getActiveRelationships(any(Long.class))).thenReturn(results2 );  
        return svc;
    }
}
