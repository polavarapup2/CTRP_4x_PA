/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import gov.nih.nci.pa.domain.EmailLog;
import gov.nih.nci.pa.enums.OpOutcomeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author dkrylov
 * 
 */
public class NewMailManagerServiceTest extends AbstractEjbTestCase {	
	
    
    @Before
    public void setUp() throws Exception {             
        
        clearEmailLog();
    }

    /**
     * @throws HibernateException
     */
    private void clearEmailLog() throws HibernateException {
        PaHibernateUtil.getCurrentSession().createQuery("delete from EmailLog")
                .executeUpdate();
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
    }
    
    @Test
    public void testJobFailureEmail() throws InterruptedException {
    	 Thread.sleep(5000);
    	     	
    	 
    	 MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
    	 bean.sendJobFailureNotification("TestJob", ExceptionUtils.getFullStackTrace(new RuntimeException("This is a test")));
    	 
    	 
         clearEmailLog();         
         waitForEmailsToArrive(1);           
         
         Iterator emailIter = smtp.getReceivedEmail();
         SmtpMessage email = (SmtpMessage)emailIter.next();
         assertTrue(email.getHeaderValue("Subject").contains("CTRP nightly job failure: TestJob"));
         assertTrue(email.getBody().contains("<pre>java.lang.RuntimeException: This is a test	at gov.nih.nci.pa.service.util.NewMailManagerServiceTest.testJobFailureEmail"));	
         assertTrue(email.getBody().contains("Dear Sir or Madam,<br /><br />This is to notify that CTRP nightly job <strong>TestJob</strong> failed its processing"));      
         assertTrue(email.getBody().contains("Please consult the server logs for more details."));
    }

    @Test
    public void emailLogFailedSend() throws PAException, InterruptedException {
        stopSMTP();

        Thread.sleep(5000);
        clearEmailLog();

        MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
        bean.sendMailWithAttachment("to@example.com", "from@example.com",
                Arrays.asList("cc1@example.com", "cc2@example.com"),
                "emailLogPlainTextNoAttachment", "Plain Text Body",
                new File[0], false);

        EmailLog log = waitForEmailLogToAppear();
        assertTrue(DateUtils.isSameDay(new Date(), log.getDateSent()));
        assertEquals(OpOutcomeCode.FAILURE, log.getOutcome());
        assertTrue(log.getErrors().contains("java.net.ConnectException"));
        assertEquals("from@example.com", log.getSender());
        assertEquals("to@example.com", log.getRecipient());
        assertEquals("cc1@example.com, cc2@example.com", log.getCc());
        assertEquals("log@example.com", log.getBcc());
        assertEquals("emailLogPlainTextNoAttachment", log.getSubject());
        assertEquals("Plain Text Body", log.getBody());
        assertEquals(0, log.getAttachments().size());

    }

    @Test
    public void emailLogPlainTextNoAttachment() throws PAException,
            InterruptedException {

        Thread.sleep(5000);
        clearEmailLog();

        MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
        bean.sendMailWithAttachment("to@example.com", "from@example.com",
                Arrays.asList("cc1@example.com", "cc2@example.com"),
                "emailLogPlainTextNoAttachment", "Plain Text Body",
                new File[0], false);
        waitForEmailsToArrive(1);

        EmailLog log = waitForEmailLogToAppear();
        verifyBaseLogFields(log);
        assertEquals("Plain Text Body", log.getBody());
        assertEquals(0, log.getAttachments().size());

    }

    @Test
    public void emailLogPlainTextWithAttachment() throws PAException,
            InterruptedException, IOException {

        Thread.sleep(5000);
        clearEmailLog();

        File file = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeByteArrayToFile(file, new byte[] { 0x01, 0x02, 0x04 });

        MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
        bean.sendMailWithAttachment("to@example.com", "from@example.com",
                Arrays.asList("cc1@example.com", "cc2@example.com"),
                "emailLogPlainTextNoAttachment", "Plain Text Body",
                new File[] { file }, false);
        waitForEmailsToArrive(1);

        EmailLog log = waitForEmailLogToAppear();
        verifyBaseLogFields(log);
        assertEquals("Plain Text Body", log.getBody());
        assertEquals(1, log.getAttachments().size());
        assertEquals(log.getAttachments().get(0).getFilename(), file.getName());
        assertTrue(Arrays.equals(log.getAttachments().get(0).getData(),
                FileUtils.readFileToByteArray(file)));

    }

