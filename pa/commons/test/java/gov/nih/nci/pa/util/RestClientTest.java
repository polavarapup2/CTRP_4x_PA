package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

public class RestClientTest {
    private RestClient client = new RestClient();
    private String url;
    @Before
    public void setup() throws PAException {
        url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
    }
    @Test (expected=PAException.class)
    public void sendHTTPRequestTest() throws PAException {
        client.sendHTTPRequest(url, "GET", null);
        
    }
}
