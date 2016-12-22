/**
 *
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.Date;


/**
 * @author Vrushali
 *
 */
public class StudyIndIdeStageConverter extends AbstractConverter<StudyIndIdeStageDTO, StudyIndIdeStage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyIndIdeStageDTO convertFromDomainToDto(StudyIndIdeStage studyIndIdeStage) {
        StudyIndIdeStageDTO studyIndIdeStageDTO = new StudyIndIdeStageDTO();
        studyIndIdeStageDTO.setIdentifier(IiConverter.convertToStudyIndIdeIi(studyIndIdeStage.getId()));
        studyIndIdeStageDTO.setStudyProtocolStageIi(IiConverter.convertToIi(studyIndIdeStage.getStudyProtocolStage()
                                                                                            .getId()));
        studyIndIdeStageDTO.setExpandedAccessStatusCode(CdConverter.convertToCd(studyIndIdeStage
                                                                                .getExpandedAccessStatusCode()));
        studyIndIdeStageDTO.setExpandedAccessIndicator(BlConverter.convertToBl(studyIndIdeStage
                                                                               .getExpandedAccessIndicator()));
        studyIndIdeStageDTO.setGrantorCode(CdConverter.convertToCd(studyIndIdeStage.getGrantorCode()));
        studyIndIdeStageDTO.setNihInstHolderCode(CdConverter.convertToCd(studyIndIdeStage.getNihInstHolderCode()));
        studyIndIdeStageDTO.setNciDivProgHolderCode(CdConverter.convertToCd(studyIndIdeStage.getNciDivPrgHolderCode()));
        studyIndIdeStageDTO.setHolderTypeCode(CdConverter.convertToCd(studyIndIdeStage.getHolderTypeCode()));
        studyIndIdeStageDTO.setIndldeNumber(StConverter.convertToSt(studyIndIdeStage.getIndIdeNumber()));
        studyIndIdeStageDTO.setIndldeTypeCode(CdConverter.convertToCd(studyIndIdeStage.getIndldeTypeCode()));
        studyIndIdeStageDTO.setExemptIndicator(BlConverter.convertToBl(studyIndIdeStage.getExemptIndicator()));
        return studyIndIdeStageDTO;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyIndIdeStage convertFromDtoToDomain(StudyIndIdeStageDTO studyIndIdeStageDTO) {
        StudyIndIdeStage studyIndIdeStage = new StudyIndIdeStage();
        convertFromDtoToDomain(studyIndIdeStageDTO, studyIndIdeStage);
        return studyIndIdeStage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(StudyIndIdeStageDTO studyIndIdeStageDTO, StudyIndIdeStage studyIndIdeStage) {
        StudyProtocolStage spBo = new StudyProtocolStage();
        spBo.setId(IiConverter.convertToLong(studyIndIdeStageDTO.getStudyProtocolStageIi()));
        studyIndIdeStage.setDateLastUpdated(new Date());
        studyIndIdeStage.setStudyProtocolStage(spBo);
        convertExpandedAccessToDomain(studyIndIdeStageDTO, studyIndIdeStage);
        if (!ISOUtil.isCdNull(studyIndIdeStageDTO.getGrantorCode())) {
            studyIndIdeStage.setGrantorCode(GrantorCode.getByCode(studyIndIdeStageDTO.getGrantorCode().getCode()));
        }
        convertHolderTypeToDomain(studyIndIdeStageDTO, studyIndIdeStage);
        if (!ISOUtil.isStNull(studyIndIdeStageDTO.getIndldeNumber())) {
            studyIndIdeStage.setIndIdeNumber(studyIndIdeStageDTO.getIndldeNumber().getValue());
        }
        if (!ISOUtil.isCdNull(studyIndIdeStageDTO.getIndldeTypeCode())) {
            studyIndIdeStage.setIndldeTypeCode(IndldeTypeCode.getByCode(
                    studyIndIdeStageDTO.getIndldeTypeCode().getCode()));
        }
        if (!ISOUtil.isBlNull(studyIndIdeStageDTO.getExemptIndicator())) {
            studyIndIdeStage.setExemptIndicator(studyIndIdeStageDTO.getExemptIndicator().getValue());
        }
    }

    private void convertExpandedAccessToDomain(StudyIndIdeStageDTO studyIndIdeStageDTO,
            StudyIndIdeStage studyIndIdeStage) {
        if (!ISOUtil.isCdNull(studyIndIdeStageDTO.getExpandedAccessStatusCode())) {
            studyIndIdeStage.setExpandedAccessStatusCode(
                    ExpandedAccessStatusCode.getByCode(studyIndIdeStageDTO.getExpandedAccessStatusCode().getCode()));
        }
        if (!ISOUtil.isBlNull(studyIndIdeStageDTO.getExpandedAccessIndicator())) {
            studyIndIdeStage.setExpandedAccessIndicator(studyIndIdeStageDTO.getExpandedAccessIndicator().getValue());
        }
    }

    private void convertHolderTypeToDomain(StudyIndIdeStageDTO studyIndIdeStageDTO, StudyIndIdeStage studyIndIdeStage) {
        if (!ISOUtil.isCdNull(studyIndIdeStageDTO.getHolderTypeCode())) {
            if (studyIndIdeStageDTO.getHolderTypeCode().getCode().equals("NIH")) {
                studyIndIdeStage.setNihInstHolderCode(NihInstituteCode.getByCode(
                        studyIndIdeStageDTO.getNihInstHolderCode().getCode()));
            } else {
                studyIndIdeStage.setNihInstHolderCode(null);
            }
            if (studyIndIdeStageDTO.getHolderTypeCode().getCode().equals("NCI")) {
                studyIndIdeStage.setNciDivPrgHolderCode(NciDivisionProgramCode.getByCode(
                        studyIndIdeStageDTO.getNciDivProgHolderCode().getCode()));
            } else {
                studyIndIdeStage.setNciDivPrgHolderCode(null);
            }
            
            studyIndIdeStage.setHolderTypeCode(HolderTypeCode.getByCode(
                    studyIndIdeStageDTO.getHolderTypeCode().getCode()));
        }
    }
}
