/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.StatusTransitionService;
import gov.nih.nci.pa.service.status.ValidationError;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Encapsulates generic status history management methods.
 * 
 * @author dkrylov
 */
public abstract class StatusHistoryManagementAction extends ActionSupport
        implements // NOPMD
        ServletRequestAware, ServletResponseAware, Preparable { // NOPMD

    private static final Logger LOG = Logger
            .getLogger(StatusHistoryManagementAction.class);

    private static final String UTF_8 = "UTF-8";

    /**
     * 
     */
    private static final long serialVersionUID = -4057619618686445613L;

    private static final String STATUS_HISTORY_LIST_KEY = "statusHistoryList";

    private static final String DELETED_STATUS_HISTORY_LIST_KEY = "deletedStatusHistoryList";

    private StatusTransitionService statusTransitionService;

    private ParticipatingOrgServiceLocal participatingOrgService;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private String discriminator;
    private String statusDate;
    private String statusCode;
    private String reason;
    private String comment;
    private String uuid;

    private boolean runValidations;

    // CHECKSTYLE:OFF

    /**
     * @return StreamResult
     * @throws JSONException
     *             JSONException
     * @throws ParseException
     *             ParseException
     * @throws IOException
     */
    public StreamResult addStatus() throws JSONException, ParseException,
            IOException {
        try {
            Collection<StatusDto> hist = getStatusHistoryFromSession();

            StatusDto status = new StatusDto();
            populateStatusFromRequest(status);
            hist.add(status);

            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                    .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    /**
     * @return StreamResult
     * @throws JSONException
     *             JSONException
     * @throws ParseException
     *             ParseException
     * @throws IOException
     */
    public StreamResult clearStatusHistory() throws JSONException,
            ParseException, IOException {
        try {
            setInitialStatusHistory(new ArrayList<StatusDto>());
            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                    .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    /**
     * @param status
     * @throws ParseException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void populateStatusFromRequest(StatusDto status)
            throws ParseException {
        final CodedEnum statEnum = getStatusEnumByCode(getStatusCode());
        status.setStatusCode(((Enum) statEnum).name());
        status.setStatusDate(DateUtils.parseDate(getStatusDate(),
                new String[] { "MM/dd/yyyy" }));
        if (requiresReasonText(statEnum)) {
            status.setReason(StringUtils.left(getReason(), 1000));
        }
        status.setComments(StringUtils.left(
                StringUtils.defaultString(getComment()), 1000));
    }

    protected abstract boolean requiresReasonText(CodedEnum<String> statEnum);

    protected abstract CodedEnum<String> getStatusEnumByCode(String code);

    @SuppressWarnings("rawtypes")
    protected abstract Class<Enum> getStatusEnumClass();

    /**
     * @return OpenSitesWarningRequired
     */
    public abstract boolean isOpenSitesWarningRequired();

    /**
     * @return StreamResult
     * @throws JSONException
     *             JSONException
     * @throws ParseException
     *             ParseException
     * @throws IOException
     */
    public StreamResult editStatus() throws JSONException, ParseException,
            IOException {
        try {
            Collection<StatusDto> hist = getStatusHistoryFromSession();
            for (StatusDto status : hist) {
                if (status.getUuid().equals(getUuid())) {
                    populateStatusFromRequest(status);
                    reSortHistory();
                }
            }
            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                    .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    /**
     * @return
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     */
    public StreamResult deleteStatus() throws JSONException, ParseException,
            IOException {
        try {
            final Collection<StatusDto> hist = getStatusHistoryFromSession();
            final Collection<StatusDto> deleted = getDeletedStatusHistoryFromSession();
            CollectionUtils.filter(hist, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    StatusDto stat = (StatusDto) o;
                    final boolean keep = !stat.getUuid().equals(getUuid());
                    if (!keep) {
                        stat.setDeleted(true);
                        stat.setComments(getComment());
                        deleted.add(stat);
                    }
                    return keep;
                }
            });
            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                    .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }

    private StreamResult handleExceptionDuringAjax(Exception e)
            throws IOException {
        LOG.error(e, e);
        response.addHeader("msg", e.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        return null;
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException
     *             JSONException
     * @throws PAException
     */
    public StreamResult getStatusHistory() throws UnsupportedEncodingException,
            JSONException, PAException {
        Collection<StatusDto> hist = getStatusHistoryFromSession();
        runValidationOrClearMessages(hist);
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        fill(arr, hist);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException
     *             JSONException
     * @throws PAException
     */
    public StreamResult getValidationSummary()
            throws UnsupportedEncodingException, JSONException, PAException {
        runValidations = true;
        Collection<StatusDto> hist = getStatusHistoryFromSession();
        runValidationOrClearMessages(hist);
        boolean errors = false;
        boolean warnings = false;
        for (StatusDto statusDto : hist) {
            errors = errors || statusDto.hasErrorOfType(ErrorType.ERROR);
            warnings = warnings || statusDto.hasErrorOfType(ErrorType.WARNING);
        }
        JSONObject root = new JSONObject();
        root.put("errors", errors);
        root.put("warnings", warnings);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException
     *             JSONException
     * @throws PAException
     */
    public StreamResult mustDisplayOpenSitesWarning()
            throws UnsupportedEncodingException, JSONException, PAException {
        boolean mustDisplay = false;
        Collection<StatusDto> hist = getStatusHistoryFromSession();
        if (!hist.isEmpty() && isOpenSitesWarningRequired()) {
            StatusDto latest = (StatusDto) CollectionUtils.get(hist,
                    hist.size() - 1);
            StudyStatusCode statusCode = StudyStatusCode.valueOf(latest
                    .getStatusCode());
            if (statusCode.isClosed()) {
                // Trial is transitioning into a closed status. May need to
                // display a warning as per PO-8323.
                mustDisplay = trialHasOpenSites();
            }
        }
        JSONObject root = new JSONObject();
        root.put("answer", mustDisplay);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private boolean trialHasOpenSites() throws PAException {
        return !getOpenSiteList().isEmpty();
    }

    private List<ParticipatingOrgDTO> getOpenSiteList() throws PAException {
        List<ParticipatingOrgDTO> list = new ArrayList<>();
        TrialDTO trial = (TrialDTO) request.getSession().getAttribute(
                TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        long spID = Long.valueOf(trial.getIdentifier());
        for (ParticipatingOrgDTO site : participatingOrgService
                .getTreatingSites(spID)) {
            if (site.getRecruitmentStatus() != null
                    && !site.getRecruitmentStatus().isClosed()) {
                list.add(site);
            }
        }
        return list;

    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException
     *             JSONException
     * @throws PAException
     */
    public StreamResult getOpenSites() throws UnsupportedEncodingException,
            JSONException, PAException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        for (ParticipatingOrgDTO site : getOpenSiteList()) {
            JSONObject data = new JSONObject();
            data.put("poID", site.getPoId());
            data.put("name", site.getName());
            data.put("statusCode", site.getRecruitmentStatus().getCode());
            data.put("statusDate", DateFormatUtils.format(
                    site.getRecruitmentStatusDate(), "MM/dd/yyyy"));
            arr.put(data);
        }
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void runValidationOrClearMessages(Collection<StatusDto> hist)
            throws PAException {
        for (StatusDto statusDto : hist) {
            statusDto.getValidationErrors().clear();
        }
        if (runValidations) {
            statusTransitionService.validateStatusHistory(AppName.REGISTRATION,
                    getTrialTypeHandledByThisClass(),
                    getStatusTypeHandledByThisClass(), new ArrayList<>(hist));
        }
    }

    /**
     * @return
     */
    protected abstract TransitionFor getStatusTypeHandledByThisClass();

    /**
     * @return TrialType
     */
    protected abstract TrialType getTrialTypeHandledByThisClass();

    private void fill(JSONArray arr, Collection<StatusDto> hist) {
        for (StatusDto r : hist) {
            JSONObject data = new JSONObject();
            data.put("statusDate",
                    DateFormatUtils.format(r.getStatusDate(), "MM/dd/yyyy"));
            data.put("statusCode", enumToCode(r.getStatusCode()));
            data.put("comments", StringEscapeUtils.escapeHtml(StringUtils
                    .defaultString(r.getComments())));
            data.put("whyStopped", StringEscapeUtils.escapeHtml(StringUtils
                    .defaultString(r.getReason())));
            data.put("validationErrors", renderValidationMessages(r));
            data.put("actions", StringUtils.EMPTY);
            data.put("DT_RowId", r.getUuid());
            arr.put(data);
        }
    }

    private String renderValidationMessages(StatusDto r) {
        StringBuilder errors = new StringBuilder();
        StringBuilder warnings = new StringBuilder();
        for (ValidationError ve : r.getValidationErrors()) {
            if (ve.getErrorType() == ErrorType.ERROR) {
                errors.append(String.format("<div class='error'>%s</div>",
                        StringEscapeUtils.escapeHtml(ve.getErrorMessage())));
            } else {
                warnings.append(String.format("<div class='warning'>%s</div>",
                        StringEscapeUtils.escapeHtml(ve.getErrorMessage())));
            }
        }
        return errors.append(warnings).toString();
    }

    @SuppressWarnings({ "unchecked" })
    private String enumToCode(String enumName) {
        return ((CodedEnum<String>) (Enum.valueOf(getStatusEnumClass(),
                enumName))).getCode();
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected Collection<StatusDto> getStatusHistoryFromSession() {
        Collection<StatusDto> hist = (Collection<StatusDto>) request
                .getSession().getAttribute(getStatusHistorySessionKey());
        if (hist == null) {
            setInitialStatusHistory(new ArrayList<StatusDto>());
            hist = (Collection<StatusDto>) request.getSession().getAttribute(
                    getStatusHistorySessionKey());
        }
        return hist;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected final Collection<StatusDto> getDeletedStatusHistoryFromSession() {
        Collection<StatusDto> hist = (Collection<StatusDto>) request
                .getSession().getAttribute(DELETED_STATUS_HISTORY_LIST_KEY);
        return hist;
    }

    protected final void clearSessionLeftOvers() {
        HttpSession s = request.getSession();
        Enumeration<String> en = s.getAttributeNames();
        while (en.hasMoreElements()) {
            String attr = en.nextElement();
            if (attr.startsWith(STATUS_HISTORY_LIST_KEY)) {
                s.removeAttribute(attr);
            }
        }
    }

    /**
     * Sub-classes MUST call this method in order to set the initial status
     * history.
     * 
     * @param c
     *            Collection<StatusDto>
     */
    protected void setInitialStatusHistory(Collection<StatusDto> c) {
        sortAndSetIntoSession(c);
        request.getSession().setAttribute(DELETED_STATUS_HISTORY_LIST_KEY,
                new HashSet<StatusDto>());
    }

    private void reSortHistory() {
        final Collection<StatusDto> c = getStatusHistoryFromSession();
        sortAndSetIntoSession(c);
    }

    /**
     * @param c
     */
    private void sortAndSetIntoSession(final Collection<StatusDto> c) {
        TreeSet<StatusDto> set = new TreeSet<StatusDto>(
                new ArrayList<StatusDto>(c));
        request.getSession().setAttribute(getStatusHistorySessionKey(), set);
    }

    /**
     * @return
     */
    private String getStatusHistorySessionKey() {
        return STATUS_HISTORY_LIST_KEY
                + StringUtils.defaultString(getDiscriminator());
    }

    // CHECKSTYLE:ON

    @Override
    public final void setServletRequest(HttpServletRequest r) {
        this.request = r;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate
     *            the statusDate to set
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
     * @param statusCode
     *            the statusCode to set
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
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *            the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public final void setServletResponse(HttpServletResponse r) {
        response = r;
    }

    /**
     * @return the servletResponse
     */
    public final HttpServletResponse getServletResponse() {
        return response;
    }

    /**
     * @return the runValidations
     */
    public boolean isRunValidations() {
        return runValidations;
    }

    /**
     * @param runValidations
     *            the runValidations to set
     */
    public void setRunValidations(boolean runValidations) {
        this.runValidations = runValidations;
    }

    /**
     * @return the statusTransitionService
     */
    public StatusTransitionService getStatusTransitionService() {
        return statusTransitionService;
    }

    /**
     * @param statusTransitionService
     *            the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionService statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    @Override
    public void prepare() {
        this.statusTransitionService = PaRegistry.getStatusTransitionService();
        this.participatingOrgService = PaRegistry.getParticipatingOrgService();
    }

    /**
     * @return the request
     */
    public final HttpServletRequest getServletRequest() {
        return request;
    }

    /**
     * @return the discriminator
     */
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * @param discriminator
     *            the discriminator to set
     */
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    /**
     * @param participatingOrgService
     *            the participatingOrgService to set
     */
    public void setParticipatingOrgService(
            ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }

}
