/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.dumbster.smtp.SmtpMessage;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Tests the trial registration process.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings("deprecation")
@Batch(number = 1)
public class RegisterTrialTest extends AbstractRegistrySeleniumTest {

    /**
     * Tests registering a trial.
     * 
     * @throws Exception
     *             on error
     */
    @Test
    public void testRegisterTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        for (String cat : new String[] { "National",
                "Externally Peer-Reviewed", "Institutional" }) {
            loginAndAcceptDisclaimer();
            String rand = RandomStringUtils.randomNumeric(10);
            registerTrial(rand, cat);
            final String nciID = getLastNciId();
            assertTrue(
                    "No success message found",
                    selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                            + nciID));
            verifyTrialConfirmaionPage(rand, nciID, cat);
            if ("Interventional"
                    .equalsIgnoreCase(getTrialConfValue("Trial Type:"))) {
                assertEquals("Yes",
                        getTrialConfValue("Section 801 Indicator :"));
                assertEquals("No",
                        getTrialConfValue("Delayed Posting Indicator :"));
            } else {
                assertEquals("No", getTrialConfValue("Section 801 Indicator :"));
            }
            logoutUser();
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testOrgNameWithQuotes() {
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 30);

        // Select Lead Organization
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=Search...");
        waitForElementById("popupFrame", 60);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("orgNameSearch", 30);
        selenium.type("orgNameSearch", "double");
        clickAndWaitAjax("id=search_organization_btn");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        WebElement orgElement = driver.findElement(By
                .id("trialDTO.leadOrganizationNameField"));
        String text = orgElement.getText();
        assertEquals("Double \" quotes\"", text);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateAbbreviatedTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();
        TrialInfo info = createAcceptedTrial(true, true);
        assignTrialOwner("abstractor-ci", info.id);
        final String nciID = info.nciID;
        searchForTrialByNciID(nciID);
        selectAction("Update");

        String rand = RandomStringUtils.randomNumeric(10);

