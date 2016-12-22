package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class UpdateFamilyAccrualAccessServiceBeanTest  extends AbstractHibernateTestCase {

	private UpdateFamilyAccrualAccessServiceBean svcBean;
	
	 @Before
	    public void setUp() throws Exception {
	     TestSchema.primeData();
		 svcBean = new UpdateFamilyAccrualAccessServiceBean();
		 svcBean.setUseTestSeq(true);
		 FamilyServiceBeanLocal mockFam = mock(FamilyServiceBeanLocal.class);
		 svcBean.setFamilyService(mockFam);
		 Set<Long> result = new HashSet<Long>();
		 result.add(TestSchema.studyProtocolIds.get(1));
		 result.add(TestSchema.studyProtocolIds.get(2));
		 when(mockFam.getSiteAccrualTrials(any(Long.class))).thenReturn(result);
		 DataAccessServiceLocal mockDA = mock(DataAccessServiceLocal.class);
		 svcBean.setDataAccessService(mockDA);
		 DAQuery qr = new DAQuery();
         qr.setSql(true);
         qr.setText(UpdateFamilyAccrualAccessServiceBean.DWF_QRY);
         Set<Long> ids = new HashSet<Long>();
         ids.add(2L);
         ids.add(3L);
         qr.addParameter("spIds", ids);
		 List<Object> trialsDWFS = new ArrayList<Object>();
		 Object[] testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(0).toString()), DocumentWorkflowStatusCode.ACCEPTED.getName() };
		 trialsDWFS.add(testCase);
		 testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(1).toString()), DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getName()};
		 trialsDWFS.add(testCase);
		 testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(2).toString()), DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getName()};
		 trialsDWFS.add(testCase);		 
		 when(mockDA.findByQuery(qr)).thenReturn(trialsDWFS);
		 qr = new DAQuery();
         qr.setSql(true);
         qr.setText(UpdateFamilyAccrualAccessServiceBean.LEAD_ORG_QRY);
         qr.addParameter("spIds", ids);
         List<Object> trialsLeadOrgIdsList = new ArrayList<Object>();
         trialsLeadOrgIdsList.add(new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(2).toString()), 1L});
         when(mockDA.findByQuery(qr)).thenReturn(trialsLeadOrgIdsList);
	    
	     
	     Session session = PaHibernateUtil.getCurrentSession();
	     RegistryUser user = TestSchema.getRegistryUser();
		 user.setFamilyAccrualSubmitter(true);
		 user.setAffiliatedOrganizationId(1L);
		 TestSchema.addUpdObject(user);
		 
		 StudySite ss= new StudySite();
		 HealthCareFacility hfc = new HealthCareFacility();
		 hfc.setId(TestSchema.healthCareFacilityIds.get(0));
		 StudyProtocol sp = new StudyProtocol();
		 sp.setId(TestSchema.studyProtocolIds.get(1));
		 ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
		 ss.setHealthCareFacility(hfc);
		 ss.setLocalStudyProtocolIdentifier("LocalSPID");
		 ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
		 ss.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2014"));
		 ss.setStudyProtocol(sp);
		 TestSchema.addUpdObject(ss);
		 StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
		 ssas.setStudySite(ss);
		 ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
		 TestSchema.addUpdObject(ssas);
		 
		 ss= new StudySite();
		 sp = new StudyProtocol();
		 sp.setId(TestSchema.studyProtocolIds.get(2));
		 ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
		 ss.setHealthCareFacility(hfc);
		 ss.setLocalStudyProtocolIdentifier("LocalSPID2");
		 ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
		 ss.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2014"));
		 ss.setStudyProtocol(sp);
		 TestSchema.addUpdObject(ss);
		 ssas = new StudySiteAccrualStatus();
		 ssas.setStudySite(ss);
		 ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
		 TestSchema.addUpdObject(ssas);
		 StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
		 ssaa.setStudySite(ss);
		 ssaa.setRegistryUser(user);
		 ssaa.setStatusCode(ActiveInactiveCode.INACTIVE);
		 ssaa.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/1/2014"));
		 ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
		 TestSchema.addUpdObject(ssaa);
		 
		 PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
	     PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
	     CSMUserService.setInstance(new MockCSMUserService());
	     when(PoRegistry.getOrganizationEntityService()).thenReturn(new MockPoOrganizationEntityService());
	     FamilyServiceRemote fs = mock(FamilyServiceRemote.class);
	     when(PoRegistry.getFamilyService()).thenReturn(fs);
	     Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
         FamilyDTO family = new FamilyDTO();
         family.setName(EnOnConverter.convertToEnOn("family name"));
         familyMap.put(IiConverter.convertToPoFamilyIi("1"), family);
         when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
         when(fs.getActiveRelationships(any(Long.class))).thenReturn(FamilyHelperTest.getRelationships(new Long[] {1L, 2L}));
	 }
	 
	 @Test
	    public void testUpdateFamilyAccrualAccess() throws Exception {
		 String qry = "select study_site_identifier, identifier, status_code from"
			 + " study_site_accrual_access where study_site_identifier in (3) and registry_user_id = 3";
		Session session = PaHibernateUtil.getCurrentSession();
		assertEquals(0, session.createSQLQuery(qry).list().size());
		svcBean.updateFamilyAccrualAccess();
		assertEquals(1, session.createSQLQuery(qry).list().size());
	 }
}
