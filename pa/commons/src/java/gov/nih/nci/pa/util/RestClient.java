package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * @author Purnima, Reshma
 *
 */
public class RestClient {

    private static final Logger LOG = Logger.getLogger(RestClient.class);

    private static final Integer HTTP_SUCCESS_CODE_200 = 200;
    private static final Integer HTTP_SUCCESS_CODE_201 = 201;
    private static final int RETRY_COUNT = 3;
    private static final int HTTP_TIME_OUT = 10000;
    private static final Integer HTTP_NOT_FOUND_404 = 404;
    private static final Integer SLEEP_TIME = 10;


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
     * @throws PAException
     *             PAException
     * 
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    public String sendHTTPRequest(String restUrl, String method, String postBody) throws PAException {
        int httpResponseCode = -1;
        String httpResponseMessage = "";
        LOG.debug("url: " + restUrl);
        HttpURLConnection urlConnection = null;

        boolean success = false;
        for (int count = 0; count < RETRY_COUNT; count++) {
            try {
                urlConnection = makeUrlConnection(new URL(restUrl), method, postBody);

                httpResponseCode = urlConnection.getResponseCode();
                if (httpResponseCode == HTTP_SUCCESS_CODE_200 || httpResponseCode == HTTP_SUCCESS_CODE_201) {
                    success = true;
                    break;
                } else if (httpResponseCode == HTTP_NOT_FOUND_404) {
                    return null;
                }
                Thread.sleep(0, SLEEP_TIME);
            } catch (Exception e) {
                LOG.error("Error: Unable to get response from Rest server (" + httpResponseCode + ") - "
                        + httpResponseMessage, e);
            }
        }
        if (success) {
            return readResponse(urlConnection);
        }  else {
            throw new PAException("Error: " + readError(urlConnection));
        }

    }
    private String readError(HttpURLConnection urlConnection) throws PAException {
        BufferedReader reader;
        StringBuilder httpResponse = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
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
            throw new PAException("Error in reading response:  " + e.getMessage(), e);
        }
        return httpResponse.toString();
        
    }
    private String readResponse(HttpURLConnection urlConnection) throws PAException {
        BufferedReader reader;
        StringBuilder httpResponse = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
            throw new PAException("Error in reading response:  " + e.getMessage(), e);
        }
        return httpResponse.toString();
    }

    /**
     * 
     * @param restUrl the URL to connect to
     * @param method the HTTP method GET/POST/PUT/DELETE
     * @param postBody the jsonStr of the post request body
     * @return http connection to the URL 
     * @throws IOException
     */
    HttpURLConnection makeUrlConnection(URL url, String method, String postBody)
            throws IOException {
        SSLContext context = getSslContext();

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if (context != null && urlConnection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) urlConnection).setSSLSocketFactory(context.getSocketFactory());
        }
        urlConnection.setConnectTimeout(HTTP_TIME_OUT);
        urlConnection.setReadTimeout(HTTP_TIME_OUT);
        urlConnection.setRequestMethod(method);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setDoOutput(true);

        if ((StringUtils.equals(method, "POST") || StringUtils.equals(method, "PUT") 
                || StringUtils.equals(method, "DELETE"))
                && postBody != null && postBody.length() > 0) {
            LOG.debug("postBody: " + postBody);
            setPostBody(urlConnection, postBody);
        } 
        return urlConnection;
    }


    /**
     * Initialize SSLContext instance and returns
     *
     * @return SSLContext and NUll upon error
     */
    SSLContext getSslContext() {
        try {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, null, new SecureRandom());
            return context;
        } catch (Exception e) {
            LOG.info("Error creating SSLContext", e);
            return null;
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
    private void setPostBody(HttpURLConnection urlConnection, String postBody) throws IOException {
        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(postBody.getBytes());
        outputStream.flush();
    }

}
