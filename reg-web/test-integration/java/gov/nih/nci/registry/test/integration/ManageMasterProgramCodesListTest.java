package gov.nih.nci.registry.test.integration;

import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 *Integration test for Manage Program Codes List screen.
 */
@Batch(number = 2)
public class ManageMasterProgramCodesListTest  extends AbstractRegistrySeleniumTest {
    
    private List<TrialInfo> trials = new ArrayList<TrialInfo>();

    /**
     * Test the program codes menu items
     * @throws Exception exception
     */
    @Test
    @SuppressWarnings({"deprecation" })
    public void testProgramCodesDataTableWithFilteringPagingAndSorting() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        //verify that program codes are sorted
        assertEquals("PG1",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[1]/td[1]")).getText());
        assertEquals("PG10",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[2]/td[1]")).getText());
        assertEquals("PG11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[3]/td[1]")).getText());
        assertEquals("PG12",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[4]/td[1]")).getText());
        assertEquals("PG6",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[9]/td[1]")).getText());
        // verify for INACTIVE program codes
        assertTrue(selenium.isTextPresent("(INACTIVE) Cancer Program2"));
        assertTrue(selenium.isTextPresent("(INACTIVE) Cancer Program6"));
        //verify pagination present
        assertEquals("Showing 1 to 10 of 12",driver.findElement(By.id("programCodesTable_info")).getText());
        // filter program codes.
        selenium.typeKeys("//div[@id='programCodesTable_filter']/descendant::label/descendant::input", "PG11");
        pause(2000);
        //should see filtered data only
        assertEquals("PG11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr/td[1]")).getText());
        assertEquals("Cancer Program11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr/td[2]")).getText());
        assertTrue(selenium.isElementPresent("xpath=//div[normalize-space(text())='Showing 1 to 1 of 1 (filtered from 12 total entries)']"));
        logoutUser();
    }
    
    /**
     * Test add new program code
     * @throws Exception exception
     */
    @Test
    @SuppressWarnings({"deprecation" })
    public void testCreateNewProgramCode() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        assertEquals("Showing 1 to 10 of 12",driver.findElement(By.id("programCodesTable_info")).getText());
        // verify that add programs button is present and enabled
        WebElement addProgramCodebutton = driver.findElement(By.id("addProgramCodeButton"));
        assertTrue(addProgramCodebutton.isEnabled());
        assertTrue(driver.findElement(By.id("newProgramCode")).isDisplayed());
        assertTrue(driver.findElement(By.id("newProgramName")).isDisplayed());
        
        // add new program code
        selenium.typeKeys("//input[@id='newProgramCode']", "newly-added-program-code");
        selenium.typeKeys("//input[@id='newProgramName']", "newly-added-program-name");
        addProgramCodebutton.click();
        waitForElementToBecomeInvisible(By.id("program_code_progress_indicator_panel"), 15);
        
        //test for newly added program code
        assertTrue(selenium.isTextPresent("newly-added-program-code"));
        assertTrue(selenium.isTextPresent("newly-added-program-name"));
        
        // verify confirmation message is shown
        ((JavascriptExecutor) driver).executeScript("scroll(0, -250);");
        assertTrue(selenium.isTextPresent("A new program code has been successfully added."));
        assertEquals("",driver.findElement(By.id("newProgramCode")).getText());
        assertEquals("",driver.findElement(By.id("newProgramName")).getText());
        
        logoutUser();
    }
    
    /**
     * Test the program codes error scenarios
     * @throws Exception exception
     */
    @Test
    @SuppressWarnings({"deprecation" })
    public void testProgramCodesCreateNewProgramCodeErrorScenarios() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        assertEquals("Showing 1 to 10 of 12", driver.findElement(By.id("programCodesTable_info")).getText());
        // test for empty program code submission
        WebElement addProgramCodebutton = driver.findElement(By.id("addProgramCodeButton"));
        assertTrue(addProgramCodebutton.isEnabled());
        assertTrue(driver.findElement(By.id("newProgramCode")).isDisplayed());
        assertTrue(driver.findElement(By.id("newProgramName")).isDisplayed());
        
        addProgramCodebutton.click();
        waitForElementToBecomeVisible(By.id("programCodeErrorMessageModal"), 2);
        assertEquals("Error",driver.findElement(By.className("modal-title")).getText());;
        assertTrue(selenium.isTextPresent("Program code is required"));
        WebElement cancelButton = driver.findElement(By.id("cancelButton"));
        cancelButton.click();
        assertTrue(selenium.isTextPresent("Program Code"));
        
        pause(3000);
        
        //test for duplicate program code error
         selenium.typeKeys("//input[@id='newProgramCode']", "PG1");
         addProgramCodebutton.click();
         waitForElementToBecomeVisible(By.id("programCodeErrorMessageModal"), 10);
         assertEquals("Error",driver.findElement(By.className("modal-title")).getText());
         assertTrue(selenium.isTextPresent("This program code already exists in the system."
                 + " Please add another program code"));
         cancelButton.click();
        
        logoutUser();
    }
    
    /**
     * Test updated program code
     * @throws Exception exception
     */
    @Test
    @SuppressWarnings({"deprecation" })
    public void testUpdateProgramCode() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        assertEquals("Showing 1 to 10 of 12",driver.findElement(By.id("programCodesTable_info")).getText());
        // verify that update programs button is present and enabled
        WebElement updateProgramCodebutton = driver.findElement(By.cssSelector("button[rel='tooltip']"));
        assertTrue(updateProgramCodebutton.isEnabled());
        hover(updateProgramCodebutton);
        String tooltiptext = updateProgramCodebutton.getAttribute("data-original-title");
        assertEquals("Edit this Program <br> Code and Name",tooltiptext);
        
        // open the edit dialog and verify the contents
        updateProgramCodebutton.click();
        waitForElementToBecomeAvailable(By.id("dialog-edit"), 15);
        assertTrue(selenium.isTextPresent("Edit Program Code"));
        assertTrue(selenium.isTextPresent("National Cancer Institute"));
        assertTrue(selenium.isTextPresent("Program Code:*"));
        assertTrue(selenium.isTextPresent("Program Name:"));
        assertEquals("PG1",driver.findElement(By.id("updatedProgramCode")).getAttribute("value"));
        assertEquals("Cancer Program1",driver.findElement(By.id("updatedProgramName")).getAttribute("value"));
        
        // test cancel the edit dialog
        WebElement cancelButton = driver.findElement(By.xpath("//button/span[contains(text(),'Cancel')]"));
        cancelButton.click();
        assertTrue(selenium.isTextPresent("Program Code"));
        
        // re-open the edit dialog
        updateProgramCodebutton.click();
        // update program code and name
        
        WebElement programCodeInputField = driver.findElement(By.id("updatedProgramCode"));
        programCodeInputField.clear();
        
        WebElement programNameInputField = driver.findElement(By.id("updatedProgramName"));
        programNameInputField.clear();
        
        selenium.typeKeys("//input[@id='updatedProgramCode']", "PG1-updated");
        selenium.typeKeys("//input[@id='updatedProgramName']", "Cancer Program1-updated");
        WebElement saveButton = driver.findElement(By.xpath("//button/span[contains(text(),'Save')]"));
        saveButton.click();
        
        //  verify confirmation dialog
        assertTrue(selenium.isTextPresent("Confirm Program Code Modification"));
        // test cancel the edit dialog
        WebElement noButton = driver.findElement(By.xpath("//button/span[contains(text(),'No')]"));
        noButton.click();
        // program code not updated when confirmation dialog is cancelled
        assertTrue(selenium.isTextPresent("PG1"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        
        //re-open the edit dialog
        updateProgramCodebutton.click();
        
        // update program code and name again
        programCodeInputField.clear();
        programNameInputField.clear();
        selenium.typeKeys("//input[@id='updatedProgramCode']", "PG1-updated");
        selenium.typeKeys("//input[@id='updatedProgramName']", "Cancer Program1-updated");
        saveButton.click();
        
        //  verify confirmation dialog
        assertTrue(selenium.isTextPresent("Confirm Program Code Modification"));
        // test confirmation the program code change by clicking on yes
        WebElement yesButton = driver.findElement(By.xpath("//button/span[contains(text(),'Yes')]"));
        yesButton.click();
        
        // both program code and name should have been updated
        pause(1000);
        ((JavascriptExecutor) driver).executeScript("scroll(0,100);");
        assertTrue(selenium.isTextPresent("PG1-updated"));
        assertTrue(selenium.isTextPresent("Cancer Program1-updated"));
        
        // verify confirmation message is shown
        ((JavascriptExecutor) driver).executeScript("scroll(0, -250);");
        assertTrue(selenium.isTextPresent("Program code has been successfully updated"));
        
        // re-open the edit dialog
        updateProgramCodebutton.click();
        // verify that latest values show up instead of the old values
        assertTrue(selenium.isTextPresent("PG1-updated"));
        assertTrue(selenium.isTextPresent("Cancer Program1-updated"));
        
        // Verify that latest values can be searched upon
        selenium.typeKeys("//div[@id='programCodesTable_filter']/descendant::label/descendant::input", "updated");
        pause(2000);
        //should see latest program code values
        assertEquals("PG1-updated",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr/td[1]")).getText());
        
        logoutUser();
    }
    
    
    /**
     * Test updated program code error conditions
     * @throws Exception exception
     */
    @Test
    @SuppressWarnings({"deprecation" })
    public void testUpdateProgramCodeErrorScenaiors() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        assertEquals("Showing 1 to 10 of 12",driver.findElement(By.id("programCodesTable_info")).getText());
        // verify that update programs button is present and enabled
        WebElement updateProgramCodebutton = driver.findElement(By.cssSelector("button[rel='tooltip']"));
        assertTrue(updateProgramCodebutton.isEnabled());
        hover(updateProgramCodebutton);
        String tooltiptext = updateProgramCodebutton.getAttribute("data-original-title");
        assertEquals("Edit this Program <br> Code and Name",tooltiptext);
        
     // open the edit dialog 
        updateProgramCodebutton.click();
        
        // clear program code and submit
        WebElement programCodeInputField = driver.findElement(By.id("updatedProgramCode"));
        programCodeInputField.clear();
        
       /* WebElement programNameInputField = driver.findElement(By.id("updatedProgramName"));
        programNameInputField.clear();*/
        
        WebElement saveButton = driver.findElement(By.xpath("//button/span[contains(text(),'Save')]"));
        saveButton.click();
        waitForElementToBecomeVisible(By.className("modal-title"), 20);
        
        assertTrue(selenium.isTextPresent("Error"));
        assertTrue(selenium.isTextPresent("Program code is required"));
        WebElement okButton = driver.findElement(By.id("cancelButton"));
        okButton.click();
        
     // re-open the edit dialog and update program code to an existing value
        moveElementIntoView(updateProgramCodebutton);
        updateProgramCodebutton.click();
        // clear program code and submit
        programCodeInputField.clear();
        selenium.typeKeys("//input[@id='updatedProgramCode']", "PG3");
        saveButton.click();
        
        //  verify confirmation dialog
        assertTrue(selenium.isTextPresent("Confirm Program Code Modification"));
        // test confirmation the program code change by clicking on yes
        WebElement yesButton = driver.findElement(By.xpath("//button/span[contains(text(),'Yes')]"));
        yesButton.click();
        
        pause(1000);
        
        assertEquals("Error",driver.findElement(By.className("modal-title")).getText());;
        assertTrue(selenium.isTextPresent(FamilyProgramCodeBeanLocal.DUPE_PROGRAM_CODE));
        okButton.click();
        
        logoutUser();
    }
    

    
    @Test
    public void testVerifyExport() throws Exception {
        loginAndHoverLink();
        recreateFamilies();
        associateProgramCodesToFamilies();
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program12"));
        assertTrue(selenium.isTextPresent("PG12"));
        assertEquals("Showing 1 to 10 of 12",driver.findElement(By.id("programCodesTable_info")).getText());
        
        // verify that csv and excel buttons
        verifyCSVExport();
        verifyExcelExport();
        logoutUser();
    }
    
    private void verifyCSVExport() throws  Exception {
        File csv = new File(downloadDir, "Manage Program Codes.csv");
        if (csv.exists()) csv.delete();
        assertFalse(csv.exists());
        clickLinkAndWait("CSV");
        pause(5000);
        assertTrue(csv.exists());
        List<String> lines = FileUtils.readLines(csv);
        assertTrue((lines.size() - 1) == 12);
        for (int i=1;i<13;i++) {
                assertTrue(lines.get(i).startsWith("\"PG"));
         }
        assertEquals("\"PG1\",\"Cancer Program1\"", lines.get(1));
        assertEquals("\"PG10\",\"(INACTIVE) Cancer Program10\"", lines.get(2));
        assertEquals("\"PG5\",\"Cancer Program5\"", lines.get(8));
        assertEquals("\"PG6\",\"(INACTIVE) Cancer Program6\"", lines.get(9));
        
        if (csv.exists()) csv.delete();
    }


    private void verifyExcelExport() throws Exception  {
        File excel = new File(downloadDir, "Manage Program Codes.xlsx");
        if (excel.exists()) excel.delete();
        assertFalse(excel.exists());
        clickLinkAndWait("Excel");
        pause(5000);
        excel.delete();
    }

    @Test
    @SuppressWarnings({ "deprecation" })
    public void testAssociationsToRejectedAndTerminatedTrialsIgnored()
            throws Exception {

        verifyAssociationsToTrialsWithGivenDWSIgnoredWhenDeleting("REJECTED");
        verifyAssociationsToTrialsWithGivenDWSIgnoredWhenDeleting("SUBMISSION_TERMINATED");
    }

    /**
     * @param status
     * @throws SQLException
     * @throws Exception
     */
    private void verifyAssociationsToTrialsWithGivenDWSIgnoredWhenDeleting(
            final String status) throws SQLException, Exception {
        deactivateAllTrials();
        super.setupFamilies();

        TrialInfo trial = createAcceptedTrial();
        addParticipatingSite(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "ACTIVE");
        addSiteInvestigator(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "451", "James", "H", "Kennedy",
                StudySiteContactRoleCode.SUB_INVESTIGATOR.name());
        addSiteInvestigator(trial,
                "National Cancer Institute Division of Cancer Prevention",
                "551", "Sony", "K", "Abraham",
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.name());
        assignProgramCode(trial, 1, "PG1");
        addDWS(trial, status);

        accessManageMasterListScreen();

        // verify that delete programs button is present and enabled
        WebElement deleteProgramCodebutton = driver.findElement(By
                .id("deletePGCodeButton-PG1"));
        assertTrue(deleteProgramCodebutton.isEnabled());
        deleteProgramCodebutton.click();
        waitForElementToBecomeAvailable(By.id("dialog-confirm-delete"), 15);
        assertTrue(selenium.isTextPresent("Confirm Delete"));
        assertTrue(selenium.isTextPresent("PG1 - Cancer Program1"));
        assertTrue(selenium.isTextPresent("Please confirm."));

        // delete program code
        WebElement deleteButton = driver.findElement(By.xpath("//button/span[contains(text(),'Delete')]"));
        deleteButton.click();
        pause(SystemUtils.IS_OS_LINUX ? 10000 : 2000);
        assertEquals(Arrays.asList(new String[] {}), getTrialProgramCodes(trial));
        logoutUser();
    }

    /**
     * @throws Exception
     */
    private void accessManageMasterListScreen() throws Exception {
        loginAndHoverLink();       
        clickAndWait("link=Manage Master List");
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
    }

    /**
     * 
     */
    private void loginAndHoverLink() {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
    }
    

    
    private Object[] queryProgramCodesForDeletedCode(String programCode) throws Exception {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Object[]> rsh = new ResultSetHandler<Object[]>() {
            @Override
            public Object[] handle(ResultSet resultSet) throws SQLException {
             // return if result set empty
             if( !resultSet.next()){
                  return null;
              }
             ResultSetMetaData metaData = resultSet.getMetaData();
             int cols = metaData.getColumnCount();
             Object[] result = new Object[cols];

             for (int i = 0; i < cols; i++) {
                 result[i] = resultSet.getObject(i + 1);
             }

             return result;
            }
        };
        Object[] resultsArray = qr.query(connection, "SELECT * FROM program_code WHERE program_code = ?", rsh, programCode);
        return resultsArray;
    }
    

    private void recreateFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(connection, String.format("INSERT INTO family( identifier, po_id, " +
                "rep_period_end, rep_period_len_months)VALUES (1, 1, '%s', 12)", date(180)));
    }
    
    private void recreateTrials() throws Exception {
        deactivateAllTrials();
        for (int i = 1; i <= 11; i++) {
           TrialInfo trial =  createAcceptedTrial();
           addParticipatingSite(trial, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
           addSiteInvestigator(trial,  "National Cancer Institute Division of Cancer Prevention", "45" + i , "James",
                   "H", "Kennedy",  StudySiteContactRoleCode.SUB_INVESTIGATOR.name());
            addSiteInvestigator(trial, "National Cancer Institute Division of Cancer Prevention", "55" + i, "Sony",
                    "K", "Abraham", StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.name());
           trials.add(trial);

        }
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_program_code");
        associateProgramCodesToFamilies();
        assignToAllStudiesProgramCode("PG12");
        assignToAllStudiesProgramCode("PG2");
     }
    
    private void assignToAllStudiesProgramCode(String code) throws SQLException {
        QueryRunner qr = new QueryRunner();
        for (TrialInfo trial : trials) {
            qr.update(connection, "insert into study_program_code " +
                    "values((select identifier from program_code where program_code='" + code + "')," +
                    trial.id + ")");
        }
    }

    private void associateProgramCodesToFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from program_code");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG1', 'Cancer Program1', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG2', 'Cancer Program2', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG3', 'Cancer Program3', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG4', 'Cancer Program4', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG5', 'Cancer Program5', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG6', 'Cancer Program6', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG7', 'Cancer Program7', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG8', 'Cancer Program8', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG9', 'Cancer Program9', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG10', 'Cancer Program10', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG11', 'Cancer Program11', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG12', 'Cancer Program12', 'ACTIVE')");

    }

}
