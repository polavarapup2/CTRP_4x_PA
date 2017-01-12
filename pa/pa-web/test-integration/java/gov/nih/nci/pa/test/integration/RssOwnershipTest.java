package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * 
 * @author dkrylov
 */
@Batch(number = 1)
public class RssOwnershipTest extends AbstractPaSeleniumTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testRssAssignedAsOwner() throws SQLException {
        String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        long userID = createCSMUser(loginName).longValue();
        long ruID = createRegistryUser(userID).longValue();
        setPaProperty("cteprss.user", loginName);

        createTrialChangeLeadOrgAndAccept();
        assertTrue(s.isTextPresent("Study Protocol Accepted."));
        clickAndWait("link=Assign Ownership");
        assertEquals("test" + ruID + " CI",
                s.getText("//table[@id='row']/tbody/tr[1]/td[1]"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUnableToFindRssUser() throws SQLException {
        String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        createCSMUser(loginName).longValue();
        setPaProperty("cteprss.user", loginName);

        createTrialChangeLeadOrgAndAccept();
        assertTrue(s.isTextPresent("Study Protocol Accepted."));
        assertTrue(s
                .isTextPresent("Unable to find ctep-rss user and assign as owner"));
        clickAndWait("link=Assign Ownership");
        assertTrue(s.isTextPresent("Nothing found to display."));

    }

    /**
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    private void createTrialChangeLeadOrgAndAccept() throws SQLException {
        TrialInfo trial = createSubmittedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Trial Validation");
        clickAndWait("link=Look Up Organization");
        waitForElementById("popupFrame", 10);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        s.type("orgCtepIdSearch", "SWOG");
        s.click("link=Search");
        final By selectBtn = By.xpath("//td/a//span[text()='Select']");
        waitForElementToBecomeAvailable(selectBtn, 15);
        pause(1000);
        driver.findElement(selectBtn).click();
        pause(5000);
        driver.switchTo().defaultContent();
        clickAndWait("link=Accept");
    }
}
