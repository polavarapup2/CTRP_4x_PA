/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockIdentifiedPersonCorrelationService implements
        IdentifiedPersonCorrelationServiceRemote {
    static List<IdentifiedPersonDTO> personList;
    static {
        personList = new ArrayList<IdentifiedPersonDTO>();
        IdentifiedPersonDTO dto = new IdentifiedPersonDTO();
        dto.setIdentifier(null);
        dto.setPlayerIdentifier(IiConverter.convertToIi("2"));
        dto.setScoperIdentifier(IiConverter.convertToIi("1"));
        dto.setAssignedId(IiConverter.convertToIi("1"));
        dto.setStatus(CdConverter.convertStringToCd("code"));
        personList.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(IdentifiedPersonDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    public IdentifiedPersonDTO getCorrelation(Ii arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090.Ii[])
     */
    public List<IdentifiedPersonDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0) {
        List<IdentifiedPersonDTO> matchingDtosList = new ArrayList<IdentifiedPersonDTO>();
        for(IdentifiedPersonDTO dto: personList){
            if(dto.getAssignedId().getExtension().equals(arg0.getAssignedId().getExtension())){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(IdentifiedPersonDTO arg0)
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
    public Map<String, String[]> validate(IdentifiedPersonDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IdentifiedPersonDTO> search(IdentifiedPersonDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

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

}
