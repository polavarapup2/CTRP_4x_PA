/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;

import org.apache.commons.lang.StringUtils;



/**
 * @author dkrylov
 * 
 */
public class StudyOverallStatusDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudyOverallStatusDTO
     */
    public StudyOverallStatusDTO build(BaseTrialInformation reg) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                .getByCode(reg.getTrialStatus().value())));
        isoDto.setReasonText(StConverter.convertToSt(reg.getWhyStopped()));
        isoDto.setStatusDate(TsConverter.convertToTs(reg.getTrialStatusDate()
                .toGregorianCalendar().getTime()));
        return isoDto;
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param reg
     *            CompleteTrialUpdate
     * @return StudyOverallStatusDTO
     * @throws PAException
     *             PAException
     */
    public StudyOverallStatusDTO build(StudyProtocolDTO spDTO,
            CompleteTrialUpdate reg) throws PAException {
        StudyOverallStatusDTO isoDto = PaRegistry
                .getStudyOverallStatusService().getCurrentByStudyProtocol(
                        spDTO.getIdentifier());
        if (reg.getTrialStatus() != null) {
            isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                    .getByCode(reg.getTrialStatus().value())));
        }
        if (StringUtils.isNotBlank(reg.getWhyStopped())) {
            isoDto.setReasonText(StConverter.convertToSt(reg.getWhyStopped()));
        }
        if (reg.getTrialStatusDate() != null) {
            isoDto.setStatusDate(TsConverter.convertToTs(reg
                    .getTrialStatusDate().toGregorianCalendar().getTime()));
        }
        isoDto.setIdentifier(null);
        isoDto.setSystemCreated(null);
        return isoDto;
    }

}
