package com.liberis.habico.app.mapper;

import com.liberis.habico.adapter.in.rest.dto.AddressRequest;
import com.liberis.habico.domain.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toDomain(AddressRequest dto);


    AddressRequest toDto(Address address);
}
