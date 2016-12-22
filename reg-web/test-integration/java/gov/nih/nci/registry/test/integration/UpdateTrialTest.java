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

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.dumbster.smtp.SmtpMessage;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

/**
 * Searches, adds participating site and Updates trial in Registry.
 * 
 * @author gundalar
 */
@SuppressWarnings("deprecation")
@Batch(number = 2)
public class UpdateTrialTest extends AbstractRegistrySeleniumTest {
    
    @Test
    public void testUpdateTrial() throws SQLException, URISyntaxException {

        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(true);
        final String nciID = getLastNciId();
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        
        
        
        invokeUpdateTrial();
        verifyCalendarPopup();        
        
    }

    private void updateNciGrant(TrialInfo info, boolean nciGrant) throws SQLException {
        
        String protocolUpdateSql = "update study_protocol set nci_grant = '"+nciGrant+"' where official_title = '"+info.title+"'";
        
        QueryRunner runner = new QueryRunner();
        runner.update(connection, protocolUpdateSql);
        
    }
    
    @Test
    public void testReviewUpdateTrial() throws SQLException, URISyntaxException, InterruptedException {

        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        
        updateNciGrant(info, true);
        addSummaryFour(info.id, "abstractor-ci");
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        
        invokeUpdateTrial(); 
        waitForPageToLoad();
       
        //Trial Abstraction Error
        clickAndWait("xpath=//button[text()='Review Trial']");
        assertEquals("Trial Abstraction Error:", driver.findElement(By.cssSelector("#general_trial_errors > div.alert.alert-danger > strong")).getText());
        
        // Add grants and submit for review
        addGrantsWhileReviewAndValidate();
        
        // validate page for grants 
        validateGrant();
      
    }
    
    @Test
    public void testUpdateOfDocs() throws SQLException, URISyntaxException, InterruptedException {
    	if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        
        addNonCASummaryFour(info.id, "abstractor-ci");
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);        

