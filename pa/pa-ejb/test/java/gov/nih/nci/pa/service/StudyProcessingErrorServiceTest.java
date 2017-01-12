/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.AccountTest;
import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.domain.KeystoreTest;
import gov.nih.nci.pa.domain.StudyProcessingError;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProcessingErrorDTO;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.File;
import java.security.Security;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

/**
 * @author gunnikrishnan
 *
 */
public class StudyProcessingErrorServiceTest extends AbstractEjbTestCase {
    private StudyProcessingErrorBeanLocal bean;
    private StudyProtocolService studySvc; 
    private GreenMail mailServer;
    GreenMailUser user;

    private File keystoreFile;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.primeData();
        
        TestSchema.addUpdObject(TestSchema.createDocumentWorkflowStatus(TestSchema.studyProtocols.get(0)));
        
        PaHibernateUtil.getCurrentSession().flush();
        
        bean = (StudyProcessingErrorBeanLocal) getEjbBean(StudyProcessingErrorBeanLocal.class);
        studySvc = (StudyProtocolService) getEjbBean(StudyProtocolBeanLocal.class);
        
        keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        keystoreFile.deleteOnExit();

        KeystoreTest.setFinalStatic(
                Keystore.class.getDeclaredField("KEYSTORE_FILE"), keystoreFile);
        
        Security.setProperty("ssl.SocketFactory.provider",
                DummySSLSocketFactory.class.getName());
        createMailAccount();
        mailServer = new GreenMail(ServerSetupTest.IMAPS);
        mailServer.start();
        String mailUser ="ctrp@nci.gov";
        String mailPass = "password";
                
