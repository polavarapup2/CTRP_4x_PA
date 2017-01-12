/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialArmsActionTest extends AbstractPaActionTest {

	TrialArmsAction trialsArmsAction;
	private Ii id = IiConverter.convertToIi(1L);
	private ArmServiceLocal armService;
	private PlannedActivityServiceLocal plaService;
	private InterventionServiceLocal intService;
	@Before 
	public void setUp() throws PAException {
		trialsArmsAction =  new TrialArmsAction();	
		StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
		dto.setStudyProtocolId(1L);
		 getSession().setAttribute(Constants.TRIAL_SUMMARY, dto);
		 trialsArmsAction.prepare();
	} 

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#execute()}.
	 */
	@Test
	public void testExecute() throws PAException {
		assertEquals("list", trialsArmsAction.execute());
	}

	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#create()}.
	 */
	@Test
	public void testCreate() throws PAException {
		assertEquals("edit", trialsArmsAction.create());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#createGroup()}.
	 */
	@Test
	public void testCreateGroup() throws PAException {
		assertEquals("edit", trialsArmsAction.createGroup());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#edit()}.
	 */
	@Test
	public void testEdit() throws PAException {
		trialsArmsAction.setSelectedArmIdentifier(null);
		assertEquals("edit", trialsArmsAction.edit());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#editGroup()}.
	 */
    @Test
    public void testEditGroup() throws PAException {
        trialsArmsAction.setSelectedArmIdentifier(null);
        assertEquals("edit", trialsArmsAction.editGroup());
        
        createMocks();
        trialsArmsAction.prepare();
        ArmDTO cArm = new ArmDTO();
        cArm.setDescriptionText(StConverter.convertToSt("Description"));
        cArm.setName(StConverter.convertToSt("name"));
        cArm.setTypeCode(CdConverter.convertToCd(ArmTypeCode.OTHER));
        List<PlannedActivityDTO> paList = new ArrayList<PlannedActivityDTO>();
        PlannedActivityDTO dto = new PlannedActivityDTO();
        dto.setIdentifier(id);
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        dto.setInterventionIdentifier(id);
        dto.setTextDescription(StConverter.convertToSt("text description"));
        paList.add(dto);
        InterventionDTO intDto = new InterventionDTO();
        intDto.setIdentifier(id);
        intDto.setName(StConverter.convertToSt("name"));
        when(armService.get(IiConverter.convertToIi("1L"))).thenReturn(cArm);
        when(plaService.getByArm(id)).thenReturn(paList);
        when(plaService.getByStudyProtocol(id)).thenReturn(paList);
        when(intService.get(id)).thenReturn(intDto);
        trialsArmsAction.setSelectedArmIdentifier("1L");
        assertEquals("edit", trialsArmsAction.editGroup());
        assertEquals("editGroup", trialsArmsAction.getCurrentAction());
    }

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#delete()}.
	 */
	@Test
	public void testDelete() throws PAException {
	    trialsArmsAction.setObjectsToDelete(new String[] {"1"});
		trialsArmsAction.delete();
		 assertEquals("Record(s) Deleted", getRequest().getAttribute("successMessage"));
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#add()}.
	 */
	@Test
	public void testAdd() throws PAException {
		trialsArmsAction.setArmType("Experimental");
		trialsArmsAction.setArmDescription("test");
		trialsArmsAction.setCurrentAction("listArm");
		trialsArmsAction.add();
		assertEquals("Record Created", getRequest().getAttribute("successMessage"));
	}
	
    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#add()}.
     */
    @Test
    public void testAddWithoutDescription() throws PAException {
        trialsArmsAction.setArmType("Experimental");
        trialsArmsAction.setArmDescription(null);
        trialsArmsAction.setCurrentAction("listArm");
        trialsArmsAction.add();
        assertFalse("Record Created".equals(getRequest().getAttribute("successMessage")));

        trialsArmsAction.setArmDescription("");
        trialsArmsAction.add();
        assertFalse("Record Created".equals(getRequest().getAttribute("successMessage")));

        trialsArmsAction.setArmDescription("       ");
        trialsArmsAction.add();
        assertFalse("Record Created".equals(getRequest().getAttribute("successMessage")));
    }
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.TrialArmsAction#update()}.
	 */
	@Test
    public void testUpdate() throws PAException {
        trialsArmsAction.setArmDescription("test");
        trialsArmsAction.setCurrentAction("listArm");
        trialsArmsAction.setCheckBoxEntry("12,2");
        assertEquals("edit", trialsArmsAction.update());
        assertTrue(trialsArmsAction.getIntList().size() > 0);
        
        trialsArmsAction.setArmType("ArmType");
        trialsArmsAction.clearFieldErrors();
        createMocks();
        trialsArmsAction.prepare();
        List<ArmDTO> armIsoList = new ArrayList<ArmDTO>();
        ArmDTO dto = new ArmDTO();
        dto.setIdentifier(id);
        armIsoList.add(dto);
        List<PlannedActivityDTO> paList = new ArrayList<PlannedActivityDTO>();
        PlannedActivityDTO pDto = new PlannedActivityDTO();
        pDto.setIdentifier(id);
        pDto.setInterventionIdentifier(id);
        paList.add(pDto);
        InterventionDTO inter = new InterventionDTO();
        inter.setIdentifier(id);
        inter.setName(StConverter.convertToSt("name"));
        when(armService.getByStudyProtocol(id)).thenReturn(armIsoList);
        when(plaService.getByArm(id)).thenReturn(paList);
        when(intService.get(id)).thenReturn(inter);
        assertEquals("list", trialsArmsAction.update());
	}
	
    private void createMocks() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        armService = mock(ArmServiceLocal.class);
        when(PaRegistry.getArmService()).thenReturn(armService);
        plaService = mock(PlannedActivityServiceLocal.class);
        when(PaRegistry.getPlannedActivityService()).thenReturn(plaService);
        intService = mock(InterventionServiceLocal.class);
        when(PaRegistry.getInterventionService()).thenReturn(intService);
    }

}
