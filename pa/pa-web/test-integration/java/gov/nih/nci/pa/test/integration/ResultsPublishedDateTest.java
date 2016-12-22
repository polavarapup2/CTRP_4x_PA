package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import com.dumbster.smtp.SmtpMessage;

@Batch(number = 3)
public class ResultsPublishedDateTest extends AbstractTrialStatusTest {

    final int ON_OFF_SWITCH_RECHECK_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 5000;
    private static final int SCHEDULING_CHANGES_PICK_UP_TIME = 60000;
    
    List<TrialInfo> testTrials;
    

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    

   
   private void setupPublishDateTrials() throws Exception {
           testTrials = new ArrayList<AbstractPaSeleniumTest.TrialInfo>();
           TrialInfo trial = createSubmittedTrial();
           addSponsor(trial, "National Cancer Institute");
           addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
           setPCD(trial, "2015-01-01", ActualAnticipatedTypeCode.ANTICIPATED);
           setPrsandPublishDate(trial, "2015-01-01");
           trial.nctID="NCT00334893";
           addNctIdentifier(trial, trial.nctID);
           testTrials.add(trial);
           
       }
   
   private void setPrsandPublishDate(TrialInfo info, String publishDate
           ) throws SQLException {
       QueryRunner runner = new QueryRunner();
       info.leadOrgID = info.uuid;
       String sql = "update study_protocol set prs_release_date =" + getTS(publishDate)
               + ", trial_published_date =null" 
               + " where identifier = " + info.id;

       runner.update(connection, sql);
   }
   
     @Test
     public void testPublishDateNoUpdateTrial() throws Exception {
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
         Thread.sleep(ON_OFF_SWITCH_RECHECK_WAIT_TIME);
         deactivateAllTrials();
        // setupPublishDateTrials();
         log("Setting up for a Results Publish test case");
         restartEmailServer();
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0/1 * 1/1 * ? *");
         Thread.sleep(SCHEDULING_CHANGES_PICK_UP_TIME);
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
    
         // Wait for PA to pick up changes to scheduling
         log("Wait for PA to pick up changes to scheduling...");
         Thread.sleep(ON_OFF_SWITCH_RECHECK_WAIT_TIME);
         log("Wait over");
         
      
         waitForEmailsToArrive(1);
         
         
      
         
         Iterator emailIter = server.getReceivedEmail();
         SmtpMessage email = (SmtpMessage) emailIter.next();
         String subject = email.getHeaderValues("Subject")[0];
         String body = email.getBody().replaceAll("\\s+", " ")
                 .replaceAll(">\\s+", ">");
         
         assertTrue(subject.equalsIgnoreCase("CTRP Nightly Job -- Update Trial Results Published Date"));
         assertTrue(body.contains("<br>Dear CTRO:<br><br>This nightly job ran successfully. No trials were updated"));
        
         //by this point job has already run turn off schedule so that it does not run again
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
         
      
                 
     }
     
     @Test
     public void testPublishDateUpdateTrial() throws Exception {
         
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
         Thread.sleep(ON_OFF_SWITCH_RECHECK_WAIT_TIME);
         deactivateAllTrials();
         setupPublishDateTrials();
         restartEmailServer();
         log("Setting up for a Results Publish test case");

         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0/1 * 1/1 * ? *");
         Thread.sleep(SCHEDULING_CHANGES_PICK_UP_TIME);
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
    
         // Wait for PA to pick up changes to scheduling
         log("Wait for PA to pick up changes to scheduling...");
         Thread.sleep(ON_OFF_SWITCH_RECHECK_WAIT_TIME);
         log("Wait over");
       
         waitForEmailsToArrive(1);
         
           
           //if job runs then make sure it updates trial publish date
           Timestamp publishDate = (Timestamp)getTrialPublishDate(testTrials.get(0).id);
           assertTrue(publishDate!=null);
           Calendar calendar = Calendar.getInstance();
           calendar.setTimeInMillis(publishDate.getTime());
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
           String publishDateString = simpleDateFormat.format(calendar.getTime());
           String currentDateString = simpleDateFormat.format(new Date());
           assertTrue(publishDateString.equals(currentDateString));
           
           
           
           
           Iterator emailIter = server.getReceivedEmail();
           SmtpMessage email = (SmtpMessage) emailIter.next();
           String subject = email.getHeaderValues("Subject")[0];
           String body = email.getBody().replaceAll("\\s+", " ")
                   .replaceAll(">\\s+", ">");
           
           assertTrue(subject.equalsIgnoreCase("CTRP Nightly Job -- Update Trial Results Published Date"));
           assertTrue(body.contains("This nightly job ran successfully. It has set the \"Trial Results Published Date\" to today's date"));
           assertTrue(body.contains(testTrials.get(0).nciID));
           
     }
     
     private Object getTrialPublishDate(long trialId) throws SQLException {
         
        
         
         QueryRunner runner = new QueryRunner();
         return (Object) runner
                 .query(connection,
                         "select trial_published_date from study_protocol where identifier="+trialId
                         ,new ArrayHandler())[0];
      
     }
     
    
     
     @Override
    public void tearDown() throws Exception {
         //reset schedule so that no more emails are sent
         setPaProperty("resultsUpdater.trials.scan.schedule", "0 0 0/23 1/1 * ? *");
        super.tearDown();
    }
     
   }
  
    
