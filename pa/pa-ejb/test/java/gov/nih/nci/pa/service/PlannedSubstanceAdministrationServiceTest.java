package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PlannedSubstanceAdministrationServiceTest extends AbstractHibernateTestCase {
    private PlannedSubstanceAdministrationServiceRemote remoteEjb =  new PlannedSubstanceAdministrationServiceBean();
    private Ii spIi;

    @Before
    public void setUp() throws Exception {
        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void getPlannedSubstanceAdministration() throws Exception {
        List<PlannedSubstanceAdministrationDTO> statusList =
            remoteEjb.getPlannedSubstanceAdministrationByStudyProtocol(spIi);
        assertEquals(1, statusList.size());

        PlannedSubstanceAdministrationDTO dto =
            remoteEjb.getPlannedSubstanceAdministration(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));

        PlannedSubstanceAdministrationDTO dto2 = new PlannedSubstanceAdministrationDTO();
        dto2 = remoteEjb.updatePlannedSubstanceAdministration(dto);
        assertEquals(dto.getDoseDescription().getValue(), dto2.getDoseDescription().getValue());
        remoteEjb.deletePlannedSubstanceAdministration(dto.getIdentifier());
    }

    @Test
    public void createPlannedSubstanceAdministration() throws Exception {
        PlannedSubstanceAdministrationDTO dto = new PlannedSubstanceAdministrationDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDoseDescription(StConverter.convertToSt("Dose"));
        dto.setDoseRegimen(StConverter.convertToSt(">"));
        IvlConverter.JavaPq low  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("2"), null);
        IvlConverter.JavaPq high  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("8"), null);
        Ivl<Pq> ivl = IvlConverter.convertPq().convertToIvl(low, high);
        dto.setDose(ivl);
        dto.setDoseFormCode(CdConverter.convertStringToCd("TABLET"));
        dto.setDoseFrequencyCode(CdConverter.convertStringToCd("BID"));
        dto.setRouteOfAdministrationCode(CdConverter.convertStringToCd("ORAL"));
        PlannedSubstanceAdministrationDTO newDTO = remoteEjb.createPlannedSubstanceAdministration(dto);
        assertEquals(newDTO.getStudyProtocolIdentifier(), spIi);
        assertNotNull(newDTO.getIdentifier());
    }
}
