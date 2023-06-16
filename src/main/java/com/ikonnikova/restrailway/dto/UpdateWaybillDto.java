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
@Schema(description = "Data for updating a waybill")
public class UpdateWaybillDto {
    @Schema(description = "New cargo weight")
    private Double cargoWeight;

    @Schema(description = "New wagon weight")
    private Double wagonWeight;

    @Schema(description = "New serial number")
    private Integer serialNumber;

    @Schema(description = "New wagon number")
    private String wagonNumber;

    @Schema(description = "New cargo ID")
    private Long cargoId;
}

