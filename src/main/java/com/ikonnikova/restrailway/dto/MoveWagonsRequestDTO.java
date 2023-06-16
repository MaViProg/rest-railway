package com.ikonnikova.restrailway.dto;

import com.ikonnikova.restrailway.entity.WagonMovePosition;
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
@Schema(description = "Request data for moving wagons")
public class MoveWagonsRequestDTO {
    @Schema(description = "List of wagon IDs to move")
    private List<Long> wagons;

    @Schema(description = "ID of the destination station track")
    private Long stationTrackId;

    @Schema(description = "Position for the moved wagons (HEAD or TAIL)")
    private WagonMovePosition position;
}



