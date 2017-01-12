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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateTime;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Anupama Sharma
 * @since 07/29/2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyMilestoneTasksServiceBean implements StudyMilestoneTasksServiceLocal {

    private static final Logger LOG = Logger.getLogger(StudyMilestoneTasksServiceBean.class);
    private static final int[] DAYS_DELTA_PER_DOW = new int[]{7, 7, 7, 7, 7, 5, 6 };
    private static final String MILESTONE_COMMENT = "Milestone auto-set based on non-response within 5 days";
    private static final String PA_USER = "pauser";
    
    @EJB 
    private LookUpTableServiceRemote lookUpTableService;
    @EJB 
    private MailManagerServiceLocal mailManagerService;
    @EJB
    private StudyMilestoneServicelocal studyMilestoneService;
    @EJB
    private StudyMilestoneTasksServiceLocal studyMilestoneTasksService;
    
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    /**
     * Perform task. if we run into more problems, we should look into making the query protocol-focused, rather than
     * milestone-focused, which may simplify the code a bit (at the cost of a more complex query)
     * @throws Exception 
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void performTask() throws Exception {
        try {
            LOG.info("Starting StudyMilestoneTasksServiceBean");
            UsernameHolder.setUser(PA_USER);
            DateTime overdueDate = getOverdueDate(new DateTime());
            Set<StudyMilestone> milestones = studyMilestoneTasksService.getTrialSummarySentMilestones(overdueDate);
            LOG.info("StudyMilestoneTasksServiceBean searching for milestones. Results returned " + milestones.size());
            StudyMilestoneTaskMessageCollection errors = createMilestones(milestones);
            sendFailureNotification(errors);
            LOG.info("Ending StudyMilestoneTasksServiceBean");
        } finally {        
            UsernameHolder.setUser(null);
        }
    }

    /**
     * Compute the overdue date for the TRIAL_SUMMARY_REPORT milestones to select. It is 5 business days before the
     * given time.
     * @param now The actual time
     * @return The overdue date for the TRIAL_SUMMARY_REPORT milestones to select
     */
    DateTime getOverdueDate(DateTime now) {
        return now.minusDays(DAYS_DELTA_PER_DOW[now.getDayOfWeek() - 1]).toDateMidnight().toDateTime();
    }

    /**
     * Gets List of TRIAL_SUMMARY_REPORT milestones sent before the given threshold but not having to
     * INITIAL_ABSTRACTION_VERIFY.
     * @param threshold The threshold
     * @return list Of StudyMilestone
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Set<StudyMilestone> getTrialSummarySentMilestones(DateTime threshold) {
        String queryString = "select sm1 from StudyMilestone sm1 inner join fetch sm1.studyProtocol "
                + "where sm1.milestoneCode = :tss and "
                + "sm1.milestoneDate <= :threshold and not exists"
                + "(select sm2 from StudyMilestone sm2 where sm2.studyProtocol.id = sm1.studyProtocol.id and "
                + "((sm2.milestoneCode =  :tss and sm2.milestoneDate>sm1.milestoneDate)or(sm2.milestoneCode = :abs)))";
        Query query = PaHibernateUtil.getCurrentSession().createQuery(
                queryString);
        query.setParameter("threshold", threshold.toDate());
        query.setParameter("tss", MilestoneCode.TRIAL_SUMMARY_REPORT);
        query.setParameter("abs", MilestoneCode.INITIAL_ABSTRACTION_VERIFY);
        Set<StudyMilestone> mileStoneList = new TreeSet<StudyMilestone>(
                new MilestoneComparator());
        mileStoneList.addAll(query.list());
        return mileStoneList;
    }

    /**
     * Attempts to create the INITIAL_ABSTRACTION_VERIFY milestone for each given TRIAL_SUMMARY_REPORT milestone.
     * @param milestones The TRIAL_SUMMARY_REPORT milestones
     * @return The set of TRIAL_SUMMARY_REPORT for which validation failed
     */
    StudyMilestoneTaskMessageCollection createMilestones(Set<StudyMilestone> milestones) {
        StudyMilestoneTaskMessageCollection errors = new StudyMilestoneTaskMessageCollection();
        for (StudyMilestone milestone : milestones) {
            String trialNciId = StringUtils.EMPTY;
            try {
                Long studyProtocolId = milestone.getStudyProtocol().getId();               
                trialNciId = paServiceUtils.getTrialNciId(studyProtocolId);
                LOG.info("Creating a new milestone with code - initial abstraction verify for study protocol "
                        + studyProtocolId);
                StudyMilestoneDTO newDTO = new StudyMilestoneDTO();
                newDTO.setCommentText(StConverter.convertToSt(MILESTONE_COMMENT));
                newDTO.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
                newDTO.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
                newDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(studyProtocolId));
                studyMilestoneTasksService.createMilestone(newDTO);
            } catch (Exception e) {
                // swallowing the exception in order to continue processing the
                // rest of the records
                if (LOG.isDebugEnabled()) {
                    LOG.debug(trialNciId
                            + ": Exception auto-creating the milestone: ", e);
                } else {
                    LOG.error(trialNciId
                            + ": Exception auto-creating the milestone: "
                            + e.getMessage());
                }
                errors.add(milestone, trialNciId + ": " + e.getMessage());
            }
            // We test for interruption to provide a smoother server shutdown if it happens during this background task
            // The next execution will pick the ones that are not processed anyway
            if (Thread.interrupted()) {
                break;
            }
        }
        return errors;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createMilestone(StudyMilestoneDTO milestone) throws PAException {
        studyMilestoneService.create(milestone);
    }
    
    /**
     * Sends the email notification for the given error collection.
     * @param errors The error collection.
     */
    void sendFailureNotification(StudyMilestoneTaskMessageCollection errors) {
        if (!errors.isEmpty()) {
            String mailBody = errors.getSummary();
            try {
                String mailSubject = lookUpTableService.getPropertyValue("abstraction.script.subject");
                String mailTo = lookUpTableService.getPropertyValue("abstraction.script.mailTo");
                mailManagerService.sendMailWithAttachment(mailTo, mailSubject, mailBody, null);
            } catch (PAException e) {
                LOG.error("error sending notification mail", e);
            }
        }
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }

    /**
     * @param studyMilestoneTasksService the studyMilestoneTasksService to set
     */
    public void setStudyMilestoneTasksService(StudyMilestoneTasksServiceLocal studyMilestoneTasksService) {
        this.studyMilestoneTasksService = studyMilestoneTasksService;
    }

    /**
     * this will compare the milestone object based on study protocol id.
     * @author vrushali
     */
    public class MilestoneComparator implements Comparator<StudyMilestone> {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(StudyMilestone milestone0, StudyMilestone milestone1) {
            Long studyId0 = milestone0.getStudyProtocol().getId();
            Long studyId1 = milestone1.getStudyProtocol().getId();
            return new CompareToBuilder().append(studyId0, studyId1).toComparison();
        }
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

}
