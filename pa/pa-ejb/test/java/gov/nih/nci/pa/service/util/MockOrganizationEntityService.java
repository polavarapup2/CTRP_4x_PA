/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
public class MockOrganizationEntityService implements
        OrganizationEntityServiceRemote {

    public static int PO_ID_SEQ = 100;

    public static final Map<String, OrganizationDTO> STORE = new HashMap<String, OrganizationDTO>();

    public static final Map<String, String> PO_ID_TO_CTEP_ID = new HashMap<String, String>();
    
    public static long CT_GOV_ID;
    
    static {
        OrganizationDTO ctgov = new OrganizationDTO();
        ctgov.setName(EnOnConverter.convertToEnOn(PAConstants.CTGOV_ORG_NAME));
        try {
            createOrg(ctgov);
            CT_GOV_ID = IiConverter.convertToLong(ctgov.getIdentifier());
        } catch (Exception e) {          
            e.printStackTrace();
        } 
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
    private static Ii createOrg(OrganizationDTO orgDTO) {
        final String poOrgId = (PO_ID_SEQ++) + "";
        final Ii id = IiConverter.convertToPoOrganizationIi(poOrgId);
        orgDTO.setIdentifier(id);
        orgDTO.setStatusCode(CdConverter.convertToCd(EntityStatusCode.PENDING));
        final Ad ad = new Ad();
        ad.setPart(new ArrayList<Adxp>());
        orgDTO.setPostalAddress(ad);

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
        return new ArrayList<OrganizationDTO>();
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
        criteria.setName(EnOnConverter.convertEnOnToString(orgDTO.getName()));
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

    @Override
    public Ii getDuplicateOfNullifiedOrg(String arg0) {       
        return null;
    }

}
