/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

/**
 * @author vinodh
 *
 */
public class StudyCancerCenterAccrualBeanTest extends AbstractHibernateTestCase {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private StudyCancerCenterAccrualBean studyCancerCenterAccrualBean;
    
    @Before
    public void setUp() throws Exception {
        studyCancerCenterAccrualBean = new StudyCancerCenterAccrualBean();
        TestSchema.primeData();
        PaHibernateUtil.enableAudit();
    }
    
    @Test
    public void testNoFamilyPOId() throws PAValidationException {
        thrown.expect(PAValidationException.class);
        thrown.expectMessage("Family PO identifier is required");
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(null, 1L, 123);
    }
    
    @Test
    public void testNoStudyProtocolId() throws PAValidationException {
        thrown.expect(PAValidationException.class);
        thrown.expectMessage("StudyProtocol identifier is required");
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(1L, null, 123);
    }
    
    @Test
    public void testNoFamilyInDB() throws PAValidationException {
        thrown.expect(PAValidationException.class);
        thrown.expectMessage("Family not found for the PO Id, 868778");
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(868778L, 1L, 123);
    }
    
    @Test
    public void testSaveStudyCancerCenterAccrual() throws PAValidationException {
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(-1L, 1L, 123);
        
        Session  session = PaHibernateUtil.getCurrentSession();
        Query qry = session.createQuery("FROM StudyCancerCenterAccrual scca ");
        assertEquals(1, qry.list().size());
    }
    
    @Test
    public void testUpdateStudyCancerCenterAccrual() throws PAValidationException {
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(-1L, 1L, 345);
        Map<Long, Integer> sccAccMap = studyCancerCenterAccrualBean.getStudyCancerCenterAccrualsByFamily(-1L);
        assertEquals(new Integer(345), (Integer)sccAccMap.get(1L));
        
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(-1L, 1L, 786);
        sccAccMap = studyCancerCenterAccrualBean.getStudyCancerCenterAccrualsByFamily(-1L);
        assertEquals(new Integer(786), (Integer)sccAccMap.get(1L));
    }
    
    @Test
    public void testgetStudyCancerCenterAccrualsByFamily() throws PAValidationException {
        studyCancerCenterAccrualBean.saveOrUpdateStudyCancerCenterAccrual(-1L, 1L, 345);
        Map<Long, Integer> sccAccMap = studyCancerCenterAccrualBean.getStudyCancerCenterAccrualsByFamily(-1L);
        
        assertNotNull(sccAccMap);
        assertEquals(1, sccAccMap.size());
        assertNotNull(sccAccMap.get(1L));
        assertEquals(new Integer(345), (Integer)sccAccMap.get(1L));
    }
}
