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
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockHealthCareProviderCorrelationService extends
        MockPoCorrelationService implements
        HealthCareProviderCorrelationServiceRemote {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public Ii createCorrelation(HealthCareProviderDTO dto)
            throws EntityValidationException, CurationException {
        return createRole(dto, IiConverter.HEALTH_CARE_PROVIDER_ROOT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090
     * .Ii)
     */
    @Override
    public HealthCareProviderDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        return (HealthCareProviderDTO) STORE.get(IiConverter
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
    public List<HealthCareProviderDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        List<HealthCareProviderDTO> list = new ArrayList<HealthCareProviderDTO>();
        for (Ii id : arg0) {
            HealthCareProviderDTO dto = getCorrelation(id);
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
    public List<HealthCareProviderDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<HealthCareProviderDTO> list = new ArrayList<HealthCareProviderDTO>();
        for (Ii playerID : arg0) {
            for (HealthCareProviderDTO dto : getRolesInStore(HealthCareProviderDTO.class)) {
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
    public List<HealthCareProviderDTO> search(HealthCareProviderDTO arg0) {
        return searchPersonRole(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto
     * , gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<HealthCareProviderDTO> search(HealthCareProviderDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        return searchPersonRole(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public void updateCorrelation(HealthCareProviderDTO arg0)
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
    public Map<String, String[]> validate(HealthCareProviderDTO arg0) {
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
    public Ii createActiveCorrelation(HealthCareProviderDTO arg0)
            throws EntityValidationException, CurationException {
        return createCorrelation(arg0);
    }

}
