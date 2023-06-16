package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления данными моделей станций.
 */
@Repository
public interface StationModelRepository extends JpaRepository<StationModel, Long> {

    /**
     * Найти модель станции по имени.
     *
     * @param name имя модели станции
     * @return найденная модель станции
     */
    StationModel findByName(String name);
}
