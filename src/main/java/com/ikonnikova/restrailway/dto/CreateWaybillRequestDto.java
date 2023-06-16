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
@Schema(description = "Request data for creating a waybill")
public class CreateWaybillRequestDto {
    @Schema(description = "The weight of the cargo")
    private Double cargoWeight;

    @Schema(description = "The weight of the wagon")
    private Double wagonWeight;

    @Schema(description = "The serial number of the waybill")
    private Integer serialNumber;

    @Schema(description = "The number of the wagon")
    private String wagonNumber;

    @Schema(description = "The ID of the cargo")
    private Long cargoId;
}

