package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import gov.nih.nci.pa.domain.TrialDataVerification;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialVerificationDataConverterTest extends
AbstractConverterTest<TrialVerificationDataConverter, TrialVerificationDataDTO, TrialDataVerification> {

    @Override
    public TrialDataVerification makeBo() {
        TrialDataVerification bo = new TrialDataVerification();
        bo.setId(ID);
        bo.setStudyProtocol(getStudyProtocol());
        bo.setVerificationMethod("Manual");
        bo.setUserLastCreated(getRegistryUser().getCsmUser());
        bo.setUserLastUpdated(getRegistryUser().getCsmUser());
        bo.setDateLastCreated(new Date());
        bo.setDateLastUpdated(new Date());
        return bo;
    }

    @Override
    public TrialVerificationDataDTO makeDto() {
        TrialVerificationDataDTO dto = new TrialVerificationDataDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(STUDY_PROTOCOL_ID));
        dto.setVerificationMethod(StConverter.convertToSt("Manual"));
        dto.setDateLastUpdated(TsConverter.convertToTs(new Date()));
        dto.setUserLastUpdated(StConverter.convertToSt(getRegistryUser().getFullName()));
        return dto;
    }

    @Override
    public void verifyBo(TrialDataVerification bo) {
        assertEquals(ID, bo.getId());
        assertEquals("Manual", bo.getVerificationMethod());
        assertEquals(getStudyProtocol().getId(), bo.getStudyProtocol().getId());
        
    }

    @Override
    public void verifyDto(TrialVerificationDataDTO dto) {
        assertEquals(ID, IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(StConverter.convertToSt("Manual"), dto.getVerificationMethod());
        assertEquals(IiConverter.convertToIi(STUDY_PROTOCOL_ID).getExtension(), 
                dto.getStudyProtocolIdentifier().getExtension());
        
    }

}
