package com.example.irctc.irctc_backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="trains")
public class Train {
    @Id
    private String trainNo;
    private String name;

    private  String routeName;


    public Train(String trainNo, String name,String routeName) {
        this.trainNo = trainNo;
        this.name = name;
        this.routeName = routeName;
    }

    public Train(){

    }

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
