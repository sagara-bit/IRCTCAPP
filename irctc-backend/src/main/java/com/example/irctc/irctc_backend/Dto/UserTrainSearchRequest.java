package com.example.irctc.irctc_backend.Dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTrainSearchRequest {
    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;

}
