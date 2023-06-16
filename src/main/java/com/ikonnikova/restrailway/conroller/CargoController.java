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

@RestController
@RequestMapping("/api/cargos")
@Tag(name = "Cargo API", description = "API для управления грузами")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @PostMapping
    @Operation(summary = "Создание груза")
    public Cargo createCargo(@RequestBody Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о грузе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о грузе успешно получена"),
            @ApiResponse(responseCode = "404", description = "Груз не найден")
    })
    public Cargo getCargoById(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
    }

    @GetMapping
    @Operation(summary = "Получение списка всех грузов")
    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление информации о грузе")
    public Cargo updateCargo(@PathVariable Long id, @RequestBody Cargo updatedCargo) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
        cargo.setCode(updatedCargo.getCode());
        cargo.setName(updatedCargo.getName());
        return cargoRepository.save(cargo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление груза по ID")
    public ResponseEntity<?> deleteCargoById(@PathVariable Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cargo not found"));
        cargoRepository.delete(cargo);
        return ResponseEntity.ok().build();
    }
}
