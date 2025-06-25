package com.liberis.habico.adapter.out.jpa.repository;

import com.liberis.habico.adapter.out.jpa.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

    BuildingEntity findBuildingByName(String name);

    BuildingEntity findBuildingByCode(String code);
}
