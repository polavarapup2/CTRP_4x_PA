/**
 * 
 */
package gov.nih.nci.pa.webservices.interceptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaRegistry;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.core.ServerResponse;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
public class RegistryAccountRequiredInterceptorTest extends AbstractMockitoTest {

    private RegistryAccountRequiredInterceptor interceptor = new RegistryAccountRequiredInterceptor();

    @Before
    public void before() throws PAException {

        when(regUserSvc.getUser(eq("jdoe01"))).thenReturn(regUser);
        when(regUserSvc.getUser(eq("abstractor"))).thenReturn(null);
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getRegistryUserService()).thenReturn(regUserSvc);

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
