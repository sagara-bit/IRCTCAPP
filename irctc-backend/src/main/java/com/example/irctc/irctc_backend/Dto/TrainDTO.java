package com.example.irctc.irctc_backend.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {
    @Id
    private Long id;  // Add this line
    @NotEmpty(message = "train number is required")
    @Size(min=5,max=20,message="Invalid length of tain no.")
    @Pattern(regexp = "^\\d{5}$",message = "Invalid train number")
    private String trainNo;

    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ]{2,49}$", message = "Invalid train name")
    private String name;

    private Integer totalDistance;
    private StationDto sourceStation;
    private StationDto destinationStation;


//    public String getTrainNo() {
//        return trainNo;
//    }
//
//    public void setTrainNo(String trainNo) {
//        this.trainNo = trainNo;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getRouteName() {
//        return routeName;
//    }
//
//    public void setRouteName(String routeName) {
//        this.routeName = routeName;
//    }
}
