package gov.nih.nci.registry.action;

import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author vinodh.rc
 * @since Apr 24, 2014
 */
public class PersonsJSONAction extends ActionSupport {

    private static final long serialVersionUID = -8316184779353387405L;
    private Map<String, String> paPersonDTOs;

    /**
     * @return result
     */
    public String getPrincipalInvestigatorsByNameAssociatedWithStudyProtocol() {
        String personTerm = (String) (ServletActionContext.getRequest()
                .getParameter("personTerm"));
        setPaPersonDTOs(new TreeMap<String, String>());

        List<PaPersonDTO> personDtos;
        try {
            personDtos = PaRegistry.getCachingPAPersonService()
                    .getAllPrincipalInvestigatorsByName(personTerm);
        } catch (PAException e) {
            LOG.error("Error calling Person service", e);
            personDtos = new ArrayList<PaPersonDTO>();
        }

        for (PaPersonDTO personDto : personDtos) {
            getPaPersonDTOs().put(personDto.getFullName(), String.valueOf(personDto.getId()));
        }
        return SUCCESS;
    }

    /**
     * Returns person DTOs
     * 
     * @return paPersonDtos
     */
    public Map<String, String> getPaPersonDTOs() {
        return paPersonDTOs;
    }

    /**
     * Sets person DTOs
     * 
     * @param paPersonDTOs paPersonDTOs
     */
    public void setPaPersonDTOs(Map<String, String> paPersonDTOs) {
        this.paPersonDTOs = paPersonDTOs;
    }

}
