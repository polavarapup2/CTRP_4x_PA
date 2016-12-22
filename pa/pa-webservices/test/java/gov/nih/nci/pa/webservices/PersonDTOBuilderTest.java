/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.*;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.converters.PersonDTOBuilder;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dkrylov
 * 
 */
public class PersonDTOBuilderTest {

    PersonDTOBuilder builder;

    final String line1 = "Line 1";
    final String line2 = "Line 2";
    final String city = "City";
    final String state = "VA";
    final String zip = "22201";
    final String country = "USA";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        builder = new PersonDTOBuilder();
        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.webservices.converters.PersonDTOBuilder#build(gov.nih.nci.pa.webservices.types.Person)}
     * .
     * 
     * @throws URISyntaxException
     * @throws CurationException
     * @throws EntityValidationException
     */
    @Test
    public final void testMatch() throws URISyntaxException,
            EntityValidationException, CurationException {
        PersonDTO poPerson = createPoPerson("John", "Doe 01");
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        container.setNewPerson(createWsPerson("John", "Doe 01"));

        assertTrue(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testMinimalDataMatch() throws URISyntaxException,
            EntityValidationException, CurationException {

        final String lastname = UUID.randomUUID().toString();

        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());

        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Jack");
        dto.getName().getPart().add(part);

        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastname);
        dto.getName().getPart().add(partFam);

        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        t.setValue(new URI("tel", "555-555-5555", null));
        telco.getItem().add(t);
        dto.setTelecomAddress(telco);

        dto.setPostalAddress(AddressConverterUtil.create(line1, "", city, "",
                "", "ALB"));

        Ii poID = PoRegistry.getPersonEntityService().createPerson(dto);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        Person p = new Person();
        p.setFirstName("jack");
        p.setLastName(lastname.toLowerCase());
        p.setPrefix("");
        p.setSuffix("");
        p.setMiddleName("");

        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.ALB);
        ad.setLine1(line1);
        ad.setLine2("");
        ad.setPostalcode("");
        ad.setStateOrProvince("");
        p.setAddress(ad);

        container.setNewPerson(p);

        assertTrue(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testDiffMiddleNameCausesMismatch()
            throws URISyntaxException, EntityValidationException,
            CurationException {
        final String lastname = UUID.randomUUID().toString();
        PersonDTO poPerson = createPoPerson("John", lastname);
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        final Person wsPerson = createWsPerson("John", lastname);
        container.setNewPerson(wsPerson);

        wsPerson.setMiddleName(null);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testDiffPrefixCausesMismatch() throws URISyntaxException,
            EntityValidationException, CurationException {
        final String lastname = UUID.randomUUID().toString();
        PersonDTO poPerson = createPoPerson("John", lastname);
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        final Person wsPerson = createWsPerson("John", lastname);
        container.setNewPerson(wsPerson);

        wsPerson.setPrefix(null);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testDiffSuffixCausesMismatch() throws URISyntaxException,
            EntityValidationException, CurationException {
        final String lastname = UUID.randomUUID().toString();
        PersonDTO poPerson = createPoPerson("John", lastname);
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        final Person wsPerson = createWsPerson("John", lastname);
        container.setNewPerson(wsPerson);

        wsPerson.setSuffix(null);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testDiffLine2CausesMismatch() throws URISyntaxException,
            EntityValidationException, CurationException {
        final String lastname = UUID.randomUUID().toString();
        PersonDTO poPerson = createPoPerson("John", lastname);
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        final Person wsPerson = createWsPerson("John", lastname);
        container.setNewPerson(wsPerson);

        wsPerson.getAddress().setLine2(null);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    @Test
    public final void testDiffZipCausesMismatch() throws URISyntaxException,
            EntityValidationException, CurationException {
        final String lastname = UUID.randomUUID().toString();
        PersonDTO poPerson = createPoPerson("John", lastname);
        Ii poID = PoRegistry.getPersonEntityService().createPerson(poPerson);

        gov.nih.nci.pa.webservices.types.Person container = new gov.nih.nci.pa.webservices.types.Person();
        final Person wsPerson = createWsPerson("John", lastname);
        container.setNewPerson(wsPerson);

        wsPerson.getAddress().setPostalcode(null);

        assertFalse(poID.equals(builder.build(container).getIdentifier()));

    }

    private Person createWsPerson(String f, String l) {
        Person p = new Person();
        p.setFirstName(f);
        p.setLastName(l);
        p.setPrefix("Dr.");
        p.setSuffix("Sr.");
        p.setMiddleName("mid");

        final Address ad = new Address();
        ad.setCity(city);
        ad.setCountry(CountryISO31661Alpha3Code.valueOf(country));
        ad.setLine1(line1);
        ad.setLine2(line2);
        ad.setPostalcode(zip);
        ad.setStateOrProvince(state);
        p.setAddress(ad);
        return p;
    }

    private PersonDTO createPoPerson(String f, String l)
            throws URISyntaxException {
        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());

        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(f);
        dto.getName().getPart().add(part);

        Enxp partMid = new Enxp(EntityNamePartType.GIV);
        partMid.setValue("mid");
        dto.getName().getPart().add(partMid);

        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(l);
        dto.getName().getPart().add(partFam);

        Enxp partPfx = new Enxp(EntityNamePartType.PFX);
        partPfx.setValue("Dr.");
        dto.getName().getPart().add(partPfx);

        Enxp partSfx = new Enxp(EntityNamePartType.SFX);
        partSfx.setValue("Sr.");
        dto.getName().getPart().add(partSfx);

        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        t.setValue(new URI("tel", "555-555-5555", null));
        telco.getItem().add(t);
        dto.setTelecomAddress(telco);

        dto.setPostalAddress(AddressConverterUtil.create(line1, line2, city,
                state, zip, country));

        dto.setStatusCode(CdConverter
                .convertStringToCd(EntityStatusCode.PENDING.name()));

        return dto;
    }

}
