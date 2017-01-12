/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.DiseaseWebDTO;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpDiseaseDetailsActionTest extends AbstractPaActionTest{

    private static PopUpDiseaseDetailsAction popUpDiseaseDetailsAction;
    
    @Before
    public void setUp(){
        popUpDiseaseDetailsAction = new PopUpDiseaseDetailsAction();
        
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpDiseaseDetailsAction#execute()}.
     */
    @Test
    public void testExecute() throws Exception{
     getRequest().setupAddParameter("diseaseId", "1");
     assertEquals("success" ,popUpDiseaseDetailsAction.execute());  
     popUpDiseaseDetailsAction.getDisease();
     popUpDiseaseDetailsAction.getParentList();
     popUpDiseaseDetailsAction.getChildList();
     popUpDiseaseDetailsAction.setChildList(new ArrayList<DiseaseWebDTO>());
     popUpDiseaseDetailsAction.setParentList(new ArrayList<DiseaseWebDTO>());
     popUpDiseaseDetailsAction.setDisease(new DiseaseWebDTO());
     getRequest().setupAddParameter("diseaseId", "");
    }

}
