package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
/**
 * Selenium test case for planned markers. 
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings("deprecation")
@Batch(number = 1)
public class PlannedMarkerTest extends AbstractPaSeleniumTest {

    private static final int CADSR_SEARCH_WAIT_TIME = 180;

    /**
     * Tests add/edit/deleting planned markers.
     *
     * @throws Exception on error
     */
    @Test
    public void testPlannedMarkers() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsScientificAbstractor();
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Markers");
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");
        verifyAttributes();
        clickAndWait("link=Cancel");
        assertTrue(selenium.isTextPresent("Nothing found to display."));
        clickAndWait("link=Add");
        //Test Validation and Creation
        clickAndWait("link=Save");
        verifyValidationAndCreation();
        //Verify values
        verifyCreatedValues();
        //Test editing
        verifyEditing(trial.id);
        clickAndWait("link=Select All");
        assertTrue(selenium.isElementPresent("link=Deselect All"));
        //Test deletion
        verifyDeletion();
        // save and retain markers
        //save and retain attributes
        //cadsr button click
        verifycaDSRView();
        // cadsr search
        verifyCaDSRSearch();
        // verify pending marker creation
        verifyCreatePendingMarker();
        

    }
    
    private void verifyAttributes() {
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-10"));
        assertFalse(selenium.isVisible("id=evaluationTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-10"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-11"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-12"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-13"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-14"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-15"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-16"));
        assertFalse(selenium.isVisible("id=assayTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=assayUse"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-10"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-11"));
        assertTrue(selenium.isElementPresent("id=specimenTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=status"));
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        assertTrue(selenium.isElementPresent("link=Save & Retain Marker Name"));
        assertTrue(selenium.isElementPresent("link=Save & Retain Attributes"));
    }
    
   
    private void verifyValidationAndCreation() {
       assertTrue(selenium.isTextPresent("Name is required"));
       assertTrue(selenium.isTextPresent("Evaluation Type is required."));
       assertTrue(selenium.isTextPresent("Assay Type is required."));
       assertTrue(selenium.isTextPresent("Biomarker Use is required."));
       assertTrue(selenium.isTextPresent("Biomarker Purpose is required."));
       assertTrue(selenium.isTextPresent("Specimen Type is required."));

       selenium.check("id=plannedMarker.evaluationType-10");
       waitForElementToBecomeAvailable(By.id("evaluationTypeOtherText"), 5);
          
       selenium.check("id=plannedMarker.assayType-16");
       waitForElementToBecomeAvailable(By.id("assayTypeOtherText"), 5);
       
       selenium.check("id=plannedMarker.tissueSpecimenType-11");
       waitForElementToBecomeAvailable(By.id("specimenTypeOtherText"), 5);
          
       clickAndWait("link=Save");
       assertTrue(selenium.isTextPresent("Name is required"));
       assertTrue(selenium.isTextPresent("Evaluation Type Other Text is required."));
       assertTrue(selenium.isTextPresent("Assay Type Other Text is required."));
       assertTrue(selenium.isTextPresent("Biomarker Use is required."));
       assertTrue(selenium.isTextPresent("Biomarker Purpose is required."));
       assertTrue(selenium.isTextPresent("Specimen Type Other Text is required."));

       selenium.type("id=name", "Marker #1");
       selenium.check("id=plannedMarker.evaluationType-1");
       selenium.type("id=evaluationTypeOtherText","EvaluationTypeOtherText");
       selenium.check("id=plannedMarker.assayType-1");
       selenium.type("id=assayTypeOtherText","AssayTypeOtherText");
       selenium.select("id=assayUse","label=Integral");
       selenium.check("id=plannedMarker.assayPurpose-1");
       selenium.check("id=plannedMarker.tissueSpecimenType-1");
       selenium.type("id=specimenTypeOtherText","SpecimenTypeOtherText");
       clickAndWait("link=Save");
       assertTrue(selenium.isTextPresent("Message. Record Created."));
       assertTrue(selenium.isTextPresent("One item found."));
    }
    
    private void verifyCreatedValues() {
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity, Other : EvaluationTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR, Other : AssayTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum, Other : SpecimenTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
    }
    
    private void verifyEditing(Long id) {
        clickAndWait("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a");
        assertTrue(selenium.isTextPresent("Edit Marker"));
        selenium.type("id=name", "Marker #1 Edit" + id);
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Updated."));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1 Edit" + id);
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity, Other : EvaluationTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR, Other : AssayTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum, Other : SpecimenTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
    }
    private void verifyDeletion() {
        selenium.check("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));
        
        ((JavascriptExecutor) driver).executeScript("handleMultiDelete('', 'plannedMarkerdelete.action');");
        waitForPageToLoad();
//        clickAndWait("link=Delete");
//        pause(1000);
        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));
    }
    private void verifycaDSRView() {
        clickAndWait("link=Add");
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        assertTrue(selenium.isElementPresent("id=searchBothTerms"));
        assertTrue(selenium.isElementPresent("id=caseTypetrue"));
        assertFalse(selenium.isChecked("id=caseTypetrue"));
        assertTrue(selenium.isElementPresent("id=highlightRequiredtrue"));
        assertTrue(selenium.isTextPresent("Marker Search in caDSR"));
        assertTrue(selenium.isElementPresent("link=Search"));
        assertTrue(selenium.isElementPresent("link=Reset"));
        assertTrue(selenium.isElementPresent("link=Create Biomarker Request"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        assertTrue(selenium.isElementPresent("id=searchName"));
        assertTrue(selenium.isElementPresent("id=searchPublicId"));
        assertTrue(selenium.isTextPresent("Nothing found to display."));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
    }
    private void verifyCaDSRSearch() {
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 30);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.select("id=searchBothTerms","label=Synonym");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[3]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.check("id=caseTypetrue");
        selenium.check("id=highlightRequiredtrue");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.contains(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        clickAndWait("link=Reset");
        assertEquals(selenium.getText("searchName"), "");
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.select("id=searchBothTerms","label=Primary Term");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        clickAndWait("link=Select");        
        driver.switchTo().defaultContent();
        waitForElementToBecomeAvailable(
                By.id("plannedMarker.evaluationType-1"), CADSR_SEARCH_WAIT_TIME);
        selenium.check("id=plannedMarker.evaluationType-1");
        selenium.check("id=plannedMarker.assayType-1");
        selenium.select("id=assayUse","label=Integral");
        selenium.check("id=plannedMarker.assayPurpose-1");
        selenium.check("id=plannedMarker.tissueSpecimenType-1");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Created."));
        assertTrue(selenium.isTextPresent("One item found."));
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"),"alpha"));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Active");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
        
    }
    
    private void verifyCreatePendingMarker() {
        clickAndWait("link=Add");
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        clickAndWait("link=Create Biomarker Request");
        assertTrue(selenium.isTextPresent("Create Permissible Value Request"));
        assertTrue(selenium.isElementPresent("id=toEmail"));
        assertTrue(selenium.isElementPresent("id=fromEmail"));
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=foundInHugo"));
        assertTrue(selenium.isElementPresent("id=message"));
        assertTrue(selenium.isElementPresent("link=Send Email"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        clickAndWait("link=Create Biomarker Request");
        assertTrue(selenium.isTextPresent("Create Permissible Value Request"));
        selenium.type("id=fromEmail", "reshma.kgoanti@semanticbits.com");
        selenium.type("id=name", "TestPendingMarker");
        selenium.type("id=message", "TestPendingMarker message");
        clickAndWait("link=Send Email");
        pause(3000);
        driver.switchTo().defaultContent();
        selenium.click("id=plannedMarker.evaluationType-1");
        selenium.click("id=plannedMarker.assayType-1");
        selenium.select("id=assayUse","label=Integral");
        selenium.click("id=plannedMarker.assayPurpose-1");
        selenium.click("id=plannedMarker.tissueSpecimenType-1");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Created."));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[1]"),"TestPendingMarker");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[2]"), "Level/Quantity");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[3]"), "PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[6]"), "Serum");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[9]//input"));
    }
}
