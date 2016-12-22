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
package gov.nih.nci.pa.action;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.WordUtils.capitalize;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action getting the studyOverallStatus history.
 * @author Michael Visee
 */
public class StudyOverallStatusHistoryAction extends ActionSupport implements Preparable { // NOPMD

    private static final long serialVersionUID = 9188773431985815913L;
    
    private static final Logger LOG = Logger
            .getLogger(StudyOverallStatusHistoryAction.class);
    
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private StudyCheckoutServiceLocal studyCheckoutService;
    private AuditTrailServiceLocal auditTrailService;
    
    private Long studyProtocolId;
    private Long statusId;
    private List<StudyOverallStatusWebDTO> overallStatusList;
    private final List<StudyOverallStatusWebDTO> deletedList = new ArrayList<StudyOverallStatusWebDTO>();
    private boolean superAbstractor;
    private String statusDate;
    private String statusCode;
    private String reason;
    private String comment;
    private String deleteComment;
    private boolean changesMadeFlag;
    private boolean validate;
    private boolean displaySuAbstractorAutoCheckoutMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        studyOverallStatusService = PaRegistry.getStudyOverallStatusService();
        studyProtocolServiceLocal = PaRegistry.getStudyProtocolService();
        studyCheckoutService = PaRegistry.getStudyCheckoutService();
        auditTrailService = PaRegistry.getAuditTrailService();
        
