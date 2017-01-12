package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialDataVerificationServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.TrialUtil;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialDataVerificationActionTest extends AbstractRegWebTest {
    private TrialDataVerificationAction action;
    private StudyProtocolServiceLocal studyProtocolService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;
    private StudyInboxServiceLocal studyInboxService;
    private TrialDataVerificationServiceLocal trialDataVerificationService;

    /**
     * Initialization.
     * @throws Exception in case of error
     */
    @Before
    public void setup() throws Exception {
        action = new TrialDataVerificationAction();
        action.prepare();
        studyProtocolService = PaRegistry.getStudyProtocolService(); 
        action.setStudyProtocolService(studyProtocolService);
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        action.setProtocolQueryService(protocolQueryService);
        registryUserService = mock(RegistryUserServiceLocal.class);
        action.setRegistryUserService(registryUserService);
        studyInboxService = mock(StudyInboxServiceLocal.class);
        action.setStudyInboxService(studyInboxService);
        trialDataVerificationService = mock(TrialDataVerificationServiceLocal.class);
        action.setTrialDataVerificationService(trialDataVerificationService);
        Organization org = new Organization();
        org.setIdentifier("1");
        org.setCity("city");
        org.setCountryName("countryName");
        org.setName("name");
        org.setPostalCode("postalCode");

        Person person = new Person();
        person.setIdentifier("1");
        person.setFirstName("firstName");
        person.setLastName("lastName");

        PAContactDTO contactDTO = new PAContactDTO();
        contactDTO.setFullName("Contact User");
        CorrelationUtilsRemote correlationUtils = mock(CorrelationUtilsRemote.class);
        when(correlationUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
        when(correlationUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(correlationUtils.getContactByPAOrganizationalContactId(any(Long.class))).thenReturn(contactDTO);
        TrialUtil trialUtils = new TrialUtil();
        trialUtils.setCorrelationUtils(correlationUtils);
        action.setTrialUtils(trialUtils);
    }
    
    @Test
    public void testView() throws PAException {
        action.setStudyProtocolId(1L);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        when(studyProtocolService.getStudyProtocol(IiConverter.convertToIi(1L))).thenReturn(dto);
        StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
        studyProtocolQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        studyProtocolQueryDTO.setDocumentWorkflowStatusDate(new Date());
        studyProtocolQueryDTO.setStudyProtocolId(1L);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L)).thenReturn(studyProtocolQueryDTO);
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        list.add(studyProtocolQueryDTO);
        when(protocolQueryService.getActiveInactiveStudyProtocolsById(1L)).thenReturn(list);
        Set<RegistryUser> trialOwners = new HashSet<RegistryUser>();
        RegistryUser user = new RegistryUser();
        user.setEmailAddress("reshma.koganti@semanticbits.com");
        trialOwners.add(user);
        when(registryUserService.getAllTrialOwners(1L)).thenReturn(trialOwners);
        List<TrialVerificationDataDTO> trialList = new ArrayList<TrialVerificationDataDTO>();
        TrialVerificationDataDTO trialDto = new TrialVerificationDataDTO();
        trialDto.setIdentifier(IiConverter.convertToIi(1L));
        trialDto.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        trialDto.setUserLastUpdated(StConverter.convertToSt("1"));
        trialDto.setDateLastUpdated(TsConverter.convertToTs(new Date()));
        
        trialList.add(trialDto);
        when(trialDataVerificationService.getDataByStudyProtocolId(1L)).thenReturn(trialList);
        assertEquals("success", action.view());
    }
    
}
