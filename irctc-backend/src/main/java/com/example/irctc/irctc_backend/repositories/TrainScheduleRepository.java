package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import  java.util.List;
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule,Long> {

    @Query("select ts from TrainSchedule ts where ts.train.id =?1")
   List<TrainSchedule> findByTrainId(long trainId);

}
