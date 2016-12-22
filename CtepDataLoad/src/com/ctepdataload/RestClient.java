package com.ctepdataload;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {
    
   private static final int TIMEOUT = 5*60*1000;
   private  CloseableHttpClient client;
   private String userName;
   private String password;
   private boolean addException;
   
   
   public void init() throws Exception {
       String userName = getUserName();
       String password = getPassword();
       
       //set username/password
       CredentialsProvider credsProvider = new BasicCredentialsProvider();
       credsProvider.setCredentials(AuthScope.ANY,
               new UsernamePasswordCredentials(userName, password));
       
     //set timeout 
       RequestConfig requestConfig = RequestConfig.custom()
                 .setConnectTimeout(TIMEOUT)
                 .setConnectionRequestTimeout(TIMEOUT)
                 .setSocketTimeout(TIMEOUT).build();
       
       //temporary to add as trusted site this is to test on local and qa
       //on PROD  addException will be false
       if (addException) {

           SSLContextBuilder builder = new SSLContextBuilder();
           builder.loadTrustMaterial(null, new TrustStrategy() {
               @Override
               public boolean isTrusted(X509Certificate[] chain,
                       String authType) throws CertificateException {
                   return true;
               }
           });
           SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
                   builder.build(),
                   SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

           client = HttpClientBuilder.create()
                   .setDefaultCredentialsProvider(credsProvider)
                   .setDefaultRequestConfig(requestConfig)
                   .setSSLSocketFactory(sslSF).build();

       } else {
           client = HttpClientBuilder.create()
                   .setDefaultCredentialsProvider(credsProvider)
                   .setDefaultRequestConfig(requestConfig)
                   .build();
       }
       
       
       
       
       
   }
   

   public CtrpResponse addSite(String xml,String url,String trialCtepId)  throws Exception{
        CtrpResponse ctrpResponse = new CtrpResponse();
        
        //create new url with ctep Id
        String newUrl = url+"/ctep/"+trialCtepId+"/sites";
        HttpPost post = new HttpPost(newUrl);
        StringEntity orgEntity = new StringEntity(xml, "UTF-8");
        post.addHeader("Accept", "text/plain");
        post.addHeader("Content-Type", "application/xml");
        post.setEntity(orgEntity);
        HttpResponse response = client.execute(post);
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            
         if (response.getStatusLine()
                    .getStatusCode()== 200){
                ctrpResponse.setSucess(true);
                ctrpResponse.setSiteIdAdded(result.toString());
         }
         else {
             ctrpResponse.setSucess(false);
             ctrpResponse.setFailureMessage(result.toString());
         }
        
        client.close();
       return ctrpResponse; 
    }

  public CtrpResponse updateSite(String xml,String url,String trialCtepId, String studySiteCtepId)  throws Exception {
      CtrpResponse ctrpResponse = new CtrpResponse();
 
      
      String newUrl = url+"/ctep/"+trialCtepId+"/sites/ctep/"+studySiteCtepId;
      HttpPut httpPut= new HttpPut(newUrl);
      StringEntity orgEntity = new StringEntity(xml, "UTF-8");
      httpPut.addHeader("Content-Type", "application/xml");
      httpPut.setEntity(orgEntity);
      HttpResponse response = client.execute(httpPut);
     
      BufferedReader rd = new BufferedReader(new InputStreamReader(response
              .getEntity().getContent()));

          StringBuffer result = new StringBuffer();
          String line = "";
          while ((line = rd.readLine()) != null) {
              result.append(line);
          }
          
       if (response.getStatusLine()
                  .getStatusCode()== 200){
              ctrpResponse.setSucess(true);
       }
       else {
           ctrpResponse.setSucess(false);
           ctrpResponse.setFailureMessage(result.toString());
       }
       client.close();
      return ctrpResponse;
   }
   
public String getUserName() {
    return userName;
}


public void setUserName(String userName) {
    this.userName = userName;
}


public String getPassword() {
    return password;
}


public void setPassword(String password) {
    this.password = password;
}


public boolean isAddException() {
    return addException;
}


public void setAddException(boolean addException) {
    this.addException = addException;
}
    

}
