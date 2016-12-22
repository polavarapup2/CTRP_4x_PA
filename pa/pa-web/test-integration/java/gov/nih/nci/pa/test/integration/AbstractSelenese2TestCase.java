/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The coppa-commons
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This coppa-commons Software License (the License) is between NCI and You. You (or
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
 * its rights in the coppa-commons Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the coppa-commons Software; (ii) distribute and
 * have distributed to and by third parties the coppa-commons Software and any
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

import java.io.File;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author Denis G. Krylov
 */
public abstract class AbstractSelenese2TestCase extends TestCase {
    private static final int PAGE_TIMEOUT_SECONDS = 360;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private int serverPort = 39480;
    private String serverHostname = "localhost";
    private String driverClass = "org.openqa.selenium.firefox.FirefoxDriver";

    protected Selenium selenium;
    protected Selenium s;
    protected WebDriver driver;
    protected String url;

    protected File downloadDir = new File("./");

    protected volatile boolean testFailed;

    @SuppressWarnings("deprecation")
    @Override
    @Before
    public void setUp() throws Exception {
        testFailed = false;
        String hostname = getServerHostname();
        int port = getServerPort();
        String driverClass = getDriverClass();
        url = port == 0 ? "http://" + hostname : "http://" + hostname + ":"
                + port;
        System.out.println("URL: " + url);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir",
                downloadDir.getCanonicalPath());
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting",
                false);
        profile.setPreference("browser.download.manager.showAlertOnComplete",
                false);
        profile.setPreference("browser.download.manager.closeWhenDone", true);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.focusWhenStarting",
                false);
        profile.setPreference(
                "browser.helperApps.neverAsk.saveToDisk",
                "text/csv;"
                        + "application/octet-stream;"
                        + "application/rtf;"
                        + "application/x-rtf;"
                        + "text/richtext;"
                        + "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"
                        + "application/xlsx;" + "application/xls");
        final Class<?> clazz = Class.forName(driverClass);
        try {
            driver = (WebDriver) clazz.getConstructor(FirefoxProfile.class)
                    .newInstance(profile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            driver = (WebDriver) clazz.newInstance();
        }
        driver.manage().window().setSize(new Dimension(1500, 900));
        selenium = new WebDriverBackedSelenium(driver, url);
        selenium.setTimeout(toMillisecondsString(PAGE_TIMEOUT_SECONDS));
        driver.manage().timeouts()
                .pageLoadTimeout(PAGE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts()
                .setScriptTimeout(PAGE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        s = selenium;

    }

    @Override
    protected void runTest() throws Throwable {
        testFailed = true;
        super.runTest();
        testFailed = false;
    }

    /**
     * Converts seconds to milliseconds.
     * 
     * @param seconds
     *            the number of seconds to convert
     * @return the number of milliseconds in the give seconds
     */
    protected static String toMillisecondsString(long seconds) {
        return String.valueOf(seconds * MILLISECONDS_PER_SECOND);
    }

    /**
     * Waits for the page to load.
     */
    protected void waitForPageToLoad() {
        selenium.waitForPageToLoad(toMillisecondsString(PAGE_TIMEOUT_SECONDS));
    }

    /**
     * Waits for the given element to load.
     * 
     * @param id
     *            the id of the element to wait for
     * @param timeoutSeconds
     *            the number seconds to wait for
     */
    protected void waitForElementById(String id, int timeoutSeconds) {
        selenium.waitForCondition(
                "selenium.browserbot.getCurrentWindow().document.getElementById('"
                        + id + "');", toMillisecondsString(timeoutSeconds));
    }

    protected void waitForTextToAppear(By by, String text, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.textToBePresentInElement(by, text));
    }

    protected void waitForElementToBecomeVisible(By by, int timeoutSeconds) {
        waitForElementToBecomeAvailable(by, timeoutSeconds);
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
    }

    protected void waitForElementToBecomeClickable(By by, int timeoutSeconds) {
        waitForElementToBecomeVisible(by, timeoutSeconds);
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(driver
                .findElement(by)));
    }

    protected void waitForElementToBecomeAvailable(By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementToGoAway(final By by, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver wd) {
                return wd.findElements(by).isEmpty();
            }
        });
    }

    protected void waitForElementToBecomeInvisible(final By by,
            int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver wd) {
                return !wd.findElement(by).isDisplayed();
            }
        });
    }

    /**
     * Click and wait for a page to load.
     * 
     * @param locator
     *            the locator of the element to click
     */
    protected void clickAndWait(String locator) {
        selenium.click(locator);
        waitForPageToLoad();
    }

    /**
     * Click and pause to allow for ajax to complete.
     * 
     * @param locator
     *            the locator of the element to click
     */
    protected void clickAndWaitAjax(String locator) {
        selenium.click(locator);
        // This pause is to allow for any js associated with an anchor to
        // complete execution
        // before moving on.
        pause(MILLISECONDS_PER_SECOND / 2);
    }

    protected void clickLinkAndWait(String linkText) {
        WebElement el = driver.findElement(By.linkText(linkText));
        el.click();
        pause(MILLISECONDS_PER_SECOND / 2);
    }

    protected void clickOnFirstVisible(By by) {
        for (WebElement e : driver.findElements(by)) {
            if (e.isDisplayed() && e.isEnabled()) {
                e.click();
                break;
            }
        }
    }

    public void pause(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Click the save button and wait for the resulting page to load.
     */
    protected void clickAndWaitSaveButton() {
        clickAndWait("save_button");
    }

    protected void verifyAlertTextAndAccept(String msg) {
        Alert javascriptAlert = driver.switchTo().alert();
        String text = javascriptAlert.getText(); // Get text on alert box
        assertTrue(text.contains(msg));
        javascriptAlert.accept();
        driver.switchTo().defaultContent();

    }

    /**
     * @return the serverPort
     */
    public int getServerPort() {
        return this.serverPort;
    }

    /**
     * @param serverPort
     *            the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the serverHostname
     */
    public String getServerHostname() {
        return this.serverHostname;
    }

    /**
     * @param serverHostname
     *            the serverHostname to set
     */
    public void setServerHostname(String serverHostname) {
        this.serverHostname = serverHostname;
    }

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass
     *            the driverClass to set
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
