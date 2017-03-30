package gov.nih.nci.pa.webservices;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.converters.TrialRegisterationWebServiceDTOConverter;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.pa.webservices.dto.CTGovImportLog;
import gov.nih.nci.pa.webservices.dto.StudyProtocolIdentityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.webservices.dto.TrialRegistrationDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

/**
 * 
 * @author Reshma
 *
 *
 */
@Path("/api/v1")
@Provider
public class ImportHelperRestService {
    private static final Logger LOG = Logger
            .getLogger(ImportHelperRestService.class);

    private static final int NOT_FOUND_CODE = 404;
    /**
     * APPLICATION_JSON
     */
    public static final String APPLICATION_JSON = "application/json";
    /**
     * ERROR
     */
    public static final String ERROR = "Error while processing your request. Please try again";

    /**
     * @param nctID
     *            nctID
     * @return Response
     * @throws PAException
     *             PAException
     */
    @GET
    @Path("/studyprotocolIdentity/{nctID}")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response getStudyProtocolIdentity(@PathParam("nctID") String nctID)
            throws PAException {
        List<StudyProtocolIdentityDTO> list = new ArrayList<StudyProtocolIdentityDTO>();
        try {
            List<StudyProtocolQueryDTO> studyProtocolDTOList = findExistentStudies(nctID);
            CTGovStudyAdapter study = PaRegistry.getCTGovSyncService()
                    .getAdaptedCtGovStudyByNctId(nctID);
            if (!studyProtocolDTOList.isEmpty()) {
                for (StudyProtocolQueryDTO dto : studyProtocolDTOList) {
                    list.add(setStudyProtocolIdentityDTO(dto));
                }
            } else {
                studyProtocolDTOList = findExistentStudies(study);
                for (StudyProtocolQueryDTO dto : studyProtocolDTOList) {
                    list.add(setStudyProtocolIdentityDTO(dto));
                }
            }
            if (list.isEmpty()) {
                return Response.status(NOT_FOUND_CODE).entity("NOT Found")
                        .build();
            }
        } catch (Exception e) {
            LOG.error(ERROR + e);
            return Response.serverError().build();
        }
        return Response.ok(list).build();
    }

