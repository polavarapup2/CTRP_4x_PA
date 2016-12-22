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

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Tests adding, editing and deleting trial documents.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Batch(number = 1)
public class TrialRelatedDocumentTest extends AbstractPaSeleniumTest {
    private static final String TRIAL_DOCUMENT = "TrialDocument.doc";

    
    @Test
    public void testAddDocument() throws URISyntaxException, SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        clickAndWait("link=Trial Related Documents");        
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");

        clickAndWait("link=Save");

        assertTrue(selenium.isTextPresent("Document Type Code must be Entered"));
        assertTrue(selenium.isTextPresent("FileName must be Entered"));

        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            System.out.println("trialDocPath: "+trialDocPath);
            selenium.select("id=typeCode", "label=Protocol Highlighted Document");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Created"));
            
            assertTrue(selenium.isElementPresent("//table[@id='row']/tbody/tr[1]/td[5]/a"));        
            assertTrue(selenium.isElementPresent("//table[@id='row']/tbody/tr[1]/td[6]//input"));
        }
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testHowDocumentsAppearInTrialHistoryPO_8189()
            throws URISyntaxException, SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        clickAndWait("link=Trial History");
        assertEquals("Protocol Document - Protocol.doc",
                selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]/a"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]")
                .contains("Original"));

        clickAndWait("link=Trial Related Documents");
        clickAndWait("link=Add");

        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(
                    TRIAL_DOCUMENT).toURI()).toString());
            selenium.select("id=typeCode", "label=Other");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Created"));

            clickAndWait("link=Trial History");
            assertEquals(
                    "Other - " + TRIAL_DOCUMENT,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]/a[2]"));
            assertEquals("Protocol Document - Protocol.doc Original Other - "
                    + TRIAL_DOCUMENT,
                    selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]")
                            .replaceAll("\\s+", " "));

            clickAndWait("link=Trial Related Documents");
            selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[6]//input");
            ((JavascriptExecutor) driver)
                    .executeScript("handleMultiDelete('', 'trialDocumentdelete.action');");
            waitForPageToLoad();
            selenium.type("inactiveComment", "inactive");
            clickAndWait("link=Done");
            assertTrue(selenium.isTextPresent("Record(s) Deleted"));

            clickAndWait("link=Trial History");
            assertEquals("Protocol Document - Protocol.doc Original Other - "
                    + TRIAL_DOCUMENT + " Deleted",
                    selenium.getText("xpath=//table[@id='row']/tbody/tr/td[5]")
                            .replaceAll("\\s+", " "));
        }
    }

    @Test
    public void testEditDocument() throws URISyntaxException, SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        clickAndWait("link=Trial Related Documents");
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
            System.out.println("trialDocPath: "+trialDocPath);
            addProtocolHighlighDocument();
    
            clickAndWait("xpath=//table[@id='row']/tbody/tr[1]/td[5]/a");        
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Save");
            assertTrue(selenium.isTextPresent("Record Updated"));
            assertTrue(selenium.isTextPresent("2 items found"));

            assertTrue(selenium.isElementPresent("link=Add"));
            clickAndWait("link=Add");
            selenium.select("id=typeCode", "label=Protocol Highlighted Document");
            selenium.type("id=fileUpload", trialDocPath);
            clickAndWait("link=Cancel");
            assertTrue(selenium.isTextPresent("2 items found"));
        }
    }

    @Test
    public void testDeleteDocument() throws SQLException, URISyntaxException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);

        clickAndWait("link=Trial Related Documents");
        
        if (!(isPhantomJS() && SystemUtils.IS_OS_LINUX)) {
            addProtocolHighlighDocument();    
            selenium.click("xpath=//table[@id='row']/tbody/tr[1]/td[6]//input");
            ((JavascriptExecutor) driver).executeScript("handleMultiDelete('', 'trialDocumentdelete.action');");
            waitForPageToLoad();
            selenium.type("inactiveComment", "inactive");
            clickAndWait("link=Done");       
            assertTrue(selenium.isTextPresent("One item found"));
        }
        
    }

    /**
     * @throws URISyntaxException
     */
    private void addProtocolHighlighDocument() throws URISyntaxException {
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");
        String trialDocPath = (new File(ClassLoader.getSystemResource(TRIAL_DOCUMENT).toURI()).toString());
        System.out.println("trialDocPath: "+trialDocPath);
        selenium.select("id=typeCode", "label=Protocol Highlighted Document");
        selenium.type("id=fileUpload", trialDocPath);
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));
        assertTrue(selenium.isTextPresent("2 items found"));
    }
}
