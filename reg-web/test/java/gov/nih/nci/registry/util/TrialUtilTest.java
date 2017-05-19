package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TrialInfoHelperUtil;
import gov.nih.nci.registry.action.AbstractHibernateTestCase;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class TrialUtilTest extends AbstractHibernateTestCase {
    
    @Test
    public void copyRegulatoryInformationTest() throws PAException, NullifiedRoleException {
        Ii studyProtocolIi = IiConverter.convertToIi(1L);
        String nciId = "NCI-2017-01234";
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setAssignedIdentifier(nciId);
        AdditionalRegulatoryInfoDTO addRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        addRegInfoDTO.setNci_id(nciId);
        addRegInfoDTO.setStudy_protocol_id("1");
        addRegInfoDTO.setExported_from_us("true");
        addRegInfoDTO.setFda_regulated_device("true");
        addRegInfoDTO.setFda_regulated_drug("false");
        addRegInfoDTO.setPed_postmarket_surv("true");
        addRegInfoDTO.setPost_prior_to_approval("null");
        
        TrialUtil trialUtil = new TrialUtil();
        TrialInfoHelperUtil trialInfoHelperUtil = mock(TrialInfoHelperUtil.class);
        trialUtil.setTrialInfoHelperUtil(trialInfoHelperUtil);
        
        StudyProtocolServiceLocal studyProtocolServiceLocal =  mock(StudyProtocolServiceLocal.class);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(true));
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(false));
        studyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        
        when(studyProtocolServiceLocal.getStudyProtocol(studyProtocolIi)).thenReturn(studyProtocolDTO);
        when(trialInfoHelperUtil.retrieveRegulatoryInfo(studyProtocolIi, nciId)).thenReturn(addRegInfoDTO);
        
        trialUtil.copyRegulatoryInformation(studyProtocolIi, trialDTO);
        
        assertEquals(trialDTO.getFdaRegulatedDevice(), addRegInfoDTO.getFda_regulated_device());
        assertEquals(trialDTO.getFdaRegulatedDrug(), addRegInfoDTO.getFda_regulated_drug());
        assertEquals(trialDTO.getExportedFromUs(), addRegInfoDTO.getExported_from_us());
        assertEquals(trialDTO.getPedPostmarketSurv(), addRegInfoDTO.getPed_postmarket_surv());
        assertEquals(trialDTO.getPostPriorToApproval(), addRegInfoDTO.getPost_prior_to_approval());
        assertEquals(trialDTO.getFdaRegulatedDevice(), addRegInfoDTO.getFda_regulated_device());
        
        assertEquals(trialDTO.getSection801Indicator(), "Yes");
        assertEquals(trialDTO.getFdaRegulatoryInformationIndicator(), "No");
        assertEquals(trialDTO.getDataMonitoringCommitteeAppointedIndicator(), "Yes");
    }
    
    @Test(expected=PAException.class)
    public void copyRegulatoryInformationTestException() throws PAException {
        Ii studyProtocolIi = IiConverter.convertToIi(1L);
        String nciId = "NCI-2017-01234";
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setAssignedIdentifier(nciId);
        
        TrialUtil trialUtil = new TrialUtil();
        
        StudyProtocolServiceLocal studyProtocolServiceLocal = mock(StudyProtocolServiceLocal.class);
       
        TrialInfoHelperUtil trialInfoHelperUtil = mock(TrialInfoHelperUtil.class);
        
        trialUtil.setTrialInfoHelperUtil(trialInfoHelperUtil);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        
        when(studyProtocolServiceLocal.getStudyProtocol(studyProtocolIi)).thenReturn(new StudyProtocolDTO());
        
        when(trialInfoHelperUtil.retrieveRegulatoryInfo(studyProtocolIi, nciId)).thenThrow(new PAException());
        
        trialUtil.copyRegulatoryInformation(studyProtocolIi, trialDTO);
    }
    
    @Test
    public void saveAdditionalRegulatoryInfoTest() throws PAException {
        TrialInfoHelperUtil mockHelperUtil = mock(TrialInfoHelperUtil.class);
        when(mockHelperUtil.mergeRegulatoryInfoUpdate(
                any(String.class), any(String.class), any(AdditionalRegulatoryInfoDTO.class)))
                .thenAnswer(new Answer<AdditionalRegulatoryInfoDTO>() {
                    @Override
                    public AdditionalRegulatoryInfoDTO answer(InvocationOnMock invocation) throws Throwable {
                        AdditionalRegulatoryInfoDTO dto = (AdditionalRegulatoryInfoDTO) invocation.getArguments()[2];
                        dto.setId("123");
                        dto.setDate_updated("11-27-2016");
                        return dto;
                    }
                });

        TrialUtil trialUtil = new TrialUtil();
        trialUtil.setTrialInfoHelperUtil(mockHelperUtil);
        
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setExportedFromUs("true");
        trialDTO.setFdaRegulatedDevice("true");
        trialDTO.setFdaRegulatedDrug("true");
        trialDTO.setPedPostmarketSurv("true");
        trialDTO.setPostPriorToApproval("true");
        trialDTO.setLastUpdatedDate("11-27-2016");
        trialDTO.setStudyProtocolId("12345");
        trialDTO.setMsId("987654321");
        
        AdditionalRegulatoryInfoDTO additionalRegulatoryInfoDTO = 
                trialUtil.saveAdditionalRegulatoryInfo(trialDTO);
        assertEquals("true", additionalRegulatoryInfoDTO.getExported_from_us());
        assertEquals("true", additionalRegulatoryInfoDTO.getFda_regulated_device());
        assertEquals("true", additionalRegulatoryInfoDTO.getFda_regulated_drug());
        assertEquals("true", additionalRegulatoryInfoDTO.getPed_postmarket_surv());
        assertEquals("true", additionalRegulatoryInfoDTO.getPost_prior_to_approval());
        assertEquals("11-27-2016", additionalRegulatoryInfoDTO.getDate_updated());
        assertEquals("12345", additionalRegulatoryInfoDTO.getStudy_protocol_id());
        assertEquals(null, additionalRegulatoryInfoDTO.getNci_id());
        assertEquals("123", additionalRegulatoryInfoDTO.getId());
        
        verify(mockHelperUtil).mergeRegulatoryInfoUpdate(eq("12345"), isNull(String.class), 
                any(AdditionalRegulatoryInfoDTO.class));
    }
    
    @Test
    public void saveDraftTest() throws PAException {
        TrialInfoHelperUtil mockHelperUtil = mock(TrialInfoHelperUtil.class);
        when(mockHelperUtil.mergeRegulatoryInfoUpdate(
                any(String.class), any(String.class), any(AdditionalRegulatoryInfoDTO.class)))
                .thenAnswer(new Answer<AdditionalRegulatoryInfoDTO>() {
                    @Override
                    public AdditionalRegulatoryInfoDTO answer(InvocationOnMock invocation) throws Throwable {
                        AdditionalRegulatoryInfoDTO dto = (AdditionalRegulatoryInfoDTO) invocation.getArguments()[2];
                        dto.setId("123");
                        dto.setDate_updated("11-27-2016");
                        return dto;
                    }
                });

        TrialUtil trialUtil = new TrialUtil();
        trialUtil.setTrialInfoHelperUtil(mockHelperUtil);
        
        TrialDTO dto = getMockTrialDTO();
        
        TrialDTO saved = (TrialDTO) trialUtil.saveDraft(dto);
        
        verify(mockHelperUtil).mergeRegulatoryInfoUpdate(eq("1"), isNull(String.class), 
                any(AdditionalRegulatoryInfoDTO.class));
        
        assertEquals("123", saved.getMsId());
        assertEquals("11-27-2016", saved.getLastUpdatedDate());
    }
    
    @Test
    public void getTrialDTOForPartiallySumbissionByIdTest() throws PAException, NullifiedRoleException {
        TrialInfoHelperUtil mockHelperUtil = mock(TrialInfoHelperUtil.class);
        when(mockHelperUtil.retrieveRegulatoryInfo(anyString(), anyString()))
                .thenAnswer(new Answer<AdditionalRegulatoryInfoDTO>() {
                    @Override
                    public AdditionalRegulatoryInfoDTO answer(InvocationOnMock invocation) throws Throwable {
                        AdditionalRegulatoryInfoDTO dto = new AdditionalRegulatoryInfoDTO();
                        dto.setStudy_protocol_id((String) invocation.getArguments()[0]);
                        dto.setNci_id((String) invocation.getArguments()[1]);
                        dto.setId("123");
                        dto.setDate_updated("11-27-2016");
                        dto.setExported_from_us("true");
                        dto.setFda_regulated_device("true");
                        dto.setFda_regulated_drug("true");
                        dto.setPed_postmarket_surv("true");
                        dto.setPost_prior_to_approval("true");
                        return dto;
                    }
                });

        TrialUtil trialUtil = new TrialUtil();
        trialUtil.setTrialInfoHelperUtil(mockHelperUtil);
        
        TrialDTO trialDTO = (TrialDTO) trialUtil.getTrialDTOForPartiallySumbissionById("1");
        
        verify(mockHelperUtil).retrieveRegulatoryInfo(eq("1"), isNull(String.class));
        
        assertEquals("123", trialDTO.getMsId());
        
        
        
    }
}
