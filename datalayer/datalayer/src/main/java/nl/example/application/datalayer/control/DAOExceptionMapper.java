package nl.example.application.datalayer.control;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.Data;
import nl.example.application.datalayer.control.DAOException;

@Provider
public class DAOExceptionMapper implements ExceptionMapper<DAOException> {
	static Logger logger = Logger.getLogger(DAOExceptionMapper.class.getName());

	@Override
	public Response toResponse(DAOException ex) {
		Throwable cause = ex.getCause();
		while (cause instanceof EJBException) {
			cause = cause.getCause();
		}
		return logSevereAndReturnInternalServerError(cause);
	}

	Response logSevereAndReturnInternalServerError(Throwable t) {
		String msg = t.getMessage() == null ? "" : t.getMessage();
		logger.log(Level.SEVERE, "stacktrace", t);
		return Response.status(INTERNAL_SERVER_ERROR).entity(ErrorResponse.of(msg)).type(MediaType.APPLICATION_JSON).build();
	}

	@Data
	private static class ErrorResponse {
		String error;

		static ErrorResponse of(String error) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(error);
			return errorResponse;
		}
	}
}
