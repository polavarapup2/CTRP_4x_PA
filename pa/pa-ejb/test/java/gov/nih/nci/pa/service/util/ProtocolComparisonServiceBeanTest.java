/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityBeanLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureBeanLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal.Difference;
import gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class ProtocolComparisonServiceBeanTest extends
        AbstractHibernateTestCase {

    private static final Collection<String> OGNL = new ArrayList<String>();
    static {
        OGNL.add("studyProtocol.scientificDescription");
        OGNL.add("eligibilityCriteria");
        OGNL.add("collaborators");
        OGNL.add("arms");
        OGNL.add("outcomes");
        OGNL.add("secondaryIDs");
        OGNL.add("leadOrganization");
        OGNL.add("leadOrganizationTrialId");
        OGNL.add("sponsor");
        OGNL.add("status.statusCode");
        OGNL.add("status.statusDate");
        OGNL.add("pi");
        OGNL.add("regulatoryAuthority");
        
        OGNL.add("studyProtocol.consortiaTrialCategoryCode");
        OGNL.add("studyProtocol.ctgovXmlRequiredIndicator");
        OGNL.add("studyProtocol.dataMonitoringCommitteeAppointedIndicator");
        OGNL.add("studyProtocol.delayedpostingIndicator");
        OGNL.add("studyProtocol.fdaRegulatedIndicator");
        OGNL.add("studyProtocol.officialTitle");
        OGNL.add("studyProtocol.phaseAdditionalQualifierCode");
        OGNL.add("studyProtocol.phaseCode");
        OGNL.add("studyProtocol.primaryPurposeAdditionalQualifierCode");
        OGNL.add("studyProtocol.primaryPurposeCode");
        OGNL.add("studyProtocol.primaryPurposeOtherText");
        OGNL.add("studyProtocol.programCodeText");
        OGNL.add("studyProtocol.proprietaryTrialIndicator");
        OGNL.add("studyProtocol.section801Indicator");

        OGNL.add("studyProtocol.acceptHealthyVolunteersIndicator");
        OGNL.add("studyProtocol.accrualReportingMethodCode");
        OGNL.add("studyProtocol.acronym");
        OGNL.add("studyProtocol.amendmentDate");
        OGNL.add("studyProtocol.amendmentNumber");
        OGNL.add("studyProtocol.amendmentReasonCode");
        OGNL.add("studyProtocol.comments");
        OGNL.add("studyProtocol.ctroOverride");
        OGNL.add("studyProtocol.expandedAccessIndicator");
        OGNL.add("studyProtocol.keywordText");
        OGNL.add("studyProtocol.maximumTargetAccrualNumber");
        OGNL.add("studyProtocol.minimumTargetAccrualNumber");
        OGNL.add("studyProtocol.nciGrant");
        OGNL.add("studyProtocol.processingPriority");
        OGNL.add("studyProtocol.publicDescription");
        OGNL.add("studyProtocol.publicTitle");
        OGNL.add("studyProtocol.recordVerificationDate");
        OGNL.add("studyProtocol.reviewBoardApprovalRequiredIndicator");
        OGNL.add("studyProtocol.secondaryPurposeOtherText");
        OGNL.add("studyProtocol.statusCode");
        OGNL.add("studyProtocol.statusDate");

        OGNL.add("studyProtocol.dates.completionDate");
        OGNL.add("studyProtocol.dates.completionDateTypeCode");
        OGNL.add("studyProtocol.dates.primaryCompletionDate");
        OGNL.add("studyProtocol.dates.primaryCompletionDateTypeCode");
        OGNL.add("studyProtocol.dates.startDate");
        OGNL.add("studyProtocol.dates.startDateTypeCode");
        
        OGNL.add("studyProtocol.allocationCode");
        OGNL.add("studyProtocol.blindingRoleCodeCaregiver");
        OGNL.add("studyProtocol.blindingRoleCodeInvestigator");
        OGNL.add("studyProtocol.blindingRoleCodeOutcome");
        OGNL.add("studyProtocol.blindingRoleCodeSubject");
        OGNL.add("studyProtocol.blindingSchemaCode");
        OGNL.add("studyProtocol.designConfigurationCode");
        OGNL.add("studyProtocol.numberOfInterventionGroups");
        OGNL.add("studyProtocol.studyClassificationCode");
        
        OGNL.add("studyProtocol.biospecimenDescription");
        OGNL.add("studyProtocol.biospecimenRetentionCode");
        OGNL.add("studyProtocol.numberOfGroups");
        OGNL.add("studyProtocol.samplingMethodCode");
        OGNL.add("studyProtocol.studyModelCode");
        OGNL.add("studyProtocol.studyModelOtherText");
        OGNL.add("studyProtocol.studyPopulationDescription");
        OGNL.add("studyProtocol.studySubtypeCode");
        OGNL.add("studyProtocol.imePerspectiveCode");
        OGNL.add("studyProtocol.timePerspectiveOtherText");

        OGNL.add("new gov.nih.nci.pa.service.util.PAServiceUtils().getTrialNciId(studyProtocol.id)");
        OGNL.add("studyProtocol.studyResourcings.{? #this.summary4ReportedResourceIndicator==true}.{organizationIdentifier}");
        
    }

    private ProtocolComparisonServiceBean bean = new ProtocolComparisonServiceBean();

    private final PlannedActivityServiceLocal plannedActivityService = new PlannedActivityBeanLocal();

    private final StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureBeanLocal();

    private final StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityBeanLocal();

    private final RegulatoryInformationServiceLocal regulatoryInfoSvc = new RegulatoryInformationBean();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        injectDependencies();
        TestSchema.primeData();
        CSMUserService.setInstance(new MockCSMUserService());
    }

    private void injectDependencies() {
        bean.setPlannedActivityService(plannedActivityService);
        bean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        bean.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        bean.setRegulatoryInformationService(regulatoryInfoSvc);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.ProtocolComparisonServiceBean#compare(gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot, gov.nih.nci.pa.service.util.ProtocolComparisonServiceLocal.ProtocolSnapshot, java.util.Collection)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testNoChanges() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);
        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertTrue(diffs.isEmpty());
    }

    @Test
    public final void testScientificDescription() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);

        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession()
                .get(StudyProtocol.class, spID);
        sp.setScientificDescription("Before");
        PaHibernateUtil.getCurrentSession().update(sp);

        ProtocolSnapshot before = bean.captureSnapshot(spID);

        sp = (StudyProtocol) PaHibernateUtil.getCurrentSession().get(
                StudyProtocol.class, spID);
        sp.setScientificDescription("After");
        PaHibernateUtil.getCurrentSession().update(sp);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("studyProtocol.scientificDescription", diff.getFieldKey());
        assertEquals("After", diff.getNewValue());
        assertEquals("Before", diff.getOldValue());

    }

    @Test
    public final void testEligCriteria() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession()
                .get(StudyProtocol.class, spID);
        PlannedEligibilityCriterion pec = new PlannedEligibilityCriterion();
        pec.setCategoryCode(ActivityCategoryCode.ELIGIBILITY_CRITERION);
        pec.setCriterionName("WHC");
        pec.setInclusionIndicator(Boolean.FALSE);
        pec.setOperator("<");
        pec.setStudyProtocol(sp);
        pec.setMinValue(new BigDecimal("12"));
        pec.setMinUnit(UnitsCode.MONTHS.getCode());
        pec.setMaxValue(new BigDecimal("24"));
        pec.setMaxUnit(UnitsCode.MONTHS.getCode());
        TestSchema.addUpdObject(pec);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("eligibilityCriteria", diff.getFieldKey());

    }

    @Test
    public final void testCollaborators() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudySite collaborator = new StudySite();
        collaborator.setFunctionalCode(StudySiteFunctionalCode.LABORATORY);
        collaborator.setResearchOrganization((ResearchOrganization) s.get(
                ResearchOrganization.class,
                TestSchema.researchOrganizationIds.get(0)));
        collaborator.setLocalStudyProtocolIdentifier("ABC");
        collaborator.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        collaborator.setStatusDateRangeLow(ISOUtil
                .dateStringToTimestamp("6/1/2008"));
        collaborator.setStudyProtocol(sp);
        TestSchema.addUpdObject(collaborator);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("collaborators", diff.getFieldKey());
        assertEquals("[Mayo University]", diff.getNewValue().toString());

    }

    @Test
    public final void testSponsor() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudySite sponsor = new StudySite();
        sponsor.setFunctionalCode(StudySiteFunctionalCode.SPONSOR);
        sponsor.setResearchOrganization((ResearchOrganization) s.get(
                ResearchOrganization.class,
                TestSchema.researchOrganizationIds.get(0)));
        sponsor.setLocalStudyProtocolIdentifier("ABC");
        sponsor.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sponsor.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("6/1/2008"));
        sponsor.setStudyProtocol(sp);
        TestSchema.addUpdObject(sponsor);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("sponsor", diff.getFieldKey());
        assertEquals("Mayo University", diff.getNewValue().toString());

    }
    
    @Test
    public final void testSummary4Sponsor() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);
        
        sp.getStudyResourcings().get(0).setOrganizationIdentifier("2");
        TestSchema.addUpdObject(sp.getStudyResourcings().get(0));

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("studyProtocol.studyResourcings.{? #this.summary4ReportedResourceIndicator==true}.{organizationIdentifier}", diff.getFieldKey());
        assertEquals("[1]", diff.getOldValue().toString());
        assertEquals("[2]", diff.getNewValue().toString());

    }

    @Test
    public final void testArms() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        Arm arm = new Arm();
        arm.setDescriptionText("Description");
        arm.setName("After");
        arm.setStudyProtocol(sp);
        arm.setTypeCode(ArmTypeCode.EXPERIMENTAL);
        TestSchema.addUpdObject(arm);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("arms", diff.getFieldKey());
        assertEquals("[ARM 01]", diff.getOldValue().toString());
        assertEquals("[ARM 01, After]", diff.getNewValue().toString());

    }

    @Test
    public final void testOutcomes() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudyOutcomeMeasure som = new StudyOutcomeMeasure();
        som.setName("After");
        som.setStudyProtocol(sp);
        som.setPrimaryIndicator(Boolean.FALSE);
        TestSchema.addUpdObject(som);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("outcomes", diff.getFieldKey());
        assertEquals("[StudyOutcomeMeasure]", diff.getOldValue().toString());
        assertEquals("[After, StudyOutcomeMeasure]", diff.getNewValue()
                .toString());

    }

    @Test
    public final void testSecondaryIDs() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        Ii ii = new Ii();
        ii.setExtension("After");
        ii.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        ii.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        sp.getOtherIdentifiers().add(ii);
        TestSchema.addUpdObject(sp);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("secondaryIDs", diff.getFieldKey());
        assertEquals("[NCI-2009-00001]", diff.getOldValue().toString());
        assertEquals("[After, NCI-2009-00001]", diff.getNewValue().toString());

    }

    @Test
    public final void testLeadOrg() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudySite leadOrg = (StudySite) s
                .createCriteria(StudySite.class)
                .add(Restrictions.eq("studyProtocol", sp))
                .add(Restrictions.eq("functionalCode",
                        StudySiteFunctionalCode.LEAD_ORGANIZATION))
                .uniqueResult();
        leadOrg.setLocalStudyProtocolIdentifier("IDAfter");

        Organization org = new Organization();
        org.setName("Dukes");
        org.setIdentifier("100");
        org.setStatusCode(EntityStatusCode.PENDING);
        TestSchema.addUpdObject(org);

        ResearchOrganization rOrg = new ResearchOrganization();
        rOrg.setOrganization(org);
        rOrg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        rOrg.setIdentifier("After");
        TestSchema.addUpdObject(rOrg);

        leadOrg.setResearchOrganization(rOrg);
        TestSchema.addUpdObject(leadOrg);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(2, diffs.size());

        final Iterator<Difference> iterator = diffs.iterator();
        Difference diff1 = iterator.next();
        assertEquals("leadOrganization", diff1.getFieldKey());
        assertEquals("Mayo University", diff1.getOldValue().toString());
        assertEquals("Dukes", diff1.getNewValue().toString());

        Difference diff2 = iterator.next();
        assertEquals("leadOrganizationTrialId", diff2.getFieldKey());
        assertEquals("Local SP ID 02", diff2.getOldValue().toString());
        assertEquals("IDAfter", diff2.getNewValue().toString());

    }

    @Test
    public final void testStatus() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        final Timestamp statusDate = new Timestamp(System.currentTimeMillis());
        sos.setStatusDate(statusDate);
        sos.setStudyProtocol(sp);
        TestSchema.addUpdObject(sos);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(2, diffs.size());

        final Iterator<Difference> it = diffs.iterator();
        Difference diff = it.next();
        assertEquals("status.statusCode", diff.getFieldKey());
        assertEquals("ACTIVE", diff.getOldValue().toString());
        assertEquals("ENROLLING_BY_INVITATION", diff.getNewValue().toString());

        diff = it.next();
        assertEquals("status.statusDate", diff.getFieldKey());
        assertEquals(statusDate, diff.getNewValue());

    }

    @Test
    public final void testPI() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        StudyContact sc = (StudyContact) s
                .createCriteria(StudyContact.class)
                .add(Restrictions.eq("studyProtocol", sp))
                .add(Restrictions.eq("roleCode",
                        StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR))
                .uniqueResult();

        Person p = TestSchema.createPersonObj();
        p.setIdentifier("1234");
        TestSchema.addUpdObject(p);

        Organization org = new Organization();
        org.setName("NCI");
        org.setIdentifier("1001");
        org.setStatusCode(EntityStatusCode.PENDING);
        TestSchema.addUpdObject(org);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(
                org, p);
        crs.setIdentifier("12345");
        TestSchema.addUpdObject(crs);

        sc = TestSchema.createStudyContactObj(sp, null, null, crs);
        TestSchema.addUpdObject(sc);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("pi", diff.getFieldKey());
        assertEquals("Amiruddin, Naveen", diff.getNewValue().toString());

    }

    @Test
    public final void testRegulatoryAuthority() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class, spID);

        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("After");
        ra.setCountry(TestSchema.countries.get(0));
        TestSchema.addUpdObject(ra);

        StudyRegulatoryAuthority sra = new StudyRegulatoryAuthority();
        sra.setRegulatoryAuthority(ra);
        sra.setStudyProtocol(sp);
        TestSchema.addUpdObject(sra);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(1, diffs.size());

        Difference diff = diffs.iterator().next();
        assertEquals("regulatoryAuthority", diff.getFieldKey());
        assertEquals("Food and Drug Administration", diff.getOldValue()
                .toString());
        assertEquals("After", diff.getNewValue().toString());

    }

    @Test
    public final void testMisc() throws PAException {
        Long spID = TestSchema.studyProtocolIds.get(0);
        ProtocolSnapshot before = bean.captureSnapshot(spID);

        final Session s = PaHibernateUtil.getCurrentSession();
        InterventionalStudyProtocol sp = (InterventionalStudyProtocol) s.get(
                StudyProtocol.class, spID);

        final Timestamp ts = new Timestamp(System.currentTimeMillis());
        sp.getDates().setStartDate(ts);
        sp.setStudyClassificationCode(StudyClassificationCode.BIO_AVAILABILITY);
        s.saveOrUpdate(sp);

        ProtocolSnapshot after = bean.captureSnapshot(spID);
        Collection<Difference> diffs = bean.compare(before, after, OGNL);
        assertEquals(2, diffs.size());

        OGNL.add("studyProtocol.dates.startDate");
        OGNL.add("studyProtocol.studyClassificationCode");

        final Iterator<Difference> it = diffs.iterator();
        Difference diff = it.next();
        assertEquals("studyProtocol.dates.startDate", diff.getFieldKey());
        assertEquals(ts, diff.getNewValue());

        diff = it.next();
        assertEquals("studyProtocol.studyClassificationCode",
                diff.getFieldKey());
        assertEquals(StudyClassificationCode.BIO_AVAILABILITY,
                diff.getNewValue());
        assertNull(diff.getOldValue());

    }

}
