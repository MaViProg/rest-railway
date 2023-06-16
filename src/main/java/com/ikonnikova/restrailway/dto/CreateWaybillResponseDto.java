package com.ikonnikova.restrailway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response data for creating a waybill")
public class CreateWaybillResponseDto {
    @Schema(description = "The ID of the created waybill")
    private Long id;

    @Schema(description = "The weight of the cargo in the waybill")
    private Double cargoWeight;

    @Schema(description = "The weight of the wagon in the waybill")
    private Double wagonWeight;

    @Schema(description = "The serial number of the waybill")
    private Integer serialNumber;

    @Schema(description = "The number of the wagon in the waybill")
    private String wagonNumber;

    @Schema(description = "The ID of the cargo in the waybill")
    private Long cargoId;
}

