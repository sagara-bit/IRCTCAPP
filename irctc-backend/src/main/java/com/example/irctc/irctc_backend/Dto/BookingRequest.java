package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.BookingPassenger;
import com.example.irctc.irctc_backend.Entity.CoachType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long userId;
   private Long trainScheduleId;
   private Long trainId;
   private Long sourceStationId;
   private Long destinationStationId;
   private LocalDate journeyDate;
   private CoachType coachType;
   private List<BookingPassengerDto> passengers;
}
