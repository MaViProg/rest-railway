package com.ikonnikova.restrailway.service;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.WagonMovePosition;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.repository.WaybillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WagonService {

    @Autowired
    private WagonRepository wagonRepository;

    @Autowired
    private StationTrackRepository stationTrackRepository;

    @Autowired
    private WaybillRepository waybillRepository;

    /**
     * Операция приема вагонов на предприятие.
     * На входе список вагонов с учетом на какой путь станции данные вагоны принимаются.
     * Вагоны могут приниматься только в конец состава.
     *
     * @param
     * @param stationTrackId
     */
    public void receiveWagons(List<Long> wagonsId, Long stationTrackId) {
    StationTrack stationTrack = stationTrackRepository.findById(stationTrackId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid station track ID"));

    Wagon lastWagon = wagonRepository.findTopByStationTrackOrderByPositionDesc(stationTrack);
    int position = lastWagon != null ? lastWagon.getPosition() : 1;

    for (Wagon wagon : wagonRepository.findAllById(wagonsId)) {
        wagon.setStationTrack(stationTrack);
        wagon.setPosition(position == 0 ? WagonMovePosition.HEAD : WagonMovePosition.TAIL);
        wagonRepository.save(wagon);
        position = 0;
    }
}


    /**
     * Операция перестановки вагонов внутри станции.
     * На входе список вагонов и путь на который они будут перемещены.
     * Вагоны могут быть перемещены только в начало или конец состава.
     * <p>
     * http://localhost:8083/api/wagons/move
     *
     * @param wagons
     * @param stationTrackId
     */
    public void moveWagons(List<Long> wagons, Long stationTrackId, WagonMovePosition direction) {
        StationTrack destinationStationTrack = stationTrackRepository.findById(stationTrackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid destination station track ID"));

        Wagon firstWagon = null;
        Wagon lastWagon = null;

        for (Long wagonId : wagons) {
            Wagon wagon = wagonRepository.findById(wagonId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid wagon ID"));

            if (firstWagon == null || wagon.getPosition() == 0) {
                firstWagon = wagon;
            }

            if (lastWagon == null || wagon.getPosition() == 1) {
                lastWagon = wagon;
            }

            if (!wagon.getStationTrack().equals(destinationStationTrack)) {
                wagon.setStationTrack(destinationStationTrack);
                wagon.setPosition(WagonMovePosition.HEAD);
                wagonRepository.save(wagon);
            }
        }

        if (firstWagon == null || lastWagon == null) {
            throw new IllegalStateException("Wagons list is empty");
        }

        int position = (direction == WagonMovePosition.HEAD) ? 1 : 0;

        for (Long wagonId : wagons) {
            Wagon wagon = wagonRepository.findById(wagonId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid wagon ID"));

            if (wagon.getStationTrack().equals(destinationStationTrack)) {
                wagon.setPosition((position == 0) ? WagonMovePosition.HEAD : WagonMovePosition.TAIL);
                wagonRepository.save(wagon);

                position = (position == 0) ? 1 : 0;
            }
        }
    }




}
