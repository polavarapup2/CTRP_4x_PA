/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 * 
 */
public class OrganizationSearchAction extends ActionSupport {

    private static final String DETAILS = "details";

    private static final Logger LOG = Logger
            .getLogger(OrganizationSearchAction.class);

    private final OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();
    private List<PaOrganizationDTO> results;
    private String orgID;
    private PaOrganizationDTO organization;

    /**
     * 
     */
    private static final long serialVersionUID = 2260338015874010239L;

    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        return SUCCESS;
    }

    /**
     * Show organization details.
     * 
     * @return result
     * @throws NullifiedEntityException NullifiedEntityException
     * @throws PAException PAException
     * @throws NullifiedRoleException NullifiedRoleException
     * @throws TooManyResultsException TooManyResultsException
     */
    public String showDetailspopup() throws NullifiedEntityException, PAException,  
    NullifiedRoleException, TooManyResultsException {     
        setOrganization(PADomainUtils.getOrgDetailsPopup(orgID));
        return DETAILS;
    }

    /**
     * @return res
     * @throws PAException
     *             exception
     */
    @SuppressWarnings("unchecked")
    public String query() throws PAException {    
        try {
            List<Country> countryList = PaRegistry.getLookUpTableService()
                    .getCountries();
            List<OrganizationDTO> orgList = PADomainUtils
                    .searchPoOrganizations(getCriteria());

            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            for (OrganizationDTO dto : orgList) {
                if (CollectionUtils.isNotEmpty(dto
                        .getFamilyOrganizationRelationships().getItem())) {
                    famOrgRelIiList.addAll(dto
                            .getFamilyOrganizationRelationships().getItem());
                }
            }

            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService()
                    .getFamilies(famOrgRelIiList);
            results = new ArrayList<PaOrganizationDTO>();
            for (OrganizationDTO dto : orgList) {
                PaOrganizationDTO paDTO = PADomainUtils
                        .convertPoOrganizationDTO(dto, countryList);
                paDTO.setFamilies(PADomainUtils.getFamilies(
                        dto.getFamilyOrganizationRelationships(), familyMap));
                results.add(paDTO);
            }
            PADomainUtils.addOrganizationCtepIDs(results);
            Collections.sort(results, new Comparator<PaOrganizationDTO>() {
                @Override
                public int compare(PaOrganizationDTO o1, PaOrganizationDTO o2) {
                    return StringUtils.defaultString(o1.getName()).compareTo(
                            StringUtils.defaultString(o2.getName()));
                }
            });
            return SUCCESS;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage", e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }

    /**
     * @return the criteria
     */
    public OrganizationSearchCriteriaDTO getCriteria() {
        return criteria;
    }

    /**
     * @return the results
     */
    public List<PaOrganizationDTO> getResults() {
        return results;
    }

    /**
     * @return the orgID
     */
    public String getOrgID() {
        return orgID;
    }

    /**
     * @param orgID
     *            the orgID to set
     */
    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    /**
     * @return the organization
     */
    public PaOrganizationDTO getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(PaOrganizationDTO organization) {
        this.organization = organization;
    }

}
