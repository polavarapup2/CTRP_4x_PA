package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import org.apache.commons.lang.BooleanUtils;


/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */
public final class PoRegistry {


    private static final PoRegistry PO_REGISTRY = new PoRegistry();
    private PoServiceLocator poServiceLocator;
    
    /**
     * @return the PO_REGISTRY
     */
    public static PoRegistry getInstance() {
        return PO_REGISTRY;
    }
    

    /**
     * Constructor for the singleton instance.
     */
    private PoRegistry() {
        if (BooleanUtils.isTrue(BooleanUtils.toBoolean(PaEarPropertyReader.getProperties().getProperty("mock.po")))) {
            poServiceLocator = new PoMockLocator();
        } else {
            poServiceLocator = new PoJndiServiceLocator();
        }
    }
    /**
     * @return the serviceLocator
     */
    public PoServiceLocator getPoServiceLocator() {
        return poServiceLocator;
    }
    
    /**
     * 
     * @param poServiceLocator poServiceLocator
     */
    public void setPoServiceLocator(PoServiceLocator poServiceLocator) {
        this.poServiceLocator  = poServiceLocator;
    }
    
    /**
     * @return PersonEntityServiceRemote
     */
    public static PersonEntityServiceRemote getPersonEntityService() {
        return getInstance().getPoServiceLocator().getPersonEntityService();
    }
    
    /**
     * @return PatientCorrelationServiceRemote
     */
    public static PatientCorrelationServiceRemote getPatientCorrelationService() {
        return getInstance().getPoServiceLocator().getPatientCorrelationService();
    }
    
    /**
     * @return OrganizationEntityServiceRemote
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() {
        return getInstance().getPoServiceLocator().getOrganizationEntityService();
    }
    
    /**
     * @return the identifier organization correlation service
     */
    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService() {
        return getInstance().getPoServiceLocator().getIdentifiedOrganizationCorrelationService();
    }
    
    /**
     * @return the health care facility correlation service
     */
    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return getInstance().getPoServiceLocator().getHealthCareFacilityCorrelationService();
    }
    
    /**
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */
    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return getInstance().getPoServiceLocator().getIdentifiedOrganizationEntityService();
    }
}
