package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPoResearchOrganizationCorrelationService implements ResearchOrganizationCorrelationServiceRemote {

    /**
     * {@inheritDoc}
     */
    public Ii createCorrelation(ResearchOrganizationDTO arg0) throws EntityValidationException {
        Ii ii = IiConverter.convertToPoResearchOrganizationIi("2");
        ii.setReliability(IdentifierReliability.ISS);
        return ii;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationDTO getCorrelation(Ii ii) throws NullifiedRoleException {
        if ("NULLIFY".equals(ii.getIdentifierName())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            throw new NullifiedRoleException(nullifiedEntities);
        }
        ResearchOrganizationDTO ro = new ResearchOrganizationDTO();
        ro.setIdentifier(DSetConverter.convertIiToDset(ii));
        ro.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        ro.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return ro;
    }

    /**
     * {@inheritDoc}
     */
    public List<ResearchOrganizationDTO> getCorrelations(Ii[] arg0) throws NullifiedRoleException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO dto) {
        ResearchOrganizationDTO ro = new ResearchOrganizationDTO();
        ro.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        ro.setPlayerIdentifier(dto.getPlayerIdentifier());
        Ii identifier = IiConverter.convertToPoResearchOrganizationIi("1");
        identifier.setReliability(IdentifierReliability.ISS);
        ro.setIdentifier(DSetConverter.convertIiToDset(identifier));
        return Arrays.asList(ro);
    }

    /**
     * {@inheritDoc}
     */
    public void updateCorrelation(ResearchOrganizationDTO arg0) throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public void updateCorrelationStatus(Ii arg0, Cd arg1) throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> validate(ResearchOrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO arg0, LimitOffset arg1)
    throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<ResearchOrganizationDTO> getCorrelationsByPlayerIds(Ii[] arg0) throws NullifiedRoleException {
        ResearchOrganizationDTO ro = new ResearchOrganizationDTO();
        ro.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        ro.setPlayerIdentifier(arg0[0]);
        Ii identifier = IiConverter.convertToPoResearchOrganizationIi("1");
        identifier.setReliability(IdentifierReliability.ISS);
        ro.setIdentifier(DSetConverter.convertIiToDset(identifier));
        
        Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
        DSet<Ad> addressSet = new DSet<Ad>();
        addressSet.setItem(new java.util.HashSet<Ad>());
        addressSet.getItem().add(address);
        ro.setPostalAddress(addressSet);
        
        List<String> phones = new ArrayList<String>();
        String phone = "1111111111";
        String email = "a@a.com";
        phones.add(phone);
        List<String> emails = new ArrayList<String>();
        emails.add(email);        
        DSet<Tel> dsetList = null;
        dsetList = DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
        dsetList = DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
        ro.setTelecomAddress(dsetList);         
        
        return Arrays.asList(ro);
    }
}
