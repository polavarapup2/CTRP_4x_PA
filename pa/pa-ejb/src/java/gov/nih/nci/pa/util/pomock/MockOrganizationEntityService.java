/**
 * 
 */
package gov.nih.nci.pa.util.pomock;

import static gov.nih.nci.pa.util.pomock.MockFamilyService.NCI_FAMILY_ID;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockOrganizationEntityService implements
        OrganizationEntityServiceRemote { // NOPMD

    public static int PO_ID_SEQ; // NOPMD

    public static final Map<String, OrganizationDTO> STORE = new HashMap<String, OrganizationDTO>();

    public static final Map<String, String> PO_ID_TO_CTEP_ID = new HashMap<String, String>();

    public static long CT_GOV_ID; // NOPMD

    public static long CTEP_ID; // NOPMD

    public static long DCP_ID; // NOPMD

    static {
        reset(1, true);
    }

    /**
     * @param startingCounter
     */
    public static final void reset(int startingCounter, boolean createROs) {
        MockResearchOrganizationCorrelationService.reset(startingCounter);
        PO_ID_SEQ = startingCounter;
        STORE.clear();
        PO_ID_TO_CTEP_ID.clear();
        createInitialOrgs(createROs);
    }

    /**
     * 
     */
    private static void createInitialOrgs(boolean createROs) {
        try {
            OrganizationDTO org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn(PAConstants.CTGOV_ORG_NAME));
            createOrg(org);
            if (createROs)
                createRO(org, "RSB");
            CT_GOV_ID = IiConverter.convertToLong(org.getIdentifier());

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn(PAConstants.CTEP_ORG_NAME));
            createOrg(org);
            if (createROs)
                createRO(org, "RSB");
            CTEP_ID = IiConverter.convertToLong(org.getIdentifier());
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()), "CTEP");
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(NCI_FAMILY_ID));

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn(PAConstants.DCP_ORG_NAME));
            createOrg(org);
            if (createROs)
                createRO(org, "RSB");
            DCP_ID = IiConverter.convertToLong(org.getIdentifier());
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()), "DCP");
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(NCI_FAMILY_ID));

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn(PAConstants.NCI_ORG_NAME));
            createOrg(org);
            if (createROs)
                createRO(org, "CCR");
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()), "NCI");
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(NCI_FAMILY_ID));

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn(PAConstants.CCR_ORG_NAME));
            createOrg(org);
            if (createROs)
                createRO(org, "RSB");
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()), "CCR");

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("CT.Gov Dupe"));
            createOrg(org);
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()),
                    "CTGOVDUPE");
            org.setStatusCode(CdConverter
                    .convertToCd(EntityStatusCode.NULLIFIED));
            
            //create org with double quote 
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("Double \" quotes\""));
            createOrg(org);
            
            //create org with single quote 
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("single \' quotes\'"));
            createOrg(org);
            

            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("SWOG"));
            createOrg(org);
            PO_ID_TO_CTEP_ID.put(
                    IiConverter.convertToString(org.getIdentifier()),
                    "SWOG");
            org.setStatusCode(CdConverter
                    .convertToCd(EntityStatusCode.ACTIVE));

            //create org with Family 2
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("Case Western Reserve University"));
            createOrg(org);
            FamilyDTO dto = new FamilyDTO();
            dto.setName(EnOnConverter.convertToEnOn("Case Comprehensive Cancer Center"));
            dto.setStatusCode(CdConverter.convertStringToCd("Active"));
            FamilyDTO newFamily = new MockFamilyService().createFamily(dto);
            
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(newFamily.getIdentifier()));
            
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("Case Comprehensive Cancer Center"));
            createOrg(org);
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(newFamily.getIdentifier()));
            
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("Cleveland Clinic Foundation"));
            createOrg(org);
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(newFamily.getIdentifier()));
            
            // Program codes
            FamilyDTO familyOnedto = new FamilyDTO();
            familyOnedto.setName(EnOnConverter.convertToEnOn("Family One"));
            familyOnedto.setStatusCode(CdConverter.convertStringToCd("Active"));
            FamilyDTO familyOne = new MockFamilyService().createFamily(familyOnedto);
            
            FamilyDTO familyTwodto = new FamilyDTO();
            familyTwodto.setName(EnOnConverter.convertToEnOn("Family Two"));
            familyTwodto.setStatusCode(CdConverter.convertStringToCd("Active"));
            FamilyDTO familyTwo = new MockFamilyService().createFamily(familyTwodto);
            
            org = new OrganizationDTO();
            org.setName(EnOnConverter.convertToEnOn("Multi Family Org"));
            createOrg(org);
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(familyOne.getIdentifier()));
            
            new MockFamilyService().relate(org,
                    new MockFamilyService().getFamily(familyTwo.getIdentifier()));
            
        } catch (Exception e) {
            e.printStackTrace(); // NOPMD
        }
    }

    private static void createRO(OrganizationDTO org, String roType)
            throws EntityValidationException, CurationException {
        ResearchOrganizationDTO dto = new ResearchOrganizationDTO();
        dto.setName(org.getName());
        dto.setPlayerIdentifier(org.getIdentifier());
        dto.setTypeCode(CdConverter.convertStringToCd(roType));
        new MockResearchOrganizationCorrelationService().createCorrelation(dto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#
     * createOrganization(gov.nih.nci.services.organization.OrganizationDTO)
     */
    @Override
    public Ii createOrganization(OrganizationDTO orgDTO)
            throws EntityValidationException, CurationException {
        return createOrg(orgDTO);
    }

    /**
     * @param orgDTO
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Ii createOrg(OrganizationDTO orgDTO) {
        final String poOrgId = (PO_ID_SEQ++) + "";
        final Ii id = IiConverter.convertToPoOrganizationIi(poOrgId);
        orgDTO.setIdentifier(id);
        orgDTO.setStatusCode(CdConverter.convertToCd(EntityStatusCode.PENDING));

        final Ad ad = getAddress();
        if (orgDTO.getPostalAddress() == null) {
            orgDTO.setPostalAddress(ad);
        }
        if (orgDTO.getTelecomAddress() == null) {
            orgDTO.setTelecomAddress(getTelAdd());
        }

        final DSet fams = new DSet();
        fams.setItem(new HashSet());
        orgDTO.setFamilyOrganizationRelationships(fams);

        STORE.put(poOrgId, orgDTO);

        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#
     * getOrganization(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public OrganizationDTO getOrganization(Ii ii)
            throws NullifiedEntityException {

        String poOrgId = ii.getExtension();
        OrganizationDTO dto = STORE.get(poOrgId);
        if (dto == null) {
            return null;
        }
        if (CdConverter.convertCdToEnum(EntityStatusCode.class,
                dto.getStatusCode()) == EntityStatusCode.NULLIFIED) {
            throw new NullifiedEntityException(ii,
                    IiConverter.convertToPoOrganizationIi(CT_GOV_ID + "")); // NOPMD
        }
        return dto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search
     * (gov.nih.nci.services.organization.OrganizationDTO)
     */
    @Override
    public List<OrganizationDTO> search(OrganizationDTO arg0) {
        try {
            return search(arg0, new LimitOffset(500, 0));
        } catch (TooManyResultsException e) {
            e.printStackTrace(); // NOPMD
            throw new RuntimeException(e); // NOPMD
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search
     * (gov.nih.nci.services.organization.OrganizationDTO,
     * gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<OrganizationDTO> search(OrganizationDTO orgDTO, LimitOffset arg1)
            throws TooManyResultsException {
        OrganizationSearchCriteriaDTO criteria = new OrganizationSearchCriteriaDTO();
        if (orgDTO.getName() != null) {
            criteria.setName(EnOnConverter.convertEnOnToString(orgDTO.getName()));
        }
        if (!ISOUtil.isIiNull(orgDTO.getIdentifier())) {
            criteria.setIdentifier(IiConverter.convertToString(orgDTO
                    .getIdentifier()));
        }
        return search(criteria, arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search
     * (gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO,
     * gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<OrganizationDTO> search(OrganizationSearchCriteriaDTO c,
            LimitOffset lim) throws TooManyResultsException {
        List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
        for (Map.Entry<String, OrganizationDTO> entry : STORE.entrySet()) {
            OrganizationDTO org = entry.getValue();
            String poid = entry.getKey();
            boolean match = true;
            if (StringUtils.isNotBlank(c.getName())) {
                match = match
                        && EnOnConverter.convertEnOnToString(org.getName())
                                .toLowerCase()
                                .contains(c.getName().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getCtepId())) {
                match = match
                        && PO_ID_TO_CTEP_ID.get(poid) != null
                        && PO_ID_TO_CTEP_ID.get(poid).toLowerCase()
                                .contains(c.getCtepId().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getIdentifier())) {
                match = match && poid.equals(c.getIdentifier());
            }
            if (StringUtils.isNotBlank(c.getCity())) {
                match = match
                        &&

                        AddressConverterUtil
                                .convertToAddressBo(org.getPostalAddress())
                                .get(AdxpCty.class.getName()).toLowerCase()
                                .contains(c.getCity().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getZip())) {
                match = match
                        &&

                        AddressConverterUtil
                                .convertToAddressBo(org.getPostalAddress())
                                .get(AdxpZip.class.getName()).toLowerCase()
                                .contains(c.getZip().toLowerCase());
            }
            if (StringUtils.isNotBlank(c.getCountry())) {
                match = match
                        && c.getCountry().equals(
                                AddressConverterUtil.convertToAddressBo(
                                        org.getPostalAddress()).get(
                                        AdxpCnt.class.getName()));
            }
            if (match) {
                list.add(org);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search
     * (gov.nih.nci.services.organization.OrganizationDTO,
     * gov.nih.nci.iso21090.EnOn, gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<OrganizationDTO> search(OrganizationDTO arg0, EnOn arg1,
            LimitOffset arg2) throws TooManyResultsException {
        return search(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#
     * updateOrganization(gov.nih.nci.services.organization.OrganizationDTO)
     */
    @Override
    public void updateOrganization(OrganizationDTO orgDTO)
            throws EntityValidationException {
        String poOrgId = orgDTO.getIdentifier().getExtension();
        STORE.put(poOrgId, orgDTO);

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#
     * updateOrganizationStatus(gov.nih.nci.iso21090.Ii,
     * gov.nih.nci.iso21090.Cd)
     */
    @Override
    public void updateOrganizationStatus(Ii ii, Cd cd)
            throws EntityValidationException {
        String poOrgId = ii.getExtension();
        OrganizationDTO dto = STORE.get(poOrgId);
        if (dto == null) {
            return;
        }
        dto.setStatusCode(cd);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.organization.OrganizationEntityServiceRemote#validate
     * (gov.nih.nci.services.organization.OrganizationDTO)
     */
    @Override
    public Map<String, String[]> validate(OrganizationDTO arg0) {
        return new HashMap<String, String[]>();
    }

    /**
     * @return
     */
    private static Ad getAddress() {
        return AddressConverterUtil.create("street", "deliv", "city", "MD",
                "20000", "USA");
    }

    /**
     * 
     * @return
     */
    private static DSet<Tel> getTelAdd() {
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        try {
            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:test@example.com"));
            telSet.add(email);
            TelPhone phone = new TelPhone();
            phone.setValue(new URI("tel:111-222-3333"));
            telSet.add(phone);
            TelUrl url = new TelUrl();
            url.setValue(new URI("http://example.com"));
            telSet.add(url);
        } catch (URISyntaxException e) {
        }
        telAd.setItem(telSet);
        return telAd;
    }

    @Override
    public Ii getDuplicateOfNullifiedOrg(String ctepID) {
        if ("CTGOVDUPE".equalsIgnoreCase(ctepID)) {
            return IiConverter.convertToPoOrganizationIi(CT_GOV_ID + ""); // NOPMD
        }
        return IiConverter.convertToPoOrganizationIi(null);
    }

}
