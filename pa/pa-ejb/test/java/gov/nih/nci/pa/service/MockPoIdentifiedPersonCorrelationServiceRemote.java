/**
 * 
 */
package gov.nih.nci.pa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * @author Denis G. Krylov
 *
 */
public class MockPoIdentifiedPersonCorrelationServiceRemote implements IdentifiedPersonCorrelationServiceRemote {

    @Override
    public Ii createCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException, CurationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IdentifiedPersonDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IdentifiedPersonDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IdentifiedPersonDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        IdentifiedPersonDTO dto =  new IdentifiedPersonDTO();
        dto.setPlayerIdentifier(IiConverter.convertToPoPersonIi("3"));
        dto.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        
        Ii ii = new Ii();
        ii.setExtension("4648");
        ii.setIdentifierName(IiConverter.CTEP_PERSON_IDENTIFIER_NAME);
        ii.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        dto.setAssignedId(ii);
        
        return Arrays.asList(dto);
    }

    @Override
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0) {
        // TODO Auto-generated method stub
        return new ArrayList<IdentifiedPersonDTO>();
    }

    @Override
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Map<String, String[]> validate(IdentifiedPersonDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
