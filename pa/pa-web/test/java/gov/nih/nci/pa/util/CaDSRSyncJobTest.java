package gov.nih.nci.pa.util;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Matchers.anyString;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class CaDSRSyncJobTest {
    private CaDSRSyncJob job;
    private JobExecutionContext context;
    private JobDetail jobDetail;
    private CaDSRSyncHelper helper;
    private MailManagerServiceLocal mailManagerService;    
    
    @Before
    public void setup() {
        job = new CaDSRSyncJob();
        helper = mock(CaDSRSyncHelper.class);
        mailManagerService = mock(MailManagerServiceLocal.class);
        context = mock(JobExecutionContext.class);
        jobDetail = mock(JobDetail.class);        
        
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);       
        when(paRegSvcLoc.getMailManagerService()).thenReturn(mailManagerService);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getName()).thenReturn("TEST");
        doNothing().when(mailManagerService).sendJobFailureNotification(anyString(), anyString());        
    }
    
    @Test
    public void executetest() throws JobExecutionException, PAException {
        job.execute(context);
    }
}
