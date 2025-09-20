package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainSeatRepository extends JpaRepository<TrainSeat,Long> {
    @Query("Select ts from TrainSeat ts where ts.trainSchedule.id =?1 order by ts.trainSeatOrder")
    List<TrainSeat> findByTrainScheduleId(Long trainScheduleId);
}
