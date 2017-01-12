/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.EntityNamePartType;
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
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.INDIDE;
import gov.nih.nci.pa.webservices.types.InterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.NonInterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.ResponsibleParty;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.mockito.ArgumentCaptor;

/**
 * @author dkrylov
 * 
 */
public abstract class BaseTrialRegistrationServiceTest extends
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

    protected void verifyStudyProtocol(BaseTrialInformation reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();

        if (reg instanceof CompleteTrialRegistration) {
            assertNull(dto.getIdentifier());
        } else {
            assertNotNull(dto.getIdentifier());
        }

        assertEquals(reg.isClinicalTrialsDotGovXmlRequired(), dto
                .getCtgovXmlRequiredIndicator().getValue().booleanValue());
       
        assertEquals(reg.getTitle(), dto.getOfficialTitle().getValue());
        assertEquals(reg.getPhase(), dto.getPhaseCode().getCode());

        if (reg.getPhase().equals("NA")) {
            if (reg.isPilot()) {
                assertEquals("Pilot", dto.getPhaseAdditionalQualifierCode()
                        .getCode());
            }
        } else {
            assertNull(dto.getPhaseAdditionalQualifierCode().getCode());
        }

        assertEquals(reg.getAccrualDiseaseTerminology().value(), dto
                .getAccrualDiseaseCodeSystem().getValue());
        assertEquals(reg.getPrimaryPurpose().value(), dto
                .getPrimaryPurposeCode().getCode());
        assertEquals(reg.getPrimaryPurposeOtherDescription(), dto
                .getPrimaryPurposeOtherText().getValue());
        if (reg.getInterventionalDesign() != null) {
            verifyInterventionalDesign(reg, dto);
        } else {
            verifyNonInterventionalDesign(reg, dto);
        }
        assertEquals(reg.getProgramCode(), dto.getProgramCodeText().getValue());
        assertEquals(reg.isFundedByNciGrant(), dto.getNciGrant().getValue()
                .booleanValue());
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

        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            assertEquals(reg.getRegulatoryInformation().isFdaRegulated(), dto
                    .getFdaRegulatedIndicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation().isSection801(), dto
                    .getSection801Indicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation().isDelayedPosting(), dto
                    .getDelayedpostingIndicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation()
                    .isDataMonitoringCommitteeAppointed(), dto
                    .getDataMonitoringCommitteeAppointedIndicator().getValue()
                    .booleanValue());
        } else {
            assertNull(dto.getFdaRegulatedIndicator());
            assertNull(dto.getSection801Indicator());
            assertNull(dto.getDelayedpostingIndicator());
            assertNull(dto.getDataMonitoringCommitteeAppointedIndicator());
        }
        assertFalse(dto.getProprietaryTrialIndicator().getValue()
                .booleanValue());
        assertEquals("REST Service", dto.getStudySource().getCode());
        assertEquals("jdoe01", dto.getUserLastCreated().getValue());

    }

    private void verifyNonInterventionalDesign(BaseTrialInformation reg,
            StudyProtocolDTO proto) {
        NonInterventionalTrialDesign design = reg.getNonInterventionalDesign();
        NonInterventionalStudyProtocolDTO dto = (NonInterventionalStudyProtocolDTO) proto;

        assertEquals(design.getStudyModelCode().value(), dto
                .getStudyModelCode().getCode());
        assertEquals(design.getStudyModelCodeOtherDescription(), dto
                .getStudyModelOtherText().getValue());
        assertEquals(design.getTimePerspectiveCode().value(), dto
                .getTimePerspectiveCode().getCode());
        assertEquals(design.getTimePerspectiveCodeOtherDescription(), dto
                .getTimePerspectiveOtherText().getValue());
        assertEquals(design.getTrialType().value(), dto.getStudySubtypeCode()
                .getCode());

    }

    private void verifyInterventionalDesign(BaseTrialInformation reg,
            StudyProtocolDTO dto) {
        assertTrue(dto instanceof InterventionalStudyProtocolDTO);
        InterventionalTrialDesign design = reg.getInterventionalDesign();
        assertEquals(design.getSecondaryPurpose().value(), dto
                .getSecondaryPurposes().getItem().iterator().next().getValue());
        assertEquals(design.getSecondaryPurposeOtherDescription(), dto
                .getSecondaryPurposeOtherText().getValue());
        assertEquals(PAConstants.INTERVENTIONAL, dto.getStudyProtocolType()
                .getValue());
    }

    protected void verifyOverallStatus(BaseTrialInformation reg,
            ArgumentCaptor<StudyOverallStatusDTO> captor) {
        StudyOverallStatusDTO dto = captor.getValue();
        assertEquals(reg.getTrialStatus().value(), dto.getStatusCode()
                .getCode());
        assertEquals(reg.getWhyStopped(), dto.getReasonText().getValue());
        assertEquals(reg.getTrialStatusDate().toGregorianCalendar().getTime(),
                dto.getStatusDate().getValue());
        assertNull(dto.getIdentifier());
        assertNull(dto.getStudyProtocolIdentifier());

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void verifyStudyIndldeDTOs(BaseTrialInformation reg,
            ArgumentCaptor<List> studyIndldeDTOs) {

        List<StudyIndldeDTO> list = studyIndldeDTOs.getValue();
        StudyIndldeDTO indDTO = list.get(0);
        StudyIndldeDTO ideDTO = list.get(1);

        INDIDE ind = reg.getInd().get(0);
        INDIDE ide = reg.getIde().get(0);

        verifyStudyIndldeDTO(indDTO, ind);
        verifyStudyIndldeDTO(ideDTO, ide);

    }

    protected void verifyStudyIndldeDTO(StudyIndldeDTO ideDTO, INDIDE ide) {
        assertEquals(ide.getExpandedAccessType().value(), ideDTO
                .getExpandedAccessStatusCode().getCode());
        assertEquals(ide.getGrantor().value(), ideDTO.getGrantorCode()
                .getCode());
        assertEquals(ide.getHolderType().value(), ideDTO.getHolderTypeCode()
                .getCode());
        if (ide.getNciDivisionProgramCode() != null) {
            assertEquals(ide.getNciDivisionProgramCode().value(), ideDTO
                    .getNciDivProgHolderCode().getCode());
        }
        if (ide.getNihInstitution() != null) {
            assertEquals(
                    NihInstituteCode.valueOf(ide.getNihInstitution().value())
                            .getCode(), ideDTO.getNihInstHolderCode().getCode());
        }
        assertEquals(ide.getNumber(), ideDTO.getIndldeNumber().getValue());
        assertEquals(ide.isExempt(), ideDTO.getExemptIndicator().getValue()
                .booleanValue());
        assertEquals(ide.isExpandedAccess(), ideDTO
                .getExpandedAccessIndicator().getValue().booleanValue());

    }

    protected void verifyStudyRegAuthDTO(BaseTrialInformation reg,
            ArgumentCaptor<StudyRegulatoryAuthorityDTO> captor) {
        StudyRegulatoryAuthorityDTO dto = captor.getValue();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto);
        } else
            assertEquals("1", dto.getRegulatoryAuthorityIdentifier()
                    .getExtension());
    }

    protected void verifySummary4studyResourcingDTO(BaseTrialInformation reg,
            ArgumentCaptor<StudyResourcingDTO> captor) {
        StudyResourcingDTO dto = captor.getValue();
        if (reg instanceof CompleteTrialRegistration) {
            assertEquals(((CompleteTrialRegistration) reg).getCategory()
                    .value(), dto.getTypeCode().getCode());
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void verifySummary4OrganizationDTO(BaseTrialInformation reg,
            ArgumentCaptor<List> captor) throws NullifiedEntityException {
        List<OrganizationDTO> listDTO = captor.getValue();
        List<Organization> listWs = reg.getSummary4FundingSponsor();

        for (int i = 0; i < listWs.size(); i++) {
            verifyOrganization(listWs.get(i), listDTO.get(i));
        }
    }

    protected void verifyPartyDTO(BaseTrialInformation reg,
            ArgumentCaptor<ResponsiblePartyDTO> captor) {
        ResponsiblePartyDTO dto = captor.getValue();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto.getAffiliation());
            assertNull(dto.getInvestigator());
            assertNull(dto.getType());
        } else {
            ResponsibleParty rp = reg.getResponsibleParty();
            if (ResponsiblePartyType.SPONSOR == rp.getType()) {
                assertEquals(ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR,
                        dto.getType());
                assertNull(dto.getAffiliation());
                assertNull(dto.getInvestigator());
            } else if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR == rp
                    .getType()) {
                assertEquals(
                        ResponsiblePartyDTO.ResponsiblePartyType.PRINCIPAL_INVESTIGATOR,
                        dto.getType());
                assertEquals(rp.getInvestigatorTitle(), dto.getTitle());
                assertEquals(Long.valueOf(3), IiConverter.convertToLong(dto
                        .getAffiliation().getIdentifier()));
            } else if (ResponsiblePartyType.SPONSOR_INVESTIGATOR == rp
                    .getType()) {
                assertEquals(
                        ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR_INVESTIGATOR,
                        dto.getType());
                assertEquals(rp.getInvestigatorTitle(), dto.getTitle());
                assertEquals(Long.valueOf(1), IiConverter.convertToLong(dto
                        .getInvestigator().getIdentifier()));
            }
        }
    }

   

    protected void verifyLeadOrganizationSiteIdentifierDTO(
            BaseTrialInformation reg, ArgumentCaptor<StudySiteDTO> captor) {
        assertEquals(reg.getLeadOrgTrialID(), captor.getValue()
                .getLocalStudyProtocolIdentifier().getValue());
    }

    protected void verifySponsor(BaseTrialInformation reg,
            ArgumentCaptor<OrganizationDTO> captor)
            throws NullifiedEntityException {
        OrganizationDTO dto = captor.getValue();
        Organization org = reg.getSponsor();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto);
        } else {
            verifyOrganization(org, dto);
        }

    }

    protected void verifyPI(BaseTrialInformation reg,
            ArgumentCaptor<PersonDTO> captor) throws NullifiedEntityException {
        Person person = reg.getPi();
        PersonDTO dto = captor.getValue();
        verifyPerson(person, dto);

    }

    private void verifyPerson(Person person, PersonDTO dto)
            throws NullifiedEntityException {
        if (person.getExistingPerson() != null) {
            if (person.getExistingPerson().getPoID() != null) {
                assertEquals(person.getExistingPerson().getPoID(),
                        IiConverter.convertToLong(dto.getIdentifier()));
            } else {
                String ctepID = person.getExistingPerson().getCtepID();
                for (Map.Entry<String, String> e : MockPersonEntityService.PO_ID_TO_CTEP_ID
                        .entrySet()) {
                    if (e.getValue().equals(ctepID)) {
                        assertEquals(e.getKey(), dto.getIdentifier()
                                .getExtension());
                        return;
                    }
                }
                Assert.fail();
            }
        } else {
            Long personID = IiConverter.convertToLong(dto.getIdentifier());
            assertNotNull(personID);
            assertEquals(new MockPersonEntityService().getPerson(dto
                    .getIdentifier()), dto);
            assertEquals(person.getNewPerson().getFirstName(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.GIV));
            assertEquals(person.getNewPerson().getLastName(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.FAM));
            assertEquals(person.getNewPerson().getPrefix(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.PFX));
            assertEquals(person.getNewPerson().getSuffix(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.SFX));
            assertEquals("1029 N Stuart St, Vienna, VA, 22201 USA",
                    AddressConverterUtil.convertToAddress(dto
                            .getPostalAddress()));
            assertEquals(
                    person.getNewPerson().getContact().get(0).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "mailto:").get(0));
            assertEquals(
                    person.getNewPerson().getContact().get(1).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-fax:").get(0));
            assertEquals(person.getNewPerson().getContact().get(2).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(), "tel:")
                            .get(0));
            assertEquals(
                    person.getNewPerson().getContact().get(3).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-tel:").get(0));

        }

    }

    protected void verifyLeadOrganization(BaseTrialInformation reg,
            ArgumentCaptor<OrganizationDTO> captor)
            throws NullifiedEntityException {
        OrganizationDTO dto = captor.getValue();
        Organization org = reg.getLeadOrganization();
        verifyOrganization(org, dto);
    }

    private void verifyOrganization(Organization org, OrganizationDTO dto)
            throws NullifiedEntityException {
        if (org.getExistingOrganization() != null) {
            if (org.getExistingOrganization().getPoID() != null) {
                assertEquals(org.getExistingOrganization().getPoID(),
                        IiConverter.convertToLong(dto.getIdentifier()));
            } else {
                String ctepID = org.getExistingOrganization().getCtepID();
                for (Map.Entry<String, String> e : MockOrganizationEntityService.PO_ID_TO_CTEP_ID
                        .entrySet()) {
                    if (e.getValue().equals(ctepID)) {
                        assertEquals(e.getKey(), dto.getIdentifier()
                                .getExtension());
                        return;
                    }
                }
                Assert.fail();
            }
        } else {
            Long newOrgId = IiConverter.convertToLong(dto.getIdentifier());
            assertNotNull(newOrgId);
            assertEquals(
                    new MockOrganizationEntityService().getOrganization(dto
                            .getIdentifier()), dto);
            assertEquals(org.getNewOrganization().getName(),
                    EnOnConverter.convertEnOnToString(dto.getName()));
            assertEquals("1029 N Stuart St, Vienna, VA, 22201 USA",
                    AddressConverterUtil.convertToAddress(dto
                            .getPostalAddress()));
            assertEquals(
                    org.getNewOrganization().getContact().get(0).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "mailto:").get(0));
            assertEquals(
                    org.getNewOrganization().getContact().get(1).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-fax:").get(0));
            assertEquals(org.getNewOrganization().getContact().get(2)
                    .getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(), "tel:")
                            .get(0));
            assertEquals(
                    org.getNewOrganization().getContact().get(3).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-tel:").get(0));

        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void verifyDocumentDTOs(BaseTrialInformation reg,
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

    protected void verifyDocumentDTO(List<DocumentDTO> list, TrialDocument doc,
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
    protected void verifyStudyResourcingDTOs(BaseTrialInformation reg,
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

    protected void verifyBatchMode(BaseTrialInformation reg,
            ArgumentCaptor<Bl> isBatchMode) {
        assertFalse(isBatchMode.getValue().getValue().booleanValue());
    }
    
    @SuppressWarnings("rawtypes")
    protected void verifyIdentifierAssigners(CompleteTrialRegistration reg,
            ArgumentCaptor<List> captor) {
        assertEquals(2, captor.getValue().size());
        StudySiteDTO dto = (StudySiteDTO) captor.getValue().get(1);
        String dcpId = dto.getLocalStudyProtocolIdentifier().getValue();
        assertEquals(reg.getDcpIdentifier(), dcpId);
   }

}
