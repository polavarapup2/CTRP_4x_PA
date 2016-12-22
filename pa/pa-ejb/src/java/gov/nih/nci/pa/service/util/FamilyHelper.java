package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for family functionality.
 * @author Hugh Reinhart
 * @since Nov 29, 2012
 */
public final class FamilyHelper {

    /**
     * Get a list of all families to which organization belongs.
     * @param orgId the PO organization id.
     * @return list of families
     * @throws PAException exception
     */
    public static List<OrgFamilyDTO> getByOrgId(Long orgId) throws PAException {
        return getByOrgId(orgId, null);
    }

    /**
     * Get a list of all families to which organization belongs with a given functional type.
     * @param orgId the PO organization id.
     * @param relationship the type of organizational relationship, null to get all
     * @return list of families
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public static List<OrgFamilyDTO> getByOrgId(Long orgId, FamilyFunctionalType relationship) throws PAException {
        List<OrgFamilyDTO> result = new ArrayList<OrgFamilyDTO>();
        if (orgId == null) {
            return result;
        }
        OrganizationDTO orgCriteria = new OrganizationDTO();
        orgCriteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().search(orgCriteria, limit);
            if (CollectionUtils.isEmpty(orgList)) {
                return result;
            }
            OrganizationDTO org = orgList.get(0);
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            if (CollectionUtils.isNotEmpty(org.getFamilyOrganizationRelationships().getItem())) {
                if (relationship == null) {
                    famOrgRelIiList.addAll(org.getFamilyOrganizationRelationships().getItem());
                } else {
                    for (Ii ii : (Set<Ii>) org.getFamilyOrganizationRelationships().getItem()) {
                        FamilyOrganizationRelationshipDTO forDto = 
                                PoRegistry.getFamilyService().getFamilyOrganizationRelationship(ii);
                        String famOrgRelType = CdConverter.convertCdToString(forDto.getFunctionalType());
                        if (StringUtils.equalsIgnoreCase(relationship.name(), famOrgRelType)) {
                            famOrgRelIiList.add(ii);
                        }
                    }
                }
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
            for (FamilyDTO dto : familyMap.values()) {
                result.add(convert(dto));
            }
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        return result;
    }

    /**
     * Get all sibling organizations associated with the family.
     * @param familyPoId the PO id of family
     * @return list of sibling PO organization ids
     * @throws PAException exception
     */
    public static List<Long> getRelatedOrgsInFamily(Long familyPoId) throws PAException {

        List<Long> result = new ArrayList<Long>();
        List<FamilyOrganizationRelationshipDTO> rList = PoRegistry.getFamilyService()
                .getActiveRelationships(familyPoId);
        for (FamilyOrganizationRelationshipDTO r : rList) {
            result.add(IiConverter.convertToLong(r.getOrgIdentifier()));
        }
        return result;
    }

