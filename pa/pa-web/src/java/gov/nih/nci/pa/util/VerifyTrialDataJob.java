package gov.nih.nci.pa.util;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class VerifyTrialDataJob implements Job {
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(VerifyTrialDataJob.class);
    private VerifyTrialDataHelper helper = new VerifyTrialDataHelper();
    
    /**
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("executing verify trial data job");
        try {
            helper.getOpenTrials();
            LOG.info("Success verify trial data job...........");
        } catch (Exception e) {
            LOG.error("error", e);
        }
        
    }
    /**
     * 
     * @return helper helper
     */
    public VerifyTrialDataHelper getHelper() {
        return helper;
    }
    /**
     * 
     * @param helper helper
     */
    public void setHelper(VerifyTrialDataHelper helper) {
        this.helper = helper;
    }

}
