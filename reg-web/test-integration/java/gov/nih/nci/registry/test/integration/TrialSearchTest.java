/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.test.integration;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Tests trial search in Registry, as well as search-related functionality.
 * 
 * @author dkrylov
 */
@SuppressWarnings("deprecation")
@Batch(number = 2)
public class TrialSearchTest extends AbstractRegistrySeleniumTest {

    @Test
    public void testSearch() throws URISyntaxException, SQLException {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        deactivateAllTrials();
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        verifySingleFieldCriteria("officialTitle", rand, info);
        verifySingleFieldCriteriaNoMatch("officialTitle", rand + "_", info);

        verifySingleFieldCriteria("phaseCode", "0", info);
        verifySingleFieldCriteriaNoMatch("phaseCode", "II", info);

        verifySingleFieldCriteria("typeCodeValues", "Treatment", info);
        verifySingleFieldCriteriaNoMatch("typeCodeValues", "Prevention", info);

        verifySingleFieldCriteria("identifier", info.nciID, info);
        verifySingleFieldCriteriaNoMatch("identifier", info.nciID + "0", info);

        verifySingleFieldCriteria("identifier", info.leadOrgID, info);
        verifySingleFieldCriteriaNoMatch("identifier", info.leadOrgID + "0",
                info);

        verifySingleFieldCriteria("identifier", "NCT" + rand, info);
        verifySingleFieldCriteriaNoMatch("identifier", "NCT" + rand + "1", info);

        verifySingleFieldCriteria("identifier", "OTHER" + rand, info);
        verifySingleFieldCriteriaNoMatch("identifier", "OTHER" + rand + "1",
                info);

        verifySingleFieldCriteria("trialCategory", "Complete", info);
        verifySingleFieldCriteriaNoMatch("trialCategory", "Abbreviated", info);

        // By Lead Org
        accessTrialSearchScreen();
        selectLeadOrg();
        runSearchAndVerifySingleTrialResult(info);

        // By PI
        accessTrialSearchScreen();
        selectPI();
        runSearchAndVerifySingleTrialResult(info);

    }

