package gov.nih.nci.pa.service;


import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author Vrushali
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed(SUBMITTER_ROLE)
public class StudyProtocolStageServiceBean extends StudyProtocolStageBeanLocal
    implements StudyProtocolStageServiceRemote {

}
