package gov.nih.nci.pa.filter;

import gov.nih.nci.pa.util.CsmHelper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filters that ensures the CSM user info is set in session.
 *
 * @author ludetc
 *
 */
public class CsmUserFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        // do nothing
    }

    /**
     * Adds CSMHelper to session if missing and if remoteUser is not null.
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpSession session = httpRequest.getSession();

        if (session.getAttribute(CsmHelper.class.getName()) == null) {
            String username = httpRequest.getRemoteUser();
            if (username != null) {
                CsmHelper csmHelper = new CsmHelper(username);
                session.setAttribute("CsmHelper", csmHelper);
            } else {
                session.removeAttribute(CsmHelper.class.getName());
            }
        }

        chain.doFilter(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig arg0) throws ServletException {
        // do nothing
    }

}
