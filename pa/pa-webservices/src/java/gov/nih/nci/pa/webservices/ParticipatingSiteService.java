/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.converters.OrganizationDTOBuilder;
import gov.nih.nci.pa.webservices.converters.ParticipatingSiteContactDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudySiteAccrualStatusDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudySiteDTOBuilder;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.Investigator;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.OrganizationOrPersonID;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.ParticipatingSiteUpdate;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.RecruitmentStatus;
import gov.nih.nci.pa.webservices.types.Sites;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.math.NumberUtils;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

/**
 * @author dkrylov
 * 
 */
@Path("/")
@Provider
public final class ParticipatingSiteService extends BaseRestService {

    private CorrelationUtils correlationUtils = new CorrelationUtils();

    /**
     * Adds a participating site to a trial.
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
    @Path("/trials/{idType}/{trialID}/sites")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response addSite(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID,
            @Validate ParticipatingSite reg) {
        try {
            StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);

            OrganizationDTO org = new OrganizationDTOBuilder().build(reg
                    .getOrganization());
            Ii poHealthFacilID = getPaServiceUtils().getPoHcfIi(
                    IiConverter.convertToString(org.getIdentifier()));

            StudySiteDTO studySiteDTO = new StudySiteDTOBuilder().build(reg);
            studySiteDTO.setStudyProtocolIdentifier(studyProtocolDTO
                    .getIdentifier());

            StudySiteAccrualStatusDTO accrualStatusDTO = new StudySiteAccrualStatusDTOBuilder()
                    .build(reg);

            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList = new ParticipatingSiteContactDTOBuilder(
                    org, getPaServiceUtils()).build(reg);
            
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();

            Ii studySiteID = PaRegistry
                    .getParticipatingSiteService()
                    .createStudySiteParticipant(studySiteDTO, accrualStatusDTO,
                            poHealthFacilID, participatingSiteContactDTOList)
                    .getIdentifier();
            
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            
            return response(studySiteID);
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
    public Response updateSite(@PathParam("id") long id,
            @Validate ParticipatingSiteUpdate ps) {
        return updateSite(IiConverter.convertToStudySiteIi(id), ps);
    }

    /**
     * @param idType
     *            idType
     * @param trialID
     *            trialID
     * @param poid
     *            poid
     * @param ps
     *            ParticipatingSiteUpdate
     * @return Response
     */
    @PUT
    @Path("/trials/{idType}/{trialID}/sites/po/{id}")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response updateSite(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID, @PathParam("id") long poid,
            @Validate ParticipatingSiteUpdate ps) {
        Response response;
        try {
            StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);
            
           
            StudySiteDTO siteDTO = PaRegistry.getParticipatingSiteService()
                    .getParticipatingSite(studyProtocolDTO.getIdentifier(),
                            poid + "");
            
            
            if (siteDTO == null) {
                throw new EntityNotFoundException(
                        "Participating site with PO ID " + poid + " on trial "
                                + idType + "/" + trialID + " is not found.");
            }
            
            response = updateSite(siteDTO.getIdentifier(), ps);
            
            return response;
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * @param idType
     *            idType
     * @param trialID
     *            trialID
     * @param ctepID
     *            ctepID
     * @param ps
     *            ParticipatingSiteUpdate
     * @return Response
     */
    @PUT
    @Path("/trials/{idType}/{trialID}/sites/ctep/{id}")
    @Consumes({ APPLICATION_XML })
    @Produces({ TXT_PLAIN })
    @NoCache
    public Response updateSite(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID,
            @PathParam("id") String ctepID, @Validate ParticipatingSiteUpdate ps) {
        try {
            StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);
            OrganizationDTO orgDTO = new OrganizationDTOBuilder().build(ctepID);
            StudySiteDTO siteDTO = PaRegistry
                    .getParticipatingSiteService()
                    .getParticipatingSite(studyProtocolDTO.getIdentifier(),
                            IiConverter.convertToString(orgDTO.getIdentifier()));
            if (siteDTO == null) {
                throw new EntityNotFoundException(
                        "Participating site with CTEP ID " + ctepID + " on trial "
                                + idType + "/" + trialID + " is not found.");
            }
            return updateSite(siteDTO.getIdentifier(), ps);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private Response updateSite(Ii siteID, BaseParticipatingSite ps) {
        try {
            StudySiteDTO existing = findSite(siteID);

            StudySiteDTO studySiteDTO = new StudySiteDTOBuilder().build(ps);
            studySiteDTO.setIdentifier(existing.getIdentifier());
            studySiteDTO.setStudyProtocolIdentifier(existing
                    .getStudyProtocolIdentifier());

            StudySiteAccrualStatusDTO accrualStatusDTO = new StudySiteAccrualStatusDTOBuilder()
                    .build(ps);

            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList = new ParticipatingSiteContactDTOBuilder(
                    getOrg(existing), getPaServiceUtils()).build(ps);
            
            PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
            Ii studySiteID = PaRegistry
                    .getParticipatingSiteService()
                    .updateStudySiteParticipant(studySiteDTO, accrualStatusDTO,
                            participatingSiteContactDTOList).getIdentifier();
            PaHibernateUtil.getHibernateHelper().openAndBindSession();
            
            return response(studySiteID);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    /**
     * Gets sites.
     * 
     * 
     * @param trialID
     *            trialID
     * @param idType
     *            idType
     * @return Response
     */
    @GET
    @Path("/trials/{idType}/{trialID}/sites")
    @Produces({ APPLICATION_XML })
    @NoCache
    @Formatted
    public Response getSites(@PathParam("idType") String idType,
            @PathParam("trialID") String trialID) {
        try {
            StudyProtocolDTO studyProtocolDTO = findTrial(idType, trialID);
            List<ParticipatingOrgDTO> list = PaRegistry
                    .getParticipatingOrgService().getTreatingSites(
                            IiConverter.convertToLong(studyProtocolDTO
                                    .getIdentifier()));
            Sites sites = convert(list);
            return Response.ok(sites).build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private Sites convert(List<ParticipatingOrgDTO> list)
            throws DatatypeConfigurationException {
        Sites sites = new ObjectFactory().createSites();
        for (ParticipatingOrgDTO dto : list) {
            sites.getSite().add(convert(dto));
        }
        return sites;
    }

    private ParticipatingSite convert(ParticipatingOrgDTO dto)
            throws DatatypeConfigurationException {
        ParticipatingSite ps = new ParticipatingSite();

        final Organization org = new Organization();
        ps.setOrganization(org);
        final OrganizationOrPersonID value = new OrganizationOrPersonID();
        org.setExistingOrganization(value);
        value.setPoID(NumberUtils.toLong(dto.getPoId()));

        if (dto.getRecruitmentStatus() != null) {
            ps.setRecruitmentStatus(RecruitmentStatus.fromValue(dto
                    .getRecruitmentStatus().getCode()));
        }
        ps.setRecruitmentStatusDate(toXml(dto.getRecruitmentStatusDate()));
        ps.setLocalTrialIdentifier(dto.getLocalProtocolIdentifier());
        ps.setProgramCode(dto.getProgramCodeText());
        ps.setOpenedForAccrual(toXml(dto.getDateOpenedForAccrual()));
        ps.setClosedForAccrual(toXml(dto.getDateClosedForAccrual()));
        ps.setTargetAccrualNumber(dto.getTargetAccrualNumber() != null ? BigInteger
                .valueOf(dto.getTargetAccrualNumber()) : null);

        ps.getInvestigator().addAll(convertInvestigators(dto));
        return ps;
    }

    private Collection<Investigator> convertInvestigators(
            ParticipatingOrgDTO dto) {
        List<Investigator> list = new ArrayList<>();
        for (PaPersonDTO paPerson : dto.getPrincipalInvestigators()) {
            final Investigator inv = convert(paPerson);
            inv.setRole("Principal Investigator");
            inv.setPrimaryContact(isPrimaryContact(paPerson, dto));
            list.add(inv);
        }
        for (PaPersonDTO paPerson : dto.getSubInvestigators()) {
            final Investigator inv = convert(paPerson);
            inv.setRole("Sub Investigator");
            inv.setPrimaryContact(isPrimaryContact(paPerson, dto));
            list.add(inv);
        }
        return list;
    }

    private boolean isPrimaryContact(PaPersonDTO paPerson,
            ParticipatingOrgDTO dto) {
        for (PaPersonDTO pc : dto.getPrimaryContacts()) {
            if (pc.getPaPersonId().equals(paPerson.getPaPersonId())) {
                return true;
            }
        }
        return false;
    }

    private Investigator convert(PaPersonDTO paPerson) {
        Investigator inv = new Investigator();
        final Person wsPerson = new Person();
        inv.setPerson(wsPerson);
        final OrganizationOrPersonID perID = new OrganizationOrPersonID();
        wsPerson.setExistingPerson(perID);
        perID.setPoID(paPerson.getPaPersonId());
        return inv;
    }

    private XMLGregorianCalendar toXml(Date date)
            throws DatatypeConfigurationException {
        if (date == null) {
            return null;
        }
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    private OrganizationDTO getOrg(StudySiteDTO siteDTO) throws PAException,
            NullifiedEntityException {
        return PoRegistry.getOrganizationEntityService().getOrganization(
                IiConverter.convertToPoOrganizationIi(correlationUtils
                        .getPAOrganizationByIi(
                                siteDTO.getHealthcareFacilityIi())
                        .getIdentifier()));
    }

    /**
     * @param siteID
     * @return
     * @throws PAException
     */
    private StudySiteDTO findSite(Ii siteID) throws PAException {
        StudySiteDTO dto = PaRegistry.getStudySiteService().get(siteID);
        if (dto == null) {
            throw new EntityNotFoundException("Participating site with PA ID "
                    + IiConverter.convertToString(siteID) + " is not found.");
        }
        return dto;
    }

    private Response response(Ii studySiteID) {
        return Response.status(Status.OK)
                .entity(IiConverter.convertToString(studySiteID))
                .type(TXT_PLAIN).build();
    }

    /**
     * @param correlationUtils
     *            the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtils correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

}
