package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.BookingStatus;
import com.example.irctc.irctc_backend.Entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private StationDto sourceStation;
    private StationDto destinationStation;
    private String pnr;
    private BigDecimal totalFare;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private List<BookingPassengerDto> passengers;


}
