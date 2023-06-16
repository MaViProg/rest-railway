package com.ikonnikova.restrailway.conroller;

import com.ikonnikova.restrailway.entity.StationModel;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.StationModelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/station-models")
@Tag(name = "Station model", description = "Station model API")
public class StationModelController {

    @Autowired
    StationModelRepository stationModelRepository;

    @GetMapping
    @Operation(summary = "Get all station models")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StationModel.class)))
    })
    public List<StationModel> getAllStationModels() {
        return stationModelRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a station model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public StationModel getStationModelById(@PathVariable Long id) {
        return stationModelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Station model not found with ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Create a station model")
    @ApiResponse(responseCode = "201", description = "Station model created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
    })
    public StationModel createStationModel(@RequestBody StationModel stationModel) {
        return stationModelRepository.save(stationModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a station model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station model updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public StationModel updateStationModel(@PathVariable Long id, @RequestBody StationModel stationModel) {
        stationModel.setId(id);
        return stationModelRepository.save(stationModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Station model deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public void deleteStationModelById(@PathVariable Long id) {
        stationModelRepository.deleteById(id);
    }
}
