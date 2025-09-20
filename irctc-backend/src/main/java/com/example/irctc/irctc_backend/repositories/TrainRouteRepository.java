package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRouteRepository extends JpaRepository<TrainRoute,Long> {

    //Additonal Query Methoda Can be defined here if needed
    List<TrainRoute> findByTrain(Train train);
}
