package gov.nih.nci.registry.rest;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.rest.domain.StudyProtocolXmlLean;
import gov.nih.nci.registry.rest.exception.BadRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.cache.Cache;
import org.jboss.resteasy.annotations.cache.ServerCached;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

/**
 * @author Hugh Reinhart
 * @since Feb 11, 2013
 */
@Path("/studyProtocols")
@Produces(MediaType.APPLICATION_XML)
public class StudyProtocolsRsrc {
    private static final Logger LOG = Logger.getLogger(StudyProtocolsRsrc.class);
    private static final int ONE_HOUR = 3600;
   private Map<String, String> identifiersMap = new HashMap<String, String>();
    
    /**
     * 
     */
    public StudyProtocolsRsrc() {
        identifiersMap.put("nci", IdentifierType.NCI.getCode());
        identifiersMap.put("ctep", IdentifierType.CTEP.getCode());
        identifiersMap.put("dcp", IdentifierType.DCP.getCode());
        identifiersMap.put("nct", IdentifierType.NCT.getCode());
    }

    /**
     * Query study protocols returning minimal data for each trial.
     * @param agentNsc agent used in study
     * @return the study protocol list
     */
    @Path("/")
    @Wrapped(element = "studyProtocols")
    @GET
    @Cache(maxAge = ONE_HOUR)
    @ServerCached
    public Response getStudiesXML(@QueryParam("agentNsc") String agentNsc) {
        if (StringUtils.isEmpty(agentNsc)) {
            throw new BadRequestException("Agent NSC number missing from request.");
        }
        if (!StringUtils.isNumeric(agentNsc)) {
            throw new BadRequestException("Agent NSC number must contain digits only."); 
        }
        try {
            List<StudyProtocolQueryDTO> results = 
                    PaRegistry.getProtocolQueryService().getStudyProtocolByAgentNsc(agentNsc);
            List<StudyProtocolXmlLean> xmlList = StudyProtocolXmlLean.getList(results);
            GenericEntity<List<StudyProtocolXmlLean>> ge = new GenericEntity<List<StudyProtocolXmlLean>>(xmlList) { };
            return Response.ok(ge).build();
        } catch (Exception e) {
            LOG.error(e);
            return Response.serverError().build();
        }
    }
    
    /**
     * @param idType idType
     * @param idValue idValue
     * @return Response
     */
    @Path("/id/{idType}/{idValue}")
    @GET
    @Cache(maxAge = ONE_HOUR)
    @ServerCached
    public Response getStudiesXMLById(@PathParam("idType") String idType,
            @PathParam("idValue") String idValue) {
        if (StringUtils.isEmpty(idType)) {
            throw new BadRequestException("idType missing from request.");
        }
        if (StringUtils.isEmpty(idValue)) {
            throw new BadRequestException("idValue missing from request.");
        }
        List<StudyProtocolXmlLean> xmlList = new ArrayList<StudyProtocolXmlLean>();
        StudyProtocolXmlLean studyProtocolXmlLean = new StudyProtocolXmlLean();
        StudyProtocolQueryCriteria studyProtocolQueryCriteria = new StudyProtocolQueryCriteria();
      
            
             if (idType.equals("pa")) {
                 Long studyProtocolId = Long.valueOf(idValue);
                studyProtocolQueryCriteria.setStudyProtocolId(studyProtocolId);
              } else {
                 String identifierType = identifiersMap.get(idType);
                 if (identifierType == null) {
                     throw new BadRequestException("Invalid identifier type specified ");
                 } else {
                     studyProtocolQueryCriteria.setIdentifierType(identifierType);
                     studyProtocolQueryCriteria.setIdentifier(idValue);
                 }
             }
        try {  
            List<StudyProtocolQueryDTO> studyProtocolQueryDTOList = 
                    PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(studyProtocolQueryCriteria);
            for (StudyProtocolQueryDTO studyProtocolQueryDTO :studyProtocolQueryDTOList) {
                studyProtocolXmlLean = new StudyProtocolXmlLean(studyProtocolQueryDTO);
                xmlList.add(studyProtocolXmlLean);
            }
           
            GenericEntity<List<StudyProtocolXmlLean>> ge = new GenericEntity<List<StudyProtocolXmlLean>>(xmlList) { };
            return Response.ok(ge).build();
        } catch (Exception e) {
            LOG.error(e);
            return Response.serverError().build();
        }
    }
}
