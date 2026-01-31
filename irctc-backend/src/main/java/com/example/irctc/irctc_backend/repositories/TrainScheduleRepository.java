package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import  java.util.List;
import java.util.Optional;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule,Long> {

    @Query("select ts from TrainSchedule ts where ts.train.id =?1")
   List<TrainSchedule> findByTrainId(long trainId);
    @Query("select ts from TrainSchedule ts where ts.train.id = ?1 and ts.runDate =?2")
    Optional<TrainSchedule> findByTrainIdAndRunDate(Long trainId, LocalDate runDate);
}
