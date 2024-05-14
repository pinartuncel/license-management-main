package de.hse.gruppe8.jaxrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Date dateStart;
    private Date dateStop;
    private String version;
    private String licenseKey;
    private Company company;
    private String ipaddress1;
    private String ipaddress2;
    private String ipaddress3;
    private Integer feature1;
    private Integer feature2;
    private Integer feature3;
    private User user1;
    private User user2;

}