    private List<StudyProtocolQueryDTO> findExistentStudies(String nctID)
            throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNctNumber(nctID);
        criteria.setExcludeRejectProtocol(true);
        for (StudyProtocolQueryDTO dto : PaRegistry.getProtocolQueryService()
                .getStudyProtocolByCriteria(criteria)) {
            list.add(dto);
        }
        return list;
    }

    private List<StudyProtocolQueryDTO> findExistentStudies(
            CTGovStudyAdapter ctgovStudy) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        if (ctgovStudy != null && StringUtils.isNotBlank(ctgovStudy.getTitle())) {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setOfficialTitle(ctgovStudy.getTitle());
            criteria.setTrialCategory("p");
            criteria.setExcludeRejectProtocol(true);
            for (StudyProtocolQueryDTO dto : PaRegistry
                    .getProtocolQueryService().getStudyProtocolByCriteria(
                            criteria)) {
                if (ctgovStudy.getTitle().equalsIgnoreCase(
                        dto.getOfficialTitle())) {
                    list.add(dto);
                }
            }
        }
        return list;
    }

    private StudyProtocolIdentityDTO setStudyProtocolIdentityDTO(
            StudyProtocolQueryDTO dto) {
        StudyProtocolIdentityDTO studyProtocolIdentityDTO = new StudyProtocolIdentityDTO();
        studyProtocolIdentityDTO.setNctId(dto.getNctIdentifier());
        studyProtocolIdentityDTO.setNciId(dto.getNciIdentifier());
        studyProtocolIdentityDTO.setStudyProtocolId(dto.getStudyProtocolId()
                .toString());
        studyProtocolIdentityDTO.setProprietaryTrial(dto.isProprietaryTrial());
        studyProtocolIdentityDTO.setTrialType(dto.getStudyProtocolType());
        studyProtocolIdentityDTO.setUpdateIndicator(true);
        studyProtocolIdentityDTO.setUserLastCreated(dto.getLastCreated()
                .getUserLastCreated());
        studyProtocolIdentityDTO.setSecondaryIdentifiers(dto
                .getOtherIdentifiers());
        return studyProtocolIdentityDTO;
    }

    /**
     * 
     * @param trialRegistrationDTO
     *            the trialRegistrationDTO
     * @return Response
     * @throws PAException
     *             PAException
     * @throws IOException
     *             IOException
     */
    @PUT
    @Path("/trialregisteration")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response updateTrialRegisteration(
            TrialRegistrationDTO trialRegistrationDTO) throws PAException,
            IOException {
        LOG.info("" + trialRegistrationDTO.getNctID());
        StudyProtocolDTO afterStudyProtocolDTO = new StudyProtocolDTO();
        String nctIdStr = trialRegistrationDTO.getNctID();
        StudyProtocolDTO returnDTO = new StudyProtocolDTO();
        StudyProtocolIdentityDTO finalDTO = new StudyProtocolIdentityDTO();
        try {
            List<StudyProtocolDTO> list = PaRegistry.getStudyProtocolService()
                    .getStudyProtocolsByNctId(nctIdStr);
            for (StudyProtocolDTO dto : list) {
                if (IiConverter.convertToLong(dto.getIdentifier()).equals(
                        Long.parseLong(trialRegistrationDTO
                                .getStudyProtocolDTO().getStudyProtocolId()))) {
                    afterStudyProtocolDTO = new TrialRegisterationWebServiceDTOConverter()
                            .convertToStudyProtocolDTO(
                                    trialRegistrationDTO.getStudyProtocolDTO(),
                                    dto);
                    break;
                }
            }
            OrganizationDTO leadOrgDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTO(trialRegistrationDTO
                            .getLeadOrgDTO());

            StudySiteDTO leadOrgID = new TrialRegisterationWebServiceDTOConverter()
                    .convertToLeadOrgID(trialRegistrationDTO.getLeadOrgID());

            OrganizationDTO sponsorDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTO(trialRegistrationDTO
                            .getSponsorDTO());

            PersonDTO investigatorDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPersonDTO(trialRegistrationDTO
                            .getInvestigatorDTO());

            ResponsiblePartyDTO partyDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPartyDTO(trialRegistrationDTO.getPartyDTO());

            PersonDTO centralContactDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPersonDTO(trialRegistrationDTO
                            .getCentralContactDTO());

            StudyOverallStatusDTO overallStatusDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOverallStatusDTO(trialRegistrationDTO
                            .getOverallStatusDTO());

            List<ArmDTO> arms = new TrialRegisterationWebServiceDTOConverter()
                    .convertToArmsDTOList(trialRegistrationDTO.getArms());

            List<PlannedEligibilityCriterionDTO> eligibility = new TrialRegisterationWebServiceDTOConverter()
                    .convertToEligibilityDTOList(trialRegistrationDTO
                            .getEligibility());

            List<StudyOutcomeMeasureDTO> outcomes = new TrialRegisterationWebServiceDTOConverter()
                    .convertTOOutcomesDTOList(trialRegistrationDTO
                            .getOutcomes());

            List<OrganizationDTO> collaborators = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTOList(trialRegistrationDTO
                            .getCollaborators());

            DocumentDTO document = new TrialRegisterationWebServiceDTOConverter()
                    .convertToDocument(trialRegistrationDTO.getDocument());

            StudySiteDTO nctID = new StudySiteDTO();
            nctID.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(nctIdStr));

            Ii protocolID = PaRegistry.getTrialRegistrationService()
                    .updateAbbreviatedStudyProtocol(afterStudyProtocolDTO,
                            nctID, leadOrgDTO, leadOrgID, sponsorDTO,
                            investigatorDTO, partyDTO, centralContactDTO,
                            overallStatusDTO, null, arms, eligibility,
                            outcomes, collaborators, Arrays.asList(document));
            returnDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    protocolID);
            List<Long> identifiersList = new ArrayList<Long>();
            Long studyprotocolId = IiConverter.convertToLong(protocolID);
            identifiersList.add(studyprotocolId);
            Map<Long, String> identifierMap = PaRegistry
                    .getStudyProtocolService().getTrialNciId(identifiersList);

            if (returnDTO != null) {
                finalDTO.setNciId(identifierMap.get(studyprotocolId));
                finalDTO.setNctId(nctIdStr);
                finalDTO.setStudyProtocolId(IiConverter
                        .convertToString(protocolID));
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctIdStr);
            return Response.serverError().build();
        }
        return Response.ok(finalDTO).build();
    }

    /**
     * 
     * @param trialRegistrationDTO
     *            trialRegistrationDTO
     * @return Response
     * @throws PAException
     *             PAException
     * @throws IOException
     *             IOException
     */
    @POST
    @Path("/trialregisteration")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response createTrialRegisteration(
            TrialRegistrationDTO trialRegistrationDTO) throws PAException,
            IOException {
        LOG.info("" + trialRegistrationDTO.getNctID());
        String nctIdStr = trialRegistrationDTO.getNctID();
        StudyProtocolDTO returnDTO = new StudyProtocolDTO();
        StudyProtocolIdentityDTO finalDTO = new StudyProtocolIdentityDTO();
        try {
            StudyProtocolDTO afterStudyProtocolDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToStudyProtocolDTO(
                            trialRegistrationDTO.getStudyProtocolDTO(),
                            new StudyProtocolDTO());
            OrganizationDTO leadOrgDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTO(trialRegistrationDTO
                            .getLeadOrgDTO());

            StudySiteDTO leadOrgID = new TrialRegisterationWebServiceDTOConverter()
                    .convertToLeadOrgID(trialRegistrationDTO.getLeadOrgID());

            OrganizationDTO sponsorDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTO(trialRegistrationDTO
                            .getSponsorDTO());

            PersonDTO investigatorDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPersonDTO(trialRegistrationDTO
                            .getInvestigatorDTO());

            ResponsiblePartyDTO partyDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPartyDTO(trialRegistrationDTO.getPartyDTO());

            PersonDTO centralContactDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToPersonDTO(trialRegistrationDTO
                            .getCentralContactDTO());

            StudyOverallStatusDTO overallStatusDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOverallStatusDTO(trialRegistrationDTO
                            .getOverallStatusDTO());

            List<ArmDTO> arms = new TrialRegisterationWebServiceDTOConverter()
                    .convertToArmsDTOList(trialRegistrationDTO.getArms());

            List<PlannedEligibilityCriterionDTO> eligibility = new TrialRegisterationWebServiceDTOConverter()
                    .convertToEligibilityDTOList(trialRegistrationDTO
                            .getEligibility());

            List<StudyOutcomeMeasureDTO> outcomes = new TrialRegisterationWebServiceDTOConverter()
                    .convertTOOutcomesDTOList(trialRegistrationDTO
                            .getOutcomes());

            List<OrganizationDTO> collaborators = new TrialRegisterationWebServiceDTOConverter()
                    .convertToOrganizationDTOList(trialRegistrationDTO
                            .getCollaborators());

            DocumentDTO document = new TrialRegisterationWebServiceDTOConverter()
                    .convertToDocument(trialRegistrationDTO.getDocument());

            StudySiteDTO nctID = new StudySiteDTO();
            nctID.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(nctIdStr));

            Ii protocolID = PaRegistry.getTrialRegistrationService()
                    .createAbbreviatedStudyProtocol(afterStudyProtocolDTO,
                            nctID, leadOrgDTO, leadOrgID, sponsorDTO,
                            investigatorDTO, partyDTO, centralContactDTO,
                            overallStatusDTO, null, arms, eligibility,
                            outcomes, collaborators, Arrays.asList(document));
            returnDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    protocolID);

            List<Long> identifiersList = new ArrayList<Long>();
            Long studyprotocolId = IiConverter.convertToLong(protocolID);
            identifiersList.add(studyprotocolId);
            Map<Long, String> identifierMap = PaRegistry
                    .getStudyProtocolService().getTrialNciId(identifiersList);

            if (returnDTO != null) {
                finalDTO.setNciId(identifierMap.get(studyprotocolId));
                finalDTO.setNctId(nctIdStr);
                finalDTO.setStudyProtocolId(IiConverter
                        .convertToString(protocolID));
            }
        } catch (Exception e) {
            LOG.error(ERROR + nctIdStr);
            return Response.serverError().build();
        }
        return Response.ok(finalDTO).build();
    }

    /**
     * 
     * @param log
     *            log
     * @return Response
     */
    @POST
    @Path("/importlog")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response createImportLogEntry(CTGovImportLog log) {
        StudyInboxDTO recent = null;
        try {
            if (log.getStudyInboxId() != null) {
                recent = new StudyInboxDTO();
                recent.setIdentifier(IiConverter.convertToIi(log
                        .getStudyInboxId()));
            }
            PaRegistry.getCTGovSyncService().createImportLogEntry(
                    log.getNciId(), log.getNctId(), log.getTitle(),
                    log.getAction(), log.getImportStatus(),
                    log.getUserCreated(), log.isNeedsReview(),
                    log.isAdminChanged(), log.isScientificChanged(), recent);
        } catch (Exception e) {
            LOG.error(ERROR + log.getNciId());
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
}
