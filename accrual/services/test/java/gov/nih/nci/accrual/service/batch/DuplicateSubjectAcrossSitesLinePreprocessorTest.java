/**
 * 
 */
package gov.nih.nci.accrual.service.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.FlaggedTrialServiceRemote;
import gov.nih.nci.pa.util.pomock.MockIdentifiedOrganizationCorrelationService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author dkrylov
 * 
 */
public class DuplicateSubjectAcrossSitesLinePreprocessorTest {

    protected DuplicateSubjectAcrossSitesLinePreprocessor processor;
    
    FlaggedTrialServiceRemote flaggedService;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        processor = new DuplicateSubjectAcrossSitesLinePreprocessor();

        // PA Locator.
        ServiceLocatorPaInterface serviceLocator = mock(ServiceLocatorPaInterface.class);
        PaServiceLocator.getInstance().setServiceLocator(serviceLocator);

        // Accrual Locator
        ServiceLocatorAccInterface accrualLocator = mock(ServiceLocatorAccInterface.class);
        AccrualServiceLocator.getInstance().setServiceLocator(accrualLocator);

        // PO
        MockOrganizationEntityService.reset(1, true);
        PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        when(poServiceLocator.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());
        when(poServiceLocator.getIdentifiedOrganizationEntityService())
                .thenReturn(new MockIdentifiedOrganizationCorrelationService());
        PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
        
        // Flagged Trials.
        flaggedService = mock(FlaggedTrialServiceRemote.class);
        when(
                flaggedService
                        .isFlagged(
                                any(StudyProtocolDTO.class),
                                eq(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES)))
                .thenReturn(false);
        when(serviceLocator.getFlaggedTrialService())
                .thenReturn(flaggedService);      

        // Protocol.
        StudyProtocolServiceRemote studyProtocolServiceRemote = mock(StudyProtocolServiceRemote.class);
        when(serviceLocator.getStudyProtocolService()).thenReturn(
                studyProtocolServiceRemote);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(
                studyProtocolServiceRemote.loadStudyProtocol(eq(IiConverter
                        .convertToAssignedIdentifierIi("NCI-2014-0001"))))
                .thenReturn(spDTO);

        // Sites
        ParticipatingSiteServiceRemote psRemote = mock(ParticipatingSiteServiceRemote.class);
        when(serviceLocator.getParticipatingSiteServiceRemote()).thenReturn(
                psRemote);
        ParticipatingSiteDTO site1 = new ParticipatingSiteDTO();
        site1.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        site1.setStudyProtocolIdentifier(spDTO.getIdentifier());
        final StudySite ss1 = new StudySite();
        ss1.setId(1L);
        ss1.setStudyProtocol(sp);
        ss1.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(1L);
        Organization org = new Organization();
        org.setId(1L);
        org.setIdentifier(MockOrganizationEntityService.CTEP_ID + "");
        hcf.setOrganization(org);
        ss1.setHealthCareFacility(hcf);

        ParticipatingSiteDTO site2 = new ParticipatingSiteDTO();
        site2.setIdentifier(IiConverter.convertToStudySiteIi(2L));
        site2.setStudyProtocolIdentifier(spDTO.getIdentifier());
        final StudySite ss2 = new StudySite();
        ss2.setId(2L);
        ss2.setStudyProtocol(sp);
        ss2.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        hcf = new HealthCareFacility();
        hcf.setId(2L);
        org = new Organization();
        org.setId(2L);
        org.setIdentifier(MockOrganizationEntityService.DCP_ID + "");
        hcf.setOrganization(org);
        ss2.setHealthCareFacility(hcf);
        when(
                psRemote.getParticipatingSitesByStudyProtocol(eq(spDTO
                        .getIdentifier()))).thenReturn(
                Arrays.asList(site1, site2));

        // Study Subject & Search.
        StudySubjectServiceLocal studySubjectServiceLocal = mock(StudySubjectServiceLocal.class);
        when(accrualLocator.getStudySubjectService()).thenReturn(
                studySubjectServiceLocal);
        final StudySubject subject = new StudySubject();
        subject.setId(1L);
        subject.setAssignedIdentifier("FHCRC 2000.013");
        subject.setStudySite(ss1);
        subject.setStudyProtocol(sp);

