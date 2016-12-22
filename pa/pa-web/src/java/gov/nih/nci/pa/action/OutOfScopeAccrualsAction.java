package gov.nih.nci.pa.action;

import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.AccrualUtilityService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Denis G. Krylov
 */
public class OutOfScopeAccrualsAction extends ActionSupport implements
        Preparable, ServletRequestAware {

    private static final long serialVersionUID = -7522821525540584076L;

    private AccrualUtilityService accrualUtilityService;
    private LookUpTableServiceRemote lookUpTableService;

    private HttpServletRequest request;

    private List<AccrualOutOfScopeTrialDTO> records = new ArrayList<AccrualOutOfScopeTrialDTO>();

    @Override
    public void prepare() {
        accrualUtilityService = PaRegistry.getAccrualUtilityService();
        lookUpTableService = PaRegistry.getLookUpTableService();
    }

    /**
     * @return Action result.
     * @throws PAException
     *             exception.
     */
    @Override
    public String execute() throws PAException {
        records = accrualUtilityService.getAllOutOfScopeTrials();
        return SUCCESS;
    }

    /**
     * @return Action result.
     * @throws PAException
     *             exception.
     */
    public String save() throws PAException {
        for (AccrualOutOfScopeTrialDTO dto : accrualUtilityService
                .getAllOutOfScopeTrials()) {
            String newCtroAction = request.getParameter("ctroAction_"
                    + dto.getId());
            if (newCtroAction != null) {
                dto.setAction(newCtroAction);
                accrualUtilityService.update(dto);
            }
        }
        request.setAttribute(Constants.SUCCESS_MESSAGE,
                "Your changes have been saved");
        return execute();
    }

    /**
     * @return List<String>
     * @throws PAException
     *             PAException
     */
    public List<String> getCtroActions() throws PAException {
        return Arrays.asList(lookUpTableService.getPropertyValue(
                "accrual.outofscope.actions").split(";"));
    }

    @Override
    public void setServletRequest(HttpServletRequest r) {
        this.request = r;
    }

    /**
     * @return the records
     */
    public List<AccrualOutOfScopeTrialDTO> getRecords() {
        return records;
    }

    /**
     * @param accrualUtilityService
     *            the accrualUtilityService to set
     */
    public void setAccrualUtilityService(
            AccrualUtilityService accrualUtilityService) {
        this.accrualUtilityService = accrualUtilityService;
    }

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

}
