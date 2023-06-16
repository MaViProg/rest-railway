import com.ikonnikova.restrailway.conroller.WagonController;
import com.ikonnikova.restrailway.dto.WagonDTO;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.service.WagonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WagonControllerTests {

    @Mock
    private WagonRepository wagonRepository;

    @Mock
    private WagonService wagonService;

    @InjectMocks
    private WagonController wagonController;

    @Before
    public void setUp() {

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetWagonByIdNotFound() {
        Long id = 2L;
        when(wagonRepository.findById(id)).thenReturn(Optional.empty());

        wagonController.getWagonById(id);
    }

    @Test
    public void testCreateWagon() {
        Wagon wagon = new Wagon(2L, "W2", "Type 2");
        when(wagonRepository.save(wagon)).thenReturn(wagon);

        Wagon createdWagon = wagonController.createWagon(wagon);

        assertEquals(wagon, createdWagon);
        verify(wagonRepository, times(1)).save(wagon);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateWagonNotFound() {
        Long id = 2L;
        WagonDTO updatedWagonDTO = new WagonDTO();
        updatedWagonDTO.setId(id);
        updatedWagonDTO.setNumber("W2 Updated");
        updatedWagonDTO.setType("Type 2 Updated");

        when(wagonRepository.findById(id)).thenReturn(Optional.empty());

        wagonController.updateWagon(id, updatedWagonDTO);
    }

    @Test
    public void testDeleteWagonById() {
        Long id = 1L;
        Wagon existingWagon = new Wagon(id, "W1", "Type 1");
        when(wagonRepository.findById(id)).thenReturn(Optional.of(existingWagon));

        ResponseEntity<?> response = wagonController.deleteWagonById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(wagonRepository, times(1)).findById(id);
        verify(wagonRepository, times(1)).delete(existingWagon);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteWagonByIdNotFound() {
        Long id = 2L;
        when(wagonRepository.findById(id)).thenReturn(Optional.empty());

        wagonController.deleteWagonById(id);
    }

}



