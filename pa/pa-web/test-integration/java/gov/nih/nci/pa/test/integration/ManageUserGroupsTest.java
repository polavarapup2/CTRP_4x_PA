package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

@Batch(number = 1)
public class ManageUserGroupsTest extends AbstractPaSeleniumTest {

    private static final int WAIT_TIME = 10;

    @SuppressWarnings("deprecation")
    @Test
    public void testSecurityAdmin() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        Number userID = createCSMUser(loginName);
        assignUserToGroup(userID, "SecurityAdmin");
        
        loginPA(loginName, "pass");
        clickAndWait("id=acceptDisclaimer");

        verifyAbstractorMenuNotAvailable();
        s.click("link=Manage User Groups");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        verifyManageUserGroups(loginName, userID);
    }

    @Test
    public void testUserWithNoRolesCantDoAnything() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        createCSMUser(loginName);
        attemptLogin("/pa", loginName, "pass");        
        verifyAbstractorMenuNotAvailable();
        assertTrue(s
                .isTextPresent("Invalid username and/or password, please try again."));
    }

    @Test
    public void testSuAbstractorsCantAccessSecurityScreens() throws Exception {
        String loginName = "security_" + System.currentTimeMillis();
        Number userID = createCSMUser(loginName);
        assignUserToGroup(userID, "SuAbstractor");
        assignUserToGroup(userID, "gridClient");
        login(loginName, "pass");
        verifySecurityAdminMenuNotAvailable();

        // Must be unable to access the screen directly.
        openAndWait("/po-web/security/manage/groups/execute.action");
        assertFalse(s.isTextPresent("Manage User Groups"));
        assertFalse(s.isElementPresent("users"));

    }

    private void verifySecurityAdminMenuNotAvailable() {
        assertFalse(selenium.isElementPresent("link=Manage User Groups"));

    }

    private void verifyManageUserGroups(String loginName, Number userID)
            throws SQLException {
        s.select("//select[@name='users_length']", "100");
        driver.findElement(By.xpath("//input[@aria-controls='users']"))
                .sendKeys(loginName);
        assertTrue(s.isTextPresent("Showing 1 to 1 of 1 entries"));
        assertTrue(s.isElementPresent("//tr/td[text()='" + loginName + "']"));
        assertOptionSelected("SecurityAdmin");
        assertOptionNotSelected("SuAbstractor");
        assertOptionNotSelected("gridClient");

        // Remove the only security group.
        useSelect2ToUnselectOption("SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "SuAbstractor"));
        assertFalse(isUserInGroup(userID, "gridClient"));

        // Now start adding back.
        useSelect2ToPickAnOption("groups_of_user_" + userID, "grid",
                "gridClient");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "SuAbstractor"));
        assertTrue(isUserInGroup(userID, "gridClient"));

        useSelect2ToPickAnOption("groups_of_user_" + userID, "SuA", "SuAbstractor");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertTrue(isUserInGroup(userID, "SuAbstractor"));
        assertTrue(isUserInGroup(userID, "gridClient"));

        useSelect2ToPickAnOption("groups_of_user_" + userID, "Secu",
                "SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertTrue(isUserInGroup(userID, "SecurityAdmin"));
        assertTrue(isUserInGroup(userID, "SuAbstractor"));
        assertTrue(isUserInGroup(userID, "gridClient"));
        
        useSelect2ToPickAnOption("groups_of_user_" + userID, "Prog",
                "ProgramCodeAdministrator");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertTrue(isUserInGroup(userID, "SecurityAdmin"));
        assertTrue(isUserInGroup(userID, "SuAbstractor"));
        assertTrue(isUserInGroup(userID, "gridClient"));
        assertTrue(isUserInGroup(userID, "ProgramCodeAdministrator"));

        // Now remove all groups again
        useSelect2ToUnselectOption("SecurityAdmin");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        useSelect2ToUnselectOption("SuAbstractor");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        useSelect2ToUnselectOption("gridClient");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        useSelect2ToUnselectOption("ProgramCodeAdministrator");
        waitForElementToBecomeInvisible(By.id("progress_indicator_panel"),
                WAIT_TIME);
        assertFalse(isUserInGroup(userID, "SecurityAdmin"));
        assertFalse(isUserInGroup(userID, "SuAbstractor"));
        assertFalse(isUserInGroup(userID, "gridClient"));
        assertFalse(isUserInGroup(userID, "ProgramCodeAdministrator"));

    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-" + id + "-results"));
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        try {
            xpath = By.xpath("//li[@role='treeitem' and text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 3);
        } catch (TimeoutException e) {
            xpath = By.xpath("//li[@role='treeitem']//b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 15);
        }

        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));

    }

    private void verifyAbstractorMenuNotAvailable() {
        assertFalse(selenium.isElementPresent("dashboardMenuOption"));
        assertFalse(selenium.isElementPresent("registeredUserDetailsMenuOption"));
        assertFalse(selenium.isElementPresent("inboxProcessingMenuOption"));
        assertFalse(selenium.isElementPresent("manageSiteAdminsMenuOption"));
        assertFalse(selenium.isElementPresent("newMarkerRequestMenuOption"));
        assertFalse(selenium.isElementPresent("importCtGovMenuOption"));
        assertFalse(selenium.isElementPresent("ctGovImportLogMenuOption"));
        assertFalse(selenium.isElementPresent("pendingAccrualsMenuOption"));
        assertFalse(selenium.isElementPresent("outOfScopeAccrualsMenuOption"));
        assertFalse(selenium.isElementPresent("submitProprietaryTrialMenuOption"));

    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    public void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param option
     * @return
     */
    public String getXPathForSelectedOption(String option) {
        return "//li[@class='select2-selection__choice' and @title='" + option
                + "']";
    }

    @SuppressWarnings("deprecation")
    public void assertOptionNotSelected(String option) {
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));
    }

}
