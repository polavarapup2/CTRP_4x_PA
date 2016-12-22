package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */
public class PoJndiServiceLocator implements PoServiceLocator {

    private static final Logger LOG = Logger.getLogger(PoJndiServiceLocator.class);
    private Context ctx;


    /**
     * Constructor.
     */
    public PoJndiServiceLocator() {
        try {
            final Properties jndiProps = new Properties();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProps.put(Context.PROVIDER_URL, "remote://" + PaEarPropertyReader.getLookUpServerInfo());
            jndiProps.put(Context.SECURITY_PRINCIPAL, PaEarPropertyReader.getLookUpServerPoPrincipal());
            jndiProps.put(Context.SECURITY_CREDENTIALS, PaEarPropertyReader.getLookUpServerPoCredentials());
            jndiProps.put("jboss.naming.client.ejb.context", true);
            jndiProps.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
            ctx = new InitialContext(jndiProps);
        } catch (Exception e) {
            LOG.error(e);
        }
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
    public PatientCorrelationServiceRemote getPatientCorrelationService() {
        return lookup("PatientCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.PatientCorrelationServiceRemote");
    }
    
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
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService() {
        return lookup("IdentifiedOrganizationCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return lookup("HealthCareFacilityCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote");
    }   

    @SuppressWarnings("unchecked")
    private <T> T lookup(String name) {
        T svc = null;
        try {
            svc = (T) ctx.lookup("po/po-services/" + name);
        } catch (NamingException e) {
            LOG.error(e);
        }
        return svc;
    }

    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return lookup("IdentifiedOrganizationCorrelationServiceBean"
                + "!gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote");
    }
}
