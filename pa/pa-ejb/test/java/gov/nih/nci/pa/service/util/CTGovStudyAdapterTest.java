/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.*;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import gov.nih.nci.pa.service.ctgov.ClinicalStudy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class CTGovStudyAdapterTest {

    private ClinicalStudy study;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        String packageName = ClinicalStudy.class.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Unmarshaller u = jc.createUnmarshaller();
        study = (ClinicalStudy) u.unmarshal((this.getClass()
                .getResourceAsStream("/NCT01861054.xml")));
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void test() {
        CTGovStudyAdapter adapter = new CTGovStudyAdapter(study);
        assertEquals("Breast Cancer", adapter.getConditions());
        assertEquals("Drug: Reparixin", adapter.getInterventions());
        assertEquals("NCT01861054", adapter.getNctId());
        assertEquals("Recruiting", adapter.getStatus());
        assertEquals("Industry", adapter.getStudyCategory());
        assertEquals(
                "A Single Arm, Preoperative, Pilot Study to Evaluate the Safety and Biological Effects of Orally"
                        + " Administered Reparixin in Early Breast Cancer Patients Who Are Candidates for Surgery",
                adapter.getTitle());
    }

}
