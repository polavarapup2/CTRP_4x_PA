/**
 * 
 */
package gov.nih.nci.registry.test.performance;

import gov.nih.nci.registry.test.integration.AbstractRegistrySeleniumTest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.collections.Closure;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author dkrylov
 * 
 */
public class TrialSearchPerformanceTest extends AbstractRegistrySeleniumTest {

    private final File report = new File("trial_search_performance_report.txt");
    {
        try {
            report.createNewFile();
            report(new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByParticipatingSite() throws Exception {
        loginAsSubmitter();
        handleDisclaimer(true);

        testSearchByParticipatingSitePerformance("Duke Cancer Institute", 20);
        testSearchByParticipatingSitePerformance(
                "Wake Forest University Health Sciences", 12);
        testSearchByParticipatingSitePerformance(
                "OHSU Knight Cancer Institute", 10);
        testSearchByParticipatingSitePerformance("Mayo Clinic Cancer Center", 9);
        testSearchByParticipatingSitePerformance("Mayo Clinic", 16);
        testSearchByParticipatingSitePerformance("Mayo Clinic in Arizona", 12);
        testSearchByParticipatingSitePerformance(
                "M D Anderson Cancer Center (MDA)", 33);
    }

    private void testSearchByParticipatingSitePerformance(final String orgName,
            int timeoutSecond) {
        searchByClosure(new Closure() {
            @SuppressWarnings("deprecation")
            @Override
            public void execute(Object o) {
                s.select("organizationType", "Participating Site");
                waitForElementToBecomeVisible(By.id("participatingSiteName"), 5);
                driver.findElement(By.id("participatingSiteName")).sendKeys(
                        orgName.replaceFirst(" \\(\\w+\\)$", ""));
                waitForElementToBecomeAvailable(
                        By.xpath("//ul[@role='listbox']/li/a[text()='"
                                + orgName + "']"), 60);
                s.click("//ul[@role='listbox']/li/a[text()='" + orgName + "']");
                pause(500);
            }
        }, "by site " + orgName, timeoutSecond);
    }

    private void searchByClosure(Closure c, String descr, int timeoutSecond) {
        double totalTime = 0;
        int tries = 5;

        // Initial search can take longer since Postgres needs to cache stuff
        // etc, so ignore it from consideration.
        runSearchWithTimeout(c, descr);

        for (int i = 0; i < tries; i++) {
            totalTime += runSearchWithTimeout(c, descr);
        }

        final double average = totalTime / tries;
        report(("Average time to search " + descr + " is " + average + " seconds.")
                .toUpperCase());
        if (!"true".equalsIgnoreCase(System
                .getProperty("no.duration.assertions")))
            assertTrue("Average wait time " + average
                    + " exceeded the given timeout of " + timeoutSecond,
                    average <= timeoutSecond);
    }

    @SuppressWarnings("deprecation")
    private double runSearchWithTimeout(Closure c, String descr) {
        accessTrialSearchScreen();

        c.execute(driver);

        selenium.click("runSearchBtn");

        final Date start = new Date();
        System.out.println("Timestamp prior to searching " + descr + ": "
                + start);

        driver.findElement(By.linkText("All Trials")).click();
        WebDriverWait waiting = new WebDriverWait(driver, 300);
        waiting.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//h2[text()='Clinical Trials Search Results']")));

        final Date end = new Date();
        System.out.println("Timestamp after searching " + descr + ": " + end);

        long diff = end.getTime() - start.getTime();
        final double diffSeconds = diff / 1000D;
        final String durationString = "Duration to search " + descr + ": "
                + diffSeconds + " seconds or " + (diff / 1000D / 60D)
                + " minutes.";
        report(durationString);

        assertTrue(s.isTextPresent("Showing 1 to"));
        assertTrue(s.isElementPresent("row"));

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
