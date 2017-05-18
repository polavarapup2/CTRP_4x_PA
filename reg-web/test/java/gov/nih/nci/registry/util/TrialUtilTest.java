package gov.nih.nci.registry.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TrialInfoHelperUtil;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

public class TrialUtilTest {
    
    @Test
    public void getTrialDTOFromDbTest() throws PAException, NullifiedRoleException {
        /*Ii studyProtocolIi = IiConverter.convertToIi(1L);
        String nciId = "NCI-2017-01234";
        TrialDTO trialDTO = new TrialDTO();
        AdditionalRegulatoryInfoDTO addRegInfoDTO = new AdditionalRegulatoryInfoDTO();
        addRegInfoDTO.setNci_id(nciId);
        addRegInfoDTO.setStudy_protocol_id("1");
        addRegInfoDTO.setExported_from_us("true");
        addRegInfoDTO.setFda_regulated_device("true");
        addRegInfoDTO.setFda_regulated_drug("false");
        addRegInfoDTO.setPed_postmarket_surv("true");
        addRegInfoDTO.setPost_prior_to_approval("null");
        
        TrialUtil trialUtil = new TrialUtil();
        
        StudyProtocolServiceLocal studyProtocolServiceLocal =  mock(StudyProtocolServiceLocal.class);
        ProtocolQueryServiceLocal protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        TrialInfoHelperUtil trialInfoHelperUtil = mock(TrialInfoHelperUtil.class);
        
        trialUtil.setTrialInfoHelperUtil(trialInfoHelperUtil);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolServiceLocal);
        when(paRegSvcLoc.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt("Test Title"));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.I));
        studyProtocolDTO.setPhaseAdditionalQualifierCode(
                CdConverter.convertToCd(PhaseAdditionalQualifierCode.PILOT));
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeAdditionalQualifierCode(spDTO.getPrimaryPurposeAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        
        
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(true));
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(false));
        studyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
        
        StudyProtocolQueryDTO spqDto = new StudyProtocolQueryDTO();
        
        when(studyProtocolServiceLocal.getStudyProtocol(studyProtocolIi)).thenReturn(studyProtocolDTO);
        
        when(protocolQueryServiceLocal.getTrialSummaryByStudyProtocolId(1L)).thenReturn(spqDto);
        when(trialInfoHelperUtil.retrieveRegulatoryInfo(studyProtocolIi, nciId)).thenReturn(addRegInfoDTO);
        
        trialUtil.getTrialDTOFromDb(studyProtocolIi, trialDTO);
        
        assertEquals(trialDTO.getFdaRegulatedDevice(), addRegInfoDTO.getFda_regulated_device());
        assertEquals(trialDTO.getFdaRegulatedDrug(), addRegInfoDTO.getFda_regulated_drug());
        assertEquals(trialDTO.getExportedFromUs(), addRegInfoDTO.getExported_from_us());
        assertEquals(trialDTO.getPedPostmarketSurv(), addRegInfoDTO.getPed_postmarket_surv());
        assertEquals(trialDTO.getPostPriorToApproval(), addRegInfoDTO.getPost_prior_to_approval());
        assertEquals(trialDTO.getFdaRegulatedDevice(), addRegInfoDTO.getFda_regulated_device());
        
        assertEquals(trialDTO.getSection801Indicator(), "Yes");
        assertEquals(trialDTO.getFdaRegulatoryInformationIndicator(), "No");
        assertEquals(trialDTO.getDataMonitoringCommitteeAppointedIndicator(), "Yes");
     */   
    }
}
