/**
 * 
 */
package gov.nih.nci.accrual.webservices;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.webservices.types.BatchFile;
import gov.nih.nci.accrual.webservices.types.DiseaseCode;
import gov.nih.nci.accrual.webservices.types.Race;
import gov.nih.nci.accrual.webservices.types.StudySubject;
import gov.nih.nci.accrual.webservices.types.StudySubjects;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.annotations.cache.NoCache;

/**
 * @author dkrylov
 * 
 */
@Path("/")
@Provider
public final class AccrualService extends BaseRestService { // NOPMD

    private static final String POID2 = "poid";
    private static final String TRIAL_ID = "trialID";
    private static final String ID_TYPE = "idType";

    /**
     * @param siteID siteID
     * @param count count
     * @return Response
     */
    @PUT
    @Path("/sites/{siteID}/count")
    @Consumes({ TXT_PLAIN })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response updateAccrualCount(@PathParam("siteID") long siteID,
            int count) {
        try {
            StudySiteDTO siteDTO = findSite(IiConverter
                    .convertToStudySiteIi(siteID));
            AccrualServiceLocator
                    .getInstance()
                    .getSubjectAccrualService()
                    .updateSubjectAccrualCount(siteDTO.getIdentifier(),
                            IntConverter.convertToInt(count),
                            AccrualSubmissionTypeCode.SERVICE);
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * @param idType idType
     * @param trialID trialID
     * @param poid poid
     * @param count count
     * @return Response
     */
    @PUT
    @Path("/trials/{idType}/{trialID}/sites/po/{poid}/count")
    @Consumes({ TXT_PLAIN })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response updateAccrualCount(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID, @PathParam(POID2) long poid,
            int count) {
        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, poid);
            AccrualServiceLocator
                    .getInstance()
                    .getSubjectAccrualService()
                    .updateSubjectAccrualCount(siteDTO.getIdentifier(),
                            IntConverter.convertToInt(count),
                            AccrualSubmissionTypeCode.SERVICE);
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // CHECKSTYLE:OFF
    
    @PUT
    @Path("/trials/{idType}/{trialID}/sites/ctep/{id}/count")
    @Consumes({ TXT_PLAIN })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response updateAccrualCount(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID,
            @PathParam("id") String ctepID, int count) {
        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, ctepID);
            AccrualServiceLocator
                    .getInstance()
                    .getSubjectAccrualService()
                    .updateSubjectAccrualCount(siteDTO.getIdentifier(),
                            IntConverter.convertToInt(count),
                            AccrualSubmissionTypeCode.SERVICE);
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DELETE
    @Path("/sites/{siteID}/subjects/{subjectID}")
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response deleteStudySubject(@PathParam("siteID") long siteID,
            @PathParam("subjectID") String subjectID) {
        return deleteStudySubject(IiConverter.convertToStudySiteIi(siteID),
                subjectID);
    }

    @DELETE
    @Path("/trials/{idType}/{trialID}/sites/po/{id}/subjects/{subjectID}")
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response deleteStudySubject(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID, @PathParam("id") long poid,
            @PathParam("subjectID") String subjectID) {

        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, poid);
            return deleteStudySubject(siteDTO.getIdentifier(), subjectID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    @DELETE
    @Path("/trials/{idType}/{trialID}/sites/ctep/{ctepID}/subjects/{subjectID}")
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response deleteStudySubject(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID,
            @PathParam("ctepID") String ctepID,
            @PathParam("subjectID") String subjectID) {
        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, ctepID);
            return deleteStudySubject(siteDTO.getIdentifier(), subjectID);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * @param id
     *            site PA ID
     * @param ps
     *            ParticipatingSite
     * @return Response
     */
    @PUT
    @Path("/sites/{id}")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response submitStudySubjects(@PathParam("id") long id,
            @Validate StudySubjects ps) {
        return submitStudySubjects(IiConverter.convertToStudySiteIi(id), ps);
    }

    @PUT
    @Path("/trials/{idType}/{trialID}/sites/po/{id}")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response submitStudySubjects(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID, @PathParam("id") long poid,
            @Validate StudySubjects ps) {
        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, poid);
            return submitStudySubjects(siteDTO.getIdentifier(), ps);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * @param idType
     * @param trialID
     * @param poid
     * @return
     * @throws PAException
     * @throws EntityNotFoundException
     */
    private StudySiteDTO findSite(String idType, String trialID, long poid)
            throws PAException, EntityNotFoundException {
        StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);
        StudySiteDTO siteDTO = PaServiceLocator
                .getInstance()
                .getParticipatingSiteServiceRemote()
                .getParticipatingSite(studyProtocolDTO.getIdentifier(),
                        poid + ""); // NOPMD
        if (siteDTO == null) {
            throw new EntityNotFoundException("Participating site with PO ID "
                    + poid + " on trial " + idType + "/" + trialID
                    + " is not found.");  // NOPMD
        }
        return siteDTO;
    }

    @PUT
    @Path("/trials/{idType}/{trialID}/sites/ctep/{id}")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response submitStudySubjects(@PathParam(ID_TYPE) String idType,
            @PathParam(TRIAL_ID) String trialID,
            @PathParam("id") String ctepID, @Validate StudySubjects ps) {
        try {
            StudySiteDTO siteDTO = findSite(idType, trialID, ctepID);
            return submitStudySubjects(siteDTO.getIdentifier(), ps);
        } catch (Exception e) {
            return handleException(e);
        }
    }
    
    @POST
    @Path("/batch")
    @NoCache
    public Response submitBatchFile(@Validate BatchFile batchFile ) {
        try {
             
             if (batchFile!=null && batchFile.getValue() !=null && batchFile.getValue().length > 0) {
                 submitBatchFile(batchFile.getValue());
             } else {
                 return Response.status(Status.BAD_REQUEST).type(TXT_PLAIN).build();
             }
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * @param idType
     * @param trialID
     * @param ctepID
     * @return
     * @throws PAException
     * @throws TooManyResultsException
     * @throws EntityNotFoundException
     */
    private StudySiteDTO findSite(String idType, String trialID, String ctepID)
            throws PAException, TooManyResultsException,
            EntityNotFoundException {
        StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);
        OrganizationDTO orgDTO = findOrgByCtepID(ctepID);
        StudySiteDTO siteDTO = PaServiceLocator
                .getInstance()
                .getParticipatingSiteServiceRemote()
                .getParticipatingSite(studyProtocolDTO.getIdentifier(),
                        IiConverter.convertToString(orgDTO.getIdentifier()));
        if (siteDTO == null) {
            throw new EntityNotFoundException(
                    "Participating site with CTEP ID " + ctepID + " on trial "
                            + idType + "/" + trialID + " is not found.");
        }
        return siteDTO;
    }

    private OrganizationDTO findOrgByCtepID(final String ctepID)
            throws TooManyResultsException, PAException {
        OrganizationSearchCriteriaDTO orgSearchCriteria = new OrganizationSearchCriteriaDTO();
        orgSearchCriteria.setCtepId(ctepID);
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> orgList = PoRegistry
                .getOrganizationEntityService()
                .search(orgSearchCriteria, limit);
        CollectionUtils.filter(orgList, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                OrganizationDTO org = (OrganizationDTO) o;
                String orgCtepID;
                try {
                    orgCtepID = AccrualUtil.findOrgCtepID(org);
                } catch (NullifiedRoleException e) {
                    throw new RuntimeException(e); // NOPMD
                }
                return StringUtils.equalsIgnoreCase(orgCtepID, ctepID);
            }
        });

        if (CollectionUtils.isEmpty(orgList)) {
            throw new EntityNotFoundException("Organization with CTEP ID of "
                    + ctepID + " cannot be found in PO.");
        } else if (orgList.size() > 1) {
            throw new EntityNotFoundException(
                    "It appears there are "
                            + orgList.size()
                            + " organizations with CTEP ID of "
                            + ctepID
                            + " in PO. Since we don't know which one to pick, we are having to fail this request.");
        } else {
            return orgList.get(0);
        }
    }

    private Response submitStudySubjects(Ii siteID, StudySubjects ss) {
        try {
            StudySiteDTO site = findSite(siteID);
            List<SubjectAccrualDTO> subjects = convertToSubjectAccrualDTOs(
                    site, ss);
            AccrualServiceLocator.getInstance().getSubjectAccrualService()
                    .manageSubjectAccruals(subjects);
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private Response deleteStudySubject(Ii siteID, String subjectID) {
        try {
            StudySiteDTO site = findSite(siteID);
            Ii studySubjectID = findExistingStudySubjectIdAndErrorIfNotFound(
                    site, subjectID);
            AccrualServiceLocator.getInstance().getSubjectAccrualService()
                    .deleteSubjectAccrual(studySubjectID, StringUtils.EMPTY);
            return Response.status(Status.OK).type(TXT_PLAIN).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private List<SubjectAccrualDTO> convertToSubjectAccrualDTOs(
            StudySiteDTO site, StudySubjects ss) throws PAException {
        List<SubjectAccrualDTO> list = new ArrayList<SubjectAccrualDTO>();
        for (StudySubject subj : ss.getStudySubject()) {
            list.add(convert(subj, site));
        }
        return list;
    }

    private SubjectAccrualDTO convert(StudySubject subj, StudySiteDTO site)
            throws PAException {
        SubjectAccrualDTO result = new SubjectAccrualDTO();
        result.setAssignedIdentifier(StConverter.convertToSt(subj
                .getIdentifier()));
        result.setGender(CdConverter
                .convertStringToCd(subj.getGender().value()));
        result.setBirthDate(TsConverter.convertToTs(subj.getBirthDate()
                .toGregorianCalendar().getTime()));
        result.setRace(convertRace(subj.getRace()));
        result.setEthnicity(CdConverter.convertStringToCd(subj.getEthnicity()
                .value()));
        result.setCountryCode(CdConverter.convertStringToCd(subj.getCountry()
                .value()));
        result.setZipCode(StConverter.convertToSt(subj.getZipCode()));
        result.setRegistrationDate(TsConverter.convertToTs(subj
                .getRegistrationDate().toGregorianCalendar().getTime()));
        result.setPaymentMethod(subj.getMethodOfPayment() != null ? CdConverter
                .convertToCd(PaymentMethodCode.valueOf(subj
                        .getMethodOfPayment().value())) : CdConverter
                .convertStringToCd(null));
        result.setDiseaseIdentifier(findDiseaseIdentifier(subj.getDisease()));
        result.setSiteDiseaseIdentifier(findDiseaseIdentifier(subj
                .getSiteDisease()));
        result.setParticipatingSiteIdentifier(site.getIdentifier());
        result.setIdentifier(findExistingStudySubjectId(site,
                subj.getIdentifier()));
        return result;
    }

    private Ii findExistingStudySubjectId(StudySiteDTO site, String assignedID)
            throws PAException {
        List<StudySubjectDto> list = AccrualServiceLocator
                .getInstance()
                .getStudySubjectService()
                .getStudySubjects(assignedID,
                        IiConverter.convertToLong(site.getIdentifier()), null);
        for (StudySubjectDto studySubjectDto : list) {
            if (StringUtils.equalsIgnoreCase(assignedID, StConverter
                    .convertToString(studySubjectDto.getAssignedIdentifier()))) {
                return studySubjectDto.getIdentifier();
            }
        }
        return null;
    }

    private Ii findExistingStudySubjectIdAndErrorIfNotFound(StudySiteDTO site,
            String subjectID) throws PAException {
        Ii ii = findExistingStudySubjectId(site, subjectID);
        if (ISOUtil.isIiNull(ii)) {
            throw new EntityNotFoundException("Study Subject with ID of "
                    + subjectID + " is not found.");
        }
        return ii;
    }

    @SuppressWarnings("deprecation")
    private Ii findDiseaseIdentifier(DiseaseCode code) {
        if (code == null) {
            return IiConverter.convertToIi((String) null);
        }
        AccrualDisease disease = AccrualServiceLocator.getInstance()
                .getAccrualDiseaseService()
                .getByCode(code.getCodeSystem(), code.getValue());
        return disease != null ? IiConverter.convertToIi(disease.getId())
                : IiConverter.convertToIi("not found");
    }

    private DSet<Cd> convertRace(List<Race> races) {
        DSet<Cd> dset = new DSet<Cd>();
        dset.setItem(new LinkedHashSet<Cd>());
        for (Race race : races) {
            dset.getItem().add(CdConverter.convertStringToCd(race.value()));
        }
        return dset;
    }

    /**
     * @param siteID
     * @return
     * @throws PAException
     */
    private StudySiteDTO findSite(Ii siteID) throws PAException {
        StudySiteDTO dto = PaServiceLocator.getInstance().getStudySiteService()
                .get(siteID);
        if (dto == null) {
            throw new EntityNotFoundException("Participating site with PA ID "
                    + IiConverter.convertToString(siteID) + " is not found.");
        }
        return dto;
    }
    
    /** Submit batch file for processing
     * @param batchData
     * @throws PAException 
     * @throws Exception
     */
    private void submitBatchFile(byte [] batchData) throws PAException  {
       Ed batchFile = EdConverter.convertToEd(batchData);
       AccrualServiceLocator.getInstance().getSubjectAccrualService().submitBatchData(batchFile);
    }
}
