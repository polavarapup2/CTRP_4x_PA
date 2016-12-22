/**
 * 
 */
package gov.nih.nci.pa.webservices.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dkrylov
 * 
 */
public final class SessionInvalidationFilter implements Filter {

    @Override
    public void destroy() { // NOPMD
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } finally {
            ((HttpServletResponse) resp).setHeader("Set-Cookie", "");
            ((HttpServletRequest) req).getSession().invalidate();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException { // NOPMD
    }

}
