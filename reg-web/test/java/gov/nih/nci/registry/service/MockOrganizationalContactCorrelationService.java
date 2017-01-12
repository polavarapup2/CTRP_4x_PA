/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockOrganizationalContactCorrelationService implements
        OrganizationalContactCorrelationServiceRemote {
    static List<OrganizationalContactDTO> list;
    static {
        list = new ArrayList<OrganizationalContactDTO>();
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        dto.setPlayerIdentifier(IiConverter.convertToIi("2"));
        Ii contactIi = IiConverter.convertToPoOrganizationalContactIi("1");
        contactIi.setReliability(IdentifierReliability.ISS);
        dto.setIdentifier(DSetConverter.convertIiToDset(contactIi ));
        dto.setScoperIdentifier(IiConverter.convertToIi("1"));
        dto.setTitle(StConverter.convertToSt("Title"));
        list.add(dto);
        dto = new OrganizationalContactDTO();
        dto.setPlayerIdentifier(IiConverter.convertToIi("3"));
        contactIi = IiConverter.convertToPoOrganizationalContactIi("2");
        contactIi.setReliability(IdentifierReliability.ISS);
        dto.setIdentifier(DSetConverter.convertIiToDset(contactIi));
        dto.setScoperIdentifier(IiConverter.convertToIi("2"));
        list.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException {

        if (arg0.getTitle().getValue().equalsIgnoreCase("TestEntityValidationException")) {
            throw new EntityValidationException(new HashMap<String, String[]>());
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    public OrganizationalContactDTO getCorrelation(Ii ii)
            throws NullifiedRoleException {
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        for (OrganizationalContactDTO contactDto : list) {
            Ii  contactIi = DSetConverter.convertToIi(contactDto.getIdentifier());
            if(!ISOUtil.isIiNull(ii) && !ISOUtil.isIiNull(contactIi)
                    && contactIi.getExtension().equals(ii.getExtension())) {
                dto = contactDto;
            }
        }

        return dto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090.Ii[])
     */
    public List<OrganizationalContactDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<OrganizationalContactDTO> search(OrganizationalContactDTO arg0) {
        List<OrganizationalContactDTO> returnList = new ArrayList<OrganizationalContactDTO>();
        for (OrganizationalContactDTO dto : list){
            if(dto.getScoperIdentifier().getExtension().equals(arg0.getScoperIdentifier().getExtension())) {
                returnList.add(dto);
            }
            if(arg0.getTitle() != null && arg0.getTitle().getValue() != null
                    && arg0.getTitle().getValue().equalsIgnoreCase("Test Create Title")) {
                return new ArrayList<OrganizationalContactDTO>();
            }
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelationStatus(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#validate(gov.nih.nci.services.PoDto)
     */
    public Map<String, String[]> validate(OrganizationalContactDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OrganizationalContactDTO> search(OrganizationalContactDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OrganizationalContactDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        return new ArrayList<OrganizationalContactDTO>();
    }

    @Override
    public Ii createActiveCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException, CurationException {       
        return IiConverter.convertToIi(1l);
    }

}
