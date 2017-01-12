/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.types.AbbreviatedTrialUpdate;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
public class TrialRegistrationServiceAbbreviatedUpdateTest extends
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

    private ProprietaryTrialManagementServiceLocal trialRegistrationServiceLocal;

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

        trialRegistrationServiceLocal = mock(ProprietaryTrialManagementServiceLocal.class);
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getProprietaryTrialService()).thenReturn(
                trialRegistrationServiceLocal);

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());

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
        final String filename = "/update_abbr.xml";
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

        final String filename = "/update_abbr.xml";
        updateFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateIrbAndConsentGetReplacedOthersGetMerged()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {

        final ArrayList<DocumentDTO> docs = new ArrayList<DocumentDTO>();

        DocumentDTO irb = new DocumentDTO();
        irb.setFileName(StConverter.convertToSt("irb_o.pdf"));
        irb.setIdentifier(IiConverter.convertToDocumentIi(1L));
        irb.setTypeCode(CdConverter
                .convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));
        docs.add(irb);

        DocumentDTO consent = new DocumentDTO();
        consent.setFileName(StConverter.convertToSt("consent_o.pdf"));
        consent.setIdentifier(IiConverter.convertToDocumentIi(2L));
        consent.setTypeCode(CdConverter
                .convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));
        docs.add(consent);

        DocumentDTO other = new DocumentDTO();
        other.setFileName(StConverter.convertToSt("other_o.pdf"));
        other.setIdentifier(IiConverter.convertToDocumentIi(3L));
        other.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docs.add(other);

        when(documentSvc.getDocumentsByStudyProtocol(any(Ii.class)))
                .thenReturn(docs);

        final String filename = "/update_abbr.xml";
        updateFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialByNciID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);
        updateAndVerify("nci", "NCI-2014-00001", reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialByCtepID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);
        updateAndVerify("ctep", "CTEP0000000001", reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateValidationException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);

        doThrow(new PAValidationException("Validation Exception: error."))
                .when(trialRegistrationServiceLocal).update(
                        any(StudyProtocolDTO.class),
                        any(OrganizationDTO.class), any(OrganizationDTO.class),
                        any(St.class), any(St.class), any(Cd.class),
                        any(List.class), any(List.class), any(List.class));

        Response r = service.updateAbbreviatedTrial("pa", "1", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Validation Exception: error.", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateTrialDataException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);

        doThrow(new TrialDataException("error")).when(
                trialRegistrationServiceLocal).update(
                any(StudyProtocolDTO.class), any(OrganizationDTO.class),
                any(OrganizationDTO.class), any(St.class), any(St.class),
                any(Cd.class), any(List.class), any(List.class),
                any(List.class));

        Response r = service.updateAbbreviatedTrial("pa", "1", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralPAError()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);

        doThrow(new PAException("error")).when(trialRegistrationServiceLocal)
                .update(any(StudyProtocolDTO.class),
                        any(OrganizationDTO.class), any(OrganizationDTO.class),
                        any(St.class), any(St.class), any(Cd.class),
                        any(List.class), any(List.class), any(List.class));

        Response r = service.updateAbbreviatedTrial("pa", "1", reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralError() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/update_abbr.xml";
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);

        doThrow(new RuntimeException("error")).when(
                trialRegistrationServiceLocal).update(
                any(StudyProtocolDTO.class), any(OrganizationDTO.class),
                any(OrganizationDTO.class), any(St.class), any(St.class),
                any(Cd.class), any(List.class), any(List.class),
                any(List.class));

        Response r = service.updateAbbreviatedTrial("pa", "1", reg);
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
        AbbreviatedTrialUpdate reg = readAbbreviatedTrialUpdateFromFile(filename);
        updateAndVerify("pa", "1", reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    @SuppressWarnings("unchecked")
    private void updateAndVerify(String idType, String trialID,
            AbbreviatedTrialUpdate reg) throws PAException,
            NullifiedEntityException {
        Response r = service.updateAbbreviatedTrial(idType, trialID, reg);
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
    private void captureAndVerifyArguments(AbbreviatedTrialUpdate reg)
            throws PAException, NullifiedEntityException {
        ArgumentCaptor<StudyProtocolDTO> studyProtocolDTO = ArgumentCaptor
                .forClass(StudyProtocolDTO.class);
        ArgumentCaptor<List> documentDTOs = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<St> nctIdentifier = ArgumentCaptor.forClass(St.class);

        verify(PaRegistry.getProprietaryTrialService()).update(
                studyProtocolDTO.capture(), any(OrganizationDTO.class),
                any(OrganizationDTO.class), any(St.class),
                nctIdentifier.capture(), any(Cd.class), documentDTOs.capture(),
                any(List.class), any(List.class));

        verifyStudyProtocol(reg, studyProtocolDTO);
        verifyDocumentDTOs(reg, documentDTOs);
        verifyCtGovIdentifier(reg, nctIdentifier);

    }

    @SuppressWarnings("rawtypes")
    private void verifyCtGovIdentifier(AbbreviatedTrialUpdate reg,
            ArgumentCaptor<St> nctIdentifier) throws PAException {

        if (StringUtils.isEmpty(paServiceUtils.getStudyIdentifier(
                IiConverter.convertToIi(1L), PAConstants.NCT_IDENTIFIER_TYPE))) {
            assertEquals(reg.getClinicalTrialsDotGovTrialID(),
                    StConverter.convertToString(nctIdentifier.getValue()));
        } else {
            assertEquals(paServiceUtils.getStudyIdentifier(
                    IiConverter.convertToIi(1L),
                    PAConstants.NCT_IDENTIFIER_TYPE),
                    StConverter.convertToString(nctIdentifier.getValue()));
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void verifyDocumentDTOs(AbbreviatedTrialUpdate reg,
            ArgumentCaptor<List> captor) throws PAException {
        List<DocumentDTO> list = captor.getValue();
        verifyDocumentDTO(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
        verifyDocumentDTO(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT);
        verifyDocumentDTO(list, reg.getOtherDocument().get(0),
                DocumentTypeCode.OTHER);

        // ensure any existing IRB & Consent docs got replaced.
        for (DocumentDTO dto : list) {
            if (StConverter.convertToString(dto.getFileName())
                    .equalsIgnoreCase("irb_o.pdf")
                    || StConverter.convertToString(dto.getFileName())
                            .equalsIgnoreCase("consent_o.pdf")) {
                Assert.fail();
            }
        }

        outer: for (DocumentDTO ex : documentSvc
                .getDocumentsByStudyProtocol(IiConverter.convertToIi(1L))) {
            if (ex.getTypeCode().getCode()
                    .equalsIgnoreCase(DocumentTypeCode.OTHER.getCode())) {
                for (DocumentDTO dto : list) {
                    if (ex.getIdentifier().equals(dto.getIdentifier())) {
                        continue outer;
                    }
                }
                Assert.fail();
            }
        }

    }

    private void verifyDocumentDTO(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type) {
        for (DocumentDTO dto : list) {
            if (dto.getTypeCode().getCode().equals(type.getCode())
                    && doc.getFilename().equals(dto.getFileName().getValue())
                    && Arrays.equals(doc.getValue(), dto.getText().getData())) {

                return;
            }
        }
        Assert.fail();
    }

    private void verifyStudyProtocol(AbbreviatedTrialUpdate reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();
        assertNotNull(dto.getIdentifier());
        assertEquals(1, IiConverter.convertToLong(dto.getIdentifier())
                .intValue());

        assertEquals("jdoe01", dto.getUserLastCreated().getValue());

    }

    @SuppressWarnings("unchecked")
    private AbbreviatedTrialUpdate readAbbreviatedTrialUpdateFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        AbbreviatedTrialUpdate o = ((JAXBElement<AbbreviatedTrialUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }
}
