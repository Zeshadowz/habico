package com.liberis.habico.adapter.in.rest.dto;

import com.liberis.habico.domain.model.Building;
import com.liberis.habico.domain.model.SaleStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UnitRequest {
    private String designation;
    private SaleStatus status;
    private LocalDate saleSart;
    private LocalDate saleEnd;
    private Building building; // Reference to the parent Building
    private boolean parentAddress;
    private AddressRequest address;
}
