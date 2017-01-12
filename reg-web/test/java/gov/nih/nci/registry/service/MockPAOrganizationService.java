/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockPAOrganizationService implements PAOrganizationServiceRemote {
    static List<Organization> orgList;
    static {
        orgList = new ArrayList<Organization>();
        Organization dto = new Organization();
        dto.setIdentifier("1");
        dto.setId(1L);
        dto.setName("OrgName");
        orgList.add(dto);
        
        dto = new Organization();
        dto.setIdentifier("3");
        dto.setId(3L);
        dto.setName("NCI");
        orgList.add(dto);
        
    }
    
    public static List<PaOrganizationDTO> leadPaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
    public static List<PaOrganizationDTO> sitePaOrganizationDTOs = new ArrayList<PaOrganizationDTO>();
    public static List<PaOrganizationDTO> organizationDTOs = new ArrayList<PaOrganizationDTO>();
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationByIndetifers(gov.nih.nci.pa.domain.Organization)
     */
    @Override
    public Organization getOrganizationByIndetifers(Organization organization)
            throws PAException {
        for (Organization org: orgList) {
            if( org.getIdentifier().equals(organization.getIdentifier())) {
                return org;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationsAssociatedWithStudyProtocol(java.lang.String)
     */
    @Override
    public List<PaOrganizationDTO> getOrganizationsAssociatedWithStudyProtocol(
            String organizationType) throws PAException {
        if ("Lead Organization".equals(organizationType)) {
            return leadPaOrganizationDTOs;
        }
        return sitePaOrganizationDTOs;
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.PAOrganizationServiceRemote#getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(gov.nih.nci.pa.domain.Organization)
     */
    @Override
    public List<PaOrganizationDTO> getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol( String organizationType, String organizationTerm)
            throws PAException {
        return organizationDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getOrganizationIdsByNames(List<String> names) throws PAException {
        return null;
    }

    
    @Override
    public List<Organization> getOrganizationsWithUserAffiliations()
            throws PAException {       
        return orgList;
    }  

}
