package gov.nih.nci.pa.action;

import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.noniso.dto.StudyProtocolFlagDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FlaggedTrialService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Denis G. Krylov
 */
public class ManageFlaggedTrialsAction extends AbstractMultiObjectDeleteAction
        implements Preparable, ServletRequestAware, ServletResponseAware {

    private static final int L_4000 = 4000;

    private static final long serialVersionUID = -7522821525540584076L;

    private static final Logger LOG = Logger
            .getLogger(ManageFlaggedTrialsAction.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FlaggedTrialService flaggedTrialService;
    private List<StudyProtocolFlagDTO> flaggedTrials;
    private List<StudyProtocolFlagDTO> deletedFlaggedTrials;

    private Long id;
    private String nciID;
    private String reason;
    private String comments;
    private String deleteComments;

    @Override
    public void prepare() {
        flaggedTrialService = PaRegistry.getFlaggedTrialService();
        if (!BooleanUtils.toBoolean((Boolean) request.getSession()
                .getAttribute(Constants.IS_SU_ABSTRACTOR))) {
            try {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Super Abstractors only");
                throw new RuntimeException(); // NOPMD
            } catch (IOException e) { // NOPMD
            }
        }
    }

    /**
     * @return Action result.
     * @throws IOException
     *             IOException
     */
    public String addOrEdit() throws IOException {
        try {
            if (getId() != null) {
                edit();
            } else {
                add();
            }
        } catch (PAException e) {
            LOG.error(e, e);
            response.addHeader("msg", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        }
        return null;
    }

    private void add() throws PAException {
        flaggedTrialService.addFlaggedTrial(getNciID().trim(),
                StudyFlagReasonCode.getByCode(getReason()),
                StringUtils.left(getComments(), L_4000));
    }

    private void edit() throws PAException {
        flaggedTrialService.updateFlaggedTrial(getId(),
                StudyFlagReasonCode.getByCode(getReason()),
                StringUtils.left(getComments(), L_4000));
    }

    /**
     * @return Action result.
     * @throws PAException
     *             exception.
     */
    @Override
    public String execute() throws PAException {
        flaggedTrials = flaggedTrialService.getActiveFlaggedTrials();
        deletedFlaggedTrials = flaggedTrialService.getDeletedFlaggedTrials();
        // If we are doing a data table CSV/Excel export, then merge all flagged
        // trials into a single collection
        // as per requirements.
        for (String pname : request.getParameterMap().keySet()) {
            if (pname.matches("^d-\\d+-e$")) {
                flaggedTrials.addAll(deletedFlaggedTrials);
                deletedFlaggedTrials.clear();
            }
        }
        return SUCCESS;
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     * 
     */
    public String successfulAdd() throws PAException {
        request.setAttribute(Constants.SUCCESS_MESSAGE,
                "Flagged trial has been added successfully");
        return execute();
    }

    /**
     * @return result
     * @throws PAException
     *             exception
     */
    public String delete() throws PAException {
        try {
            deleteSelectedObjects();
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return execute();
    }

    @Override
    public void deleteObject(Long flaggedTrialID) throws PAException {
        flaggedTrialService.delete(flaggedTrialID,
                StringUtils.left(getDeleteComments(), L_4000));
    }

    @Override
    public void setServletRequest(HttpServletRequest r) {
        this.request = r;
    }

    @Override
    public void setServletResponse(HttpServletResponse r) {
        response = r;
    }

    /**
     * @param flaggedTrialService
     *            the flaggedTrialService to set
     */
    public void setFlaggedTrialService(FlaggedTrialService flaggedTrialService) {
        this.flaggedTrialService = flaggedTrialService;
    }

    /**
     * @return the flaggedTrials
     */
    public List<StudyProtocolFlagDTO> getFlaggedTrials() {
        return flaggedTrials;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nciID
     */
    public String getNciID() {
        return nciID;
    }

    /**
     * @param nciID
     *            the nciID to set
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
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
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the deleteComments
     */
    public String getDeleteComments() {
        return deleteComments;
    }

    /**
     * @param deleteComments
     *            the deleteComments to set
     */
    public void setDeleteComments(String deleteComments) {
        this.deleteComments = deleteComments;
    }

    /**
     * @return the deletedFlaggedTrials
     */
    public List<StudyProtocolFlagDTO> getDeletedFlaggedTrials() {
        return deletedFlaggedTrials;
    }

}
