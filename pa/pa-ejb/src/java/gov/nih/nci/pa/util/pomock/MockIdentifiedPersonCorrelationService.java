/**
 * 
 */
package gov.nih.nci.pa.util.pomock;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
public class MockIdentifiedPersonCorrelationService implements
        IdentifiedPersonCorrelationServiceRemote {

    /**
     * 
     */
    public MockIdentifiedPersonCorrelationService() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public Ii createCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException, CurationException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090
     * .Ii)
     */
    @Override
    public IdentifiedPersonDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090
     * .Ii[])
     */
    @Override
    public List<IdentifiedPersonDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#getCorrelationsByPlayerIds(gov
     * .nih.nci.iso21090.Ii[])
     */
    @Override
    public List<IdentifiedPersonDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<IdentifiedPersonDTO> list = new ArrayList<IdentifiedPersonDTO>();
        for (Ii ii : arg0) {
            String poid = ii.getExtension();
            String ctepId = MockPersonEntityService.PO_ID_TO_CTEP_ID.get(poid);
            if (StringUtils.isNotBlank(ctepId)) {
                IdentifiedPersonDTO dto = new IdentifiedPersonDTO();
                Ii ctepIi = new Ii();
                ctepIi.setExtension(ctepId);
                ctepIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
                ctepIi.setIdentifierName(IiConverter.CTEP_PERSON_IDENTIFIER_NAME);
                dto.setAssignedId(ctepIi);
                dto.setPlayerIdentifier(MockPersonEntityService.STORE.get(poid)
                        .getIdentifier());
                list.add(dto);
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
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto
     * , gov.nih.nci.coppa.services.LimitOffset)
     */
    @Override
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci
     * .services.PoDto)
     */
    @Override
    public void updateCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException {
        throw new UnsupportedOperationException();

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
    public Map<String, String[]> validate(IdentifiedPersonDTO arg0) {       
        return new HashMap<String, String[]>();
    }

}
