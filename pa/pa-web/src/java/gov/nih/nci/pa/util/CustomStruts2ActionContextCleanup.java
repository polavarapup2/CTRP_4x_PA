package gov.nih.nci.pa.util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;
import org.apache.struts2.dispatcher.Dispatcher;

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
 * Created by bnorris on 5/25/17.
 * <p>
 * This class is derived from the class org.apache.struts2.dispatcher.ActionContextCleanUp
 * located in the Struts2 jar file. This class is referenced in the web.xml file filters. We
 * needed to remove the showDeprecatedWarning() method to get rid of the warnings that were
 * printed to the console. We couldn't simply just change the log level since they used a
 * System.out.println() in the method.
 */
/** @deprecated */
public class CustomStruts2ActionContextCleanup implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomStruts2ActionContextCleanup.class);
    private static final String COUNTER = "__cleanup_recursion_counter";

    /**
     *  CustomStruts2ActionContextCleanup
     */
    public CustomStruts2ActionContextCleanup() { // NOPMD
    }

    /**
     * doFilter method
     * @param req req
     * @param res res
     * @param chain chain
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String timerKey = "ActionContextCleanUp_doFilter: ";

        try {
            UtilTimerStack.push(timerKey);
            boolean var15 = false;

            try {
                var15 = true;
                Integer count = (Integer) request.getAttribute(COUNTER);
                if (count == null) {
                    count = Integer.valueOf(1);
                } else {
                    count = Integer.valueOf(count.intValue() + 1);
                }

                request.setAttribute(COUNTER, count);
                chain.doFilter(request, response);
                var15 = false;
            } finally {
                if (var15) {
                    int counterVal = ((Integer) request.getAttribute(COUNTER)).intValue();
                    --counterVal;
                    request.setAttribute(COUNTER, Integer.valueOf(counterVal));
                    cleanUp(request);
                }
            }

            int counterVal = ((Integer) request.getAttribute(COUNTER)).intValue();
            --counterVal;
            request.setAttribute(COUNTER, Integer.valueOf(counterVal));
            cleanUp(request);
        } finally {
            UtilTimerStack.pop(timerKey);
        }

    }

    /**
     * cleanUp method
     * @param req req
     */
    protected static void cleanUp(ServletRequest req) {
        Integer count = (Integer) req.getAttribute(COUNTER);
        if (count != null && count.intValue() > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("skipping cleanup counter=" + count, new String[0]);
            }

        } else {
            ActionContext.setContext((ActionContext) null);
            Dispatcher.setInstance((Dispatcher) null);
        }
    }

    /**
     * Destroy method
     */
    public void destroy() { // NOPMD
    }

    /**
     * init method
     * @param arg0 arg0
     * @throws ServletException ServletException
     */
    public void init(FilterConfig arg0) throws ServletException {
    }
}