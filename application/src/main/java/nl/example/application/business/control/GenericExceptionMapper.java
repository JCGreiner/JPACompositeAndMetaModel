package nl.example.application.business.control;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    static Logger logger = Logger.getLogger(GenericExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof EJBException) {
            Throwable cause = ex.getCause();
            while (cause instanceof EJBException) {
                cause = cause.getCause();
            }
            logSevereAndReturnInternalServerError(cause);
        }
        return logSevereAndReturnInternalServerError(ex);
    }

    Response logSevereAndReturnInternalServerError(Throwable t) {
        String msg = t.getMessage() == null ? "" : t.getMessage();
        logger.log(Level.SEVERE, "stacktrace", t);
        return Response.status(INTERNAL_SERVER_ERROR).entity(msg).type(MediaType.APPLICATION_JSON).build();
    }
}
