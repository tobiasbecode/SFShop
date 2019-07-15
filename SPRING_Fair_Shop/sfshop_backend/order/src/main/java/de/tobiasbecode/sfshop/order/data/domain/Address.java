package de.tobiasbecode.sfshop.order.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="address")
public class Address {

    public Address(String addressName, String addressStreet, String addressPLZ,
                   String addressCity, String addressCountry, String addressMail, String username) {
        this.addressName = addressName;
        this.addressStreet = addressStreet;
        this.addressPLZ = addressPLZ;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.addressMail = addressMail;
        this.username = username;

    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name="addressName")
    private String addressName;

    @Column(name="addressStreet")
    private String addressStreet;

    @Column(name="addressPLZ")
    private String addressPLZ;

    @Column(name="addressCity")
    private String addressCity;

    @Column(name="addressCountry")
    private String addressCountry;

    @Column(name="addressMail")
    private String addressMail;

    @Column(name="username")
    private String username;
}

