package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * This action class manages the Organization contact(s).
 *
 * @author Harsha
 *
 */
public class OrganizationContactAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1L;
    private static final String DISPLAY_ORG_CONTACTS = "display_org_contacts";
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();
    private List<Country> countryList = new ArrayList<Country>();
    private String personName;
    private String orgContactIdentifier;

    /**
     * @return the orgContactIdentifier
     */
    public String getOrgContactIdentifier() {
        return orgContactIdentifier;
    }

    /**
     * @param orgContactIdentifier the orgContactIdentifier to set
     */
    public void setOrgContactIdentifier(String orgContactIdentifier) {
        this.orgContactIdentifier = orgContactIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     *
     * @return res
     */
    public String getOrganizationContacts() {
        try {
            if (orgContactIdentifier != null && orgContactIdentifier.equals("undefined")) {
                return DISPLAY_ORG_CONTACTS;
            }
            OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
            contactDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(orgContactIdentifier));
            List<OrganizationalContactDTO> list = PoRegistry.getOrganizationalContactCorrelationService()
                    .search(contactDTO);
            for (OrganizationalContactDTO organizationalContactDTO : list) {
                try {
                    persons.add(PoRegistry.getPersonEntityService().getPerson(
                            organizationalContactDTO.getPlayerIdentifier()));
                } catch (NullifiedEntityException e) {
                    addActionError(e.getMessage());
                    ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
                    LOG.error("NullifiedEntityException occured while getting organization contact : " + e);
                    return DISPLAY_ORG_CONTACTS;
                }
            }
        } catch (Exception e) {
            //addActionError(e.getMessage());
            //ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Exception occured while getting organization contact : " + e);
            return DISPLAY_ORG_CONTACTS;
        }
        return DISPLAY_ORG_CONTACTS;
    }

    /**
     * @return the persons
     */
    public List<PersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
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

    @SuppressWarnings("unchecked")
    private void populateCountryList() throws PAException {
        countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = PaRegistry.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
