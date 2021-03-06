package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma
 *
 */
public class CTGovImportMergeHelperTest {
    private CTGovImportMergeHelper helper = new CTGovImportMergeHelper();
    private RestClient client = mock(RestClient.class);
    private String url; 
    
    @Before
    public void setUp() throws PAException {
        url = PaEarPropertyReader.getCtrpImportCtApiUrl() + "/NCT12345678";
    }
    
    @Test
    public void updateNctIdTest() throws PAException, IOException {
        String response = "[{\"paTrialID\":\"176578\",\"nciTrialID\":\"NCI-2017-00420\"}]";
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenReturn(response);
        helper.setClient(client);
        List<TrialRegistrationConfirmationDTO> dtos = helper.updateNctId("NCT12345678", "testuser");
        verify(client).sendHTTPRequest(url,"PUT", "testuser");
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals("NCI-2017-00420", dtos.get(0).getNciTrialID());
    }

    @Test
    public void updateNctIdNullTest() throws PAException {
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenReturn(null);
        helper.setClient(client);
        List<TrialRegistrationConfirmationDTO> dtos = helper.updateNctId("NCT12345678", null);
        verify(client).sendHTTPRequest(url,"PUT", null);
        assertNull(dtos);
    }

    @Test
    public void updateNctIdExceptionTest() throws PAException {
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenThrow(new PAException("TEST"));
        helper.setClient(client);
        try {
            helper.updateNctId("NCT12345678", "testuser");
            fail("Expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof PAException);
            assertEquals("TEST", e.getMessage());
        }
        verify(client).sendHTTPRequest(url, "PUT", "testuser");
    }

    
    @Test
    public void insertNctIdTest() throws PAException {
        String response = "{\"paTrialID\":\"176578\",\"nciTrialID\":\"NCI-2017-00420\"}";
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenReturn(response);
        helper.setClient(client);
        TrialRegistrationConfirmationDTO dto = helper.insertNctId("NCT12345678", "testuser");
        verify(client).sendHTTPRequest(url, "POST", "testuser");
        assertNotNull(dto);
        assertEquals("176578", dto.getPaTrialID());
        assertEquals("NCI-2017-00420", dto.getNciTrialID());
    }
    
    @Test
    public void insertNctIdNullTest() throws PAException {
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenReturn(null);
        helper.setClient(client);
        // TODO: should this throw an exception?
        TrialRegistrationConfirmationDTO dto = helper.insertNctId("NCT12345678", "testuser");
        assertNotNull(dto);
        assertNull(dto.getNciTrialID());
        assertNull(dto.getPaTrialID());
        verify(client).sendHTTPRequest(url, "POST", "testuser");
    }

    @Test
    public void insertNctIdExceptionTest() throws PAException {
        when(client.sendHTTPRequest(anyString(), anyString(), anyString())).thenThrow(new PAException("TEST"));
        helper.setClient(client);
        try {
            helper.insertNctId("NCT12345678", "testuser");
            fail("Expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof PAException);
            assertEquals("TEST", e.getMessage());
        }
        verify(client).sendHTTPRequest(url, "POST", "testuser");
    }
}
