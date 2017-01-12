/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.RegistryUser;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Denis G. Krylov
 *
 */
public class RegistryUserConverterTest {

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

	/**
	 * Test method for {@link gov.nih.nci.pa.iso.convert.RegistryUserConverter#convertToDSet(java.util.Collection)}.
	 */
	@Test
	public final void testConvertToDSet() {
		Collection<RegistryUser> users = new ArrayList<RegistryUser>();
		RegistryUser user = new RegistryUser();
		user.setEmailAddress("denis.krylov@semanticbits.com");
		users.add(user);
		
		user = new RegistryUser();
		user.setId(Long.MIN_VALUE);
		user.setEmailAddress("");
		users.add(user);
		
		DSet<Tel> dset = RegistryUserConverter.convertToDSet(users);
		assertNotNull(dset);
		assertTrue(CollectionUtils.isNotEmpty(dset.getItem()));
		assertEquals(1, dset.getItem().size());
		
		Tel tel = dset.getItem().iterator().next();
		assertEquals("mailto:denis.krylov@semanticbits.com", tel.getValue().toString());
		
	}

}
