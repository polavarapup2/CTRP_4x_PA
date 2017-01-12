
package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

/*
@Batch(number = 2)
public class ParticipatingSiteCloseTest extends AbstractPaSeleniumTest {
   
    /**
     * Creates participating sites.
     * 
     * @throws SQLException
     */
    /*@Test
    public void testIfSiteNotClosedIfTrialStatusDateIsNotLater() throws Exception {
        TrialInfo info = createAndSelectTrial();
        String today = MONTH_DAY_YEAR_FMT.format(new Date());
      
       
         addSiteToTrialWithNameAndDate(
                 info, "National Cancer Institute Division of Cancer Prevention", "In Review",today, false);
       
        clickAndWait("link=Participating Sites");
        assertTrue(selenium.isTextPresent("In Review"));
        
        clickAndWait("link=Trial Status");
        
        changeStatus("Closed to Accrual", today);
        
        s.click("//button/span[@class='ui-button-text' and text()='Cancel']");
        clickAndWait("link=Participating Sites");
        
        //check now that participating site status should not changed
        assertTrue(selenium.isTextPresent("In Review"));
        
        //check if site not closed email is arrived
        waitForEmailsToArrive(1);
        
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");
        
        assertEquals(
                "CTRP was unable to close some participating sites for trial",
                subject);
        
        assertTrue(body.contains("When this trial was closed, the CTRP application was unable to automatically add a closure status to the following participating site(s)"));
        
        //check if nci id is present
        assertTrue(body.contains(info.nciID));
        
        //check if trial status is present
        assertTrue(body.contains("Closed to Accrual"));
        
        //check if site name is present
        assertTrue(body.contains("National Cancer Institute Division of Cancer Prevention"));
        
        //check if site current status is present 
        assertTrue(body.contains("In Review"));
        
        //check if site status date is correct
        assertTrue(body.contains(today));
        
       

    }
    
    *//**
     * Creates participating sites.
     * 
     * @throws SQLException
     *//*
    @Test
    public void testIfSiteNotClosedIfSiteHasClosureInHistory() throws Exception {
        TrialInfo info = createAndSelectTrial();
        String today = MONTH_DAY_YEAR_FMT.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -3);
        String earlierDate = MONTH_DAY_YEAR_FMT.format(calendar.getTime());
        
        addSiteToTrialWithNameAndDate(
                info, "National Cancer Institute Division of Cancer Prevention", "In Review",earlierDate, false);
        
        //add closure status in history
        
        clickAndWait("link=Participating Sites");
        
        clickAndWait("xpath=//table[@id='row']/tbody/tr[1]/td[7]/a"); 
      
        
        clickAndWaitAjax("link=History");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        
        clickAndWait("link=Add New Status");
        selenium.select("statusCode", "Closed to Accrual");
        
        calendar.add(Calendar.DATE, -4);
        earlierDate = MONTH_DAY_YEAR_FMT.format(calendar.getTime());
        selenium.type("statusDate",earlierDate);
        
        clickAndWait("id=saveStatusHistoryPopUp");
        
        assertTrue(selenium.isTextPresent("Participating site status record has been added"));
        
        clickAndWait("link=Cancel");
        
        driver.switchTo().defaultContent();
        
        clickAndWait("link=Trial Status");
        
        
        changeStatus("Closed to Accrual", today);
        
        s.click("//button/span[@class='ui-button-text' and text()='Cancel']");
        clickAndWait("link=Participating Sites");
        
        //check now that participating site status should not changed
        assertTrue(selenium.isTextPresent("In Review"));
        
        //check if site not closed email is arrived
        waitForEmailsToArrive(1);
        
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");
        
        assertEquals(
                "CTRP was unable to close some participating sites for trial",
                subject);
        
        assertTrue(body.contains("When this trial was closed, the CTRP application was unable to automatically add a closure status to the following participating site(s)")); 
        
        //check if nci id is present
        assertTrue(body.contains(info.nciID));
        
        //check if trial status is present
        assertTrue(body.contains("Closed to Accrual"));
        
        //check if site name is present
        assertTrue(body.contains("National Cancer Institute Division of Cancer Prevention"));
        
        //check if site current status is present 
        assertTrue(body.contains("In Review"));
        
        //check if site status date is correct
        assertTrue(body.contains(today));
       

    }
    
    private void changeStatus(String statusName,String date ) {
        
       selenium.select("id=currentTrialStatus", "label=" + statusName);
       selenium.type("id=statusDate", date);
       
       clickAndWait("link=Save");
       assertTrue(selenium.isTextPresent("Record Updated"));
  }

   
}*/
