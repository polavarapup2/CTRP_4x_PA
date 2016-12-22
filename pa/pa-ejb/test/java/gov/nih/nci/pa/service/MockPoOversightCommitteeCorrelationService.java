package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPoOversightCommitteeCorrelationService implements OversightCommitteeCorrelationServiceRemote {

    public Ii createCorrelation(OversightCommitteeDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    public OversightCommitteeDTO getCorrelation(Ii Ii)
            throws NullifiedRoleException {
        if ("NULLIFY".equals(Ii.getIdentifierName())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            throw new NullifiedRoleException(nullifiedEntities);
        }
        OversightCommitteeDTO oc = new OversightCommitteeDTO();
        oc.setIdentifier(DSetConverter.convertIiToDset(Ii));
        oc.setPlayerIdentifier(IiConverter.convertToPoOversightCommitteeIi("1"));
        oc.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return oc;
    }

    public List<OversightCommitteeDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OversightCommitteeDTO> search(OversightCommitteeDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateCorrelation(OversightCommitteeDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public Map<String, String[]> validate(OversightCommitteeDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OversightCommitteeDTO> search(OversightCommitteeDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OversightCommitteeDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    

}
