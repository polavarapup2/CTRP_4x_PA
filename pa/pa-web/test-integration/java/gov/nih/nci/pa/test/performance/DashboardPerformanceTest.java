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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author dkrylov
 * 
 */
public class DashboardPerformanceTest extends AbstractPaSeleniumTest {

    private final File report = new File("dashboard_performance_report.txt");
    {
        try {
            report.createNewFile();
            report(new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdmin() {
        loginAsAdminAbstractor();
        checkDashboardPerformance("Admin Dashboard", 11);
    }

    @Test
    public void testScientific() {
        loginAsScientificAbstractor();
        checkDashboardPerformance("Scientific Dashboard", 11);
    }

    private void checkDashboardPerformance(String descr, int timeoutSecond) {
        double totalTime = 0;
        int tries = 3;

        for (int i = 0; i < tries; i++) {
            totalTime += dashboard(descr);
        }

        final double average = totalTime / tries;
        report(("Average time to bring up " + descr + " is " + average + " seconds.")
                .toUpperCase());
        if (!"true".equalsIgnoreCase(System
                .getProperty("no.duration.assertions")))
            assertTrue("Average wait time " + average
                    + " exceeded the given timeout of " + timeoutSecond,
                    average <= timeoutSecond);
    }

    @SuppressWarnings("deprecation")
    private double dashboard(String descr) {

        driver.findElement(By.id("trialSearchMenuOption")).click();
        final WebElement dashboardLink = driver.findElement(By
                .id("dashboardMenuOption"));
        final Date start = new Date();
        System.out
                .println("Timestamp prior to calling " + descr + ": " + start);

        dashboardLink.click();

        WebDriverWait waiting = new WebDriverWait(driver, 300);
        waiting.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//a[text()='Workload']")));

        final Date end = new Date();
        System.out.println("Timestamp after calling " + descr + ": " + end);

        long diff = end.getTime() - start.getTime();
        final double diffSeconds = diff / 1000D;
        final String durationString = "Duration to invoke " + descr + ": "
                + diffSeconds + " seconds or " + (diff / 1000D / 60D)
                + " minutes.";
        report(durationString);

        assertTrue(s.isTextPresent("trials found, displaying all trials"));
        assertTrue(s.isElementPresent("wl"));

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
