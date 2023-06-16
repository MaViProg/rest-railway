package com.ikonnikova.restrailway.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
 * Справочник номенклатур грузов (Код груза, Наименование груза)
 * 
 */
@Entity
@Table(name = "cargo")
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
@Schema(description = "Cargo information")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The ID of the cargo")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    @Schema(description = "The code of the cargo")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    @Schema(description = "The name of the cargo")
    private String name;

    public Cargo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Cargo(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cargo")
    @Schema(description = "Waybill")
    private Waybill waybill;
}
