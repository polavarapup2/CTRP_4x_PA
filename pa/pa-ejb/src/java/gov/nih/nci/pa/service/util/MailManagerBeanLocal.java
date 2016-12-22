/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.service.util;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.EmailAttachment;
import gov.nih.nci.pa.domain.EmailLog;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.OpOutcomeCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.xml.serialize.LineSeparator;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.engine.SessionFactoryImplementor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength"
    , "PMD.ConsecutiveAppendsShouldReuse" })
public class MailManagerBeanLocal implements MailManagerServiceLocal, TemplateLoader {

    private static final String LEAD_ORG_NAME = "${leadOrgName}";
    private static final String LEAD_ORG_ID = "${leadOrgID}";
    private static final String FROMADDRESS = "fromaddress";
    private static final String PLACEHOLDER_0 = "{0}";
    private static final String NONE = "NONE";
    private static final String USER_NAME = "${name}";
    private static final String SEND_MAIL_ERROR = "Send Mail error";
    private static final String DEADLINE = "${Deadline}";
    private static final String HOLD_REASON = "${HoldReason}";
    private static final String HOLD_DATE = "${HoldDate}";
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final Logger LOG = Logger.getLogger(MailManagerBeanLocal.class);
    private static final int LINE_WIDTH = 65;
    private static final String TSR = "TSR_";
    private static final String EXTENSION_RTF = ".rtf";
    private static final String EXTENSION_HTML = ".html";
    private static final String CURRENT_DATE = "${CurrentDate}";
    private static final String NCI_TRIAL_IDENTIFIER = "${nciTrialIdentifier}";
    private static final String OWNER_NAME = "${SubmitterName}";
    private static final String LEAD_ORG_TRIAL_IDENTIFIER = "${leadOrgTrialIdentifier}";
    private static final String RECEIPT_DATE = "${receiptDate}";
    private static final String TRIAL_TITLE = "${trialTitle}";
    private static final String AMENDMENT_NUMBER = "${amendmentNumber}";
    private static final String AMENDMENT_DATE = "${amendmentDate}";
    private static final String UPDATES = "${updates}";
    private static final String USERNAME_SEARCH_BODY_PROPERTY = "user.usernameSearch.body";
    private static final String USERNAME_SEARCH_SUBJECT_PROPERTY = "user.usernameSearch.subject";
    private static final String ERRORS = "${errors}";
    private static final int SMTP_TIMEOUT = 120000;
    private static final String CDE_REQUEST_TO_EMAIL = "CDE_REQUEST_TO_EMAIL";
    private static final String SIR_OR_MADAM = "Sir or Madam";
    private static final String OTHER_TRIAL_IDENTIFIER = "${otherTrialIdentifiers}";
    private static final String SUBMISSION_DATE = "${submissionDate}";
    private static final String CTEP_TRIAL_IDENTIFIER = "${ctepTrialIdentifier}";
    private static final String DCP_TRIAL_IDENTIFIER = "${dcpTrialIdentifier}";
    private static final String NCT_IDENTIFIER = "${nctIdentifier}";
    private static final String EMAIL_ADDRESS = "${emailAddress}";
    private static final String TABLE_ROWS = "${tableRows}";
    private static final String EFFECTIVE_DATE = "TrialDataVerificationNotificationsEffectiveDate";
    private static final String DUE_DATE = "${dueDate}";
    private static final String TOTAL_SUBMITTED = "${totalSubmitted}";
    private static final String SUCCESSFUL_UPDATES = "${successfulUpdates}";
    private static final String FAILURES = "${failures}";
    private static final String N_VALUE = "${n_value}";
    private static final String YES_VAL = "YES";
    private static final String NO_VAL = "NO";
    private static final String TRIAL_IDENTIFIERS = "${trialIdentifiers}";
    
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService;
    @EJB
    private TSRReportGeneratorServiceLocal tsrReportGeneratorService;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkflStatusSrv;
    @EJB
    private StudySiteServiceLocal studySiteService;    
    
