package com.ikonnikova.restrailway.conroller;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.exceptions.EntityNotFoundException;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
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
 * Rest controller for station tracks.
 */

@RestController
@RequestMapping("/api/station-tracks")
@Tag(name = "Station Track", description = "API for station tracks")
public class StationTrackController {

    private static final Logger logger = LoggerFactory.getLogger(StationTrackController.class);

    @Autowired
    private StationTrackRepository stationTrackRepository;

    /**
     * Get all station tracks.
     *
     * @return The list of all station tracks.
     */
    @GetMapping
    @Operation(summary = "Get all station tracks")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StationTrack.class)))
    })
    public List<StationTrack> getAllStationTracks() {
        logger.info("Get all station tracks");
        return stationTrackRepository.findAll();
    }

    /**
     * Get a station track by ID.
     *
     * @param id The ID of the station track.
     * @return The station track with the specified ID.
     * @throws EntityNotFoundException if the station track is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a station track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public StationTrack getStationTrackById(@PathVariable Long id) {
        logger.info("Get station track by ID: {}", id);
        return stationTrackRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Station track not found with ID: " + id));
    }

    /**
     * Create a new station track.
     *
     * @param stationTrack The station track to create.
     * @return The created station track.
     */
    @PostMapping
    @Operation(summary = "Create a station track")
    @ApiResponse(responseCode = "201", description = "Station track created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
    })
    public StationTrack createStationTrack(@RequestBody StationTrack stationTrack) {
        logger.info("Create station track: {}", stationTrack);
        return stationTrackRepository.save(stationTrack);
    }

    /**
     * Update a station track.
     *
     * @param id           The ID of the station track to update.
     * @param stationTrack The updated station track data.
     * @return The updated station track.
     * @throws EntityNotFoundException if the station track is not found.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a station track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station track updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public StationTrack updateStationTrack(@PathVariable Long id, @RequestBody StationTrack stationTrack) {
        logger.info("Update station track with ID: {}", id);
        stationTrack.setId(id);
        return stationTrackRepository.save(stationTrack);
    }

    /**
     * Delete a station track by ID.
     *
     * @param id The ID of the station track to delete.
     * @throws EntityNotFoundException if the station track is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Station track deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public void deleteStationTrackById(@PathVariable Long id) {
        logger.info("Delete station track with ID: {}", id);
        stationTrackRepository.deleteById(id);
    }
}

