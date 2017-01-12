package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FamilyHelperTest {

    private OrganizationEntityServiceRemote oes;
    private FamilyServiceRemote fs;

    @Before
    public void setUp() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
    }

    @Test
    public void getByOrgIdNullTest() throws Exception {
        assertTrue(FamilyHelper.getByOrgId(null).isEmpty());
    }

    @Test(expected = PAException.class)
    public void getByOrgIdTooManyResultsTest() throws Exception {
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(1));
        FamilyHelper.getByOrgId(1L);
    }

    @Test
    public void getByOrgIdTest() throws Exception {
        // no family associated with org
        OrganizationDTO org  = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        assertTrue(FamilyHelper.getByOrgId(1L).isEmpty());

        // one family
        Set<Ii> familySet = new HashSet<Ii>();
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        dset.setItem(familySet);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        assertEquals(1, FamilyHelper.getByOrgId(1L).size());

        // n results
        familySet.add(IiConverter.convertToPoFamilyIi("2"));
        familyMap.put(IiConverter.convertToPoFamilyIi("2"), getPoFamilyDTO(2L));
        assertEquals(2, FamilyHelper.getByOrgId(1L).size());
    }

    @Test
    public void testGetRelatedOrgsInFamilyNull() throws Exception {
        assertTrue(FamilyHelper.getRelatedOrgsInFamily((Long) null).isEmpty());
    }


    @Test
    public void testGetRelatedOrgsInFamilyNotFound() throws Exception {
        assertTrue(FamilyHelper.getRelatedOrgsInFamily(-1L).isEmpty());
    }
    @Test
    public void testGetRelatedOrgsInFamily() throws Exception {
        assertEquals(0, FamilyHelper.getRelatedOrgsInFamily(1L).size());
        when(fs.getActiveRelationships(1L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        assertEquals(2, FamilyHelper.getRelatedOrgsInFamily(1L).size());
    }


    @Test
    public void getAllRelatedOrgsNull() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs((Long) null).isEmpty());
    }

    @Test
    public void getAllRelatedOrgsNotFound() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs(1L).isEmpty());
    }

    @Test(expected = PAException.class)
    public void getAllRelatedOrgsTooManyResultsTest() throws Exception {
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(1));
        FamilyHelper.getAllRelatedOrgs((Long) 1L);
    }


    @Test
    public void getAllRelatedOrgsTest() throws Exception {
        OrganizationDTO org  = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        Set<Ii> familySet = new HashSet<Ii>();
        dset.setItem(familySet);
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        familySet.add(IiConverter.convertToPoFamilyIi("2"));
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        familyMap.put(IiConverter.convertToPoFamilyIi("2"), getPoFamilyDTO(2L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        
        // 2 families 1 common org and 1 unique per family
        when(fs.getActiveRelationships(1L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 3L}));
        assertEquals(3, FamilyHelper.getAllRelatedOrgs(1L).size());

        // 2 families with the same 2 members
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        assertEquals(2, FamilyHelper.getAllRelatedOrgs(1L).size());
    }

    @Test
    public void getP30GrantSerialNumberNotFoundTest() throws Exception {
        assertNull(FamilyHelper.getP30GrantSerialNumber(1L));
    }

    @Test
    public void getP30GrantSerialNumberTest() throws Exception {
        OrganizationDTO org  = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        Set<Ii> familySet = new HashSet<Ii>();
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        familySet.add(IiConverter.convertToPoFamilyIi("2"));
        familySet.add(IiConverter.convertToPoFamilyIi("3"));
        dset.setItem(familySet);
        when(fs.getFamilies(any(Set.class))).thenAnswer(new Answer<Map<Ii, FamilyDTO>>() {
            @Override
            public Map<Ii, FamilyDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
                for (Ii item : (Set<Ii>) args[0]) {
                    familyMap.put(item, getPoFamilyDTO(IiConverter.convertToLong(item)));
                }
                return familyMap;
            }
        });
        FamilyOrganizationRelationshipDTO forDto = new FamilyOrganizationRelationshipDTO();
        forDto.setFunctionalType(CdConverter.convertStringToCd(FamilyFunctionalType.ORGANIZATIONAL.name()));
        when(fs.getFamilyOrganizationRelationship(any(Ii.class))).thenReturn(forDto);
        String sn = "293481";
        FamilyP30DTO p30 = new FamilyP30DTO();
        p30.setSerialNumber(EnOnConverter.convertToEnOn(sn));
        when(fs.getP30Grant(2L)).thenReturn(p30);
        assertEquals(sn, FamilyHelper.getP30GrantSerialNumber(1L));

        // test non-organizational
        forDto.setFunctionalType(CdConverter.convertStringToCd(FamilyFunctionalType.AFFILIATION.name()));
        assertNull(FamilyHelper.getP30GrantSerialNumber(1L));
    }

    private FamilyDTO getPoFamilyDTO(Long id) {
        FamilyDTO family = new FamilyDTO();
        family.setIdentifier(IiConverter.convertToPoFamilyIi(id.toString()));
        family.setName(EnOnConverter.convertToEnOn("family" + id));
        return family;
    }

    public static List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }
    @Test
    public void getAllFamiliesTest() throws Exception { 
        List<FamilyDTO> familyList = new ArrayList<FamilyDTO>();
        FamilyDTO dto= new FamilyDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(EnOnConverter.convertToEnOn("Family1"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        familyList.add(dto);
        when(fs.search(any(FamilyDTO.class), any(LimitOffset.class))).thenReturn(familyList);
        assertEquals(1, FamilyHelper.getAllFamilies().size());
    }
    
    @Test
    public void getPOFamilyByPOIDTest() {
       FamilyDTO dto = new FamilyDTO();
       dto.setIdentifier(IiConverter.convertToIi(1L));
       dto.setName(EnOnConverter.convertToEnOn("Family1"));
       dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
       when(fs.getFamily(any(Ii.class))).thenReturn(dto);
       assertEquals(1, IiConverter.convertToLong(FamilyHelper.getPOFamilyByPOID(1L).getIdentifier()).longValue());
    }
}
