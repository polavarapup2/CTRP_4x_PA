package gov.nih.nci.pa.iso.convert;



import gov.nih.nci.pa.domain.PlannedMarkerSyncWithCaDSR;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;


/**
 * 
 * @author Reshma.Koganti
 * 
 */

public class PlannedMarkerSyncWithCaDSRConverter 
    extends AbstractConverter<PlannedMarkerSyncWithCaDSRDTO, PlannedMarkerSyncWithCaDSR> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSyncWithCaDSRDTO convertFromDomainToDto(PlannedMarkerSyncWithCaDSR pmSync) {
        return convertFromDomainToDTO(pmSync, new PlannedMarkerSyncWithCaDSRDTO());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSyncWithCaDSR convertFromDtoToDomain(PlannedMarkerSyncWithCaDSRDTO pmSyncDTO) {
        PlannedMarkerSyncWithCaDSR bo = new PlannedMarkerSyncWithCaDSR();
        convertFromDtoToDomain(pmSyncDTO, bo);
        return bo;
    } 
    
    /**
    *
    * @param bo PlannedActivity domain object
    * @param dto PlannedActivityDTO
    * @return dto PlannedActivityDTO
    */
   public static PlannedMarkerSyncWithCaDSRDTO convertFromDomainToDTO(
           PlannedMarkerSyncWithCaDSR bo, PlannedMarkerSyncWithCaDSRDTO dto) {
       if (bo != null) {
           dto.setName(StConverter.convertToSt(bo.getName())); 
           dto.setMeaning(StConverter.convertToSt(bo.getMeaning()));
           dto.setDescription(StConverter.convertToSt(bo.getDescription()));
           if (bo.getStatusCode() != null) {
               dto.setStatusCode(CdConverter.convertStringToCd(bo.getStatusCode().getName()));
           }
           dto.setCaDSRId(IiConverter.convertToIi(bo.getCaDSRId()));
           dto.setNtTermIdentifier(StConverter.convertToSt(bo.getNtTermIdentifier()));
           dto.setPvName(StConverter.convertToSt(bo.getPvName()));
           dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
       }
       return dto;
   }
    
   /**
    * {@inheritDoc}
    */
   @Override
   public void convertFromDtoToDomain(PlannedMarkerSyncWithCaDSRDTO dto, PlannedMarkerSyncWithCaDSR bo) {
       bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
       bo.setName(StConverter.convertToString(dto.getName()));
       bo.setMeaning(StConverter.convertToString(dto.getMeaning()));
       bo.setDescription(StConverter.convertToString(dto.getDescription()));
       bo.setCaDSRId(IiConverter.convertToLong(dto.getCaDSRId()));
       bo.setStatusCode(ActiveInactivePendingCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
       bo.setNtTermIdentifier(StConverter.convertToString(dto.getNtTermIdentifier()));
       bo.setPvName(StConverter.convertToString(dto.getPvName()));
   }
}
