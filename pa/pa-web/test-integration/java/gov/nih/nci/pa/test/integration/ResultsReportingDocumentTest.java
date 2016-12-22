
package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.dumbster.smtp.SmtpMessage;

@Batch(number = 1)
public class ResultsReportingDocumentTest extends AbstractPaSeleniumTest {
    private static final String TRIAL_DOCUMENT = "TrialDocument.doc";
    

    
    @Test
    public void testAddDocument() throws URISyntaxException, SQLException {
     
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        
        
        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        assertTrue(selenium.isTextPresent("No trial documents exist on the trial"));
      

              
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");

        clickAndWait("link=Save");

        assertTrue(selenium.isTextPresent("Document Type Code must be Entered"));
        assertTrue(selenium.isTextPresent("FileName must be Entered"));
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Before results");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Created"));
            assertTrue(selenium.isTextPresent("Before results"));
            
            long recordCount = getRecordCount(trial.id,DocumentTypeCode.BEFORE_RESULTS.getName(), false);
            
            assertTrue(recordCount>0);
            
        } 
    }
    
    
    @Test
    public void testEditDocument() throws URISyntaxException, SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            
            clickAndWait("link=Add");

            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Comparison");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
         
            clickAndWait("xpath=//table[@id='row']/tbody/tr[1]/td[7]/a");        
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Updated"));
           
            long recordCount = getRecordCount(trial.id,DocumentTypeCode.COMPARISON.getName(), false);
            
            assertTrue(recordCount>0);
         
        }
    }

    @Test
    public void testDeleteDocument() throws SQLException, URISyntaxException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            
            clickAndWait("link=Add");

            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Before results");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            WebElement element = driver.findElement(By.xpath("//table[@id='row']/tbody/tr[1]/td[7]//a[2]"));
            String id =element.getAttribute("id");
            if (id!=null){
                String[] tokens = id.split("_");
                if (tokens.length ==3) {
                    String scriptString = " document.getElementById('objectsToDelete').value="+tokens[1]+";";
                    ((JavascriptExecutor) driver).executeScript(scriptString);
                  }
                
            }
           
            ((JavascriptExecutor) driver).executeScript(" document.forms[0].action = 'resultsReportingDocumentdelete.action'; document.forms[0].submit();");
             waitForPageToLoad();
            
            assertTrue(selenium.isTextPresent("No trial documents exist on the trial"));
            
            long recordCount = getRecordCount(trial.id,DocumentTypeCode.BEFORE_RESULTS.getName(), true);
            
            assertTrue(recordCount==0);
        }
        
    }

    @Test
    public void testReviewCtro() throws SQLException, URISyntaxException {
        
        //needs to make sure real email address are upated to avoid email sent from test
        updateEmailAddress();
        
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        
        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            
            clickAndWait("link=Add");

            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Comparison");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            
            assertTrue(selenium.isTextPresent("Record Created"));
            selenium.isElementPresent("link=Yes");
            
            selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[4]/a");
            
            assertTrue(selenium.isTextPresent("CTRO Review Complete"));
            selenium.select("id=ctroUserId", "label=admin-ci");
            clickAndWait("id=ctroSave");
            
            WebElement webElement = driver.findElement(By.xpath("//table[@id='row']/tbody/tr[1]/td[4]"));
            String text = webElement.getText();
            assertTrue(text.contains("admin-ci"));
            
            long recordCount =getUsersRecordCount(trial.id, true);
            
            assertTrue(recordCount > 0);
            
        }
    }
    
    @Test
    public void testReviewCcct() throws SQLException, URISyntaxException {
        
        //needs to make sure real email address are upated to avoid email sent from test
        updateEmailAddress();
        
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        
        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            
            clickAndWait("link=Add");

            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Comparison");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            
            assertTrue(selenium.isTextPresent("Record Created"));
            selenium.isElementPresent("link=Yes");
            
            selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[5]/a");
            
            assertTrue(selenium.isTextPresent("CTRO Review Complete"));
            selenium.type("id=ccctUserName", "ctrpsubstractor");
            clickAndWait("id=ccctSave");
            
            WebElement webElement = driver.findElement(By.xpath("//table[@id='row']/tbody/tr[1]/td[5]"));
            String text = webElement.getText();
            assertTrue(text.contains("ctrpsubstractor"));
            
            long recordCount =getUsersRecordCount(trial.id, false);
            
            assertTrue(recordCount > 0);
            
            
        }
    }
    
    @Test
    public void testCtroNotificationEmail() throws SQLException, URISyntaxException, InterruptedException {
        
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        
        
        
        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        assertTrue(selenium.isTextPresent("No trial documents exist on the trial"));
      

              
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");

      
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Comparison");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Created"));
            assertTrue(selenium.isTextPresent("Comparison"));
            
            long recordCount = getRecordCount(trial.id,DocumentTypeCode.COMPARISON.getName(), false);
            
            assertTrue(recordCount>0);
            
            waitForEmailsToArrive(1);
            Iterator emailIter = server.getReceivedEmail();
            SmtpMessage email = (SmtpMessage) emailIter.next();
            String subject = email.getHeaderValues("Subject")[0];
            String body = email.getBody().replaceAll("\\s+", " ")
                    .replaceAll(">\\s+", ">");
            
          
            
            assertEquals(
                    "Results Reporting & Tracking: "+trial.nciID+" Trial Comparison",
                    subject);
            
            assertTrue(body.contains("Can you review the attached comparison document for "+trial.nciID+"? Please let me know if you have any questions or when updates have been completed"));
          
            
        }
        
        
    }
      
    @Test
    public void testCcctNotificationEmail() throws SQLException, URISyntaxException, InterruptedException {
        
        //needs to make sure real email address are upated to avoid email sent from test
        updateEmailAddress();
        
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        
        openAndWait("/pa/protected/resultsReportingDocumentquery.action?studyProtocolId="+trial.id);
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            
            clickAndWait("link=Add");

            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Comparison");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            
            assertTrue(selenium.isTextPresent("Record Created"));
            selenium.isElementPresent("link=Yes");
            
            selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[4]/a");
            
            assertTrue(selenium.isTextPresent("CTRO Review Complete"));
            selenium.select("id=ctroUserId", "label=admin-ci");
            clickAndWait("id=ctroSave");
            
            WebElement webElement = driver.findElement(By.xpath("//table[@id='row']/tbody/tr[1]/td[4]"));
            String text = webElement.getText();
            assertTrue(text.contains("admin-ci"));
            
            long recordCount =getUsersRecordCount(trial.id, true);
            
            assertTrue(recordCount > 0);
            
            
            waitForEmailsToArrive(2);
            Iterator emailIter = server.getReceivedEmail();
            if(emailIter.hasNext()) {
                //skip the first email
                emailIter.next();
                
            }
            //read second email
            if(emailIter.hasNext()) {
            SmtpMessage email = (SmtpMessage) emailIter.next();
            String subject = email.getHeaderValues("Subject")[0];
            String body = email.getBody().replaceAll("\\s+", " ")
           .replaceAll(">\\s+", ">");
            
            assertEquals(
                    "Results Reporting & Tracking: "+trial.nciID+" Trial Comparison",
                    subject);
            
            assertTrue(body.contains("Attached is the comparison document for "+trial.nciID+" for your review. A Cover Sheet is being sent in a separate email. Please let me know if you have any questions or when your review/updates have been completed."));
            
            }
            
            
            
            
        }
    }
   
    private void updateEmailAddress()
         throws SQLException {
        String sql;
        sql = "update pa_properties set value='sample@example.com' where name ='abstraction.script.mailTo'"; 
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);
        
        runner = new QueryRunner();
        sql = "update pa_properties set value='sample@example.com' where name ='ccct.comparision.email.tolist'"; 
        runner.update(connection, sql);
        
    }
    
    private long getRecordCount(long trialId, String typeCode , boolean isDelete)
            throws SQLException {
        String sql;
        if (isDelete) {
            sql = "select count(*) from document where study_protocol_identifier ="+trialId+" and type_code='"+typeCode+"' and active_indicator = true";
        }
        else {
            sql = "select count(*) from document where study_protocol_identifier ="+trialId+" and type_code='"+typeCode+"'";     
        }
       
        long trialCount =0;
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
             trialCount = (long) row[0];
           
        }
        return trialCount;
    }
    
    private long getUsersRecordCount(long trialId, boolean isCtro)
            throws SQLException {
        String sql;
        if (isCtro) {
            sql = "select count(*) from document where study_protocol_identifier ="+trialId+" and ctro_user_id is not null";
        }
        else {
            sql = "select count(*) from document where study_protocol_identifier ="+trialId+" and ccct_user_name  is not null";   
        }
       
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
