package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.support.Batch;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Test;

/**
 * Tests for Design Details
 *
 * @author bnorris
 */
@Batch(number = 3)
public class DesignDetailsTest extends AbstractTrialStatusTest {

    public void setUp() throws Exception {
        super.setUp();
        setupFamilies();
    }

    @SuppressWarnings({ "javadoc", "deprecation" })
    @Test
    public void testAccrualsField() throws Exception {
        TrialInfo trial = createAcceptedTrial(false,
                "National Cancer Institute");

        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        clickLinkAndWait("Design Details");
        assertEquals("0", s.getText("id=isdesign.details.accrualNum").trim());

        s.select("webDTO.studyType", "label=Non-Interventional");
        waitForPageToLoad();
        assertEquals("0", s.getText("id=isdesign.details.accrualNum").trim());

    }

}