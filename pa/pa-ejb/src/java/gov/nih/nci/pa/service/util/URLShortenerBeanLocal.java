package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
public class URLShortenerBeanLocal implements URLShortenerServiceLocal {

    private static final Logger LOG = Logger
            .getLogger(URLShortenerBeanLocal.class);

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    @SuppressWarnings("deprecation")
    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public URL shorten(URL url) {
        try {
            Account account = getGoUsaGovAccount();
            String apiURL = lookUpTableService
                    .getPropertyValue("go.usa.gov.api.shorten.url")
                    .replace("{user_name}",
                            URLEncoder.encode(account.getUsername(), "UTF-8"))
                    .replace("{api_key}", account.getDecryptedPassword())
                    .replace("{longUrl}",
                            URLEncoder.encode(url.toString(), "UTF-8"));

            final int timeout = Integer.parseInt(lookUpTableService
                    .getPropertyValue("go.usa.gov.api.timeout"));
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
            HttpConnectionParams.setSoTimeout(httpParams, timeout);
            final HttpClient httpClient = new DefaultHttpClient(httpParams);

            HttpGet req = new HttpGet(apiURL);
            req.addHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(req);
            HttpEntity resEntity = response.getEntity();

            final Gson gson = new Gson();
            final Map map = (Map) gson.fromJson(
                    EntityUtils.toString(resEntity, "UTF-8"), Object.class);

            return new URL(
                    (String) PropertyUtils.getProperty(
                            map,
                            lookUpTableService
                                    .getPropertyValue("go.usa.gov.api.shorten.json.path")));
        } catch (Exception e) {
            LOG.error(e, e);
            return null;
        }
    }

    private Account getGoUsaGovAccount() throws PAException {
        return (Account) PaHibernateUtil.getCurrentSession()
                .createQuery("from Account where externalSystem=:system")
                .setMaxResults(1)
                .setParameter("system", ExternalSystemCode.GO_USA_GOV)
                .uniqueResult();
    }

}
