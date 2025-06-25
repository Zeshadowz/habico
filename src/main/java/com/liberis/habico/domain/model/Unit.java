package com.liberis.habico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    private Long id;
    private String designation;
    private SaleStatus status;
    private LocalDate saleSart;
    private LocalDate saleEnd;
    private Building building; // Reference to the parent Building
    private boolean parentAddress;
    private Address address; // can be different from Building's address
}
