/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import static org.apache.commons.lang.StringUtils.defaultString;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.trim;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.PoEntityCannotBeCreatedException;
import gov.nih.nci.pa.webservices.EntityNotFoundException;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.OrganizationOrPersonID;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author dkrylov
 * 
 */
public class OrganizationDTOBuilder {

    private static final Logger LOG = Logger
            .getLogger(OrganizationDTOBuilder.class);

    /**
     * @param org
     *            Organization
     * @return OrganizationDTO
     * @throws NullifiedEntityException
     *             NullifiedEntityException
     * @throws PAException
     *             PAException
     * @throws TooManyResultsException
     *             ,
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     */
    public OrganizationDTO build(Organization org)
            throws NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        if (org == null) {
            return null;
        }
        if (org.getExistingOrganization() != null) {
            return locateExistingOrg(org.getExistingOrganization());
        } else {
            return createNewOrgOrFindExactMatch(org.getNewOrganization());
        }

    }

    private OrganizationDTO createNewOrgOrFindExactMatch(
            gov.nih.nci.po.webservices.types.Organization newOrg) {
        OrganizationDTO match = findExactMatchInPo(newOrg);
        if (match != null) {
            return match;
        }
        try {
            OrganizationDTO orgDto = new OrganizationDTO();
            orgDto.setName(EnOnConverter.convertToEnOn(newOrg.getName()));
            orgDto.setStatusCode(CdConverter
                    .convertStringToCd(EntityStatusCode.PENDING.name()));

            final Address addr = newOrg.getAddress();
            orgDto.setPostalAddress(AddressConverterUtil.create(
                    addr.getLine1(), addr.getLine2(), addr.getCity(), addr
                            .getStateOrProvince(), addr.getPostalcode(), addr
                            .getCountry().value()));

            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());

            if (StringUtils.isNotBlank(getContact(newOrg, ContactType.PHONE))) {
                Tel t = new Tel();
                t.setValue(new URI("tel",
                        getContact(newOrg, ContactType.PHONE), null));
                telco.getItem().add(t);
            }
            if (StringUtils.isNotBlank(getContact(newOrg, ContactType.FAX))) {
                Tel f = new Tel();
                f.setValue(new URI("x-text-fax", getContact(newOrg,
                        ContactType.FAX), null));
                telco.getItem().add(f);
            }
            if (StringUtils.isNotBlank(getContact(newOrg, ContactType.TTY))) {
                Tel tt = new Tel();
                tt.setValue(new URI("x-text-tel", getContact(newOrg,
                        ContactType.TTY), null));
                telco.getItem().add(tt);
            }
            if (StringUtils.isNotBlank(getContact(newOrg, ContactType.URL))) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(getContact(newOrg, ContactType.URL)));
                telco.getItem().add(telurl);
            }
            if (StringUtils.isNotBlank(getContact(newOrg, ContactType.EMAIL))) {
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:"
                        + getContact(newOrg, ContactType.EMAIL)));
                telco.getItem().add(telemail);
            }
            orgDto.setTelecomAddress(telco);

            return PoRegistry.getOrganizationEntityService().getOrganization(
                    PoRegistry.getOrganizationEntityService()
                            .createOrganization(orgDto));
        } catch (NullifiedEntityException e) {
            throw new PoEntityCannotBeCreatedException(
                    "It appears you have attempted to create an organization "
                            + newOrg.getName()
                            + " in NULLIFIED status. "
                            + "Such organizations, albeit can be created, cannot be used on trials.");
        } catch (URISyntaxException | EntityValidationException
                | CurationException e) {
            LOG.error(e, e);
            throw new PoEntityCannotBeCreatedException(
                    "Organization "
                            + newOrg.getName()
                            + " could not be created in PO because it has either missing or invalid data. "
                            + "Organization information is subject to PO business rules and validation."
                            + " Additional information: " + e.getMessage());

        }
    }

    private OrganizationDTO findExactMatchInPo(
            gov.nih.nci.po.webservices.types.Organization newOrg) {
        if (StringUtils.isBlank(newOrg.getName())) {
            return null;
        }
        try {
            OrganizationSearchCriteriaDTO criteria = turnIntoSearchCriteria(newOrg);
            List<OrganizationDTO> orgList = PADomainUtils
                    .searchPoOrganizations(criteria);
            return findExactMatchAmongSearchResults(orgList, newOrg);
        } catch (Exception e) {
            LOG.warn("Attempt to find an exact match in PO for organization "
                    + newOrg.getName() + " failed. Assuming no match.");
            LOG.warn(e, e);
            return null;
        }
    }

    private OrganizationDTO findExactMatchAmongSearchResults(
            List<OrganizationDTO> list,
            gov.nih.nci.po.webservices.types.Organization newOrg)
            throws NullifiedEntityException {
        for (OrganizationDTO dto : list) {
            if (isExactMatch(dto, newOrg)) {
                return PoRegistry.getOrganizationEntityService()
                        .getOrganization(dto.getIdentifier());
            }
        }
        return null;
    }

    private boolean isExactMatch(OrganizationDTO poOrg,
            gov.nih.nci.po.webservices.types.Organization newOrg) {
        boolean nameOK = equalsIgnoreCase(trim(newOrg.getName()),
                trim(EnOnConverter.convertEnOnToString(poOrg.getName())));

        PaOrganizationDTO paDTO = new PaOrganizationDTO();
        PADomainUtils.setPostalAddressFields(null, paDTO,
                poOrg.getPostalAddress());

        boolean address1OK = equalsIgnoreCase(trim(newOrg.getAddress()
                .getLine1()), trim(paDTO.getAddress1()));
        boolean address2OK = equalsIgnoreCase(trim(defaultString(newOrg
                .getAddress().getLine2())),
                trim(defaultString(paDTO.getAddress2())));
        boolean cityOK = equalsIgnoreCase(trim(newOrg.getAddress().getCity()),
                trim(paDTO.getCity()));
        boolean stateOK = equalsIgnoreCase(trim(defaultString(newOrg
                .getAddress().getStateOrProvince())),
                trim(defaultString(paDTO.getState())));
        boolean countryOK = equalsIgnoreCase(trim(newOrg.getAddress()
                .getCountry().name()), trim(paDTO.getCountry()));
        boolean zipOK = equalsIgnoreCase(trim(defaultString(newOrg.getAddress()
                .getPostalcode())), trim(defaultString(paDTO.getZip())));
        return nameOK && address1OK && address2OK && cityOK && stateOK
                && countryOK && zipOK;
    }

    private OrganizationSearchCriteriaDTO turnIntoSearchCriteria(
            gov.nih.nci.po.webservices.types.Organization newOrg) {
        OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();
        criteria.setName(trim(newOrg.getName()));
        criteria.setCity(trim(newOrg.getAddress().getCity()));
        criteria.setZip(trim(newOrg.getAddress().getPostalcode()));
        criteria.setCountry(newOrg.getAddress().getCountry().name());
        return criteria;
    }

    private String getContact(
            gov.nih.nci.po.webservices.types.Organization org, ContactType type) {
        for (Contact c : org.getContact()) {
            if (type == c.getType()) {
                return c.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    private OrganizationDTO locateExistingOrg(
            OrganizationOrPersonID existingOrganization)
            throws NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        if (existingOrganization.getPoID() != null) {
            return locateExistingOrgByPOID(existingOrganization.getPoID());
        } else {
            return build(existingOrganization.getCtepID());
        }

    }

    /**
     * @param ctepID ctepID
     * @return OrganizationDTO
     * @throws TooManyResultsException TooManyResultsException
     * @throws PAException PAException
     */
    public OrganizationDTO build(final String ctepID)
            throws TooManyResultsException, PAException {
        try {
            OrganizationSearchCriteriaDTO orgSearchCriteria = new OrganizationSearchCriteriaDTO();
            orgSearchCriteria.setCtepId(ctepID);
            List<OrganizationDTO> orgList = PADomainUtils
                    .searchPoOrganizations(orgSearchCriteria);
            CollectionUtils.filter(orgList, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    OrganizationDTO org = (OrganizationDTO) o;
                    String orgPoId = IiConverter.convertToString(org
                            .getIdentifier());
                    String orgCtepID;
                    try {
                        orgCtepID = PADomainUtils.getOrgDetailsPopup(orgPoId)
                                .getCtepId();
                    } catch (NullifiedEntityException | NullifiedRoleException
                            | PAException | TooManyResultsException e) {
                        throw new RuntimeException(e);
                    }
                    return StringUtils.equalsIgnoreCase(orgCtepID, ctepID);
                }
            });

            if (CollectionUtils.isEmpty(orgList)) {
                throw new EntityNotFoundException(
                        "Organization with CTEP ID of " + ctepID
                                + " cannot be found in PO.");
            } else if (orgList.size() > 1) {
                throw new EntityNotFoundException(
                        "It appears there are "
                                + orgList.size()
                                + " organizations with CTEP ID of "
                                + ctepID
                                + " in PO. Since we don't know which one to pick, we are having to fail this request.");
            } else {
                return orgList.get(0);
            }
        } catch (NullifiedEntityException | NullifiedRoleException ex) {
            throw new EntityNotFoundException(
                    "Organization with CTEP ID of "
                            + ctepID
                            + " appears to have NULLIFIED status in PO and thus cannot be used.");
        }
    }

    private OrganizationDTO locateExistingOrgByPOID(Long poID) {
        try {
            final OrganizationDTO organization = PoRegistry
                    .getOrganizationEntityService().getOrganization(
                            IiConverter.convertToPoOrganizationIi(poID
                                    .toString()));
            if (organization == null) {
                throw new EntityNotFoundException("Organization with PO ID of "
                        + poID + " cannot be found in PO.");
            }
            return organization;
        } catch (NullifiedEntityException e) {
            throw new EntityNotFoundException(
                    "Organization with PO ID of "
                            + poID
                            + " appears to have NULLIFIED status in PO and thus cannot be used.");
        }
    }

    /**
     * @param wsOrgs
     *            List<Organization>
     * @return OrganizationDTO
     * @throws NullifiedEntityException
     *             NullifiedEntityException
     * @throws PAException
     *             PAException
     * @throws TooManyResultsException
     *             ,
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     */
    public List<OrganizationDTO> build(List<Organization> wsOrgs)
            throws NullifiedEntityException, NullifiedRoleException,
            TooManyResultsException, PAException {
        List<OrganizationDTO> list = new ArrayList<>();
        for (Organization org : wsOrgs) {
            list.add(build(org));
        }
        return list;
    }

}
