package com.liberis.habico.adapter.in.rest.dto;

import com.liberis.habico.domain.model.CStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {
    private Long id;
    private String name;
    private String code;
    private boolean active;
    private CStatus status;
    private AddressResponse address;
    private List<UnitResponse> units;
}
