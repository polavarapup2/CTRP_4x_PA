package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ExternalSystemCode;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Account in an external system, such as Twitter.
 * 
 * @author dkrylov
 * 
 */
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    private static final long serialVersionUID = 1234567890L;
    private static final Logger LOG = Logger.getLogger(Account.class);

    private String accountName;
    private ExternalSystemCode externalSystem;
    private String username;
    private String encryptedPassword;

    /**
     * @return the accountName
     */
    @Id
    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the externalSystem
     */
    @Column(name = "external_system")
    @Enumerated(EnumType.STRING)
    public ExternalSystemCode getExternalSystem() {
        return externalSystem;
    }

    /**
     * @param externalSystem
     *            the externalSystem to set
     */
    public void setExternalSystem(ExternalSystemCode externalSystem) {
        this.externalSystem = externalSystem;
    }

    /**
     * @return the username
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * HEX-representation of the password RSA-encrypted with a public key.
     * 
     * @return the encryptedPassword
     */
    @Column(name = "encrypted_password")
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * HEX-representation of the password RSA-encrypted with a public key.
     * 
     * @param encryptedPassword
     *            the encryptedPassword to set
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * @return decrypted password.
     */
    @Transient
    public String getDecryptedPassword() {
        String decrypt = null;
        KeyPair keypair = new Keystore().getKeypair();
        if (StringUtils.isNotBlank(getEncryptedPassword()) && keypair != null) {
            try {
                Cipher cipher = Cipher.getInstance(Keystore.ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());
                decrypt = new String(cipher.doFinal(Hex
                        .decodeHex(getEncryptedPassword().toCharArray())),
                        Charset.defaultCharset());
            } catch (InvalidKeyException | NoSuchAlgorithmException
                    | NoSuchPaddingException | IllegalBlockSizeException
                    | BadPaddingException | DecoderException e) {
                LOG.error(e, e);
            }
        }
        return decrypt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accountName == null) ? 0 : accountName.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (accountName == null) {
            if (other.accountName != null) {
                return false;
            }
        } else if (!accountName.equals(other.accountName)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [accountName=").append(accountName)
                .append(", externalSystem=").append(externalSystem)
                .append(", username=").append(username)
                .append(", encryptedPassword=").append(encryptedPassword)
                .append("]");
        return builder.toString();
    }

}
