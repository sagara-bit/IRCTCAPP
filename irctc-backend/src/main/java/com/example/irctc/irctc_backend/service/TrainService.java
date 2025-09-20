package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.TrainDTO;

import java.util.List;

public interface TrainService {
    //create Train
    public TrainDTO createTrain(TrainDTO trainDTO);
    //list of trains
    public List<TrainDTO> getAllTrains();

    // get train by id

    public TrainDTO getTrainById(Long id);

    //update Train
    public  TrainDTO updateTrain(Long id, TrainDTO trainDTO);

    //delete Train
    public  void deleteTrain(Long id);
}
