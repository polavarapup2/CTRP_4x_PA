package gov.nih.nci.pa.util;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTOs;
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
    private ImportRestClient client = mock(ImportRestClient.class);
    private String url;
    @Before
    public void setUp() throws PAException {
        url = PaEarPropertyReader.getCtrpImportCtApiUrl();
        
    }
    @Test
    public void updateNctIdTest() throws PAException {
        String response = "<List><item><paTrialID>176578</paTrialID>"
                + "<nciTrialID>NCI-2017-00420</nciTrialID></item></List>";
        when(client.sendHTTPRequest(url + "/NCT12345678", "PUT", null)).thenReturn(response);
        helper.setClient(client);
        TrialRegistrationConfirmationDTOs dtos = helper.updateNctId("NCT12345678");
        assertTrue(dtos != null);
        assertTrue(dtos.getDtos().size() == 1);
        assertTrue(dtos.getDtos().get(0).getNciTrialID().equals("NCI-2017-00420"));
    }
    
}
