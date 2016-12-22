package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceBeanTest;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

public class AccrualDiseaseTerminologyServiceTest extends AbstractHibernateTestCase {

    private final StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
    private final AccrualDiseaseTerminologyServiceBean bean = new AccrualDiseaseTerminologyServiceBean();
    private final AccrualDiseaseTerminologyServiceRemote remoteEjb = bean;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @SuppressWarnings("unchecked")
	@Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        UsernameHolder.setUser(TestSchema.getUser().getLoginName());
        AnatomicSite as = new AnatomicSite();
        as.setCode("Lung");
        as.setCodingSystem("Summary 4 Anatomic Sites");
        TestSchema.addUpdObject(as);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
        when(lookupSvc.getLookupEntityByCode(any(Class.class), any(String.class))).thenReturn(as);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);

        createAccrualDisease("SDC", "SDC01","SDC01");
        createAccrualDisease("SDC", "SDC02","SDC02");
        createAccrualDisease("ICD-O-3", "C34.1", "icdo3 site code");
        createAccrualDisease("ICD9", "ICD9_01", "ICD9_01");
        createAccrualDisease("ICD10", "ICD10_01", "ICD10_01");
    }

    private static void createAccrualDisease(String codeSystem,
            String diseaseCode, String name) {
        AccrualDisease ad = new AccrualDisease();
        ad.setCodeSystem(codeSystem);
        ad.setDiseaseCode(diseaseCode);
        ad.setPreferredName(name);
        ad.setDisplayName(name);
        TestSchema.addUpdObject(ad);
    }

    @Test
    public void getValidCodeSystemsTest() throws Exception {
        List<String> csList = bean.getValidCodeSystems();
        assertEquals(4, csList.size());
        assertEquals(true, csList.contains("ICD9"));
        assertEquals(true, csList.contains("ICD10"));
        assertEquals(true, csList.contains("SDC"));
        assertEquals(true, csList.contains("ICD-O-3"));
    }

    @Test
    public void getCodeSystemTest() throws Exception {
        assertNull(bean.getCodeSystem(null));
        assertNull(bean.getCodeSystem(-1L));
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("ICD10"));
        Ii ii = spBean.createInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
        Long id = IiConverter.convertToLong(ii);
        assertEquals("ICD10", bean.getCodeSystem(id));
    }

    @Test
    public void updateCodeSystemTest() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        Ii ii = spBean.createInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
        Long id = IiConverter.convertToLong(ii);
        expectedEx.expectMessage("Invalid disease code system: xyzzy");
        bean.updateCodeSystem(id, "xyzzy");

        expectedEx = ExpectedException.none();
        assertEquals("SDC", bean.getCodeSystem(id));
        bean.updateCodeSystem(id, "ICD10");
        assertEquals("ICD10", bean.getCodeSystem(id));
    }

    @Test
    public void testCanChangeCodeSystem() throws Exception {
    	// null test
        assertTrue(remoteEjb.canChangeCodeSystem(null));

        // no subjects test
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        Ii ii = spBean.createInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
        Long id = IiConverter.convertToLong(ii);
        assertTrue(remoteEjb.canChangeCodeSystem(id));
        // no disease code test
        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, id);
        StudySite studySite = new StudySite();
        studySite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        studySite.setStudyProtocol(sp);
        sp.getStudySites().add(studySite);
        TestSchema.addUpdObject(studySite);
        assertNotNull(studySite.getId());
        Country country = new Country();
        TestSchema.addUpdObject(country);
        assertNotNull(country.getId());
        Patient pat = new Patient();
        pat.setCountry(country);
        pat.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(pat);
        assertNotNull(pat.getId());
        StudySubject ssub = new StudySubject();
        ssub.setPatient(pat);
        ssub.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        ssub.setStudyProtocol(sp);
        ssub.setStudySite(studySite);
        ssub.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        studySite.setStudySubjects(new ArrayList<StudySubject>());
        studySite.getStudySubjects().add(ssub);
        TestSchema.addUpdObject(ssub);
        assertNotNull(ssub.getId());
        assertTrue(remoteEjb.canChangeCodeSystem(id));

        // active subject with disease test
        AccrualDisease ad = new AccrualDisease();
        ad.setCodeSystem("ICD10");
        ad.setDiseaseCode("xyzzy");
        ad.setDisplayName("xyzzy");
        ad.setPreferredName("xyzzy");
        ad.setStudySubjects(new ArrayList<StudySubject>());
        ad.getStudySubjects().add(ssub);
        ssub.setDisease(ad);
        TestSchema.addUpdObject(ad);
        assertNotNull(ad.getId());
        assertFalse(remoteEjb.canChangeCodeSystem(id));

        // inactive subject test
        ssub.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        TestSchema.addUpdObject(ssub);
        assertTrue(remoteEjb.canChangeCodeSystem(id));
    }
    
    @Test
    public void testCanChangeCodeSystemForSpIds() throws Exception {

        // no subjects test
        NonInterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createNonInterventionalStudyProtocolDTOObj();
        ispDTO.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        Ii ii = spBean.createNonInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
        Long id = IiConverter.convertToLong(ii);
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        Map<Long, Boolean> result = remoteEjb.canChangeCodeSystemForSpIds(ids);
        assertTrue(result.get(id));

        // no disease code test
        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, id);
        StudySite studySite = new StudySite();
        studySite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        studySite.setStudyProtocol(sp);
        sp.getStudySites().add(studySite);
        TestSchema.addUpdObject(studySite);
        assertNotNull(studySite.getId());
        Country country = new Country();
        TestSchema.addUpdObject(country);
        assertNotNull(country.getId());
        Patient pat = new Patient();
        pat.setCountry(country);
        pat.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(pat);
        assertNotNull(pat.getId());
        StudySubject ssub = new StudySubject();
        ssub.setPatient(pat);
        ssub.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        ssub.setStudyProtocol(sp);
        ssub.setStudySite(studySite);
        ssub.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        studySite.setStudySubjects(new ArrayList<StudySubject>());
        studySite.getStudySubjects().add(ssub);
        TestSchema.addUpdObject(ssub);
        assertNotNull(ssub.getId());
        ids.clear();
        ids.add(id);
        result = remoteEjb.canChangeCodeSystemForSpIds(ids);
        assertTrue(result.get(id));

        // active subject with disease test
        AccrualDisease ad = new AccrualDisease();
        ad.setCodeSystem("ICD10");
        ad.setDiseaseCode("xyzzy");
        ad.setDisplayName("xyzzy");
        ad.setPreferredName("xyzzy");
        ad.setStudySubjects(new ArrayList<StudySubject>());
        ad.getStudySubjects().add(ssub);
        ssub.setDisease(ad);
        TestSchema.addUpdObject(ad);
        assertNotNull(ad.getId());
        ids.clear();
        ids.add(id);
        result = remoteEjb.canChangeCodeSystemForSpIds(ids);
        assertFalse(result.get(id));

        // inactive subject test
        ssub.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        TestSchema.addUpdObject(ssub);
        ids.clear();
        ids.add(id);
        result = remoteEjb.canChangeCodeSystemForSpIds(ids);
        assertTrue(result.get(id));
    }
}
