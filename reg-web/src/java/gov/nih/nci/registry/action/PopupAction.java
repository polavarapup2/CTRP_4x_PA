/***
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AddressUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Harsha
 */
public class PopupAction extends ActionSupport implements Preparable {
    private static final String CREATE_ORG_RESPONSE = "create_org_response";
    private static final long serialVersionUID = -4295358782987888548L;
    private static final String FAILURE_MSG_ATTR = "failureMessage";
    private static final String ORGS_RESULT = "orgs";
    private List<Country> countryList = new ArrayList<Country>();
    private List<PaOrganizationDTO> paOrgs = new ArrayList<PaOrganizationDTO>();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<PaPersonDTO> persons = new ArrayList<PaPersonDTO>();
    
    private final OrganizationSearchCriteriaDTO orgSearchCriteria = new OrganizationSearchCriteriaDTO();
    
    private OrgSearchCriteria createOrg = new OrgSearchCriteria();
    private PaPersonDTO personDTO = new PaPersonDTO();
    private final PersonSearchCriteriaDTO personSearchCriteria = new PersonSearchCriteriaDTO();
    private static final String PERS_CREATE_RESPONSE = "create_pers_response";

    private static final Logger LOG  = Logger.getLogger(PopupAction.class);

    private String poId;
    private String ctepId;
    
    //Person attributes
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String state;
    private String zip;
    
    private String streetAddr;
    private String preFix;
    private String suffix;
    private String midName;
    private String phone;


    //Organization attribute;
    private String orgName;
    private String familyName;
    private String orgStAddress;
    private String countryName;
    private String cityName;
    private String stateName;
    private String zipCode;

    private String phoneNumber;
    private String tty;
    private String fax;
    private String url;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     *
     * @return String success or failure
     */
    public String lookuppersons() {
        persons = null;
        return "persons";
    }

