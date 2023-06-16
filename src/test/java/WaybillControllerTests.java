import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ikonnikova.restrailway.conroller.WaybillController;
import com.ikonnikova.restrailway.dto.CreateWaybillRequestDto;
import com.ikonnikova.restrailway.entity.Cargo;
import com.ikonnikova.restrailway.entity.Waybill;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.repository.WaybillRepository;
import com.ikonnikova.restrailway.service.WaybillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class WaybillControllerTests {

    @Mock
    private WaybillRepository waybillRepository;

    @Mock
    private WaybillService waybillService;

    @Mock
    private WagonRepository wagonRepository;


    @InjectMocks
    private WaybillController waybillController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllWaybills() {
        List<Waybill> waybills = new ArrayList<>();
        waybills.add(new Waybill());
        waybills.add(new Waybill());
        when(waybillRepository.findAll()).thenReturn(waybills);

        List<Waybill> result = waybillController.getAllWaybills();

        assertThat(result.size()).isEqualTo(2);
        verify(waybillRepository).findAll();
    }

    @Test
    public void testGetWaybillById() {
        Waybill waybill = new Waybill();
        when(waybillRepository.findById(1L)).thenReturn(Optional.of(waybill));

        Waybill result = waybillController.getWaybillById(1L);

        assertThat(result).isEqualTo(waybill);
        verify(waybillRepository).findById(1L);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(waybillController).build();
    }

    @Test
    public void testCreateWaybill() throws Exception {
        CreateWaybillRequestDto requestDto = new CreateWaybillRequestDto();
        requestDto.setCargoWeight(100.0);
        requestDto.setWagonWeight(50.0);
        requestDto.setSerialNumber(123);
        requestDto.setWagonNumber("ABC123");
        requestDto.setCargoId(1L);

        Waybill waybill = new Waybill();
        waybill.setId(1L);
        waybill.setCargoWeight(requestDto.getCargoWeight());
        waybill.setWagonWeight(requestDto.getWagonWeight());
        waybill.setSerialNumber(requestDto.getSerialNumber());
        waybill.setWagonNumber(requestDto.getWagonNumber());

        Cargo cargo = new Cargo();
        cargo.setId(requestDto.getCargoId());
        waybill.setCargo(cargo);

        when(waybillService.createWaybill(any(Waybill.class))).thenReturn(waybill);

        mockMvc.perform(post("/api/waybills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cargoWeight", is(100.0)))
                .andExpect(jsonPath("$.wagonWeight", is(50.0)))
                .andExpect(jsonPath("$.serialNumber", is(123)))
                .andExpect(jsonPath("$.wagonNumber", is("ABC123")))
                .andExpect(jsonPath("$.cargoId", is(1)));
    }

    private static String asJsonString(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(object);
    }


    @Test(expected = EntityNotFoundException.class)
    public void testDeleteWaybill() {
        // Arrange
        when(waybillRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        waybillController.deleteWaybill(1L);

        // Assert
        verify(waybillRepository).deleteById(1L);
    }

}
