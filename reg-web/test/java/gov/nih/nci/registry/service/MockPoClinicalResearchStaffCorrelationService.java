/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockPoClinicalResearchStaffCorrelationService implements
        ClinicalResearchStaffCorrelationServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(ClinicalResearchStaffDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToPoClinicalResearchStaffIi("1");
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    public ClinicalResearchStaffDTO getCorrelation(Ii ii)
            throws NullifiedRoleException {
        if (NullFlavor.NA.equals(ii.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(ii, IiConverter.convertToPoClinicalResearchStaffIi("1"));
            throw new NullifiedRoleException(nullifiedEntities);
        }
        ClinicalResearchStaffDTO crs = new ClinicalResearchStaffDTO();
        crs.setIdentifier(new DSet<Ii>());
        crs.getIdentifier().setItem(new HashSet<Ii>());
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT);
        crs.getIdentifier().getItem().add(ii);
        crs.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        crs.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        crs.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return crs;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090.Ii[])
     */
    public List<ClinicalResearchStaffDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<ClinicalResearchStaffDTO> search(ClinicalResearchStaffDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(ClinicalResearchStaffDTO arg0)
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
    public Map<String, String[]> validate(ClinicalResearchStaffDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ClinicalResearchStaffDTO> search(ClinicalResearchStaffDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ClinicalResearchStaffDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        List<ClinicalResearchStaffDTO> crsList = new ArrayList<ClinicalResearchStaffDTO>();
        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        Ii crsIi = IiConverter.convertToPoClinicalResearchStaffIi("1");
        crsIi.setReliability(IdentifierReliability.ISS);
        crsDTO.setIdentifier(DSetConverter.convertIiToDset(crsIi));
        crsList.add(crsDTO);
        return crsList;
    }

    @Override
    public Ii createActiveCorrelation(ClinicalResearchStaffDTO arg0)
            throws EntityValidationException, CurationException {
        return IiConverter.convertToPoClinicalResearchStaffIi("1");
    }

}
