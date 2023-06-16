package com.ikonnikova.restrailway.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Entity Waybill
 * Натурный лист для приема вагонов
 * (Список вагонов с атрибутами: Порядковый номер, Номер вагона,
 * Номенклатура груза, Вес груза в вагоне, Вес вагона)
 */


@Entity
@Table(name = "waybill")
@AllArgsConstructor
@NoArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Waybill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The ID of the waybill.")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "cargo_id", referencedColumnName = "id")
    @Schema(description = "The associated cargo.", required = true)
    private Cargo cargo;

    @NotNull
    @Column(name = "cargo_weight", nullable = false)
    @Schema(description = "The weight of the cargo.", required = true)
    private Double cargoWeight;

    @NotNull
    @Column(name = "wagon_weight", nullable = false)
    @Schema(description = "The weight of the wagon.", required = true)
    private Double wagonWeight;

    @NotNull
    @Column(name = "serial_number", nullable = false)
    @Schema(description = "The serial number of the waybill.", required = true)
    private Integer serialNumber;

    @NotNull
    @Column(name = "wagon_number", nullable = false)
    @Schema(description = "The number of the wagon.", required = true)
    private String wagonNumber;

    public Waybill(Cargo cargo) {
        this.cargo = cargo;
        this.cargoWeight = 0.0;
        this.wagonWeight = 0.0;
    }

    @Override
    public String toString() {
        return "Waybill{" +
                "id=" + id +
                ", cargo=" + cargo +
                ", cargoWeight=" + cargoWeight +
                ", wagonWeight=" + wagonWeight +
                ", serialNumber=" + serialNumber +
                ", wagonNumber='" + wagonNumber + '\'' +
                '}';
    }
}
