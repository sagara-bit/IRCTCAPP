package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train,Long> {
    @Query("""
            select tr
             from TrainRoute tr
                where tr.station.id = :sourceStationId or tr.station.id = :destinationStationId
                group by tr.id
                having SUM(CASE WHEN tr.station.id = :sourceStationId THEN 1 ELSE 0 END)> 0  
                        AND SUM(CASE WHEN tr.station.id = :destinationStationId THEN 1 ELSE 0 END)> 0 
                        AND ( MIN(CASE when tr.station.id=:sourceStationId THEN tr.stationOrder ELSE 9999 END)<  MIN(CASE when tr.station.id=:destinationStationId THEN tr.stationOrder ELSE 9999 END) )
            """)
    List<TrainRoute> findTrainBySourceAndDestinationInOrder(@Param("sourceStationId")Long sourceStationId, @Param("destinationStationId") Long destinationStationId);
}
