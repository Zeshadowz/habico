package com.liberis.habico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private Long id;
    private String street;
    private String streetNumber;
    private String additional;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
