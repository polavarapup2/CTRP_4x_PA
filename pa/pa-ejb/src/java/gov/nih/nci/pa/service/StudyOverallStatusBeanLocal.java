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

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionServiceLocal;
import gov.nih.nci.pa.service.status.ValidationError;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateMidnight;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyOverallStatusBeanLocal extends // NOPMD
        AbstractCurrentStudyIsoService<StudyOverallStatusDTO, StudyOverallStatus, StudyOverallStatusConverter>
        implements StudyOverallStatusServiceLocal { // NOPMD
    
    private static final Logger LOG = Logger
            .getLogger(StudyOverallStatusBeanLocal.class);

    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal;
    @EJB
    private StatusTransitionServiceLocal statusTransitionService;
    @EJB
    private ParticipatingSiteServiceLocal participatingSiteService;
    
    @Override
    protected String getQueryOrderClause() {
        return " order by alias.statusDate, alias.id";
    }

    
    @Override
    public void createStatusHistory(Ii spIi,
            List<StudyOverallStatusDTO> statusHistory) throws PAException {
        for (StudyOverallStatusDTO newStatus : statusHistory) {
            StudyOverallStatusDTO oldStatus = getCurrentByStudyProtocol(spIi);
            newStatus.setStudyProtocolIdentifier(spIi);
            runChecksAndCreate(newStatus, oldStatus);
        }
    }
    
    @Override
    public void updateStatusHistory(Ii spIi,
            final List<StudyOverallStatusDTO> statusHistory) throws PAException {
       // StudyOverallStatusDTO current = getCurrentByStudyProtocol(spIi);
        for (StudyOverallStatusDTO dto : statusHistory) {
            dto.setStudyProtocolIdentifier(spIi);
            if (BlConverter.convertToBool(dto.getDeleted())
                    && ISOUtil.isIiNull(dto.getIdentifier())) {
                // this is a status that was added but then deleted via UI.
                // Disregard.
                continue;
            } else if (BlConverter.convertToBool(dto.getDeleted())
                    && !ISOUtil.isIiNull(dto.getIdentifier())) {
                softDelete(dto);
            } else if (!ISOUtil.isIiNull(dto.getIdentifier())) {
                update(dto, false);
            } else {
                insert(dto, false);
            }
        }
     // commented as part of PO-9862
       // closeOpenSitesIfNeeded(spIi, current);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOverallStatusDTO create(StudyOverallStatusDTO newStatus) // NOPMD
            throws PAException {
        if (!ISOUtil.isIiNull(newStatus.getIdentifier())) {
            throw new PAException("Existing StudyOverallStatus objects cannot be modified. Append new object instead.");
        }      
        // enforce business rules
        StudyOverallStatusDTO oldStatus = getCurrentByStudyProtocol(newStatus.getStudyProtocolIdentifier());
        String currentText = null;
        if (oldStatus != null && oldStatus.getReasonText() != null) {
            currentText = oldStatus.getReasonText().getValue();
        }
        String newText = null;
        if (newStatus.getReasonText() != null) {
            newText = newStatus.getReasonText().getValue();
        }
        boolean statusTextChanged = 
                (currentText == null) ? (newText != null) : !currentText.equals(newText);
        if (oldStatus != null && !isTrialStatusOrDateChanged(newStatus, newStatus.getStudyProtocolIdentifier()) 
                && !statusTextChanged) {
            //this means no change in update
            updateAdditionalCommentsIfNeeded(oldStatus, newStatus.getAdditionalComments());
            return oldStatus;
        }       
        StudyOverallStatusDTO createdStatus = runChecksAndCreate(newStatus, oldStatus);
     // commented as part of PO-9862
       // Map <String, ParticipatingSiteDTO> sitesClosedMap = 
               // closeOpenSitesIfNeeded(newStatus.getStudyProtocolIdentifier(), oldStatus);
      //  newStatus.setSitesClosedMap(sitesClosedMap);
        return createdStatus;
    }
 // commented as part of PO-9862
/*
    private Map <String, ParticipatingSiteDTO> closeOpenSitesIfNeeded(Ii spID, StudyOverallStatusDTO oldStatus)
            throws PAException {
        Map <String, ParticipatingSiteDTO> sitesClosedMap = new HashMap<String, ParticipatingSiteDTO>();
        if (oldStatus == null) {
            return sitesClosedMap;
        }
      
        StudyOverallStatusDTO currentStatus = getCurrentByStudyProtocol(spID);
        StudyStatusCode oldCode = CdConverter.convertCdToEnum(
                StudyStatusCode.class, oldStatus.getStatusCode());
        StudyStatusCode newCode = CdConverter.convertCdToEnum(
                StudyStatusCode.class, currentStatus.getStatusCode());
        if (oldCode != newCode && !oldCode.isClosed() // NOPMD
                && newCode.isClosed()) {
            
            sitesClosedMap =  participatingSiteService
                    .closeOpenSites(
                            spID,
                            oldStatus,
                            currentStatus,
                            (StudySourceInterceptor.STUDY_SOURCE_CONTEXT.get() == StudySourceCode.GRID_SERVICE 
                            || StudySourceInterceptor.STUDY_SOURCE_CONTEXT
                                    .get() == StudySourceCode.REST_SERVICE));
        }
        
        return sitesClosedMap;
    }

*/
    /**
     * @param newStatus
     * @param oldStatus
     * @return
     * @throws PAException  
     */
    private StudyOverallStatusDTO runChecksAndCreate(
            StudyOverallStatusDTO newStatus, StudyOverallStatusDTO oldStatus)
            throws PAException {
        DateMidnight oldDate = null;
        if (oldStatus != null) {            
            oldDate = TsConverter.convertToDateMidnight(oldStatus.getStatusDate());
        }
        StudyStatusCode newCode = StudyStatusCode.getByCode(newStatus.getStatusCode().getCode());
        DateMidnight newDate = TsConverter.convertToDateMidnight(newStatus.getStatusDate());
        validateStatusCodeAndDate(newCode, oldDate, newDate);
        validateReasonText(newStatus);

        Session session = PaHibernateUtil.getCurrentSession();
        StudyOverallStatus bo = convertFromDtoToDomain(newStatus);
        session.saveOrUpdate(bo);
        StudyRecruitmentStatus srs = createStudyRecruitmentStatus(bo);
        if (srs != null) {
            session.saveOrUpdate(srs);
        }
        return convertFromDomainToDto(bo);
    }
    

    private void updateAdditionalCommentsIfNeeded(
            StudyOverallStatusDTO oldStatusDTO, St additionalComments) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyOverallStatus sos = (StudyOverallStatus) session.load(
                StudyOverallStatus.class,
                IiConverter.convertToLong(oldStatusDTO.getIdentifier()));
        String newComments = StConverter.convertToString(additionalComments);
        if (!StringUtils.equals(newComments, sos.getAdditionalComments())) {
            sos.setAdditionalComments(newComments);
            session.saveOrUpdate(sos);
            oldStatusDTO.setAdditionalComments(additionalComments); 
        }
    }

    /**
     * Creates a recruitment status for the given StudyOverallStatus.
     * @param bo the StudyOverallStatus domain object.
     * @return the recruitment status domain object.
     */
    StudyRecruitmentStatus createStudyRecruitmentStatus(StudyOverallStatus bo) {
        // automatically update StudyRecruitmentStatus for applicable overall status code's
        if (bo != null && bo.getStatusCode() != null) {
            StudyRecruitmentStatus srsBo = new StudyRecruitmentStatus();
            srsBo.setStatusCode(RecruitmentStatusCode.getByStatusCode(bo.getStatusCode()));
            srsBo.setStatusDate(bo.getStatusDate());
            srsBo.setStudyProtocol(bo.getStudyProtocol());
            return srsBo;
        }
        return null;
    }
    
    private void createStudyRecruitmentStatusForCurrentOverallStatus(
            Ii studyProtocolID) throws PAException {
        
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();

        if (studyRecruitmentStatusServiceLocal
                .getCurrentByStudyProtocol(studyProtocolID) != null) {
            StudyOverallStatusDTO dto = getCurrentByStudyProtocol(studyProtocolID);
            StudyOverallStatus bo = convertFromDtoToDomain(dto);
            StudyRecruitmentStatus srs = createStudyRecruitmentStatus(bo);
            if (srs != null) {
                session.saveOrUpdate(srs);
            }
        }

    }

    /**
     * Performs validation of status date and code.
     * @param oldCode the current status code
     * @param newCode the status code to transition to
     * @param oldDate the current date
     * @param newDate the date of the new transition
     * @throws PAException on error
     */
    private void validateStatusCodeAndDate(StudyStatusCode newCode, DateMidnight oldDate,
            DateMidnight newDate) throws PAException {
        checkCondition(newCode == null, "Study status must be set.");
        checkCondition(newDate == null, "Study status date must be set.");    
        checkCondition(oldDate != null && newDate.isBefore(oldDate),
                       "New current status date should be bigger/same as old date.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOverallStatusDTO update(StudyOverallStatusDTO dto)
            throws PAException {
        return update(dto, false);
    }
    
    private StudyOverallStatusDTO update(StudyOverallStatusDTO dto,
            boolean closeOpenSitesIfNeeded) throws PAException {
        checkBasicConditions(dto);
       // StudyOverallStatusDTO current = getCurrentByStudyProtocol(dto
          //      .getStudyProtocolIdentifier());
        final StudyOverallStatusDTO updatedDTO = super.update(dto);
        // If this update has resulted in a change of the trial's current
        // overall status,
        // we need to sync it up with the recruitment status.
        if (isCurrentStatus(updatedDTO)) {
            createStudyRecruitmentStatusForCurrentOverallStatus(updatedDTO
                    .getStudyProtocolIdentifier());
        }
     // commented as part of PO-9862
        //if (closeOpenSitesIfNeeded) {
          //  closeOpenSitesIfNeeded(updatedDTO.getStudyProtocolIdentifier(),
           //         current);
       // }
        return updatedDTO;
    }

    /**
     * @param dto
     * @throws PAException
     */
    private void checkBasicConditions(StudyOverallStatusDTO dto)
            throws PAException {
        checkCondition(
                StudyStatusCode.getByCode(dto.getStatusCode().getCode()) == null,
                "Study status must be set.");
        checkCondition(
                TsConverter.convertToTimestamp(dto.getStatusDate()) == null,
                "Study status date must be set.");
        checkCondition(ISOUtil.isIiNull(dto.getStudyProtocolIdentifier()),
                "Study protocol must be set.");
        checkCondition(
                DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).before(
                        DateUtils.truncate(TsConverter.convertToTimestamp(dto
                                .getStatusDate()), Calendar.DAY_OF_MONTH)),
                "Study status date cannot be in future.");
        validateReasonText(dto);
    }

    
    @Override
    public void insert(StudyOverallStatusDTO dto) throws PAException {
        insert(dto, false);
    }
    
    @SuppressWarnings("deprecation")
    private void insert(StudyOverallStatusDTO dto, boolean closeOpenSitesIfNeeded) throws PAException {
        checkBasicConditions(dto);
       // StudyOverallStatusDTO current = getCurrentByStudyProtocol(dto
        //        .getStudyProtocolIdentifier());
        StudyOverallStatus bo = convertFromDtoToDomain(dto);
        bo.setId(null);
        bo.setDateLastCreated(new Date());
        bo.setUserLastCreated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));

        Session session = PaHibernateUtil.getCurrentSession();
        session.save(bo);
        session.flush();

        // If this update has resulted in a change of the trial's current
        // overall status,
        // we need to sync it up with the recruitment status.
        StudyOverallStatusDTO newlyCreatedStatus = get(IiConverter
                .convertToIi(bo.getId()));
        if (isCurrentStatus(newlyCreatedStatus)) {
            createStudyRecruitmentStatusForCurrentOverallStatus(newlyCreatedStatus
                    .getStudyProtocolIdentifier());
        }
        // commented as part of PO-9862
        //if (closeOpenSitesIfNeeded) {
         //   closeOpenSitesIfNeeded(
          //          newlyCreatedStatus.getStudyProtocolIdentifier(), current);
      //  }
    }

    /**
     * @param ii index of object
     * @throws PAException exception
     */
    @Override
    public void delete(Ii ii) throws PAException {
        
        final StudyOverallStatusDTO dto = get(ii);
        boolean currentStatus = isCurrentStatus(dto);
        
        super.delete(ii);    
        
        // If this deletion has resulted in a change of the trial's current overall status,
        // we need to sync it up with the recruitment status.
        if (currentStatus) {
            createStudyRecruitmentStatusForCurrentOverallStatus(dto.getStudyProtocolIdentifier());
        }
    }

    private boolean isCurrentStatus(StudyOverallStatusDTO dto) throws PAException {
        StudyOverallStatusDTO current = getCurrentByStudyProtocol(dto
                .getStudyProtocolIdentifier());
        return current != null
                && current.getIdentifier().getExtension()
                        .equals(dto.getIdentifier().getExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.NPathComplexity")
    public boolean isTrialStatusOrDateChanged(StudyOverallStatusDTO newStatusDto, Ii studyProtocolIi)
            throws PAException {
        DocumentWorkflowStatusDTO dwsDTO = documentWorkFlowStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        boolean statusOrDateChanged = true;
        // original submission
        DocumentWorkflowStatusCode currentDwfStatus =
                DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsDTO.getStatusCode()));
        if (DocumentWorkflowStatusCode.SUBMITTED == currentDwfStatus
                && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {
            statusOrDateChanged = false;
        }
        StudyOverallStatusDTO currentDBdto = getCurrentByStudyProtocol(studyProtocolIi);
        if (currentDBdto != null) {
            StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
            DateMidnight currentStatusDate = TsConverter.convertToDateMidnight(currentDBdto.getStatusDate());
            StudyStatusCode newStatusCode = StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode());
            DateMidnight newStatusDate = TsConverter.convertToDateMidnight(newStatusDto.getStatusDate());
     
            boolean codeChanged = (newStatusCode == null) ? (currentStatusCode != null)
                    : !newStatusCode.equals(currentStatusCode);
            boolean statusDateChanged = (currentStatusDate == null) ? (newStatusDate != null)
                    : !currentStatusDate.equals(newStatusDate);     
            if (!codeChanged && !statusDateChanged) {
                statusOrDateChanged = false;
            }
        } else {
            statusOrDateChanged = true;
        }
        return statusOrDateChanged;
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public void validate(StudyOverallStatusDTO statusDto, StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        this.validate(statusDto, studyProtocolDTO, errorMsg);
        if (errorMsg.length() > 0) {
            throw new PAValidationException("Validation Exception " + errorMsg);
        }
    }
    
   
    private void validateReasonText(StudyOverallStatusDTO statusDto) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        StudyStatusCode status = StudyStatusCode.getByCode(CdConverter.convertCdToString(statusDto.getStatusCode()));
        if (status != null && status.requiresReasonText()) {
            if (ISOUtil.isStNull(statusDto.getReasonText())) {
                errorMsg.append("A reason must be entered when the study status is set to "
                        + CdConverter.convertCdToString(statusDto.getStatusCode()) + ".");
            }
            if (StringUtils.length(StConverter.convertToString(statusDto.getReasonText()))
                    > PAAttributeMaxLen.LEN_2000) {
                errorMsg.append("Reason must be less than 2000 characters.");
            }
        } else {
            statusDto.setReasonText(StConverter.convertToSt(null));
        }
        if (StringUtils.isNotEmpty(errorMsg.toString())) {
            throw new PAValidationException("Validation Exception " + errorMsg.toString());
        }
    }

    /**
     * Checks the given condition and generates a PAException accordingly.
     * @param condition The condition that must cause a PAException
     * @param msg The message in the exception
     * @throws PAException thrown if the given condition is true.
     */
    private void checkCondition(boolean condition, String msg) throws PAException {
        if (condition) {
            throw new PAException(msg);
        }
    }

    /**
     * @param statusDto
     * @param studyProtocolDTO
     * @param relaxed 
     * @param addActionError
     * @throws PAException
     * @return
     */
    private StringBuffer enforceBusniessRuleForUpdate(StudyOverallStatusDTO statusDto, // NOPMD
            StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        StudyStatusCode newCode = StudyStatusCode.getByCode(statusDto.getStatusCode().getCode());
        DateMidnight newStatusDate = TsConverter.convertToDateMidnight(statusDto.getStatusDate());
        StudyOverallStatusDTO  currentDBdto = getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
        StudyStatusCode oldStatusCode = currentDBdto != null ? StudyStatusCode
                .getByCode(currentDBdto.getStatusCode().getCode()) : null;
        DateMidnight newStartDate = TsConverter.convertToDateMidnight(studyProtocolDTO.getStartDate());
        String actualString = "Actual";
        String anticipatedString = "Anticipated";
        String newStartDateType = CdConverter.convertCdToString(studyProtocolDTO.getStartDateTypeCode());
        String newCompletionDateType = 
                CdConverter.convertCdToString(studyProtocolDTO.getPrimaryCompletionDateTypeCode());

        if (newCode == null) {
            errMsg.append("Invalid new study status: '" + statusDto.getStatusCode().getCode() + "'. ");
        } 
        
        if (!ISOUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
                && ISOUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())) {

            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (newStartDate.equals(newStatusDate)) {
                    errMsg.append("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!StringUtils.equals(newStartDateType, actualString)) {
                    errMsg.append("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && StringUtils.equals(newStartDateType, anticipatedString)) {
                errMsg.append("Trial start date can be 'Anticipated' only if the status is "
                        + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && StringUtils.equals(newStartDateType, actualString)) {
                errMsg.append("Trial Start date type should be 'Anticipated' and Trial Start date "
                        + "should be future date if Trial Status is changed from 'Approved' to 'Withdrawn'.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                StudyOverallStatusDTO oldStatusDto = getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
                if (StringUtils.equals(newCompletionDateType, anticipatedString)) {
                    errMsg.append("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '");
                    errMsg.append(newCode.getCode());
                    errMsg.append("'.");
                }
                if (TsConverter.convertToDateMidnight(studyProtocolDTO.getPrimaryCompletionDate())
                        .isBefore(TsConverter.convertToDateMidnight(oldStatusDto.getStatusDate()))) {
                    errMsg.append("Primary Completion Date must be the same or greater than Current Trial "
                            + " Status Date when Current Trial Status is '");
                    errMsg.append(newCode.getCode());
                    errMsg.append("'.");
                }
            } else {
                if (!StringUtils.equals(newCompletionDateType, anticipatedString)) {
                    errMsg.append("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
        }
        return errMsg;
    }
    
    @Override
    public List<String> validateTrialStatusAndDates(StudyProtocolDTO dto,
            StudyOverallStatusDTO statusDto) {  
        List<String> errors = new ArrayList<String>();       
        DateMidnight statusDate = TsConverter.convertToDateMidnight(statusDto.getStatusDate());
        String statusCode = CdConverter.convertCdToString(statusDto.getStatusCode());

        DateMidnight today = new DateMidnight();
        StudyProtocolDates dates = AbstractStudyProtocolConverter.convertDatesToDomain(dto);
        DateMidnight startDate = (dates.getStartDate() != null) ? new DateMidnight(dates.getStartDate()) : null;
        DateMidnight primaryCompletionDate =
                (dates.getPrimaryCompletionDate() != null) ? new DateMidnight(dates.getPrimaryCompletionDate()) : null;
        DateMidnight completionDate =
                (dates.getCompletionDate() != null) ? new DateMidnight(dates.getCompletionDate()) : null;
        //If the null flavor is unknown we ignore primary completion date, thus making it option for PO-2429
        boolean unknownPrimaryCompletionDate = ISOUtil.isTsNull(dto
                .getPrimaryCompletionDate());

        // Constraint/Rule: 22 Current Trial Status Date must be current or past.
        if (statusDate == null) {
            errors.add("Current Trial Status Date must be provided.\n");
        } else if (today.isBefore(statusDate)) {
            errors.add("Current Trial Status Date cannot be in the future.\n");
        }
        // Constraint/Rule: 23 Trial Start Date must be current/past if 'actual' trial start date type
        // is selected and must be future if 'anticipated' trial start date type is selected.
        if (dates.getStartDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL
                && today.isBefore(startDate)) {
            errors.add("Actual Trial Start Date must be current or in the past. \n");
        } else if (dates.getStartDateTypeCode() == ActualAnticipatedTypeCode.ANTICIPATED
                && today.isAfter(startDate)) {
            errors.add("Anticipated Start Date must be current or in the future. \n");
        }
        // Constraint/Rule:24 Primary Completion Date must be current/past if 'actual' primary completion date type
        // is selected and must be future if 'anticipated'trial primary completion date type is selected.
        if (unknownPrimaryCompletionDate) {
            if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL) {
                errors.add("Unknown Primary Completion date must be marked as Anticipated or N/A.\n");
            }
        } else {
            if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL
                    && today.isBefore(primaryCompletionDate)) {
                errors.add("Actual Primary Completion Date must be current or in the past.\n");
            } else if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.ANTICIPATED
                    && today.isAfter(primaryCompletionDate)) {
                errors.add("Anticipated Primary Completion Date must be current or in the future. \n");
            }

        }

        Set<String> compareStatusCodes = new HashSet<String>();
        compareStatusCodes.add(StudyStatusCode.APPROVED.getCode());
        compareStatusCodes.add(StudyStatusCode.IN_REVIEW.getCode());
        compareStatusCodes.add(StudyStatusCode.WITHDRAWN.getCode());
        if (!compareStatusCodes.contains(statusCode)
                && ActualAnticipatedTypeCode.ACTUAL != dates.getStartDateTypeCode()) {
            errors.add("Trial Start Date must be Actual for any Current Trial Status besides Approved/In Review.\n");
        }

        // Constraint/Rule: 25 If Current Trial Status is 'Active', Trial Start Date must be the same as
        //Current Trial Status Date and have 'actual' type. New Rule added-01/15/09 if start date is smaller
        //than the Current Trial Status Date, replace Current Trial Status date with the actual Start Date.
        //pa2.0 as part of release removing the "replace Current Trial Status date with the actual Start Date."
        if (StudyStatusCode.ACTIVE.getCode().equals(statusCode)
                && (startDate == null || startDate.isAfter(statusDate)
                || dates.getStartDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL)) {
            errors.add("If Current Trial Status is Active, Trial Start Date must be Actual "
                    + " and same as or smaller than Current Trial Status Date.\n");
        }


        // Constraint/Rule: 27 If Current Trial Status is 'Completed', Primary Completion Date must be the
        // same as Current Trial Status Date and have 'actual' type.
        if (StudyStatusCode.COMPLETE.getCode().equals(statusCode)
                && (dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL)) {
            errors.add("If Current Trial Status is Completed, Primary Completion Date must be Actual. ");
        }

        // Constraint/Rule: 28 If Current Trial Status is 'Completed' or 'Administratively Completed',
        // Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type
        // for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'.
        if (StudyStatusCode.COMPLETE.getCode().equals(statusCode)
                || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(statusCode)) {
            if (dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.ACTUAL) { // NOPMD
                errors.add("If Current Trial Status is Complete or Administratively Complete, "
                        + " Primary Completion Date must be  Actual.\n");
            }
        }

        // Constraint/Rule:29 Trial Start Date must be same/smaller than Primary Completion Date.
        if (!unknownPrimaryCompletionDate
                && dates.getPrimaryCompletionDate() != null
                && dates.getPrimaryCompletionDate()
                        .before(dates.getStartDate())) {
            errors.add("Trial Start Date must be same or earlier than Primary Completion Date.\n");
        }

        if (completionDate != null) {
            if (dates.getCompletionDateTypeCode() == null) {
                errors.add("Completion Date Type must be specified.\n");
            } else {
                if (dates.getCompletionDateTypeCode() == ActualAnticipatedTypeCode.ACTUAL) {
                    if (today.isBefore(completionDate)) {
                        errors.add("Actual Trial Completion Date must be current or in the past.\n");
                    }
                } else {
                    if (today.isAfter(completionDate)) {
                        errors.add("Anticipated Completion Date must be current or in the future\n");
                    }
                }
            }
            if (!unknownPrimaryCompletionDate && completionDate.isBefore(primaryCompletionDate)) {
                errors.add("Completion date must be >= Primary completion date.\n");
            }
        }
        
        return errors;
    }

    private StringBuffer validateTrialDates(StudyProtocolDTO dto,
            StudyOverallStatusDTO statusDto) {
        return new StringBuffer(StringUtils.join(
                validateTrialStatusAndDates(dto, statusDto), ""));
    }

    /**
     * @param documentWorkFlowStatusService the documentWorkFlowStatusService to set
     */
    public void setDocumentWorkFlowStatusService(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyOverallStatusServiceLocal#validate(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO,
     *  gov.nih.nci.pa.iso.dto.StudyProtocolDTO, java.lang.StringBuilder)
     */
    @Override
    public void validate(StudyOverallStatusDTO statusDto,
            StudyProtocolDTO studyProtocolDTO, StringBuilder errorMsg) {
        try {
            if (statusDto == null) {
                errorMsg.append("Study Overall Status cannot be null. ");
            } else {
                if (!ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())
                        && this.isTrialStatusOrDateChanged(statusDto,
                                studyProtocolDTO.getIdentifier())) {
                    errorMsg.append(enforceBusniessRuleForUpdate(statusDto,
                            studyProtocolDTO));
                }
                errorMsg.append(validateTrialDates(studyProtocolDTO, statusDto));
                validateReasonText(statusDto);
            }
        } catch (PAException e) {
            errorMsg.append(e.getMessage());
        }
    }
  

    /**
     * @param studyRecruitmentStatusServiceLocal the studyRecruitmentStatusServiceLocal to set
     */
    public void setStudyRecruitmentStatusServiceLocal(
            StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal) {
        this.studyRecruitmentStatusServiceLocal = studyRecruitmentStatusServiceLocal;
    }

    /**
     * @param statusTransitionService the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionServiceLocal statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    @Override
    public boolean statusHistoryHasWarnings(Ii spID) throws PAException {
        List<StatusDto> validatedList = getValidatedStatusHistory(spID);
        return CollectionUtils.exists(validatedList, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StatusDto s = (StatusDto) arg0;
                return s.hasErrorOfType(ErrorType.WARNING);
            }
        });
    }
    
    @Override
    public List<StudyOverallStatusDTO> getByStudyProtocolWithTransitionValidations(
            Ii spIi) throws PAException {
        List<StudyOverallStatusDTO> list = getByStudyProtocol(spIi);
        List<StatusDto> statusList = getValidatedStatusHistory(spIi);
        for (StudyOverallStatusDTO dto : list) {
            final StatusDto statusDto = statusList.get(list.indexOf(dto));
            dto.setErrors(StConverter.convertToSt(statusDto
                    .getConsolidatedErrorMessage()));
            dto.setWarnings(StConverter.convertToSt(statusDto
                    .getConsolidatedWarningMessage()));
        }
        return list;
    }


    /**
     * @param spID
     * @return
     * @throws PAException
     */
    private List<StatusDto> getValidatedStatusHistory(Ii spID)
            throws PAException {
        StudyProtocolDTO dto = studyProtocolService.getStudyProtocol(spID);
        TrialType trialType = BlConverter.convertToBool(dto
                .getProprietaryTrialIndicator()) ? TrialType.ABBREVIATED
                : TrialType.COMPLETE;
        List<StatusDto> statusList = getStatusHistoryByProtocol(dto.getIdentifier());
        List<StatusDto> validatedList;
        try {
            validatedList = statusTransitionService.validateStatusHistory(
                    AppName.PA, trialType, TransitionFor.TRIAL_STATUS,
                    statusList);
        } catch (PAException e) {
            LOG.error(e, e);
            validatedList = statusList;
            for (StatusDto status : validatedList) {
                ValidationError err = new ValidationError();
                err.setErrorType(ErrorType.ERROR);
                err.setErrorMessage(e.getMessage());
                status.getValidationErrors().add(err);
            }
        }
        return validatedList;
    }

    @Override
    public List<StatusDto> getStatusHistoryByProtocol(Ii spID) throws PAException {
        List<StatusDto> list = new ArrayList<StatusDto>();
        for (StudyOverallStatusDTO dto: getByStudyProtocol(spID)) {
            list.add(convert(dto));
        }
        return list;
    }

    private StatusDto convert(StudyOverallStatusDTO dto) {
        StatusDto status = new StatusDto();
        status.setId(IiConverter.convertToLong(dto.getIdentifier()));
        status.setStatusCode(CdConverter.convertCdToEnum(StudyStatusCode.class,
                dto.getStatusCode()).name());
        status.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
        status.setReason(StConverter.convertToString(dto.getReasonText()));
        status.setComments(StConverter.convertToString(dto
                .getAdditionalComments()));
        status.setSystemCreated(BlConverter.convertToBool(dto
                .getSystemCreated()));
        return status;
    }

    @Override
    public boolean statusHistoryHasErrors(Ii spID) throws PAException {
        List<StatusDto> validatedList = getValidatedStatusHistory(spID);
        return CollectionUtils.exists(validatedList, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StatusDto s = (StatusDto) arg0;
                return s.hasErrorOfType(ErrorType.ERROR);
            }
        });
    }

    @Override
    public void softDelete(StudyOverallStatusDTO dto) throws PAException {
        checkCondition(StringUtils.isBlank(StConverter.convertToString(dto
                .getAdditionalComments())),
                "A comment is required when deleting a status");
        Session session = PaHibernateUtil.getCurrentSession();
        StudyOverallStatus sos = (StudyOverallStatus) session.load(
                StudyOverallStatus.class,
                IiConverter.convertToLong(dto.getIdentifier()));
        sos.setDeleted(true);
        sos.setAdditionalComments(StConverter.convertToString(dto.getAdditionalComments()));
        sos.setDateLastUpdated(new Date());
        sos.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        session.saveOrUpdate(sos);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StudyOverallStatusDTO> getDeletedByStudyProtocol(Ii spIi)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select alias from "
                + getTypeArgument().getName()
                + " alias join alias.studyProtocol sp where sp.id = :studyProtocolId"
                + " and alias.deleted=true " + getQueryOrderClause();        
        Query query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(spIi));
        return convertFromDomainToDTOs(query.list());
    }


    /**
     * @param participatingSiteService the participatingSiteService to set
     */
    public void setParticipatingSiteService(
            ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }

  
    

}
