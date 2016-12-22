/**
 * 
 */
package gov.nih.nci.accrual.webservices.interceptors;

import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import java.lang.reflect.Method;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@Provider
@ServerInterceptor
public final class RegistryAccountRequiredInterceptor implements
        PreProcessInterceptor, AcceptedByMethod {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jboss.resteasy.spi.interception.AcceptedByMethod#accept(java.lang
     * .Class, java.lang.reflect.Method)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean accept(Class arg0, Method arg1) {
        return true;
    }

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethod method) {
        try {
            String userName = UsernameHolder.getUser();
            if (StringUtils.isNotBlank(userName)) {
                RegistryUser registryUser = PaServiceLocator.getInstance().getRegistryUserService()
                        .getUser(userName);
                if (registryUser == null) {
                    return new ServerResponse(
                            "It appears you have a valid account in NCI network; "
                                    + "however, your account setup in NCI CTRP Registration Site does not appear"
                                    + " to have been completed. "
                                    + "It is required in order to use NCI CTRP Accrual Web Services successfully.",
                            Status.PRECONDITION_FAILED.getStatusCode(),
                            new Headers<Object>());
                }
            }
            return null;
        } catch (PAException e) {
            throw new Failure(e);
        }
    }

}
