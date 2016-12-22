package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.sql.Timestamp;
import java.util.Date;

public class StudyCheckoutConverterTest extends
        AbstractConverterTest<StudyCheckoutConverter, StudyCheckoutDTO, StudyCheckout> {

    private final Timestamp date1 = new Timestamp((new Date()).getTime());
    private final Timestamp date2 = new Timestamp((new Date()).getTime() + 1);

    @Override
    public StudyCheckout makeBo() {
        StudyCheckout bo = new StudyCheckout();
        bo.setId(ID);
        bo.setStudyProtocol(getStudyProtocol());
        bo.setCheckOutDate(date1);
        bo.setCheckOutType(CheckOutType.ADMINISTRATIVE);
        bo.setUserIdentifier("Test");
        bo.setCheckInDate(date2);
        bo.setCheckInComment("Test Comment");
        bo.setCheckInUserIdentifier("Test2");
        return bo;
    }

    @Override
    public StudyCheckoutDTO makeDto() {
        StudyCheckoutDTO dto = new StudyCheckoutDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setCheckOutDate(TsConverter.convertToTs(date1));
        dto.setCheckOutTypeCode(CdConverter.convertStringToCd(CheckOutType.ADMINISTRATIVE.getCode()));
        dto.setUserIdentifier(StConverter.convertToSt("Test"));
        dto.setCheckInDate(TsConverter.convertToTs(date2));
        dto.setCheckInComment(StConverter.convertToSt("Test Comment"));
        dto.setCheckInUserIdentifier(StConverter.convertToSt("Test2"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(STUDY_PROTOCOL_ID));
        return dto;
    }

    @Override
    public void verifyBo(StudyCheckout bo) {
        assertEquals(ID, bo.getId());
        assertEquals(date1, bo.getCheckOutDate());
        assertEquals(CheckOutType.ADMINISTRATIVE, bo.getCheckOutType());
        assertEquals("Test", bo.getUserIdentifier());
        assertEquals(date2, bo.getCheckInDate());
        assertEquals("Test Comment", bo.getCheckInComment());
        assertEquals("Test2", bo.getCheckInUserIdentifier());
        assertEquals(STUDY_PROTOCOL_ID, bo.getStudyProtocol().getId());
    }

    @Override
    public void verifyDto(StudyCheckoutDTO dto) {
        assertEquals(ID, IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(date1, TsConverter.convertToTimestamp(dto.getCheckOutDate()));
        assertEquals(CheckOutType.ADMINISTRATIVE.getCode(), CdConverter.convertCdToString(dto.getCheckOutTypeCode()));
        assertEquals("Test", dto.getUserIdentifier().getValue());
        assertEquals(date2, TsConverter.convertToTimestamp(dto.getCheckInDate()));
        assertEquals("Test Comment", StConverter.convertToString(dto.getCheckInComment()));
        assertEquals("Test2", dto.getCheckInUserIdentifier().getValue());
        assertEquals(STUDY_PROTOCOL_ID, IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }

}
