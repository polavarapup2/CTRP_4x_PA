/**
 * 
 */
package gov.nih.nci.pa.util.pomock;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockPersonEntityService implements PersonEntityServiceRemote {
    
    public static int PO_ID_SEQ = 1; // NOPMD

    public static final Map<String, PersonDTO> STORE = new HashMap<String, PersonDTO>();

    public static final Map<String, String> PO_ID_TO_CTEP_ID = new HashMap<String, String>();
    
    static {
        reset();
    }
    
    /**
     * Reset
     */
    public static final void reset() {
        PO_ID_SEQ = 1;
        STORE.clear();
        PO_ID_TO_CTEP_ID.clear();
        createInitialPersons();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#createPerson(gov
     * .nih.nci.services.person.PersonDTO)
     */
    @Override
    public Ii createPerson(PersonDTO personDTO)
            throws EntityValidationException, CurationException {
        return createAndStorePerson(personDTO);
    }

    /**
     * @param personDTO
     * @return
     */
    private static Ii createAndStorePerson(PersonDTO personDTO) {
        final String poPersonId = (PO_ID_SEQ++)
                + ""; // NOPMD
        personDTO.setStatusCode(CdConverter
                .convertToCd(EntityStatusCode.PENDING));
        personDTO.setIdentifier(IiConverter.convertToPoPersonIi(poPersonId));
        
        if (personDTO.getPostalAddress() == null) {
            final Ad ad = getAddress();       
            personDTO.setPostalAddress(ad);
        }
        
        if (personDTO.getTelecomAddress()==null) {
            personDTO.setTelecomAddress(getTelAdd());
        }
        
        STORE.put(poPersonId, personDTO);
        return IiConverter.convertToPoPersonIi(poPersonId);
    }

    private static void createInitialPersons() {
       PersonDTO person = new PersonDTO();
       person.setName(EnPnConverter.convertToEnPn("John", "G", "Doe", "Mr.", "III"));
       createAndStorePerson(person);
       PO_ID_TO_CTEP_ID.put(IiConverter.convertToString(person.getIdentifier()), "JDOE01");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#getPerson(gov.nih
     * .nci.iso21090.Ii)
     */
    @Override
    public PersonDTO getPerson(Ii ii) throws NullifiedEntityException {
        return STORE.get(ii.getExtension());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#search(gov.nih.
     * nci.services.person.PersonDTO)
     */
    @Override
    public List<PersonDTO> search(PersonDTO arg0) {
        PersonSearchCriteriaDTO criteria = new PersonSearchCriteriaDTO();
        criteria.setFirstName(EnPnConverter.getNamePart(arg0.getName(), EntityNamePartType.GIV));
        criteria.setLastName(EnPnConverter.getNamePart(arg0.getName(), EntityNamePartType.FAM));
        try {
            return search(criteria, new LimitOffset(500, 0));
        } catch (TooManyResultsException e) {
            throw new RuntimeException(e); // NOPMD
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#search(gov.nih.
     * nci.services.person.PersonDTO, gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<PersonDTO> search(PersonDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        return search(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#search(gov.nih.
     * nci.services.person.PersonSearchCriteriaDTO,
     * gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<PersonDTO> search(PersonSearchCriteriaDTO c, LimitOffset arg1)
            throws TooManyResultsException {
        List<PersonDTO> list = new ArrayList<PersonDTO>();
        for (Map.Entry<String, PersonDTO> entry : STORE.entrySet()) {
            PersonDTO person = entry.getValue();
            String poid = entry.getKey();
            boolean match = true;
            if (StringUtils.isNotBlank(c.getFirstName())) {
                match = match
                        && EnPnConverter
                                .getNamePart(person.getName(),
                                        EntityNamePartType.GIV, 0).toLowerCase()
                                .contains(c.getFirstName().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getLastName())) {
                match = match
                        && EnPnConverter
                                .getNamePart(person.getName(),
                                        EntityNamePartType.FAM).toLowerCase()
                                .contains(c.getLastName().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getCtepId())) {
                match = match
                        && PO_ID_TO_CTEP_ID.get(poid) != null
                        && PO_ID_TO_CTEP_ID.get(poid).toLowerCase()
                                .contains(c.getCtepId().toLowerCase());
            }
            if (match) {
                list.add(person);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#updatePerson(gov
     * .nih.nci.services.person.PersonDTO)
     */
    @Override
    public void updatePerson(PersonDTO arg0) throws EntityValidationException {
        STORE.put(arg0.getIdentifier().getExtension(), arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#updatePersonStatus
     * (gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    @Override
    public void updatePersonStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        try {
            getPerson(arg0).setStatusCode(arg1);
        } catch (NullifiedEntityException e) {          
            e.printStackTrace();
            throw new RuntimeException(e); // NOPMD
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.person.PersonEntityServiceRemote#validate(gov.nih
     * .nci.services.person.PersonDTO)
     */
    @Override
    public Map<String, String[]> validate(PersonDTO arg0) {
        return new HashMap<String, String[]>();
    }
    
    /**
     * @return
     */
    private static Ad getAddress() {
        return AddressConverterUtil.create("street", "deliv", "city", "MD",
                "20000", "USA");
    }

    /**
     * 
     * @return
     */
    private static DSet<Tel> getTelAdd() {
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        try {
            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:test@example.com"));
            telSet.add(email);
            TelPhone phone = new TelPhone();
            phone.setValue(new URI("tel:111-222-3333"));
            telSet.add(phone);
            TelUrl url = new TelUrl();
            url.setValue(new URI("http://example.com"));
            telSet.add(url);
        } catch (URISyntaxException e) {
        }
        telAd.setItem(telSet);
        return telAd;
    }

}
