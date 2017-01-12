/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author dkrylov
 * 
 */
public class UpdateAndAmendPerformanceTest extends AbstractRegistrySeleniumTest {

    /**
     * Tests logging in as abstractor.
     * 
     * @throws Exception
     *             on error
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testAmendPerformanceOnTrialsWithManySites() throws Exception {
        System.out.println(System.getProperties());
        if (!"true".equalsIgnoreCase(System.getProperty("server.readonly"))) {
            amendAndCheckPerformance("NCI-2011-01123");

            updateAndCheckPerformance("NCI-2011-02050");
            amendAndCheckPerformance("NCI-2011-02050");

            amendAndCheckPerformance("NCI-2009-00702");
        }
    }

    @SuppressWarnings("deprecation")
    private void updateAndCheckPerformance(String nciID)
            throws URISyntaxException, SQLException {
        prepareAndFindTrial(nciID);
        selectAction("Update");

        s.type("otherIdentifierOrg", Math.random() + "");
        s.click("otherIdbtnid");
        s.type("trialDTO_completionDate", "12/31/2019");
        s.click("trialDTO_completionDateTypeAnticipated");
        populateDocuments();

        clickAndWait("xpath=//button[text()='Review Trial']");
        handleOpenSitesDialogIfAppears();
        waitForElementById("updateTrialreviewUpdate", 60);

        String op = "Update";
        submitAndTimeOperation(nciID, op);

    }

    private void acceptUpdates(String nciID) throws SQLException {
        new QueryRunner().update(connection,
                "update study_inbox set close_date=now() where study_protocol_identifier="
                        + getTrialIdByNciId(nciID));
    }

    /**
     * @param nciID
     * @throws URISyntaxException
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    private void amendAndCheckPerformance(final String nciID)
            throws URISyntaxException, SQLException {
        prepareAndFindTrial(nciID);
        selectAction("Amend");

        populateAmendmentNumber();
        try {
            deleteStatus(3);
        } catch (Exception e1) {
            System.out.println("Status history is all set.");
        }
        
        s.type("trialDTO_primaryCompletionDate", "12/31/2019");
        s.click("trialDTO_primaryCompletionDateTypeAnticipated");
        
        populateDocuments();
        clickAndWait("xpath=//button[text()='Review Trial']");

        handleOpenSitesDialogIfAppears();

        waitForElementById("reviewTrialForm", 30);

        String op = "Amendment";
        submitAndTimeOperation(nciID, op);
    }

    /**
     * @param nciID
     * @param op
     */
    @SuppressWarnings("deprecation")
    private void submitAndTimeOperation(final String nciID, String op) {
        final Date start = new Date();
        System.out.println("Timestamp prior to submitting an " + op + " of "
                + nciID + ": " + start);
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        selenium.waitForPageToLoad(toMillisecondsString(60 * 10));
        assertTrue(selenium
                .isTextPresent("The amendment to trial with the NCI Identifier "
                        + nciID + " was successfully submitted.")
                || selenium
                        .isTextPresent("The trial update with the NCI Identifier "
                                + nciID + " was successfully submitted."));
        final Date end = new Date();
        System.out.println("Timestamp after submitting an " + op + " of "
                + nciID + ": " + end);

        long diff = end.getTime() - start.getTime();
        final String durationString = "Duration: " + (diff / 1000L)
                + " seconds or " + (diff / 1000D / 60D) + " minutes.";
        System.out.println(durationString);

        // We don't need assert the duration was <= 5 minutes, I think. We have
        // ensured the operation has succeeded by
        // checking the user message (see above). 5 minute timeout is hard-coded
        // in JBoss, so if update/amendment fails
        // because of that, the above check will fail.
        /*
         * assertTrue( "" + op + " operation for " + nciID +
         * " did not complete within 5 minutes, which is the timeout hard-coded in JBoss. "
         * + durationString, diff <= 1000 * 60 * 5L);
         */
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void handleOpenSitesDialogIfAppears() {
        try {
            waitForElementToBecomeVisible(By.id("dialog-opensites"), 15);
            s.click("//div[@aria-labelledby='ui-dialog-title-dialog-opensites']//span[text()='Proceed']");
        } catch (Exception e) {
            System.out.println("dialog-opensites did not show up.");
        }
    }

    /**
     * @param nciID
     * @throws SQLException
     */
    private void prepareAndFindTrial(final String nciID) throws SQLException {
        assignOwnerSafely(nciID);
        acceptUpdates(nciID);
        logoutUser();
        loginAsSubmitter();
        handleDisclaimer(true);
        searchForTrialByNciID(nciID);
    }

    /**
     * @param nciID
     */
    private void assignOwnerSafely(final String nciID) {
        try {
            assignTrialOwner("submitter-ci", getTrialIdByNciId(nciID)
                    .longValue());
        } catch (Exception e1) {
            System.out.println("submitter-ci already owns " + nciID);
        }
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    protected void populateAmendmentNumber() {
        s.type("trialDTO.localAmendmentNumber", "1");
        s.type("trialDTO.amendmentDate", today);
    }

    /**
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
    protected void populateDocuments() throws URISyntaxException {
        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        if (s.isElementPresent("protocolHighlightDocument"))
            selenium.type("protocolHighlightDocument", protocolDocPath);
    }
}
