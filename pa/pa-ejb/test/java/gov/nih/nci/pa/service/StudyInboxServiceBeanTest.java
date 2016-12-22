/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.enums.StudyInboxSectionCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Denis G. Krylov
 * 
 */
public class StudyInboxServiceBeanTest extends AbstractHibernateTestCase {

    private StudyInboxServiceBean bean = new StudyInboxServiceBean();
    private CSMUserUtil csmUtil;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        csmUtil = new MockCSMUserService();
        CSMUserService.setInstance(csmUtil);
        UsernameHolder.setUser(TestSchema.getUser().getLoginName());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.StudyInboxServiceBean#acknowledge(gov.nih.nci.iso21090.Ii, gov.nih.nci.pa.enums.StudyInboxSectionCode)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testAcknowledge() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyInbox inbox = createStudyInbox();
        s.save(inbox);
        s.flush();

        AbstractStudyProtocolConverter.setCsmUserUtil(csmUtil);
        bean.acknowledge(IiConverter.convertToIi(inbox.getId()),
                StudyInboxSectionCode.BOTH);

        StudyInbox ackInbox = (StudyInbox) s.get(StudyInbox.class,
                inbox.getId());
        assertNotNull(ackInbox.getCloseDate());

    }
    
    @Test
    public final void testAcknowledgeAdmin() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyInbox inbox = createStudyInbox();
        inbox.setAdmin(true);
        inbox.setScientific(true);
        s.save(inbox);
        s.flush();

        AbstractStudyProtocolConverter.setCsmUserUtil(csmUtil);
        bean.acknowledge(IiConverter.convertToIi(inbox.getId()),
                StudyInboxSectionCode.ADMIN);

        StudyInbox ackInbox = (StudyInbox) s.get(StudyInbox.class,
                inbox.getId());
        assertNull(ackInbox.getCloseDate());
        assertNotNull(ackInbox.getAdminCloseDate());
        assertEquals(ackInbox.getAdminAcknowledgedUser().getUserId().longValue(), 1L);
    }
    
    @Test
    public final void testAcknowledgeScientific() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyInbox inbox = createStudyInbox();
        inbox.setAdmin(true);
        inbox.setScientific(true);
        s.save(inbox);
        s.flush();

        AbstractStudyProtocolConverter.setCsmUserUtil(csmUtil);
        bean.acknowledge(IiConverter.convertToIi(inbox.getId()),
                StudyInboxSectionCode.SCIENTIFIC);

        StudyInbox ackInbox = (StudyInbox) s.get(StudyInbox.class,
                inbox.getId());
        assertNull(ackInbox.getCloseDate());
        assertNotNull(ackInbox.getScientificCloseDate());
        assertEquals(ackInbox.getScientificAcknowledgedUser().getUserId().longValue(), 1L); 
    }
    
    @Test
    public final void testAcknowledgeAdminAndScientific() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyInbox inbox = createStudyInbox();
        inbox.setAdmin(true);
        inbox.setScientific(true);
        s.save(inbox);
        s.flush();

        AbstractStudyProtocolConverter.setCsmUserUtil(csmUtil);
        bean.acknowledge(IiConverter.convertToIi(inbox.getId()),
                StudyInboxSectionCode.ADMIN);
        s.flush();
        bean.acknowledge(IiConverter.convertToIi(inbox.getId()),
                StudyInboxSectionCode.SCIENTIFIC);

        StudyInbox ackInbox = (StudyInbox) s.get(StudyInbox.class,
                inbox.getId());
        assertNotNull(ackInbox.getCloseDate());
        assertNotNull(ackInbox.getAdminCloseDate());
        assertNotNull(ackInbox.getScientificCloseDate());
        assertEquals(ackInbox.getAdminAcknowledgedUser().getUserId().longValue(), 1L);
        assertEquals(ackInbox.getScientificAcknowledgedUser().getUserId().longValue(), 1L);
    }

    private StudyInbox createStudyInbox() {
        StudyInbox inbox = new StudyInbox();
        inbox.setAdmin(false);
        inbox.setComments("comments");
        inbox.setDateLastCreated(new Date());
        inbox.setOpenDate(new Timestamp(new Date().getTime()));
        inbox.setScientific(false);
        inbox.setStudyProtocol(TestSchema.createStudyProtocolObj());
        inbox.setTypeCode(StudyInboxTypeCode.UPDATE);
        return inbox;
    }

}
