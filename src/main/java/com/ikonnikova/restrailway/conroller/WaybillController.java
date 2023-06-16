package com.ikonnikova.restrailway.conroller;

import com.ikonnikova.restrailway.dto.CreateWaybillRequestDto;
import com.ikonnikova.restrailway.dto.CreateWaybillResponseDto;
import com.ikonnikova.restrailway.dto.UpdateWaybillDto;
import com.ikonnikova.restrailway.entity.Cargo;
import com.ikonnikova.restrailway.entity.Waybill;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.WaybillRepository;
import com.ikonnikova.restrailway.service.WagonService;
import com.ikonnikova.restrailway.service.WaybillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waybills")
@Tag(name = "Waybill controller", description = "Waybill API")
public class WaybillController {

    @Autowired
    private WaybillRepository waybillRepository;

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private WagonService wagonService;

    /**
     * getAllWaybills()
     * GET http://localhost:8084/api/waybills
     *
     * @return
     */
    @GetMapping
    @Operation(summary = "Get all waybills")
    @ApiResponse(responseCode = "200", description = "List of waybills retrieved successfully")
    public List<Waybill> getAllWaybills() {
        return waybillRepository.findAll();
    }

    /**
     * getWaybillById()
     * GET http://localhost:8083/api/waybills/id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get waybill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Waybill.class))}),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public Waybill getWaybillById(@PathVariable Long id) {
        return waybillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Waybill not found with id " + id));
    }

    /**
     * createWaybill()
     * POST http://localhost:8083/api/waybills/
     *
     * @param createWaybillRequestDto
     * @return
     */
    @PostMapping
    @Operation(summary = "Create a waybill")
    @ApiResponse(responseCode = "201", description = "Waybill created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CreateWaybillResponseDto.class))})
    public CreateWaybillResponseDto createWaybill(@RequestBody CreateWaybillRequestDto createWaybillRequestDto) {

        Waybill waybill = new Waybill();

        waybill.setCargoWeight(createWaybillRequestDto.getCargoWeight());
        waybill.setWagonWeight(createWaybillRequestDto.getWagonWeight());
        waybill.setSerialNumber(createWaybillRequestDto.getSerialNumber());
        waybill.setWagonNumber(createWaybillRequestDto.getWagonNumber());

        Cargo cargo = new Cargo();

        cargo.setId(createWaybillRequestDto.getCargoId());
        waybill.setCargo(cargo);

        Waybill waybill2 = waybillService.createWaybill(waybill);
        CreateWaybillResponseDto createWaybillResponseDto = new CreateWaybillResponseDto();
        createWaybillResponseDto.setCargoWeight(waybill2.getCargoWeight());
        createWaybillResponseDto.setWagonWeight(waybill2.getWagonWeight());
        createWaybillResponseDto.setSerialNumber(waybill2.getSerialNumber());
        createWaybillResponseDto.setWagonNumber(waybill2.getWagonNumber());
        createWaybillResponseDto.setCargoId(waybill2.getCargo().getId());
        createWaybillResponseDto.setId(waybill2.getId());

        return createWaybillResponseDto;
    }

    /**
     * updateWaybill()
     * PUT http://localhost:8083/api/waybills/id
     *
     * @param updateWaybillDto
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a waybill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill updated successfully"),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public Waybill updateWaybill(@RequestBody UpdateWaybillDto updateWaybillDto,
                                 @PathVariable Long id) {

        Waybill waybill = new Waybill();
        waybill.setId(id);
        waybill.setCargoWeight(updateWaybillDto.getCargoWeight());
        waybill.setWagonWeight(updateWaybillDto.getWagonWeight());
        waybill.setSerialNumber(updateWaybillDto.getSerialNumber());
        waybill.setWagonNumber(updateWaybillDto.getWagonNumber());

        Cargo cargo = new Cargo();
        cargo.setId(updateWaybillDto.getCargoId());

        waybill.setCargo(cargo);
        return waybillRepository.save(waybill);
    }

    /**
     * deleteWaybill()
     * DELETE http://localhost:8083/api/waybills/id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a waybill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public void deleteWaybill(@PathVariable Long id) {
        waybillRepository.deleteById(id);
    }

    /**
     * Операция убытия вагонов на сеть РЖД.
     * Вагоны могут убывать только с начала состава.
     * waybillId} - фактический идентификатор накладной
     * <p>
     * POST http://localhost:8083/api/waybills/1/depart
     *
     * @param waybillId
     * @return
     */
    @PostMapping("/{waybillId}/depart")
    @Operation(summary = "Depart wagons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagons departed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> departWagons(@PathVariable Long waybillId) {
        try {
            waybillService.departWagons(waybillId);
            return ResponseEntity.ok("Wagons departed successfully");

        } catch (IllegalArgumentException | IllegalStateException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
