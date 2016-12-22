/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteOverallStatus;
import gov.nih.nci.pa.enums.StudySiteStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.Date;

/**
 * @author Vrushali
 * 
 */
public class StudySiteOverallStatusConverter extends
        AbstractConverter<StudySiteOverallStatusDTO, StudySiteOverallStatus> {

    /**
     *{@inheritDoc}
     */
    @Override
    public StudySiteOverallStatusDTO convertFromDomainToDto(StudySiteOverallStatus bo) {
        StudySiteOverallStatusDTO dto = new StudySiteOverallStatusDTO();
        dto.setIdentifier(IiConverter.convertToStudySiteOverallStatusIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDate(TsConverter.convertToTs(bo.getStatusDate()));
        dto.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(bo.getStudySite().getId()));
        return dto;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public StudySiteOverallStatus convertFromDtoToDomain(StudySiteOverallStatusDTO dto) throws PAException {
        StudySiteOverallStatus bo = new StudySiteOverallStatus();
        convertFromDtoToDomain(dto, bo);
        return bo;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(StudySiteOverallStatusDTO dto, StudySiteOverallStatus bo) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            String errmsg = " convertFromDtoToDomain has been implemented for new domain"
                    + " objects only.  StudySiteOverallStatusDTO.ii must be null. ";
            throw new PAException(errmsg);
        }
        if (ISOUtil.isIiNull(dto.getStudySiteIdentifier())) {
            String errmsg = " StudySiteOverallStatus.studySite cannot be null. ";
            throw new PAException(errmsg);
        }

        StudySite spBo = new StudySite();
        spBo.setId(IiConverter.convertToLong(dto.getStudySiteIdentifier()));

        bo.setDateLastUpdated(new Date());
        bo.setStatusCode(StudySiteStatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
        bo.setStudySite(spBo);
    }

}
