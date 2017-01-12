/**
 * caBIG Open Source Software License
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.TrialDataVerification;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.ISOUtil;

/**
 * 
 * @author Reshma.Koganti
 *
 */
public class TrialVerificationDataConverter 
    extends AbstractConverter<TrialVerificationDataDTO, TrialDataVerification> {
    /**
     * {@inheritDoc}
     */
    @Override
    public TrialVerificationDataDTO convertFromDomainToDto(TrialDataVerification trialVerification) {
        return convertFromDomainToDTO(trialVerification, new TrialVerificationDataDTO());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TrialDataVerification convertFromDtoToDomain(TrialVerificationDataDTO trialVerificationDTO) {
        TrialDataVerification bo = new TrialDataVerification();
        convertFromDtoToDomain(trialVerificationDTO, bo);
        return bo;
    } 
    /**
     * 
     * @param bo bo
     * @param dto dto
     * @return dto dto
     */
    public static TrialVerificationDataDTO convertFromDomainToDTO(
            TrialDataVerification bo, TrialVerificationDataDTO dto) {
        if (bo != null) {
            dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
            if (bo.getStudyProtocol() != null) {
                dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(bo.getStudyProtocol().getId()));
            }
            dto.setVerificationMethod(StConverter.convertToSt(bo.getVerificationMethod())); 
            dto.setDateLastUpdated(TsConverter.convertToTs(bo.getDateLastUpdated()));
            if (bo.getUserLastUpdated() != null) {
                dto.setUserLastUpdated(StConverter.convertToSt(bo
                        .getUserLastUpdated().getLoginName())); 
            }
            
        }
        return dto;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(TrialVerificationDataDTO dto, TrialDataVerification bo) {
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setVerificationMethod(StConverter.convertToString(dto.getVerificationMethod())); 
        StudyProtocol spBo = null;
        if (!ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            spBo = new StudyProtocol();
            spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }
        bo.setStudyProtocol(spBo);
    }
}
