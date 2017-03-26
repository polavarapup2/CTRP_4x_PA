package gov.nih.nci.pa.webservices;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.dto.StudyProtocolIdentityDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
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
public class ImportHelperRestService extends BaseRestService {
    private static final int NOT_FOUND_CODE = 404;
    /**
     * @param nctID nctID
     * @return Response
     * @throws PAException  PAException
     */
    @GET
    @Path("/studyprotocolIdentity/{nctID}")
    @Consumes({ APPLICATION_JSON })
    @Produces({ APPLICATION_JSON })
    @NoCache
    @Formatted
    public Response getStudyProtocolIdentity(@PathParam("nctID") String nctID) throws PAException {
        List<StudyProtocolIdentityDTO> list = new ArrayList<StudyProtocolIdentityDTO>();
        List<StudyProtocolQueryDTO> studyProtocolDTOList = findExistentStudies(nctID);
        if (!studyProtocolDTOList.isEmpty()) {
            for (StudyProtocolQueryDTO dto : studyProtocolDTOList) {
                StudyProtocolIdentityDTO studyProtocolIdentityDTO = new StudyProtocolIdentityDTO();
                studyProtocolIdentityDTO.setNctId(dto.getNctIdentifier());
                studyProtocolIdentityDTO.setNciId(dto.getNciIdentifier());
                studyProtocolIdentityDTO.setStudyProtocolId(dto.getStudyProtocolId().toString());
                studyProtocolIdentityDTO.setProprietaryTrial(dto.isProprietaryTrial());
                studyProtocolIdentityDTO.setTrialType(dto.getStudyProtocolType());
                studyProtocolIdentityDTO.setUpdateIndicator(true);
                studyProtocolIdentityDTO.setUserLastCreated(dto.getLastCreated().getUserLastCreated());
                studyProtocolIdentityDTO.setSecondaryIdentifiers(dto.getOtherIdentifiers());
                list.add(studyProtocolIdentityDTO);
            }
        }
        if (list.isEmpty()) {
            return Response.status(NOT_FOUND_CODE).entity("NOT Found").build();
        }
        return Response.ok(list).build();
    }
    
    // remember to reject trials
    private List<StudyProtocolQueryDTO> findExistentStudies(String nctID) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNctNumber(nctID);
        criteria.setTrialCategory("p");
        criteria.setExcludeRejectProtocol(true);
        for (StudyProtocolQueryDTO dto : PaRegistry.getProtocolQueryService()
                    .getStudyProtocolByCriteria(criteria)) {
                list.add(dto);
        }
        return list;
    }
        

}
 
