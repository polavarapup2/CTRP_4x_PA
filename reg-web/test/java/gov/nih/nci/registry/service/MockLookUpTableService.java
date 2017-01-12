/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.Keystore;
import gov.nih.nci.pa.domain.KeystoreTest;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * @author Vrushali
 *
 */
public class MockLookUpTableService implements LookUpTableServiceRemote {
    public static final String FROM_ADDRESS = "ncictrp@mail.nih.gov";
    public static final String LOG_ADDRESS = "ctrplogs@mail.nih.gov";

    static Map<String,String> map;
    static {
        map = new HashMap<String, String>();
        map.put("smtp", "mailfwd.nih.gov");
        map.put("smtp.port", "25");
        map.put("fromaddress", FROM_ADDRESS);
        map.put("allowed.uploadfile.types", "doc");
        map.put("user.account.subject", "Junit user account subject");
        map.put("user.account.body", "Junit user account body url");
        map.put("trial.batchUpload.bodyHeader", "Junit ${SubmitterName}  ${CurrentDate}");
        map.put("trial.batchUpload.body", "Junit ${totalCount}  ${successCount} ${failedCount}");
        map.put("trial.batchUpload.errorMsg", "Junit ${ReleaseNumber} error");
        map.put("current.release.no", "Junit test");
        map.put("log.email.address", LOG_ADDRESS);
        map.put("delayed.posting.change.date", "September 2015");
        map.put("trial.batchUpload.reporBothtMsg", "report both ${createtableRows} ${amendtableRows} ${changeDate}");
        map.put("trial.batchUpload.reportCreateMsg", "create report ${tableRows} ${changeDate}");
        map.put("trial.batchUpload.reporAmendtMsg", "amend report ${tableRows} ${changeDate}");
        map.put("trial.batchUpload.reportMsg", "normal report");
        
        /*
         * Added for test case ReportViewerActionTest - JIRA: PO-7595
         * 
         * */
        map.put("regweb.reportview.availableReports", "Data Table 4:ROLE_DEV,Data Table 3:ROLE_DT3");
        map.put("regweb.reportview.mail.from","example@semanticbits.com");
        map.put("regweb.reportview.mail.to","example@semanticbits.com");
        map.put("regweb.reportview.mail.subject","JUnit Test Mail");
        map.put("regweb.reportview.mail.body","JUnit Test Mail");
        map.put("regweb.reportview.dt4.ldapgroup","TEST_GROUP_REPORT_VIEW");

        map.put("jasper.base.user.rest.url","http://localhost:20101/reports/rest/user");
        map.put("jasper.admin.username","admin");
        map.put("jasper.admin.password","admin");
        map.put("reg.web.admin.showReportsMenu", "true");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getCountries() throws PAException {
        List<Country> countryList = new ArrayList<Country>();
        Country country = new Country();
        country.setAlpha2("US");
        country.setAlpha3("USA");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("CA");
        country.setAlpha3("CAN");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("AS");
        country.setAlpha3("AUS");
        countryList.add(country);
        country = new Country();
        country.setAlpha2("JP");
        country.setAlpha3("JPN");
        countryList.add(country);
        return countryList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Country getCountryByName(String name) throws PAException {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPropertyValue(String name) throws PAException {
        return map.get(name);
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public List<Country> searchCountry(Country country) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public List<AnatomicSite> getAnatomicSites() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(Class<T> clazz, String code) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getStudyAlternateTitleTypes() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPropertyValueFromCache(String name) throws PAException {       
        return getPropertyValue(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getJasperCredentialsAccount() throws PAException {

        File keystoreFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        keystoreFile.deleteOnExit();

        try {
            KeystoreTest.setFinalStatic(
                    Keystore.class.getDeclaredField("KEYSTORE_FILE"), keystoreFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Account account = new Account();
        
        account.setAccountName("jasper.token");
        //account.setEncryptedPassword("4a0979dd97b89e7e661ad5b0c0cb44ef01588b1257e2ebc579aa7756fbcd9dcb8e0f65826d179565211b8bf25438a1126c87eed050f5e5e271d283fdde9daf9890b0643f4f8f0eceaead74bc01909f56b2aa7ea3dd8dbb994d3c2ef780b832c4abb76ee365f33033577770d88cb6f51f8be1ba1ba89d1822a1a1f1aa4d2bd28cf76bba17ab91029902227c9f46a5eca40314b2ce089e53f8a2240e0de35b32c80e883d2250a64a17b2af31b24b6dc8cba453852b05bbf45edd78913c7b119fd8d545d02533c05b751cdde4c1ac477ee8067e9ed7f66dc8779d62c2922d295bc1ebd0997dc7e5b823cdbbd30af58aabc11433eb43d0597b1c9b1f30c29f8eb135");
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
    
    private static byte[] encrypt(String text, PublicKey key) {
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
