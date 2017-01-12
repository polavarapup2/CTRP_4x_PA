/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Vrushali
 *
 */
public class StudyFundingStageConverter {

    /**
    *
    * @param studyFundingStage  tempStudyFunding
    * @return tempStudyFundingDTO
    */
   public static StudyFundingStageDTO convertFromDomainToDTO(StudyFundingStage studyFundingStage) {
       return convertFromDomainToDTO(studyFundingStage, new StudyFundingStageDTO());
   }
   
 /**
   *
   * @param studyFundingStageDTO studyFundingStageDTO
   * @return StudyProtocol StudyProtocol
   */
  public static StudyFundingStage convertFromDTOToDomain(StudyFundingStageDTO studyFundingStageDTO) {
      return convertFromDTOToDomain(studyFundingStageDTO , new StudyFundingStage());
  }

  private static StudyFundingStageDTO convertFromDomainToDTO(
          StudyFundingStage studyFundingStage,
          StudyFundingStageDTO studyFundingStageDTO) {
      studyFundingStageDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
             studyFundingStage.getFundingMechanismCode()));
      studyFundingStageDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
             studyFundingStage.getNciDivisionProgramCode()));
      studyFundingStageDTO.setNihInstitutionCode(CdConverter.convertStringToCd(
              studyFundingStage.getNihInstituteCode()));
      studyFundingStageDTO.setSerialNumber(StConverter.convertToSt(studyFundingStage.getSerialNumber()));
      studyFundingStageDTO.setFundingPercent(RealConverter.convertToReal(studyFundingStage.getFundingPercent()));
      studyFundingStageDTO.setStudyProtocolStageIi(IiConverter.convertToIi(
             studyFundingStage.getStudyProtocolStage().getId()));
      return studyFundingStageDTO;
  }

  private static StudyFundingStage convertFromDTOToDomain(
        StudyFundingStageDTO studyFundingStageDTO,
        StudyFundingStage studyFundingStage) {
    
      studyFundingStage.setFundingMechanismCode(CdConverter.convertCdToString(
              studyFundingStageDTO.getFundingMechanismCode()));
      studyFundingStage.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(CdConverter.convertCdToString(
              studyFundingStageDTO.getNciDivisionProgramCode())));
      studyFundingStage.setNihInstituteCode(CdConverter.convertCdToString(
              studyFundingStageDTO.getNihInstitutionCode()));
      studyFundingStage.setSerialNumber(StConverter.convertToString(studyFundingStageDTO.getSerialNumber()));
      studyFundingStage.setFundingPercent(RealConverter.convertToDouble(studyFundingStageDTO.getFundingPercent()));
        StudyProtocolStage spBo = new StudyProtocolStage();
        spBo.setId(IiConverter.convertToLong(studyFundingStageDTO.getStudyProtocolStageIi()));
        studyFundingStage.setStudyProtocolStage(spBo);
        return studyFundingStage;
  }
}
