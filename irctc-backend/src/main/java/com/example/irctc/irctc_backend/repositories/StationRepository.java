package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station,Long> {
}
