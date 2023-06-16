package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Long> {

   @Query(value = "SELECT * FROM waybill WHERE wagon_number = :wagonNumber ORDER BY serial_number DESC LIMIT 1", nativeQuery = true)
   List<Waybill> findLastWaybillsByWagonNumber(@Param("wagonNumber") String wagonNumber);
}

