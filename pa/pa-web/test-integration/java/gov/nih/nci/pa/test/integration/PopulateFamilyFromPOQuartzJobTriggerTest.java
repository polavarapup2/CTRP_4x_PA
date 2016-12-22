package gov.nih.nci.pa.test.integration;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Will check if Quartz Job fired and family have data from PO
 */
public class PopulateFamilyFromPOQuartzJobTriggerTest extends AbstractPaSeleniumTest {

    @Test
    public void testLogin() throws Exception {
        loginAsAbstractor();

        //since we have MOCK PO I am just checking that family table is not empty
        assertTrue(getFamilyRecordCount() > 0);

        logoutUser();
    }


    private long getFamilyRecordCount() throws SQLException {

        long count =0;
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, "select count(*) from family",
                new ArrayListHandler());
        for (Object[] row : results) {
            count = (long) row[0];
        }
        return count;
    }
}
