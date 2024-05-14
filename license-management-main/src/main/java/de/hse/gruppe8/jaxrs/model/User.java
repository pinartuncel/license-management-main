package de.hse.gruppe8.jaxrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String username;
    private Boolean isAdmin = false;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    private String jwt;
    private Company company;
}