        Patient p = new Patient();
        p.setBirthDate(AccrualUtil.yearMonthStringToTimestamp("196106"));
        p.setEthnicCode(PatientEthnicityCode.HISPANIC);
        p.setRaceCode("WHITE,NOT_REPORTED");
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setZip("20170");
        subject.setPatient(p);

        when(studySubjectServiceLocal.search(any(SearchSSPCriteriaDto.class)))
                .thenAnswer(new Answer<List<StudySubject>>() {
                    @Override
                    public List<StudySubject> answer(InvocationOnMock invocation)
                            throws Throwable {
                        SearchSSPCriteriaDto crit = (SearchSSPCriteriaDto) invocation
                                .getArguments()[0];
                        if (subject
                                .getPatient()
                                .getBirthDate()
                                .equals(AccrualUtil
                                        .yearMonthStringToTimestamp(crit
                                                .getPatientBirthDate()))
                                && subject
                                        .getAssignedIdentifier()
                                        .toUpperCase()
                                        .contains(
                                                crit.getStudySubjectAssignedIdentifier()
                                                        .toUpperCase())
                                && crit.getStudySiteIds().contains(
                                        subject.getStudySite().getId())) {
                            return Arrays.asList(subject);

                        }
                        return new ArrayList<StudySubject>();
                    }
                });

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExceptionsDoNotPropagate() throws IOException {

        when(
                PaServiceLocator
                        .getInstance()
                        .getServiceLocator()
                        .getStudyProtocolService()
                        .loadStudyProtocol(
                                eq(IiConverter
                                        .convertToAssignedIdentifierIi("NCI-2014-0001"))))
                .thenThrow(new RuntimeException());

        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196107 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExceptionsDoNotPropagate2() throws IOException, PAException {

        when(
                PaServiceLocator.getInstance().getServiceLocator()
                        .getParticipatingSiteServiceRemote()
                        .getParticipatingSitesByStudyProtocol(any(Ii.class)))
                .thenThrow(new PAException());

        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196107 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDupeRemoval() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                                "PATIENT_RACES,NCI-2014-00416,A0009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        assertFalse(file.equals(result.getPreprocessedFile()));
        assertFalse(result.getValidationErrors().isEmpty());

        File filtered = result.getPreprocessedFile();
        filtered.deleteOnExit();

        List<String> lines = FileUtils.readLines(filtered, "UTF-8");
        assertEquals("\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,", lines.get(0));
        assertEquals("", lines.get(1));
        assertEquals(
                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,DCP,,,,,,,,,,10029462,,",
                lines.get(2));
        assertEquals("", lines.get(3));
        assertEquals("PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                lines.get(4));
        assertEquals("PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                lines.get(5));
        assertEquals("PATIENT_RACES,NCI-2014-00416,A0009,Black", lines.get(6));

        assertEquals(1, result.getValidationErrors().size());
        ValidationError error = result.getValidationErrors().get(0);
        assertEquals(
                "The following lines contain Patient IDs already in use by Patients registered on a different site for "
                        + "this study with matching Gender, Date of Birth, Race, and Ethnicity. "
                        + "Patients were not processed:",
                error.getErrorMessage());
        assertEquals(
                "Line 2: Patient ID FHCRC 2000.013 is already registered at "
                        + "Cancer Therapy Evaluation Program, Site PO ID: "
                        + MockOrganizationEntityService.CTEP_ID
                        + ", Site CTEP ID: CTEP", error.getErrorDetails()
                        .get(0));

    }
    
    @Test
    public void testFlaggedTrial() throws IOException, PAException {
        
        when(
                flaggedService
                        .isFlagged(
                                any(StudyProtocolDTO.class),
                                eq(StudyFlagReasonCode.DO_NOT_ENFORCE_UNIQUE_SUBJECTS_ACCROSS_SITES)))
                .thenReturn(true);
        
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                                "PATIENT_RACES,NCI-2014-00416,A0009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        assertTrue(file.equals(result.getPreprocessedFile()));
        assertTrue(result.getValidationErrors().isEmpty());


    }

    @SuppressWarnings("unchecked")
    @Test
    public void testWhenSiteHasNoCtepId() throws IOException {

        MockOrganizationEntityService.PO_ID_TO_CTEP_ID
                .remove(MockOrganizationEntityService.CTEP_ID + "");

        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                                "PATIENT_RACES,NCI-2014-00416,A0009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        ValidationError error = result.getValidationErrors().get(0);
        assertEquals(
                "Line 2: Patient ID FHCRC 2000.013 is already registered at "
                        + "Cancer Therapy Evaluation Program, Site PO ID: "
                        + MockOrganizationEntityService.CTEP_ID
                        + ", Site CTEP ID: N/A", error.getErrorDetails().get(0));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCdusCodesHandledProperly() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,2,1,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01",
                                "PATIENT_RACES,NCI-2014-00416,A0009,03" }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();

        assertEquals(1, result.getValidationErrors().size());
        ValidationError error = result.getValidationErrors().get(0);
        assertEquals(
                "The following lines contain Patient IDs already in use by Patients registered on a different site for "
                        + "this study with matching Gender, Date of Birth, Race, and Ethnicity. "
                        + "Patients were not processed:",
                error.getErrorMessage());
        assertEquals(
                "Line 2: Patient ID FHCRC 2000.013 is already registered at "
                        + "Cancer Therapy Evaluation Program, Site PO ID: "
                        + MockOrganizationEntityService.CTEP_ID
                        + ", Site CTEP ID: CTEP", error.getErrorDetails()
                        .get(0));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSiteByPoId() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , 2 , 1 ,,20110513,, "
                                        + MockOrganizationEntityService.DCP_ID
                                        + " ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,2,1,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01",
                                "PATIENT_RACES,NCI-2014-00416,A0009,03" }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();

        assertEquals(1, result.getValidationErrors().size());
        ValidationError error = result.getValidationErrors().get(0);
        assertEquals(
                "The following lines contain Patient IDs already in use by Patients registered on a different site for "
                        + "this study with matching Gender, Date of Birth, Race, and Ethnicity. "
                        + "Patients were not processed:",
                error.getErrorMessage());
        assertEquals(
                "Line 2: Patient ID FHCRC 2000.013 is already registered at "
                        + "Cancer Therapy Evaluation Program, Site PO ID: "
                        + MockOrganizationEntityService.CTEP_ID
                        + ", Site CTEP ID: CTEP", error.getErrorDetails()
                        .get(0));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDiffSubjectIdCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.01 ,77058,, 196106 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDiffDobCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196107 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDiffGenderCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , 1 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,98",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDiffEthnicityCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Unknown ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSameSiteCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, CTEP ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @Test
    public void testBlankProtocol() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,DCP,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                                "PATIENT_RACES,NCI-2014-00416,A0009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @Test
    public void testBlankSubjectID() throws IOException {
        File file = createTempFile();

        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,,77058,, 196106 , Female , Hispanic or Latino ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENTS, NCI-2014-0001, A0009 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,,,,,,,,,,,10029462,,",
                                "",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,Not Reported",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,White",
                                "PATIENT_RACES,NCI-2014-00416,A0009,Black" }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDiffRaceCausesNoMatch() throws IOException {
        File file = createTempFile();
        FileUtils
                .writeLines(
                        file,
                        "UTF-8",
                        Arrays.asList(new String[] {
                                "\uFEFFCOLLECTIONS,NCI-2014-0001,,,,,,,,,",
                                "PATIENTS, NCI-2014-0001 ,fhCRC 2000.013,77058,, 196106 , 2 , 1 ,,20110513,, dcp ,,,,,,,,,,10029462,,",
                                "PATIENT_RACES,NCI-2014-0001,fhCRC 2000.013,01", }));

        PreprocessingResult result = processor.preprocess(file);
        result.getPreprocessedFile().deleteOnExit();
        assertEquals(0, result.getValidationErrors().size());
        assertEquals(file, result.getPreprocessedFile());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.service.batch.DuplicateSubjectLinePreprocessor#preprocess(java.io.File)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public void testEmptyFile() throws IOException {
        File file = createTempFile();
        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());

    }

    @Test
    public void testJunkFile() throws IOException {
        File file = createTempFile();

        byte[] data = new byte[(int) FileUtils.ONE_MB];
        new Random().nextBytes(data);
        FileUtils.writeByteArrayToFile(file, data);

        PreprocessingResult result = processor.preprocess(file);
        assertEquals(file, result.getPreprocessedFile());
        assertTrue(result.getValidationErrors().isEmpty());

    }

    /**
     * @return
     * @throws IOException
     */
    private File createTempFile() throws IOException {
        File file = File.createTempFile(UUID.randomUUID().toString(), "txt");
        file.deleteOnExit();
        return file;
    }

}
