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
package gov.nih.nci.pa.action;

import static org.apache.commons.lang.StringUtils.isEmpty;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * Action class for viewing and editing the protocol status.
 *
 * @author Hugh Reinhart
 * @since 08/20/2008
 */
public class StudyOverallStatusAction extends ActionSupport implements Preparable, ServletRequestAware {

    
    private static final long serialVersionUID = -3647758169522163514L;

    private Map<String, String> dateTypeList;
    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudyCheckoutServiceLocal studyCheckoutService;
   
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    private Ii spIdIi;
    private String currentTrialStatus;
    private String statusDate;
    private String statusReason;
    private String startDate;
    private String primaryCompletionDate;
    private String completionDate;
    private String startDateType;
    private String primaryCompletionDateType;
    private String completionDateType;
    private String additionalComments;
    private boolean displaySuAbstractorAutoCheckoutMessage;
    
    private HttpServletRequest request;

    private StudyOverallStatusDTO sosDto;
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        prepareServices();
        try {
            prepareData();
        } catch (PAException e) {
            throw new RuntimeException(e); // NOPMD
        }
    }

    /**
     * Initialize the services used by this action.
     */
    void prepareServices() {
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyOverallStatusService = PaRegistry.getStudyOverallStatusService();
        studyProtocolService = PaRegistry.getStudyProtocolService();      
        studyCheckoutService = PaRegistry.getStudyCheckoutService();
    }

    /**
     * Initialize the data used by this action.
     * @throws PAException 
     */
    void prepareData() throws PAException {
        dateTypeList = new HashMap<String, String>();
        String code = ActualAnticipatedTypeCode.ACTUAL.getCode();
        dateTypeList.put(code, code);
        code = ActualAnticipatedTypeCode.ANTICIPATED.getCode();
        dateTypeList.put(code, code);
        HttpSession session = ServletActionContext.getRequest().getSession();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) session.getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
        sosDto = studyOverallStatusService.getCurrentByStudyProtocol(spIdIi);
    }

    /**
     * @return Action result.
     */
    @Override
    public String execute() {
        try {
            loadForm();
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return Action.SUCCESS;
    }

    /**
     * @return result
     */
    public String update() {
        clearErrorsAndMessages();        
        try {
            StudyOverallStatusDTO statusDto = getStudyOverallStatus();
            StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
            studyProtocolDTO.setIdentifier(spIdIi);
            getStudyProtocolDates(studyProtocolDTO);
            validateOverallStatus(statusDto);      
            insertOrUpdateStudyOverallStatus(statusDto);
            updateStudyProtocol();
            if (!hasActionErrors()) {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                loadForm();
                // PO-8409: run transitions validations.
                runTransitionValidationAndInvokeSuAbstractorLogic(studyProtocolDTO);
            }            
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return Action.SUCCESS;
    }

    /**
     * @param spDTO StudyProtocolDTO
     * @throws PAException PAException
     */
    void runTransitionValidationAndInvokeSuAbstractorLogic(
            StudyProtocolDTO spDTO) throws PAException {
        boolean errors = studyOverallStatusService.statusHistoryHasErrors(spDTO
                .getIdentifier());
        boolean warnings = studyOverallStatusService
                .statusHistoryHasWarnings(spDTO.getIdentifier());
        if (errors && warnings) {
            addActionError(getText("trialStatus.warningsAndErrors"));
        } else if (errors) {
            addActionError(getText("trialStatus.errors"));
        } else if (warnings) {
            addActionError(getText("trialStatus.warnings"));
        }

        // If validation Errors were found, and the trial is not checked-out by
        // anyone for Admin or Scientific abstraction, the system must:
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        // Display the message on the next slide
        final HttpServletRequest r = ServletActionContext.getRequest();
        HttpSession session = r.getSession();
        StudyProtocolQueryDTO queryDTO = (StudyProtocolQueryDTO) session
                .getAttribute(Constants.TRIAL_SUMMARY);
        if (errors && request.isUserInRole(Constants.SUABSTRACTOR)
                && isEmpty(queryDTO.getAdminCheckout().getCheckoutBy())
                && isEmpty(queryDTO.getScientificCheckout().getCheckoutBy())) {
            studyCheckoutService.checkOut(spIdIi,
                    CdConverter.convertToCd(CheckOutType.ADMINISTRATIVE),
                    StConverter.convertToSt(r.getRemoteUser()));
            queryDTO = PaRegistry
                    .getCachingProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(
                            IiConverter.convertToLong(spIdIi));
            ActionUtils.loadProtocolDataInSession(queryDTO,
                    new CorrelationUtils(), paServiceUtils);
            displaySuAbstractorAutoCheckoutMessage = true;
        }
        
    }

    /**
     * Loads the form data for display.
     * @throws PAException if an error occurs
     */
    @SuppressWarnings("PMD.NPathComplexity")
    void loadForm() throws PAException {
        StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(spIdIi);
        if (spDto != null) {            
            setStartDate(TsConverter.convertToString(spDto.getStartDate()));
            setPrimaryCompletionDate(TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
            setCompletionDate(TsConverter.convertToString(spDto.getCompletionDate()));
            setStartDateType(spDto.getStartDateTypeCode() != null ? spDto
                    .getStartDateTypeCode().getCode() : null);
            setPrimaryCompletionDateType(spDto
                    .getPrimaryCompletionDateTypeCode() != null ? spDto
                    .getPrimaryCompletionDateTypeCode().getCode() : null);
            setCompletionDateType(spDto.getCompletionDateTypeCode() != null ? spDto
                    .getCompletionDateTypeCode().getCode() : null);
        } else {
            setStartDate(null);
            setPrimaryCompletionDate(null);
            setCompletionDate(null);
            setStartDateType(null);
            setPrimaryCompletionDateType(null);
            setCompletionDateType(null);
        }        
        sosDto = studyOverallStatusService.getCurrentByStudyProtocol(spIdIi);
        if (sosDto != null) {
            setCurrentTrialStatus((sosDto.getStatusCode() != null) ? sosDto.getStatusCode().getCode() : null);
            setStatusDate(TsConverter.convertToString(sosDto.getStatusDate()));
            setStatusReason(StConverter.convertToString(sosDto.getReasonText()));
            setAdditionalComments(StConverter.convertToString(sosDto.getAdditionalComments()));
        } else {
            setCurrentTrialStatus(null);
            setStatusDate(null);
            setStatusReason(null);
            setAdditionalComments(null);
        }
    }

    /**
     * Extract the StudyOverallStatusDTO from the submitted data.
     * @return The StudyOverallStatusDTO extracted from the form submitted data
     */ 
    StudyOverallStatusDTO getStudyOverallStatus() {
        StudyOverallStatusDTO statusDto = new StudyOverallStatusDTO();
        statusDto.setIdentifier(IiConverter.convertToStudyOverallStatusIi((Long) null));
        statusDto.setReasonText(StConverter.convertToSt(getStatusReason()));
        statusDto.setAdditionalComments(StConverter.convertToSt(getAdditionalComments()));
        statusDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(currentTrialStatus)));
        statusDto.setStatusDate(TsConverter.convertToTs(ISOUtil.dateStringToTimestamp(statusDate)));
        statusDto.setStudyProtocolIdentifier(spIdIi);
        return statusDto;
    }

    /**
     * Validates the overallStatus.
     * @param statusDto The StudyOverallStatusDTO to validate.
     * @throws PAException If an error occurs.
     */
    void validateOverallStatus(StudyOverallStatusDTO statusDto) throws PAException {
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIdentifier(spIdIi);
        getStudyProtocolDates(studyProtocolDTO);
        StringBuilder sb = new StringBuilder();
        if (ISOUtil.isCdNull(statusDto.getStatusCode())) {
            sb.append("Please provide a value for Current Trial Status. ");
        } else {
            if (StringUtils.isBlank(statusDate)) {
                sb.append("Current Trial Status Date is required. ");
            }
            if (StringUtils.isBlank(startDate)) {
                sb.append("Trial Start Date is required. ");
            }
            if (StringUtils.isBlank(startDateType)) {
                sb.append("Trial Start Date type (Anticipated or Actual) is required. ");
            }   
            // don't validate primary completion date if it is non
            // interventional trial
            // and CTGovXmlRequired is false.
            StudyProtocolDTO spDto = studyProtocolService
                    .getStudyProtocol(spIdIi);
            if (PAUtil.isPrimaryCompletionDateRequired(spDto)) {
                runRequiredPcdChecks(sb);
            }
            runNonApplicablePcdChecks(sb);
           
        }
        if (sb.length() > 0) {
            throw new PAException(sb.toString());
        }
        studyOverallStatusService.validate(statusDto, studyProtocolDTO);
        
    }

    /**
     * @param sb
     * @throws PAException
     */
    private void runNonApplicablePcdChecks(StringBuilder sb) throws PAException {
        if (ActualAnticipatedTypeCode.NA.getCode().equals(
                primaryCompletionDateType)
                && StringUtils.isBlank(paServiceUtils.getCtepOrDcpId(
                        IiConverter.convertToLong(spIdIi),
                        PAConstants.DCP_IDENTIFIER_TYPE))) {
            sb.append("Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'. ");
        }
        if (ActualAnticipatedTypeCode.NA.getCode().equals(
                primaryCompletionDateType)
                && StringUtils.isNotBlank(primaryCompletionDate)) {
            sb.append("When the Primary Completion Date Type is set to 'N/A', "
                    + "the Primary Completion Date must be null. ");
        }
    }

    /**
     * @param sb
     */
    private void runRequiredPcdChecks(StringBuilder sb) {
        if (StringUtils.isBlank(primaryCompletionDate)
                && !ActualAnticipatedTypeCode.NA.getCode().equals(
                        primaryCompletionDateType)) {
            sb.append("Primary Completion Date is required, unless this is a DCP trial "
                    + "and the date type is set to N/A. ");
        }
        if (StringUtils.isBlank(primaryCompletionDateType)) {
            sb.append("Primary Completion Date type is required. ");
        }
    }

    /**
     * Fills the dates in the given study protocol.
     * @param dto The study protocol dto to fill.
     */
    void getStudyProtocolDates(StudyProtocolDTO dto) {
        dto.setStartDate(TsConverter.convertToTs(ISOUtil.dateStringToTimestamp(startDate)));
        dto.setStartDateTypeCode(CdConverter.convertStringToCd(startDateType));
        dto.setPrimaryCompletionDate(TsConverter.convertToTs(ISOUtil.dateStringToTimestamp(primaryCompletionDate)));
        dto.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(primaryCompletionDateType));
        dto.setCompletionDate(TsConverter.convertToTs(ISOUtil.dateStringToTimestamp(completionDate)));
        dto.setCompletionDateTypeCode(CdConverter.convertStringToCd(completionDateType));        
    }

    /**
     * Insert or updates the given status.
     * @param statusDto The status to insert or update
     * @throws PAException If an error occurs
     */
    void insertOrUpdateStudyOverallStatus(StudyOverallStatusDTO statusDto) throws PAException {        
        if (StringUtils.isNotBlank(currentTrialStatus)) {
            StudyProtocolQueryDTO spqDTO =
                    protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(spIdIi));
            StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(spIdIi);
            // original submission            
            if (sosDto != null
                    && spqDTO.getDocumentWorkflowStatusCode() != null
                    && spqDTO.getDocumentWorkflowStatusCode().getCode().equalsIgnoreCase("SUBMITTED")
                    && IntConverter.convertToInteger(spDTO.getSubmissionNumber()) == 1) {                
                sosDto = studyOverallStatusService.getCurrentByStudyProtocol(spIdIi);
                statusDto.setIdentifier(sosDto.getIdentifier());
                studyOverallStatusService.update(statusDto);
            } else {
                studyOverallStatusService.create(statusDto);
            }
            // set the current date and status to the session
            spqDTO.setStudyStatusCode(StudyStatusCode.getByCode(currentTrialStatus));
            spqDTO.setStudyStatusDate(ISOUtil.dateStringToTimestamp(statusDate));
            // set the nee object back to session
            ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, spqDTO);
        }
    }
    
    /**
     * Updates the study protocol dates.
     */
    void updateStudyProtocol() {
        try {
            StudyProtocolDTO dto = studyProtocolService.getStudyProtocol(spIdIi);
            getStudyProtocolDates(dto);
            studyProtocolService.updateStudyProtocol(dto);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }

    /**
     * @return the currentTrialStatus
     */
    public String getCurrentTrialStatus() {
        return currentTrialStatus;
    }

    /**
     * @param currentTrialStatus the currentTrialStatus to set
     */
    public void setCurrentTrialStatus(String currentTrialStatus) {
        this.currentTrialStatus = currentTrialStatus;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = ISOUtil.normalizeDateString(statusDate);
    }

    /**
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = ISOUtil.normalizeDateString(startDate);
    }

    /**
     * @return the primaryCompletionDate
     */
    public String getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }

    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(String primaryCompletionDate) {
        this.primaryCompletionDate = ISOUtil.normalizeDateString(primaryCompletionDate);
    }

    /**
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = ISOUtil.normalizeDateString(completionDate);
    }

    /**
     * @return the startDateType
     */
    public String getStartDateType() {
        return startDateType;
    }

    /**
     * @param startDateType the startDateType to set
     */
    public void setStartDateType(String startDateType) {
        this.startDateType = startDateType;
    }

    /**
     * @return the primaryCompletionDateType
     */
    public String getPrimaryCompletionDateType() {
        return primaryCompletionDateType;
    }

    /**
     * @param primaryCompletionDateType the primaryCompletionDateType to set
     */
    public void setPrimaryCompletionDateType(String primaryCompletionDateType) {
        this.primaryCompletionDateType = primaryCompletionDateType;
    }

    /**
     * @return the completionDateType
     */
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }

    /**
     * @return the dateTypeList
     */
    public Map<String, String> getDateTypeList() {
        return dateTypeList;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    @Override
    public void setServletRequest(HttpServletRequest r) {
        this.request = r;
    }

    /**
     * @return the sosDto
     */
    public StudyOverallStatusDTO getSosDto() {
        return sosDto;
    }

    /**
     * @param sosDto the sosDto to set
     */
    public void setSosDto(StudyOverallStatusDTO sosDto) {
        this.sosDto = sosDto;
    }

    /**
     * @return the additionalComments
     */
    public String getAdditionalComments() {
        return additionalComments;
    }

    /**
     * @param additionalComments the additionalComments to set
     */
    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    /**
     * @return the displaySuAbstractorAutoCheckoutMessage
     */
    public boolean isDisplaySuAbstractorAutoCheckoutMessage() {
        return displaySuAbstractorAutoCheckoutMessage;
    }

    /**
     * @param studyCheckoutService the studyCheckoutService to set
     */
    public void setStudyCheckoutService(
            StudyCheckoutServiceLocal studyCheckoutService) {
        this.studyCheckoutService = studyCheckoutService;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }
}
