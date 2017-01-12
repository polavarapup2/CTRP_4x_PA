/**
 * 
 */
package gov.nih.nci.registry.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.PaRegistry;

/**
 * @author vpoluri
 *
 */

public class ReportViewerInterceptor implements Interceptor {

    private static final long serialVersionUID = -3974874894070301086L;
    private static final Logger LOG = Logger.getLogger(ReportViewerInterceptor.class);
    private LookUpTableServiceRemote lookupTableService;

    /**
     * init method
     */
    @Override
    public void init() {
        lookupTableService = PaRegistry.getLookUpTableService();
    }

    /**
     * @param action
     *            - Invocation action
     * @return follow up action key
     * @throws Exception - exception 
     */
    @Override
    public String intercept(ActionInvocation action) throws Exception {

        String response = "";
        Map<String, Object> session = action.getInvocationContext().getSession();

        String isReportsAllowed = (String) session.get("isReportsAllowed");

        if (isReportsAllowed == null || isReportsAllowed.length() <= 0) {
            isReportsAllowed = getShowReportViewerFlag();
            session.put("isReportsAllowed", isReportsAllowed);
        }

        boolean isSiteAdmin = (boolean) session.get("isSiteAdmin");

        if (isReportsAllowed != null && isSiteAdmin && isReportsAllowed.equals("true")) {
            response = action.invoke();
        } else {
            response = "noAccess";
        }

        return response;
    }

    
    /**
     * 
     * @return - returns isReportsAllowed flag
     */
    private String getShowReportViewerFlag() {

        String isReportsAllowed = null;
        try {
            isReportsAllowed = getLookupTableService().getPropertyValueFromCache("reg.web.admin.showReportsMenu");

        } catch (PAException e) {
            LOG.error(e.getMessage(), e);
        }

        return isReportsAllowed;
    }

    /**
     * @return the lookupTableService
     */
    public LookUpTableServiceRemote getLookupTableService() {
        if (lookupTableService == null) {
            lookupTableService = PaRegistry.getLookUpTableService();
        }
        return lookupTableService;
    }

    /**
     * @param lookupTableService
     *            the lookupTableService to set
     */
    public void setLookupTableService(LookUpTableServiceRemote lookupTableService) {
        this.lookupTableService = lookupTableService;
    }

    /**
     * 
     */
    @Override
    public void destroy() {
        lookupTableService = null;
    }

}
