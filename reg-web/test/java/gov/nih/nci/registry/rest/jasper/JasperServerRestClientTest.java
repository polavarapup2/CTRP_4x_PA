package gov.nih.nci.registry.rest.jasper;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.domain.KeystoreTest;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockRestClientNCITServer;

public class JasperServerRestClientTest {

    private JasperServerRestClient restClient;
    public static final int NCIT_API_MOCK_PORT = randomPort();
    MockRestClientNCITServer mockRestClientNCITServer = new MockRestClientNCITServer();
    private Map<String, String> reportGroupMap;
    
    /**
     * @return
     */
    private static int randomPort() {
        int port;
        while (isPortInUse((port = (int) (1024 + Math.random() * 64500)))) {
            System.out.println("Port " + port + " in use; trying another...");
        }
        return port;
    }
    
    private static boolean isPortInUse(final int port) {
        try {
            new ServerSocket(port).close();
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    @Before
    public void setup() throws Exception {
        
        File keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        keystoreFile.deleteOnExit();

        KeystoreTest.setFinalStatic(
                Keystore.class.getDeclaredField("KEYSTORE_FILE"), keystoreFile);
        
        mockRestClientNCITServer.startServer(NCIT_API_MOCK_PORT);
        String baseURL = "http://localhost:" + NCIT_API_MOCK_PORT + "/reports/rest/user";

        
        MockPaRegistryServiceLocator mockPaReg = mock(MockPaRegistryServiceLocator.class);
        MockLookUpTableService mockPaLookup = mock(MockLookUpTableService.class);
        
        when(mockPaLookup.getJasperCredentialsAccount()).thenReturn(getAccount());
        when(mockPaReg.getLookUpTableService()).thenReturn(mockPaLookup);
        
        PaRegistry.getInstance().setServiceLocator(mockPaReg);
        
        restClient = new JasperServerRestClient(baseURL, true);
        restClient.setLookUpTableService(new MockLookUpTableService());
        
        reportGroupMap = new HashMap<String, String>();
        reportGroupMap.put("DT4", "ROLE_DT4");
        reportGroupMap.put("DT3", "ROLE_DT3|ORG_2");
        
    }

    private Account getAccount() {
        
        Account account = new Account();
        account.setAccountName("jasper.token");
        account.setEncryptedPassword(getEncryptedPassword());
        account.setExternalSystem(ExternalSystemCode.JASPER);
        account.setUsername("jasperadmin");
        
        return account;
    }
    
    private String getEncryptedPassword() {
        Keystore ks = new Keystore();
        String password = RandomStringUtils.randomAscii(32);
        byte[] encryptedBytes = encrypt(password, ks.getKeypair().getPublic());
        String encryptedPasswordInHexUpper = Hex
                .encodeHexString(encryptedBytes).toUpperCase();
        
        return encryptedPasswordInHexUpper;
    }
    
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }
    
    @Test
    public void testUpdateRoles() {
        String user = "firstName";
        String reportIds = "DT4";
        String upResp = restClient.checkAndUpdateUser(user, reportIds, reportGroupMap);
        assertNotNull(upResp);
    }

    @Test
    public void testUpdateRolesWithOrg() {
        String user = "firstName";
        String reportIds = "DT3";
        String upResp = restClient.checkAndUpdateUser(user, reportIds, reportGroupMap);
        assertNotNull(upResp);
    }
    
    @Test
    public void testCreateUserWithDefaultRolesBadRequest() {
        String user = "badRequest";
        String reportIds = "DT3";
        String upResp = restClient.checkAndUpdateUser(user, reportIds, reportGroupMap);
        
        assert(upResp.contains("403"));
    }
    
    @After
    public void tearDown() throws Exception {
        mockRestClientNCITServer.stopServer();
    }
}
