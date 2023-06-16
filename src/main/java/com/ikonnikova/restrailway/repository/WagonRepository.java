package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {

    Wagon findTopByStationTrackOrderByPositionDesc(StationTrack stationTrack);

    Wagon findFirstWagonByWaybill(Waybill waybill);

    void deleteAllByWaybill(Waybill waybill);

    List<Wagon> findAllWagonsByWaybill(Waybill waybill);

}

