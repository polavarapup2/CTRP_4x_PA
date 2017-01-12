/**
 * 
 */
package gov.nih.nci.pa.action; // NOPMD

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;
import static gov.nih.nci.pa.util.Constants.IS_RESULTS_ABSTRACTOR;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyContactWebDTO;
import gov.nih.nci.pa.dto.StudyProcessingErrorDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactService;
import gov.nih.nci.pa.service.StudyProcessingErrorService;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Gopal Unnikrishnan (unnikrishnang)
 * @author Biju Joseph (josephb2)
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public class ResultsDashboardAction extends AbstractCheckInOutAction implements
        Preparable, ServletRequestAware, ServletResponseAware {
    private static final String RESULT_ABSTRACTOR_LANDING = "resultAbstractorLanding";
    private static final String AJAX_RESPONSE = "ajaxResponse";

    private static final Logger LOG = Logger.getLogger(DashboardAction.class);

    private static final long serialVersionUID = 8458441253215157815L;
    private final CorrelationUtils correlationUtils = new CorrelationUtils();
    private final PAServiceUtils paServiceUtils = new PAServiceUtils();

    private HttpServletRequest request;

    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyProtocolService studyProtocolService;
    private StudyProcessingErrorService speService;
    // fields that capture search criteria
    private Boolean section801IndicatorYes;
    private Boolean section801IndicatorNo;
    private Date pcdFrom;
    private Date pcdTo;
    private String pcdType;
    private String trialIdentifier;

    // Results
    private List<StudyProtocolQueryDTO> results;

    // Change date parameters
    private Long studyId;
    private Date dateValue;
    private String dateAttr;

    private InputStream ajaxResponseStream;

    // Chart Data
    private int inProcessCnt = 0;
    private int completedCnt = 0;
    private int notStartedCnt = 0;
    private int issuesCnt = 0;

    private String studyNCIId;
    private StudyContactService studyContactService;

    private HttpServletResponse response;

    @Override
    public String execute() throws PAException {
        clearFilters();
        return search();
    }

    /**
     * Search study results
     * 
     * @return String
     * @throws PAException
     *             PAException
     */
    public String search() throws PAException {
        if (!canAccessDashboard()) {
            sendUnauthorizedResponse();
            return null;
        }
        try {
            StudyProtocolQueryCriteria criteria = buildCriteria();
            return search(criteria);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return RESULT_ABSTRACTOR_LANDING;
    }

    /**
     * Update results date of a study protocol
     * 
     * @return String
     */
    public String ajaxChangeDate() {
        if (!canAccessDashboard()) {
            sendUnauthorizedResponse();
            return null;
        }

        Timestamp updateDate = null;

        if (dateValue != null) {
            updateDate = new Timestamp(dateValue.getTime());
        }

        if (studyProtocolService.updateStudyProtocolResultsDate(studyId,
                dateAttr, updateDate)) {
            ajaxResponseStream = new ByteArrayInputStream(SUCCESS.getBytes());
        } else {
            ajaxResponseStream = new ByteArrayInputStream(ERROR.getBytes());
        }
        return AJAX_RESPONSE;
    }

    /**
     * Get the study protocol id for the given trails NCI id
     * 
     * @return result view
     * @throws PAException
     *             PAException
     */
    public String ajaxGetStudyStudyProtocolIdByNCIId() throws PAException {
        Ii nciIi = new Ii();
        nciIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        nciIi.setExtension(getStudyNCIId());
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(nciIi);
        if (sp != null) {
            long spId = IiConverter.convertToLong(sp.getIdentifier());
            ajaxResponseStream = new ByteArrayInputStream(String.valueOf(spId)
                    .getBytes());
            StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(spId);
            ActionUtils.loadProtocolDataInSession(studyProtocolQueryDTO,
                    correlationUtils, paServiceUtils);
        } else {
            ajaxResponseStream = new ByteArrayInputStream("".getBytes());
        }
        return AJAX_RESPONSE;
    }

    /**
     * @param servletRequest
     *            the servletRequest to set
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
    }

    @Override
    public void prepare() {
        ActionUtils.setUserRolesInSession(request);
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        speService = PaRegistry.getStudyProcessingErrorService();
        studyContactService = PaRegistry.getStudyContactService();
    }

    private void sendUnauthorizedResponse() {
        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Page accessible only for Results Abstractors role");
        } catch (IOException ioe) {
            LOG.error("Error sending unauthorized response", ioe);
        }
    }

    private void clearFilters() {
        pcdFrom = null;
        pcdTo = null;
        pcdType = null;
        trialIdentifier = null;
    }

    private boolean canAccessDashboard() {
        return isInRole(IS_RESULTS_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
    }

    @SuppressWarnings("unchecked")
    private String search(final StudyProtocolQueryCriteria criteria) {
        try {

            results = new ArrayList<StudyProtocolQueryDTO>();

            criteria.setNciSponsored(true);
            criteria.setStudyProtocolType(InterventionalStudyProtocol.class
                    .getSimpleName());            

            if (!"GET".equalsIgnoreCase(request.getMethod())) {
                CacheUtils.removeItemFromCache(
                        CacheUtils.getReportingResultsCache(),
                        criteria.getUniqueCriteriaKey());
            }

            List<StudyProtocolQueryDTO> studyProtocolQueryResults = (List<StudyProtocolQueryDTO>) CacheUtils
                    .getFromCacheOrBackend(
                            CacheUtils.getReportingResultsCache(),
                            criteria.getUniqueCriteriaKey(),
                            new CacheUtils.Closure() {
                                @Override
                                public Object execute() throws PAException {

                                    List<StudyProtocolQueryDTO> currentResults = 
                                            PAUtil.applyAdditionalFilters(protocolQueryService
                                            .getStudyProtocolByCriteria(
                                                    criteria,
                                                    SKIP_ALTERNATE_TITLES,
                                                    SKIP_LAST_UPDATER_INFO,
                                                    SKIP_OTHER_IDENTIFIERS,
                                                    SKIP_PROGRAM_CODES));

                                    // collecting protocol-ids.
                                    List<Long> protocolIds = new ArrayList<Long>();
                                    for (StudyProtocolQueryDTO studyProtocolQueryDTO : currentResults) {
                                        protocolIds.add(studyProtocolQueryDTO
                                                .getStudyProtocolId());
                                    }
                                    // load comparison document associated
                                    // with protocols
                                    Map<Long, DocumentDTO> comparisonDocumentMap = PaRegistry
                                            .getDocumentService()
                                            .getDocumentByIDListAndType(
                                                    protocolIds,
                                                    DocumentTypeCode.COMPARISON);

                                    for (StudyProtocolQueryDTO resultQueryDTO : currentResults) {
                                        // fetch the documents from
                                        // in-memory map.
                                        DocumentDTO documentDTO = (DocumentDTO) MapUtils
                                                .getObject(
                                                        comparisonDocumentMap,
                                                        resultQueryDTO
                                                                .getStudyProtocolId());
                                        if (documentDTO != null) {
                                            resultQueryDTO
                                                    .setCcctUserCreatedDate(TsConverter
                                                            .convertToTimestamp(documentDTO
                                                                    .getCcctUserReviewDateTime()));
                                            resultQueryDTO
                                                    .setCtroUserCreatedDate(TsConverter
                                                            .convertToTimestamp(documentDTO
                                                                    .getCtroUserReviewDateTime()));
                                            resultQueryDTO
                                                    .setCcctUserName(StConverter
                                                            .convertToString(documentDTO
                                                                    .getCcctUserName()));
                                            resultQueryDTO.setCtroUserName(PAUtil
                                                    .getDocumentUserCtroOrCcctReviewerName(
                                                            documentDTO, true));
                                        }

                                        
                                        resultQueryDTO
                                                .setDesigneeNamesList(getStudyContacts(resultQueryDTO
                                                        .getStudyProtocolId()));
                                    }

                                    return currentResults;

                                }
                            });

            results.addAll(studyProtocolQueryResults);

            loadResultsChartData(results);

        } catch (Exception e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return RESULT_ABSTRACTOR_LANDING;
    }
    
    private String getStudyContacts(Long studyProtocolId) throws PAException {
        // BJ - the following approach may
        // need refactoring.
        
        List<String> studyContactsNamesList = new ArrayList<String>();
        StringBuffer studyContactNames = new StringBuffer();

        // get study contacts list
        LimitOffset limit = new LimitOffset(
                PAConstants.MAX_SEARCH_RESULTS,
                0);
        StudyContactDTO searchCriteria = new StudyContactDTO();
        searchCriteria
                .setStudyProtocolIdentifier(IiConverter
                        .convertToStudyProtocolIi(studyProtocolId));

        searchCriteria.setRoleCode(CdConverter
                .convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
        try {

            List<StudyContactDTO> studyDesigneeContactDtos = studyContactService
                    .search(searchCriteria,
                            limit);

            if (CollectionUtils
                    .isNotEmpty(studyDesigneeContactDtos)) {
                for (StudyContactDTO scDto : studyDesigneeContactDtos) {
                    FunctionalRoleStatusCode stsCd = CdConverter
                            .convertCdToEnum(
                                    FunctionalRoleStatusCode.class,
                                    scDto.getStatusCode());
                    if (!FunctionalRoleStatusCode.ACTIVE.equals(stsCd) 
                            && !FunctionalRoleStatusCode.PENDING.equals(stsCd)) {
                        continue;
                    }

                    StudyContactWebDTO studyContactWebDTO = new StudyContactWebDTO(
                            scDto);
                    Person person = studyContactWebDTO
                            .getContactPerson();

                    if (person != null) {                        
                        studyContactsNamesList.add(person
                                .getFullNameForResultsDashboard());                        
                    }                    
                }
            }
        } catch (TooManyResultsException e) {
            LOG.error(
                    "Error while searching study contacts",
                    e);
            throw new PAException(e);
        }        
        
        return sortResultsDashboardDesigneeNames(studyContactsNamesList);        
    }
    
    String sortResultsDashboardDesigneeNames(List<String> designees) {
        Collections.sort(designees);
        return StringUtils.join(designees, ", ");
    }

   

    private StudyProtocolQueryCriteria buildCriteria() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        List<Boolean> section801Indicators = new ArrayList<Boolean>();
        if (section801IndicatorYes != null && section801IndicatorYes) {
            section801Indicators.add(true);
        }

        if (section801IndicatorNo != null && section801IndicatorNo) {
            section801Indicators.add(false);
        }
        criteria.setSection801Indicators(section801Indicators);
        criteria.setPcdFrom(pcdFrom);
        criteria.setPcdTo(pcdTo);
        criteria.setPcdType(pcdType);
        if (StringUtils.isNotBlank(trialIdentifier)) {
            criteria.setIdentifierType(StudyProtocolQueryCriteria.ALL);
            criteria.setIdentifier(trialIdentifier);
        }
        return criteria;
    }

    /**
     * Load counts of trials in various results reporting stages, the result
     * reporting stage status is determined as follows
     * 
     * Completed: protocol.trialPublishedDate != null In Process:
     * protocol.reportingInProcessDate != null && protocol.trialPublishedDate ==
     * null Not Started: protocol.reportingInProcessDate == null Issues: unique
     * ( protocol.trialPublishedDate == null && count
     * (protocol.studyProcessingError.resolution date == null) > 0)
     * 
     */
    private void loadResultsChartData(List<StudyProtocolQueryDTO> studyProtocols) {
        for (Iterator iterator = studyProtocols.iterator(); iterator.hasNext();) {
            StudyProtocolQueryDTO studyProtocolQueryDTO = (StudyProtocolQueryDTO) iterator
                    .next();
            if (studyProtocolQueryDTO.getTrialPublishedDate() != null) {
                completedCnt++;
            } else if (studyProtocolQueryDTO.getReportingInProcessDate() != null) {
                inProcessCnt++;
            } else {
                notStartedCnt++;
            }

            if (studyProtocolQueryDTO.getTrialPublishedDate() == null) {
                List<StudyProcessingErrorDTO> errors = speService
                        .getStudyProcessingErrorByStudy(studyProtocolQueryDTO
                                .getStudyProtocolId());
                for (Iterator iterator2 = errors.iterator(); iterator2
                        .hasNext();) {
                    StudyProcessingErrorDTO studyProcessingErrorDTO = (StudyProcessingErrorDTO) iterator2
                            .next();
                    if (studyProcessingErrorDTO.getResolutionDate() == null) {
                        issuesCnt++;
                        break;
                    }
                }
            }
        }
    }

    

    /**
     * @return the protocolQueryService
     */
    ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return the studyProtocolService
     */
    StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @return the section801IndicatorYes
     */
    public Boolean isSection801IndicatorYes() {
        return section801IndicatorYes;
    }

    /**
     * @param section801IndicatorYes
     *            the section801IndicatorYes to set
     */
    public void setSection801IndicatorYes(Boolean section801IndicatorYes) {
        this.section801IndicatorYes = section801IndicatorYes;
    }

    /**
     * @return the section801IndicatorNo
     */
    public Boolean getSection801IndicatorNo() {
        return section801IndicatorNo;
    }

    /**
     * @param section801IndicatorNo
     *            the section801IndicatorNo to set
     */
    public void setSection801IndicatorNo(Boolean section801IndicatorNo) {
        this.section801IndicatorNo = section801IndicatorNo;
    }

    /**
     * @return the trialIdentifier
     */
    public String getTrialIdentifier() {
        return trialIdentifier;
    }

    /**
     * @param trialIdentifier
     *            the trialIdentifier to set
     */
    public void setTrialIdentifier(String trialIdentifier) {
        this.trialIdentifier = trialIdentifier;
    }

    /**
     * @return the pcdFrom
     */
    public Date getPcdFrom() {
        return pcdFrom;
    }

    /**
     * @param pcdFrom
     *            the pcdFrom to set
     */
    public void setPcdFrom(Date pcdFrom) {
        this.pcdFrom = pcdFrom;
    }

    /**
     * @return the pcdTo
     */
    public Date getPcdTo() {
        return pcdTo;
    }

    /**
     * @param pcdTo
     *            the pcdTo to set
     */
    public void setPcdTo(Date pcdTo) {
        this.pcdTo = pcdTo;
    }

    /**
     * @return the pcdType
     */
    public String getPcdType() {
        return pcdType;
    }

    /**
     * @param pcdType
     *            the pcdType to set
     */
    public void setPcdType(String pcdType) {
        this.pcdType = pcdType;
    }

    /**
     * @return the results
     */
    public List<StudyProtocolQueryDTO> getResults() {
        return results;
    }

    /**
     * @return the studyId
     */
    public Long getStudyId() {
        return studyId;
    }

    /**
     * @param studyId
     *            the studyId to set
     */
    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    /**
     * @return the dateAttr
     */
    public String getDateAttr() {
        return dateAttr;
    }

    /**
     * @param dateAttr
     *            the dateAttr to set
     */
    public void setDateAttr(String dateAttr) {
        this.dateAttr = dateAttr;
    }

    /**
     * @return the dateValue
     */
    public Date getDateValue() {
        return dateValue;
    }

    /**
     * @param dateValue
     *            the dateValue to set
     */
    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    /**
     * @return the ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }

    @Override
    public String view() throws PAException {
        return null;
    }

    /**
     * @return the inProcessCnt
     */
    public int getInProcessCnt() {
        return inProcessCnt;
    }

    /**
     * @param inProcessCnt
     *            the inProcessCnt to set
     */
    public void setInProcessCnt(int inProcessCnt) {
        this.inProcessCnt = inProcessCnt;
    }

    /**
     * @return the completedCnt
     */
    public int getCompletedCnt() {
        return completedCnt;
    }

    /**
     * @param completedCnt
     *            the completedCnt to set
     */
    public void setCompletedCnt(int completedCnt) {
        this.completedCnt = completedCnt;
    }

    /**
     * @return the notStartedCnt
     */
    public int getNotStartedCnt() {
        return notStartedCnt;
    }

    /**
     * @param notStartedCnt
     *            the notStartedCnt to set
     */
    public void setNotStartedCnt(int notStartedCnt) {
        this.notStartedCnt = notStartedCnt;
    }

    /**
     * @return the issuesCnt
     */
    public int getIssuesCnt() {
        return issuesCnt;
    }

    /**
     * @param issuesCnt
     *            the issuesCnt to set
     */
    public void setIssuesCnt(int issuesCnt) {
        this.issuesCnt = issuesCnt;
    }

    /**
     * @return the studyNCIId
     */
    public String getStudyNCIId() {
        return studyNCIId;
    }

    /**
     * @param studyNCIId
     *            the studyNCIId to set
     */
    public void setStudyNCIId(String studyNCIId) {
        this.studyNCIId = studyNCIId;
    }

    /**
     * @return studyContactService
     */
    public StudyContactService getStudyContactService() {
        return studyContactService;
    }

    /**
     * @param studyContactService
     *            studyContactService
     */
    public void setStudyContactService(StudyContactService studyContactService) {
        this.studyContactService = studyContactService;
    }

    @Override
    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;
    }

}
