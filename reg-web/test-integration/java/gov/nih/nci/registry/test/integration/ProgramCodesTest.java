package gov.nih.nci.registry.test.integration;

import gov.nih.nci.pa.enums.StudyStatusCode;

import java.util.Date;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;



@Batch(number = 2)
public class ProgramCodesTest extends AbstractRegistrySeleniumTest {
	
	@Test
	public void testMultiFamilySwitch() throws Exception {
		deleteAllExistingFamilies();
		
		// Creating a new user
		String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        Number userID = createCSMUser(loginName);
        createRegistryUser(userID);
        assignUserToGroup(userID, "Submitter"); 
        assignUserToGroup(userID, "ProgramCodeAdministrator");
        login(loginName, "pass");
        handleDisclaimer(true);
        
        // Affliate the user to multi family org
        changeUserOrgToMultiFamily(); 
        pause(500);
        
        // Make user admin of registry
        changeUserToAdmin(userID.toString());
        
        // Re-login with admin privileges
        logoutUser();
        login(loginName, "pass");
        handleDisclaimer(true);  
        
        // access program codes screen        
        accessManageMasterListScreen();        
        
        // switching to next family. DTO changes.
        switchFamily("Family2");
        Select dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        assertEquals("Family2", dropdown.getFirstSelectedOption().getText());
        
        // Back to recently changed family
        switchFamily("Family1");   
        dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        assertEquals("Family1", dropdown.getFirstSelectedOption().getText());
       
	}
	
