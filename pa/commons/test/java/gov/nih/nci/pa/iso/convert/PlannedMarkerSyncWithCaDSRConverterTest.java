package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
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
public class PlannedMarkerSyncWithCaDSRConverterTest 
    extends AbstractConverterTest<PlannedMarkerSyncWithCaDSRConverter, 
    PlannedMarkerSyncWithCaDSRDTO, PlannedMarkerSyncWithCaDSR> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSyncWithCaDSR makeBo() {
        PlannedMarkerSyncWithCaDSR bo = new PlannedMarkerSyncWithCaDSR();
        bo.setCaDSRId(12345L);
        bo.setName("name");
        bo.setMeaning("meaning");
        bo.setDescription("description");
        bo.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        bo.setId(ID);
        return bo;    
    }
    
    /**
    * {@inheritDoc}
    */
   @Override
   public PlannedMarkerSyncWithCaDSRDTO makeDto() {
       PlannedMarkerSyncWithCaDSRDTO dto = new PlannedMarkerSyncWithCaDSRDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setName(StConverter.convertToSt("name"));
        dto.setMeaning(StConverter.convertToSt("meaning"));
        dto.setDescription(StConverter.convertToSt("description"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        return dto;
    }

   /**
    * {@inheritDoc}
    */
   @Override
   public void verifyBo(PlannedMarkerSyncWithCaDSR bo) {
       assertEquals(ID, bo.getId());
       assertEquals("name", bo.getName());
      
       assertEquals("meaning", bo.getMeaning());
       assertEquals("description", bo.getDescription());
       
   }

    @Override
    public void verifyDto(PlannedMarkerSyncWithCaDSRDTO dto) {
       
        assertEquals("name", StConverter.convertToString(dto.getName()));
        assertEquals("meaning", StConverter.convertToString(dto.getMeaning()));
        assertEquals("description", StConverter.convertToString(dto.getDescription()));
        
    }
}
