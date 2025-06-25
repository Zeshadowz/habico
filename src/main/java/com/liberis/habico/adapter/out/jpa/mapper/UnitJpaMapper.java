package com.liberis.habico.adapter.out.jpa.mapper;

import com.liberis.habico.adapter.out.jpa.entity.UnitEntity;
import com.liberis.habico.domain.model.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressJpaMapper.class})
public interface UnitJpaMapper {

    @Mapping(target = "building", ignore = true)
    Unit toDomain(UnitEntity entity);

    List<Unit> toDomain(List<UnitEntity> entities);

    UnitEntity toEntity(Unit unit);

    List<UnitEntity> toEntity(List<Unit> units);
}
