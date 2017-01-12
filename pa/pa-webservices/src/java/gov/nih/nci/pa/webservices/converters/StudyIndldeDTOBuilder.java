/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.HolderType;
import gov.nih.nci.pa.webservices.types.INDIDE;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public final class StudyIndldeDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return List<StudyIndldeDTO>
     */
    public List<StudyIndldeDTO> build(BaseTrialInformation reg) {
        List<StudyIndldeDTO> studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();

        for (INDIDE ind : reg.getInd()) {
            StudyIndldeDTO isoDTO = convert(ind, IndldeTypeCode.IND);
            studyIndldeDTOList.add(isoDTO);
        }
        for (INDIDE ide : reg.getIde()) {
            StudyIndldeDTO isoDTO = convert(ide, IndldeTypeCode.IDE);
            studyIndldeDTOList.add(isoDTO);
        }

        return studyIndldeDTOList;

    }

    private StudyIndldeDTO convert(INDIDE ind, IndldeTypeCode typeCode) {
        StudyIndldeDTO isoDTO = new StudyIndldeDTO();
        isoDTO.setIndldeTypeCode(CdConverter.convertToCd(typeCode));
        isoDTO.setIndldeNumber(StConverter.convertToSt(ind.getNumber()));
        isoDTO.setGrantorCode(CdConverter.convertStringToCd(ind.getGrantor()
                .value()));
        isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(ind
                .getHolderType().value()));
        if (ind.getHolderType() == HolderType.NIH
                && ind.getNihInstitution() != null) {
            isoDTO.setNihInstHolderCode(CdConverter
                    .convertToCd(NihInstituteCode.valueOf(ind
                            .getNihInstitution().value())));
        }
        if (ind.getHolderType() == HolderType.NCI
                && ind.getNciDivisionProgramCode() != null) {
            isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(ind
                    .getNciDivisionProgramCode().value()));
        }
        isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(ind
                .isExpandedAccess()));
        if (ind.getExpandedAccessType() != null) {
            isoDTO.setExpandedAccessStatusCode(CdConverter
                    .convertStringToCd(ind.getExpandedAccessType().value()));
        }
        isoDTO.setExemptIndicator(BlConverter.convertToBl(ind.isExempt()));
        return isoDTO;
    }

}
