package com.ikonnikova.restrailway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Data for updating a station track")
public class UpdateStationTrackDTO {
    @Schema(description = "New name of the station track")
    private String name;

    @Schema(description = "New ID of the station model")
    private Long stationModelId;
}

