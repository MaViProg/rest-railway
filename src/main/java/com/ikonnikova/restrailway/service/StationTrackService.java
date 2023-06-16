package com.ikonnikova.restrailway.service;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationTrackService {

    @Autowired
    StationTrackRepository stationTrackRepository;

    public StationTrack createStationTrack (StationTrack stationTrack) {
        return stationTrackRepository.save(stationTrack);
    }
}


