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
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockHealthCareFacilityCorrelationService extends
        MockPoCorrelationService implements
        HealthCareFacilityCorrelationServiceRemote {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public Ii createCorrelation(HealthCareFacilityDTO dto)
            throws EntityValidationException, CurationException {
        return createRole(dto, IiConverter.HEALTH_CARE_FACILITY_ROOT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090
     * .Ii)
     */
    @Override
    public HealthCareFacilityDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        return (HealthCareFacilityDTO) STORE.get(IiConverter
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
    public List<HealthCareFacilityDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        List<HealthCareFacilityDTO> list = new ArrayList<HealthCareFacilityDTO>();
        for (Ii id : arg0) {
            HealthCareFacilityDTO dto = getCorrelation(id);
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
    public List<HealthCareFacilityDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<HealthCareFacilityDTO> list = new ArrayList<HealthCareFacilityDTO>();
        for (Ii playerID : arg0) {
            for (HealthCareFacilityDTO dto : getRolesInStore(HealthCareFacilityDTO.class)) {
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
    public List<HealthCareFacilityDTO> search(HealthCareFacilityDTO arg0) {
       return super.searchOrganizationRole(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto
     * , gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<HealthCareFacilityDTO> search(HealthCareFacilityDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        return super.searchOrganizationRole(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public void updateCorrelation(HealthCareFacilityDTO arg0)
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
    public Map<String, String[]> validate(HealthCareFacilityDTO arg0) {
        return null;
    }

    

}
