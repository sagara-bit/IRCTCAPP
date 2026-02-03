package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.Booking;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPassengerDto {

    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private  String seatNumber;
    private String coachId;


}
