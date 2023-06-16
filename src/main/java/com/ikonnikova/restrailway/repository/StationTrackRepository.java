package com.ikonnikova.restrailway.repository;

import com.ikonnikova.restrailway.entity.StationTrack;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface StationTrackRepository extends JpaRepository<StationTrack, Long> {

    boolean existsByName(String name);

    @Transactional
    @Query("SELECT st FROM StationTrack st JOIN FETCH st.wagons")
    List<StationTrack> findAllWithWagons();
}

