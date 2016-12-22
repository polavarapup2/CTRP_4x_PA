/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.INDIDE;
import gov.nih.nci.pa.webservices.types.InterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.NonInterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.ResponsibleParty;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

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
public class TrialRegistrationServiceCompleteAmendmentTest extends
        BaseTrialRegistrationServiceTest {

    private TrialRegistrationService service;

    private TrialRegistrationServiceLocal trialRegistrationServiceLocal;

    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException {
        service = new TrialRegistrationService();

        PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
        when(paServiceUtils.getTrialNciId(any(Long.class))).thenReturn(
                "NCI-2014-00001");
        service.setPaServiceUtils(paServiceUtils);

        trialRegistrationServiceLocal = mock(TrialRegistrationServiceLocal.class);
        when(
                trialRegistrationServiceLocal.amend(
                        any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class),
                        any(OrganizationDTO.class), any(PersonDTO.class),
                        any(OrganizationDTO.class),
                        any(ResponsiblePartyDTO.class),
                        any(StudySiteDTO.class), any(List.class),
                        any(List.class), any(StudyResourcingDTO.class),
                        any(StudyRegulatoryAuthorityDTO.class), any(Bl.class),
                        any(Bl.class))).thenReturn(
                IiConverter.convertToIi(999L));
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

        UsernameHolder.setUser("jdoe01");

        spDto.setStudyProtocolType(StConverter.convertToSt("Interventional"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAmend() throws JAXBException, SAXException,
            PAException, NullifiedEntityException {

        final String filename = "/amend_complete.xml";
        amendFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAmendNonCtGov() throws JAXBException, SAXException,
            PAException, NullifiedEntityException {

        final String filename = "/amend_complete_non_ct_gov.xml";
        amendFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCannotChangeTrialDesign() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {

        final String filename = "/amend_complete_non_interventional.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);
        Response r = service.amendCompleteTrial("nci", "NCI-2000-00001", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals(
                "An amendment cannot change a trial from Interventional to Non-Interventional or vice versa.",
                r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAmendPAException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/amend_complete.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);

        when(
                trialRegistrationServiceLocal.amend(
                        any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class),
                        any(OrganizationDTO.class), any(PersonDTO.class),
                        any(OrganizationDTO.class),
                        any(ResponsiblePartyDTO.class),
                        any(StudySiteDTO.class), any(List.class),
                        any(List.class), any(StudyResourcingDTO.class),
                        any(StudyRegulatoryAuthorityDTO.class), any(Bl.class),
                        any(Bl.class))).thenThrow(new PAException("error"));

        Response r = service.amendCompleteTrial("pa", "1", reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testBlankIdTypeAndOrValue() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/amend_complete.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);
        Response r = service.amendCompleteTrial("", "", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals(
                "Please provide trial identifier type and value as described in the documentation.",
                r.getEntity().toString());

        r = service.amendCompleteTrial("nci", "", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals(
                "Please provide trial identifier type and value as described in the documentation.",
                r.getEntity().toString());

        r = service.amendCompleteTrial("", "1", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals(
                "Please provide trial identifier type and value as described in the documentation.",
                r.getEntity().toString());

    }
    
    @SuppressWarnings("unchecked")
    @Test
    public final void testAmendByDCPID() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/amend_complete.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);
        Response r = service.amendCompleteTrial("dcp", "DCP123", reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
      

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAmendCompleteGeneralError() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/amend_complete.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);

        when(
                trialRegistrationServiceLocal.amend(
                        any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class),
                        any(OrganizationDTO.class), any(PersonDTO.class),
                        any(OrganizationDTO.class),
                        any(ResponsiblePartyDTO.class),
                        any(StudySiteDTO.class), any(List.class),
                        any(List.class), any(StudyResourcingDTO.class),
                        any(StudyRegulatoryAuthorityDTO.class), any(Bl.class),
                        any(Bl.class)))
                .thenThrow(new RuntimeException("error"));

        Response r = service.amendCompleteTrial("ctep", "00001", reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAmendValidationException() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/amend_complete.xml";
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);

        when(
                trialRegistrationServiceLocal.amend(
                        any(StudyProtocolDTO.class),
                        any(StudyOverallStatusDTO.class), any(List.class),
                        any(List.class), any(List.class),
                        any(OrganizationDTO.class), any(PersonDTO.class),
                        any(OrganizationDTO.class),
                        any(ResponsiblePartyDTO.class),
                        any(StudySiteDTO.class), any(List.class),
                        any(List.class), any(StudyResourcingDTO.class),
                        any(StudyRegulatoryAuthorityDTO.class), any(Bl.class),
                        any(Bl.class))).thenThrow(
                new PAValidationException("Validation Exception: error."));

        Response r = service.amendCompleteTrial("ctep", "00001", reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Validation Exception: error.", r.getEntity().toString());

    }

    private void amendFromXmlFileAndVerify(String filename)
            throws NullifiedEntityException, PAException, JAXBException,
            SAXException {
        CompleteTrialAmendment reg = readCompleteTrialAmendmentFromFile(filename);
        amendAndVerify(reg);

    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    @SuppressWarnings("unchecked")
    private void amendAndVerify(CompleteTrialAmendment reg) throws PAException,
            NullifiedEntityException {
        Response r = service.amendCompleteTrial("nci", "NCI-2014-00001", reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        JAXBElement<TrialRegistrationConfirmation> el = (JAXBElement<TrialRegistrationConfirmation>) r
                .getEntity();
        TrialRegistrationConfirmation conf = el.getValue();
        assertEquals("NCI-2014-00001", conf.getNciTrialID());
        assertEquals(999, conf.getPaTrialID());

        captureAndVerifyArguments(reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void captureAndVerifyArguments(CompleteTrialAmendment reg)
            throws PAException, NullifiedEntityException {
        ArgumentCaptor<StudyProtocolDTO> studyProtocolDTO = ArgumentCaptor
                .forClass(StudyProtocolDTO.class);
        ArgumentCaptor<StudyOverallStatusDTO> overallStatusDTO = ArgumentCaptor
                .forClass(StudyOverallStatusDTO.class);
        ArgumentCaptor<List> studyIndldeDTOs = (ArgumentCaptor
                .forClass(List.class));
        ArgumentCaptor<List> studyResourcingDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<List> documentDTOs = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<OrganizationDTO> leadOrganizationDTO = ArgumentCaptor
                .forClass(OrganizationDTO.class);
        ArgumentCaptor<PersonDTO> principalInvestigatorDTO = ArgumentCaptor
                .forClass(PersonDTO.class);
        ArgumentCaptor<OrganizationDTO> sponsorOrganizationDTO = ArgumentCaptor
                .forClass(OrganizationDTO.class);
        ArgumentCaptor<ResponsiblePartyDTO> partyDTO = ArgumentCaptor
                .forClass(ResponsiblePartyDTO.class);
        ArgumentCaptor<StudySiteDTO> leadOrganizationSiteIdentifierDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<List> studyIdentifierDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<List> summary4organizationDTO = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<StudyResourcingDTO> summary4studyResourcingDTO = ArgumentCaptor
                .forClass(StudyResourcingDTO.class);
        ArgumentCaptor<StudyRegulatoryAuthorityDTO> studyRegAuthDTO = ArgumentCaptor
                .forClass(StudyRegulatoryAuthorityDTO.class);
        ArgumentCaptor<Bl> isBatchMode = ArgumentCaptor.forClass(Bl.class);
        ArgumentCaptor<Bl> gracefully = ArgumentCaptor.forClass(Bl.class);
        ArgumentCaptor<DSet> owners = ArgumentCaptor.forClass(DSet.class);
        verify(PaRegistry.getTrialRegistrationService()).amend(
                studyProtocolDTO.capture(), overallStatusDTO.capture(),
                studyIndldeDTOs.capture(), studyResourcingDTOs.capture(),
                documentDTOs.capture(), leadOrganizationDTO.capture(),
                principalInvestigatorDTO.capture(),
                sponsorOrganizationDTO.capture(), partyDTO.capture(),
                leadOrganizationSiteIdentifierDTO.capture(),
                studyIdentifierDTOs.capture(),
                summary4organizationDTO.capture(),
                summary4studyResourcingDTO.capture(),
                studyRegAuthDTO.capture(), isBatchMode.capture(),
                gracefully.capture());

        verifyStudyProtocol(reg, studyProtocolDTO);
        verifyOtherIdentifiers(reg, studyProtocolDTO);
        verifyOverallStatus(reg, overallStatusDTO);
        verifyStudyIndldeDTOs(reg, studyIndldeDTOs);
        verifyStudyResourcingDTOs(reg, studyResourcingDTOs);
        verifyDocumentDTOs(reg, documentDTOs);
        verifyLeadOrganization(reg, leadOrganizationDTO);
        verifyPI(reg, principalInvestigatorDTO);
        verifySponsor(reg, sponsorOrganizationDTO);
        verifyPartyDTO(reg, partyDTO);
        verifyLeadOrganizationSiteIdentifierDTO(reg,
                leadOrganizationSiteIdentifierDTO);
        verifyIdentifierAssigners(reg, studyIdentifierDTOs);
        verifySummary4OrganizationDTO(reg, summary4organizationDTO);
        verifySummary4studyResourcingDTO(reg, summary4studyResourcingDTO);
        verifyStudyRegAuthDTO(reg, studyRegAuthDTO);
        verifyBatchMode(reg, isBatchMode);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void verifyDocumentDTOs(BaseTrialInformation base,
            ArgumentCaptor<List> captor) {
        super.verifyDocumentDTOs(base, captor);

        CompleteTrialAmendment reg = (CompleteTrialAmendment) base;

        List<DocumentDTO> list = captor.getValue();
        verifyDocumentDTO(list, reg.getChangeMemoDocument(),
                DocumentTypeCode.CHANGE_MEMO_DOCUMENT);
        verifyDocumentDTO(list, reg.getProtocolHighlightDocument(),
                DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT);
    }

    @Override
    protected void verifyStudyProtocol(BaseTrialInformation reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        super.verifyStudyProtocol(reg, captor);

        StudyProtocolDTO dto = captor.getValue();
        CompleteTrialAmendment am = (CompleteTrialAmendment) reg;

        assertEquals(am.getAmendmentNumber(),
                StConverter.convertToString(dto.getAmendmentNumber()));
        assertEquals(am.getAmendmentDate().toGregorianCalendar().getTime(), dto
                .getAmendmentDate().getValue());
    }

    @SuppressWarnings("rawtypes")
    private void verifyIdentifierAssigners(CompleteTrialAmendment reg,
            ArgumentCaptor<List> captor) {
        assertEquals(3, captor.getValue().size());
        StudySiteDTO dto = (StudySiteDTO) captor.getValue().get(0);
        assertEquals(reg.getClinicalTrialsDotGovTrialID(), dto
                .getLocalStudyProtocolIdentifier().getValue());
        assertEquals("1", dto.getResearchOrganizationIi().getExtension());

        dto = (StudySiteDTO) captor.getValue().get(1);
        assertEquals(reg.getCtepIdentifier(), dto
                .getLocalStudyProtocolIdentifier().getValue());
        assertEquals("2", dto.getResearchOrganizationIi().getExtension());

        dto = (StudySiteDTO) captor.getValue().get(2);
        assertEquals(reg.getDcpIdentifier(), dto
                .getLocalStudyProtocolIdentifier().getValue());
        assertEquals("3", dto.getResearchOrganizationIi().getExtension());

    }

    private void verifyOtherIdentifiers(CompleteTrialAmendment reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();
        for (String otherID : reg.getOtherTrialID()) {
            if (!PAUtil.containsIi(dto.getSecondaryIdentifiers().getItem(),
                    otherID, IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT)) {
                Assert.fail("Study other identifier not found: " + otherID);
            }
        }
        assertTrue(PAUtil.containsIi(dto.getSecondaryIdentifiers().getItem(),
                "NCI_2010_0001", IiConverter.STUDY_PROTOCOL_ROOT));

    }

    @SuppressWarnings("unchecked")
    private CompleteTrialAmendment readCompleteTrialAmendmentFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialAmendment o = ((JAXBElement<CompleteTrialAmendment>) u
                .unmarshal(url)).getValue();
        return o;
    }

}
