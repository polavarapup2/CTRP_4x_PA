package gov.nih.nci.pa.action;

import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 *
 */
public abstract class AbstractCheckInOutAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * SHOW_VIEW_REFRESH.
     */
    public static final String SHOW_VIEW_REFRESH = "viewRefresh";
    /**
     * StudyCheckoutServiceLocal.
     */
    private StudyCheckoutServiceLocal studyCheckoutService;
    /**
     * studyProtocolId.
     */
    private Long studyProtocolId;
    /**
     * checkInReason.
     */
    private String checkInReason;
    
    /**
     *  superAbstractorId
     */
    private Long superAbstractorId;
    
    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public final String checkInSciAndCheckOutToSuperAbs() throws PAException {
        try {
            getStudyCheckoutService().checkInSciAndCheckOutToSuperAbs(
                    getStudyProtocolId(),
                    getCheckInReason(),
                    CSMUserService.getInstance().getCSMUserById(
                            getSuperAbstractorId()));
            String msg = getText("studyProtocol.trial.checkInSciAndCheckOutToSuperAbs");
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, msg);
            return view();
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }
    
    /** 
     * @return String
     * @throws PAException PAException
     */
    public abstract String view() throws PAException;
   

    /**
     * Forced administrative and scientific check-in for super abstractors.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String adminAndScientificCheckIn() throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        if (!suAbs) {
            throw new PAException(
                    "Admin & Scientific forced check-in is only available to super abstractors.");
        }
        // forcibly check out, in case a different user has this trial checked
        // in
        adminCheckOut();
        scientificCheckOut();
        // now check in.
        checkIn(CheckOutType.ADMINISTRATIVE);
        checkIn(CheckOutType.SCIENTIFIC);
        ServletActionContext.getRequest().setAttribute(
                Constants.SUCCESS_MESSAGE,
                getText("studyProtocol.trial.checkIn.both"));
        return SHOW_VIEW_REFRESH;
    }

    /**
     * Forced administrative and scientific check-out for super abstractors.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String adminAndScientificCheckOut() throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        if (!suAbs) {
            throw new PAException(
                    "Admin & Scientific forced check-out is only available to super abstractors.");
        }
        // forcibly check in, in case a different user has this trial checked
        // out.
        adminCheckIn();
        scientificCheckIn();
        // now check out.
        adminCheckOut();
        scientificCheckOut();
        return SHOW_VIEW_REFRESH;
    }

    /**
     * Administrative check-in.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String adminCheckIn() throws PAException {
        return checkIn(CheckOutType.ADMINISTRATIVE);
    }

    /**
     * Administrative check-out.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String adminCheckOut() throws PAException {
        return checkOut(CheckOutType.ADMINISTRATIVE);
    }

    private String checkIn(CheckOutType checkOutType) throws PAException {
        try {
            studyCheckoutService.checkIn(
                    IiConverter.convertToStudyProtocolIi(studyProtocolId),
                    CdConverter.convertToCd(checkOutType),
                    StConverter.convertToSt(UsernameHolder.getUser()),
                    StConverter.convertToSt(getCheckInReason()));
            String msg = getText("studyProtocol.trial.checkIn."
                    + checkOutType.name());
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, msg);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }

    private String checkOut(CheckOutType checkOutType) throws PAException {
        try {
            getStudyCheckoutService().checkOut(
                    IiConverter.convertToStudyProtocolIi(studyProtocolId),
                    CdConverter.convertToCd(checkOutType),
                    StConverter.convertToSt(UsernameHolder.getUser()));
            String msg = getText("studyProtocol.trial.checkOut."
                    + checkOutType.name());
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, msg);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }

    /**
     * @return the checkInReason
     */
    public String getCheckInReason() {
        return checkInReason;
    }

    /**
     * @return the studyCheckoutService
     */
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return studyCheckoutService;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * Scientific check-in.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String scientificCheckIn() throws PAException {
        return checkIn(CheckOutType.SCIENTIFIC);
    }
    
    /**
     * Scientific check-out.
     * 
     * @return The result name
     * @throws PAException
     *             exception
     */
    public String scientificCheckOut() throws PAException {
        return checkOut(CheckOutType.SCIENTIFIC);
    }

    /**
     * @param checkInReason
     *            the checkInReason to set
     */
    public void setCheckInReason(String checkInReason) {
        this.checkInReason = checkInReason;
    }

    /**
     * @param studyCheckoutService the studyCheckoutService to set
     */
    public void setStudyCheckoutService(StudyCheckoutServiceLocal studyCheckoutService) {
        this.studyCheckoutService = studyCheckoutService;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    
    /**
     * @return the superAbstractorId
     */
    public Long getSuperAbstractorId() {
        return superAbstractorId;
    }

    /**
     * @param superAbstractorId the superAbstractorId to set
     */
    public void setSuperAbstractorId(Long superAbstractorId) {
        this.superAbstractorId = superAbstractorId;
    }
}