package de.hse.gruppe8.jaxrs.resources;


import de.hse.gruppe8.exception.NoUserFoundException;
import de.hse.gruppe8.jaxrs.model.Authentication;
import de.hse.gruppe8.jaxrs.model.ErrorResponse;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.jaxrs.services.LoginService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class LoginResource {

    @Inject
    LoginService loginService;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response login(Authentication authentication) {
        try {
            User currentUser = loginService.login(authentication);
            return Response.status(200).entity(currentUser).build();
        } catch (NoUserFoundException e) {
            return Response.status(403).entity(new ErrorResponse(e.getMessage())).build();
        }
    }
}