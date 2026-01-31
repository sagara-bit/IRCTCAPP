package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.CoachType;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabeTrainResponse {
    private Long traindId;
    private String trainName;
    private  String trainNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private Map<CoachType,Integer> seatsAvailable;
    private Map<CoachType,Double> priceByCoach;
}
