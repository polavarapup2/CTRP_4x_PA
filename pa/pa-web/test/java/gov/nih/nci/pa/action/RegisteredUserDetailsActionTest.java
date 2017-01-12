/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.action.RegisteredUserDetailsAction.RegisteredUserDetail;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Arrays;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisteredUserDetailsActionTest extends AbstractPaActionTest {
    
    private RegistryUser user;
    private User csmUser;
    private AccrualSubmissionAccessDTO accrualAccessDTO;
    private StudyProtocolQueryDTO protocolDTO;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ServletActionContext.getRequest().getSession()
                .setAttribute(Constants.IS_SU_ABSTRACTOR, Boolean.TRUE);
        
        csmUser = new User();
        csmUser.setLoginName("jdoe");
        
        user = new RegistryUser();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setCsmUser(csmUser);
        user.setAffiliatedOrganizationId(1L);
        
        accrualAccessDTO = new AccrualSubmissionAccessDTO();
        accrualAccessDTO.setParticipatingSiteOrgName("Mayo");
        accrualAccessDTO.setParticipatingSitePoOrgId("10");
        accrualAccessDTO.setTrialId(1L);
        accrualAccessDTO.setTrialNciId("NCIID");
        accrualAccessDTO.setTrialTitle("The title");
        
        protocolDTO = new StudyProtocolQueryDTO();
        protocolDTO.setStudyProtocolId(20L);
        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.RegisteredUserDetailsAction#execute()}.
     * @throws PAException 
     */
    @Test
    public final void testExecute() throws PAException {
        RegisteredUserDetailsAction action = new RegisteredUserDetailsAction();
        action.setModel(new RegisteredUserDetail());
        action.setScopeKey("KEY");

        RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        when(registryUserServiceLocal.search(null)).thenReturn(
                Arrays.asList(user));
        action.setRegistryUserService(registryUserServiceLocal);

        assertEquals("success", action.execute());
        assertNull(action.getModel().getUser());
        assertEquals(1, action.getModel().getUserList().size());
        assertEquals(new Long(1), action.getModel().getUserList().get(0)
                .getId());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.RegisteredUserDetailsAction#query()}.
     * @throws PAException 
     */
    @Test
    public final void testQuery() throws PAException {
        RegisteredUserDetailsAction action = new RegisteredUserDetailsAction();
        action.setModel(new RegisteredUserDetail());
        action.setScopeKey("KEY");

        RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
        when(registryUserServiceLocal.getUserById(1L)).thenReturn((user));
        action.setRegistryUserService(registryUserServiceLocal);
        action.setSelectedUserId(1L);
        
        PAServiceUtils utils = mock(PAServiceUtils.class);
        when(utils.getPOOrganizationEntity(any(Ii.class))).thenReturn(orgDto);
        action.setPaServiceUtils(utils);
        
        StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal = mock(StudySiteAccrualAccessServiceLocal.class);
        when(studySiteAccrualAccessServiceLocal.getAccrualSubmissionAccess(any(RegistryUser.class))).thenReturn(Arrays.asList(accrualAccessDTO));
        action.setStudySiteAccrualAccessService(studySiteAccrualAccessServiceLocal);
        
        ProtocolQueryServiceLocal protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(Arrays.asList(protocolDTO));
        action.setProtocolQueryService(protocolQueryServiceLocal);

        assertEquals("success", action.query());
        RegisteredUserDetail model = action.getModel();
        assertNotNull(model);
        assertEquals(1, model.getSiteAdminOrgs().size());
        assertEquals(orgDto, model.getSiteAdminOrgs().get(0));
        assertEquals(1, model.getAccrualSubmissionAccess().size());
        assertEquals(1, model.getAccrualSubmissionAccess().get(0).getTrialId().intValue());
        assertEquals("NCIID", model.getAccrualSubmissionAccess().get(0).getTrialNciId());
        assertEquals("The title", model.getAccrualSubmissionAccess().get(0).getTrialTitle());
        assertEquals(1, model.getAccrualSubmissionAccess().get(0).getParticipatingSites().size());
        assertEquals("Mayo", model.getAccrualSubmissionAccess().get(0).getParticipatingSites().get(0).getName());
        assertEquals("10", model.getAccrualSubmissionAccess().get(0).getParticipatingSites().get(0).getPoId());
        
        assertEquals(1, model.getTrialsOwned().size());
        assertEquals(20L, model.getTrialsOwned().get(0).getStudyProtocolId().longValue());
        assertEquals(1, model.getTrialsSubmitted().size());
        assertEquals(20L, model.getTrialsSubmitted().get(0).getStudyProtocolId().longValue());
        assertEquals("KEY", action.getScopeKey());
        assertEquals(1L, action.getSelectedUserId().longValue());
        
    }
    
    @Test
    public final void testQueryNoUser() throws PAException {
        RegisteredUserDetailsAction action = new RegisteredUserDetailsAction();
        action.setModel(new RegisteredUserDetail());
        action.setScopeKey("KEY");
        action.setSelectedUserId(null);        
        assertEquals("success", action.query());
        RegisteredUserDetail model = action.getModel();
        assertNotNull(model);
        assertNull(model.getUser());
        
        
    }
    

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.RegisteredUserDetailsAction#paging()}.
     * @throws PAException 
     */
    @Test
    public final void testPaging() throws PAException {
        RegisteredUserDetailsAction action = new RegisteredUserDetailsAction();
        action.prepare();
        assertEquals("success", action.paging());
    }

}