        invokeUpdateTrial(); 
        waitForPageToLoad();
        
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);        
       
        //Trial Abstraction Error
        clickAndWait("xpath=//button[text()='Review Trial']");
        verifyUploadedDocs();
        
        clickAndWait("xpath=//button[text()='Submit']");
        pause(2000);
        verifyUploadedDocs();
	
    }
    
    @Test
    public void testUpdateTrialEmailNotification() throws SQLException, URISyntaxException, InterruptedException {

        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        
        addNonCASummaryFour(info.id, "abstractor-ci");
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        
        restartEmailServer();
        invokeUpdateTrial(); 
        waitForPageToLoad();
        
         //Trial Abstraction Error
        clickAndWait("xpath=//button[text()='Review Trial']");
        
        
        clickAndWait("xpath=//button[text()='Submit']");
        pause(2000);
        
        waitForEmailsToArrive(0);
        
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        
        invokeUpdateTrial(); 
        waitForPageToLoad();
        
        selenium.click("id=trialDTO_startDateTypeAnticipated");
        
        selenium.type("trialDTO_startDate", MONTH_DAY_YEAR_FMT.format(new Date()));
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        
        clickAndWait("xpath=//button[text()='Submit']");
        
        pause(2000);
        
        waitForEmailsToArrive(1);
        
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String body = email.getBody();
        assertTrue(body.contains("<p><b>Update Information:</b><br>Trial Start Date Type was updated.Trial Start Date was updated.</p>"));
    }
    
    
    @Test
    public void testIfProgramCodesNotDisplayedForOrgWithNoFamily() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        
        
        
        invokeUpdateTrial();
        waitForPageToLoad();
        assertFalse(selenium.isVisible("//div[@id='programCodeBlock']"));
        //assertFalse(selenium.isElementPresent("//div[@id='programCodeBlock']"));
    }
    @Test
    public void testIfProgramCodesDisplayedForOrgWithFamily() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        TrialInfo info = createAcceptedTrial(false,"National Cancer Institute");
        final String nciID = getLastNciId();
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
        
        
        
        invokeUpdateTrial(true);
        waitForPageToLoad();
        waitForElementById("programCodesValues", 60);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");

    }
   
    @Test
    public void testIfProgramsCodesRetainedAfterReviewAndEdit() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        TrialInfo info = createAcceptedTrial(false,"National Cancer Institute");
        addSummaryFour(info.id, "abstractor-ci");
        final String nciID = getLastNciId();
        
        loginToPAAndAddSite(info);

        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
        
        
        
        invokeUpdateTrial(true);
        waitForPageToLoad();
        waitForElementById("programCodesValues", 60);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForPageToLoad();
        
        clickAndWait("xpath=//button[text()='Edit ']");
        waitForPageToLoad();
        
        //check if program codes are retained
        moveElementIntoView(By.id("programCodesValues"));
        
        assertOptionSelected("PG1-Cancer Program1");
        assertOptionSelected("PG2-Cancer Program2");
        
    }
    
    @Test
    public void testSubmitUpdateProgramCodes() throws Exception {
        
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        
        associateProgramCodes();
        TrialInfo info = createAcceptedTrial(false,"National Cancer Institute");
        addSummaryFour(info.id, "abstractor-ci");
        final String nciID = getLastNciId();
        
        

        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
        
        
        
        invokeUpdateTrial(true);
        waitForPageToLoad();
        waitForElementById("programCodesValues", 60);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForPageToLoad();
        
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + nciID + " was successfully submitted."));
        
        //get trial id from nci Id
        long trialId =(Long)getTrialIdByNciId(nciID);
        
        
        //check if program codes are actually added in the database
        long programCodeCount = getProgramCodesCount(trialId);
        
        assert programCodeCount==2;
        
        //test if program code update message appeared
        logoutUser();
        logoutPA();
        loginAsSuperAbstractor();
        searchAndSelectTrial(info.title);
        
        
        clickAndWait("link=Trial History");
        openAndWait("/pa/protected/trialHistory.action?activeTab=updates&nciID="
                + info.nciID);
        
        assertTrue(selenium.isTextPresent("Program codes updated"));
        
        //somehow click on acknowledge does not work in selenium may be javascript alert asking user to confirm
        //do you want to acknowledge this update
        //clickAndWait("xpath=//table[@id='row']/tbody/tr[1]/td[3]/a");
        
        //delete this update so that for next use case it does not show up
        acknowledgeThisUpdate(trialId);
        
        //delete program code see if this produces update message
        logoutPA();
        
        loginAndAcceptDisclaimer();

        rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
        
        
        
        invokeUpdateTrial(true);
        waitForPageToLoad();
        waitForElementById("programCodesValues", 60);
        
        waitForElementById("programCodesValues", 60);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToUnselectOption("PG1-Cancer Program1");
        useSelect2ToUnselectOption("PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForPageToLoad();
        
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + nciID + " was successfully submitted."));
        
        //get trial id from nci Id
        trialId =(Long)getTrialIdByNciId(nciID);
        
        
        //check if program codes are actually deleted in the database
        programCodeCount = getProgramCodesCount(trialId);
        
        assert programCodeCount==0;
        
        //test if program code update message appeared
        logoutUser();
        logoutPA();
        loginAsSuperAbstractor();
        searchAndSelectTrial(info.title);
        
        
        clickAndWait("link=Trial History");
        openAndWait("/pa/protected/trialHistory.action?activeTab=updates&nciID="
                + info.nciID);
        
        assertTrue(selenium.isTextPresent("Program codes updated"));
        
         
   }
    
/**
 * Update trial and don't update program code check if no program code update message is displayed 
 */
@Test    
 public void testIfNoProgramCodeUpdateMessage() throws Exception {
    if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
        // PhantomJS keeps crashing on Linux CI box. No idea why at the
        // moment.
        return;
    }
    
    TrialInfo info = createAcceptedTrial(false,"National Cancer Institute");
    addSummaryFour(info.id, "abstractor-ci");
    final String nciID = getLastNciId();
    
    assignTrialOwner("abstractor-ci", info.id);

    loginAndAcceptDisclaimer();

    String rand = info.leadOrgID;
    runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
    
    invokeUpdateTrial(true);
    waitForPageToLoad();
    clickAndWait("xpath=//button[text()='Review Trial']");
    waitForPageToLoad();
    
    clickAndWait("xpath=//button[text()='Submit']");
    waitForPageToLoad();
    assertTrue(selenium
            .isTextPresent("The trial update with the NCI Identifier "
                    + nciID + " was successfully submitted."));
    
    
    logoutUser();
    logoutPA();
    loginAsSuperAbstractor();
    searchAndSelectTrial(info.title);
    
    
    clickAndWait("link=Trial History");
    openAndWait("/pa/protected/trialHistory.action?activeTab=updates&nciID="
            + info.nciID);
    
    assertFalse(selenium.isTextPresent("Program codes updated"));
    
 }

