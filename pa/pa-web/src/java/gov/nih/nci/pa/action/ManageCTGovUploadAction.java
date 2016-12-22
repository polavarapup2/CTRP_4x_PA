package gov.nih.nci.pa.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovUploadServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Denis G. Krylov
 * 
 */
public class ManageCTGovUploadAction extends ActionSupport implements
        Preparable {

    private static final long serialVersionUID = -768848603259442783L;

    private LookUpTableServiceRemote lookUpTableService;
    private CTGovUploadServiceLocal ctGovUploadServiceLocal;

    private Boolean enabled;
    private boolean superAbstractor;
    private HttpServletRequest request;

    @Override
    public void prepare() {
        request = ServletActionContext.getRequest();
        superAbstractor = request.isUserInRole(Constants.SUABSTRACTOR);
        lookUpTableService = PaRegistry.getLookUpTableService();
        ctGovUploadServiceLocal = PaRegistry.getCTGovUploadService();
    }

    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        enabled = Boolean.valueOf(lookUpTableService
                .getPropertyValue("ctgov.ftp.enabled"));
        return SUCCESS;
    }

    /**
     * 
     * @return string
     * @throws PAException
     *             PAException
     */
    public String save() throws PAException {        
        if (superAbstractor) {
            PAUtil.updatePaProperty("ctgov.ftp.enabled", enabled.toString());
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    Constants.UPDATE_MESSAGE);
        } else {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    "Only Super Abstractors can change ClinicalTrials.gov Upload settings");
        }
        return execute();
    }

    /**
     * @return res
     * @throws IOException
     *             IOException
     * @throws PAException
     *             PAException
     */
    public String triggerCTGovUpload() throws PAException, IOException {        
        if (superAbstractor) {
            ctGovUploadServiceLocal.uploadToCTGov();
            request.setAttribute(Constants.SUCCESS_MESSAGE, "Upload performed");
        } else {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    "Only Super Abstractors can trigger ClinicalTrials.gov Upload");
        }
        return SUCCESS;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
