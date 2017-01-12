package gov.nih.nci.pa.util;

import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import org.apache.commons.lang.BooleanUtils;


/**
 * A class for all Po look-ups.
 * @author NAmiruddin
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
            this.poServiceLocator = new MockPoJndiServiceLocator();
        } else {
            this.poServiceLocator = new PoJndiServiceLocator();
        }
    }

    /**
     *
     * @return OrganizationEntityServiceRemote
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() {
        return getInstance().getPoServiceLocator().getOrganizationEntityService();
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     */
    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return getInstance().getPoServiceLocator().getHealthCareFacilityCorrelationService();
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     */
    public static ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        return getInstance().getPoServiceLocator().getResearchOrganizationCorrelationService();
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     */
    public static OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        return getInstance().getPoServiceLocator().getOversightCommitteeCorrelationService();
    }

    /**
     * @return the serviceLocator
     */
    public PoServiceLocator getPoServiceLocator() {
        return this.poServiceLocator;
    }

    /**
     *
     * @param poServiceLocator poServiceLocator
     */
    public void setPoServiceLocator(PoServiceLocator poServiceLocator) {
        this.poServiceLocator  = poServiceLocator;
    }
    /**
     *
     * @return PersonEntityServiceRemote
     */
    public static PersonEntityServiceRemote getPersonEntityService() {
        return getInstance().getPoServiceLocator().getPersonEntityService();
    }
    /**
     * @return ClinicalResearchStaffCorrelationServiceRemote
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        return getInstance().getPoServiceLocator().getClinicalResearchStaffCorrelationService();
    }
    /**
     *
     * @return HealthCareProviderCorrelationServiceRemote
     */
    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        return getInstance().getPoServiceLocator().getHealthCareProviderCorrelationService();
    }
    /**
     *
     * @return OrganizationalContactCorrelationServiceRemote
     */
    public static OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
       return getInstance().getPoServiceLocator().getOrganizationalContactCorrelationService();
    }
    /**
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */
    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return getInstance().getPoServiceLocator().getIdentifiedOrganizationEntityService();
    }

    /**
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */
    public static IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        return getInstance().getPoServiceLocator().getIdentifiedPersonEntityService();
    }

    /**
     * @return FamilyServiceRemote
     */
    public static FamilyServiceRemote getFamilyService() {
        return getInstance().getPoServiceLocator().getFamilyService();
    }
}
