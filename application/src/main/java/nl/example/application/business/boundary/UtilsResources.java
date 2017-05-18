package nl.example.application.business.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.example.application.business.entity.dto.PingResponse;

@Path("utils")
@io.swagger.annotations.Api(value = "Utils")
public class UtilsResources {

    @GET
    @Path("ping")
    @io.swagger.annotations.ApiOperation(value = "Ping", notes = "Ping the example")
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Ok", response = PingResponse.class)
    })
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPing() {
        return Response.ok(new PingResponse()).build();
    }
}
