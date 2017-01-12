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

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.thoughtworks.selenium.SeleniumException;

/**
 * Abstract base class for selenium tests.
 * 
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@Ignore
@SuppressWarnings("deprecation")
public abstract class AbstractRegistrySeleniumTest extends
        AbstractPaSeleniumTest {

    protected static final FastDateFormat MONTH_DAY_YEAR_FMT = FastDateFormat
            .getInstance("MM/dd/yyyy");

    protected static final String PROTOCOL_DOCUMENT = "ProtocolDoc.doc";
    protected static final String IRB_DOCUMENT = "IrbDoc.doc";
    protected static final String IRB_UPDATED_DOCUMENT = "IrbUpdated.doc";
    protected static final String SITES_DOCUMENT = "Sites.doc";
    protected static final String CONSENT_DOCUMENT = "Consent.doc";
    protected static final String OTHER_DOCUMENT = "Other.doc";
    protected static final String ZERO_DOCUMENT = "Zero.doc";

    @Override
    protected void logoutUser() {
        openAndWait("/registry/login/logout.action");
    }

    @Override
    protected void login(String username, String password) {
        openAndWait("/registry");
        verifyLoginPage();
        clickAndWait("link=Sign In");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=loginButton");
        verifyDisclaimerPage();
    }

    public void loginAsAbstractor() {
        login("abstractor-ci", "pass");
    }

    public void loginAsSubmitter() {
        login(StringUtils.isNotBlank(System.getProperty("submitter.login")) ? System.getProperty("submitter.login")
                : "submitter-ci",
                StringUtils.isNotBlank(System.getProperty("submitter.password")) ? System
                        .getProperty("submitter.password") : "pass");
    }

    public void verifyDisclaimer() {
        loginAsAbstractor();
        handleDisclaimer(false);
        loginAsAbstractor();
        handleDisclaimer(true);
    }

    public void loginAndAcceptDisclaimer() {
        loginAsAbstractor();
        handleDisclaimer(true);
    }

    protected void handleDisclaimer(boolean accept) {
        verifyDisclaimerPage();
        if (accept) {
            clickAndWait("id=acceptDisclaimer");
            verifySearchPage();
        } else {
            clickAndWait("id=rejectDisclaimer");
            verifyLoginPage();
        }

    }

    protected void verifySearchPage() {
        assertTrue(selenium.isTextPresent("Search Clinical Trials"));
        assertTrue(selenium.isElementPresent("id=resetSearchBtn"));
        assertTrue(selenium.isElementPresent("id=runSearchBtn"));
    }

    protected void verifyLoginPage() {
        assertTrue(selenium.isTextPresent("Sign In"));
        assertTrue(selenium.isTextPresent("Contact Us"));
        assertTrue(selenium.isTextPresent("Policies"));
    }

    protected void verifyDisclaimerPage() {
        assertTrue(selenium.isElementPresent("id=acceptDisclaimer"));
        assertTrue(selenium.isElementPresent("id=rejectDisclaimer"));
    }

    protected boolean isLoggedIn() {
        return !selenium.isElementPresent("link=Sign In");
    }

    protected void registerTrial(String trialName, String leadOrgTrialId,
            String category) throws URISyntaxException, SQLException {
        registerTrial(trialName, leadOrgTrialId,
                RandomStringUtils.randomNumeric(10), category);
    }

    protected void registerTrial(final String rand, String category)
            throws URISyntaxException, SQLException {
        registerTrial(generateTrialTitle(rand), "LEAD" + rand, rand, category);
    }

    protected void registerTrial(String trialName, String leadOrgTrialId,
            final String rand, String category) throws URISyntaxException,
            SQLException {
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        registerTrialWithoutDeletingExistingOne(trialName, leadOrgTrialId,
                rand, category);
    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @throws URISyntaxException
     */
    protected void registerTrialWithoutDeletingExistingOne(String trialName,
            String leadOrgTrialId, final String rand, String category)
            throws URISyntaxException {
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand,
                category);
        reviewAndSubmit();

    }

    /**
     * 
     */
    protected void reviewAndSubmit() {
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 20);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();

    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @param rand
     * @throws URISyntaxException
     */
    protected void populateRegisterNationalTrialScreen(String trialName,
            String leadOrgTrialId, final String rand, final String category)
            throws URISyntaxException {
        today = MONTH_DAY_YEAR_FMT.format(new Date());
        tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(
                new Date(), 1));

        // Select register trial and choose trial type
        hoverLink("Register Trial");
        pause(500);
        clickAndWait("link=" + category);
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 30);

        populateFieldsWithTrialData(trialName, leadOrgTrialId, rand);
    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @param rand
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
    protected void populateFieldsWithTrialData(String trialName,
            String leadOrgTrialId, final String rand) throws URISyntaxException {
        hideTopMenu();

        selenium.click("id=xmlRequiredtrue");
        hover(By.xpath("//i[preceding-sibling::input[@id='xmlRequiredfalse']]"));
        assertTrue(selenium
                .isTextPresent("Indicate whether you need an XML file "
                        + "to submit/update your trial to ClinicalTrials.gov"));

        selenium.type("trialDTO.leadOrgTrialIdentifier", leadOrgTrialId);

        selenium.type("trialDTO.nctIdentifier", "NCT" + rand);
        final String nctID = "OTHER" + rand;
        s.type("otherIdentifierOrg", nctID);
        clickAndWaitAjax("id=otherIdbtnid");
        waitForElementToBecomeVisible(
                By.xpath("//div[@id='otherIdentifierdiv']//tr/td[normalize-space(text())='"
                        + nctID + "']"), 15);

        selenium.type("trialDTO.officialTitle", trialName);
        selenium.select("trialDTO.phaseCode", "label=0");
        selenium.click("id=trialDTO.trialType.Interventional");
        selenium.select("trialDTO.primaryPurposeCode", "label=Treatment");
        selenium.select("trialDTO.secondaryPurposes", "label=Ancillary");
        selenium.select("trialDTO.accrualDiseaseCodeSystem", "label=ICD10");

        // Select Lead Organization
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=National Cancer Institute Division of Cancer Prevention (Your Affiliation)");

        // Select Principal Investigator
        driver.switchTo().defaultContent();
        clickAndWaitAjax("xpath=//div[@id='loadPersField']//button");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);
        selenium.type("firstName", "John");
        selenium.type("lastName", "Doe");
        clickAndWaitAjax("id=search_person_btn");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();

        // Select Sponsor
        driver.switchTo().defaultContent();
        hover(By.id("trialDTO.sponsorNameField"));
        clickAndWaitAjax("xpath=//table[@id='dropdown-sponsorOrganization']//a[text()='Cancer Therapy Evaluation Program']");

        selenium.select("trialDTO.responsiblePartyType", "label=Sponsor");

        // Select Funding Sponsor
        hover(By.id("trialDTO.summaryFourOrgName"));
        clickAndWaitAjax("xpath=//table[@id='dropdown-sum4Organization']//a[text()='National Cancer Institute']");
        // selenium.type("trialDTO.programCodeText", "PG" + rand);

        // Grants
        moveElementIntoView(By.id("nciGrantfalse"));
        selenium.click("nciGrantfalse");
        selenium.select("fundingMechanismCode", "label=B09");
        selenium.select("nihInstitutionCode", "label=AA");
        selenium.type("serialNumber", rand);
        selenium.select("nciDivisionProgramCode", "label=CCR");
        selenium.click("grantbtnid");
        waitForElementById("grantdiv", 5);
        driver.switchTo().defaultContent();
        populateStatusHistory();

        selenium.type("trialDTO_startDate", tommorrow);
        selenium.click("trialDTO_startDateTypeAnticipated");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", oneYearFromToday);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", oneYearFromToday);
        assertFalse(s
                .isElementPresent("xpath=//input[@type='radio' and @value='N/A']"));

        // IND/IDE
        moveElementIntoView(By.id("group3"));
        selenium.select("group3", "label=IND");
        selenium.type("id=indidenumber", rand);
        selenium.click("id=SubCat");
        selenium.select("id=SubCat", "label=CDER");
        selenium.select("holderType", "label=NIH");
        selenium.select("programcodenihselectedvalue",
                "label=NEI-National Eye Institute");
        moveElementIntoView(By.id("group4"));
        driver.findElement(By.id("group4")).sendKeys(" ");
        // selenium.click("id=group4");
        selenium.select("expanded_status", "label=Available");
        selenium.click("exemptIndicator");
        moveElementIntoView(By.id("addbtn"));
        selenium.click("addbtn");
        waitForElementById("indidediv", 5);

        // Regulator Information
        moveElementIntoView(By.id("countries"));
        selenium.select("countries", "label=United States");
        waitForElementToBecomeVisible(By.id("trialDTO.selectedRegAuth"), 15);
        moveElementIntoView(By.id("trialDTO.selectedRegAuth"));
        pause(1000);
        selenium.select("trialDTO.selectedRegAuth",
                "label=Food and Drug Administration");
        assertFalse(driver.findElement(
                By.id("trialDTO.delayedPostingIndicatorNo")).isDisplayed());
        moveElementIntoView(By
                .id("trialDTO.fdaRegulatoryInformationIndicatorYes"));
        selenium.click("trialDTO.fdaRegulatoryInformationIndicatorYes");
        selenium.click("trialDTO.section801IndicatorYes");
        assertFalse(driver.findElement(
                By.id("trialDTO.delayedPostingIndicatorNo")).isEnabled());
        assertTrue(driver.findElement(
                By.id("trialDTO.delayedPostingIndicatorNo")).isSelected());
        selenium.click("trialDTO.dataMonitoringCommitteeAppointedIndicatorYes");

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
        selenium.type("submitTrial_otherDocument_0", (new File(ClassLoader
                .getSystemResource(OTHER_DOCUMENT).toURI()).toString()));
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    protected void populateStatusHistory() {
        addStatus("", "In Review", "");

        // Add a comment to In Review.
        selenium.click("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[1]/td[5]/i[@class='fa fa-edit']");
        selenium.type("editComment", "This is initial status");
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Save']");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr[1]/td[position()=3 and text()='This is initial status']"),
                10);

        // Add Active, ensure warning, and delete.
        addStatus("", "Active", "");
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[@class='warning']"));
        deleteStatus(2);

        // Add Temporarily Closed to Accrual and Intervention, ensure errors,
        // ensure unable to submit,
        // and delete.
        addStatus("", "Temporarily Closed to Accrual and Intervention",
                "Put on hold for some reason.");
        assertEquals(
                "Statuses [IN REVIEW] and [TEMPORARILY CLOSED TO ACCRUAL AND INTERVENTION] can not have the same date",
                selenium.getText("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=1 and @class='error']"));
        assertEquals(
                "Interim status [ACTIVE] is missing",
                selenium.getText("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=2 and @class='error']"));
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=3 and @class='warning']"));
        assertEquals(
                "Interim status [TEMPORARILY CLOSED TO ACCRUAL] is missing",
                selenium.getText("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=4 and @class='warning']"));
        hover(By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr[2]/td[5]/img[@rel='popover']"));
        waitForElementToBecomeAvailable(
                By.xpath("//div[@class='popover fade top in']"), 5);
        assertEquals("Put on hold for some reason.",
                selenium.getText("xpath=//div[@class='popover fade top in']"));
        selenium.click("xpath=//button[text()='Review Trial']");
        waitForElementToBecomeVisible(By.id("transitionErrorsWarnings"), 10);
        assertEquals(
                "Status Transition Errors and Warnings were found. This trial cannot be submitted until all Status Transition Errors have been resolved. Please use the action icons below to make corrections.",
                selenium.getText("transitionErrorsWarnings")
                        .replaceAll("\\s+", " ").trim());
        deleteStatus(2);

        // Change In Review to Approved.
        editStatus(1, "", "Approved", "Changed to Approved.");

        // Add In Review with yesterday's date.
        addStatus(yesterday, "In Review", "Yesterday's status");

    }

    protected void editStatus(int row, String date, String newStatus,
            String comment) {
        selenium.click("xpath=//table[@id='trialStatusHistoryTable']/tbody/tr["
                + row + "]/td[5]/i[@class='fa fa-edit']");
        waitForElementToBecomeVisible(By.id("dialog-edit"), 5);
        assertEquals("Edit Trial Status",
                selenium.getText("ui-dialog-title-dialog-edit"));
        if (StringUtils.isNotBlank(date)) {
            selenium.type("statusDate", date);
        } else {
            selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='statusDate']]");
            clickOnFirstVisible(By.xpath("//td[@class='day active']"));
            clickOnFirstVisible(By
                    .xpath("//div[@class='datepicker']/button[@class='close']"));
        }
        selenium.select("statusCode", "label=" + newStatus);
        selenium.type("editComment", comment);
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Save']");
        waitForElementToBecomeInvisible(By.id("dialog-edit"), 10);
        waitForElementToBecomeInvisible(By.id("indicator"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=1 and text()='"
                        + (StringUtils.isNotBlank(date) ? date : today) + "']"),
                10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=2 and text()='" + newStatus
                        + "']"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=3 and text()='" + comment
                        + "']"), 10);

    }

    @SuppressWarnings("deprecation")
    protected void deleteStatus(int row) {
        final String trashIconPath = "//table[@id='trialStatusHistoryTable']/tbody/tr[" + row
                        + "]/td[5]/i[@class='fa fa-trash-o']";
        waitForElementToBecomeAvailable(By.xpath(trashIconPath), 15);
        moveElementIntoView(By.xpath(trashIconPath));
        selenium.click("xpath=" + trashIconPath);
        waitForElementToBecomeVisible(By.id("dialog-delete"), 5);
        assertEquals("Please provide a comment",
                selenium.getText("ui-dialog-title-dialog-delete"));
        assertEquals(
                "Please provide a comment explaining why you are deleting this trial status:",
                selenium.getText("xpath=//div[@id='dialog-delete']/p")
                        .replaceAll("\\s+", " ").trim());
        selenium.type("deleteComment", "Wrong status");
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Delete']");
        if (row == 1) {
            waitForElementToBecomeAvailable(
                    By.xpath("//table[@id='trialStatusHistoryTable']//td[@class='dataTables_empty']"),
                    10);
        } else {
            waitForElementToGoAway(
                    By.xpath("//table[@id='trialStatusHistoryTable']/tbody/tr["
                            + row + "]"), 10);
        }
    }

    @SuppressWarnings("deprecation")
    protected void addStatus(String date, String status, String whyStopped) {
        selenium.select("trialDTO_statusCode", "label=" + status);
        selenium.type("trialDTO_reason", whyStopped);
        if (StringUtils.isNotBlank(date)) {
            selenium.type("trialDTO_statusDate", date);
        } else {
            final String path = "//span[@class='add-on btn-default' and preceding-sibling::input[@id='trialDTO_statusDate']]";
            moveElementIntoView(By.xpath(path));
            selenium.click("xpath="+path);
            clickOnFirstVisible(By.xpath("//td[@class='day active']"));
            clickOnFirstVisible(By
                    .xpath("//div[@class='datepicker']/button[@class='close']"));
        }
        moveElementIntoView(By.id("addStatusBtn"));
        clickAndWaitAjax("id=addStatusBtn");
        waitForElementToBecomeVisible(By.id("trialStatusHistoryTable"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='trialStatusHistoryTable']/tbody//tr//td[text()='"
                        + status + "']"), 10);
    }

    /**
     * @throws SQLException
     * 
     */
    protected void verifyTrialStatus(String nciID, String expected)
            throws SQLException {
        assertEquals(expected, getTrialConfValue("Current Trial Status:"));
        assertEquals("", getTrialConfValue("Why the Study Stopped:"));
        assertEquals(today, getTrialConfValue("Current Trial Status Date:"));

        // Ensure status history of two statuses (In Review, Approved) created.
        if (StringUtils.isNotBlank(nciID)) {
            TrialInfo info = new TrialInfo();
            info.nciID = nciID;
            List<TrialStatus> hist = getTrialStatusHistory(info);
            assertEquals(2, hist.size());

            assertTrue(DateUtils.isSameDay(hist.get(0).statusDate,
                    yesterdayDate));
            assertEquals("IN_REVIEW", hist.get(0).statusCode);
            assertTrue(StringUtils.isBlank(hist.get(0).comments));

            assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
            assertEquals(expected.toUpperCase(), hist.get(1).statusCode);
            assertEquals("Changed to " + expected + ".", hist.get(1).comments);
        }
    }

    protected void hideTopMenu() {
        ((JavascriptExecutor) driver).executeScript("$('nav').hide();");

    }

    @SuppressWarnings("deprecation")
    protected String getTrialConfValue(String labeltxt) {
        try {
            return selenium
                    .getText("//div[preceding-sibling::label[normalize-space(text())=\""
                            + labeltxt + "\"]]");

        } catch (SeleniumException e) {
            return selenium
                    .getText("//div[preceding-sibling::label/strong[normalize-space(text())='"
                            + labeltxt + "']/..]");
        }
    }

    protected String getUIRowValue(String labeltxt) {
        return driver
                .findElement(
                        By.xpath("//div[@class='row']/span[@class='label'][normalize-space(.) = '"
                                + labeltxt + "']/following::span")).getText();
    }

    /**
     * @throws URISyntaxException
     * @throws SQLException
     */
    protected TrialInfo registerAndAcceptTrial(String rand)
            throws URISyntaxException, SQLException {

        registerTrial(rand, "National");
        final String nciID = getLastNciId();
        assertTrue(
                "No success message found",
                selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "
                        + nciID));
        TrialInfo info = acceptTrialByNciId(nciID, "LEAD" + rand);
        info.rand = rand;
        info.title = generateTrialTitle(rand);
        info.uuid = rand;
        return info;
    }

    /**
     * @param rand
     * @return
     */
    private String generateTrialTitle(String rand) {
        return "An Open-Label Study of Ruxolitinib " + rand;
    }

    /**
     * 
     */
    protected void selectAction(String action) {
        selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        clickAndWait("xpath=//ul[@id='actmenu']/li/a[normalize-space(text())='"
                + action + "']");
        hideTopMenu();
    }

    /**
     * 
     */
    protected void accessTrialSearchScreen() {
        hoverLink("Search");
        pause(1500);
        clickAndWait("xpath=//a[text()='Clinical Trials']");
        waitForElementById("resetSearchBtn", 5);
    }

    /**
     * @param fieldID
     * @param value
     * @param info
     */
    protected void runSearchAndVerifySingleTrialResult(String fieldID,
            String value, TrialInfo info) {

        accessTrialSearchScreen();
        selenium.type(fieldID, value);

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
        assertEquals(info.title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]"));
        assertEquals("ClinicalTrials.gov",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals(info.leadOrgID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]"));
        assertEquals("Doe, John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[6]"));
        assertEquals(
                "View",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[9]/a"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']"));
    }

    /**
     * @param nciID
     */
    protected void searchForTrialByNciID(final String nciID) {
        accessTrialSearchScreen();
        selenium.type("identifier", nciID);
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);
    }

    /**
     * 
     */
    protected final void findInMyTrials() {
        findInMyTrials("");
    }

    /**
     * 
     */
    protected final void findInMyTrials(String id) {
        loginAsSubmitter();
        handleDisclaimer(true);
        accessTrialSearchScreen();
        if (StringUtils.isNotBlank(id))
            s.type("identifier", id);
        selenium.click("runSearchBtn");
        clickAndWait("link=My Trials");
        waitForElementById("row", 20);
    }

    /**
     * @param params
     */
    protected final void submitTrialAndVerifyOpenSitesDialog(String[] params,
            String buttonToClick, boolean isEarlierDay) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String earlierDate = MONTH_DAY_YEAR_FMT.format(calendar.getTime());

        s.click("xpath=//button[text()='" + buttonToClick + "']");
        waitForElementToBecomeVisible(By.id("dialog-opensites"), 10);
        assertEquals("The trial has open sites",
                s.getText("ui-dialog-title-dialog-opensites"));
        assertEquals(
                "Since you are closing the trial, all open sites will be closed as well as a result. "
                        + "For your information, below is a list of currently open sites that will be affected by this operation.",
                s.getText("//div[@id='dialog-opensites']/p")
                        .replaceAll("\\s+", " ").trim());
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='openSitesTable']/tbody/tr[2]"), 10);
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='openSitesTable']/tbody/tr[2]"), 5);

        assertEquals("PO ID",
                s.getText("//table[@id='openSitesTable']/thead/tr/th[1]"));
        assertEquals("Name",
                s.getText("//table[@id='openSitesTable']/thead/tr/th[2]"));
        assertEquals("Status",
                s.getText("//table[@id='openSitesTable']/thead/tr/th[3]"));
        assertEquals("Status Date",
                s.getText("//table[@id='openSitesTable']/thead/tr/th[4]"));
        assertTrue(s.isTextPresent("Showing 1 to 2 of 2 entries"));

        s.click("//table[@id='openSitesTable']/thead/tr/th[1]");
        pause(2000);

        assertEquals("3",
                s.getText("//table[@id='openSitesTable']/tbody/tr[1]/td[1]"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                s.getText("//table[@id='openSitesTable']/tbody/tr[1]/td[2]"));
        assertEquals(params[0],
                s.getText("//table[@id='openSitesTable']/tbody/tr[1]/td[3]"));
        if (!isEarlierDay) {
            assertEquals(
                    today,
                    s.getText("//table[@id='openSitesTable']/tbody/tr[1]/td[4]"));
        } else {

            assertEquals(
                    earlierDate,
                    s.getText("//table[@id='openSitesTable']/tbody/tr[1]/td[4]"));
        }

        assertEquals("2",
                s.getText("//table[@id='openSitesTable']/tbody/tr[2]/td[1]"));
        assertEquals("Cancer Therapy Evaluation Program",
                s.getText("//table[@id='openSitesTable']/tbody/tr[2]/td[2]"));
        assertEquals(params[1],
                s.getText("//table[@id='openSitesTable']/tbody/tr[2]/td[3]"));

        if (!isEarlierDay) {
            assertEquals(
                    today,
                    s.getText("//table[@id='openSitesTable']/tbody/tr[2]/td[4]"));
        } else {
            assertEquals(
                    earlierDate,
                    s.getText("//table[@id='openSitesTable']/tbody/tr[2]/td[4]"));
        }

        // Test Cancel button.
        s.click("//div[@aria-labelledby='ui-dialog-title-dialog-opensites']//span[text()='Cancel']");
        waitForElementToBecomeInvisible(By.id("dialog-opensites"), 3);

        // Bring the dialog back again and submit the update.
        s.click("xpath=//button[text()='" + buttonToClick + "']");
        waitForElementToBecomeVisible(By.id("dialog-opensites"), 10);
        s.click("//div[@aria-labelledby='ui-dialog-title-dialog-opensites']//span[text()='Proceed']");
    }

    protected void changeRegUserAffiliation(String loginName, int orgPoId,
            String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update registry_user set affiliate_org='"
                + orgName
                + "', affiliated_org_id="
                + orgPoId
                + " where csm_user_id=(select user_id from csm_user where csm_user.login_name like '%"
                + loginName + "%')";
        runner.update(connection, sql);

    }

    protected Number findParticipatingSite(TrialInfo trial, String orgName,
            String localID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "SELECT ss.identifier FROM "
                + "("
                + "   ("
                + "      study_site ss"
                + "      JOIN healthcare_facility ro ON"
                + "      ("
                + "         (ro.identifier = ss.healthcare_facility_identifier)"
                + "      )"
                + "   )"
                + "   JOIN organization org ON ((org.identifier = ro.organization_identifier))"
                + ")" + "WHERE ss.local_sp_indentifier='" + localID
                + "' AND org.name='" + orgName
                + "' AND ((ss.functional_code)::text = 'TREATING_SITE'::text)";

        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number siteID = results != null ? (Number) results[0] : null;
        return siteID;
    }

    protected void addToSiteStatusHistory(Number siteID, String statusCode,
            Timestamp date) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_site_accrual_status (identifier,status_code,status_date,study_site_identifier) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')), '"
                + statusCode + "', '" + date.toString() + "'," + siteID + " )";
        runner.update(connection, sql);
    }

    protected String date(int offsetFromToday) {
        return DateFormatUtils.format(
                DateUtils.addDays(new Date(), offsetFromToday), "MM/dd/yyyy");
    }

    protected void removeProgramCodesFromTrial(Long studyProtocolId)
            throws Exception {
        QueryRunner runner = new QueryRunner();
        runner.update(connection,
                "delete from study_program_code where study_protocol_id = "
                        + studyProtocolId);
    }

    protected List<String> getProgramCodesByTrial(Long studyProtocolId)
            throws Exception {
        QueryRunner runner = new QueryRunner();
        List<Object[]> results = runner
                .query(connection,
                        "select pc.program_code from program_code pc "
                                + "join study_program_code sp on sp.program_code_id = pc.identifier "
                                + "where sp.study_protocol_id = "
                                + studyProtocolId, new ArrayListHandler());
        List<String> list = new ArrayList<String>();
        if (results != null) {
            for (Object[] o : results) {
                list.add(String.valueOf(o[0]));
            }
        }
        return list;
    }

    protected void moveBackTrialStatusDate(TrialInfo trialInfo, int days) throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, String.format("update study_overall_status  set status_date  = status_date - interval " +
                "'%s' day where study_protocol_identifier = %s", days, trialInfo.id));
    }
    
    protected void moveForwardTrialStatusDate(TrialInfo trialInfo, int days) throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, String.format("update study_overall_status  set status_date  = status_date + interval " +
                "'%s' day where study_protocol_identifier = %s", days, trialInfo.id));
    }


}
