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
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author Michael Visee
 */
@Batch(number = 2)
public class MyAccountTest extends AbstractRegistrySeleniumTest {
	
	private static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 10000
            : 2000;

    /**
     * Test the MyAccount scenario in the successful case.
     */
    @Test
    public void testMyAccountSuccess() {
        loginAndAcceptDisclaimer();
        clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        driver.switchTo().frame(0);
        clickAndWait("css=button.btn-primary");
        assertTrue(selenium.isTextPresent("Your account was successfully updated"));
    }
    
    /**
     * Test the MyAccount scenario in the failure case
     */
    @Test
    public void testMyAccountFailure() {
    	loginAndAcceptDisclaimer();
        clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        driver.switchTo().frame(0);
        selenium.type("id=registryUserWebDTO.emailAddress", "");
        selenium.type("id=registryUserWebDTO.firstName", "");
        selenium.type("id=registryUserWebDTO.lastName", "");
        selenium.type("id=registryUserWebDTO.addressLine", "");
        selenium.type("id=registryUserWebDTO.city", "");
        selenium.type("id=registryUserWebDTO.postalCode", "");
        selenium.type("id=registryUserWebDTO.phone", "");         
        clickAndWait("css=button.btn-primary");
        assertTrue(selenium.isTextPresent("Email Address is required"));
        assertTrue(selenium.isTextPresent("First Name is required"));
        assertTrue(selenium.isTextPresent("Last Name is required"));
        assertTrue(selenium.isTextPresent("Street Address is required"));
        assertTrue(selenium.isTextPresent("City is required"));
        assertTrue(selenium.isTextPresent("Zip Code is required"));
        assertTrue(selenium.isTextPresent("Phone number is required"));
    }
    
    /**
     * Testing logout when there is a change in org affiliation
     * @throws SQLException error if any
     */
    @Test
    public void testOrgChangeLogout() throws SQLException {
        addReportGroupToRegUser("abstractor-ci");
    	loginAndAcceptDisclaimer();    	
        clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
    	moveElementIntoView(By.id("registryUserWebDTO.affiliateOrgField"));    
        JavascriptExecutor js = (JavascriptExecutor) driver;                
        js.executeScript("showPopWin('orgPoplookuporgs.action', 850, 550, loadAffliatedOrgDiv, 'Select Affiliated Organization')");
        pause(OP_WAIT_TIME);
        
        // Searching for organization
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        selenium.type("id=orgNameSearch", "Cancer Therapy Evaluation Program");             
        WebElement element = driver.findElement(By.id("search_organization_btn"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        waitForElementById("row", 20);       
        
        // Changing the organization
        WebElement selectButton = driver.findElement(By.xpath("//table[@id='row']/tbody/tr/td[8]/button"));
        JavascriptExecutor selectExecutor = (JavascriptExecutor)driver;
        selectExecutor.executeScript("arguments[0].click();", selectButton);          
        
        // Saving / updating the account
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementToBecomeAvailable(By.xpath("//button[normalize-space(text())='Save']"), 20);
        WebElement save = driver.findElement(By.xpath("//button[normalize-space(text())='Save']"));
        JavascriptExecutor saveExecutor = (JavascriptExecutor)driver;
        
        assertNotNull(queryReportGroupToRegUser("abstractor-ci"));
        saveExecutor.executeScript("arguments[0].click();", save);
        
        // Checking for logout to occur
        pause(OP_WAIT_TIME);
        driver.switchTo().defaultContent();
        verifyLoginPage();
        assertNull(queryReportGroupToRegUser("abstractor-ci"));
    }
    
    protected void addReportGroupToRegUser(String csmUserName)
            throws SQLException {
        long userId = getCsmUserByLoginName(csmUserName);
        String sql = "update registry_user set report_groups='DataTable4'"
                + " where csm_user_id=" + userId;
        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

        LOG.info("Added report group to the registry user");
    }
    
    protected String queryReportGroupToRegUser(String csmUserName)
            throws SQLException {
        long userId = getCsmUserByLoginName(csmUserName);
        String sql = "select report_groups from registry_user "
                + " where csm_user_id=" + userId;
        QueryRunner runner = new QueryRunner();
        String repGrp = null;
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
            repGrp = (String) row[0];
           
        }

        return repGrp;
    }
}
