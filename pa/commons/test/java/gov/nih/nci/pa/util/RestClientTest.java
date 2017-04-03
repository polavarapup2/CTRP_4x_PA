package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.service.PAException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.junit.Before;
import org.junit.Test;

public class RestClientTest {
    private RestClient client = new RestClient();
    private String url;
    
    @Before
    public void setup() throws PAException {
        url = PaEarPropertyReader.getFdaaaDataClinicalTrialsUrl();
    }
    
    @Test
    public void testSendHTTPRequest() throws PAException, IOException {
        RestClient spyRestClient = spy(this.client);
        HttpURLConnection mockUrlConnection = mock(HttpURLConnection.class);
        doReturn(mockUrlConnection).when(spyRestClient).makeUrlConnection(any(URL.class), anyString(), anyString());
        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
        when(mockUrlConnection.getResponseCode()).thenReturn(200);
        when(mockUrlConnection.getInputStream()).thenReturn(new ByteArrayInputStream("TEST".getBytes()));
        String response = spyRestClient.sendHTTPRequest(url, "GET", null);
        assertEquals("TEST", response);
    }
    
    @Test
    public void testSendHTTPRequestConnectException() throws PAException, IOException {
        RestClient spyRestClient = spy(this.client);
        doThrow(new SocketException("TEST")).when(spyRestClient).makeUrlConnection(any(URL.class), anyString(), anyString());
        try {
            spyRestClient.sendHTTPRequest(url, "GET", null);
            fail("Expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof PAException);
        }
        verify(spyRestClient, times(3)).makeUrlConnection(new URL(url), "GET", null);
    }
    
    @Test
    public void testSendHTTPRequestServerError() throws PAException, IOException {
        RestClient spyRestClient = spy(this.client);
        HttpURLConnection mockUrlConnection = mock(HttpURLConnection.class);
        doReturn(mockUrlConnection).when(spyRestClient).makeUrlConnection(any(URL.class), anyString(), anyString());
        when(mockUrlConnection.getResponseCode()).thenReturn(500);
        try {
            spyRestClient.sendHTTPRequest(url, "GET", null);
            fail("Expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof PAException);
        }
        verify(spyRestClient, times(3)).makeUrlConnection(new URL(url), "GET", null);
    }


    @Test
    public void testSendHTTPRequestNotFound() throws PAException, IOException {
        RestClient spyRestClient = spy(this.client);
        HttpURLConnection mockUrlConnection = mock(HttpURLConnection.class);
        doReturn(mockUrlConnection).when(spyRestClient).makeUrlConnection(any(URL.class), anyString(), anyString());
        when(mockUrlConnection.getResponseCode()).thenReturn(404);
        String response = spyRestClient.sendHTTPRequest(url, "GET", null);
        assertNull(response);
        verify(spyRestClient, times(1)).makeUrlConnection(new URL(url), "GET", null);
    }

    @Test
    public void testSendHTTPRequestReadException() throws PAException, IOException {
        RestClient spyRestClient = spy(this.client);
        HttpURLConnection mockUrlConnection = mock(HttpURLConnection.class);
        doReturn(mockUrlConnection).when(spyRestClient).makeUrlConnection(any(URL.class), anyString(), anyString());
        when(mockUrlConnection.getResponseCode()).thenReturn(200);
        when(mockUrlConnection.getOutputStream()).thenThrow(new IOException("TEST"));
        try {
            spyRestClient.sendHTTPRequest(url, "GET", null);
            fail("Expected exception");
        } catch (Exception e) {
            assertTrue(e instanceof PAException);
        }
        verify(spyRestClient, times(1)).makeUrlConnection(new URL(url), "GET", null);
        verify(mockUrlConnection).getInputStream();
    }    
    
    @Test
    public void testMakeUrlConnectionPost() throws Exception {
        final HttpURLConnection mockUrlConnection = mock(HttpURLConnection. class);
        when(mockUrlConnection.getInputStream()).thenReturn(new ByteArrayInputStream("TEST".getBytes()));
        // can't mock URL because it's final so mock URLStreamHandler
        URLStreamHandler stuburlStreamHandler = new URLStreamHandler() {
            @Override
             protected URLConnection openConnection(URL u ) throws IOException {
                return mockUrlConnection ;
             }
        };
        
        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
        
        HttpURLConnection connection = client.makeUrlConnection(new URL(null, url, stuburlStreamHandler), "POST", "TEST");
        assertNotNull(connection);
        
        verify(mockUrlConnection).setConnectTimeout(10000);
        verify(mockUrlConnection).setReadTimeout(10000);
        verify(mockUrlConnection).setRequestMethod("POST");
        verify(mockUrlConnection).setRequestProperty("Accept", "application/json");
        verify(mockUrlConnection).setRequestProperty("Content-Type", "application/json");
        verify(mockUrlConnection).setDoOutput(true);
        verify(mockUrlConnection).getOutputStream();
        verify(mockOutputStream).write("TEST".getBytes());
        verify(mockOutputStream).flush();
    }
    
    @Test
    public void testMakeUrlConnectionGet() throws Exception {
        final HttpURLConnection mockUrlConnection = mock(HttpURLConnection. class);
        when(mockUrlConnection.getInputStream()).thenReturn(new ByteArrayInputStream("TEST".getBytes()));
        // can't mock URL because it's final so mock URLStreamHandler
        URLStreamHandler stuburlStreamHandler = new URLStreamHandler() {
            @Override
             protected URLConnection openConnection(URL u ) throws IOException {
                return mockUrlConnection ;
             }
        };
        
        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
        
        HttpURLConnection connection = client.makeUrlConnection(new URL(null, url, stuburlStreamHandler), "GET", null);
        assertNotNull(connection);
        
        verify(mockUrlConnection, never()).getOutputStream();
        verify(mockOutputStream, never()).write(any(byte[].class));
    }

}
