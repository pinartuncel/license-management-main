package de.hse.gruppe8.jaxrs.resources;

import de.hse.gruppe8.jaxrs.model.Contract;
import de.hse.gruppe8.jaxrs.model.ErrorResponse;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.jaxrs.services.ContractService;
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
@Path("/contracts")
public class ContractResource {

    @Inject
    ContractService contractService;

    @Inject
    UserService userService;

    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contract.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response createContract(@Context SecurityContext securityContext, Contract contract) {
        User currentUser = userService.getUserFromContext(securityContext);

        contract.setId(null);
        contract = contractService.createContract(currentUser, contract);
        if (contract != null) {
            return Response.ok().entity(contract).build();
        } else {
            return Response.status(401).entity(new ErrorResponse("Can`t create new contract")).build();
        }
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contract[].class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response getContracts(@Context SecurityContext securityContext) {
        User currentUser = userService.getUserFromContext(securityContext);

        return Response.ok().entity(contractService.getContracts(currentUser)).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contract.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response getContractById(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        Contract contract = contractService.getContract(currentUser, id);
        if (contract != null) {
            return Response.ok().entity(contract).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Can't get contract with id %d", id))).build();
        }
    }

    @PUT
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contract.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response updateContract(@Context SecurityContext securityContext, @PathParam("id") Long id, Contract contract) {
        User currentUser = userService.getUserFromContext(securityContext);

        contract.setId(id);
        Contract newContract = contractService.updateContract(currentUser, contract);
        if (newContract != null) {
            return Response.ok().entity(newContract).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Can't update contract with id %d", id))).build();
        }
    }

    @DELETE
    @APIResponses(value = {
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response deleteContract(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        boolean isSuccess = contractService.deleteContract(currentUser, id);

        if (isSuccess) {
            return Response.ok().build();
        }
        return Response.status(403).entity(new ErrorResponse(String.format("Attempt to delete contract with id: %d was not successful!", id))).build();
    }

}