        user = mailServer.setUser(mailUser, mailUser, mailPass);
    }

    @After
    public void stop(){
        if (mailServer != null)
            mailServer.stop();
        if (keystoreFile != null)
            keystoreFile.delete();
    }
    
    @Test
    public void testCreateStudyProcessingError() throws Exception {
        StudyProcessingError spe = new StudyProcessingError();
        spe.setStudyProtocol(TestSchema.studyProtocols.get(0));
        spe.setCmsTicketId("cmsTicketId");
        spe.setErrorDate(new Timestamp(System.currentTimeMillis()));
        spe.setErrorMessage("Some error message");
        spe.setActionTaken("Some action taken");
        spe.setComment("Some comment");
        spe.setRecurringError(true);
        spe.setResolutionDate(new Timestamp(System.currentTimeMillis()));
        Ii speId = bean.createStudyProcessingError(spe);
        assertFalse(ISOUtil.isIiNull(speId));
    }

    @Test
    public void testGetStudyProcessingError() throws Exception {
        StudyProcessingErrorDTO spe = bean
                .getStudyProcessingError(TestSchema.studyProtocolErrorIds
                        .get(0));
        assertNotNull(spe);
        assertEquals("cmsTicketId", spe.getCmsTicketId());
        assertEquals("Some error message", spe.getErrorMessage());
        assertEquals("Some action taken", spe.getActionTaken());
        assertEquals("Some comment", spe.getComment());
        assert (spe.getRecurringError());
    }

    @Test
    public void testUpdateProcessingError() throws Exception {
        StudyProcessingErrorDTO spe = bean
                .getStudyProcessingError(TestSchema.studyProtocolErrorIds
                        .get(0));
        assertNotNull(spe);
        spe.setActionTaken("New action taken");
        spe.setCmsTicketId("NewcsmTicketId");
        spe.setErrorMessage("New error message");
        spe.setComment("New comment");
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        spe.setErrorDate(ts);
        spe.setResolutionDate(ts);
        spe.setRecurringError(false);

        spe = bean.updateStudyProcessingError(spe);

        assertEquals("NewcsmTicketId", spe.getCmsTicketId());
        assertEquals("New error message", spe.getErrorMessage());
        assertEquals("New action taken", spe.getActionTaken());
        assertEquals("New comment", spe.getComment());
        assertFalse(spe.getRecurringError());
        assertEquals(ts.getTime(), spe.getErrorDate().getTime());
        assertEquals(ts.getTime(), spe.getResolutionDate().getTime());
    }

    
    @Test
    public void testProcessCTGovErrorEmail() throws PAException{
        String message = "Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 60 (NCI-2009-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "\n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n";
        Date d = new Date();
        bean.processUploadErrorEmail(message, d);
       
        
        //Test recurrent with in 30 days
        String recurrentmessage = "Batch Job J0004413 completed successfully. \n"+
                    "Number of new studies 0 \n"+
                    "Number of changed studies 71 \n"+
                    "Number of unchanged studies 293 \n"+
                    "Number of failures 0 \n"+
                    "Total number of studies processed 364 \n"+
                    "Total number of studies submitted 364 \n"+
                    "\n"+
                    "------------------------ \n"+
                    "Errors Encountered \n"+
                    "\n"+
                    "Study Number 24 (NCI-2010-00001) \n"+
                    "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                    "\n"+
                    "Study Number 50 (NCI-2009-00002) \n"+
                    "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                    "\n"+
                    "Study Number 60 (NCI-2009-00001) \n"+
                    "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                    "\n"+
                                        "\n"+
                    "------------------------ \n"+
                    "\n"+
                    "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                    "The Job ID for this job is J0004413.\n";
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DATE, 20);
            bean.processUploadErrorEmail(recurrentmessage, c1.getTime());
            
            // Test recurrent beyond 30 days
            String recurrentmessage2 = "Batch Job J0004413 completed successfully. \n"+
                    "Number of new studies 0 \n"+
                    "Number of changed studies 71 \n"+
                    "Number of unchanged studies 293 \n"+
                    "Number of failures 0 \n"+
                    "Total number of studies processed 364 \n"+
                    "Total number of studies submitted 364 \n"+
                    "\n"+
                    "------------------------ \n"+
                    "Errors Encountered \n"+
                    "\n"+
                    "Study Number 24 (NCI-2010-00001) \n"+
                    "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                    "\n"+
                    "Study Number 50 (NCI-2009-00002) \n"+
                    "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                    "\n"+
                    "\n"+
                    "\n"+
                    "Study Number 108 (NCI-2009-00001) \n"+
                    "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                    "\n"+
                    "------------------------ \n"+
                    "\n"+
                    "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                    "The Job ID for this job is J0004413.\n";
            Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.DATE, 31);
            bean.processUploadErrorEmail(recurrentmessage2, c2.getTime());
            
            PaHibernateUtil.getCurrentSession().flush();
            PaHibernateUtil.getCurrentSession().clear();
            
            List<StudyProcessingError> errors = studySvc.getStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                    .get(0))).getStudyProcessingErrors();
            assertEquals(5, errors.size());
            assertEquals("primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past.", errors.get(1).getErrorMessage());
            assertEquals(d.getTime(), errors.get(1).getErrorDate().getTime());
            assertEquals(false, errors.get(1).getRecurringError());
            assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(2).getErrorMessage());
            assertEquals(d.getTime(), errors.get(2).getErrorDate().getTime());
            assertEquals(false, errors.get(2).getRecurringError());
            assertEquals("primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past.", errors.get(3).getErrorMessage());
            assertEquals(c1.getTimeInMillis(), errors.get(3).getErrorDate().getTime());
            assertEquals(true, errors.get(3).getRecurringError());
            assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(4).getErrorMessage());
            assertEquals(c2.getTimeInMillis(),errors.get(4).getErrorDate().getTime());
            assertEquals(false, errors.get(4).getRecurringError());
    }
    
    @Test
    public void testProcessStudyUploadErrors() throws Exception {
      
        MimeMessage message = new MimeMessage( (Session) null);
        message.setFrom(new InternetAddress("ctgov@nci.gov"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                "ctrp@nci.gov"));
        message.setSubject("PRS Protocol Upload Notification");
        message.setText("Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "\n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n");
 
        // use greenmail to store the message
        user.deliver(message);
        bean.processStudyUploadErrors();
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        
        List<StudyProcessingError> errors =  studySvc.getStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                .get(0))).getStudyProcessingErrors();
        assertEquals(2, errors.size());
        assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(1).getErrorMessage());
        assert(errors.get(1).getErrorDate().getTime() - System.currentTimeMillis() < 2000 );
        assertEquals(false, errors.get(1).getRecurringError());
    }
    
    
    @Test
    public void testProcessStudyUploadErrorsNoEmail() throws Exception {
        bean.processStudyUploadErrors();
        List<StudyProcessingError> errors =  studySvc.getStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                .get(0))).getStudyProcessingErrors();
        assertEquals(1, errors.size());
    }
    
    
    @Test
    public void testProcessStudyUploadErrorsMultipleEmails() throws Exception {
      
        MimeMessage message = new MimeMessage( (Session) null);
        message.setFrom(new InternetAddress("ctgov@nci.gov"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                "ctrp@nci.gov"));
        message.setSubject("PRS Protocol Upload Notification");
        message.setText("Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "\n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n");
 
        // use greenmail to store the message
        user.deliver(message);
        
        message = new MimeMessage( (Session) null);
        message.setFrom(new InternetAddress("ctgov@nci.gov"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                "ctrp@nci.gov"));
        message.setSubject("PRS Protocol Upload Notification");
        message.setText("Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "\n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription2 -- Textblock contains an invalid character at position: 3000 \n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n");
 
        // use greenmail to store the message
        user.deliver(message);
        bean.processStudyUploadErrors();
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        
        List<StudyProcessingError> errors =  studySvc.getStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                .get(0))).getStudyProcessingErrors();
        assertEquals(4, errors.size());
        assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(1).getErrorMessage());
        assert(errors.get(1).getErrorDate().getTime() - System.currentTimeMillis() < 5000 );
        assertEquals(false, errors.get(1).getRecurringError());
        assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(2).getErrorMessage());
        assert(errors.get(2).getErrorDate().getTime() - System.currentTimeMillis() < 5000 );
        assertEquals(true, errors.get(2).getRecurringError());
        assertEquals("detailedDescription2 -- Textblock contains an invalid character at position: 3000", errors.get(3).getErrorMessage());
        assert(errors.get(3).getErrorDate().getTime() - System.currentTimeMillis() < 5000 );
        assertEquals(false, errors.get(3).getRecurringError());
    }
    
    @Test
    public void testGetStudyProcessingErrrorsByStudy() throws Exception{
        MimeMessage message = new MimeMessage( (Session) null);
        message.setFrom(new InternetAddress("ctgov@nci.gov"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                "ctrp@nci.gov"));
        message.setSubject("PRS Protocol Upload Notification");
        message.setText("Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "\n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n");
 
        // use greenmail to store the message
        user.deliver(message);
        
        message = new MimeMessage( (Session) null);
        message.setFrom(new InternetAddress("ctgov@nci.gov"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                "ctrp@nci.gov"));
        message.setSubject("PRS Protocol Upload Notification");
        message.setText("Batch Job J0004413 completed successfully. \n"+
                "Number of new studies 0 \n"+
                "Number of changed studies 71 \n"+
                "Number of unchanged studies 293 \n"+
                "Number of failures 0 \n"+
                "Total number of studies processed 364 \n"+
                "Total number of studies submitted 364 \n"+
                "\n"+
                "------------------------ \n"+
                "Errors Encountered \n"+
                "\n"+
                "Study Number 24 (NCI-2010-00001) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "Study Number 50 (NCI-2009-00002) \n"+
                "ERROR: primaryCompletionDate -- Anticipated Primary Completion Date cannot be in the past. \n"+
                "\n"+
                "\n"+
                "\n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription -- Textblock contains an invalid character at position: 1463 \n"+
                "Study Number 108 (NCI-2009-00001) \n"+
                "ERROR: detailedDescription2 -- Textblock contains an invalid character at position: 3000 \n"+
                "\n"+
                "------------------------ \n"+
                "\n"+
                "You can check the details via the Check Upload Status option, under the PRS Home page's Records menu. \n"+
                "The Job ID for this job is J0004413.\n");
 
        // use greenmail to store the message
        user.deliver(message);
        bean.processStudyUploadErrors();
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        
        List<StudyProcessingErrorDTO> errors = bean.getStudyProcessingErrorByStudy(TestSchema.studyProtocolIds
                .get(0));  
        assertEquals(3, errors.size());
        assertEquals("detailedDescription2 -- Textblock contains an invalid character at position: 3000", errors.get(0).getErrorMessage());
        assert(errors.get(0).getErrorDate().getTime() - System.currentTimeMillis() < 5000 );
        assertEquals(false, errors.get(0).getRecurringError());
        assertEquals("detailedDescription -- Textblock contains an invalid character at position: 1463", errors.get(1).getErrorMessage());
        assert(errors.get(1).getErrorDate().getTime() - System.currentTimeMillis() < 5000 );
        assertEquals(true, errors.get(1).getRecurringError());
        
    }
    
    private void createMailAccount() {
        final org.hibernate.Session s = PaHibernateUtil.getCurrentSession();
        Account a = new Account();
        a.setAccountName("CTGovUploadErrorEmailAccount");
        a.setExternalSystem(ExternalSystemCode.MAILBOX);
        a.setUsername("ctrp@nci.gov");
        a.setEncryptedPassword(Hex
                .encodeHexString(AccountTest.encrypt("password", new Keystore().getKeypair().getPublic())));
        s.save(a);
        s.flush();
    }

}
