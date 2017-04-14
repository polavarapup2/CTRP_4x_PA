package gov.nih.nci.pa.webservices;

import static org.apache.commons.lang.StringUtils.left;
import gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
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
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.converters.TrialRegisterationWebServiceDTOConverter;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.pa.webservices.dto.AgeDTO;
import gov.nih.nci.pa.webservices.dto.CTGovImportLogWebService;
import gov.nih.nci.pa.webservices.dto.ProtocolSnapshotDTO;
import gov.nih.nci.pa.webservices.dto.StudyProtocolIdentityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.webservices.dto.TrialRegistrationDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

import org.apache.commons.collections.CollectionUtils;
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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class ImportHelperRestService { // NOPMD
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
    private static final String ELIGIBILITY = "eligibilityCriteria";
    private static final String ARMS = "arms";
    private static final int L_5000 = 5000;
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
        List<StudyProtocolQueryDTO> dtoList = PaRegistry.getProtocolQueryService()
                .getStudyProtocolByCriteria(criteria);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            for (StudyProtocolQueryDTO dto : dtoList) {
                list.add(dto);
            }
        }
        return list;
    }

//    private List<StudyProtocolQueryDTO> findExistentStudies(
//            CTGovStudyAdapter ctgovStudy) throws PAException {
//        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
//        if (ctgovStudy != null && StringUtils.isNotBlank(ctgovStudy.getTitle())) {
//            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
//            criteria.setOfficialTitle(ctgovStudy.getTitle());
//            criteria.setTrialCategory("p");
//            criteria.setExcludeRejectProtocol(true);
//            List<StudyProtocolQueryDTO> dtoList = PaRegistry
//                    .getProtocolQueryService().getStudyProtocolByCriteria(
//                            criteria);
//            if (CollectionUtils.isNotEmpty(dtoList)) {
//                for (StudyProtocolQueryDTO dto : dtoList) {
//                    if (ctgovStudy.getTitle().equalsIgnoreCase(
//                            dto.getOfficialTitle())) {
//                        list.add(dto);
//                    }
//                }
//            }
//        }
//        return list;
//    }

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
    public Response createImportLogEntry(CTGovImportLogWebService log) {
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

        Map<String, CTGovImportLogWebService> map = new HashMap<String, CTGovImportLogWebService>();
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
                    map.put(nctIdentifier, setLogEntries(associatedImportLogs.get(0)));
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
     
    private CTGovImportLogWebService setLogEntries(CTGovImportLog log) {
        CTGovImportLogWebService returnDto = new CTGovImportLogWebService();
        returnDto.setNciId(log.getNciID());
        returnDto.setNctId(log.getNctID());
        returnDto.setTitle(log.getTitle());
        returnDto.setAction(log.getAction());
        returnDto.setImportStatus(log.getImportStatus());
        if (log.getReviewRequired() != null) {
            returnDto.setNeedsReview(log.getReviewRequired());
        }
        if (log.getAdmin() != null) {
            returnDto.setAdminChanged(log.getAdmin());
        }
        if (log.getScientific() != null) {
            returnDto.setScientificChanged(log.getScientific());
        }
        if (log.getStudyInbox() != null) {
            returnDto.setStudyInboxId(log.getStudyInbox().getId());
        }
        returnDto.setUserCreated(log.getUserCreated());
        returnDto.setDateCreated(log.getDateCreated().toString());
        return returnDto;
    }
    /**
     * Gets the CT.Gov import log entries with matching NCT identifier.
     * @param nctIdentifier NCT identifier to match.
     * @return list of CT.Gov import log entries with matching NCT identifer.
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    private List<CTGovImportLog> getLogEntries(String nctIdentifier)
            throws PAException {
        String hqlQuery = "from CTGovImportLog log where log.nctID = :nctID and " 
            + "log.importStatus = :importStatus order by log.dateCreated DESC LIMIT 1";
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hqlQuery);
        query.setParameter("nctID", nctIdentifier);
        query.setParameter("importStatus", "Success");
        return query.list();
    }
    
    // get the protocol snapshot and the studyinbox 
    //map<String, DTO>
    /**
     * 
     * @param spID
     *            spID
     * @return Response
     */
    @GET
    @Path("/snapshot/{spID}")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted

    public Response getProtocolSnapshotWithSInboxID(
            @PathParam("spID") String spID) {
        ProtocolSnapshotDTO returnDto = null;
        try {
            Ii studyProtocolId = IiConverter.convertToIi(spID);
            returnDto = setSnapshotMap(studyProtocolId);
            
            List<StudyInboxDTO> inboxEntries = PaRegistry
                    .getStudyInboxService()
                    .getOpenInboxEntries(studyProtocolId);
            Ii recent = null;
            if (!inboxEntries.isEmpty()) {
                recent = inboxEntries.get(0).getIdentifier();
            }
            if (!ISOUtil.isIiNull(recent)) {
                returnDto.setStudyInboxId(IiConverter.convertToString(recent));
            }
        } catch (Exception e) {
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
        }
        return Response.ok(returnDto).build();
    }
    
    private List<String> getOgnls() throws PAException {
       return (Arrays.asList(PaRegistry
                .getLookUpTableService()
                .getPropertyValue("ctgov.sync.fields_of_interest")
                .split(";")));
    }
    
    private List<gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO> getEligibility(
            Ii studyProtocolId) throws PAException {
        List<gov.nih.nci.pa.webservices.dto
        .PlannedEligibilityCriterionDTO> resturneligibility = new ArrayList<gov.nih
                .nci.pa.webservices.dto.PlannedEligibilityCriterionDTO>();
        List<PlannedEligibilityCriterionDTO> pecList = PaRegistry
                .getPlannedActivityService()
                .getPlannedEligibilityCriterionByStudyProtocol(
                        studyProtocolId);
        if (CollectionUtils.isNotEmpty(pecList)) {
            for (PlannedEligibilityCriterionDTO dto : pecList) {
                resturneligibility.add(setEligibility(dto));
            }
        }
        return resturneligibility;
    }
    private gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO setEligibility(
            PlannedEligibilityCriterionDTO dto) {
        gov.nih.nci.pa.webservices.dto.PlannedEligibilityCriterionDTO eligibilityDTO = new gov.nih.nci
                .pa.webservices.dto.PlannedEligibilityCriterionDTO();
        eligibilityDTO.setCategoryCode(CdConverter.convertCdToString(dto
                .getCategoryCode()));
        eligibilityDTO.setInclusionIndicator(BlConverter.convertToBool(dto
                .getInclusionIndicator()));
        eligibilityDTO.setCdePublicIdentifier(IiConverter.convertToLong(dto
                .getCdePublicIdentifier()));
        eligibilityDTO.setCdeVersionNumber(StConverter.convertToString(dto
                .getCdeVersionNumber()));
        eligibilityDTO.setCriterionName(StConverter.convertToString(dto
                .getCriterionName()));
        eligibilityDTO.setDisplayOrder(IntConverter.convertToInteger(dto
                .getDisplayOrder()));
        eligibilityDTO.setEligibleGenderCode(CdConverter.convertCdToString(dto
                .getEligibleGenderCode()));
        eligibilityDTO.setInterventionIdentifier(IiConverter.convertToLong(dto
                .getInterventionIdentifier()));
        eligibilityDTO.setLeadProductIndicator(BlConverter.convertToBool(dto
                .getLeadProductIndicator()));
        eligibilityDTO.setOperator(StConverter.convertToString(dto
                .getOperator()));
        eligibilityDTO.setStructuredIndicator(BlConverter.convertToBool(dto
                .getStructuredIndicator()));
        eligibilityDTO.setUserLastCreated(StConverter.convertToString(dto
                .getUserLastCreated()));
        eligibilityDTO.setTextValue(StConverter.convertToString(dto
                .getTextValue()));
        eligibilityDTO.setTextDescription(StConverter.convertToString(dto
                .getTextDescription()));
        eligibilityDTO.setIdentifier(IiConverter.convertToLong(dto.getIdentifier()));
        AgeDTO ageMaxDto = null;
        AgeDTO ageMinDto = null;
        if (dto.getValue() != null) {
            ageMaxDto = new AgeDTO();
            ageMinDto = new AgeDTO();
            if (!ISOUtil.isIvlHighNull(dto.getValue())) {
                ageMaxDto.setValue(dto.getValue().getHigh().getValue());
                if (dto.getValue().getHigh().getPrecision() != null) {
                    ageMaxDto.setPrecision(dto.getValue().getHigh()
                            .getPrecision());
                }

            }
            if (!ISOUtil.isIvlUnitNull(dto.getValue())) {
                ageMaxDto.setUnitCode(dto.getValue().getHigh().getUnit());
            }
            if (!ISOUtil.isIvlLowNull(dto.getValue())) {

                ageMinDto.setValue(dto.getValue().getLow().getValue());
                if (dto.getValue().getHigh().getPrecision() != null) {
                    ageMinDto.setPrecision(dto.getValue().getLow()
                            .getPrecision());
                }
            }
            if (!ISOUtil.isIvlUnitNull(dto.getValue())) {
                ageMinDto.setUnitCode(dto.getValue().getLow().getUnit());
            }
        }
        eligibilityDTO.setMinValue(ageMinDto);
        eligibilityDTO.setMaxValue(ageMaxDto);
        return eligibilityDTO;
    }
    
    private List<gov.nih.nci.pa.webservices.dto.ArmDTO> getArms(Ii studyProtocolId) throws PAException {
        List<gov.nih.nci.pa.webservices.dto.ArmDTO> resturnArms = new ArrayList<gov.nih.nci
                .pa.webservices.dto.ArmDTO>();
        List<ArmDTO> armIsoList = PaRegistry.getArmService()
                .getByStudyProtocol(studyProtocolId);

        for (ArmDTO arm : armIsoList) {
            resturnArms.add(setArms(arm));
        }
        return resturnArms;
    }
    private gov.nih.nci.pa.webservices.dto.ArmDTO setArms(ArmDTO arm) {
        gov.nih.nci.pa.webservices.dto.ArmDTO arm1 = new gov.nih.nci.pa.webservices.dto.ArmDTO();
        arm1.setDescriptionText(StConverter.convertToString(arm
                .getDescriptionText()));
        arm1.setName(StConverter.convertToString(arm.getName()));
        arm1.setTypeCode(CdConverter.convertCdToString(arm
                .getTypeCode()));
        arm1.setId(IiConverter.convertToLong(arm.getIdentifier()));
        return arm1;
    }
    
    private  ProtocolSnapshotDTO setSnapshotMap(Ii studyProtocolId) throws PAException {
        
        ProtocolSnapshotDTO returnDto = new ProtocolSnapshotDTO();
        returnDto.setArmList(getArms(studyProtocolId));
        
        returnDto.setEligibilityList(getEligibility(studyProtocolId));
        StudyProtocolDTO studyDTO = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(studyProtocolId);
        for (String ognl : getOgnls()) {
            List<String> innerValues = Arrays.asList(ognl.split("\\."));
            if (innerValues != null && innerValues.size() == 2) {
                if (innerValues.get(0).equals("studyProtocol")) {
                    if (innerValues.get(1).equals("publicDescription")) {
                        returnDto.setPublicDescription(StConverter.convertToString(studyDTO
                                .getPublicDescription()));
                    } else if (innerValues.get(1).equals(
                            "scientificDescription")) {
                        returnDto.setScientificDescription(StConverter.convertToString(studyDTO
                                .getScientificDescription()));
                    } else if (innerValues.get(1).equals("keywordText")) {
                        returnDto.setKeywordText(StConverter.convertToString(studyDTO
                                .getKeywordText()));
                    }
                }
            }
        }
        return returnDto;
    }
    
    /**
     * 
     * @param spID spID
     * @param before before
     * @return Response
     */
    // post processing 
    @PUT
    @Path("/postprocessing/{spID}")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response postProcessingCall(@PathParam("spID") String spID, ProtocolSnapshotDTO before) {
        ProtocolSnapshotDTO after = new ProtocolSnapshotDTO();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Ii studyProtocolId = IiConverter.convertToIi(spID);
            after = setSnapshotMap(studyProtocolId);
            final boolean needsReview = needsReview(before, after);
            final boolean adminChanged = false;
            final boolean scientificChanged = needsReview;
            if (needsReview) {
                attachListOfChangedFieldsToInboxEntry(
                        studyProtocolId, before, after,
                        adminChanged, scientificChanged);
            }
            PaRegistry.getCTGovSyncService().closeStudyInboxAndAcceptTrialIfNeeded(
                    studyProtocolId, needsReview, before.getLastChangedDate());
            returnMap.put("needsReview", needsReview);
            returnMap.put("adminChanged", adminChanged);
            returnMap.put("scientificChanged", scientificChanged);
        } catch (Exception e) {
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage())
                    .build();
        }
        return Response.ok(returnMap).build();
    }
    
    /**
     * 
     * @param startDate start date of job
     * @return Response
     */
    //email sync job import logs
    @POST
    @Path("/emailsynclogs")
    @NoCache
    @Formatted
    public Response emailSyncJobImportLogs(Date startDate) {
        LOG.info("Received a request to email import logs at end of import sync job started at " + startDate);
        try {
            //Record the finish time of nightly job
            Date endDate = new Date();
            //Get the log entries which got created between start date and end date
            CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();
            searchCriteria.setOnOrAfter(startDate);
            searchCriteria.setOnOrBefore(endDate);
            List<CTGovImportLog> logEntries = PaRegistry.getCTGovSyncService().getLogEntries(searchCriteria);
            if (logEntries != null && !logEntries.isEmpty()) {
                //Send a status e-mail with a summary of trials in CTRP updated from CTGov to 
                //authorized users
                PaRegistry.getMailManagerService().sendCTGovSyncStatusSummaryMail(logEntries);                    
            }
            
        } catch (Exception e) {
            LOG.error(ERROR, e);
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Success").build();
    }
    
    /**
     * 
     * @param before before
     * @param after after
     * @return boolean
     */

    public boolean needsReview(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        
          boolean eligibilityChange = isEligibilityChange(before, after);
        
        boolean armChange = isArmsChange(before, after);
        
        boolean pubDescChange = isPublicDescChange(before, after);

        boolean sciChange = isScientificDescChange(before, after);
        
        boolean keyWordChange = isKeywordChange(before, after);
        
        if (!eligibilityChange || !armChange || !pubDescChange || !sciChange || !keyWordChange) {
            return true;
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private boolean isEligibilityChange(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        Set<gov.nih.nci.pa.webservices.dto
        .PlannedEligibilityCriterionDTO> eligibility1 = (Set<gov.nih.nci.pa.webservices
                .dto.PlannedEligibilityCriterionDTO>) new HashSet();
        eligibility1.addAll(before.getEligibilityList());
        
        Set<gov.nih.nci.pa.webservices.dto
        .PlannedEligibilityCriterionDTO>  eligibility2 = (Set<gov.nih.nci.pa.webservices
                .dto.PlannedEligibilityCriterionDTO>) new HashSet();
        eligibility2.addAll(after.getEligibilityList());
        
        return (Objects.deepEquals(eligibility1, eligibility2));
    }
    
    @SuppressWarnings("unchecked")
    private boolean isArmsChange(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        Set<gov.nih.nci.pa.webservices.dto.ArmDTO> arms1 = (Set<gov.nih.nci.pa.webservices.dto.ArmDTO>) new HashSet();
        arms1.addAll(before.getArmList());
        Set<gov.nih.nci.pa.webservices.dto.ArmDTO> arms2 = (Set<gov.nih.nci.pa.webservices.dto.ArmDTO>) new HashSet();
        arms2.addAll(after.getArmList());
        return (Objects.deepEquals(arms1, arms2));
    }
    
    private boolean isPublicDescChange(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        String desc1 = before.getPublicDescription();
        String desc2 = after.getPublicDescription();
        return (StringUtils.equals(desc1, desc2));
    }
    
    private boolean isScientificDescChange(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        String desc1 = before.getScientificDescription();
        String desc2 = after.getScientificDescription();
        return (StringUtils.equals(desc1, desc2));
    }
    
    private boolean isKeywordChange(ProtocolSnapshotDTO before, ProtocolSnapshotDTO after) {
        String desc1 = before.getKeywordText();
        String desc2 = after.getKeywordText();
        return (StringUtils.equals(desc1, desc2));
    }
    private void attachListOfChangedFieldsToInboxEntry(Ii studyProtocolIi,
            ProtocolSnapshotDTO before, ProtocolSnapshotDTO after,
            boolean adminChanged, boolean scientificChanged) throws PAException {
        StudyInboxDTO recent = null;
        final List<String> differences = findDifferences(before, after);
        List<StudyInboxDTO> inboxEntries = PaRegistry.getStudyInboxService()
                .getOpenInboxEntries(studyProtocolIi);
        if (!inboxEntries.isEmpty()) {
            recent = inboxEntries.get(0);
            recent.setAdmin(BlConverter.convertToBl(adminChanged));
            recent.setScientific(BlConverter.convertToBl(scientificChanged));
            for (String diff : differences) {
                String fieldLabel = PaRegistry.getCTGovSyncService().getFieldLabel(diff);
                String currentComments = StringUtils.defaultString(StConverter
                        .convertToString(recent.getComments()));
                String newComments = left(currentComments
                        + gov.nih.nci.pa.util.TrialUpdatesRecorder.SEPARATOR
                        + fieldLabel + " changed", L_5000);
                recent.setComments(StConverter.convertToSt(newComments));
            }
            PaRegistry.getStudyInboxService().update(recent);
        }
    }
    
    private List<String> findDifferences(ProtocolSnapshotDTO before,
            ProtocolSnapshotDTO after) {
        List<String> values = new ArrayList<String>();
        if (!isEligibilityChange(before, after)) {
            values.add(ELIGIBILITY);
        }
        if (!isArmsChange(before, after)) {
            values.add(ARMS);
        }
        if (!isPublicDescChange(before, after)) {
            values.add("studyProtocol.publicDescription");
        }
        if (!isScientificDescChange(before, after)) {
            values.add("studyProtocol.scientificDescription");
        }
        if (!isKeywordChange(before, after)) {
            values.add("studyProtocol.keywordText");
        }
        return values;
    }

}
