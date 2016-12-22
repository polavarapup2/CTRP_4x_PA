package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.LastCreatedDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class VerifyTrialDataNightlyServiceTest extends AbstractHibernateTestCase {
	private VerifyTrialDataNightlyServiceBeanLocal bean;
	private RegistryUserServiceLocal registryUserService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private LookUpTableServiceRemote lookUpTableService;
    private MailManagerServiceLocal mailManagerService;
    VerifyTrialDataNightlyServiceBeanLocal mockBean;
    StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
    List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
    RegistryUser regUser = new RegistryUser();
    private static final String N = "group1TrialsVerificationFrequency";
    Set<RegistryUser> trialOwners = new HashSet<RegistryUser>();
    
    @Before
    public void setUp() throws Exception {
        bean = new VerifyTrialDataNightlyServiceBeanLocal();  
        registryUserService = mock(RegistryUserServiceLocal.class);
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        lookUpTableService = mock(LookUpTableServiceRemote.class);
        mailManagerService = mock(MailManagerServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        mockBean = mock(VerifyTrialDataNightlyServiceBeanLocal.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
       
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        dto.setNciIdentifier("NCI-2009-0001");
        LastCreatedDTO lastCreatedDTO = new LastCreatedDTO();
        lastCreatedDTO.setUserLastCreated("firstLastName");
        dto.setLastCreated(lastCreatedDTO);
        dto.setRecordVerificationDate(new Date());
        
        dto.setVerificationDueDate(org.apache.commons.lang.time.DateUtils.addDays(new Date(), 30));
        dto.setStudyProtocolId(1L);
        
        User user = new User();
        user.setLoginName("firstLastName");
       
        regUser.setAffiliateOrg("NCI");
        regUser.setFirstName("firstName");
        regUser.setLastName("LastName");
        regUser.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        regUser.setCsmUser(user);
        regUser.setId(1L);
        records.add(dto);
        trialOwners.add(regUser);
        StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
        dto1.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        dto1.setNciIdentifier("NCI-2009-0002");
        
        dto1.setLastCreated(lastCreatedDTO);
        dto1.setRecordVerificationDate(new Date());
        
        dto1.setVerificationDueDate(org.apache.commons.lang.time.DateUtils.addDays(new Date(), 7));
        dto1.setStudyProtocolId(2L);
        records.add(dto1);
        
        
        bean.setRegistryUserService(registryUserService);
        bean.setProtocolQueryService(protocolQueryService);
        bean.setMailManagerService(mailManagerService);
        bean.setLookUpTableService(lookUpTableService);
        
        when(registryUserService.getUser("firstLastName")).thenReturn(regUser);
        when(protocolQueryService.getStudyProtocolByCriteria(queryCriteria)).thenReturn(records);
    }

    
    @Test
    public void getOpenTrialTest() throws PAException {
        when(lookUpTableService.getPropertyValue(N)).thenReturn("12");
        doCallRealMethod().when(mockBean).getOpenTrials();
        bean.getOpenTrials(); 
        
    }
    
    @Test
    public void getOpenTrialVerifyTest() throws PAException {
        when(mockBean.getCriteria()).thenReturn(queryCriteria);
        when(lookUpTableService.getPropertyValue(N)).thenReturn("0");     
        when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
        doCallRealMethod().when(mockBean).getOpenTrials();
        bean.getOpenTrials(); 
        
    }
    
    @Test
    public void groupUsersTest() throws PAException {
        when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
        Map<RegistryUser, List<StudyProtocolQueryDTO>> map = bean.groupUsers(records);
        assertTrue(map.size() > 0);
        assertTrue(map.containsKey(regUser));
        List<StudyProtocolQueryDTO> dtoList = map.get(regUser);
        StudyProtocolQueryDTO dto = dtoList.get(0);
        assertTrue(dto.getStudyProtocolId() == 1);
    }
    
    @Test
    public void getNearingDueDatetest() throws PAException {
         when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
         Map<RegistryUser, List<StudyProtocolQueryDTO>> map = bean.groupUsers(records);
         Map<RegistryUser, List<StudyProtocolQueryDTO>> resultMap = bean.getNearingDueDate(map);
         assertTrue(resultMap.size() > 0);
    }
    
    @Test
    public void getCTRONearingDueDateTest() throws PAException {
        List<StudyProtocolQueryDTO> result = bean.getCTRONearingDueDate(records);
        assertTrue(result.size() > 0);
    }

}
