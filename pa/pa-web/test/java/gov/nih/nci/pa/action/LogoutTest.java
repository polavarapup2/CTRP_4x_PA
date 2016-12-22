/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class LogoutTest extends AbstractPaActionTest {

	Logout logout;

	@Before
	public void setUp() throws PAException {
	  logout =  new Logout();
	  getSession().setAttribute(Constants.IS_ABSTRACTOR, Boolean.TRUE);

	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.Logout#logout()}.
	 */
	@Test
	public void testLogout() {
		assertEquals("success", logout.logout());
	}

}
