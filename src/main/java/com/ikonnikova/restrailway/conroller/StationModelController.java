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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Rest controller for managing station models.
 */

@RestController
@RequestMapping("/api/station-models")
@Tag(name = "Station model", description = "API for managing station models")
public class StationModelController {

    private static final Logger logger = LoggerFactory.getLogger(StationModelController.class);

    @Autowired
    private StationModelRepository stationModelRepository;

    /**
     * Get all station models.
     *
     * @return The list of all station models.
     */
    @GetMapping
    @Operation(summary = "Get all station models")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StationModel.class)))
    })
    public List<StationModel> getAllStationModels() {
        logger.info("Getting all station models");
        return stationModelRepository.findAll();
    }

    /**
     * Get a station model by ID.
     *
     * @param id The ID of the station model.
     * @return The station model with the specified ID.
     * @throws EntityNotFoundException if the station model is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a station model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public StationModel getStationModelById(@PathVariable Long id) {
        logger.info("Getting station model by ID: {}", id);
        return stationModelRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Station model not found with ID: {}", id);
                    return new EntityNotFoundException("Station model not found with ID: " + id);
                });
    }

    /**
     * Create a new station model.
     *
     * @param stationModel The station model to create.
     * @return The created station model.
     */
    @PostMapping
    @Operation(summary = "Create a station model")
    @ApiResponse(responseCode = "201", description = "Station model created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
    })
    public StationModel createStationModel(@RequestBody StationModel stationModel) {
        logger.info("Creating a station model: {}", stationModel);
        StationModel createdStationModel = stationModelRepository.save(stationModel);
        logger.info("Station model created: {}", createdStationModel);
        return createdStationModel;
    }

    /**
     * Update a station model.
     *
     * @param id           The ID of the station model to update.
     * @param stationModel The updated station model data.
     * @return The updated station model.
     * @throws EntityNotFoundException if the station model is not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a station model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station model updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public StationModel updateStationModel(@PathVariable Long id, @RequestBody StationModel stationModel) {
        logger.info("Updating station model with ID: {}", id);
        logger.info("Updated station model: {}", stationModel);

        stationModel.setId(id);
        StationModel updatedStationModel = stationModelRepository.save(stationModel);

        logger.info("Station model updated successfully: {}", updatedStationModel);
        return updatedStationModel;
    }

    /**
     * Delete a station model by ID.
     *
     * @param id The ID of the station model to delete.
     * @throws EntityNotFoundException if the station model is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Station model deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Station model not found")
    })
    public void deleteStationModelById(@PathVariable Long id) {
        logger.info("Deleting station model with ID: {}", id);

        if (stationModelRepository.existsById(id)) {
            stationModelRepository.deleteById(id);
            logger.info("Station model deleted successfully.");
        } else {
            throw new EntityNotFoundException("Station model not found with ID: " + id);
        }
    }

}

