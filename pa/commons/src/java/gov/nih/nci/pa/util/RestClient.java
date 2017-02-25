package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;


/**
 * @author vpoluri
 *
 */
public class RestClient {

    private static final Logger LOG = Logger.getLogger(RestClient.class);

    private static final Integer HTTP_SUCCESS_CODE_200 = 200;
    private static final Integer HTTP_SUCCESS_CODE_201 = 201;
    private static final int RETRY_COUNT = 3;
    private static final int HTTP_TIME_OUT = 10000;

    /**
     * 
     *
     * @param allowTrustedSites
     *            - enables to allow all tursted sites
     */

     public RestClient(boolean allowTrustedSites) {
        if (allowTrustedSites) {
            trustAllCerts();
        }
    }


     /**
      * trusts all certificates
      */
     private void trustAllCerts() {

         // Create a trust manager that does not validate certificate chains
         TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
             public java.security.cert.X509Certificate[] getAcceptedIssuers() { // NOPMD
                 return null;
             }

             public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                 //TODO
             }

             public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                 //TODO
             }
         } };
         // Install the all-trusting trust manager
         try {
             SSLContext sc = SSLContext.getInstance("SSL");
             sc.init(null, trustAllCerts, new java.security.SecureRandom());
             HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         } catch (Exception e) {
             LOG.error(e.getMessage(), e);
         }
     }

    /**
     * Invokes REST api to get the
     * 
     * @param restUrl
     *            - rest URL
     * @param method
     *            - HTTP Method
     * @param postBody
     *            - Post body
     * @return - Response after update
     * @throws PAException PAException
     * 
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    public String sendHTTPRequest(String restUrl, String method, String postBody)
            throws PAException {
        int httpResponseCode = -1;
        String httpResponseMessage = "";
        LOG.debug("url: " + restUrl);
        StringBuilder httpResponse = new StringBuilder();
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;

        boolean success = false;
        for (int count = 0; count <= RETRY_COUNT; count++) {
            try {
                URL url = new URL(restUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(HTTP_TIME_OUT);
                urlConnection.setReadTimeout(HTTP_TIME_OUT);
                urlConnection.setRequestMethod(method);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type",
                        "text/xml;charset=UTF-8");

                if (postBody != null && postBody.length() > 0) {
                    LOG.debug("postBody: " + postBody);
                    setPostBody(urlConnection, postBody);
                }

                httpResponseCode = urlConnection.getResponseCode();
                httpResponseMessage = urlConnection.getResponseMessage();
                if (httpResponseCode == HTTP_SUCCESS_CODE_200
                        && httpResponseCode == HTTP_SUCCESS_CODE_201) {
                    success = true;
                    break;
                }
            } catch (Exception e) {
                LOG.error("Error: Unable to get response from Rest server ("
                        + httpResponseCode + ") - " + httpResponseMessage, e);
            }
        }
        if (success) {
            try {
                reader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    httpResponse.append(line);
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                    }
                }

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                throw new PAException(
                        "Error in reading micro service response:  "
                                + e.getMessage(), e);
            }
            return httpResponse.toString();
        } else {
            throw new PAException(
                    "Error: Unable to get response from Rest server @"
                            + restUrl);
        }

    }

    /**
     * Sets post body to the http request
     * 
     * @param urlConnection
     *            - URL Connection
     * @param postBody
     *            - Post body
     * @throws IOException
     *             - throws IOException
     */
    private void setPostBody(HttpURLConnection urlConnection, String postBody)
            throws IOException {

        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(postBody.getBytes());
        outputStream.flush();
    }


}

