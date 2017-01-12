package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings("unchecked")
public class MockPoOrganizationEntityService implements
        OrganizationEntityServiceRemote {
    final private static Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
    private final static Map<String, String[]> familyOrgMap;

    static List<OrganizationDTO> orgDtoList;
    static {
        orgDtoList = new ArrayList<OrganizationDTO>();
        OrganizationDTO dto = basicOrgDto("abc");
        List<String> phones = new ArrayList<String>();
        phones.add("7037071111");
        phones.add("7037071112");
        phones.add("7037071113");
        DSet<Tel> master = new DSet<Tel>();
        dto.setTelecomAddress(DSetConverter.convertListToDSet(phones, "PHONE",master));
        orgDtoList.add(dto);
        
        orgDtoList.add(basicOrgDto("abc1"));
        orgDtoList.add(basicOrgDto("1"));
        orgDtoList.add(basicOrgDto("584"));
        orgDtoList.add(basicOrgDto("22"));
        familyOrgMap = new HashMap<String, String[]>();
        familyOrgMap.put("family1", new String[] {"22", "1"});
        familyOrgMap.put("family2", new String[] {"abc"});
        OrganizationDTO orgDto = basicOrgDto("2");
        orgDto.setStatusCode(CdConverter.convertStringToCd("NULLIFIED"));  
        orgDto.setName(EnOnConverter.convertToEnOn("IsNullified"));
        orgDtoList.add(orgDto);
        
        nullifiedEntities.put(IiConverter.convertToPoOrganizationIi("2"), IiConverter.convertToPoOrganizationIi("22"));
        
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
    public OrganizationDTO getOrganization(Ii id) throws NullifiedEntityException {
        if (NullFlavor.NA.equals(id.getNullFlavor())) {
            throw new NullifiedEntityException(nullifiedEntities);
        }

        for (OrganizationDTO dto : orgDtoList) {
            if (dto.getIdentifier().getExtension().equals(id.getExtension())) {
                if (dto.getStatusCode().getCode().equals("NULLIFIED")) {
                    throw new NullifiedEntityException(nullifiedEntities);
                }
                return dto;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    public List<OrganizationDTO> search(OrganizationDTO orgDto) {
        List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        if (orgDto.getIdentifier() != null) {
            for (OrganizationDTO dto:orgDtoList){
                if (dto.getIdentifier().getExtension().equals(orgDto.getIdentifier().getExtension())){
                    matchingDtosList.add(dto);
                }
            }
        }
        if (orgDto.getName()!= null) {
            String inputName = EnOnConverter.convertEnOnToString(orgDto.getName());
            for (OrganizationDTO dto:orgDtoList){
                String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
                if (dtoName .equals(inputName)){
                    matchingDtosList.add(dto);
                }
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

    public List<OrganizationDTO> search(OrganizationDTO orgDto, LimitOffset lmOff)
            throws TooManyResultsException {
    	List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        if (orgDto.getIdentifier() != null) {
            for (OrganizationDTO dto:orgDtoList){
                if (dto.getIdentifier().getExtension().equals(orgDto.getIdentifier().getExtension())){
                    matchingDtosList.add(dto);
                }
            }
        }
        if (orgDto.getName()!= null ) {
            String inputName = EnOnConverter.convertEnOnToString(orgDto.getName());
            for (OrganizationDTO dto:orgDtoList){
                String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
                if (dtoName .equals(inputName)){
                    matchingDtosList.add(dto);
                }
            }
        }
        
        int fromIndex = (lmOff.getOffset() < 0 ? 0 : lmOff.getOffset());
        int toIndex = Math.min(fromIndex + lmOff.getLimit(), matchingDtosList.size());
        
        try {
        	matchingDtosList = matchingDtosList.subList(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) { // fromIndex > toIndex
        	matchingDtosList.clear();  // return empty list
        }
        
        if (matchingDtosList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        
        return matchingDtosList ;
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

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0, EnOn arg1, LimitOffset arg2)
            throws TooManyResultsException {
        return search(arg0, arg2);
    }

    @Override
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
        DSet<Ii> dset = new DSet<Ii>();
        Set<Ii> familySet = new HashSet<Ii>();
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        dset.setItem(familySet);
        dto.setFamilyOrganizationRelationships(dset);
        orgDtoList.add(dto);
        return orgDtoList;
    }

   
    public Ii getDuplicateOfNullifiedOrg(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
