package nl.example.application.business.boundary;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.example.application.business.control.ExampleHandler;
import nl.example.application.datalayer.entity.db.EntityA;

@Stateless
@Path("application")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {
	static Logger logger = Logger.getLogger(ExampleResource.class.getName());

	@Inject
	ExampleHandler handler;

	@POST
	@Path("store")
	public Response modifyEntityA(EntityA entityA, @Context HttpServletRequest request) {
		EntityA score = handler.storeEntityA(entityA);
		return Response.ok(score).build();
	}

	@GET
	@Path("findNative/{dummyValue}")
	public Response findEntityAByDummyValueNative(@PathParam("dummyValue") String dummyValue,
			@Context HttpServletRequest request) {
		List<EntityA> list = handler.findEntityANative(dummyValue);
		GenericEntity<List<EntityA>> generic = new GenericEntity<List<EntityA>>(list) {
		};
		return Response.ok(generic).build();
	}

	@GET
	@Path("findCriteria/{dummyValue}")
	public Response findEntityAByDummyValueCriteria(@PathParam("dummyValue") String dummyValue,
			@Context HttpServletRequest request) {
		List<EntityA> list = handler.findEntityACriteria(dummyValue);
		GenericEntity<List<EntityA>> generic = new GenericEntity<List<EntityA>>(list) {
		};
		return Response.ok(generic).build();
	}
}
