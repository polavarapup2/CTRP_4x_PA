package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Batch(number = 1)
public class RegulatoryInformationTest extends AbstractPaSeleniumTest {
     @Test
     public void testRegulatoryInfo() throws Exception {
         TrialInfo trial = createSubmittedTrial();
         loginAsAdminAbstractor();
         searchSelectAndAcceptTrial(trial.title, true, false);
         clickAndWait("link=Regulatory Information");
         assertTrue(selenium.isElementPresent("link=Save"));
         assertTrue(selenium.isElementPresent("link=Cancel"));
         assertTrue(selenium.isElementPresent("id=fdaindid"));
         assertTrue(selenium.isElementPresent("id=datamonid"));
         clickAndWait("link=Cancel");
         
     }
}
