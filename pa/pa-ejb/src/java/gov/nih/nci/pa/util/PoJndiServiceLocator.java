package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;
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

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * A class for all Po look-ups.
 * @author NAmiruddin
 *
 */
public class PoJndiServiceLocator implements PoServiceLocator {

    private static final Logger LOG = Logger.getLogger(PoJndiServiceLocator.class);
    private static Context ctx;

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        return lookup("OrganizationEntityServiceBean"
                + "!gov.nih.nci.services.organization.OrganizationEntityServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return lookup("HealthCareFacilityCorrelationServiceBean" 
                + "!gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        return lookup("ResearchOrganizationCorrelationServiceBean" 
                + "!gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        return lookup("OversightCommitteeCorrelationServiceBean" 
                + "!gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        return lookup("PersonEntityServiceBean!gov.nih.nci.services.person.PersonEntityServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        return lookup("ClinicalResearchStaffCorrelationServiceBean" 
                + "!gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        return lookup("HealthCareProviderCorrelationServiceBean" 
                + "!gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        return lookup("OrganizationalContactCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return lookup("IdentifiedOrganizationCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        return lookup("IdentifiedPersonCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public FamilyServiceRemote getFamilyService() {
        return lookup("FamilyServiceRemoteBean!gov.nih.nci.services.family.FamilyServiceRemote");
    }

    @SuppressWarnings("unchecked")
    private <T> T lookup(String name) {
        T svc = null;
        try {
            Context context = getContext();
            svc = (T) context.lookup("po/po-services/" + name);
        } catch (NamingException e) {
            LOG.error(e);
        }
        return svc;
    }

    /**
     * @return the context
     * @throws NamingException exception
     */
    public static synchronized Context getContext() throws NamingException {
        if (ctx == null) {
            try {
                final Properties jndiProps = new Properties();
                jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                jndiProps.put(Context.PROVIDER_URL, "remote://" + PaEarPropertyReader.getLookUpServerInfo());
                jndiProps.put(Context.SECURITY_PRINCIPAL, PaEarPropertyReader.getLookUpServerPoPrincipal());
                jndiProps.put(Context.SECURITY_CREDENTIALS, PaEarPropertyReader.getLookUpServerPoCredentials());
                jndiProps.put("jboss.naming.client.ejb.context", true);
                jndiProps.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
                ctx = new InitialContext(jndiProps);
            } catch (PAException e) {
                LOG.error(e);
            }
        }
        return ctx;
    }
}
