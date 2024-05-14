package de.hse.gruppe8.jaxrs.resources;

import de.hse.gruppe8.jaxrs.model.Company;
import de.hse.gruppe8.jaxrs.model.Contract;
import de.hse.gruppe8.jaxrs.model.ErrorResponse;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.jaxrs.services.CompanyService;
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

;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecuritySchemes(value = {
        @SecurityScheme(securitySchemeName = "apiKey",
                type = SecuritySchemeType.HTTP,
                scheme = "Bearer")}
)
@SecurityRequirement(name = "apiKey")
@Path("/companies")
public class CompanyResource {

    @Inject
    CompanyService companyService;


    @Inject
    UserService userService;


    @POST
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Company.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response createCompany(@Context SecurityContext securityContext, Company company) {
        User currentUser = userService.getUserFromContext(securityContext);

        company.setId(null);
        company = companyService.createCompany(currentUser, company);
        if (company != null) {
            return Response.ok().entity(company).build();
        } else {
            return Response.status(401).entity(new ErrorResponse("Can`t create new company")).build();
        }
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Company[].class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response getCompanies(@Context SecurityContext securityContext) {
        User currentUser = userService.getUserFromContext(securityContext);
        return Response.ok().entity(companyService.getCompanies(currentUser)).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Company.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response getCompanyById(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        Company company = companyService.getCompany(currentUser, id);
        if (company != null) {
            return Response.ok().entity(company).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Company with id %d not found", id))).build();
        }
    }

    @PUT
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Company.class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response updateCompany(@Context SecurityContext securityContext, @PathParam("id") Long id, Company company) {
        User currentUser = userService.getUserFromContext(securityContext);

        company.setId(id);
        Company newCompany = companyService.updateCompany(currentUser, company);
        if (newCompany != null) {
            return Response.ok().entity(newCompany).build();
        } else {
            return Response.status(401).entity(new ErrorResponse(String.format("Can't update company with id %d", id))).build();
        }
    }

    @DELETE
    @APIResponses(value = {
            @APIResponse(responseCode = "200"),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}")
    public Response deleteCompany(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        boolean isSuccess = companyService.deleteCompany(currentUser, id);

        if (isSuccess) {
            return Response.ok().build();
        }
        return Response.status(403).entity(new ErrorResponse(String.format("Attempt to delete company with id: %d was not successful!", id))).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User[].class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}/users")
    public Response getUsersFromCompany(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        return Response.ok().entity(companyService.getUsersFromCompany(currentUser, id)).build();
    }

    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Contract[].class))),
            @APIResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @Path("/{id}/contracts")
    public Response getContractsFromCompany(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        User currentUser = userService.getUserFromContext(securityContext);

        return Response.ok().entity(companyService.getContractsFromCompany(currentUser, id)).build();
    }

}
