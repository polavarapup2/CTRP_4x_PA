package gov.nih.nci.pa.service.util.report;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TSRReportParticipatingSiteTest {

    @Test
    public void compareToTest() throws Exception {
        TSRReportParticipatingSite ps1 = new TSRReportParticipatingSite();
        TSRReportParticipatingSite ps2 = new TSRReportParticipatingSite();

        assertEquals(0, ps1.compareTo(ps1));
        assertEquals(0, ps1.compareTo(ps2));

        ps2.setFacility("bbb");
        assertEquals(-1, ps1.compareTo(ps2));
        ps1.setFacility("bbb");
        assertEquals(0, ps1.compareTo(ps2));
        ps2.setFacility(null);
        assertEquals(1, ps1.compareTo(ps2));
        ps2.setFacility("aaa");
        assertEquals(1, ps1.compareTo(ps2));
        ps2.setFacility("ccc");
        assertEquals(-1, ps1.compareTo(ps2));
        ps2.setFacility("AAA");
        assertEquals(1, ps1.compareTo(ps2));
        ps2.setFacility("CCC");
        assertEquals(-1, ps1.compareTo(ps2));
    }

}
