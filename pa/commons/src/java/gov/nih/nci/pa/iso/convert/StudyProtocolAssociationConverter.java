/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolAssociation;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

/**
 * @author Denis G. Krylov
 * 
 */
public class StudyProtocolAssociationConverter
        extends
        AbstractConverter<StudyProtocolAssociationDTO, StudyProtocolAssociation> {

    @Override
    public StudyProtocolAssociation convertFromDtoToDomain(
            StudyProtocolAssociationDTO dto) throws PAException {
        StudyProtocolAssociation bo = new StudyProtocolAssociation();
        convertFromDtoToDomain(dto, bo);
        return bo;
    }

    @Override
    public void convertFromDtoToDomain(StudyProtocolAssociationDTO dto,
            StudyProtocolAssociation bo) throws PAException {
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setIdentifierType(CdConverter.convertCdToEnum(IdentifierType.class,
                dto.getIdentifierType()));
        bo.setOfficialTitle(StConverter.convertToString(dto.getOfficialTitle()));
        bo.setStudyIdentifier(StConverter.convertToString(dto
                .getStudyIdentifier()));
        bo.setStudyProtocolType(CdConverter.convertCdToEnum(
                StudyTypeCode.class, dto.getStudyProtocolType()));
        bo.setStudySubtypeCode(CdConverter.convertCdToEnum(
                StudySubtypeCode.class, dto.getStudySubtypeCode()));
        if (!ISOUtil.isIiNull(dto.getStudyProtocolA())) {
            StudyProtocol sp = new StudyProtocol();
            sp.setId(IiConverter.convertToLong(dto.getStudyProtocolA()));
            bo.setStudyProtocolA(sp);
        }
        if (!ISOUtil.isIiNull(dto.getStudyProtocolB())) {
            StudyProtocol sp = new StudyProtocol();
            sp.setId(IiConverter.convertToLong(dto.getStudyProtocolB()));
            bo.setStudyProtocolB(sp);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public StudyProtocolAssociationDTO convertFromDomainToDto(
            StudyProtocolAssociation bo) throws PAException {
        StudyProtocolAssociationDTO dto = new StudyProtocolAssociationDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setIdentifierType(CdConverter.convertToCd(bo.getIdentifierType()));
        dto.setOfficialTitle(StConverter.convertToSt(bo.getOfficialTitle()));
        dto.setStudyIdentifier(StConverter.convertToSt(bo.getStudyIdentifier()));
        dto.setStudyProtocolType(CdConverter.convertToCd(bo
                .getStudyProtocolType()));
        dto.setStudySubtypeCode(CdConverter.convertToCd(bo
                .getStudySubtypeCode()));
        
        if (bo.getStudyProtocolA() != null) {
            dto.setStudyProtocolA(IiConverter.convertToIi(bo
                    .getStudyProtocolA().getId()));
        }
        if (bo.getStudyProtocolB() != null) {
            dto.setStudyProtocolB(IiConverter.convertToIi(bo
                    .getStudyProtocolB().getId()));
        }
        return dto;
    }

}
