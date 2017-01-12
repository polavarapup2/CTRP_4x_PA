package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRValueFromDBTest  extends AbstractHibernateTestCase{
	  /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRValueFromDBTest.class);
    ApplicationService appService = mock(ApplicationService.class);
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    
    @Test
    public void testCaDSRURL() throws Exception {
        TestSchema.caDSRSyncJobProperties();
        PAProperties prop = new PAProperties();
        prop.setName("CADSR_URL");
        prop.setValue("url");
        TestSchema.addUpdObject(prop);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableSrv);
        appService = getApplicationService();
//        assertEquals("HTTP invoker proxy for service URL [/http/applicationService]",appService.toString());
    }
    
    /**
     * 
     * @return ApplicationService appService
     * @throws Exception  Exception
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public ApplicationService getApplicationService() throws Exception {
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error(
                    "Error attempting to instantiate caDSR Application Service.",
                    e);
            throw new Exception(e);
        }
        return appService;
    }
    
}
