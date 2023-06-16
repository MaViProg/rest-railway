package com.ikonnikova.restrailway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response data for creating a station track")
public class CreateStationTrackResponseDTO {
    @Schema(description = "The ID of the created station track")
    private Long id;

    @Schema(description = "The name of the created station track")
    private String name;

    @Schema(description = "The ID of the associated station model")
    private Long stationModelId;
}

