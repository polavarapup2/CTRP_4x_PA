package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusBeanLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class FamilyServiceTest extends AbstractHibernateTestCase {

    private FamilyServiceBeanLocal ejb;
    private OrganizationEntityServiceRemote oes;
    private FamilyServiceRemote fs;
    RegistryUserServiceLocal registryUserEjb;
    StudySiteAccrualAccessServiceBean siteAccessEjb;
    ParticipatingOrgServiceBean partOrgEjb;
    DocumentWorkflowStatusServiceLocal dwsEjb;
    private RegistryUser ru;
    Session sess;

    @Before
    public void setUp() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
        TestSchema.primeData();
        sess = PaHibernateUtil.getCurrentSession();
        ru = (RegistryUser) sess.get(RegistryUser.class, TestSchema.registryUserIds.get(0));

        CSMUserService.setInstance(new MockCSMUserService());
        ejb = new FamilyServiceBeanLocal();
        registryUserEjb = new RegistryUserBeanLocal();
        siteAccessEjb = new StudySiteAccrualAccessServiceBean();
        partOrgEjb = new ParticipatingOrgServiceBean();
        partOrgEjb.setStudySiteAccrualStatusService(mock(StudySiteAccrualStatusServiceLocal.class));
        partOrgEjb.setPaHealthCareProviderService(mock(PAHealthCareProviderLocal.class));
        siteAccessEjb.setParticipatingOrgServiceLocal(partOrgEjb);
        siteAccessEjb.setStudySiteAccrualStatusService(mock(StudySiteAccrualStatusServiceLocal.class));
        dwsEjb = new DocumentWorkflowStatusBeanLocal();
        ejb.setRegistryUserService(registryUserEjb);
        ejb.setStudySiteAccess(siteAccessEjb);
        ejb.setDwsService(dwsEjb);
        ejb.setParticipatingOrgService(partOrgEjb);
    }

    private FamilyDTO getPoFamilyDTO(Long id) {
        FamilyDTO family = new FamilyDTO();
        family.setIdentifier(IiConverter.convertToPoFamilyIi(id.toString()));
        family.setName(EnOnConverter.convertToEnOn("family" + id));
        return family;
    }

    private List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }

    @Test
    public void assignFamilyAccrualAccessNullUserTest() throws Exception {
        ejb.assignFamilyAccrualAccess(null, ru, null);
    }

    @Test
    public void assignFamilyAccrualAccessNullCreatorTest() throws Exception {
        ejb.assignFamilyAccrualAccess(ru, null, null);
    }

    @Test
    public void assignFamilyAccrualAccessNoTrialsTest() throws Exception {
        assertFalse(ru.getFamilyAccrualSubmitter());
        ejb.assignFamilyAccrualAccess(ru, ru, null);
        assertTrue(ru.getFamilyAccrualSubmitter());
    }

    @Test
    public void assignFamilyAccrualAccessTest() throws Exception {
        sess.createQuery("delete from StudyAccrualAccess").executeUpdate();
        Long orgId = getCompleteTrialLeadOrgId();
        ru.setAffiliatedOrganizationId(orgId);
        TestSchema.addUpdObject(ru);
        OrganizationDTO org  = new OrganizationDTO();
        org.setIdentifier(IiConverter.convertToPoOrganizationIi(orgId.toString()));
        DSet<Ii> dset = new DSet<Ii>();
        Set<Ii> familySet = new HashSet<Ii>();
        dset.setItem(familySet);
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        when(fs.getActiveRelationships(1L)).thenReturn(getRelationships(new Long[] {1L}));

        List<Long> poOrgIds =  FamilyHelper.getAllRelatedOrgs(ru.getAffiliatedOrganizationId());
        Set<Long> trialIds = ejb.getSiteAccrualTrials(poOrgIds);
        // not eligible for accrual, no dws
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        ejb.assignFamilyAccess(trialIds, ru, ru, null);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        
        // not eligible for accrual
        TestSchema.addAbstractedWorkflowStatus(TestSchema.studyProtocolIds.get(0));
        ejb.assignFamilyAccess(trialIds, ru, ru, null);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));

        // eligible for accrual
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        for (DocumentWorkflowStatus dws : sp.getDocumentWorkflowStatuses()) {
            dws.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
            sess.update(dws);
        }
        ejb.assignFamilyAccess(trialIds, ru, ru, null);
        assertEquals(1, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        
        assertEquals(1, ejb.getSiteAccrualTrials(ru.getAffiliatedOrganizationId()).size());
        convertToAbbreviateTrial();
        assertEquals(1, ejb.getSiteAccrualTrials(ru.getAffiliatedOrganizationId()).size());
    }

    @Test
    public void getSiteAccrualTrialsTest() throws Exception {
        Long spId = TestSchema.studyProtocolIds.get(0);
        List<Long> orgIds = new ArrayList<Long>();
        orgIds.add(getCompleteTrialLeadOrgId());
  
        // complete
        assertTrue(ejb.getSiteAccrualTrials(orgIds).contains(spId));
        // abbr
        convertToAbbreviateTrial();
        assertTrue(ejb.getSiteAccrualTrials(orgIds).contains(spId));
        // current status not eligible
        assertTrue(RecruitmentStatusCode.NOT_ELIGIBLE_FOR_ACCRUAL_STATUSES.contains(RecruitmentStatusCode.IN_REVIEW));
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, spId);
        for (StudySite ss : sp.getStudySites()) {
            if (ss.getFunctionalCode().equals(StudySiteFunctionalCode.TREATING_SITE)) {
                StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
                ssas.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
                ssas.setStatusDate(new Timestamp((new Date()).getTime()));
                ssas.setStudySite(ss);
                sess.save(ssas);
            }
        }
        assertFalse(ejb.getSiteAccrualTrials(orgIds).contains(spId));
    }

    @Test
    public void getSiteAccrualTrialsTestNoTrialsTest() throws Exception {
        assertEquals(0, ejb.getSiteAccrualTrials((Long) null).size());
        assertEquals(0, ejb.getSiteAccrualTrials(-1L).size());
    }

    @Test
    public void unassignAllAccrualAccessNullUserTest() throws Exception {
        ejb.unassignAllAccrualAccess(null, ru);
    }

    @Test(expected = PAException.class)
    public void unassignAllAccrualAccessNullCreatorTest() throws Exception {
        ejb.unassignAllAccrualAccess(ru, null);
    }

    @Test
    public void unassignAllAccrualAccessNoExistingAccessTest() throws Exception {
        ru.setFamilyAccrualSubmitter(true);
        ru.setSiteAccrualSubmitter(true);
        registryUserEjb.updateUser(ru);
        RegistryUser updated = registryUserEjb.getUserById(ru.getId());
        assertTrue(updated.getFamilyAccrualSubmitter());
        assertTrue(updated.getSiteAccrualSubmitter());
        ejb.unassignAllAccrualAccess(ru, ru);
        updated = registryUserEjb.getUserById(ru.getId());
        assertFalse(updated.getFamilyAccrualSubmitter());
        assertFalse(updated.getSiteAccrualSubmitter());
    }

    @Test
    public void unassignAllAccrualAccessExistingAccessCompleteTest() throws Exception {
        StudyProtocol sp = createComplete();
        assignAccess(sp);
        assertEquals(1, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        siteAccessEjb.unassignAllAccrualAccess(ru, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, null, ru);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
    }

    @Test
    public void unassignAllAccrualAccessExistingAccessAbbreviatedTest() throws Exception {
        StudyProtocol sp = createAbbreviated();
        assignAccess(sp);
        assertEquals(1, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        siteAccessEjb.unassignAllAccrualAccess(ru, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, null, ru);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
    }

    @Test
    public void updateSiteAndFamilyPermissionsNullTest() throws Exception {
        ejb.updateSiteAndFamilyPermissions(null);
    }

    @Test
    public void updateSiteAndFamilyPermissionsTest() throws Exception {
        ejb.updateSiteAndFamilyPermissions(TestSchema.studyProtocolIds.get(0));
    }

    private StudyProtocol createComplete() throws Exception {
        sess.createQuery("DELETE FROM StudySiteAccrualAccess").executeUpdate();
        sess.createQuery("DELETE FROM StudyAccrualAccess").executeUpdate();
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);
        return sp;
    }

    private StudyProtocol createAbbreviated() throws Exception {
        StudyProtocol sp = createComplete();
        sp.setProprietaryTrialIndicator(true);
        TestSchema.addUpdObject(sp);
        return sp;
    }

    private void assignAccess(StudyProtocol sp) throws Exception {
        siteAccessEjb.assignTrialLevelAccrualAccess(ru, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", ru);
    }

    private long studyAccessCount(ActiveInactiveCode statusCode, AssignmentActionCode actionCode) throws Exception {
        Query qry = sess.createQuery("FROM StudyAccrualAccess saa "
                + "WHERE saa.statusCode = :statusCode AND saa.actionCode = :actionCode");
        qry.setParameter("actionCode", actionCode);
        qry.setParameter("statusCode", statusCode);
        return qry.list().size();
    }

    private Long getCompleteTrialLeadOrgId() {
        Long spId = TestSchema.studyProtocolIds.get(0);
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, spId);
        assertFalse(sp.getProprietaryTrialIndicator());
        Set<StudySite> sss = sp.getStudySites();
        StudySite leadOrg = null;
        for (StudySite ss : sss) {
            if (ObjectUtils.equals(StudySiteFunctionalCode.LEAD_ORGANIZATION, ss.getFunctionalCode())) {
                leadOrg = ss;
            }
        }
        assertNotNull(leadOrg);
        return Long.valueOf(leadOrg.getResearchOrganization().getOrganization().getIdentifier());
    }

    private void convertToAbbreviateTrial() {
        Long spId = TestSchema.studyProtocolIds.get(0);
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, spId);
        sp.setProprietaryTrialIndicator(true);
        sess.save(sp);
    }
}
