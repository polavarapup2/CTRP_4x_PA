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

import java.sql.SQLException;
import java.util.Iterator;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author dkrylov
 */
@SuppressWarnings("deprecation")
@Batch(number = 2)
public class SignUpTest extends AbstractRegistrySeleniumTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testSignUpWithExistingAccount() throws InterruptedException,
            SQLException {
        String uuid = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        final String emailID = uuid + "@example.com";
        openAndWait("/registry");
        clickAndWait("link=Sign Up");
        assertTrue(s
                .isTextPresent("If you already have an NIH or NCI account, click here to proceed"));
        clickAndWait("link=here");
        s.type("username", uuid);
        s.type("password", "nopass");
        clickAndWait("//button[text()='Next']");
        assertTrue(s
                .isTextPresent("Invalid username and/or password, please try again."));

        int userID = createCSMUser(uuid).intValue();
        s.type("username", uuid);
        s.type("password", "pass");
        clickAndWait("//button[text()='Next']");

        s.type("registryUserWebDTO.emailAddress", emailID);
        populateAccountInfo();
        clickAndWait("//button[text()='Sign Up']");
        assertTrue(s
                .isTextPresent("Your User Account has been successfully created. Please log in using your username and password."));
        // Must be able to log in!
        verifyNewUserCanLogIn(uuid, emailID);

        RegistryUser ru = getRegistryUserByEmail(emailID);
        assertNull(ru.token);
        assertNotNull(ru.csmUserID);
        assertEquals(userID, ru.csmUserID.intValue());
        assertTrue(isUserInGroup(ru.csmUserID, "Submitter"));

    }

    /**
     * @param uuid
     * @param emailID
     */
    private void verifyNewUserCanLogIn(String uuid, final String emailID) {
        login(uuid, "pass");
        handleDisclaimer(true);
        clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        s.selectFrame("popupFrame");
        assertEquals(emailID, s.getValue("registryUserWebDTO.emailAddress"));
        assertEquals("John", s.getValue("registryUserWebDTO.firstName"));
        assertEquals("Doe", s.getValue("registryUserWebDTO.lastName"));
        assertEquals("Cancer Therapy Evaluation Program",
                s.getText("registryUserWebDTO.affiliateOrgField"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSignUpWithEmailAddress() throws InterruptedException,
            SQLException {
        String uuid = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        openAndWait("/registry");
        clickAndWait("link=Sign Up");
        final String emailID = uuid + "@example.com";

        s.type("registerUser_registryUserWebDTO_emailAddress", emailID);
        s.click("//button[text()='Next']");

        populateAccountInfo();
        clickAndWait("//button[text()='Sign Up']");
        assertTrue(s
                .isTextPresent("Thank you for requesting an account for the CTRP system. You will receive an email once the request is processed. Processing the request could take up to 48 hours."));

        waitForEmailsToArrive(2);
        Iterator emailIter = server.getReceivedEmail();
        RegistryUser ru = getRegistryUserByEmail(emailID);
        assertNull(ru.csmUserID);
        assertNotNull(ru.token);

        // First email must go to App Support.
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String to = email.getHeaderValues("To")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll("^.*?7bit ", "").replaceAll("------.*?$", "");
        assertEquals("ncicbmb@example.com", to);
        assertEquals("CTRP: New Account Request", subject);
        assertEquals(
                "Dear Sir or Madam, We have received a request for a new user account in the Clinical Trials Reporting Program (CTRP) Clinical Trials Registration application. To create the account, please follow the steps below: 1. Create an account in the NCI External LDAP. "
                        + "The user information is as follows: * "
                        + "First Name: John * "
                        + "Last Name: Doe * "
                        + "Affiliated Organization: Cancer Therapy Evaluation Program * "
                        + "Phone Number: 555-555-5555 * "
                        + "Email: "
                        + emailID
                        + " "
                        + "2. Navigate to the following URL and follow the directions on the screen: "
                        + "http://localhost:"
                        + getServerPort()
                        + "/registry/registerUseractivate.action?token="
                        + ru.token
                        + " "
                        + "3. Inform the user that his/her account is ready. If you have questions, please contact us at ncictro@mail.nih.gov. Thank you,NCI Clinical Trials Reporting Office",
                body);

        // Second email must go to user.
        email = (SmtpMessage) emailIter.next();
        subject = email.getHeaderValues("Subject")[0];
        to = email.getHeaderValues("To")[0];
        body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll("^.*?7bit ", "").replaceAll("------.*?$", "");
        assertEquals(emailID, to);
        assertEquals("New NCI CTRP Account Request", subject);
        assertEquals(
                "<p>Dear John Doe,</p><p>Thank you for requesting an account for the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP) system.</p><p>You will receive an email notification with instructions for activating your account.<br/>Please allow two (2) business days to process your request.</p><p>If you have questions, please contact us at ncictro@mail.nih.gov.</p><p>Thank you,</p><p>NCI Clinical Trials Reporting Office<br/>http://www.cancer.gov/ncictrp<br/>ncictro@mail.nih.gov</p>",
                body);

        // Now activate account.
        restartEmailServer();
        pause(1000);
        openAndWait("/registry/registerUseractivate.action?token=" + ru.token);
        assertTrue(s
                .isTextPresent("Please enter the LDAP ID of the newly created user account and click Activate"));
        s.type("ldapID", uuid);
        clickAndWait("//button[text()='Activate']");
        assertTrue(s
                .isTextPresent("The account has been successfully activated. Please inform the user."));
        waitForEmailsToArrive(1);

        // 3rd email must go to user.
        emailIter = server.getReceivedEmail();
        email = (SmtpMessage) emailIter.next();
        subject = email.getHeaderValues("Subject")[0];
        to = email.getHeaderValues("To")[0];
        body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll("^.*?7bit ", "").replaceAll("------.*?$", "");
        assertEquals(emailID, to);
        assertEquals("Your NCI CTRP Account is now active", subject);
        assertEquals(
                "Dear John Doe, Your NCI CTRP Account has been activated. You should be able to log in with your LDAP User ID: "
                        + uuid
                        + ". If you have questions, please contact us at ncictro@mail.nih.gov. Thank you,NCI Clinical Trials Reporting Office",
                body);

        ru = getRegistryUserByEmail(emailID);
        assertNull(ru.token);
        assertNotNull(ru.csmUserID);
        assertEquals(getNextIdForCsmUser().intValue() - 1,
                ru.csmUserID.intValue());
        assertTrue(isUserInGroup(ru.csmUserID, "Submitter"));

        verifyNewUserCanLogIn(uuid, emailID);

    }

    /**
     * 
     */

    private void populateAccountInfo() {
        s.type("registryUserWebDTO.firstName", "John");
        s.type("registryUserWebDTO.lastName", "Doe");
        s.type("registryUserWebDTO.addressLine", "13921 Park Center Rd");
        s.type("registryUserWebDTO.city", "Herndon");
        s.select("registryUserWebDTO.state", "Alaska");
        s.type("registryUserWebDTO.postalCode", "22222");
        s.type("registryUserWebDTO.phone", "555-555-5555");

        moveElementIntoView(By.id("registryUserWebDTO.affiliateOrgField"));
        hover(By.id("registryUserWebDTO.affiliateOrgField"));
        s.click("registryUserWebDTO.affiliateOrgField");
        waitForElementToBecomeVisible(By.linkText("Search..."), 5);
        s.click("link=Search...");

        // Searching for organization
        s.selectFrame("popupFrame");
        selenium.type("id=orgNameSearch", "Cancer Therapy Evaluation Program");
        WebElement runSearchBtn = driver.findElement(By
                .id("search_organization_btn"));
        runSearchBtn.click();
        waitForElementToBecomeAvailable(
                By.xpath("//table[@id='row']/tbody/tr/td[8]/button"), 15);
        s.click("//table[@id='row']/tbody/tr/td[8]/button");
        driver.switchTo().defaultContent();

        s.click("registryUserWebDTO.enableEmailstrue");
    }
}
