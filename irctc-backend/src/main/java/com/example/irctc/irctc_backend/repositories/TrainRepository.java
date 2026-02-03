package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train,Long> {
    @Query("""
    select tr.train
                   from TrainRoute tr
                   where tr.station.id in (:sourceStationId, :destinationStationId)
                     and tr.train.trainNo is not null
                   group by tr.train
                   having count(distinct tr.station.id) = 2
""")
    List<Train> findTrainBySourceAndDestination(@Param("sourceStationId")Long sourceStationId, @Param("destinationStationId") Long destinationStationId );

    @Query("select tr from TrainRoute tr")
    List<TrainRoute> findAllRoutes();
}
