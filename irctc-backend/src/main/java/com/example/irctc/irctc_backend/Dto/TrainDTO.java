package com.example.irctc.irctc_backend.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TrainDTO {


    @NotEmpty(message = "train number is required")
    @Size(min=5,max=20,message="Invalid length of tain no.")
    @Pattern(regexp = "^\\d{5}$",message = "Invalid train number")
    @Id
    private String trainNo;

    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ]{2,49}$", message = "Invalid train name")
    private String name;
    private  String routeName;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
