/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Standardized base class for Actions that delete multiple records at once.
 * 
 * @author Denis G. Krylov
 * 
 */
public abstract class AbstractMultiObjectDeleteAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 5313291468883274316L;

    private List<String> objectsToDelete = new ArrayList<String>();

    /**
     * Deletes selected objects.
     * 
     * @throws PAException
     *             PAException
     * 
     */
    public void deleteSelectedObjects() throws PAException {
        checkIfAnythingSelected();
        for (String id : objectsToDelete) {
            deleteObject(Long.parseLong(id));
        }
    }

    /**
     * checkIfAnythingSelected.
     * 
     * @throws PAException
     *             PAException
     */
    protected void checkIfAnythingSelected() throws PAException {
        if (CollectionUtils.isEmpty(objectsToDelete)) {
            throw new PAException(Constants.NOTHING_TO_DELETE_MESSAGE);
        }
    }

    /**
     * Deletes an object with the given ID.
     * 
     * @param objectId
     *            Long
     * @exception PAException
     *                PAException
     */
    public abstract void deleteObject(Long objectId) throws PAException;

    /**
     * @return the objectsToDelete
     */
    public String[] getObjectsToDelete() {
        return objectsToDelete.toArray(new String[0]); //NOPMD
    }

    /**
     * @param objectsToDeleteAsArray
     *            the objectsToDelete to set
     */
    public void setObjectsToDelete(String[] objectsToDeleteAsArray) {
        objectsToDelete = new ArrayList<String>();
        for (String objIdStr : objectsToDeleteAsArray) {
            String[] idSplit = objIdStr.split(",");
            for (String objId : idSplit) {
                if (NumberUtils.isNumber(objId.trim())) {
                    objectsToDelete.add(objId.trim());
                }
            }
        }
    }

    /**
     * Sets success message if not yet set.
     * 
     * @param msg
     *            String
     */
    public void setSuccessMessageIfNotYet(String msg) {
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getAttribute(Constants.SUCCESS_MESSAGE) == null) {
            request.setAttribute(Constants.SUCCESS_MESSAGE, msg);
        }
    }

}
