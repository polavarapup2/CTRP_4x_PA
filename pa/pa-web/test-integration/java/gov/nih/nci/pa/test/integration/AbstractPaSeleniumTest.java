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

import static gov.nih.nci.pa.test.integration.util.TestProperties.TEST_DB_DRIVER;
import static gov.nih.nci.pa.test.integration.util.TestProperties.TEST_DB_PASSWORD;
import static gov.nih.nci.pa.test.integration.util.TestProperties.TEST_DB_URL;
import static gov.nih.nci.pa.test.integration.util.TestProperties.TEST_DB_USER;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.test.integration.util.TestProperties;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.After;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.dumbster.smtp.SimpleSmtpServer;

/**
 * Abstract base class for selenium tests.
 * 
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@SuppressWarnings("deprecation")
@Ignore
public abstract class AbstractPaSeleniumTest extends AbstractSelenese2TestCase {

    protected static final FastDateFormat MONTH_DAY_YEAR_FMT = FastDateFormat
            .getInstance("MM/dd/yyyy");
    private static final String PHANTOM_JS_DRIVER = "org.openqa.selenium.phantomjs.PhantomJSDriver";
    public static Logger LOG = Logger.getLogger(AbstractPaSeleniumTest.class
            .getName());
    protected Connection connection;

    protected String today = MONTH_DAY_YEAR_FMT.format(new Date());
    protected String tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    protected String oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils
            .addYears(new Date(), 1));
    protected Date yesterdayDate = DateUtils.addDays(new Date(), -1);
    protected String yesterday = MONTH_DAY_YEAR_FMT.format(yesterdayDate);

    // SMTP
    private int PORT = randomPort();

    protected SimpleSmtpServer server;

    static {
        new Timer(true).schedule(new TimerTask() {
            @SuppressWarnings("rawtypes")
            @Override
            public void run() {
                if (SystemUtils.IS_OS_WINDOWS) {
                    return;
                }
                System.out
                        .println("---------------------------------------------------------------------------------");
                System.out
                        .println("I am a periodic thread dump logger. Please excuse me for verbose output and ignore for now.");
                Map allThreads = Thread.getAllStackTraces();
                Iterator iterator = allThreads.keySet().iterator();
                StringBuffer stringBuffer = new StringBuffer();
                while (iterator.hasNext()) {
                    Thread key = (Thread) iterator.next();
                    StackTraceElement[] trace = (StackTraceElement[]) allThreads
                            .get(key);
                    stringBuffer.append(key + "\r\n");
                    for (int i = 0; i < trace.length; i++) {
                        stringBuffer.append(" " + trace[i] + "\r\n");
                    }
                    stringBuffer.append("\r\n");
                }
                System.out.println(stringBuffer);
            }
        }, 220000, 220000);
    }

    /**
     * @return
     */
    protected static int randomPort() {
        int port;
        while (isPortInUse((port = (int) (32768 + Math.random() * 32766)))) {
            System.out.println("Port " + port + " in use; trying another...");
        }
        return port;
    }

    private static boolean isPortInUse(final int port) {
        try {
            new ServerSocket(port).close();
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    @Override
    public void setUp() throws Exception {
        setUpSelenium();
        openDbConnection();
        startSMTP();
    }

    /**
     * @throws SQLException
     */
    private void startSMTP() throws SQLException {
        new QueryRunner().update(connection, "update pa_properties set value='"
                + PORT + "' where name='smtp.port'");

        server = new SimpleSmtpServer(PORT);
        Thread t = new Thread(server);
        t.start();

        // Block until the server socket is created
        synchronized (server) {
            try {
                server.wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @throws Exception
     */
    private void setUpSelenium() throws Exception {
        super.setServerHostname(TestProperties.getServerHostname());
        super.setServerPort(TestProperties.getServerPort());
        super.setDriverClass(TestProperties.getDriverClass());
        // super.setDriverClass(PHANTOM_JS_DRIVER);
        System.setProperty("phantomjs.binary.path",
                TestProperties.getPhantomJsPath());
        super.setUp();
        selenium.setSpeed(TestProperties.getSeleniumCommandDelay());
    }

    private void openDbConnection() {
        try {
            DbUtils.loadDriver(TestProperties.getProperty(TEST_DB_DRIVER));
            this.connection = DriverManager.getConnection(
                    TestProperties.getProperty(TEST_DB_URL),
                    TestProperties.getProperty(TEST_DB_USER),
                    TestProperties.getProperty(TEST_DB_PASSWORD));
            LOG.info("Successfully connected to the database at "
                    + TestProperties.getProperty(TEST_DB_URL));
        } catch (Exception e) {
            LOG.severe("Unable to open a JDBC connection to the database: tests may fail!");
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public final void restartEmailServer() throws SQLException {
        stopSMTP();
        PORT = randomPort();
        startSMTP();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        DbUtils.closeQuietly(connection);
        stopSMTP();
        if (testFailed)
            takeScreenShot();
        logoutUser();
        closeBrowser();
        super.tearDown();
    }

    /**
     * 
     */
    protected void stopSMTP() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void waitForEmailsToArrive(int numberOfEmailsToWaitFor)
            throws InterruptedException {
        long stamp = System.currentTimeMillis();
        while (server.getReceivedEmailSize() < numberOfEmailsToWaitFor
                && System.currentTimeMillis() - stamp < 1000 * 60) {
            Thread.sleep(1000);
        }
        assertTrue(numberOfEmailsToWaitFor <= server.getReceivedEmailSize());
    }

    private void takeScreenShot() {
        final String screenShotFileName = getClass().getSimpleName()
                + "_ScreenShot_"
                + new Timestamp(System.currentTimeMillis()).toString()
                        .replaceAll("\\D+", "_") + ".png";
        takeScreenShot(screenShotFileName);

    }

    /**
     * @param screenShotFileName
     */
    protected void takeScreenShot(final String screenShotFileName) {
        Future f = Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    File destFile = new File(
                            (SystemUtils.IS_OS_WINDOWS ? SystemUtils.JAVA_IO_TMPDIR
                                    : SystemUtils.USER_DIR), screenShotFileName);
                    File scrFile = ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(scrFile, destFile);
                    scrFile.delete();
                    System.out.println("Saved screen shot: "
                            + destFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            f.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            f.cancel(true);
        }

    }

    private void closeBrowser() {
        try {
            driver.quit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    protected void logoutUser() {
        logoutPA();
    }

    /**
     * 
     */
    protected final void logoutPA() {
        openAndWait("/pa/logout.action");
    }

    @SuppressWarnings("deprecation")
    protected void reInitializeWebDriver() throws Exception {
        closeBrowser();
        super.tearDown();
        setUpSelenium();
    }

    /**
     * @return
     */
    protected boolean isPhantomJS() {
        return driver.getClass().getName().equals(PHANTOM_JS_DRIVER);
    }

    protected void login(String username, String password) {
        loginPA(username, password);
    }

    public final void loginPA(String username, String password) {
        login("/pa", username, password);
    }

    protected void login(String path, String username, String password) {
        attemptLogin(path, username, password);
        assertTrue(selenium.isElementPresent("link=Logout"));
        verifyDisclaimerPage();
    }

    /**
     * @param path
     * @param username
     * @param password
     */
    protected void attemptLogin(String path, String username, String password) {
        openAndWait(path);
        verifyLoginPage();
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=loginLink");
    }

    private void verifyLoginPage() {
        assertTrue(selenium.isTextPresent("Login"));
        assertTrue(selenium.isTextPresent("CONTACT US"));
        assertTrue(selenium.isTextPresent("PRIVACY NOTICE"));
        assertTrue(selenium.isTextPresent("DISCLAIMER"));
        assertTrue(selenium.isTextPresent("ACCESSIBILITY"));
        assertTrue(selenium.isTextPresent("SUPPORT"));
        clickAndWait("link=Login");
    }

    protected void verifyDisclaimerPage() {
        assertTrue(selenium.isElementPresent("id=acceptDisclaimer"));
        assertTrue(selenium.isElementPresent("id=rejectDisclaimer"));
    }

    protected void disclaimer(boolean accept) {
        verifyDisclaimerPage();
        if (accept) {
            clickAndWait("id=acceptDisclaimer");
            clickAndWait("id=trialSearchMenuOption");
            verifyHomePage();
        } else {
            clickAndWait("id=rejectDisclaimer");
            verifyLoginPage();
        }

    }

    protected void verifyHomePage() {
        assertTrue(selenium.isElementPresent("link=Logout"));
        assertTrue(selenium.isElementPresent("id=trialSearchMenuOption"));
        assertTrue(selenium.isElementPresent("id=inboxProcessingMenuOption"));
        assertTrue(selenium.isElementPresent("id=logoutMenuOption"));
    }

    /**
     * Verifies that the trial search page has been loaded.
     */
    protected void verifyTrialSearchPage() {
        assertTrue(selenium.isElementPresent("id=officialTitle"));
        assertTrue(selenium.isElementPresent("id=leadOrganizationId"));
        assertTrue(selenium.isElementPresent("id=identifierType"));
        assertTrue(selenium.isElementPresent("id=identifier"));
        assertTrue(selenium.isElementPresent("id=principalInvestigatorId"));
        assertTrue(selenium.isElementPresent("id=primaryPurpose"));
        assertTrue(selenium.isElementPresent("id=phaseCode"));
        assertTrue(selenium.isElementPresent("id=studyStatusCode"));
        assertTrue(selenium.isElementPresent("id=documentWorkflowStatusCode"));
        assertTrue(selenium.isElementPresent("id=studyMilestone"));
        assertTrue(selenium.isElementPresent("id=holdStatus"));
        assertTrue(selenium.isElementPresent("id=studyLockedBy"));
        assertTrue(selenium.isElementPresent("id=submissionType"));
        assertTrue(selenium.isElementPresent("id=trialCategory"));
        assertTrue(selenium.isElementPresent("link=Search"));
        assertTrue(selenium.isElementPresent("link=Reset"));
    }

    /**
     * Verifies that a trial has been selected from the search results.
     * 
     * @param nciTrialIdentifier
     *            the NCI trial identifier i.e. NCI-2010-00001
     */
    protected void verifyTrialSelected(String nciTrialIdentifier) {
        assertTrue(selenium.isTextPresent(nciTrialIdentifier));
        assertTrue(selenium.isTextPresent("Trial Overview"));
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        assertTrue(selenium.isElementPresent("link=Trial History"));
        assertTrue(selenium.isElementPresent("link=Trial Milestones"));
        assertTrue(selenium.isElementPresent("link=On-hold Information"));
        assertTrue(selenium.isElementPresent("link=Manage Accrual Access"));
        assertTrue(selenium.isElementPresent("link=View TSR"));
        assertTrue(selenium.isElementPresent("link=Assign Ownership"));
        assertTrue(selenium.isTextPresent("Validation"));
        assertTrue(selenium.isElementPresent("link=Trial Related Documents"));
        assertTrue(selenium.isElementPresent("link=Trial Status"));
        assertTrue(selenium.isElementPresent("link=Trial Funding"));
        assertTrue(selenium.isElementPresent("link=Trial IND/IDE"));
        assertTrue(selenium.isElementPresent("link=Regulatory Information"));
        assertTrue(selenium.isElementPresent("link=Trial Validation"));
    }

    /**
     * Checks a trial out as a scientific abstractor. Assumes that the trial has
     * already been selected.
     */
    protected void checkOutTrialAsScientificAbstractor() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium.isElementPresent("link=Scientific Check In"));
        clickAndWait("link=Scientific Check Out");
        assertTrue(selenium.isElementPresent("link=Scientific Check In"));
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
    }

    /**
     * Checks a trial out. Assumes that the trial has already been selected.
     */
    protected void checkInTrialAsScientificAbstractor() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertFalse(selenium.isElementPresent("link=Scientific Check Out"));
        assertTrue(selenium.isElementPresent("link=ScientificCheck In"));
        clickAndWait("link=Scientific Check In");
        assertTrue(selenium.isElementPresent("link=Scientific Check Out"));
        assertFalse(selenium.isElementPresent("link=Scientific Check In"));
    }

    /**
     * Checks a trial out as a admin abstractor. Assumes that the trial has
     * already been selected.
     */
    protected void checkOutTrialAsAdminAbstractor() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
        assertFalse(selenium.isElementPresent("link=Admin Check In"));
        clickAndWait("link=Admin Check Out");
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
    }

    /**
     * Checks a trial out as an admin abstractor. Assumes that the trial has
     * already been selected.
     */
    protected void checkInTrialAsAdminAbstractor() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertFalse(selenium.isElementPresent("link=Admin Check Out"));
        assertTrue(selenium.isElementPresent("link=Admin Check In"));
        clickAndWait("link=Admin Check In");
        assertTrue(selenium.isElementPresent("id=comments"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-primary"));
        assertTrue(selenium.isElementPresent("class=btn btn-icon btn-default"));
        assertEquals(selenium.getText("class=btn btn-icon btn-primary"), "Ok");
        assertEquals(selenium.getText("class=btn btn-icon btn-default"),
                "Cancel");
        selenium.type("id=comments", "Test admin check in comments");
        clickAndWait("class=btn btn-icon btn-primary");
        assertTrue(selenium.isElementPresent("link=Admin Check Out"));
    }

    /**
     * Accepts a trial. Assumes that the trial has already been checked out.
     */
    protected void acceptTrial() {
        clickAndWait("link=Trial Validation");
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Accept"));
        assertTrue(selenium.isElementPresent("link=Reject"));

        if (selenium.isElementPresent("id=amendmentReasonCode")) {
            selenium.select("id=amendmentReasonCode", "label=Both");
        }

        clickAndWait("link=Accept");
    }

    /**
     * Verifies that the trial has been accepted.
     */
    protected void verifyTrialAccepted() {
        assertTrue(selenium.isTextPresent("Trial Overview"));
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        assertTrue(selenium.isElementPresent("link=Trial History"));
        assertTrue(selenium.isElementPresent("link=Trial Milestones"));
        assertTrue(selenium.isElementPresent("link=On-hold Information"));
        assertTrue(selenium.isElementPresent("link=Manage Accrual Access"));
        assertTrue(selenium.isElementPresent("link=View TSR"));
        assertTrue(selenium.isElementPresent("link=Assign Ownership"));

        assertTrue(selenium.isTextPresent("Administrative Data"));
        assertTrue(selenium.isElementPresent("link=General Trial Details"));
        assertTrue(selenium.isElementPresent("link=NCI Specific Information"));
        assertTrue(selenium.isElementPresent("link=Regulatory Information"));
        assertTrue(selenium.isElementPresent("link=Human Subject Safety"));
        assertTrue(selenium.isElementPresent("link=Trial IND/IDE"));
        assertTrue(selenium.isElementPresent("link=Trial Status"));
        assertTrue(selenium.isElementPresent("link=Trial Funding"));
        assertTrue(selenium.isElementPresent("link=Participating Sites"));
        assertTrue(selenium.isElementPresent("link=Collaborators"));
        assertTrue(selenium.isElementPresent("link=Trial Related Documents"));

        assertTrue(selenium.isTextPresent("Scientific Data"));
        assertTrue(selenium.isElementPresent("link=Trial Description"));
        assertTrue(selenium.isElementPresent("link=Design Details"));
        assertTrue(selenium.isElementPresent("link=Outcome Measures"));
        assertTrue(selenium.isElementPresent("link=Eligibility Criteria"));
        assertTrue(selenium.isElementPresent("link=Disease/Condition"));
        assertTrue(selenium.isElementPresent("link=Markers"));
        assertTrue(selenium.isElementPresent("link=Interventions"));
        assertTrue(selenium.isElementPresent("link=Arms")
                || selenium.isElementPresent("link=Groups/Cohorts"));
        assertTrue(selenium.isElementPresent("link=Sub-groups"));

        assertTrue(selenium.isTextPresent("Completion"));
        assertTrue(selenium.isElementPresent("link=Abstraction Validation"));
    }

    /**
     * Searches for, checks out, select and accepts the trial with the given
     * title. Title must be unique among available trials
     * 
     * @param trialTitle
     *            the trial title to search for
     * @param adminAbstractor
     *            whether or not to check out the trial as an admin abstractor
     * @param scientificAbstractor
     *            whether or not to check out the trial as scientific abstractor
     */
    protected void searchSelectAndAcceptTrial(String trialTitle,
            boolean adminAbstractor, boolean scientificAbstractor) {
        String nciTrialId = searchAndSelectTrial(trialTitle);
        verifyTrialSelected(nciTrialId);
        if (adminAbstractor) {
            checkOutTrialAsAdminAbstractor();
        }
        if (scientificAbstractor) {
            checkOutTrialAsScientificAbstractor();
        }
        acceptTrial();
        verifyTrialAccepted();
    }

    protected String searchAndSelectTrialWithMilestoneCheck(String trialTitle,
            String currMilestone, String adminMilestone,
            String scientificMilestone) {
        verifyTrialSearchPage();

        selenium.type("id=officialTitle", trialTitle);
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        assertTrue(selenium.isElementPresent("id=row"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]"));
        assertFalse(selenium
                .isElementPresent("xpath=//table[@id='row']//tr[2]"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]/a"));
        String nciTrialId = selenium
                .getText("xpath=//table[@id='row']//tr[1]//td[1]/a");
        assertTrue(selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]")
                .contains(currMilestone));
        assertTrue(selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]")
                .contains(adminMilestone));
        assertTrue(selenium.getText("xpath=//table[@id='row']//tr[1]//td[5]")
                .contains(scientificMilestone));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        return nciTrialId;
    }

    protected String searchAndSelectTrial(String trialTitle) {
        return searchAndSelectTrialWithMilestoneCheck(trialTitle, "", "", "");
    }

    public final void loginAsSuperAbstractor() {
        loginPA("ctrpsubstractor", "pass");
        disclaimer(true);
    }

    public void loginAsAbstractor() {
        login("abstractor-ci", "pass");
        disclaimer(false);
        login("abstractor-ci", "pass");
        disclaimer(true);
    }

    public void loginAsScientificAbstractor() {
        login("scientific-ci", "pass");
        disclaimer(false);
        login("scientific-ci", "pass");
        disclaimer(true);
    }

    public void loginAsAdminAbstractor() {
        login("admin-ci", "pass");
        disclaimer(false);
        login("admin-ci", "pass");
        disclaimer(true);
    }

    public final void loginAsResultsAbstractor() {
        loginPA("results-abstractor", "pass");
        disclaimer(true);
    }

    protected boolean isLoggedIn() {
        return selenium.isElementPresent("link=Logout")
                && !selenium.isElementPresent("link=Login");
    }

    protected void openAndWait(String url) {
        if (!isPhantomJS()) {
            selenium.open(url);
        } else {
            openAndHandleStuckPhantomJsDriver(url);
        }
        waitForPageToLoad();
    }

    private void openAndHandleStuckPhantomJsDriver(final String url) {
        int tries = 0;
        while (tries < 3) {
            tries++;
            Future<Boolean> f = Executors.newSingleThreadExecutor().submit(
                    new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            selenium.open(url);
                            return true;
                        }
                    });
            try {
                if (f.get(30, TimeUnit.SECONDS)) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err
                        .println("PhantomJS stuck in 'get' (an odd issue on Linux); restarting and trying again. Attempt # "
                                + tries);
            }
            f.cancel(true);
            try {
                this.reInitializeWebDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    protected TrialInfo createSubmittedTrial() throws SQLException {
        return createSubmittedTrial(false);
    }

    protected TrialInfo createSubmittedTrial(boolean isAbbr)
            throws SQLException {
        return createSubmittedTrial(isAbbr, false);
    }

    protected TrialInfo createSubmittedTrial(boolean isAbbr,
            boolean skipDocuments, String... orgName) throws SQLException {
        TrialInfo info = new TrialInfo();

        // trimming uuid if length greater than 30 characters as this is used
        // for leadOrgId, which throws a validation error if its length is
        // greater than 30 chars
        info.uuid = StringUtils.left(UUID.randomUUID().toString(), 30);

        info.title = "Title " + info.uuid;

        pickUsers(info);

        QueryRunner runner = new QueryRunner();

        String protocolInsertSQL = "INSERT INTO study_protocol "
                + "(identifier,accr_rept_meth_code,acronym,accept_healthy_volunteers_indicator,data_monty_comty_apptn_indicator,"
                + "delayed_posting_indicator,expd_access_indidicator,fda_regulated_indicator,review_brd_approval_req_indicator,"
                + "keyword_text,official_title,max_target_accrual_num,phase_code,phase_other_text,pri_compl_date,pri_compl_date_type_code,"
                + "start_date,start_date_type_code,primary_purpose_code,primary_purpose_other_text,public_description,"
                + "public_tittle,section801_indicator,record_verification_date,scientific_description,study_protocol_type,allocation_code,"
                + "blinding_role_code_subject,blinding_role_code_caregiver,blinding_role_code_investigator,blinding_role_code_outcome,"
                + "blinding_schema_code,design_configuration_code,number_of_intervention_groups,study_classification_code,bio_specimen_description,"
                + "bio_specimen_retention_code,sampling_method_code,number_of_groups,study_model_code,study_model_other_text,time_perspective_code,"
                + "time_perspective_other_text,study_population_description,date_last_created,date_last_updated,status_code,status_date,"
                + "amendment_number,amendment_date,amendment_reason_code,submission_number,parent_protocol_identifier,"
                + "min_target_accrual_num,proprietary_trial_indicator,ctgov_xml_required_indicator,user_last_created_id,user_last_updated_id,"
                + "phase_additional_qualifier_code,primary_purpose_additional_qualifier_code,completion_date,completion_date_type_code,"
                + "study_subtype_code,comments,processing_priority,assigned_user_id,ctro_override,secondary_purpose_other_text,nci_grant,"
                + "consortia_trial_category,final_accrual_num,study_source, submitting_organization_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE'))"
                + ",'ABBREVIATED','Accr',false,false,false,false,"
                + "false,false,'stage I prostate cancer, stage IIB prostate cancer, stage IIA prostate cancer, stage III prostate cancer'"
                + ",'"
                + info.title
                + "'"
                + ",null,'II',null,{ts '2018-04-16 12:18:50.572'},'ANTICIPATED',{ts '2013-04-16 12:18:50.572'},'ACTUAL',"
                + "'TREATMENT',null,'Public Description "
                + info.uuid
                + "','"
                + info.title
                + "',"
                + "false,null,'Scientific Description','InterventionalStudyProtocol','RANDOMIZED_CONTROLLED_TRIAL',"
                + "null,null,null,null,'OPEN','PARALLEL',1,'EFFICACY',null,null,null,1,null,null,null,null,null,"
                + "{ts '2014-04-16 12:18:50.572'},null,'ACTIVE',{ts '2013-04-16 12:18:50.572'},null,"
                + "null,null,1,null,60," + isAbbr + ",false," + info.csmUserID
                + ",null,null,null,"
                + "{ts '2018-04-16 12:18:50.572'},'ANTICIPATED',null,null,2,"
                + info.csmUserID + ",false,null,false,null,null,'OTHER', 1);";
        runner.update(connection, protocolInsertSQL);
        info.id = (Long) runner
                .query(connection,
                        "select identifier from study_protocol order by identifier desc limit 1",
                        new ArrayHandler())[0];
        assignNciId(info);
        addDWS(info, "SUBMITTED");
        addMilestone(info, "SUBMISSION_RECEIVED");
        if (orgName != null && orgName.length > 0) {
            addLeadOrg(info, orgName[0]);
        } else {
            addLeadOrg(info, "ClinicalTrials.gov");
        }

        addPI(info, "1");
        addSOS(info, "IN_REVIEW", yday_midnight());
        addSOS(info, "APPROVED");
        if (!skipDocuments)
            addDocument(info, "PROTOCOL_DOCUMENT", "Protocol.doc");

        LOG.info("Registered a new trial: " + info);
        return info;

    }

    protected void addSummaryFour(long spId, String csmUserName)
            throws SQLException {
        Number orgId = getResearchOrgId("ClinicalTrials.gov");
        long userId = getCsmUserByLoginName(csmUserName);

        String sql = "insert into study_resourcing values "
                + "( (SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                + "'NATIONAL', 'TRUE','" + orgId.intValue() + "'," + spId
                + ",'P30','CA'," + "'CTEP','12197','TRUE','',null,null,"
                + userId + "," + userId + ",0)";
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

        LOG.info("Added summary four info to db");
    }

    protected void addNonCASummaryFour(long spId, String csmUserName)
            throws SQLException {
        Number orgId = getResearchOrgId("ClinicalTrials.gov");
        long userId = getCsmUserByLoginName(csmUserName);

        String sql = "insert into study_resourcing values "
                + "( (SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                + "'NATIONAL', 'TRUE','" + orgId.intValue() + "'," + spId
                + ",'B09','AA'," + "'CTEP','12197','TRUE','',null,null,"
                + userId + "," + userId + ",0)";
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

        LOG.info("Added summary four info to db");
    }

    protected long getCsmUserByLoginName(String loginName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Number regUserID = (Number) runner.query(connection,
                "select user_id from csm_user cu "
                        + "where cu.login_name like '%" + loginName + "%'",
                new ArrayHandler())[0];

        return regUserID.longValue();

    }

    protected void addDocument(TrialInfo info, String type, String filename)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = String
                .format("INSERT INTO document (identifier,type_code,active_indicator,file_name,study_protocol_identifier,"
                        + "inactive_comment_text,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id,"
                        + "original,deleted,study_inbox_id,ctro_user_date_created,ccct_user_date_created,ctro_user_id,ccct_user_name) VALUES "
                        + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'%s',true,'%s',%s,'',"
                        + "%s,%s,%s,%s,true,false,null,%s,%s,%s,%s);", type,
                        filename, info.id, today(), today(), info.csmUserID,
                        info.csmUserID, today(), today(), info.csmUserID,
                        info.csmUserID + "");
        runner.update(connection, sql);

    }

    protected void addDesginee(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        // insert organizational contact
        String sql = String
                .format("insert into organizational_contact(assigned_identifier ,person_identifier, organization_identifier, status_code) values"
                        + " (99, (select identifier from person where first_name='John'), "
                        + " (select identifier  from organization where name ='ClinicalTrials.gov'),'ACTIVE' )");
        runner.update(connection, sql);

        // insert study_contact
        sql = null;
        sql = String
                .format("INSERT INTO study_contact("
                        + " identifier, role_code, primary_indicator, address_line, delivery_address_line,"
                        + " city, state, postal_code, country_identifier, telephone, email,"
                        + " healthcare_provider_identifier, clinical_research_staff_identifier,"
                        + " study_protocol_identifier, status_code, status_date_range_low,"
                        + " date_last_created, date_last_updated, status_date_range_high,"
                        + " organizational_contact_identifier, user_last_created_id, user_last_updated_id,"
                        + " title, prs_user_name, comments)"
                        + " VALUES ("
                        + " 19000090, 'DESIGNEE_CONTACT', true , null, null, null, null,null,null,  '866-319-4357' ,'sample@example.com',"
                        + " null, null, "
                        + info.id
                        + ", 'ACTIVE', '2014-11-15 15:41:44.529', null, null, null,"
                        + " (select identifier from organizational_contact)"
                        + ",   null, null, null, 'D PRS', 'designee contact comments'    )  ");
        runner.update(connection, sql);

    }

    protected void deleteTrialDocuments(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(connection,
                "delete from document where study_protocol_identifier="
                        + info.id);
    }

    protected void deleteContact() throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(connection, "delete from organizational_contact");
        runner.update(connection,
                "delete from study_contact where identifier =19000090");
    }

    /**
     * @param info
     * @throws SQLException
     */
    protected void pickUsers(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final Object[] regUserResults = runner.query(connection,
                "select identifier, csm_user_id from registry_user limit 1",
                new ArrayHandler());
        info.registryUserID = (Long) regUserResults[0];
        info.csmUserID = (Long) regUserResults[1];
    }

    protected Number createStudyInbox(TrialInfo trial) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_inbox (identifier,comments,open_date,close_date,study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id,type_code,admin,scientific,admin_close_date,scientific_close_date,admin_ack_user_id,scientific_ack_user_id)"
                + " VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'Trial has been updated from ClinicalTrials.gov',"
                + millenium()
                + ", null, "
                + trial.id
                + ","
                + millenium()
                + ","
                + millenium()
                + ","
                + trial.csmUserID
                + ","
                + trial.csmUserID
                + "," + "'UPDATE',null,null,null,null,null,null)";
        runner.update(connection, sql);
        return (Number) runner
                .query(connection,
                        "select identifier from study_inbox order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }

    protected Number createStudyCheckout(TrialInfo trial, Date date)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_checkout(identifier, study_protocol_identifier, user_identifier, checkout_type, checkout_date, checkin_date)"
                + " VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')), "
                + trial.id
                + ",'"
                + trial.csmUserID
                + "', 'ADMINISTRATIVE', '"
                + new Timestamp(date.getTime())
                + "', '"
                + new Timestamp(date.getTime()) + "')";
        runner.update(connection, sql);
        return (Number) runner
                .query(connection,
                        "select identifier from study_checkout order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }

    protected void makeAdminPerformed(Number studyInboxID, Number csmUserID)
            throws SQLException {
        new QueryRunner().update(connection,
                "update study_inbox set admin=true, admin_close_date="
                        + pastMillenium() + ", admin_ack_user_id=" + csmUserID
                        + " where identifier=" + studyInboxID);
    }

    protected void makeScientificPerformed(Number studyInboxID, Number csmUserID)
            throws SQLException {
        new QueryRunner().update(connection,
                "update study_inbox set scientific=true, scientific_close_date="
                        + pastMillenium() + ", scientific_ack_user_id="
                        + csmUserID + " where identifier=" + studyInboxID);
    }

    protected void makeAdminPending(Number studyInboxID, Number ctgovEntryID)
            throws SQLException {
        new QueryRunner().update(connection,
                "update study_inbox set admin=true where identifier="
                        + studyInboxID);
        new QueryRunner().update(connection,
                "update ctgovimport_log set admin=true, review_required=true where identifier="
                        + ctgovEntryID);
    }

    protected void makeScientificPending(Number studyInboxID,
            Number ctgovEntryID) throws SQLException {
        new QueryRunner().update(connection,
                "update study_inbox set scientific=true where identifier="
                        + studyInboxID);
        new QueryRunner()
                .update(connection,
                        "update ctgovimport_log set scientific=true, review_required=true where identifier="
                                + ctgovEntryID);
    }

    protected Number createCSMUser() throws SQLException {
        Number id = getNextIdForCsmUser();

        String loginName = "test" + id.intValue();

        String sql = "INSERT INTO csm_user VALUES (" + id.intValue() + ", "
                + "'" + loginName
                + "', '', '', NULL, NULL, NULL, NULL, 'BtM2GNbiAxg=', "
                + "NULL, NULL, NULL, '2015-08-04', 0, NULL, NULL)";

        new QueryRunner().update(connection, sql);

        return id;
    }

    /**
     * @return
     * @throws SQLException
     */
    protected Number getNextIdForCsmUser() throws SQLException {
        String idSql = "select nextval('csm_user_user_id_seq')";
        Number id = (Number) new QueryRunner().query(connection, idSql,
                new ArrayHandler())[0];
        return id;
    }

    protected Number createCSMUser(String loginName) throws SQLException {
        Number id = getNextIdForCsmUser();

        String sql = "INSERT INTO csm_user (user_id, login_name,first_name,last_name,update_date,migrated_flag) VALUES ("
                + id.intValue()
                + ", "
                + "'"
                + loginName
                + "', '', '', now(),0)";

        new QueryRunner().update(connection, sql);

        return id;
    }

    protected void unassignUserFromGroup(String loginName, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sqlTemplate = "delete from csm_user_group where user_id = "
                + "(select user_id from csm_user where login_name = '%s') "
                + "and group_id = (select group_id from csm_group where group_name='%s')";
        runner.update(connection, String.format(sqlTemplate, loginName, group));
    }

    protected void assignUserToGroup(String loginName, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into csm_user_group (user_id, group_id) values ((select user_id from csm_user where login_name='"
                + loginName
                + "'), (select group_id from csm_group where group_name='"
                + group + "')  )";
        runner.update(connection, sql);
    }

    protected void assignUserToGroup(Number userID, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into csm_user_group (user_id, group_id) values ("
                + userID
                + ", (select group_id from csm_group where group_name='"
                + group + "')  )";
        runner.update(connection, sql);
    }

    protected boolean isUserInGroup(Number userID, String group)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select user_group_id from csm_user_group where user_id="
                + userID
                + " and group_id=(select group_id from csm_group where group_name='"
                + group + "')";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        return results != null;
    }

    protected Number createRegistryUser(Number csmUserId) throws SQLException {
        return createRegistryUserForDCP(csmUserId);
    }

    protected Number createRegistryUserForDCP(Number csmUserId)
            throws SQLException {
        return createRegistryUser(csmUserId,
                "National Cancer Institute Division of Cancer Prevention");
    }

    protected Number createRegistryUserForCTEP(Number csmUserId)
            throws SQLException {
        return createRegistryUser(csmUserId,
                "Cancer Therapy Evaluation Program");
    }

    protected Number createRegistryUser(Number csmUserId,
            String organizationName) throws SQLException {

        String orgIdentifier = getOrgPoIdByName(organizationName);

        QueryRunner runner = new QueryRunner();
        String idSql = "SELECT NEXTVAL('HIBERNATE_SEQUENCE')";

        Number id = (Number) runner
                .query(connection, idSql, new ArrayHandler())[0];
        String firstName = "test" + id.intValue();

        String sql = "INSERT INTO registry_user VALUES ("
                + id.intValue()
                + ", "
                + "'"
                + firstName
                + "', NULL, 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', "
                + String.format("'%s',", organizationName)
                + csmUserId
                + ", NULL, NULL, 'Test Org', NULL, NULL, 'testusersel@example.com', "
                + orgIdentifier
                + ", 'MEMBER', NULL, NULL, true, false, false, false, NULL)";

        runner.update(connection, sql);

        return id;
    }

    protected boolean checkReportViewerColumnUpdated(Number id)
            throws SQLException {
        boolean retVal = false;

        QueryRunner runner = new QueryRunner();
        String sql = "select enable_reports, report_groups from registry_user where identifier = "
                + id.intValue();

        Object[] queryResponse = runner.query(connection, sql,
                new ArrayHandler());

        if (queryResponse != null && queryResponse.length >= 2) {

            boolean enableReports = (boolean) queryResponse[0];
            String reportGroups = (String) queryResponse[1];

            if (enableReports && reportGroups.equals("Data Table 4")) {
                retVal = true;
            }
        }

        return retVal;
    }

    protected int removeCSMUser(Number userId) throws SQLException {
        QueryRunner runner = new QueryRunner();

        String sql = "delete from csm_user where user_id=" + userId;

        int count = runner.update(connection, sql);

        return count;
    }

    protected int removeRegistryUser(Number userId) throws SQLException {
        QueryRunner runner = new QueryRunner();

        String sql = "delete from registry_user where identifier=" + userId;

        int count = runner.update(connection, sql);

        return count;
    }

    protected Number createCtGovImportLogEntry(TrialInfo trial,
            Number studyInboxID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO ctgovimport_log (identifier,nci_id,nct_id,trial_title,action_performed,import_status,"
                + "user_created,date_created,review_required,admin,scientific,study_inbox_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE'))"
                + " ,'"
                + trial.nciID
                + "'"
                + " ,'"
                + trial.nciID
                + "'"
                + " ,'"
                + trial.title
                + "',"
                + "'Update',"
                + "'Success',"
                + "'ClinicalTrials.gov Import',"
                + millenium()
                + ","
                + "false,"
                + "false," + "false," + "" + studyInboxID + ")";
        runner.update(connection, sql);
        return (Number) runner
                .query(connection,
                        "select identifier from ctgovimport_log order by identifier desc limit 1",
                        new ArrayHandler())[0];
    }

    protected TrialInfo createAcceptedTrial() throws SQLException {
        return createAcceptedTrial(false);
    }

    protected TrialInfo createAcceptedTrial(boolean isAbbreviated,
            boolean skipDocuments) throws SQLException {
        TrialInfo info = createSubmittedTrial(isAbbreviated, skipDocuments);
        addDWS(info, "ACCEPTED");
        addMilestone(info, "SUBMISSION_ACCEPTED");
        return info;
    }

    protected TrialInfo createAcceptedTrial(boolean isAbbreviated,
            boolean skipDocuments, String orgName) throws SQLException {
        TrialInfo info = createSubmittedTrial(isAbbreviated, skipDocuments,
                orgName);
        addDWS(info, "ACCEPTED");
        addMilestone(info, "SUBMISSION_ACCEPTED");
        return info;
    }

    protected TrialInfo createAcceptedTrial(boolean isAbbreviated)
            throws SQLException {
        return createAcceptedTrial(isAbbreviated, false);
    }

    protected TrialInfo createAcceptedTrial(boolean isAbbreviated,
            String orgName) throws SQLException {
        return createAcceptedTrial(isAbbreviated, false, orgName);
    }

    /**
     * @return
     * @throws SQLException
     */
    protected final TrialInfo createAndSelectTrial() throws SQLException {
        deactivateAllTrials();
        TrialInfo info = createAcceptedTrial(true);
        selectTrialInPA(info);
        return info;
    }

    /**
     * @param info
     */
    protected final void selectTrialInPA(TrialInfo info) {
        login("/pa", "ctrpsubstractor", "pass");
        disclaimer(true);
        searchAndSelectTrial(info.title);
    }

    protected void verifySiteIsNowClosed(TrialInfo info, String orgName,
            String expectedStatusCode) throws SQLException {
        final Number siteID = findParticipatingSite(info, orgName);
        List<SiteStatus> hist = getSiteStatusHistory(siteID);
        assertEquals(2, hist.size());
        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals(
                RecruitmentStatusCode.getByCode(expectedStatusCode).name(),
                hist.get(1).statusCode);

    }

    /**
     * @param info
     * @param siteCtepId
     */
    protected final void addSiteToTrial(TrialInfo info, String siteCtepId,
            String status, boolean isEarlierDate) {
        clickAndWait("link=Participating Sites");
        clickAndWait("link=Add");
        clickAndWaitAjax("link=Look Up");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("orgCtepIdSearch", 15);
        selenium.type("orgCtepIdSearch", siteCtepId);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        if (s.isElementPresent("siteLocalTrialIdentifier"))
            selenium.type("siteLocalTrialIdentifier", info.uuid);
        selenium.select("recStatus", status);
        if (!isEarlierDate) {
            selenium.type("id=recStatusDate", today);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String earlierDate = MONTH_DAY_YEAR_FMT.format(calendar.getTime());
            selenium.type("id=recStatusDate", earlierDate);
        }

        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));

        selenium.click("link=Investigators");
        clickAndWaitAjax("link=Add");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        clickAndWaitAjax("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        pause(2000);
        driver.switchTo().defaultContent();
        assertTrue(selenium.isTextPresent("One item found"));
    }

    /**
     * @param info
     * @param siteCtepId
     */
    protected final void addSiteToTrialWithName(TrialInfo info, String name,
            String status) {
        clickAndWait("link=Participating Sites");
        clickAndWait("link=Add");
        clickAndWaitAjax("link=Look Up");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("orgNameSearch", 15);
        selenium.type("orgNameSearch", name);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        if (s.isElementPresent("siteLocalTrialIdentifier"))
            selenium.type("siteLocalTrialIdentifier", info.uuid);
        selenium.select("recStatus", status);
        selenium.type("id=recStatusDate", today);
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));

    }

    /**
     * @param info
     * @param siteCtepId
     */
    protected final void addSiteToTrialWithNameAndDate(TrialInfo info,
            String name, String status, String date, boolean isAccrualDate) {
        clickAndWait("link=Participating Sites");
        clickAndWait("link=Add");
        clickAndWaitAjax("link=Look Up");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("orgNameSearch", 15);
        selenium.type("orgNameSearch", name);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        if (s.isElementPresent("siteLocalTrialIdentifier"))
            selenium.type("siteLocalTrialIdentifier", info.uuid);
        selenium.select("recStatus", status);
        selenium.type("id=recStatusDate", date);

        if (isAccrualDate) {

            selenium.type("dateOpenedForAccrual", date);

        }
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));

    }

    protected void addSOS(TrialInfo info, String code) throws SQLException {
        final String statusDate = today_midnight();
        addSOS(info, code, statusDate);

    }

    /**
     * @param info
     * @param code
     * @param statusDate
     * @throws SQLException
     */
    protected void addSOS(TrialInfo info, String code, final String statusDate)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_overall_status (identifier,comment_text,status_code,status_date,"
                + "study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,"
                + "user_last_updated_id,system_created) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),null,'"
                + code
                + "',"
                + statusDate
                + " ,"
                + info.id
                + ","
                + "null,null,null,null,false)";
        runner.update(connection, sql);

        String sql2 = "INSERT INTO study_recruitment_status (identifier,status_code,status_date,"
                + "study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,"
                + "user_last_updated_id) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + code
                + "',"
                + statusDate
                + " ,"
                + info.id
                + ","
                + "null,null,null,null)";
        runner.update(connection, sql2);
    }

    protected void changeTrialStatus(TrialInfo info, String code)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "UPDATE study_overall_status SET status_code='" + code
                + "' where study_protocol_identifier=" + info.id;
        runner.update(connection, sql);

        sql = "UPDATE study_recruitment_status SET status_code='" + code
                + "' where study_protocol_identifier=" + info.id;
        runner.update(connection, sql);

    }

    protected void deleteCurrentTrialStatus(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "UPDATE study_overall_status SET deleted=true WHERE current=true and study_protocol_identifier="
                + info.id;
        assertEquals(1, runner.update(connection, sql));
    }

    protected void deleteEntireTrialStatusHistory(TrialInfo info)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(connection,
                "DELETE FROM study_overall_status WHERE study_protocol_identifier="
                        + info.id);
        runner.update(connection,
                "DELETE FROM  study_recruitment_status WHERE study_protocol_identifier="
                        + info.id);
    }

    protected String today() {
        return String.format("{ts '%s'}",
                new Timestamp(System.currentTimeMillis()).toString());
    }

    private String today_midnight() {
        return String.format(
                "{ts '%s'}",
                new Timestamp(DateUtils.truncate(new Date(),
                        Calendar.DAY_OF_MONTH).getTime()).toString());
    }

    protected String yday_midnight() {
        return String.format(
                "{ts '%s'}",
                new Timestamp(DateUtils.truncate(
                        DateUtils.addDays(new Date(), -1),
                        Calendar.DAY_OF_MONTH).getTime()).toString());
    }

    private String millenium() {
        return "{ts '2000-01-01 00:00:00.000'}";
    }

    private String pastMillenium() {
        return "{ts '2000-01-02 00:00:00.000'}";
    }

    protected void addMilestone(TrialInfo info, String code)
            throws SQLException {
        final String milestoneDate = today();
        addMilestone(info, code, milestoneDate);
    }

    /**
     * @param info
     * @param code
     * @param milestoneDate
     * @throws SQLException
     */
    protected void addMilestone(TrialInfo info, String code,
            final String milestoneDate) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_milestone (identifier,comment_text,milestone_code,milestone_date,"
                + "study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id,"
                + "rejection_reason_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),null,'"
                + code
                + "',"
                + ""
                + milestoneDate
                + ","
                + info.id
                + ","
                + ""
                + milestoneDate
                + ","
                + milestoneDate
                + ","
                + info.csmUserID
                + "," + info.csmUserID + ",null)";
        runner.update(connection, sql);
    }

    private void addPI(TrialInfo info, String poPersonID) throws SQLException {
        Number paPersonID = findOrCreatePersonByPoId(poPersonID);
        Number crsID = findOrCreateCrs(paPersonID, "ClinicalTrials.gov");
        Number hcpID = findOrCreateHcp(paPersonID, "ClinicalTrials.gov");

        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_contact"
                + "(identifier,role_code,primary_indicator,address_line,delivery_address_line,city,state,postal_code,"
                + "country_identifier,telephone,email,healthcare_provider_identifier,clinical_research_staff_identifier,"
                + "study_protocol_identifier,status_code,status_date_range_low,date_last_created,date_last_updated,"
                + "status_date_range_high,organizational_contact_identifier,user_last_created_id,user_last_updated_id,"
                + "title) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'STUDY_PRINCIPAL_INVESTIGATOR',null,null,null,null,null,null,null,null"
                + ",null," + hcpID + "," + crsID + "," + info.id
                + ",'PENDING'," + today() + ",null,null,null,null,"
                + info.csmUserID + "," + info.csmUserID + ",null)";
        runner.update(connection, sql);

    }

    protected final Number findParticipatingSite(TrialInfo trial, String orgName)
            throws SQLException {
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
                + ")" + " WHERE org.name='" + orgName
                + "' AND ss.study_protocol_identifier=" + trial.id
                + " AND ((ss.functional_code)::text = 'TREATING_SITE'::text)";

        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number siteID = results != null ? (Number) results[0] : null;
        return siteID;
    }

    private Number findOrCreateHcp(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select crs.identifier from healthcare_provider crs "
                + "inner join organization o on crs.organization_identifier=o.identifier"
                + "  where person_identifier="
                + paPersonID
                + " and o.name='"
                + orgName + "' limit 1";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number hcpID = results != null ? (Number) results[0] : null;
        if (hcpID == null) {
            createHcp(paPersonID, orgName);
            hcpID = (Number) runner.query(connection, sql, new ArrayHandler())[0];
        }
        return hcpID;
    }

    private Number findOrCreateCrs(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select crs.identifier from clinical_research_staff crs "
                + "inner join organization o on crs.organization_identifier=o.identifier"
                + "  where person_identifier="
                + paPersonID
                + " and o.name='"
                + orgName + "' limit 1";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number crsID = results != null ? (Number) results[0] : null;
        if (crsID == null) {
            createCrs(paPersonID, orgName);
            crsID = (Number) runner.query(connection, sql, new ArrayHandler())[0];
        }
        return crsID;
    }

    private void createHcp(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO healthcare_provider (identifier,assigned_identifier,person_identifier,organization_identifier,"
                + "status_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) ,'1' ,"
                + paPersonID + " ," + getOrgIdByName(orgName) + " ,'PENDING')";
        runner.update(connection, sql);
    }

    private void createCrs(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO clinical_research_staff (identifier,assigned_identifier,person_identifier,organization_identifier,"
                + "status_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) ,'1' ,"
                + paPersonID + " ," + getOrgIdByName(orgName) + " ,'PENDING')";
        runner.update(connection, sql);
    }

    protected Number getOrgIdByName(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner.query(connection,
                "select o.identifier from organization o " + "where o.name='"
                        + orgName + "' limit 1", new ArrayHandler())[0];
    }

    protected String getOrgPoIdByName(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner.query(connection,
                "select o.assigned_identifier from organization o "
                        + "where o.name='" + orgName + "' limit 1",
                new ArrayHandler())[0];
    }

    private Number findPersonByPoId(String poPersonID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select identifier from person where assigned_identifier='"
                + poPersonID + "'";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());

        Number paID = results != null ? (Number) results[0] : null;
        return paID;
    }

    private Number findOrCreatePersonByPoId(String poPersonID)
            throws SQLException {

        Number paID = findPersonByPoId(poPersonID);
        if (paID == null) {
            createPerson(poPersonID, "John", "G", "Doe", "Mr.", "III");
            paID = findPersonByPoId(poPersonID);
        }

        return paID;
    }

    protected List<TrialStatus> getTrialStatusHistory(TrialInfo trial)
            throws SQLException {
        final String sql = "select status_code, status_date, addl_comments, comment_text from study_overall_status "
                + "where deleted=false and study_protocol_identifier="
                + (trial.id != null ? trial.id : getTrialIdByNciId(trial.nciID))
                + " ORDER BY status_date ASC, identifier ASC";
        return loadTrialStatuses(sql);
    }

    protected String getCurrentDWS(TrialInfo trial) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner
                .query(connection,
                        "select status_code from document_workflow_status "
                                + "where study_protocol_identifier="
                                + (trial.id != null ? trial.id
                                        : getTrialIdByNciId(trial.nciID))
                                + " ORDER BY status_date_range_low desc, identifier desc LIMIT 1",
                        new ArrayHandler())[0];
    }

    protected List<TrialStatus> getDeletedTrialStatuses(TrialInfo trial)
            throws SQLException {
        final String sql = "select status_code, status_date, addl_comments, comment_text from study_overall_status "
                + "where deleted=true and study_protocol_identifier="
                + (trial.id != null ? trial.id : getTrialIdByNciId(trial.nciID))
                + " ORDER BY status_date ASC, identifier ASC";
        return loadTrialStatuses(sql);
    }

    protected List<SiteStatus> getSiteStatusHistory(Number siteID)
            throws SQLException {
        final String sql = "select status_code, status_date, comments from study_site_accrual_status "
                + "where deleted=false and study_site_identifier="
                + siteID
                + " ORDER BY status_date ASC, identifier ASC";
        return loadSiteStatuses(sql);
    }

    protected List<SiteStatus> getDeletedSiteStatusHistory(Number siteID)
            throws SQLException {
        final String sql = "select status_code, status_date, comments from study_site_accrual_status "
                + "where deleted=true and study_site_identifier="
                + siteID
                + " ORDER BY status_date ASC, identifier ASC";
        return loadSiteStatuses(sql);
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    private List<SiteStatus> loadSiteStatuses(final String sql)
            throws SQLException {
        List<SiteStatus> list = new ArrayList<>();
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
            SiteStatus status = new SiteStatus();
            status.statusCode = (String) row[0];
            status.statusDate = (Date) row[1];
            status.comments = (String) row[2];
            list.add(status);
        }
        return list;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    protected Tweet getTweetById(final Number id) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner
                .query(connection,
                        "select tweet_text, status, sent_date, errors, identifier from tweets where identifier="
                                + id, new ArrayListHandler());
        Object[] row = results.get(0);
        Tweet t = new Tweet();
        t.text = (String) row[0];
        t.status = (String) row[1];
        t.sentDate = (Date) row[2];
        t.errors = (String) row[3];
        t.tweetID = (Number) row[4];
        return t;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    protected Account getAccountByName(final String name) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection,
                "select username, encrypted_password from accounts where account_name='"
                        + name + "'", new ArrayListHandler());
        Object[] row = results.get(0);
        Account a = new Account();
        a.username = (String) row[0];
        a.encryptedPassword = (String) row[1];

        gov.nih.nci.pa.domain.Account account = new gov.nih.nci.pa.domain.Account();
        account.setEncryptedPassword(a.encryptedPassword);
        a.unencryptedPassword = account.getDecryptedPassword();

        return a;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    protected RegistryUser getRegistryUserByEmail(final String email)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner
                .query(connection,
                        "select email_address, csm_user_id, token from registry_user where email_address ilike '"
                                + email + "'", new ArrayListHandler());
        Object[] row = results.get(0);
        RegistryUser ru = new RegistryUser();
        ru.email = (String) row[0];
        ru.csmUserID = (Number) row[1];
        ru.token = (String) row[2];
        return ru;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    private List<TrialStatus> loadTrialStatuses(final String sql)
            throws SQLException {
        List<TrialStatus> list = new ArrayList<>();
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
            TrialStatus status = new TrialStatus();
            status.statusCode = (String) row[0];
            status.statusDate = (Date) row[1];
            status.comments = (String) row[2];
            status.whyStopped = (String) row[3];
            list.add(status);
        }
        return list;
    }

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    protected List<String> getTrialProgramCodes(final TrialInfo trial)
            throws SQLException {
        final Number trialID = trial.id != null ? trial.id
                : getTrialIdByNciId(trial.nciID);
        return getTrialProgramCodes(trialID);
    }

    /**
     * @param trialID
     * @return
     * @throws SQLException
     */
    protected List<String> getTrialProgramCodes(final Number trialID)
            throws SQLException {
        final String sql = "select program_code from program_code pc inner join study_program_code spc on spc.program_code_id=pc.identifier "
                + " inner join study_protocol sp on sp.identifier=spc.study_protocol_id where sp.identifier="
                + trialID + " ORDER BY program_code ASC";
        List<String> list = new ArrayList<>();
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
            list.add((String) row[0]);
        }
        return list;
    }

    protected void assignProgramCode(TrialInfo trial, int familyPoId,
            String code) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_program_code (program_code_id, study_protocol_id) "
                + "VALUES ((select identifier from program_code where program_code='"
                + code
                + "' and family_id=(select identifier from family where po_id="
                + familyPoId + ")), " + trial.id + ")";
        runner.update(connection, sql);

    }

    protected TrialStatus getCurrentTrialStatus(TrialInfo trial)
            throws SQLException {
        final List<TrialStatus> trialStatusHistory = getTrialStatusHistory(trial);
        return trialStatusHistory.get(trialStatusHistory.size() - 1);
    }

    private void createPerson(String poPersonID, String firstName,
            String middleName, String lastName, String prefix, String suffix)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO person (identifier,assigned_identifier,first_name,middle_name,last_name,"
                + "status_code,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + poPersonID
                + "','"
                + firstName
                + "','"
                + middleName
                + "','"
                + lastName + "','PENDING',null," + "null,null,null)";
        runner.update(connection, sql);

    }

    protected void replaceLeadOrg(TrialInfo info, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "DELETE FROM study_site WHERE study_protocol_identifier="
                + info.id + " and functional_code='LEAD_ORGANIZATION'";
        runner.update(connection, sql);
        addLeadOrg(info, orgName);
    }

    private void addLeadOrg(TrialInfo info, String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        info.leadOrgID = info.uuid;
        String sql = "INSERT INTO study_site (identifier,functional_code,local_sp_indentifier,"
                + "review_board_approval_number,review_board_approval_date,review_board_approval_status_code,"
                + "target_accrual_number,study_protocol_identifier,healthcare_facility_identifier,"
                + "research_organization_identifier,oversight_committee_identifier,"
                + "status_code,status_date_range_low,date_last_created,date_last_updated,status_date_range_high,"
                + "review_board_organizational_affiliation,program_code_text,accrual_date_range_low,accrual_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'LEAD_ORGANIZATION','"
                + info.leadOrgID
                + "',null,null,null,null,"
                + info.id
                + " "
                + ",null,"
                + getResearchOrgId(orgName)
                + ",null,'PENDING',{ts '2014-04-16 14:56:08.559'},{ts '2014-04-16 14:56:08.559'},"
                + "{ts '2014-04-16 14:56:08.559'},null,null,null,"
                + "null,null," + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    protected void addNctIdentifier(TrialInfo info, String nctID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        info.nctID = nctID;
        String sql = "INSERT INTO study_site (identifier,functional_code,local_sp_indentifier,"
                + "review_board_approval_number,review_board_approval_date,review_board_approval_status_code,"
                + "target_accrual_number,study_protocol_identifier,healthcare_facility_identifier,"
                + "research_organization_identifier,oversight_committee_identifier,"
                + "status_code,status_date_range_low,date_last_created,date_last_updated,status_date_range_high,"
                + "review_board_organizational_affiliation,program_code_text,accrual_date_range_low,accrual_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'IDENTIFIER_ASSIGNER','"
                + nctID
                + "',null,null,null,null,"
                + info.id
                + " "
                + ",null,"
                + getResearchOrgId("ClinicalTrials.gov")
                + ",null,'PENDING',now(),now(),"
                + "now(),null,null,null,"
                + "null,null," + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    protected Number addParticipatingSite(TrialInfo trial, String orgName,
            String status) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_site (identifier,functional_code,local_sp_indentifier,"
                + "review_board_approval_number,review_board_approval_date,review_board_approval_status_code,"
                + "target_accrual_number,study_protocol_identifier,healthcare_facility_identifier,"
                + "research_organization_identifier,oversight_committee_identifier,"
                + "status_code,status_date_range_low,date_last_created,date_last_updated,status_date_range_high,"
                + "review_board_organizational_affiliation,program_code_text,accrual_date_range_low,accrual_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'TREATING_SITE','',null,null,null,null,"
                + trial.id
                + ","
                + findOrCreateHcf(orgName)
                + ",null,null,'ACTIVE',now(),now(),"
                + "now(),null,null,null,"
                + "null,null," + trial.csmUserID + "," + trial.csmUserID + ")";
        runner.update(connection, sql);

        runner.update(
                connection,
                "insert into study_site_accrual_status(identifier, status_code, status_date, study_site_identifier, deleted) values "
                        + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),"
                        + "'"
                        + status
                        + "', now(), (select max (identifier) from study_site), false)");

        return (Integer) runner.query(connection,
                "select max (identifier) from study_site", new ArrayHandler())[0];

    }

    protected void addSiteInvestigator(TrialInfo trial, String siteOrgName,
            String invPoId, String firstName, String middleName,
            String lastName, String role) throws SQLException {
        Number paID = findPersonByPoId(invPoId);
        if (paID == null) {
            createPerson(invPoId, firstName, middleName, lastName, "Mr.", "I");
            paID = findPersonByPoId(invPoId);
        }

        QueryRunner runner = new QueryRunner();

        Number crsID = findOrCreateCrs(paID, siteOrgName);
        Number hcpID = findOrCreateHcp(paID, siteOrgName);
        Number siteID = findParticipatingSite(trial, siteOrgName);

        String invSql = "insert into study_site_contact "
                + "(identifier, role_code, primary_indicator, study_protocol_identifier, "
                + "status_code,status_date_range_low, healthcare_provider_identifier,"
                + "clinical_research_staff_identifier, study_site_identifier) values("
                + "(SELECT NEXTVAL('HIBERNATE_SEQUENCE'))," + "'" + role
                + "', false," + trial.id + ", 'PENDING'," + today() + ","
                + hcpID + "," + crsID + "," + siteID + ")";
        runner.update(connection, invSql);

    }

    private Number findOrCreateHcf(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select hcf.identifier from healthcare_facility hcf "
                + "inner join organization o on hcf.organization_identifier=o.identifier"
                + "  where o.name='" + orgName + "' limit 1";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number hcfID = results != null ? (Number) results[0] : null;
        if (hcfID == null) {
            createHcf(orgName);
            hcfID = (Number) runner.query(connection, sql, new ArrayHandler())[0];
        }
        return hcfID;
    }

    private void createHcf(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO healthcare_facility (identifier,assigned_identifier,status_code,status_date_range_low,organization_identifier) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) ,'"
                + getOrgIdByName(orgName)
                + "','PENDING',now(),"
                + getOrgIdByName(orgName) + ")";
        runner.update(connection, sql);

    }

    protected void addSponsor(TrialInfo info, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        info.leadOrgID = info.uuid;
        String sql = "INSERT INTO study_site (identifier,functional_code,local_sp_indentifier,"
                + "review_board_approval_number,review_board_approval_date,review_board_approval_status_code,"
                + "target_accrual_number,study_protocol_identifier,healthcare_facility_identifier,"
                + "research_organization_identifier,oversight_committee_identifier,"
                + "status_code,status_date_range_low,date_last_created,date_last_updated,status_date_range_high,"
                + "review_board_organizational_affiliation,program_code_text,accrual_date_range_low,accrual_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'SPONSOR','"
                + info.leadOrgID
                + "',null,null,null,null,"
                + info.id
                + " "
                + ",null,"
                + getResearchOrgId(orgName)
                + ",null,'PENDING',{ts '2014-04-16 14:56:08.559'},{ts '2014-04-16 14:56:08.559'},"
                + "{ts '2014-04-16 14:56:08.559'},null,null,null,"
                + "null,null," + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    protected void setSeciont801Indicator(TrialInfo info, Boolean section801)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set section801_indicator="
                + section801 + " where identifier = " + info.id;
        ;
        runner.update(connection, sql);
    }

    protected void setPCD(TrialInfo info, String pcd,
            ActualAnticipatedTypeCode type) throws SQLException {
        QueryRunner runner = new QueryRunner();
        info.leadOrgID = info.uuid;
        String sql = "update study_protocol set PRI_COMPL_DATE =" + getTS(pcd)
                + ", PRI_COMPL_DATE_TYPE_CODE ='" + type
                + "' where identifier = " + info.id;

        runner.update(connection, sql);
    }

    /**
     * Returns a timestamp string that can be used in the sql for the give date
     * string in the format yyyy-mm-dd
     * 
     * @param date
     * @return ts sting
     */
    protected String getTS(String date) {
        return "{ts '" + date + " 00:00:00.000'}";
    }

    private Number getResearchOrgId(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select ro.identifier from research_organization ro inner join organization o "
                                + "on o.identifier=ro.organization_identifier and o.name='"
                                + orgName + "' limit 1", new ArrayHandler())[0];

    }

    protected Number getTrialIdByNct(String nctID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select study_protocol_identifier from rv_trial_id_nct where local_sp_indentifier='"
                                + nctID + "'", new ArrayHandler())[0];
    }

    protected Number getTrialIdByNciId(String nciID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select study_protocol_id from rv_trial_id_nci inner join study_protocol sp on sp.identifier=study_protocol_id "
                                + "where sp.status_code='ACTIVE' and extension='"
                                + nciID + "'", new ArrayHandler())[0];
    }

    protected Number getTrialIdByLeadOrgID(String loID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select study_protocol_identifier from rv_lead_organization inner join study_protocol on "
                                + "study_protocol.identifier=study_protocol_identifier "
                                + " where study_protocol.status_code='ACTIVE' and local_sp_indentifier='"
                                + loID + "'", new ArrayHandler())[0];
    }

    protected Object getTrialField(final TrialInfo trial,
            final String dbColumnName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return runner.query(connection, "select " + dbColumnName
                + " from study_protocol where identifier=" + trial.id,
                new ArrayHandler())[0];
    }

    protected Number waitForTrialToRegister(String loID, int seconds)
            throws SQLException {
        long stamp = System.currentTimeMillis();
        do {
            try {
                return getTrialIdByLeadOrgID(loID);
            } catch (Exception e) {
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        } while (System.currentTimeMillis() - stamp < seconds * 1000);
        return null;
    }

    protected TrialInfo acceptTrialByNciId(String nciID, String leadOrgID)
            throws SQLException {
        TrialInfo info = new TrialInfo();
        info.nciID = nciID;
        info.id = (Long) getTrialIdByLeadOrgID(leadOrgID);
        info.leadOrgID = leadOrgID;
        pickUsers(info);
        addDWS(info, "ACCEPTED");
        addMilestone(info, "SUBMISSION_ACCEPTED");
        return info;

    }

    protected TrialInfo acceptTrialByNciIdWithGivenDWS(String nciID,
            String leadOrgID, String status) throws SQLException {
        TrialInfo info = new TrialInfo();
        info.nciID = nciID;
        info.id = (Long) getTrialIdByLeadOrgID(leadOrgID);
        info.leadOrgID = leadOrgID;
        pickUsers(info);
        addDWS(info, status);
        addMilestone(info, "SUBMISSION_ACCEPTED");
        return info;

    }

    protected void addDWS(TrialInfo info, String status) throws SQLException {
        String stamp = new Timestamp(System.currentTimeMillis()).toString();
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO document_workflow_status (identifier,status_code,comment_text,status_date_range_low,"
                + "study_protocol_identifier,date_last_created,date_last_updated,status_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + status
                + "',null,"
                + "{ts '"
                + stamp
                + "'},"
                + info.id
                + ",{ts '"
                + stamp
                + "'},"
                + "{ts '"
                + stamp
                + "'},{ts '"
                + stamp + "'}," + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    protected void addOnHold(TrialInfo trial, String code, Date from, Date to,
            String category) throws SQLException {
        String stamp = new Timestamp(System.currentTimeMillis()).toString();
        QueryRunner runner = new QueryRunner();
        String sql = "insert into study_onhold (identifier, onhold_reason_code, onhold_date, offhold_date, study_protocol_identifier, "
                + "onhold_reason_category)"
                + " VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')), '"
                + code
                + "', "
                + jdbcTs(from)
                + ", "
                + jdbcTs(to)
                + ", "
                + trial.id
                + ", '" + category + "')";
        runner.update(connection, sql);
    }

    protected void addOnHold_Timestamp(TrialInfo trial, String code,
            String onHold_TS, String OffHold_TS, String category)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into study_onhold (identifier, onhold_reason_code, onhold_date, offhold_date, study_protocol_identifier, "
                + "onhold_reason_category)"
                + " VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')), '"
                + code
                + "', "
                + onHold_TS
                + ", "
                + OffHold_TS
                + ", "
                + trial.id
                + ", '" + category + "')";
        runner.update(connection, sql);
    }

    protected String jdbcTs(Date dt) {
        return dt != null ? "'" + new Timestamp(dt.getTime()).toString() + "'"
                : "null";
    }

    private void assignNciId(TrialInfo info) throws SQLException {
        info.nciID = "NCI-2014-" + RandomStringUtils.randomNumeric(8);
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_otheridentifiers "
                + "(study_protocol_id,null_flavor,displayable,extension,identifier_name,reliability,root,scope) "
                + "VALUES ("
                + info.id
                + ",null,null,'"
                + info.nciID
                + "','NCI study protocol entity identifier',null,'2.16.840.1.113883.3.26.4.3',null)";
        runner.update(connection, sql);
    }

    protected String getLastNciId() throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner
                .query(connection,
                        "select extension from study_otheridentifiers where root='2.16.840.1.113883.3.26.4.3'"
                                + " and identifier_name='NCI study protocol entity identifier'"
                                + " order by study_protocol_id desc LIMIT 1",
                        new ArrayHandler())[0];
    }

    protected void changeNciId(String from, String to) throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(
                connection,
                "update study_otheridentifiers set extension='"
                        + to
                        + "' where root='2.16.840.1.113883.3.26.4.3' and extension='"
                        + from + "'");
    }

    protected void deactivateTrialByNctId(String nctID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set status_code='INACTIVE' where exists"
                + " (select * from study_site ss where ss.study_protocol_identifier=study_protocol.identifier and ss.local_sp_indentifier='"
                + nctID + "')";
        runner.update(connection, sql);

        runner.update(connection,
                "delete from study_site where local_sp_indentifier='" + nctID
                        + "'");
    }

    protected final void deactivateTrialByLeadOrgId(String leadOrgID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(
                connection,
                "update study_protocol sp set status_code='INACTIVE' where exists "
                        + "(select local_sp_indentifier from study_site ss where ss.study_protocol_identifier=sp.identifier "
                        + "and ss.functional_code='LEAD_ORGANIZATION' and ss.local_sp_indentifier='"
                        + StringEscapeUtils.escapeSql(leadOrgID) + "')");
        runner.update(
                connection,
                "delete from study_site where functional_code='LEAD_ORGANIZATION' and local_sp_indentifier='"
                        + StringEscapeUtils.escapeSql(leadOrgID) + "'");
        LOG.info("De-activated trial with Lead Org ID of " + leadOrgID);
    }

    protected void deactivateAllTrials() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set status_code='INACTIVE'";
        runner.update(connection, sql);
        runner.update(connection,
                "update study_site set local_sp_indentifier='"
                        + UUID.randomUUID().toString()
                        + "' where local_sp_indentifier is not null");
        runner.update(connection, "delete from study_otheridentifiers");
    }

    protected void assignTrialOwner(String loginName, Long trialID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        Number regUserID = (Number) runner
                .query(connection,
                        "select identifier from registry_user ru inner join csm_user cu on ru.csm_user_id=cu.user_id where cu.login_name like '%"
                                + loginName + "%'", new ArrayHandler())[0];

        String sql = "INSERT INTO study_owner (study_id,user_id,enable_emails) VALUES ("
                + trialID + "," + regUserID + ", true)";
        runner.update(connection, sql);
    }

    protected void grantAccrualAccess(String username, long siteID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_site_accrual_access (identifier,study_site_identifier,status_code,status_date_range_low,"
                + "registry_user_id,source) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')), "
                + siteID
                + ", 'ACTIVE', '2013-03-21 13:35:09.109', "
                + "(select identifier from registry_user inner join csm_user on registry_user.csm_user_id=csm_user.user_id where login_name like '%"
                + username + "' )" + ", 'ACC_GENERATED' )";
        runner.update(connection, sql);

    }

    /**
     * @param paTrialID
     * @throws SQLException
     */
    protected void enableCtroOverride(final Number paTrialID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set ctro_override=true where identifier="
                + paTrialID;
        runner.update(connection, sql);
    }

    protected void setupFamilies() throws Exception {
        QueryRunner runner = new QueryRunner();
        runner.update(connection, "delete from family");
        for (int i : new int[] { 1, 2 }) {
            long count = (Long) runner.query(connection,
                    "select count(identifier) from family where po_id = " + i,
                    new ArrayHandler())[0];
            if (count <= 0) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 12);
                String endDate = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                runner.update(connection, String.format(
                        "INSERT INTO family(po_id, rep_period_end, rep_period_len_months) "
                                + "VALUES (%s, '%s', %s)", i, endDate, 12));
            }
            Integer familyId = (Integer) runner.query(connection,
                    "select identifier from family where po_id = " + i,
                    new ArrayHandler())[0];

            runner.update(connection,
                    "DELETE FROM program_code where family_id = " + familyId);
            for (int j : new int[] { 1, 2, 3, 4, 5, 6 }) {
                runner.update(connection, String.format(
                        "INSERT INTO program_code(family_id, program_code, program_name, status_code) "
                                + "VALUES (%s, '%s', '%s', '%s')", familyId,
                        "PG" + j, "Cancer Program" + j, "ACTIVE"));
            }
            runner.update(connection, String.format(
                    "INSERT INTO program_code(family_id, program_code, program_name, status_code) "
                            + "VALUES (%s, '%s', '%s', '%s')", familyId, "PG7",
                    "Cancer Program7", "INACTIVE"));

        }
    }

    protected void moveElementIntoView(By by) {
        WebElement element = driver.findElement(by);
        moveElementIntoView(element);
    }

    /**
     * @param element
     */
    protected void moveElementIntoView(WebElement element) {
        Point p = element.getLocation();
        ((JavascriptExecutor) driver).executeScript("window.scroll(" + p.getX()
                + "," + (p.getY() - 150) + ");");
        pause(200);
    }

    protected void hoverLink(String linkText) {
        By by = By.linkText(linkText);
        hover(by);
    }

    protected void hover(By by) {
        WebElement elem = driver.findElement(by);
        hover(elem);
    }

    /**
     * @param elem
     */
    protected void hover(WebElement elem) {
        Actions action = new Actions(driver);
        action.moveToElement(elem);
        action.perform();
    }

    /**
     * @throws SQLException
     */
    protected void setPaProperty(String name, String value) throws SQLException {
        new QueryRunner().update(connection, "update pa_properties set value='"
                + value + "' where name='" + name + "'");
    }

    protected void topWindow() {
        driver.switchTo().defaultContent();
        if (!isPhantomJS())
            driver.switchTo().parentFrame();
    }

    protected void log(String msg) {
        System.out.println(new Date().toLocaleString() + ": " + msg);
    }

    public static final class TrialInfo implements Comparable<TrialInfo> {

        public String nctID;
        public String uuid;
        public String title;
        public Long id;
        public String leadOrgID;
        public String nciID;
        public Long registryUserID;
        public Long csmUserID;
        public String rand;
        public String flaggedReason;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @Override
        public int compareTo(TrialInfo o) {
            return this.uuid.compareTo(o.uuid);
        }

    }

    public static final class TrialStatus {
        public String statusCode;
        public Date statusDate;
        public String comments;
        public String whyStopped;
        public boolean deleted;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

    }

    public static final class RegistryUser {
        public Number csmUserID;
        public String token;
        public String email;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static final class Account {
        public String username;
        public String encryptedPassword;
        public String unencryptedPassword;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static final class Tweet {
        public String text;
        public String status;
        public Date sentDate;
        public String errors;
        public Number tweetID;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static final class SiteStatus {
        public String statusCode;
        public Date statusDate;
        public String comments;
        public boolean deleted;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

    }

    public static void main(String[] args) {
        randomPort();
    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        boolean elementPresent = s.isElementPresent("select2-" + id
                + "-results");
        if (!elementPresent) {
            // odd behavior in FF, click again.
            sitesBox.click();
            elementPresent = s.isElementPresent("select2-" + id + "-results");
        }
        assertTrue(elementPresent);
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        xpath = By.xpath("//li[@role='treeitem' and text()='" + option + "']");
        waitForElementToBecomeAvailable(xpath, 10);
        driver.findElement(xpath).click();
        assertOptionSelected(option);
    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    public void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    @SuppressWarnings("deprecation")
    public void useSelect2ToUnselectOption(String option) {
        s.click("//li[@class='select2-selection__choice' and @title='" + option
                + "']/span[@class='select2-selection__choice__remove']");
        assertFalse(s.isElementPresent(getXPathForSelectedOption(option)));

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
