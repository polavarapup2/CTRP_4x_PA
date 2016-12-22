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
public class VerifyTrialDataJobTest extends AbstractHibernateTestCase {
    private VerifyTrialDataJob job;
    private JobExecutionContext context;
    private VerifyTrialDataHelper helper;
    @Before
    public void setup() {
        job = new VerifyTrialDataJob();
        helper = mock(VerifyTrialDataHelper.class);
        job.setHelper(helper);
    }
    @Test
    public void executetest() throws JobExecutionException, PAException {
        job.execute(context);
    }

}
