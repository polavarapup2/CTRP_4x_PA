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

import static org.apache.commons.lang.WordUtils.capitalize;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action getting the participating site Status history.
 * @author Vinodh
 */
public class SiteStatusHistoryAction extends ActionSupport implements Preparable { // NOPMD

    private static final long serialVersionUID = 9188773431985815913L;
    
    private static final Logger LOG = Logger
            .getLogger(SiteStatusHistoryAction.class);
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    
    private ParticipatingSiteServiceLocal participatingSiteService;
    private StudySiteServiceLocal studySiteService;
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    private StatusTransitionService statusTransitionService;
    private StudyProtocolServiceLocal studyProtocolServiceLocal;
    private AuditTrailServiceLocal auditTrailService;
    
    private Long studyProtocolId;
    private Long studySiteId;
    private Long statusId;
    private List<StudyOverallStatusWebDTO> siteStatusList;
    private final List<StudyOverallStatusWebDTO> deletedList = new ArrayList<StudyOverallStatusWebDTO>();
    private boolean superAbstractor;
    private String statusDate;
    private String statusCode;
    private String comment;
    private String deleteComment;
    private boolean changesMadeFlag;
    private boolean validate;
    
    private StudyProtocolQueryDTO spDTO =  null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        participatingSiteService = PaRegistry.getParticipatingSiteService();
        studyProtocolServiceLocal = PaRegistry.getStudyProtocolService();
        studySiteService = PaRegistry.getStudySiteService();
        studySiteAccrualStatusService = PaRegistry.getStudySiteAccrualStatusService();
        statusTransitionService = PaRegistry.getStatusTransitionService();
        auditTrailService = PaRegistry.getAuditTrailService();
        
        final HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        spDTO = (StudyProtocolQueryDTO) session
                .getAttribute(Constants.TRIAL_SUMMARY);
        studyProtocolId = spDTO.getStudyProtocolId();
        superAbstractor = request.isUserInRole(Constants.SUABSTRACTOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws PAException {
        siteStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        loadStudySiteAccrualStatusList();
        if (validate) {
            validateStatusTransitionHistory();
        }
        
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            for (String actionErr : getActionErrors()) {
                sb.append("\n").append(actionErr);
            }
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, sb.toString());
        }
        
        flagAvailableActions(siteStatusList);
        
