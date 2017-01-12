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

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.test.integration.support.Batch;
import gov.nih.nci.pa.util.PAConstants;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 * Selenium test for manage terms feature
 * 
 * @author Gopalakrishnan Unnikrishnan
 */
@Batch(number = 1)
public class ResultsDashboardTest extends OtherIdentifiersRelatedTest {
    
    List<TrialInfo> testTrials;
    
    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        logoutUser();
        deactivateAllTrials();
        registerTestTrials();        
        loginPA("results-abstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        clickAndWait("link=Search");
    }
    
    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
    
    @Test       
    public void testResultsReportingLandingPage(){        
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking Dashboard"));
    }
    
    @Test       
    public void testUnauthorizedAccess(){
        logoutUser();
        loginAsScientificAbstractor();
        openAndWait("/pa/protected/resultsDashboard.action");
        assertTrue(selenium.isTextPresent("Access Denied"));
    }
      
    @Test       
    public void testLandingPageForSuAbstractorRoles(){
        logoutUser();
        loginPA("ctrpsubstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        assertTrue(selenium.isTextPresent("My Dashboard"));
    }
    
    @Test
    public void testAmendedTrials() throws Exception {
    	TrialInfo acceptedTrial = createAcceptedTrial();
        new QueryRunner()
                .update(connection,
                        "update study_protocol set proprietary_trial_indicator=false, amendment_date=now(), submission_number=2"
                                + " where identifier=" + acceptedTrial.id);
        addSponsor(acceptedTrial, "National Cancer Institute");
        acceptedTrial.nctID="NCT00010";
        addNctIdentifier(acceptedTrial, acceptedTrial.nctID);
        
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("4 items found, displaying all items.1"));        
        assertTrue(selenium.isTextPresent("NCT00010"));       
        
    }
    
