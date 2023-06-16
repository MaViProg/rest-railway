package com.ikonnikova.restrailway.dto;

import com.ikonnikova.restrailway.entity.Wagon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Information about station track")
public class CreateStationTrackRequestDTO {

    @Schema(description = "The name of the station track")
    private String name;

    @Schema(description = "The ID of the station model")
    private Long stationModelId;

    @Schema(description = "The list of wagons associated with the station track")
    private List<Wagon> wagons;
}


