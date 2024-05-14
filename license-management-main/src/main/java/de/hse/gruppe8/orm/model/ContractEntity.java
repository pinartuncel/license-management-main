package de.hse.gruppe8.orm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Contracts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_stop")
    private Date dateStop;

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "license_key", length = 50)
    private String licenseKey;

    @Column(name = "feature_1")
    private int feature1;

    @Column(name = "feature_2")
    private int feature2;

    @Column(name = "feature_3")
    private int feature3;

    @Column(name = "ip_1", length = 16)
    private String ipaddress1;

    @Column(name = "ip_2", length = 16)
    private String ipaddress2;

    @Column(name = "ip_3", length = 16)
    private String ipaddress3;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne
    private UserEntity user1;

    @ManyToOne
    private UserEntity user2;
}