    /**
     * Get all sibling organizations.
     * @param orgId the PO organization id
     * @return list of sibling PO organization ids
     * @throws PAException exception
     */
    public static List<Long> getAllRelatedOrgs(Long orgId) throws PAException {
        List<OrgFamilyDTO> families = getByOrgId(orgId);
        Set<Long> result = new HashSet<Long>();
        for (OrgFamilyDTO family : families) {
           result.addAll(getRelatedOrgsInFamily(family.getId()));
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get all sibling organizations.
     * @param orgIds list of PO organization ids
     * @return list of sibling PO organization ids
     * @throws PAException exception
     */
    public static List<Long> getAllRelatedOrgs(Collection<Long> orgIds)throws PAException {
        Set<Long> result = new HashSet<Long>();
        for (Long orgId : orgIds) {
            result.addAll(getAllRelatedOrgs(orgId));
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get the P30 grant serial number for the Cancer Center associated with the org. 
     * @param orgId PO organization id
     * @return grant serial number or null if not an organizational member of a Cancer Center family
     * @throws PAException exception
     */
    public static String getP30GrantSerialNumber(Long orgId) throws PAException {
        String result = null;
        List<OrgFamilyDTO> families = getByOrgId(orgId, FamilyFunctionalType.ORGANIZATIONAL);
        if (!families.isEmpty()) {
            for (OrgFamilyDTO family : families) {
                FamilyP30DTO p30 = PoRegistry.getFamilyService().getP30Grant(family.getId());
                if (p30 != null) {
                    result = EnOnConverter.convertEnOnToString(p30.getSerialNumber());
                    break;
                }
                
            }
        }
        return result; 
    }
    
    /**
     * Get site admins for Po Org
     * @param poOrgId Po Org Id
     * @return Set<RegistryUser> List of site admins
     * @throws PAException exception
     */
    public static Set<RegistryUser> getSiteAdmins(Long poOrgId) throws PAException {
        Set<RegistryUser> siteAdmins = new HashSet<RegistryUser>();
        List<RegistryUser> users =  PaRegistry.getRegistryUserService().findByAffiliatedOrg(poOrgId);
           for (RegistryUser registryUser : users) {
               if (UserOrgType.ADMIN == registryUser.getAffiliatedOrgUserType()) {
                   siteAdmins.add(registryUser);
               }
           }
        return siteAdmins;
    }
    
    /**
     * Get the cancer centers for an poOrg
     * @param orgPoId PoId for the organization
     * @return List<ResearchOrganizationDTO> List of cancer center orgs
     * @throws NullifiedRoleException exception thrown if any
     * @throws PAException exception thrown if any
     */
    public static List<ResearchOrganizationDTO> getCancerCenters(Long orgPoId) 
            throws NullifiedRoleException, PAException {
        List<ResearchOrganizationDTO> result = new ArrayList<ResearchOrganizationDTO>();
        
        if (orgPoId == null) {
            return result;
        }
        List<Long> allFamilyMembersPoIds = FamilyHelper.getAllRelatedOrgs(orgPoId);
        if (allFamilyMembersPoIds == null || allFamilyMembersPoIds.isEmpty()) {
            return result;
        }
        
        Ii[] iis = new Ii[allFamilyMembersPoIds.size()];
        for (int i = 0; i < allFamilyMembersPoIds.size(); i++) {
            iis[i] = IiConverter.convertToPoOrganizationIi(allFamilyMembersPoIds.get(i).toString());
        }
        
        List<ResearchOrganizationDTO> roList = PoRegistry
                .getResearchOrganizationCorrelationService()
                .getCorrelationsByPlayerIds(iis);
        for (ResearchOrganizationDTO ro : roList) {
            if (StringUtils.equalsIgnoreCase(
                    CdConverter.convertCdToString(ro.getTypeCode()), "CCR")) {
                result.add(ro);
            }
        }
        return result;
    }
    
    /**
     * Get the cancer center admins for a poOrgId
     * @param poOrgId poOrg Id
     * @return Set<RegistryUser> List of cancer center admins for a poOrgId
     * @throws NullifiedRoleException exception thrown if any
     * @throws PAException exception thrown if any
     */
    public static Set<RegistryUser> getCancerCenterAdmins(Long poOrgId) 
            throws NullifiedRoleException, PAException {
        List<ResearchOrganizationDTO> roList = getCancerCenters(poOrgId);
        Set<RegistryUser> admins = new HashSet<RegistryUser>();
        
        for (ResearchOrganizationDTO roDTO : roList) {
            List<RegistryUser> users =  
                    PaRegistry.getRegistryUserService().findByAffiliatedOrg(
                    IiConverter.convertToLong(roDTO.getPlayerIdentifier()));
            
            for (RegistryUser registryUser : users) {
                if (UserOrgType.ADMIN == registryUser.getAffiliatedOrgUserType()) {
                    admins.add(registryUser);
                }
            }
        }
        return admins;
    }
    
    /**
     * Get all families from PO
     * @return List<OrgFamilyDTO> List of all families
     * @throws PAException exception thrown if any
     */
    public static List<OrgFamilyDTO> getAllFamilies() throws PAException {

        FamilyDTO  familySearchCriteria = new FamilyDTO();
        familySearchCriteria.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrgFamilyDTO> orgFamilyDTOList = new ArrayList<OrgFamilyDTO>();

        try {
            List<FamilyDTO> familyList = PoRegistry.getFamilyService().search(familySearchCriteria, limit);
            if (!CollectionUtils.isEmpty(familyList)) {
                for (FamilyDTO family : familyList) {
                    orgFamilyDTOList.add(convert(family));
                }
            }
            return orgFamilyDTOList;
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
    }
    /**
     * Get family from PO using familyPOID
     * @param familyPoID familyPoID
     * @return FamilyDTO FamilyDTO
     */
    public static FamilyDTO getPOFamilyByPOID(Long familyPoID) {
        return PoRegistry.getFamilyService().getFamily(IiConverter.convertToPoFamilyIi(familyPoID.toString()));
    }

    /**
     * Will transform FamilyDTO from PA to OrgFamilyDTO in PA.
     * @param familyDTO
     * @return
     */
    private static OrgFamilyDTO convert(FamilyDTO familyDTO) {
        OrgFamilyDTO orgFamilyDto = new OrgFamilyDTO();
        orgFamilyDto.setId(IiConverter.convertToLong(familyDTO.getIdentifier()));
        orgFamilyDto.setName(EnOnConverter.convertEnOnToString(familyDTO.getName()));
        return orgFamilyDto;
    }
}