        assertEquals(info.nciID, getTrialConfValue("NCI Trial Identifier:"));
        assertEquals(info.leadOrgID,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals(info.title, getTrialConfValue("Title:"));
        assertEquals("ClinicalTrials.gov",
                getTrialConfValue("Lead Organization:"));
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("II", getTrialConfValue("Phase:"));

        selenium.type("nctId", "NCT" + rand);
        clickAndWaitAjax("id=nctIdbtnid");

        // Add Protocol and IRB Document
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("irbApproval", irbDocPath);
        selenium.type("informedConsentDocument", (new File(ClassLoader
                .getSystemResource(CONSENT_DOCUMENT).toURI()).toString()));
        selenium.type("submitProprietaryTrial_otherDocument_0", (new File(
                ClassLoader.getSystemResource(OTHER_DOCUMENT).toURI())
                .toString()));

        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("updateProprietaryTrialreview", 10);

        assertEquals(info.leadOrgID,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals(info.title, getTrialConfValue("Title:"));
        assertEquals("ClinicalTrials.gov",
                getTrialConfValue("Lead Organization:"));
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("II", getTrialConfValue("Phase:"));

        // Documents
        assertEquals(
                "IrbDoc.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='IRB Approval Document']]"));
        assertEquals(
                "Consent.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Informed Consent Document']]"));
        assertEquals(
                "Other.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Other']]"));

        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();

        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + nciID + " was successfully submitted"));
        assertEquals(info.nciID, getTrialConfValue("NCI Trial Identifier:"));
        assertEquals(info.leadOrgID,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals(info.title, getTrialConfValue("Title:"));
        assertEquals("ClinicalTrials.gov",
                getTrialConfValue("Lead Organization:"));
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("II", getTrialConfValue("Phase:"));
        // Documents
        assertEquals(
                "IrbDoc.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='IRB Approval Document']]"));
        assertEquals(
                "Consent.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Informed Consent Document']]"));
        assertEquals(
                "Other.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Other']]"));

    }
  //commented as part of PO-9862
    /*
    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateCompleteTrialAndCloseSitesPO_8323() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        updateCompleteTrialAndCloseSitesPO_8323(new String[] {
        "In Review",
        "Active", 
        "Closed to Accrual" });

    }*/
  //commented as part of PO-9862

   /* @SuppressWarnings("deprecation")
    @Test
    public void testUpdateCompleteTrialStatusAndCloseSitesPO_8323()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        String[] params = new String[] { "In Review", "Active",
                "Closed to Accrual" };

        TrialInfo info = prepareTrialForUpdateAndSelectIt(params, true);

        selectAction("Change Status");
        waitForElementToBecomeVisible(By.id("popupFrame"), 15);
        final WebElement frame = driver.findElement(By.id("popupFrame"));
        driver.switchTo().frame(frame);        

        changeTrialStatusAndDates(params);

        // Try to submit and verify the dialog (see JIRA).
        submitTrialAndVerifyOpenSitesDialog(params, "Save" , true);
        waitForPageToLoad();

        assertTrue(selenium.isTextPresent("Trial status successfully updated."));
        driver.switchTo().defaultContent();

        doBackEndChecksAndLogOut(params, info);

    }*/


 /*   protected void doBackEndChecksAndLogOut(String[] params, TrialInfo info)
            throws SQLException {
        // Do backend checks; ensure sites are closed with the same status.
        verifySiteIsNowClosed(info,
                "National Cancer Institute Division of Cancer Prevention",
                params[2]);
        verifySiteIsNowClosed(info, "Cancer Therapy Evaluation Program",
                params[2]);

        logoutUser();
        logoutPA();
    }*/

  //commented as part of PO-9862
    /*
    private void updateCompleteTrialAndCloseSitesPO_8323(String[] params)
            throws URISyntaxException, SQLException {

        TrialInfo info = prepareTrialForUpdateAndSelectIt(params , true);

        selectAction("Update");

        changeTrialStatusAndDates(params);

        // Try to submit and verify the dialog (see JIRA).
        submitTrialAndVerifyOpenSitesDialog(params, "Review Trial", true);
        waitForElementById("updateTrialreviewUpdate", 10);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();

        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + info.nciID + " was successfully submitted"));

        doBackEndChecksAndLogOut(params, info);
    }*/

    /**
     * @param params
     */
    protected void changeTrialStatusAndDates(String[] params) {
        deleteStatus(2);
        deleteStatus(1);
        addStatus(date(-3), "In Review", "");
        addStatus(date(-2), "Approved", "");
        addStatus(date(-1), "Active", "");
        addStatus(date(0), params[2], "");

        // Adjust dates.
        selenium.type("trialDTO_startDate", today);
        selenium.click("trialDTO_startDateTypeActual");
    }
    
    /**
     * @param params
     */
    protected void changeTrialStatusAndDatesForLaterDate(String[] params) {
        deleteStatus(2);
        deleteStatus(1);
        addStatus(date(-3), "In Review", "");
        addStatus(date(-2), "Approved", "");
        addStatus(date(-1), "Active", "");
        addStatus(date(+1), params[2], "");

        // Adjust dates.
        selenium.type("trialDTO_startDate", today);
        selenium.click("trialDTO_startDateTypeActual");
    }

    /**
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws SQLException
     */
    protected TrialInfo prepareTrialForUpdateAndSelectIt(String[] params , boolean isEarlierDate)
            throws URISyntaxException, SQLException {
        loginAndAcceptDisclaimer();

        // Register trial.
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        assignTrialOwner("submitter-ci", info.id);
        logoutUser();

        // Add 3 sites, one is already closed.
        selectTrialInPA(info);
        addSiteToTrial(info, "DCP", params[0] ,isEarlierDate);
        addSiteToTrial(info, "CTEP", params[1] ,isEarlierDate);
        addSiteToTrial(info, "NCI", "Withdrawn" ,isEarlierDate);

        // Change trial status to closed in Registry.
        findInMyTrials(info.nciID);
        return info;
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateCompleteTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        final String nciID = info.nciID;
        searchForTrialByNciID(nciID);

        selectAction("Update");

        verifyUpdateCompleteTrialPage(info);
        populateUpdateCompleteTrialPage(rand);
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("updateTrialreviewUpdate", 10);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();

        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + nciID + " was successfully submitted"));

        assertTrue(selenium
                .isElementPresent("//li[normalize-space(text())='OTHER" + rand
                        + "-2']"));
        assertEquals("ICD9", getTrialConfValue("Accrual Disease Terminology:"));

        // Grants
        int newGrantIndex = selenium.getText(
                "//div[@id='grantsDiv']/table/tbody/tr[1]/td[1]").equals("C06") ? 1
                : 2;
        int oldGrantIndex = newGrantIndex == 1 ? 2 : 1;
        assertEquals(
                "C06",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + newGrantIndex + "]/td[1]"));
        assertEquals(
                "AG",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + newGrantIndex + "]/td[2]"));
        assertEquals(
                rand + "2",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + newGrantIndex + "]/td[3]"));
        assertEquals(
                "CIP",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + newGrantIndex + "]/td[4]"));

        assertEquals(
                "B09",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + oldGrantIndex + "]/td[1]"));
        assertEquals(
                "AA",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + oldGrantIndex + "]/td[2]"));
        assertEquals(
                rand,
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + oldGrantIndex + "]/td[3]"));
        assertEquals(
                "CCR",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr["
                        + oldGrantIndex + "]/td[4]"));

        verifyTrialStatus(nciID, "Active");

        // Make sure deleted statuses are there as well.
        List<TrialStatus> hist = getDeletedTrialStatuses(info);
        assertEquals(2, hist.size());

        assertTrue(DateUtils.isSameDay(hist.get(0).statusDate, yesterdayDate));
        assertEquals("IN_REVIEW", hist.get(0).statusCode);
        assertEquals("Wrong status", hist.get(0).comments);

        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals("APPROVED", hist.get(1).statusCode);
        assertEquals("Wrong status", hist.get(1).comments);

        // Start/End Dates
        assertEquals(today + " Actual", getTrialConfValue("Trial Start Date:"));
        assertEquals(tommorrow + " Anticipated",
                getTrialConfValue("Primary Completion Date:"));
        assertEquals(tommorrow + " Anticipated",
                getTrialConfValue("Completion Date:"));

        verifyDocuments();

    }

    /**
     * @param rand
     * @throws URISyntaxException
     */
    protected void populateUpdateCompleteTrialPage(String rand)
            throws URISyntaxException {
        selenium.type("otherIdentifierOrg", "OTHER" + rand + "-2");
        selenium.click("otherIdbtnid");
        selenium.select("trialDTO.accrualDiseaseCodeSystem", "ICD9");

        // Grants
        moveElementIntoView(By.id("fundingMechanismCode"));
        selenium.select("fundingMechanismCode", "label=C06");
        selenium.select("nihInstitutionCode", "label=AG");
        selenium.type("serialNumber", rand + "2");
        selenium.select("nciDivisionProgramCode", "label=CIP");
        selenium.click("grantbtnid");
        waitForElementById("grantadddiv", 5);

        // Trial Status Information
        driver.switchTo().defaultContent();
        deleteStatus(2);
        deleteStatus(1);
        populateStatusHistory();
        editStatus(2, today, "Active", "Changed to Active.");

        selenium.type("trialDTO_startDate", today);
        selenium.click("trialDTO_startDateTypeActual");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", tommorrow);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", tommorrow);

        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        selenium.type("participatingSites", (new File(ClassLoader
                .getSystemResource(SITES_DOCUMENT).toURI()).toString()));
        selenium.type("informedConsentDocument", (new File(ClassLoader
                .getSystemResource(CONSENT_DOCUMENT).toURI()).toString()));
        selenium.type("updateTrial_otherDocument_0", (new File(ClassLoader
                .getSystemResource(OTHER_DOCUMENT).toURI()).toString()));
    }

    private void verifyUpdateCompleteTrialPage(TrialInfo info) {
        String rand = info.rand;
        assertEquals(info.nciID, getTrialConfValue("NCI Trial Identifier:"));
        assertEquals("LEAD" + rand,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals("NCT" + rand,
                getTrialConfValue("ClinicalTrials.gov Identifier:"));
        assertEquals("OTHER" + rand,
                selenium.getValue("updateTrial_otherIdentifiers"));
        assertEquals("true",
                driver.findElement(By.id("updateTrial_otherIdentifiers"))
                        .getAttribute("readonly"));
        assertEquals("An Open-Label Study of Ruxolitinib " + rand,
                selenium.getValue("submitTrial_protocolWebDTO_trialTitle"));
        assertEquals(
                "true",
                driver.findElement(
                        By.id("submitTrial_protocolWebDTO_trialTitle"))
                        .getAttribute("readonly"));
        assertEquals("0", getTrialConfValue("Phase:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("ICD10",
                selenium.getValue("trialDTO.accrualDiseaseCodeSystem"));
        assertTrue("National Cancer Institute Division of Cancer Prevention"
                .equals(getTrialConfValue("Lead Organization:")));
        assertEquals("Doe,John", getTrialConfValue("Principal Investigator:")
                .replaceAll("\\s", ""));
        assertEquals("National",
                getTrialConfValue("Data Table 4 Funding Sponsor Type:"));
        assertEquals("National Cancer Institute",
                getTrialConfValue("Data Table 4 Funding Sponsor:"));
        //assertEquals("PG" + rand, getTrialConfValue("Program code:"));

        assertEquals("", selenium.getValue("trialDTO_statusCode"));
        assertEquals("", selenium.getValue("trialDTO_reason"));
        assertEquals("", selenium.getValue("trialDTO_statusDate"));
        assertEquals(
                yesterday,
                selenium.getText("//table[@id='trialStatusHistoryTable']/tbody/tr[1]/td[1]"));
        assertEquals(
                "In Review",
                selenium.getText("//table[@id='trialStatusHistoryTable']/tbody/tr[1]/td[2]"));
        assertEquals(
                today,
                selenium.getText("//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[1]"));
        assertEquals(
                "Approved",
                selenium.getText("//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[2]"));
        assertEquals(
                "Changed to Approved.",
                selenium.getText("//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[3]"));

        assertEquals(tommorrow, selenium.getValue("trialDTO_startDate"));
        assertTrue(selenium.isChecked("trialDTO_startDateTypeAnticipated"));
        assertEquals(oneYearFromToday,
                selenium.getValue("trialDTO_primaryCompletionDate"));
        assertTrue(selenium
                .isChecked("trialDTO_primaryCompletionDateTypeAnticipated"));
        assertEquals(oneYearFromToday,
                selenium.getValue("trialDTO_completionDate"));
        assertTrue(selenium.isChecked("trialDTO_completionDateTypeAnticipated"));
        assertFalse(s
                .isElementPresent("xpath=//input[@type='radio' and @value='N/A']"));

        // INDs
        assertEquals("IND",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[1]"));
        assertEquals(rand,
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[2]"));
        assertEquals("CDER",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[3]"));
        assertEquals("NIH",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[4]"));
        assertEquals("NEI-National Eye Institute",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[5]"));
        assertEquals("Yes",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[6]"));
        assertEquals("Available",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[7]"));
        assertEquals("Yes",
                selenium.getText("//table[@id='indideTable']/tbody/tr/td[8]"));

        // Grants
        assertEquals("B09",
                selenium.getText("//div[@id='grantdiv']/table/tbody/tr/td[1]"));
        assertEquals("AA",
                selenium.getText("//div[@id='grantdiv']/table/tbody/tr/td[2]"));
        assertEquals(rand,
                selenium.getText("//div[@id='grantdiv']/table/tbody/tr/td[3]"));
        assertEquals("CCR",
                selenium.getText("//div[@id='grantdiv']/table/tbody/tr/td[4]"));

        verifyDocuments();

        // Regulatory
        assertEquals("United States",
                getTrialConfValue("Trial Oversight Authority Country :"));
        assertEquals(
                "Food and Drug Administration",
                getTrialConfValue("Trial Oversight Authority Organization Name :"));
        assertEquals("Yes",
                getTrialConfValue("FDA Regulated Intervention Indicator :"));
        assertEquals("Yes", getTrialConfValue("Section 801 Indicator :"));
        assertEquals("No", getTrialConfValue("Delayed Posting Indicator :"));
        assertEquals(
                "Yes",
                getTrialConfValue("Data Monitoring Committee Appointed Indicator :"));

        assertEquals("Cancer Therapy Evaluation Program",
                getTrialConfValue("Sponsor:"));
        assertEquals("Sponsor", getTrialConfValue("Responsible Party:"));

        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Ancillary", getTrialConfValue("Secondary Purpose:"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testViewCategoryDefinitions() throws Exception {
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(1500);
        clickAndWait("xpath=//a[text()='View Category Definitions']");
        waitForElementById("myModal", 5);
        assertEquals("Category Definitions",
                selenium.getText("//h4[@id='myLargeModalLabel']"));
        assertEquals(
                "National:",
                selenium.getText("//div[@class='modal-body']/table/tbody/tr[1]/td[1]"));
        assertEquals(
                "Externally Peer-Reviewed:",
                selenium.getText("//div[@class='modal-body']/table/tbody/tr[2]/td[1]"));
        assertEquals(
                "Institutional:",
                selenium.getText("//div[@class='modal-body']/table/tbody/tr[3]/td[1]"));
        assertEquals(
                "Industrial/Other:",
                selenium.getText("//div[@class='modal-body']/table/tbody/tr[4]/td[1]"));
        selenium.click("xpath=//button[following-sibling::h4[@id='myLargeModalLabel']]");
        pause(1500);
        assertFalse(selenium.isVisible("id=myModal"));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testImportDisabledAfterClick() throws Exception {
    
        loginAndAcceptDisclaimer();
        // Select register trial and choose trial type
        hoverLink("Register Trial");
        pause(1500);
        clickAndWait("xpath=//a[text()='Industrial/Other']");
        waitForElementById("nctID", 30);
        deactivateTrialByNctId("NCT00038610");
        selenium.type("nctID", "NCT00038610");
        clickAndWait("xpath=//button/i[@class='fa-search']");

        assertEquals("NCT00038610",
                selenium.getText("//table[@id='row']/tbody/tr/td[1]"));
        assertEquals("Completed",
                selenium.getText("//table[@id='row']/tbody/tr/td[2]"));
        assertTrue(selenium
                .getText("//table[@id='row']/tbody/tr/td[3]")
                .contains(
                        "Phase II Study of Hyper-CVAD Plus Imatinib Mesylate (Gleevec, STI571) for Philadelphia-Positive Acute Lymphocytic Leukemia"));
        
        WebElement element = driver.findElement(By.id("importTrial"));
        // Disable form submit otherwise form will be submitted and this button
        // will not be visible in selenium
        // if this button is not visible then button is disabled can not be
        // tested
       
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.handleAction =function(args){ return false;};");
        element.click();
        
        //test if button is disabled
        assertFalse(element.isEnabled());
        String buttonText = element.getText();
        
        //test if button text changed to please wait
        assertEquals("Please wait",buttonText);
    }    

    @SuppressWarnings("deprecation")
    @Test
    public void testRegisterAbbreviatedByNct() throws Exception {

        loginAndAcceptDisclaimer();
        // Select register trial and choose trial type
        hoverLink("Register Trial");
        pause(1500);
        clickAndWait("xpath=//a[text()='Industrial/Other']");
        waitForElementById("nctID", 30);
        deactivateTrialByNctId("NCT00038610");
        selenium.type("nctID", "NCT00038610");
        clickAndWait("xpath=//button/i[@class='fa-search']");

        assertEquals("NCT00038610",
                selenium.getText("//table[@id='row']/tbody/tr/td[1]"));
        assertEquals("Completed",
                selenium.getText("//table[@id='row']/tbody/tr/td[2]"));
        assertTrue(selenium
                .getText("//table[@id='row']/tbody/tr/td[3]")
                .contains(
                        "Phase II Study of Hyper-CVAD Plus Imatinib Mesylate (Gleevec, STI571) for Philadelphia-Positive Acute Lymphocytic Leukemia"));

        clickAndWait("xpath=//button/i[@class='fa-cloud-download']");

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("Trial NCT00038610 has been imported and registered in CTRP system successfully."
                        + " A unique NCI identifier "
                        + nciID
                        + " has been assigned to this trial with a processing status of Submitted. "
                        + "You can add your site to the trial now using the Add My Site button on this page"));
        assertTrue(selenium
                .isElementPresent("xpath=//button[text()='Add My Site']"));
        assertEquals(nciID, getTrialConfValue("NCI Trial Identifier:"));
        assertEquals("ID01-006",
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals(
                "Phase II Study of Hyper-CVAD Plus Imatinib Mesylate"
                        + " (Gleevec, STI571) for Philadelphia-Positive Acute Lymphocytic Leukemia",
                getTrialConfValue("Title:"));
        assertEquals("II", getTrialConfValue("Phase:"));
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("SDC", getTrialConfValue("Accrual Disease Terminology:"));
        assertEquals("M.D. Anderson Cancer Center",
                getTrialConfValue("Lead Organization:"));
        assertEquals("Industrial",
                getTrialConfValue("Trial Submission Category:"));
        assertEquals("M.D. Anderson Cancer Center",
                getTrialConfValue("Data Table 4 Funding Sponsor/Source:"));
        assertEquals("Yes", getTrialConfValue("Industrial?"));

        // Add My Site
        clickAndWait("xpath=//button[text()='Add My Site']");
        waitForElementById("popupFrame", 20);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        
        final String addSitesMultipleXPath = "//input[@name='addSitesMultiple']";
        waitForElementToBecomeAvailable(By.xpath(addSitesMultipleXPath), 15);
        assertEquals("true",selenium.getValue(addSitesMultipleXPath));
        assertTrue(selenium
                .isTextPresent("Because your organization belongs to a family, you can add to this trial any "
                        + "site within that family. Please select the site you would like to add below:"));
        
        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(3, options.length);
        selenium.select("pickedSiteOrgPoId",
                "label=National Cancer Institute");
        clickAndWait("pickSiteBtn");
        waitForElementToBecomeAvailable(By.xpath(addSitesMultipleXPath), 15);
        assertEquals("true",selenium.getValue(addSitesMultipleXPath));
        assertEquals("National Cancer Institute",
                selenium.getValue("organizationName"));

        selenium.type("localIdentifier", "XYZ0000001");

        clickAndWaitAjax("xpath=//button/i[@class='fa-search']");
        waitForElementById("popupFrame", 10);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("search_person_btn", 30);
        selenium.type("firstName", "John");
        selenium.type("lastName", "Doe");
        clickAndWaitAjax("id=search_person_btn");
        waitForElementById("row", 15);
        moveElementIntoView(By
                .xpath("//table[@id='row']/tbody/tr[1]/td[8]/button"));
        selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));

        selenium.type("programCode", "PGCODE");
        selenium.select("siteDTO_recruitmentStatus", "label=In Review");
        selenium.type("siteDTO_recruitmentStatusDate", "09/25/2014");
        clickAndWaitAjax("id=addStatusBtn");
        waitForElementById("siteStatusHistoryTable", 15);

        selenium.click("xpath=//button/i[@class='fa-floppy-o']");
        pause(5000);

        driver.switchTo().parentFrame().switchTo().defaultContent();
        waitForElementToBecomeVisible(By.cssSelector(".alert-success"), 15);
        
        assertTrue(
                "No success message found",
                selenium.isTextPresent("Your site has been added to the trial."));
        assertEquals("National Cancer Institute",
                selenium.getText("//table[@id='row']/tbody/tr/td[1]"));
        assertEquals("Doe,John",
                selenium.getText("//table[@id='row']/tbody/tr/td[2]"));
        assertEquals("XYZ0000001",
                selenium.getText("//table[@id='row']/tbody/tr/td[3]"));
        assertEquals("In Review",
                selenium.getText("//table[@id='row']/tbody/tr/td[4]"));
        assertEquals("09/25/2014",
                selenium.getText("//table[@id='row']/tbody/tr/td[5]"));
        assertEquals("", selenium.getText("//table[@id='row']/tbody/tr/td[6]"));
        assertEquals("", selenium.getText("//table[@id='row']/tbody/tr/td[7]"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testReviewEditSubmit() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);
        clickAndWait("xpath=//button[text()='Review Trial']");
        clickAndWait("xpath=//button/i[@class='fa-edit']");
        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyTrialConfirmaionPage(rand, nciID, category);
        if ("Interventional".equalsIgnoreCase(getTrialConfValue("Trial Type:"))) {
            assertEquals("Yes", getTrialConfValue("Section 801 Indicator :"));
            assertEquals("No", getTrialConfValue("Delayed Posting Indicator :"));
        } else {
            assertEquals("No", getTrialConfValue("Section 801 Indicator :"));
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSaveDraft() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);
        clickAndWait("xpath=//button[text()='Save as Draft']");
        final Number draftID = getLastDraftId();
        
        waitForEmailsToArrive(1);
        Iterator<SmtpMessage> emailIter = server.getReceivedEmail();        
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String body = email.getBody();
        String subject = email.getHeaderValue("Subject");
            
        System.out.println(body);
        System.out.println(subject);
        verifySaveDraftMailBody(body);
        verifySaveDraftMailSubject(subject);        
        
        
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial draft has been successfully saved and assigned the Identifier "
                        + draftID));
        verifyTrialConfirmaionPage(rand, "", category);
        if ("Interventional".equalsIgnoreCase(getTrialConfValue("Trial Type:"))) {
            assertEquals("Yes", getTrialConfValue("Section 801 Indicator :"));
            assertEquals("", getTrialConfValue("Delayed Posting Indicator :"));
        } else {
            assertEquals("No", getTrialConfValue("Section 801 Indicator :"));
        }
        hoverLink("Search");
        clickAndWait("link=Clinical Trials");
        waitForElementById("runSearchBtn", 30);
        selenium.click("runSearchBtn");
        clickAndWait("link=Saved Drafts");
        assertTrue(selenium.isTextPresent(title));
        assertTrue(selenium.isTextPresent(leadOrgTrialId));
        clickAndWait("xpath=//a[text()='" + draftID + "']");
        verifyTrialConfirmaionPage(rand, "", category);

        hoverLink("Search");
        clickAndWait("link=Clinical Trials");
        waitForElementById("runSearchBtn", 30);
        selenium.click("runSearchBtn");
        clickAndWait("link=Saved Drafts");
        clickAndWait("xpath=//a[@href='/registry/protected/submitTrialcompletePartialSubmission.action?studyProtocolId="
                + draftID + "']/button");
        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyTrialConfirmaionPage(rand, nciID, category);

    }
    
    /**
     * Check if program code drop down shown for Org with Family
     * @throws Exception
     */
    @Test
    public void testIfProgramCodeDropDownShown() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=National Cancer Institute Division of Cancer Prevention (Your Affiliation)");
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
    }
    
    /**
     * Check if program codes not shown for Org without family
     * @throws Exception
     */
    @Test
    public void testIfProgramCodeDropNotDownShownForNonCancer() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
       
       
        
     // Select Lead Organization
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=Search...");
        waitForElementById("popupFrame", 60);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("orgNameSearch", 30);
        selenium.type("orgNameSearch", "NCI - Center for Cancer Research");
        clickAndWaitAjax("id=search_organization_btn");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        
        assertFalse(selenium.isVisible("//div[@id='programCodeBlock']"));
        
       
    }
    
    /**
     * Test if user is able to register trial with program codes
     * @throws Exception
     */
    @Test
    public void testRegisterTrialWithProgramCodes () throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        String category ="National";
        String rand = RandomStringUtils.randomNumeric(10);
        String trialName ="Name"+rand;
        String leadOrgTrialId ="Lead"+rand;
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand,
                category);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        
      //now populate program codes
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        reviewAndSubmit();
        
        //check if trial is submitted successfully
        //get the nci id for the trial
        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        assertTrue(selenium.isTextPresent("PG1"));
        assertTrue(selenium.isTextPresent("PG2"));
        
         
        //get trial id from nci Id
        long trialId =(Long)getTrialIdByNciId(nciID);
        
        
        //check if program codes are actually added in the database
        long programCodeCount = getProgramCodesCount(trialId);
        
        assert programCodeCount==2;
    }
    
    
    /**
     * Test if program code values are retained after user click on review and then edit again
     * @throws Exception
     */
    @Test
    public void testIfProgamCodesRetainedAfterReviewAndEdit() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        String category ="National";
        String rand = RandomStringUtils.randomNumeric(10);
        String trialName ="Name"+rand;
        String leadOrgTrialId ="Lead"+rand;
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand,
                category);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        
      //now populate program codes
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
  
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 20);
        clickAndWait("xpath=//button[text()='Edit ']");
        waitForPageToLoad();
        
        //check if program codes are retained
        moveElementIntoView(By.id("programCodesValues"));
        
        assertOptionSelected("PG1-Cancer Program1");
        assertOptionSelected("PG2-Cancer Program2");
        
       
        

    }
    
    /**
     * Test if program code values are retained after there is error in final submit
     * @throws Exception
     */
    @Test
    public void testIfProgramCodesRetainedAfterErrorInFinalSubmit() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        String category ="National";
        String rand = RandomStringUtils.randomNumeric(10);
        String trialName ="Name"+rand;
        String leadOrgTrialId ="Lead"+rand;
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand,
                category);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        
      //now populate program codes
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
  
        
        reviewAndSubmit();
        
        //now again register trial with same lead org identifier this will throw error
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        rand = RandomStringUtils.randomNumeric(10);
        
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand,
                category);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        
      //now populate program codes
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 20);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
       
        //check if there is error
        assertTrue(
        selenium.isTextPresent("A trial exists in the system with the same Lead Organization Trial Identifier for the selected Lead Organization"));
        
        //check if program codes are retained
        moveElementIntoView(By.id("programCodesValues"));
        
        assertOptionSelected("PG1-Cancer Program1");
        assertOptionSelected("PG2-Cancer Program2");
        
       
        

    }
    
    /**
     * Test if program code values are retained if there is error in initial submit
     */
    @Test
    public void testIfProgramCodeValuesRetainedAfterError() throws Exception{
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        
        loginAndAcceptDisclaimer();
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=National");
        
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=National Cancer Institute Division of Cancer Prevention (Your Affiliation)");
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        
        //there should be error because other fields are left blank
        assertTrue(
        selenium.isTextPresent("The form has errors and could not be submitted. Please check the fields highlighted below"));
        
        //check if program code values are retained
        moveElementIntoView(By.id("programCodesValues"));
        
        assertOptionSelected("PG1-Cancer Program1");
        assertOptionSelected("PG2-Cancer Program2");
    }
    
    
    /**
     * Check if program code values are retained after user save trial as draft and then search again
     * @throws Exception
     */
    public void testIfProgramCodeValuesAreRetainedAfterSaveDraft() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);
        
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Save as Draft']");
        final Number draftID = getLastDraftId();
        
        
        hoverLink("Search");
        clickAndWait("link=Clinical Trials");
        waitForElementById("runSearchBtn", 30);
        selenium.click("runSearchBtn");
        clickAndWait("link=Saved Drafts");
        assertTrue(selenium.isTextPresent(title));
        assertTrue(selenium.isTextPresent(leadOrgTrialId));
        clickAndWait("xpath=//a[@href='/registry/protected/submitTrialcompletePartialSubmission.action?studyProtocolId="
                + draftID + "']/button");
        
        //check if program code values are retained
        moveElementIntoView(By.id("programCodesValues"));
        
        assertOptionSelected("PG1-Cancer Program1");
        assertOptionSelected("PG2-Cancer Program2");        
    }
    
    private void associateProgramCodes() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from program_code");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG1', 'Cancer Program1', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG2', 'Cancer Program2', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG3', 'Cancer Program3', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG4', 'Cancer Program4', 'ACTIVE')");
    }
    
    
    private long getProgramCodesCount(long trialId) throws SQLException {
        
        QueryRunner runner = new QueryRunner();
        return (Long) runner
                .query(connection,
                        "select count(*) from study_program_code where study_protocol_id="+trialId
                        +" and program_code_id in (select identifier from program_code where program_code in ('PG1','PG2'))" ,
                        new ArrayHandler())[0];
     
    }
    
    
    /**
     * @param body
     */
    private void verifySaveDraftMailBody(final String body) {
        assertTrue(body
                .contains("<hr><p><b>Title: </b>An Open-Label Study of Ruxolitinib "));
        assertTrue(body
                .contains("Lead Organization Trial ID:"));
        assertTrue(body
                .contains("Lead Organization:"));
        assertTrue(body
                .contains("Submission Date:"));
        assertTrue(body
                .contains("<p>Dear Abstractor User,</p><p>You have saved a draft of the trial identified above for submission to the NCI Clinical Trials Reporting Program (CTRP).</p>"
                		+ "<p>The CTRP has assigned your draft a unique temporary identifier for tracking purposes.</p> "
                		+ "<p><b>NEXT STEPS:</b><br>To retrieve and complete your submission, use the \"Search Saved Drafts\" feature on "
                		+ "the \"Search Trials\" page in the CTRP Registration application.</p>"
                		+ "<p>Clinical Trials Reporting Office (CTRO) staff will not access or process your trial until you have completed the submission.</p>"
                		+ "<p><b>Important!</b> You can save your draft  for a maximum of 30 days.</p>"
                		+ "<p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p>"
                		+ "<p>Thank you for submitting your trial for registration in the Clinical Trials Reporting Program.</p>"));
    }
    
    /**
     * @param subject
     */
    private void verifySaveDraftMailSubject(final String subject) {
    	assertTrue(subject
                .contains("NCI CTRP: Trial RECORD SAVED as DRAFT for"));
    }

    private Number getLastDraftId() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select identifier from study_protocol_stage order by identifier desc LIMIT 1",
                        new ArrayHandler())[0];
    }

    @Test
    public void testRegisterNonCtGovTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);
        selenium.click("id=xmlRequiredfalse");
        assertFalse(selenium.isVisible("id=sponsorDiv"));
        assertFalse(selenium.isVisible("id=regDiv"));
        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyBaseTrialInfo(rand, nciID, category);
        verifyTrialConfValueMissing("Sponsor:");
        verifyTrialConfValueMissing("Responsible Party:");
        verifyTrialConfValueMissing("Trial Oversight Authority Country :");
        verifyTrialConfValueMissing("Trial Oversight Authority Organization Name :");
        verifyTrialConfValueMissing("FDA Regulated Intervention Indicator :");
        verifyTrialConfValueMissing("Section 801 Indicator :");
        verifyTrialConfValueMissing("Delayed Posting Indicator :");
        verifyTrialConfValueMissing("Data Monitoring Committee Appointed Indicator :");

    }

    @Test
    public void testRegisterNonInterventionalTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);
        moveElementIntoView(By.id("trialDTO.trialType.Noninterventional"));
        selenium.click("id=trialDTO.trialType.Noninterventional");
        assertFalse(selenium.isVisible("id=trialDTO.secondaryPurposes"));

        moveElementIntoView(By.id("trialDTO.primaryPurposeCode"));
        selenium.select("trialDTO.primaryPurposeCode2", "label=Treatment");
        selenium.select("trialDTO.studySubtypeCode", "label=Observational");
        selenium.select("trialDTO.studyModelCode", "label=Other");
        selenium.type("trialDTO.studyModelOtherText", "Study Model Other");
        selenium.select("trialDTO.timePerspectiveCode", "label=Other");
        selenium.type("trialDTO.timePerspectiveOtherText", "Time Other");
        selenium.chooseOkOnNextConfirmation();
        selenium.click("id=trialDTO.section801IndicatorNo");

        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyBaseTrialInfo(rand, nciID, category);
        verifyRegulatoryInfo();
        verifySponsor();
        verifyTrialConfValueMissing("Secondary Purpose:");
        verifyNonInterventionalInfo();

    }

    @Test
    public void testResponsiblePartyOptionsHandling() throws Exception {

        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);

        moveElementIntoView(By.id("trialDTO.responsiblePartyType"));
        selenium.select("trialDTO.responsiblePartyType",
                "label=Principal Investigator");
        assertTrue(selenium.isVisible("trialDTO.responsiblePersonName"));
        assertTrue(selenium.isVisible("trialDTO.responsiblePersonTitle"));
        assertTrue(selenium
                .isVisible("trialDTO.responsiblePersonAffiliationOrgName"));
        assertTrue(selenium.isVisible("affiliationLookupcell"));
        assertFalse(selenium.isVisible("investigatorlookupcell"));
        assertEquals("Doe,John",
                selenium.getValue("trialDTO.responsiblePersonName"));
        assertEquals("Principal Investigator",
                selenium.getValue("trialDTO.responsiblePersonTitle"));
        assertEquals(
                "Cancer Therapy Evaluation Program",
                selenium.getValue("trialDTO.responsiblePersonAffiliationOrgName"));
        WebElement we = driver.findElement(By
                .id("trialDTO.responsiblePersonName"));
        assertEquals("true", we.getAttribute("readonly"));
        we = driver.findElement(By
                .id("trialDTO.responsiblePersonAffiliationOrgName"));
        assertEquals("true", we.getAttribute("readonly"));

        selenium.select("trialDTO.responsiblePartyType",
                "label=Sponsor-Investigator");
        assertTrue(selenium.isVisible("trialDTO.responsiblePersonName"));
        assertTrue(selenium.isVisible("trialDTO.responsiblePersonTitle"));
        assertTrue(selenium
                .isVisible("trialDTO.responsiblePersonAffiliationOrgName"));
        assertFalse(selenium.isVisible("affiliationLookupcell"));
        assertTrue(selenium.isVisible("investigatorlookupcell"));
        assertEquals("Doe,John",
                selenium.getValue("trialDTO.responsiblePersonName"));
        assertEquals("Principal Investigator",
                selenium.getValue("trialDTO.responsiblePersonTitle"));
        assertEquals(
                "Cancer Therapy Evaluation Program",
                selenium.getValue("trialDTO.responsiblePersonAffiliationOrgName"));
        we = driver.findElement(By.id("trialDTO.responsiblePersonName"));
        assertEquals("true", we.getAttribute("readonly"));
        we = driver.findElement(By
                .id("trialDTO.responsiblePersonAffiliationOrgName"));
        assertEquals("true", we.getAttribute("readonly"));

        selenium.select("trialDTO.responsiblePartyType", "label=Sponsor");
        assertFalse(selenium.isVisible("trialDTO.responsiblePersonName"));
        assertFalse(selenium.isVisible("trialDTO.responsiblePersonTitle"));
        assertFalse(selenium
                .isVisible("trialDTO.responsiblePersonAffiliationOrgName"));
        assertFalse(selenium.isVisible("affiliationLookupcell"));
        assertFalse(selenium.isVisible("investigatorlookupcell"));

        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyTrialConfirmaionPage(rand, nciID, category);
        if ("Interventional".equalsIgnoreCase(getTrialConfValue("Trial Type:"))) {
            assertEquals("Yes", getTrialConfValue("Section 801 Indicator :"));
            assertEquals("No", getTrialConfValue("Delayed Posting Indicator :"));
        } else {
            assertEquals("No", getTrialConfValue("Section 801 Indicator :"));
        }

    }

    @Test
    public void testSponsorInvestigator() throws Exception {
        if (isPhantomJS()) {
            // having an odd issue in phantom js with frame switching.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);

        moveElementIntoView(By.id("trialDTO.responsiblePartyType"));
        selenium.select("trialDTO.responsiblePartyType",
                "label=Sponsor-Investigator");
        selenium.type("trialDTO.responsiblePersonTitle",
                "Principal Investigator!!");

        driver.switchTo().defaultContent();
        clickAndWaitAjax("xpath=//div[@id='investigatorlookupcell']//button");
        waitForElementById("popupFrame", 60);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("search_person_btn", 30);
        selenium.click("add_person_btn");
        selenium.type("poOrganizations_firstName", rand);
        selenium.type("poOrganizations_lastName", rand);
        selenium.type("poOrganizations_streetAddress", "123 Maint St");
        selenium.select("poOrganizations_orgStateSelect", "label=AL");
        selenium.type("poOrganizations_email", rand + "@example.com");
        selenium.type("poOrganizations_city", "Vienna");
        selenium.type("poOrganizations_zip", "22222");
        selenium.type("poOrganizations_phone", "555-555-5555");
        clickAndWaitAjax("xpath=//i[@class='fa-floppy-o']");
        waitForElementById("row", 15);
        moveElementIntoView(By
                .xpath("//table[@id='row']/tbody/tr[1]/td[8]/button"));
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();

        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyBaseTrialInfo(rand, nciID, category);
        verifyRegulatoryInfo();
        verifyInterventionalInfo();

        assertEquals("Cancer Therapy Evaluation Program",
                getTrialConfValue("Sponsor:"));
        assertEquals("Sponsor-Investigator",
                getTrialConfValue("Responsible Party:"));
        assertEquals(rand + ", " + rand, getTrialConfValue("Investigator"));
        assertEquals("Principal Investigator!!",
                getTrialConfValue("Investigator Title"));
        assertEquals("Cancer Therapy Evaluation Program",
                getTrialConfValue("Investigator Affiliation"));

    }

    @Test
    public void testGeneralValidation() throws Exception {
        loginAndAcceptDisclaimer();

        // Select register trial and choose trial type
        hoverLink("Register Trial");
        clickAndWait("link=National");
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 30);
        hideTopMenu();
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementToBecomeAvailable(By.className("alert-danger"), 30);
        pause(1000);
        assertTrue(selenium
                .isTextPresent("The form has errors and could not be submitted. Please check the fields highlighted below"));
        assertTrue(selenium
                .isTextPresent("Lead Organization Trial Identifier is required"));
        assertTrue(selenium.isTextPresent("Title is required"));
        assertTrue(selenium.isTextPresent("Please select a Phase"));
        assertTrue(selenium
                .isTextPresent("Please select an Accrual Disease Terminology"));
        assertTrue(selenium
                .isTextPresent("Please choose a 'Lead Organization' using the lookup"));
        assertTrue(selenium
                .isTextPresent("Please choose a 'Lead Principle Investigator' using the lookup"));
        assertTrue(selenium
                .isTextPresent("Please choose a 'Sponsor' using the lookup"));
        assertTrue(selenium.isTextPresent("Responsible Party is required"));
        assertTrue(selenium
                .isTextPresent("Select the Data Table 4 Funding Sponsor"));
        assertTrue(selenium.isTextPresent("Trial status history is required"));
        assertTrue(selenium.isTextPresent("Please enter a valid status"));
        assertTrue(selenium.isTextPresent("Please enter a valid date"));
        assertTrue(selenium.isTextPresent("Please enter a valid date type"));
        assertTrue(selenium
                .isTextPresent("Select the oversight authority country"));
        assertTrue(selenium.isTextPresent("Protocol Document is required"));
        assertTrue(selenium.isTextPresent("IRB Approval Document is required"));

    }

    @Test
    public void testPrincipalInvestigator() throws Exception {
        if (isPhantomJS()) {
            // having an odd issue in phantom js with frame switching.
            return;
        }
        loginAndAcceptDisclaimer();

        String rand = RandomStringUtils.randomNumeric(10);
        String title = "An Open-Label Study of Ruxolitinib " + rand;
        String leadOrgTrialId = "LEAD" + rand;
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        final String category = "National";
        populateRegisterNationalTrialScreen(title, leadOrgTrialId, rand,
                category);

        moveElementIntoView(By.id("trialDTO.responsiblePartyType"));
        selenium.select("trialDTO.responsiblePartyType",
                "label=Principal Investigator");
        selenium.type("trialDTO.responsiblePersonTitle",
                "Principal Investigator!!");

        driver.switchTo().defaultContent();
        clickAndWaitAjax("xpath=//div[@id='affiliationLookupcell']//button");
        waitForElementById("popupFrame", 60);
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgCtepIdSearch", "DCP");
        clickAndWaitAjax("id=search_organization_btn");
        waitForElementById("row", 15);
        moveElementIntoView(By
                .xpath("//table[@id='row']/tbody/tr[1]/td[9]/button"));
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        assertEquals(
                "National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("trialDTO.responsiblePersonAffiliationOrgName"));

        reviewAndSubmit();

        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        verifyBaseTrialInfo(rand, nciID, category);
        verifyRegulatoryInfo();
        if ("Interventional".equalsIgnoreCase(getTrialConfValue("Trial Type:"))) {
            assertEquals("Yes", getTrialConfValue("Section 801 Indicator :"));
            assertEquals("No", getTrialConfValue("Delayed Posting Indicator :"));
        } else {
            assertEquals("No", getTrialConfValue("Section 801 Indicator :"));
        }
        verifyInterventionalInfo();

        assertEquals("Cancer Therapy Evaluation Program",
                getTrialConfValue("Sponsor:"));
        assertEquals("Principal Investigator",
                getTrialConfValue("Responsible Party:"));
        assertEquals("Doe, John", getTrialConfValue("Investigator"));
        assertEquals("Principal Investigator!!",
                getTrialConfValue("Investigator Title"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                getTrialConfValue("Investigator Affiliation"));

    }

    private void verifyTrialConfValueMissing(String label) {
        try {
            getTrialConfValue(label);
            Assert.fail();
        } catch (SeleniumException e) {
        }
    }

    /**
     * Tests saving a draft trial.
     * 
     * @param nciID
     * @throws SQLException
     * @throws Exception
     *             on error
     */
    private void verifyTrialConfirmaionPage(String rand, String nciID,
            String category) throws SQLException {
        verifyBaseTrialInfo(rand, nciID, category);
        verifyRegulatoryInfo();
        verifySponsor();
        verifyInterventionalInfo();

    }

    /**
     * @param rand
     * @param nciID
     * @throws SQLException
     */
    protected void verifyBaseTrialInfo(String rand, String nciID,
            String category) throws SQLException {
        if (StringUtils.isNotBlank(nciID))
            assertEquals(nciID, getTrialConfValue("NCI Trial Identifier:"));

        assertEquals("LEAD" + rand,
                getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals("NCT" + rand,
                getTrialConfValue("ClinicalTrials.gov Identifier:"));

        if (isDraftConfPage())
            assertTrue(selenium
                    .isElementPresent("//td[normalize-space(text())='OTHER"
                            + rand + "']"));
        else
            assertTrue(selenium
                    .isElementPresent("//li[normalize-space(text())='OTHER"
                            + rand + "']"));

        assertEquals("An Open-Label Study of Ruxolitinib " + rand,
                getTrialConfValue("Title:"));
        assertEquals("0", getTrialConfValue("Phase:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));

        assertEquals("ICD10", getTrialConfValue("Accrual Disease Terminology:"));

        assertTrue("National Cancer Institute Division of Cancer Prevention (Your Affiliation)"
                .equals(getTrialConfValue("Lead Organization:"))
                || "National Cancer Institute Division of Cancer Prevention"
                        .equals(getTrialConfValue("Lead Organization:")));

        assertEquals("Doe,John", getTrialConfValue("Principal Investigator:")
                .replaceAll("\\s", ""));

        assertEquals(category,
                getTrialConfValue("Data Table 4 Funding Sponsor Type:"));
        assertEquals("National Cancer Institute",
                getTrialConfValue("Data Table 4 Funding Sponsor/Source:"));
        //assertEquals("PG" + rand, getTrialConfValue("Program code:"));

        verifyTrialStatus(nciID, "Approved");

        assertEquals(tommorrow + " Anticipated",
                getTrialConfValue("Trial Start Date:"));
        assertEquals(oneYearFromToday + " Anticipated",
                getTrialConfValue("Primary Completion Date:"));
        assertEquals(oneYearFromToday + " Anticipated",
                getTrialConfValue("Completion Date:"));

        // INDs
        assertEquals("IND",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[1]"));
        assertEquals(rand,
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[2]"));
        assertEquals("CDER",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[3]"));
        assertEquals("NIH",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[4]"));
        assertEquals("NEI-National Eye Institute",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[5]"));
        assertEquals("Yes",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[6]"));
        assertEquals("Available",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[7]"));
        assertEquals("Yes",
                selenium.getText("//div[@id='indideDiv']/table/tbody/tr/td[8]"));

        // Grants
        assertEquals("B09",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr/td[1]"));
        assertEquals("AA",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr/td[2]"));
        assertEquals(rand,
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr/td[3]"));
        assertEquals("CCR",
                selenium.getText("//div[@id='grantsDiv']/table/tbody/tr/td[4]"));

        verifyDocuments();
    }

    /**
     * 
     */
    protected void verifyDocuments() {
        // Documents
        assertEquals(
                "ProtocolDoc.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Protocol Document']]"));
        assertEquals(
                "Sites.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Participating sites']]"));
        assertEquals(
                "IrbDoc.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='IRB Approval Document']]"));
        assertEquals(
                "Consent.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Informed Consent Document']]"));
        assertEquals(
                "Other.doc",
                selenium.getText("//td[preceding-sibling::td[normalize-space(text())='Other']]"));
    }

    private boolean isDraftConfPage() {
        return selenium.getLocation().contains("partiallySubmittedView.action")
                || selenium
                        .isTextPresent("The trial draft has been successfully saved and assigned the Identifier");
    }

    /**
     * 
     */
    protected void verifyInterventionalInfo() {
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Ancillary", getTrialConfValue("Secondary Purpose:"));
    }

    /**
     * 
     */
    protected void verifyNonInterventionalInfo() {
        assertEquals("Non-Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Observational",
                getTrialConfValue("Non-interventional Trial Type:"));
        assertEquals("Other", getTrialConfValue("Study Model Code:"));
        assertEquals("Study Model Other",
                getTrialConfValue("If Study Model is 'Other', describe:"));
        assertEquals("Other", getTrialConfValue("Time Perspective Code:"));
        assertEquals("Time Other",
                getTrialConfValue("If Time Perspective is 'Other', describe:"));
    }

    /**
     * 
     */
    protected void verifyRegulatoryInfo() {
        // Regulatory
        assertEquals("United States",
                getTrialConfValue("Trial Oversight Authority Country :"));
        assertEquals(
                "Food and Drug Administration",
                getTrialConfValue("Trial Oversight Authority Organization Name :"));
        assertEquals("Yes",
                getTrialConfValue("FDA Regulated Intervention Indicator :"));

        assertEquals(
                "Yes",
                getTrialConfValue("Data Monitoring Committee Appointed Indicator :"));
    }

    /**
     * 
     */
    protected void verifySponsor() {
        assertEquals("Cancer Therapy Evaluation Program",
                getTrialConfValue("Sponsor:"));
        assertEquals("Sponsor", getTrialConfValue("Responsible Party:"));
    }

    /**
     * Tests Lookup of an organization with apostrophe.
     * 
     * @throws Exception
     *             on error
     */
    @Ignore("Assumes po is up and running. Needs to be fixed.")
    /*
     * public void lookupOrganization() throws Exception {
     * loginAndAcceptDisclaimer(); clickAndWaitAjax("registerTrialMenuOption");
     * selenium.selectFrame("popupFrame");
     * waitForElementById("summaryFourFundingCategoryCode", 60);
     * selenium.select("summaryFourFundingCategoryCode", "label=National");
     * clickAndWaitAjax("link=Submit"); waitForPageToLoad();
     * clickAndWaitAjax("link=Look Up Org"); waitForElementById("popupFrame",
     * 60); selenium.selectFrame("popupFrame");
     * clickAndWaitAjax("link=Add Org"); String orgName =
     * "PO-2098'test organization"; selenium.type("orgName", orgName);
     * selenium.type("orgAddress", "2115 E Jefferson St");
     * selenium.type("orgCity", "Rockville"); selenium.select("orgStateSelect",
     * "label=MD"); selenium.type("orgZip", "20852"); selenium.type("orgEmail",
     * "po2098@example.com"); clickAndWaitAjax("link=Save");
     * clickAndWaitAjax("link=Cancel"); clickAndWaitAjax("link=Close");
     * selenium.waitForPageToLoad("30000");
     * 
     * driver.switchTo().defaultContent(); clickAndWaitAjax("link=Look Up Org");
     * selenium.selectFrame("popupFrame"); selenium.type("orgNameSearch", "'");
     * clickAndWaitAjax("link=Search"); waitForElementById("row", 15);
     * assertTrue("Wrong search results returned",
     * selenium.isTextPresent("One item found"));
     * assertTrue("Wrong search results returned",
     * selenium.isTextPresent(orgName));
     * clickAndWait("//table[@id='row']/tbody/tr[1]/td[7]/a/span/span");
     * selenium.selectWindow(null); assertEquals("Wrong Principal investigator",
     * orgName, selenium.getValue("name=trialDTO.leadOrganizationName")); }
     */
    /**
     * Tests Lookup of a person with apostrophe.
     * @throws Exception on error
     */
    /*
     * public void lookupPerson() throws Exception { loginAndAcceptDisclaimer();
     * clickAndWaitAjax("registerTrialMenuOption");
     * selenium.selectFrame("popupFrame");
     * waitForElementById("summaryFourFundingCategoryCode", 60);
     * selenium.select("summaryFourFundingCategoryCode", "label=National");
     * clickAndWaitAjax("link=Submit"); waitForPageToLoad();
     * clickAndWaitAjax("link=Look Up Person"); waitForElementById("popupFrame",
     * 60); selenium.selectFrame("popupFrame");
     * clickAndWaitAjax("link=Add Person");
     * selenium.type("poOrganizations_firstName", "Michael");
     * selenium.type("poOrganizations_lastName", "O'Grady");
     * selenium.type("poOrganizations_streetAddress", "2115 E Jefferson St");
     * selenium.type("poOrganizations_city", "Rockville");
     * selenium.select("poOrganizations_orgStateSelect", "label=MD");
     * selenium.type("poOrganizations_zip", "20852");
     * selenium.type("poOrganizations_email", "mogrady@example.com");
     * clickAndWaitAjax("link=Save"); clickAndWaitAjax("link=Cancel");
     * clickAndWaitAjax("link=Close"); selenium.waitForPageToLoad("30000");
     * driver.switchTo().defaultContent();
     * clickAndWaitAjax("link=Look Up Person");
     * selenium.selectFrame("popupFrame"); selenium.type("lastName", "'");
     * clickAndWaitAjax("link=Search"); waitForElementById("row", 15);
     * assertTrue("Wrong search results returned",
     * selenium.isTextPresent("One item found"));
     * assertTrue("Wrong search results returned",
     * selenium.isTextPresent("O'Grady"));
     * clickAndWait("//table[@id='row']/tbody/tr[1]/td[6]/a/span/span");
     * selenium.selectWindow(null); assertEquals("Wrong Principal investigator",
     * "O'Grady,Michael", selenium.getValue("name=trialDTO.piName")); }
     */
    private String getNciIdViaSearch(String trialName) {

        clickAndWait("searchTrialsMenuOption");
        waitForElementById("searchMyTrialsBtn", 5);
        waitForElementById("searchAllTrialsBtn", 5);

        selenium.type("id=officialTitle", trialName);
        clickAndWait("id=searchAllTrialsBtn");
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]"));
        String nciId = selenium
                .getText("xpath=//table[@id='row']//tr[1]//td[1]");
        assertTrue(nciId.contains("NCI"));
        assertEquals(14, nciId.length());
        return nciId;
    }
    
    
    
   


}
