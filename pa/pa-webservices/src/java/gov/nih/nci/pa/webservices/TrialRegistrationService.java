/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.converters.DocumentDTOBuilder;
import gov.nih.nci.pa.webservices.converters.OrganizationDTOBuilder;
import gov.nih.nci.pa.webservices.converters.PersonDTOBuilder;
import gov.nih.nci.pa.webservices.converters.ResponsiblePartyDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyIndldeDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyOverallStatusDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyProtocolDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyRegulatoryAuthorityDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyResourcingDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudySiteDTOBuilder;
import gov.nih.nci.pa.webservices.types.AbbreviatedTrialUpdate;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

/**
 * @author dkrylov
 * 
 */
@Path("/")
@Provider
public class TrialRegistrationService extends BaseRestService {

    private static final Logger LOG = Logger
            .getLogger(TrialRegistrationService.class);

    /**
     * Registers an complete trial.
     * 
     * @param reg
     *            CompleteTrialRegistration
     * @return Response
     */
    @POST
    @Path("/trials/complete")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response registerCompleteTrial(
            @Validate CompleteTrialRegistration reg) {
        try {
            StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTOBuilder()
                    .build(reg);
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTOBuilder()
                    .build(reg);
            List<StudyIndldeDTO> studyIndldeDTOs = new StudyIndldeDTOBuilder()
                    .build(reg);
            List<StudyResourcingDTO> studyResourcingDTOs = new StudyResourcingDTOBuilder()
                    .build(reg.getGrant());
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder()
                    .build(reg);
            OrganizationDTO leadOrgDTO = new OrganizationDTOBuilder().build(reg
                    .getLeadOrganization());
            PersonDTO principalInvestigatorDTO = new PersonDTOBuilder()
                    .build(reg.getPi());
            OrganizationDTO sponsorOrgDTO = new OrganizationDTOBuilder()
                    .build(reg.getSponsor());
            ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTOBuilder(
                    principalInvestigatorDTO, sponsorOrgDTO).build(reg);

            StudySiteDTO leadOrgSiteIdDTO = new StudySiteDTO();
            leadOrgSiteIdDTO.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(reg.getLeadOrgTrialID()));

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildClinicalTrialsGovIdAssigner(reg
                            .getClinicalTrialsDotGovTrialID()));

            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildDcpIdAssigner(reg.getDcpIdentifier()));

            List<OrganizationDTO> summary4orgDTO = new OrganizationDTOBuilder()
                    .build(reg.getSummary4FundingSponsor());

            StudyResourcingDTO summary4studyResourcingDTO = new StudyResourcingDTO();
            summary4studyResourcingDTO.setTypeCode(CdConverter
                    .convertToCd(SummaryFourFundingCategoryCode.getByCode(reg
                            .getCategory().value())));
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTOBuilder()
                    .build(reg);

            final DSet<Tel> owners = new DSet<>();
            owners.setItem(new LinkedHashSet<Tel>());
            for (String emailAddr : reg.getTrialOwner()) {
                Tel telEmail = new Tel();
                telEmail.setValue(new URI("mailto:" + emailAddr));
                owners.getItem().add(telEmail);
            }
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
            Ii studyProtocolIi = PaRegistry.getTrialRegistrationService()
                    .createCompleteInterventionalStudyProtocol(
                            studyProtocolDTO, overallStatusDTO,
                            studyIndldeDTOs, studyResourcingDTOs, documentDTOs,
                            leadOrgDTO, principalInvestigatorDTO,
                            sponsorOrgDTO, partyDTO, leadOrgSiteIdDTO,
                            studyIdentifierDTOs, summary4orgDTO,
                            summary4studyResourcingDTO, studyRegAuthDTO,
                            BlConverter.convertToBl(Boolean.FALSE),
                            (owners.getItem().isEmpty() ? null : owners));
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            long paTrialID = IiConverter.convertToLong(studyProtocolIi);
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    /**
     * Updates a complete trial.
     * 
     * @param reg
     *            CompleteTrialRegistration
     * @param trialID
     *            trialID
     * @param idType
     *            idType
     * @return Response
     */
    @POST
    @Path("/trials/complete/{idType}/{trialID}")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response updateCompleteTrial(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID,
            @Validate CompleteTrialUpdate reg) {
        try {
            StudyProtocolDTO spDTO = findTrial(idType, trialID);
            Long paTrialID = IiConverter.convertToLong(spDTO.getIdentifier());

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            if (StringUtils.isBlank(getPaServiceUtils().getStudyIdentifier(
                    spDTO.getIdentifier(), PAConstants.NCT_IDENTIFIER_TYPE))) {
                studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                        .buildClinicalTrialsGovIdAssigner(reg
                                .getClinicalTrialsDotGovTrialID()));
            }
            new StudyProtocolDTOBuilder().build(spDTO, reg);
            List<StudyResourcingDTO> studyResourcingDTOs = new StudyResourcingDTOBuilder()
                    .build(reg.getGrant());
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTOBuilder()
                    .build(spDTO, reg);
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder().build(
                    spDTO, reg);
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
            PaRegistry.getTrialRegistrationService().update(spDTO,
                    overallStatusDTO, studyIdentifierDTOs, null,
                    studyResourcingDTOs, documentDTOs, null, null, null, null,
                    null, null, null, null, null,
                    BlConverter.convertToBl(Boolean.FALSE));
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    /**
     * Updates an abbreviated trial.
     * 
     * @param reg
     *            AbbreviatedTrialUpdate
     * @param trialID
     *            trialID
     * @param idType
     *            idType
     * @return Response
     */
    @POST
    @Path("/trials/abbreviated/{idType}/{trialID}")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response updateAbbreviatedTrial(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID,
            @Validate AbbreviatedTrialUpdate reg) {
        try {
            StudyProtocolDTO spDTO = findTrial(idType, trialID);
            Long paTrialID = IiConverter.convertToLong(spDTO.getIdentifier());

            St nctIdentifier = StConverter.convertToSt(getPaServiceUtils()
                    .getStudyIdentifier(spDTO.getIdentifier(),
                            PAConstants.NCT_IDENTIFIER_TYPE));
            if (ISOUtil.isStNull(nctIdentifier)) {
                nctIdentifier = StConverter.convertToSt(reg
                        .getClinicalTrialsDotGovTrialID());
            }
            new StudyProtocolDTOBuilder().build(spDTO, reg);
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder().build(
                    spDTO, reg);
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
            PaRegistry.getProprietaryTrialService().update(spDTO, null, null,
                    null, nctIdentifier, null, documentDTOs,
                    new ArrayList<StudySiteDTO>(),
                    new ArrayList<StudySiteAccrualStatusDTO>());
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    /**
     * Updates a complete trial.
     * 
     * @param reg
     *            CompleteTrialRegistration
     * @param trialID
     *            trialID
     * @param idType
     *            idType
     * @return Response
     */
    @PUT
    @Path("/trials/complete/{idType}/{trialID}")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response amendCompleteTrial(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID,
            @Validate CompleteTrialAmendment reg) {
        try {
            StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);

            new StudyProtocolDTOBuilder().build(studyProtocolDTO, reg);
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTOBuilder()
                    .build(reg);
            List<StudyIndldeDTO> studyIndldeDTOs = new StudyIndldeDTOBuilder()
                    .build(reg);
            List<StudyResourcingDTO> studyResourcingDTOs = new StudyResourcingDTOBuilder()
                    .build(reg.getGrant());
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder()
                    .build(reg);
            OrganizationDTO leadOrgDTO = new OrganizationDTOBuilder().build(reg
                    .getLeadOrganization());
            PersonDTO principalInvestigatorDTO = new PersonDTOBuilder()
                    .build(reg.getPi());
            OrganizationDTO sponsorOrgDTO = reg
                    .isClinicalTrialsDotGovXmlRequired() ? new OrganizationDTOBuilder()
                    .build(reg.getSponsor()) : null;
            ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTOBuilder(
                    principalInvestigatorDTO, sponsorOrgDTO).build(reg);

            StudySiteDTO leadOrgSiteIdDTO = new StudySiteDTO();
            leadOrgSiteIdDTO.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(reg.getLeadOrgTrialID()));

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildClinicalTrialsGovIdAssigner(reg
                            .getClinicalTrialsDotGovTrialID()));
            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildCtepIdAssigner(reg.getCtepIdentifier()));
            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildDcpIdAssigner(reg.getDcpIdentifier()));

            List<OrganizationDTO> summary4orgDTO = new OrganizationDTOBuilder()
                    .build(reg.getSummary4FundingSponsor());

            StudyResourcingDTO summary4studyResourcingDTO = new StudyResourcingDTO();
            List<StudyResourcingDTO> existingStudyResourcingDTOs = PaRegistry
                    .getStudyResourcingService().getSummary4ReportedResourcing(
                            studyProtocolDTO.getIdentifier());
            if (CollectionUtils.isNotEmpty(existingStudyResourcingDTOs)
                    && existingStudyResourcingDTOs.get(0).getTypeCode() != null) {
                summary4studyResourcingDTO
                        .setTypeCode(existingStudyResourcingDTOs.get(0)
                                .getTypeCode());
            }

            StudyRegulatoryAuthorityDTO studyRegAuthDTO = reg
                    .isClinicalTrialsDotGovXmlRequired() ? new StudyRegulatoryAuthorityDTOBuilder()
                    .build(reg) : null;
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
            Ii amendId = PaRegistry.getTrialRegistrationService().amend(
                    studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs, leadOrgDTO,
                    principalInvestigatorDTO, sponsorOrgDTO, partyDTO,
                    leadOrgSiteIdDTO, studyIdentifierDTOs, summary4orgDTO,
                    summary4studyResourcingDTO, studyRegAuthDTO,
                    BlConverter.convertToBl(Boolean.FALSE),
                    BlConverter.convertToBl(Boolean.TRUE));
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            Long paTrialID = IiConverter.convertToLong(amendId);
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    private Response buildTrialRegConfirmationResponse(long paTrialID) {
        TrialRegistrationConfirmation conf = new TrialRegistrationConfirmation();
        conf.setNciTrialID(getPaServiceUtils().getTrialNciId(paTrialID));
        conf.setPaTrialID(paTrialID);
        return Response.ok(
                new ObjectFactory().createTrialRegistrationConfirmation(conf))
                .build();
    }

    /**
     * Registers an abbreviated trial by importing it from ClinicalTrials.gov.
     * 
     * @param nct
     *            ClinicalTrials.gov ID
     * @return Response
     */
    @POST
    @Path("/trials/abbreviated/{nct}")
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response registerAbbreviatedTrial(@PathParam("nct") String nct) {
        CTGovSyncServiceLocal ctGovSyncService = PaRegistry
                .getCTGovSyncService();
        StudyProtocolServiceLocal studyProtocolService = PaRegistry
                .getStudyProtocolService();
        Response response;
        try {
            response = validateNctId(nct);
            if (response == null) {
                String nciID = ctGovSyncService.importTrial(nct);
                final Long newTrialId = IiConverter
                        .convertToLong(studyProtocolService.getStudyProtocol(
                                IiConverter
                                        .convertToAssignedIdentifierIi(nciID))
                                .getIdentifier());
                return buildTrialRegConfirmationResponse(newTrialId);
            }
        } catch (Exception e) {
            return logErrorAndPrepareResponse(Status.INTERNAL_SERVER_ERROR, e);
        }
        return response;
    }

    private Response validateNctId(String nct) throws PAException {
        CTGovSyncServiceLocal ctGovSyncService = PaRegistry
                .getCTGovSyncService();
        StudyProtocolServiceLocal studyProtocolService = PaRegistry
                .getStudyProtocolService();
        if (StringUtils.isBlank(nct)) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Please provide an ClinicalTrials.gov Identifier value.")
                    .type(TXT_PLAIN).build();
        } else if (!StringUtils.isAlphanumericSpace(nct)) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Provided ClinicalTrials.gov Identifer is invalid.")
                    .type(TXT_PLAIN).build();
        } else if (ctGovSyncService.getAdaptedCtGovStudyByNctId(nct) == null) {
            return Response
                    .status(Status.NOT_FOUND)
                    .entity("A study with the given identifier is not found in ClinicalTrials.gov.")
                    .type(TXT_PLAIN).build();
        } else if (!studyProtocolService.getStudyProtocolsByNctId(// NOPMD
                nct).isEmpty()) {
            return Response
                    .status(Status.PRECONDITION_FAILED)
                    .entity("A study with the given identifier already exists in CTRP.")
                    .type(TXT_PLAIN).build();

        }
        return null;
    }

}
