package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * ImportCtGovAction
 * 
 * @author Denis G. Krylov
 * 
 */
public final class ImportCtGovAction extends ActionSupport implements
        Preparable { // NOPMD

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ImportCtGovAction.class);
    private CTGovSyncServiceLocal ctGovSyncService;
    private StudyProtocolService studyProtocolService;
    private ProtocolQueryServiceLocal protocolQueryService;

    private String nctID;
    private String nctIdToImport;
    private CTGovStudyAdapter study;
    private boolean searchPerformed;
    private boolean studyExists;
    private StudyProtocolQueryDTO potentialMatch;

    private HttpServletRequest request;

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
        validateNctID(getNctID());
        if (hasActionErrors()) {
            return ERROR;
        }
        try {
            study = ctGovSyncService.getAdaptedCtGovStudyByNctId(getEscapedNctID());
            searchPerformed = true;
            studyExists = !findExistentStudies(getNctID()).isEmpty();
            if (!studyExists) {
                final List<StudyProtocolQueryDTO> potentialMatches = findExistentStudies(study);
                potentialMatch = potentialMatches.isEmpty() ? null
                        : potentialMatches.get(0);
            }
            return SUCCESS;
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }

    private List<StudyProtocolQueryDTO> findExistentStudies(
            CTGovStudyAdapter ctgovStudy) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        if (ctgovStudy != null && StringUtils.isNotBlank(ctgovStudy.getTitle())) {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setOfficialTitle(ctgovStudy.getTitle());
            criteria.setTrialCategory("p");
            criteria.setExcludeRejectProtocol(true);
            for (StudyProtocolQueryDTO dto : protocolQueryService
                    .getStudyProtocolByCriteria(criteria)) {
                if (ctgovStudy.getTitle().equalsIgnoreCase(
                        dto.getOfficialTitle())) {
                    list.add(dto);
                }
            }
        }
        return list;
    }

    private List<StudyProtocolDTO> findExistentStudies(String nct)
            throws PAException {
        return studyProtocolService.getStudyProtocolsByNctId(nct);
    }

    /**
     * @return string
     */
    public String importTrial() {
        validateNctID(getNctIdToImport());
        if (hasActionErrors()) {
            return ERROR;
        }
        try {
            String nciID = ctGovSyncService.importTrial(getNctIdToImport());
            final String[] msgArgs = new String[] {getNctIdToImport(), nciID };
            final String msg = studyExists ? getText(
                    "importctgov.import.update.success", msgArgs) : getText(
                    "importctgov.import.new.success", msgArgs);
            request.setAttribute(Constants.SUCCESS_MESSAGE, msg);
            return SUCCESS;
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }
    
    /**
     * @return res     
     *             IOException
     * @throws PAException
     *             PAException
     */
    public String triggerFullSync() throws PAException {        
        if (ServletActionContext.getRequest().isUserInRole(Constants.SUABSTRACTOR)) {
            PaRegistry.getCTGovSyncNightlyService().updateIndustrialAndConsortiaTrials();
            request.setAttribute(Constants.SUCCESS_MESSAGE, "Sync performed.");
        } else {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    "Only Super Abstractors can trigger ClinicalTrials.gov sync.");
        }
        return SUCCESS;
    }

    /**
     * @param nctIdToValidate
     */
    private void validateNctID(final String nctIdToValidate) {
        if (StringUtils.isBlank(nctIdToValidate)) {
            addActionError("Please provide an ClinicalTrials.gov Identifier value");
        } else if (!StringUtils.isAsciiPrintable(nctIdToValidate)) {
            addActionError("Provided ClinicalTrials.gov Identifier is invalid");
        }
    }
    
    /**
     * This function returns an version of the nctID that has been encoded for use in URLs,
     * such as is used to query the ClinicalTrials.gov API.
     * @return the nctID properly htmlescaped
     */
    @SuppressWarnings("deprecation")
    public String getEscapedNctID() {
        /*
         * This function uses the default encoding because otherwise we would have to deal with an exception
         * for the cases where the specified encoding does not exist.
         */
        return java.net.URLEncoder.encode(nctID);
    }

    /**
     * @return the nctID
     */
    public String getNctID() {
        return nctID;
    }

    /**
     * @param nctID
     *            the nctID to set
     */
    public void setNctID(String nctID) {
        this.nctID = nctID;
    }

    @Override
    public void prepare() {
        ctGovSyncService = PaRegistry.getCTGovSyncService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        request = ServletActionContext.getRequest();
    }

    /**
     * @param ctGovSyncService
     *            the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

    /**
     * @return the study
     */
    public CTGovStudyAdapter getStudy() {
        return study;
    }

    /**
     * @return the searchPerformed
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * @return the nctIdToImport
     */
    public String getNctIdToImport() {
        return nctIdToImport;
    }

    /**
     * @param nctIdToImport
     *            the nctIdToImport to set
     */
    public void setNctIdToImport(String nctIdToImport) {
        this.nctIdToImport = nctIdToImport;
    }

    /**
     * @return the studyExists
     */
    public boolean isStudyExists() {
        return studyExists;
    }

    /**
     * @param studyExists
     *            the studyExists to set
     */
    public void setStudyExists(boolean studyExists) {
        this.studyExists = studyExists;
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return the potentialMatch
     */
    public StudyProtocolQueryDTO getPotentialMatch() {
        return potentialMatch;
    }

}
