import com.ikonnikova.restrailway.conroller.StationTrackController;
import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StationTrackControllerTests {

    private static final Long STATION_TRACK_ID = 1L;
    private static final String STATION_TRACK_NAME = "Test Station Track";

    @InjectMocks
    private StationTrackController stationTrackController;

    @Mock
    private StationTrackRepository stationTrackRepository;

    @Test
    public void shouldReturnAllStationTracks() {
        List<StationTrack> expectedStationTracks = Arrays.asList(new StationTrack(), new StationTrack());
        when(stationTrackRepository.findAll()).thenReturn(expectedStationTracks);

        List<StationTrack> actualStationTracks = stationTrackController.getAllStationTracks();

        assertEquals(expectedStationTracks, actualStationTracks);
    }

    @Test
    public void shouldReturnStationTrackById() {
        StationTrack expectedStationTrack = new StationTrack();
        expectedStationTrack.setId(STATION_TRACK_ID);
        when(stationTrackRepository.findById(STATION_TRACK_ID)).thenReturn(Optional.of(expectedStationTrack));

        StationTrack actualStationTrack = stationTrackController.getStationTrackById(STATION_TRACK_ID);

        assertEquals(expectedStationTrack, actualStationTrack);
    }

    @Test
    public void shouldCreateStationTrack() {
        StationTrack stationTrack = new StationTrack();
        stationTrack.setName(STATION_TRACK_NAME);
        when(stationTrackRepository.save(stationTrack)).thenReturn(stationTrack);

        StationTrack createdStationTrack = stationTrackController.createStationTrack(stationTrack);

        assertEquals(stationTrack, createdStationTrack);
        verify(stationTrackRepository, times(1)).save(stationTrack);
    }

    @Test
    public void shouldUpdateStationTrack() {
        StationTrack stationTrack = new StationTrack();
        stationTrack.setId(STATION_TRACK_ID);
        stationTrack.setName(STATION_TRACK_NAME);

        when(stationTrackRepository.save(stationTrack)).thenReturn(stationTrack);

        StationTrack updatedStationTrack = stationTrackController.updateStationTrack(STATION_TRACK_ID, stationTrack);

        assertEquals(stationTrack, updatedStationTrack);
        verify(stationTrackRepository, times(1)).save(stationTrack);
    }

    @Test
    public void shouldDeleteStationTrackById() {
        stationTrackController.deleteStationTrackById(STATION_TRACK_ID);
        verify(stationTrackRepository, times(1)).deleteById(STATION_TRACK_ID);
    }
}

