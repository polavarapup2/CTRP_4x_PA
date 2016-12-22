// CHECKSTYLE:OFF
/**
 * 
 */
package gov.nih.nci.pa.domain;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha384Hex;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/**
 * PA Keystore.
 * 
 * @author dkrylov
 * 
 */
public final class Keystore {

    /**
     * ALGORITHM
     */
    public static final String ALGORITHM = "RSA";

    private static final String KEYPAIR_NAME = "paKeypair";

    private static final String KEYSTORE_FORMAT = "PKCS12";

    private static final int KEYSIZE = 2048;

    private static final File KEYSTORE_FILE = new File(SystemUtils.USER_HOME,
            "pa-keystore.pkcs12");

    private static final Logger LOG = Logger.getLogger(Keystore.class);

    /**
     * 
     */
    public Keystore() {
        init();
    }

    private void init() {
        synchronized (KEYSTORE_FILE) {
            if (!KEYSTORE_FILE.exists()) {
                try {
                    createKeystoreAndGenerateKeyPair();
                } catch (InvalidKeyException | NoSuchAlgorithmException
                        | KeyStoreException | CertificateException
                        | NoSuchProviderException | SignatureException
                        | IOException e) {
                    LOG.error(e, e);
                }
            }
        }
    }

    /**
     * @return PublicKeyPEM as String
     */
    public InputStream getPublicKeyPEMAsStream() {
        return IOUtils.toInputStream(getPublicKeyPEM());
    }

    /**
     * Plumbing to support calls from Struts. Ugly to have this here, I know...
     * 
     * @return String
     */
    public String execute() {
        return "success";
    }

    /**
     * @return PublicKeyPEM as String
     */
    public String getPublicKeyPEM() {
        try {
            KeyPair pair = getKeypair();
            PublicKey key = pair.getPublic();
            StringBuilder sb = new StringBuilder();
            sb.append("-----BEGIN PUBLIC KEY-----" + SystemUtils.LINE_SEPARATOR);

            CharArrayWriter base64Chunked = new CharArrayWriter();
            char[] buf = new char[64];
            byte[] encodedBytes = Base64.encodeBase64(key.getEncoded());
            for (int i = 0; i < encodedBytes.length; i += buf.length) {
                int index = 0;
                while (index != buf.length) {
                    if ((i + index) >= encodedBytes.length) {
                        break;
                    }
                    buf[index] = (char) encodedBytes[i + index];
                    index++;
                }
                base64Chunked.write(buf, 0, index);
                base64Chunked.write(SystemUtils.LINE_SEPARATOR);
            }

            sb.append(base64Chunked.toString());
            sb.append("-----END PUBLIC KEY-----");
            return sb.toString();
        } catch (IOException e) {
            LOG.error(e, e);
            return StringUtils.EMPTY;
        }

    }

    /**
     * @return KeyPair
     */
    public KeyPair getKeypair() {
        FileInputStream is = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
            is = FileUtils.openInputStream(KEYSTORE_FILE);
            keyStore.load(is, getKeystorePassword());
            final PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) keyStore
                    .getEntry(KEYPAIR_NAME, new KeyStore.PasswordProtection(
                            getKeystorePassword()));
            return new KeyPair(entry.getCertificate().getPublicKey(),
                    entry.getPrivateKey());
        } catch (KeyStoreException | NoSuchAlgorithmException
                | CertificateException | UnrecoverableEntryException
                | IOException e) {
            LOG.error(e, e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private void createKeystoreAndGenerateKeyPair()
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, InvalidKeyException,
            NoSuchProviderException, SignatureException {
        KeyPair pair = generateKeyPair();

        // Init keystore.
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FORMAT);
        char[] password = getKeystorePassword();
        keyStore.load(null, password);

        X509CertImpl cert = generateSelfSignedCert(pair);
        KeyStore.PrivateKeyEntry keyEntry = new KeyStore.PrivateKeyEntry(
                pair.getPrivate(), new Certificate[] { cert });
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(
                getKeystorePassword());
        keyStore.setEntry(KEYPAIR_NAME, keyEntry, protParam);

        // store away the keystore
        java.io.FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream(KEYSTORE_FILE);
            keyStore.store(fos, password);
        } finally {
            IOUtils.closeQuietly(fos);
        }

    }

    private char[] getKeystorePassword() throws IOException {
        Properties props = new Properties();
        final InputStream stream = this.getClass().getResourceAsStream(
                "/pa-common.properties");
        props.load(stream);
        return hashPassword(props.getProperty("keystore.password"))
                .toCharArray();
    }

    private String hashPassword(final String password) {
        return md5Hex(sha256Hex(md5Hex(sha384Hex(password)
                + sha256Hex(password))
                + sha384Hex(password)));
    }

    /**
     * @param pair
     * @return
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws SignatureException
     * @throws CertificateParsingException
     */

    private X509CertImpl generateSelfSignedCert(final KeyPair pair)
            throws IOException, CertificateException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchProviderException, SignatureException,
            CertificateParsingException {
        X509CertInfo info = new X509CertInfo();
        Date from = new Date();
        Date to = DateUtils.addYears(from, 5);
        CertificateValidity interval = new CertificateValidity(from, to);
        BigInteger sn = new BigInteger(64, new SecureRandom());
        X500Name owner = new X500Name("cn=pa");

        info.set(X509CertInfo.VALIDITY, interval);
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
        info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(owner));
        info.set(X509CertInfo.ISSUER, new CertificateIssuerName(owner));
        info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(
                CertificateVersion.V3));
        AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

        // Sign the cert to identify the algorithm that's used.
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(pair.getPrivate(), "SHA1withRSA");

        // Update the algorith, and resign.
        algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
        info.set(CertificateAlgorithmId.NAME + "."
                + CertificateAlgorithmId.ALGORITHM, algo);
        cert = new X509CertImpl(info);
        cert.sign(pair.getPrivate(), "SHA1withRSA");
        return cert;
    }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEYSIZE);
        return keyGen.genKeyPair();
    }

}
