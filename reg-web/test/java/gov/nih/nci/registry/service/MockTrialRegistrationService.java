/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockTrialRegistrationService implements TrialRegistrationServiceLocal{

    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            Bl isBatchMode) throws PAException {
                if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
                    throw new PAException("test");
                }
                return IiConverter.convertToIi("2");
        }

    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            Bl isBatchMode) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }
    
    public void reject(Ii studyProtocolIi, St rejectionReason, Cd rejectionReasonCode, 
              MilestoneCode milestoneCode) throws PAException {
        // TODO Auto-generated method stub

    }

    public Ii createAbbreviatedInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO,
            OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

    public void update(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudySiteDTO> studyIdentifierDTOs,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaborators,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses,
            List<StudySiteDTO> studySites, Bl isBatchMode) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, List<StudySiteDTO> studySiteDTOs, 
            Bl isBatchMode)
            throws PAException {
    }

    @Override
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            DSet<Tel> owners) throws PAException {

        return createCompleteInterventionalStudyProtocol(studyProtocolDTO,
                overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                studyIdentifierDTOs, studyContactDTO, studySiteContactDTO,
                summary4organizationDTO, summary4studyResourcingDTO,
                responsiblePartyContactIi, studyRegAuthDTO, isBatchMode);
    }

    @Override
    public Ii createAbbreviatedInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO,
            OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode,
            DSet<Tel> owners) throws PAException {
        return createAbbreviatedInterventionalStudyProtocol(studyProtocolDTO,
                studySiteAccrualStatusDTO, documentDTOs, leadOrganizationDTO,
                studySiteInvestigatorDTO, leadOrganizationStudySiteDTO,
                studySiteOrganizationDTO, studySiteDTO, nctIdentifierDTO,
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                isBatchMode);
    }

    @Override
    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            Bl handleDuplicateGrantAndINDsGracefully) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("2");
    }

    
    public Ii createAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO partyDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

   
    public Ii updateAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID,OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO responsiblePartyDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

    @Override
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            DSet<Tel> owners) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

    @Override
    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("2");
    }

    @Override
    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            Bl handleDuplicateGrantAndINDsGracefully) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("2");
    }

    @Override
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            List<StudyOverallStatusDTO> statusHistory,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue()
                .equals("testthrowException")) {
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

    @Override
    public void update(StudyProtocolDTO studyProtocolDTO,
            List<StudyOverallStatusDTO> overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatchMode)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            List<StudyOverallStatusDTO> statusHistory,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("2");
    }

}
