package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.webservices.types.Grant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public class StudyResourcingDTOBuilder {

    /**
     * @param grants
     *            List<Grant>
     * @return List<StudyResourcingDTO>
     */
    public List<StudyResourcingDTO> build(List<Grant> grants) {
        List<StudyResourcingDTO> grantsDTOList = new ArrayList<StudyResourcingDTO>();
        for (Grant dto : grants) {
            StudyResourcingDTO isoDTO = new StudyResourcingDTO();
            isoDTO.setSummary4ReportedResourceIndicator(BlConverter
                    .convertToBl(Boolean.FALSE));
            isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto
                    .getFundingMechanism()));
            isoDTO.setNciDivisionProgramCode(CdConverter
                    .convertToCd(NciDivisionProgramCode.getByCode(dto
                            .getNciDivisionProgramCode().value())));
            isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto
                    .getNihInstitutionCode()));
            isoDTO.setSerialNumber(StConverter.convertToSt(dto
                    .getSerialNumber()));
            isoDTO.setFundingPercent(RealConverter.convertToReal(dto
                    .getFundingPercentage() != null ? Double.valueOf(dto
                    .getFundingPercentage()) : null));
            grantsDTOList.add(isoDTO);
        }

        return grantsDTOList;
    }

}
