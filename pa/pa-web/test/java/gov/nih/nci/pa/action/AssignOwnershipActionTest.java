/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.AssignOwnershipSearchCriteria;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class AssignOwnershipActionTest extends AbstractPaActionTest {
    private final AssignOwnershipAction action = new AssignOwnershipAction();
    private final RegistryUserServiceLocal regUserSvc = mock(RegistryUserServiceLocal.class);
    private final MailManagerServiceLocal mailManagerSvc = mock(MailManagerServiceLocal.class);
    private final OrganizationEntityServiceRemote orgEntSvc = mock(OrganizationEntityServiceRemote.class);

    @Before
    public void setup() throws Exception {
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("Org Name"));
        org.setIdentifier(IiConverter.convertToIi(1L));
        orgList.add(org);
        when(orgEntSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgList);       
        RegistryUser regUser = new RegistryUser();
        regUser.setLastName("LAST NAME");
        regUser.setId(1L);
        List<RegistryUser> regUsersList = new ArrayList<RegistryUser>();
        Set<RegistryUser> regUsersSet = new HashSet<RegistryUser>();
        regUsersList.add(regUser);
        regUsersSet.add(regUser);
        when(regUserSvc.search(any(RegistryUser.class))).thenReturn(regUsersList);
        when(regUserSvc.getAllTrialOwners(anyLong())).thenReturn(regUsersSet); 
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);        
        when(paRegSvcLoc.getRegistryUserService()).thenReturn(regUserSvc);
        when(paRegSvcLoc.getMailManagerService()).thenReturn(mailManagerSvc);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        action.setRegistryUserService(regUserSvc);
        action.setOrgEntitySvc(orgEntSvc);
        action.setCriteria(new AssignOwnershipSearchCriteria());
    }

    @Test
    public void testcsmUsersNamesProperty() {
        assertNull(action.getUsers());
        action.setUsers(new ArrayList<TrialOwner>());
        assertNotNull(action.getUsers());
    }

    @Test
    public void testDisplayAffiliatedOrg() throws TooManyResultsException {
        getRequest().setupAddParameter("orgId", "1");        
        assertEquals("display_affiliated_org", action.displayAffiliatedOrganization());
        assertEquals("Org Name", action.getCriteria().getAffiliatedOrgName());
        assertEquals(Long.valueOf(1), action.getCriteria().getAffiliatedOrgId());
        when(orgEntSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(0));
        assertEquals("display_affiliated_org", action.displayAffiliatedOrganization());
        assertTrue(action.getActionErrors().iterator().next().startsWith("Too many"));        
    }
    
    @Test
    public void testview() throws PAException {        
        assertEquals("success",action.view());                
        assertEquals("LAST NAME", action.getTrialOwners().iterator().next().getRegUser().getLastName());
        when(regUserSvc.getAllTrialOwners(anyLong())).thenThrow(new PAException());
        assertEquals("success",action.view());
        assertTrue(action.getActionErrors().iterator().next().startsWith("Unable to lookup trial owners for study protocol"));
    }
    
    @Test
    public void testSave() throws PAException {
        assertEquals("success",action.save());
        assertEquals(1, action.getActionErrors().size());
        assertTrue(action.getActionErrors().contains("assignOwnership.user.error"));
        action.clearActionErrors();

        getRequest().setupAddParameter("csmUserId", "user1@mail.nih.gov");
        assertEquals("success",action.save());
        assertEquals(1, action.getActionErrors().size());
        assertTrue(action.getActionErrors().contains("assignOwnership.user.error"));
        action.clearActionErrors();

        getRequest().setupAddParameter("userId", "123");                        
        assertEquals("success", action.save());
    }
    
    @Test
    public void testSearch() throws PAException {                
        assertEquals("success",action.search());   
        when(regUserSvc.search(any(RegistryUser.class))).thenThrow(new PAException());
        assertEquals("success",action.search());
        assertTrue(action.getActionErrors().contains("Error getting csm users."));
    }
    
    @Test
    public void testRemove() throws PAException {
        getRequest().setupAddParameter("userId", "123");        
        assertEquals("success", action.remove());        
    }    
    
    @Test
    public void testSaveEmailPreference() throws PAException {
        getRequest().setupAddParameter("userId", "123"); 
        action.setEnableEmails(false);
        action.saveEmailPreference();        
    }
}
