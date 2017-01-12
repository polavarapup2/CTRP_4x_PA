package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

public class AccrualDiseaseServiceTest  extends AbstractHibernateTestCase {

    AccrualDisease criteria;
    AccrualDiseaseBeanLocal bean;
    StudySubjectServiceLocal ssBean;
    SearchStudySiteService sssBean;
    StudyProtocolServiceRemote spBean;
    AccrualDiseaseTerminologyServiceRemote adtBean;

    @Before
    public void setUp() throws Exception {
        ssBean = mock(StudySubjectServiceLocal.class);
        sssBean = mock(SearchStudySiteService.class);
        spBean = mock(StudyProtocolServiceRemote.class);
        adtBean = mock(AccrualDiseaseTerminologyServiceRemote.class);
        when(adtBean.getValidCodeSystems()).thenReturn(Arrays.asList("SDC", "ICD9", "ICD10", "ICD-O-3"));
        bean = new AccrualDiseaseBeanLocal();
        bean.setSearchStudySiteSvc(sssBean);
        ServiceLocatorPaInterface svcLocator = mock(ServiceLocatorPaInterface.class);
        when(svcLocator.getStudyProtocolService()).thenReturn(spBean);
        when(svcLocator.getAccrualDiseaseTerminologyService()).thenReturn(adtBean);
        PaServiceLocator.getInstance().setServiceLocator(svcLocator);
        TestSchema.primeData();
        criteria = new AccrualDisease();
    }

    @Test
    public void getTest() {
        assertNull(bean.get(-1L));
        assertNotNull(bean.get(1L));
    }

    @Test
    public void getIiTest() {
        assertNull(bean.get(IiConverter.convertToIi(-1L)));
        assertNotNull(bean.get(IiConverter.convertToIi(1L)));
        Ii ii = new Ii();
        ii.setExtension("xyzzy");
        ii.setIdentifierName("ICD9");
        assertNull(bean.get(ii));
        ii.setExtension("code2");
        assertNotNull(bean.get(ii));
        ii.setIdentifierName("SDC");
        ii.setExtension("SDC01");
        assertNotNull(bean.get(ii));
    }
    
    @Test
    public void getIiNullTest() {
        assertNull(bean.get((Ii)null));
    }

    @Test
    public void getByCode() {
        assertNull(bean.getByCode("ICD9", "ode1"));
        assertNotNull(bean.getByCode("ICD9", "code1"));
    }

    @Test
    public void getByCodeICDO3NoDot() {  // PO-6136
        assertNull(bean.getByCode("ICD9", "C34.1"));
        AccrualDisease ad1 = bean.getByCode("ICD-O-3", "C34.1");
        AccrualDisease ad2 = bean.getByCode("ICD-O-3", "C341");
        assertNotNull(ad1);
        assertNotNull(ad2);
        assertEquals(ad1, ad2);
        assertNull(bean.getByCode("ICD9", "C341"));
    }

    @Test
    public void searchTest() {
        criteria.setId(3L);
        assertEquals(1, bean.search(criteria).size());
        criteria.setId(null);
        criteria.setCodeSystem("ICD9");
        assertEquals(7, bean.search(criteria).size());
        criteria.setCodeSystem(null);
        criteria.setDiseaseCode("ode1");
        assertEquals(0, bean.search(criteria).size());
        criteria.setDiseaseCode("code1");
        assertEquals(1, bean.search(criteria).size());
        criteria.setDiseaseCode(null);
        criteria.setPreferredName("name2");
        assertEquals(1, bean.search(criteria).size());
        criteria.setPreferredName("ame2");
        assertEquals(1, bean.search(criteria).size());
        criteria.setCodeSystem("ICD-O-3");
        criteria.setPreferredName(null);
        criteria.setDiseaseCode("c341");
        assertEquals(1, bean.search(criteria).size());
        //exercise search results are sorted by code system followed by disease code.
        criteria.setCodeSystem(null);
        criteria.setDiseaseCode(null);
        criteria.setPreferredName("acute leukemia");
        List<AccrualDisease> diseaseList = bean.search(criteria);
        assertEquals(3, diseaseList.size());
        assertEquals("ICD9", diseaseList.get(0).getCodeSystem());
        assertEquals("code6", diseaseList.get(0).getDiseaseCode());
        assertEquals("ICD9", diseaseList.get(1).getCodeSystem());
        assertEquals("code7", diseaseList.get(1).getDiseaseCode());
        assertEquals("SDC", diseaseList.get(2).getCodeSystem());
        assertEquals("SDC05", diseaseList.get(2).getDiseaseCode());
    }

    @Test
    public void diseaseCodeMandatoryTest() throws Exception {
        // DCP
        when(sssBean.isStudyHasDCPId(any(Ii.class))).thenReturn(true);
        assertFalse(bean.diseaseCodeMandatory(1L));

        // Prevention trial
        when(sssBean.isStudyHasDCPId(any(Ii.class))).thenReturn(false);
        StudyProtocolDTO sp = new StudyProtocolDTO();
        sp.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        when(spBean.getStudyProtocol(any(Ii.class))).thenReturn(sp);
        assertFalse(bean.diseaseCodeMandatory(1L));

        // Disease mandatory
        sp.setPrimaryPurposeCode(CdConverter.convertStringToCd("TREATMENT"));
        assertTrue(bean.diseaseCodeMandatory(1L));

        // Exception
        when(spBean.getStudyProtocol(any(Ii.class))).thenThrow(new PAException());
        assertTrue(bean.diseaseCodeMandatory(1L));
    }
}
