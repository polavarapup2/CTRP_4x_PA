/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AbstractPDQTrialServiceHelper.PersonWithFullNameDTO;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class POServiceUtilsTest extends AbstractHibernateTestCase {

    private static final MockOrganizationEntityService ORGANIZATION_ENTITY_SERVICE = new MockOrganizationEntityService();
    private static final MockPersonEntityService PERSON_ENTITY_SERVICE = new MockPersonEntityService();
    private static final MockIdentifiedOrganizationCorrelationService ID_ORG_SERVICE = new MockIdentifiedOrganizationCorrelationService();
    private static final MockIdentifiedPersonCorrelationService ID_PERSON_SERVICE = new MockIdentifiedPersonCorrelationService();
   

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        CSMUserService.setInstance(new MockCSMUserService());

        PoServiceLocator poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(
                ORGANIZATION_ENTITY_SERVICE);
        when(poSvcLoc.getPersonEntityService()).thenReturn(
                PERSON_ENTITY_SERVICE);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(
                ID_ORG_SERVICE);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(
                ID_PERSON_SERVICE);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.POServiceUtils#matchOrCreatePoObjects(java.util.List)}
     * .
     * 
     * @throws PAException
     * @throws NullifiedEntityException
     */
    @Test
    public final void testNoMatchCreateNewOrg() throws PAException,
            NullifiedEntityException {

        OrganizationDTO newOrg = new OrganizationDTO();
        newOrg.setName(EnOnConverter.convertToEnOn("New Org No Mapping, Inc"));
        POServiceUtils.matchOrCreatePoObject(newOrg);

        assertNotNull(newOrg.getIdentifier());
        OrganizationDTO match = PoRegistry.getOrganizationEntityService()
                .getOrganization(newOrg.getIdentifier());
        assertEquals("New Org No Mapping, Inc",
                EnOnConverter.convertEnOnToString(match.getName()));
        assertEquals(newOrg.getIdentifier().getExtension(), match
                .getIdentifier().getExtension());

    }

    @Test
    public final void testNoMatchCreateNewPerson() throws PAException,
            NullifiedEntityException {

        PersonDTO newPerson = new PersonDTO();
        newPerson.setName(EnPnConverter.convertToEnPn("John", null, "Doe",
                null, null));
        POServiceUtils.matchOrCreatePoObject(newPerson);

        assertNotNull(newPerson.getIdentifier());

        PersonDTO match = PoRegistry.getPersonEntityService().getPerson(
                newPerson.getIdentifier());
        assertEquals("Doe, John",
                EnPnConverter.convertToLastCommaFirstName(match.getName()));
        assertEquals(newPerson.getIdentifier().getExtension(), match
                .getIdentifier().getExtension());

    }

    @Test
    public final void testMatchByPdqPersonName() throws PAException,
            NullifiedEntityException, EntityValidationException, CurationException {

        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_person_map (ctgov_firstname, ctgov_lastname, pdq_firstname, pdq_lastname) "
                        + "VALUES ('Emily','Ctgov','Emily','Ctrp')")
                .executeUpdate();

        PersonDTO ctrpPerson = new PersonDTO();
        ctrpPerson.setName(EnPnConverter.convertToEnPn("Emily", "H", "Ctrp",
                null, null));
        ctrpPerson.setIdentifier(PoRegistry.getPersonEntityService()
                .createPerson(ctrpPerson));

        PersonDTO ctgovPerson = new PersonDTO();
        ctgovPerson.setName(EnPnConverter.convertToEnPn("Emily", "H", "Ctgov",
                null, null));
        POServiceUtils.matchOrCreatePoObject(ctgovPerson);
        
        assertEquals(ctrpPerson.getIdentifier().getExtension(), ctgovPerson
                .getIdentifier().getExtension());

    }
    
    @Test
    public final void testMatchByPdqPersonFullName() throws PAException,
            NullifiedEntityException, EntityValidationException, CurationException {

        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_person_map (ctgov_fullname, ctgov_firstname, ctgov_lastname, pdq_firstname, pdq_lastname) "
                        + "VALUES ('Dr. James A. B. Doe, MD, PhD', 'James','Doe','James','Ctrp')")
                .executeUpdate();

        PersonDTO ctrpPerson = new PersonDTO();
        ctrpPerson.setName(EnPnConverter.convertToEnPn("James", null, "Ctrp",
                null, null));
        ctrpPerson.setIdentifier(PoRegistry.getPersonEntityService()
                .createPerson(ctrpPerson));

        PersonWithFullNameDTO ctgovPerson = new PersonWithFullNameDTO();
        ctgovPerson.setName(EnPnConverter.convertToEnPn("James", null, "B. Doe",
                null, null));
        ctgovPerson.setFullName("dr. James A. B. Doe, MD, Phd");
        POServiceUtils.matchOrCreatePoObject(ctgovPerson);
        
        assertEquals(ctrpPerson.getIdentifier().getExtension(), ctgovPerson
                .getIdentifier().getExtension());

    }
    
    @Test
    public final void testMatchByPdqPersonPoId() throws PAException,
            NullifiedEntityException, EntityValidationException, CurationException {

        PersonDTO ctrpPerson = new PersonDTO();
        ctrpPerson.setName(EnPnConverter.convertToEnPn("Jane", null, "Ctrp-Doe",
                null, null));
        ctrpPerson.setIdentifier(PoRegistry.getPersonEntityService()
                .createPerson(ctrpPerson));
        
        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_person_map (ctgov_firstname, ctgov_lastname, pdq_firstname, pdq_lastname, po_id) "
                        + "VALUES ('Jane','Ctgov-Doe','','','12345667;"+ctrpPerson.getIdentifier().getExtension()+"')")
                .executeUpdate();
       

        PersonDTO ctgovPerson = new PersonDTO();
        ctgovPerson.setName(EnPnConverter.convertToEnPn("Jane", null, "Ctgov-Doe",
                null, null));
        POServiceUtils.matchOrCreatePoObject(ctgovPerson);
        
        assertEquals(ctrpPerson.getIdentifier().getExtension(), ctgovPerson
                .getIdentifier().getExtension());

    }
    
    @Test
    public final void testMatchByPdqPersonCtepId() throws PAException,
            NullifiedEntityException, EntityValidationException, CurationException {

        PersonDTO ctrpPerson = new PersonDTO();
        ctrpPerson.setName(EnPnConverter.convertToEnPn("Eva", null, "Smith",
                null, null));
        ctrpPerson.setIdentifier(PoRegistry.getPersonEntityService()
                .createPerson(ctrpPerson));
        MockPersonEntityService.PO_ID_TO_CTEP_ID.put(ctrpPerson.getIdentifier().getExtension(), "101010");
        
        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_person_map (ctgov_firstname, ctgov_lastname, pdq_firstname, pdq_lastname, ctep_id) "
                        + "VALUES ('Jack','Ctgov-Doe','A','B','MN026;101010')")
                .executeUpdate();
       

        PersonDTO ctgovPerson = new PersonDTO();
        ctgovPerson.setName(EnPnConverter.convertToEnPn("Jack", null, "Ctgov-Doe",
                null, null));
        POServiceUtils.matchOrCreatePoObject(ctgovPerson);
        
        assertEquals(ctrpPerson.getIdentifier().getExtension(), ctgovPerson
                .getIdentifier().getExtension());
        

    }

    @Test
    public final void testMatchByPdqOrgName() throws PAException,
            NullifiedEntityException, EntityValidationException,
            CurationException {
        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_org_map (role,ctgov_name,ctgov_city,ctgov_state,ctgov_country,cdr_id,pdq_name,pdq_city,pdq_state,pdq_country,po_id,ctep_id) "
                        + "VALUES (null,'Schiffler Cancer Center',null,null,null,null,'Schiffler Cancer Center at Wheeling Hospital',null,null,null,null,null)")
                .executeUpdate();
        OrganizationDTO schiffler = new OrganizationDTO();
        schiffler.setName(EnOnConverter
                .convertToEnOn("Schiffler Cancer Center at Wheeling Hospital"));
        schiffler.setIdentifier(PoRegistry.getOrganizationEntityService()
                .createOrganization(schiffler));

        OrganizationDTO newOrg = new OrganizationDTO();
        newOrg.setName(EnOnConverter.convertToEnOn("Schiffler Cancer Center"));
        POServiceUtils.matchOrCreatePoObject(newOrg);

        assertNotNull(newOrg.getIdentifier());
        assertEquals(newOrg.getIdentifier().getExtension(), schiffler
                .getIdentifier().getExtension());

    }

    @Test
    public final void testMatchByPdqOrgPoId() throws PAException,
            NullifiedEntityException, EntityValidationException,
            CurationException {

        OrganizationDTO sharp = new OrganizationDTO();
        sharp.setName(EnOnConverter
                .convertToEnOn("Sharp Memorial Hospital Cancer Center in San Diego"));
        sharp.setIdentifier(PoRegistry.getOrganizationEntityService()
                .createOrganization(sharp));

        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_org_map (role,ctgov_name,ctgov_city,ctgov_state,ctgov_country,cdr_id,pdq_name,pdq_city,pdq_state,pdq_country,po_id,ctep_id) "
                        + "VALUES (null,'Sharp Memorial Hospital',null,null,null,null,'Sharp Memorial Hospital Cancer Center No Match By Name',null,null,null,'123456;abcdefg;"
                        + sharp.getIdentifier().getExtension() + "',null)")
                .executeUpdate();

        OrganizationDTO newOrg = new OrganizationDTO();
        newOrg.setName(EnOnConverter.convertToEnOn("Sharp Memorial Hospital"));
        POServiceUtils.matchOrCreatePoObject(newOrg);

        assertNotNull(newOrg.getIdentifier());
        assertEquals(newOrg.getIdentifier().getExtension(), sharp
                .getIdentifier().getExtension());

    }

    @Test
    public final void testMatchByPdqOrgCtepId() throws PAException,
            NullifiedEntityException, EntityValidationException,
            CurationException {

        OrganizationDTO match = new OrganizationDTO();
        match.setName(EnOnConverter.convertToEnOn("Eli Lilly and Company"));
        match.setIdentifier(PoRegistry.getOrganizationEntityService()
                .createOrganization(match));
        MockOrganizationEntityService.PO_ID_TO_CTEP_ID.put(match
                .getIdentifier().getExtension(), "LIL001");

        Session s = PaHibernateUtil.getCurrentSession();
        s.createSQLQuery(
                "INSERT INTO ctgov_org_map (role,ctgov_name,ctgov_city,ctgov_state,ctgov_country,cdr_id,pdq_name,pdq_city,pdq_state,pdq_country,po_id,ctep_id) "
                        + "VALUES (null,'Eli Lilly Asia,INC',null,null,null,null,'Eli Lilly and Company No Name/PoID Match',null,null,null,null,'ZO1;LIL001')")
                .executeUpdate();

        OrganizationDTO newOrg = new OrganizationDTO();
        newOrg.setName(EnOnConverter.convertToEnOn("Eli Lilly Asia,INC"));
        POServiceUtils.matchOrCreatePoObject(newOrg);

        assertNotNull(newOrg.getIdentifier());
        assertEquals(newOrg.getIdentifier().getExtension(), match
                .getIdentifier().getExtension());

    }

}
