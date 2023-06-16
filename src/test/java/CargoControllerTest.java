import com.ikonnikova.restrailway.conroller.CargoController;
import com.ikonnikova.restrailway.entity.Cargo;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.CargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CargoControllerTest {

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private CargoController cargoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCargo_ReturnsNewCargo() {
        Cargo inputCargo = new Cargo();
        inputCargo.setId(1L);
        inputCargo.setCode("C001");
        inputCargo.setName("Cargo 1");

        when(cargoRepository.save(any(Cargo.class))).thenReturn(inputCargo);

        Cargo createdCargo = cargoController.createCargo(inputCargo);

        assertNotNull(createdCargo);
        assertEquals(inputCargo.getId(), createdCargo.getId());
        assertEquals(inputCargo.getCode(), createdCargo.getCode());
        assertEquals(inputCargo.getName(), createdCargo.getName());

        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }

    @Test
    void getCargoById_WithValidId_ReturnsCargo() {
        Long cargoId = 1L;
        Cargo cargo = new Cargo();
        cargo.setId(cargoId);
        cargo.setCode("C001");
        cargo.setName("Cargo 1");

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargo));

        Cargo foundCargo = cargoController.getCargoById(cargoId);

        assertNotNull(foundCargo);
        assertEquals(cargoId, foundCargo.getId());
        assertEquals(cargo.getCode(), foundCargo.getCode());
        assertEquals(cargo.getName(), foundCargo.getName());

        verify(cargoRepository, times(1)).findById(cargoId);
    }

    @Test
    void getCargoById_WithInvalidId_ThrowsEntityNotFoundException() {
        Long cargoId = 1L;

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cargoController.getCargoById(cargoId));

        verify(cargoRepository, times(1)).findById(cargoId);
    }

    @Test
    void getAllCargos_ReturnsAllCargos() {
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(new Cargo(1L, "C001", "Cargo 1"));
        cargos.add(new Cargo(2L, "C002", "Cargo 2"));

        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> allCargos = cargoController.getAllCargos();

        assertNotNull(allCargos);
        assertEquals(cargos.size(), allCargos.size());
        assertEquals(cargos.get(0).getId(), allCargos.get(0).getId());
        assertEquals(cargos.get(1).getId(), allCargos.get(1).getId());
        // Assert other properties as needed

        verify(cargoRepository, times(1)).findAll();
    }

    @Test
    void updateCargo_WithValidId_ReturnsUpdatedCargo() {
        Long cargoId = 1L;
        Cargo existingCargo = new Cargo();
        existingCargo.setId(cargoId);
        existingCargo.setCode("C001");
        existingCargo.setName("Cargo 1");

        Cargo updatedCargo = new Cargo();
        updatedCargo.setId(cargoId);
        updatedCargo.setCode("C002");
        updatedCargo.setName("Updated Cargo");

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(existingCargo));
        when(cargoRepository.save(any(Cargo.class))).thenReturn(updatedCargo);

        Cargo resultCargo = cargoController.updateCargo(cargoId, updatedCargo);

        assertNotNull(resultCargo);
        assertEquals(updatedCargo.getCode(), resultCargo.getCode());
        assertEquals(updatedCargo.getName(), resultCargo.getName());

        verify(cargoRepository, times(1)).findById(cargoId);
        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }

    @Test
    void updateCargo_WithInvalidId_ThrowsEntityNotFoundException() {
        Long cargoId = 1L;
        Cargo updatedCargo = new Cargo();
        updatedCargo.setId(cargoId);
        updatedCargo.setCode("C002");
        updatedCargo.setName("Updated Cargo");

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cargoController.updateCargo(cargoId, updatedCargo));

        verify(cargoRepository, times(1)).findById(cargoId);
        verify(cargoRepository, never()).save(any(Cargo.class));
    }

    @Test
    void deleteCargoById_WithValidId_ReturnsHttpStatusOk() {
        Long cargoId = 1L;
        Cargo cargo = new Cargo();
        cargo.setId(cargoId);
        cargo.setCode("C001");
        cargo.setName("Cargo 1");

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargo));

        ResponseEntity<?> responseEntity = cargoController.deleteCargoById(cargoId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(cargoRepository, times(1)).findById(cargoId);
        verify(cargoRepository, times(1)).delete(cargo);
    }

    @Test
    void deleteCargoById_WithInvalidId_ThrowsEntityNotFoundException() {
        Long cargoId = 1L;

        when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cargoController.deleteCargoById(cargoId));

        verify(cargoRepository, times(1)).findById(cargoId);
        verify(cargoRepository, never()).delete(any(Cargo.class));
    }
}
