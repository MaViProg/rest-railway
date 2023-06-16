import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.WagonMovePosition;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.service.WagonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WagonServiceTests {

    @Mock
    private WagonRepository wagonRepository;

    @Mock
    private StationTrackRepository stationTrackRepository;

    @InjectMocks
    private WagonService wagonService;

    @Test(expected = IllegalArgumentException.class)
    public void testReceiveWagonsInvalidStationTrackId() {
        Long stationTrackId = 1L;
        List<Long> wagonsId = Arrays.asList(1L, 2L);

        when(stationTrackRepository.findById(stationTrackId)).thenReturn(Optional.empty());

        wagonService.receiveWagons(wagonsId, stationTrackId);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testMoveWagonsInvalidDestinationStationTrackId() {
        Long stationTrackId = 1L;
        List<Long> wagons = Arrays.asList(1L, 2L);
        WagonMovePosition direction = WagonMovePosition.HEAD;

        when(stationTrackRepository.findById(stationTrackId)).thenReturn(Optional.empty());

        wagonService.moveWagons(wagons, stationTrackId, direction);
    }

    @Test
    public void testGetAllWagons() {
        List<Wagon> expectedWagons = Arrays.asList(
                new Wagon(1L, "W1", "Type 1"),
                new Wagon(2L, "W2", "Type 2"),
                new Wagon(3L, "W3", "Type 3")
        );

        when(wagonRepository.findAll()).thenReturn(expectedWagons);

        List<Wagon> actualWagons = wagonRepository.findAll();

        assertEquals(expectedWagons, actualWagons);
    }

    @Test
    public void testGetWagonById() {
        Long wagonId = 1L;
        Wagon expectedWagon = new Wagon(wagonId, "W1", "Type 1");

        when(wagonRepository.findById(wagonId)).thenReturn(Optional.of(expectedWagon));

        Optional<Wagon> actualWagon = wagonRepository.findById(wagonId);

        assertEquals(Optional.of(expectedWagon), actualWagon);
    }

}
