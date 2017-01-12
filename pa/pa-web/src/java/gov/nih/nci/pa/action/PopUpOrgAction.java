package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.util.AddressUtil;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;


/**
 * @author Hugh Reinhart
 * @since Mar 27, 2012
 */
public class PopUpOrgAction extends AbstractPopUpPoAction {
    private static final String STRUTS_TOKEN_POPUP = "struts.token.popup";

    private static final long serialVersionUID = -6795733516099098037L;

    private List<PaOrganizationDTO> orgs = new ArrayList<PaOrganizationDTO>();
    private final OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();
    private String familyName;
    private String fax;
    private String orgName;
    private String orgStAddress;
    private String tty;
    private String url;

    /**
     *
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            getCountriesList();
            
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        orgs = null;
        return "orgs";
    }

    /**
     *
     * @return result
     */
    public String displayOrgList() {
        return processDisplayOrganizations(SUCCESS);
    }

    /**
     *
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return processDisplayOrganizations("orgs");
    }

    /**
     * @return result
     */
    public String createOrganization() {
        // Because this is an Ajax request, Struts token field will not get updated in the HTML form
        // after execution of this method; this will cause an Invalid Token error on any subsequent request.
        // Since this token was put in to deal with an AppScan CSRF issue anyway (PO-6232), we don't need to
        // refresh the token value and thus will retain the "original" token value from the request.
        final HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession()
                .setAttribute(
                        TokenHelper
                                .buildTokenSessionAttributeName(STRUTS_TOKEN_POPUP),
                        request.getParameter(STRUTS_TOKEN_POPUP));
        setStateName(AddressUtil.fixState(getStateName(), getCountryName()));

        addActionErrors(AddressUtil.addressValidations(getOrgStAddress(), getCityName(), getStateName(),
                getZipCode(), getCountryName()));
        addActionErrors(AddressUtil.requiredField("Organization", getOrgName()));

        if (StringUtils.isNotBlank(getEmail()) && !PAUtil.isValidEmail(getEmail())) {
            addActionError("Email address is invalid");
        }

        if (StringUtils.isNotBlank(getUrl()) && !PAUtil.isCompleteURL(getUrl())) {
            addActionError("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/");
        }
        if (AddressUtil.usaOrCanada(getCountryName())) {
            validateUsCanadaPhoneNumber(getTelephone(), "phone");
            validateUsCanadaPhoneNumber(fax, "fax");
            validateUsCanadaPhoneNumber(tty, "TTY");
        }

        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            for (String actionErr : getActionErrors()) {
                sb.append(" - ").append(actionErr);
            }
            request.setAttribute(Constants.FAILURE_MESSAGE, sb.toString());
            return "create_org_response";
        }

        String result = null;
        try {
            result = updatePo();
        } catch (Exception e) {
            request.setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return result;
    }

    private void validateUsCanadaPhoneNumber(String number, String type) {
        String badPhoneMsg = "Valid USA/Canada %s numbers must match ###-###-####x#*, e.g. "
                + "555-555-5555 or 555-555-5555x123";
        if (StringUtils.isNotBlank(number) && !PAUtil.isUsOrCanadaPhoneNumber(number)) {
            addActionError(String.format(badPhoneMsg, type));
        }
    }

    private void addActionErrors(Collection<String> errors) {
        for (String error : errors) {
            addActionError(error);
        }
    }

