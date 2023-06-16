import com.ikonnikova.restrailway.conroller.StationModelController;
import com.ikonnikova.restrailway.entity.StationModel;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.StationModelRepository;
import org.junit.Before;
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
public class StationModelControllerTest {

    @Mock
    private StationModelRepository stationModelRepository;

    @InjectMocks
    private StationModelController stationModelController;

    @Before
    public void setUp() {
        // Mock the behavior of the stationModelRepository
        StationModel stationModel = new StationModel();
        stationModel.setId(1L);
        stationModel.setName("Station 1");
        when(stationModelRepository.findById(1L)).thenReturn(Optional.of(stationModel));

        List<StationModel> stationModels = Arrays.asList(stationModel);
        when(stationModelRepository.findAll()).thenReturn(stationModels);
    }


    @Test(expected = EntityNotFoundException.class)
    public void testGetStationModelByIdNotFound() {
        Long id = 2L;
        when(stationModelRepository.findById(id)).thenReturn(Optional.empty());

        stationModelController.getStationModelById(id);
    }

    @Test
    public void testCreateStationModel() {
        StationModel stationModel = new StationModel(2L, "Station 2");
        when(stationModelRepository.save(stationModel)).thenReturn(stationModel);

        StationModel createdStationModel = stationModelController.createStationModel(stationModel);

        assertEquals(stationModel, createdStationModel);
        verify(stationModelRepository, times(1)).save(stationModel);
    }

    @Test
    public void testUpdateStationModel() {
        Long id = 1L;
        StationModel stationModel = new StationModel(id, "Updated Station 1");

        when(stationModelRepository.save(stationModel)).thenReturn(stationModel);

        StationModel updatedStationModel = stationModelController.updateStationModel(id, stationModel);

        assertEquals(stationModel, updatedStationModel);
        verify(stationModelRepository, times(1)).save(stationModel);
    }

    @Test
    public void testDeleteStationModelById() {
        Long id = 1L;
        stationModelController.deleteStationModelById(id);
        verify(stationModelRepository, times(1)).deleteById(id);
    }
}
