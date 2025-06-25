package com.liberis.habico;

import com.liberis.habico.adapter.in.rest.dto.AddressRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.UnitRequest;
import com.liberis.habico.domain.model.CStatus;
import com.liberis.habico.domain.model.SaleStatus;

import java.time.LocalDate;

public class TestDataProvider {

    public static BuildingRequest generateBuildingRequest() {
        return BuildingRequest.builder()
                .name("Habico API")
                .code("HBCO")
                .status(CStatus.PENDING)
                .active(true)
                .build();
    }

    public static UnitRequest generateUnitRequestWithParentAddress() {
        return UnitRequest.builder()
                .designation("HBCO-A")
                .status(SaleStatus.AVAILABLE)
                .saleSart(LocalDate.now())
                .parentAddress(true)
                .build();
    }

    public static UnitRequest generateUnitRequestWithOwnAddress() {
        return UnitRequest.builder()
                .designation("HBCO-A")
                .status(SaleStatus.AVAILABLE)
                .saleSart(LocalDate.now())
                .parentAddress(false)
                .address(generateAddressRequest())
                .build();
    }

    public static AddressRequest generateAddressRequest() {
        return AddressRequest.builder()
                .street("Habico-A street")
                .streetNumber("25")
                .zipcode("12345")
                .city("Habico City")
                .state("Habico State")
                .country("Habico Country")
                .build();
    }
}
