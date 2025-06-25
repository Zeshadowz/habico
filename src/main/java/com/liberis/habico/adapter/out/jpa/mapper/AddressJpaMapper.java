package com.liberis.habico.adapter.out.jpa.mapper;

import com.liberis.habico.adapter.out.jpa.entity.AddressEntity;
import com.liberis.habico.domain.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressJpaMapper {
    Address toDomain(AddressEntity entity);

    AddressEntity toEntity(Address address);
}