    private String updatePo() throws URISyntaxException, EntityValidationException, CurationException,
            NullifiedEntityException {
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(getOrgName()));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, getCityName(), getStateName(),
                getZipCode(), getCountryName()));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        if (StringUtils.isNotBlank(getTelephone())) {
            Tel t = new Tel();
            t.setValue(new URI("tel", getTelephone(), null));
            telco.getItem().add(t);
        }
        if (StringUtils.isNotBlank(fax)) {
            Tel f = new Tel();
            f.setValue(new URI("x-text-fax", fax, null));
            telco.getItem().add(f);
        }
        if (StringUtils.isNotBlank(tty)) {
            Tel tt = new Tel();
            tt.setValue(new URI("x-text-tel", tty, null));
            telco.getItem().add(tt);
        }
        if (StringUtils.isNotBlank(url)) {
            TelUrl telurl = new TelUrl();
            telurl.setValue(new URI(url));
            telco.getItem().add(telurl);
        }
        if (StringUtils.isNotBlank(getEmail())) {
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + getEmail()));
            telco.getItem().add(telemail);
        }
        orgDto.setTelecomAddress(telco);
        Ii id = PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
        OrganizationDTO newOrg = PoRegistry.getOrganizationEntityService().getOrganization(id);
        convertPoOrganizationDTO(newOrg, null);
        return "create_org_response";
    }

    @SuppressWarnings("unchecked")
    private String processDisplayOrganizations(String retvalue) { // NOPMD
        
        if (isSearchCriteriaNotSet()) {
            String message = "Please enter at least one search criteria";
            orgs = null;
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return retvalue;
        }
        
        try {
            getCountriesList();
            criteria.setName(getOrgName());
            criteria.setFamilyName(getFamilyName());
            criteria.setCity(getCityName());
            criteria.setCountry(getCountryName());
            criteria.setZip(getZipCode());
            criteria.setState(getStateName());
            criteria.setCtepId(getCtepId());
            criteria.setIdentifier(getPoId());
            
            List<OrganizationDTO> orgList = PADomainUtils.searchPoOrganizations(getCriteria());
            
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            for (OrganizationDTO dto : orgList) {
                if (CollectionUtils.isNotEmpty(dto.getFamilyOrganizationRelationships().getItem())) {
                    famOrgRelIiList.addAll(dto.getFamilyOrganizationRelationships().getItem());
                }
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
            for (OrganizationDTO dto : orgList) {
                PaOrganizationDTO paDTO = PADomainUtils.convertPoOrganizationDTO(dto, getCountryList());
                paDTO.setFamilies(PADomainUtils.getFamilies(dto.getFamilyOrganizationRelationships(), familyMap));
                orgs.add(paDTO);
            }
            PADomainUtils.addOrganizationCtepIDs(orgs);
            Collections.sort(orgs, new Comparator<PaOrganizationDTO>() {
                @Override
                public int compare(PaOrganizationDTO o1, PaOrganizationDTO o2) {
                    return StringUtils.defaultString(o1.getName()).compareTo(
                            StringUtils.defaultString(o2.getName()));
                }
            });
            return retvalue;
        } catch (Exception e) {           
            orgs = null;
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,  e.getMessage());
            return retvalue;
        }
    }
    
    private boolean isSearchCriteriaNotSet() {
        return StringUtils.isBlank(getOrgName()) && StringUtils.isBlank(getFamilyName())
                && StringUtils.isBlank(getCityName()) && StringUtils.isBlank(getZipCode())
                && StringUtils.isBlank(getStateName()) && StringUtils.isBlank(getPoId())
                && StringUtils.isBlank(getCtepId()) && StringUtils.isBlank(getCountryName());
    }    

    @SuppressWarnings("unchecked")
    private void convertPoOrganizationDTO(OrganizationDTO poOrg, Map<Ii, FamilyDTO> familyMap) {
        Map<String, String> addrs = AddressConverterUtil.convertToAddressBo(poOrg.getPostalAddress());
        PaOrganizationDTO paOrg = new PaOrganizationDTO();
        paOrg.setCountry(addrs.get(AdxpCnt.class.getName()));
        paOrg.setZip(addrs.get(AdxpZip.class.getName()));
        paOrg.setCity(addrs.get(AdxpCty.class.getName()));
        paOrg.setState(addrs.get(AdxpSta.class.getName()));
        paOrg.setId(poOrg.getIdentifier().getExtension().toString());
        paOrg.setName(poOrg.getName().getPart().get(0).getValue());
        if (MapUtils.isNotEmpty(familyMap)) {
            paOrg.setFamilies(PADomainUtils.getFamilies(poOrg.getFamilyOrganizationRelationships(), familyMap));
        }
        orgs.clear();
        orgs.add(paOrg);
    }

    /**
     * @return the orgs
     */
    public List<PaOrganizationDTO> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<PaOrganizationDTO> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgStAddress
     */
    public String getOrgStAddress() {
        return orgStAddress;
    }

    /**
     * @param orgStAddress the orgStAddress to set
     */
    public void setOrgStAddress(String orgStAddress) {
        this.orgStAddress = orgStAddress;
    }

    /**
     * @return the tty
     */
    public String getTty() {
        return tty;
    }

    /**
     * @param tty the tty to set
     */
    public void setTty(String tty) {
        this.tty = tty;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the criteria
     */
    public OrganizationSearchCriteriaDTO getCriteria() {
        return criteria;
    }
}