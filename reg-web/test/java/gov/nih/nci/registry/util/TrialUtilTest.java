package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

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
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

public class TrialUtilTest {
    
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
}