    @Test       
    public void testLandingPageForSuandResultAbstractorRoles() throws Exception{
        logoutUser();
        loginPA("multiroleuser", "pass");
        clickAndWait("id=acceptDisclaimer");
        assertTrue(selenium.isTextPresent("My Dashboard"));
        assertTrue(selenium.isTextPresent("Super Abstractor"));
        assertTrue(selenium.isTextPresent("Results Reporting"));
        
    }
    /**
     * Test the results reporting home page
     */
    @Test       
    public void testResultDashboardHomePage() throws Exception{
        assertTrue(selenium.isTextPresent("Section 801 Indicator:"));
        assertTrue(selenium.isTextPresent("Primary Completion Date:"));
        assertTrue(selenium.isTextPresent("From:"));
        assertTrue(selenium.isTextPresent("To:"));
        assertTrue(selenium.isTextPresent("Type:"));
        assertTrue(selenium.isTextPresent("Search"));
        assertTrue(selenium.isTextPresent("Reset"));
        assertTrue(selenium.isTextPresent("Search Results"));
        assertTrue(selenium.isTextPresent("3 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nctID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nctID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(5).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(5).nciID));
        assertTrue(selenium.isTextPresent("Doe - John"));
        assertTrue(selenium.isTextPresent("Results Reporting Progress"));
        assertTrue(selenium.isTextPresent("Add/Update Designee or PIO Contact"));
        assertTrue(selenium.isTextPresent("View/Upload Trial Comparison Documents"));
        assertTrue(selenium.isTextPresent("Results Cover Sheet"));
        assertTrue(selenium.isTextPresent("XML Upload Errors & Actions Taken"));
        assertTrue(selenium.isTextPresent("Enter NCI Trial ID:"));
        deleteContact();
    }
    
    
    @Test       
    public void testOnlyOneOfsection801CheckboxIsChecked(){
        selenium.click("id=section801IndicatorYes");
        assertFalse(driver.findElement(By.id("section801IndicatorNo")).isSelected());
        selenium.click("id=section801IndicatorNo");
        assertFalse(driver.findElement(By.id("section801IndicatorYes")).isSelected());
        selenium.click("id=section801IndicatorNo");
        assertFalse(driver.findElement(By.id("section801IndicatorNo")).isSelected());
        assertFalse(driver.findElement(By.id("section801IndicatorYes")).isSelected());
    }
    
    @Test       
    public void testResultsSearchNoFilter(){
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("Search Results"));
        assertTrue(selenium.isTextPresent("3 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nctID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nctID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(5).nctID));
        assertFalse(selenium.isTextPresent(testTrials.get(5).nciID));
    }
    
    @Test       
    public void testResultsSearchWithDateFilter(){
        selenium.type("id=pcdFrom", "01/01/2015");
        selenium.type("id=pcdTo", "12/31/2015");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("2 items found, displaying all items."));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }
    
    @Test    
    public void testResultsSearchWithTrialIdentifierFilter(){
        selenium.type("id=trialIdentifier", "NCT00001");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
        
        clickAndWait("link=Reset");
        assertTrue(selenium.isTextPresent(""));
    }
    
    @Test       
    public void testResultsSearchWithDateTypeFilter(){
        selenium.type("id=pcdFrom", "01/01/2015");
        selenium.type("id=pcdTo", "12/31/2015");
        final Select selectBox = new Select(driver.findElement(By.id("pcdType")));
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");        
        java.util.Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        
        selectBox.selectByValue("Anticipated");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        
        assertTrue(selenium.isTextPresent("ClinicalTrials.gov Import " + reportDate));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }    
    
    @Test       
    public void testResultsSearchBySection801Yes(){
        selenium.click("id=section801IndicatorYes");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("2 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }        
    
    @Test       
    public void testResultsSearchBySection801No(){
        selenium.click("id=section801IndicatorNo");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found."));
        assertFalse(selenium.isTextPresent(testTrials.get(0).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }
    
    @Test       
    public void testUpdateDate(){
        long trialId = testTrials.get(0).id;
        selenium.type("id=pcdSentToPIODate_"+trialId, "01/01/2015");
        selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "01/02/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=pcdSentToPIODate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"), 3);
        selenium.type("id=desgneeNotifiedDate_"+trialId, "01/03/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=pcdConfirmedDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("pcdConfirmedDate_"+trialId+"_flash"), 3);
        selenium.type("id=reportingInProcessDate_"+trialId, "01/04/2015");        
        pause(1000);
        assertTrue(selenium.isVisible("id=desgneeNotifiedDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("desgneeNotifiedDate_"+trialId+"_flash"), 3);
        selenium.type("id=threeMonthReminderDate_"+trialId, "01/05/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=reportingInProcessDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("reportingInProcessDate_"+trialId+"_flash"), 3);
        selenium.type("id=fiveMonthReminderDate_"+trialId, "01/06/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=threeMonthReminderDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("threeMonthReminderDate_"+trialId+"_flash"), 3);
        selenium.type("id=sevenMonthEscalationtoPIODate_"+trialId, "01/07/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=fiveMonthReminderDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("fiveMonthReminderDate_"+trialId+"_flash"), 3);
        selenium.type("id=resultsSentToPIODate_"+trialId, "01/08/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=sevenMonthEscalationtoPIODate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("sevenMonthEscalationtoPIODate_"+trialId+"_flash"), 3);
        selenium.type("id=resultsApprovedByPIODate_"+trialId, "01/09/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=resultsSentToPIODate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("resultsSentToPIODate_"+trialId+"_flash"), 3);
        selenium.type("id=prsReleaseDate_"+trialId, "01/10/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=resultsApprovedByPIODate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("resultsApprovedByPIODate_"+trialId+"_flash"), 3);
        selenium.type("id=qaCommentsReturnedDate_"+trialId, "01/11/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=prsReleaseDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("prsReleaseDate_"+trialId+"_flash"), 3);
        selenium.type("id=trialPublishedDate_"+trialId, "01/12/2015");
        pause(1000);
        assertTrue(selenium.isVisible("id=qaCommentsReturnedDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("qaCommentsReturnedDate_"+trialId+"_flash"), 3);
        driver.findElement(By.id("trialPublishedDate_"+trialId)).sendKeys("\t");
        pause(1000);
        assertTrue(selenium.isVisible("id=trialPublishedDate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("trialPublishedDate_"+trialId+"_flash"), 3);
        
        pause(1000);
        clickAndWait("link=Search");
        pause(1000);
        assertEquals("01/01/2015",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/02/2015",driver.findElement(By.id("pcdConfirmedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/03/2015",driver.findElement(By.id("desgneeNotifiedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/04/2015",driver.findElement(By.id("reportingInProcessDate_"+trialId)).getAttribute("value"));
        assertEquals("01/05/2015",driver.findElement(By.id("threeMonthReminderDate_"+trialId)).getAttribute("value"));
        assertEquals("01/06/2015",driver.findElement(By.id("fiveMonthReminderDate_"+trialId)).getAttribute("value"));
        assertEquals("01/07/2015",driver.findElement(By.id("sevenMonthEscalationtoPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/08/2015",driver.findElement(By.id("resultsSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/09/2015",driver.findElement(By.id("resultsApprovedByPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/10/2015",driver.findElement(By.id("prsReleaseDate_"+trialId)).getAttribute("value"));
        assertEquals("01/11/2015",driver.findElement(By.id("qaCommentsReturnedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/12/2015",driver.findElement(By.id("trialPublishedDate_"+trialId)).getAttribute("value"));
    }
    
    @Test       
    public void testUpdateDateToBlank(){
    	
        long trialId = testTrials.get(0).id;
        selenium.type("id=pcdSentToPIODate_"+trialId, "01/01/2015");
        selenium.type("id=pcdConfirmedDate_"+trialId, "01/02/2015");
        driver.findElement(By.id("pcdConfirmedDate_"+trialId)).sendKeys("\t");
        pause(1000);
        assertTrue(selenium.isVisible("id=pcdSentToPIODate_"+trialId+"_flash"));
        waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"), 3);
        clickAndWait("link=Search");
        assertEquals("01/01/2015",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/02/2015",driver.findElement(By.id("pcdConfirmedDate_"+trialId)).getAttribute("value"));
        
        // Re-setting dates to blank
        selenium.type("id=pcdSentToPIODate_"+trialId, "");
        selenium.type("id=pcdConfirmedDate_"+trialId, "");                
        driver.findElement(By.id("pcdConfirmedDate_"+trialId)).sendKeys("\t");
        pause(5000);
        clickAndWait("link=Search");
        assertEquals("",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("",driver.findElement(By.id("pcdConfirmedDate_"+trialId)).getAttribute("value"));        
    }
    
    @Test
    public void testPiechart(){
        assertTrue(selenium.isTextPresent("Results Reporting Progress"));
        assertTrue(selenium.isTextPresent("In Process"));
        assertTrue(selenium.isTextPresent("Completed"));
        assertTrue(selenium.isTextPresent("Not Started"));
        assertTrue(selenium.isTextPresent("Issues"));
        assertNotNull(driver.findElement(By.id("resultsChart")));
    }
    
    public void testTrialView(){
        clickAndWait("id=trialview_" + testTrials.get(0).id);
        waitForElementToBecomeAvailable(By.id("sendCoverSheetEmail"), 30);
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking - Trial View"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchDesigneeOrPIOContacts(){
        selenium.type("id=designeeTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("designeeTrialIdSearch");
        waitForElementToBecomeAvailable(By.id("reportStudyContactsForm"), 30);
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking - Add/Update Contact Information"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }

    public void testSearchTrialComparison(){
        selenium.type("id=trialCompDocsTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("trialCompDocsTrialSearch");
        waitForElementToBecomeAvailable(By.id("trialDocumentsForm"), 30);
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking - View/Upload Trial Comparison Documents"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchResultsCoverSheet(){
        selenium.type("id=coverSheetTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("coverSheetTrialSearch");
        waitForElementToBecomeAvailable(By.id("sendCoverSheetEmail"), 30);
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking & Cover Sheet"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchUploadErrors(){
        selenium.type("id=uploadErrorsTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("uploadErrorsTrialSearch");
        waitForElementToBecomeAvailable(By.id("editActionTakenDialog"), 30);
        assertTrue(selenium.isTextPresent("Summary Of XML Upload Errors & Actions Taken"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchUploadErrorsNoTrialId(){
        clickSearchForResultsReportingData("uploadErrorsTrialSearch");
        waitForElementToBecomeAvailable(By.id("editActionTakenDialog"), 30);
        assertTrue(selenium.isTextPresent("Summary Of XML Upload Errors & Actions Taken"));
        assertFalse(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    @Test       
    public void testVerifyResultReportingSearchResultsColumnDCPAndCTEPId() throws Exception { 
        logoutUser();
        TrialInfo trial;
        String ctep= "CTEPString";
        String dcp = "DCPString";
        
        // CTEP/DCP ID ColumValue <CTEPString, DCPString>
        deactivateAllTrials();
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        addNctIdentifier(trial, trial.nctID);
        goToGTDScreen(trial);
        verifyStudySiteAssignedIdentifier(trial, "CTEP Identifier", ctep);
        verifyStudySiteAssignedIdentifier(trial, "DCP Identifier", dcp);
        logoutUser();        
        loginPA("results-abstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        selenium.type("id=trialIdentifier", trial.nciID);
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        assertTrue(selenium.isTextPresent(trial.nciID));
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]"), trial.nciID);
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]"), ctep);
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]"), dcp);
        
        //  CTEP/DCP ID ColumValue - Empty
        logoutUser(); 
        deactivateAllTrials();
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        trial.nctID="NCT00001";
        addNctIdentifier(trial, trial.nctID);
        loginPA("results-abstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        selenium.type("id=trialIdentifier", trial.nciID);
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        assertTrue(selenium.isTextPresent(trial.nciID));
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]"), trial.nciID);
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]"), "");
        
        //  CTEP/DCP ID ColumValue - CTEPString
        logoutUser(); 
        deactivateAllTrials();
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        addNctIdentifier(trial, trial.nctID);
        goToGTDScreen(trial);
        verifyStudySiteAssignedIdentifier(trial, "CTEP Identifier", ctep);
        logoutUser();        
        loginPA("results-abstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        selenium.type("id=trialIdentifier", trial.nciID);
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        assertTrue(selenium.isTextPresent(trial.nciID));
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]"), trial.nciID);
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]"), ctep);

        //  CTEP/DCP ID ColumValue - DCPString
        logoutUser(); 
        deactivateAllTrials();
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        addNctIdentifier(trial, trial.nctID);
        goToGTDScreen(trial);
        verifyStudySiteAssignedIdentifier(trial, "DCP Identifier", dcp);
        logoutUser();        
        loginPA("results-abstractor", "pass");
        clickAndWait("id=acceptDisclaimer");
        selenium.type("id=trialIdentifier", trial.nciID);
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        assertTrue(selenium.isTextPresent(trial.nciID));
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]"), trial.nciID);
        assertEquals(selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]"), "");
    }    

    
    private void clickSearchForResultsReportingData(String searchBtnId){
        clickAndWaitAjax("//*[@id='"+searchBtnId+"']");
    }
    
     private void registerTestTrials()
            throws SQLException {
        testTrials = new ArrayList<AbstractPaSeleniumTest.TrialInfo>();
        TrialInfo trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        setPCD(trial, "2015-01-01", ActualAnticipatedTypeCode.ANTICIPATED);
        addDocument(trial, "COMPARISON", "Protocol.doc");
        setSeciont801Indicator(trial, true);
        trial.nctID="NCT00001";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial);
        
        trial = createSubmittedTrial();
        deleteContact();
        addDesginee(trial);
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_NORESPONSE");
        setPCD(trial, "2015-12-31", ActualAnticipatedTypeCode.ACTUAL);
        setSeciont801Indicator(trial, true);
        trial.nctID="NCT00002";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial);
        
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "VERIFICATION_PENDING");
        setPCD(trial, "2016-01-01", ActualAnticipatedTypeCode.ACTUAL);
        setSeciont801Indicator(trial, false);
        trial.nctID="NCT00003";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial);        
        
        
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        trial.nctID="NCT00004";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial);
        
        trial = createSubmittedTrial();
        addSponsor(trial, "Cancer Therapy Evaluation Program");
        addDWS(trial, "VERIFICATION_PENDING");
        trial.nctID="NCT00005";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial);
        
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        replaceLeadOrg(trial, PAConstants.CCR_ORG_NAME);
        addDWS(trial, "VERIFICATION_PENDING");
        setPCD(trial, "2016-01-01", ActualAnticipatedTypeCode.ACTUAL);
        setSeciont801Indicator(trial, false);
        trial.nctID="NCT00006";
        addNctIdentifier(trial, trial.nctID);
        testTrials.add(trial); 
    }
     
     //test if date field value is never changed then it should never be saved
     @Test       
     public void testDateNotSavedIfNoUpdate(){
         long trialId = testTrials.get(0).id;
         selenium.type("id=pcdSentToPIODate_"+trialId, "01/01/2015");
         selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "01/02/2015");
         pause(1000);
         waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"),3);
         //if above condition is true then date is saved just check if value is present to assert
         assertEquals("01/01/2015",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
         
         //now type same value and check if that date should not be saved
         selenium.type("id=pcdSentToPIODate_"+trialId, "01/01/2015");
         selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "01/02/2015");
         
         //below condition should never be true because update should never be saved
         //check if saved message is not displayed
         
         try {
            waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"),3);
            
            //if message is displayed i.e. above condition is true then mark this test as failure
            assert false;
         } catch (Exception e) {
             //save message is never displayed then test case result is success
             assert true;
         }
         
         //update value to blank this time it should be saved
         selenium.type("id=pcdSentToPIODate_"+trialId, "");
         selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "");
         pause(1000);
         waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"),3);
         //if above condition is true then date is saved just check if value is present to assert
         assertEquals("",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
         
         //now again try to update value to blank this should never be saved
         selenium.type("id=pcdSentToPIODate_"+trialId, "");
         selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "");
         
         //below condition should never be true because update should never be saved
         //check if saved message is not displayed
         
         try {
            waitForElementToBecomeInvisible(By.id("pcdSentToPIODate_"+trialId+"_flash"),3);
            
            //if message is displayed i.e. above condition is true then mark this test as failure
            assert false;
         } catch (Exception e) {
             //save message is never displayed then test case result is success
             assert true;
         }
         
         
     }            
     
     @Test
     public void testResutlReportingCSVExport() throws SQLException, IOException {
         deactivateAllTrials();
         TrialInfo trial = createSubmittedTrial();
         addSponsor(trial, "National Cancer Institute");
         addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
         setPCD(trial, "2015-01-01", ActualAnticipatedTypeCode.ANTICIPATED);
         addDocument(trial, "COMPARISON", "Protocol.doc");
         setSeciont801Indicator(trial, true);
         trial.nctID = "NCT00001";
         addNctIdentifier(trial, trial.nctID);

         clickAndWait("link=Search");

         assertTrue(selenium.isTextPresent("Results Reporting & Tracking Dashboard"));

         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");        
         java.util.Date today = Calendar.getInstance().getTime();
         String reportDate = df.format(today);
         
         // Finally, download CSV.
         if (!isPhantomJS()) {
             selenium.click("xpath=//div[@id='results']//a/span[normalize-space(text())='CSV']");
             pause(SystemUtils.IS_OS_LINUX ? 10000 : 2000);
             File csv = new File(downloadDir, "resultsReportingDashboard.csv");
             assertTrue(csv.exists());
             csv.deleteOnExit();

             List<String> lines = FileUtils.readLines(csv);
             assertEquals(
                     "NCI Trial Identifier,NCT ID,CTEP ID,DCP ID,Lead Org PO ID,Lead Organization,Results Designee,PCD,PCD Sent to PIO,PCD Confirmed,Designee Notified,Reporting in Process,3 Month Reminder,5 Month Reminder,7 Month Escalation,Results Sent to PIO,Results Approved by PIO,CTRO Trial Comparison Review,Trial Comparison Approval,CCCT Trial Comparison Review,PRS Release Date,QA Comments Returned Date,Trial Results Published Date",
                     lines.get(0));

             final String normalizedContent = lines.get(1).replaceAll("\\s+", " ");
             final String expected = trial.nciID
                     + ",NCT00001,,,1,ClinicalTrials.gov,,01/01/2015,,,,,,,,,,ClinicalTrials.gov Import " + reportDate+","
                     + "ClinicalTrials.gov Import "+reportDate+",11 "+reportDate+",,,";

             System.out.println(normalizedContent);
             System.out.println(expected);
             assertTrue(normalizedContent.matches(expected));

             csv.delete();
         }
     }
}
