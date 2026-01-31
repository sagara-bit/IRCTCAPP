package com.example.irctc.irctc_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="train_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate runDate;
    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;
    private Integer availableSeats;
    //kitini seat ki type
    @OneToMany(mappedBy = "trainSchedule")
    private List<TrainSeat> trainSeats;
    //booking
    @OneToMany(mappedBy = "trainSchedule")
    private List<Booking> bookings;
}
