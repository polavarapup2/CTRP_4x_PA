package gov.nih.nci.registry.action;

import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author vinodh.rc
 * @since Apr 24, 2014
 */
public class OrganizationsJSONAction extends ActionSupport {

    private static final long serialVersionUID = -8316184779353387405L;
    private Map<String, String> organizationDtos;

    /**
     * @return result
     */
    public String getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol() {
        String organizationType = (String) (ServletActionContext.getRequest()
                .getParameter("organizationType"));
        String organizationTerm = (String) (ServletActionContext.getRequest()
                .getParameter("organizationTerm"));
        setOrganizationDtos(new TreeMap<String, String>());
        if (StringUtils.isEmpty(organizationType)
                || StringUtils.isEmpty(organizationTerm)) {
            return SUCCESS;
        }
        organizationTerm = StringEscapeUtils.escapeSql(organizationTerm);
        List<PaOrganizationDTO> orgDtos;
        try {
            if ("Both".equalsIgnoreCase(organizationType)) {

                orgDtos = new ArrayList<PaOrganizationDTO>();
                orgDtos.addAll(PaRegistry
                        .getCachingPAOrganizationService()
                        .getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(
                                "Lead Organization", organizationTerm));
                orgDtos.addAll(PaRegistry
                        .getCachingPAOrganizationService()
                        .getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(
                                "Participating Site", organizationTerm));

            } else {

                orgDtos = PaRegistry
                        .getCachingPAOrganizationService()
                        .getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(
                                organizationType, organizationTerm);

            }
        } catch (PAException e) {
            LOG.error("Error calling organization service", e);
            orgDtos = new ArrayList<PaOrganizationDTO>();
        }
        
        Collection<String> orgNms = CollectionUtils.collect(orgDtos, 
                    TransformerUtils.invokerTransformer("getName"));
        for (PaOrganizationDTO orgDto : orgDtos) {
            if (Collections.frequency(orgNms, orgDto.getName()) > 1) {
                String poOrgIdOrCtepId = null;
                try {
                    poOrgIdOrCtepId = PADomainUtils.getCtepId(orgDto
                            .getIdentifier());
                } catch (PAException e) {
                    LOG.error("Error getting ctepId", e);
                }
                poOrgIdOrCtepId = poOrgIdOrCtepId == null ? orgDto
                        .getIdentifier() : poOrgIdOrCtepId;
                organizationDtos.put(String.format("%s (%s)", orgDto.getName(),
                        poOrgIdOrCtepId), orgDto.getId());
            } else {
                organizationDtos.put(orgDto.getName(), orgDto.getId());
            }
        }
        
        return SUCCESS;
    }

    /**
     * 
     * @return organizationDtos
     */
    public Map<String, String> getOrganizationDtos() {
        return organizationDtos;
    }

    /**
     * set organizationDtos
     * 
     * @param organizationDtos organizationDtos
     */
    public void setOrganizationDtos(Map<String, String> organizationDtos) {
        this.organizationDtos = organizationDtos;
    }

}
