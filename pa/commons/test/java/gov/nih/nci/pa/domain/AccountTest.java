/**
 * 
 */
package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class AccountTest {

    private File keystoreFile;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        keystoreFile.deleteOnExit();

        KeystoreTest.setFinalStatic(
                Keystore.class.getDeclaredField("KEYSTORE_FILE"), keystoreFile);

    }

    @After
    public void tearDown() throws Exception {
        keystoreFile.delete();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.domain.Account#getDecryptedPassword()}.
     */
    @Test
    public final void testGetDecryptedPassword() {
        Keystore ks = new Keystore();
        Account account = new Account();

        // Encrypt password.
        String password = RandomStringUtils.randomAscii(32);
        System.out.println("Password before encryption: " + password);
        byte[] encryptedBytes = encrypt(password, ks.getKeypair().getPublic());
        String encryptedPasswordInHexUpper = Hex
                .encodeHexString(encryptedBytes).toUpperCase();
        String encryptedPasswordInHexLower = Hex
                .encodeHexString(encryptedBytes).toUpperCase();
        System.out.println("Encrypted bytes in HEX: "
                + encryptedPasswordInHexUpper);

        // Account class must be able to decrypt properly.
        account.setEncryptedPassword(encryptedPasswordInHexLower);
        assertEquals(password, account.getDecryptedPassword());
        account.setEncryptedPassword(encryptedPasswordInHexUpper);
        assertEquals(password, account.getDecryptedPassword());

    }

    @Test
    public final void testWithOpenSSL() throws IOException {

        Keystore ks = new Keystore();

        // Save password.
        String password = RandomStringUtils.randomAscii(32);
        System.out.println("Password before encryption: " + password);
        File passwordFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString());
        FileUtils.writeStringToFile(passwordFile, password);
        passwordFile.deleteOnExit();

        // Save public key as PEM somewhere
        File publicKeyPem = new File("public_key.pem");
        FileUtils.writeStringToFile(publicKeyPem, (ks.getPublicKeyPEM()));
        publicKeyPem.deleteOnExit();
        System.out.println("Public Key PEM:\r\n" + ks.getPublicKeyPEM());

        // Encrypted password file (binary).
        File encryptedPasswordFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString());
        encryptedPasswordFile.deleteOnExit();

        if (SystemUtils.IS_OS_LINUX || isOpenSSLAvailable()) {
            // Encrypt the password using OpenSSL and the pub key.
            final String cmd = "openssl rsautl -in "
                    + passwordFile.getCanonicalPath() + " -out "
                    + encryptedPasswordFile.getCanonicalPath() + " -inkey "
                    + publicKeyPem.getCanonicalPath() + " -pubin -encrypt";
            System.out.println("OpenSSL cmd: " + cmd);
            String output = runOSCommand(cmd);

            System.out.println("OpenSSL output: " + output);
            System.out.println("Encrypted password is in: "
                    + encryptedPasswordFile.getCanonicalPath());

            // Read encrypted bytes and turn them into HEX.
            byte[] encryptedBytes = FileUtils
                    .readFileToByteArray(encryptedPasswordFile);
            String encryptedPasswordInHexUpper = Hex.encodeHexString(
                    encryptedBytes).toUpperCase();
            String encryptedPasswordInHexLower = Hex.encodeHexString(
                    encryptedBytes).toUpperCase();
            System.out.println("Encrypted bytes in HEX: "
                    + encryptedPasswordInHexUpper);

            // Account class must be able to decrypt properly.
            Account account = new Account();
            account.setEncryptedPassword(encryptedPasswordInHexLower);
            assertEquals(password, account.getDecryptedPassword());
            account.setEncryptedPassword(encryptedPasswordInHexUpper);
            assertEquals(password, account.getDecryptedPassword());
        }

    }

    private boolean isOpenSSLAvailable() {
        try {
            return runOSCommand("openssl version").contains("OpenSSL");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private String runOSCommand(String cmd) throws ExecuteException,
            IOException {
        StringBuilder sb = new StringBuilder();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(out, err);

        CommandLine cmdLine = CommandLine.parse(cmd);
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(streamHandler);
        int exitValue = executor.execute(cmdLine);

        sb.append("Exit code: " + exitValue + SystemUtils.LINE_SEPARATOR);
        sb.append("stdout: " + SystemUtils.LINE_SEPARATOR + out
                + SystemUtils.LINE_SEPARATOR);
        sb.append("stderr: " + SystemUtils.LINE_SEPARATOR + err
                + SystemUtils.LINE_SEPARATOR);

        return sb.toString();

    }

    /**
     * Encrypt the plain text using public key.
     * 
     * @param text
     *            : original plain text
     * @param key
     *            :The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
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
}
