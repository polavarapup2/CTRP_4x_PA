/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.SubGroupsWebDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.hibernate.validator.AssertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class SubGroupsActionTest extends AbstractPaActionTest{

    
    SubGroupsAction subGroupsAction;
    SubGroupsWebDTO dto;
    
    @Before
    public void setUp(){
        subGroupsAction = new SubGroupsAction();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        dto = new SubGroupsWebDTO();
        dto.setDescription("test");
        dto.setGroupNumberText("gpText");
        subGroupsAction.setSubGroupsWebDTO(dto);
        
    }
    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#query()}.
     * @throws PAException 
     */
    @Test
    public void testQuery() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        StratumGroupServiceLocal stratumGroupServiceLocal = mock(StratumGroupServiceLocal.class);
        List<StratumGroupDTO> isoList = new ArrayList<StratumGroupDTO>();
        StratumGroupDTO dto = new StratumGroupDTO();
        dto.setDescription(StConverter.convertToSt("description"));
        dto.setGroupNumberText(StConverter.convertToSt("groupNumberText"));
        dto.setIdentifier(IiConverter.convertToIi(1L));
        isoList.add(dto);
        when(PaRegistry.getStratumGroupService()).thenReturn(stratumGroupServiceLocal);
        when(PaRegistry.getStratumGroupService().
                getByStudyProtocol(IiConverter.convertToIi(1L))).thenReturn(isoList);
        String result = subGroupsAction.query();
        assertEquals("success", result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#create()}.
     */
    @Test
    public void testCreate() {
        subGroupsAction.create();
        assertFalse(subGroupsAction.hasActionErrors());
        
        dto = new SubGroupsWebDTO();
        subGroupsAction.setSubGroupsWebDTO(dto);
        subGroupsAction.create();
        assertTrue(subGroupsAction.hasFieldErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#edit()}.
     */
    @Test
    public void testEdit() {
       dto.setId("1");
       String result = subGroupsAction.edit(); 
       assertEquals("input",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#update()}.
     */
    @Test
    public void testUpdate() {
        dto.setId("1");
        String result = subGroupsAction.update(); 
        assertFalse(subGroupsAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#delete()}.
     */
    @Test
    public void testDelete() {
        subGroupsAction.setObjectsToDelete(new String[] {"1"});
        String result = subGroupsAction.delete(); 
        assertEquals("success",result);
    }

}
