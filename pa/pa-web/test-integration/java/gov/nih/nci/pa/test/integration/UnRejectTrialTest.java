package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;

import org.junit.Test;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.test.integration.support.Batch;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Batch(number = 1)
public class UnRejectTrialTest extends AbstractPaSeleniumTest {
    
     @Test
     public void testUnrejectTrial() throws SQLException {
         loginAsSuperAbstractor();
         TrialInfo info = createSubmittedTrial();
         addDWS(info, "REJECTED");
         addMilestone(info, "SUBMISSION_REJECTED");
         logoutUser();
         selectTrialInPA(info);
         assertTrue(selenium.isElementPresent("link=Trial Milestones"));
         clickAndWait("link=Trial Milestones");
         assertTrue(selenium.isElementPresent("link=Un-Reject Trial"));
         clickAndWait("link=Un-Reject Trial");
         assertTrue(selenium.isElementPresent("id=comment-dialog"));
         selenium.type("id=comments", "testCode");
         clickAndWait("id=save");
         assertTrue(selenium.isTextPresent("Message. " + info.nciID +" has been restored to previously active version.."));
         assertTrue(selenium.getText("xpath=//table[@id='table-1']//tr[2]//td[3]").contains("testCode"));
         assertFalse(selenium.isElementPresent("link=Un-Reject Trial"));
         assertFalse(selenium.getText("xpath=//table[@id='table-1']//tr[2]//td[1]").contains("Submission Rejection Date"));
         String status = getCurrentDWS(info);
         assertEquals("SUBMITTED", status);
     }
     
     @Test
     public void testemptyComment() throws SQLException {
         loginAsSuperAbstractor();
         TrialInfo info = createSubmittedTrial();
         addDWS(info, "REJECTED");
         addMilestone(info, "SUBMISSION_REJECTED");
         logoutUser();
         selectTrialInPA(info);
         assertTrue(selenium.isElementPresent("link=Trial Milestones"));
         clickAndWait("link=Trial Milestones");
         assertTrue(selenium.isElementPresent("link=Un-Reject Trial"));
         clickAndWait("link=Un-Reject Trial");
         assertTrue(selenium.isElementPresent("id=comment-dialog"));
         selenium.type("id=comments", "    ");
         clickAndWait("id=save");
         assertTrue(selenium.isElementPresent("link=Un-Reject Trial"));
         clickAndWait("link=Un-Reject Trial");
         selenium.type("id=comments", "");
         assertTrue(selenium.isElementPresent("link=Un-Reject Trial"));
         String status = getCurrentDWS(info);
         assertEquals("REJECTED", status);
     }
     
     @Test
     public void testAdminScientificUnrejectTrial() throws SQLException {
         loginAsAdminAbstractor();
         TrialInfo info = createSubmittedTrial();
         addDWS(info, "REJECTED");
         addMilestone(info, "SUBMISSION_REJECTED");
         searchAndSelectTrial(info.title);
         assertTrue(selenium.isElementPresent("link=Trial Milestones"));
         clickAndWait("link=Trial Milestones");
         assertFalse(selenium.isElementPresent("link=Un-Reject Trial"));
         logoutUser();
         loginAsScientificAbstractor();
         searchAndSelectTrial(info.title);
         assertTrue(selenium.isElementPresent("link=Trial Milestones"));
         clickAndWait("link=Trial Milestones");
         assertFalse(selenium.isElementPresent("link=Un-Reject Trial"));
     }
     
     @Test
     public void testAcceptUnrejectTrial() throws SQLException {
         loginAsSuperAbstractor();
         TrialInfo info = createAcceptedTrial();
         addDWS(info, "REJECTED");
         addMilestone(info, "LATE_REJECTION_DATE");
         logoutUser();
         selectTrialInPA(info);
         assertTrue(selenium.isElementPresent("link=Trial Milestones"));
         clickAndWait("link=Trial Milestones");
         assertTrue(selenium.isElementPresent("link=Un-Reject Trial"));
         clickAndWait("link=Un-Reject Trial");
         assertTrue(selenium.isElementPresent("id=comment-dialog"));
         selenium.type("id=comments", "testCode");
         clickAndWait("id=save");
         assertTrue(selenium.isTextPresent("Message. " + info.nciID +" has been restored to previously active version.."));
         assertTrue(selenium.getText("xpath=//table[@id='table-1']//tr[3]//td[3]").contains("testCode"));
         assertFalse(selenium.isElementPresent("link=Un-Reject Trial"));
         assertFalse(selenium.getText("xpath=//table[@id='table-1']//tr[3]//td[1]").contains("Late Rejection Date"));
         String status = getCurrentDWS(info);
         assertEquals("ACCEPTED", status);
     }
    
}
