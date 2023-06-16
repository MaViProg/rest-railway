package com.ikonnikova.restrailway.conroller;

import com.ikonnikova.restrailway.entity.Cargo;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.CargoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for managing cargos.
 */

@RestController
@RequestMapping("/api/cargos")
@Tag(name = "Cargo API", description = "API for managing cargos")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    /**
     * Create a new cargo.
     *
     * @param cargo The cargo to create.
     * @return The created cargo.
     */
    @PostMapping
    @Operation(summary = "Create a cargo")
    public Cargo createCargo(@RequestBody Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    /**
     * Get a cargo by ID.
     *
     * @param id The ID of the cargo.
     * @return The cargo with the specified ID.
     * @throws EntityNotFoundException if the cargo is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a cargo by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo information received successfully"),
            @ApiResponse(responseCode = "404", description = "Cargo not found")
    })
    public Cargo getCargoById(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
    }

    /**
     * Get all cargos.
     *
     * @return The list of all cargos.
     */
    @GetMapping
    @Operation(summary = "Get all cargos")
    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    /**
     * Update a cargo.
     *
     * @param id           The ID of the cargo to update.
     * @param updatedCargo The updated cargo data.
     * @return The updated cargo.
     * @throws EntityNotFoundException if the cargo is not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a cargo")
    public Cargo updateCargo(@PathVariable Long id, @RequestBody Cargo updatedCargo) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
        cargo.setCode(updatedCargo.getCode());
        cargo.setName(updatedCargo.getName());
        return cargoRepository.save(cargo);
    }

    /**
     * Delete a cargo by ID.
     *
     * @param id The ID of the cargo to delete.
     * @return ResponseEntity with no content if the cargo is successfully deleted.
     * @throws EntityNotFoundException if the cargo is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cargo by ID")
    public ResponseEntity<?> deleteCargoById(@PathVariable Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
        cargoRepository.delete(cargo);
        return ResponseEntity.ok().build();
    }
}
