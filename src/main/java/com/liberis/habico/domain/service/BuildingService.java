package com.liberis.habico.domain.service;

import com.liberis.habico.adapter.out.jpa.entity.BuildingEntity;
import com.liberis.habico.adapter.out.jpa.mapper.BuildingJpaMapper;
import com.liberis.habico.adapter.out.jpa.repository.BuildingRepository;
import com.liberis.habico.domain.model.Building;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingJpaMapper buildingJpaMapper;

    public Building createBuilding(Building building) {
        BuildingEntity entity = buildingJpaMapper.toEntity(building);
        entity = buildingRepository.save(entity);
        return buildingJpaMapper.toDomain(entity);
    }

    public Building updateBuilding(Building building) {
        BuildingEntity buildingEntity = buildingJpaMapper.toEntity(building);
        buildingEntity = buildingRepository.save(buildingEntity);
        return buildingJpaMapper.toDomain(buildingEntity);
    }

    public Building findBuildingByName(String name) {
        BuildingEntity entity = buildingRepository.findBuildingByName(name);
        return buildingJpaMapper.toDomain(entity);
    }

    public Building findBuildingByCode(String code) {
        BuildingEntity entity = buildingRepository.findBuildingByCode(code);
        return buildingJpaMapper.toDomain(entity);
    }


}
