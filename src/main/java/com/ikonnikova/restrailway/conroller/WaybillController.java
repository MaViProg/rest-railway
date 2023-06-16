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

/**
 * Rest controller for managing waybills.
 */

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
     * Get all waybills.
     *
     * @return The list of all waybills.
     */
    @GetMapping
    @Operation(summary = "Get all waybills")
    @ApiResponse(responseCode = "200", description = "List of waybills retrieved successfully")
    public List<Waybill> getAllWaybills() {
        return waybillRepository.findAll();
    }

    /**
     * Get waybill by ID.
     *
     * @param id The ID of the waybill.
     * @return The waybill with the specified ID.
     * @throws EntityNotFoundException if the waybill is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get waybill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Waybill.class))
            }),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public Waybill getWaybillById(@PathVariable Long id) {
        return waybillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Waybill not found with id " + id));
    }

    /**
     * Create a waybill.
     *
     * @param createWaybillRequestDto The request data for creating a waybill.
     * @return The response data containing the created waybill.
     */
    @PostMapping
    @Operation(summary = "Create a waybill")
    @ApiResponse(responseCode = "201", description = "Waybill created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CreateWaybillResponseDto.class))
    })
    public CreateWaybillResponseDto createWaybill(@RequestBody CreateWaybillRequestDto createWaybillRequestDto) {
        Waybill waybill = new Waybill();
        waybill.setCargoWeight(createWaybillRequestDto.getCargoWeight());
        waybill.setWagonWeight(createWaybillRequestDto.getWagonWeight());
        waybill.setSerialNumber(createWaybillRequestDto.getSerialNumber());
        waybill.setWagonNumber(createWaybillRequestDto.getWagonNumber());

        Cargo cargo = new Cargo();
        cargo.setId(createWaybillRequestDto.getCargoId());
        waybill.setCargo(cargo);

        Waybill createdWaybill = waybillService.createWaybill(waybill);

        CreateWaybillResponseDto createWaybillResponseDto = new CreateWaybillResponseDto();
        createWaybillResponseDto.setCargoWeight(createdWaybill.getCargoWeight());
        createWaybillResponseDto.setWagonWeight(createdWaybill.getWagonWeight());
        createWaybillResponseDto.setSerialNumber(createdWaybill.getSerialNumber());
        createWaybillResponseDto.setWagonNumber(createdWaybill.getWagonNumber());
        createWaybillResponseDto.setCargoId(createdWaybill.getCargo().getId());
        createWaybillResponseDto.setId(createdWaybill.getId());

        return createWaybillResponseDto;
    }

    /**
     * Update a waybill.
     *
     * @param updateWaybillDto The request data for updating the waybill.
     * @param id               The ID of the waybill to update.
     * @return The updated waybill.
     * @throws EntityNotFoundException if the waybill is not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a waybill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill updated successfully"),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public Waybill updateWaybill(@RequestBody UpdateWaybillDto updateWaybillDto, @PathVariable Long id) {
        Waybill waybill = waybillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Waybill not found with id " + id));

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
     * Delete a waybill by ID.
     *
     * @param id The ID of the waybill to delete.
     * @throws EntityNotFoundException if the waybill is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a waybill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waybill deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Waybill not found")
    })
    public void deleteWaybill(@PathVariable Long id) {
        if (!waybillRepository.existsById(id)) {
            throw new EntityNotFoundException("Waybill not found with id " + id);
        }
        waybillRepository.deleteById(id);
    }

    /**
     * Depart wagons for a given waybill.
     *
     * @param waybillId The ID of the waybill.
     * @return ResponseEntity indicating the success of the operation or the error message.
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
