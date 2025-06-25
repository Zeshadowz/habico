package com.liberis.habico.adapter.in.rest.dto;

import com.liberis.habico.domain.model.CStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BuildingRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5, max = 30)
    private String name;

    @NotBlank(message = "Code is mandatory ")
    private String code;
    private boolean active;

    @NotNull(message = "Status is mandatory")
    private CStatus status;

    @Valid
    @NotNull(message = "Address is mandatory")
    private AddressRequest address;

    @Size(min = 1, message = "Building mus have at least one unit")
    @NotNull(message = "Units are mandatory")
    @Valid
    private List<UnitRequest> units;
}
