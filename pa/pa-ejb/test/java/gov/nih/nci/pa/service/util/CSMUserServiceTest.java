package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class CSMUserServiceTest extends AbstractHibernateTestCase {
    
	CSMUserService bean;
    private final CSMUserService beanMock = mock(CSMUserService.class);
    RegistryUser user;
    User csmUser;
    UserProvisioningManager upManager;
    private HibernateHelper paHibernateHelper;
	private Object[] objs =  new Object[6];
    
    @Before
    public void setup() throws Exception {
    	
        TestSchema.primeData();
        CSMUserService.setInstance(new MockCSMUserService());
        bean = new CSMUserService();
        csmUser = new User();
        csmUser.setLoginName("Abstractor: " + new Date());
        csmUser.setFirstName("Joe");
        csmUser.setLastName("Smith");
        csmUser.setUpdateDate(new Date());
        csmUser.setUserId(1L);
        UsernameHolder.setUser(csmUser.getLoginName());
        user = new RegistryUser();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmailAddress("test@example.com");
        user.setPhone("123-456-7890");
        user.setCsmUser(csmUser);
        user.setUserLastCreated(csmUser);
        user.setUserLastUpdated(csmUser);
        user.setFamilyAccrualSubmitter(false);
        user.setId(1L);
        CSMUserService.setInstance(beanMock);
        paHibernateHelper = PaHibernateUtil.getHibernateHelper(); 
    }

    
    @Test
    public void getTest() throws Exception {
        try {
//             PaRegistry.getInstance().setServiceLocator(mock(ServiceLocator.class));
//             upManager = mock(UserProvisioningManager.class);
//             SecurityServiceProvider sp = mock(SecurityServiceProvider.class);
//             when(sp.getUserProvisioningManager("pa")).thenReturn(upManager);
//             when(upManager.getUser("loginName")).thenAnswer(new Answer<User>() {
//                 @Override
//                 public User answer(InvocationOnMock invocation) throws Throwable {
//                     User user = new User();
//                     user.setLastName("lastName");
//                     user.setFirstName("firstName");
//                     user.setLoginName("loginName");
//                     return user;
//                 }
//             } );
            bean.getCSMUser("loginName");
//            
//            assertEquals("firstName", returnUser.getFirstName());
        } catch (Exception cse) {
            // expected
        }
    }
    @Test
    public void createCSMUserTest() throws Exception {
        try {
            RegistryUser dto = new RegistryUser();
            dto.setFirstName("firstName");
            dto.setLastName("lastName");
            User csmUser = new User();
            csmUser.setUserId(1L);
            dto.setCsmUser(csmUser);
            dto.setId(1L);
            bean.createCSMUser(dto, "loginName","password");
        } catch (Exception cse) {
            // expected
        }
    }
    
    @Test
    public void isCurrentUserAbstractorTest() throws PAException {
        when(beanMock.getCSMUser(anyString())).thenReturn(csmUser);
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        map.put(1L, "Joe");
        when(beanMock.getAbstractors()).thenReturn(map);
        doCallRealMethod().when(beanMock).isCurrentUserAbstractor();
        boolean value = beanMock.isCurrentUserAbstractor();
        assertTrue(value);
    }
    
    @Test
    public void isCurrentUserAutoCurationTest() throws PAException {
        when(beanMock.getCSMUser(anyString())).thenReturn(null);
        doCallRealMethod().when(beanMock).isCurrentUserAutoCuration();
        boolean value = beanMock.isCurrentUserAutoCuration();
        assertFalse(value);
    }
    
    @Test
    public void getAbstractorsTest() throws PAException {
        when(beanMock.getCSMUser(anyString())).thenReturn(null);
        doCallRealMethod().when(beanMock).getAbstractors();
        Map<Long, String> map = beanMock.getAbstractors();
        assertFalse(map.size() > 0);
    }
    
    @Test
    public void getCSMUserByIdTest() throws PAException {
        try {
            bean.getCSMUserById(1L);
        } catch (Exception cse) {
            // expected
        }
    }
    @Test
    public void getCSMUsersTest() throws PAException {
         try {
             bean.getCSMUsers();
         } catch (Exception cse) {
             // expected
         }
    }
    
    @Test
    public void assignUserToGroupTest() throws PAException {
        try {
            bean.assignUserToGroup("loginName", "GroupName");
        } catch (Exception cse) {
            // expected
        }
    }
    
    @Test
    public void isUserInGroupTest() throws PAException {
        try {
            bean.isUserInGroup("loginName", "GroupName");
        } catch (Exception cse) {
            // expected
        }
    }
    
    @Test
    public void getUserGroupsTest() throws PAException {
        try {
            bean.getUserGroups("loginName");
        } catch (Exception cse) {
            // expected
        }
    }
    

}
