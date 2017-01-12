/**
 * 
 */
package gov.nih.nci.pa.util.pomock;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author dkrylov
 * 
 */
// CHECKSTYLE:OFF
public final class MockFamilyService implements FamilyServiceRemote { // NOPMD

    public static int SEQ = 1; // NOPMD

    public static final Map<Long, FamilyDTO> FAMILIES = new HashMap<Long, FamilyDTO>();

    public static final Map<Long, FamilyOrganizationRelationshipDTO> RELATIONSHIPS = new HashMap<Long, FamilyOrganizationRelationshipDTO>();

    public static Ii NCI_FAMILY_ID;

    static {
        reset();
    }
    
    static List<FamilyDTO> familyList;
    static {
        familyList = new ArrayList<FamilyDTO>();
        FamilyDTO dto = new FamilyDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(EnOnConverter.convertToEnOn("Family1"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        familyList.add(dto);

        FamilyDTO dto2 = new FamilyDTO();
        
        dto2.setIdentifier(IiConverter.convertToIi(2L));
        dto2.setName(EnOnConverter.convertToEnOn("Family2"));
        dto2.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        familyList.add(dto2);
    }
    
    public FamilyDTO createFamily(FamilyDTO dto) {
        return create(dto);
    }

    /**
     * @param dto
     * @return
     */
    private static FamilyDTO create(FamilyDTO dto) {
        dto.setIdentifier(IiConverter.convertToPoFamilyIi((SEQ++) + ""));
        FAMILIES.put(IiConverter.convertToLong(dto.getIdentifier()), dto);
        return dto;
    }

    public static void reset() {
        SEQ = 1;
        FAMILIES.clear();
        RELATIONSHIPS.clear();

        
        FamilyDTO dto = new FamilyDTO();
        dto.setName(EnOnConverter.convertToEnOn("National Cancer Institute"));
        dto.setStatusCode(CdConverter.convertStringToCd("Active"));
        create(dto);
        NCI_FAMILY_ID = dto.getIdentifier();

    }

    public FamilyOrganizationRelationshipDTO createFamilyOrganizationRelationship(
            FamilyOrganizationRelationshipDTO dto) {
        dto.setIdentifier(IiConverter.convertToPoFamilyIi((SEQ++) + ""));
        RELATIONSHIPS.put(IiConverter.convertToLong(dto.getIdentifier()), dto);
        return dto;
    }

    public static FamilyOrganizationRelationshipDTO createRelatedFamilyOrganizationRelationship(
            FamilyOrganizationRelationshipDTO dto, FamilyDTO familyDTO) {
	
	familyDTO = create(familyDTO);
	
	dto.setFamilyIdentifier(familyDTO.getIdentifier());
	dto.setIdentifier(IiConverter.convertToPoFamilyIi(SEQ+++""));

	FAMILIES.put(IiConverter.convertToLong(familyDTO.getIdentifier()), familyDTO);
        RELATIONSHIPS.put(IiConverter.convertToLong(dto.getIdentifier()), dto);

        return dto;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.family.FamilyServiceRemote#getActiveRelationships
     * (java.lang.Long)
     */
    @Override
    public List<FamilyOrganizationRelationshipDTO> getActiveRelationships(
            Long familyId) {
        List<FamilyOrganizationRelationshipDTO> list = new ArrayList<>();
        for (FamilyOrganizationRelationshipDTO dto : RELATIONSHIPS.values()) {
            if (familyId.equals(IiConverter.convertToLong(dto
                    .getFamilyIdentifier()))) {
                list.add(dto);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.family.FamilyServiceRemote#getFamilies(java.util
     * .Set)
     */
    @Override
    public Map<Ii, FamilyDTO> getFamilies(Set<Ii> familyOrgRelationshipIis) {
        Map<Long, FamilyDTO> localMap = getLocalFamilies(getFamOrgRelIds(familyOrgRelationshipIis));
        Map<Ii, FamilyDTO> retMap = new LinkedHashMap<Ii, FamilyDTO>();
        for (Ii familyOrgRelationshipIi : familyOrgRelationshipIis) {
            Long extension = IiConverter.convertToLong(familyOrgRelationshipIi);
            familyOrgRelationshipIi = IiConverter.convertToPoFamilyIi(extension.toString());
            retMap.put(familyOrgRelationshipIi, (localMap.get(extension)));
        }
        return retMap;
    }

    private Map<Long, FamilyDTO> getLocalFamilies(
            Set<Long> familyOrgRelationshipIds) {
        Map<Long, FamilyDTO> retMap = new HashMap<Long, FamilyDTO>();
        if (CollectionUtils.isEmpty(familyOrgRelationshipIds)) {
            return retMap;
        }

        for (FamilyOrganizationRelationshipDTO relationship : this
                .getByIds(familyOrgRelationshipIds
                        .toArray(new Long[familyOrgRelationshipIds.size()]))) {
            retMap.put(IiConverter.convertToLong(relationship.getIdentifier()),
                    getFamily(relationship.getFamilyIdentifier()));
        }
        return retMap;
    }

    private List<FamilyOrganizationRelationshipDTO> getByIds(Long[] array) { // NOPMD
        List<FamilyOrganizationRelationshipDTO> list = new ArrayList<>();
        for (Long relID : array) {
            if (RELATIONSHIPS.containsKey(relID)) {
                list.add(RELATIONSHIPS.get(relID));
            }
        }
        return list;
    }

    private Set<Long> getFamOrgRelIds(Set<Ii> familyOrganizationRelationships) {
        Set<Long> famOrgRelIdList = new HashSet<Long>();
        for (Ii ii : familyOrganizationRelationships) {
            famOrgRelIdList.add(IiConverter.convertToLong(ii));
        }
        return famOrgRelIdList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.family.FamilyServiceRemote#getFamily(gov.nih.nci
     * .iso21090.Ii)
     */
    @Override
    public FamilyDTO getFamily(Ii arg0) {
        return FAMILIES.get(IiConverter.convertToLong(arg0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.family.FamilyServiceRemote#
     * getFamilyOrganizationRelationship(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public FamilyOrganizationRelationshipDTO getFamilyOrganizationRelationship(
            Ii arg0) {
        return RELATIONSHIPS.get(IiConverter.convertToLong(arg0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.family.FamilyServiceRemote#getP30Grant(java.lang
     * .Long)
     */
    @Override
    public FamilyP30DTO getP30Grant(Long arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.family.FamilyServiceRemote#search(gov.nih.nci.services
     * .family.FamilyDTO, gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<FamilyDTO> search(FamilyDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        List<FamilyDTO> families = new ArrayList<FamilyDTO>();
        if (arg0.getName() != null) {
            String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
            for(FamilyDTO dto : familyList){
                String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
                if(dtoName .equals(inputName)){
                    families.add(dto);
                }
            }
        }
        if (arg0.getIdentifier() != null && arg0.getIdentifier().getExtension() != null ) {
            for(FamilyDTO dto : familyList){
                if(dto.getIdentifier().getExtension().equals(arg0.getIdentifier().getExtension())){
                     families.add(dto);
                }
            }
        }
        if (arg0.getStatusCode() != null) {
            for(FamilyDTO dto : familyList){
                if(CdConverter.convertCdToString(dto.getStatusCode())
                     .equals(CdConverter.convertCdToString(arg0.getStatusCode()))) {
                     families.add(dto);
                }
            }
        }
        return families ;
    }

    @SuppressWarnings("unchecked")
    public void relate(OrganizationDTO org, FamilyDTO fam)
            throws EntityValidationException {
        FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
        rel.setFamilyIdentifier(fam.getIdentifier());
        rel.setOrgIdentifier(org.getIdentifier());
        rel.setFunctionalType(CdConverter.convertStringToCd("ORGANIZATIONAL"));
        rel = createFamilyOrganizationRelationship(rel);
        org.getFamilyOrganizationRelationships().getItem()
                .add(rel.getIdentifier());
        new MockOrganizationEntityService().updateOrganization(org);
    }

}
