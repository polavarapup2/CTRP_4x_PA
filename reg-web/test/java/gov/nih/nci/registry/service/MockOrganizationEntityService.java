/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.pomock.MockFamilyService;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockOrganizationEntityService implements
        OrganizationEntityServiceRemote {
    static List<OrganizationDTO> orgDtoList;
    static {
        orgDtoList = new ArrayList<OrganizationDTO>();
        OrganizationDTO dto = new OrganizationDTO();
        dto.setFamilyOrganizationRelationships(new DSet<Ii>());
        dto.getFamilyOrganizationRelationships().setItem(new HashSet<Ii>());
        
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("code"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        
       orgDtoList.add(dto);
        
        /*
         * Created family and family organization dto's to make a relationship.
         */
        
        OrganizationDTO dto2 = new OrganizationDTO();
        
        dto2.setIdentifier(IiConverter.convertToIi("2"));
        dto2.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto2.setStatusCode(CdConverter.convertStringToCd("code"));
        dto2.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        
        FamilyDTO familyDto = new FamilyDTO();
        familyDto.setName(EnOnConverter.convertToEnOn("National Cancer Institute"));
        familyDto.setStatusCode(CdConverter.convertStringToCd("Active"));
        FamilyOrganizationRelationshipDTO famOrgDTO = new FamilyOrganizationRelationshipDTO();
        famOrgDTO.setOrgIdentifier(dto2.getIdentifier());
        famOrgDTO = MockFamilyService.createRelatedFamilyOrganizationRelationship(famOrgDTO, familyDto);

        DSet<Ii> dset = new DSet<Ii>();
        Set<Ii> item = new HashSet<Ii>();
        item.add(famOrgDTO.getIdentifier());
        dset.setItem(item);
        
        dto2.setFamilyOrganizationRelationships(dset);
        
        
        
        orgDtoList.add(dto2);
    }
    /**
     * {@inheritDoc}
     */
    public Ii createOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToIi("1");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationDTO getOrganization(Ii arg0)
            throws NullifiedEntityException {
        for(OrganizationDTO dto:orgDtoList){
            if(dto.getIdentifier().getExtension().equals(arg0.getExtension())){
                return dto;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0) {
        List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
        for(OrganizationDTO dto:orgDtoList){
            String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
            if(dtoName .equals(inputName)){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList ;
    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganizationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> validate(OrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OrganizationDTO> search(OrganizationDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        if (arg0.getName() != null) {
            String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
            for(OrganizationDTO dto:orgDtoList){
                String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
                if(dtoName .equals(inputName)){
                    matchingDtosList.add(dto);
                }
            }
        }
        if (arg0.getIdentifier() != null && arg0.getIdentifier().getExtension() != null ) {
            for(OrganizationDTO dto:orgDtoList){
                if(dto.getIdentifier().getExtension().equals(arg0.getIdentifier().getExtension())){
                    matchingDtosList.add(dto);
                }
            }
        }
        return matchingDtosList ;

    }

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0, EnOn arg1, LimitOffset arg2)
            throws TooManyResultsException {
        return this.search(arg0, arg2);
    }

   
    public List<OrganizationDTO> search(OrganizationSearchCriteriaDTO criteria,
            LimitOffset lo) throws TooManyResultsException {       
        List<OrganizationDTO> orgDtoList = new ArrayList<OrganizationDTO>();
        OrganizationDTO dto = basicOrgDto("4648");
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        DSet<Tel> master = new DSet<Tel>();
        dto.setTelecomAddress(DSetConverter.convertListToDSet(phones, "PHONE",master));
        orgDtoList.add(dto);
        return orgDtoList;
    }

    private static OrganizationDTO basicOrgDto(String id) {
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setFamilyOrganizationRelationships(new DSet<Ii>());
        orgDto.getFamilyOrganizationRelationships().setItem(new HashSet<Ii>());
        orgDto.setIdentifier(IiConverter.convertToPoOrganizationIi(id));
        orgDto.setName(EnOnConverter.convertToEnOn("OrgName"));
        orgDto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        orgDto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine",
                "cityOrMunicipality", "stateOrProvince", "postalCode", "USA"));
        return orgDto;
    }

   
    public Ii getDuplicateOfNullifiedOrg(String arg0) {   
        return null;
    }

}
