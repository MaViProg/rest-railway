package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Репозиторий для управления данными путей станций.
 */
@Repository
public interface StationTrackRepository extends JpaRepository<StationTrack, Long> {

    /**
     * Проверяет существование пути станции по имени.
     *
     * @param name имя пути станции
     * @return true, если путь станции с указанным именем существует, в противном случае - false
     */
    boolean existsByName(String name);

    /**
     * Находит все пути станций с загрузкой связанных вагонов.
     *
     * @return список путей станций с загруженными вагонами
     */
    @Transactional
    @Query("SELECT st FROM StationTrack st JOIN FETCH st.wagons")
    List<StationTrack> findAllWithWagons();
}