    @Test
    public void emailLogHtmlTextWithAttachment() throws PAException,
            InterruptedException, IOException {

        Thread.sleep(5000);
        clearEmailLog();

        File file = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeByteArrayToFile(file, new byte[] { 0x01, 0x02, 0x04 });

        MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
        bean.sendMailWithHtmlBodyAndAttachment("to@example.com",
                "from@example.com",
                Arrays.asList("cc1@example.com", "cc2@example.com"),
                "emailLogPlainTextNoAttachment", "<h1>HTML!</h1>",
                new File[] { file }, true);
        waitForEmailsToArrive(1);

        EmailLog log = waitForEmailLogToAppear();
        verifyBaseLogFields(log);
        assertEquals("<h1>HTML!</h1>", log.getBody());
        assertEquals(1, log.getAttachments().size());
        assertEquals(log.getAttachments().get(0).getFilename(), file.getName());
        assertTrue(Arrays.equals(log.getAttachments().get(0).getData(),
                new byte[] { 0x01, 0x02, 0x04 }));

        // Since we specified delete=true above, file must be gone.
        Thread.sleep(2000L);
        assertFalse(file.exists());

    }

    @Test
    public void emailLogHtmlTextNoAttachment() throws PAException,
            InterruptedException {

        Thread.sleep(5000);
        clearEmailLog();

        MailManagerBeanLocal bean = getEjbBean(MailManagerBeanLocal.class);
        bean.sendMailWithHtmlBody("from@example.com", "to@example.com",
                Arrays.asList("cc1@example.com", "cc2@example.com"),
                "emailLogPlainTextNoAttachment", "<h1>HTML!</h1>");
        waitForEmailsToArrive(1);

        EmailLog log = waitForEmailLogToAppear();
        verifyBaseLogFields(log);
        assertEquals("<h1>HTML!</h1>", log.getBody());
        assertEquals(0, log.getAttachments().size());

    }

    /**
     * @param log
     */
    private void verifyBaseLogFields(EmailLog log) {
        assertTrue(DateUtils.isSameDay(new Date(), log.getDateSent()));
        assertEquals(OpOutcomeCode.SUCCESS, log.getOutcome());
        assertEquals("", log.getErrors());
        assertEquals("from@example.com", log.getSender());
        assertEquals("to@example.com", log.getRecipient());
        assertEquals("cc1@example.com, cc2@example.com", log.getCc());
        assertEquals("log@example.com", log.getBcc());
        assertEquals("emailLogPlainTextNoAttachment", log.getSubject());
    }

    private EmailLog waitForEmailLogToAppear() throws InterruptedException {
        long stamp = System.currentTimeMillis();
        while (PaHibernateUtil.getCurrentSession().createQuery("from EmailLog")
                .list().size() == 0
                && System.currentTimeMillis() - stamp < 1000 * 20) {
            Thread.sleep(500);
        }
        return (EmailLog) PaHibernateUtil.getCurrentSession()
                .createQuery("from EmailLog order by dateSent desc")
                .setMaxResults(1).uniqueResult();

    }

    private void waitForEmailsToArrive(int numberOfEmailsToWaitFor)
            throws InterruptedException {
        long stamp = System.currentTimeMillis();
        while (smtp.getReceivedEmailSize() < numberOfEmailsToWaitFor
                && System.currentTimeMillis() - stamp < 1000 * 20) {
            Thread.sleep(500);
        }
        assertEquals(numberOfEmailsToWaitFor, smtp.getReceivedEmailSize());
    }

}
