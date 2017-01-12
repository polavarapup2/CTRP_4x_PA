/**
 * 
 */
package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.openqa.selenium.By;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractTrialStatusTest extends AbstractPaSeleniumTest {
    protected String tomorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    protected String today = MONTH_DAY_YEAR_FMT.format(new Date());
    protected String yesterday = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), -1));

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @param useDashboard
     * @param trial
     */
    protected void selectTrial(boolean useDashboard, TrialInfo trial) {
        if (!useDashboard) {
            openAndWait("/pa/protected/studyProtocolexecute.action");
            searchAndSelectTrial(trial.title);
        } else {
            selectTrialInDashboard(trial);
        }
    }

    /**
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void selectTrialInDashboard(TrialInfo trial) {
        openAndWait("/pa/protected/dashboard.action");
        if (selenium.isElementPresent("id=searchid")) {
            clickAndWait("searchid");
            selenium.type("submittedOnOrAfter", "01/01/2000");
            clickAndWait("xpath=//div[@class='actionsrow']//a//span[text()='Search']");
            clickAndWait("//table[@id='results']//a[normalize-space(text())='"
                    + trial.nciID.replaceFirst("NCI-", "") + "']");
        } else
            clickAndWait("link=" + trial.nciID.replaceFirst("NCI-", ""));
    }

    @SuppressWarnings("deprecation")
    protected void insertStatus(String newCode, String newDate, String reason,
            String comment) {
        selenium.click("xpath=//div[@class='actionsrow']//span[normalize-space(text())='Add New Status']");
        waitForElementToBecomeVisible(By.id("edit-dialog"), 2);
        assertEquals("Add Trial Status", selenium.getText("edit-dialog-header"));
        selenium.select("id=statusCode", "label=" + newCode);
        selenium.type("statusDate", newDate);
        selenium.type("reason", reason);
        selenium.type("comment", comment);
        selenium.click("xpath=//div[@id='edit-dialog']//input[@value='Save']");
        waitForPageToLoad();

    }

    /**
     * @return
     * @throws SQLException
     */
    protected TrialInfo createTrialAndAccessStatusPage() throws SQLException {
        return createTrialAndAccessStatusPage("admin-ci");
    }

    protected TrialInfo createTrialAndAccessStatusPage(String username)
            throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        login(username, "pass");
        disclaimer(true);
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        if (selenium.isElementPresent("link=Scientific Check Out")) {
            checkOutTrialAsScientificAbstractor();
        }
        clickAndWait("link=Trial Status");

        // Verify History button
        openHistory();
        return trial;
    }

    /**
     * 
     */
    protected void openHistory() {
        selenium.click("xpath=//span[normalize-space(text())='History']");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("row_info", 15);
    }

}
