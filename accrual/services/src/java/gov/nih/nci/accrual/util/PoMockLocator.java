/**
 * 
 */
package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.util.pomock.MockHealthCareFacilityCorrelationService;
import gov.nih.nci.pa.util.pomock.MockIdentifiedOrganizationCorrelationService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * @author dkrylov
 *
 */
public final class PoMockLocator implements PoServiceLocator {

   
    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getPersonEntityService()
     */
    @Override
    public PersonEntityServiceRemote getPersonEntityService() {        
        return new MockPersonEntityService();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getPatientCorrelationService()
     */
    @Override
    public PatientCorrelationServiceRemote getPatientCorrelationService() {      
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getOrganizationEntityService()
     */
    @Override
    public OrganizationEntityServiceRemote getOrganizationEntityService() {       
        return new MockOrganizationEntityService();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getIdentifiedOrganizationCorrelationService()
     */
    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService() {       
        return new MockIdentifiedOrganizationCorrelationService();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getHealthCareFacilityCorrelationService()
     */
    @Override
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {       
        return new MockHealthCareFacilityCorrelationService();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.util.PoServiceLocator#getIdentifiedOrganizationEntityService()
     */
    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {       
        return new MockIdentifiedOrganizationCorrelationService();
    }

}
