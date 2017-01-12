/**
 * 
 */
package gov.nih.nci.pa.service.util; // NOPMD

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author dkrylov
 * 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
        SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
public class FlaggedTrialRemoteService extends FlaggedTrialServiceBean
        implements FlaggedTrialServiceRemote {

}
