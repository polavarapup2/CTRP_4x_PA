package gov.nih.nci.accrual.util;

import static org.mockito.Mockito.mock;
import gov.nih.nci.accrual.service.MockPoOrganizationEntityService;
import gov.nih.nci.accrual.service.MockPoPersonEntityService;
import gov.nih.nci.accrual.service.util.MockPatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

public class MockPoServiceLocator implements PoServiceLocator {
    private final PersonEntityServiceRemote personEntityService = new MockPoPersonEntityService();
    private final  PatientCorrelationServiceRemote patientService = new MockPatientCorrelationServiceRemote();
    private final OrganizationEntityServiceRemote organizationEntityService = new MockPoOrganizationEntityService();
    private final IdentifiedOrganizationCorrelationServiceRemote identifiedOrgCorrelationSvc
        = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
    private final HealthCareFacilityCorrelationServiceRemote healthCareCorrelationSvc =
        mock(HealthCareFacilityCorrelationServiceRemote.class);
    
    private final IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote = 
            mock(IdentifiedOrganizationCorrelationServiceRemote.class);
    
    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        return personEntityService;
    }
    
    /**
     * {@inheritDoc}
     */
    public PatientCorrelationServiceRemote getPatientCorrelationService() {
        return patientService;
    }
    
    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
    	return organizationEntityService;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService() {
        return identifiedOrgCorrelationSvc;
    }
    
    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return healthCareCorrelationSvc;
    }

    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {        
        return identifiedOrganizationCorrelationServiceRemote;
    }
}
