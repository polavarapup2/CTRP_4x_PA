/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
public class TrialRegistrationServiceCompleteRegistrationTest extends
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
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenReturn(
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

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterComplete() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteValidationException()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new PAValidationException("Validation Exception: error."));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Validation Exception: error.", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralPAError()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new PAException("error"));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralError() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new RuntimeException("error"));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteOrgNotFound() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        reg.getLeadOrganization().getExistingOrganization().setPoID(999999999L);
        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals(
                "Organization with PO ID of 999999999 cannot be found in PO.",
                r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompletePersonNotFound()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        reg.getPi().getExistingPerson().setPoID(9999999L);
        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals("Person with PO ID of 9999999 cannot be found in PO.", r
                .getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterPIAsResponsibleParty() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_RespPartyPI.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterNonCtGov() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_nonctgov.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterNonInterventional() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_noninterventional.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterWithNewOrgAndPerson() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_newOrgsAndPersons.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterWithOrgsPersonsIdentifiedByCtepID()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete_OrgPersonWithCtepID.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterSponsorInvestigatorAsResponsibleParty()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete_RespPartySponsorInvestigator.xml";
        registerFromXmlFileAndVerify(filename);

    }

    /**
     * @param filename
     * @throws JAXBException
     * @throws SAXException
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void registerFromXmlFileAndVerify(final String filename)
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        registerAndVerify(reg);
    }

    @SuppressWarnings("unchecked")
    private CompleteTrialRegistration readCompleteTrialRegistrationFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();
        return o;
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void registerAndVerify(CompleteTrialRegistration reg)
            throws PAException, NullifiedEntityException {
        Response r = service.registerCompleteTrial(reg);
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
    private void captureAndVerifyArguments(CompleteTrialRegistration reg)
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
        ArgumentCaptor<DSet> owners = ArgumentCaptor.forClass(DSet.class);
        verify(PaRegistry.getTrialRegistrationService())
                .createCompleteInterventionalStudyProtocol(
                        studyProtocolDTO.capture(), overallStatusDTO.capture(),
                        studyIndldeDTOs.capture(),
                        studyResourcingDTOs.capture(), documentDTOs.capture(),
                        leadOrganizationDTO.capture(),
                        principalInvestigatorDTO.capture(),
                        sponsorOrganizationDTO.capture(), partyDTO.capture(),
                        leadOrganizationSiteIdentifierDTO.capture(),
                        studyIdentifierDTOs.capture(),
                        summary4organizationDTO.capture(),
                        summary4studyResourcingDTO.capture(),
                        studyRegAuthDTO.capture(), isBatchMode.capture(),
                        owners.capture());

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
        verifyCtGovIdentifierAssignerSite(reg, studyIdentifierDTOs);
        verifySummary4OrganizationDTO(reg, summary4organizationDTO);
        verifySummary4studyResourcingDTO(reg, summary4studyResourcingDTO);
        verifyStudyRegAuthDTO(reg, studyRegAuthDTO);
        verifyBatchMode(reg, isBatchMode);
        verifyOwners(reg, owners);
        
        String dcpIdFromXml = reg.getDcpIdentifier();
        if(dcpIdFromXml!=null) {
            verifyIdentifierAssigners(reg, studyIdentifierDTOs);    
        }
        
        
    }

    private void verifyOtherIdentifiers(CompleteTrialRegistration reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();
        assertEquals(reg.getOtherTrialID().get(0), dto
                .getSecondaryIdentifiers().getItem().iterator().next()
                .getExtension());
        assertEquals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, dto
                .getSecondaryIdentifiers().getItem().iterator().next()
                .getRoot());
    }

    @SuppressWarnings("rawtypes")
    protected void verifyOwners(CompleteTrialRegistration reg,
            ArgumentCaptor<DSet> owners) {
        assertEquals("mailto:" + reg.getTrialOwner().get(0), ((Tel) owners
                .getValue().getItem().iterator().next()).getValue().toString());

    }

    protected void verifyCtGovIdentifierAssignerSite(BaseTrialInformation reg,
            ArgumentCaptor<List> captor) {
        assertEquals(2, captor.getValue().size());
        StudySiteDTO dto = (StudySiteDTO) captor.getValue().get(0);
        assertEquals(reg.getClinicalTrialsDotGovTrialID(), dto
                .getLocalStudyProtocolIdentifier().getValue());
        assertEquals("1", dto.getResearchOrganizationIi().getExtension());
    }

}
