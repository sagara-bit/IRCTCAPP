package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.CoachType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainSeatDto {
    private  Long id;
    private Long trainScheduleId;
    private CoachType coachType;
    private  Integer totalSeats;
    private  Integer availableSeats;
    private Double price;
    private Integer seatNumberToAssign;
    private Integer trainSeatOrder;


}