        deletedList.clear();
        List<StudySiteAccrualStatusDTO> delList = 
                studySiteAccrualStatusService.getDeletedByStudySite(
                        IiConverter.convertToStudySiteIi(studySiteId));
        for (StudySiteAccrualStatusDTO iso : delList) {
            deletedList.add(convert(iso));
        }
        
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (validate && !hasWarningsOrErrors(siteStatusList) && !hasActionErrors()
                && request.getAttribute(Constants.SUCCESS_MESSAGE) == null) {
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "All statuses and status transitions are valid");
        }
        
        return SUCCESS;
    }
    
    /**
     * Run transition history validations
     */
    private void validateStatusTransitionHistory() {
        List<StatusDto> statusDtoList = convertToStatusDtoList(siteStatusList);
        try {
            statusDtoList = statusTransitionService.validateStatusHistory(AppName.PA,
                    spDTO.isProprietaryTrial() ? TrialType.ABBREVIATED : TrialType.COMPLETE,
                            TransitionFor.SITE_STATUS, statusDtoList);
            setSiteStatusList(convertToStatusWebDtoList(statusDtoList));
        } catch (Exception e) {
            LOG.info(e);
            handle("Error validating participating site status history for participating site, " 
            + studySiteId , new PAException(e));
        }
    }

    /**
     * @param siteStatusWebDTOList
     * @return List<StatusDto>
     */
    private List<StatusDto> convertToStatusDtoList(
            List<StudyOverallStatusWebDTO> siteStatusWebDTOList) {
        List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
        for (StudyOverallStatusWebDTO statusWebDto : siteStatusWebDTOList) {
            statusDtoList.add(convert(statusWebDto));
        }
        
        return statusDtoList;
    }
    
    /**
     * @param siteStatusDTOList
     * @return List<StudyOverallStatusWebDTO>
     */
    private List<StudyOverallStatusWebDTO> convertToStatusWebDtoList(
            List<StatusDto> siteStatusDTOList) {
        List<StudyOverallStatusWebDTO> tmpSSList = new ArrayList<StudyOverallStatusWebDTO>();
        for (StatusDto statusDto : siteStatusDTOList) {
            tmpSSList.add(convert(statusDto));
        }
        
        return tmpSSList;
    }
    
    private StatusDto convert(StudyOverallStatusWebDTO dto) {
        StatusDto status = new StatusDto();
        status.setId(dto.getId());
        status.setStatusCode(RecruitmentStatusCode.getByCode(dto.getStatusCode()).name());
        status.setStatusDate(dto.getStatusDateRaw());
        status.setSystemCreated(dto.isSystemCreated());
        status.setComments(dto.getComments());
        status.setUpdatedBy(dto.getUpdatedBy());
        status.setUpdatedOn(dto.getUpdatedOn());
        return status;
    }
    
    private StudyOverallStatusWebDTO convert(StatusDto dto) {
        StudyOverallStatusWebDTO status = new StudyOverallStatusWebDTO();
        status.setId(dto.getId());
        status.setStatusCode(RecruitmentStatusCode.valueOf(dto.getStatusCode()).getCode());
        status.setStatusDateRaw(dto.getStatusDate());
        status.setStatusDate(SDF.format(dto.getStatusDate()));
        status.setSystemCreated(dto.isSystemCreated());
        status.setComments(dto.getComments());
        status.setUpdatedBy(dto.getUpdatedBy());
        status.setUpdatedOn(dto.getUpdatedOn());
        
        status.setErrors(dto.getConsolidatedErrorMessage());
        status.setWarnings(dto.getConsolidatedWarningMessage());
        return status;
    }

    private void loadStudySiteAccrualStatusList() {
        if (studySiteId == null) {
            return;
        }
        siteStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        List<StudySiteAccrualStatusDTO> isoList;
        try {
            isoList = studySiteAccrualStatusService.getStudySiteAccrualStatusByStudySite(
                    IiConverter.convertToStudySiteIi(Long.valueOf(studySiteId)));
            StudyOverallStatusWebDTO studySiteStatus = null;
            for (StudySiteAccrualStatusDTO iso : isoList) {
                studySiteStatus = convert(iso);
                siteStatusList.add(studySiteStatus);
            }
        } catch (PAException e) {
            handle(e);
        }
    }

    /**
     * @param iso
     * @return
     * @throws PAException
     */
    private StudyOverallStatusWebDTO convert(StudySiteAccrualStatusDTO iso)
            throws PAException {
        StudyOverallStatusWebDTO studySiteStatus = new StudyOverallStatusWebDTO();
        studySiteStatus.setId(IiConverter.convertToLong(iso.getIdentifier()));
        studySiteStatus.setStatusCode(RecruitmentStatusCode.getByCode(iso
            .getStatusCode().getCode()).getCode());
        studySiteStatus.setStatusDate(PAUtil.normalizeDateString(
        TsConverter.convertToTimestamp(iso.getStatusDate()).toString()));
        studySiteStatus.setStatusDateRaw(TsConverter.convertToTimestamp(iso.getStatusDate()));
        studySiteStatus.setComments(iso.getComments().getValue());
        studySiteStatus.setUpdatedOn(TsConverter.convertToTimestamp(iso.getUpdatedOn()));
        
        String updatedBy = ISOUtil.isStNull(iso.getUpdatedBy()) ? ""
                : CsmUserUtil.getDisplayUsername(CSMUserService.getInstance()
                        .getCSMUser(StConverter.convertToString(iso.getUpdatedBy())));    
        studySiteStatus.setUpdatedBy(updatedBy);
        return studySiteStatus;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String validateStatusTransitions() throws PAException {
        validate = true;
        String ret = execute();
        return ret;
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
        if (statusId == null) {
            return execute();
        }
        try {
            StudySiteAccrualStatusDTO dto = studySiteAccrualStatusService.getStudySiteAccrualStatus(
                    IiConverter.convertToStudySiteIi(statusId));
            if (dto != null) {
                if (StringUtils.isEmpty(getDeleteComment())) {
                    throw new PAException("Comment for deleting status is missing");
                }
                dto.setComments(StConverter.convertToSt(getDeleteComment()));
                studySiteAccrualStatusService.softDelete(dto);
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
       handle(null, e);
    }

    /**
     * @param msg
     * @param e
     */
    private void handle(String msg, PAException e) {
        LOG.warn(e, e);
        
        ServletActionContext.getRequest().setAttribute(
                Constants.FAILURE_MESSAGE, (msg == null?"" : msg) + "\n" + e.getMessage());
    }
    
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String save() throws PAException {
        if (statusId == null) {
            return execute();
        }
        
        try {
            Ii statusIi = IiConverter.convertToStudySiteOverallStatusIi(statusId);
            StudySiteAccrualStatusDTO dto = studySiteAccrualStatusService.getStudySiteAccrualStatus(statusIi);
            if (dto != null) {
                validateUpdates();
                loadUpdatesIntoDTO(dto);
                studySiteAccrualStatusService.updateStudySiteAccrualStatus(dto);
                ServletActionContext.getRequest().setAttribute(
                        Constants.SUCCESS_MESSAGE,
                        getText("siteStatus.edit.done"));
                changesMadeFlag = true;
                validate = true;
               }
            } catch (PAException e) {
                handle(e);
            }
        return execute();
    }
    
    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String addNew() throws PAException {
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setStudySiteIi(IiConverter.convertToStudySiteIi(Long.valueOf(studySiteId)));
        try {
            validateUpdates();
            loadUpdatesIntoDTO(dto);
            if (hasActionErrors()) {
                StringBuffer sb = new StringBuffer();
                for (String actionErr : getActionErrors()) {
                    sb.append("\n").append(actionErr);
                }
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, sb.toString());
                return execute();
            }
            studySiteAccrualStatusService.createStudySiteAccrualStatus(dto);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE,
                    getText("siteStatus.add.done"));
            changesMadeFlag = true;
            validate = true;            
            String ret = execute();
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
                    .getAuditTrail(StudySiteAccrualStatus.class,
                            IiConverter.convertToIi(statusId));
            fillWithAuditTrail(arr, trail);
        }
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes("UTF-8")));
    }
    // CHECKSTYLE:ON
    
    private static final Map<String, String> TRANSLATE_MAP = new HashMap<>();
    static {
        TRANSLATE_MAP.put("comments", "comments");
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
            for (AuditLogDetail detail : r.getDetails()) {
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

    private String adjustAttrValue(String val) {
        try {
            return RecruitmentStatusCode.valueOf(val).getCode();
        } catch (Exception e) {
        }

        try {
            return DateFormatUtils.format(DateUtils.parseDate(val,
                    new String[] {"yyyy-MM-dd HH:mm:ss.SSS" }), "MM/dd/yyyy");
        } catch (Exception e) {
        }

        return val;
    }

    /**
     * Consolidates all action error messages into a single string and sets it as failure message
     */
    private void consolidateAndClearActionErrors() {
        StringBuffer sb = new StringBuffer();
        for (String actionErr : getActionErrors()) {
            sb.append("\n").append(actionErr);
        }
        
        ServletActionContext.getRequest().setAttribute(
                Constants.FAILURE_MESSAGE, sb.toString().replaceAll("\\.\\s?", "\n"));
        clearActionErrors();
    }

    private void validateUpdates() throws PAException {
        Date date = PAUtil.dateStringToDateTime(statusDate);
        if (date == null) {
            throw new PAException(
                    getText("siteStatus.edit.invalidDate"));
        }
    }

    private void loadUpdatesIntoDTO(StudySiteAccrualStatusDTO dto) {
        dto.setComments(StConverter.convertToSt(comment));
        dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .getByCode(statusCode)));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToDateTime(statusDate)));
        dto.setUpdatedOn(TsConverter.convertToTs(new Date()));
        dto.setUpdatedBy(StConverter.convertToSt(UsernameHolder.getUser()));
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
     * @return the siteStatusList
     */
    public List<StudyOverallStatusWebDTO> getSiteStatusList() {
        return siteStatusList;
    }

    /**
     * @param siteStatusList the siteStatusList to set
     */
    public void setSiteStatusList(List<StudyOverallStatusWebDTO> siteStatusList) {
        this.siteStatusList = siteStatusList;
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
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }

    /**
     * @param studySiteId the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
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
     * @param auditTrailService the auditTrailService to set
     */
    public void setAuditTrailService(AuditTrailServiceLocal auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    /**
     * @return the participatingSiteService
     */
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return participatingSiteService;
    }

    /**
     * @param participatingSiteService the participatingSiteService to set
     */
    public void setParticipatingSiteService(
            ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }

    /**
     * @return the studySiteService
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @return the studySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(
            StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @return the statusTransitionService
     */
    public StatusTransitionService getStatusTransitionService() {
        return statusTransitionService;
    }

    /**
     * @param statusTransitionService the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionService statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    /**
     * @return the studyProtocolServiceLocal
     */
    public StudyProtocolServiceLocal getStudyProtocolServiceLocal() {
        return studyProtocolServiceLocal;
    }

    /**
     * @return the auditTrailService
     */
    public AuditTrailServiceLocal getAuditTrailService() {
        return auditTrailService;
    }


}
