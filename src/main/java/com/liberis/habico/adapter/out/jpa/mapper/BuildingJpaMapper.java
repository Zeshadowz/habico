package com.liberis.habico.adapter.out.jpa.mapper;

import com.liberis.habico.adapter.out.jpa.entity.BuildingEntity;
import com.liberis.habico.domain.model.Building;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UnitJpaMapper.class, AddressJpaMapper.class})
public interface BuildingJpaMapper {
    Building toDomain(BuildingEntity entity);

    BuildingEntity toEntity(Building building);

    @AfterMapping
    default void afterMapping(@MappingTarget BuildingEntity entity) {
        entity.getUnits().forEach(unit -> {
            unit.setBuilding(entity);
            if (unit.getAddress() == null) {
                unit.setAddress(entity.getAddress());
            }
        });
    }
}
