/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Denis G. Krylov
 * 
 */
public class PersonSearchAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -455612586883087108L;

    private static final String DETAILS = "details";

    private static final Logger LOG = Logger
            .getLogger(PersonSearchAction.class);

    private final PersonSearchCriteriaDTO criteria = new PersonSearchCriteriaDTO();
    private List<PaPersonDTO> results;
    private PaPersonDTO person;
    private String personID;

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
     * @return res
     * @throws PAException
     *             exception
     */
    @SuppressWarnings("unchecked")
    public String query() throws PAException {   
        try {
            results = PADomainUtils.searchPoPersons(getCriteria());
            Collections.sort(results, new Comparator<PaPersonDTO>() {
                @Override
                public int compare(PaPersonDTO o1, PaPersonDTO o2) {
                    return StringUtils
                            .defaultString(o1.getLastName())
                            .compareTo(
                                    StringUtils.defaultString(o2.getLastName()));
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
     * @return String
     * @throws NullifiedEntityException NullifiedEntityException
     * @throws PAException PAException
     * @throws NullifiedRoleException NullifiedRoleException
     * @throws TooManyResultsException TooManyResultsException
     */
    public String showDetailspopup() throws NullifiedEntityException,
            PAException, NullifiedRoleException, TooManyResultsException {
        setPerson(PADomainUtils.getPersonDetailsPopup(personID));
        return DETAILS;
    }

    /**
     * @return the criteria
     */
    public PersonSearchCriteriaDTO getCriteria() {
        return criteria;
    }

    /**
     * @return the results
     */
    public List<PaPersonDTO> getResults() {
        return results;
    }

    /**
     * @param results
     *            the results to set
     */
    public void setResults(List<PaPersonDTO> results) {
        this.results = results;
    }

    /**
     * @return the person
     */
    public PaPersonDTO getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(PaPersonDTO person) {
        this.person = person;
    }

    /**
     * @return the personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * @param personID the personID to set
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

}
