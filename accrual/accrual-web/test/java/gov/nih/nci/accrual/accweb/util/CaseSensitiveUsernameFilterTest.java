package gov.nih.nci.accrual.accweb.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;

import org.junit.Test;

import com.mockrunner.mock.web.MockFilterChain;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

public class CaseSensitiveUsernameFilterTest {
	
	@Test
	public void junitCoverage() throws Exception{
		CaseSensitiveUsernameFilter filter = new CaseSensitiveUsernameFilter();		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteUser("JUnitUser");
		filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
		assertEquals("JUnitUser", CaseSensitiveUsernameHolder.getUser());
	}

}
