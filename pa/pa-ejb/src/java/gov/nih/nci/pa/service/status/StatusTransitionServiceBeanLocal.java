package gov.nih.nci.pa.service.status;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.StatusRules;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author vinodh copyright NCI 2008. All rights reserved. This code may not be
 *         used without the express written permission of the copyright holder,
 *         NCI.
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StatusTransitionServiceBeanLocal 
    extends BaseStatusTransitionService
    implements StatusTransitionServiceLocal {

    @EJB
    private LookUpTableServiceRemote lookUpTableServiceRemote;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StatusRules getStatusRules() throws PAException {
        return (StatusRules) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getStatusRulesCache(), "StatusRules", new Closure() {

                    @Override
                    public Object execute() throws PAException {
                        StatusTransitionsConfig statusTransitionsConfig 
                                        = new StatusTransitionsConfig();
                        StatusRules statusRules = null;

                        String statusRulesStr = lookUpTableServiceRemote
                                .getPropertyValueFromCache("status.rules");
                        try {
                            statusRules = statusTransitionsConfig
                                    .loadStatusRules(statusRulesStr);
                        } catch (Exception e) {
                            throw new PAException(
                                    "Error loading status rules from config", e);
                        }

                        return statusRules;
                    }
                });
    }

    /**
     * @param lookUpTableServiceRemote the lookUpTableServiceRemote to set
     */
    public void setLookUpTableServiceRemote(
            LookUpTableServiceRemote lookUpTableServiceRemote) {
        this.lookUpTableServiceRemote = lookUpTableServiceRemote;
    }

}
