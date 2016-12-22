package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/23/2008
 */
public class NonInterventionalStudyProtocolConverter extends
        StudyProtocolConverter {

    /**
     * 
     * @param osp
     *            ObservationalStudyProtocol
     * @return ObservationalStudyProtocolDTO ObservationalStudyProtocolDTO
     */
    public static NonInterventionalStudyProtocolDTO convertFromDomainToDTO(
            NonInterventionalStudyProtocol osp) {
        final NonInterventionalStudyProtocolDTO studyProtocolDTO = new NonInterventionalStudyProtocolDTO();
        NonInterventionalStudyProtocolDTO ospDTO = convertFromDomainToDTO(osp,
                studyProtocolDTO);
        return ospDTO;
    }

    /**
     * @param osp
     *            NonInterventionalStudyProtocol
     * @param studyProtocolDTO
     *            NonInterventionalStudyProtocolDTO
     * @return NonInterventionalStudyProtocolDTO
     */
    public static NonInterventionalStudyProtocolDTO convertFromDomainToDTO(
            NonInterventionalStudyProtocol osp,
            final NonInterventionalStudyProtocolDTO studyProtocolDTO) {
        NonInterventionalStudyProtocolDTO ospDTO = (NonInterventionalStudyProtocolDTO) StudyProtocolConverter
                .convertFromDomainToDTO(osp, studyProtocolDTO);

        ospDTO.setBiospecimenDescription(StConverter.convertToSt(osp
                .getBiospecimenDescription()));
        ospDTO.setBiospecimenRetentionCode(CdConverter.convertToCd(osp
                .getBiospecimenRetentionCode()));
        ospDTO.setNumberOfGroups(IntConverter.convertToInt(osp
                .getNumberOfGroups()));
        ospDTO.setSamplingMethodCode(CdConverter.convertToCd(osp
                .getSamplingMethodCode()));
        ospDTO.setStudyModelCode(CdConverter.convertToCd(osp
                .getStudyModelCode()));
        ospDTO.setStudyModelOtherText(StConverter.convertToSt(osp
                .getStudyModelOtherText()));
        ospDTO.setTimePerspectiveCode(CdConverter.convertToCd(osp
                .getTimePerspectiveCode()));
        ospDTO.setTimePerspectiveOtherText(StConverter.convertToSt(osp
                .getTimePerspectiveOtherText()));
        ospDTO.setStudyPopulationDescription(StConverter.convertToSt(osp
                .getStudyPopulationDescription()));
        ospDTO.setStudySubtypeCode(CdConverter.convertToCd(osp
                .getStudySubtypeCode()));
        return ospDTO;
    }

    /**
     * 
     * @param ospDTO
     *            ObservationalStudyProtocolDTO
     * @return ObservationalStudyProtocol ObservationalStudyProtocol
     * @throws PAException
     *             when error.
     */
    public static NonInterventionalStudyProtocol convertFromDTOToDomain(
            NonInterventionalStudyProtocolDTO ospDTO) throws PAException {
        final NonInterventionalStudyProtocol studyProtocol = new NonInterventionalStudyProtocol();
        return convertFromDTOToDomain(ospDTO, studyProtocol);
    }

    /**
     * @param ospDTO
     *            NonInterventionalStudyProtocolDTO
     * @param studyProtocol
     *            NonInterventionalStudyProtocol
     * @return NonInterventionalStudyProtocol
     * @throws PAException
     *             PAException
     */
    public static NonInterventionalStudyProtocol convertFromDTOToDomain(
            NonInterventionalStudyProtocolDTO ospDTO,
            final NonInterventionalStudyProtocol studyProtocol)
            throws PAException {
        NonInterventionalStudyProtocol osp = (NonInterventionalStudyProtocol) StudyProtocolConverter
                .convertFromDTOToDomain(ospDTO, studyProtocol);
        osp.setBiospecimenDescription(StConverter.convertToString(ospDTO
                .getBiospecimenDescription()));
        osp.setBiospecimenRetentionCode(BiospecimenRetentionCode
                .getByCode(CdConverter.convertCdToString(ospDTO
                        .getBiospecimenRetentionCode())));
        osp.setNumberOfGroups(IntConverter.convertToInteger(ospDTO
                .getNumberOfGroups()));
        osp.setSamplingMethodCode(SamplingMethodCode.getByCode(CdConverter
                .convertCdToString(ospDTO.getSamplingMethodCode())));
        osp.setStudyModelCode(StudyModelCode.getByCode(CdConverter
                .convertCdToString(ospDTO.getStudyModelCode())));
        osp.setStudyModelOtherText(StConverter.convertToString(ospDTO
                .getStudyModelOtherText()));
        osp.setTimePerspectiveCode(TimePerspectiveCode.getByCode(CdConverter
                .convertCdToString(ospDTO.getTimePerspectiveCode())));
        osp.setTimePerspectiveOtherText(StConverter.convertToString(ospDTO
                .getTimePerspectiveOtherText()));
        osp.setStudyPopulationDescription(StConverter.convertToString(ospDTO
                .getStudyPopulationDescription()));
        osp.setStudySubtypeCode(StudySubtypeCode.getByCode(CdConverter
                .convertCdToString(ospDTO.getStudySubtypeCode())));
        return osp;
    }
}
