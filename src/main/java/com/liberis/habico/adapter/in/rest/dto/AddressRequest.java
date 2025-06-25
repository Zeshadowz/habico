package com.liberis.habico.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AddressRequest {
    @NotBlank
    private String street;
    private String streetNumber;
    private String additional;
    @NotBlank
    private String city;

    private String state;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String country;
}
