package com.liberis.habico.adapter.in.rest.dto;

import com.liberis.habico.domain.model.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitResponse {
    private Long id;
    private String designation;
    private SaleStatus status;
    private LocalDate saleSart;
    private LocalDate saleEnd;
    private boolean parentAddress;
    private AddressResponse address;
}
