/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.ObjectFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author dkrylov
 * 
 */
public class BaseRestService implements ContextResolver<JAXBContext> {

    /**
     * APPLICATION_XML
     */
    public static final String APPLICATION_XML = "application/xml";
    /**
     * TXT_PLAIN
     */
    public static final String TXT_PLAIN = "text/plain";

    /**
     * PAServiceUtils
     */
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    private static final Logger LOG = Logger.getLogger(BaseRestService.class);

    @Override
    public JAXBContext getContext(Class<?> arg0) {
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            LOG.error(e, e);
            throw new RuntimeException(e); // NOPMD
        }
    }

    /**
     * @param idType
     *            idType
     * @param trialID
     *            trialID
     * @return StudyProtocolDTO
     * @throws PAException
     *             PAException
     */
    protected StudyProtocolDTO findTrial(String idType, String trialID)
            throws PAException {
        if (StringUtils.isBlank(trialID) || StringUtils.isBlank(idType)) {
            throw new TrialDataException(
                    "Please provide trial identifier type and value as described in the documentation.");
        }
        Ii ii = new Ii();
        if ("pa".equalsIgnoreCase(idType)) {
            ii = IiConverter.convertToStudyProtocolIi(Long.valueOf(trialID));
        } else if ("nci".equalsIgnoreCase(idType)) {
            ii.setExtension(trialID);
            ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        } else if ("ctep".equalsIgnoreCase(idType)) {
            ii.setExtension(trialID);
            ii.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        }  else if ("dcp".equalsIgnoreCase(idType)) {
            ii.setExtension(trialID);
            ii.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
        }
        return PaRegistry.getStudyProtocolService().getStudyProtocol(ii);
    }

    /**
     * @param e
     *            Exception
     * @return Response
     */
    protected Response handleException(Exception e) {
        if (e instanceof EntityNotFoundException) {
            return logErrorAndPrepareResponse(Status.NOT_FOUND, e);
        } else if (e instanceof TrialDataException
                || e instanceof PoEntityCannotBeCreatedException
                || e instanceof PAValidationException) {
            return logErrorAndPrepareResponse(Status.BAD_REQUEST, e);
        } else if (e instanceof PAException) {
            return StringUtils.startsWithIgnoreCase(e.getMessage(),
                    "Validation Exception") ? logErrorAndPrepareResponse(
                    Status.BAD_REQUEST, e) : logErrorAndPrepareResponse(
                    Status.INTERNAL_SERVER_ERROR, e);
        } else {
            return logErrorAndPrepareResponse(Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * @param status
     *            Status
     * @param e
     *            Exception
     * @return Response
     */
    protected Response logErrorAndPrepareResponse(Status status, Exception e) {
        LOG.error(e, e);
        return Response.status(status).entity(e.getMessage()).type(TXT_PLAIN)
                .build();
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }

}
