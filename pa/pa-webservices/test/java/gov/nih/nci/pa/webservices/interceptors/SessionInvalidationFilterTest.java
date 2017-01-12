/**
 * 
 */
package gov.nih.nci.pa.webservices.interceptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gov.nih.nci.pa.util.AbstractMockitoTest;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

import com.mockrunner.mock.web.MockFilterChain;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * @author dkrylov
 * 
 */
public class SessionInvalidationFilterTest extends AbstractMockitoTest {

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.interceptors.SessionInvalidationFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)}
     * .
     * 
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public final void testDoFilter() throws ServletException, IOException {
        SessionInvalidationFilter filter = new SessionInvalidationFilter();
        filter.init(null);

        MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpSession session = new MockHttpSession();        
        request.setSession(session);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertFalse(session.isValid());
        assertEquals("", response.getHeader("Set-Cookie"));
        
        filter.destroy();

    }

}
