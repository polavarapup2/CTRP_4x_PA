package gov.nih.nci.pa.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.junit.Before;
import org.junit.Test;

public class OrganizationSynchronizationServiceBeanTest extends AbstractHibernateTestCase {

    private final OrganizationSynchronizationServiceBean bean = new OrganizationSynchronizationServiceBean();
    StudySiteServiceLocal spsService = new StudySiteBeanLocal();
    Long createdHcfId = null;
    Long createdSpsId = null;

    Ii pid;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        bean.setSpsLocal(spsService);
        TestSchema.primeData();

        createTestData();
    }

    @Test
    public void synchronizeHealthCareFacilityActiveToPending() throws Exception {
        Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi("abc");
        bean.synchronizeHealthCareFacility(hcfIi);
    }


//    @Test
    public void synchronizeHealthCareFacilityNullify() throws Exception {
        Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi("abc");
        hcfIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeHealthCareFacility(hcfIi);

    }

    @Test
    public void synchronizeResearchOrganizationActiveToPending() throws Exception {
        Ii roIi = IiConverter.convertToPoResearchOrganizationIi("abc");
        bean.synchronizeResearchOrganization(roIi);

    }

    @Test
    public void synchronizeResearchOrganizationNullify() throws Exception {
        Ii roIi = IiConverter.convertToPoResearchOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeResearchOrganization(roIi);
    }

    @Test
    public void synchronizeOversightCommitteeActiveToPending() throws Exception {
        Ii roIi = IiConverter.convertToPoOversightCommitteeIi("abc");
        bean.synchronizeOversightCommittee(roIi);

    }

    @Test
    public void synchronizeOversightCommitteeNullify() throws Exception {
        Ii roIi = IiConverter.convertToPoOversightCommitteeIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeOversightCommittee(roIi);
    }

    @Test
    public void synchronizeOrganization()  throws Exception {
        Ii roIi = IiConverter.convertToPoOrganizationIi("abc");
        bean.synchronizeOrganization(roIi);
    }

   @Test
    public void synchronizeOrganizationNullify()  throws Exception {
        Ii roIi = IiConverter.convertToPoOrganizationIi("abc");
        roIi.setNullFlavor(NullFlavor.NA);
        bean.synchronizeOrganization(roIi);
    }

    @Test
    public void testOrgNullification() throws Exception {
        Session session = PaHibernateUtil.getCurrentSession();

        Criteria criteria = session.createCriteria(Organization.class);
        criteria.add(Expression.eq("identifier", "22"));
        assertTrue("new pa org should not exist yet", criteria.list().size() == 0);

        User csmUser = new User();
        csmUser.setLoginName("loginName");
        csmUser.setFirstName("firstName");
        csmUser.setLastName("lastName");
        csmUser.setUpdateDate(new Date());
        session.save(csmUser);
        
        RegistryUser ru = new RegistryUser();
        ru.setAffiliatedOrganizationId(2L);
        ru.setAffiliateOrg("isNullified");
        ru.setCsmUser(csmUser);
        Long ruId = (Long) session.save(ru);
        session.flush();
        CorrelationUtils cUtils = new CorrelationUtils();
        Ii roIi = IiConverter.convertToPoOrganizationIi("2");
        Organization paOrg = cUtils.getPAOrganizationByIi(roIi);

        StudyProtocolStage sps = new StudyProtocolStage();
        sps.setLeadOrganizationIdentifier(String.valueOf(paOrg.getIdentifier()));
        sps.getSummaryFourOrgIdentifiers().add(String.valueOf(paOrg.getIdentifier()));
        sps.setSponsorIdentifier(String.valueOf(paOrg.getIdentifier()));
        sps.setSubmitterOrganizationIdentifier(String.valueOf(paOrg.getIdentifier()));
        sps.setAccrualDiseaseCodeSystem("SDC");

        Long spsId = (Long) session.save(sps);
        session.flush();
        StudyProtocolStage dbSps = (StudyProtocolStage) session.load(StudyProtocolStage.class, spsId);
        assertTrue("Org Name was not set properly", dbSps.getLeadOrganizationIdentifier().equals("2"));

        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cancer for THOLA");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(ISOUtil.dateStringToTimestamp("1/1/2000"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(ISOUtil.dateStringToTimestamp("12/31/2009"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setExtension("NCI-2009-00001");
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(1);
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        sp.setAccrualDiseaseCodeSystem("SDC");
        Long spId = (Long) session.save(sp);
        session.flush();

        StudyProtocol dbSp = (StudyProtocol) session.load(StudyProtocol.class, spId);
        StudyResourcing sr = new StudyResourcing();
        sr.setOrganizationIdentifier(String.valueOf(paOrg.getId()));
        sr.setStudyProtocol(dbSp);
        Long srId = (Long) session.save(sr);
        session.flush();

        StudyResourcing dbSr = (StudyResourcing) session.load(StudyResourcing.class, srId);
        assertEquals("Sr Org Name was not set properly", "2", dbSr.getOrganizationIdentifier());

        bean.synchronizeOrganization(roIi);
        session.flush();
        session.clear();

        RegistryUser dbRu = (RegistryUser) session.load(RegistryUser.class, ruId);

        assertTrue(dbRu.getAffiliatedOrganizationId().equals(22L));
        assertTrue("Org Name was not updated", dbRu.getAffiliateOrg().equals("OrgName"));

        dbSps = (StudyProtocolStage) session.load(StudyProtocolStage.class, spsId);
        assertTrue("LeadOrganizationIdentifier was not updated", dbSps.getLeadOrganizationIdentifier().equals("22"));
        for (String summaryFOrg : dbSps.getSummaryFourOrgIdentifiers()) {
        	assertTrue("SummaryFourOrgIdentifier was not updated", summaryFOrg.equals("22"));
        }
        assertTrue("SponsorIdentifier was not updated", dbSps.getSponsorIdentifier().equals("22"));
        assertTrue("SubmitterOrganizationIdentifier was not updated", dbSps.getSubmitterOrganizationIdentifier()
            .equals("22"));

        criteria = session.createCriteria(Organization.class);
        criteria.add(Expression.eq("identifier", "22"));
        assertTrue("new pa org should exist", criteria.list().size() == 1);
    }

    private void createTestData() {

        Organization org  = TestSchema.createOrganizationObj();
        org.setIdentifier("2");
        org.setName("Will be nullified Org");
        Session session = PaHibernateUtil.getCurrentSession();
        session.save(org);
        session.flush();
    }

}
