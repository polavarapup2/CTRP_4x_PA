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
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public class MockResearchOrganizationCorrelationService extends
        MockPoCorrelationService implements
        ResearchOrganizationCorrelationServiceRemote {

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public Ii createCorrelation(ResearchOrganizationDTO dto)
            throws EntityValidationException, CurationException {
        return createRole(dto, IiConverter.RESEARCH_ORG_ROOT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090
     * .Ii)
     */
    @Override
    public ResearchOrganizationDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        return (ResearchOrganizationDTO) STORE.get(IiConverter
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
    public List<ResearchOrganizationDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        List<ResearchOrganizationDTO> list = new ArrayList<ResearchOrganizationDTO>();
        for (Ii id : arg0) {
            ResearchOrganizationDTO dto = getCorrelation(id);
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
    public List<ResearchOrganizationDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<ResearchOrganizationDTO> list = new ArrayList<ResearchOrganizationDTO>();
        for (Ii playerID : arg0) {
            for (ResearchOrganizationDTO dto : getRolesInStore(ResearchOrganizationDTO.class)) {
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
    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO arg0) { // NOPMD
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
    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        return this.search(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public void updateCorrelation(ResearchOrganizationDTO arg0)
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
    public Map<String, String[]> validate(ResearchOrganizationDTO arg0) {
        return null;
    }

   

}
