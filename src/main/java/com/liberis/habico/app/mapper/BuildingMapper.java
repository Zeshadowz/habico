package com.liberis.habico.app.mapper;

import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingResponse;
import com.liberis.habico.domain.model.Building;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UnitMapper.class, AddressMapper.class})
public interface BuildingMapper {

    Building toModel(BuildingRequest buildingRequest);


    BuildingResponse toDTO(Building building);

}
