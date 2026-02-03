package com.example.irctc.irctc_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_passengers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    private String name;
    private Integer age;
    private String gender;
    @ManyToOne
    private TrainSeat trainSeat;
    private  String seatNumber;
}
