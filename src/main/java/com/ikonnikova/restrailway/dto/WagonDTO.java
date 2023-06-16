package com.ikonnikova.restrailway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"_links"})
@Schema(description = "Wagon information")
public class WagonDTO {
    @Schema(description = "Wagon ID")
    private Long id;

    @Schema(description = "Wagon number")
    private String number;

    @Schema(description = "Wagon type")
    private String type;

    @Schema(description = "Tare weight of the wagon")
    private double tareWeight;

    @Schema(description = "Load capacity of the wagon")
    private double loadCapacity;

    @Schema(description = "Position of the wagon")
    private int position;
}

