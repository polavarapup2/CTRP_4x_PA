/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xml.sax.SAXException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("deprecation")
public class TrialRegistrationServiceCompleteUpdateTest extends
        AbstractMockitoTest {

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

    private TrialRegistrationService service;

    private TrialRegistrationServiceLocal trialRegistrationServiceLocal;

    private PAServiceUtils paServiceUtils;

    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException {
        service = new TrialRegistrationService();

        paServiceUtils = mock(PAServiceUtils.class);
        when(paServiceUtils.getTrialNciId(any(Long.class))).thenReturn(
                "NCI-2014-00001");
        when(
                paServiceUtils.getStudyIdentifier(any(Ii.class),
                        eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn("");
        service.setPaServiceUtils(paServiceUtils);

        trialRegistrationServiceLocal = mock(TrialRegistrationServiceLocal.class);
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getTrialRegistrationService()).thenReturn(
                trialRegistrationServiceLocal);

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());

        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getOrganizationCorrelationService()).thenReturn(
                new OrganizationCorrelationServiceBean());
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getStudyOverallStatusService()).thenReturn(
                studyOverallStatusSvc);

        when(documentSvc.getDocumentsByStudyProtocol(any(Ii.class)))
                .thenReturn(new ArrayList<DocumentDTO>());
        when(PaRegistry.getInstance().getServiceLocator().getDocumentService())
                .thenReturn(documentSvc);

        UsernameHolder.setUser("jdoe01");

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdate() throws JAXBException, SAXException,
            PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        updateFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateExistingNctIDNotModified()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {

        when(
                paServiceUtils.getStudyIdentifier(any(Ii.class),
                        eq(PAConstants.NCT_IDENTIFIER_TYPE))).thenReturn(
                "NCT238947238974");

        final String filename = "/update_complete.xml";
        updateFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialByNciID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);
        updateAndVerify("nci", "NCI-2014-00001", reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialByCtepID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);
        updateAndVerify("ctep", "CTEP0000000001", reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialByDCPID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);
        updateAndVerify("dcp", "dcp123", reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateValidationException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);

        doThrow(new PAValidationException("Validation Exception: error."))
                .when(trialRegistrationServiceLocal).update(
                        any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class), any(List.class),
                        any(StudyContactDTO.class),
                        any(StudySiteContactDTO.class),
                        any(OrganizationDTO.class),
                        any(StudyResourcingDTO.class), any(Ii.class),
                        any(StudyRegulatoryAuthorityDTO.class),
                        any(List.class), any(List.class), any(List.class),
                        any(Bl.class));

        Response r = service.updateCompleteTrial("pa", "1", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Validation Exception: error.", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialDataException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);

        doThrow(new TrialDataException("error")).when(
                trialRegistrationServiceLocal).update(
                any(StudyProtocolDTO.class), any(StudyOverallStatusDTO.class),
                any(List.class), any(List.class), any(List.class),
                any(List.class), any(StudyContactDTO.class),
                any(StudySiteContactDTO.class), any(OrganizationDTO.class),
                any(StudyResourcingDTO.class), any(Ii.class),
                any(StudyRegulatoryAuthorityDTO.class), any(List.class),
                any(List.class), any(List.class), any(Bl.class));

        Response r = service.updateCompleteTrial("pa", "1", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralPAError()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);

        doThrow(new PAException("error")).when(trialRegistrationServiceLocal)
                .update(any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class), any(List.class),
                        any(StudyContactDTO.class),
                        any(StudySiteContactDTO.class),
                        any(OrganizationDTO.class),
                        any(StudyResourcingDTO.class), any(Ii.class),
                        any(StudyRegulatoryAuthorityDTO.class),
                        any(List.class), any(List.class), any(List.class),
                        any(Bl.class));

        Response r = service.updateCompleteTrial("pa", "1", reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralError() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_complete.xml";
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);

        doThrow(new RuntimeException("error")).when(
                trialRegistrationServiceLocal).update(
                any(StudyProtocolDTO.class), any(StudyOverallStatusDTO.class),
                any(List.class), any(List.class), any(List.class),
                any(List.class), any(StudyContactDTO.class),
                any(StudySiteContactDTO.class), any(OrganizationDTO.class),
                any(StudyResourcingDTO.class), any(Ii.class),
                any(StudyRegulatoryAuthorityDTO.class), any(List.class),
                any(List.class), any(List.class), any(Bl.class));

        Response r = service.updateCompleteTrial("pa", "1", reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    /**
     * @param filename
     * @throws JAXBException
     * @throws SAXException
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void updateFromXmlFileAndVerify(final String filename)
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        CompleteTrialUpdate reg = readCompleteTrialUpdateFromFile(filename);
        updateAndVerify("pa", "1", reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    @SuppressWarnings("unchecked")
    private void updateAndVerify(String idType, String trialID,
            CompleteTrialUpdate reg) throws PAException,
            NullifiedEntityException {
        Response r = service.updateCompleteTrial(idType, trialID, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        JAXBElement<TrialRegistrationConfirmation> el = (JAXBElement<TrialRegistrationConfirmation>) r
                .getEntity();
        TrialRegistrationConfirmation conf = el.getValue();
        assertEquals("NCI-2014-00001", conf.getNciTrialID());
        assertEquals(1, conf.getPaTrialID());

        captureAndVerifyArguments(reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void captureAndVerifyArguments(CompleteTrialUpdate reg)
            throws PAException, NullifiedEntityException {
        ArgumentCaptor<StudyProtocolDTO> studyProtocolDTO = ArgumentCaptor
                .forClass(StudyProtocolDTO.class);
        ArgumentCaptor<StudyOverallStatusDTO> overallStatusDTO = ArgumentCaptor
                .forClass(StudyOverallStatusDTO.class);
        ArgumentCaptor<List> studyResourcingDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<List> documentDTOs = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List> studyIdentifierDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<Bl> isBatchMode = ArgumentCaptor.forClass(Bl.class);
        verify(PaRegistry.getTrialRegistrationService()).update(
                studyProtocolDTO.capture(), overallStatusDTO.capture(),
                studyIdentifierDTOs.capture(), any(List.class),
                studyResourcingDTOs.capture(), documentDTOs.capture(),
                any(StudyContactDTO.class), any(StudySiteContactDTO.class),
                any(OrganizationDTO.class), any(StudyResourcingDTO.class),
                any(Ii.class), any(StudyRegulatoryAuthorityDTO.class),
                any(List.class), any(List.class), any(List.class),
                isBatchMode.capture());

        verifyStudyProtocol(reg, studyProtocolDTO);
        verifyOverallStatus(reg, overallStatusDTO);
        verifyStudyResourcingDTOs(reg, studyResourcingDTOs);
        verifyDocumentDTOs(reg, documentDTOs);
        verifyCtGovIdentifierAssignerSite(reg, studyIdentifierDTOs);
        verifyBatchMode(reg, isBatchMode);
    }

    @SuppressWarnings("rawtypes")
    private void verifyCtGovIdentifierAssignerSite(CompleteTrialUpdate reg,
            ArgumentCaptor<List> captor) throws PAException {

        if (StringUtils.isEmpty(paServiceUtils.getStudyIdentifier(
                IiConverter.convertToIi(1L), PAConstants.NCT_IDENTIFIER_TYPE))) {
            assertEquals(1, captor.getValue().size());
            StudySiteDTO dto = (StudySiteDTO) captor.getValue().get(0);
            assertEquals(reg.getClinicalTrialsDotGovTrialID(), dto
                    .getLocalStudyProtocolIdentifier().getValue());
            assertEquals("1", dto.getResearchOrganizationIi().getExtension());
        } else {
            assertEquals(0, captor.getValue().size());
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void verifyDocumentDTOs(CompleteTrialUpdate reg,
            ArgumentCaptor<List> captor) {
        List<DocumentDTO> list = captor.getValue();
        verifyDocumentDTO(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT);
        verifyDocumentDTO(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
        verifyDocumentDTO(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES);
        verifyDocumentDTO(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT);
        verifyDocumentDTO(list, reg.getOtherDocument().get(0),
                DocumentTypeCode.OTHER);

    }

    private void verifyDocumentDTO(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type) {
        for (DocumentDTO dto : list) {
            if (dto.getTypeCode().getCode().equals(type.getCode())) {
                assertEquals(doc.getFilename(), dto.getFileName().getValue());
                assertEquals(doc.getValue(), dto.getText().getData());
                return;
            }
        }
        Assert.fail();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void verifyStudyResourcingDTOs(CompleteTrialUpdate reg,
            ArgumentCaptor<List> captor) {
        List<StudyResourcingDTO> list = captor.getValue();
        for (int i = 0; i < list.size(); i++) {
            verifyStudyResourcingDTO(list.get(i), reg.getGrant().get(i));
        }
    }

    private void verifyStudyResourcingDTO(StudyResourcingDTO dto, Grant grant) {
        assertEquals(dto.getFundingMechanismCode().getCode(),
                grant.getFundingMechanism());
        assertEquals(dto.getNihInstitutionCode().getCode(),
                grant.getNihInstitutionCode());
        assertEquals(dto.getSerialNumber().getValue(), grant.getSerialNumber());
        assertEquals(dto.getNciDivisionProgramCode().getCode(), grant
                .getNciDivisionProgramCode().value());
        assertEquals(dto.getFundingPercent().getValue().floatValue(), grant
                .getFundingPercentage().floatValue(), 0);
        assertFalse(dto.getSummary4ReportedResourceIndicator().getValue());

    }

    private void verifyOverallStatus(CompleteTrialUpdate reg,
            ArgumentCaptor<StudyOverallStatusDTO> captor) {
        StudyOverallStatusDTO dto = captor.getValue();
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        assertEquals(1,
                IiConverter.convertToLong(dto.getStudyProtocolIdentifier())
                        .intValue());

        assertEquals(reg.getTrialStatus().value(), dto.getStatusCode()
                .getCode());
        assertEquals(reg.getWhyStopped(), dto.getReasonText().getValue());
        assertEquals(reg.getTrialStatusDate().toGregorianCalendar().getTime(),
                dto.getStatusDate().getValue());

    }

    private void verifyStudyProtocol(CompleteTrialUpdate reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();
        assertNotNull(dto.getIdentifier());
        assertEquals(1, IiConverter.convertToLong(dto.getIdentifier())
                .intValue());

        for (String otherID : reg.getOtherTrialID()) {
            if (!PAUtil.containsIi(dto.getSecondaryIdentifiers().getItem(),
                    otherID, IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT)) {
                Assert.fail("Study other identifier not found: " + otherID);
            }
        }
        assertTrue(PAUtil.containsIi(dto.getSecondaryIdentifiers().getItem(),
                "NCI_2010_0001", IiConverter.STUDY_PROTOCOL_ROOT));

        assertEquals(reg.getAccrualDiseaseTerminology().value(), dto
                .getAccrualDiseaseCodeSystem().getValue());

        assertEquals(reg.getTrialStartDate().getValue().toGregorianCalendar()
                .getTime(), dto.getStartDate().getValue());
        assertEquals(reg.getTrialStartDate().getType(), dto
                .getStartDateTypeCode().getCode());
        assertEquals(reg.getPrimaryCompletionDate().getValue().getValue()
                .toGregorianCalendar().getTime(), dto
                .getPrimaryCompletionDate().getValue());
        assertEquals(reg.getPrimaryCompletionDate().getValue().getType(), dto
                .getPrimaryCompletionDateTypeCode().getCode());
        assertEquals(reg.getCompletionDate().getValue().toGregorianCalendar()
                .getTime(), dto.getCompletionDate().getValue());
        assertEquals(reg.getCompletionDate().getType(), dto
                .getCompletionDateTypeCode().getCode());

        assertEquals("jdoe01", dto.getUserLastCreated().getValue());

    }

    private void verifyBatchMode(CompleteTrialUpdate reg,
            ArgumentCaptor<Bl> isBatchMode) {
        assertFalse(isBatchMode.getValue().getValue().booleanValue());
    }

    @SuppressWarnings("unchecked")
    private CompleteTrialUpdate readCompleteTrialUpdateFromFile(String string)
            throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialUpdate o = ((JAXBElement<CompleteTrialUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }
}
