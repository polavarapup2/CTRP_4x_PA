package gov.nih.nci.pa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Reshma Koganti
 * 
 */
@SuppressWarnings({ "PMD" })
public class CharacterEncodingFilter implements Filter {

    /**
     * The default character encoding to set for requests that pass through this
     * filter.
     */
    private String encodingStyle = null;

    /**
     * The filter configuration object we are associated with. If this value is
     * null, this filter instance is not currently configured.
     */
    private FilterConfig filterConfigs = null;

    /**
     * Should a character encoding specified by the client be ignored?
     */
    private boolean ignore = true;

    /**
     * Take this filter out of service.
     */
    public void destroy() {

        this.encodingStyle = null;
        this.filterConfigs = null;

    }

    /**
     * Select and set (if specified) the character encoding to be used to
     * interpret request parameters for this request.
     * 
     * @param request
     *            The servlet request we are processing
     * @param response
     *            The servlet response we are creating
     * @param chain
     *            The filter chain we are processing
     * 
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // Conditionally select and set the character encoding to be used
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null) {
                request.setCharacterEncoding(encoding);
            }
        }
        response.setCharacterEncoding(encodingStyle);
        // Pass control on to the next filter
        chain.doFilter(request, response);

    }

    /**
     * Place this filter into service.
     * 
     * @param filterConfig
     *            The filter configuration object
     * @exception ServletException
     *                if ServletException occurs
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfigs = filterConfig;
        this.encodingStyle = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("true")) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("yes")) {
            this.ignore = true;
        } else {
            this.ignore = false;
        }

    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Select an appropriate character encoding to be used, based on the
     * characteristics of the current request and/or filter initialization
     * parameters. If no character encoding should be set, return
     * <code>null</code>.
     * <p>
     * The default implementation unconditionally returns the value configured
     * by the <strong>encoding</strong> initialization parameter for this
     * filter.
     * 
     * @param request
     *            The servlet request we are processing
     * @return String the encoding
     */
    protected String selectEncoding(ServletRequest request) {

        return (this.encodingStyle);

    }

}