    @Test
    public void testTabs() throws URISyntaxException, SQLException {
        TrialInfo trialInfo = createAcceptedTrial();
        loginAndAcceptDisclaimer();
        accessTrialSearchScreen();
        selenium.type("officialTitle", trialInfo.uuid);
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);
        assertEquals(trialInfo.title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));

        clickAndWaitAjax("xpath=//div[@class='container']//a[normalize-space(text())='Search Clinical Trials']");
        assertTrue(selenium.isVisible("officialTitle"));
        assertEquals(trialInfo.uuid, selenium.getValue("officialTitle"));

        clickAndWaitAjax("xpath=//div[@class='container']//a[normalize-space(text())='Search Results']");
        assertTrue(selenium.isVisible("row"));
        assertEquals(trialInfo.title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMultiCriteriaSearch() throws URISyntaxException,
            SQLException {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();
        registerAndAcceptTrial(RandomStringUtils.randomNumeric(10));

        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);

        accessTrialSearchScreen();
        selenium.type("officialTitle", rand);
        selenium.type("phaseCode", "0");
        selenium.type("typeCodeValues", "Treatment");
        selenium.type("identifier", info.nciID);
        selenium.type("identifier", info.leadOrgID);
        selenium.type("identifier", "NCT" + rand);
        selenium.type("identifier", "OTHER" + rand);
        selenium.type("trialCategory", "Complete");
        selectLeadOrg();
        selectPI();
        runSearchAndVerifySingleTrialResult(info);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDataTableControls() throws URISyntaxException, SQLException {
        deactivateAllTrials();
        Collection<TrialInfo> trials = new TreeSet<>();
        for (int i = 0; i < 20; i++) {
            trials.add(createAcceptedTrial());
        }
        List<TrialInfo> trialsAsList = new ArrayList<>(trials);

        loginAndAcceptDisclaimer();
        accessTrialSearchScreen();
        selenium.type("trialCategory", "Complete");
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);

        // At this point, results table must only show 10 rows.
        for (int i = 1; i <= 10; i++) {
            assertTrue(selenium
                    .isElementPresent("xpath=//table[@id='row']/tbody/tr[" + i
                            + "]"));
        }
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[11]"));

        // Display all 20
        selenium.select("xpath=//select[@name='row_length']", "25");
        pause(500);
        for (int i = 1; i <= 20; i++) {
            assertTrue(selenium
                    .isElementPresent("xpath=//table[@id='row']/tbody/tr[" + i
                            + "]"));
        }

        // sort by title
        selenium.click("xpath=//table[@id='row']/thead//div[text()='Title']");
        for (int i = 0; i < trialsAsList.size(); i++) {
            TrialInfo trialInfo = trialsAsList.get(i);
            assertEquals(
                    trialInfo.title,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr["
                            + (i + 1) + "]/td[2]"));
        }
        selenium.click("xpath=//table[@id='row']/thead//div[text()='Title']");
        for (int i = trialsAsList.size() - 1; i >= 0; i--) {
            TrialInfo trialInfo = trialsAsList.get(i);
            assertEquals(
                    trialInfo.title,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr["
                            + (trialsAsList.size() - i) + "]/td[2]"));
        }

        // sort by lead org.
        selenium.click("xpath=//table[@id='row']/thead//th[text()='Lead Org Trial Identifier']");
        for (int i = 0; i < trialsAsList.size(); i++) {
            TrialInfo trialInfo = trialsAsList.get(i);
            assertEquals(
                    trialInfo.leadOrgID,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr["
                            + (i + 1) + "]/td[5]"));
        }
        selenium.click("xpath=//table[@id='row']/thead//th[text()='Lead Org Trial Identifier']");
        for (int i = trialsAsList.size() - 1; i >= 0; i--) {
            TrialInfo trialInfo = trialsAsList.get(i);
            assertEquals(
                    trialInfo.leadOrgID,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr["
                            + (trialsAsList.size() - i) + "]/td[5]"));
        }

        // Remove & add back columns
        selenium.click("xpath=//button/span[text()='Choose columns']");
        List<WebElement> els = driver.findElements(By
                .xpath("//ul[@class='ColVis_collection']/li//input"));
        for (WebElement webElement : els) {
            webElement.click();
        }
        assertTrue(driver.findElements(
                By.xpath("//table[@id='row']/thead/tr/th")).isEmpty());

        int i = 1;
        for (WebElement webElement : els) {
            webElement.click();
            pause(100);
            assertTrue(selenium
                    .isElementPresent("xpath=//table[@id='row']/thead/tr/th["
                            + (i++) + "]"));
        }

        // paging
        s.click("xpath=//body");
        pause(5000);
        moveElementIntoView(By.xpath("//table[@id='row']"));
        selenium.click("xpath=//table[@id='row']");
        pause(1000);
        selenium.select("xpath=//select[@name='row_length']", "10");
        assertTrue(selenium.isTextPresent("Showing 1 to 10 of 20"));
        selenium.click("xpath=//table[@id='row']/thead//div[text()='Title']");
        assertEquals(trialsAsList.get(0).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));

        selenium.click("xpath=//div[@id='row_paginate']//a[text()='2']");
        assertTrue(selenium.isTextPresent("Showing 11 to 20 of 20"));
        assertEquals(trialsAsList.get(19).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[10]/td[2]"));

        selenium.click("xpath=//div[@id='row_paginate']//a[@id='row_previous']");
        assertTrue(selenium.isTextPresent("Showing 1 to 10 of 20"));
        assertEquals(trialsAsList.get(0).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));

        selenium.click("xpath=//div[@id='row_paginate']//a[@id='row_next']");
        assertTrue(selenium.isTextPresent("Showing 11 to 20 of 20"));
        assertEquals(trialsAsList.get(19).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[10]/td[2]"));

        selenium.click("xpath=//div[@id='row_paginate']//a[@id='row_first']");
        assertTrue(selenium.isTextPresent("Showing 1 to 10 of 20"));
        assertEquals(trialsAsList.get(0).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));

        selenium.click("xpath=//div[@id='row_paginate']//a[@id='row_last']");
        assertTrue(selenium.isTextPresent("Showing 11 to 20 of 20"));
        assertEquals(trialsAsList.get(19).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[10]/td[2]"));
        selenium.click("xpath=//div[@id='row_paginate']//a[@id='row_first']");

        // in-page search
        selenium.select("xpath=//select[@name='row_length']", "25");
        driver.findElement(By.xpath("//div[@id='row_filter']//input"))
                .sendKeys(trialsAsList.get(10).uuid);
        assertEquals(trialsAsList.get(10).title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[2]"));
        assertTrue(selenium.isTextPresent("Showing 1 to 1 of 1"));

        // open up trial details
        clickAndWait("link=" + trialsAsList.get(10).nciID);
        assertTrue(selenium.isTextPresent("Trial Details"));
        assertEquals(trialsAsList.get(10).nciID,
                getTrialConfValue("NCI Trial Identifier:"));
        assertEquals(trialsAsList.get(10).leadOrgID,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals(trialsAsList.get(10).title, getTrialConfValue("Title:"));

    }

    /**
     * 
     */
    private void selectPI() {
        driver.findElement(By.id("principalInvestigatorName")).sendKeys("John");
        pause(3000);
        selenium.click("xpath=//li[@class='ui-menu-item']/a[text()='Doe,John']");
        assertEquals("Doe,John", selenium.getValue("principalInvestigatorName"));
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void selectLeadOrg() {
        selenium.select("organizationType", "label=Lead Organization");
        driver.findElement(By.id("organizationName")).sendKeys("National");
        pause(3000);
        selenium.click("xpath=//li[@class='ui-menu-item']/a[text()='National Cancer Institute Division of Cancer Prevention']");
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSearchMyTrials() throws URISyntaxException, SQLException {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        deactivateAllTrials();
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        accessTrialSearchScreen();
        selenium.click("runSearchBtn");
        clickAndWait("link=My Trials");
        waitForElementById("row", 10);
        verifySingleTrialExtendedSearchResult(info);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testReset() throws URISyntaxException, SQLException {
        loginAndAcceptDisclaimer();
        accessTrialSearchScreen();
        selenium.type("officialTitle", "officialTitle");
        selenium.type("phaseCode", "0");
        selenium.type("typeCodeValues", "Treatment");
        selenium.type("identifier", "identifier");
        selenium.type("trialCategory", "Complete");
        selenium.select("phaseAdditionalQualifierCode", "label=Yes");
        selenium.type("identifierType", "NCI");

        selenium.select("organizationType", "label=Lead Organization");
        driver.findElement(By.id("organizationName")).sendKeys("National");
        pause(3000);
        selenium.click("xpath=//li[@class='ui-menu-item']/a[text()='National Cancer Institute Division of Cancer Prevention']");

        driver.findElement(By.id("principalInvestigatorName")).sendKeys("John");
        pause(3000);
        selenium.click("xpath=//li[@class='ui-menu-item']/a[text()='Doe,John']");

        clickAndWait("id=resetSearchBtn");

        assertEquals("", selenium.getValue("officialTitle"));
        assertEquals("", selenium.getValue("phaseCode"));
        assertEquals("", selenium.getValue("typeCodeValues"));
        assertEquals("", selenium.getValue("phaseAdditionalQualifierCode"));
        assertEquals("", selenium.getValue("trialCategory"));
        assertEquals("All", selenium.getValue("identifierType"));
        assertEquals("", selenium.getValue("identifier"));
        assertEquals("", selenium.getValue("organizationType"));
        assertEquals("", selenium.getValue("organizationName"));
        assertEquals("", selenium.getValue("principalInvestigatorName"));

    }

    private void verifySingleFieldCriteria(String fieldID, String value,
            TrialInfo info) {
        accessTrialSearchScreen();
        selenium.type(fieldID, value);
        runSearchAndVerifySingleTrialResult(info);

    }

    private void verifySingleFieldCriteriaNoMatch(String fieldID, String value,
            TrialInfo info) {
        accessTrialSearchScreen();
        selenium.type(fieldID, value);
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("search-results", 10);
        assertTrue(selenium
                .isElementPresent("xpath=//div[normalize-space(text())='Nothing found to display.']"));

    }

    /**
     * @param info
     */
    protected void runSearchAndVerifySingleTrialResult(TrialInfo info) {
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);
        verifySingleTrialSearchResult(info);
    }

    /**
     * @param info
     */
    protected void verifySingleTrialSearchResult(TrialInfo info) {
        assertEquals(
                info.nciID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]/a"));
        assertEquals("An Open-Label Study of Ruxolitinib " + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals(info.leadOrgID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]"));
        assertEquals("Doe, John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[6]"));
        assertEquals("NCT" + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[7]"));
        assertEquals("OTHER" + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[8]"));
        assertEquals(
                "View",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[9]/a"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']"));
    }

    /**
     * @param info
     */
    protected void verifySingleTrialExtendedSearchResult(TrialInfo info) {
        assertEquals(
                info.nciID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]/a"));
        assertEquals("An Open-Label Study of Ruxolitinib " + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]"));
        assertEquals(info.leadOrgID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals("Doe, John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]"));
        assertEquals("NCT" + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[6]"));
        assertEquals("OTHER" + info.rand,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[7]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[8]"));
        assertEquals("Accepted",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[9]"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']"));
        assertEquals(
                "ICD10",
                selenium.getValue("xpath=//table[@id='row']/tbody/tr[1]/td[11]/select"));
        assertEquals(
                "View",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[12]/a"));
        assertEquals("O",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[13]"));
        assertEquals("TREATMENT",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[14]"));
        assertEquals("Complete",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[15]"));
        assertEquals(tommorrow,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[16]"));
        assertEquals("Cancer Therapy Evaluation Program",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[17]"));
        assertEquals("Cancer Therapy Evaluation Program",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[18]"));
        assertEquals("NATIONAL",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[19]"));
        assertEquals("abstractor-ci",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[21]"));
        assertEquals(oneYearFromToday,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[22]"));
    }

    

   

}
