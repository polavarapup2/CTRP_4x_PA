package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import org.hibernate.Session;
import org.junit.Test;

public class CorrelationUtilsTest extends AbstractHibernateTestCase {
	private final CorrelationUtils correlationUtils = new CorrelationUtils();
	
	@Test
	public void testgetContactByPAOrganizationalContactId() throws Exception {
		try {
			correlationUtils.getContactByPAOrganizationalContactId(null);
			fail("Po Organization Identifier is null  ");
		} catch (PAException e) {
            // expected
        }
		try {
			correlationUtils.getContactByPAOrganizationalContactId(1L);
			fail("Object not found using getContactByPAOrganizationalContactId() for id = 1.");
		} catch (PAException e) {
            // expected
        }
        Session session = PaHibernateUtil.getCurrentSession();

        Organization newOrg = TestSchema.createOrganizationObj();
        Person newPerson = TestSchema.createPersonObj();
        session.saveOrUpdate(newOrg);
        session.saveOrUpdate(newPerson);

        OrganizationalContact orgCon = TestSchema.createOrganizationalContactObj(newOrg, newPerson);

        session.saveOrUpdate(orgCon);
        session.flush();
		correlationUtils.getContactByPAOrganizationalContactId(orgCon.getId());
		
		orgCon.setPerson(null);
        session.saveOrUpdate(orgCon);
        session.flush();
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        correlationUtils.getContactByPAOrganizationalContactId(orgCon.getId());
        
        orgCon.setStatusCode(StructuralRoleStatusCode.NULLIFIED);

        session.saveOrUpdate(orgCon);
        session.flush();
        correlationUtils.getContactByPAOrganizationalContactId(orgCon.getId());        
	}
	
	@Test
	public void testgetPAOrganizationByIi() throws Exception {
		try {
			correlationUtils.getPAOrganizationByIi(null);
			fail("orgStructuralRoleIi is null  ");
		} catch (PAException e) {
            // expected
        }
		Ii roIi = IiConverter.convertToPoResearchOrganizationIi("2");
		correlationUtils.getPAOrganizationByIi(roIi);
		roIi = IiConverter.convertToPoOversightCommitteeIi("2");
		correlationUtils.getPAOrganizationByIi(roIi);
		roIi = IiConverter.convertToPoOrganizationIi("2");
		correlationUtils.getPAOrganizationByIi(roIi);
		roIi = IiConverter.convertToPaOrganizationIi(2L);
		correlationUtils.getPAOrganizationByIi(roIi);
		roIi.setIdentifierName("test");
		try {
			correlationUtils.getPAOrganizationByIi(roIi);
			fail(" Unknown identifier name provided  : test");
		} catch (PAException e) {
            // expected
        }
	}
	

	
	@Test
	public void junitCoverage() throws Exception {
		PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
		try {
			correlationUtils.getPAPersonByIi(null);
			fail("isoIi is null  ");
		} catch (PAException e) {
            // expected
        }
		Ii roIi = IiConverter.convertToPoHealthcareProviderIi("2");
		correlationUtils.getPAPersonByIi(roIi);
		roIi = IiConverter.convertToPoPersonIi("2");
		correlationUtils.getPAPersonByIi(roIi);
		roIi = IiConverter.convertToPaPersonIi(2L);
		correlationUtils.getPAPersonByIi(roIi);
		roIi.setIdentifierName("test");
		try {
			correlationUtils.getPAPersonByIi(roIi);
			fail(" Unknown identifier name provided  : test");
		} catch (PAException e) {
            // expected
        }
		try {
			correlationUtils.convertPOToPAOrganization(null);
			fail(" PO Organization cannot be null");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.convertPOToPAPerson(null);
			fail(" PO Person cannot be null");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.createPADomain(null);
			fail("domain should not be null");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.convertPOEntifyStatusToPAEntityStatus(null);
			fail(" Cd cannot be null");
		} catch (PAException e) {
        // expected
		}
		Cd cd = new Cd();
		cd.setCode("INACTIVE");
		correlationUtils.convertPOEntifyStatusToPAEntityStatus(cd);
		cd.setCode("NULLIFIED");
		correlationUtils.convertPOEntifyStatusToPAEntityStatus(cd);
		cd.setCode("PENDING");
		correlationUtils.convertPOEntifyStatusToPAEntityStatus(cd);
		cd.setCode("test");
		try {
			correlationUtils.convertPOEntifyStatusToPAEntityStatus(cd);
			fail(" Unsupported PA known status test");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.convertPORoleStatusToPARoleStatus(null);
			fail(" Cd cannot be null");
		} catch (PAException e) {
        // expected
		}
		cd.setCode("CANCELLED");
		correlationUtils.convertPORoleStatusToPARoleStatus(cd);
		cd.setCode("NULLIFIED");
		correlationUtils.convertPORoleStatusToPARoleStatus(cd);
		cd.setCode("SUSPENDED");
		correlationUtils.convertPORoleStatusToPARoleStatus(cd);
		cd.setCode("TERMINATED");
		correlationUtils.convertPORoleStatusToPARoleStatus(cd);
		cd.setCode("test");
		try {
			correlationUtils.convertPORoleStatusToPARoleStatus(cd);
			fail(" Unsupported PA known status test");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.createPAOrganization(null);
			fail("organization should not be null");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.createPAPerson(null);
			fail("Person  should not be null");
		} catch (PAException e) {
        // expected
		}
		OrganizationalContactDTO dto = new OrganizationalContactDTO ();
		Set<Ii> set = new HashSet<Ii>();
        set.add(IiConverter.convertToPoOrganizationalContactIi("1"));
        DSet<Ii> iis = new DSet<Ii>();
        iis.setItem(set);        
		dto.setIdentifier(iis);
		correlationUtils.getGenericContactIiFromCtepId(dto);
		set.add(IiConverter.convertToIdentifiedPersonEntityIi("1"));
        iis = new DSet<Ii>();
        iis.setItem(set);        
		dto.setIdentifier(iis);
		try {
			correlationUtils.getGenericContactIiFromCtepId(dto);
		} catch (PAException e) {
        // expected
		}
		set = new HashSet<Ii>();
		set.add(IiConverter.convertToIi("1"));
        iis = new DSet<Ii>();
        iis.setItem(set);        
		dto.setIdentifier(iis);
		try {
			correlationUtils.getGenericContactIiFromCtepId(dto);
			fail("Could not determine root of investigator. Root passed in was: ");
		} catch (PAException e) {
        // expected
		}
		try {
			correlationUtils.getPoOrgContactByCtepId(roIi);
		} catch (PAException e) {
	        // expected
		}
		try {
			correlationUtils.getPoHcpByCtepId(null, new Ii(), new Ii());
			fail();
		} catch (PAException e) {
	        // expected
		}
		try {
			correlationUtils.getPoCrsByCtepId(null, new Ii(), new Ii());
			fail();
		} catch (PAException e) {
	        // expected
		}
	}

}