    private final ExecutorService mailDeliveryExecutor = Executors
            .newSingleThreadExecutor();
    private final ExecutorService mailLogExecutor = Executors
            .newSingleThreadExecutor();
    
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    private final Configuration cfg;
    { //NOPMD
        cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setWhitespaceStripping(false);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setTemplateLoader(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTSREmail(Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO =
                protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

            String body = "";
            String amendNumber = "";
            if (spDTO.getAmendmentNumber() != null) {
                amendNumber = spDTO.getAmendmentNumber();
            }
            if (spDTO.getAmendmentDate() != null) {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.amend.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.amend.body");
                }
            } else if (spDTO.isProprietaryTrial()) {
                body = lookUpTableService.getPropertyValue("tsr.proprietary.body");
            } else {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.body");
                }
            }
            
            body = commonMailBodyReplacements(spDTO, body);
            body = body.replace("${xmlFileName}", spDTO.getNciIdentifier() + ".xml");
            body = body.replace(AMENDMENT_NUMBER, amendNumber);
            if (spDTO.getAmendmentDate() != null) {
                body = body.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
            }

            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            StringBuffer sb2 = new StringBuffer(folderPath);
            StringBuffer sb3 = new StringBuffer(folderPath);
            String rtfTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb2, EXTENSION_RTF);
            String htmlTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb3, EXTENSION_HTML);
            String mailSubject = "";

            if (spDTO.isProprietaryTrial()) {
                File[] attachments = {new File(rtfTsrFile), new File(htmlTsrFile)};
                mailSubject = lookUpTableService.getPropertyValue("tsr.proprietary.subject");
                sendEmail(spDTO, body, attachments, mailSubject, false, true);
            } else if (BooleanUtils.isTrue(spDTO.getCtgovXmlRequiredIndicator())) {
                String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
                File[] attachments = {new File(xmlFile), new File(rtfTsrFile), new File(htmlTsrFile)};

                if (spDTO.getAmendmentDate() != null) {
                    mailSubject = lookUpTableService.getPropertyValue("tsr.amend.subject");
                } else {
                    mailSubject = lookUpTableService.getPropertyValue("tsr.subject");
                }
                sendEmail(spDTO, body, attachments, mailSubject, false, true);
            } else {
                File[] attachments = {new File(rtfTsrFile), new File(htmlTsrFile)};

                if (spDTO.getAmendmentDate() != null) {
                    mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.amend.subject");
                } else {
                    mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.subject");
                }
                sendEmail(spDTO, body, attachments, mailSubject, false, true);
            }

        } catch (Exception e) {
            throw new PAException("Exception occured while sending TSR Report to submitter", e);
        }
    }

    
    private String getCTEPIdentifier(Long studyProtocolID) throws PAException {
        return paServiceUtils.getStudyIdentifier(
                IiConverter.convertToStudyProtocolIi(studyProtocolID),
                PAConstants.CTEP_IDENTIFIER_TYPE);
    }
    
    private String getDCPIdentifier(Long studyProtocolID) throws PAException {
        return paServiceUtils.getStudyIdentifier(
                IiConverter.convertToStudyProtocolIi(studyProtocolID),
                PAConstants.DCP_IDENTIFIER_TYPE);
    }
    
    private String getTSRFile(Ii studyProtocolID, String nciIdentifier, StringBuffer pathToFile, String format)
        throws PAException {

        String fileNameDateStr =  DateFormatUtils.format(new Date(), PAConstants.TSR_DATE_FORMAT);
        String tsrFile = pathToFile.append(File.separator).append(TSR).append(nciIdentifier)
                .append(fileNameDateStr).append(format).toString();
        ByteArrayOutputStream tsrStream = null;
        OutputStream tsrFileStream = null;
        try {
            final TSRReportGeneratorServiceCachingDecorator tsrService = new TSRReportGeneratorServiceCachingDecorator(
                    tsrReportGeneratorService);
            if (StringUtils.equals(format, EXTENSION_RTF)) {
                tsrStream = tsrService
                        .generateRtfTsrReport(studyProtocolID);
            } else if (StringUtils.equals(format, EXTENSION_HTML)) {
                tsrStream = tsrService
                        .generateHtmlTsrReport(studyProtocolID);
            }
            tsrFileStream = new FileOutputStream(tsrFile);
            tsrStream.writeTo(tsrFileStream);
        } catch (Exception e) {
            throw new PAException("Exception occured while getting TSR Report to submitter", e);
        } finally {
            IOUtils.closeQuietly(tsrFileStream);
        }
        return tsrFile;
    }

    private String getXmlFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO, StringBuffer sb) throws PAException {
        String xmlFile = new String(sb.append(File.separator).append(spDTO.getNciIdentifier().toString() + ".xml"));
        try {
            if (!spDTO.isProprietaryTrial()) {

                // Format the xml only for non proprietary
                String xmlData = format(ctGovXmlGeneratorService.generateCTGovXml(studyProtocolIi));
                OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream(xmlFile));
                oos.write(xmlData);
                oos.close();
            }
        } catch (Exception e) {
            throw new PAException("Exception occured while getting XmlFile to submitter", e);
        }
        return xmlFile;
    }

    private String format(String unformattedXml) {
        Writer out = new StringWriter();
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(LINE_WIDTH);
            format.setEncoding("UTF-8");
            format.setIndenting(true);
            format.setIndent(2);
            format.setLineSeparator(LineSeparator.Web);
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return out.toString();
    }

    private Document parseXmlFile(String in) {
        DocumentBuilder db = null;
        Document doc = null;
        InputSource is = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            is = new InputSource(new StringReader(in));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (SAXException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return doc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMailWithAttachment(String mailTo, String subject, String mailBody, File[] attachments) {
        sendMailWithAttachment(mailTo, subject, mailBody, attachments, false);
    }

    private void sendMailWithAttachment(String mailTo, String subject, String mailBody, File[] attachments, 
            boolean deleteAttachments) {
        try {
            String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
            sendMailWithAttachment(mailTo, mailFrom, null , subject, mailBody, attachments, deleteAttachments);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    void sendMailWithAttachment(String mailTo, String mailFrom, // NOPMD
            List<String> mailCc, String subject, String mailBody,
            File[] attachments, boolean deleteAttachments) { 
        try {
            // Define Message
            MimeMessage message = prepareMessage(mailTo, mailFrom, mailCc, subject);
            // body
            Multipart multipart = new MimeMultipart();
            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            
            List<File> postDeletes = new ArrayList<File>();
            
            if (!ArrayUtils.isEmpty(attachments)) {
                // Add attachments to message
                for (File attachment : attachments) {
                    MimeBodyPart attPart = new MimeBodyPart();
                    final File guardCopy = createGuardCopy(attachment);
                    attPart.setDataHandler(new DataHandler(new FileDataSource(guardCopy)));
                    attPart.setFileName(attachment.getName());
                    multipart.addBodyPart(attPart);
                    
                    postDeletes.add(attachment);
                    postDeletes.add(guardCopy);
                }
            }
            message.setContent(multipart);
            // Send Message
            invokeTransportAsync(message, deleteAttachments ? postDeletes.toArray(new File[0]) : null); // NOPMD
        } catch (Exception e) {
            LOG.error(e.getMessage());
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    /**
     * Since we are sending emails asynchronously now, attachments may get
     * deleted by other parts of code before the email gets actually sent. We
     * need to create a guard copy of the attachment.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    private File createGuardCopy(File file) {
        try {
            String uuid = UUID.randomUUID().toString();
            File tempDir = new File(SystemUtils.getJavaIoTmpDir(), uuid);
            tempDir.mkdirs();
            File guard = new File(tempDir, file.getName());
            FileUtils.copyFile(file, guard);
            guard.deleteOnExit();
            return guard;
        } catch (IOException e) {
            LOG.error(e, e);
            // Well, what can we do here, just return the original file.
            return file;
        }
    }

    private void invokeTransportAsync(final MimeMessage message,
            final File[] postDeletes) {
        mailDeliveryExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(message);
                    logEmail(message, null, postDeletes);
                } catch (Exception e) {
                    LOG.error(SEND_MAIL_ERROR, e);
                    logEmail(message, e, postDeletes);
                }
            }
        });
    }
    
    @Override
    public void send(MimeMessage message) {
        invokeTransportAsync(message, null);
    }

    private void logEmail(final MimeMessage message, final Throwable error,
            final File[] postDeletes) {
        mailLogExecutor.submit(new Runnable() {
            @Override
            public void run() {
                org.hibernate.classic.Session currentSession = null;
                try {
                    SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) PaHibernateUtil
                            .getHibernateHelper().getSessionFactory();
                    currentSession = sessionFactoryImplementor.openSession(
                            null, true, false,
                            ConnectionReleaseMode.AFTER_STATEMENT);
                    currentSession.save(convertToEmailLog(message, error));
                    currentSession.flush();
                } catch (Exception e) {
                    LOG.error(e, e);
                } finally {
                    if (currentSession != null) {
                        currentSession.close();
                    }
                    if (postDeletes != null) {
                        for (File file : postDeletes) {
                            file.delete();
                        }
                    }

                }
            }
        });
    }


    private EmailLog convertToEmailLog(final MimeMessage message,
            final Throwable error) throws MessagingException, IOException {
        EmailLog e = new EmailLog();
        e.setDateSent(new Date());
        e.setOutcome(error != null ? OpOutcomeCode.FAILURE
                : OpOutcomeCode.SUCCESS);
        e.setErrors(ExceptionUtils.getFullStackTrace(error));
        e.setSender(toStr(message.getFrom()));
        e.setRecipient(toStr(message.getRecipients(RecipientType.TO)));
        e.setCc(toStr(message.getRecipients(RecipientType.CC)));
        e.setBcc(toStr(message.getRecipients(RecipientType.BCC)));
        e.setSubject(message.getSubject());

        Object body = message.getContent();
        if (body instanceof Multipart) {
            Multipart multipart = (Multipart) body;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (part.getContent() instanceof String
                        && StringUtils.isBlank(part.getFileName())) {
                    e.setBody(StringUtils.defaultString(e.getBody())
                            + part.getContent().toString());
                } else if (StringUtils.isNotBlank(part.getFileName())) {
                    attach(e, part);
                }
            }
        } else {
            e.setBody(ObjectUtils.defaultIfNull(body, StringUtils.EMPTY)
                    .toString());
        }
        return e;
    }


    private void attach(EmailLog email, BodyPart part)
            throws MessagingException, IOException {
        EmailAttachment attach = new EmailAttachment();        
        attach.setFilename(part.getFileName());
        attach.setData(IOUtils.toByteArray(part.getInputStream()));
        email.getAttachments().add(attach);
    }


    private String toStr(Address[] addr) {
        StringBuilder sb = new StringBuilder();
        if (addr != null) {
            for (Address a : addr) {
                sb.append(a.toString());
                sb.append(", ");
            }
        }
        return sb.toString().replaceFirst(", $", "");
    }

    /**
     * Sends an email notifying the submitter that the protocol is amended in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAmendNotificationMail(Ii studyProtocolIi) throws PAException {

        StudyProtocolQueryDTO spDTO = protocolQueryService
            .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        String mailBody = lookUpTableService.getPropertyValue("trial.amend.body");
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);
    }
    /**
     * 
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException ex
     */
    @Override
    public void sendAmendDSPWarningNotificationMail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
            .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        String mailBody = lookUpTableService.getPropertyValue("trial.reg.service.amend.warning.body");
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        mailBody = mailBody.replace("${changeDate}", getFormatedCurrentDate());
        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);
        List<String> trialList = new ArrayList<String>();
        trialList.add(spDTO.getNciIdentifier());
        sendCTROWarningEmail(spDTO.getLastCreated().getUserLastCreated(), spDTO
                .getSubmitterOrgName(), "AmendServiceWarning", trialList);
    }
    
    /**
     * 
     * @param studyProtocolIi studyProtocolIi
     * @param unmatchedEmails unmatchedEmails
     * @throws PAException ex
     */
    @Override
    public void sendCreateDSPWarningNotificationMail(Ii studyProtocolIi, 
         Collection<String> unmatchedEmails) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
        RegistryUser user = registryUserService.getUser(spDTO.getLastCreated().getUserLastCreated());
        if (user == null) {
            LOG.error("Registry User does not exist: " + spDTO.getLastCreated().getUserLastCreated());
            return;
        }
        String propertyPrefix = (spDTO.isProprietaryTrial()) ? "proprietarytrial" : "trial";
        String mailBody = lookUpTableService.getPropertyValue(propertyPrefix + ".service.create.register.body");
        String mailSubject = lookUpTableService.getPropertyValue(propertyPrefix + ".register.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace("${changeDate}", getFormatedCurrentDate());
        String regUserName = user.getFirstName() + " " + user.getLastName();
        mailBody = mailBody.replace(OWNER_NAME, regUserName);
        if (CollectionUtils.isNotEmpty(unmatchedEmails)) {
            mailBody = mailBody
                    .replace(
                            ERRORS,
                            lookUpTableService
                                    .getPropertyValue(
                                            "trial.register.unidentifiableOwner.sub.email.body")
                                    .replace(
                                            PLACEHOLDER_0,
                                            StringUtils.join(unmatchedEmails,
                                                    "\r\n")));
        } else {
            mailBody = mailBody.replace(ERRORS, "");
        }        
        sendMailWithHtmlBody(user.getEmailAddress(), mailSubject, mailBody);
        List<String> trialList = new ArrayList<String>();
        trialList.add(spDTO.getNciIdentifier());
        sendCTROWarningEmail(spDTO.getLastCreated().getUserLastCreated(), spDTO.getSubmitterOrgName(),
             "CreateServiceWarning", trialList);
    }
    
    /**
     * Returns a identifier header as per the FTL defined 
     * @param spDTO
     * @return String identifiers
     * @throws PAException
     */
    String getStudyIdentifiers(StudyProtocolQueryDTO spDTO) throws PAException {
        String headerIdentifiers = "";
        try {
            Template bodyFtl = cfg
                    .getTemplate("trial.identifiers.header");
        
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("spDTO", spDTO);        
        
            StringWriter body = new StringWriter();
            bodyFtl.process(root, body);
            headerIdentifiers = body.toString();
        } catch (IOException | TemplateException e) {
            LOG.error(e, e);
        }
        return headerIdentifiers;
    }
    
    /**
     * Sends a job failure email
     * @param jobName job name
     * @param errorTrace stacktrace       
     */
    @Override
    public void sendJobFailureNotification(String jobName, String errorTrace)  {
        String mailSubject = processJobFailureSubject(jobName);
        String mailBody = processJobFailureBody(jobName, errorTrace);
        try {
            String emailsToSendJobFailures = lookUpTableService.getPropertyValue("ctrp.job.failure.recipients");
            List<String> emails = null;
            if (StringUtils.isNotBlank(emailsToSendJobFailures)) {
                emails = new ArrayList<String>(Arrays.asList(emailsToSendJobFailures.split(",")));
            }        
            for (String email : emails) {
                sendMailWithHtmlBody(email, mailSubject, mailBody);
            }    
        } catch (PAException e) {
            LOG.error(e, e);
        }
    }
    
    /**
     * 
     * @param jobName
     * @return
     */
    String processJobFailureSubject(String jobName) {
        String subject = "";
        try {
            Template subjectFtl = cfg
                    .getTemplate("ctrp.job.failure.subject");
        
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("jobName", jobName);        
        
            StringWriter body = new StringWriter();
            subjectFtl.process(root, body);
            subject = body.toString();
        } catch (IOException | TemplateException e) {
            LOG.error(e, e);
        }
        return subject;
        
    }
    
    /**
     * 
     * @param jobName
     * @param errorTrace
     * @return
     */
    String processJobFailureBody(String jobName, String errorTrace) {
        String body = "";
        try {
            Template bodyFtl = cfg
                    .getTemplate("ctrp.job.failure.body");
        
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("jobName", jobName);        
            root.put("stackTrace", errorTrace);
            root.put("timeStamp", new Date().toString());
        
            StringWriter bodyWriter = new StringWriter();
            bodyFtl.process(root, bodyWriter);
            body = bodyWriter.toString();
        } catch (IOException | TemplateException e) {
            LOG.error(e, e);
        }
        return body;
    }
    
    /**
     * Common Mail Body replacements.
     * @param sp
     * @param mailBody
     * @return    
     */
    String commonMailBodyReplacements(StudyProtocolQueryDTO spDTO, String mailBody) throws PAException {        
        if (mailBody == null || spDTO == null) {
            //throw a graceful exception on blank body
            throw new PAException("Can't send email with blank data.");
        }
        String body = mailBody;
        String identifiers = getStudyIdentifiers(spDTO); 
        if (StringUtils.isNotBlank(identifiers)) {
            body = body.replace(TRIAL_IDENTIFIERS, identifiers);
        }                
        body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle());
        body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        if (spDTO.getLeadOrganizationPOId() != null) {
            body = body.replace(LEAD_ORG_ID, spDTO.getLeadOrganizationPOId().toString());
        } else {
            body = body.replace(LEAD_ORG_ID, " ");
        }
        body = body.replace(LEAD_ORG_NAME, spDTO.getLeadOrganizationName());
        body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        body = body.replace(SUBMISSION_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));
        body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
        body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));

        String nctIdentiferRow = "";
        if (StringUtils.isNotEmpty(spDTO.getNctNumber())) {
            nctIdentiferRow = lookUpTableService.getPropertyValue("nct.identifier.row");
            nctIdentiferRow = nctIdentiferRow.replace(NCT_IDENTIFIER, spDTO.getNctNumber());
        }
        body = body.replace("${nctIdentifierRow}", nctIdentiferRow);
        
        String otherIdentifiersRow = "";
        if (StringUtils.isNotEmpty(spDTO.getOtherIdentifiersAsString())) {
            otherIdentifiersRow = lookUpTableService.getPropertyValue("other.identifiers.row");
            otherIdentifiersRow = otherIdentifiersRow
                    .replace(OTHER_TRIAL_IDENTIFIER, StringUtils.join(spDTO.getOtherIdentifiers(), ","));
        }
        body = body.replace("${otherIdentifiersRow}", otherIdentifiersRow);
        
        String ctepIdRow = "";
        String ctepID = getCTEPIdentifier(spDTO.getStudyProtocolId());
        if (StringUtils.isNotEmpty(ctepID)) {
            ctepIdRow = lookUpTableService.getPropertyValue("ctep.identifier.row");
            ctepIdRow = ctepIdRow.replace(CTEP_TRIAL_IDENTIFIER, ctepID);
        }
        body = body.replace("${ctepIdentifierRow}", ctepIdRow);
        
        String dcpIdRow = "";
        String dcpID = getDCPIdentifier(spDTO.getStudyProtocolId());
        if (StringUtils.isNotEmpty(dcpID)) {
            dcpIdRow = lookUpTableService.getPropertyValue("dcp.identifier.row");
            dcpIdRow = dcpIdRow.replace(DCP_TRIAL_IDENTIFIER, dcpID);
        }
        body = body.replace("${dcpIdentifierRow}", dcpIdRow);
        
        if (body.contains("${subOrgTrialIdentifier}") || body.contains("${subOrg}")) {
            String subOrgTrialIdentifier = "";
            PAServiceUtils serviceUtil = new PAServiceUtils();
            List<StudySiteDTO> siteList =
                    studySiteService.getByStudyProtocol(IiConverter.convertToStudyProtocolIi(
                            spDTO.getStudyProtocolId()), new ArrayList<StudySiteDTO>());
            for (StudySiteDTO dto : siteList) {
                if (dto.getFunctionalCode().getCode().equals(StudySiteFunctionalCode.TREATING_SITE.getCode())) {
                    subOrgTrialIdentifier = dto.getLocalStudyProtocolIdentifier().getValue();
                    body = body.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
                    String subOrgName =
                            serviceUtil.getOrCreatePAOrganizationByIi(dto.getHealthcareFacilityIi()).getName();
                    body = body.replace("${subOrg}", subOrgName);
                    break;
                }
            }
            body = body.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
            body = body.replace("${subOrg}", "N/A");
        }
        
        return body;
    }
    
    /**
     * Common Mail Subject Replacements
     * @param spDTO
     * @param mailSubject
     * @return
     */
    String commonMailSubjectReplacements(StudyProtocolQueryDTO spDTO, String mailSubject) {
        String subject =  mailSubject;
        subject = subject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        subject = subject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        subject = subject.replace("${subOrgTrialIdentifier}", spDTO.getLocalStudyProtocolIdentifier());
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        subject = subject.replace(AMENDMENT_NUMBER, amendNumber);
        return subject;
    }
    
    /**
     * @param nciId nciId
     * @param mailSubject mailSubject
     * @return String
     */
    public String commonMailSubjectReplacementsForNCI(String nciId, String mailSubject) {
        String subject =  mailSubject;
        subject = subject.replace("${nciId}", nciId);
        
        return subject;
    }

    /**
     * send mail to submitter when amended trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
            .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.amend.accept.body");
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.accept.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);    

    }

    /**
     * Sends an email notifying the submitter that the protocol is registered in the system.
     * @param studyProtocolIi ii
     * @param unmatchedEmails email addresses that did not match any registry users during trial registration
     * @throws PAException ex
     */
    @Override
    public void sendNotificationMail(Ii studyProtocolIi, Collection<String> unmatchedEmails) throws PAException {
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolIi);
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(studyProtocolId);
        RegistryUser user = registryUserService.getUser(spDTO.getLastCreated().getUserLastCreated());
        if (user == null) {
            LOG.error("Registry User does not exist: " + spDTO.getLastCreated().getUserLastCreated());
            return;
        }
        String propertyPrefix = (spDTO.isProprietaryTrial()) ? "proprietarytrial" : "trial";
        String mailBody = lookUpTableService.getPropertyValue(propertyPrefix + ".register.body");
        String mailSubject = lookUpTableService.getPropertyValue(propertyPrefix + ".register.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        
        String regUserName = user.getFirstName() + " " + user.getLastName();
        mailBody = mailBody.replace(OWNER_NAME, regUserName);

        if (CollectionUtils.isNotEmpty(unmatchedEmails)) {
            mailBody = mailBody
                    .replace(
                            ERRORS,
                            lookUpTableService
                                    .getPropertyValue(
                                            "trial.register.unidentifiableOwner.sub.email.body")
                                    .replace(
                                            PLACEHOLDER_0,
                                            StringUtils.join(unmatchedEmails,
                                                    "\r\n")));
        } else {
            mailBody = mailBody.replace(ERRORS, "");
        }
        sendMailWithHtmlBody(user.getEmailAddress(), mailSubject, mailBody);
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendOnHoldReminder
     * (java.lang.Long, gov.nih.nci.pa.domain.StudyOnhold, java.util.Date)
     */
    @Override
    public List<String> sendOnHoldReminder(Long studyProtocolId, StudyOnhold onhold,
            Date deadline) throws PAException {     
        
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        
        String mailSubject = lookUpTableService.getPropertyValue("trial.onhold.reminder.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        String mailBody = prepareOnHoldMailBody(onhold, deadline, spDTO, true);        
        return sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, true);
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendOnHoldReminder
     * (java.lang.Long, gov.nih.nci.pa.domain.StudyOnhold, java.util.Date)
     */
    @Override
    public void sendOnHoldEmail(Long studyProtocolId, StudyOnhold onhold,
            Date deadline) throws PAException {
        
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        
        String mailSubject = lookUpTableService.getPropertyValue("trial.onhold.reminder.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        String mailBody = prepareOnHoldMailBody(onhold, deadline, spDTO, false);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, true);        
    }

    /**
     * @param spDTO
     * @param mailSubject
     * @param mailBody
     * @throws PAException
     */
    private List<String> sendEmailToAllTrialOwners(StudyProtocolQueryDTO sp, 
            String subject, String body, boolean includeSubmitter) throws PAException {
        Set<String> emails = new HashSet<String>();
        Collection<RegistryUser> recipients = buildTrialOwnerAndSubmitterList(
                sp, includeSubmitter);
        String emailAddress = null;
        for (RegistryUser recipient : recipients) {
            emailAddress = recipient.getEmailAddress();
            String regUserName = recipient.getFirstName() + " " + recipient.getLastName();
            String mailBody = body.replace(OWNER_NAME, regUserName);
            sendMailWithHtmlBody(emailAddress, subject, mailBody);
            emails.add(emailAddress);
        }
        return new ArrayList<String>(emails);
    }


    /**
     * @param sp
     * @param includeSubmitter
     * @return
     * @throws PAException
     */
    private Collection<RegistryUser> buildTrialOwnerAndSubmitterList(
            StudyProtocolQueryDTO sp, boolean includeSubmitter)
            throws PAException {
        Collection<RegistryUser> recipients = new HashSet<RegistryUser>(getStudyOwners(sp));
        if (includeSubmitter) {
            RegistryUser submitter = registryUserService.getUser(sp.getLastCreated().getUserLastCreated());
            if (submitter != null) {
                recipients.add(submitter);
            }
        }
        return recipients;
    }    
    
    
    /**
     * This method constructs the mail body for a on hold reminder email or immediate email. 
     * @param onhold StudyOnHold
     * @param deadline Deadline date for responding to the Hold. 
     * @param spDTO StudyProtocolQueryDTO
     * @param reminderMail Boolean value to pick the reminder template or the immediate mail template.
     * @return MailBody
     * @throws PAException Exception to be thrown 
     */
    private String prepareOnHoldMailBody(StudyOnhold onhold, Date deadline,
            StudyProtocolQueryDTO spDTO, boolean reminderMail)
            throws PAException {
        String mailBody = "";        
        if (reminderMail) {
            mailBody = lookUpTableService
                    .getPropertyValue("trial.onhold.reminder.body");
        } else {
            mailBody = lookUpTableService
                    .getPropertyValue("trial.onhold.email.body");
        }
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(HOLD_DATE,
                getFormatedDate(onhold.getOnholdDate()));
        mailBody = mailBody.replace(DEADLINE, getFormatedDate(deadline));
        mailBody = mailBody.replace(HOLD_REASON, StringUtils.isBlank(onhold
                .getOnholdReasonText()) ? onhold.getOnholdReasonCode()
                .getCode() : onhold.getOnholdReasonText());
        return mailBody;
    }
    

    /**
     * Sends an email to submitter when Amendment to trial is rejected by CTRO staff.
     * @param spDTO StudyProtocolQueryDTO 
     * @param rejectReason rr
     * @throws PAException ex
     */
    @Override
    public void sendAmendRejectEmail(StudyProtocolQueryDTO spDTO, String rejectReason) throws PAException {
        if (spDTO == null) {
            throw new PAException("Cannot send a rejection email for null trial.");
        }
        String mailBody = lookUpTableService.getPropertyValue("trial.amend.reject.body");
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));               
        mailBody = mailBody.replace("${reasonForRejection}", rejectReason);
        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.reject.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, true);
    }

    /**
     * Sends an email notifying the submitter that the protocol is rejected by CTRO.
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException ex
     */
    @Override
    public void sendRejectionEmail(Ii studyProtocolIi) throws PAException {
        String commentText = "";
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        List<DocumentWorkflowStatusDTO> dtoList = docWrkflStatusSrv.getByStudyProtocol(studyProtocolIi);
        for (DocumentWorkflowStatusDTO dto : dtoList) {
            if (dto.getStatusCode().getCode().equalsIgnoreCase(DocumentWorkflowStatusCode.REJECTED.getCode())
                    && dto.getCommentText() != null) {
                commentText = dto.getCommentText().getValue();
            }
        }
        String mailBody = lookUpTableService.getPropertyValue("rejection.body");
        
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace("${reasoncode}", commentText);

        String mailSubject = lookUpTableService.getPropertyValue("rejection.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);
    }

    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }

    private String getFormatedDate(Date date) {
        if (date ==  null) {
            return "(No Date)";
        }
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }

    /**
     * send mail to submitter when trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.accept.body");
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        String mailSubject = lookUpTableService.getPropertyValue("trial.accept.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);
    }

  
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendUpdateNotificationMail
     * (gov.nih.nci.iso21090.Ii, java.lang.String)
     */
    @Override
    public void sendUpdateNotificationMail(Ii studyProtocolIi, String updatesList) throws PAException {

        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));

        String mailBody = lookUpTableService.getPropertyValue("trial.update.body");
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(UPDATES,
                StringUtils.isNotBlank(updatesList) ? updatesList : NONE);
        String mailSubject = lookUpTableService.getPropertyValue("trial.update.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendEmailToAllTrialOwners(spDTO, mailSubject, mailBody, false);

    }

    /**
     * Send cde request mail.
     *
     * @param mailFrom the mail from
     * @param mailBody the mail body
     */
    @Override
    public void sendCDERequestMail(String mailFrom, String mailBody) {
        try {
            // Define Message
            MimeMessage message = prepareMessage(lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL), mailFrom, 
                    null, lookUpTableService.getPropertyValue("CDE_REQ_TO_EMAIL_SUB_PERMISSIBLE"));
            // body
            Multipart multipart = new MimeMultipart();
            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            message.setContent(multipart);
            // Send Message
            invokeTransportAsync(message, null);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        } // catch
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public void sendMarkerCDERequestMail(Ii studyProtocolIi, String from, PlannedMarkerDTO marker,
            String markerText) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO =
                protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

            boolean foundInHugo = StringUtils.isNotEmpty(CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));
            String loginName = StConverter.convertToString(marker.getUserLastCreated());
            User csmUser = CSMUserService.getInstance().getCSMUser(loginName);
            RegistryUser registryUser = registryUserService.getUser(csmUser.getLoginName());

            String body = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_BODY");
            String identifiers = getStudyIdentifiers(spDTO); 
            if (StringUtils.isNotBlank(identifiers)) {
                body = body.replace(TRIAL_IDENTIFIERS, identifiers);
            }
            body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
            body = body.replace("${trialIdentifier}", spDTO.getNciIdentifier());
            body = body.replace("${markerName}", StConverter.convertToString(marker.getName()));
            body = body.replace("${foundInHugo}", BooleanUtils.toStringYesNo(foundInHugo));

            String hugoClause = "";
            if (foundInHugo) {
                hugoClause = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_HUGO_CLAUSE");
                hugoClause = hugoClause.replace("${hugoCode}",
                        CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));
            }
            body = body.replace("${hugoCodeClause}", hugoClause);

            String markerTextClause = "";
            if (StringUtils.isNotEmpty(markerText)) {
                markerTextClause = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE");
                markerTextClause = markerTextClause.replace("${markerText}", markerText);
            }
            body = body.replace("${markerTextClause}", markerTextClause);
            if (StringUtils.isBlank(csmUser.getFirstName())
                    && StringUtils.isBlank(csmUser.getLastName())) { 
                if (registryUser != null && (StringUtils.isNotBlank(registryUser.getFirstName())
                      || StringUtils.isNotBlank(registryUser.getLastName()))) {
                            body = body.replace("${submitterName}", registryUser.getFirstName() + " "
                                     + registryUser.getLastName());           
                } else {
                            body = body.replace("${submitterName}", "");
                }
            } else {
                body = body.replace("${submitterName}", csmUser.getFirstName() + " "
                        + csmUser.getLastName());
            } 
            String toAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            List<String> toList = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(toAddress, ",");
            while (st.hasMoreElements()) {
                toList.add((String) st.nextElement());
            }
            toAddress = toList.get(0);
            String subject = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_SUBJECT");
            List<String> copyList = new ArrayList<String>();
            for (String to : toList) {
                if (!StringUtils.equals(to, toAddress)) {
                     copyList.add(to);
                }
            }
            copyList.add(lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_FROM_EMAIL"));
            if (StringUtils.isBlank(csmUser.getEmailId())) {
                if (registryUser != null && StringUtils.isNotBlank(registryUser.getEmailAddress())) {
                    copyList.add(registryUser.getEmailAddress());
                    sendMailWithHtmlBody(from, toAddress, copyList, subject, body);
                } else {
                    sendMailWithHtmlBody(from, toAddress, null, subject, body);
                }
            } else {
                copyList.add(csmUser.getEmailId());
                sendMailWithHtmlBody(from, toAddress, copyList, subject, body);
            } 
        } catch (Exception e) {
            throw new PAException("An error occured while sending a request for a new CDE", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
    public void sendMarkerAcceptanceMailToCDE(String nciIdentifier, 
            String from, PlannedMarkerDTO marker) throws PAException { //NOPMD
        try {            
            User csmUser = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser());
            RegistryUser registryUser = registryUserService.getUser(csmUser.getLoginName());
            boolean foundInHugo = StringUtils.isNotEmpty(CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));
            String hugoCode = "N/A";
            if (foundInHugo) {
                hugoCode = CdConverter.convertCdToString(marker.getHugoBiomarkerCode());
            }
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            Date date = new Date();
            StringBuilder bodyB = new StringBuilder();
            bodyB.append("Dear caDSR," + "\n\n"
            + "This is just to notify you that a marker '" 
            + StConverter.convertToString(marker.getName())
            + ",HUGO code:"
            + hugoCode
            + "' has been accepted in the CTRP Protocol Abstraction "); 
            if (StringUtils.isBlank(csmUser.getFirstName()) && StringUtils.isBlank(csmUser.getLastName())) {
                if (registryUser != null) {
                    if (StringUtils.isBlank(registryUser.getFirstName()) 
                            && StringUtils.isBlank(registryUser.getLastName())) {
                        bodyB = bodyB.append("");
                    } else {
                       bodyB = bodyB.append("by " + registryUser.getFirstName() 
                               + " " + registryUser.getLastName());  
                    }     
                }  
            } else {
                bodyB = bodyB.append("by " + csmUser.getFirstName() + " " + csmUser.getLastName());
            }
            bodyB.append(" on " 
            + dateFormat.format(date)
            + ". However, this marker may still need to be added into the caDSR repository."
            + "\n\n"
            + "Thank you"
            + "\n"
            + "NCI Clinical Trials Reporting Program");
            String body = String.valueOf(bodyB);
            String toAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            List<String> toList = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(toAddress, ",");
            while (st.hasMoreElements()) {
                toList.add((String) st.nextElement());
            }
            toAddress = toList.get(0);
            List<String> copyList = new ArrayList<String>();
            for (String to : toList) {
                if (!StringUtils.equals(to, toAddress)) {
                     copyList.add(to);
                }
            }
            String subject = "Accepted New biomarker " 
                + StConverter.convertToString(marker.getName()) 
                + ",HUGO code:" + hugoCode + " in CTRP PA";
            String fromAddress = lookUpTableService.getPropertyValue(FROMADDRESS);
            if (StringUtils.isBlank(from) && registryUser == null) {
                from = fromAddress;
            } else if (registryUser != null) {
                if (StringUtils.isBlank(registryUser.getEmailAddress())) {
                        from = fromAddress;
                 } else {
                        from = registryUser.getEmailAddress();  
                 }
            }
            sendMailWithAttachment(toAddress, from, copyList, subject, body, null, false);
        } catch (Exception e) {
            throw new PAException("An error occured while sending a acceptance email for a CDE", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarkerEmailAddress(PlannedMarkerDTO marker) throws PAException {
        String emailId = StringUtils.EMPTY;
        try {
            String userId = StConverter.convertToString(marker.getUserLastCreated());
            User csmUser = CSMUserService.getInstance().getCSMUserById(Long.valueOf(userId));
            emailId = determineRecipientEmail(csmUser);
        } catch (Exception e) {
            throw new PAException("An error occured while retrieving Submitter's email address", e);
        }
        return emailId;
    }


    /**
     * @param emailId
     * @param csmUser
     * @return
     * @throws PAException
     */
    private String determineRecipientEmail(User csmUser) throws PAException {
        String emailId = StringUtils.EMPTY;
        RegistryUser registryUser = registryUserService.getUser(csmUser
                .getLoginName());
        if (registryUser != null
                && StringUtils.isNotBlank(registryUser.getEmailAddress())) {
            emailId = registryUser.getEmailAddress();
        } else if (StringUtils.isNotBlank(csmUser.getEmailId())) {
            emailId = csmUser.getEmailId();
        }
        return emailId;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMarkerQuestionToCTROMail(String nciIdentifier, 
            String toAddress, PlannedMarkerDTO marker, String question) throws PAException {
        try {
            String to = toAddress;
            if (StringUtils.isBlank(toAddress)) {
                to = getMarkerEmailAddress(marker);
            }
            String body = "Dear CTRO,"
                + "\n\n"
                + "A new marker request has been submitted to caDSR for trial "
                + nciIdentifier 
                + ". However, we have the following question(s) before we accept this marker "
                + StConverter.convertToString(marker.getName()) 
                + ":\n\n"
                + "'"
                + question
                + "'\n\n"
                + "Please contact "
                + "the caDSR team.\n\n"
                + "Thank you\n"
                + "NCI Clinical Trials Reporting Program"; 

            String fromAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            String subject = "Question regarding new biomarker "
                + StConverter.convertToString(marker.getName()) 
                + " Request for Trial " 
                + nciIdentifier;
            sendMailWithAttachment(to, fromAddress, null, subject, body, null, false);
        } catch (Exception e) {
            throw new PAException("An error occured while sending a q email for a CDE", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendCadsrJobErrorEMail() throws PAException {
        try {
            String subject = lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_ERROR_SUBJECT");   
            String body = lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_ERROR_BODY");
            String toAddress = lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_EMAIl_LIST");
            body = body.replace(CURRENT_DATE, getFormatedDate(new Date()));
            List<String> toList = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(toAddress, ",");
            while (st.hasMoreElements()) {
                toList.add((String) st.nextElement());
            }
            toAddress = toList.get(0);
            List<String> copyList = new ArrayList<String>();
            for (String to : toList) {
                if (!StringUtils.equals(to, toAddress)) {
                     copyList.add(to);
                }
            }
            String from = lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_FROM_ADDRESS");
            sendMailWithHtmlBody(from, toAddress, copyList, subject, body);
        } catch (Exception e) {
            throw new PAException("An error occured while sending email for an error message", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendXMLAndTSREmail(String fullName, String mailTo, Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
                .convertToLong(studyProtocolIi));

            String body = lookUpTableService.getPropertyValue("xml.body");
            body = commonMailBodyReplacements(spDTO, body);
            body = body.replace("${fileName}", spDTO.getNciIdentifier().toString() + ".xml");
            body = body.replace(OWNER_NAME, fullName);

            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
            StringBuffer sb2 = new StringBuffer(folderPath);
            StringBuffer sb3 = new StringBuffer(folderPath);
            String rtfTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb2, EXTENSION_RTF);
            String htmlTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb3, EXTENSION_HTML);

            String mailSubject = lookUpTableService.getPropertyValue("xml.subject");
            mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);

            File[] attachments = {new File(xmlFile), new File(rtfTsrFile), new File(htmlTsrFile)};
            String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
            sendMailWithHtmlBodyAndAttachment(mailTo, mailFrom, null, mailSubject, body, attachments, true);
            
        } catch (Exception e) {
            throw new PAException("Exception occured while sending XML and TSR Report to submitter", e);
        }
    }

    private List<String> sendEmail(StudyProtocolQueryDTO spDTO, String body, // NOPMD
            File[] attachments, String mailSubject, boolean includeSubmitter,
            boolean deleteAttachments) throws PAException {
        Set<String> emails = new HashSet<String>();
        String emailSubject = mailSubject;
        emailSubject = commonMailSubjectReplacements(spDTO, emailSubject);
        body = commonMailBodyReplacements(spDTO, body);

        // We are making the assumption here that if the user created the trial
        // then they have a registry
        // account and thus an email address saved in our system - aevansel
        // 05/18/2010.
        // Sending email to all study owners, and not just the submitter -
        // kanchink
        String emailAddress = null;
        String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
        try {
            Collection<RegistryUser> recipients = buildTrialOwnerAndSubmitterList(
                    spDTO, includeSubmitter);
            for (RegistryUser recipient : recipients) {
                emailAddress = recipient.getEmailAddress();
                String regUserName = recipient.getFirstName() + " "
                        + recipient.getLastName();
                String emailBodyText = body.replace(OWNER_NAME, regUserName);
                
                sendMailWithHtmlBodyAndAttachment(emailAddress, mailFrom, null,
                        emailSubject, emailBodyText, attachments,
                        deleteAttachments);
                
                emails.add(emailAddress);
            }
        } catch (Exception e) {
            throw new PAException("Error attempting to send email to "
                    + emailAddress, e);
        }
        return new ArrayList<String>(emails);
    }

    private Set<RegistryUser> getStudyOwners(final StudyProtocolQueryDTO spDTO)
            throws PAException {
        Set<RegistryUser> studyOwners = new HashSet<RegistryUser>();
        try {
            StudyProtocol studyProtocol = (StudyProtocol) PaHibernateUtil
                    .getCurrentSession().get(StudyProtocol.class,
                            spDTO.getStudyProtocolId());
            if (studyProtocol != null) {
                studyOwners.addAll(studyProtocol.getStudyOwners());
            }

            CollectionUtils.filter(studyOwners, new Predicate() {
                @Override
                public boolean evaluate(Object obj) {
                    RegistryUser user = (RegistryUser) obj;
                    try {
                        return registryUserService.isEmailNotificationsEnabled(
                                user.getId(), spDTO.getStudyProtocolId());
                    } catch (PAException e) {
                        LOG.error(e, e);
                        return true;
                    }
                }
            });
        } catch (Exception e) {
            throw new PAException(
                    "Error retrieving Study Protocol with Identifier = "
                            + spDTO.getStudyProtocolId(), e);
        }
        return studyOwners;
    }

    /**
     * @param userId userid
     */
    @Override
    public void sendAdminAcceptanceEmail(Long userId) {
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.accept.body", "");
    }

    /**
     * @param userId id
     * @param reason reason
     */
    @Override
    public void sendAdminRejectionEmail(Long userId, String reason) {
        String rejectReason = "No Reason Provided.";
        if (StringUtils.isNotEmpty(reason)) {
            rejectReason = reason;
        }
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.reject.body", rejectReason);
    }

    private void sendAdminAcceptanceRejectionEmail(Long userId, String emailBodyLookupKey, String reason) {
        try {
            RegistryUser admin = registryUserService.getUserById(userId);
            String emailSubject = lookUpTableService.getPropertyValue("trial.admin.accept.subject");
            String emailBody = lookUpTableService.getPropertyValue(emailBodyLookupKey);
            emailBody = emailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
            emailBody = emailBody.replace("${affliateOrgName}", admin.getAffiliateOrg());
            if (StringUtils.isNotEmpty(reason)) {
                emailBody = emailBody.replace("${rejectReason}", reason);
                emailSubject = emailSubject.replace("${adminAccessRequestStatus}", "REJECTED");
            } else {
                emailSubject = emailSubject.replace("${adminAccessRequestStatus}", "ACCEPTED");
            }
            emailBody = emailBody.replace(OWNER_NAME, admin.getFirstName() + " " + admin.getLastName());
            sendMailWithHtmlBody(admin.getEmailAddress(), emailSubject, emailBody);
        } catch (PAException e) {
            LOG.error("Error attempting to send email to ", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendSearchUsernameEmail(String emailAddress) throws PAException {
        if (!PAUtil.isValidEmail(emailAddress)) {
            throw new PAException("Validation Exception: A valid email address must be provided");
        }
        List<RegistryUser> users = registryUserService.getLoginNamesByEmailAddress(emailAddress);
        if (CollectionUtils.isNotEmpty(users)) {
            sendSearchUsernameEmail(emailAddress, users);
            return true;
        }
        return false;
    }

    /**
     * get the login names of a list of registry users.
     * @param users The List of registry users.
     * @return The list of CN extracted from the given identities.
     */
    List<String> getGridIdentityUsernames(List<RegistryUser> users) {
        List<String> userNames = new ArrayList<String>();
        for (RegistryUser user : users) {
            userNames.add(CsmUserUtil.getGridIdentityUsername(user.getCsmUser().getLoginName()));
        }
        return userNames;
    }

    /**
     * Send the user name search email.
     * @param emailAddress The e-mail address to which the mail must be send
     * @param users The list of users with that email address
     * @throws PAException If an error occurs.
     */
    void sendSearchUsernameEmail(String emailAddress, List<RegistryUser> users) throws PAException {
        String subject = lookUpTableService.getPropertyValue(USERNAME_SEARCH_SUBJECT_PROPERTY);
        String body = lookUpTableService.getPropertyValue(USERNAME_SEARCH_BODY_PROPERTY);
        RegistryUser user = users.get(0);
        body = body.replace("${firstName}", user.getFirstName());
        body = body.replace("${lastName}", user.getLastName());
        String userNamesString = StringUtils.join(getGridIdentityUsernames(users), ", ");
        body = body.replace("${userNames}", userNamesString);
        sendMailWithHtmlBody(emailAddress, subject, body);
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param ctGovXmlGeneratorService the ctGovXmlGeneratorService to set
     */
    public void setCtGovXmlGeneratorService(CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService) {
        this.ctGovXmlGeneratorService = ctGovXmlGeneratorService;
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(TSRReportGeneratorServiceLocal tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param docWrkflStatusSrv the docWrkflStatusSrv to set
     */
    public void setDocWrkflStatusSrv(DocumentWorkflowStatusServiceLocal docWrkflStatusSrv) {
        this.docWrkflStatusSrv = docWrkflStatusSrv;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }
    
    
    /**
     * @param paServiceUtil the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtil) {
        this.paServiceUtils = paServiceUtil;
    }


    @Override
    public void sendUnidentifiableOwnerEmail(Long studyProtocolId,
            Collection<String> emails) throws PAException {
        
        StudyProtocolQueryDTO study = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);  
        
        // First, email goes to CTRO
        sendUnidentifiableOwnerEmailToCTRO(study, emails);

        // Second, an email goes to each individual unidentifiable owner: https://tracker.nci.nih.gov/browse/PO-5223.
        sendUnidentifiableOwnerEmailToMismatchedUsers(study, emails);
    }

    private void sendUnidentifiableOwnerEmailToMismatchedUsers(
            StudyProtocolQueryDTO study, Collection<String> emails) throws PAException {

        String mailSubject = lookUpTableService
                .getPropertyValue("trial.register.mismatchedUser.email.subject");       
        String mailBodyTemplate = lookUpTableService
                .getPropertyValue("trial.register.mismatchedUser.email.body");
       
        String nciID = study.getNciIdentifier();        
        String submitter = identifySubmitter(study);
        
        for (String email : emails) {
            if (PAUtil.isValidEmail(email)) {
                String mailBody = mailBodyTemplate
                        .replace(CURRENT_DATE, getFormatedCurrentDate())
                        .replace(NCI_TRIAL_IDENTIFIER, nciID)
                        .replace(OWNER_NAME, submitter)
                        .replace(EMAIL_ADDRESS, email);
                sendMailWithHtmlBody(email, mailSubject, mailBody);
            }
        }
    }

    /**
     * @param studyProtocolId
     * @param emails
     * @throws PAException
     */
    private void sendUnidentifiableOwnerEmailToCTRO(StudyProtocolQueryDTO study,
            Collection<String> emails) throws PAException {
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.register.unidentifiableOwner.email.subject");
        String mailTo = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        String mailBody = lookUpTableService
                .getPropertyValue("trial.register.unidentifiableOwner.email.body");
        
        String submitter = identifySubmitter(study);
        String badEmails = StringUtils.join(emails, ",");        
        
        mailBody = commonMailBodyReplacements(study, mailBody);
        mailBody = mailBody.replace(OWNER_NAME, submitter);
        mailBody = mailBody.replace("${badEmail}", badEmails);

        sendMailWithHtmlBody(mailTo, mailSubject, mailBody);
    }

    /**
     * Attempts to identify trial submitting organization.
     * 
     * @param study
     * @return
     */
    private String identifySubmitter(StudyProtocolQueryDTO study) {
        String submitter = "";        
        try {
            RegistryUser user = study.getLastCreated() != null ? registryUserService
                    .getUser(study.getLastCreated().getUserLastCreated())
                    : null;
            if (user != null) {
                submitter = findAffiliatedOrg(user);
                if (StringUtils.isBlank(submitter)) {
                    submitter = user.getFirstName() + " " + user.getLastName();
                }
            }
        } catch (Exception e) {
            LOG.error(
                    "Unable to identify an owning organization for this trial: "
                            + study.getNciIdentifier(), e);
        }
        return submitter;
    }

    /**
     * @param servUtil
     * @param user
     * @return
     */
    String findAffiliatedOrg(RegistryUser user) {
        PAServiceUtils servUtil = new PAServiceUtils();
        return servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(String
                .valueOf(user.getAffiliatedOrganizationId())));
    }
    
    @Override
    public void sendMailWithHtmlBody(String mailFrom, String mailTo,
            List<String> mailCc, String mailSubject, String mailBody) {
        try {
            MimeMessage message = prepareMessage(mailTo, mailFrom, mailCc, mailSubject);
            message.setContent(mailBody, "text/html");
            // Send Message
            invokeTransportAsync(message, null);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    @Override
    public void sendMailWithHtmlBody(String mailTo, String subject, String mailBody) {
        try {
            MimeMessage message = prepareMessage(mailTo, 
                    lookUpTableService.getPropertyValue(FROMADDRESS), null, subject);
            message.setContent(mailBody, "text/html");
            // Send Message
            invokeTransportAsync(message, null);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }
    
    @Override
    public void sendMailWithHtmlBodyAndAttachment(String mailTo, String mailFrom, // NOPMD
            List<String> mailCc, String subject, String mailBody,
            File[] attachments, boolean deleteAttachments) { 
        try {
            // Define Message
            MimeMessage message = prepareMessage(mailTo, mailFrom, mailCc,
                    subject);
            // body
            Multipart multipart = new MimeMultipart();
            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            msgPart.setContent(mailBody, "text/html");
            multipart.addBodyPart(msgPart);

            List<File> postDeletes = new ArrayList<File>();

            if (!ArrayUtils.isEmpty(attachments)) {
                // Add attachments to message
                for (File attachment : attachments) {
                    MimeBodyPart attPart = new MimeBodyPart();
                    final File guardCopy = createGuardCopy(attachment);
                    attPart.setDataHandler(new DataHandler(new FileDataSource(
                            guardCopy)));
                    attPart.setFileName(attachment.getName());
                    multipart.addBodyPart(attPart);

                    postDeletes.add(attachment);
                    postDeletes.add(guardCopy);
                }
            }
            message.setContent(multipart);
            // Send Message
            invokeTransportAsync(message,
                    deleteAttachments ? postDeletes.toArray(new File[0]) : null); // NOPMD
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }
    
    


    @Override
    public void sendSubmissionTerminationEmail(Long studyProtocolId) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        String mailBody = lookUpTableService.getPropertyValue("trial.onhold.termination.body");
        String mailSubject = lookUpTableService.getPropertyValue("trial.onhold.termination.subject");
        String ctroEmail = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        sendMailWithHtmlBody(ctroEmail, mailSubject, mailBody);
    }

    MimeMessage prepareMessage(String mailTo, String mailFrom, 
            List<String> mailCc, String subject) throws PAException {
        Session session;
        // get system properties
        Properties props = System.getProperties();
        // Set up mail server
        props.put("mail.smtp.host", lookUpTableService.getPropertyValue("smtp"));
        props.put("mail.smtp.port", lookUpTableService.getPropertyValue("smtp.port"));
        props.put("mail.smtp.timeout", SMTP_TIMEOUT);
        props.put("mail.smtp.connectiontimeout", SMTP_TIMEOUT);
        
        if (StringUtils.isNotBlank(lookUpTableService.getPropertyValue("smtp.auth.username"))) {
            props.put("mail.smtp.auth", "true");
            Authenticator auth = new SMTPAuthenticator(lookUpTableService.getPropertyValue("smtp.auth.username"), 
                    lookUpTableService.getPropertyValue("smtp.auth.password"));
            // Get session
            session = Session.getDefaultInstance(props, auth);
        } else {
            // Get session
            session = Session.getDefaultInstance(props, null);
        }        
        
        MimeMessage result = new MimeMessage(session);
        try {
            result.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            result.setFrom(new InternetAddress(mailFrom)); 
            if (mailCc != null && !mailCc.isEmpty()) {
                   InternetAddress[] myList = new InternetAddress[mailCc.size()];
                   for (int i = 0; i < mailCc.size(); i++) {
                       myList[i] = new InternetAddress(mailCc.get(i));
                   }
                result.addRecipients(Message.RecipientType.CC, myList);         
            }
            result.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress(lookUpTableService.getPropertyValue("log.email.address")));
            result.setSentDate(new java.util.Date());
            result.setSubject(subject);
        } catch (Exception e) {
            throw new PAException("Error preparing MIME message.", e);
        }
        return result;
    }
    
    
    
   
    @Override
    public void sendTrialOwnershipAddEmail(Long userID, Long trialID)
            throws PAException {
        
        String mailBody = lookUpTableService
                .getPropertyValue("trial.ownership.add.email.body");
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.ownership.add.email.subject");

        sendTrialOwnershipChangeEmail(userID, trialID, mailBody, mailSubject);
        
        
    }

    /**
     * @param userID
     * @param trialID
     * @param mailBody
     * @param mailSubject
     * @throws PAException
     */
    private void sendTrialOwnershipChangeEmail(Long userID, Long trialID,
            String mailBody, String mailSubject) throws PAException { // NOPMD
        
        if (!registryUserService.isEmailNotificationsEnabled(userID, trialID)) {
            return;
        }
        
        RegistryUser user = registryUserService.getUserById(userID);
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(trialID);

        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(USER_NAME, getFullUserName(user));
        sendMailWithHtmlBody(user.getEmailAddress(), mailSubject, mailBody);
    }

    /**
     * @param user
     * @return
     */
    private String getFullUserName(RegistryUser user) {
        final String fullName = StringUtils.defaultString(user.getFirstName())
                + " " + StringUtils.defaultString(user.getLastName());
        if (StringUtils.isBlank(fullName)) {
            return SIR_OR_MADAM;
        } else {
            return fullName.trim();
        }
    }
    
    private String getFullUserName(User user) throws PAException {
        if (user == null) {
            return SIR_OR_MADAM;
        }
        RegistryUser registryUser = registryUserService.getUser(user
                .getLoginName());
        if (registryUser != null) {
            return getFullUserName(registryUser);
        } else {
            return CsmUserUtil.getDisplayUsername(user);
        }
    }

    @Override
    public void sendTrialOwnershipRemoveEmail(Long userID, Long trialID)
            throws PAException {
        String mailBody = lookUpTableService
                .getPropertyValue("trial.ownership.remove.email.body");
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.ownership.remove.email.subject");

        sendTrialOwnershipChangeEmail(userID, trialID, mailBody, mailSubject);
    }

    
    private RegistryUser getDCPUser() throws PAException {
        RegistryUser dcpRegUser = null;
        String dcpUser = lookUpTableService
                .getPropertyValue("dcp.user");
        if (StringUtils.isNotEmpty(dcpUser)) {
            String dcpUserLoginName = PaEarPropertyReader.getNciLdapPrefix() + dcpUser;
            dcpRegUser = registryUserService.getUser(dcpUserLoginName);
        }
        return dcpRegUser;
    }

    @Override
    public void sendNCTIDChangeNotificationMail(Ii studyProtocolIi,
            String newNCT, String oldNCT) {
        try {
            RegistryUser dcpRegUser = getDCPUser();
            if (dcpRegUser != null) {
                StudyProtocolQueryDTO spDTO = protocolQueryService
                        .getTrialSummaryByStudyProtocolId(IiConverter
                                .convertToLong(studyProtocolIi));

                String mailBody = lookUpTableService
                        .getPropertyValue("nct.change.notification.body");
                mailBody = commonMailBodyReplacements(spDTO, mailBody);
                mailBody = mailBody.replace("${previousNCTIdentifier}",
                        StringUtils.isNotEmpty(oldNCT) ? oldNCT : "Null");
                mailBody = mailBody.replace("${newNCTIdentifier}",
                        StringUtils.isNotEmpty(newNCT) ? newNCT : "Null");
                mailBody = mailBody.replace(OWNER_NAME,
                        getFullUserName(dcpRegUser));

                String mailSubject = lookUpTableService
                        .getPropertyValue("nct.change.notification.subject");
                mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
                mailSubject = mailSubject.replace("${dcpTrialIdentifier}",
                        getDCPIdentifier(spDTO.getStudyProtocolId()));

                sendMailWithHtmlBody(dcpRegUser.getEmailAddress(), mailSubject,
                        mailBody);
            }

        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendVerifyDataEmail(Map<RegistryUser, List<StudyProtocolQueryDTO>> map) throws PAException {
        String mailSubject = "";
        String mailBody = "";
        String mailto = "";
        mailSubject = lookUpTableService.getPropertyValue("verifyData.email.subject");
        mailBody = lookUpTableService.getPropertyValue("verifyData.email.bodyHeader");
        mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyData.email.body"));
        mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyData.email.bodyFooter"));
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(N_VALUE , lookUpTableService
                 .getPropertyValue("group1TrialsVerificationFrequency"));
        try {
            String date = lookUpTableService.getPropertyValue(EFFECTIVE_DATE);
            Date effectiveDate = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(date);
            
            for (Entry<RegistryUser, List<StudyProtocolQueryDTO>> entry : map.entrySet()) {
                RegistryUser user = entry.getKey();                
                List<StudyProtocolQueryDTO> list = entry.getValue();
                StringBuffer innerTable = new StringBuffer();
                for (StudyProtocolQueryDTO dto : list) {
                    if (new Date().after(effectiveDate) && dto.getVerificationDueDate().after(effectiveDate)) {
                        innerTable
                                .append("<tr><td align=\"left\" style=\"width:30%\">"
                                        + dto.getNciIdentifier()
                                        + "</td>"
                                        + "<td align=\"left\" style=\"width:30%\">"
                                        + dto.getLocalStudyProtocolIdentifier()
                                        + "</td>"
                                        + "<td align=\"left\" style=\"width:30%\">"
                                        + getFormatedDate(dto
                                                .getVerificationDueDate())
                                        + "</td></tr>");
                    }
                }
                
                if (innerTable.length() > 0) {
                    mailBody = mailBody.replace(TABLE_ROWS, innerTable.toString());
                    mailto = user.getEmailAddress();
                    mailBody = mailBody.replace(USER_NAME, getFullUserName(user)); 
                    sendMailWithHtmlBody(mailto, mailSubject, mailBody);
                }
                mailto = "";
                mailBody = lookUpTableService.getPropertyValue("verifyData.email.bodyHeader");
                mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyData.email.body"));
                mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyData.email.bodyFooter"));
                mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
                mailBody = mailBody.replace(N_VALUE , lookUpTableService
                         .getPropertyValue("group1TrialsVerificationFrequency"));
            }
        } catch (ParseException e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }     
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendCTROVerifyDataEmail(List<StudyProtocolQueryDTO> list) throws PAException {
        String mailSubject = "";
        String mailBody = "";
        String mailto = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        mailSubject = lookUpTableService.getPropertyValue("verifyDataCTRO.email.subject");
        if (!list.isEmpty()) {
            mailSubject = mailSubject.replace(DUE_DATE, getFormatedDate(list.get(0)
                     .getVerificationDueDate()));
        }
        
        mailBody = lookUpTableService.getPropertyValue("verifyDataCTRO.email.bodyHeader");
        mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyDataCTRO.email.body"));
        mailBody = mailBody.concat(lookUpTableService.getPropertyValue("verifyDataCTRO.email.bodyFooter"));
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        StringBuffer innerTable = new StringBuffer();
        try {
            String date = lookUpTableService.getPropertyValue(EFFECTIVE_DATE);
            Date effectiveDate = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(date);
            
            for (StudyProtocolQueryDTO dto : list) {                
                mailBody = mailBody.replace(DUE_DATE, getFormatedDate(dto.getVerificationDueDate()));
                if (new Date().after(effectiveDate) && dto.getVerificationDueDate().after(effectiveDate)) {
                    innerTable.append("<tr><td align=\"left\" style=\"width:50%\">" + dto.getNciIdentifier() + "</td>"
                            + "<td align=\"left\" style=\"width:50%\">" + dto.getLeadOrganizationName() + "</td>"
                            + "<td align=\"left\" style=\"width:50%\">" + dto.getLocalStudyProtocolIdentifier() 
                            + "</td></tr>");
                }
            }
            if (innerTable.length() > 0) {
                mailBody = mailBody.replace(TABLE_ROWS, innerTable.toString()); 
                sendMailWithHtmlBody(mailto, mailSubject, mailBody);
            }
        } catch (ParseException e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }
    
    @Override
    public void sendCTGovSyncStatusSummaryMail(List<CTGovImportLog> logEntries) throws PAException {
        String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
        String mailSubject = lookUpTableService.getPropertyValue("ctgovsync.email.subject");
        String mailBody = lookUpTableService.getPropertyValue("ctgovsync.email.body");
        String mailingList = lookUpTableService.getPropertyValue("ctgovsync.email.to");
        List<String> ccList = new ArrayList<String>();
        String mailTo = null;
        //Split the mailing addresses based on ;
        String[] mailingAddresses = StringUtils.split(mailingList, ";");
        boolean firstAddress = true;
        for (String mailingAddress : mailingAddresses) {
            //Use first address as the to address
            if (firstAddress) {
                mailTo = mailingAddress;
                firstAddress = false;
            } else {
                //add other following addresses to CC list.
                ccList.add(mailingAddress);
            }
        }
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailSubject = mailSubject.replace(CURRENT_DATE, getFormatedCurrentDate());
        int successfulUpdates = 0;
        int failures = 0;
        int totalSubmitted = 0;
        //to track all submissions
        StringBuffer allSubmissions = new StringBuffer();
        //to track failed submissions
        StringBuffer failedSubmissions = new StringBuffer();
        //Loop over the log entries associated with updated trials
        for (CTGovImportLog logEntry : logEntries) {
            StringBuffer logEntryBuffer = new StringBuffer();
            logEntryBuffer.append("<tr>");
            logEntryBuffer.append("<td>").append(logEntry.getNciID()).append("</td>");
            logEntryBuffer.append("<td>").append(logEntry.getNctID()).append("</td>");
            logEntryBuffer.append("<td>").append(logEntry.getTitle()).append("</td>");
            logEntryBuffer.append("<td>").append("Industrial").append("</td>");
            logEntryBuffer.append("<td>").append("Nightly Job").append("</td>");
            logEntryBuffer.append("<td>").append(logEntry.getDateCreated().toString()).append("</td>");
            logEntryBuffer.append("<td>").append(logEntry.getImportStatus()).append("</td>");
            logEntryBuffer.append("<td>").append(
                    logEntry.getDisplayableReviewIndicator()).append("</td>");                       
            logEntryBuffer.append("</tr>");
            if (logEntry.getImportStatus().equals(CTGovSyncServiceBean.SUCCESS)) {
                successfulUpdates++;
            } else {
                failedSubmissions.append(logEntryBuffer.toString());
                failures++;
            }
            allSubmissions.append(logEntryBuffer.toString());
        }
        totalSubmitted = logEntries.size();
        mailBody = mailBody.replace(TOTAL_SUBMITTED, Integer.toString(totalSubmitted));
        mailBody = mailBody.replace(SUCCESSFUL_UPDATES, Integer.toString(successfulUpdates));
        mailBody = mailBody.replace(FAILURES, Integer.toString(failures));        
        //populate failed submissions information
        StringBuffer submissions = new StringBuffer();
        submissions.append("<!DOCTYPE html>");
        submissions.append("<html><body>");
        submissions.append("<p><b>Failed Submissions:</b></p>");
        submissions.append("<table border=\"1\"><tr>");
        submissions.append("<td>NCI ID</td><td>ClinicalTrials.gov Identifier</td><td>Title</td><td>Trial Type</td>");
        submissions.append("<td>Mechanism</td><td>Date/Time</td><td>Import Status</td>");
        submissions.append("<td>Needs Review?</td>");
        submissions.append(failedSubmissions.toString());
        submissions.append("</tr>");
        submissions.append("</table>");
        //populate all submissions information
        submissions.append("<p><b>All Submissions:</b></p>");
        submissions.append("<table border=\"1\"><tr>");
        submissions.append("<td>NCI ID</td><td>ClinicalTrials.gov Identifier</td><td>Title</td><td>Trial Type</td>");
        submissions.append("<td>Mechanism</td><td>Date/Time</td><td>Import Status</td>");
        submissions.append("<td>Needs Review?</td>");
        submissions.append("</tr>");
        submissions.append(allSubmissions.toString());
        submissions.append("</table>");
        submissions.append("</body></html>");
        File[] attachments = new File[1];        
        //Creating a zip file which includes failed and all submissions information
        //as a html file. The zip file would be included as an attachment in status 
        //summary e-mail.
        ZipOutputStream zipOutput = null;
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            Calendar calendar = new GregorianCalendar();
            Date date = calendar.getTime();
            DateFormat format = new SimpleDateFormat("MMddyyyy", Locale.getDefault());
            String currentDate = format.format(date);
            String fileName = "ctgovDailyImport" + currentDate;
            //create html file
            File ctgovDailyImportHtmlFile = new File(tempDir, fileName + ".html");  
            //copy the submissions information to the html file.
            FileUtils.writeStringToFile(ctgovDailyImportHtmlFile, submissions.toString());
            //create zip file
            File ctgovDailyImportZipFile = new File(tempDir, fileName + ".zip"); 
            zipOutput = new ZipOutputStream(new BufferedOutputStream(FileUtils.openOutputStream(
                    ctgovDailyImportZipFile)));            
            //create new zip entry and write html file date to archive
            ZipEntry entry = new ZipEntry(fileName + ".html");
            zipOutput.putNextEntry(entry);
            IOUtils.write(FileUtils.readFileToByteArray(ctgovDailyImportHtmlFile), zipOutput);
            //After html file has been written to zip archive, delete it.
            FileUtils.deleteQuietly(ctgovDailyImportHtmlFile);
            zipOutput.closeEntry();  
            //add zip file as an attachment
            attachments[0] = ctgovDailyImportZipFile;
        } catch (IOException e) {
            LOG.error("IOException : " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(zipOutput);            
        }
        //send email with zip file as an attachment
        
        sendMailWithHtmlBodyAndAttachment(mailTo, mailFrom, ccList, mailSubject, mailBody, attachments, true);
    } 
    
   @Override
   public void sendSyncEmail(String ncitIdentifier, String toAddress, String preferredName,
           String userName , String displayName) throws PAException {
        
       try {
          
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
           String submissionDate = simpleDateFormat.format(new Date());
         
           
           String mailSubject = lookUpTableService.getPropertyValue("sync.email.subject");
           mailSubject = mailSubject.replace("${ncitTerm}", ncitIdentifier);
           mailSubject = mailSubject.replace("${preferredName}", preferredName);
           
           
           String mailBody = lookUpTableService.getPropertyValue("sync.email.body");
           mailBody = mailBody.replace("${ncitTerm}", ncitIdentifier);
           mailBody = mailBody.replace("${preferredName}", preferredName);
           if (userName != null) {
               mailBody = mailBody.replace("${user}", userName);
           } else {
               mailBody = mailBody.replace("${user}", "");
           }
           
           if (displayName != null) {
               mailBody = mailBody.replace("${displayName}", displayName);
           } else {
               mailBody = mailBody.replace("${displayName}", "");
           }
           mailBody = mailBody.replace("${submissionDate}", submissionDate);
           
          
           String fromAddress = lookUpTableService.getPropertyValue(FROMADDRESS);
         
           sendMailWithHtmlBodyAndAttachment(toAddress, fromAddress, null, mailSubject, mailBody, null, false);
       } catch (Exception e) {
           throw new PAException("An error occured while sending a q email for a CDE", e);
       }
    }


    @Override
    public void sendSuperAbstractorTransitionErrorsEmail(Long studyProtocolId,
            User recipient) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);

        String mailBody = lookUpTableService
                .getPropertyValue("trial.status.transition.errors.body");
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.status.transition.errors.subject");
        mailSubject = commonMailSubjectReplacements(spDTO, mailSubject);
        mailBody = commonMailBodyReplacements(spDTO, mailBody);
        mailBody = mailBody.replace(USER_NAME, getFullUserName(recipient));
        mailBody = mailBody.replace(
                "${sciAbsName}",
                getFullUserName(CSMUserService.getInstance().getCSMUser(
                        UsernameHolder.getUser())));

        String recipientEmail = determineRecipientEmail(recipient);
        if (StringUtils.isNotBlank(recipientEmail)) {
            sendMailWithHtmlBody(recipientEmail, mailSubject, mailBody);
        }

    }
    
    /**
     * Sends notification email to recipient
     * @param emailRecipient Email address of the recipient
     * @param emailSubjKey PA props key to get email subject template
     * @param emailBodyKey PA props key to get email body template
     * @param subjParams parameters to replace placeholders in email subject
     * @param bodyParams parameters to replace placeholders in email body
     * @throws PAException exception
     */
    @Override
    public void sendNotificationMail(String emailRecipient, String emailSubjKey, 
            String emailBodyKey, Object[] subjParams, Object[] bodyParams) throws PAException {
        String mailBody = lookUpTableService.getPropertyValue(emailBodyKey);
        String mailSubject = lookUpTableService.getPropertyValue(emailSubjKey);
        try {
            mailSubject = String.format(mailSubject, subjParams);
            mailBody = String.format(mailBody, bodyParams);
        } catch (Exception e) {
            throw new PAException(
                    "Error building email subject and body from message templates and supplied parameters, " 
                            + e.getMessage());
        }
        
        sendMailWithHtmlBody(emailRecipient, mailSubject, mailBody);
    }
    
    @Override
    public void sendAccountActivationEmail(RegistryUser user) {
        try {

            final Map<String, Object> root = new HashMap<String, Object>();
            root.put("user", user);

            StringWriter subject = new StringWriter();
            StringWriter body = new StringWriter();
            cfg.getTemplate("self.registration.activation.subject").process(
                    root, subject);
            cfg.getTemplate("self.registration.activation.body").process(root,
                    body);

            sendMailWithAttachment(user.getEmailAddress(), subject.toString(),
                    body.toString(), null);

        } catch (IOException | TemplateException e) {
            LOG.error(e, e);
        }

    }
 // commented as part of PO-9862
    /*@Override
    public void sendSiteCloseNotification(SiteStatusChangeNotificationData data) {
        try {
            Template subjectFtl = cfg
                    .getTemplate("site.status.change.notification.subject");
            Template bodyFtl = cfg
                    .getTemplate("site.status.change.notification.body");

            StudyProtocolQueryDTO trial = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(IiConverter
                            .convertToLong(data.getStudyProtocolID()));            
            
            String identifiers = getStudyIdentifiers(trial); 
            
            String date = getFormatedDate(new Date());
            Collection<RegistryUser> recipients = buildTrialOwnerAndSubmitterList(
                    trial, true);
            for (RegistryUser recipient : recipients) {
                String emailAddress = recipient.getEmailAddress();
                Map<String, Object> root = new HashMap<String, Object>();
                root.put("trial", trial);
                root.put("date", date);
                root.put("data", data);
                root.put("recipient", recipient);
                
                if (StringUtils.isNotBlank(identifiers)) {
                    root.put("trialIdentifiers", identifiers);
                }

                StringWriter subject = new StringWriter();
                StringWriter body = new StringWriter();
                subjectFtl.process(root, subject);
                bodyFtl.process(root, body);

                sendMailWithHtmlBody(emailAddress, subject.toString(),
                        body.toString());
            }
        } catch (PAException | IOException | TemplateException e) {
            LOG.error(e, e);
        }
    }*/
    
    /**
     * Sends an email to app Support.
     * @param params params.
     */
    public void sendNewUserRequestEmail(String [] params) {
        try {
            String mailTo = lookUpTableService.getPropertyValue("appsupport.mailTo");
            String emailSubject = lookUpTableService
                .getPropertyValue("self.registration.appsupport.email.subject");

            MessageFormat formatterBody =
                new MessageFormat(lookUpTableService
                        .getPropertyValue("self.registration.appsupport.email.body"));

          

            String emailBody = formatterBody.format(params);

            LOG.warn("emailBody is: " + emailBody);
            sendMailWithAttachment(mailTo, emailSubject, emailBody, null);
        } catch (Exception e) {
            LOG.error("Send confirmation mail error", e);
        }
    }
    
    /**
     * 
     * @param userName userName
     * @param leadOrgName leadOrgName
     * @param warningMap warningMap
     */
    public void generateCTROWarningEmail(String userName, String leadOrgName, 
            Map<String, String> warningMap) {
            int createCount = 0;
            int amendCount = 0;
            List<String> createTrialIDS = new ArrayList<String>();
            List<String> amendTrialIDS = new ArrayList<String>();
            if (!MapUtils.isEmpty(warningMap)) {
                Set<String> s = warningMap.keySet();
                Iterator<String> iter = s.iterator();
                while (iter.hasNext()) {
                    String trialId = iter.next();
                    String warning = warningMap.get(trialId); 
                    if (StringUtils.equalsIgnoreCase("CreateWarning", warning)) {
                        createCount++;
                        createTrialIDS.add(trialId);
                    } else {
                         amendCount++;
                         amendTrialIDS.add(trialId);
                    }
                }
            }
            
            if (createCount > 0) {
                sendCTROWarningEmail(userName, leadOrgName, "CreateWarning", createTrialIDS);
            }
            if (amendCount > 0) {
                sendCTROWarningEmail(userName, leadOrgName, "AmendWarning", createTrialIDS);
            }
    }
    
    private void sendCTROWarningEmail(String userName, String leadOrgName, String warning, 
         List<String> trialList) {
      try {
        Template subjectFtl = null;
        Template bodyFtl = null;
        
        if (StringUtils.equalsIgnoreCase("CreateWarning", warning)) {
            subjectFtl = cfg
                    .getTemplate("trial.batchUpload.create.warning.subject");
            bodyFtl = cfg
                    .getTemplate("trial.batchUpload.create.warning.body");
        } else if (StringUtils.equalsIgnoreCase("AmendWarning", warning)) {
            subjectFtl = cfg
                    .getTemplate("trial.batchUpload.amend.warning.subject");
            bodyFtl = cfg
                    .getTemplate("trial.batchUpload.amend.warning.body");
        } else if (StringUtils.equalsIgnoreCase("AmendServiceWarning", warning)) {
             subjectFtl = cfg
                     .getTemplate("trial.service.amend.subject");
             bodyFtl = cfg
                     .getTemplate("trial.service.amend.body");
        } else if (StringUtils.equalsIgnoreCase("CreateServiceWarning", warning)) {
            subjectFtl = cfg
                    .getTemplate("trial.service.create.subject");
            bodyFtl = cfg
                    .getTemplate("trial.service.create.body");
       }
        
        RegistryUser registryUser = registryUserService.getUser(userName);
        String toEmail = lookUpTableService.getPropertyValue("abstraction.script.mailTo");
        String changeDate = lookUpTableService.getPropertyValue("delayed.posting.change.date");
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat(PAUtil.DATE_FORMAT, Locale.getDefault());
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("submitter_name", registryUser.getFullName());
        root.put("submitting_organization_name", StringUtils.defaultString(leadOrgName));
        root.put("submission_date", format.format(date));

        StringBuffer innerTable = new StringBuffer();
        for (String trialID : trialList) {
            innerTable.append("<table><tr><td>" + trialID
                    + "</td></tr></table>");
        }
        
        if (innerTable.length() > 0) {
            root.put("tableRows", innerTable.toString());
        }
        
        if (trialList.size() > 1) {
            root.put("trial_ids", trialList.get(0) + "...");
        } else if (!trialList.isEmpty()) {
            root.put("trial_ids", trialList.get(0));
        }
        root.put("changeDate", changeDate);
        StringWriter subject = new StringWriter();
        StringWriter body = new StringWriter();
        subjectFtl.process(root, subject);
        bodyFtl.process(root, body);
        sendMailWithHtmlBody(toEmail, subject.toString(),
                    body.toString());
    } catch (PAException | IOException | TemplateException e) {
        LOG.error(e, e);
    }
    }
    
    
    @Override
    public void sendPleaseWaitEmail(String mailTo, String [] params) {
        try {
            

            String emailSubject = lookUpTableService
                .getPropertyValue("self.registration.pleaseWait.email.subject");

            MessageFormat formatterBody =
                new MessageFormat(lookUpTableService
                        .getPropertyValue("self.registration.pleaseWait.email.body"));

          
            String emailBody = formatterBody.format(params);
            sendMailWithHtmlBody(mailTo, emailSubject, emailBody);
           
        } catch (Exception e) {
            LOG.error("Send confirmation mail error", e);
        }
    }

    @Override
    public void closeTemplateSource(Object arg0) throws IOException {
        // NO-OP
    }


    @Override
    public Object findTemplateSource(String key) throws IOException {
        try {
            return lookUpTableService.getPropertyValue(key);
        } catch (PAException e) {
            LOG.warn(e.getMessage());
            return null;
        }
    }


    @Override
    public long getLastModified(Object arg0) {        
        return -1;
    }


    @Override
    public Reader getReader(Object templateSource,
            String encoding) throws IOException {        
        return new StringReader((String) templateSource);
    }


    @Override
    public void sendComparisonDocumentToCtro(String nciID , String nctId, File attachment) throws PAException {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        String submissionDate = simpleDateFormat.format(new Date());
        
        
        String mailTo = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        
        String mailSubject = lookUpTableService.getPropertyValue("ctro.comparision.email.subject");
        mailSubject = mailSubject.replace("${nciId}", nciID);
       
        String body = lookUpTableService.getPropertyValue("ctro.comparision.email.body");
        body = body.replace("${nctId}", nctId);
        body = body.replace("${currentDate}", submissionDate);
        body = body.replace("${nciId}", nciID);
       
        
        File [] file = new File[1];
        file[0] = attachment;
        
        String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
        
        sendMailWithHtmlBodyAndAttachment(mailTo, mailFrom, null, mailSubject, body, file, true);
    }


    @Override
    public void sendComparisonDocumentToCcct(String nciId, String nctId,
            File attachment) throws PAException {
        
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        List<String> toEmailAddressList = new ArrayList<String>();
        String submissionDate = simpleDateFormat.format(new Date());
        
        
        
        String mailTo = lookUpTableService
                .getPropertyValue("ccct.comparision.email.tolist");
        
        if (mailTo != null) {
            String [] lists = mailTo.split(",");
            toEmailAddressList = Arrays.asList(lists);
        }
        
        String mailSubject = lookUpTableService.getPropertyValue("ccct.comparision.email.subject");
        mailSubject = mailSubject.replace("${nciId}", nciId);
       
        String body = lookUpTableService.getPropertyValue("ccct.comparision.email.body");
        body = body.replace("${nctId}", nctId);
        body = body.replace("${currentDate}", submissionDate);
        body = body.replace("${nciId}", nciId);
       
        
        File [] file = new File[1];
        file[0] = attachment;
        
        String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
        
        //send separate to each receiver
        for (String toEmail: toEmailAddressList) {
            sendMailWithHtmlBodyAndAttachment(toEmail, mailFrom, null, mailSubject, body, file, true);
        }
        
    }


    @Override
    public void sendCoverSheetEmail(String nciId,
            StudyProtocolDTO studyProtocolDTO,
            List<StudyRecordChange> studyRecordChangeList) throws PAException {
       
        String mailTo = lookUpTableService
                .getPropertyValue("ccct.comparision.email.tolist");
        SimpleDateFormat simpleDateFormat = null;
        String useStandardLanguage = null;
        String dateEnteredInPrs = null;
        String designeeAccessRevoked = null;
        String designeeAccessRevokedDate = "";
        String changesInCtrpCtGov = null;
        String changesInCtrpCtGovDate = "";
        String sendToCtGovUpdated = null;
        List<String> toEmailAddressList = new ArrayList<String>();
        Date date = null;
        
       try { 
        
        simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        
        if (mailTo != null) {
            String [] lists = mailTo.split(",");
            toEmailAddressList = Arrays.asList(lists);
        }
        String mailSubject = lookUpTableService.getPropertyValue("ctro.coversheet.email.subject");
        mailSubject = mailSubject.replace("${nciId}", nciId);
        
        if (BlConverter.convertToBool(studyProtocolDTO.getUseStandardLanguage())) {
            useStandardLanguage = YES_VAL;
        } else {
            useStandardLanguage = NO_VAL;
        }
        if (BlConverter.convertToBool(studyProtocolDTO.getDateEnteredInPrs())) {
            dateEnteredInPrs = YES_VAL;
        } else {
            dateEnteredInPrs = NO_VAL;
        }
        
        if (BlConverter.convertToBool(studyProtocolDTO.getDesigneeAccessRevoked())) {
            designeeAccessRevoked = YES_VAL;
        } else {
            designeeAccessRevoked = NO_VAL;
        }
        Timestamp timestamp = null;
        timestamp = TsConverter.convertToTimestamp(studyProtocolDTO.getDesigneeAccessRevokedDate());
        
        if (timestamp != null) {
            date = new Date(timestamp.getTime());
            designeeAccessRevokedDate = simpleDateFormat.format(date); 
        }
        
        if (BlConverter.convertToBool(studyProtocolDTO.getChangesInCtrpCtGov())) {
            changesInCtrpCtGov = YES_VAL;
        } else {
            changesInCtrpCtGov = NO_VAL;
        }
        
        timestamp = null; 
        timestamp = TsConverter.convertToTimestamp(studyProtocolDTO.getChangesInCtrpCtGovDate());
        if (timestamp != null) {
            date = new Date(timestamp.getTime());
            changesInCtrpCtGovDate = simpleDateFormat.format(date); 
        }
        
        if (BlConverter.convertToBool(studyProtocolDTO.getChangesInCtrpCtGov())) {
            sendToCtGovUpdated = YES_VAL;
        } else {
            sendToCtGovUpdated = NO_VAL;
        }
        
        
        
        String body = lookUpTableService.getPropertyValue("ctro.coversheet.email.body");
        body = body.replace("${useStandardLanguage}", useStandardLanguage);
        body = body.replace("${dateEnteredInPrs}", dateEnteredInPrs);
        body = body.replace("${designeeAccessRevoked}", designeeAccessRevoked);
        body = body.replace("${designeeAccessRevokedDate}", designeeAccessRevokedDate);
        body = body.replace("${changesInCtrpCtGov}", changesInCtrpCtGov);
        body = body.replace("${changesInCtrpCtGovDate}", changesInCtrpCtGovDate);
        body = body.replace("${sendToCtGovUpdated}", sendToCtGovUpdated);
        
        StringBuffer notesData = new StringBuffer();
        
        //data disc and records changes size changes hence they can not be store in db as template that needs to be
       //appended to body dynamically
       notesData.append("<h2>Record Changes </h2>");
        notesData.append("<table border=\"1\" style=\"width:100%\">");
        notesData.append("<thead><tr>");
        notesData.append("<th>Change Type</th>");
        notesData.append("<th>Action Taken</th>");
        notesData.append("<th>Action Completion Date</th>");
        notesData.append("</thead></tr>");
        notesData.append("<tbody>");
        StudyRecordChange studyRecordChange = null;
        
        for (StudyRecordChange studyNote : studyRecordChangeList) {
            studyRecordChange = new StudyRecordChange();
            studyRecordChange = (StudyRecordChange) studyNote;
            notesData.append("<tr>");
            notesData.append("<td>" + studyRecordChange.getChangeType() + "</td>");
            notesData.append("<td>" + studyRecordChange.getActionTaken() + "</td>");
            notesData.append("<td>" + simpleDateFormat.format(studyRecordChange.getActionCompletionDate()) + "</td>");
            notesData.append("</tr>");
        }
        notesData.append("</tbody>");
        notesData.append("</table>");
        
        notesData.append(body);
        
        
        String mailFrom = lookUpTableService.getPropertyValue(FROMADDRESS);
        
        //send separate to each receiver
        for (String toEmail: toEmailAddressList) {
            sendMailWithHtmlBodyAndAttachment(toEmail, mailFrom, null, mailSubject, notesData.toString(), null, true);
        }
        
        
       } catch (Exception e) {
           throw new PAException(e.getMessage());
       }
        
    }
    
 // commented as part of PO-9862
   /* @Override
   public void sendSiteNotCloseNotification(SiteStatusChangeNotificationData dataForEmail)
           throws PAException {
        
        String mailTo = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        
        
        
        String mailSubject = lookUpTableService.getPropertyValue("participating.site.not.closed.email.subject");
        
        StudyProtocolQueryDTO trial = protocolQueryService
                .getTrialSummaryByStudyProtocolId(IiConverter
                        .convertToLong(dataForEmail.getStudyProtocolID())); 
        
        mailSubject = mailSubject.replace("${nciId}", trial.getNciIdentifier());
       
        String body = lookUpTableService.getPropertyValue("participating.site.not.closed.email.body");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT_WITH_TIME 
                , Locale.getDefault());
        
        body = body.replace("${currentDateTime}", simpleDateFormat.format(new Date()));
        
        body = body.replace("${title}"
                , StringUtils.defaultString(trial.getOfficialTitle()));    
        
        body = body.replace("${nciId}"
                , StringUtils.defaultString(trial.getNciIdentifier()));
        
        body = body.replace("${nctId}"
                    , StringUtils.defaultString(trial.getNctNumber()));
        
        body = body.replace("${dcpId}"
                    , StringUtils.defaultString(trial.getDcpId()));
        
        body = body.replace("${ctepId}"
                   , StringUtils.defaultString(trial.getCtepId()));    
        
        body = body.replace("${currentStatus}",
              CdConverter.convertCdToString(dataForEmail.getNewTrialStatus().getStatusCode()));
        body = body.replace("${currentStatusDate}",
                TsConverter.convertToString((dataForEmail.getNewTrialStatus().getStatusDate())));
  
        
        StringBuffer notClosedSitesData = new StringBuffer(); 
        notClosedSitesData.append("<table width='100%' border='1'>");
        notClosedSitesData.append("<tr>");
        notClosedSitesData.append("<td style='font-weight:bold' width='60%'>Site Name </td>");
        notClosedSitesData.append("<td style='font-weight:bold' width='20%'>Current Status </td>");
        notClosedSitesData.append("<td style='font-weight:bold' width='20%'>Current Site Status Date</td>");
        notClosedSitesData.append("</tr>");
        
        for (SiteData siteData : dataForEmail.getSiteData()) {
            notClosedSitesData.append("<tr>");
            notClosedSitesData.append("<td width='60%'>" + siteData.getName() + "</td>");
            notClosedSitesData.append("<td width='20%'>" + siteData.getPreviousTrialStatus().getCode() + "</td>");
            notClosedSitesData.append("<td width='20%'>" + siteData.getPreviousTrialStatusDate() + "</td>");
            notClosedSitesData.append("</tr>");
        }
        
        notClosedSitesData.append("</table>");
        
        body = body.replace("${unclosedSites}", notClosedSitesData.toString());
        sendMailWithHtmlBody(mailTo, mailSubject, body);
        
    }*/
    
    @Override
    public void sendTrialPublishDateNoUpdateEmail() throws PAException {
        String mailTo = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
           
        String mailSubject = lookUpTableService.getPropertyValue("resultsUpdater.trials.job.email.subject");
        String body = lookUpTableService.getPropertyValue("resultsUpdater.trials.job.notupdated.email.body");
        sendMailWithHtmlBody(mailTo, mailSubject, body);
        
    }
    
    @Override
    public void sendTrialPublishDateUpdateEmail(List<String> trialNciIdList)
            throws PAException {
       Collections.sort(trialNciIdList);
       String mailTo = lookUpTableService
               .getPropertyValue("abstraction.script.mailTo");
          
       String mailSubject = lookUpTableService.getPropertyValue("resultsUpdater.trials.job.email.subject");
       try {
       
       Template bodyFtl = cfg
               .getTemplate("resultsUpdater.trials.job.updated.email.body");
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("trialNciIdList", trialNciIdList);
        StringWriter body = new StringWriter();
        bodyFtl.process(root, body);
        sendMailWithHtmlBody(mailTo, mailSubject, body.toString());
       } catch (Exception e) {
           throw new PAException(e.getMessage());
       }
       
        
    }

    
    

    @Override
    public List<MailMessage> getNewEmails(String mailServer, int port, String user, String password, String folder) 
            throws PAException {
        if (StringUtils.isEmpty(mailServer) || port <= 0 || StringUtils.isEmpty(user) 
                || StringUtils.isEmpty(password) || StringUtils.isEmpty(folder)) {
            throw new PAException("One of mail server, port, user, password or folder not specified, unable to read "
                    + "email");
        }
        Store store = null;
        Folder mailFolder = null;
        try {
            Properties mailProps = new Properties();
            mailProps.setProperty("mail.store.protocol", "imaps");
            mailProps.setProperty("mail.imaps.port", String.valueOf(port));
            Session mailSession = Session.getInstance(mailProps);
            store = mailSession.getStore();
            store.connect(mailServer, user, password);
            mailFolder = store.getFolder(folder);
            mailFolder.open(Folder.READ_WRITE);
            
            List<MailMessage> result = new ArrayList<MailManagerService.MailMessage>();
            int newMailCount = mailFolder.getNewMessageCount();
            if (newMailCount > 0) {
                int mailCount = mailFolder.getMessageCount(); 
                int start = mailCount - newMailCount + 1;
                Message[] messages = mailFolder.getMessages(start, mailCount);
                for (int i = 0; i < messages.length; i++) {
                    result.add(loadMessage(messages[i]));    
                }
            } 
            return result;
        } catch (MessagingException|IOException e) {
            throw new PAException("Exception while reading messages from IMAP email server using user " + user, e);
        } finally {
            try {
                if (store != null) {
                    store.close();
                    if (mailFolder != null && mailFolder.isOpen()) {
                        mailFolder.close(true);
                    }
                }
            } catch (MessagingException me) { }
        }
    }
    
    /**
     * Pre-load message details for use after the mail session is closed
     */
    private MailMessage loadMessage(Message m) throws MessagingException, IOException {
        MailMessage mm = new MailMessage();
        if (m.getContent() instanceof MimeMultipart) {
            mm.setMessage(((MimeMultipart) m.getContent()).getBodyPart(0).getContent().toString());
        } else {
            mm.setMessage(m.getContent().toString());
        }
        String [] from = new String[m.getFrom().length];
        for (int i = 0; i < m.getFrom().length; i++) {
            from[i] = m.getFrom()[i].toString();
        }
        mm.setFrom(from);        
        String [] to = new String[m.getAllRecipients().length];
        for (int i = 0; i < m.getAllRecipients().length; i++) {
            to[i] = m.getAllRecipients()[i].toString();
        }
        mm.setTo(to);
        mm.setSendDate(m.getSentDate());
        mm.setSubject(m.getSubject());
        return mm;
    }


    /**
     * Takes a study protocol id and returns the HTML template of all the identifiers
     * @param nciId nci identifier     
     * @return String HTML template of identifiers
     * @throws PAException ex  
     */
    @Override
    public String getStudyIdentifiersHTMLTable(String nciId) throws PAException {
        if (StringUtils.isBlank(nciId)) {
            return "";
        }
        StudyProtocolQueryCriteria spqCriteria = new StudyProtocolQueryCriteria();
        spqCriteria.setNciIdentifier(nciId);        
        List<StudyProtocolQueryDTO> list;
        try {
            list = PaRegistry.getCachingProtocolQueryService().getStudyProtocolByCriteria(spqCriteria);            
        } catch (PAException e) {
            LOG.error(e, e);
            return "";
        }
        
        StudyProtocolQueryDTO spDTO = null;
        
        if (list.size() > 0) {
            spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(list.get(0).getStudyProtocolId());    
        } else {
            return "";
        }                
        return getStudyIdentifiers(spDTO);
    }


   

   
    
    
   
}
