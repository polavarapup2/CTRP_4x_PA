package gov.nih.nci.pa.util;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRSyncJob implements Job {
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRSyncJob.class);

    /** execute.
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        LOG.info("executing CaDSRSyncJob job");
        try {
            CaDSRSyncHelper helper = new CaDSRSyncHelper();
            helper.syncPlannedMarkerAttributes();
            helper.updateMarkerTables();
            LOG.info("CaDSRSyncJob job success.....");
        } catch (Exception e) {
            LOG.error("error", e);
            PaRegistry.getMailManagerService()
            .sendJobFailureNotification(context.getJobDetail().getName(), ExceptionUtils.getFullStackTrace(e)); 
        }
    }

}
