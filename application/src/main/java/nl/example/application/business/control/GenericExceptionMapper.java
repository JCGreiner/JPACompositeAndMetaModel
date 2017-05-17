package nl.example.application.business.control;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AccessLocalException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
    static Logger logger = Logger.getLogger(GenericExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof AccessLocalException) {
            return Response.status(UNAUTHORIZED).entity("").type(MediaType.TEXT_PLAIN).build();
        }
        return logSevereAndReturnInternalServerError(ex);
    }

    Response logSevereAndReturnInternalServerError(Exception ex) {
        String msg = ex.getMessage() == null ? "" : ex.getMessage();
        logger.log(Level.SEVERE, "stacktrace", ex);
        return Response.status(INTERNAL_SERVER_ERROR).entity(msg).type(MediaType.TEXT_PLAIN).build();
    }
}