        final HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) session
                .getAttribute(Constants.TRIAL_SUMMARY);
        studyProtocolId = spDTO.getStudyProtocolId();
        superAbstractor = request.isUserInRole(Constants.SUABSTRACTOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws PAException {
        overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        Ii spIi = IiConverter.convertToStudyProtocolIi(studyProtocolId);
        List<StudyOverallStatusDTO> isoList = validate ? studyOverallStatusService
                .getByStudyProtocolWithTransitionValidations(spIi) : studyOverallStatusService
                .getByStudyProtocol(spIi);
        for (StudyOverallStatusDTO iso : isoList) {
            overallStatusList.add(new StudyOverallStatusWebDTO(iso));
        }
        flagAvailableActions(overallStatusList);
        
        deletedList.clear();
        List<StudyOverallStatusDTO> delList = studyOverallStatusService.getDeletedByStudyProtocol(spIi);
        for (StudyOverallStatusDTO iso : delList) {
            deletedList.add(new StudyOverallStatusWebDTO(iso));
        }
        
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (validate && !overallStatusList.isEmpty() && !hasWarningsOrErrors(overallStatusList)
                && request.getAttribute(Constants.SUCCESS_MESSAGE) == null) {
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "All Statuses and Status Transitions are valid");
        }
        
        return SUCCESS;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String validateStatusTransitions() throws PAException {
        validate = true;
        String ret = execute();
        invokeSuperAbstractorAutoCheckoutLogic();
        return ret;
    }
    
    private void invokeSuperAbstractorAutoCheckoutLogic() throws PAException {
        // If validation Errors were found, and the trial is not checked-out by
        // anyone for Admin or Scientific abstraction, the system must:
        // Check-out the trial for Admin abstraction under the SuperAbstractor's
        // name, and,
        // Display the message on the next slide
        final HttpServletRequest r = ServletActionContext.getRequest();
        HttpSession session = r.getSession();
        StudyProtocolQueryDTO queryDTO = (StudyProtocolQueryDTO) session
                .getAttribute(Constants.TRIAL_SUMMARY);
        if (hasErrors(overallStatusList)
                && r.isUserInRole(Constants.SUABSTRACTOR)
                && isEmpty(queryDTO.getAdminCheckout().getCheckoutBy())
                && isEmpty(queryDTO.getScientificCheckout().getCheckoutBy())) {
            studyCheckoutService.checkOut(
                    IiConverter.convertToStudyProtocolIi(studyProtocolId),
                    CdConverter.convertToCd(CheckOutType.ADMINISTRATIVE),
                    StConverter.convertToSt(r.getRemoteUser()));
            queryDTO = PaRegistry
                    .getCachingProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(
                            IiConverter.convertToLong(IiConverter
                                    .convertToStudyProtocolIi(studyProtocolId)));
            ActionUtils.loadProtocolDataInSession(queryDTO,
                    new CorrelationUtils(), new PAServiceUtils());
            displaySuAbstractorAutoCheckoutMessage = true;
            changesMadeFlag = true;
        }

    }

    private boolean hasWarningsOrErrors(List<StudyOverallStatusWebDTO> list) {
        for (StudyOverallStatusWebDTO dto : list) {
            if (StringUtils.isNotBlank(dto.getErrors())
                    || StringUtils.isNotBlank(dto.getWarnings())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasErrors(List<StudyOverallStatusWebDTO> list) {
        for (StudyOverallStatusWebDTO dto : list) {
            if (StringUtils.isNotBlank(dto.getErrors())) {
                return true;
            }
        }
        return false;
    }

    private void flagAvailableActions(final List<StudyOverallStatusWebDTO> list) {
        final boolean abstractor = ActionUtils
                .isAbstractor(ServletActionContext.getRequest().getSession());
        for (StudyOverallStatusWebDTO dto : list) {
            dto.setEditable(abstractor);
            dto.setDeletable(abstractor && list.size() > 1);
        }
    }

    /**
     * @return String
     * @throws PAException PAException
     */
    public String delete() throws PAException {        
        try {
            Ii statusIi = IiConverter.convertToStudyProtocolIi(statusId);
            StudyOverallStatusDTO dto = studyOverallStatusService.get(statusIi);
            if (dto != null) {
                dto.setAdditionalComments(StConverter.convertToSt(getDeleteComment()));
                studyOverallStatusService.softDelete(dto);
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
            }
            changesMadeFlag = true;           
        } catch (PAException e) {
            handle(e);
        }
        return execute();
    }

    /**
     * @param e
     */
    private void handle(PAException e) {
        LOG.warn(e, e);
        ServletActionContext.getRequest().setAttribute(
                Constants.FAILURE_MESSAGE, e.getMessage());
    }
    
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String save() throws PAException {
        Ii statusIi = IiConverter.convertToStudyProtocolIi(statusId);
        StudyOverallStatusDTO dto = studyOverallStatusService.get(statusIi);
        if (dto != null) {
            try {
                validateUpdates();
                loadUpdatesIntoDTO(dto);
                validateStatusBeforeUpdate(dto);
                studyOverallStatusService.update(dto);
                ServletActionContext.getRequest().setAttribute(
                        Constants.SUCCESS_MESSAGE,
                        getText("studyOverallStatus.edit.done"));
                changesMadeFlag = true;
                validate = true;
                String ret = execute();
                invokeSuperAbstractorAutoCheckoutLogic();
                return ret;
            } catch (PAException e) {
                handle(e);
            }
        }
        return execute();
    }
    
    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String addNew() throws PAException {
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(studyProtocolId));
        try {
            validateUpdates();
            loadUpdatesIntoDTO(dto);            
            studyOverallStatusService.insert(dto);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE,
                    getText("studyOverallStatus.add.done"));
            changesMadeFlag = true;
            validate = true;            
            String ret = execute();
            invokeSuperAbstractorAutoCheckoutLogic();
            return ret;
        } catch (PAException e) {
            handle(e);
            return execute();
        }        
    }

    // CHECKSTYLE:OFF
    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException JSONException
     */
    @SuppressWarnings("deprecation")
    public StreamResult getAuditTrail() throws UnsupportedEncodingException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        if (statusId != null) {
            List<AuditLogRecord> trail = auditTrailService
                    .getAuditTrail(StudyOverallStatus.class,
                            IiConverter.convertToIi(statusId));
            fillWithAuditTrail(arr, trail);
        }
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes("UTF-8")));
    }
    // CHECKSTYLE:ON
    
    private static final Map<String, String> TRANSLATE_MAP = new HashMap<>();
    static {
        TRANSLATE_MAP.put("commentText", "Why Study Stopped");
        TRANSLATE_MAP.put("additionalComments", "Comments");
        TRANSLATE_MAP.put("statusCode", "Status");
        TRANSLATE_MAP.put("statusDate", "Status Date");
        TRANSLATE_MAP.put("deleted", "Deleted");      
        
    }
    
    private void fillWithAuditTrail(JSONArray arr, List<AuditLogRecord> trail) {
        for (AuditLogRecord r : trail) {
            JSONArray data = new JSONArray();
            data.put(DateFormatUtils.format(r.getCreatedDate(),
                    "MM/dd/yyyy HH:mm"));
            data.put(CsmUserUtil.getGridIdentityUsername(r.getUsername()));
            data.put(capitalize(r.getType().name().toLowerCase()));

            StringBuilder changes = new StringBuilder();
            changes.append("<table><thead><tr><th>Attribute</th><th>Old Value</th><th>New Value</th>"
                    + "</tr></thead>");
            for (AuditLogDetail detail : sort(r.getDetails())) {
                final String attrName = TRANSLATE_MAP
                        .get(detail.getAttribute());
                if (attrName != null) {
                    changes.append("<tr>");
                    changes.append("<td>");
                    changes.append(attrName);
                    changes.append("</td>");
                   
                    changes.append("<td>");
                    changes.append(StringEscapeUtils.escapeHtml(StringUtils
                            .defaultString(adjustAttrValue(detail.getOldValue()))));
                    changes.append("</td>");

                    changes.append("<td>");
                    changes.append(StringEscapeUtils.escapeHtml(StringUtils
                            .defaultString(adjustAttrValue(detail.getNewValue()))));
                    changes.append("</td></tr>");
                }
            }
            changes.append("</table>");
            data.put(changes);
            arr.put(data);
        }
    }

    /**
     * @param details Set<AuditLogDetail>
     * @return Collection<AuditLogDetail>
     */
    Collection<AuditLogDetail> sort(Set<AuditLogDetail> details) {
        TreeSet<AuditLogDetail> sorted = new TreeSet<>(
                new Comparator<AuditLogDetail>() {
                    @Override
                    public int compare(AuditLogDetail o1, AuditLogDetail o2) {                        
                        return o1.getAttribute().compareTo(o2.getAttribute());
                    }
                });
        sorted.addAll(details);
        return sorted;
    }

    private String adjustAttrValue(String val) {
        try {
            return StudyStatusCode.valueOf(val).getCode();
        } catch (Exception e) {
        }

        try {
            return DateFormatUtils.format(DateUtils.parseDate(val,
                    new String[] {"yyyy-MM-dd HH:mm:ss.SSS" }), "MM/dd/yyyy");
        } catch (Exception e) {
        }

        return val;
    }

    private void validateStatusBeforeUpdate(StudyOverallStatusDTO dto)
            throws PAException {
        // If this status is the current one, then we need to apply standard
        // validation rules.
        // We can skip validation for historical statuses.
        final Ii studyProtocolIdentifier = dto.getStudyProtocolIdentifier();
        StudyOverallStatusDTO current = studyOverallStatusService
                .getCurrentByStudyProtocol(studyProtocolIdentifier);
        if (current.getIdentifier().getExtension().equals(dto.getIdentifier().getExtension())) {
            StudyProtocolDTO studyProtocolDTO = studyProtocolServiceLocal.getStudyProtocol(studyProtocolIdentifier);
            studyOverallStatusService.validate(dto, studyProtocolDTO);
        }
    }

    private void validateUpdates() throws PAException {
        Date date = PAUtil.dateStringToDateTime(statusDate);
        if (date == null) {
            throw new PAException(
                    getText("studyOverallStatus.edit.invalidDate"));
        }
    }

    private void loadUpdatesIntoDTO(StudyOverallStatusDTO dto) {
        dto.setReasonText(StConverter.convertToSt(reason));
        dto.setAdditionalComments(StConverter.convertToSt(comment));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                .getByCode(statusCode)));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToDateTime(statusDate)));
    }

    /**
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the overallStatusList
     */
    public List<StudyOverallStatusWebDTO> getOverallStatusList() {
        return overallStatusList;
    }

    /**
     * @param overallStatusList the overallStatusList to set
     */
    public void setOverallStatusList(List<StudyOverallStatusWebDTO> overallStatusList) {
        this.overallStatusList = overallStatusList;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @return the superAbstractor
     */
    public boolean isSuperAbstractor() {
        return superAbstractor;
    }

    /**
     * @param superAbstractor the superAbstractor to set
     */
    public void setSuperAbstractor(boolean superAbstractor) {
        this.superAbstractor = superAbstractor;
    }

    /**
     * @return the id
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * @param id the id to set
     */
    public void setStatusId(Long id) {
        this.statusId = id;
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
        this.statusDate = statusDate;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the changesMadeFlag
     */
    public boolean isChangesMadeFlag() {
        return changesMadeFlag;
    }

    /**
     * @param changesMadeFlag the changesMadeFlag to set
     */
    public void setChangesMadeFlag(boolean changesMadeFlag) {
        this.changesMadeFlag = changesMadeFlag;
    }

    /**
     * @param studyProtocolServiceLocal the studyProtocolServiceLocal to set
     */
    public void setStudyProtocolServiceLocal(
            StudyProtocolServiceLocal studyProtocolServiceLocal) {
        this.studyProtocolServiceLocal = studyProtocolServiceLocal;
    }

    /**
     * @return the deletedList
     */
    public List<StudyOverallStatusWebDTO> getDeletedList() {
        return deletedList; 
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the validate
     */
    public boolean isValidate() {
        return validate;
    }

    /**
     * @param validate the validate to set
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    /**
     * @return the deleteComment
     */
    public String getDeleteComment() {
        return deleteComment;
    }

    /**
     * @param deleteComment the deleteComment to set
     */
    public void setDeleteComment(String deleteComment) {
        this.deleteComment = deleteComment;
    }

    /**
     * @param studyCheckoutService the studyCheckoutService to set
     */
    public void setStudyCheckoutService(
            StudyCheckoutServiceLocal studyCheckoutService) {
        this.studyCheckoutService = studyCheckoutService;
    }

    /**
     * @return the displaySuAbstractorAutoCheckoutMessage
     */
    public boolean isDisplaySuAbstractorAutoCheckoutMessage() {
        return displaySuAbstractorAutoCheckoutMessage;
    }

    /**
     * @param auditTrailService the auditTrailService to set
     */
    public void setAuditTrailService(AuditTrailServiceLocal auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

   

}
