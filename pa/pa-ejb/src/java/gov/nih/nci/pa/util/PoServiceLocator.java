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

/**
 * A class for all Po look-ups.
 * @author NAmiruddin
 *
 */

public interface PoServiceLocator {


    /**
     * @return OrganizationEntityServiceRemote
     */
    OrganizationEntityServiceRemote getOrganizationEntityService();

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     */
    HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService();

    /**
     * @return ResearchOrganizationCorrelationServiceRemote
     */
    ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService();

    /**
     * @return OversightCommitteeCorrelationServiceRemote
     */
    OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService();

    /**
     *
     * @return PersonEntityServiceRemote
     */
    PersonEntityServiceRemote getPersonEntityService();

    /**
     *
     * @return ClinicalResearchStaffCorrelationServiceRemote
     */
    ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService();

    /**
     *
     * @return HealthCareProviderCorrelationServiceRemote
     */
    HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService();

    /**
     *
     * @return OrganizationalContactCorrelationServiceRemote
     */
    OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService();

    /**
     *
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */
    IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService();

    /**
     *
     * @return IdentifiedPersonCorrelationServiceRemote
     */
    IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService();

    /**
     *
     * @return FamilyServiceRemote
     */
    FamilyServiceRemote getFamilyService();

}
