package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyContactBeanLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PersonSynchronizationServiceBeanTest extends AbstractHibernateTestCase {

    private final PersonSynchronizationServiceBean bean = new PersonSynchronizationServiceBean();
    private final PersonSynchronizationServiceLocal remoteEjb = bean;
    StudySiteContactServiceLocal spcService = new StudySiteContactBeanLocal();
    StudyContactServiceLocal scService = new StudyContactBeanLocal();

    Ii pid;
    Long personId = Long.valueOf(1);
    Session session = null;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        bean.setSpcLocal(spcService);
        bean.setScLocal(scService);
        // TestSchema.primeData();
        session  = PaHibernateUtil.getCurrentSession();
        createTestData();
    }

    @Test
    public void synchronizePersonUpdateTest() throws Exception {
        Ii roIi = IiConverter.convertToPoPersonIi("abc");
        remoteEjb.synchronizePerson(roIi);
        Person np = (Person) PaHibernateUtil.getCurrentSession().load(Person.class, personId);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    @Test
    public void synchronizePersonNullifiy() throws Exception {
        Ii poIi = IiConverter.convertToPoPersonIi("abc");
        poIi.setNullFlavor(NullFlavor.NA);
        CorrelationUtils cUtils = new CorrelationUtils();
        Person paPer = cUtils.getPAPersonByIi(poIi);
        StudyProtocolStage sps = new StudyProtocolStage();
        sps.setPiIdentifier(String.valueOf(paPer.getIdentifier()));
        sps.setResponsibleIdentifier(String.valueOf(paPer.getIdentifier()));
        sps.setSitePiIdentifier(String.valueOf(paPer.getIdentifier()));
        sps.setAccrualDiseaseCodeSystem("SDC");
        Long spsId = (Long)session.save(sps);
        session.flush();
        StudyProtocolStage dbSps = (StudyProtocolStage) session.load(StudyProtocolStage.class, spsId);
        assertTrue("Person was not set properly", dbSps.getPiIdentifier().equals("abc"));
        remoteEjb.synchronizePerson(poIi);
        session.flush();
        session.clear();

        dbSps = (StudyProtocolStage) session.load(StudyProtocolStage.class, spsId);
        assertTrue("PiIdentifier was not updated", dbSps.getPiIdentifier().equals("2"));
        assertTrue("ResponsibleIdentifier was not updated", dbSps.getResponsibleIdentifier().equals("2"));
        assertTrue("SitePiIdentifier was not updated", dbSps.getSitePiIdentifier().equals("2"));

    }

    @Test
    public void synchronizeClinicalResearchStaffUpdateTest() throws Exception {
        Ii crsIi = IiConverter.convertToPoClinicalResearchStaffIi("abc");
        remoteEjb.synchronizeClinicalResearchStaff(crsIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    // @Test
    public void synchronizeClinicalResearchStaffNullifyTest() throws Exception {
        Ii crsIi = IiConverter.convertToPoClinicalResearchStaffIi("abc");
        crsIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeClinicalResearchStaff(crsIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    @Test
    public void synchronizeHealthCareProviderUpdateTest() throws Exception {
        Ii hcpIi = IiConverter.convertToPoHealthcareProviderIi("abc");
        remoteEjb.synchronizeHealthCareProvider(hcpIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    @Test
    public void synchronizeHealthCareProviderNulllifyTest() throws Exception {
        Ii hcpIi = IiConverter.convertToPoHealthcareProviderIi("abc");
        hcpIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeHealthCareProvider(hcpIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    @Test
    public void synchronizeOrganizationalContactUpdateTest() throws Exception {
        Ii ocIi = IiConverter.convertToPoOrganizationalContactIi("abc");
        remoteEjb.synchronizeOrganizationalContact(ocIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    @Test
    public void synchronizeOrganizationalContactNullifyTest() throws Exception {
        Ii ocIi = IiConverter.convertToPoOrganizationalContactIi("abc");
        ocIi.setNullFlavor(NullFlavor.NA);
        remoteEjb.synchronizeOrganizationalContact(ocIi);
        // todo : somehow the update is happening in a different session and the changes are not committed, so unable to
        // do assert with the changed values
    }

    private void createTestData() {

        Person p = TestSchema.createPersonObj();
        TestSchema.addUpdObject(p);
        assertNotNull(p.getId());
        personId = p.getId();
        Person np = (Person) PaHibernateUtil.getCurrentSession().load(Person.class, personId);
        // System.out.println("id = "+personId);
        // System.out.println("id = "+np.getId());
        // System.out.println("name id = "+np.getFirstMiddleLastName());
        // System.out.println("name id = "+np.getStatusCode());
        // System.out.println("---------------");

        Organization o = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        assertNotNull(crs.getId());

    }

}
