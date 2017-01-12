/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.webservices.converters.OrganizationDTOBuilder;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class OrganizationDTOBuilderTest {

    private OrganizationDTOBuilder builder;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        builder = new OrganizationDTOBuilder();
        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.converters.OrganizationDTOBuilder#build(gov.nih.nci.pa.webservices.types.Organization)}
     * .
     * 
     * @throws CurationException
     * @throws EntityValidationException
     * @throws PAException
     * @throws TooManyResultsException
     * @throws NullifiedRoleException
     * @throws NullifiedEntityException
     */
    @Test
    public final void testMatch() throws EntityValidationException,
            CurationException, NullifiedEntityException,
            NullifiedRoleException, TooManyResultsException, PAException {
        final String line1 = "Line 1";
        final String line2 = "Line 2";
        final String city = "City";
        final String state = "VA";
        final String zip = "22201";
        final String country = "USA";
        final String name = "New Organization 001";

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(name));
        orgDto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));
        orgDto.setPostalAddress(AddressConverterUtil.create(line1, line2, city,
                state, zip, country));
        Ii poID = PoRegistry.getOrganizationEntityService().createOrganization(
                orgDto);

        Organization container = new Organization();
        gov.nih.nci.po.webservices.types.Organization wsOrg = new gov.nih.nci.po.webservices.types.Organization();
        container.setNewOrganization(wsOrg);
        wsOrg.setName(name);
        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertEquals(poID, builder.build(container).getIdentifier());

    }

    @Test
    public final void testNoMatchIfNullified()
            throws EntityValidationException, CurationException,
            NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        final String line1 = "Line 1";
        final String line2 = "Line 2";
        final String city = "City";
        final String state = "VA";
        final String zip = "22201";
        final String country = "USA";
        final String name = "New Organization 005";

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(name));
        orgDto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));
        orgDto.setPostalAddress(AddressConverterUtil.create(line1, line2, city,
                state, zip, country));
        Ii poID = PoRegistry.getOrganizationEntityService().createOrganization(
                orgDto);

        Organization container = new Organization();
        gov.nih.nci.po.webservices.types.Organization wsOrg = new gov.nih.nci.po.webservices.types.Organization();
        container.setNewOrganization(wsOrg);
        wsOrg.setName(name);
        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertEquals(poID, builder.build(container).getIdentifier());

        MockOrganizationEntityService.STORE.get(poID.getExtension())
                .setStatusCode(
                        CdConverter.convertToCd(EntityStatusCode.NULLIFIED));
        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testExactNameMatchOnly()
            throws EntityValidationException, CurationException,
            NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        final String line1 = "Line 1";
        final String line2 = "Line 2";
        final String city = "City";
        final String state = "VA";
        final String zip = "22201";
        final String country = "USA";
        final String name = "New Organization 002";

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(name));
        orgDto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));
        orgDto.setPostalAddress(AddressConverterUtil.create(line1, line2, city,
                state, zip, country));
        Ii poID = PoRegistry.getOrganizationEntityService().createOrganization(
                orgDto);

        Organization container = new Organization();
        gov.nih.nci.po.webservices.types.Organization wsOrg = new gov.nih.nci.po.webservices.types.Organization();
        container.setNewOrganization(wsOrg);
        wsOrg.setName("New Organization 00");
        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testExactAddressMatchOnly()
            throws EntityValidationException, CurationException,
            NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        final String line1 = "Line 1";
        final String line2 = "Line 2";
        final String city = "City";
        final String state = "VA";
        final String zip = "22201";
        final String country = "USA";
        final String name = "New Organization 003";

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(name));
        orgDto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));
        orgDto.setPostalAddress(AddressConverterUtil.create(line1, line2, city,
                state, zip, country));
        Ii poID = PoRegistry.getOrganizationEntityService().createOrganization(
                orgDto);

        Organization container = new Organization();
        gov.nih.nci.po.webservices.types.Organization wsOrg = new gov.nih.nci.po.webservices.types.Organization();
        container.setNewOrganization(wsOrg);
        wsOrg.setName(name);
        Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        // ad.setLine2(line2);
        ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

        ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        // ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

        ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        ad.setPostalcode(zip);
        // ad.setStateOrProvince(state);
        wsOrg.setAddress(ad);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testMinimalInternationalAddress()
            throws EntityValidationException, CurationException,
            NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        final String line1 = "Line 1";
        final String city = "City";
        final String country = "ALB";
        final String name = "New Organization 004";

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(name));
        orgDto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));
        orgDto.setPostalAddress(AddressConverterUtil.create(line1, "", city,
                "", "", country));
        Ii poID = PoRegistry.getOrganizationEntityService().createOrganization(
                orgDto);

        Organization container = new Organization();
        gov.nih.nci.po.webservices.types.Organization wsOrg = new gov.nih.nci.po.webservices.types.Organization();
        container.setNewOrganization(wsOrg);
        wsOrg.setName(name);
        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        wsOrg.setAddress(ad);

        assertEquals(poID, builder.build(container).getIdentifier());

    }

}
