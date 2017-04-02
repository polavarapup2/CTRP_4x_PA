package gov.nih.nci.pa.webservices;

import gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.IdentifierType;
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
import gov.nih.nci.pa.util.PaHibernateUtil;
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
import java.util.HashMap;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
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
    private StudySiteDTO leadOrgID = new StudySiteDTO();
    private OrganizationDTO sponsorDTO = new OrganizationDTO();
    private PersonDTO investigatorDTO = new PersonDTO();
    private ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTO();
    private PersonDTO centralContactDTO = new PersonDTO();
    private StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
    private List<ArmDTO> arms = new ArrayList<ArmDTO>();
    private List<PlannedEligibilityCriterionDTO> eligibility = new ArrayList<PlannedEligibilityCriterionDTO>();
    private List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
    private List<OrganizationDTO> collaborators = new ArrayList<OrganizationDTO>();
    private DocumentDTO document = new DocumentDTO();
    private OrganizationDTO leadOrgDTO = new OrganizationDTO();

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
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
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
        StudyProtocolDTO afterStudyProtocolDTO = null;
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
            convertDTOs(trialRegistrationDTO);

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
            LOG.error(ERROR + nctIdStr, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
        }
        return Response.ok(finalDTO).build();
    }

    private void convertDTOs(TrialRegistrationDTO trialRegistrationDTO)
            throws IOException {
        leadOrgDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToOrganizationDTO(trialRegistrationDTO.getLeadOrgDTO());

        leadOrgID = new TrialRegisterationWebServiceDTOConverter()
                .convertToLeadOrgID(trialRegistrationDTO.getLeadOrgID());

        sponsorDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToOrganizationDTO(trialRegistrationDTO.getSponsorDTO());

        investigatorDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToPersonDTO(trialRegistrationDTO.getInvestigatorDTO());

        partyDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToPartyDTO(trialRegistrationDTO.getPartyDTO());

        centralContactDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToPersonDTO(trialRegistrationDTO.getCentralContactDTO());

        overallStatusDTO = new TrialRegisterationWebServiceDTOConverter()
                .convertToOverallStatusDTO(trialRegistrationDTO
                        .getOverallStatusDTO());

        arms = new TrialRegisterationWebServiceDTOConverter()
                .convertToArmsDTOList(trialRegistrationDTO.getArms());

        eligibility = new TrialRegisterationWebServiceDTOConverter()
                .convertToEligibilityDTOList(trialRegistrationDTO
                        .getEligibility());

        outcomes = new TrialRegisterationWebServiceDTOConverter()
                .convertTOOutcomesDTOList(trialRegistrationDTO.getOutcomes());

        collaborators = new TrialRegisterationWebServiceDTOConverter()
                .convertToOrganizationDTOList(trialRegistrationDTO
                        .getCollaborators());

        document = new TrialRegisterationWebServiceDTOConverter()
                .convertToDocument(trialRegistrationDTO.getDocument());
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
        StudyProtocolDTO newDTO = null;
        try {
            if (trialRegistrationDTO.getStudyProtocolDTO() instanceof InterventionalStudyProtocolDTO) {
                newDTO = new gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO();
            } else {
                newDTO = new gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO();
            }
            StudyProtocolDTO afterStudyProtocolDTO = new TrialRegisterationWebServiceDTOConverter()
                    .convertToStudyProtocolDTO(
                            trialRegistrationDTO.getStudyProtocolDTO(), newDTO);
            convertDTOs(trialRegistrationDTO);
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
            LOG.error(ERROR + nctIdStr, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
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
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
        }
        return Response.ok("Success").build();
    }

    /**
     * 
     * @return Response
     */
    @GET
    @Path("/importlog")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response getIndustrialConsortiaTrialsWithNCTIds() {

        Map<String, CTGovImportLog> map = new HashMap<String, CTGovImportLog>();
        // Query all industrial and consortia trials with NCT identifiers in CTRP.
        try {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setIdentifierType(IdentifierType.NCT.getCode());
            criteria.setNctNumber("NCT");
            criteria.setTrialCategory("p");
            criteria.setExcludeRejectProtocol(true);
            List<StudyProtocolQueryDTO> trials = PaRegistry
                    .getProtocolQueryService().getStudyProtocolByCriteria(
                            criteria);
            // Loop over all the trials
            for (StudyProtocolQueryDTO trial : trials) {
                String nctIdentifier = trial.getNctIdentifier();
                List<CTGovImportLog> associatedImportLogs = getLogEntries(nctIdentifier);
                if (associatedImportLogs != null && !associatedImportLogs.isEmpty()) {
                    map.put(nctIdentifier, associatedImportLogs.get(0));
                } else {
                    map.put(nctIdentifier, null);
                }
                
            }
        } catch (Exception e) {
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
        }
        return Response.ok(map).build();
    }
    /**
     * Gets the CT.Gov import log entries with matching NCT identifier.
     * @param nctIdentifier NCT identifier to match.
     * @return list of CT.Gov import log entries with matching NCT identifer.
     * @throws PAException PAException
     */
    private List<CTGovImportLog> getLogEntries(String nctIdentifier)
            throws PAException {
        String hqlQuery = "from CTGovImportLog log where log.nctID = :nctID and " 
            + "log.importStatus = :importStatus order by log.dateCreated DESC";
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hqlQuery);
        query.setParameter("nctID", nctIdentifier);
        query.setParameter("importStatus", "Success");
        return query.list();
    }
    
    // get the protocol snapshot and the studyinbox 
    //map<String, DTO>
    
}
