package com.ikonnikova.restrailway.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity StationModel
 * Станционная модель (Станции, Пути станций)
 */
@Entity
@Table(name = "station")
@AllArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class StationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The ID of the station")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "The name of the station", required = true)
    private String name;

    @OneToMany(mappedBy = "stationModel", fetch = FetchType.EAGER)
    @JsonIgnore
    @Schema(hidden = true)
    private List<StationTrack> stationTracks = new ArrayList<>();

    public StationModel(String name) {
        this.name = name != null ? name : "";
    }

    public StationModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}




