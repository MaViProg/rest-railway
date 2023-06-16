package com.ikonnikova.restrailway.service;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с путями станции.
 */
@Service
public class StationTrackService {

    @Autowired
    private StationTrackRepository stationTrackRepository;

    /**
     * Метод createStationTrack:
     * Создает новый путь станции.
     *
     * @param stationTrack создаваемый путь станции
     * @return созданный путь станции
     */
    public StationTrack createStationTrack(StationTrack stationTrack) {
        return stationTrackRepository.save(stationTrack);
    }

}



