/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.dto.LookUpWebDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpTypeInterventionActionTest extends AbstractPaActionTest {

	PopUpTypeInterventionAction popUpInterventionAction;
	
	@Before 
	public void setUp() throws PAException {
    	popUpInterventionAction =  new PopUpTypeInterventionAction();	  
	} 
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#type()}.
	 */
	@Test
	public void testType() {
		getRequest().setupAddParameter("className", "uom");
		getRequest().setupAddParameter("divName", "uomDetails");
		assertEquals("lookUp", popUpInterventionAction.type());
		assertEquals("uom",popUpInterventionAction.getLookupSearchCriteria().getType());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpList() {
		 getRequest().setupAddParameter("code", "");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "uomDetails");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
		 assertEquals("Please enter at least one search criteria", getRequest().getAttribute("failureMessage"));
	}
	
	@Test
	public void testDisplayLookUpListWithClassNameNull() {
		 getRequest().setupAddParameter("code", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
		 popUpInterventionAction.setLookupDtos(new ArrayList<LookUpWebDTO>());
		 assertNotNull(popUpInterventionAction.getLookupDtos());
		 popUpInterventionAction.setLookupSearchCriteria(new LookUpWebDTO());
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataUOM() {
		 getRequest().setupAddParameter("code", "ml");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "UnitOfMeasurement");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataDoseForm() {
		 getRequest().setupAddParameter("code", "pill");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "DoseForm");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataDoseFrequency() {
		 getRequest().setupAddParameter("code", "q2");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "DoseFrequency");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataRouteOfAdministration() {
		 getRequest().setupAddParameter("code", "oral");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "RouteOfAdministration");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataMethodCode() {
		 getRequest().setupAddParameter("code", "test");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "MethodCode");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpList()}.
	 */
	@Test
	public void testDisplayLookUpListWithDataTargetSite() {
		 getRequest().setupAddParameter("code", "Chest");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "TargetSite");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	
	@Test
	public void testDisplayLookUpListWithAll() {
		 getRequest().setupAddParameter("code", "pill");
		 getRequest().setupAddParameter("publicId", "123");
		 getRequest().setupAddParameter("description", "desc");
		 getRequest().setupAddParameter("displayName", "display");
		 getRequest().setupAddParameter("className", "DoseForm");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("success",popUpInterventionAction.displayLookUpList());
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.PopUpTypeInterventionAction#displayLookUpListDisplayTag()}.
	 */
	@Test
	public void testDisplayLookUpListDisplayTag() {
		 getRequest().setupAddParameter("code", "");
		 getRequest().setupAddParameter("publicId", "");
		 getRequest().setupAddParameter("description", "");
		 getRequest().setupAddParameter("displayName", "");
		 getRequest().setupAddParameter("className", "uomDetails");
		 getRequest().setupAddParameter("divName", "code");
		 assertEquals("lookUp",popUpInterventionAction.displayLookUpListDisplayTag());
		 assertEquals("Please enter at least one search criteria", getRequest().getAttribute("failureMessage"));
	}

}
