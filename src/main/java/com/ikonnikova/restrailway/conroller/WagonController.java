package com.ikonnikova.restrailway.conroller;

import com.ikonnikova.restrailway.dto.MoveWagonsRequestDTO;
import com.ikonnikova.restrailway.dto.ReceiveWagonsRequestDTO;
import com.ikonnikova.restrailway.dto.WagonDTO;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.WagonMovePosition;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.service.WagonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Rest controller for managing wagons.
 */
@RestController
@RequestMapping("/api/wagons")
@Tag(name = "Wagon controller", description = "API for managing wagons")
public class WagonController {

    @Autowired
    private WagonRepository wagonRepository;

    @Autowired
    private WagonService wagonService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get wagon by ID.
     *
     * @param id The ID of the wagon.
     * @return The wagon with the specified ID.
     * @throws EntityNotFoundException if the wagon is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get wagon by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagon retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WagonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Wagon not found")
    })
    public WagonDTO getWagonById(@PathVariable Long id) {
        Wagon wagon = wagonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wagon not found with id " + id));
        return modelMapper.map(wagon, WagonDTO.class);
    }

    /**
     * Get all wagons.
     *
     * @return The list of all wagons.
     */
    @GetMapping
    @Operation(summary = "Get all wagons")
    public List<WagonDTO> getAllWagons() {
        List<Wagon> wagons = wagonRepository.findAll();
        return wagons.stream()
                .map(wagon -> modelMapper.map(wagon, WagonDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Create a new wagon.
     *
     * @param wagon The wagon to create.
     * @return The created wagon.
     */
    @PostMapping
    @Operation(summary = "Create a wagon")
    @ApiResponse(responseCode = "200", description = "Wagon created successfully")
    public Wagon createWagon(@Valid @RequestBody Wagon wagon) {
        return wagonRepository.save(wagon);
    }

    /**
     * Update a wagon.
     *
     * @param id              The ID of the wagon to update.
     * @param updatedWagonDTO The updated wagon data.
     * @return The updated wagon.
     * @throws EntityNotFoundException if the wagon is not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a wagon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagon updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WagonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Wagon not found")
    })
    public WagonDTO updateWagon(@PathVariable Long id, @RequestBody WagonDTO updatedWagonDTO) {
        Wagon wagon = wagonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wagon not found with id " + id));
        wagon.setNumber(updatedWagonDTO.getNumber());
        wagon.setType(updatedWagonDTO.getType());
        wagon.setTareWeight(updatedWagonDTO.getTareWeight());
        wagon.setLoadCapacity(updatedWagonDTO.getLoadCapacity());
        wagon.setPosition(WagonMovePosition.valueOf(String.valueOf(updatedWagonDTO.getPosition())));
        Wagon savedWagon = wagonRepository.save(wagon);
        return modelMapper.map(savedWagon, WagonDTO.class);
    }

    /**
     * Delete a wagon by ID.
     *
     * @param id The ID of the wagon to delete.
     * @return ResponseEntity indicating the success of the operation.
     * @throws EntityNotFoundException if the wagon is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a wagon by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagon deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Wagon not found")
    })
    public ResponseEntity<?> deleteWagonById(@PathVariable Long id) {
        Wagon wagon = wagonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wagon not found"));
        wagonRepository.delete(wagon);
        return ResponseEntity.ok().build();
    }

    /**
     * Receive wagons.
     *
     * @param receiveWagonsRequestDTO The request containing wagon IDs and station track ID.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/receive")
    @Operation(summary = "Receive wagons")
    @ApiResponse(responseCode = "200", description = "Wagons received successfully")
    public ResponseEntity<String> receiveWagons(@RequestBody ReceiveWagonsRequestDTO receiveWagonsRequestDTO) {
        wagonService.receiveWagons(receiveWagonsRequestDTO.getWagonsId(), receiveWagonsRequestDTO.getStationTrackId());
        return ResponseEntity.ok("Wagons received successfully");
    }

    /**
     * Move wagons.
     *
     * @param request The request containing wagon IDs, station track ID, and position.
     * @return ResponseEntity indicating the success of the operation or the error message.
     */
    @PostMapping("/move-wagons")
    @Operation(summary = "Move wagons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wagons moved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> moveWagons(@RequestBody MoveWagonsRequestDTO request) {
        List<Long> wagons = request.getWagons();
        Long stationTrackId = request.getStationTrackId();
        WagonMovePosition direction = request.getPosition();

        try {
            wagonService.moveWagons(wagons, stationTrackId, direction);
            return ResponseEntity.ok("Wagons moved successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}


