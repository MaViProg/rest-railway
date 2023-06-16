package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления данными о грузах.
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}

