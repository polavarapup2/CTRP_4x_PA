package gov.nih.nci.pa.util;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class VerifyTrialDataHelperTest extends AbstractHibernateTestCase {
    private VerifyTrialDataHelper helper;
    private RegistryUserServiceLocal registryUserService = mock(RegistryUserServiceLocal.class);
    private ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class);
    private MailManagerServiceLocal mailManagerService = mock(MailManagerServiceLocal.class);
    VerifyTrialDataHelper helperMock = mock(VerifyTrialDataHelper.class);
    StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
    List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
    RegistryUser regUser = new RegistryUser();
    private static final String N = "N_value";
    Set<RegistryUser> trialOwners = new HashSet<RegistryUser>();
    
    @Before
    public void setUp() throws Exception {
        helper = new VerifyTrialDataHelper();  
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
       
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        dto.setNciIdentifier("NCI-2009-0001");
        LastCreatedDTO lastCreatedDTO = new LastCreatedDTO();
        lastCreatedDTO.setUserLastCreated("firstLastName");
        dto.setLastCreated(lastCreatedDTO);
        dto.setRecordVerificationDate(new Date());
        dto.setStudyProtocolId(1L);
        
        User user = new User();
        user.setLoginName("firstLastName");
       
        regUser.setAffiliateOrg("NCI");
        regUser.setFirstName("firstName");
        regUser.setLastName("LastName");
        regUser.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        regUser.setCsmUser(user);
        records.add(dto);
       
        trialOwners.add(regUser);
        helper.setRegistryUserService(registryUserService);
        helper.setProtocolQueryService(protocolQueryService);
        helper.setMailManagerService(mailManagerService);
        helper.setLookUpTableService(lookUpTableService);
        helperMock.setRegistryUserService(registryUserService);
        helperMock.setProtocolQueryService(protocolQueryService);
        helperMock.setMailManagerService(mailManagerService);
        helperMock.setLookUpTableService(lookUpTableService);
        when(registryUserService.getUser("firstLastName")).thenReturn(regUser);
        when(protocolQueryService.getStudyProtocolByCriteria(queryCriteria)).thenReturn(records);
    }
    @Test
    public void getOpenTrialsTest() throws PAException {
        when(PaRegistry.getRegistryUserService()).thenReturn(registryUserService);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(PaRegistry.getMailManagerService()).thenReturn(mailManagerService);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableService);
        helper.getOpenTrials(); 
        
    }
    
    @Test
    public void getOpenTrialTest() throws PAException {
        when(PaRegistry.getRegistryUserService()).thenReturn(registryUserService);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(PaRegistry.getMailManagerService()).thenReturn(mailManagerService);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableService);
        when(helperMock.getCriteria()).thenReturn(queryCriteria);
        when(registryUserService.getUser("firstLastName")).thenReturn(regUser);
        when(protocolQueryService.getStudyProtocolByCriteria(queryCriteria)).thenReturn(records);
        when(lookUpTableService.getPropertyValue(N)).thenReturn("12");
        doCallRealMethod().when(helperMock).getOpenTrials();
        helperMock.getOpenTrials(); 
        
    }
    
    @Test
    public void getOpenTrialVerifyTest() throws PAException {
        when(PaRegistry.getRegistryUserService()).thenReturn(registryUserService);
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryService);
        when(PaRegistry.getMailManagerService()).thenReturn(mailManagerService);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableService);
        when(helperMock.getCriteria()).thenReturn(queryCriteria);
        when(registryUserService.getUser("firstLastName")).thenReturn(regUser);
        when(protocolQueryService.getStudyProtocolByCriteria(queryCriteria)).thenReturn(records);
        when(lookUpTableService.getPropertyValue(N)).thenReturn("0");     
        when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
        doCallRealMethod().when(helperMock).getOpenTrials();
        helperMock.getOpenTrials(); 
        
    }

    
    
    
}
