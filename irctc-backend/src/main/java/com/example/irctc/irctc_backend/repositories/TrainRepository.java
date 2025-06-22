package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train,String> {
}
