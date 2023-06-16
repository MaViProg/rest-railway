package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления данными путевых листов.
 */
@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Long> {

}


