package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

/**
 * 
 * 
 * @author Denis G. Krylov
 */
@Batch(number = 1)
public class OutOfScopeAccrualsTest extends AbstractPaSeleniumTest {

    @Test
    public void testNoRecords() throws SQLException {
        deleteAllOutOfScopeTrials();
        loginAsSuperAbstractor();
        clickAndWait("link=Accruals Out-Of-Scope Trials");
        assertTrue(selenium.isTextPresent("No records found."));
        assertTrue(selenium.isTextPresent("Accruals Out-Of-Scope Trials"));
    }

    @Test
    public void testDisplay() throws SQLException {
        deleteAllOutOfScopeTrials();
        createOutOfScopeTrial("ABC0001");
        loginAsSuperAbstractor();
        clickAndWait("link=Accruals Out-Of-Scope Trials");
        verifyDefaultTableContent("");
    }

    @Test
    public void testCancel() throws SQLException {
        deleteAllOutOfScopeTrials();
        createOutOfScopeTrial("ABC0001");
        loginAsSuperAbstractor();
        clickAndWait("link=Accruals Out-Of-Scope Trials");

        verifyDefaultTableContent("");
        selenium.select("xpath=//table[@id='row']/tbody/tr//td[5]/select",
                "Rejected");
        clickAndWait("link=Cancel");
        verifyDefaultTableContent("");
    }

    @Test
    public void testSave() throws SQLException {
        deleteAllOutOfScopeTrials();
        createOutOfScopeTrial("ABC0001");
        loginAsSuperAbstractor();
        clickAndWait("link=Accruals Out-Of-Scope Trials");

        verifyDefaultTableContent("");
        selenium.select("xpath=//table[@id='row']/tbody/tr//td[5]/select",
                "Rejected");
        clickAndWait("link=Save");

        assertTrue(selenium
                .isElementPresent("xpath=//div[@class='confirm_msg']"));
        assertTrue(selenium.isTextPresent("Your changes have been saved"));

        verifyDefaultTableContent("Rejected");
    }

    /**
     * 
     */
    private void verifyDefaultTableContent(String ctroActionExpected) {
        // verify table headers
        assertTrue(selenium.isTextPresent("One trial found."));
        assertTrue(selenium.getText("xpath=//table[@id='row']/thead/tr//th[1]")
                .contains("Trial CTEP ID"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/thead/tr//th[2]")
                .contains("Failure Reason"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/thead/tr//th[3]")
                .contains("Submission Date/Time"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/thead/tr//th[4]")
                .contains("User"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/thead/tr//th[5]")
                .contains("CTRO Action"));
        assertTrue(selenium.isTextPresent("CSV"));
        assertTrue(selenium.isTextPresent("Excel"));

        // verify table content
        assertTrue(selenium.getText("xpath=//table[@id='row']/tbody/tr//td[1]")
                .equals("ABC0001"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/tbody/tr//td[2]")
                .equals("Missing Trial"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/tbody/tr//td[3]")
                .equals("05/07/2014 04:58 PM"));
        assertTrue(selenium.getText("xpath=//table[@id='row']/tbody/tr//td[4]")
                .equals("ctrpsubstractor"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr//td[5]/select"));
        assertEquals(
                ctroActionExpected,
                selenium.getValue("xpath=//table[@id='row']/tbody/tr//td[5]/select"));

        // buttons
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
    }

    private void createOutOfScopeTrial(String ctepID) throws SQLException {
        String sql = "INSERT INTO accrual_out_of_scope_trial "
                + "(identifier,ctep_id,failure_reason,submission_date,user_id,ctro_action) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + ctepID
                + "' ,'Missing Trial',"
                + "{ts '2014-05-07 16:58:00.000'},"
                + "(select user_id from csm_user where login_name like '%ctrpsubstractor%' limit 1),'')";

        QueryRunner runner = new QueryRunner();
        runner.update(connection, sql);

    }

    private void deleteAllOutOfScopeTrials() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from accrual_out_of_scope_trial";
        runner.update(connection, sql);
    }

}
