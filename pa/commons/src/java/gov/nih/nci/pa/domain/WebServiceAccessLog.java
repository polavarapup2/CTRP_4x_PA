package gov.nih.nci.pa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * 
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "webservice_access_log")
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class WebServiceAccessLog implements PersistentObject {

    private static final long serialVersionUID = 2827128893597594641L;

    private Long id;

    private Date datetime;
    private String clientIp;
    private String clientUsername;
    private String uri;
    private String method;
    private String headers;
    private String payload;
    private String response;
    private Integer responseCode;
    private Long processingTime;
    private String processingErrors;

    /**
     * 
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the datetime
     */
    @Column(name = "datetime")
    public Date getDatetime() {
        return datetime;
    }

    /**
     * @param datetime
     *            the datetime to set
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the clientIp
     */
    @Column(name = "client_ip")
    public String getClientIp() {
        return clientIp;
    }

    /**
     * @param clientIp
     *            the clientIp to set
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return the clientUsername
     */
    @Column(name = "client_username")
    public String getClientUsername() {
        return clientUsername;
    }

    /**
     * @param clientUsername
     *            the clientUsername to set
     */
    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    /**
     * @return the uri
     */
    @Column(name = "uri")
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the method
     */
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the headers
     */
    @Column(name = "headers")
    public String getHeaders() {
        return headers;
    }

    /**
     * @param headers
     *            the headers to set
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * @return the payload
     */
    @Column(name = "payload")
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload
     *            the payload to set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * @return the response
     */
    @Column(name = "response")
    public String getResponse() {
        return response;
    }

    /**
     * @param response
     *            the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the responseCode
     */
    @Column(name = "response_code")
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode
     *            the responseCode to set
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the processingTime
     */
    @Column(name = "processing_time")
    public Long getProcessingTime() {
        return processingTime;
    }

    /**
     * @param processingTime
     *            the processingTime to set
     */
    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    /**
     * @return the processingErrors
     */
    @Column(name = "processing_errors")
    public String getProcessingErrors() {
        return processingErrors;
    }

    /**
     * @param processingErrors
     *            the processingErrors to set
     */
    public void setProcessingErrors(String processingErrors) {
        this.processingErrors = processingErrors;
    }

}
