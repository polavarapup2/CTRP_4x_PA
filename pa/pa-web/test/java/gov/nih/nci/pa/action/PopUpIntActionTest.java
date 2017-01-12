/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpIntActionTest extends AbstractPaActionTest {

	PopUpIntAction popUpIntAction;
	
	@Before 
	public void setUp() throws PAException {
	  popUpIntAction =  new PopUpIntAction();	
	  getRequest().setupAddParameter("searchName", "");
	  getRequest().setupAddParameter("includeSynonym", "yes");
	  getRequest().setupAddParameter("exactMatch", "yes");
	} 
	
	@Test
	public void testDisplayList() {
		popUpIntAction.displayList();
		assertNotNull(getRequest().getAttribute("failureMessage"));
		assertEquals("Please enter at least one search criteria.", getRequest().getAttribute("failureMessage"));
		popUpIntAction.setSearchName("searchName");
		popUpIntAction.getSearchName();
		popUpIntAction.setIncludeSynonym("yes");
		popUpIntAction.getIncludeSynonym();
		popUpIntAction.setExactMatch("yes");
		popUpIntAction.getExactMatch();
		popUpIntAction.setInterWebList(new ArrayList<InterventionWebDTO>());
		popUpIntAction.getInterWebList();
	
	}
	@Test
	public void testDisplayListWithData() {
		getRequest().setupAddParameter("searchName", "Test");
		assertEquals("success", popUpIntAction.displayList());
			
	}
}
