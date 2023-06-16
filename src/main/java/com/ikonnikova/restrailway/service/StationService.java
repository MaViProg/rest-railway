package com.ikonnikova.restrailway.service;


import com.ikonnikova.restrailway.entity.StationModel;
import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.repository.StationModelRepository;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    private final StationModelRepository stationModelRepository;

    public StationService(StationModelRepository stationModelRepository) {
        this.stationModelRepository = stationModelRepository;
    }

    public StationModel createStationModelWithTracks() {
        StationModel stationModel = new StationModel();
        StationTrack stationTrack1 = new StationTrack("Example Station Track 1", stationModel);
        StationTrack stationTrack2 = new StationTrack("Example Station Track 2", stationModel);

        return stationModelRepository.save(stationModel);
    }
}
