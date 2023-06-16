package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления данными вагонов.
 */
@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {

    /**
     * Находит вагон с наибольшей позицией на указанном треке станции.
     *
     * @param stationTrack трек станции
     * @return вагон с наибольшей позицией на указанном треке станции
     */
    Wagon findTopByStationTrackOrderByPositionDesc(StationTrack stationTrack);

    /**
     * Находит первый вагон, связанный с указанным путевым листом.
     *
     * @param waybill путевой лист
     * @return первый вагон, связанный с указанным путевым листом
     */
    Wagon findFirstWagonByWaybill(Waybill waybill);

    /**
     * Удаляет все вагоны, связанные с указанным путевым листом.
     *
     * @param waybill путевой лист
     */
    void deleteAllByWaybill(Waybill waybill);

    /**
     * Находит все вагоны, связанные с указанным путевым листом.
     *
     * @param waybill путевой лист
     * @return список вагонов, связанных с указанным путевым листом
     */
    List<Wagon> findAllWagonsByWaybill(Waybill waybill);

}


