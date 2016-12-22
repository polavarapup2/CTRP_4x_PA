/**
 * 
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.TreeMap;

import gov.nih.nci.pa.dto.PaOrganizationDTO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 *
 */
public class ComparableOrganizationDTOTest {

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
     * Test method for {@link gov.nih.nci.registry.util.ComparableOrganizationDTO#hashCode()}.
     */
    @Test
    public final void testHashCode() {     
        ComparableOrganizationDTO dto1 = new ComparableOrganizationDTO();
        dto1.setName("dto1");
        ComparableOrganizationDTO dto2 = new ComparableOrganizationDTO();
        dto2.setName("dto2");
        
        for (int i=-1000000;i<1000000;i++) {
            dto1.setId(i+"");
            
            dto2.setId(i+"");
            assertEquals(dto1.hashCode(), dto2.hashCode());
        }
        
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.ComparableOrganizationDTO#ComparableOrganizationDTO(gov.nih.nci.pa.dto.PaOrganizationDTO)}.
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    @Test
    public final void testComparableOrganizationDTO() throws IllegalAccessException, InvocationTargetException {
        PaOrganizationDTO paDTO = new PaOrganizationDTO();
        paDTO.setCity("city");
        paDTO.setCountry("country");
        paDTO.setCtepId("ctepId");
        paDTO.setFamilies(new HashMap<Long, String>());
        paDTO.setFamilyName("fname");
        paDTO.setFunctionalRole("frole");
        paDTO.setId("1");
        paDTO.setIdentifier("id");
        paDTO.setInvestigator("investigator");
        paDTO.setName("name");
        paDTO.setNciIdentifier("nciID");
        paDTO.setNciNumber("nci#");
        paDTO.setPrimarycontact("pcontact");
        paDTO.setProgramCode("pcode");
        paDTO.setRecruitmentStatus("rstat");
        paDTO.setRecruitmentStatusDate("01/01/01");
        paDTO.setState("state");
        paDTO.setStatus("status");
        paDTO.setTargetAccrualNumber("1111");
        paDTO.setZip("22203");
        
        ComparableOrganizationDTO compDTO = new ComparableOrganizationDTO(paDTO);
        assertEquals("city",compDTO.getCity());
        assertEquals("country",compDTO.getCountry());
        assertEquals("ctepId",compDTO.getCtepId());
        assertEquals(new HashMap<Long, String>(),compDTO.getFamilies());
        assertEquals("fname",compDTO.getFamilyName());
        assertEquals("frole",compDTO.getFunctionalRole());
        assertEquals("1",compDTO.getId());
        assertEquals("id",compDTO.getIdentifier());
        assertEquals("investigator",compDTO.getInvestigator());
        assertEquals("name",compDTO.getName());
        assertEquals("nciID",compDTO.getNciIdentifier());
        assertEquals("nci#",compDTO.getNciNumber());
        assertEquals("pcontact",compDTO.getPrimarycontact());
        assertEquals("pcode",compDTO.getProgramCode());
        assertEquals("rstat",compDTO.getRecruitmentStatus());
        assertEquals("01/01/01",compDTO.getRecruitmentStatusDate());
        assertEquals("state",compDTO.getState());
        assertEquals("status",compDTO.getStatus());
        assertEquals("1111",compDTO.getTargetAccrualNumber());
        assertEquals("22203",compDTO.getZip());        
        
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.ComparableOrganizationDTO#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        ComparableOrganizationDTO dto1 = new ComparableOrganizationDTO();
        dto1.setCity("city");
        dto1.setCountry("country");
        dto1.setCtepId("ctepId");
        dto1.setFamilies(new HashMap<Long, String>());
        dto1.setFamilyName("fname");
        dto1.setFunctionalRole("frole");        
        dto1.setIdentifier("id");
        dto1.setInvestigator("investigator");
        dto1.setName("name");
        dto1.setNciIdentifier("nciID");
        dto1.setNciNumber("nci#");
        dto1.setPrimarycontact("pcontact");
        dto1.setProgramCode("pcode");
        dto1.setRecruitmentStatus("rstat");
        dto1.setRecruitmentStatusDate("01/01/01");
        dto1.setState("state");
        dto1.setStatus("status");
        dto1.setTargetAccrualNumber("1111");
        dto1.setZip("22203");
        
        ComparableOrganizationDTO dto2 = new ComparableOrganizationDTO();
        dto2.setCity("_city");
        dto2.setCountry("_country");
        dto2.setCtepId("_ctepId");
        dto2.setFamilies(new TreeMap<Long, String>());
        dto2.setFamilyName("_fname");
        dto2.setFunctionalRole("_frole");        
        dto2.setIdentifier("_id");
        dto2.setInvestigator("_investigator");
        dto2.setName("_name");
        dto2.setNciIdentifier("_nciID");
        dto2.setNciNumber("_nci#");
        dto2.setPrimarycontact("_pcontact");
        dto2.setProgramCode("_pcode");
        dto2.setRecruitmentStatus("_rstat");
        dto2.setRecruitmentStatusDate("_01/01/01");
        dto2.setState("_state");
        dto2.setStatus("_status");
        dto2.setTargetAccrualNumber("_1111");
        dto2.setZip("_22203");    
        
        // null IDs are not equal.
        assertFalse(dto1.equals(dto2));
        
        dto1.setId("1");
        dto2.setId("2");
        assertFalse(dto1.equals(dto2));
        
        dto1.setId("1");
        dto2.setId("1");
        assertTrue(dto1.equals(dto2));
        
        
    }

    /**
     * Test method for {@link gov.nih.nci.registry.util.ComparableOrganizationDTO#compareTo(gov.nih.nci.registry.util.ComparableOrganizationDTO)}.
     */
    @Test
    public final void testCompareTo() {
        ComparableOrganizationDTO dto1 = new ComparableOrganizationDTO();
        ComparableOrganizationDTO dto2 = new ComparableOrganizationDTO();
        
        dto1.setName("1");
        dto2.setName("2");
        assertEquals(-1, dto1.compareTo(dto2));
        assertEquals(+1, dto2.compareTo(dto1));
        
        dto1.setName("1");
        dto2.setName("1");
        assertEquals(0, dto1.compareTo(dto2));
        
    }

}