    /**
     *
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            orgs = null;
            paOrgs = null;
            populateCountryList();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return ORGS_RESULT;
    }

    /**
     * 
     * @return s
     */
    public String addSummaryFourOrg() {     
        try {
            BaseTrialDTO trialDTO =  (BaseTrialDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE); 
            String orgId = ServletActionContext.getRequest().getParameter("orgId");
            String chosenName = ServletActionContext.getRequest().getParameter("chosenName");
            SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
            summarySp.setRowId(UUID.randomUUID().toString());
            summarySp.setOrgId(orgId);
            summarySp.setOrgName(chosenName);
            if (trialDTO == null) {
                trialDTO = new TrialDTO();
                trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
                ServletActionContext.getRequest().getSession()
                .setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);
            } else if (!trialDTO.getSummaryFourOrgIdentifiers().contains(summarySp)) {
                trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
            } else if (trialDTO.getSummaryFourOrgIdentifiers().contains(summarySp)) {
                addFieldError("summary4FundingSponsor", "Selected Sponsor already exists for this trial");
            }
        } catch (Exception e) {
            LOG.error("Exception occured while adding SummaryFourOrg " + e);
            addFieldError("summary4FundingSponsor", e.getMessage());
        }
        return "summaryFourOrg";
    }
    
    /**
     * @return string
     */
    public String deleteSummaryFourOrg() {
        BaseTrialDTO trialDTO =  (BaseTrialDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE);
        String uuId = ServletActionContext.getRequest().getParameter("uuid");
        for (int i = trialDTO.getSummaryFourOrgIdentifiers().size() - 1; i >= 0; i--) {
            SummaryFourSponsorsWebDTO webDto = trialDTO.getSummaryFourOrgIdentifiers().get(i);
            if (webDto.getRowId().equals(uuId)) {
                trialDTO.getSummaryFourOrgIdentifiers().remove(i);
            }
        }
        return "summaryFourOrg";
    }

    /**
     *
     * @return result
     */
    public String displayOrgList() {
        return populateOrgs(false);
    }

    /**
     * @throws PAException on error
     * @return res
     */
    public String displayPersonsListDisplayTag() throws PAException {
        return populatePersons(true);
    }

    /**
     * @throws PAException on error
     * @return res
     */
    public String displayPersonsList() throws PAException {
        return populatePersons(false);
    }

    /**
     * @return results
     */
    public String displayPasswordReset() {
        return "displayPasswordReset";
    }

    private String populatePersons(boolean pagination) throws PAException { // NOPMD
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (isPersonCriterionEmpty()) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            request.setAttribute(FAILURE_MSG_ATTR, message);
            return SUCCESS;
        }
        try {
            if (StringUtils.isNotBlank(getPoId()) || StringUtils.isNotBlank(getCtepId())) {
                personSearchCriteria.setId(getPoId());
                personSearchCriteria.setCtepId(getCtepId());
                persons = PADomainUtils.searchPoPersons(getPersonSearchCriteria());
            } else {
                PersonDTO p = new PersonDTO();
                p.setName(EnPnConverter.convertToEnPn(getFirstName(), null, getLastName(), null, null));
                p.setPostalAddress(AddressConverterUtil.create(null, null, getCity(), getState(), getZip(),
                        getCountry()));
                if (StringUtils.isNotBlank(getEmail())) {
                    List<String> emailList = new ArrayList<String>();
                    emailList.add(getEmail());
                    p.setTelecomAddress(DSetConverter.convertListToDSet(emailList, "EMAIL", p.getTelecomAddress()));
                }
                persons = PADomainUtils.searchPoPersons(p);
            }
            if (persons != null && !persons.isEmpty()) {
                Collections.sort(persons, new Comparator<PaPersonDTO>() {
                    @Override
                    public int compare(PaPersonDTO o1, PaPersonDTO o2) {
                        return StringUtils
                                .defaultString(o1.getLastName())
                                .compareTo(
                                        StringUtils.defaultString(o2.getLastName()));
                    }
                });
            }            
        } catch (Exception e) {
            persons = null;
            LOG.error("Error occured while searching PO Persons " + e.getMessage(), e);
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, e.getMessage());
            return pagination ? "persons" : SUCCESS;
        }
        return pagination ? "persons" : SUCCESS;
    }

    private boolean isPersonCriterionEmpty() {
        return StringUtils.isEmpty(firstName)  && StringUtils.isEmpty(lastName)  && StringUtils.isEmpty(email)
                && StringUtils.isEmpty(ctepId) && StringUtils.isEmpty(city) && StringUtils.isEmpty(zip)
                && StringUtils.isEmpty(state)  && StringUtils.isEmpty(poId) && StringUtils.isEmpty(country);
    }

    /**
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return populateOrgs(true);
    }

    @SuppressWarnings("unchecked")
    private String populateOrgs(boolean pagination) { // NOPMD
        try {
            if (isOrgCriterionEmpty()) {
                String message = "Please enter at least one search criteria";
                paOrgs = null;
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, message);
                return pagination ? ORGS_RESULT : SUCCESS;
            }
            
            orgSearchCriteria.setName(getOrgName());
            orgSearchCriteria.setFamilyName(getFamilyName());
            orgSearchCriteria.setCity(getCityName());
            orgSearchCriteria.setCountry(getCountryName());
            orgSearchCriteria.setState(getStateName());
            orgSearchCriteria.setZip(getZipCode());
            orgSearchCriteria.setCtepId(getCtepId());
            orgSearchCriteria.setIdentifier(getPoId());
            
            List<OrganizationDTO> orgList = PADomainUtils.searchPoOrganizations(getOrgSearchCriteria());

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
                paDTO.setP30GrantSerialNumber(FamilyHelper.getP30GrantSerialNumber(
                        IiConverter.convertToLong(dto.getIdentifier())));
                paOrgs.add(paDTO);
            }
            
            PADomainUtils.addOrganizationCtepIDs(paOrgs);
            
            Collections.sort(paOrgs, new Comparator<PaOrganizationDTO>() {
                @Override
                public int compare(PaOrganizationDTO o1, PaOrganizationDTO o2) {
                    return StringUtils.defaultString(o1.getName()).compareTo(
                            StringUtils.defaultString(o2.getName()));
                }
            });
            
            return pagination ? ORGS_RESULT : SUCCESS;
        } catch (Exception e) {
            paOrgs = null;
            orgs = null;
            LOG.error("Error occurred while searching PO Organizations", e);
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, e.getMessage());
            return pagination ? ORGS_RESULT : SUCCESS;
        }
    }

    private boolean isOrgCriterionEmpty() {
        return StringUtils.isEmpty(orgName) && StringUtils.isEmpty(countryName) && StringUtils.isEmpty(cityName)
                && StringUtils.isEmpty(zipCode)  && StringUtils.isEmpty(getCtepId()) && StringUtils.isEmpty(getPoId()) 
                && StringUtils.isEmpty(familyName) && StringUtils.isEmpty(stateName);
    }

    private String getCountryNameUsingCode(String code) {
        for (Country c : countryList) {
            if (c.getAlpha3().toString().equals(code)) {
                return c.getName();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void populateCountryList() throws PAException {
        countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = PaRegistry.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String createOrganization() throws PAException {
        stateName = AddressUtil.fixState(stateName, countryName);

        addActionErrors(AddressUtil.requiredField("Organization", orgName));
        addActionErrors(AddressUtil.addressValidations(orgStAddress, cityName, stateName, zipCode, countryName));

        if (StringUtils.isNotEmpty(email) && !PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }

        if (StringUtils.isNotBlank(url) && !PAUtil.isCompleteURL(url)) {
            addActionError("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/");
        }
        validatePhoneNumber(getPhoneNumber(), "phone", getCountryName());
        validatePhoneNumber(getFax(), "fax", getCountryName());
        validatePhoneNumber(getTty(), "TTY", getCountryName());
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            for (String actionErr : getActionErrors()) {
                sb.append(" - ").append(actionErr);
            }
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, sb.toString());
            return CREATE_ORG_RESPONSE;
        }

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        try {
            if (getPhoneNumber() != null && getPhoneNumber().length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", getPhoneNumber(), null));
                telco.getItem().add(t);
            }
            if (getFax() != null && getFax().length() > 0) {
                Tel f = new Tel();
                f.setValue(new URI("x-text-fax", getFax(), null));
                telco.getItem().add(f);
            }
            if (getTty() != null && getTty().length() > 0) {
                Tel tt = new Tel();
                tt.setValue(new URI("x-text-tel", getTty(), null));
                telco.getItem().add(tt);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                telco.getItem().add(telurl);
            }
            if (StringUtils.isNotEmpty(email)) {
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                telco.getItem().add(telemail);
            }
            orgDto.setTelecomAddress(telco);
            Ii id = PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert.add(PoRegistry.getOrganizationEntityService().getOrganization(id));
            convertPoOrganizationDTO(callConvert, null);
            
            // Load up paOrg list.
            for (OrganizationDTO dto : callConvert) {
                PaOrganizationDTO paDTO = PADomainUtils.convertPoOrganizationDTO(dto, getCountryList());                
                paOrgs.add(paDTO);
            }            
            PADomainUtils.addOrganizationCtepIDs(paOrgs);            
            
        } catch (NullifiedEntityException e) {
            handleError(e);
        } catch (URISyntaxException e) {
            handleError(e);
        } catch (EntityValidationException e) {
            handleError(e);
        } catch (CurationException e) {
            handleError(e);
        } catch (NullifiedRoleException e) {
            handleError(e);
        }
        return CREATE_ORG_RESPONSE;
    }

    private String handleError(Exception exception) {
        LOG.error(ExceptionUtils.getFullStackTrace(exception));
        return handleError(exception.getMessage());
    }

    private String handleError(String message) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, message);
        return CREATE_ORG_RESPONSE;
    }

    /**
     *
     * @return String rest
     */
    public String createPerson() {
        final HttpServletRequest request = ServletActionContext.getRequest();

        state = AddressUtil.fixState(state, country);
        addActionErrors(AddressUtil.requiredField("First Name", firstName));
        addActionErrors(AddressUtil.requiredField("Last Name", lastName));

        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(getPhone())) {
            addActionError("Either an email address or a phone is required");
        }
        if (StringUtils.isNotEmpty(email) && !PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }

        addActionErrors(AddressUtil.addressValidations(streetAddr, city, state, zip, country));

        if (StringUtils.isNotBlank(getUrl()) && !PAUtil.isCompleteURL(getUrl())) {
            addActionError("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/");
        }
        validatePhoneNumber(getPhone(), "phone", getCountry());
        validatePhoneNumber(getFax(), "fax", getCountry());
        validatePhoneNumber(getTty(), "TTY", getCountry());
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            for (String actionErr : getActionErrors()) {
                sb.append(" - ").append(actionErr);
            }
            request.setAttribute(FAILURE_MSG_ATTR, sb.toString());
            return PERS_CREATE_RESPONSE;
        }

        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middle name exists stick it in here!
        if (StringUtils.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.GIV);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);
        if (StringUtils.isNotEmpty(preFix)) {
            Enxp partPfx = new Enxp(EntityNamePartType.PFX);
            partPfx.setValue(preFix);
            dto.getName().getPart().add(partPfx);
        }
        if (StringUtils.isNotEmpty(suffix)) {
            Enxp partSfx = new Enxp(EntityNamePartType.SFX);
            partSfx.setValue(suffix);
            dto.getName().getPart().add(partSfx);
        }
       // dto.getName().getPart().add(part);
        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
        try {
            if (phone != null && phone.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phone, null));
                list.getItem().add(t);
            }
            if (fax != null && fax.length() > 0) {
                Tel faxTel = new Tel();
                faxTel.setValue(new URI("x-text-fax", fax, null));
                list.getItem().add(faxTel);
            }
            if (tty != null && tty.length() > 0) {
                Tel ttyTel = new Tel();
                ttyTel.setValue(new URI("x-text-tel", tty, null));
                list.getItem().add(ttyTel);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                list.getItem().add(telurl);
            }
            if (StringUtils.isNotBlank(email)) {
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
            }
            dto.setTelecomAddress(list);
            dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
            dto.setStatusCode(new PAServiceUtils().isAutoCurationEnabled() ? CdConverter
                    .convertStringToCd("ACTIVE") : CdConverter
                    .convertStringToCd(null));
            Ii id = PoRegistry.getPersonEntityService().createPerson(dto);
            persons.add(PADomainUtils.convertToPaPersonDTO(PoRegistry.getPersonEntityService().getPerson(id)));
        } catch (Exception e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        }
        return PERS_CREATE_RESPONSE;
    }

    private void validatePhoneNumber(String number, String type, String cnt) {
        if (StringUtils.isBlank(number)) {
            return;
        }
        String badUsaMsg = "Valid USA/Canada %s numbers must match ###-###-####x#*, e.g. "
                + "555-555-5555 or 555-555-5555x123";
        String badOtherMsg = "The %s number is invalid";
        if (!PAUtil.isValidPhone(number)) {
            addActionError(String.format(AddressUtil.usaOrCanada(cnt) ? badUsaMsg : badOtherMsg, type));
        } else {
            if (AddressUtil.usaOrCanada(cnt) && !PAUtil.isUsOrCanadaPhoneNumber(number)) {
                addActionError(String.format(badUsaMsg, type));
            }
        }
    }

    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute(FAILURE_MSG_ATTR, message);
        return returnString;
    }

    @SuppressWarnings("unchecked")
    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos, Map<Ii, FamilyDTO> familyMap) {
        SearchOrgResultDisplay displayElement = null;
        for (OrganizationDTO poOrgDto : poOrgDtos) {
            displayElement = new SearchOrgResultDisplay();
            displayElement.setId(poOrgDto.getIdentifier().getExtension().toString());
            displayElement.setName(poOrgDto.getName().getPart().get(0).getValue());
            //
            AddressPartType type = null;
            for (Adxp part : poOrgDto.getPostalAddress().getPart()) {
                type = part.getType();
                if (type.name().equals("CNT")) {
                    displayElement.setCountry(getCountryNameUsingCode(part.getCode()));
                }
                if (type.name().equals("ZIP")) {
                    displayElement.setZip(part.getValue());
                }
                if (type.name().equals("CTY")) {
                    displayElement.setCity(part.getValue());
                }
                if (type.name().equals("STA")) {
                    displayElement.setState(part.getValue());
                }
            }
            if (MapUtils.isNotEmpty(familyMap)) {
                displayElement.setFamilies(getFamilies(poOrgDto.getFamilyOrganizationRelationships(), familyMap));
            }
            orgs.add(displayElement);
        }
    }

    private Map<Long, String> getFamilies(DSet<Ii> familyOrganizationRelationships, Map<Ii, FamilyDTO> familyMap) {
        Map<Long, String> retMap = new HashMap<Long, String>();
        Set<Ii> famOrgIis = familyOrganizationRelationships.getItem();
        for (Ii ii : famOrgIis) {
            FamilyDTO dto = familyMap.get(ii);
            retMap
                    .put(IiConverter.convertToLong(dto.getIdentifier()), EnOnConverter.convertEnOnToString(dto
                            .getName()));
        }
        return retMap;
    }

    private void addActionErrors(Collection<String> errors) {
        for (String error : errors) {
            addActionError(error);
        }
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the persons
     */
    public List<PaPersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PaPersonDTO> persons) {
        this.persons = persons;
    }

    /**
     * @return the createOrg
     */
    public OrgSearchCriteria getCreateOrg() {
        return createOrg;
    }

    /**
     * @param createOrg the createOrg to set
     */
    public void setCreateOrg(OrgSearchCriteria createOrg) {
        this.createOrg = createOrg;
    }

    /**
     * @return the personDTO
     */
    public PaPersonDTO getPersonDTO() {
        return personDTO;
    }

    /**
     * @param personDTO the personDTO to set
     */
    public void setPersonDTO(PaPersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     *
     * @param ctepId the ctepId
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

    /**
     * @return the org name
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the org name
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return cityName the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the city
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return state name
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the state name
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return org street address
     */
    public String getOrgStAddress() {
        return orgStAddress;
    }

    /**
     * @param orgStAddress the org street name
     */
    public void setOrgStAddress(String orgStAddress) {
        this.orgStAddress = orgStAddress;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return tty
     */
    public String getTty() {
        return tty;
    }

    /**
     * @param tty the tty
     */
    public void setTty(String tty) {
        this.tty = tty;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the street address
     */
    public String getStreetAddr() {
        return streetAddr;
    }

    /**
     * @param streetAddr the street address
     */
    public void setStreetAddr(String streetAddr) {
        this.streetAddr = streetAddr;
    }

    /**
     * @return the prefix
     */
    public String getPreFix() {
        return preFix;
    }

    /**
     * @param preFix the prefix
     */
    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return midName the middle name
     */
    public String getMidName() {
        return midName;
    }

    /**
     * @param midName the middle name
     */
    public void setMidName(String midName) {
        this.midName = midName;
    }

    /**
     * @return phone the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the poId
     */
    public String getPoId() {
        return poId;
    }

    /**
     * @param poId the poId to set
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }

    /**
     * @return the orgSearchCriteria
     */
    public OrganizationSearchCriteriaDTO getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @return the paOrgs
     */
    public List<PaOrganizationDTO> getPaOrgs() {
        return paOrgs;
    }

    /**
     * @param paOrgs the paOrgs to set
     */
    public void setPaOrgs(List<PaOrganizationDTO> paOrgs) {
        this.paOrgs = paOrgs;
    }

    /**
     * @return the personSearchCriteria
     */
    public PersonSearchCriteriaDTO getPersonSearchCriteria() {
        return personSearchCriteria;
    }
}
