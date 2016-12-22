package gov.nih.nci.accrual.accweb.dto.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class RegistryUserWebDTOTest {
	
	RegistryUserWebDTO webDto = new RegistryUserWebDTO();
	
	 @Before
	    public void initDto() {
		 webDto.setId(1l);
		 webDto.setAddressLine("addressLine");
		 webDto.setAffiliatedOrganizationId(1l);
		 webDto.setCity("city");
		 webDto.setCountry("country");
		 webDto.setCsmUserId(1l);
		 webDto.setEmailAddress("emailAddress");
		 webDto.setEnableEmails(true);
		 webDto.setFirstName("firstName");
		 webDto.setLastName("lastName");
		 webDto.setMiddleName("middleName");
		 webDto.setPhone("phone");
		 webDto.setPrsOrgName("prsOrgName");
		 webDto.setState("state");
		 webDto.setAffiliateOrg("affiliateOrg");
		 webDto.setPostalCode("postalCode");
	 }
	 
	 @Test
	 public void testGetterMethods() {
		 assertNotNull(webDto.getAddressLine());
		 assertNotNull(webDto.getAffiliatedOrganizationId());
		 assertNotNull(webDto.getAffiliateOrg());
		 assertNotNull(webDto.getCity());
		 assertNotNull(webDto.getCountry());
		 assertNotNull(webDto.getCsmUserId());
		 assertNotNull(webDto.getEmailAddress());
		 assertNotNull(webDto.getEnableEmails());
		 assertNotNull(webDto.getFirstName());
		 assertNotNull(webDto.getId());
		 assertNotNull(webDto.getLastName());
		 assertNotNull(webDto.getMiddleName());
		 assertNotNull(webDto.getPhone());
		 assertNotNull(webDto.getPostalCode());
		 assertNotNull(webDto.getPrsOrgName());
		 assertNotNull(webDto.getState());
	 }
	 

}
