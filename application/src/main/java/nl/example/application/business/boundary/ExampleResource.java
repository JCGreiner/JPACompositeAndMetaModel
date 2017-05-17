package nl.example.application.business.boundary;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nl.example.application.business.control.ExampleHandler;
import nl.example.application.datalayer.entity.db.EntityA;

@Stateless
@Path("applicationforms/modify")
@Consumes("application/json")
public class ExampleResource {
    static Logger logger = Logger.getLogger(ExampleResource.class.getName());

    @Inject
    ExampleHandler handler;

    @POST
    @Path("score")
    public Response modifyRiskIndicatorScore(EntityA riskIndicatorScore,
            @Context HttpServletRequest request) {
        EntityA score = handler.storeRiskIndicatorScore(riskIndicatorScore);
        return Response.ok(score).build();
    }
}
