package com.ikonnikova.restrailway.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Entity StationTrack
 * Пути станций
 */
@Entity
@Table(name = "station_track")
@AllArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@JsonSubTypes({
        @JsonSubTypes.Type(value = StationTrack.class, name = "station_track")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class StationTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The ID of the station track.")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "The name of the station track.", required = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "station_model_id", nullable = false)
    @Schema(description = "The associated station model.")
    private StationModel stationModel;

    @OneToMany(mappedBy = "stationTrack", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Schema(hidden = true)
    private List<Wagon> wagons;

    public StationTrack(String name, StationModel stationModel) {
        this.name = name;
        this.stationModel = stationModel;
        stationModel.getStationTracks().add(this);
    }

    public StationTrack() {
    }

    public StationTrack(Long stationTrackId) {
    }

    public void setStationModel(StationModel stationModel) {
        this.stationModel = stationModel;
        stationModel.getStationTracks().add(this);
    }
}


