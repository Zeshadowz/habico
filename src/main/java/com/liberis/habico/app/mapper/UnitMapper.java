package com.liberis.habico.app.mapper;

import com.liberis.habico.adapter.in.rest.dto.UnitRequest;
import com.liberis.habico.adapter.in.rest.dto.UnitResponse;
import com.liberis.habico.domain.model.Unit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AddressMapper.class
})
public interface UnitMapper {
    Unit toDomain(UnitRequest unitRequest);

    List<Unit> toDomain(List<UnitRequest> units);

    UnitResponse toDTO(Unit unit);

    List<UnitResponse> toDTO(List<Unit> units);
}
