/**
 * 
 */
package gov.nih.nci.accrual.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.webservices.interceptors.RegistryAccountRequiredInterceptor;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.core.ServerResponse;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
public class RegistryAccountRequiredInterceptorTest {

    private RegistryAccountRequiredInterceptor interceptor = new RegistryAccountRequiredInterceptor();

    @Before
    public void before() throws PAException {
        ServiceLocatorPaInterface serviceLocatorPaInterface = mock(ServiceLocatorPaInterface.class);
        PaServiceLocator.getInstance().setServiceLocator(
                serviceLocatorPaInterface);
        
        RegistryUser regUser = new RegistryUser();
        regUser.setPrsOrgName("prs Org Name");
        regUser.setAffiliatedOrganizationId(1L);

        RegistryUserServiceRemote regUserSvc = mock(RegistryUserServiceRemote.class);
        when(serviceLocatorPaInterface.getRegistryUserService()).thenReturn(
                regUserSvc);

        when(regUserSvc.getUser(eq("jdoe01"))).thenReturn(regUser);
        when(regUserSvc.getUser(eq("abstractor"))).thenReturn(null);

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.interceptors.RegistryAccountRequiredInterceptor#accept(java.lang.Class, java.lang.reflect.Method)}
     * .
     */
    @Test
    public final void testAccept() {
        assertTrue(interceptor.accept(null, null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.interceptors.RegistryAccountRequiredInterceptor#preProcess(org.jboss.resteasy.spi.HttpRequest, org.jboss.resteasy.core.ResourceMethod)}
     * .
     */
    @Test
    public final void testPreProcessOK() {
        UsernameHolder.setUserCaseSensitive("jdoe01");
        assertNull(interceptor.preProcess(null, null));
    }

    @Test
    public final void testPreProcessFailed() {
        UsernameHolder.setUserCaseSensitive("abstractor");
        ServerResponse r = interceptor.preProcess(null, null);
        assertEquals(Status.PRECONDITION_FAILED.getStatusCode(), r.getStatus());
    }

}
