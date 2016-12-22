package gov.nih.nci.pa.util;


import gov.nih.nci.pa.service.PAException;

import org.quartz.Job;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRPermissibleValueSyncJob implements Job {
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRPermissibleValueSyncJob.class);
    private CaDSRPVSyncJobHelper helper = new CaDSRPVSyncJobHelper();
    /** execute.
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("executing permissible value job");
        try {
            helper.updatePlannedMarkerSyncTable();
            LOG.info("Success permissible value job...........");
        } catch (Exception e) {
            LOG.error("error", e);
            try {
               PaRegistry.getMailManagerService()
                  .sendCadsrJobErrorEMail();
            } catch (PAException e1) {
                PaRegistry.getMailManagerService()
                .sendJobFailureNotification(context.getJobDetail().getName(), ExceptionUtils.getFullStackTrace(e));     
                LOG.error("error while sending email", e);
            }
        }
    }
    /**
     * 
     * @return helper helper
     */
    public CaDSRPVSyncJobHelper getHelper() {
        return helper;
       
    }
    /**
     * 
     * @param helper helper
     */
    public void setHelper(CaDSRPVSyncJobHelper helper) {
        this.helper = helper;
    }
}
