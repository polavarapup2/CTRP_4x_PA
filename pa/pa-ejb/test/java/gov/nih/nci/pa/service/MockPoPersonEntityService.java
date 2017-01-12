/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sun.jndi.cosnaming.IiopUrl;

/**
 * @author Vrushali
 *
 */
public class MockPoPersonEntityService implements PersonEntityServiceRemote {
    static List<PersonDTO> personList;
    static {
        personList = new ArrayList<PersonDTO>();
        PersonDTO dto = new PersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setName(EnPnConverter.convertToEnPn("firstName", null, "lastName", null, null));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        try {
            DSet<Tel> list = new DSet<Tel>();
            list.setItem(new HashSet<Tel>());
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:mail@mail.com"));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
        } catch (URISyntaxException e) {
        }
        personList.add(dto);
        dto = new PersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("3"));
        dto.setName(EnPnConverter.convertToEnPn("OtherName", null, "OtherName", null, null));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        
        personList.add(dto);
        dto = new PersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setName(EnPnConverter.convertToEnPn("OtherName", null, "OtherName", null, null));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        personList.add(dto);
        
        dto = new PersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("abc"));
        dto.setName(EnPnConverter.convertToEnPn("SomeName", null, "SomeName", null, null));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        personList.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#createPerson(gov.nih.nci.services.person.PersonDTO)
     */
    public Ii createPerson(PersonDTO arg0) throws EntityValidationException {
        return IiConverter.convertToIi("2");
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#getPerson(gov.nih.nci.iso21090.Ii)
     */
    public PersonDTO getPerson(Ii arg0) throws NullifiedEntityException {
        if (NullFlavor.NA.equals(arg0.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(arg0, IiConverter.convertToPoPersonIi("2"));
            throw new NullifiedEntityException(nullifiedEntities);
        }
        for(PersonDTO dto:personList){
            if(dto.getIdentifier().getExtension().equals(arg0.getExtension())){
                return dto;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#search(gov.nih.nci.services.person.PersonDTO)
     */
    @Deprecated
    public List<PersonDTO> search(PersonDTO arg0) {
        List<PersonDTO> matchingDTO = new ArrayList<PersonDTO>();
        List<Enxp> nameList = arg0.getName().getPart();
        for(PersonDTO dto:personList){
            List<Enxp> dtoNameList = dto.getName().getPart();
            if(dtoNameList.get(0).getValue().equals(nameList.get(0).getValue())){
                matchingDTO.add(dto);
            }
        }
        return matchingDTO;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#updatePerson(gov.nih.nci.services.person.PersonDTO)
     */
    public void updatePerson(PersonDTO arg0) throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#updatePersonStatus(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    public void updatePersonStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#validate(gov.nih.nci.services.person.PersonDTO)
     */
    public Map<String, String[]> validate(PersonDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PersonDTO> search(PersonDTO arg0, LimitOffset arg1)
        throws TooManyResultsException {
    	List<PersonDTO> matchingDTO = new ArrayList<PersonDTO>();
        List<Enxp> nameList = arg0.getName().getPart();
        for(PersonDTO dto:personList){
            List<Enxp> dtoNameList = dto.getName().getPart();
            if(dtoNameList.get(0).getValue().equals(nameList.get(0).getValue())){
                matchingDTO.add(dto);
            }
        }
        
        int fromIndex = (arg1.getOffset() < 0 ? 0 : arg1.getOffset());
        int toIndex = Math.min(fromIndex + arg1.getLimit(), matchingDTO.size());

        try {
        	matchingDTO = matchingDTO.subList(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) { // fromIndex > toIndex
        	matchingDTO.clear();  // return empty list
        }

        if (matchingDTO.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return matchingDTO;
    }

    @Override
    public List<PersonDTO> search(PersonSearchCriteriaDTO c, LimitOffset arg1)
            throws TooManyResultsException {       
        for(PersonDTO dto:personList){
            if(dto.getIdentifier().getExtension().equals(c.getId())){
                return Arrays.asList(dto);
            }
        }
        return new ArrayList<PersonDTO>();
    }

}
