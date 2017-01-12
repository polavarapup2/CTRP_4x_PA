package gov.nih.nci.registry.dto;

import static org.junit.Assert.*;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrialDTOTest {
    
    private final List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();

    @Before
    public void init() {
        RegulatoryAuthOrgDTO rao = new RegulatoryAuthOrgDTO();
        rao.setId(1L);
        regIdAuthOrgList.add(rao);
    }
    
    @Test
    public void testRegAuthority() {
        TrialDTO dto = new TrialDTO();
        dto.setRegIdAuthOrgList(regIdAuthOrgList );
        assertNull(dto.getRegAuthorityOrg());
        dto.setSelectedRegAuth("1");
        assertTrue(regIdAuthOrgList.get(0) == dto.getRegAuthorityOrg());
        
        assertNull(dto.getRegAuthorityCountry());
    }

}
