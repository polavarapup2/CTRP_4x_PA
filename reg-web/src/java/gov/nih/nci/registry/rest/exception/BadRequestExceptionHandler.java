package gov.nih.nci.registry.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * @author Hugh Reinhart
 * @since Mar 4, 2013
 */
@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException ex) {
        StringBuilder response = new StringBuilder("<error>");
        response.append("<message>" + ex.getMessage() + "</message>");
        response.append("</error>");
        return Response.status(Status.BAD_REQUEST).entity(response.toString()).build();
    }
}
