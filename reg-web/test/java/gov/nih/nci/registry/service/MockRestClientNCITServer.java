package gov.nih.nci.registry.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import gov.nih.nci.registry.rest.jasper.ObjectFactory;
import gov.nih.nci.registry.rest.jasper.Users;
import gov.nih.nci.registry.rest.jasper.Users.User;
import gov.nih.nci.registry.rest.jasper.Users.User.Roles;

public class MockRestClientNCITServer {

    private HttpServer server;

    private String marshallXML(Object users) {

        String responseXML = "";

        try {
            JAXBContext userJxContext = JAXBContext.newInstance(Users.class);
            Marshaller userMarshaller = userJxContext.createMarshaller();
            userMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            userMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            userMarshaller.marshal(users, outputStream);
            responseXML = outputStream.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseXML;
    }

    public void startServer(int port) throws Exception {

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/reports", new HttpHandler() {
            @Override
            public void handle(HttpExchange t) throws IOException {
                try {

                    List<String> authTokens = t.getRequestHeaders().get("Authorization");

                    if (authTokens != null && authTokens.size() > 0) {
                        for (String authToken : authTokens) {
                            if (authToken.startsWith("Basic")) {
                                String uri = t.getRequestURI().toString();
                                String userName = uri.substring(uri.lastIndexOf("/") + 1);

                                if (userName.equals("badRequest")) {
                                    t.sendResponseHeaders(400, 0);
                                    return;
                                } else if(userName.equals("user")) {
                                    userName = "firstName";
                                }
                                    

                                ObjectFactory objFactory = new ObjectFactory();

                                Users users = objFactory.createUsers();
                                User user = objFactory.createUsersUser();
                                user.setEnabled("true");
                                user.setUsername(userName);
                                user.setTenantId("organization_1");

                                List<Users.User> userList = new ArrayList<>();
                                userList.add(user);

                                Roles roles = objFactory.createUsersUserRoles();
                                roles.setRoleName("DT4");
                                roles.setExternallyDefined("false");
                                roles.setTenantId("organization_1");
                                List<Users.User.Roles> roleList = new ArrayList<>();

                                roleList.add(roles);
                                user.setRoles(null);
                                user.setRoles(roleList);

                                users.setUser(null);
                                users.setUser(userList);

                                String responsXML = marshallXML(users);

                                t.sendResponseHeaders(200, 0);
                                OutputStream os = t.getResponseBody();
                                os.write(responsXML.getBytes("UTF-8"));
                                os.flush();
                                os.close();

                            } else {
                                t.sendResponseHeaders(403, 0);
                            }
                        }
                    } else {
                        t.sendResponseHeaders(403, 0);
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
