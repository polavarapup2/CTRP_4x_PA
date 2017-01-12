package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyAccrualAccess;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.iso.dto.StudyAccrualAccessDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.sql.Timestamp;
import java.util.Date;

public class StudyAccrualAccessConverterTest extends AbstractConverterTest<StudyAccrualAccessConverter, StudyAccrualAccessDTO, StudyAccrualAccess> {

    private final Date now = new Date();

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyAccrualAccess makeBo() {
        StudyAccrualAccess bo = new StudyAccrualAccess();
        bo.setId(ID);
        bo.setRegistryUser(getRegistryUser());
        bo.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        bo.setComments("Request Details");
        bo.setStatusCode(ActiveInactiveCode.ACTIVE);
        bo.setStatusDateRangeLow(new Timestamp(now.getTime()));
        bo.setActionCode(AssignmentActionCode.ASSIGNED);
        bo.setStudyProtocol(getStudyProtocol());
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyAccrualAccessDTO makeDto() {
        StudyAccrualAccessDTO dto = new StudyAccrualAccessDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(now));
        dto.setSource(CdConverter.convertToCd(AccrualAccessSourceCode.PA_SITE_REQUEST));
        dto.setComments(StConverter.convertToSt("Request Details"));
        dto.setRegistryUserIdentifier(IiConverter.convertToIi(REGISTRY_USER_ID));
        dto.setActionCode(CdConverter.convertToCd(AssignmentActionCode.ASSIGNED));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(STUDY_PROTOCOL_ID));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBo(StudyAccrualAccess bo) {
        assertEquals(ID, bo.getId());
        assertEquals(REGISTRY_USER_ID, bo.getRegistryUser().getId());
        assertEquals(AccrualAccessSourceCode.PA_SITE_REQUEST, bo.getSource());
        assertEquals("Request Details", bo.getComments());
        assertEquals(ActiveInactiveCode.ACTIVE, bo.getStatusCode());
        assertEquals(now, bo.getStatusDateRangeLow());
        assertEquals(AssignmentActionCode.ASSIGNED, bo.getActionCode());
        assertEquals(STUDY_PROTOCOL_ID, bo.getStudyProtocol().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyDto(StudyAccrualAccessDTO dto) {
        assertEquals(ID, IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(REGISTRY_USER_ID, IiConverter.convertToLong(dto.getRegistryUserIdentifier()));
        assertEquals(ActiveInactiveCode.ACTIVE.getCode(), dto.getStatusCode().getCode());
        assertEquals(now, dto.getStatusDate().getValue());
        assertEquals(AccrualAccessSourceCode.PA_SITE_REQUEST.getCode(), dto.getSource().getCode());
        assertEquals("Request Details", StConverter.convertToString(dto.getComments()));
        assertEquals(AssignmentActionCode.ASSIGNED.getCode(), dto.getActionCode().getCode());
        assertEquals(STUDY_PROTOCOL_ID, IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }
}
