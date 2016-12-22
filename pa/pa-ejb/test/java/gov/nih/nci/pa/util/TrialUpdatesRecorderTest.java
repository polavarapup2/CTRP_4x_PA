/**
 * 
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class TrialUpdatesRecorderTest {

    private ServiceLocator paSvcLoc;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);

        StudySiteServiceLocal studySiteSrv = mock(StudySiteServiceLocal.class);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteSrv);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setResearchOrganizationIi(IiConverter
                .convertToPoResearchOrganizationIi("abc"));
        studySiteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("CODE2"));
        when(studySiteSrv.get(eq(IiConverter.convertToStudySiteIi(1L))))
                .thenReturn(studySiteDTO);

        StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
        when(paSvcLoc.getStudyOverallStatusService()).thenReturn(
                studyOverallStatusService);
        when(
                studyOverallStatusService.isTrialStatusOrDateChanged(
                        any(StudyOverallStatusDTO.class), any(Ii.class)))
                .thenReturn(true);
        
        StudyResourcingServiceLocal studyResourcingServiceLocal = mock(StudyResourcingServiceLocal.class);
        when(paSvcLoc.getStudyResoucringService()).thenReturn(studyResourcingServiceLocal);
        StudyResourcingDTO studyResDto = new StudyResourcingDTO();
        Cd cd = new Cd();
        cd.setCode("U10");
        studyResDto.setFundingMechanismCode(cd);
        cd = new Cd();
        cd.setCode("Abbreviated");
        studyResDto.setTypeCode(cd);
        cd = new Cd();
        cd.setCode("CA");
        studyResDto.setNihInstitutionCode(cd);
        studyResDto.setNciDivisionProgramCode(cd);
        studyResDto.setSerialNumber(StConverter.convertToSt("177"));
        when(studyResourcingServiceLocal.get(eq(IiConverter.convertToStudyResourcingIi(1L)))).thenReturn(studyResDto);

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#reset()}.
     */
    @Test
    public final void testReset() {
        TrialUpdatesRecorder.reset();
        TrialUpdatesRecorder.recordUpdate("TEST");
        TrialUpdatesRecorder.reset();
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(java.util.Collection, java.util.Collection, java.lang.String)}
     * .
     */
    @Test
    public final void testRecordUpdateCollectionCollectionString() {
        TrialUpdatesRecorder.reset();
        TrialUpdatesRecorder.recordUpdate((Collection) null, (Collection) null,
                "TEST");
        TrialUpdatesRecorder.recordUpdate(new ArrayList(), (Collection) null,
                "TEST");
        TrialUpdatesRecorder.recordUpdate(new ArrayList(), new ArrayList(),
                "TEST");
        TrialUpdatesRecorder.recordUpdate(null, new ArrayList(), "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());

        TrialUpdatesRecorder.recordUpdate(Arrays.asList("1"),
                Arrays.asList("1", "2"), "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(gov.nih.nci.iso21090.Ts, gov.nih.nci.iso21090.Ts, java.lang.String)}
     * .
     */
    @Test
    public final void testRecordUpdateTsTsString() {
        TrialUpdatesRecorder.reset();
        TrialUpdatesRecorder.recordUpdate((Ts) null, (Ts) null, "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());

        Ts ts1 = TsConverter.convertToTs(new Date());
        Ts ts2 = TsConverter.convertToTs(new Date());
        TrialUpdatesRecorder.recordUpdate(ts1, ts2, "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());

    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(gov.nih.nci.iso21090.Cd, gov.nih.nci.iso21090.Cd, java.lang.String)}
     * .
     */
    @Test
    public final void testRecordUpdateCdCdString() {
        TrialUpdatesRecorder.reset();
        TrialUpdatesRecorder.recordUpdate((Cd) null, (Cd) null, "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());

        Cd cd1 = CdConverter.convertStringToCd("cd1");
        Cd cd2 = CdConverter.convertStringToCd("cd2");
        TrialUpdatesRecorder.recordUpdate(cd1, cd1, "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());
        TrialUpdatesRecorder.recordUpdate(cd1, cd2, "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(java.util.List, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testRecordUpdateListOfStudyResourcingDTOString()
            throws PAException {
        TrialUpdatesRecorder.reset();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        TrialUpdatesRecorder.recordUpdate(studyResourcingDTOs, "TEST");
        assertTrue(TrialUpdatesRecorder.getRecordedUpdates().isEmpty());

        StudyResourcingDTO studyResDto = new StudyResourcingDTO();
        Cd cd = new Cd();
        cd.setCode("U10");
        studyResDto.setFundingMechanismCode(cd);
        cd = new Cd();
        cd.setCode("Abbreviated");
        studyResDto.setTypeCode(cd);
        cd = new Cd();
        cd.setCode("CA");
        studyResDto.setNihInstitutionCode(cd);
        studyResDto.setNciDivisionProgramCode(cd);
        studyResDto.setSerialNumber(StConverter.convertToSt("177"));
        studyResourcingDTOs.add(studyResDto);

        TrialUpdatesRecorder.recordUpdate(studyResourcingDTOs, "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());
        
        TrialUpdatesRecorder.reset();
        studyResDto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        TrialUpdatesRecorder.recordUpdate(studyResourcingDTOs, "TEST");
        assertEquals("", TrialUpdatesRecorder.getRecordedUpdates());
        
        TrialUpdatesRecorder.reset();
        cd = new Cd();
        cd.setCode("ZU");
        studyResDto.setNciDivisionProgramCode(cd);
        TrialUpdatesRecorder.recordUpdate(studyResourcingDTOs, "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(java.lang.String)}
     * .
     */
    @Test
    public final void testRecordUpdateString() {
        TrialUpdatesRecorder.reset();
        TrialUpdatesRecorder.recordUpdate("TEST1");
        TrialUpdatesRecorder.recordUpdate("TEST2");
        assertEquals("TEST1\rTEST2", TrialUpdatesRecorder.getRecordedUpdates());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordParticipatingSiteUpdate(java.util.List, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testRecordParticipatingSiteUpdate() throws PAException {
        TrialUpdatesRecorder.reset();

        List<StudySiteDTO> studySiteDTOs = new ArrayList<StudySiteDTO>();
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        siteDTO.setProgramCodeText(StConverter.convertToSt("CODE1"));
        studySiteDTOs.add(siteDTO);

        TrialUpdatesRecorder.recordParticipatingSiteUpdate(studySiteDTOs,
                "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());
        
        TrialUpdatesRecorder.reset();

        studySiteDTOs = new ArrayList<StudySiteDTO>();
        siteDTO = new StudySiteDTO();
        siteDTO.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        siteDTO.setProgramCodeText(StConverter.convertToSt("CODE2"));
        studySiteDTOs.add(siteDTO);

        TrialUpdatesRecorder.recordParticipatingSiteUpdate(studySiteDTOs,
                "TEST");
        assertEquals("", TrialUpdatesRecorder.getRecordedUpdates());        
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.TrialUpdatesRecorder#recordUpdate(gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testRecordUpdateStudyOverallStatusDTOString()
            throws PAException {
        TrialUpdatesRecorder.reset();
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        TrialUpdatesRecorder.recordUpdate(overallStatusDTO, "TEST");
        assertEquals("TEST", TrialUpdatesRecorder.getRecordedUpdates());
    }

}
