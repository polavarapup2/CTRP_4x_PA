/**
 * 
 */
package gov.nih.nci.pa.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings({ "PMD.ExcessiveMethodLength", "deprecation" })
@edu.umd.cs.findbugs.annotations.SuppressWarnings
public class QuartzInitializerServlet extends
        org.quartz.ee.servlet.QuartzInitializerServlet {
 
    private static final String CTGOV_UPLOAD_ERROR_PROCESSING_JOB_TRIGGER = "ctgovUploadErrorProcessingJobTrigger";

    private static final String DAILY_TRIGGER_GROUP = "DailyTriggerGroup";

    private static final int SECONDS = 5;
    private static final long HOW_OFTEN_TO_CHECK_FOR_SCHEDULE_CHANGE = Boolean
            .valueOf(System.getProperty("ctrp.env.ci")) ? DateUtils.MILLIS_PER_SECOND
            * SECONDS
            : DateUtils.MILLIS_PER_MINUTE;

    private static final Logger LOG = Logger
            .getLogger(QuartzInitializerServlet.class);

    private final Timer timer = new Timer(true);

    /**
     * 
     */
    private static final long serialVersionUID = 8969010341751838861L;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);

        setupJobSchedule(sc, "ctgov.ftp.schedule", "ctgovUploadJob",
                "ctgovUploadJobTrigger");
        setupJobSchedule(sc, "ctgov.upload.errorEmailProcessing.schedule",
                "ctgovUploadErrorProcessingJob",
                CTGOV_UPLOAD_ERROR_PROCESSING_JOB_TRIGGER);
        setupJobSchedule(sc, "twitter.queue.process.schedule",
                "tweetQueueProcessingJob", "tweetQueueProcessingJobTrigger");
        setupJobSchedule(sc, "twitter.trials.scan.schedule",
                "trialTweetingJob", "trialTweetingJobTrigger");
        
        setupJobSchedule(sc, "resultsUpdater.trials.scan.schedule",
                "resultsPublishedDateUpdaterJob", "resultsPublishedDateUpdaterJobTrigger");

    }

    private void setupJobSchedule(ServletConfig sc,
            final String paPropertyName, final String jobName,
            final String triggerName) {
        final StdSchedulerFactory factory = (StdSchedulerFactory) sc
                .getServletContext().getAttribute(QUARTZ_FACTORY_KEY);
        timer.schedule(new TimerTask() {
            private String lastValue;

            @Override
            public void run() {
                try {
                    String cron = PaRegistry.getLookUpTableService()
                            .getPropertyValue(paPropertyName);
                    if (!StringUtils.equals(lastValue, cron)) {
                        LOG.warn("Setting " + jobName + " schedule to " + cron
                                + "; previous schedule was " + lastValue);
                        Scheduler scheduler = factory.getScheduler();
                        CronTrigger trigger = new CronTrigger(triggerName,
                                DAILY_TRIGGER_GROUP, jobName, "DEFAULT", cron);
                        trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
                        Date nextFireTime;
                        if (scheduler.getTrigger(triggerName,
                                DAILY_TRIGGER_GROUP) != null) {
                            nextFireTime = scheduler.rescheduleJob(triggerName,
                                    DAILY_TRIGGER_GROUP, trigger);
                        } else {
                            nextFireTime = scheduler.scheduleJob(trigger);
                        }
                        LOG.warn("Next fire time for " + jobName + ": "
                                + nextFireTime);
                        lastValue = cron;
                    }
                } catch (Exception e) {
                    LOG.error(e, e);
                }
            }
        }, 0, HOW_OFTEN_TO_CHECK_FOR_SCHEDULE_CHANGE);

    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            timer.cancel();
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

}
