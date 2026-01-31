package com.example.irctc.irctc_backend.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainScheduleDto {

    private Long id;
    private LocalDate runDate;
    private Long trainId;
    private Integer availableSeats;

}
