package com.liberis.habico.adapter.out.jpa.repository;

import com.liberis.habico.adapter.out.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
