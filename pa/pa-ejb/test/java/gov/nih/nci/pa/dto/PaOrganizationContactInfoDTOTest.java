/**
 * 
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class PaOrganizationContactInfoDTOTest {

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

    @Test
    public final void test() {
        PaOrganizationContactInfoDTO dto = new PaOrganizationContactInfoDTO();
        dto.setEmails(Arrays.asList("email"));
        dto.setPhones(Arrays.asList("phones"));
        dto.setFaxes(Arrays.asList("faxes"));
        dto.setWebsites(Arrays.asList("site"));
        
        assertEquals("email", dto.getEmails().get(0));
        assertEquals("phones", dto.getPhones().get(0));
        assertEquals("faxes", dto.getFaxes().get(0));
        assertEquals("site", dto.getWebsites().get(0));
        
        
    }

}
