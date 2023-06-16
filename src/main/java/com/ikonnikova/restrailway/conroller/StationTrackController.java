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

import java.util.List;


@RestController
@RequestMapping("/api/station-tracks")
@Tag(name = "Station Track", description = "Station Track API")
public class StationTrackController {

    @Autowired
    private StationTrackRepository stationTrackRepository;

    @GetMapping
    @Operation(summary = "Get all station tracks")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StationTrack.class)))
    })
    public List<StationTrack> getAllStationTracks() {
        return stationTrackRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a station track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public StationTrack getStationTrackById(@PathVariable Long id) {
        return stationTrackRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Station track not found with ID: " + id));
    }

    @PostMapping
    @Operation(summary = "Create a station track")
    @ApiResponse(responseCode = "201", description = "Station track created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
    })
    public StationTrack createStationTrack(@RequestBody StationTrack stationTrack) {
        return stationTrackRepository.save(stationTrack);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a station track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station track updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StationTrack.class))
            }),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public StationTrack updateStationTrack(@PathVariable Long id, @RequestBody StationTrack stationTrack) {
        stationTrack.setId(id);
        return stationTrackRepository.save(stationTrack);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station track by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Station track deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Station track not found")
    })
    public void deleteStationTrackById(@PathVariable Long id) {
        stationTrackRepository.deleteById(id);
    }
}
