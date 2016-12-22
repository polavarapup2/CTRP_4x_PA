package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.PlannedMarkerSynonyms;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSynonymsDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * 
 * @author Reshma.Koganti
 * 
 */

public class PlannedMarkerSynonymsConverter 
extends AbstractConverter<PlannedMarkerSynonymsDTO, PlannedMarkerSynonyms> {
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSynonymsDTO convertFromDomainToDto(PlannedMarkerSynonyms pmSynonyms) {
        return convertFromDomainToDTO(pmSynonyms, new PlannedMarkerSynonymsDTO());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSynonyms convertFromDtoToDomain(PlannedMarkerSynonymsDTO pmSynDTO) {
        PlannedMarkerSynonyms bo = new PlannedMarkerSynonyms();
        convertFromDtoToDomain(pmSynDTO, bo);
        return bo;
    } 

    /**
    *
    * @param bo PlannedMarkerSynonyms domain object
    * @param dto PlannedMarkerSynonymsDTO
    * @return dto PlannedMarkerSynonymsDTO
    */
   public static PlannedMarkerSynonymsDTO convertFromDomainToDTO(
      PlannedMarkerSynonyms bo, PlannedMarkerSynonymsDTO dto) {
       if (bo != null) {
           dto.setAlternativeName(StConverter.convertToSt(bo.getAlternativeName())); 
           if (bo.getStatusCode() != null) {
               dto.setStatusCode(CdConverter.convertStringToCd(bo.getStatusCode().getName()));
           }
           if (bo.getPermissibleValue() != null) {
               dto.setPermissibleValue(IiConverter.convertToIi(bo.getPermissibleValue().getId()));
           }
       }
       return dto;
   }
    
   /**
    * {@inheritDoc}
    */
   @Override
   public void convertFromDtoToDomain(PlannedMarkerSynonymsDTO dto, PlannedMarkerSynonyms bo) {
       bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
       bo.setAlternativeName(StConverter.convertToString(dto.getAlternativeName()));
       bo.setStatusCode(ActiveInactivePendingCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
   }

}
