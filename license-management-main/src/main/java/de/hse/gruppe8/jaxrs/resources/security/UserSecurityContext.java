package de.hse.gruppe8.jaxrs.resources.security;

import lombok.Getter;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Getter
public class UserSecurityContext implements SecurityContext {

    private Long userid;

    public UserSecurityContext(Long userid) {
        this.userid = userid;
    }

    @Override
    public Principal getUserPrincipal() {
        return userid::toString;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
