package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.AccrualOutOfScopeTrial;
import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.util.AccrualUtilityServiceBean;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Denis G. Krylov
 */
public class AccrualUtilityServiceBeanTest extends AbstractHibernateTestCase {
    private AccrualUtilityServiceBean bean = new AccrualUtilityServiceBean();

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
    }

    @Test
    public void getAllOutOfScopeTrials() throws PAException {
        AccrualOutOfScopeTrial bo = new AccrualOutOfScopeTrial();
        bo.setAction("Rejected");
        bo.setCtepID("MN0001");
        bo.setFailureReason("Missing Trial");
        final Date date = new Date();
        bo.setSubmissionDate(date);
        bo.setUser(TestSchema.user);
        
        PaHibernateUtil.getCurrentSession().save(bo);
        PaHibernateUtil.getCurrentSession().flush();
        
        List<AccrualOutOfScopeTrialDTO> list = bean.getAllOutOfScopeTrials();
        assertEquals(1, list.size());
        
        AccrualOutOfScopeTrialDTO dto = list.get(0);
        assertEquals("Rejected", dto.getAction());
        assertEquals("MN0001", dto.getCtepID());
        assertEquals("Missing Trial", dto.getFailureReason());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals(TestSchema.user.getLoginName(), dto.getUserLoginName());
        assertEquals(TestSchema.user.getLoginName(), dto.getUserLoginNameStripped());

    }
    
    @Test
    public void update() throws PAException {
        AccrualOutOfScopeTrial bo = new AccrualOutOfScopeTrial();
        bo.setAction("Rejected");
        bo.setCtepID("MN0001");
        bo.setFailureReason("Missing Trial");
        final Date newDate = new Date();
        final Date date = newDate;
        bo.setSubmissionDate(date);
        bo.setUser(TestSchema.user);
        
        PaHibernateUtil.getCurrentSession().save(bo);
        PaHibernateUtil.getCurrentSession().flush();
        
        List<AccrualOutOfScopeTrialDTO> list = bean.getAllOutOfScopeTrials();
        assertEquals(1, list.size());
        
        AccrualOutOfScopeTrialDTO dto = list.get(0);        
        dto.setAction("Out of scope");
        dto.setCtepID("MN0002");
        dto.setFailureReason("Out of scope");
        dto.setSubmissionDate(newDate);        
        bean.update(dto);
        PaHibernateUtil.getCurrentSession().flush();
        
        list = bean.getAllOutOfScopeTrials();
        assertEquals(1, list.size());
        
        dto = list.get(0);
        assertEquals("Out of scope", dto.getAction());
        assertEquals("MN0002", dto.getCtepID());
        assertEquals("Out of scope", dto.getFailureReason());
        assertEquals(newDate, dto.getSubmissionDate());
        

    }
    
    @Test
    public void delete() throws PAException {
        AccrualOutOfScopeTrial bo = new AccrualOutOfScopeTrial();
        bo.setAction("Rejected");
        bo.setCtepID("MN0001");
        bo.setFailureReason("Missing Trial");
        final Date newDate = new Date();
        final Date date = newDate;
        bo.setSubmissionDate(date);
        bo.setUser(TestSchema.user);
        
        PaHibernateUtil.getCurrentSession().save(bo);
        PaHibernateUtil.getCurrentSession().flush();
        
        List<AccrualOutOfScopeTrialDTO> list = bean.getAllOutOfScopeTrials();
        assertEquals(1, list.size());
        
        AccrualOutOfScopeTrialDTO dto = list.get(0);
        bean.delete(dto);
        PaHibernateUtil.getCurrentSession().flush();
        
        list = bean.getAllOutOfScopeTrials();
        assertEquals(0, list.size());

    }
    
    @Test
    public void create() throws PAException {
        AccrualOutOfScopeTrialDTO dto = new AccrualOutOfScopeTrialDTO();
        dto.setAction("Rejected");
        dto.setCtepID("MN0001");
        dto.setFailureReason("Missing Trial");
        final Date date = new Date();
        dto.setSubmissionDate(date);
        dto.setUserLoginName("user1@mail.nih.gov");
        
        bean.create(dto);
        PaHibernateUtil.getCurrentSession().flush();
        
        List<AccrualOutOfScopeTrialDTO> list = bean.getAllOutOfScopeTrials();
        assertEquals(1, list.size());
        
        dto = list.get(0);
        assertEquals("Rejected", dto.getAction());
        assertEquals("MN0001", dto.getCtepID());
        assertEquals("Missing Trial", dto.getFailureReason());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals("user1@mail.nih.gov", dto.getUserLoginName());
        assertEquals("user1@mail.nih.gov", dto.getUserLoginNameStripped());

    }
    
    @Test
    public void getByCtepID() throws PAException {
        AccrualOutOfScopeTrial bo = new AccrualOutOfScopeTrial();
        bo.setAction("Rejected");
        bo.setCtepID("MN0005");
        bo.setFailureReason("Missing Trial");
        final Date date = new Date();
        bo.setSubmissionDate(date);
        bo.setUser(TestSchema.user);        
        PaHibernateUtil.getCurrentSession().save(bo);
        PaHibernateUtil.getCurrentSession().flush();
        
        AccrualOutOfScopeTrialDTO dto = bean.getByCtepID("MN0005");
        
        assertEquals("Rejected", dto.getAction());
        assertEquals("MN0005", dto.getCtepID());
        assertEquals("Missing Trial", dto.getFailureReason());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals(TestSchema.user.getLoginName(), dto.getUserLoginName());
        assertEquals(TestSchema.user.getLoginName(), dto.getUserLoginNameStripped());

    }


}

