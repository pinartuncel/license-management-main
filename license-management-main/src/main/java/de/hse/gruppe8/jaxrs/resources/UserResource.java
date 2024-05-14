package de.hse.gruppe8.jaxrs.resources;

import de.hse.gruppe8.jaxrs.model.ErrorResponse;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.jaxrs.services.UserService;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecuritySchemes(value = {
        @SecurityScheme(securitySchemeName = "apiKey",
                type = SecuritySchemeType.HTTP,
                scheme = "Bearer")}
)
@SecurityRequirement(name = "apiKey")
@Path("/users")
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response createUser(@Context SecurityContext securityContext, User user) {
        User currentUser = userService.getUserFromContext(securityContext);

        user.setId(null);
        user = userService.createUser(currentUser, user);
        if (user != null) {
            return Response.ok().entity(user).build();
        } else {
            return Response.status(401).entity(new ErrorResponse("Can`t create new user")).build();
        }
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User[].class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response getUsers(@Context SecurityContext securityContext) {
        User currentUser = userService.getUserFromContext(securityContext);

        return Response.ok().entity(userService.getUsers(currentUser)).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response getUserById(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        User user = userService.getUser(currentUser, id);
        if (user != null) {
            return Response.ok().entity(user).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Can't get contract with id %d", id))).build();
        }
    }

    @PUT
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response updateUser(@Context SecurityContext securityContext, @PathParam("id") Long id, User user) {
        User currentUser = userService.getUserFromContext(securityContext);

        user.setId(id);
        User newUser = userService.updateUser(currentUser, user);
        if (newUser != null) {
            return Response.ok().entity(newUser).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Can't update user with id %d", id))).build();
        }
    }

    @DELETE
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response deleteUser(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        boolean isSuccess = userService.deleteUser(currentUser, id);

        if (isSuccess) {
            return Response.ok().build();
        }
        return Response.status(403).entity(new ErrorResponse(String.format("Attempt to delete user with id: %d was not successful!", id))).build();
    }

}