/**
 * Test if update does not overwrite program codes associated with trial
 * and belongs to other family
 * @throws Exception
 */
@Test
public void testIfProgramCodesForOtherFamilyNotChanged() throws Exception {
    
    if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
     }
       
        associateProgramCodes();
        TrialInfo info = createAcceptedTrial(false,"National Cancer Institute");
        addSummaryFour(info.id, "abstractor-ci");
        final String nciID = getLastNciId();
        
        

        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info,"National Cancer Institute");
        
        //associate program code from another family to trial
        associateProgramCodeToTrial(info.id);
        
        invokeUpdateTrial(true);
        waitForPageToLoad();
        waitForElementById("programCodesValues", 60);
        
        clickAndWaitAjax("id=programCodesValues");
        moveElementIntoView(By.id("programCodesValues"));
        useSelect2ToPickAnOption("programCodesValues","PG1","PG1-Cancer Program1");
        useSelect2ToPickAnOption("programCodesValues","PG2","PG2-Cancer Program2");
        
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForPageToLoad();
        
        waitForElementToBecomeAvailable(By.xpath("//button[text()='Submit']"),
                15);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The trial update with the NCI Identifier "
                        + nciID + " was successfully submitted."));
        
        //get trial id from nci Id
        long trialId =(Long)getTrialIdByNciId(nciID);
        
        
        //check if program codes are actually added in the database
        long programCodeCount = getProgramCodesCountForOtherFamily(trialId);
        
        assert programCodeCount==3;
}
    private void associateProgramCodes() throws Exception {
       
         QueryRunner qr = new QueryRunner();
         qr.update(connection, "delete from program_code");
         qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                 "values ((select identifier from family where po_id=1 ),'PG1', 'Cancer Program1', 'ACTIVE')");
         qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                 "values ((select identifier from family where po_id=1 ),'PG2', 'Cancer Program2', 'ACTIVE')");
         qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                 "values ((select identifier from family where po_id=1 ),'PG3', 'Cancer Program3', 'ACTIVE')");
         qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                 "values ((select identifier from family where po_id=1 ),'PG4', 'Cancer Program4', 'ACTIVE')");
         qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                 "values ((select identifier from family where po_id=2 ),'PG5', 'Cancer Program4', 'ACTIVE')");
     }
    private long getProgramCodesCount(long trialId) throws SQLException {
        
        QueryRunner runner = new QueryRunner();
        return (Long) runner
                .query(connection,
                        "select count(*) from study_program_code where study_protocol_id="+trialId
                        +" and program_code_id in (select identifier from program_code where program_code in ('PG1','PG2'))" ,
                        new ArrayHandler())[0];
     
    }
    
    private long getProgramCodesCountForOtherFamily(long trialId) throws SQLException {
        
        QueryRunner runner = new QueryRunner();
        return (Long) runner
                .query(connection,
                        "select count(*) from study_program_code where study_protocol_id="+trialId
                        +" and program_code_id in (select identifier from program_code where program_code in ('PG1','PG2','PG5'))" ,
                        new ArrayHandler())[0];
     
    }
    
    private void associateProgramCodeToTrial(long trialId) throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "insert into study_program_code values((select identifier from program_code where program_code='PG5'),"+trialId+")");
    }
    
    private void acknowledgeThisUpdate(long trialId) throws Exception {
        
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_inbox where study_protocol_identifier="+trialId);
        
    }
    

    private String getProgramCodePartcipatingSite(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String ssql = "select  program_code_text from study_site where functional_code ='TREATING_SITE' and  study_protocol_identifier="
                + info.id;
        
        return (String) runner
                .query(connection, ssql ,  new ArrayHandler())[0];
      }
    
    private void verifyUploadedDocs() {
    	assertTrue(selenium.isTextPresent("IRB Approval Document"));
        assertTrue(selenium.isTextPresent("IrbDoc.doc"));
        
        assertTrue(selenium.isTextPresent("Protocol Document"));
        assertTrue(selenium.isTextPresent("ProtocolDoc.doc"));
        
    }
    
    private void validateGrant() {
        
        List<WebElement> tr_collection = driver.findElements(By.xpath("id('row')/tbody/tr"));

        boolean recordFound = false;
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            assertEquals(4, td_collection.size());
            for (WebElement webElement : td_collection) {
                if(webElement.getText().equals("P30")) {
                    assertEquals("P30", td_collection.get(0).getText());
                    recordFound = true;
                }
            }
        }
        
        assert recordFound;
    }
    
    private void addGrantsWhileReviewAndValidate() {        
        selenium.select("id=fundingMechanismCode", "label=P30");
        selenium.select("id=nihInstitutionCode", "label=CA");
        selenium.type("id=serialNumber", "12197");
        selenium.select("id=nciDivisionProgramCode", "label=OD");
        moveElementIntoView(By.id("grantbtnid"));
        selenium.click("id=grantbtnid");
        assertEquals("P30", selenium.getText("css=#grantadddiv tr.odd > td"));
        clickAndWait("xpath=//button[text()='Review Trial']");
        assertEquals("Review Trial Details", selenium.getText("css=span"));
        
    }

    /**
     * @param info
     * @throws SQLException
     */
    private void loginToPAAndAddSite(TrialInfo info) throws SQLException {
        login("/pa", "ctrpsubstractor", "pass");
        disclaimer(true);
        searchAndSelectTrial(info.title);
        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "In Review" , false);
    }

    /**
     * @param fieldID
     * @param value
     * @param info
     */
    protected void runSearchAndVerifySingleTrialResult(String fieldID,
            String value, TrialInfo info,String...orgName) {

        accessTrialSearchScreen();
        selenium.type(fieldID, value);

        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);
        verifySingleTrialSearchResult(info,orgName);

    }

    /**
     * @param info
     */
    protected void verifySingleTrialSearchResult(TrialInfo info,String...orgName) {
        assertEquals(
                info.nciID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]/a"));
        assertEquals(info.title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]"));
        if(orgName!=null && orgName.length>0) {
            assertEquals(orgName[0],
                    selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        } else {
            assertEquals("ClinicalTrials.gov",
                    selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
                
        }
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

    private void invokeUpdateTrial(boolean...isLeadOrgDifferent) {
        final By selectActionBtn = By
                .xpath("//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        moveElementIntoView(selectActionBtn);
        driver.findElement(selectActionBtn).click();
        driver.findElement(By.xpath("//li/a[normalize-space(text())='Update']"))
                .click();
        if (isLeadOrgDifferent == null) {
            assertEquals("1",
                    selenium.getValue("trialDTO.leadOrganizationIdentifier"));
                
        }
        assertFalse(s
                .isElementPresent("xpath=//input[@type='radio' and @value='N/A']"));
        hideTopMenu();
    }

    private void verifyCalendarPopup() {

        Date today = new Date();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String month = monthFormat.format(today);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(today);

        // find 3 divs containing calendar
        List<WebElement> dateDivs = driver
                .findElements(By.id("datetimepicker"));
        assertEquals("Wrong number of date elements", 3, dateDivs.size());
        for (WebElement dateDiveElement : dateDivs) {
            List<WebElement> columns = dateDiveElement.findElements(By
                    .tagName("span"));
            // assertEquals("Wrong number of span elements", 2, columns.size());
            Boolean hasCalendarElement = false;

            for (WebElement cell : columns) {
                if (cell.getAttribute("class").equals("add-on btn-default")) {
                    hasCalendarElement = true;
                    moveElementIntoView(cell);
                    cell.click();
                    assertTrue(selenium.isTextPresent(month + " " + year));
                    break;
                }
            }

            assertTrue(hasCalendarElement);
        }
    }

}
