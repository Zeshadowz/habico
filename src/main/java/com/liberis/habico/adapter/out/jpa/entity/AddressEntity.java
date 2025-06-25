package com.liberis.habico.adapter.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String streetNumber;
    private String additional;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    // @OneToOne(cascade = CascadeType.ALL)
    //private BuildingEntity building;
}
