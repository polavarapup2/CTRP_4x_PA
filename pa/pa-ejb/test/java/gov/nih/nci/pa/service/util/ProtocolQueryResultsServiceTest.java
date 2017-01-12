package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ProtocolQueryResultsServiceTest {

    static final long ADMIN_USERID = 34;
    static final long ADMINISTRATED_ORG = 74;
    static final long MEMB_USERID = 77;

    // copied from service
    private static final int UPDATING_IDX = 5;
    private static final int SUBMISSION_NUMBER_IDX = 7;

    ProtocolQueryResultsServiceBean bean;
    DataAccessServiceLocal daMock;
    RegistryUserServiceLocal usrMock;

    RegistryUser admin;
    RegistryUser memb;

    BigInteger studyProtocolIdentifier = new BigInteger("14");
    String officialTitle = "title";
    Boolean proprietaryTrialIndicator = true;
    Date recordVerificationDate = new Date();
    Boolean ctgovXmlRequiredIndicator = true;
    Boolean updating = true;
    Date dateLastCreated = new Date();
    Integer submissionNumber = 1;
    String nciNumber = "NCI-2099-00001";
    String nctNumber = "nct gog 03";
    String leadOrgPoid = "123";
    String leadOrgName = "Duke Medical Center";
    String leadOrgSpIdentifier = "duk001";
    String currentDwfStatusCode = DocumentWorkflowStatusCode.SUBMITTED
            .getName();
    Date currentDwfStatusDate = new Date();
    String currentStudyOverallStatus = StudyStatusCode.ACTIVE.getName();
    String currentAdminMilestone = MilestoneCode.ADMINISTRATIVE_READY_FOR_QC
            .getName();
    String currentScientificMilestone = MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE
            .getName();
    String currentOtherMilestone = MilestoneCode.SUBMISSION_ACCEPTED.getName();
    Integer adminCheckoutIdentifier = 19;
    String adminCheckoutUser = "cyx";
    Integer scientificCheckoutIdentifiER = 21;
    String scientificCheckoutUser = "jkd";
    String studyPiFirstName = "John";
    String studyPiLastName = "Doe";
    String userLastCreatedLogin = "userlogin";
    String userLastCreatedFirst = "Jane";
    String userLastCreatedLast = "Smith";
    String dcpId = "DCPID";
    String ctepId = "CTEPID";
    Date amendmentDate = new Date();
    Date updatedDate = new Date();
    String phase = "0";
    String primaryPusrpose = "TREATMENT";
    Date startDate = new Date();
    String summary4fundingSponsor = "";
    String responsiblePartyOrganizationName = "";
    String responsiblePartyPIFirstName = "";
    String responsiblePartyPILastName = "";
    String sponsor = "NCI";
    Date primaryCompletionDate = new Date();
    String studyProtocolType = "InterventionalStudyProtocol";
    String studySubtypeCode = "OBSERVATIONAL";
   

    private String submitter_org_name;
    private Date current_admin_milestone_date;
    private Date current_scientific_milestone_date;
    private Date current_other_milestone_date;
    private Integer processing_priority;
    private String last_milestone;
    private Date last_milestone_date;
    private String active_milestone;
    private Date active_milestone_date;
    private String admin_checkout_csm_fname;
    private String admin_checkout_csm_lname;
    private String admin_checkout_reg_fname;
    private String admin_checkout_reg_lname;
    private String scientific_checkout_csm_fname;
    private String scientific_checkout_csm_lname;
    private String scientific_checkout_reg_fname;
    private String scientific_checkout_reg_lname;
    private String onhold_reason_code;
    private Date onhold_date;
    private Date offhold_date;
    private String cdr_id;
    private String ccr_id;
    private String amendment_number;
    private Date admin_checkout_date;
    private Date scientific_checkout_date;
    private String comments;
    private String onholdDescription;
    private String study_source;
    private String accrual_disease_code_system;
    private String prev_dws = "SUBMITTED";
    private String submitter_org = "1";
    private String onholdReasonCategory = "CTRP";   
    private Date overriddenExpectedAbstractionCompletionDate = new Date();
    private String overriddenExpectedAbstractionCompletionComments = "";

    Object[] qryResult = { studyProtocolIdentifier, officialTitle,
            proprietaryTrialIndicator, recordVerificationDate,
            ctgovXmlRequiredIndicator, updating, dateLastCreated,
            submissionNumber, nciNumber, nctNumber, leadOrgPoid, leadOrgName,
            leadOrgSpIdentifier, currentDwfStatusCode, currentDwfStatusDate,
            currentStudyOverallStatus, currentAdminMilestone,
            currentScientificMilestone, currentOtherMilestone,
            adminCheckoutIdentifier, adminCheckoutUser,
            scientificCheckoutIdentifiER, scientificCheckoutUser,
            studyPiFirstName, studyPiLastName, userLastCreatedLogin,
            userLastCreatedFirst, userLastCreatedLast, dcpId, ctepId,
            amendmentDate, updatedDate, phase, primaryPusrpose, startDate,
            summary4fundingSponsor, sponsor, responsiblePartyOrganizationName,
            responsiblePartyPIFirstName, responsiblePartyPILastName,
            userLastCreatedLogin, userLastCreatedFirst, userLastCreatedLast,
            primaryCompletionDate, studyProtocolType, studySubtypeCode,
            submitter_org_name, current_admin_milestone_date,
            current_scientific_milestone_date, current_other_milestone_date,
            processing_priority, last_milestone, last_milestone_date,
            active_milestone, active_milestone_date, admin_checkout_csm_fname,
            admin_checkout_csm_lname, admin_checkout_reg_fname,
            admin_checkout_reg_lname, scientific_checkout_csm_fname,
            scientific_checkout_csm_lname, scientific_checkout_reg_fname,
            scientific_checkout_reg_lname, onhold_reason_code, onhold_date,
            offhold_date, cdr_id, amendment_number, admin_checkout_date,
            scientific_checkout_date, comments, onholdDescription,
            study_source, ccr_id, accrual_disease_code_system, prev_dws,
            submitter_org, onholdReasonCategory,
            overriddenExpectedAbstractionCompletionDate,
            overriddenExpectedAbstractionCompletionComments, new Date(),new Date(),
            new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),
            new Date(),new Date(),new Date()};
    Object[] siteQryResult = { studyProtocolIdentifier,
            BigInteger.valueOf(MEMB_USERID) };

    @Before
    public void init() throws Exception {
        ProtocolQueryResultsServiceBean svc = new ProtocolQueryResultsServiceBean();
        daMock = mock(DataAccessServiceLocal.class);
        usrMock = mock(RegistryUserServiceLocal.class);
        svc.setDataAccessService(daMock);
        svc.setRegistryUserService(usrMock);
        bean = svc;

        // set up users
        admin = new RegistryUser();
        admin.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        admin.setId(ADMIN_USERID);
        when(usrMock.getUserById(ADMIN_USERID)).thenReturn(admin);
        when(usrMock.getPartialUserById(ADMIN_USERID)).thenReturn(admin);
        memb = new RegistryUser();
        memb.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        memb.setAffiliatedOrganizationId(1L);
        memb.setId(MEMB_USERID);
        when(usrMock.getUserById(MEMB_USERID)).thenReturn(memb);
        when(usrMock.getPartialUserById(MEMB_USERID)).thenReturn(memb);

        // set up owned study
        List<Object> ownedStudies = new ArrayList<Object>();
        ownedStudies.add(studyProtocolIdentifier);
        DAQuery queryMemb = new DAQuery();
        queryMemb.setSql(true);
        queryMemb
                .setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
        queryMemb.addParameter("userId", MEMB_USERID);
        when(daMock.findByQuery(queryMemb)).thenReturn(ownedStudies);

        // set up main query
        DAQuery qryMain = new DAQuery();
        qryMain.setSql(true);
        qryMain.setText(ProtocolQueryResultsServiceBean.QRY_STRING);
        Set<Long> ids = new HashSet<Long>();
        ids.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        qryMain.setParameters(params);
        List<Object> result = new ArrayList<Object>();
        result.add(qryResult);
        when(daMock.findByQuery(qryMain)).thenReturn(result);

        // set up subordinate query
        DAQuery qryStudyId = new DAQuery();
        qryStudyId.setSql(true);
        qryStudyId.setText(ProtocolQueryResultsServiceBean.STUDY_ID_QRY_STRING);
        params = new HashMap<String, Object>();
        params.put("orgId", 1L);
        qryStudyId.setParameters(params);
        result = new ArrayList<Object>();
        result.add(siteQryResult);
        when(daMock.findByQuery(qryStudyId)).thenReturn(result);

        // set up other Identifier
        DAQuery qryMain1 = new DAQuery();
        qryMain1.setSql(true);
        qryMain1.setText(ProtocolQueryResultsServiceBean.OTHER_IDENTIFIERS_QRY_STRING);
        Set<Long> ids1 = new HashSet<Long>();
        ids1.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("ids", ids1);
        qryMain1.setParameters(params1);
        List<Object> result1 = new ArrayList<Object>();
        result1.add(new Object[] { studyProtocolIdentifier, "OTHER_ID",
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME });
        when(daMock.findByQuery(qryMain1)).thenReturn(result1);

        // fetch last updated date
        DAQuery qryMain2 = new DAQuery();
        qryMain2.setSql(true);
        qryMain2.setText(ProtocolQueryResultsServiceBean.LAST_UPDATED_DATE);
        Set<Long> ids2 = new HashSet<Long>();
        ids1.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("ids", ids2);
        qryMain2.setParameters(params2);
        List<Object> result2 = new ArrayList<Object>();
        result2.add(new Object[] { studyProtocolIdentifier, "Test", "Test",
                "Test", new Date() });
        when(daMock.findByQuery(qryMain1)).thenReturn(result2);

        // populate study alternate titles
        DAQuery qryMain3 = new DAQuery();
        qryMain3.setSql(true);
        qryMain3.setText(ProtocolQueryResultsServiceBean.STUDY_ALTERNATE_TITLE_QRY_STRING);
        Set<Long> ids3 = new HashSet<Long>();
        ids3.add(studyProtocolIdentifier.longValue());
        Map<String, Object> params3 = new HashMap<String, Object>();
        params3.put("ids", ids3);
        qryMain3.setParameters(params3);
        List<Object> result3 = new ArrayList<Object>();
        result3.add(new Object[] { studyProtocolIdentifier, "Alternate Title",
                "Other" });
        when(daMock.findByQuery(qryMain3)).thenReturn(result3);
    }

    @Test
    public void emptyListTest() throws Exception {
        assertEquals(0, bean.getResults(null, false, null).size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), false, null)
                .size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), false, 1L)
                .size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), true, null)
                .size());
        assertEquals(0, bean.getResults(new ArrayList<Long>(), true, 1L).size());
    }

    @Test
    public void emptyListLeanTest() throws Exception {
        List<Object> result = new ArrayList<Object>();
        result.add(qryResult);
        when(daMock.findByQuery(any(DAQuery.class))).thenReturn(result);
        assertEquals(0, bean.getResultsLean(null).size());
        assertEquals(0, bean.getResultsLean(new ArrayList<Long>()).size());
    }

    @Test
    public void noOwnedTrialsTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        for (long x = 0; x < 501; x++) {
            ids.add(x);
        }
        assertEquals(0, bean.getResults(ids, true, null).size());
    }

    @Test
    public void submissionTypeTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // update
        List<StudyProtocolQueryDTO> trials = bean.getResults(ids, false,
                MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.U, trials.get(0)
                .getSubmissionTypeCode());

        // original
        qryResult[UPDATING_IDX] = null;
        trials = bean.getResults(ids, false, MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.O, trials.get(0)
                .getSubmissionTypeCode());
        // amendment
        qryResult[SUBMISSION_NUMBER_IDX] = 2;
        trials = bean.getResults(ids, false, MEMB_USERID);
        assertEquals(1, trials.size());
        assertEquals(SubmissionTypeCode.A, trials.get(0)
                .getSubmissionTypeCode());
    }

    @Test
    public void submissionTypeLeanTest() throws Exception {
        List<Object> result = new ArrayList<Object>();
        result.add(qryResult);
        when(daMock.findByQuery(any(DAQuery.class))).thenReturn(result);
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        assertEquals(1, bean.getResultsLean(ids).size());
    }

    @Test
    public void adminTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // get all trials
        assertEquals(1, bean.getResults(ids, false, ADMIN_USERID).size());
        // get owned trials, not affiliated, not trial owner
        assertEquals(0, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, affiliated, not trial owner
        admin.setAffiliateOrg(leadOrgPoid);
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, affiliated, trial owner
        List<Object> ownedStudies = new ArrayList<Object>();
        ownedStudies.add(studyProtocolIdentifier);
        DAQuery qry = new DAQuery();
        qry.setSql(true);
        qry.setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
        qry.addParameter("userId", ADMIN_USERID);
        when(daMock.findByQuery(qry)).thenReturn(ownedStudies);
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
        // get owned trials, not affiliated, trial owner
        admin.setAffiliateOrg("xyzzy");
        assertEquals(1, bean.getResults(ids, true, ADMIN_USERID).size());
    }

    @Test
    public void memberTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // all trials
        assertEquals(1, bean.getResults(ids, false, MEMB_USERID).size());
        // owned trials, still returns since user is trial owner
        assertEquals(1, bean.getResults(ids, true, MEMB_USERID).size());
    }

    @Test
    public void nullSafeResultsTest() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        StudyProtocol id = new StudyProtocol();
        id.setId(studyProtocolIdentifier.longValue());
        ids.add(studyProtocolIdentifier.longValue());
        // don't null [0] as study_protocol_identifier never null
        for (int x = 1; x < qryResult.length; x++) {
            qryResult[x] = null;
        }
        assertEquals(1, bean.getResults(ids, false, null).size());
    }

    @Test
    public void testSetFlags() {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        List<String> rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        ((ProtocolQueryResultsServiceBean) bean).setFlags(dto, qryResult,
                rssOrgs);
        assertTrue(dto.isSiteSelfRegistrable());

        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("Duke Medical Center");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        ((ProtocolQueryResultsServiceBean) bean).setFlags(dto, qryResult,
                rssOrgs);
        assertFalse(dto.isSiteSelfRegistrable());

        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.REJECTED);
        bean.setFlags(dto, qryResult, rssOrgs);
        assertFalse(dto.isSiteSelfRegistrable());

        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dto.setStudyStatusCode(StudyStatusCode.ACTIVE);
        bean.setFlags(dto, qryResult, rssOrgs);
        assertTrue(dto.isSiteSelfRegistrable());

        dto = new StudyProtocolQueryDTO();
        dto.setProprietaryTrial(true);
        rssOrgs = Arrays.asList("RSS");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dto.setStudyStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        bean.setFlags(dto, qryResult, rssOrgs);
        assertTrue(dto.isSiteSelfRegistrable());

    }

    /**
     * @throws PAException
     * 
     */
    @Test
    public void testGetStudiesOnWhichUserHasSite() throws PAException {
        Map<Long, Boolean> map = bean.getStudiesOnWhichUserHasSite(memb);
        assertEquals(1, map.size());
        assertEquals(Boolean.TRUE, map.get(studyProtocolIdentifier.longValue()));
    }
}
