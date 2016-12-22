/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
public class MockPersonEntityService implements PersonEntityServiceRemote {

    public static final Map<String, PersonDTO> STORE = new HashMap<String, PersonDTO>();

    public static final Map<String, String> PO_ID_TO_CTEP_ID = new HashMap<String, String>();

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
        final String poPersonId = (MockOrganizationEntityService.PO_ID_SEQ++)
                + "";
        personDTO.setStatusCode(CdConverter
                .convertToCd(EntityStatusCode.PENDING));
        personDTO.setIdentifier(IiConverter.convertToPoPersonIi(poPersonId));
        final Ad ad = new Ad();
        ad.setPart(new ArrayList<Adxp>());
        personDTO.setPostalAddress(ad);
        STORE.put(poPersonId, personDTO);
        return IiConverter.convertToPoPersonIi(poPersonId);
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
        return new ArrayList<PersonDTO>();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
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

}
