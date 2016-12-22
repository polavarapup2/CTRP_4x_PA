package gov.nih.nci.pa.service.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class FamilySynchronizationServiceTest extends AbstractHibernateTestCase {

    FamilySynchronizationServiceBean ejb;
    private OrganizationEntityServiceRemote poOrg;
    private FamilyServiceRemote poFamily;
    StudySiteAccrualAccessServiceBean studySiteAccess;
    RegistryUserServiceBean registryUserService;
    FamilyServiceBeanLocal familyService;
    Session sess;

    @Before
    public void setUp() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        poOrg = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(poOrg);
        poFamily = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(poFamily);
        PoRegistry.getInstance().setPoServiceLocator(psl);

        studySiteAccess = mock(StudySiteAccrualAccessServiceBean.class);
        registryUserService = mock(RegistryUserServiceBean.class);
        familyService = mock(FamilyServiceBeanLocal.class);
        ejb = new FamilySynchronizationServiceBean();
        ejb.setFamilyService(familyService);
        ejb.setRegistryUserService(registryUserService);
        ejb.setStudySiteAccess(studySiteAccess);
    }

    @Test
    public void messageNull() throws Exception {
        ejb.synchronizeFamilyOrganizationRelationship(null);
    }

    @Test
    public void messageNotFound() throws Exception {
        ejb.synchronizeFamilyOrganizationRelationship(1L);
    }

    @Test
    public void delete() throws Exception {
        Long relId = 13L;
        Ii relIi = IiConverter.convertToPoFamilyOrgRelationshipIi("13");
        Ii familyIi = IiConverter.convertToPoFamilyIi("12");
        Ii orgIi = IiConverter.convertToPoOrganizationIi("23");
        FamilyOrganizationRelationshipDTO relationship = new FamilyOrganizationRelationshipDTO();
        relationship.setFamilyIdentifier(familyIi);
        relationship.setOrgIdentifier(orgIi);
        relationship.setIdentifier(relIi);
        List<FamilyOrganizationRelationshipDTO> activeRelationships = new ArrayList<FamilyOrganizationRelationshipDTO>();
        when(poFamily.getActiveRelationships(anyLong())).thenReturn(activeRelationships);
        when(poFamily.getFamilyOrganizationRelationship(any(Ii.class))).thenReturn(relationship);

        // no access
        when(registryUserService.findByAffiliatedOrgs(any(Collection.class))).thenReturn(new ArrayList<RegistryUser>());
        ejb.synchronizeFamilyOrganizationRelationship(relId);
        verify(studySiteAccess, never()).createTrialAccessHistory(any(RegistryUser.class), any(AccrualAccessSourceCode.class), 
                anyLong(), anyString(), any(RegistryUser.class));
    }

    @Test
    public void addOrgToFamily() throws Exception {
        Long relId = 13L;
        Ii relIi = IiConverter.convertToPoFamilyOrgRelationshipIi("13");
        Ii familyIi = IiConverter.convertToPoFamilyIi("12");
        Ii orgIi = IiConverter.convertToPoOrganizationIi("23");
        FamilyOrganizationRelationshipDTO relationship = new FamilyOrganizationRelationshipDTO();
        relationship.setFamilyIdentifier(familyIi);
        relationship.setOrgIdentifier(orgIi);
        relationship.setIdentifier(relIi);
        List<FamilyOrganizationRelationshipDTO> activeRelationships = new ArrayList<FamilyOrganizationRelationshipDTO>();
        activeRelationships.add(relationship);
        when(poFamily.getActiveRelationships(anyLong())).thenReturn(activeRelationships);
        when(poFamily.getFamilyOrganizationRelationship(any(Ii.class))).thenReturn(relationship);

        RegistryUser user = new RegistryUser();
        List<RegistryUser> users = new ArrayList<RegistryUser>();
        users.add(user);
        when(registryUserService.findByAffiliatedOrgs(any(Collection.class))).thenReturn(users);
        user.setFamilyAccrualSubmitter(false);
        ejb.synchronizeFamilyOrganizationRelationship(relId);
        verify(familyService, never()).assignFamilyAccrualAccess(any(RegistryUser.class), any(RegistryUser.class), anyString());
        user.setFamilyAccrualSubmitter(true);
        ejb.synchronizeFamilyOrganizationRelationship(relId);
        verify(familyService).assignFamilyAccrualAccess(any(RegistryUser.class), any(RegistryUser.class), anyString());
    }
}
