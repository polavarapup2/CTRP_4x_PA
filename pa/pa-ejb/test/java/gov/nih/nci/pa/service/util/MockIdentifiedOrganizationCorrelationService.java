/**
 * 
 */
package gov.nih.nci.pa.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * @author Denis G. Krylov
 *
 */
public class MockIdentifiedOrganizationCorrelationService implements
        IdentifiedOrganizationCorrelationServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    @Override
    public Ii createCorrelation(IdentifiedOrganizationDTO arg0)
            throws EntityValidationException, CurationException {        
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public IdentifiedOrganizationDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090.Ii[])
     */
    @Override
    public List<IdentifiedOrganizationDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {        
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelationsByPlayerIds(gov.nih.nci.iso21090.Ii[])
     */
    @Override
    public List<IdentifiedOrganizationDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {   
        List<IdentifiedOrganizationDTO> list = new ArrayList<IdentifiedOrganizationDTO>();
        for (Ii ii: arg0) {
            String poid = ii.getExtension();
            String ctepId = MockOrganizationEntityService.PO_ID_TO_CTEP_ID.get(poid);
            if (StringUtils.isNotBlank(ctepId)) {
                IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO();
                Ii ctepIi = new Ii();
                ctepIi.setExtension(ctepId);
                ctepIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
                ctepIi.setIdentifierName(IiConverter.CTEP_ORG_IDENTIFIER_NAME);
                dto.setAssignedId(ctepIi);
                dto.setPlayerIdentifier(MockOrganizationEntityService.STORE.get(poid).getIdentifier());
                list.add(dto);
            }
        }
        return list;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    @Override
    public List<IdentifiedOrganizationDTO> search(IdentifiedOrganizationDTO arg0) {       
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto, gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<IdentifiedOrganizationDTO> search(
            IdentifiedOrganizationDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    @Override
    public void updateCorrelation(IdentifiedOrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelationStatus(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    @Override
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#validate(gov.nih.nci.services.PoDto)
     */
    @Override
    public Map<String, String[]> validate(IdentifiedOrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote#getCorrelationsByPlayerIdsWithoutLimit(java.lang.Long[])
     */
    @Override
    public List<IdentifiedOrganizationDTO> getCorrelationsByPlayerIdsWithoutLimit(
            Long[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