	/**
     * Test the program codes menu items
	 * @throws Exception 
     */
    @Test
    public void testProgramCodesMenu() throws Exception {    	
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);        
        hoverLink("Administration");   
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));        
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        assertTrue(selenium.isTextPresent("Manage Code Assignments"));
    }    
    
	/**
     * Test the program codes Organization Family List
	 * @throws Exception 
     */
    @Test
    public void testProgramCodesOrgFammilyList() throws Exception {    	
       loginAndGoToMasterListPage();
        Select dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        dropdown.selectByVisibleText("Family1");
        dropdown.selectByVisibleText("Family2");
    }  
    
    public void testManageProgramCodes() throws Exception {
    	createFamilies();
        createProgramCode("Cancer Program1",1L,"PG1");
        loginAndGoToMasterListPage();
        Select dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        
        dropdown.selectByVisibleText("Family1");
        assertEquals("PG1",
                selenium.getText("xpath=//table[@id='programCodesTable']/tbody/tr[1]/td[1]"));
        clickAndWait("xpath=//table[@id='programCodesTable']/tbody/tr[1]/td[1]/a");
        assertTrue(selenium.isTextPresent("Manage Program Code Assignments"));
    }


    @Test
    public void testProgramCodeDeletion() throws Exception {
        deleteAllExistingFamilies();
        removeFromAllStudiesProgramCodes();
        createFamilies();
        //go to master list
        loginAndGoToMasterListPage();
        //add two new families
        addProgramCode("A1", "Apple 1");
        addProgramCode("A2", "Apple 2");

        //Then there will be 2 program codes in database
        assertEquals(2, getCountOfProgramCode(1L));

        //When I click click on del button
        selenium.click("deletePGCodeButton-A1");

        //Then I see confirm delete dialog show up
        waitForElementToBecomeVisible(By.id("dialog-confirm-delete"), 5);

        //When I click the cancel button
        selenium.click("dialog-confirm-delete-btn-cancel");

        //Then dialog must close
        waitForElementToBecomeInvisible(By.id("dialog-confirm-delete"), 5);

        // And I still see A1 in the page
        assertTrue(selenium.isTextPresent("Apple 1"));

        //When I click on del button again
        selenium.click("deletePGCodeButton-A1");

        //Then I see confirm delete dialog show up
        waitForElementToBecomeVisible(By.id("dialog-confirm-delete"), 5);

        //Then I click the delete button
        selenium.click("dialog-confirm-delete-btn-delete");

        //Then dialog must close
        waitForElementToBecomeInvisible(By.id("dialog-confirm-delete"), 5);

        // And I will not see A1 in the page
        assertFalse(selenium.isTextPresent("Apple 1"));

        //and there will be 1 program code in database
        assertEquals(1, getCountOfProgramCode(1L));
    }



    @Test
    public void testProgramCodeDeletionWithActiveTrialAssociation() throws Exception {

        deactivateAllTrials();
        deleteAllExistingFamilies();
        removeFromAllStudiesProgramCodes();
        createFamilies();

        //go to master list
        loginAndGoToMasterListPage();
        //add two new families
        addProgramCode("A1", "Apple 1");
        addProgramCode("A2", "Apple 2");

        //Then there will be 2 program codes in database
        assertEquals(2, getCountOfProgramCode(1L));

        //now create a trial and associate program codes to it
        TrialInfo trial1 = createAcceptedTrial();
        addParticipatingSite(trial1, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial1, 1, "A1");

        TrialInfo trial2 = createAcceptedTrial();
        addParticipatingSite(trial2, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial2, 1, "A1");

        //When I click click on manage button
        selenium.click("managePGCodeButton-A1");
        waitForPageToLoad();

        //Then I should see the trials in the table
        assertTrue(selenium.isTextPresent(trial1.title));
        assertTrue(selenium.isTextPresent(trial2.title));

        //Then I come back to manage list screen
        accessManageMasterListScreen();
        waitForPageToLoad();

        //Then I do not see trials in page
        assertFalse(selenium.isTextPresent(trial1.title));
        assertFalse(selenium.isTextPresent(trial2.title));

        //And I click click on del button
        selenium.click("deletePGCodeButton-A1");

        waitForElementToBecomeVisible(By.id("dialog-inactivate-program-code"), 10);
        //and wait for table to load
        waitForElementToBecomeAvailable(By.xpath("//table[@id='trialsAssociatedToProgramCodes']/tbody/tr[2]"),10);

        //I see trials
        assertTrue(selenium.isTextPresent(trial1.title));
        assertTrue(selenium.isTextPresent(trial2.title));

        //when I click on yes button
        moveElementIntoView(By.id("dialog-inactivate-program-code-btn-yes"));
        selenium.click("dialog-inactivate-program-code-btn-yes");

        //Then dialog must close
        waitForElementToBecomeInvisible(By.id("dialog-inactivate-program-code"), 10);

        //And the table reloads
        waitForElementToGoAway(By.xpath("//table[@id='programCodesTable']/tbody/tr[2]"),10);

        // And I will not see A1 in the page
        assertFalse(selenium.isTextPresent("Apple 1"));

        //and there will be 1 program code in database
        assertEquals(1, getCountOfProgramCode(1L));

    }


    @Test
    public void testProgramCodeDeletionWithInActiveTrialAssociation() throws Exception {

        deactivateAllTrials();
        deleteAllExistingFamilies();
        removeFromAllStudiesProgramCodes();
        createFamilies();

        //go to master list
        loginAndGoToMasterListPage();
        //add two new families
        addProgramCode("A1", "Apple 1");
        addProgramCode("A2", "Apple 2");

        //Then there will be 2 program codes in database
        assertEquals(2, getCountOfProgramCode(1L));

        //now create a trial and associate program codes to it
        TrialInfo trial1 = createAcceptedTrial();
        addParticipatingSite(trial1, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial1, 1, "A1");

        TrialInfo trial2 = createAcceptedTrial();
        addParticipatingSite(trial2, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial2, 1, "A1");

        //When I click click on manage button
        selenium.click("managePGCodeButton-A1");
        waitForPageToLoad();

        //Then I should see the trials in the table
        assertTrue(selenium.isTextPresent(trial1.title));
        assertTrue(selenium.isTextPresent(trial2.title));

        //Then I come back to manage list screen
        accessManageMasterListScreen();
        waitForPageToLoad();

        //lets diativate all trials
        deactivateAllTrials();

        //And I click click on del button
        selenium.click("deletePGCodeButton-A1");


        //Then I see confirm delete dialog show up
        waitForElementToBecomeVisible(By.id("dialog-confirm-delete"), 5);

        //Then I click the delete button
        selenium.click("dialog-confirm-delete-btn-delete");

        //Then dialog must close
        waitForElementToBecomeInvisible(By.id("dialog-confirm-delete"), 5);

        // And I will not see A1 in the page
        assertFalse(selenium.isTextPresent("Apple 1"));

        //and there will be 1 program code in database
        assertEquals(1, getCountOfProgramCode(1L));

    }




    @Test
    public void testProgramCodeDeletionWithActiveAndInactiveTrialAssociation() throws Exception {

        deactivateAllTrials();
        deleteAllExistingFamilies();
        removeFromAllStudiesProgramCodes();
        createFamilies();

        //go to master list
        loginAndGoToMasterListPage();
        //add two new families
        addProgramCode("A1", "Apple 1");
        addProgramCode("A2", "Apple 2");

        //Then there will be 2 program codes in database
        assertEquals(2, getCountOfProgramCode(1L));

        //now create a trial and associate program codes to it
        TrialInfo trial1 = createAcceptedTrial();
        addParticipatingSite(trial1, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial1, 1, "A1");

        TrialInfo trial2 = createAcceptedTrial();
        addParticipatingSite(trial2, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial2, 1, "A1");

        //create trial 3 and deactivate it.
        TrialInfo trial3 = createAcceptedTrial();
        addParticipatingSite(trial3, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial3, 1, "A1");
        deactivateTrialByLeadOrgId(trial3.leadOrgID);

        //create trial 4 and move its status date back by an year
        TrialInfo trial4 = createAcceptedTrial();
        addParticipatingSite(trial4, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial4, 1, "A1");
        deleteEntireTrialStatusHistory(trial4);
        addSOS(trial4, StudyStatusCode.ADMINISTRATIVELY_COMPLETE.name());

         //And I click click on del button
        selenium.click("deletePGCodeButton-A1");

        waitForElementToBecomeVisible(By.id("dialog-inactivate-program-code"), 10);
        //and wait for table to load
        waitForElementToBecomeAvailable(By.xpath("//table[@id='trialsAssociatedToProgramCodes']/tbody/tr[2]"),10);

        //I see trials
        assertTrue(selenium.isTextPresent(trial1.title));
        assertTrue(selenium.isTextPresent(trial2.title));
        assertFalse(selenium.isTextPresent(trial3.title));
        assertFalse(selenium.isTextPresent(trial4.title));

        //when I click on yes button
        moveElementIntoView(By.id("dialog-inactivate-program-code-btn-yes"));
        selenium.click("dialog-inactivate-program-code-btn-yes");

        //Then dialog must close
        waitForElementToBecomeInvisible(By.id("dialog-inactivate-program-code"), 10);

        waitForElementToBecomeVisible(By.xpath("//table[@id='programCodesTable']/tbody/tr[1]/td[2]/span"), 10);
        // And I will not see A1 in the page
        assertTrue(selenium.isTextPresent("Apple 1"));
        assertTrue(selenium.isTextPresent("(INACTIVE)"));

        //and there will be 1 program code in database
        assertEquals(2, getCountOfProgramCode(1L));

    }


    @Test
    public void testProgramCodeClickFilterListingInManageAssignmentPage() throws Exception {

        deactivateAllTrials();
        deleteAllExistingFamilies();
        removeFromAllStudiesProgramCodes();
        createFamilies();

        //go to master list
        loginAndGoToMasterListPage();
        //add two new families
        addProgramCode("A1", "Apple 1");
        addProgramCode("A2", "Apple 2");

        //Then there will be 2 program codes in database
        assertEquals(2, getCountOfProgramCode(1L));

        //now create a trial and associate program code A1
        TrialInfo trial1 = createAcceptedTrial();
        addParticipatingSite(trial1, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial1, 1, "A1");

        //and create another trial and associate program code A2
        TrialInfo trial2 = createAcceptedTrial();
        addParticipatingSite(trial2, "National Cancer Institute Division of Cancer Prevention", "ACTIVE");
        assignProgramCode(trial2, 1, "A2");



        //When I click click on manage button of A1
        selenium.click("managePGCodeButton-A1");
        waitForPageToLoad();

        //Then I should see the trial-1 in the table  and not trial2
        assertTrue(selenium.isTextPresent(trial1.title));
        assertFalse(selenium.isTextPresent(trial2.title));

        //Then I come back to manage list screen
        accessManageMasterListScreen();
        waitForPageToLoad();

        //this time click on manage button of A2
        selenium.click("managePGCodeButton-A2");
        waitForPageToLoad();

        //Then I should see the trial-2 in the table  and not trial-1
        assertTrue(selenium.isTextPresent(trial2.title));
        assertFalse(selenium.isTextPresent(trial1.title));
    }


    private void loginAndGoToMasterListPage() throws Exception {
        unassignUserFromGroup("abstractor-ci", "ProgramCodeAdministrator");
        assignUserToGroup("abstractor-ci", "ProgramCodeAdministrator");
        loginAndAcceptDisclaimer();
        accessManageMasterListScreen();
    }

    private void addProgramCode(String code, String name) {
       selenium.type("newProgramCode", code);
       selenium.type("newProgramName", name);
       selenium.click("addProgramCodeButton");
       waitForElementToBecomeAvailable(By.xpath("//table[@id='programCodesTable']/tbody//tr/td/a[text()='" + code + "']"), 20);
       selenium.type("newProgramCode", "");
       selenium.type("newProgramName", "");
    }
    
    protected void createProgramCode(String pcName, Long familyPoId, String programCode)
            throws Exception {
        QueryRunner runner = new QueryRunner();
        String sql = "select identifier from program_code where program_code = '"+programCode+"'";
        Object[] pcID = runner.query(connection, sql,
                new ArrayHandler());
                       
        if (pcID == null) {
            sql = "insert into program_code (family_id, program_code,program_name,status_code) values (" +familyPoId+ ", '" +programCode+ "', '" +pcName+"','ACTIVE')";
            runner.update(connection, sql);
        }
    }
    private void createFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(connection, String.format("INSERT INTO family( identifier, po_id, " +
                "rep_period_end, rep_period_len_months)VALUES (1, 1, '%s', 12)", date(180)));
        qr.update(connection, String.format("INSERT INTO family( identifier, po_id, " +
                "rep_period_end, rep_period_len_months)VALUES (2, 2, '%s', 12)", date(-366)));
    }
    /**
     * Test the program codes menu items
     * @throws Exception 
     */
    @Test
    public void testProgramCodesInitialRecordCreation() throws Exception {
    	deleteAllExistingFamilies();
    	assertEquals(0, getCountOfFamily().intValue());
    	loginAndGoToMasterListPage();
        pause(500);
        assertEquals(2, getCountOfFamily().intValue());
    }    
    
    @Test
    public void testProgramCodesMenuNotVisibleForNonFamily() throws Exception {    	   	
        String loginName = RandomStringUtils.randomAlphabetic(12).toLowerCase();
        Number userID = createCSMUser(loginName);
        createRegistryUser(userID);
        assignUserToGroup(userID, "Submitter");
        changeUserToNoFamilyOrg(userID.toString());        
        login(loginName, "pass");
        handleDisclaimer(true); 
        waitForElementToBecomeVisible(By.linkText("Administration"), 5);
        hoverLink("Administration");        
        assertFalse(selenium.isTextPresent("Program Codes"));       
    }
    
    @Test
    public void testProgramCodesMenuNotVisibleForNonSiteAdmin() throws Exception {
    	loginAsSubmitter();
        handleDisclaimer(true);        
        pause(1000);                
        assertFalse(selenium.isTextPresent("Administration"));        
    }     
    
    private void accessManageMasterListScreen() {    	
        waitForElementToBecomeVisible(By.linkText("Administration"), 5);        
        hoverLink("Administration");   
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 5);        
        hoverLink("Program Codes");
        clickAndWait("link=Manage Master List");

    }
    
    private void changeUserToNoFamilyOrg(String csmUserId)
            throws Exception {
    	QueryRunner runner = new QueryRunner();
    	Integer clinGovIdentifier = (Integer) runner
                .query(connection,
                        "select identifier from organization where name = 'ClinicalTrials.gov'",
                        new ArrayHandler())[0];
    	
        String sql = "update registry_user set affiliated_org_user_type = 'ADMIN', affiliated_org_id = " 
        		+ clinGovIdentifier  +  " where csm_user_id = " + csmUserId;        
        runner.update(connection, sql);          
    } 
    
    private void changeUserToAdmin(String csmUserId)
            throws Exception {
    	QueryRunner runner = new QueryRunner();    	    	
        String sql = "update registry_user set affiliated_org_user_type = 'ADMIN' where csm_user_id = " + csmUserId;        
        runner.update(connection, sql);          
    } 
     
    private void deleteAllExistingFamilies()
            throws Exception {
    	QueryRunner runner = new QueryRunner();    	
        String sql = "delete from family";        
        runner.update(connection, sql);         
    }


    private void removeFromAllStudiesProgramCodes() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from study_program_code");
    }

    private Long getCountOfFamily()
            throws Exception {
    	QueryRunner runner = new QueryRunner();
    	Long families = (Long) runner
                .query(connection,
                        "select count(*) from family",
                        new ArrayHandler())[0];  	
                  
        return families;
    }


    private long getCountOfProgramCode(long familyPoId)
            throws Exception {
        QueryRunner runner = new QueryRunner();
        return (long) runner.query(connection,
                 "select count(*) from program_code pc join family f on pc.family_id = f.identifier where f.po_id ="
                + familyPoId,
                 new ArrayHandler())[0];

    }


    private Date getReportingPeriodDate(String poId)
            throws Exception {
    	QueryRunner runner = new QueryRunner();
    	Date currDate = (Date) runner
                .query(connection,
                        "select rep_period_end from family where po_id = " + poId,
                        new ArrayHandler())[0];  	
                  
        return currDate;
    }  
    
    private Integer getReportingPeriodLength(String poId)
            throws Exception {
    	QueryRunner runner = new QueryRunner();
    	Integer repLength = (Integer) runner
                .query(connection,
                        "select rep_period_len_months from family where po_id = " + poId ,
                        new ArrayHandler())[0];  	
                  
        return repLength;
    }  
    
    private void changeUserOrgToMultiFamily() {
    	clickAndWait("css=a.nav-user");
        clickAndWait("css=a.account");
        driver.switchTo().frame(0);
    	moveElementIntoView(By.id("registryUserWebDTO.affiliateOrgField"));    
        JavascriptExecutor js = (JavascriptExecutor) driver;                
        js.executeScript("showPopWin('orgPoplookuporgs.action', 850, 550, loadAffliatedOrgDiv, 'Select Affiliated Organization')");
        pause(500);
        
        // Searching for organization
        driver.switchTo().frame(0);
        selenium.type("id=orgNameSearch", "Multi Family Org");             
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
        driver.switchTo().frame(0);
        waitForElementToBecomeAvailable(By.xpath("//button[normalize-space(text())='Save']"), 20);
        WebElement save = driver.findElement(By.xpath("//button[normalize-space(text())='Save']"));
        JavascriptExecutor saveExecutor = (JavascriptExecutor)driver;
        
        
        saveExecutor.executeScript("arguments[0].click();", save);       
        
    }    
     
    private void switchReportingPeriodLength(String length) {
    	Select dropdown = new Select(driver.findElement(By.id("reportingPeriodLength")));
        dropdown.selectByVisibleText(length);
        waitForElementToBecomeVisible(By.id("reporting_flash"), 10);
        waitForElementToBecomeInvisible(By.id("reporting_flash"), 10); 
    }
    
    private void changeReportingDate() {
    	selenium.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='reportingPeriodEndDate']]");
        clickOnFirstVisible(By.xpath("//td[@class='day']"));
        clickOnFirstVisible(By
                .xpath("//div[@class='datepicker']/button[@class='close']"));
        waitForElementToBecomeVisible(By.id("date_flash"), 10);
        waitForElementToBecomeInvisible(By.id("date_flash"), 10);
    }
    
    private void switchFamily(String family) {
    	Select dropdown = new Select(driver.findElement(By.id("selectedDTOId")));
        dropdown.selectByVisibleText(family);         
    }
    
    private String getPoId() {
    	WebElement hiddenInput = driver.findElement(By.id("poID"));
    	String value = hiddenInput.getAttribute("value");
    	return value;
    }

}