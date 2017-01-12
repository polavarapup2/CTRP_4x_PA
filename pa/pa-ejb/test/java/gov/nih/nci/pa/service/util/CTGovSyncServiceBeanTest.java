/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static gov.nih.nci.iso21090.EntityNamePartType.FAM;
import static gov.nih.nci.iso21090.EntityNamePartType.GIV;
import static gov.nih.nci.iso21090.EntityNamePartType.PFX;
import static gov.nih.nci.iso21090.EntityNamePartType.SFX;
import static gov.nih.nci.pa.iso.util.EnPnConverter.convertToEnPn;
import static gov.nih.nci.pa.iso.util.EnPnConverter.getNamePart;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Denis G. Krylov
 * 
 */
public class CTGovSyncServiceBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CTGovSyncServiceBean serviceBean;

    private User ctgovimportUser;
    private User notCtgovimportUser;
    private static final String NCT02158936 = "NCT02158936";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        serviceBean = (CTGovSyncServiceBean) getEjbBean(CTGovSyncServiceBean.class);
        setUpCtgovUser();
        PaHibernateUtil.disableAudit();
    }

    /**
     * @throws HibernateException
     * @throws PAException
     */
    private void setUpCtgovUser() throws HibernateException, PAException {
        ctgovimportUser = new User();
        ctgovimportUser.setLoginName(CTGovSyncServiceBean.CTGOVIMPORT_USERNAME);
        ctgovimportUser.setFirstName("ctgovimport");
        ctgovimportUser.setLastName("ctgovimport");
        ctgovimportUser.setUpdateDate(new Date());
        TestSchema.addUpdObject(ctgovimportUser);

        RegistryUser ctgovimportRegistryUser = new RegistryUser();
        ctgovimportRegistryUser.setCsmUser(ctgovimportUser);
        ctgovimportRegistryUser.setFirstName("ctgovimport");
        ctgovimportRegistryUser.setLastName("ctgovimport");
        TestSchema.addUpdObject(ctgovimportRegistryUser);

        notCtgovimportUser = new User();
        notCtgovimportUser.setLoginName("notCtgovimportUser");
        notCtgovimportUser.setFirstName("notCtgovimportUser");
        notCtgovimportUser.setLastName("notCtgovimportUser");
        notCtgovimportUser.setUpdateDate(new Date());
        TestSchema.addUpdObject(notCtgovimportUser);

        RegistryUser notCtgovimportUser = new RegistryUser();
        notCtgovimportUser.setCsmUser(this.notCtgovimportUser);
        notCtgovimportUser.setFirstName("notCtgovimportUser");
        notCtgovimportUser.setLastName("notCtgovimportUser");
        TestSchema.addUpdObject(notCtgovimportUser);

        PaHibernateUtil.getCurrentSession().flush();
        UsernameHolder.setUserCaseSensitive(ctgovimportUser.getLoginName());

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void done() throws Exception {

        PaHibernateUtil.enableAudit();

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getCtGovStudyByNctId(java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetCtGovStudyByNctId() throws PAException {
        assertNotNull(serviceBean.getCtGovStudyByNctId("NCT01861054"));
        assertNull(serviceBean.getCtGovStudyByNctId(""));
        assertNull(serviceBean.getCtGovStudyByNctId(null));
        assertNull(serviceBean.getCtGovStudyByNctId("NCT404"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getAdaptedCtGovStudyByNctId(java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetAdaptedCtGovStudyByNctId() throws PAException {
        CTGovStudyAdapter adapter = serviceBean
                .getAdaptedCtGovStudyByNctId("NCT01861054");
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testImportFailureLogCreated() throws PAException,
            ParseException {
        final Session session = PaHibernateUtil.getCurrentSession();

        try {
            serviceBean.importTrial("NCT500");
            fail();
        } catch (PAException e) {
        }

        CTGovImportLog log = (CTGovImportLog) session.createQuery(
                " from CTGovImportLog log where log.nctID='NCT500'")
                .uniqueResult();
        assertEquals("Failure: unable to retrieve from ClinicalTrials.gov",
                log.getImportStatus());
    }

    @Test
    public final void testConvertCtGovPhone() {
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609 896-9100"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609 896 9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609-896 9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("(609) 896-9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("1-609.896.9100 (U.S. Only)"));
        assertEquals("514-428-8600",
                serviceBean.convertCtGovPhone("514-428-8600 / 1-800-567-2594"));
        assertEquals("888-669-6682",
                serviceBean.convertCtGovPhone("1-888-669-6682"));
        assertEquals("800-340-6843",
                serviceBean.convertCtGovPhone("+1(800)340-6843"));
        assertEquals("301-728-7094",
                serviceBean.convertCtGovPhone("3017287094"));
    }

    @Test
    public final void testDropLeftOver() {
        assertEquals(
                "Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin.",
                serviceBean
                        .dropLeftOver("Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin.\r\n\r\n        Details of the above and additional inclusion and exclusion criteria can be discussed with"));

    }

    @Test
    public final void testBreakDownCtGovPersonName() throws PAException {
        EnPn name = getEnPn("Mark S. Allen, MD");
        EnPn converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Mark", getNamePart(converted, GIV, 0));
        assertEquals("Allen", getNamePart(converted, FAM));
        assertEquals("S", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));

        name = getEnPn("Lajos Pusztai, MD, DPHIL");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Lajos", getNamePart(converted, GIV, 0));
        assertEquals("Pusztai", getNamePart(converted, FAM));
        assertEquals(null, getNamePart(converted, GIV, 1));
        assertEquals("MD, DPHIL", getNamePart(converted, SFX));

        name = getEnPn("Maria E. Suarez-Almazor, MD, PhD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Maria", getNamePart(converted, GIV, 0));
        assertEquals("Suarez-Almazor", getNamePart(converted, FAM));
        assertEquals("E", getNamePart(converted, GIV, 1));
        assertEquals("MD, PhD", getNamePart(converted, SFX));

        name = getEnPn("Prof. Mary J Laughlin, MD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Mary", getNamePart(converted, GIV, 0));
        assertEquals("Laughlin", getNamePart(converted, FAM));
        assertEquals("J", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));
        assertEquals("Prof.", getNamePart(converted, PFX));

        name = getEnPn("Corey Cutler, MD, MPH, FRCP(C)");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Corey", getNamePart(converted, GIV, 0));
        assertEquals("Cutler", getNamePart(converted, FAM));
        assertEquals("MD, MPH, F", getNamePart(converted, SFX));

        name = getEnPn("Laurie Grove, PA-C");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Laurie", getNamePart(converted, GIV, 0));
        assertEquals("Grove", getNamePart(converted, FAM));
        assertEquals("PA-C", getNamePart(converted, SFX));

        name = getEnPn("Ellie Guardino, MD/PhD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Ellie", getNamePart(converted, GIV, 0));
        assertEquals("Guardino", getNamePart(converted, FAM));
        assertEquals("MD/PhD", getNamePart(converted, SFX));

        name = getEnPn("Barry Skikne, M.D., FACP; FCP (SA)");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Barry", getNamePart(converted, GIV, 0));
        assertEquals("Skikne", getNamePart(converted, FAM));
        assertEquals("M.D., FACP", getNamePart(converted, SFX));

        name = getEnPn("Drhubo J Ghaal, MD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Drhubo", getNamePart(converted, GIV, 0));
        assertEquals("Ghaal", getNamePart(converted, FAM));
        assertEquals("J", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));
        assertEquals(null, getNamePart(converted, PFX));

    }

    private EnPn getEnPn(String string) {
        return convertToEnPn(null, null, string, null, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6968() throws PAException {
        String nctID = "NCT01727869";
        String newNctID = "NCT01727870";
        String officialTitle = "Study of REGN1400 Alone and in Combination With Erlotinib or Cetuximab in Patients With Certain Types of Cancer";
        // Exercise new import trial
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            assertNotNull(sp.getOfficialTitle());
            assertTrue("Official title must match", sp.getOfficialTitle()
                    .equals(officialTitle));

            // Change NCT identifier to be able to update this protocol with a
            // different ClinicalTrials.gov XML
            // that actually belongs to a different trial.
            changeNCTNumber(nctID, newNctID);

            // Apply update on top of the existing record.
            String newNciID = serviceBean.importTrial(newNctID);
            final long newId = getProtocolIdByNciId(newNciID, session);

            session.flush();
            session.clear();

            // Make sure we didn't create two protocols.
            assertEquals(nciID, newNciID);
            assertEquals(id, newId);

            sp = (InterventionalStudyProtocol) session.get(
                    InterventionalStudyProtocol.class, id);
            assertNotNull(sp.getOfficialTitle());
            assertTrue("Official title must match", sp.getOfficialTitle()
                    .equals(officialTitle));
            StudyInbox inbox = sp.getStudyInbox().iterator().next();
            assertNotNull(inbox);
            assertEquals(
                    inbox.getComments(),
                    "Trial has been updated from ClinicalTrials.gov\rDetailed Description changed\rEligibility Criteria changed");
            // fields of interest don't get auto acknowledged
            assertNull(inbox.getCloseDate());
        } finally {
            deactivateTrial(session, id);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPO7090() throws PAException {
        String nctID = "NCT00001111";
        String newNctID = "NCT00001112";
        // Exercise new import trial
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            // Change NCT identifier to be able to update this protocol with a
            // different ClinicalTrials.gov XML
            // that actually belongs to a different trial.
            changeNCTNumber(nctID, newNctID);

            // Apply update on top of the existing record.
            String newNciID = serviceBean.importTrial(newNctID);
            final long newId = getProtocolIdByNciId(newNciID, session);

            session.flush();
            session.clear();

            // Make sure we didn't create two protocols.
            assertEquals(nciID, newNciID);
            assertEquals(id, newId);

            sp = (InterventionalStudyProtocol) session.get(
                    InterventionalStudyProtocol.class, id);
            StudyInbox inbox = sp.getStudyInbox().iterator().next();
            assertNotNull(inbox);
            assertEquals(inbox.getComments(),
                    "Trial has been updated from ClinicalTrials.gov");
            // non fields of interest get auto acknowledged
            assertNotNull(inbox.getCloseDate());
        } finally {
            deactivateTrial(session, id);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6438EligCriteriaHandling() throws PAException,
            ParseException {
        String nciID = serviceBean.importTrial("NCT00324155");
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        Ii ii = IiConverter.convertToStudyProtocolIi(id);

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(4, exclList.size());
        assertEquals("Pregnant / nursing", exclList.get(0).getTextDescription());
        assertEquals("Primary ocular or mucosal melanoma", exclList.get(3)
                .getTextDescription());

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(7, inclList.size());
        assertEquals("Informed Consent", inclList.get(0).getTextDescription());
        assertEquals("Prior therapy restriction (adjuvant only)",
                inclList.get(6).getTextDescription());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6467EligCriteriaHandling() throws PAException,
            ParseException {
        String nciID = serviceBean.importTrial("NCT01023880");
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(1, exclList.size());
        assertTrue(exclList.get(0).getTextDescription().trim()
                .startsWith("Key Inclusion Criteria:"));

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(1, inclList.size());
        assertTrue(inclList.get(0).getTextDescription().trim()
                .startsWith("Key Inclusion Criteria:"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6548EligCriteriaHandling() throws PAException,
            ParseException {
        String nciID = serviceBean.importTrial("NCT00653939");
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(6, inclList.size());
        assertEquals(
                "Pathologically confirmed Stage IIIB NSCLC with malignant pleural   effusion, or Stage IV disease",
                inclList.get(0).getTextDescription());
        assertEquals(
                "Subjects or their legal representatives must be able to read, understand and provide written informed consent to participate in the trial.",
                inclList.get(5).getTextDescription());

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(10, exclList.size());
        assertEquals("Predominant Squamous Cell NSCLC histology.", exclList
                .get(0).getTextDescription());
        assertEquals(
                "Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin, bevacizumab, or radiologic contrast dyes.",
                exclList.get(9).getTextDescription());

    }

    /**
     * @param session
     * @param spID
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<PlannedEligibilityCriterion> getInclusionCriteriaList(
            final Session session, final long spID) throws HibernateException {
        List<PlannedEligibilityCriterion> inclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=true and so.criterionName is null and so.studyProtocol.id="
                                + spID + " order by so.displayOrder").list();
        return inclList;
    }

    /**
     * @param session
     * @param spID
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<PlannedEligibilityCriterion> getExclusionCriteriaList(
            final Session session, final long spID) throws HibernateException {
        List<PlannedEligibilityCriterion> exclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=false and so.studyProtocol.id="
                                + spID + " order by so.displayOrder").list();
        return exclList;
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testIOExceptionRetried3TimesAndThenFails()
            throws PAException, ParseException {
        thrown.expect(PAException.class);
        thrown.expectMessage(SystemUtils.IS_OS_WINDOWS ? "Connection refused: connect"
                : "Connection refused");

        stopNctMockServer();
        String nciID = serviceBean.importTrial("NCT01861054");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#importTrial(java.lang.String)}
     * .
     * 
     * @throws PAException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testImportTrial() throws PAException, ParseException {
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            checkTrialPersonOrgData(sp, "Sponsor Inc.", "Sponsor Inc.", nctID);
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, false);

        } finally {
            deactivateTrial(session, id);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testActiveNotRecruitingMapsToClosedToAccrual()
            throws PAException, ParseException {
        final String nctID = "NCT3408503445";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            assertEquals(StudyStatusCode.CLOSED_TO_ACCRUAL, sp
                    .getStudyOverallStatuses().iterator().next()
                    .getStatusCode());
        } finally {
            deactivateTrial(session, id);
        }
    }

    @Test
    public final void testImportDoubleMaskingNoLongerFails()
            throws PAException, ParseException {

        final String nctID = "NCT1111111";
        String nciID = serviceBean.importTrial(nctID);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                .get(InterventionalStudyProtocol.class, id);

        assertEquals(BlindingSchemaCode.DOUBLE_BLIND,
                sp.getBlindingSchemaCode());
        assertNull(sp.getBlindingRoleCodeCaregiver());
        assertNull(sp.getBlindingRoleCodeSubject());
        assertNull(sp.getBlindingRoleCodeInvestigator());
        assertNull(sp.getBlindingRoleCodeOutcome());

    }

    @Test
    public final void testUpdateTrial() throws PAException, ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);

        // Change NCT identifier to be able to update this protocol with a
        // different ClinicalTrials.gov XML that
        // actually belongs to a different trial.
        changeNCTNumber("NCT01440088", "NCT01861054");

        nctID = "NCT01861054";

        // Apply update on top of the existing record.
        String newNciID = serviceBean.importTrial(nctID);
        final long newId = getProtocolIdByNciId(newNciID, session);

        session.flush();
        session.clear();

        // Make sure we didn't create two protocols.
        assertEquals(nciID, newNciID);
        assertEquals(id, newId);

        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            // The update should have altered all fields except the Lead Org (by
            // design).
            checkTrialPersonOrgData(sp, "Sponsor Inc.", "Sponsor Inc.", nctID);
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, true);
            checkAdminScientificMarkedInLogEntry(nctID, nciID, session);
            checkInboxEntry(sp);

        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    @Test
    public final void testUpdateDoesNotCreateDuplicateStudyStatusPO8265()
            throws PAException, ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            // Change NCT identifier to be able to update this protocol with a
            // different ClinicalTrials.gov XML that
            // actually belongs to a different trial.
            changeNCTNumber("NCT01440088", "NCT01861054");
            nctID = "NCT01861054";

            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            assertEquals(1, sp.getStudyOverallStatuses().size());
            StudyOverallStatus status = sp.getStudyOverallStatuses().iterator()
                    .next();
            final Timestamp newDate = new Timestamp(DateUtils.addYears(
                    new Date(), -10).getTime());
            status.setStatusDate(newDate);
            session.save(status);
            session.flush();
            session.clear();

            // Apply update on top of the existing record.
            String newNciID = serviceBean.importTrial(nctID);
            final long newId = getProtocolIdByNciId(newNciID, session);

            session.flush();
            session.clear();

            // Make sure we didn't create two protocols.
            assertEquals(id, newId);
            assertEquals(nciID, newNciID);

            sp = (InterventionalStudyProtocol) session.get(
                    InterventionalStudyProtocol.class, id);
            assertEquals(1, sp.getStudyOverallStatuses().size());
            assertEquals(StudyStatusCode.ACTIVE, sp.getStudyOverallStatuses()
                    .iterator().next().getStatusCode());
            assertTrue(DateUtils.isSameDay(sp.getStudyOverallStatuses()
                    .iterator().next().getStatusDate(), newDate));
        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    @Test
    public final void testPO6835_LeadOrgIdChange() throws PAException,
            ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);

        // Change NCT identifier to be able to update this protocol with a
        // different ClinicalTrials.gov XML that
        // actually belongs to a different trial.
        changeNCTNumber("NCT01440088", "NCT01861055");

        nctID = "NCT01861055";

        // Apply update on top of the existing record.
        String newNciID = serviceBean.importTrial(nctID);
        final long newId = getProtocolIdByNciId(newNciID, session);

        session.flush();
        session.clear();

        // Make sure we didn't create two protocols.
        assertEquals(nciID, newNciID);
        assertEquals(id, newId);

        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            // New Lead Org ID should be in place.
            assertEquals("REP0211",
                    getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                            .getLocalStudyProtocolIdentifier());

            // Old Lead Org ID should have been recorded as Other Identifier.
            ensureOtherIdentifierExists(sp, "REP0210");

        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    private void ensureOtherIdentifierExists(InterventionalStudyProtocol sp,
            String otherID) {
        for (Ii ii : sp.getOtherIdentifiers()) {
            if (StringUtils.equals(ii.getExtension(), otherID)
                    && StringUtils.equals(ii.getIdentifierName(),
                            IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME)) {
                return;
            }
        }
        Assert.fail();
    }

    private void checkAdminScientificMarkedInLogEntry(String nctID,
            String nciID, Session session) {
        CTGovImportLog log = findLogEntry(nciID, session);
        assertTrue(log.getAdmin());
        assertTrue(log.getScientific());

    }

    private void checkInboxEntry(InterventionalStudyProtocol sp) {
        StudyInbox inbox = sp.getStudyInbox().iterator().next();
        assertEquals(
                "Trial has been updated from ClinicalTrials.gov\rDetailed Description changed\rEligibility Criteria changed",
                inbox.getComments());
        assertNull(inbox.getCloseDate());
        assertTrue(inbox.getScientific());
        assertTrue(inbox.getAdmin());
        assertNull(inbox.getAdminCloseDate());
        assertNull(inbox.getScientificCloseDate());
        assertEquals(StudyInboxTypeCode.UPDATE, inbox.getTypeCode());

    }

    /**
     * PO-6482: For existing trials in CTRP where the submitter is NOT
     * Clinicaltrials.gov, DO NOT UPDATE these with the data from
     * Clinicaltrials.gov XML - Sponsor - Summary4 Funding Sponsor - Lead Org
     * 
     * @throws PAException
     * @throws ParseException
     */
    @Test
    public final void testUpdateNonCtgovSubmittedTrial() throws PAException,
            ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);

        // Change NCT identifier to be able to update this protocol with a
        // different ClinicalTrials.gov XML that
        // actually belongs to a different trial.
        changeNCTNumber("NCT01440088", "NCT01861054");

        // Change submitter to a different user other than ctgovimportuser in
        // order to trigger the behavior
        // described in PO-6482.
        changeSubmitter(id, notCtgovimportUser);

        // Switch to the mode described in PO-6482. Import orgs, skip persons.
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "false";

        nctID = "NCT01861054";
        // Apply update on top of the existing record.
        String newNciID = serviceBean.importTrial(nctID);
        final long newId = getProtocolIdByNciId(newNciID, session);

        session.flush();
        session.clear();

        // Make sure we didn't create two protocols.
        assertEquals(nciID, newNciID);
        assertEquals(id, newId);

        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            // The update should have altered all fields except the Lead Org (by
            // design).
            checkTrialOrgData(sp, "Sponsor Inc.", "Threshold Pharmaceuticals",
                    nctID);

            // responsible party should have remained the same: Sponsor
            StudySite rp = getStudySite(sp,
                    StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR);
            assertNotNull(rp);

            // Other data come from update.
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, true);
            checkInboxEntry(sp);

        } finally {
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    private void changeSubmitter(long id, User u) {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update study_protocol set user_last_created_id="
                        + u.getUserId()).executeUpdate();
        session.flush();

    }

    private void changeNCTNumber(String from, String to) {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update study_site set local_sp_indentifier='" + to
                        + "' where local_sp_indentifier='" + from + "'")
                .executeUpdate();
        session.flush();
        session.clear();
    }

    @Test
    public final void testImportTrialNoPoData() throws PAException,
            ParseException {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update pa_properties set value='false' where name='ctgov.sync.import_orgs'")
                .executeUpdate();
        session.createSQLQuery(
                "update pa_properties set value='false' where name='ctgov.sync.import_persons'")
                .executeUpdate();
        session.flush();
        session.clear();
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            checkNCT01861054EmptyPersonOrgData(sp);
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, false);

        } finally {
            restoreImportOrgsPersonFlags(session);
            deactivateTrial(session, id);
        }
    }

    /**
     * @param session
     * @throws HibernateException
     */
    private void restoreImportOrgsPersonFlags(final Session session)
            throws HibernateException {
        session.createSQLQuery(
                "update pa_properties set value='true' where name='ctgov.sync.import_orgs'")
                .executeUpdate();
        session.createSQLQuery(
                "update pa_properties set value='true' where name='ctgov.sync.import_persons'")
                .executeUpdate();
        session.flush();
        session.clear();
    }

    @Test
    public final void testImportTrialOrgsNoPersonsData() throws PAException,
            ParseException {

        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update pa_properties set value='true' where name='ctgov.sync.import_orgs'")
                .executeUpdate();
        session.createSQLQuery(
                "update pa_properties set value='false' where name='ctgov.sync.import_persons'")
                .executeUpdate();
        session.flush();
        session.clear();

        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            checkTrialOrgData(sp, "Sponsor Inc.", "Sponsor Inc.", nctID);
            checkTrialEmptyPersonData(sp);
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, false);

        } finally {
            restoreImportOrgsPersonFlags(session);
            deactivateTrial(session, id);
        }
    }

    @Test
    public final void testImportTrialPersonsNoOrgData() throws PAException,
            ParseException {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update pa_properties set value='false' where name='ctgov.sync.import_orgs'")
                .executeUpdate();
        session.createSQLQuery(
                "update pa_properties set value='true' where name='ctgov.sync.import_persons'")
                .executeUpdate();
        session.flush();
        session.clear();

        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);

            checkNCT01861054PersonData(sp);
            checkNCT01861054EmptyOrgData(sp);
            checkNCT01861054OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, false);

        } finally {
            restoreImportOrgsPersonFlags(session);
            deactivateTrial(session, id);
        }
    }

    /**
     * @param session
     * @param id
     * @throws HibernateException
     */
    private void deactivateTrial(final Session session, final long id)
            throws HibernateException {
        session.createSQLQuery(
                "update study_protocol set status_code='INACTIVE' where identifier="
                        + id).executeUpdate();
        session.flush();
    }

    /**
     * @param nctID
     * @param nciID
     * @param session
     * @throws HibernateException
     */
    private void checkSuccessfulImportLogEntry(final String nctID,
            String nciID, final Session session, boolean hasStudyInbox)
            throws HibernateException {
        CTGovImportLog log = findLogEntry(nciID, session);
        assertEquals(nctID, log.getNctID());
        assertEquals("Success", log.getImportStatus());
        if (hasStudyInbox) {
            assertNotNull(log.getStudyInbox());
        }
    }

    /**
     * @param nciID
     * @param session
     * @return
     * @throws HibernateException
     */
    private CTGovImportLog findLogEntry(String nciID, final Session session)
            throws HibernateException {
        CTGovImportLog log = (CTGovImportLog) session
                .createQuery(
                        " from CTGovImportLog log where log.nciID='" + nciID
                                + "' order by log.dateCreated desc").list()
                .get(0);
        return log;
    }

    /**
     * @param nciID
     * @param session
     * @return
     * @throws HibernateException
     */
    private long getProtocolIdByNciId(String nciID, final Session session)
            throws HibernateException {
        return ((BigInteger) session.createSQLQuery(
                "select study_protocol_id from study_otheridentifiers where extension='"
                        + nciID + "'").uniqueResult()).longValue();
    }

    /**
     * @param session
     * @param spID
     * @param sp
     * @throws PAException
     * @throws NumberFormatException
     * @throws ParseException
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private void checkNCT01861054OtherData(final Session session,
            InterventionalStudyProtocol sp) throws PAException,
            NumberFormatException, ParseException, HibernateException {
        final long id = sp.getId();
        Ii ii = IiConverter.convertToStudyProtocolIi(id);
        assertEquals("REP0210",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getLocalStudyProtocolIdentifier());
        assertEquals(
                "Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients",
                sp.getPublicTitle());
        assertTrue(sp.getProprietaryTrialIndicator());
        assertEquals(
                "A Single Arm, Preoperative, Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients Who Are Candidates for Surgery",
                sp.getOfficialTitle());
        assertTrue(sp.getDataMonitoringCommitteeAppointedIndicator());

        checkRegulatoryInformation(ii, "NCT01861054");

        assertEquals(
                "cancer investigating use of reparixin as single agent in the time period between clinical",
                sp.getPublicDescription());
        assertEquals(
                "According to the cancer stem cell (CSC) model, tumors are organized in a cellular hierarchy",
                sp.getScientificDescription());
        assertEquals(StudyStatusCode.ACTIVE, sp.getStudyOverallStatuses()
                .iterator().next().getStatusCode());
        assertEquals(DateUtils.parseDate("02/01/2013",
                new String[] { "MM/dd/yyyy" }), sp.getDates().getStartDate());
        assertEquals(DateUtils.parseDate("02/01/2014",
                new String[] { "MM/dd/yyyy" }), sp.getDates()
                .getPrimaryCompletionDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED, sp.getDates()
                .getPrimaryCompletionDateTypeCode());
        assertEquals(PhaseCode.II, sp.getPhaseCode());
        assertEquals(StudyClassificationCode.SAFETY_OR_EFFICACY,
                sp.getStudyClassificationCode());
        assertEquals(DesignConfigurationCode.SINGLE_GROUP,
                sp.getDesignConfigurationCode());

        assertEquals(BlindingSchemaCode.DOUBLE_BLIND,
                sp.getBlindingSchemaCode());
        assertEquals(BlindingRoleCode.CAREGIVER,
                sp.getBlindingRoleCodeCaregiver());
        assertEquals(BlindingRoleCode.SUBJECT, sp.getBlindingRoleCodeSubject());
        assertNull(sp.getBlindingRoleCodeInvestigator());
        assertNull(sp.getBlindingRoleCodeOutcome());

        assertEquals("TREATMENT", sp.getPrimaryPurposeCode().getName());

        List<StudyOutcomeMeasure> outcomes = session.createQuery(
                "from StudyOutcomeMeasure so where so.studyProtocol.id=" + id
                        + " order by so.id").list();
        assertEquals(18, outcomes.size());
        assertEquals(
                "Markers of Cancer Stem Cells (CSCs) in the primary tumor and the tumoral microenvironment",
                outcomes.get(0).getName());
        assertEquals("Change in markers from baseline at day 21",
                outcomes.get(0).getTimeFrame());
        assertEquals(false, outcomes.get(0).getSafetyIndicator());
        assertEquals(
                "CSCs will be measured in tissue samples by techniques that may include: ALDEFLUOR assay and assessment of CD44/CD24 by flow cytometry or examination of RNA transcripts by RT-PCR, aldehyde dehydrogenase 1 (ALDH1), CD44/CD24 and epithelial mesenchymal markers (Snail, Twist, Notch) by immunohistochemistry (IHC).",
                outcomes.get(0).getDescription());
        assertEquals(true, outcomes.get(0).getPrimaryIndicator());

        assertEquals(1, sp.getNumberOfInterventionGroups().intValue());
        assertEquals(40, sp.getMinimumTargetAccrualNumber().intValue());

        assertEquals(1, sp.getArms().size());
        assertEquals("Treated patients", sp.getArms().get(0).getName());
        assertEquals(ArmTypeCode.EXPERIMENTAL, sp.getArms().get(0)
                .getTypeCode());
        assertEquals(
                "Patients eligible will be treated with Reparixin as add-in monotherapy",
                sp.getArms().get(0).getDescriptionText());
        assertTrue(sp.getArms().get(0).getInterventions().isEmpty());

        PlannedEligibilityCriterion gender = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='GENDER' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(EligibleGenderCode.FEMALE, gender.getEligibleGenderCode());

        PlannedEligibilityCriterion age = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='AGE' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(18, age.getMinValue().intValue());
        assertEquals(999, age.getMaxValue().intValue());
        assertFalse(sp.getAcceptHealthyVolunteersIndicator());

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(12, exclList.size());
        assertEquals("Male.", exclList.get(0).getTextDescription());
        verifyDisplayOrder(exclList);

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(11, inclList.size());
        assertEquals("Female aged > 18 years.", inclList.get(0)
                .getTextDescription());
        verifyDisplayOrder(inclList);

        assertEquals(
                "Cancer Stem Cells, Novel targeted therapy, CXCR1/2 Inhibitors",
                sp.getKeywordText());
        assertTrue(sp.getFdaRegulatedIndicator());
        assertFalse(sp.getExpandedAccessIndicator());
    }

    /**
     * @param session
     * @param spID
     * @param sp
     * @throws PAException
     * @throws NumberFormatException
     * @throws ParseException
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private void checkNCT02158936OtherData(final Session session,
            InterventionalStudyProtocol sp) throws PAException,
            NumberFormatException, ParseException, HibernateException {
        final long id = sp.getId();
        Ii ii = IiConverter.convertToStudyProtocolIi(id);
        assertEquals("112121",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getLocalStudyProtocolIdentifier());
        assertEquals(
                "A Study of Eltrombopag or Placebo in Combination With Azacitidine in Subjects With International Prognostic Scoring System (IPSS) Intermediate-1, Intermediate-2 or High-risk Myelodysplastic Syndromes (MDS)",
                sp.getPublicTitle());
        assertTrue(sp.getProprietaryTrialIndicator());
        assertEquals(
                "A Randomized, Double-blind, Placebo-controlled, Phase III, Multi-centre Study of Eltrombopag or Placebo in Combination With Azacitidine in Subjects With IPSS Intermediate-1, Intermediate 2 and High-risk Myelodysplastic Syndromes (MDS) SUPPORT: A StUdy of eltromboPag in myelodysPlastic SyndrOmes Receiving azaciTidine",
                sp.getOfficialTitle());
        assertTrue(sp.getDataMonitoringCommitteeAppointedIndicator());

        checkRegulatoryInformation(ii, NCT02158936);

        assertTrue(sp.getPublicDescription().contains(
                "Eltrombopag olamine (SB-497115-GR) is an orally bioavailable, "
                        + "small molecule thrombopoietin"));
        assertEquals(StudyStatusCode.IN_REVIEW, sp.getStudyOverallStatuses()
                .iterator().next().getStatusCode());
        assertEquals(DateUtils.parseDate("06/01/2014",
                new String[] { "MM/dd/yyyy" }), sp.getDates().getStartDate());
        assertEquals(DateUtils.parseDate("12/01/2017",
                new String[] { "MM/dd/yyyy" }), sp.getDates()
                .getPrimaryCompletionDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED, sp.getDates()
                .getPrimaryCompletionDateTypeCode());
        assertEquals(PhaseCode.III, sp.getPhaseCode());
        assertEquals(StudyClassificationCode.SAFETY_OR_EFFICACY,
                sp.getStudyClassificationCode());
        assertEquals(DesignConfigurationCode.PARALLEL,
                sp.getDesignConfigurationCode());

        assertEquals(BlindingSchemaCode.DOUBLE_BLIND,
                sp.getBlindingSchemaCode());
        assertNull(sp.getBlindingRoleCodeCaregiver());
        assertEquals(BlindingRoleCode.SUBJECT, sp.getBlindingRoleCodeSubject());
        assertEquals(BlindingRoleCode.INVESTIGATOR,
                sp.getBlindingRoleCodeInvestigator());
        assertNull(sp.getBlindingRoleCodeOutcome());

        assertEquals("TREATMENT", sp.getPrimaryPurposeCode().getName());

        List<StudyOutcomeMeasure> outcomes = session.createQuery(
                "from StudyOutcomeMeasure so where so.studyProtocol.id=" + id
                        + " order by so.id").list();
        assertEquals(21, outcomes.size());
        assertEquals(
                "Cycle 1-4 platelet transfusion independence (The proportion of subjects who are platelet transfusion free during Cycles 1-4 of azacitidine therapy)",
                outcomes.get(0).getName());
        assertEquals("4 cycles (Cycle = 28 days)", outcomes.get(0)
                .getTimeFrame());
        assertEquals(false, outcomes.get(0).getSafetyIndicator());
        assertEquals(
                "A subject is defined as being platelet transfusion independent if they receive no platelet transfusions within the first 4 cycles of treatment with azacitidine",
                outcomes.get(0).getDescription());
        assertEquals(true, outcomes.get(0).getPrimaryIndicator());

        assertEquals(2, sp.getNumberOfInterventionGroups().intValue());
        assertEquals(350, sp.getMinimumTargetAccrualNumber().intValue());

        assertEquals(2, sp.getArms().size());
        assertEquals("Eltrombopag", sp.getArms().get(0).getName());
        assertEquals(ArmTypeCode.EXPERIMENTAL, sp.getArms().get(0)
                .getTypeCode());
        assertEquals(
                "Eligible subject will receive a starting dose of eltrombopag of 200 milligrams (mg) (100 mg for "
                        + "subjects of East Asian heritage).  Dose modifications of eltrombopag will be permitted by 100 mg "
                        + "increments (50 mg increments for East Asians) to a lowest dose of 100 mg (50 mg for East Asian "
                        + "heritage) or a maximum dose of 300 mg (150 mg for East Asian heritage) in order to maintain "
                        + "platelet counts at a safe and effective level (i.e. a level sufficient to avoid platelet "
                        + "transfusions and bleeding events). Subjects will receive azacitidine 75 mg/meter^2 subcutaneously "
                        + "once daily for 7 days (+/- 3 day treatment window permitted) every 28 days, for at least 6 cycles "
                        + "if tolerated and until they are no longer receiving benefit (defined as at least stable disease"
                        + " per the investigator's assessment) or until disease progression, death, or unacceptable "
                        + "toxicity/adverse event. The subject may receive eltrombopag daily for the full 28 days each "
                        + "cycle for as long as the subject is receiving azacitidine",
                sp.getArms().get(0).getDescriptionText());
        assertTrue(sp.getArms().get(0).getInterventions().isEmpty());

        PlannedEligibilityCriterion gender = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='GENDER' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(EligibleGenderCode.BOTH, gender.getEligibleGenderCode());

        PlannedEligibilityCriterion age = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='AGE' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(18, age.getMinValue().intValue());
        assertEquals(999, age.getMaxValue().intValue());
        assertFalse(sp.getAcceptHealthyVolunteersIndicator());

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(11, exclList.size());
        assertEquals(
                "Previous treatment with hypomethylating agent or induction chemotherapy for MDS",
                exclList.get(0).getTextDescription());
        verifyDisplayOrder(exclList);

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(12, inclList.size());
        assertEquals("Age >=18 years", inclList.get(0).getTextDescription());
        verifyDisplayOrder(inclList);

        assertEquals(
                "Eltrombopag, azacitidine, thrombocytopenia, myelodysplastic syndromes (MDS), thrombopoietin",
                sp.getKeywordText());
        assertFalse(sp.getFdaRegulatedIndicator());
        assertFalse(sp.getExpandedAccessIndicator());
    }

    // PO-6570
    private void verifyDisplayOrder(final List<PlannedEligibilityCriterion> list) {
        CollectionUtils.forAllDo(list, new Closure() {
            @Override
            public void execute(Object obj) {
                PlannedEligibilityCriterion pec = (PlannedEligibilityCriterion) obj;
                Assert.assertTrue(pec.getDisplayOrder() != null);
            }
        });
    }

    /**
     * @param spID
     * @throws PAException
     * @throws NumberFormatException
     */
    private void checkRegulatoryInformation(Ii spID, String nctID)
            throws PAException, NumberFormatException {

        if (nctID.equals(NCT02158936)) {
            assertEquals(
                    "IDMC",
                    ((RegulatoryInformationBean) getEjbBean(RegulatoryInformationBean.class))
                            .get(Long
                                    .parseLong(((StudyRegulatoryAuthorityBeanLocal) getEjbBean(StudyRegulatoryAuthorityBeanLocal.class))
                                            .getCurrentByStudyProtocol(spID)
                                            .getRegulatoryAuthorityIdentifier()
                                            .getExtension()))
                            .getAuthorityName());
        } else {
            assertEquals(
                    "Food and Drug Administration",
                    ((RegulatoryInformationBean) getEjbBean(RegulatoryInformationBean.class))
                            .get(Long
                                    .parseLong(((StudyRegulatoryAuthorityBeanLocal) getEjbBean(StudyRegulatoryAuthorityBeanLocal.class))
                                            .getCurrentByStudyProtocol(spID)
                                            .getRegulatoryAuthorityIdentifier()
                                            .getExtension()))
                            .getAuthorityName());

        }

        // Check only one record in study_regulatory_authority table.
        assertEquals(
                1,
                ((StudyRegulatoryAuthorityBeanLocal) getEjbBean(StudyRegulatoryAuthorityBeanLocal.class))
                        .getByStudyProtocol(spID).size());
    }

    /**
     * @param sp
     */
    private void checkTrialPersonOrgData(InterventionalStudyProtocol sp,
            String leadOrgName, String sponsorName, String nctID) {
        checkTrialOrgData(sp, leadOrgName, sponsorName, nctID);
        checkNCT01861054PersonData(sp);
        checkNCT01861054RespPartyData(sp);
    }

    /**
     * @param sp
     */
    private void checkNCT01861054RespPartyData(InterventionalStudyProtocol sp) {
        StudyContact rp = getStudyContact(
                sp,
                StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        assertEquals("Goldstein", rp.getClinicalResearchStaff().getPerson()
                .getLastName());
        assertEquals("Lori", rp.getClinicalResearchStaff().getPerson()
                .getFirstName());
        assertEquals("Associate professor of pediatrics", rp.getTitle());
        assertEquals("Children's Hospital Boston", rp
                .getClinicalResearchStaff().getOrganization().getName());
    }

    /**
     * @param sp
     */
    private void checkNCT01861054PersonData(InterventionalStudyProtocol sp) {
        StudyContact pi = getStudyContact(sp,
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        assertEquals("Goldstein", pi.getClinicalResearchStaff().getPerson()
                .getLastName());
        assertEquals("Lori", pi.getClinicalResearchStaff().getPerson()
                .getFirstName());
        assertEquals("J", pi.getClinicalResearchStaff().getPerson()
                .getMiddleName());

        StudyContact cc = getStudyContact(sp,
                StudyContactRoleCode.CENTRAL_CONTACT);
        assertEquals("Ruffini", cc.getClinicalResearchStaff().getPerson()
                .getLastName());
        assertEquals("Pieradelchi", cc.getClinicalResearchStaff().getPerson()
                .getFirstName());
    }

    /**
     * @param sp
     */
    private void checkTrialOrgData(InterventionalStudyProtocol sp,
            String leadOrgName, String sponsorName, String nctID) {
        if (nctID.equals(NCT02158936)) {
            assertEquals(leadOrgName,
                    getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                            .getResearchOrganization().getOrganization()
                            .getName());

            assertNull(getStudySite(sp, StudySiteFunctionalCode.LABORATORY));

        } else {
            assertEquals(leadOrgName,
                    getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                            .getResearchOrganization().getOrganization()
                            .getName());
            assertEquals("National Institutes of Health (NIH)",
                    getStudySite(sp, StudySiteFunctionalCode.LABORATORY)
                            .getResearchOrganization().getOrganization()
                            .getName());
        }

        assertEquals(sponsorName,
                getStudySite(sp, StudySiteFunctionalCode.SPONSOR)
                        .getResearchOrganization().getOrganization().getName());

        StudyResourcing summary4 = sp.getStudyResourcings().get(0);
        assertTrue(summary4.getSummary4ReportedResourceIndicator());
        assertEquals(SummaryFourFundingCategoryCode.INDUSTRIAL,
                summary4.getTypeCode());
        assertEquals(
                sponsorName,
                ((Organization) PaHibernateUtil.getCurrentSession().get(
                        Organization.class,
                        new Long(summary4.getOrganizationIdentifier())))
                        .getName());
    }

    @Test
    public final void testAttemptUpdateCompleteTrial() throws PAException,
            ParseException {

        thrown.expect(PAException.class);
        thrown.expectMessage("Complete trials cannot be updated from ClinicalTrials.gov");

        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);
        long id = 0;

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        try {

            id = getProtocolIdByNciId(nciID, session);
            StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class,
                    id);
            sp.setProprietaryTrialIndicator(Boolean.FALSE);
            session.update(sp);
            session.flush();
            serviceBean.importTrial(nctID);
        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    @Test
    public final void testImportNCT00760500_PO_6462() throws PAException,
            ParseException {
        final String nctID = "NCT00760500";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        NonInterventionalStudyProtocol sp = (NonInterventionalStudyProtocol) session
                .get(NonInterventionalStudyProtocol.class, id);
        assertEquals(StudyModelCode.ECOLOGIC_OR_COMMUNITY_STUDIES,
                sp.getStudyModelCode());

    }
    
    @Test
    public final void testImportNCT00338442() throws PAException, ParseException {
         final String nctID = "NCT00338442";
         String nciID =serviceBean.importTrial(nctID);
         assertTrue(StringUtils.isNotEmpty(nciID));
         final Session session = PaHibernateUtil.getCurrentSession();
         session.flush();
         session.clear();
         final long id = getProtocolIdByNciId(nciID, session);
       
         
         try {
             InterventionalStudyProtocol spin  = (InterventionalStudyProtocol) session
                     .get(InterventionalStudyProtocol.class, id);

            assertTrue(spin.getExpandedAccessIndicator());

         } finally {
             deactivateTrial(session, id);
         }
    }
    @Test
    public final void testImportObservationalPatientRegistry()
            throws PAException, ParseException {
        final String nctID = "NCT01963949";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class, id);
        assertTrue(sp instanceof NonInterventionalStudyProtocol);

    }

    private void checkNCT01861054EmptyPersonOrgData(
            InterventionalStudyProtocol sp) {
        checkNCT01861054EmptyOrgData(sp);
        checkTrialEmptyPersonData(sp);
    }

    /**
     * @param sp
     */
    private void checkTrialEmptyPersonData(InterventionalStudyProtocol sp) {
        StudyContact pi = getStudyContact(sp,
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        assertNull(pi);

        StudyContact rp = getStudyContact(
                sp,
                StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        assertNull(rp);

        StudyContact cc = getStudyContact(sp,
                StudyContactRoleCode.CENTRAL_CONTACT);
        assertNull(cc);
    }

    /**
     * @param sp
     */
    private void checkNCT01861054EmptyOrgData(InterventionalStudyProtocol sp) {
        assertEquals("CTRO Replace This Field",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getResearchOrganization().getOrganization().getName());
        assertNull(getStudySite(sp, StudySiteFunctionalCode.SPONSOR));
        assertNull(getStudySite(sp, StudySiteFunctionalCode.LABORATORY));
        assertTrue(sp.getStudyResourcings().isEmpty());

    }

    private StudyContact getStudyContact(InterventionalStudyProtocol sp,
            StudyContactRoleCode code) {
        for (StudyContact ss : sp.getStudyContacts()) {
            if (code.equals(ss.getRoleCode())) {
                return ss;
            }
        }
        return null;
    }

    private StudySite getStudySite(InterventionalStudyProtocol sp,
            StudySiteFunctionalCode code) {
        for (StudySite ss : sp.getStudySites()) {
            if (code.equals(ss.getFunctionalCode())) {
                return ss;
            }
        }
        return null;
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getLogEntries(java.util.Date, java.util.Date)}
     * .
     * 
     * @throws ParseException
     * @throws PAException
     */
    @Test
    public final void testGetLogEntries() throws ParseException, PAException {
        final Session session = PaHibernateUtil.getCurrentSession();

        CTGovImportLog log1 = new CTGovImportLog();
        log1.setNciID("NCI1");
        log1.setNctID("NCT1");
        log1.setTitle("Title : Trial 1");
        log1.setAction("New Trial");
        log1.setImportStatus("Success");
        log1.setUserCreated("User1");
        log1.setDateCreated(DateUtils.parseDate("08/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log1);

        CTGovImportLog log2 = new CTGovImportLog();
        log2.setNciID("NCI2");
        log2.setNctID("NCT2");
        log2.setTitle("Title : Trial 2");
        log2.setAction("Update");
        log2.setImportStatus("Failure : Exception");
        log2.setUserCreated("User1");
        log2.setDateCreated(DateUtils.parseDate("07/15/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log2);
        session.flush();

        CTGovImportLog log3 = new CTGovImportLog();
        log3.setNciID("NCI3");
        log3.setNctID("NCT3");
        log3.setTitle("Title : Trial 3");
        log3.setAction("Update");
        log3.setImportStatus("Success");
        log3.setUserCreated("User2");
        log3.setDateCreated(DateUtils.parseDate("07/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log3);
        session.flush();

        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);

        StudyInbox inbox1 = createStudyInbox(sp);
        inbox1.setAdmin(true);
        session.save(inbox1);
        session.flush();

        StudyInbox inbox2 = createStudyInbox(sp);
        inbox2.setScientific(true);
        session.save(inbox2);
        session.flush();

        StudyInbox inbox3 = createStudyInbox(sp);
        inbox3.setAdmin(true);
        inbox3.setScientific(true);
        session.save(inbox3);
        session.flush();

        StudyInbox inbox4 = createStudyInbox(sp);
        inbox4.setAdmin(true);
        inbox4.setAdminCloseDate(new Timestamp(new Date().getTime()));
        session.save(inbox4);
        session.flush();

        StudyInbox inbox5 = createStudyInbox(sp);
        inbox5.setScientific(true);
        inbox5.setScientificCloseDate(new Timestamp(new Date().getTime()));
        session.save(inbox5);
        session.flush();

        StudyInbox inbox6 = createStudyInbox(sp);
        inbox6.setAdmin(true);
        inbox6.setScientific(true);
        inbox6.setAdminCloseDate(new Timestamp(new Date().getTime()));
        inbox6.setScientificCloseDate(new Timestamp(new Date().getTime()));
        session.save(inbox6);
        session.flush();

        CTGovImportLog log4 = new CTGovImportLog();
        log4.setNciID("NCI4");
        log4.setNctID("NCT4");
        log4.setTitle("Title : Trial 4");
        log4.setAction("Update");
        log4.setImportStatus("Success");
        log4.setUserCreated("User1");
        log4.setDateCreated(DateUtils.parseDate("06/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log4.setStudyInbox(inbox1);
        session.save(log4);
        session.flush();

        CTGovImportLog log5 = new CTGovImportLog();
        log5.setNciID("NCI5");
        log5.setNctID("NCT5");
        log5.setTitle("Title : Trial 5");
        log5.setAction("Update");
        log5.setImportStatus("Success");
        log5.setUserCreated("User1");
        log5.setDateCreated(DateUtils.parseDate("05/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log5.setStudyInbox(inbox2);
        session.save(log5);
        session.flush();

        CTGovImportLog log6 = new CTGovImportLog();
        log6.setNciID("NCI6");
        log6.setNctID("NCT6");
        log6.setTitle("Title : Trial 6");
        log6.setAction("Update");
        log6.setImportStatus("Success");
        log6.setUserCreated("User1");
        log6.setDateCreated(DateUtils.parseDate("04/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log6.setStudyInbox(inbox3);
        session.save(log6);
        session.flush();

        CTGovImportLog log7 = new CTGovImportLog();
        log7.setNciID("NCI7");
        log7.setNctID("NCT7");
        log7.setTitle("Title : Trial 7");
        log7.setAction("Update");
        log7.setImportStatus("Success");
        log7.setUserCreated("User1");
        log7.setDateCreated(DateUtils.parseDate("03/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log7.setStudyInbox(inbox4);
        session.save(log7);
        session.flush();

        CTGovImportLog log8 = new CTGovImportLog();
        log8.setNciID("NCI8");
        log8.setNctID("NCT8");
        log8.setTitle("Title : Trial 8");
        log8.setAction("Update");
        log8.setImportStatus("Success");
        log8.setUserCreated("User1");
        log8.setDateCreated(DateUtils.parseDate("02/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log8.setStudyInbox(inbox5);
        session.save(log8);
        session.flush();

        CTGovImportLog log9 = new CTGovImportLog();
        log9.setNciID("NCI9");
        log9.setNctID("NCT9");
        log9.setTitle("Title : Trial 9");
        log9.setAction("Update");
        log9.setImportStatus("Success");
        log9.setUserCreated("User1");
        log9.setDateCreated(DateUtils.parseDate("01/01/2013",
                new String[] { "MM/dd/yyyy" }));
        log9.setStudyInbox(inbox6);
        session.save(log9);
        session.flush();

        // exercise start and end dates are specified
        CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setOnOrAfter(new Date(0));
        searchCriteria.setOnOrBefore(new Date(System.currentTimeMillis()));
        List<CTGovImportLog> entries = serviceBean
                .getLogEntries(searchCriteria);
        assertEquals(9, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        assertEquals("NCI3", entries.get(2).getNciID());
        assertEquals("NCI4", entries.get(3).getNciID());
        assertEquals("NCI5", entries.get(4).getNciID());
        assertEquals("NCI6", entries.get(5).getNciID());
        assertEquals("NCI7", entries.get(6).getNciID());
        assertEquals("NCI8", entries.get(7).getNciID());
        assertEquals("NCI9", entries.get(8).getNciID());

        // exercise start and end dates are specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setOnOrAfter(DateUtils.parseDate("07/30/2013",
                new String[] { "MM/dd/yyyy" }));
        searchCriteria.setOnOrBefore(DateUtils.parseDate("08/01/2013",
                new String[] { "MM/dd/yyyy" }));
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());

        // exercise start date is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setOnOrAfter(DateUtils.parseDate("07/30/2013",
                new String[] { "MM/dd/yyyy" }));
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());

        // exercise end date is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setOnOrBefore(DateUtils.parseDate("07/30/2013",
                new String[] { "MM/dd/yyyy" }));
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(8, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());
        assertEquals("NCI4", entries.get(2).getNciID());
        assertEquals("NCI5", entries.get(3).getNciID());
        assertEquals("NCI6", entries.get(4).getNciID());
        assertEquals("NCI7", entries.get(5).getNciID());
        assertEquals("NCI8", entries.get(6).getNciID());
        assertEquals("NCI9", entries.get(7).getNciID());

        // exercise NCI identifier is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setNciIdentifier("NCI3");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI3", entries.get(0).getNciID());

        // exercise NCT identifier is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setNctIdentifier("NCT2");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCT2", entries.get(0).getNctID());

        // exercise title is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setOfficialTitle("Title :");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(9, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        assertEquals("NCI3", entries.get(2).getNciID());
        assertEquals("NCI4", entries.get(3).getNciID());
        assertEquals("NCI5", entries.get(4).getNciID());
        assertEquals("NCI6", entries.get(5).getNciID());
        assertEquals("NCI7", entries.get(6).getNciID());
        assertEquals("NCI8", entries.get(7).getNciID());
        assertEquals("NCI9", entries.get(8).getNciID());

        // exercise action is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setAction("New Trial");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());

        searchCriteria.setAction("Update");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(8, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());
        assertEquals("NCI4", entries.get(2).getNciID());
        assertEquals("NCI5", entries.get(3).getNciID());
        assertEquals("NCI6", entries.get(4).getNciID());
        assertEquals("NCI7", entries.get(5).getNciID());
        assertEquals("NCI8", entries.get(6).getNciID());
        assertEquals("NCI9", entries.get(7).getNciID());

        // exercise import status is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setImportStatus("Success");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(8, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());
        assertEquals("NCI4", entries.get(2).getNciID());
        assertEquals("NCI5", entries.get(3).getNciID());
        assertEquals("NCI6", entries.get(4).getNciID());
        assertEquals("NCI7", entries.get(5).getNciID());
        assertEquals("NCI8", entries.get(6).getNciID());
        assertEquals("NCI9", entries.get(7).getNciID());

        searchCriteria.setImportStatus("Failure");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());

        // exercise user is specified
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setUserCreated("User1");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(8, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        assertEquals("NCI4", entries.get(2).getNciID());
        assertEquals("NCI5", entries.get(3).getNciID());
        assertEquals("NCI6", entries.get(4).getNciID());
        assertEquals("NCI7", entries.get(5).getNciID());
        assertEquals("NCI8", entries.get(6).getNciID());
        assertEquals("NCI9", entries.get(7).getNciID());

        searchCriteria.setUserCreated("User2");
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(1, entries.size());
        assertEquals("NCI3", entries.get(0).getNciID());

        // exercise pending admin ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPendingAdminAcknowledgment(true);
        searchCriteria.setPendingScientificAcknowledgment(false);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

        // exercise pending sci ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPendingAdminAcknowledgment(false);
        searchCriteria.setPendingScientificAcknowledgment(true);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

        // exercise pending admin and sci ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPendingAdminAcknowledgment(true);
        searchCriteria.setPendingScientificAcknowledgment(true);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

        // exercise performed admin ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPerformedAdminAcknowledgment(true);
        searchCriteria.setPerformedScientificAcknowledgment(false);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

        // exercise performed sci ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPerformedAdminAcknowledgment(false);
        searchCriteria.setPerformedScientificAcknowledgment(true);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

        // exercise performed admin and sci ack
        searchCriteria = new CTGovImportLogSearchCriteria();
        searchCriteria.setPerformedAdminAcknowledgment(true);
        searchCriteria.setPerformedScientificAcknowledgment(true);
        entries = serviceBean.getLogEntries(searchCriteria);
        assertEquals(6, entries.size());

    }

    @Test
    public final void testWithheldTrialsCannotBeImported() throws PAException,
            ParseException {

        thrown.expect(PAException.class);
        thrown.expectMessage("Trials with status of 'Withheld' cannot be imported or updated in CTRP");

        final String nctID = "NCT01916083";
        serviceBean.importTrial(nctID);

    }

    @Test
    public final void testUpdateRejectedTrialFails() throws Exception {

        thrown.expect(PAException.class);
        thrown.expectMessage("Updates to rejected trials are not allowed");

        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);
        long id = 0;

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        try {
            id = getProtocolIdByNciId(nciID, session);

            StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class,
                    id);
            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusDateRangeLow(TestSchema.TODAY);
            dws.setCommentText("");
            dws.setUserLastUpdated(ctgovimportUser);
            dws.setDateLastUpdated(TestSchema.TODAY);
            dws.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
            session.save(dws);
            session.flush();

            serviceBean.importTrial(nctID);
        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }

    @Test
    public void isNctIdValidTest() {
        assertEquals(true, serviceBean.isNctIdValid("NCT1"));
        assertEquals(true, serviceBean.isNctIdValid("NCT12344444"));
        assertEquals(false, serviceBean.isNctIdValid("1NCT12344444"));
        assertEquals(false, serviceBean.isNctIdValid("N12344444"));
        assertEquals(false, serviceBean.isNctIdValid("NCT"));
    }

    @Test
    public final void testImportTrialNoCountryPresentInRegulatoryAuthority()
            throws PAException, ParseException {

        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update pa_properties set value='true' where name='ctgov.sync.import_orgs'")
                .executeUpdate();
        session.createSQLQuery(
                "update pa_properties set value='false' where name='ctgov.sync.import_persons'")
                .executeUpdate();
        session.flush();
        session.clear();

        final String nctID = NCT02158936;
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            checkTrialOrgData(sp, "GlaxoSmithKline", "GlaxoSmithKline", nctID);

            checkTrialEmptyPersonData(sp);
            checkNCT02158936OtherData(session, sp);
            checkSuccessfulImportLogEntry(nctID, nciID, session, false);

        } finally {
            restoreImportOrgsPersonFlags(session);
            deactivateTrial(session, id);
        }
    }

    private StudyInbox createStudyInbox(StudyProtocol sp) {
        StudyInbox inbox = new StudyInbox();
        inbox.setAdmin(false);
        inbox.setComments("comments");
        inbox.setDateLastCreated(new Date());
        inbox.setOpenDate(new Timestamp(new Date().getTime()));
        inbox.setScientific(false);
        inbox.setStudyProtocol(sp);
        inbox.setTypeCode(StudyInboxTypeCode.UPDATE);
        return inbox;
    }
}
