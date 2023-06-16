package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationModelRepository extends JpaRepository<StationModel, Long> {
    StationModel findByName(String name);
}
