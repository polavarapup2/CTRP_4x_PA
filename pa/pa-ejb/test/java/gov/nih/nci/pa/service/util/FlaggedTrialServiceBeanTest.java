/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolFlag;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.noniso.dto.StudyProtocolFlagDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
public class FlaggedTrialServiceBeanTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    FlaggedTrialServiceBean bean;

    @Before
    public void init() throws Exception {
        bean = (FlaggedTrialServiceBean) getEjbBean(FlaggedTrialServiceBean.class);
        TestSchema.primeData();

        StudyProtocol sp = TestSchema.studyProtocols.get(0);
        DocumentWorkflowStatus dws = TestSchema
                .createDocumentWorkflowStatus(sp);
        PaHibernateUtil.getCurrentSession().save(dws);
        PaHibernateUtil.getCurrentSession().flush();

        UsernameHolder
                .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#getActiveFlaggedTrials()}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetActiveFlaggedTrials() throws PAException {
        assertTrue(bean.getActiveFlaggedTrials().isEmpty());
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        StudyProtocolFlag flag = verifyNewlyFlaggedTrial();

        assertEquals(1, bean.getActiveFlaggedTrials().size());
        final StudyProtocolFlagDTO dto = bean.getActiveFlaggedTrials().get(0);
        assertEquals(flag.getId().longValue(), dto.getId());
        assertEquals("Added flagged trial", dto.getComments());
        assertEquals("Abstractor, Super", dto.getFlaggedBy());
        assertEquals(flag.getDateFlagged(), dto.getFlaggedOn());
        assertEquals("NCI-2009-00001", dto.getNciID());

        bean.delete(flag.getId(), "Now deleted");
        assertTrue(bean.getActiveFlaggedTrials().isEmpty());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#updateFlaggedTrial(java.lang.Long, gov.nih.nci.pa.enums.StudyFlagReasonCode, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testUpdateFlaggedTrial() throws PAException {
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        StudyProtocolFlag flag = verifyNewlyFlaggedTrial();

        bean.updateFlaggedTrial(flag.getId(),
                StudyFlagReasonCode.DO_NOT_SEND_TO_CLINICALTRIALS_GOV,
                "Updated!");
        PaHibernateUtil.getCurrentSession().refresh(flag);

        assertEquals(StudyFlagReasonCode.DO_NOT_SEND_TO_CLINICALTRIALS_GOV,
                flag.getFlagReason());
        assertEquals("Updated!", flag.getComments());
        assertNull(flag.getDateDeleted());
        assertNull(flag.getDeletingUser());
        assertNull(flag.getDeleteComments());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#addFlaggedTrial(java.lang.String, gov.nih.nci.pa.enums.StudyFlagReasonCode, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testAddFlaggedTrial() throws PAException {

        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        verifyNewlyFlaggedTrial();

    }

    @Test
    public final void testUnableToAddDuplicateFlags() throws PAException {

        thrown.expect(PAException.class);
        thrown.expectMessage(FlaggedTrialServiceBean.DUPE_FLAG);

        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");

        PaHibernateUtil.getCurrentSession().flush();

    }

    @Test
    public final void testUnableToEditAndCreateDuplicateFlags()
            throws PAException {

        final Session s = PaHibernateUtil.getCurrentSession();

        thrown.expect(GenericJDBCException.class);

        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial #1");
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_SEND_TO_CLINICALTRIALS_GOV,
                "Added flagged trial #2");

        s.flush();

        StudyProtocolFlag flag = (StudyProtocolFlag) s
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("studyProtocol",
                        TestSchema.studyProtocols.get(0)))
                .add(Restrictions.eq("deleted", Boolean.FALSE))
                .add(Restrictions.eq("flagReason",
                        StudyFlagReasonCode.DO_NOT_SEND_TO_CLINICALTRIALS_GOV))
                .uniqueResult();
        bean.updateFlaggedTrial(flag.getId(),
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES, "This must fail");
        s.flush();

    }

    @Test
    public final void testTrialNotFound() throws PAException {

        thrown.expect(PAException.class);
        thrown.expectMessage("This trial does not exist in CTRP. Please enter a different NCI Identifier");

        bean.addFlaggedTrial("NCI-2009-823490",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");

    }

    /**
     * @throws HibernateException
     */
    private StudyProtocolFlag verifyNewlyFlaggedTrial()
            throws HibernateException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocolFlag flag = (StudyProtocolFlag) s
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("studyProtocol",
                        TestSchema.studyProtocols.get(0)))
                .add(Restrictions.eq("deleted", Boolean.FALSE))
                .add(Restrictions.eq("flagReason",
                        StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES))
                .uniqueResult();
        verifyFieldsAreSetAfterFlagging(flag);
        assertNull(flag.getDateDeleted());
        assertNull(flag.getDeletingUser());
        assertNull(flag.getDeleteComments());
        return flag;
    }

    /**
     * @param flag
     */
    private void verifyFieldsAreSetAfterFlagging(StudyProtocolFlag flag) {
        assertTrue(DateUtils.isSameDay(new Date(), flag.getDateFlagged()));
        assertEquals(
                "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor",
                flag.getFlaggingUser().getLoginName());
        assertEquals("Abstractor, Super", flag.getFlaggingUserName());
        assertEquals("Added flagged trial", flag.getComments());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#delete(java.lang.Long, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testDelete() throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        StudyProtocolFlag flag = verifyNewlyFlaggedTrial();

        bean.delete(flag.getId(), "Now deleted");
        s.flush();
        flag = (StudyProtocolFlag) s
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("studyProtocol",
                        TestSchema.studyProtocols.get(0)))
                .add(Restrictions.eq("deleted", Boolean.TRUE)).uniqueResult();

        verifyFieldsAreSetAfterFlagging(flag);
        verifyFieldsAreSetAfterDeleting(flag);

    }

    /**
     * @param flag
     */
    private void verifyFieldsAreSetAfterDeleting(StudyProtocolFlag flag) {
        assertTrue(DateUtils.isSameDay(new Date(), flag.getDateDeleted()));
        assertEquals(
                "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor",
                flag.getDeletingUser().getLoginName());
        assertEquals("Abstractor, Super", flag.getDeletingUserName());
        assertEquals("Now deleted", flag.getDeleteComments());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#getDeletedFlaggedTrials()}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetDeletedFlaggedTrials() throws PAException {
        assertTrue(bean.getDeletedFlaggedTrials().isEmpty());
        assertTrue(bean.getActiveFlaggedTrials().isEmpty());
        bean.addFlaggedTrial("NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_PROCESS_CDUS_FILES,
                "Added flagged trial");
        StudyProtocolFlag flag = verifyNewlyFlaggedTrial();
        assertFalse(bean.getActiveFlaggedTrials().isEmpty());
        assertTrue(bean.getDeletedFlaggedTrials().isEmpty());

        bean.delete(flag.getId(), "Now deleted");

        assertEquals(1, bean.getDeletedFlaggedTrials().size());
        assertTrue(bean.getActiveFlaggedTrials().isEmpty());

        final StudyProtocolFlagDTO dto = bean.getDeletedFlaggedTrials().get(0);
        assertEquals(flag.getId().longValue(), dto.getId());
        assertEquals("Now deleted", dto.getDeleteComments());
        assertEquals("Abstractor, Super", dto.getDeletedBy());
        assertEquals(flag.getDateDeleted(), dto.getDeletedOn());
        assertEquals("NCI-2009-00001", dto.getNciID());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FlaggedTrialServiceBean#isFlagged(gov.nih.nci.pa.iso.dto.StudyProtocolDTO, gov.nih.nci.pa.enums.StudyFlagReasonCode)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testIsFlagged() throws PAException {
        StudyProtocol sp = TestSchema.studyProtocols.get(0);
        StudyProtocolDTO dto = StudyProtocolConverter
                .convertFromDomainToDTO(sp);
        assertFalse(bean
                .isFlagged(
                        dto,
                        StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES));

        bean.addFlaggedTrial(
                "NCI-2009-00001",
                StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES,
                "Added flagged trial");

        assertTrue(bean
                .isFlagged(
                        dto,
                        StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES));
        assertFalse(bean.isFlagged(dto,
                StudyFlagReasonCode.DO_NOT_SEND_TO_CLINICALTRIALS_GOV));

    }

}
