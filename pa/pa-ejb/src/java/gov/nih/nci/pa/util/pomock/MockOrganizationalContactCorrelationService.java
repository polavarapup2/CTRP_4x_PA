/**
 * 
 */
package gov.nih.nci.pa.util.pomock;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockOrganizationalContactCorrelationService extends
        MockPoCorrelationService implements
        OrganizationalContactCorrelationServiceRemote {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public Ii createCorrelation(OrganizationalContactDTO dto)
            throws EntityValidationException, CurationException {
        return createRole(dto, IiConverter.ORGANIZATIONAL_CONTACT_ROOT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090
     * .Ii)
     */
    @Override
    public OrganizationalContactDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        return (OrganizationalContactDTO) STORE.get(IiConverter
                .convertToString(arg0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090
     * .Ii[])
     */
    @Override
    public List<OrganizationalContactDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        List<OrganizationalContactDTO> list = new ArrayList<OrganizationalContactDTO>();
        for (Ii id : arg0) {
            OrganizationalContactDTO dto = getCorrelation(id);
            if (dto != null) {
                list.add(dto);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelationsByPlayerIds(gov
     * .nih.nci.iso21090.Ii[])
     */
    @Override
    public List<OrganizationalContactDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<OrganizationalContactDTO> list = new ArrayList<OrganizationalContactDTO>();
        for (Ii playerID : arg0) {
            for (OrganizationalContactDTO dto : getRolesInStore(OrganizationalContactDTO.class)) {
                if (playerID.getExtension().equalsIgnoreCase(
                        dto.getPlayerIdentifier().getExtension())) {
                    list.add(dto);
                }
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto
     * )
     */
    @Override
    public List<OrganizationalContactDTO> search(OrganizationalContactDTO arg0) {
        try {
            return search(arg0, new LimitOffset(1, 0));
        } catch (TooManyResultsException e) {
            //nothing to do
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto
     * , gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<OrganizationalContactDTO> search(OrganizationalContactDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        List<OrganizationalContactDTO> list = new ArrayList<OrganizationalContactDTO>();
        for (OrganizationalContactDTO dto : getRolesInStore(OrganizationalContactDTO.class)) {
            if (arg0.getPlayerIdentifier().getExtension().equalsIgnoreCase(
                    dto.getPlayerIdentifier().getExtension())
                 && arg0.getScoperIdentifier().getExtension().equalsIgnoreCase(
                         dto.getScoperIdentifier().getExtension())) {
                list.add(dto);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public void updateCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException {
        STORE.put(IiConverter.convertToString(DSetConverter.convertToIi(arg0
                .getIdentifier())), arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelationStatus(gov.nih
     * .nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    @Override
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        throw new UnsupportedOperationException();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#validate(gov.nih.nci.services
     * .PoDto)
     */
    @Override
    public Map<String, String[]> validate(OrganizationalContactDTO arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.services.correlation.
     * ClinicalResearchStaffCorrelationServiceRemote
     * #createActiveCorrelation(gov.
     * nih.nci.services.correlation.ClinicalResearchStaffDTO)
     */
    @Override
    public Ii createActiveCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException, CurationException {
        return createCorrelation(arg0);
    }

}
