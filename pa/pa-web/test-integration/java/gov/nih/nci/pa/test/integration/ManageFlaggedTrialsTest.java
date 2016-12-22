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
package gov.nih.nci.pa.test.integration;

import static org.apache.commons.lang.time.DateUtils.parseDate;
import static org.apache.commons.lang.time.DateUtils.truncate;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests listing, adding, editing and deleting arms.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Batch(number = 3)
public class ManageFlaggedTrialsTest extends AbstractPaSeleniumTest {

    private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 15000
            : 2000;
    private static final String MM_DD_YYYY_HH_MM_AAA = "MM/dd/yyyy hh:mm aaa";
    private static final String[] REASONS = new String[] {
            "Do not enforce unique Subject ID across sites",
            "Do not submit tweets" };

    @SuppressWarnings("deprecation")
    @Test
    public void testAddFlaggedTrial() throws SQLException, ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        verifyAddErrorMessageAndCancel();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);
        verifySingleFlaggedTrial(trial);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAddDuplicateFlagValidation() throws SQLException,
            ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);
        clickAndWait("link=Manage Flagged Trials");
        populateAddFlagDialogAndHitSave(trial);
        pause(OP_WAIT_TIME);
        assertTrue(selenium.isAlertPresent());
        assertFalse(selenium
                .isTextPresent("Flagged trial has been added successfully."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInvalidNciId() throws SQLException, ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        trial.nciID = "NCI-2014-";
        populateAddFlagDialogAndHitSave(trial);
        assertTrue(selenium.isVisible("err"));
        assertEquals("NCI Trial ID is invalid.", selenium.getText("err"));
        selenium.click("xpath=//button/span[normalize-space(text())='Cancel']");

        trial.nciID = "NCI-2014-238947238947";
        populateAddFlagDialogAndHitSave(trial);
        pause(OP_WAIT_TIME);
        assertTrue(selenium.isAlertPresent());
        assertFalse(selenium
                .isTextPresent("Flagged trial has been added successfully."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testEditDuplicateFlagValidation() throws SQLException,
            ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);
        trial.flaggedReason = "Do not enforce unique Subject ID across sites";
        addFlaggedTrial(trial);

        clickAndWait("link=Manage Flagged Trials");
        selenium.click("xpath=//table[@id='flaggedTrials']/thead/tr[1]/th[2]");
        selenium.click("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[6]");
        selenium.select("reason", "label=Do not submit tweets");
        selenium.type("comments", "This is edited comment.");
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
        pause(OP_WAIT_TIME);
        assertFalse(selenium.isTextPresent("Changes saved!"));
        assertTrue(selenium.isAlertPresent());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testNavigateToTrialHistory() throws SQLException,
            ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);
        selenium.click("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[1]");
        waitForPageToLoad();
        assertEquals("Trial History Information", selenium.getTitle());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testEditFlaggedTrial() throws SQLException, ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);

        takeScreenShot(getClass().getSimpleName()
                + "_BeforeClickingEdit_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        selenium.click("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[6]");
        assertTrue(selenium.isVisible("flag-form"));

        takeScreenShot(getClass().getSimpleName()
                + "_EditFormShowedUp_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        assertEquals("true",
                driver.findElement(By.id("nciID")).getAttribute("disabled"));
        assertEquals("Do not submit tweets", selenium.getValue("reason"));
        assertEquals("This is a comment", selenium.getValue("comments"));

        selenium.select("reason",
                "label=Do not enforce unique Subject ID across sites");
        selenium.type("comments", "This is edited comment.");
        pause(1500);

        takeScreenShot(getClass().getSimpleName()
                + "_BeforeClickingSave_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        selenium.click("xpath=//button/span[normalize-space(text())='Save']");

        takeScreenShot(getClass().getSimpleName()
                + "_ImmediatelyAfterClickingSave_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        waitForElementToBecomeVisible(By.id("msg"), 15);
        assertTrue(selenium.isTextPresent("Changes saved!"));

        takeScreenShot(getClass().getSimpleName()
                + "_ChangesSaved_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        pause(5000);

        takeScreenShot(getClass().getSimpleName()
                + "_AfterPause_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png");

        System.out.println("flaggedTrials inner HTML: "
                + driver.findElement(By.xpath("//table[@id='flaggedTrials']"))
                        .getAttribute("innerHTML"));

        assertEquals(
                "Do not enforce unique Subject ID across sites",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[2]"));

        assertEquals(
                "This is edited comment.",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[5]"));

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Test
    public void testMultiDelete() throws SQLException, ParseException,
            IOException {
        List<TrialInfo> trials = addMultipleFlaggedTrials();
        pause(1000);
        selenium.select("name=flaggedTrials_length", "label=25");
        pause(1000);
        selenium.click("link=Select All");
        selenium.click("link=Delete");
        assertTrue(selenium.isVisible("comment-form"));
        selenium.type("deleteCommentsBox", "This is a delete comment.");
        selenium.click("xpath=//button/span[normalize-space(text())='Delete']");
        waitForPageToLoad();

        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));
        assertTrue(selenium.isTextPresent("No flagged trials found."));
        assertTrue(selenium.isVisible("deletedFlaggedTrials"));
        selenium.select("name=deletedFlaggedTrials_length", "label=25");
        pause(1000);
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[11]"));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSelectDeselectAll() throws SQLException, ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);

        selenium.click("link=Select All");
        assertTrue(selenium
                .isChecked("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[7]//input[@type='checkbox']"));
        selenium.click("link=Deselect All");
        assertFalse(selenium
                .isChecked("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[7]//input[@type='checkbox']"));

        selenium.click("link=Delete");
        assertFalse(selenium.isVisible("comment-form"));

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Test
    public void testEnsureDeletedTrialsIncludedInExport() throws SQLException,
            ParseException, IOException {
        TrialInfo trial1 = createTrialAndAccessManageFlags();
        trial1.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial1);

        TrialInfo trial2 = createAcceptedTrial();
        trial2.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial2);

        selenium.click("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[7]//input[@type='checkbox']");
        selenium.click("link=Delete");
        waitForElementToBecomeVisible(By.id("deleteCommentsBox"), 5);
        selenium.type("deleteCommentsBox", "This is a delete comment.");
        selenium.click("xpath=//button/span[normalize-space(text())='Delete']");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "flagged_trials_all.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();
            List<String> lines = FileUtils.readLines(csv);
            assertEquals(3, lines.size());
            assertTrue((lines.get(1).startsWith(trial1.nciID) && lines.get(2)
                    .startsWith(trial2.nciID))
                    || (lines.get(1).startsWith(trial2.nciID) && lines.get(2)
                            .startsWith(trial1.nciID)));
            csv.delete();
        }

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeleteFlaggedTrial() throws SQLException, ParseException {
        TrialInfo trial = createTrialAndAccessManageFlags();
        trial.flaggedReason = "Do not submit tweets";
        addFlaggedTrial(trial);

        selenium.click("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[7]//input[@type='checkbox']");
        selenium.click("link=Delete");
        assertTrue(selenium.isVisible("comment-form"));

        selenium.click("xpath=//button/span[normalize-space(text())='Delete']");
        assertTrue(selenium.isVisible("commentErr"));
        assertEquals("Comment is mandatory.", selenium.getText("commentErr"));
        selenium.click("xpath=//div[@aria-describedby='comment-form']//button/span[normalize-space(text())='Cancel']");
        assertFalse(selenium.isVisible("comment-form"));

        selenium.click("link=Delete");
        selenium.type("deleteCommentsBox", "This is a delete comment.");
        assertEquals("3975 characters left", selenium.getText("limitlbl_1"));
        selenium.click("xpath=//button/span[normalize-space(text())='Delete']");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));
        assertTrue(selenium.isTextPresent("No flagged trials found."));

        assertTrue(selenium.isVisible("deletedFlaggedTrials"));
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[1]"));
        assertEquals(
                "Do not submit tweets",
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[2]"));
        assertEquals(
                "ctrpsubstractor "
                        + DateFormatUtils.format(
                                truncate(getDateOfLastFlaggedTrial(),
                                        Calendar.MINUTE), MM_DD_YYYY_HH_MM_AAA),
                selenium.getText(
                        "xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[3]")
                        .replaceAll("\\s+", " "));
        assertEquals(
                "ctrpsubstractor "
                        + DateFormatUtils.format(
                                truncate(getDeleteDateOfLastFlaggedTrial(),
                                        Calendar.MINUTE), MM_DD_YYYY_HH_MM_AAA),
                selenium.getText(
                        "xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[4]")
                        .replaceAll("\\s+", " "));
        assertEquals(
                "General Comments: This is a comment Delete Comments: This is a delete comment.",
                selenium.getText(
                        "xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[5]")
                        .replaceAll("\\s+", " "));

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Test
    public void testFlaggedTrialsTableControls() throws SQLException,
            ParseException, IOException {
        List<TrialInfo> trials = addMultipleFlaggedTrials();

        // Page Size control.
        selenium.select("name=flaggedTrials_length", "label=25");
        pause(1500);
        assertTrue(selenium.isTextPresent("Showing 1 to 11 of 11 entries"));
        assertFalse(selenium
                .isElementPresent("xpath=//a[normalize-space(text())='2']"));

        // Sorting
        selenium.click("xpath=//table[@id='flaggedTrials']/thead/tr[1]/th[1]");
        Collections.sort(trials, new Comparator<TrialInfo>() {
            @Override
            public int compare(TrialInfo o1, TrialInfo o2) {
                return o2.nciID.compareTo(o1.nciID);
            }
        });
        for (TrialInfo trialInfo : trials) {
            assertEquals(
                    trialInfo.nciID,
                    selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr["
                            + (trials.indexOf(trialInfo) + 1) + "]/td[1]"));
        }
        selenium.click("xpath=//table[@id='flaggedTrials']/thead/tr[1]/th[2]");
        assertEquals(
                "Do not enforce unique Subject ID across sites",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[2]"));
        assertEquals(
                "Do not submit tweets",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[11]/td[2]"));

        // Searching
        TrialInfo trial = trials.get(0);
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(
                trial.nciID);
        assertTrue(selenium
                .isTextPresent("Showing 1 to 1 of 1 entries (filtered from 11 total entries)"));
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[1]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='flaggedTrials']/tbody/tr[2]"));

        // Paging
        selenium.type("xpath=//input[@type='search']", "");
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(" ");
        selenium.click("xpath=//table[@id='flaggedTrials']/thead/tr[1]/th[1]");
        selenium.select("name=flaggedTrials_length", "label=10");
        pause(1500);
        selenium.click("xpath=//a[normalize-space(text())='2']");
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[1]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='flaggedTrials']/tbody/tr[2]"));

        // Finally, download CSV.
        if (!isPhantomJS()) {
            selenium.click("xpath=//a/span[normalize-space(text())='CSV']");
            pause(OP_WAIT_TIME);
            File csv = new File(downloadDir, "flagged_trials_all.csv");
            assertTrue(csv.exists());
            csv.deleteOnExit();
            List<String> lines = FileUtils.readLines(csv);
            assertEquals(12, lines.size());
            assertEquals(
                    "NCI Trial ID,Flag Reason,Flagged By,Flagged On,Comments,Deleted By,Deleted On,Delete Comments",
                    lines.get(0));
            assertTrue(lines
                    .get(11)
                    .matches(
                            "^"
                                    + trial.nciID
                                    + ","
                                    + trial.flaggedReason
                                    + ",ctrpsubstractor,.*?,This is a comment,,,"
                                    + "$"));
            csv.delete();
        }

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Test
    public void testDeletedTrialsTableControls() throws SQLException,
            ParseException, IOException {
        List<TrialInfo> trials = addMultipleFlaggedTrials();

        // Delete ALL
        selenium.select("name=flaggedTrials_length", "label=25");
        pause(1500);
        selenium.click("link=Select All");
        selenium.click("link=Delete");
        selenium.type("deleteCommentsBox", "This is a delete comment.");
        selenium.click("xpath=//button/span[normalize-space(text())='Delete']");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));

        // Sorting
        selenium.select("name=deletedFlaggedTrials_length", "label=25");
        pause(1500);
        selenium.click("xpath=//table[@id='deletedFlaggedTrials']/thead/tr[1]/th[1]");
        Collections.sort(trials, new Comparator<TrialInfo>() {
            @Override
            public int compare(TrialInfo o1, TrialInfo o2) {
                return o2.nciID.compareTo(o1.nciID);
            }
        });
        pause(1500);
        for (TrialInfo trialInfo : trials) {
            assertEquals(
                    trialInfo.nciID,
                    selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr["
                            + (trials.indexOf(trialInfo) + 1) + "]/td[1]"));
        }
        selenium.click("xpath=//table[@id='deletedFlaggedTrials']/thead/tr[1]/th[2]");
        pause(1500);
        assertEquals(
                "Do not enforce unique Subject ID across sites",
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[2]"));
        assertEquals(
                "Do not submit tweets",
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[11]/td[2]"));

        // Searching
        TrialInfo trial = trials.get(0);
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(
                trial.nciID);
        assertTrue(selenium
                .isTextPresent("Showing 1 to 1 of 1 entries (filtered from 11 total entries)"));
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[1]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[2]"));

        // Paging
        selenium.type("xpath=//input[@type='search']", "");
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(" ");
        selenium.click("xpath=//table[@id='deletedFlaggedTrials']/thead/tr[1]/th[1]");
        selenium.select("name=deletedFlaggedTrials_length", "label=10");
        pause(1500);
        selenium.click("xpath=//a[normalize-space(text())='2']");
        pause(1000);
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[1]/td[1]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='deletedFlaggedTrials']/tbody/tr[2]"));

    }

    /**
     * @return
     * @throws SQLException
     */
    private List<TrialInfo> addMultipleFlaggedTrials() throws SQLException {
        deleteAllFlaggedTrials();
        List<TrialInfo> trials = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            TrialInfo trial = createAcceptedTrial();
            trials.add(trial);
        }

        loginAsSuperAbstractor();
        clickAndWait("link=Manage Flagged Trials");

        for (TrialInfo trialInfo : trials) {
            trialInfo.flaggedReason = REASONS[trials.indexOf(trialInfo) % 2];
            addFlaggedTrial(trialInfo);
        }
        return trials;
    }

    /**
     * @param trial
     * @throws SQLException
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    private void verifySingleFlaggedTrial(TrialInfo trial) throws SQLException,
            ParseException {
        assertTrue(selenium.isVisible("flaggedTrials"));
        assertEquals(
                trial.nciID,
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[1]"));
        assertEquals(
                "Do not submit tweets",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[2]"));
        assertEquals(
                "ctrpsubstractor",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[3]"));
        assertEquals(
                truncate(getDateOfLastFlaggedTrial(), Calendar.MINUTE),
                truncate(
                        parseDate(
                                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[4]"),
                                new String[] { MM_DD_YYYY_HH_MM_AAA }),
                        Calendar.MINUTE));
        assertEquals(
                "This is a comment",
                selenium.getText("xpath=//table[@id='flaggedTrials']/tbody/tr[1]/td[5]"));
    }

    /**
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void addFlaggedTrial(TrialInfo trial) {
        populateAddFlagDialogAndHitSave(trial);
        waitForPageToLoad();
        waitForElementToBecomeAvailable(By.className("confirm_msg"), 15);
        pause(500);
        assertTrue(selenium
                .isTextPresent("Flagged trial has been added successfully."));
    }

    /**
     * @param trial
     */
    @SuppressWarnings("deprecation")
    private void populateAddFlagDialogAndHitSave(TrialInfo trial) {
        pause(1000);
        selenium.click("xpath=//span[normalize-space(text())='Add Flagged Trial']");
        pause(1000);
        waitForElementById("comments", 5);
        selenium.type("nciID", trial.nciID);
        selenium.select("reason", "label=" + trial.flaggedReason);
        pause(1000);
        selenium.type("comments", "This is a comment");
        pause(1500);
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            assertEquals("3983 characters left", selenium.getText("limitlbl_0"));
        }
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    private void verifyAddErrorMessageAndCancel() {
        selenium.click("xpath=//span[normalize-space(text())='Add Flagged Trial']");
        assertTrue(selenium.isVisible("flag-form"));
        selenium.click("xpath=//button/span[normalize-space(text())='Save']");
        assertTrue(selenium.isVisible("err"));
        assertEquals("NCI Trial ID is required.", selenium.getText("err"));
        selenium.click("xpath=//button/span[normalize-space(text())='Cancel']");
        assertFalse(selenium.isVisible("flag-form"));
    }

    /**
     * @return
     * @throws SQLException
     */
    private TrialInfo createTrialAndAccessManageFlags() throws SQLException {
        deleteAllFlaggedTrials();
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        clickAndWait("link=Manage Flagged Trials");
        return trial;
    }

    private Date getDateOfLastFlaggedTrial() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Date) runner
                .query(connection,
                        "select date_flagged from study_protocol_flags order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }

    private Date getDeleteDateOfLastFlaggedTrial() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Date) runner
                .query(connection,
                        "select date_deleted from study_protocol_flags order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }

    private void deleteAllFlaggedTrials() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from study_protocol_flags";
        runner.update(connection, sql);

    }

}
