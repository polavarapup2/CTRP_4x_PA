/**
 * 
 */
package gov.nih.nci.accrual.webservices;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.util.AccrualDiseaseServiceLocal;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.webservices.types.BatchFile;
import gov.nih.nci.accrual.webservices.types.ObjectFactory;
import gov.nih.nci.accrual.webservices.types.StudySubject;
import gov.nih.nci.accrual.webservices.types.StudySubjects;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.util.pomock.MockIdentifiedOrganizationCorrelationService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author dkrylov
 * 
 */
public class AccrualServiceTest {

    static {
        try {
            final ConsoleAppender appender = new ConsoleAppender(
                    new SimpleLayout());
            appender.setThreshold(Priority.INFO);
            Logger.getRootLogger().addAppender(appender);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private AccrualService service = new AccrualService();
    ServiceLocatorAccInterface serviceLocatorAccInterface = mock(ServiceLocatorAccInterface.class);

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        ServiceLocatorPaInterface serviceLocatorPaInterface = mock(ServiceLocatorPaInterface.class);

        AccrualServiceLocator.getInstance().setServiceLocator(
                serviceLocatorAccInterface);
        PaServiceLocator.getInstance().setServiceLocator(
                serviceLocatorPaInterface);

        StudySiteServiceRemote studySiteServiceRemote = mock(StudySiteServiceRemote.class);
        when(serviceLocatorPaInterface.getStudySiteService()).thenReturn(
                studySiteServiceRemote);

        StudySiteDTO site = new StudySiteDTO();
        site.setIdentifier(IiConverter.convertToStudySiteIi(12345L));
        site.setStudyProtocolIdentifier(IiConverter.convertToStudySiteIi(1L));
        when(studySiteServiceRemote.get(any(Ii.class))).thenReturn(site);

        SubjectAccrualServiceLocal subjectAccrualServiceLocal = mock(SubjectAccrualServiceLocal.class);
        when(serviceLocatorAccInterface.getSubjectAccrualService()).thenReturn(
                subjectAccrualServiceLocal);

        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.getStudyProtocol((Ii) anyObject())).thenReturn(setupSpDto());
        when(serviceLocatorPaInterface.getStudyProtocolService()).thenReturn(
                spSvc);

        final ParticipatingSiteServiceRemote participatingSiteServiceLocal = mock(ParticipatingSiteServiceRemote.class);
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class))).thenReturn(site);
        when(serviceLocatorPaInterface.getParticipatingSiteServiceRemote())
                .thenReturn(participatingSiteServiceLocal);

        PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        when(poServiceLocator.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());
        when(poServiceLocator.getIdentifiedOrganizationEntityService())
                .thenReturn(new MockIdentifiedOrganizationCorrelationService());
        PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);

        StudySubjectServiceLocal studySubjectServiceLocal = mock(StudySubjectServiceLocal.class);
        StudySubjectDto ssDto = new StudySubjectDto();
        ssDto.setAssignedIdentifier(StConverter.convertToSt("SU001"));
        ssDto.setIdentifier(IiConverter.convertToIi(892374L));
        when(
                studySubjectServiceLocal.getStudySubjects(eq("SU001"),
                        eq(new Long(12345)), (Date) isNull())).thenReturn(
                Arrays.asList(ssDto));
        when(serviceLocatorAccInterface.getStudySubjectService()).thenReturn(
                studySubjectServiceLocal);

        AccrualDiseaseServiceLocal accrualDiseaseServiceLocal = mock(AccrualDiseaseServiceLocal.class);
        AccrualDisease disease = new AccrualDisease();
        disease.setCodeSystem("ICD-O-3");
        disease.setDiseaseCode("C34.1");
        disease.setId(1L);
        when(
                accrualDiseaseServiceLocal.getByCode(any(String.class),
                        any(String.class))).thenReturn(disease);
        when(serviceLocatorAccInterface.getAccrualDiseaseService()).thenReturn(
                accrualDiseaseServiceLocal);

    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#updateAccrualCount(long, int)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testUpdateAccrualBySiteId() throws PAException {
        Response r = service.updateAccrualCount(12345L, 89234);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());

        verifyAccrualCountMethodCalls();

    }

    /**
     * @throws PAException
     */
    private void verifyAccrualCountMethodCalls() throws PAException {
        ArgumentCaptor<Ii> participatingSiteIi = ArgumentCaptor
                .forClass(Ii.class);
        ArgumentCaptor<Int> count = ArgumentCaptor.forClass(Int.class);
        ArgumentCaptor<AccrualSubmissionTypeCode> submissionType = ArgumentCaptor
                .forClass(AccrualSubmissionTypeCode.class);
        verify(AccrualServiceLocator.getInstance().getSubjectAccrualService())
                .updateSubjectAccrualCount(participatingSiteIi.capture(),
                        count.capture(), submissionType.capture());

        assertEquals(Long.valueOf(12345L),
                IiConverter.convertToLong(participatingSiteIi.getValue()));
        assertEquals(Integer.valueOf(89234),
                IntConverter.convertToInteger(count.getValue()));
        assertEquals(AccrualSubmissionTypeCode.SERVICE,
                submissionType.getValue());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#updateAccrualCount(java.lang.String, java.lang.String, long, int)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testUpdateAccrualCountByPoId() throws PAException {
        Response r = service.updateAccrualCount("nci", "NCI-2014-00001", 1,
                89234);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyAccrualCountMethodCalls();

    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#updateAccrualCount(java.lang.String, java.lang.String, java.lang.String, int)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testUpdateAccrualCountByCtepId() throws PAException {
        Response r = service.updateAccrualCount("nci", "NCI-2014-00001", "DCP",
                89234);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyAccrualCountMethodCalls();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#updateAccrualCount(java.lang.String, java.lang.String, java.lang.String, int)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testUpdateAccrualCountByDcpId() throws PAException {
        Response r = service.updateAccrualCount("dcp", "NCI-2014-00001", "DCP",
                89234);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyAccrualCountMethodCalls();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#deleteStudySubject(long, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testDeleteStudySubjectBySiteAndSubjectID()
            throws PAException {
        Response r = service.deleteStudySubject(1, "SU001");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyDeleteMethodCalls();
    }

    private void verifyDeleteMethodCalls() throws PAException {
        ArgumentCaptor<Ii> subjectAccrualIi = ArgumentCaptor.forClass(Ii.class);
        ArgumentCaptor<String> deleteReason = ArgumentCaptor
                .forClass(String.class);

        verify(AccrualServiceLocator.getInstance().getSubjectAccrualService())
                .deleteSubjectAccrual(subjectAccrualIi.capture(),
                        deleteReason.capture());

        assertEquals(Long.valueOf(892374L),
                IiConverter.convertToLong(subjectAccrualIi.getValue()));
        assertEquals("", deleteReason.getValue());

    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#deleteStudySubject(java.lang.String, java.lang.String, long, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testDeleteStudySubjectByPoId() throws PAException {
        Response r = service.deleteStudySubject("nci", "NCI-2014-0001", 1,
                "SU001");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyDeleteMethodCalls();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#deleteStudySubject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testDeleteStudySubjectByCtepId() throws PAException {
        Response r = service.deleteStudySubject("nci", "NCi-2014-0001", "CTEP",
                "SU001");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyDeleteMethodCalls();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#submitStudySubjects(long, gov.nih.nci.accrual.webservices.types.StudySubjects)}
     * .
     * 
     * @throws JAXBException
     * @throws PAException
     */
    @Test
    public final void testSubmitStudySubjects() throws JAXBException,
            PAException {
        StudySubjects reg = readStudySubjectsFromFile("/add_update_subject_icdo3.xml");
        Response r = service.submitStudySubjects(12345L, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyManageSubjectsCall(reg);
    }

    /**
     * .
     * 
     * @throws JAXBException
     * @throws PAException
     */
    @Test
    public final void testSubmitStudySubjectsDisease() throws JAXBException,
            PAException {
        StudySubjects reg = readStudySubjectsFromFile("/subject_bad_disease.xml");
        Response r = service.submitStudySubjects(123L, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        ArgumentCaptor<List> subjectsCaptor = ArgumentCaptor
                .forClass(List.class);
        verify(AccrualServiceLocator.getInstance().getSubjectAccrualService())
                .manageSubjectAccruals(subjectsCaptor.capture());
        List<SubjectAccrualDTO> subjects = subjectsCaptor.getValue();
        assertEquals(1, subjects.size());

        StudySubject xmlSubject = reg.getStudySubject().get(0);
        SubjectAccrualDTO dto = subjects.get(0);
        assertEquals(xmlSubject.getIdentifier(),
                StConverter.convertToString(dto.getAssignedIdentifier()));
        assertEquals(xmlSubject.getBirthDate().toGregorianCalendar().getTime(),
                TsConverter.convertToTimestamp(dto.getBirthDate()));
        assertEquals(
                PaymentMethodCode.valueOf(
                        xmlSubject.getMethodOfPayment().value()).getCode(),
                CdConverter.convertCdToString(dto.getPaymentMethod()));
        assertEquals("1", dto.getDiseaseIdentifier().getExtension());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void verifyManageSubjectsCall(StudySubjects reg) throws PAException {
        ArgumentCaptor<List> subjectsCaptor = ArgumentCaptor
                .forClass(List.class);
        verify(AccrualServiceLocator.getInstance().getSubjectAccrualService())
                .manageSubjectAccruals(subjectsCaptor.capture());
        List<SubjectAccrualDTO> subjects = subjectsCaptor.getValue();
        assertEquals(1, subjects.size());

        StudySubject xmlSubject = reg.getStudySubject().get(0);
        SubjectAccrualDTO dto = subjects.get(0);
        assertEquals(xmlSubject.getIdentifier(),
                StConverter.convertToString(dto.getAssignedIdentifier()));
        assertEquals(xmlSubject.getBirthDate().toGregorianCalendar().getTime(),
                TsConverter.convertToTimestamp(dto.getBirthDate()));
        assertEquals(xmlSubject.getGender().value(),
                CdConverter.convertCdToString(dto.getGender()));
        assertEquals(
                xmlSubject.getRace().iterator().next().value(),
                CdConverter.convertCdToString(dto.getRace().getItem()
                        .iterator().next()));
        assertEquals(xmlSubject.getEthnicity().value(),
                CdConverter.convertCdToString(dto.getEthnicity()));
        assertEquals(xmlSubject.getCountry().value(),
                CdConverter.convertCdToString(dto.getCountryCode()));
        assertEquals(xmlSubject.getZipCode(),
                StConverter.convertToString(dto.getZipCode()));
        assertEquals(xmlSubject.getRegistrationDate().toGregorianCalendar()
                .getTime(),
                TsConverter.convertToTimestamp(dto.getRegistrationDate()));
        assertEquals(
                PaymentMethodCode.valueOf(
                        xmlSubject.getMethodOfPayment().value()).getCode(),
                CdConverter.convertCdToString(dto.getPaymentMethod()));
        assertEquals("1", dto.getDiseaseIdentifier().getExtension());
        assertEquals("1", dto.getSiteDiseaseIdentifier().getExtension());
        assertEquals(new Long(892374L),
                IiConverter.convertToLong(dto.getIdentifier()));
    }

    @SuppressWarnings("unchecked")
    private StudySubjects readStudySubjectsFromFile(String filename)
            throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(filename);
        StudySubjects o = (StudySubjects) u.unmarshal(url);
        return o;
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#submitStudySubjects(java.lang.String, java.lang.String, long, gov.nih.nci.accrual.webservices.types.StudySubjects)}
     * .
     * 
     * @throws JAXBException
     * @throws PAException
     */
    @Test
    public final void testSubmitStudySubjectsPoId() throws JAXBException,
            PAException {
        StudySubjects reg = readStudySubjectsFromFile("/add_update_subject_icdo3.xml");
        Response r = service.submitStudySubjects("pa", "1", 1, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyManageSubjectsCall(reg);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.accrual.webservices.AccrualService#submitStudySubjects(java.lang.String, java.lang.String, java.lang.String, gov.nih.nci.accrual.webservices.types.StudySubjects)}
     * .
     * 
     * @throws JAXBException
     * @throws PAException
     */
    @Test
    public final void testSubmitStudySubjectsCtepId() throws JAXBException,
            PAException {
        StudySubjects reg = readStudySubjectsFromFile("/add_update_subject_icdo3.xml");
        Response r = service.submitStudySubjects("pa", "1", "DCP", reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyManageSubjectsCall(reg);
    }

    @Test
    public final void testSubmitStudySubjectsDcpId() throws JAXBException,
            PAException {
        StudySubjects reg = readStudySubjectsFromFile("/add_update_subject_icdo3.xml");
        Response r = service.submitStudySubjects("dcp", "103816", "DCP", reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        verifyManageSubjectsCall(reg);
    }

    @Test
    public final void testTrialNotFound() throws PAException {
        StudyProtocolServiceRemote spSvc = PaServiceLocator.getInstance()
                .getStudyProtocolService();
        when(spSvc.getStudyProtocol((Ii) anyObject())).thenThrow(
                new PAException("No match"));
        Response r = service.updateAccrualCount("ctep", "ctepid", "DCP", 89234);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());

    }

    @Test
    public final void testSiteNotFound() throws PAException {
        final ParticipatingSiteServiceRemote participatingSiteServiceLocal = PaServiceLocator
                .getInstance().getParticipatingSiteServiceRemote();
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class))).thenReturn(null);

        Response r = service.updateAccrualCount("nci", "NCI-2014-00001", 1,
                89234);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals("Participating site with PO ID " + 1 + " on trial "
                + "nci" + "/" + "NCI-2014-00001" + " is not found.",
                r.getEntity());

    }

    @Test
    public void testSubmitEmptyBatchFile() {
        BatchFile batchFile = new BatchFile();
        Response response = service.submitBatchFile(batchFile);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    public void testSubmitBatchFile() throws URISyntaxException, IOException {

        File file = new File(this.getClass()
                .getResource("/CDUS_Complete_Rest_unit.txt").toURI());
        String data = FileUtils.readFileToString(file);
        BatchFile batchFile = new BatchFile();
        batchFile.setValue(data.getBytes());

        Response response = service.submitBatchFile(batchFile);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public final void testOrgNotFound() throws PAException {
        Response r = service.updateAccrualCount("nci", "NCI-2014-00001",
                "mn001", 89234);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals(
                "Organization with CTEP ID of mn001 cannot be found in PO.",
                r.getEntity());

    }

    private InterventionalStudyProtocolDTO setupSpDto() {
        Ii spId = new Ii();
        spId.setExtension("1");

        InterventionalStudyProtocolDTO spDto = new InterventionalStudyProtocolDTO();
        spDto.setPublicTitle(StConverter.convertToSt("title"));
        spDto.setAcronym(StConverter.convertToSt("acronym"));
        spDto.setOfficialTitle(StConverter.convertToSt("off title"));
        spDto.setIdentifier(spId);
        spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        spDto.setFdaRegulatedIndicator(BlConverter.convertToBl(true));
        spDto.setStudyProtocolType(StConverter
                .convertToSt("InterventionalStudyProtocol"));
        spDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                .convertToBl(true));
        spDto.setSection801Indicator(BlConverter.convertToBl(true));
        spDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        spDto.setReviewBoardApprovalRequiredIndicator(BlConverter
                .convertToBl(true));
        spDto.setRecordVerificationDate(TsConverter
                .convertToTs(new Timestamp(0)));
        spDto.setAccrualReportingMethodCode(CdConverter
                .convertToCd(AccrualReportingMethodCode.ABBREVIATED));
        spDto.setStartDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setStartDateTypeCode(CdConverter.convertStringToCd("Actual"));
        spDto.setPrimaryCompletionDate(TsConverter
                .convertToTs(new Timestamp(0)));
        spDto.setPrimaryCompletionDateTypeCode(CdConverter
                .convertStringToCd("Anticipated"));
        spDto.setCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setCompletionDateTypeCode(CdConverter
                .convertStringToCd("Anticipated"));
        spDto.setPublicDescription(StConverter
                .convertToSt("public description"));
        spDto.setDelayedpostingIndicator(BlConverter.convertToBl(true));
        spDto.setPublicTitle(StConverter.convertToSt("public title"));
        spDto.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(true));
        spDto.setPrimaryPurposeCode(CdConverter.convertStringToCd("TREATMENT"));

        spDto.setPhaseCode(CdConverter.convertToCd((Lov) null));
        spDto.setDesignConfigurationCode(CdConverter.convertToCd((Lov) null));
        spDto.setNumberOfInterventionGroups(IntConverter
                .convertToInt((Integer) null));
        spDto.setBlindingSchemaCode(CdConverter.convertToCd((Lov) null));
        spDto.setAllocationCode(CdConverter.convertToCd((Lov) null));
        final Ivl<Int> targetAccrualNumber = new Ivl<Int>();
        targetAccrualNumber.setLow(IntConverter.convertToInt((Integer) null));
        spDto.setTargetAccrualNumber(targetAccrualNumber);

        DSet<Ii> secondaryIdentifiers = new DSet<Ii>();
        Ii assignedId = new Ii();
        assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        assignedId.setExtension("NCI-2014-0001");
        Set<Ii> iis = new HashSet<Ii>();
        iis.add(assignedId);

        Ii otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("OtherId");
        iis.add(otherId);

        secondaryIdentifiers.setItem(iis);

        spDto.setSecondaryIdentifiers(secondaryIdentifiers);
        return spDto;
    }

}
