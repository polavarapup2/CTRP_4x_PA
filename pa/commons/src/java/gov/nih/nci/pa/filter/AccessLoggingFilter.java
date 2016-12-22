package gov.nih.nci.pa.filter;

import gov.nih.nci.pa.domain.WebServiceAccessLog;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings({ "PMD" })
public class AccessLoggingFilter implements Filter {

    private static final Logger LOG = Logger
            .getLogger("WebServiceAccessLog");

    private String matchPattern;

    /**
     * 
     * @param req
     *            The servlet request we are processing
     * @param resp
     *            The servlet response we are creating
     * @param chain
     *            The filter chain we are processing
     * 
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = StringUtils.defaultString(request.getRequestURI());
        if (uri.matches(matchPattern)) {
            processAndLog(request, response, chain);
        } else {
            // Pass control on to the next filter
            chain.doFilter(request, response);
        }

    }

    @SuppressWarnings("deprecation")
    private void processAndLog(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
                request);
        final InterceptingHttpServletResponse wrappedResponse = new InterceptingHttpServletResponse(
                response);

        final Date stamp = new Date();
        final WebServiceAccessLog log = new WebServiceAccessLog();
        log.setClientIp(request.getRemoteAddr());
        log.setDatetime(stamp);
        log.setClientUsername(request.getRemoteUser());
        log.setUri(request.getRequestURI()
                + (StringUtils.isNotBlank(request.getQueryString()) ? "?"
                        + request.getQueryString() : ""));
        log.setMethod(request.getMethod());
        log.setHeaders(getHeadersAsString(request));
        log.setPayload(IOUtils.toString(wrappedRequest.getReader()));
        log.setProcessingErrors(StringUtils.EMPTY);
        wrappedRequest.resetInputStream();

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            log.setProcessingErrors(ExceptionUtils.getFullStackTrace(e));
        } finally {
            log.setResponse(IOUtils.toString(wrappedResponse.getCopy()
                    .getCopy().toByteArray(), response.getCharacterEncoding()));
            log.setResponseCode(response.getStatus());
            log.setProcessingErrors(StringUtils.defaultString(wrappedResponse
                    .getStatusError()) + log.getProcessingErrors());
            log.setProcessingTime(System.currentTimeMillis() - stamp.getTime());
            LOG.info(log);
            save(log);
        }

    }

    private void save(WebServiceAccessLog log) {
        final HibernateHelper helper = PaHibernateUtil.getHibernateHelper();
        try {
            helper.openAndBindSession();
            helper.getCurrentSession().save(log);
            helper.getCurrentSession().flush();
        } catch (Exception e) {
            LOG.error(e, e);
        } finally {
            helper.unbindAndCleanupSession();
        }

    }

    private String getHeadersAsString(HttpServletRequest request) {
        final StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            sb.append(name
                    + "="
                    + ("authorization".equalsIgnoreCase(name) ? "*********"
                            : value) + SystemUtils.LINE_SEPARATOR);
        }
        return sb.toString();
    }

    /**
     * Place this filter into service.
     * 
     * @param filterConfig
     *            The filter configuration object
     * @exception ServletException
     *                if ServletException occurs
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        matchPattern = filterConfig.getInitParameter("matchPattern");
    }

    @Override
    public void destroy() {
    }

    /**
     * @author dkrylov
     * 
     */
    private static class InterceptingHttpServletResponse extends
            HttpServletResponseWrapper {

        private final HttpServletResponse response;
        private final CopyOutputStream copy;
        private String statusError;

        public InterceptingHttpServletResponse(HttpServletResponse response) {
            super(response);
            this.response = response;
            try {
                this.copy = new CopyOutputStream(response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    copy.write(b);

                }
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(getOutputStream(), true);
        }

        public CopyOutputStream getCopy() {
            return copy;
        }

        /**
         * @author dkrylov
         * 
         */
        private static final class CopyOutputStream extends OutputStream {

            private final OutputStream original;
            private final ByteArrayOutputStream copy = new ByteArrayOutputStream();

            public CopyOutputStream(OutputStream original) {
                this.original = original;
            }

            /**
             * @param b
             * @throws IOException
             * @see java.io.OutputStream#write(int)
             */
            public void write(int b) throws IOException {
                original.write(b);
                copy.write(b);
            }

            /**
             * @param b
             * @throws IOException
             * @see java.io.OutputStream#write(byte[])
             */
            public void write(byte[] b) throws IOException {
                original.write(b);
                copy.write(b);
            }

            /**
             * @param b
             * @param off
             * @param len
             * @throws IOException
             * @see java.io.OutputStream#write(byte[], int, int)
             */
            public void write(byte[] b, int off, int len) throws IOException {
                original.write(b, off, len);
                copy.write(b, off, len);
            }

            /**
             * @throws IOException
             * @see java.io.OutputStream#flush()
             */
            public void flush() throws IOException {
                original.flush();
                copy.flush();
            }

            /**
             * @throws IOException
             * @see java.io.OutputStream#close()
             */
            public void close() throws IOException {
                original.close();
                copy.close();
            }

            /**
             * @return the copy
             */
            public ByteArrayOutputStream getCopy() {
                return copy;
            }

        }

        /**
         * @param arg0
         * @param arg1
         * @throws IOException
         * @see javax.servlet.http.HttpServletResponse#sendError(int,
         *      java.lang.String)
         */
        public void sendError(int arg0, String arg1) throws IOException {
            response.sendError(arg0, arg1);
            this.statusError = arg1;
        }

        /**
         * @param arg0
         * @param arg1
         * @deprecated
         * @see javax.servlet.http.HttpServletResponse#setStatus(int,
         *      java.lang.String)
         */
        public void setStatus(int arg0, String arg1) {
            response.setStatus(arg0, arg1);
            this.statusError = arg1;
        }

        /**
         * @return the statusError
         */
        public String getStatusError() {
            return statusError;
        }

    }

    /**
     * @author dkrylov
     * 
     */
    private static class ResettableStreamHttpServletRequest extends
            HttpServletRequestWrapper {

        private byte[] rawData;
        private HttpServletRequest request;
        private ResettableServletInputStream servletStream;

        public ResettableStreamHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
            this.servletStream = new ResettableServletInputStream();

        }

        public void resetInputStream() {
            servletStream.stream = new ByteArrayInputStream(rawData);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getInputStream());
                servletStream.stream = new ByteArrayInputStream(rawData);
            }
            return servletStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream(),
                    request.getCharacterEncoding()));
        }

        /**
         * @author dkrylov
         * 
         */
        private final class ResettableServletInputStream extends
                ServletInputStream {

            private InputStream stream;

            @Override
            public int read() throws IOException {
                return stream.read();
            }
        }
    }
}
