package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PendingPatientAccrualsServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
* @author Kalpana Guthikonda
* @since 10/10/2013
*/
public class PendingAccrualsQueueAction extends AbstractMultiObjectDeleteAction implements Preparable {
    
    private static final long serialVersionUID = -7522821525540584076L;
    private PendingPatientAccrualsServiceLocal patientService;
    private List<PatientStage> pendingAccruals = new ArrayList<PatientStage>();
    private String identifier;

    @Override
    public void prepare() throws PAException {
        setPatientService(PaRegistry.getPendingPatientAccrualsService());        
    }

    /**
     * @return Action result.
     * @throws PAException exception.
     */
    @Override
    public String execute() throws PAException {         
        setPendingAccruals(patientService.getAllPatientsStage(getIdentifier()));
        return SUCCESS;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String delete() throws PAException {
        try {
            checkIfAnythingSelected();
            List<Long> deleteList = new ArrayList<Long>();
            for (String objIdStr : getObjectsToDelete()) {
                String[] idSplit = objIdStr.split(",");
                for (String objId : idSplit) {
                    if (NumberUtils.isNumber(objId.trim())) {
                        deleteList.add(Long.parseLong(objId.trim()));
                    }
                }
            }
            patientService.deletePatientStage(deleteList);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return execute();
    }

    @Override
    public void deleteObject(Long objectId) throws PAException {
        // TODO Auto-generated method stub
    }
    
    /**
     * @param patientService the patientService to set
     */
    public void setPatientService(PendingPatientAccrualsServiceLocal patientService) {
        this.patientService = patientService;
    }

    /**
     * @return the pendingAccruals
     */
    public List<PatientStage> getPendingAccruals() {
        return pendingAccruals;
    }

    /**
     * @param patients the patients to set
     */
    public void setPendingAccruals(List<PatientStage> patients) {
        this.pendingAccruals = patients;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
