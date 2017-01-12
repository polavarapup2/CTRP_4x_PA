package gov.nih.nci.accrual.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CaseSensitiveUsernameHolderTest {
	
	@Test
	public void junitCoverage() {
		CaseSensitiveUsernameHolder  holder = new CaseSensitiveUsernameHolder();
		holder.setUser("__anonymous__");
		holder.setUser(null);
		holder.setUser("Testing");
		assertEquals("Testing", holder.getUser());
		holder.setUser("");
		assertEquals("__anonymous__", holder.getUser());
	}

}
