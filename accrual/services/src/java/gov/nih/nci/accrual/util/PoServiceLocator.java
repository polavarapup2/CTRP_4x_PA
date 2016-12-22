package gov.nih.nci.accrual.util;

import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */

public interface PoServiceLocator {   
   
    /**
     * @return PersonEntityServiceRemote
     */
    PersonEntityServiceRemote getPersonEntityService();
    
    /**
     * @return PatientCorrelationServiceRemote
     */
    PatientCorrelationServiceRemote getPatientCorrelationService();
    
    /**
     * 
     * @return OrganizationEntityServiceRemote
     */
    OrganizationEntityServiceRemote getOrganizationEntityService();
    
    /**
     * @return the identified organization correlation service
     */
    IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService();
    
    /**
     * @return the health care facility correlation service
     */
    HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService();

    /**
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */
    IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService();
}
