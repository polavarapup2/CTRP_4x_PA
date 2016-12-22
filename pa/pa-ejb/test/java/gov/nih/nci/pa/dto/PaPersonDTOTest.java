/**
 *
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderServiceBean;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PaPersonDTOTest extends AbstractHibernateTestCase {
    private PAHealthCareProviderLocal remoteEjb = new PAHealthCareProviderServiceBean();

    @Before
    public void init() throws Exception {
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        PaHibernateUtil.getCurrentSession().save(sp);

        Organization o = TestSchema.createOrganizationObj();
        PaHibernateUtil.getCurrentSession().save(o);

        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        PaHibernateUtil.getCurrentSession().save(p);

        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(o);
        PaHibernateUtil.getCurrentSession().save(hcf);

        StudySite spc = TestSchema.createStudySiteObj(sp, hcf);
        PaHibernateUtil.getCurrentSession().save(spc);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(o, p);
        PaHibernateUtil.getCurrentSession().save(crs);

        StudySiteContact spcc = new StudySiteContact();
        spcc.setClinicalResearchStaff(crs);
        spcc.setRoleCode(StudySiteContactRoleCode.SUBMITTER);
        spcc.setStudySite(spc);
        spcc.setStudyProtocol(sp);
        spcc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        PaHibernateUtil.getCurrentSession().save(spcc);
    }

    @Test
    public void testGetSet() throws Exception {
        List<PaPersonDTO> data = remoteEjb.getPersonsByStudySiteId(Long.valueOf(1), "SUBMITTER");
        data.get(0).setFirstName("TestFN");
        data.get(0).setLastName("TestLN");
        assertEquals("Testing first name failed", "TestFN", data.get(0).getFirstName());
        assertEquals("Testing last name failed", "TestLN", data.get(0).getLastName());
        assertNotNull("Testing Full name failed", data.get(0).getFullName());
        data.get(0).setStreetAddress("101 Renner rd");
        data.get(0).setStreetAddress2("202 Renner rd");
        data.get(0).setFunctionalRole("frole");
        data.get(0).setCtepId("ctepid");
        data.get(0).setStatus("status");
        data.get(0).setAffiliation("affiliation");
        data.get(0).setCity("Richardson");
        data.get(0).setState("TX");
        data.get(0).setCountry("USA");
        data.get(0).setZip("75081");
        data.get(0).setEmail("a@a.com");
        data.get(0).setPhone("1110001111");
        data.get(0).setTty("tty");
        data.get(0).setUrl("www.url.com");
        data.get(0).setFax("222222222");

        assertNotNull(data.get(0).getStreetAddress2());
        assertNotNull(data.get(0).getFunctionalRole());
        assertNotNull(data.get(0).getCtepId());
        assertNotNull(data.get(0).getStatus());
        assertNotNull(data.get(0).getAffiliation());
        assertNotNull("Testing Address failed", data.get(0).getAddress());
        assertNotNull("Testing Email", data.get(0).getEmail());
        assertNotNull("Testing Phone", data.get(0).getPhone());
        assertNotNull("Testing role name", data.get(0).getRoleName());
        assertEquals("Testing tty", "tty", data.get(0).getTty());
        assertEquals("Testing url", "www.url.com", data.get(0).getUrl());
        assertEquals("Testing fax", "222222222", data.get(0).getFax());
    }
    
    
    @Test
    public void testGetAddressWithVariousStateValues() throws Exception {
        List<PaPersonDTO> data = remoteEjb.getPersonsByStudySiteId(Long.valueOf(1), "SUBMITTER");
        data.get(0).setCity("Richardson");
        data.get(0).setState(null);
        data.get(0).setCountry("USA");
        data.get(0).setZip("75081");
        assertEquals("Richardson,,USA,75081",data.get(0).getAddress());
        data.get(0).setState("");
        assertEquals("Richardson,,USA,75081",data.get(0).getAddress());
        data.get(0).setState("   ");
        assertEquals("Richardson,,USA,75081",data.get(0).getAddress());
        data.get(0).setState("TX");
        assertEquals("Richardson,TX,USA,75081",data.get(0).getAddress());
    }
}
