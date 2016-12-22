/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
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
public class MockPoOrganizationalContactCorrelationService implements
        OrganizationalContactCorrelationServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToPoOrganizationalContactIi("1");   
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    public OrganizationalContactDTO getCorrelation(Ii ii)
            throws NullifiedRoleException {
        if (NullFlavor.NA.equals(ii.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(ii, IiConverter.convertToPoOrganizationalContactIi("1"));
            throw new NullifiedRoleException(nullifiedEntities);
        }
        OrganizationalContactDTO oc = new OrganizationalContactDTO();
        oc.setIdentifier(DSetConverter.convertIiToDset(ii));
        oc.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        oc.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        oc.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return oc;    
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
        List<OrganizationalContactDTO> results = new ArrayList<OrganizationalContactDTO>();
        OrganizationalContactDTO oc = new OrganizationalContactDTO();
        DSet<Ii> ocIiSet = arg0.getIdentifier();
        if (ISOUtil.isDSetNotEmpty(ocIiSet)) {
            oc.setIdentifier(ocIiSet);
        } else {
            oc.setIdentifier(DSetConverter.convertIiToDset(
                    IiConverter.convertToPoOrganizationalContactIi("1")));
        }
        Ii pIi = arg0.getPlayerIdentifier() == null ? 
                IiConverter.convertToPoPersonIi("1") : arg0.getPlayerIdentifier();
        oc.setPlayerIdentifier(pIi);
        Ii sIi = arg0.getScoperIdentifier() == null ? 
                IiConverter.convertToPoOrganizationIi("1") : arg0.getScoperIdentifier();
        oc.setScoperIdentifier(sIi);
        oc.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        results.add(oc);
        return results;
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
        // TODO Auto-generated method stub
        return new ArrayList<OrganizationalContactDTO>();
    }

    @Override
    public Ii createActiveCorrelation(OrganizationalContactDTO arg0)
            throws EntityValidationException, CurationException {
        return IiConverter.convertToPoOrganizationalContactIi("1");   
    }

}
