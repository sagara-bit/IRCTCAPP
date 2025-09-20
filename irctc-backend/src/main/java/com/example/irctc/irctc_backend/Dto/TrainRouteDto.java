package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.TrainImage;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainRouteDto {
    private Long id;
//    private Long trainId;
//    private Long stationId;
    private TrainDTO train;
    private StationDto station;
    private Integer stationOrder;
    private LocalTime arrivalTime;
    private  LocalTime departureTime;
    private Integer haltMinutes;
    private Integer distanceFromSource;


}
