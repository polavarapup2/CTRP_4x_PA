package gov.nih.nci.pa.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MockNCITServer {
    
private HttpServer server;    
 
public void startServer(int port ) throws Exception {

    server = HttpServer.create(new InetSocketAddress(port),
            0);

    server.createContext("/lexEVSAPI", new HttpHandler() {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
               String uri = t.getRequestURI().toString();
                String ncId = null;
                String fileName=null;
                if (uri.indexOf("/children") <0) {
                    ncId= uri.substring(uri.lastIndexOf("/") + 1);
                    fileName =ncId;
                }
                else {
                    String url = uri.substring(0,uri.indexOf("/children") );
                    ncId= url.substring(url.lastIndexOf("/") + 1);
                    fileName= ncId+"children";
                }
                
              String xml = null;  
              if(!ncId.equals("C1")) {
                  xml = IOUtils.toString(this.getClass()
                          .getResourceAsStream("/" + fileName + ".xml")); 
              }
               
              if(xml == null) {
                  t.sendResponseHeaders(404, 0);
              }
              else {
                  t.sendResponseHeaders(200, 0);
                  OutputStream os = t.getResponseBody();
                  os.write(xml.getBytes("UTF-8"));
                  os.flush();
                  os.close();
              }
                    
                
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
    });
    
    server.createContext("/lexApiURL", new HttpHandler() {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                String uri = t.getRequestURI().toString();
                String ncId = uri.substring(uri.lastIndexOf("/") + 1);
                if (ncId.equals("C1")) {
                    t.sendResponseHeaders(404, 0); 
                }
                else {
                    String xml = IOUtils.toString(this.getClass()
                            .getResourceAsStream("/" + ncId + ".xml"));
                    t.sendResponseHeaders(200, 0);
                    OutputStream os = t.getResponseBody();
                    os.write(xml.getBytes("UTF-8"));
                    os.flush();
                    os.close(); 
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
    });
    server.setExecutor(null); 
    server.start();
  
}

public void stopServer() {
    server.stop(1);
    
}
    
}  
