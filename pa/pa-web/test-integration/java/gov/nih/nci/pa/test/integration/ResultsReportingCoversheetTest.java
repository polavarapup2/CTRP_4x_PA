package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.dumbster.smtp.SmtpMessage;


@Batch(number = 1)
public class ResultsReportingCoversheetTest  extends AbstractPaSeleniumTest {
    
    protected static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 15000
            : 2000;
    String baseUrl=null;

  public ResultsReportingCoversheetTest() {
      baseUrl ="/pa/protected/resultsReportingCoverSheetquery.action?studyProtocolId=";
  }
   

   
    
    @SuppressWarnings("deprecation")
    @Test
    public void testAddStudyChange() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id);
        assertTrue(count==0);
        
        assertTrue(selenium.isTextPresent("No study record changes found."));
        
        
        
        assertTrue(selenium.isElementPresent("id=addStudyRecord"));
        selenium.click("id=addStudyRecord");
        
        pause(1000);
        waitForElementById("changeType", 5);
        
        selenium.type("changeType", "changeType");
        selenium.type("actionTakenChangeType", "actionTakenChangeType");
        selenium.type("actionCompletionDateChangeType", today);
        pause(1000);
        
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
        pause(OP_WAIT_TIME);
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
        count = getRecordCount(trial.id);
        assertTrue(count>0);
    }
    
   
   @SuppressWarnings("deprecation")
   @Test
   public void testEditStudyChange() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       long count = getRecordCount(trial.id);
       assertTrue(count==0);
       
       assertTrue(selenium.isTextPresent("No study record changes found."));
       
       assertTrue(selenium.isElementPresent("id=addStudyRecord"));
       selenium.click("id=addStudyRecord");
       
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType");
       selenium.type("actionTakenChangeType", "actionTakenChangeType");
       selenium.type("actionCompletionDateChangeType", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
       
       selenium.click("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[4]/div/img[1]");
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType123");
       selenium.type("actionTakenChangeType", "actionTakenChangeType123");
       selenium.type("actionCompletionDateChangeType", today);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       
       assertEquals(
               "changeType123",
               selenium.getText("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[1]"));

       assertEquals(
               "actionTakenChangeType123",
               selenium.getText("xpath=//table[@id='recordChanges']/tbody/tr[1]/td[2]"));
       
       count = getRecordCount(trial.id);
       assertTrue(count>0);
   }
   
  
   
   @SuppressWarnings("deprecation")
   @Test
   public void testDeleteStudyChange() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       assertTrue(selenium.isTextPresent("No study record changes found."));
       
       assertTrue(selenium.isElementPresent("id=addStudyRecord"));
       selenium.click("id=addStudyRecord");
       
       pause(1000);
       waitForElementById("changeType", 5);
       
       selenium.type("changeType", "changeType");
       selenium.type("actionTakenChangeType", "actionTakenChangeType");
       selenium.type("actionCompletionDateChangeType", today);
       pause(1000);
       
       selenium.click("xpath=//button/span[normalize-space(text())='Save']");
       pause(OP_WAIT_TIME);
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Record Change has been added successfully."));
       
       WebElement element = driver.findElement(By.xpath("//table[@id='recordChanges']/tbody/tr[1]/td[5]"));
       String elementValue =(String)((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", element);
       String url ="resultsReportingCoverSheetdelete.action?objectsToDelete="+elementValue;
       ((JavascriptExecutor) driver).executeScript("jQuery('#deleteType').val('studyrecord'); jQuery('#coverSheetForm')[0].action = '"+url+"'; jQuery('#coverSheetForm').submit();");
       waitForPageToLoad();
       
       assertTrue(selenium.isTextPresent("No study record changes found."));
       assertTrue(selenium.isTextPresent("Record(s) Deleted."));
       
       long count = getRecordCount(trial.id);
       assertTrue(count==0);
   
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSaveFinalRecord() throws SQLException, ParseException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       selenium.select("useStandardLanguage", "Yes");
       waitForElementToBecomeInvisible(By.id("useStandardLanguage_flash"), 6);
       WebElement element = driver.findElement(By.id("useStandardLanguage"));
       //if above condition is successful that means indeed save is sucessfully just assert value of dropdown
       String selectedOption = new Select(element).getFirstSelectedOption().getText();
       assertEquals("Yes", selectedOption);
       
       selenium.select("dateEnteredInPrs", "Yes");
       waitForElementToBecomeInvisible(By.id("dateEnteredInPrs_flash"), 6);
       //if above condition is successful that means indeed save is sucessfully just assert value of dropdown
       element = driver.findElement(By.id("dateEnteredInPrs"));
       selectedOption = new Select(element).getFirstSelectedOption().getText();
       assertEquals("Yes", selectedOption);
       
       selenium.select("designeeAccessRevoked", "Yes");
       waitForElementToBecomeInvisible(By.id("designeeAccessRevoked_flash"), 6);
       //if above condition is successful that means indeed save is sucessfully just assert value of dropdown
       element = driver.findElement(By.id("designeeAccessRevoked"));
       selectedOption = new Select(element).getFirstSelectedOption().getText();
       assertEquals("Yes", selectedOption);
       
       
       selenium.type("designeeAccessRevokedDate", today);
       waitForElementToBecomeInvisible(By.id("designeeAccessRevokedDate_flash"), 6);
       //if above condition is successful that means indeed save is sucessfully just assert value of dropdown
       element = driver.findElement(By.id("designeeAccessRevokedDate"));
       
     
       selenium.select("changesInCtrpCtGov", "Yes");
       waitForElementToBecomeInvisible(By.id("changesInCtrpCtGov_flash"), 6);
       //if above condition is successful that means indeed save is sucessfully just assert value of dropdown
       element = driver.findElement(By.id("changesInCtrpCtGov"));
       selectedOption = new Select(element).getFirstSelectedOption().getText();
       assertEquals("Yes", selectedOption);
       
   
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testCoverSheetEmail() throws SQLException, ParseException, InterruptedException {
       TrialInfo trial = createAcceptedTrial();
       loginAsSuperAbstractor();
       searchAndSelectTrial(trial.title);
       openAndWait(baseUrl+trial.id);
       
       selenium.select("useStandardLanguage", "Yes");
       selenium.select("dateEnteredInPrs", "Yes");
       selenium.select("designeeAccessRevoked", "Yes");
       selenium.type("designeeAccessRevokedDate", today);
       selenium.select("changesInCtrpCtGov", "Yes");
       selenium.type("changesInCtrpCtGovDate", today);
       selenium.select("sendToCtGovUpdated", "Yes");
       
      
       
       ((JavascriptExecutor) driver).executeScript("jQuery('#coverSheetForm')[0].action ='resultsReportingCoverSheetsendConverSheetEmail.action'; jQuery('#coverSheetForm').submit();");
       waitForPageToLoad();
       assertTrue(selenium.isTextPresent("Email Sent Successfully."));
       
       waitForEmailsToArrive(1);
       Iterator emailIter = server.getReceivedEmail();
       SmtpMessage email = (SmtpMessage) emailIter.next();
       String subject = email.getHeaderValues("Subject")[0];
       String body = email.getBody().replaceAll("\\s+", " ")
               .replaceAll(">\\s+", ">");
       
       assertTrue(subject.equals("Trials Result Coversheet : "+trial.nciID));
       assertTrue(body.contains("<td>Results Designee Access Revoked?</td><td>YES "+today));
   }
   
   private long getRecordCount(long trialId)
           throws SQLException {
       String sql;
       
           sql = "select count(*) from study_record_change where study_protocol_identifier ="+trialId;
      
       long trialCount =0;
       QueryRunner runner = new QueryRunner();
       final List<Object[]> results = runner.query(connection, sql,
               new ArrayListHandler());
       for (Object[] row : results) {
            trialCount = (long) row[0];
          
       }
       return trialCount;
   }
   
}
