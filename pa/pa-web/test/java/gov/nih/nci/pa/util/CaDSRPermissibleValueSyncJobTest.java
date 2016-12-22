package gov.nih.nci.pa.util;
import static org.mockito.Mockito.mock;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class CaDSRPermissibleValueSyncJobTest {
    private CaDSRPermissibleValueSyncJob job;
    private JobExecutionContext context;
    private CaDSRPVSyncJobHelper helper;
    @Before
    public void setup() {
        job = new CaDSRPermissibleValueSyncJob();
        helper = mock(CaDSRPVSyncJobHelper.class);
        job.setHelper(helper);
    }
    @Test
    public void executetest() throws JobExecutionException, PAException {
        job.execute(context);
    }
}
