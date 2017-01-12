/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockOrganizationCorrelationService implements
        OrganizationCorrelationServiceRemote {

    static ArrayList<Organization> paOrgList = new ArrayList<Organization>();
    static ArrayList<HealthCareFacility> hcfList = new ArrayList<HealthCareFacility>();
    static ArrayList<OversightCommittee> ocList = new ArrayList<OversightCommittee>();
    static ArrayList<ResearchOrganization> roList = new ArrayList<ResearchOrganization>();
    static HashMap<String, Organization> poPaOrgMap = new HashMap<String, Organization>();

    private static Long seq = 0L;

    public static String getOrgNameFromPoIdentifier(String poIdentifier) {
        return "Organization " + poIdentifier;
    }

    private void createPaOrg(String poId) {
        seq++;
        Organization o = new Organization();
        o.setCity("city" + seq);
        o.setCountryName("USA");
        o.setIdentifier(poId);
        o.setName(getOrgNameFromPoIdentifier(poId));
        o.setPostalCode("12345");
        o.setStatusCode(EntityStatusCode.ACTIVE);
        paOrgList.add(o);
        poPaOrgMap.put(poId, o);
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createHealthCareFacilityCorrelations(String orgPoIdentifier)
            throws PAException {
        if (!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for (HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier))
                ;
            return hcf.getId();
        }
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(seq++);
        hcf.setIdentifier("po" + seq);
        hcf.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        hcfList.add(hcf);
        return hcf.getId();
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createOversightCommitteeCorrelations(String orgPoIdentifier)
            throws PAException {
        if (!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for (HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier))
                ;
            return hcf.getId();
        }
        OversightCommittee oc = new OversightCommittee();
        oc.setId(seq++);
        oc.setIdentifier("po" + seq);
        oc.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        oc.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        oc.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ocList.add(oc);
        return oc.getId();
    }

    /**
     * @param poOrg
     * @return
     * @throws PAException
     */
    public Organization createPAOrganizationUsingPO(OrganizationDTO poOrg)
            throws PAException {
        seq++;
        Organization o = new Organization();
        o.setCity("city" + seq);
        o.setCountryName("USA");
        o.setIdentifier(IiConverter.convertToString(poOrg.getIdentifier()));
        o.setName(EnOnConverter.convertEnOnToString(poOrg.getName()));
        o.setPostalCode("12345");
        o.setStatusCode(EntityStatusCode.ACTIVE);
        paOrgList.add(o);
        poPaOrgMap.put(o.getIdentifier(), o);
        return o;
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createResearchOrganizationCorrelations(String orgPoIdentifier) {
        if (!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for (HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier))
                ;
            return hcf.getId();
        }
        ResearchOrganization ro = new ResearchOrganization();
        ro.setId(seq++);
        ro.setIdentifier("po" + seq);
        ro.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        roList.add(ro);
        return ro.getId();
    }

    /**
     * @param studyProtocolId
     * @param functionalCode
     * @return
     * @throws PAException
     */
    public List<Organization> getOrganizationByStudySite(
            Long studyProtocolId,
            StudySiteFunctionalCode functionalCode) throws PAException {
        ArrayList<Organization> orgList = new ArrayList<Organization>();

        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setFunctionalCode(CdConverter.convertToCd(functionalCode));
        List<StudySiteDTO> spList = PaRegistry
                .getStudySiteService().getByStudyProtocol(
                        IiConverter.convertToIi(studyProtocolId), criteria);
        for (StudySiteDTO sp : spList) {
            if (!ISOUtil.isIiNull(sp.getHealthcareFacilityIi())) {
                for (HealthCareFacility hcf : hcfList) {
                    if (hcf.getId().equals(
                            IiConverter.convertToLong(sp
                                    .getHealthcareFacilityIi()))) {
                        orgList.add(hcf.getOrganization());
                    }
                }
            }
            if (!ISOUtil.isIiNull(sp.getOversightCommitteeIi())) {
                for (OversightCommittee oc : ocList) {
                    if (oc.getId().equals(
                            IiConverter.convertToLong(sp
                                    .getOversightCommitteeIi()))) {
                        orgList.add(oc.getOrganization());
                    }
                }
            }
            if (!ISOUtil.isIiNull(sp.getResearchOrganizationIi())) {
                for (ResearchOrganization ro : roList) {
                    if (ro.getId().equals(
                            IiConverter.convertToLong(sp
                                    .getResearchOrganizationIi()))) {
                        orgList.add(ro.getOrganization());
                    }
                }
            }
        }
        return orgList;
    }

    /**
     *
     * @param studyProtocolIi
     *            sp id
     * @param cd
     *            functional role code
     * @return Organization
     * @throws PAException
     *             onError
     */
    public Organization getOrganizationByFunctionRole(Ii studyProtocolIi, Cd cd)
            throws PAException {
        return null;
    }
    public String getPOOrgIdentifierByIdentifierType(String identifierType) throws PAException {
        String poOrgId = "";
        if (identifierType.equalsIgnoreCase(PAConstants.NCT_IDENTIFIER_TYPE)) {
            poOrgId = "1";
        }
        return poOrgId;
    }
    
    @Override
    public String getPOOrgIdentifierByOrgName(String orgName)
            throws PAException {       
        return getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
    }

    public Ii getPoResearchOrganizationByEntityIdentifier(Ii orgPoIdentifier)
            throws PAException {
        return IiConverter.convertToIi("1");
    }

    public Long createHcfWithExistingPoHcf(Ii poHcfIdentifier) throws PAException {
        return 1L;
    }

    /**
     * {@inheritDoc}
     */
    public Ii getROByFunctionRole(Ii studyProtocolIi, Cd cd) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAffiliatedWithTrial(Long spId, Long orgId, StudySiteFunctionalCode type) throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

}
