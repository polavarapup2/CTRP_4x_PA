/**
 *
 */
package gov.nih.nci.accrual.util;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

/**
 * @author Kalpana Guthikonda
 *
 */
public class MockPaLookupTableServiceBean implements LookUpTableServiceRemote {

    public List<Country> getCountries() throws PAException {
        List<Country> countries = new ArrayList<Country>();
        Country c = new Country();
        c.setAlpha2("US");
        c.setAlpha3("USA");
        c.setName("USA");
        countries.add(c);
        return countries;
    }

    /**
     * {@inheritDoc}
     */
    public Country getCountryByName(String name) throws PAException {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        return null;
    }

    public List<NIHinstitute> getNihInstitutes() throws PAException {
        return null;
    }

    public String getPropertyValue(String name) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Country> searchCountry(Country country) throws PAException {
        List<Country> retList = new ArrayList<Country>();
        for (Country c: getCountries()) {
            if (country.getAlpha3().equalsIgnoreCase(c.getAlpha3())) {
                retList.add(c);
            }
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    public List<AnatomicSite> getAnatomicSites() throws PAException {
        List<AnatomicSite> returnVal = new ArrayList<AnatomicSite>();
        returnVal.add(TestSchema.createAnatomicSiteObj("Lung"));
        returnVal.add(TestSchema.createAnatomicSiteObj("Kidney"));
        returnVal.add(TestSchema.createAnatomicSiteObj("Heart"));
        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(Class<T> clazz, String code) throws PAException {
        if (AnatomicSite.class.getName().equals(clazz.getName())) {
            return (T) TestSchema.createAnatomicSiteObj(code);
        }
        return null;
    }

	public List<String> getStudyAlternateTitleTypes() throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

    
    public String getPropertyValueFromCache(String name) throws PAException {        
        return getPropertyValue(name);
    }

    @Override
    public Account getJasperCredentialsAccount() throws PAException {
        Account account = new Account();
        
        account.setAccountName("jasper.token");
        account.setEncryptedPassword("4a0979dd97b89e7e661ad5b0c0cb44ef01588b1257e2ebc579aa7756fbcd9dcb8e0f65826d179565211b8bf25438a1126c87eed050f5e5e271d283fdde9daf9890b0643f4f8f0eceaead74bc01909f56b2aa7ea3dd8dbb994d3c2ef780b832c4abb76ee365f33033577770d88cb6f51f8be1ba1ba89d1822a1a1f1aa4d2bd28cf76bba17ab91029902227c9f46a5eca40314b2ce089e53f8a2240e0de35b32c80e883d2250a64a17b2af31b24b6dc8cba453852b05bbf45edd78913c7b119fd8d545d02533c05b751cdde4c1ac477ee8067e9ed7f66dc8779d62c2922d295bc1ebd0997dc7e5b823cdbbd30af58aabc11433eb43d0597b1c9b1f30c29f8eb135");
        account.setExternalSystem(ExternalSystemCode.JASPER);
        account.setUsername("jasperadmin");
        
        return account;
    }

}
