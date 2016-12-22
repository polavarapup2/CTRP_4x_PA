package gov.nih.nci.pa.filter;

import gov.nih.nci.pa.util.CsmHelper;

import org.junit.Test;

import com.mockrunner.mock.web.MockFilterChain;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * Test the csm user filter
 * @author ludetc
 *
 */
public class CsmUserFilterTest {

    @Test
    public void testFilter() throws Exception {

        CsmUserFilter filter = new CsmUserFilter();
        filter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);

        request.getSession().setAttribute(CsmHelper.class.getName(), new CsmHelper(""));
        filter = new CsmUserFilter();
        chain = new MockFilterChain();
        filter.doFilter(request, response, chain);

        filter.destroy();
    }

    @Test
    public void testFilter2() throws Exception {

        CsmUserFilter filter = new CsmUserFilter();
        filter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("aUser");

        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);

        request.getSession().setAttribute(CsmHelper.class.getName(), new CsmHelper(""));
        filter = new CsmUserFilter();
        chain = new MockFilterChain();
        filter.doFilter(request, response, chain);

        filter.destroy();
    }
}
