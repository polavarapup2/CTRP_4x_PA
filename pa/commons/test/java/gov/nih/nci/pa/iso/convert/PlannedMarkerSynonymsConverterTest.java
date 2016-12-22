package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.PlannedMarkerSyncWithCaDSR;
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

public class PlannedMarkerSynonymsConverterTest extends
AbstractConverterTest<PlannedMarkerSynonymsConverter, PlannedMarkerSynonymsDTO, PlannedMarkerSynonyms> {
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSynonyms makeBo() {
        PlannedMarkerSynonyms bo = new PlannedMarkerSynonyms();
        PlannedMarkerSyncWithCaDSR permissibleValue = new PlannedMarkerSyncWithCaDSR();
        permissibleValue.setCaDSRId(12345L);
        permissibleValue.setName("Biomarker");
        permissibleValue.setMeaning("Biomarker long name");
        permissibleValue.setDescription("desc");
        permissibleValue.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        permissibleValue.setId(ID);
        bo.setId(ID);
        bo.setAlternativeName("AlternativeName");
        bo.setPermissibleValue(permissibleValue);
        bo.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        return bo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerSynonymsDTO makeDto() {
        PlannedMarkerSynonymsDTO dto = new PlannedMarkerSynonymsDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setAlternativeName(StConverter.convertToSt("AlternativeName"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        return dto;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBo(PlannedMarkerSynonyms bo) {
        assertEquals(ID, bo.getId());
        assertEquals("AlternativeName", bo.getAlternativeName());
        assertEquals("Active", bo.getStatusCode().getCode());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyDto(PlannedMarkerSynonymsDTO dto) {
        assertEquals("AlternativeName", StConverter.convertToString(dto.getAlternativeName()));
        assertEquals("ACTIVE", CdConverter.convertCdToString(dto.getStatusCode()));
        assertEquals(ID, IiConverter.convertToLong(dto.getPermissibleValue()));
    }
}
