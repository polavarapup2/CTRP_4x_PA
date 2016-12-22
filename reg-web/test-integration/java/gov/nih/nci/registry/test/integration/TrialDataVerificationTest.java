package gov.nih.nci.registry.test.integration;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import gov.nih.nci.pa.test.integration.support.Batch;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

/**
 * Tests Trial Data Verificaition
 * 
 * @author purams
 */
@SuppressWarnings("deprecation")
@Batch(number = 2)
public class TrialDataVerificationTest extends AbstractRegistrySeleniumTest {

    public void testTrailDataVerification_ManualVerification() throws SQLException, URISyntaxException {

        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);

        invokeVerifyData();

        waitForPageToLoad();

        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();

        Alert javascriptAlert = driver.switchTo().alert();
        javascriptAlert.accept();

        waitForPageToLoad();

        assertEquals("Manual Verification Entered",
                selenium.getText("xpath=//table[@id='webDTOList']/tbody/tr[1]/td[2]"));
        assertEquals(getRegistryUserFullNameByCSMLoginName("abstractor-ci"),
                selenium.getText("xpath=//table[@id='webDTOList']/tbody/tr[1]/td[3]"));

    }

    private void invokeVerifyData() {
        final By selectActionBtn = By
                .xpath("//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        moveElementIntoView(selectActionBtn);
        driver.findElement(selectActionBtn).click();
        driver.findElement(By.xpath("//li/a[normalize-space(text())='Verify Data']")).click();

    }

    private String getRegistryUserFullNameByCSMLoginName(final String loginName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        long csm_user_id = getCsmUserByLoginName(loginName);
        final List<Object[]> results = runner.query(connection,
                "select first_name, last_name from registry_user where csm_user_id =" + csm_user_id,
                new ArrayListHandler());
        Object[] row = results.get(0);
        return (String) row[0] + " " + (String) row[1];
    }
}
