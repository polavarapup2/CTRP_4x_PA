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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests trial search in Registry, as well as search-related functionality.
 * 
 * @author dkrylov
 */
@SuppressWarnings("deprecation")
@Batch(number = 2)
public class AddUpdateSiteTest extends AbstractRegistrySeleniumTest {


    @Override
    public void setUp() throws  Exception {
        super.setUp();
        setupFamilies();
    }


    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_SingleSiteFromFamily() throws URISyntaxException,
            SQLException {
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "In Review" , false);

        findInMyTrials();
        invokeAction("Update My Site");

        // Since there is only one site from the family on this trial at this
        // point, we should have gone straight
        // to updating it.
        waitForElementById("organizationName", 15);
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
        assertEquals("false",selenium.getValue("//input[@name='addSitesMultiple']"));
        driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();
        waitForTextToAppear(By.className("alert-success"),
                "Message: Your site information has been updated.", 20);

    }


    @SuppressWarnings("deprecation")
    @Test
    public void testAddProgramCode() throws Exception {
        //Given a trial associated with DCP
        TrialInfo info = createAndSelectTrial();
        //And no program codes associated with the Trial
        removeProgramCodesFromTrial(info.id);

        //when I add a site and Program Code PG1
        addMySiteAndVerify(info, true);
        
        //Then I see in DATABASE PG1 associated with the study
        List<String> codes = getProgramCodesByTrial(info.id);
        assertTrue(codes.contains("PG1"));
    }

        @SuppressWarnings("deprecation")
    @Test
    public void testUpdateProgramCode() throws
            Exception {
        //Given a trial associated with DCP
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "In Review" , false);

        //And the trial is not associated with Program Codes
        removeProgramCodesFromTrial(info.id);

        //When I search for the trial as abstractor
        findInMyTrials();

        //I must see update site option
        invokeAction("Update My Site");

        //And I see DCP as the site
        waitForElementById("organizationName", 15);
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));

        //When I select PG1 and PG2
        useSelect2ToPickAnOption("programCode","PG1","PG1 - Cancer Program1");
        useSelect2ToPickAnOption("programCode","PG2","PG2 - Cancer Program2");

        //and hit save
        driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();

        //I see the message that the study site is updated
        waitForTextToAppear(By.className("alert-success"),
                "Message: Your site information has been updated.", 20);

        List<String> codes = getProgramCodesByTrial(info.id);
        assertTrue(codes.size() == 2);
        assertTrue(codes.contains("PG1"));
        assertTrue(codes.contains("PG2"));

        logoutUser();

        //Now I login again again as abstractor
        findInMyTrials();

        //and click on update stie
        invokeAction("Update My Site");

        waitForElementById("organizationName", 15);
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
        assertFalse(selenium.isTextPresent("Manage Program Codes"));

        //add PG3 to the list of options
        useSelect2ToPickAnOption("programCode","PG3","PG3 - Cancer Program3");

        //remove previously selected PG2
        useSelect2ToUnselectOption("PG2 - Cancer Program2");

        //and hit save
        driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();

        //I see the message that the study site is updated
        waitForTextToAppear(By.className("alert-success"),
                "Message: Your site information has been updated.", 20);


        //And in the database I see PG1 and PG3
        codes = getProgramCodesByTrial(info.id);
        assertTrue(codes.size() == 2);
        assertTrue(codes.contains("PG1"));
        assertTrue(codes.contains("PG3"));

        //and PG2 is no longer associated with study
        assertFalse(codes.contains("PG2"));

        logoutUser();

        //Now login as user having site admin role
        loginAsAbstractor();
        handleDisclaimer(true);

        //find the trial
        accessTrialSearchScreen();
        s.type("identifier", info.nciID);
        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 20);

        //and click on update stie
        invokeAction("Update My Site");

        waitForElementById("organizationName", 15);
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
        assertTrue(selenium.isTextPresent("Manage Program Codes"));

        logoutUser();


    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO_8615_StatusHistoryIsValidatedUponEntrance()
            throws URISyntaxException, SQLException {
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "In Review", false);

        addToSiteStatusHistory(
                findParticipatingSite(
                        info,
                        "National Cancer Institute Division of Cancer Prevention",
                        info.uuid.substring(0, 20)), "ENROLLING_BY_INVITATION",
                new Timestamp(System.currentTimeMillis()));

        findInMyTrials();
        invokeAction("Update My Site");
        waitForElementById("organizationName", 15);
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
        waitForElementToBecomeVisible(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]"),
                15);
        pause(5000);
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAddMySite_PO9100PickASiteFromMultipleOrgs()
            throws URISyntaxException, SQLException {
        // User can add more than 1 org as site, in this case the Pick Site
        // selection screen appears first
        TrialInfo info = createAndSelectTrial();
        addMySiteAndVerify(info, false);

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO9100_AddSiteSingleOrganization()
            throws URISyntaxException, SQLException {
        // User can only add 1 org as site, as other orgs(CTEP,NCI) are
        // associated with a site.
        TrialInfo info = createAndSelectTrial();
        addSiteToTrial(info, "CTEP", "In Review" , false);
        addSiteToTrial(info, "NCI", "In Review" , false);
        addMySiteAndVerify_SingleOrganization(info);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateMySite() throws URISyntaxException, SQLException {
        TrialInfo info = createAndSelectTrial();
        addMySiteAndVerify(info, false);
        logoutUser();

        findInMyTrials();
        invokeAction("Update My Site");
        verifyTrialData(info);

        // Populate fields.
        s.type("localIdentifier", "DCP_SITE_U");
        pickInvestigator();

        deleteStatus(info, 2);
        deleteStatus(info, 1);

        // Check validation.
        clickAndWait("xpath=//button[text()='Save']");
        assertTrue(s
                .isTextPresent("A valid Recruitment Status Date is required"));
        assertTrue(s
                .isTextPresent("Please enter a value for Recruitment Status"));

        populateStatusHistory(info);

        s.click("xpath=//button/i[@class='fa-floppy-o']");
        driver.switchTo().defaultContent();

        // Check results.
        if (isPhantomJS()) {
            waitForPageToLoad();
            pause(10000);
            assertTrue(s
                    .isTextPresent("Message: Your site information has been updated."));
        } else {
            waitForTextToAppear(By.className("alert-success"),
                    "Message: Your site information has been updated.", 10);
        }
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[1]"));
        assertEquals("Doe,John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[2]"));
        assertEquals("DCP_SITE_U",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[3]"));

        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[4]"));
        assertEquals(today,
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]"));

        // Status history.
        verifySiteStatusHistory(info, "DCP_SITE_U");

        // Deleted status history.
        verifyDeletedSiteStatusHistory(info, "DCP_SITE_U");

    }

    /**
     * @param info
     * @throws SQLException
     */
    private void addMySiteAndVerify(TrialInfo info, boolean addProgramCode) throws SQLException {

        assignTrialOwner("submitter-ci", info.id);
        findInMyTrials();
        invokeAction("Add My Site");
        final String addSitesMultipleID = "//input[@name='addSitesMultiple']";
        
        waitForElementToBecomeAvailable(By.xpath(addSitesMultipleID), 10);
        waitForElementToBecomeAvailable(By.id("pickedSiteOrgPoId"), 10);
        
        assertEquals("true",selenium.getValue(addSitesMultipleID));
        assertWeAreOnPickASiteSelectionScreen();
        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(3, options.length);
        selenium.select("pickedSiteOrgPoId",
                "label=National Cancer Institute Division of Cancer Prevention");
        clickAndWait("pickSiteBtn");
        assertEquals("true",selenium.getValue(addSitesMultipleID));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));

        
        verifyTrialData(info);

        // Check validation.
        clickAndWait("xpath=//button[text()='Save']");
        assertEquals(
                "Local Trial Identifier is required",
                s.getText("xpath=//span[@class='alert-danger' and preceding-sibling::input[@id='localIdentifier']]"));
        assertEquals(
                "Please choose a Site Principal Investigator using the lookup",
                s.getText("xpath=//span[@class='alert-danger' and preceding-sibling::input[@id='investigator']]"));
        assertTrue(s
                .isTextPresent("A valid Recruitment Status Date is required"));
        assertTrue(s
                .isTextPresent("Please enter a value for Recruitment Status"));

        // Populate fields.
        final String localID = "DCP_SITE";
        s.type("localIdentifier", localID);

        // Investigator.
        pickInvestigator();
        useSelect2ToPickAnOption("programCode","PG1","PG1 - Cancer Program1");

        populateStatusHistory(info);

        s.click("xpath=//button/i[@class='fa-floppy-o']");
        driver.switchTo().defaultContent();

        // Check results.
        waitForTextToAppear(By.className("alert-success"),
                "Message: Your site has been added to the trial.", 10);

        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[1]"));
        assertEquals("Doe,John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[2]"));
        assertEquals(localID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[3]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[4]"));
        assertEquals(today,
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]"));

        verifySiteStatusHistory(info, localID);
    }

    private void addMySiteAndVerify_SingleOrganization(TrialInfo info)
            throws SQLException {
        assignTrialOwner("submitter-ci", info.id);
        findInMyTrials();
        invokeAction("Add My Site");
        waitForElementById("organizationName", 15);
        assertEquals("false",selenium.getValue("//input[@name='addSitesMultiple']"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));

        verifyTrialData(info);

        // Check validation.
        clickAndWait("xpath=//button[text()='Save']");
        assertEquals(
                "Local Trial Identifier is required",
                s.getText("xpath=//span[@class='alert-danger' and preceding-sibling::input[@id='localIdentifier']]"));
        assertEquals(
                "Please choose a Site Principal Investigator using the lookup",
                s.getText("xpath=//span[@class='alert-danger' and preceding-sibling::input[@id='investigator']]"));
        assertTrue(s
                .isTextPresent("A valid Recruitment Status Date is required"));
        assertTrue(s
                .isTextPresent("Please enter a value for Recruitment Status"));

        // Populate fields.
        final String localID = "DCP_SITE";
        s.type("localIdentifier", localID);

        // Investigator.
        pickInvestigator();

        useSelect2ToPickAnOption("programCode","PG1","PG1 - Cancer Program1");


        populateStatusHistory(info);

        s.click("xpath=//button/i[@class='fa-floppy-o']");
        driver.switchTo().defaultContent();

        // Check results.
        waitForTextToAppear(By.className("alert-success"),
                "Message: Your site has been added to the trial.", 10);

        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[1]"));
        assertEquals("Doe,John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[2]"));
        assertEquals(localID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[3]"));        
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[4]"));
        assertEquals(today,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[3]/td[5]"));

        verifySiteStatusHistory(info, localID);

    }

    /**
     * @param info
     * @param localID
     * @throws SQLException
     */
    private void verifySiteStatusHistory(TrialInfo info, final String localID)
            throws SQLException {
        final Number siteID = findParticipatingSite(info,
                "National Cancer Institute Division of Cancer Prevention",
                localID);
        assertNotNull(siteID);

        // Ensure status history properly created.
        List<SiteStatus> hist = getSiteStatusHistory(siteID);
        assertEquals(2, hist.size());

        assertTrue(DateUtils.isSameDay(hist.get(0).statusDate, yesterdayDate));
        assertEquals("IN_REVIEW", hist.get(0).statusCode);
        assertTrue(StringUtils.isBlank(hist.get(0).comments));

        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals("Approved".toUpperCase(), hist.get(1).statusCode);
        assertEquals("Changed to Approved.", hist.get(1).comments);
    }

    /**
     * @param info
     * @param localID
     * @throws SQLException
     */
    private void verifyDeletedSiteStatusHistory(TrialInfo info,
            final String localID) throws SQLException {
        final Number siteID = findParticipatingSite(info,
                "National Cancer Institute Division of Cancer Prevention",
                localID);
        assertNotNull(siteID);

        // Ensure status history properly created.
        List<SiteStatus> hist = getDeletedSiteStatusHistory(siteID);
        assertEquals(2, hist.size());

        assertTrue(DateUtils.isSameDay(hist.get(0).statusDate, yesterdayDate));
        assertEquals("IN_REVIEW", hist.get(0).statusCode);
        assertEquals("Wrong status", hist.get(0).comments);

        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals("Approved".toUpperCase(), hist.get(1).statusCode);
        assertEquals("Wrong status", hist.get(1).comments);
    }

    /**
     * 
     */
    private void pickInvestigator() {
        s.click("xpath=//button/i[@class='fa-search']");
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));   
        waitForElementToBecomeAvailable(By.id("search_person_btn"), 10);
        s.type("perCtepIdSearch", "JDOE01");
        s.click("search_person_btn");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='row']/tbody/tr"), 10);
        s.click("xpath=//table[@id='row']/tbody/tr/td[8]/button");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));   
        assertEquals("Doe,John", s.getValue("investigator"));
    }

    /**
     * @param info
     */
    private void verifyTrialData(TrialInfo info) {
        assertEquals(
                info.nciID,
                s.getText("xpath=//div[preceding-sibling::label[text()=' NCI Trial Identifier:']]"));
        assertEquals(
                info.leadOrgID,
                s.getText("xpath=//div[preceding-sibling::label[text()=' Lead Org Trial Identifier:']]"));
        assertEquals(
                info.title,
                s.getText("xpath=//div[preceding-sibling::label[text()=' Title:']]"));
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                s.getValue("organizationName"));
    }

    /**
     * @param trial
     */
    private void populateStatusHistory(TrialInfo trial) {
        addStatus(trial, null, "In Review");

        // Add a comment to In Review.
        selenium.click("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[1]/td[5]/i[@class='fa fa-edit']");
        selenium.type("editComment", "This is initial status");
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Save']");
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr[1]/td[position()=3 and text()='This is initial status']"),
                10);

        // Add Active, ensure warning, and delete.
        addStatus(trial, null, "Active");
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]/div[@class='warning']"));
        deleteStatus(trial, 2);

        // Add Temporarily Closed to Accrual and Intervention, ensure errors,
        // ensure unable to submit,
        // and delete.
        addStatus(trial, null, "Temporarily Closed to Accrual and Intervention");
        assertEquals(
                "Statuses [IN REVIEW] and [TEMPORARILY CLOSED TO ACCRUAL AND INTERVENTION] can not have the same date",
                selenium.getText("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=1 and @class='error']"));
        assertEquals(
                "Interim status [ACTIVE] is missing",
                selenium.getText("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=2 and @class='error']"));
        assertEquals(
                "Interim status [APPROVED] is missing",
                selenium.getText("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=3 and @class='warning']"));
        assertEquals(
                "Interim status [TEMPORARILY CLOSED TO ACCRUAL] is missing",
                selenium.getText("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr[2]/td[4]/div[position()=4 and @class='warning']"));
        s.click("xpath=//button/i[@class='fa-floppy-o']");
        waitForElementToBecomeVisible(
                By.xpath("//div[@id='transitionErrorsWarnings']"), 10);
        assertEquals(
                "Status Transition Errors and Warnings were found. This site cannot be saved until all Status Transition Errors have been resolved. Please use the action icons below to make corrections.",
                selenium.getText("//div[@id='transitionErrorsWarnings']")
                        .replaceAll("\\s+", " ").trim());
        deleteStatus(trial, 2);

        // Change In Review to Approved.
        editStatus(trial, 1, "", "Approved", "Changed to Approved.");

        // Add In Review with yesterday's date.
        addStatus(trial, yesterday, "In Review");
    }

    protected void editStatus(TrialInfo trial, int row, String date,
            String newStatus, String comment) {
        selenium.click("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr["
                + row + "]/td[5]/i[@class='fa fa-edit']");
        waitForElementToBecomeVisible(By.id("dialog-edit"), 5);
        assertEquals("Edit Site Recruitment Status",
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
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=1 and normalize-space(text())='"
                        + (StringUtils.isNotBlank(date) ? date : today) + "']"),
                20);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=2 and normalize-space(text())='" + newStatus
                        + "']"), 20);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr["
                        + row + "]/td[position()=3 and normalize-space(text())='" + comment
                        + "']"), 20);

    }

    protected final void deleteStatus(TrialInfo trial, int row) {
        selenium.click("xpath=//table[@id='siteStatusHistoryTable']/tbody/tr["
                + row + "]/td[5]/i[@class='fa fa-trash-o']");
        waitForElementToBecomeVisible(By.id("dialog-delete"), 5);
        assertEquals("Please provide a comment",
                selenium.getText("ui-dialog-title-dialog-delete"));
        assertEquals(
                "Please provide a comment explaining why you are deleting this recruitment status:",
                selenium.getText("xpath=//div[@id='dialog-delete']/p")
                        .replaceAll("\\s+", " ").trim());
        selenium.type("deleteComment", "Wrong status");
        selenium.click("xpath=//div[@class='ui-dialog-buttonset']//span[text()='Delete']");
        if (row == 1) {
            waitForElementToBecomeAvailable(
                    By.xpath("//table[@id='siteStatusHistoryTable']//td[@class='dataTables_empty']"),
                    10);
        } else {
            waitForElementToGoAway(
                    By.xpath("//table[@id='siteStatusHistoryTable']/tbody/tr["
                            + row + "]"), 10);
        }
    }

    @SuppressWarnings("deprecation")
    protected void addStatus(TrialInfo trial, String date, String status) {
        selenium.select("id=siteDTO_recruitmentStatus", "label=" + status);
        if (StringUtils.isNotBlank(date)) {
            selenium.type("id=siteDTO_recruitmentStatusDate", date);
        } else {
            selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='siteDTO_recruitmentStatusDate']]");
            clickOnFirstVisible(By.xpath("//td[@class='day active']"));
            clickOnFirstVisible(By
                    .xpath("//div[@class='datepicker']/button[@class='close']"));
        }
        clickAndWaitAjax("addStatusBtn");
        waitForElementToBecomeVisible(By.id("siteStatusHistoryTable"), 10);
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='siteStatusHistoryTable']/tbody//tr//td[position()=2 and normalize-space(text())='"
                        + status + "']"), 10);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_AllSitesFromFamilyAndNotAffiliatedWithCancerCenter()
            throws URISyntaxException, SQLException {
        TrialInfo info = createAndSelectTrial();

        addSiteToTrial(info, "DCP", "In Review" , false);
        addSiteToTrial(info, "CTEP", "In Review" , false);
        addSiteToTrial(info, "NCI", "In Review" , false);

        findInMyTrials();
        invokeAction("Update My Site");

        // We must be presented with an option to update one of the two
        // non-cancer center sites.
        assertWeAreOnSiteSelectionScreen("National Cancer Institute Division of Cancer Prevention");

        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(2, options.length);
        assertTrue(ArrayUtils.contains(options,
                "Cancer Therapy Evaluation Program"));
        assertTrue(ArrayUtils.contains(options,
                "National Cancer Institute Division of Cancer Prevention"));

        pickAndUpdateCTEP();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_AllSitesFromFamilyAndAffiliatedWithCancerCenter()
            throws URISyntaxException, SQLException {

        changeUserAffiliation("submitter-ci@example.com", "4",
                "National Cancer Institute");

        TrialInfo info = createAndSelectTrial();

        addSiteToTrial(info, "DCP", "In Review", false);
        addSiteToTrial(info, "CTEP", "In Review" , false);
        addSiteToTrial(info, "NCI", "In Review", false);

        findInMyTrials();
        invokeAction("Update My Site");

        // We must be presented with an option to update one of the 3
        // non-cancer center sites.
        assertWeAreOnSiteSelectionScreen("National Cancer Institute");

        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(3, options.length);
        assertTrue(ArrayUtils.contains(options,
                "Cancer Therapy Evaluation Program"));
        assertTrue(ArrayUtils.contains(options, "National Cancer Institute"));
        assertTrue(ArrayUtils.contains(options,
                "National Cancer Institute Division of Cancer Prevention"));

        pickAndUpdateCTEP();

    }

    @Override
    public void tearDown() throws Exception {
        changeUserAffiliation("submitter-ci@example.com", "3",
                "National Cancer Institute Division of Cancer Prevention");
        super.tearDown();
    }

    private void changeUserAffiliation(String email, String poID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "UPDATE registry_user SET affiliated_org_id=" + poID
                + ", affiliate_org='" + orgName + "' WHERE email_address='"
                + email + "'";
        runner.update(connection, sql);

    }

    /**
     * 
     */
    private void pickAndUpdateCTEP() {
        selenium.select("pickedSiteOrgPoId",
                "label=Cancer Therapy Evaluation Program");
        clickAndWait("pickSiteBtn");
        assertEquals("false",selenium.getValue("//input[@name='addSitesMultiple']"));
        assertEquals("Cancer Therapy Evaluation Program",
                selenium.getValue("organizationName"));

           driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();

        if (!isPhantomJS()) {
            waitForTextToAppear(By.className("alert-success"),
                    "Message: Your site information has been updated.", 30);
            // Make sure the right site got updated.
            assertEquals("Cancer Therapy Evaluation Program",
                    selenium.getText("//table[@id='row']/tbody/tr[2]/td[1]"));

        }
    }

    /**
     * 
     */
    private void assertWeAreOnSiteSelectionScreen(String orgName) {
        assertTrue(selenium
                .isTextPresent("Based on the fact that your organization belongs to a family, you can update more than one "
                        + "site on this trial. Please select the site you would like to update below:"));
        assertEquals(orgName, selenium.getSelectedLabel("pickedSiteOrgPoId"));
    }
    
    private void assertWeAreOnPickASiteSelectionScreen() {
        assertTrue(selenium
                .isTextPresent("Because your organization belongs to a family, you can add to this trial any "
                        + "site within that family. Please select the site you would like to add below:"));
    }


    /**
     * 
     */
    private void invokeAction(String action) {
        final By selectActionBtn = By
                .xpath("//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        moveElementIntoView(selectActionBtn);
        driver.findElement(selectActionBtn).click();
        driver.findElement(
                By.xpath("//li/a[normalize-space(text())='" + action + "']"))
                .click();       
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        
    }

}
