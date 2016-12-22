/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class AbstractMultiObjectDeleteActionTest extends AbstractPaActionTest {
    
    

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    private AbstractMultiObjectDeleteAction getActionInstance() {
        AbstractMultiObjectDeleteAction action = new AbstractMultiObjectDeleteAction() {            
            @Override
            public void deleteObject(Long objectId) throws PAException {  
                assertEquals(1, objectId.longValue());
            }
        };
        return action;
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractMultiObjectDeleteAction#deleteSelectedObjects()}.
     * @throws PAException 
     */
    @Test(expected=PAException.class)
    public final void testDeleteSelectedObjectsNothingSelected() throws PAException {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        action.deleteSelectedObjects();
    }
    
    public final void testDeleteSelectedObjects() throws PAException {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        action.setObjectsToDelete(new String[] {"1"});
        action.deleteSelectedObjects();
    }
    

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractMultiObjectDeleteAction#checkIfAnythingSelected()}.
     * @throws PAException 
     */
    @Test(expected=PAException.class)
    public final void testCheckIfAnythingSelected() throws PAException {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        action.deleteSelectedObjects();
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractMultiObjectDeleteAction#getObjectsToDelete()}.
     */
    @Test
    public final void testGetObjectsToDelete() {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        action.setObjectsToDelete(new String[] {"1"});
        ArrayUtils.isEquals(new String[] {"1"}, action.getObjectsToDelete());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractMultiObjectDeleteAction#setObjectsToDelete(java.lang.String[])}.
     */
    @Test
    public final void testSetObjectsToDelete() {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        action.setObjectsToDelete(new String[] {"1","2","","abc"});
        ArrayUtils.isEquals(new String[] {"1","2"}, action.getObjectsToDelete());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractMultiObjectDeleteAction#setSuccessMessageIfNotYet(java.lang.String)}.
     */
    @Test
    public final void testSetSuccessMessageIfNotYet() {
        AbstractMultiObjectDeleteAction action = getActionInstance();
        final HttpServletRequest request = ServletActionContext.getRequest();
        action.setSuccessMessageIfNotYet("msg");
        assertEquals("msg", request.getAttribute(Constants.SUCCESS_MESSAGE));
        action.setSuccessMessageIfNotYet("msg2");
        assertEquals("msg", request.getAttribute(Constants.SUCCESS_MESSAGE));
    }

}
