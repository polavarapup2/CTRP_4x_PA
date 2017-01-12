/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class DiseaseActionTest extends AbstractPaActionTest {

    private DiseaseAction diseaseAction;
    private DiseaseWebDTO dto;

    /**
     * Initialization method.
     * @throws PAException if an error occurs
     */

    @Before
    public void setUp() throws PAException {
        diseaseAction = new DiseaseAction();
        dto = new DiseaseWebDTO();
        diseaseAction.setDisease(dto);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        diseaseAction.prepare();
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#edit()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testEdit() throws PAException {
        diseaseAction.setSelectedRowIdentifier("1");
        String result = diseaseAction.edit();
        assertEquals("edit", result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#update()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdate() throws PAException {
        DiseaseWebDTO webdto = new DiseaseWebDTO();
        webdto.setDiseaseIdentifier("1");
        diseaseAction.setDisease(webdto);
        assertEquals("list", diseaseAction.update());
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#update()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdate2() throws PAException {
        String result = diseaseAction.update();
        assertEquals("list", result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#display()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testdisplay() throws PAException {
        getRequest().setupAddParameter("diseaseId", "1");
        assertEquals("edit", diseaseAction.display());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#delete()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testDelete() throws PAException {
        diseaseAction.setObjectsToDelete(new String[] {"1"});
        assertEquals("list", diseaseAction.delete());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#execute()}.
     * @throws PAException if an error occurs
     */
    @Test
    public void testExecute() throws PAException {
        assertEquals("list", diseaseAction.execute());
        assertNotNull(diseaseAction.getDiseaseList());
    }
}
