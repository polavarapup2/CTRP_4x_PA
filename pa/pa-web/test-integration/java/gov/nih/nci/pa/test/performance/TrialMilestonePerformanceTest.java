/**
 * 
 */
package gov.nih.nci.pa.test.performance;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author dkrylov
 * 
 */
public class TrialMilestonePerformanceTest extends AbstractPaSeleniumTest {

    private final File report = new File(
            "trial_milestone_performance_report.txt");
    {
        try {
            report.createNewFile();
            report(new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTSRSentMileston() {
        loginAsSuperAbstractor();
        checkAddMilestonePerformance("Trial Summary Report Date", 130);
    }

    private void checkAddMilestonePerformance(String milestone,
            int timeoutSecond) {
        double totalTime = 0;
        int tries = 5;

        for (int i = 0; i < tries; i++) {
            totalTime += addMilestone(milestone);
        }

        final double average = totalTime / tries;
        report(("Average time to add " + milestone + " is " + average + " seconds.")
                .toUpperCase());
        if (!"true".equalsIgnoreCase(System
                .getProperty("no.duration.assertions")))
            assertTrue("Average wait time " + average
                    + " exceeded the given timeout of " + timeoutSecond,
                    average <= timeoutSecond);
    }

    @SuppressWarnings("deprecation")
    private double addMilestone(String milestone) {

        driver.findElement(By.id("trialSearchMenuOption")).click();
        searchAndSelectTrial("A Phase III Randomized Trial of Adjuvant Chemotherapy with or without Bevacizumab for Patients with Completely Resected Stage IB (≥ 4 cm) - IIIA Non-small Cell Lung Cancer (NSCLC)");

        final WebElement dashboardLink = driver.findElement(By
                .linkText("Trial Milestones"));
        dashboardLink.click();
        s.select("milestone", milestone);
        final Date start = new Date();
        System.out.println("Timestamp prior to adding " + milestone + ": "
                + start);

        clickAndWait("addMilestoneBtn");

        final Date end = new Date();
        System.out.println("Timestamp after adding " + milestone + ": " + end);

        long diff = end.getTime() - start.getTime();
        final double diffSeconds = diff / 1000D;
        final String durationString = "Duration to add " + milestone + ": "
                + diffSeconds + " seconds or " + (diff / 1000D / 60D)
                + " minutes.";
        report(durationString);

        assertTrue(s.isTextPresent("Trial Summary Report Date"));
        assertTrue(s
                .isElementPresent("xpath=//input[@value='ctrpsubstractor']"));
        return diffSeconds;
    }

    private void report(String str) {
        System.out.println(str);
        try {
            FileUtils.writeStringToFile(report,
                    FileUtils.readFileToString(report)
                            + SystemUtils.LINE_SEPARATOR + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
