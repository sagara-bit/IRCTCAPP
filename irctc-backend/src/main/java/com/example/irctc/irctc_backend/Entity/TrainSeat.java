package com.example.irctc.irctc_backend.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="train_seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_schedule_id")
    private TrainSchedule trainSchedule;

    @Enumerated(EnumType.STRING)
    private CoachType coachType; //Enum : AC,SLEEEPER,GENERAL

    private  Integer totalSeats;

    private  Integer availableSeats;
    //nextToAssign + numberofbok
     private  Integer nextToAssign = 1;

    private Double price;

    private Integer seatNumberToAssign;
    private Integer trainSeatOrder;

    public  boolean isCoachFull(){
        return  availableSeats <= 0;
    }

    public  boolean isSeatAvailable(int seatToBook){
        return seatToBook <=availableSeats;
    }






}
