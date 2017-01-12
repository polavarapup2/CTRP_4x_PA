/**
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

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.StudyProcessingError;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProcessingErrorConverter;
import gov.nih.nci.pa.dto.StudyProcessingErrorDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerService.MailMessage;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * StudyProcessing error service implementation 
 * @author gunnikrishnan
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StudyProcessingErrorBeanLocal extends
        AbstractBaseSearchBean<StudyProcessingError> implements
        StudyProcessingErrorServiceLocal {

    private static final int RECURRENT_DAYS = -31;
    // CT.gov Upload Error Mialbox properties
    private static final String IMAP_SERVER = "imap.server";
    private static final String IMAP_PORT = "imap.port";
    private static final String IMAP_FOLDER = "imap.folder";
    private static final String CTGOV_UPLOAD_ERROR_EMAIL_ACCOUNT = "ctgov.upload.errorEmail.account";
    private static final String CTGOV_UPLOAD_ERROR_EMAIL_SUBJECT = "ctgov.upload.errorEmail.subject";
    private static final String CTGOV_UPLOAD_ERROR_REGEX = "ctgov.upload.error.regex";

    private static final Logger LOG = Logger
            .getLogger(StudyProcessingErrorBeanLocal.class);
    static {
        LOG.setLevel(Level.INFO);
    }

    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;

    @Override
    public StudyProcessingErrorDTO getStudyProcessingError(Long id)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProcessingError studyProcessingError = (StudyProcessingError) session
                .get(StudyProcessingError.class, id);

        if (studyProcessingError == null) {
            throw new PAException(
                    "No matching study processing error for Ii.extension " + id);
        }
        return StudyProcessingErrorConverter.convertFromDomainToDTO(studyProcessingError);
    }

    @Override
    public StudyProcessingErrorDTO updateStudyProcessingError(
            StudyProcessingErrorDTO studyProcessingErrorDto) throws PAException {
        if (studyProcessingErrorDto == null) {
            throw new PAException(" studyProcessingErrorDto should not be null.");
        }

        Session session = PaHibernateUtil.getCurrentSession();
        StudyProcessingError studyProcessingError = (StudyProcessingError) session
                .get(StudyProcessingError.class, studyProcessingErrorDto.getIdentifier());
        
        studyProcessingError.setActionTaken(studyProcessingErrorDto.getActionTaken());
        studyProcessingError.setCmsTicketId(studyProcessingErrorDto.getCmsTicketId());
        studyProcessingError.setComment(studyProcessingErrorDto.getComment());
        studyProcessingError.setDateLastUpdated(new Date());
        studyProcessingError.setErrorMessage(studyProcessingErrorDto.getErrorMessage());
        studyProcessingError.setErrorDate(studyProcessingErrorDto.getErrorDate());
        studyProcessingError.setErrorType(studyProcessingErrorDto.getErrorType());
        studyProcessingError.setRecurringError(studyProcessingErrorDto.getRecurringError());
        studyProcessingError.setResolutionDate(studyProcessingErrorDto.getResolutionDate());
        if (studyProcessingErrorDto.getStudyId() > 0) {
            studyProcessingError.setStudyProtocol((StudyProtocol) session.get(StudyProtocol.class,
                                            studyProcessingErrorDto.getStudyId()));
        }
        studyProcessingError.setUserLastUpdated(CSMUserService.getInstance()
                                .getCSMUser(UsernameHolder.getUser()));
        
        session.saveOrUpdate(studyProcessingError);
        session.flush();
        return StudyProcessingErrorConverter.convertFromDomainToDTO(studyProcessingError);
    }

    @Override
    public Ii createStudyProcessingError(
            StudyProcessingError studyProcessingError) throws PAException {
        if (studyProcessingError == null) {
            throw new PAException("studyProcessingError should not be null.");
        }
        if (studyProcessingError.getId() != null) {
            throw new PAException(
                    "studyProcessingError.id should be null, but got  = "
                            + studyProcessingError.getId());
        }

        Session session = PaHibernateUtil.getCurrentSession();
        studyProcessingError.setDateLastCreated(new Date());
        session.save(studyProcessingError);
        return IiConverter.convertToStudyProtocolIi(studyProcessingError
                .getId());
    }

    @Override
    public void processStudyUploadErrors() throws PAException {
        try {
            LOG.info("Starting study upload errors processing...");
            Account ctGovemailAcc = getCTGovMailAccount();
            List<MailMessage> newCTGovMails = mailManagerSerivceLocal
                    .getNewEmails(lookUpTableService
                            .getPropertyValue(IMAP_SERVER), Integer
                            .valueOf(lookUpTableService
                                    .getPropertyValue(IMAP_PORT)),
                            ctGovemailAcc.getUsername(),
                            ctGovemailAcc.getDecryptedPassword(),
                            lookUpTableService
                                    .getPropertyValue(IMAP_FOLDER));
            LOG.info("Got " + newCTGovMails.size() + " emails to process");
            if (newCTGovMails.size() > 0) {
                String ctGovMailSubject = lookUpTableService
                        .getPropertyValue(CTGOV_UPLOAD_ERROR_EMAIL_SUBJECT);
                for (int i = 0; i < newCTGovMails.size(); i++) {
                    MailMessage mm = newCTGovMails.get(i);
                    if (ctGovMailSubject.equals(mm.getSubject())) {
                        processUploadErrorEmail(mm.getMessage(),
                                mm.getSendDate());
                    } else {
                        LOG.warn("Received a message that is not a CT.gov upload error message, ignoring;  "
                                + "message subject is: " + mm.getSubject());
                    }
                }
            }
        } catch (PAException e) {            
            LOG.error("Error reading email message", e);
            throw e;
        }
    }

    /**
     * Parse the CT.gov upload error email and create study processing errors
     * 
     * @param message
     *            study processing error email content
     * @param errorDate
     *            error date
     * @return Number of error records that could not be processed for various
     *         reasons
     * @throws PAException
     */
    int processUploadErrorEmail(String message, Date errorDate)
            throws PAException {
        LOG.info("Using RegEx pattern '"
                + lookUpTableService.getPropertyValue(CTGOV_UPLOAD_ERROR_REGEX)
                + "' to parse the error email");

        Pattern ctgovErrPattern = Pattern.compile(
                lookUpTableService.getPropertyValue(CTGOV_UPLOAD_ERROR_REGEX),
                Pattern.DOTALL);
        Matcher m = ctgovErrPattern.matcher(message);
        int procesingErrors = 0;
        int processedErrors = 0;
        while (m.find()) {
            String nciId = m.group(1).trim();
            String error = m.group(2).trim();
            LOG.debug("Parsed error for study:" + nciId + ", error:" + error);
            if (StringUtils.isNotEmpty(nciId)) {
                Ii nciIi = new Ii();
                nciIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
                nciIi.setExtension(nciId);
                StudyProtocolDTO studyDto;
                try {
                    studyDto = studyProtocolService.getStudyProtocol(nciIi);
                    StudyProcessingError spe = new StudyProcessingError();
                    StudyProtocol sp = new StudyProtocol();
                    sp.setId(IiConverter.convertToLong(studyDto.getIdentifier()));
                    spe.setStudyProtocol(sp);
                    spe.setErrorDate(new Timestamp(errorDate.getTime()));
                    spe.setErrorMessage(error);
                    spe.setRecurringError(isRecurringError(spe));
                    createStudyProcessingError(spe);
                    processedErrors++;
                } catch (PAException pae) {
                    LOG.error("No study protocol found with NCI ID: " + nciId);
                }      
            }
        }
        LOG.info("Processed " + processedErrors
                + " errors from the CT.gov upload error email");
        return procesingErrors;
    }

    /**
     * Check if this is a recurring error
     * 
     * @param spe
     * @return true if the same error has occurred for the same study in the
     *         last 30 days, false otherwise
     */
    private boolean isRecurringError(StudyProcessingError spe) {
        Session session = PaHibernateUtil.getCurrentSession();
        Calendar cal = Calendar.getInstance();
        cal.setTime(spe.getErrorDate());
        cal.add(Calendar.DATE, RECURRENT_DAYS);
        Timestamp ts = new Timestamp(cal.getTime().getTime());
        long prevErrcount = ((Long) session
                .createQuery(
                        "select count(spe) from StudyProcessingError spe "
                                + "where spe.studyProtocol.id =:studyId and spe.errorDate >:errorDate "
                                + "and spe.errorMessage =:error")
                .setParameter("studyId", spe.getStudyProtocol().getId())
                .setParameter("errorDate", ts)
                .setParameter("error", spe.getErrorMessage()).uniqueResult())
                .longValue();
        return (prevErrcount > 0);
    }

    /**
     * Load CTGov email account details
     * 
     * @return Account object for CTGov email account
     */
    private Account getCTGovMailAccount() throws PAException {
        String ctgovEmailAccount = lookUpTableService
                .getPropertyValue(CTGOV_UPLOAD_ERROR_EMAIL_ACCOUNT);
        if (StringUtils.isEmpty(ctgovEmailAccount)) {
            throw new PAException(
                    "CT.gov upload error email account name not configured. Make sure "
                            + "'ctgov.upload.errorEmail.account' PA property");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        List<Account> result = (List<Account>) session.createQuery(
                "select acc from Account acc where acc.accountName =:accName")
                    .setParameter("accName", ctgovEmailAccount).list();
        
        if (result.size() == 0) {
            throw new PAException(
                    "CT.gov upload error email account '" + ctgovEmailAccount
                            + "'not configured, make sure an Account with name " + ctgovEmailAccount 
                            + " is configured");
        } else {
            return result.get(0);
        }
    }

    /**
     * @param mailManagerSerivceLocal the mailManagerSerivceLocal to set
     */
    void setMailManagerSerivceLocal(
            MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

 
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    @Override
    public List<StudyProcessingErrorDTO> getStudyProcessingErrorByStudy(Long studyId) {
        List<StudyProcessingErrorDTO> speDtos = new ArrayList<StudyProcessingErrorDTO>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, RECURRENT_DAYS);
        Session session = PaHibernateUtil.getCurrentSession();
        List<StudyProcessingError> spes = session
                .createQuery("select spe from StudyProcessingError spe where"
                        + " id in (select max(spe.id) from "
                        + "StudyProcessingError spe where spe.errorDate >:errorDate and spe.studyProtocol.id =:studyId"
                        + " group by spe.studyProtocol, spe.errorMessage) order by spe.id desc")
                       .setParameter("errorDate", cal.getTime())
                       .setParameter("studyId", studyId).list();
        if (spes != null) {
            for (Iterator iterator = spes.iterator(); iterator.hasNext();) {
                speDtos.add(StudyProcessingErrorConverter.convertFromDomainToDTO((StudyProcessingError) iterator
                        .next()));
            }   
        }
        return speDtos;
    }

    @Override
    public List<StudyProcessingErrorDTO> getLatestStudyProcessingErrors() {
        List<StudyProcessingErrorDTO> speDtos = new ArrayList<StudyProcessingErrorDTO>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, RECURRENT_DAYS);
        Session session = PaHibernateUtil.getCurrentSession();
        List<StudyProcessingError> spes  = session
                        .createQuery("select spe from StudyProcessingError spe where"
                            + " id in (select max(spe.id) from "
                            + "StudyProcessingError spe where spe.errorDate > :errorDate "
                            + "group by spe.studyProtocol, spe.errorMessage) order by spe.id desc")
                           .setParameter("errorDate", cal.getTime()).list();
        if (spes != null) {
            for (Iterator iterator = spes.iterator(); iterator.hasNext();) {
                speDtos.add(StudyProcessingErrorConverter.convertFromDomainToDTO((StudyProcessingError) iterator
                        .next()));
            }   
        }
        return speDtos;
    }
}

