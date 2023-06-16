package com.ikonnikova.restrailway.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity Wagon
 * Паспорт вагонов
 * Номер
 * Тип
 * Вес тары
 * Грузоподъемность)
 */

@Entity
@Table(name = "wagon")
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"_links"})
public class Wagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The ID of the wagon.")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    @Schema(description = "The number of the wagon.", required = true)
    private String number;

    @NotNull
    @Column(name = "type", nullable = false)
    @Schema(description = "The type of the wagon.", required = true)
    private String type;

    @NotNull
    @Column(name = "tare_weight", nullable = false)
    @Schema(description = "The tare weight of the wagon.", required = true)
    private double tareWeight;

    @NotNull
    @Column(name = "load_capacity", nullable = false)
    @Schema(description = "The load capacity of the wagon.", required = true)
    private double loadCapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_track_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "The associated station track.")
    private StationTrack stationTrack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waybill_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "The associated waybill.")
    private Waybill waybill;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    @Schema(description = "The position of the wagon.", required = true, allowableValues = {"HEAD", "TAIL"})
    private WagonMovePosition position;

    public Wagon(String number, String type, double tareWeight, double loadCapacity, StationTrack stationTrack) {
        this.number = number;
        this.type = type;
        this.tareWeight = tareWeight;
        this.loadCapacity = loadCapacity;
        this.stationTrack = stationTrack;
        this.position = WagonMovePosition.TAIL;
    }

    public Wagon(long id, StationTrack stationTrack, int i) {
        this.id = id;
        this.stationTrack = stationTrack;
        this.position = WagonMovePosition.TAIL;
    }

    public Wagon(long id, String number, String type, int tareWeight, int loadCapacity, WagonMovePosition position) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.tareWeight = tareWeight;
        this.loadCapacity = loadCapacity;
        this.position = position;
    }

    public Wagon(long id, String number, String type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }


    public int getPosition() {
        switch (position) {
            case HEAD:
                return 0;
            case TAIL:
                return 1;
            default:
                throw new IllegalStateException("Invalid position: " + position);
        }
    }
}




