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

public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String department;
    private String street;
    private String zipCode;
    private String city;
    private String country;
}
