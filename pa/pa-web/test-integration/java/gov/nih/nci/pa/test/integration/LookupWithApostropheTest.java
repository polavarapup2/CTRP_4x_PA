package gov.nih.nci.pa.test.integration;

import org.junit.Ignore;

@Ignore
public class LookupWithApostropheTest extends AbstractPaSeleniumTest {

    /**
     * Tests lookup of lead organization with apostrophe.
     *
     * @throws Exception on error
     */
    @Ignore
    public void leadOrganizationLookup() throws Exception {
        loginAsAdminAbstractor();
        verifyTrialSearchPage();
        searchAndSelectTrial("Test Summ 4 Anatomic Site Trial created by Selenium.");
        clickAndWait("link=General Trial Details");
        clickAndWaitAjax("link=Look Up Org");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        selenium.type("orgNameSearch", "2098'");
        clickAndWaitAjax("link=Search");
        assertTrue("Wrong search results returned", selenium.isTextPresent("One item found"));
        assertTrue("Wrong search results returned", selenium.isTextPresent("PO-2098'test organization"));
        clickAndWaitAjax("//table[@id='row']/tbody/tr[1]/td[7]/a/span/span");
        selenium.selectFrame("relative=up");
        assertEquals("Wrong Principal investigator", "PO-2098'test organization", selenium.getValue("name=gtdDTO.leadOrganizationName"));
    }

    /**
     * Tests lookup of person with apostrophe.
     *
     * @throws Exception on error
     */
    @Ignore
    public void personLookup() throws Exception {
        loginAsAdminAbstractor();
        verifyTrialSearchPage();
        selenium.type("id=officialTitle", "Test Summ 4 Anatomic Site Trial created by Selenium.");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        assertTrue(selenium.isElementPresent("id=row"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[2]"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]/a"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        clickAndWait("link=General Trial Details");
        clickAndWaitAjax("//div[@id='loadPersField']/table/tbody/tr/td[2]/ul/li/a/span/span");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        selenium.type("personLastName", "O'g");
        clickAndWaitAjax("link=Search");
        assertTrue("Wrong search results returned", selenium.isTextPresent("One item found"));
        assertTrue("Wrong search results returned", selenium.isTextPresent("O'Grady"));
        clickAndWaitAjax("//table[@id='row']/tbody/tr[1]/td[8]/a/span/span");
        selenium.selectFrame("relative=up");
        assertEquals("Wrong Principal investigator", "O'Grady,Michael", selenium.getValue("name=gtdDTO.piName"));
    }

}
