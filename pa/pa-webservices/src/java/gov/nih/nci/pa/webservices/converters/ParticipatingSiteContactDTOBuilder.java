/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPerson;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.Investigator;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.PrimaryContact;
import gov.nih.nci.pa.webservices.types.EmailOrPhone;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class ParticipatingSiteContactDTOBuilder {

    private final OrganizationDTO organization;
    private final PAServiceUtils paServiceUtils;

    /**
     * @param organization
     *            OrganizationDTO
     * @param paServiceUtils
     *            PAServiceUtils
     */
    public ParticipatingSiteContactDTOBuilder(OrganizationDTO organization,
            PAServiceUtils paServiceUtils) {
        this.organization = organization;
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param ps
     *            ParticipatingSite
     * @return List<ParticipatingSiteContactDTO>
     * @throws PAException
     *             PAException
     */
    public List<ParticipatingSiteContactDTO> build(BaseParticipatingSite ps)
            throws PAException {
        List<ParticipatingSiteContactDTO> list = new ArrayList<>();
        for (Investigator inv : ps.getInvestigator()) {
            list.add(build(inv, ps.getPrimaryContact() != null));
        }
        if (ps.getPrimaryContact() != null) {
            list.add(build(ps.getPrimaryContact()));
        }
        return list;
    }

    private ParticipatingSiteContactDTO build(PrimaryContact pc)
            throws PAException {
        PersonDTO person = new PersonDTOBuilder().build(pc.getPerson());

        ParticipatingSiteContactDTO participatingSiteContactDTO = new ParticipatingSiteContactDTO();
        participatingSiteContactDTO.setPersonDTO(person);

        StudySiteContactDTO studySiteContact = new StudySiteContactDTO();
        studySiteContact.setPostalAddress(person.getPostalAddress());
        studySiteContact.setPrimaryIndicator(BlConverter.convertToBl(true));
        studySiteContact.setRoleCode(CdConverter
                .convertToCd(StudySiteContactRoleCode.PRIMARY_CONTACT));
        studySiteContact.setStatusDateRange(IvlConverter.convertTs()
                .convertToIvl(new Date(), null));
        studySiteContact.setTelecomAddresses(getTelecomAddress(pc
                .getContactDetails()));
        participatingSiteContactDTO.setStudySiteContactDTO(studySiteContact);

        ClinicalResearchStaffDTO crsDTO = paServiceUtils.getCrsDTO(person
                .getIdentifier(), organization.getIdentifier().getExtension());
        participatingSiteContactDTO.setAbstractPersonRoleDTO(crsDTO);

        return participatingSiteContactDTO;
    }

    private DSet<Tel> getTelecomAddress(EmailOrPhone contactDetails)
            throws PAException {
        final DSet<Tel> dset = new DSet<>();
        dset.setItem(new LinkedHashSet<Tel>());
        for (JAXBElement<String> element : contactDetails.getContent()) {
            try {
                if (StringUtils.isNotBlank(element.getValue())) {
                    TelPerson tel = null;
                    if ("email".equalsIgnoreCase(element.getName()
                            .getLocalPart())) {
                        tel = new TelEmail();
                        tel.setValue(new URI(TelEmail.SCHEME_MAILTO + ":"
                                + URLEncoder.encode(element.getValue())));
                    } else {
                        tel = new TelPhone();
                        tel.setValue(new URI(TelPhone.SCHEME_TEL + ":"
                                + URLEncoder.encode(element.getValue())));
                    }
                    dset.getItem().add(tel);
                }
            } catch (URISyntaxException e) {
                throw new PAException(
                        "Invalid primary contact email or phone: "
                                + element.getValue(), e);
            }
        }
        return dset;
    }

    private ParticipatingSiteContactDTO build(Investigator i,
            boolean primaryContactExplicitlySpecified) throws PAException {

        PersonDTO person = new PersonDTOBuilder().build(i.getPerson());

        ParticipatingSiteContactDTO participatingSiteContactDTO = new ParticipatingSiteContactDTO();
        participatingSiteContactDTO.setPersonDTO(person);

        StudySiteContactDTO studySiteContact = new StudySiteContactDTO();
        studySiteContact.setPostalAddress(person.getPostalAddress());
        studySiteContact.setPrimaryIndicator(BlConverter.convertToBl(i
                .isPrimaryContact() && !primaryContactExplicitlySpecified));
        studySiteContact
                .setRoleCode(CdConverter.convertStringToCd(i.getRole()));
        studySiteContact.setStatusDateRange(IvlConverter.convertTs()
                .convertToIvl(new Date(), null));
        studySiteContact.setTelecomAddresses(person.getTelecomAddress());
        participatingSiteContactDTO.setStudySiteContactDTO(studySiteContact);

        ClinicalResearchStaffDTO crsDTO = paServiceUtils.getCrsDTO(person
                .getIdentifier(), organization.getIdentifier().getExtension());
        participatingSiteContactDTO.setAbstractPersonRoleDTO(crsDTO);

        return participatingSiteContactDTO;
    }

}
