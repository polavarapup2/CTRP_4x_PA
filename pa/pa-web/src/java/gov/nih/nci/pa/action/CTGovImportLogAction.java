package gov.nih.nci.pa.action;

import static gov.nih.nci.pa.domain.CTGovImportLog.ADMIN_ACKNOWLEDGMENT;
import static gov.nih.nci.pa.domain.CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
import static gov.nih.nci.pa.domain.CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT;
import gov.nih.nci.pa.decorator.CTGovImportLogDecorator;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.dto.CTGovTrialWorkflowHistoryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Monish
 *
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.TooManyMethods" })
public class CTGovImportLogAction extends ActionSupport implements
Preparable {

    private static final String DETAILS = "details";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CTGovImportLogAction.class);
    private static final String ACKNOWLEDGMENT = "Acknowledgment";
    
    private HttpServletRequest request;
    private boolean searchPerformed;
    
    private List<CTGovImportLogDecorator> allCtGovImportLogs = new ArrayList<CTGovImportLogDecorator>();
    private List<CTGovTrialWorkflowHistoryDTO> nctCtGovImportLogs = new ArrayList<CTGovTrialWorkflowHistoryDTO>();
    private CTGovSyncServiceLocal ctGovSyncService;
    private String nctId;
    private String logsOnOrAfter;
    private String logsOnOrBefore;    
    private CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();

    @Override
    public void prepare() {
        request = ServletActionContext.getRequest();
        ctGovSyncService = PaRegistry.getCTGovSyncService();
    }
    
    /**
     * @return res
     * 
     */
    @Override
    public String execute() {
        return SUCCESS;
    }    
    
    /**
     * @return string
     */
    public String query() {
        if (hasActionErrors()) {
            return ERROR;
        }
        try {      
            Date onOrAfter = StringUtils.isNotBlank(getLogsOnOrAfter()) ? PAUtil
                    .dateStringToDateTime(getLogsOnOrAfter()) : new Date(0);
            Date onOrBefore = StringUtils.isNotBlank(getLogsOnOrBefore()) ? PAUtil
                    .endOfDay(PAUtil.dateStringToDateTime(getLogsOnOrBefore()))
                    : PAUtil.endOfDay(new Date());
                    
            validateTimeLine(onOrAfter, onOrBefore);
            //get all the log entries sorted by date
            searchCriteria.setOnOrAfter(onOrAfter);
            searchCriteria.setOnOrBefore(onOrBefore);
            List<CTGovImportLog> importLogs = ctGovSyncService.getLogEntries(searchCriteria);
            
            processAndDecorate(importLogs);    
            
            setSearchPerformed(true);
            return SUCCESS;
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }

    /**
     * @param importLogs
     */
    private void processAndDecorate(List<CTGovImportLog> importLogs) {

        Map<String, CTGovImportLogDecorator> map = new HashMap<String, CTGovImportLogDecorator>();
        for (CTGovImportLog importLog : importLogs) {
            CTGovImportLogDecorator decor = map.get(importLog.getNctID());
            if (decor == null) {
                decor = new CTGovImportLogDecorator(importLog, ctGovSyncService);
                map.put(importLog.getNctID(), decor);
                getAllCtGovImportLogs().add(decor);
            }
        }
    }
    
    /**
     * Displays pop up page showing the history of CT.Gov import log entries
     * for a specified NCT identifier. 
     * @return list of CT.Gov import log entries for a specified NCT identifier. 
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public String showDetailspopup() {
        try {
            //get the all the log entries for the specified NCT ID.
            searchCriteria.setNctIdentifier(getNctId());
            request.setAttribute("nctID", getNctId());
            List<CTGovImportLog> logEntries = ctGovSyncService.getLogEntries(searchCriteria);
            for (CTGovImportLog existingEntry : logEntries) {                
                request.setAttribute(
                        "nciID",
                        StringUtils.isNotEmpty(existingEntry.getNciID()) ? existingEntry
                                .getNciID() : request.getAttribute("nciID"));
                request.setAttribute(
                        "title",
                        StringUtils.isNotEmpty(existingEntry.getTitle()) ? existingEntry
                                .getTitle() : request.getAttribute("title"));
                
                extractDetailsFromLogEntry(existingEntry);                
            }
            return DETAILS;
        } catch (PAException pae) {
            request.setAttribute(Constants.FAILURE_MESSAGE, pae.getLocalizedMessage());
            LOG.error(pae, pae);
        }
        return ERROR;
    }
    
    private void extractDetailsFromLogEntry(CTGovImportLog log) {
        nctCtGovImportLogs.add(exrtactCtGovImportEventDTO(log));

        StudyInbox si = log.getStudyInbox();
        // if there is a performed admin/scientific acknowledgment
        // then the log information needs to be split to show
        // two entries : first entry to show close date and acknowledged user
        // and second entry to show pending information.
        if (log.getAckPerformed().equals(ADMIN_ACKNOWLEDGMENT)) {
            nctCtGovImportLogs.add(extractAdminAckDTO(si));
        } else if (log.getAckPerformed().equals(SCIENTIFIC_ACKNOWLEDGEMENT)) {
            nctCtGovImportLogs.add(extractSciAckDTO(si));
        } else if (log.getAckPerformed().equals(
                ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT)) {
            nctCtGovImportLogs.add(extractAdminAckDTO(si));
            nctCtGovImportLogs.add(extractSciAckDTO(si));
        }

    }

    /**
     * @param si
     * @return
     */
    private CTGovTrialWorkflowHistoryDTO extractSciAckDTO(StudyInbox si) {
        CTGovTrialWorkflowHistoryDTO newEntry = new CTGovTrialWorkflowHistoryDTO();
        newEntry.setAction(ACKNOWLEDGMENT);
        newEntry.setDateCreated(si.getScientificCloseDate());
        newEntry.setUserCreated(CsmUserUtil.getDisplayUsername(
                si.getScientificAcknowledgedUser()));
        newEntry.setAckPerformed(SCIENTIFIC_ACKNOWLEDGEMENT);
        return newEntry;
    }

    /**
     * @param si
     * @return
     */
    private CTGovTrialWorkflowHistoryDTO extractAdminAckDTO(StudyInbox si) {
        CTGovTrialWorkflowHistoryDTO newEntry = new CTGovTrialWorkflowHistoryDTO();
        newEntry.setAction(ACKNOWLEDGMENT);
        newEntry.setDateCreated(si.getAdminCloseDate());
        newEntry.setUserCreated(CsmUserUtil.getDisplayUsername(
                si.getAdminAcknowledgedUser()));
        newEntry.setAckPerformed(ADMIN_ACKNOWLEDGMENT);
        return newEntry;
    }

    private CTGovTrialWorkflowHistoryDTO exrtactCtGovImportEventDTO(CTGovImportLog log) {
        CTGovTrialWorkflowHistoryDTO dto = new CTGovTrialWorkflowHistoryDTO();
        dto.setAction(log.getAction());
        dto.setUserCreated(log.getUserCreated());
        dto.setDateCreated(log.getDateCreated());
        dto.setImportStatus(log.getImportStatus());
        dto.setAckPending(log.getAckPendingAtTimeOfImport());
        return dto;
    }

    /**
     * Validates start and end date
     * @param onOrAfter start date
     * @param onOrBefore end date
     * @throws PAException PAException
     */
    private void validateTimeLine(Date onOrAfter, Date onOrBefore) throws PAException {
        if (onOrAfter != null && onOrBefore != null
                && onOrAfter.after(onOrBefore)) {
            throw new PAException(
                    "Dates are inconsistent and will never produce results. "
                            + "Please correct");
        }
    }
    
    /**
     * @param searchPerformed the searchPerformed to set
     */
    public void setSearchPerformed(boolean searchPerformed) {
        this.searchPerformed = searchPerformed;
    }

    /**
     * @return the searchPerformed
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * @return the allCtGovImportLogs
     */
    public List<CTGovImportLogDecorator> getAllCtGovImportLogs() {
        return allCtGovImportLogs;
    }

    /**
     * @param allCtGovImportLogs the allCtGovImportLogs to set
     */
    public void setAllCtGovImportLogs(List<CTGovImportLogDecorator> allCtGovImportLogs) {
        this.allCtGovImportLogs = allCtGovImportLogs;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

    /**
     * @return nctCtGovImportLogs
     */
    public List<CTGovTrialWorkflowHistoryDTO> getNctCtGovImportLogs() {
        return nctCtGovImportLogs;
    }

    /**
     * @param nctCtGovImportLogs nctCtGovImportLogs to set
     */
    public void setNctCtGovImportLogs(List<CTGovTrialWorkflowHistoryDTO> nctCtGovImportLogs) {
        this.nctCtGovImportLogs = nctCtGovImportLogs;
    }

    /**
     * @return nctId
     */
    public String getNctId() {
        return nctId;
    }

    /**
     * @param nctId nctId to set
     */
    public void setNctId(String nctId) {
        this.nctId = nctId;
    }

    /**
     * @return the logsOnOrAfter
     */
    public String getLogsOnOrAfter() {
        return logsOnOrAfter;
    }

    /**
     * @param logsOnOrAfter the logsOnOrAfter to set
     */
    public void setLogsOnOrAfter(String logsOnOrAfter) {
        this.logsOnOrAfter = logsOnOrAfter;
    }

    /**
     * @return the logsOnOrBefore
     */
    public String getLogsOnOrBefore() {
        return logsOnOrBefore;
    }

    /**
     * @param logsOnOrBefore the logsOnOrBefore to set
     */
    public void setLogsOnOrBefore(String logsOnOrBefore) {
        this.logsOnOrBefore = logsOnOrBefore;
    }
    
    /**
     * @return search criteria
     */
    public CTGovImportLogSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * @param searchCriteria search criteria to set
     */
    public void setSearchCriteria(CTGovImportLogSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}