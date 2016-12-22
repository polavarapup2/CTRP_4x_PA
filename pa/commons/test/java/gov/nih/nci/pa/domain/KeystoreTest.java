/**
 * 
 */
package gov.nih.nci.pa.domain;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha384Hex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class KeystoreTest {

    {
        org.apache.log4j.Logger.getRootLogger().setLevel(
                org.apache.log4j.Level.ALL);
    }

    private File keystoreFile;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        keystoreFile.deleteOnExit();

        setFinalStatic(Keystore.class.getDeclaredField("KEYSTORE_FILE"),
                keystoreFile);

    }

    @After
    public void tearDown() throws Exception {
        keystoreFile.delete();
    }

    /**
     * Test method for {@link gov.nih.nci.pa.domain.Keystore#Keystore()}.
     */
    @Test
    public final void testKeystore() {
        final Keystore ks = new Keystore();
        assertTrue(keystoreFile.exists());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.domain.Keystore#getKeypair()}.
     * 
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableEntryException
     */
    @Test
    public final void testGetKeypair() throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException,
            UnrecoverableEntryException {
        final Keystore ks = new Keystore();
        assertTrue(keystoreFile.exists());

        String passwordHash = md5Hex(sha256Hex(md5Hex(sha384Hex("myKeystorePassword")
                + sha256Hex("myKeystorePassword"))
                + sha384Hex("myKeystorePassword")));

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(FileUtils.openInputStream(keystoreFile),
                passwordHash.toCharArray());
        final PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) keyStore
                .getEntry("paKeypair", new KeyStore.PasswordProtection(
                        passwordHash.toCharArray()));
        final KeyPair pair = new KeyPair(entry.getCertificate().getPublicKey(),
                entry.getPrivateKey());

        assertEquals(pair.getPrivate(), ks.getKeypair().getPrivate());
        assertEquals(pair.getPublic(), ks.getKeypair().getPublic());

    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
