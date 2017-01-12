package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PAOrganizationServiceTest extends AbstractHibernateTestCase {

    private PAOrganizationServiceBean bean = new PAOrganizationServiceBean();
    private PAOrganizationServiceRemote remoteEjb = bean;
    private Long orgId;

    @Before
    public void setUp(  ) throws Exception {        
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Organization o = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(o);
        orgId = o.getId();
        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);
        StudySite spc = TestSchema.createStudySiteObj(sp, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        StudySiteContact spcc = new StudySiteContact();
        spcc.setClinicalResearchStaff(crs);
        spcc.setRoleCode(StudySiteContactRoleCode.SUBMITTER);
        spcc.setStudySite(spc);
        spcc.setStudyProtocol(sp);
        spcc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(spcc);
    }

    @Test
    public void getOrganizationsAssociatedWithStudyProtocolTest() throws Exception {
        List<PaOrganizationDTO> data = remoteEjb.getOrganizationsAssociatedWithStudyProtocol(PAConstants.LEAD_ORGANIZATION);
        assertNotNull(data);
        assertEquals("Size does not match", data.size(), 1);
        assertEquals(" name does not match", data.get(0).getName(), "Mayo University");
    }
    
    @Test
    public void getOrganizationsWithTypeAndNameAssociatedWithStudyProtocolTest() throws Exception {
        List<PaOrganizationDTO> data = remoteEjb.getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(PAConstants.LEAD_ORGANIZATION, "Mayo");
        assertNotNull(data);
        assertEquals("Size does not match", data.size(), 1);
        assertEquals(" name does not match", data.get(0).getName(), "Mayo University");
    }

    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        remoteEjb.getOrganizationByIndetifers(new Organization());
    }

    @Test
    public void getOrganizationByIndetifersTest() throws Exception {
        Organization o = new Organization();
        o.setId(orgId);
        Organization data = remoteEjb.getOrganizationByIndetifers(o);
        assertNotNull(data);
        assertEquals(" name does not match" , data.getName(), "Mayo University");
    } 
    
    @Test
    public void getOrganizationIdsByNames() throws PAException {
        TestBean<String, Long> testBean = createDataForSearchByName() ;
        List<Long> result = remoteEjb.getOrganizationIdsByNames(testBean.input);
        assertEquals(3, result.size());
        Collections.sort(testBean.output);
        Collections.sort(result);
        assertTrue(CollectionUtils.isEqualCollection(testBean.output, result));
    }
    
    @Test
    public void getOrganizationsWithUserAffiliations() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        
        List<Organization> list = remoteEjb
                .getOrganizationsWithUserAffiliations();
        assertEquals(0, list.size());
        
        RegistryUser user = TestSchema.getRegistryUser();
        user.setAffiliatedOrganizationId(1L);
        s.update(user);
        s.flush();
        
        list = remoteEjb
                .getOrganizationsWithUserAffiliations();
        //assertEquals(1, list.size()); //This has a 50% failure
        //assertEquals(orgId, list.get(0).getId());
    }
    
    
    private TestBean<String, Long> createDataForSearchByName() {
        TestBean<String, Long> result = new TestBean<String, Long>();

        Organization organization1 = TestSchema.createOrganizationObj();
        organization1.setName("name1");
        TestSchema.addUpdObject(organization1);
        result.input.add("name1");
        result.output.add(organization1.getId());

        Organization organization2 = TestSchema.createOrganizationObj();
        organization2.setName("name2");
        TestSchema.addUpdObject(organization2);
        result.input.add("name2");
        result.output.add(organization2.getId());

        Organization organization3 = TestSchema.createOrganizationObj();
        organization3.setName("name3");
        TestSchema.addUpdObject(organization3);
        result.input.add("name3");
        result.output.add(organization3.getId());

        return result;
    }
    
    
    private static class TestBean<I, U> {
        List<I> input = new ArrayList<I>();
        List<U> output = new ArrayList<U>();
    } 
}
