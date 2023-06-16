package com.ikonnikova.restrailway.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request data for receiving wagons")
public class ReceiveWagonsRequestDTO {
    @Schema(description = "ID of the destination station track")
    private Long stationTrackId;

    @Schema(description = "List of wagon IDs to receive")
    private List<Long> wagonsId;
}

