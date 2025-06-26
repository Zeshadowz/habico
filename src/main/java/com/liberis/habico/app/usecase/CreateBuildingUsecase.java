package com.liberis.habico.app.usecase;

import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingResponse;
import com.liberis.habico.app.mapper.BuildingMapper;
import com.liberis.habico.common.exception.DuplicateResourceException;
import com.liberis.habico.domain.model.Building;
import com.liberis.habico.domain.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBuildingUsecase {

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;

    public BuildingResponse createBuilding(BuildingRequest buildingRequest) {
        if (null != buildingService.findBuildingByName(buildingRequest.getName())) {
            throw new DuplicateResourceException(buildingRequest.getName() + " already exists");
        }

        if (null != buildingService.findBuildingByCode(buildingRequest.getCode())) {
            throw new DuplicateResourceException(buildingRequest.getCode() + " already exists");
        }

        // TODO: Add check same address. Two buildings cannot have the same address

        // TODO: Validate unit address. Same as Building or not

        Building building = buildingService.createBuilding(buildingMapper.toModel(buildingRequest));
        return buildingMapper.toDTO(building);
    }

}
